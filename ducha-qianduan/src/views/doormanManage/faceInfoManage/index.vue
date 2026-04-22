<template>
  <div style="padding: 10px">
    <!-- 搜索栏 -->
    <div style="margin-bottom: 10px">
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
      <el-button
        type="primary"
        @click="handleSearchAddCondition()"
        style="margin-left: 10px"
        >点击输入查询条件</el-button
      >
      <el-button
        type="primary"
        @click="handleSearch()"
        style="margin-left: 10px"
        >查询</el-button
      >
      <el-button type="primary" @click="addFaceInfo()" style="margin-left: 10px"
        >新增</el-button
      >
      <el-button
        :disabled="multipleSelection.length <= 0"
        type="danger"
        @click="batchDeleteFaceInfo()"
        style="margin-left: 10px"
        >批量删除</el-button
      >
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
        prop="name"
        label="姓名"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="sex"
        label="性别"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="province"
        label="省份"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="city"
        label="城市"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="certificateNumber"
        label="证件号"
        min-width="120"
        align="center"
        sortable
      />
      <el-table-column
        prop="similarity"
        label="相似度"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="FMCount"
        label="人脸比对成功总次数"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="occurrences"
        label="出现频次"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="lastFMTime"
        label="最近比对成功时间"
        min-width="120"
        align="center"
        sortable
      />
      <el-table-column
        prop="importTime"
        label="入库时间"
        min-width="120"
        align="center"
        sortable
      />
      <el-table-column label="操作" fixed="right" width="250" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="small"
            @click="viewFaceInfo(scope.row)"
            >查看</el-button
          >
          <el-button
            type="primary"
            size="small"
            @click="updateFaceInfo(scope.row)"
            >修改</el-button
          >
          <el-button
            type="danger"
            size="small"
            @click="deleteFaceInfo(scope.row)"
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

    <face-search-dialog
      :visible.sync="searchVisible"
      :faceData="currentFace"
      @submit="handleSearchFaceInfoSubmit"
      @cancel="handleSearchFaceInfoCancel"
    />

    <!-- 查看详情弹窗 -->
    <face-detail-dialog :visible.sync="viewVisible" :detail="currentFace" />

    <face-edit-dialog
      :visible.sync="editVisible"
      :faceData="currentFace"
      :deviceId="selectedDeviceId"
      @submit="handleFaceInfoSubmit"
      @cancel="handleFaceInfoCancel"
    />
  </div>
</template>

<script>
import request from "@/utils/request";
import FaceSearchDialog from "./FaceSearchDialog.vue";
import FaceDetailDialog from "./FaceDetailDialog.vue";
import FaceEditDialog from "./FaceEditDialog.vue";
import DeviceEditDialog from "@/views/doorcarManage/deviceManage/DeviceEditDialog.vue";

export default {
  name: "PeopleFaceManage",
  components: {
    DeviceEditDialog,
    FaceSearchDialog,
    FaceDetailDialog,
    FaceEditDialog,
  },
  data() {
    return {
      currentFace: null,
      selectedDeviceId: null, // 当前选择的设备
      loading: false,
      devices: [], // 设备列表
      currentPage: 1,
      pageSize: 10,
      total: 0,

      searchCondition: null,

      searchVisible: false,
      viewVisible: false,
      editVisible: false,
      tableData: [
        // {
        //   // searchID: "test", // ro, req, string, 搜索记录唯一标识
        //   // responseStatus: true, // ro, req, bool, 查询结果状态
        //   // responseStatusStrg: "OK", // ro, req, enum, 查询状态字符串描述 [OK#查询结束, MORE#还有数据, NO MATCH#无匹配]
        //   // numOfMatches: 1, // ro, req, int, 本次返回的记录条数
        //   // totalMatches: 1, // ro, req, int, 符合条件的总条数
        //   // progress: 1, // ro, opt, int, 查询进度(0-100)，100表示查询完成
        //   // MatchList: [
        //   //   {
        //   FDID: "test", // ro, opt, string, 人脸比对库唯一标识ID
        //   thresholdValue: 1, // ro, opt, int, 检测阈值 [0-100] 越大准确率越高
        //   bornTime: "1970-01-01+08:00", // ro, opt, date, 出生日期
        //   name: "张三", // ro, req, string, 姓名 [1,32]
        //   sex: "male", // ro, opt, enum, 性别 [male#男, female#女]
        //   province: "11", // ro, opt, string, 省份代码
        //   city: "11", // ro, opt, string, 城市代码
        //   certificateType: "ID", // ro, opt, enum, 证件类型 [ID#身份证, passportID#护照, other#其他]
        //   certificateNumber: "123456789012345678", // ro, opt, string, 证件号 [8,32]
        //   picURL: "http://127.0.0.1/test.jpg", // ro, req, picurl, 图片访问 URL
        //   PID: "PID_TEST_001", // ro, req, string, 图片ID
        //   PersonInfoExtendList: [
        //     {
        //       id: 1, // ro, req, int, 扩展信息序号
        //       enable: true, // ro, req, bool, 扩展信息是否启用
        //       name: "部门", // ro, opt, string, 扩展名称
        //       value: "研发部", // ro, opt, string, 扩展内容
        //     },
        //   ],
        //   similarity: 98.7, // ro, opt, float, 相似度 [0.0,100.0]
        //   ModelingStatus: {
        //     FDID: "test", // ro, opt, string, 人脸库ID
        //     PID: "PID_TEST_001", // ro, opt, string, 图片ID
        //     name: "face_张三", // ro, req, string, 图片名称
        //     status: "success", // ro, req, enum, 建模状态 [success#建模成功, failed#失败, none#未建模]
        //     reason: "noface", // ro, opt, enum, 失败原因 [noface#无人脸, imageURLDownloadFailed#图片下载失败, ...]
        //     customHumanID: "12345678901234567890123456789012", // ro, opt, string, 客户自定义人员ID（32位）
        //     customFaceLibID: "12345678901234567890123456789012", // ro, opt, string, 客户自定义人脸库ID（32位）
        //     modeData: "base64EncodedModelData", // ro, opt, string, 模型数据(Base64)
        //   },
        //   isNoSaveFDPicture: true, // ro, opt, bool, 是否不保存原始人脸库图片
        //   humanId: "HUMAN_1001", // ro, opt, string, 人员ID
        //   modelStatus: "modeling", // ro, opt, enum, 建模状态（旧） [modeling, unmodeled, modelingFailed]
        //   FMCount: 1, // ro, opt, int, 人脸比对成功总次数
        //   importTime: "2025-01-01T00:00:00+08:00", // ro, opt, datetime, 入库时间
        //   UUPID: "UUPID_TEST_001", // ro, opt, string, 底库图片唯一标识ID
        //   occurrences: 3, // ro, opt, int, 出现频次
        //   IMSIList: [
        //     {
        //       IMSI: "460001234567890", // ro, opt, string, IMSI号码
        //       count: 10, // ro, opt, int, 比中次数
        //       lastFMTime: "2025-01-01T00:00:00+08:00", // ro, opt, datetime, 最近比对时间
        //     },
        //   ],
        //   lastFMTime: "2025-01-01T00:00:00+08:00", // ro, opt, datetime, 最近比对成功时间
        //   // },
        //   // ],
        // },
      ],

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
    async handleSearch() {
      try {
        console.log("this.selectedDeviceId", this.selectedDeviceId);
        if (this.selectedDeviceId === null || this.selectedDeviceId === "") {
          this.$message.error("请选择设备！");
          return;
        }
        const data = {
          deviceId: this.selectedDeviceId,
          searchResultPosition: 0,
          maxResults: 1,
          faceLibType: "blackFD",
          FDID: "1",
          FPID: "164",
        };
        console.log("data", data);
        const res = await request({
          url: "/faceInfo/list",
          method: "POST",
          data: data,
        });
        console.log("res为", res);
        if (res && res.status) {
          this.tableData = res.MatchList;
          this.total = res.totalMatches || 0;
          this.$message.success(res.message);
        } else {
          this.tableData = [];
          this.total = 0;
          this.$message.warning("未获取到人脸数据");
        }
      } catch (e) {
        this.$message.error("查询失败：" + e.message);
      } finally {
        this.loading = false;
      }
    },
    viewFaceInfo(row) {
      this.currentFace = row;
      this.viewVisible = true;
    },
    handleSearchAddCondition() {
      console.log("查询");
      this.searchVisible = true;
    },
    addFaceInfo() {
      console.log("新增");
      this.currentFace = {}; // 空对象代替 null
      this.editVisible = true;
    },
    updateFaceInfo(row) {
      console.log("修改", row);
      this.currentFace = row;
      this.editVisible = true;
    },
    // 表格样式修改
    changeHeaderCellStyle(row, column, rowIndex, columnIndex) {
      if (row.columnIndex === 0) {
        return "background: #004279 ; color:#fff;"; // 修改的样式
      } else {
        return "background: #004279 ;color:#fff; ";
      }
    },
    // 监听多选变化
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 切换页码
    handlePageChange(page) {
      this.currentPage = page;
      // this.searchUserInfo()
    },
    // 修改每页条数
    handleSizeChange(size) {
      this.pageSize = size;
      this.currentPage = 1; // 切换条数后回到第一页
      // this.searchUserInfo()
    },
    handleSearchFaceInfoSubmit(faceInfoData) {
      this.searchCondition = faceInfoData;
      this.searchVisible = false;
      this.handleSearch();
    },
    handleSearchFaceInfoCancel() {
      this.searchVisible = false;
    },
    async handleFaceInfoSubmit(faceInfoData) {
      const url = faceInfoData.id ? "/faceInfo/update" : "/faceInfo/add";
      console.log("提交人脸信息参数：", faceInfoData);
      try {
        const res = await request({
          url,
          method: "POST",
          data: faceInfoData,
        });
        console.log("提交人脸信息结果：", res);
        if (res && res.status === true) {
          this.$message.success(res.message || "操作成功");
          this.editVisible = false;
          this.loading = true;
          try {
            await this.handleSearch();
          } finally {
            this.loading = false;
          }
        } else {
          this.$message.error(res?.message || "操作失败");
        }
      } catch (error) {
        console.error("提交人脸信息失败:", error);
        this.$message.error("提交人脸信息失败，请检查网络或后端接口");
      }
    },
    handleFaceInfoCancel() {
      this.editVisible = false;
    },
    // 单条删除
    deleteFaceInfo(row) {
      this.$confirm(`确定要删除工号为【${row.employeeNo}】的人员吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          const list = [
            {
              value: row.value,
              rowKey: row.rowKey,
            },
          ];
          this.deleteFace(list);
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    // 批量删除
    batchDeleteFaceInfo() {
      const list = this.multipleSelection.map((item) => ({
        value: item.value,
        rowKey: item.rowKey,
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
          this.deleteFace(list);
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    // 通用删除方法
    async deleteFace(list) {
      const data = {
        deviceId: this.selectedDeviceId,
        mode: "byEmployeeNo",
        EmployeeNoList: list,
        operateType: "byTerminal",
        terminalNoList: this.terminalNoList || [1], // 示例
        orgNoList: this.orgNoList || [],
      };
      console.log("删除人员参数：", data);
      // try {
      //   const res = await request({
      //     url: "/api/user/delete",
      //     method: "POST",
      //     data: data,
      //   });
      //
      //   if (res.code === 0) {
      //     this.$message.success("删除成功");
      //     this.searchUserInfo(); // 重新加载人员列表
      //   } else {
      //     this.$message.error(res.msg || "删除失败");
      //   }
      // } catch (e) {
      //   console.error("删除人员失败：", e);
      //   this.$message.error("请求出错，请稍后重试");
      // }
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
.dark-form {
  background-color: #0a2340;
  color: #ffffff;
}
.dark-form .el-form-item__label {
  color: #000000;
}
.dark-form .el-input__inner {
  color: #ffffff;
  background-color: #0a2340;
  border-color: #1f3a5b;
}
.dark-form .el-input__inner::placeholder {
  color: #cccccc;
}
.dark-form .el-upload__tip {
  color: #000000;
}
.people-face-management {
  padding: 18px;
  background: #fff;
  border-radius: 6px;
}
.header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 12px;
}
.dialog-footer {
  text-align: right;
}
</style>
