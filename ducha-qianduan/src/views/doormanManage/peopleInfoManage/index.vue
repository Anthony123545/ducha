<template>
  <div style="padding: 10px">
    <el-tabs
      v-model="activeTab"
      @tab-click="handleTabChange"
      style="color: white !important"
    >
      <el-tab-pane
        v-for="tab in peopleTypeTabs"
        :key="tab.name"
        :label="tab.label"
        :name="tab.name"
      ></el-tab-pane>
    </el-tabs>
    <!-- 搜索栏 -->
    <div style="height: 50px; display: flex; align-items: center; gap: 10px">
      <el-dialog
        :visible.sync="photoDialogVisible"
        title="访客拍照"
        width="85vw"
        destroy-on-close
      >
        <div class="photo-wrapper">
          <!-- 截图区域 -->
          <div class="iframe-container">
            <iframe
              ref="visitorFrame"
              :src="visitorUrl"
              class="visitor-iframe"
              allow="insecure-content"
            ></iframe>
          </div>
          <div class="btn-row">
            <el-button
              type="primary"
              size="small"
              @click="photoDialogVisible = false"
              >关闭</el-button
            >
          </div>
        </div>
      </el-dialog>
      <!-- 选择设备 -->
      <el-select
        v-model="selectedDeviceId"
        placeholder="选择设备"
        size="small"
        style="width: 150px"
        clearable
        @change="handleSearch"
      >
        <el-option
          style="color: black"
          v-for="device in devices"
          :key="device.id"
          :label="device.name"
          :value="device.id"
        />
      </el-select>
      <!-- 输入多个工号 -->
      <el-input
        v-model="searchKey"
        placeholder="输入工号（多个用逗号或空格分隔）"
        size="small"
        style="width: 300px"
        clearable
        @keyup.enter.native="handleSearch()"
      />
      <el-button
        type="primary"
        size="small"
        @click="handleSearch()"
        style="margin-left: 10px"
        >查询</el-button
      >
      <el-button type="primary" size="small" @click="handleAdd">新增</el-button>
      <el-button
        :disabled="multipleSelection.length <= 0"
        type="danger"
        size="small"
        @click="handleBatchDelete()"
        >批量删除</el-button
      >
      <el-button
        v-if="activeTab === 'visitor'"
        placeholder="访客拍照"
        type="primary"
        size="small"
        icon="el-icon-camera"
        @click="openPhotoInNewTab"
      >
        访客拍照
      </el-button>
    </div>

    <!-- 人员表格 -->
    <el-table
      v-loading="loading"
      :data="tableData"
      style="width: 100%; margin-top: 10px"
      border
      :header-cell-style="changeHeaderCellStyle"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" align="center" min-width="50" sortable>
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        prop="employeeNo"
        label="工号"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="name"
        label="姓名"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="gender"
        label="性别"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="userType"
        label="人员类型"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="belongGroup"
        label="所属群组"
        min-width="100"
        align="center"
        sortable
      />
      <el-table-column
        prop="roomNumber"
        label="房间号"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="floorNumber"
        label="层号"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column label="有效期" min-width="250" align="center" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.Valid">
            {{ scope.row.Valid.beginTime }} ~ {{ scope.row.Valid.endTime }}
          </span>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="doorRight"
        label="门权限"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="userVerifyMode"
        label="验证方式"
        width="150"
        align="center"
        sortable
      />
      <el-table-column
        prop="openDoorTime"
        label="已开门次数"
        width="100"
        align="center"
        sortable
      />
      <el-table-column
        prop="maxOpenDoorTime"
        label="最大开门次数"
        width="100"
        align="center"
        sortable
      />
      <el-table-column label="操作" width="250" fixed="right" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="small"
            @click="viewUserInfo(scope.row)"
            >查看</el-button
          >
          <el-button
            type="danger"
            size="small"
            @click="updateUserInfo(scope.row)"
            >修改</el-button
          >
          <el-button
            type="danger"
            size="small"
            @click="deleteUserInfo(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div style="margin-top: 10px; text-align: right">
      <el-pagination
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        :page-sizes="[5, 10, 20, 50, 100]"
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
      />
    </div>
    <!-- 查看详情弹窗 -->
    <user-detail-dialog
      v-if="viewVisible"
      :visible.sync="viewVisible"
      :deviceId="selectedDeviceId"
      :detail="currentUser"
    />
    <user-edit-dialog
      v-if="editVisible"
      :visible.sync="editVisible"
      :deviceId="selectedDeviceId"
      :userData="currentUser"
      @submit="handlePeopleInfoSubmit"
      @cancel="handlePeopleInfoCancel"
    />
  </div>
</template>

<script>
import request from "@/utils/request";
import UserDetailDialog from "./UserDetailDialog.vue";
import UserEditDialog from "./UserEditDialog.vue";
import DeviceEditDialog from "@/views/doorcarManage/deviceManage/DeviceEditDialog.vue";

export default {
  name: "PeopleInfoManage",
  components: {
    DeviceEditDialog,
    UserDetailDialog,
    UserEditDialog,
  },
  data() {
    return {
      peopleTypeTabs: [
        { label: "内部人员", name: "normal" },
        { label: "访客", name: "visitor" },
      ],
      activeTab: "normal", // 当前选中的tab
      // 拍照相关
      photoDialogVisible: false, // 控制拍照弹窗显示
      visitorUrl: process.env.VUE_APP_VISITOR_URL, // 访客tab内嵌网页地址

      currentUser: null,
      loading: false,
      devices: [], // 设备列表
      selectedDeviceId: null, // 当前选择的设备
      searchKey: "",
      total: 0,
      currentPage: 1,
      pageSize: 10,

      viewVisible: false,
      editVisible: false,
      tableData: [
        // {
        //   employeeNo: "1001", // ro, req, string, 工号（人员ID）,
        //   name: "张三", // ro, opt, string, 姓名
        //   userType: "normal", // 人员类型 req, enum, 人员类型, subType:string, [normal#普通人（主人）,visitor#来宾（访客）,blackList#非授权名单人]*/
        //   closeDelayEnabled: true, // 是否关门延迟 *opt, bool, 是否关门延迟, desc:true-是，false-否
        //   Valid: {
        //     // 有效期参数 opt, object, 有效期参数, desc:（enable不使能代表长期有效）（有效时间跨度为1970年1月1日0点0分0秒~2037年12月31日23点59分59秒）
        //     enable: true, // req, bool, 使能有效期, desc:false-不使能，true-使能
        //     beginTime: "2025-01-01T00:00:00", // req, string, 有效期起始时间, range:[,], desc:timeType字段不存在或为local时，beginTime为设备本地时间，如：2017-08-01T17:30:08；timeType字段为UTC时，beginTime为UTC时间，如：2017-08-01T17:30:08+08:00
        //     endTime: "2026-01-01T00:00:00", // req, string, 有效期结束时间, range:[,], desc:timeType字段不存在或为local时，endTime为设备本地时间，如：2017-08-01T17:30:08；timeType字段为UTC时，endTime为UTC时间，如：2017-08-01T17:30:08+08:00
        //     timeType: "local", // opt, enum, 时间类型, subType:string, [local#设备本地时间,UTC#UTC时间]*/
        //   },
        //   belongGroup: "1,3,5", // opt, string, 所属群组
        //   password: "123456", // opt, string, 密码
        //   doorRight: "1,2,3", // opt, string, 门权限, range:[,], desc:代表对门1、门3有权限
        //   RightPlan: [
        //     // *opt, array, 门权限计划, subType:object
        //     {
        //       doorNo: 1, // opt, int, 门编号（锁ID）, range:[,], step:, unit:, unitType
        //       planTemplateNo: "1,3", // *opt, string, 计划模板编号, range:[,], desc:同个门不同计划模板采用权限或的方式处理
        //     },
        //     { doorNo: 2, planTemplateNo: "2" },
        //   ],
        //   maxOpenDoorTime: 10, // *opt, string, 计划模板编号, range:[,], desc:同个门不同计划模板采用权限或的方式处理
        //   openDoorTime: 2, // opt, int, 已认证次数, range:[,], step:, unit:, unitType
        //   roomNumber: 305, // opt, int, 房间号, range:[,], step:, unit:, unitType
        //   floorNumber: 3, // opt, int, 层号, range:[,], step:, unit:, unitType
        //   doubleLockRight: true, // opt, bool, 反锁开门权限, desc:true-有权限，false-无权限
        //   localUIRight: true, // opt, bool, 是否具有设备本地UI访问权限, desc:true-有权限，false-无权限
        //   userVerifyMode: "faceAndCard",
        //   // opt, enum, 人员验证方式, subType:string, [cardAndPw#刷卡+密码,card#刷卡,cardOrPw#刷卡或密码,fp#指纹,fpAndPw#指纹+密码,fpOrCard#指纹或刷卡,fpAndCard#指纹+刷卡,fpAndCardAndPw#指纹+刷卡+密码,faceOrFpOrCardOrPw#人脸或指纹或刷卡或密码,faceAndFp#人脸+指纹,faceAndPw#人脸+密码,faceAndCard#人脸+刷卡,face#人脸,employeeNoAndPw#工号+密码,fpOrPw#指纹或密码,employeeNoAndFp#工号+指纹,employeeNoAndFpAndPw#工号+指纹+密码,faceAndFpAndCard#人脸+指纹+刷卡,faceAndPwAndFp#人脸+密码+指纹,employeeNoAndFace#工号+人脸,faceOrfaceAndCard#人脸或人脸+刷卡,fpOrface#指纹或人脸,cardOrfaceOrPw#刷卡或人脸或密码,cardOrFace#刷卡或人脸,cardOrFaceOrFp#刷卡或人脸或指纹],
        //   // desc:人员验证方式的优先级高于读卡器验证方式 若为空表示人员验证方式无效
        //   dynamicCode: "A1B2C3", // opt, string, 动态权限码, range:[,]*/
        //   callNumbers: ["1-1-1-305"], // opt, array, 呼叫号码列表, subType:string, range:[,], desc:默认规则X-X-X-X，如1-1-1-401, roomNumber字段扩展，支持列表时，使用列表配置相关信息
        //   floorNumbers: [3], // opt, array, 层号列表, subType:int, range:[,], desc:floorNumber扩展，支持列表时，可使用该字段配置层号
        //   numOfFace: 1, // ro, opt, int, 关联人脸数量, range:[,], step:, unit:, unitType:, desc:不返回不支持
        //   numOfFP: 2, // ro, opt, int, 关联指纹数量, range:[,], step:, unit:, unitType:, desc:不返回不支持
        //   numOfCard: 1, // ro, opt, int, 关联卡数量, range:[,], step:, unit:, unitType:, desc:不返回不支持
        //   gender: "male", // ro, opt, enum, 人脸图片对应的人员性别, subType:string, [male#男,female#女,unknown#未知]*/
        //   PersonInfoExtends: [
        //     // ro, opt, array, 人员信息扩展, subType:object, range:[,]*/
        //     {
        //       value: "部门：研发部", // ro, opt, string, 人员信息扩展内容, range
        //     },
        //   ],
        //   groupName: "研发部", // opt, string, 组织架构, range:[1,64]
        // },
      ],

      totalMatches: 22, // ro, req, int, 符合条件的记录总条数, range:[,], step:, unit:, unitType
      numOfMatches: 1, // *ro, req, int, 本次返回的记录条数, range:[,], step:, unit:, unitType
      responseStatusStrg: "", // ro, req, enum, 查询状态字符串描述, subType:string, [OK#查询结束,MORE#还有数据等待查询,NO MATCH#没有匹配数据], desc:OK-查询结束,MORE-还有数据等待查询,NO MATCH-没有匹配数据

      // === 枚举映射 ===
      userTypeMap: {
        normal: "普通人（主人）",
        visitor: "访客",
        blackList: "黑名单",
      },
      genderMap: {
        male: "男",
        female: "女",
        unknown: "未知",
      },
      verifyModeMap: {
        cardAndPw: "刷卡+密码",
        card: "刷卡",
        cardOrPw: "刷卡或密码",
        fp: "指纹",
        fpAndPw: "指纹+密码",
        fpOrCard: "指纹或刷卡",
        fpAndCard: "指纹+刷卡",
        fpAndCardAndPw: "指纹+刷卡+密码",
        faceOrFpOrCardOrPw: "人脸或指纹或刷卡或密码",
        faceAndFp: "人脸+指纹",
        faceAndPw: "人脸+密码",
        faceAndCard: "人脸+刷卡",
        face: "人脸",
        employeeNoAndPw: "工号+密码",
        fpOrPw: "指纹或密码",
        employeeNoAndFp: "工号+指纹",
        employeeNoAndFpAndPw: "工号+指纹+密码",
        faceAndFpAndCard: "人脸+指纹+刷卡",
        faceAndPwAndFp: "人脸+密码+指纹",
        employeeNoAndFace: "工号+人脸",
        faceOrfaceAndCard: "人脸或人脸+刷卡",
        fpOrface: "指纹或人脸",
        cardOrfaceOrPw: "刷卡或人脸或密码",
        cardOrFace: "刷卡或人脸",
        cardOrFaceOrFp: "刷卡或人脸或指纹",
        employeeNoAndFaceAndPw: "工号+人脸+密码",
        cardOrFaceOrFaceAndCard: "刷卡或人脸或人脸+刷卡",
      },

      multipleSelection: [], // 存储选中的多行数据
    };
  },
  mounted() {
    this.getDeviceInfo().then(() => {
      if (this.devices.length > 0) {
        this.handleSearch();
      }
    });
  },
  methods: {
    // Tab切换事件
    handleTabChange() {
      console.log("activeTab", this.activeTab);
      this.currentPage = 1;
      this.handleSearch();
    },
    // 打开拍照弹窗
    openPhotoDialog() {
      this.photoDialogVisible = true;
    },
    openPhotoInNewTab() {
      // 直接打开目标服务的原始地址（不是代理地址）
      window.open("http://34.131.62.90", "_blank");
    },
    async handleSearch() {
      this.loading = true;
      try {
        if (!this.selectedDeviceId) {
          this.$message.error("请选择设备！");
          return;
        }

        const MAX_DEVICE_LIMIT = 10; // 闸机硬件单次限制
        const employeeNos = this.searchKey.split(/[,，\s]+/).filter(Boolean);
        const employeeNoList = employeeNos.map((no) => ({ employeeNo: no }));

        // 1. 计算需要分多少次请求
        // 如果每页 50 条，则需要请求 5 次 (每次 10 条)
        const requestCount = Math.ceil(this.pageSize / MAX_DEVICE_LIMIT);
        const finalUserInfo = [];
        let finalTotal = 0;

        // 2. 循环发送分片请求
        for (let i = 0; i < requestCount; i++) {
          // 计算当前分片的起始位置
          // 原理：(当前页起始偏移) + (当前分片索引 * 10)
          const currentPosition =
            (this.currentPage - 1) * this.pageSize + i * MAX_DEVICE_LIMIT;

          const data = {
            deviceId: this.selectedDeviceId,
            searchID: Date.now().toString() + "_" + i, // 加上索引防止 ID 重复
            searchResultPosition: currentPosition,
            maxResults: MAX_DEVICE_LIMIT, // 始终传 10，确保硬件不报错
            EmployeeNoList: employeeNoList,
          };

          console.log(
            `正在请求分片 ${i + 1}/${requestCount}, Position: ${currentPosition}`,
          );

          const res = await request({
            url: "/peopleInfo/list",
            method: "POST",
            data: data,
          });

          if (res && res.status) {
            // 合并数据
            if (res.UserInfo && Array.isArray(res.UserInfo)) {
              finalUserInfo.push(...res.UserInfo);
            }
            // 总数只需要取一次
            finalTotal = res.totalMatches || 0;
          } else {
            // 如果其中一个分片报错，可以跳过或中断
            console.error(`分片 ${i} 请求失败:`, res.message);
            // 如果是第一页第一片就失败，才报错
            if (i === 0) throw new Error(res.message || "接口调用失败");
            break; // 后续分片可能没数据了
          }

          // 如果获取到的数据已经少于 10 条，说明后面没数据了，提前结束循环
          if (!res.UserInfo || res.UserInfo.length < MAX_DEVICE_LIMIT) {
            break;
          }
        }

        // 3. 渲染最终合并后的结果
        this.tableData = finalUserInfo;
        this.total = finalTotal;

        if (this.tableData.length > 0) {
          this.$message.success("获取数据成功");
        } else {
          this.$message.warning("未获取到人员数据");
        }
      } catch (e) {
        this.$message.error("查询失败：" + e.message);
        this.tableData = [];
        this.total = 0;
      } finally {
        this.loading = false;
      }
    },

    async handlePeopleInfoSubmit(formData) {
      console.log("子组件的信息：", formData);

      const peopleInfoJson = formData.get("peopleInfo");
      const faceInfoJson = formData.get("faceInfo");
      const file = formData.get("file");

      try {
        const peopleInfo = peopleInfoJson ? JSON.parse(peopleInfoJson) : {};
        const faceInfo = faceInfoJson ? JSON.parse(faceInfoJson) : null;

        console.log("解析后的 peopleInfo：", peopleInfo);
        console.log("解析后的 faceInfo：", faceInfo);
        console.log("文件对象：", file);

        // -------- 1. 获取工号 --------
        let employeeNo =
          peopleInfo.UserInfo?.employeeNo || peopleInfo.employeeNo || "";
        if (!employeeNo) {
          this.$message.error("工号不能为空！");
          return;
        }
        // 先判断新增 or 编辑
        const isEdit = peopleInfo.isEdit;
        // -------- 2. 处理访客工号 --------
        if (this.activeTab === "visitor") {
          // 确保 UserInfo 存在
          if (!peopleInfo.UserInfo) peopleInfo.UserInfo = {};

          if (!employeeNo.startsWith("visitor")) {
            employeeNo = "visitor" + employeeNo;
          }
          peopleInfo.UserInfo.employeeNo = employeeNo;
        }
        // -------- 3. 新增用户：工号重复性校验 --------
        if (!isEdit) {
          const exist = await this.isExistThisPeople(employeeNo);
          if (exist.success) return;
        }
        console.log("最终提交人员信息 peopleInfo：", peopleInfo);

        // -------- 4. 保存人员信息 --------
        const peopleResult = await this.submitPeopleInfo(peopleInfo);
        if (!peopleResult.success) {
          this.$message.error("人员信息保存失败！");
          return;
        }

        // -------- 5. 如无头像，流程结束 --------
        if (!file) {
          this.$message.success("人员信息保存成功");
          this.editVisible = false;
          await this.handleSearch();
          return;
        }

        // -------- 6. 上传人脸信息 --------
        const faceResult = await this.submitFaceInfo(
          faceInfo,
          file,
          employeeNo,
        );
        if (!faceResult.success) {
          this.$message.error("人员保存成功，但人脸上传失败！");
        } else {
          this.$message.success("人员 + 人脸 保存成功");
        }

        // -------- 7. 关闭窗口 --------
        this.editVisible = false;
        await this.handleSearch();
      } catch (error) {
        console.error("提交人员信息失败:", error);
        this.$message.error("提交人员信息失败，请检查网络或后端接口");
      }
    },
    async isExistThisPeople(employeeNo) {
      const data = {
        deviceId: this.selectedDeviceId,
        searchID: Date.now().toString(), // 唯一ID
        searchResultPosition: (this.currentPage - 1) * this.pageSize,
        maxResults: this.pageSize,
        EmployeeNoList: [{ employeeNo: employeeNo }],
      };
      console.log("data", data);
      const res = await request({
        url: "/peopleInfo/list",
        method: "POST",
        data: data,
      });
      console.log("res为", res);
      if (res && res.status) {
        if (res.totalMatches > 0) {
          this.$message.warning(" 当前工号已存在");
          return { success: true };
        }
      } else {
        // this.$message.warning("未获取到人员数据");
        return { success: false };
      }
    },
    viewUserInfo(row) {
      this.currentUser = row;
      this.viewVisible = true;
    },
    handleAdd() {
      console.log("新增");
      this.currentUser = {};
      this.editVisible = true;
    },
    updateUserInfo(row) {
      console.log("修改", row);
      this.currentUser = row;
      this.editVisible = true;
    },
    // 监听多选变化
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 提交人员信息
    async submitPeopleInfo(peopleInfo) {
      try {
        const url = peopleInfo.isEdit
          ? "/peopleInfo/update"
          : "/peopleInfo/add";
        const res = await request({
          url,
          method: "POST",
          data: peopleInfo,
        });
        console.log("人员信息提交结果：", res);
        return {
          success: res && res.status === true,
          data: res?.data,
        };
      } catch (error) {
        console.error("提交人员信息异常:", error);
        return { success: false };
      }
    },
    // 提交人脸信息
    async submitFaceInfo(faceInfo, file, employeeNo) {
      try {
        // 创建新的 FormData（符合后端接口要求）
        const formData = new FormData();

        // 添加文件（对应后端 @RequestParam("file")）
        if (file) {
          formData.append("file", file);
          console.log("已添加文件到 FormData");
        }
        if (faceInfo) {
          const paramsJson = JSON.stringify({
            ...faceInfo,
            employeeNo: employeeNo, // 确保包含 employeeNo
          });
          formData.append("params", paramsJson);
          console.log("已添加 params JSON:", paramsJson);
        }

        //  验证 FormData 内容
        console.log("FormData 内容：");
        for (let [key, value] of formData.entries()) {
          if (key === "file") {
            console.log(
              `${key}: File对象 - ${value.name} (${value.size} bytes)`,
            );
          } else {
            console.log(`${key}:`, value);
          }
        }

        const faceUrl = faceInfo.isEdit ? "/faceInfo/update" : "/faceInfo/add";
        const faceRes = await request({
          url: faceUrl,
          method: "POST",
          data: formData,
        });
        console.log("人脸信息提交结果：", faceRes);
        return { success: faceRes && faceRes.status === true };
      } catch (error) {
        console.error("提交人脸信息异常:", error);
        return { success: false };
      }
    },
    handlePeopleInfoCancel() {
      this.editVisible = false;
    },
    // 单条删除
    deleteUserInfo(row) {
      this.$confirm(`确定要删除工号为【${row.employeeNo}】的人员吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          const list = [{ employeeNo: row.employeeNo }];
          this.deleteUser(list);
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    // 批量删除
    handleBatchDelete() {
      const list = this.multipleSelection.map((item) => ({
        employeeNo: item.employeeNo,
      }));
      if (list.length === 0) {
        this.$message.warning("请先选择要删除的人员");
        return;
      }
      this.$confirm(
        `确定要删除选中的 ${list.length} 个人员吗？`,
        "批量删除确认",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        },
      )
        .then(() => {
          this.deleteUser(list);
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    // 通用删除方法
    async deleteUser(list) {
      const data = {
        deviceId: this.selectedDeviceId,
        mode: "byEmployeeNo",
        EmployeeNoList: list,
        // operateType: "byTerminal",
        // terminalNoList: this.terminalNoList || [1], // 示例
        // orgNoList: this.orgNoList || [],
      };
      console.log("删除人员参数：", data);
      this.loading = true;
      try {
        const res = await request({
          url: "/peopleInfo/delete",
          method: "POST",
          data: data,
        });
        console.log("删除人员结果", res);
        if (res && res.status) {
          this.$message.success(res.message);
          this.handleSearch();
        } else {
          this.$message.warning(res.message);
        }
      } catch (e) {
        this.$message.error("删除人员结果失败：" + e.message);
      } finally {
        this.loading = false;
      }
    },
    // 表格样式修改
    changeHeaderCellStyle(row, column, rowIndex, columnIndex) {
      if (row.columnIndex === 0) {
        return "background: #004279 ; color:#fff;"; // 修改的样式
      } else {
        return "background: #004279 ;color:#fff; ";
      }
    },
    // 切换页码
    handlePageChange(page) {
      this.currentPage = page;
      this.handleSearch();
    },
    // 修改每页条数
    handleSizeChange(size) {
      this.pageSize = size;
      this.currentPage = 1; // 切换条数后回到第一页
      this.handleSearch();
    },
    async getDeviceInfo() {
      try {
        this.loading = true;
        const data = {
          deviceType: "peopleDoor",
          enable: true,
          paginate: false,
        };
        console.log("获取设备列表参数：", data);
        const res = await request({
          url: "/device/list",
          method: "POST",
          data: data,
        });
        console.log("获取设备列表结果：", res);
        // 判断返回结构
        if (res && res.status === true) {
          this.devices = Array.isArray(res.list) ? res.list : [];
          this.total = typeof res.total === "number" ? res.total : 0;
          this.$message.success(res.message || "获取设备列表成功");
          console.log("设备列表:", this.devices);
          if (this.devices.length > 0) {
            this.selectedDeviceId = this.devices[0].id;
            console.log("选中的设备ID:", this.selectedDeviceId);
            // 获取该设备的人员信息
            // this.handleSearch()
          }
        } else {
          this.devices = [];
          this.total = 0;
          this.$message.error(res.message || "获取设备列表失败");
        }
      } catch (error) {
        console.error("获取设备信息失败:", error);
        this.$message.error("获取设备列表失败，请检查网络或后端接口！");
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>

<style scoped>
/* .header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 12px;
} */

/* 未激活文字颜色 */
.el-tabs >>> .el-tabs__item {
  color: #ffffff;
}

/* 激活 tab 文字颜色 */
.el-tabs >>> .el-tabs__item.is-active {
  color: #13cc65;
}

.photo-wrapper {
  width: 100%;
}

.iframe-container {
  width: 100%;
  height: 75vh;
  position: relative;
}

.visitor-iframe {
  width: 100%;
  height: 100%;
  border: none;
  border-radius: 4px;
}

.btn-row {
  margin-top: 16px;
  text-align: right;
}
</style>
