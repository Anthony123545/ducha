<template>
  <el-dialog
    :visible.sync="localVisible"
    :title="isEdit ? '修改人员' : '新增人员'"
    @close="handleCancel"
    custom-class="custom-dialog"
  >
    <el-form
      :model="formData"
      :rules="formRules"
      ref="userForm"
      label-width="210px"
      class="user-form"
    >
      <el-divider content-position="center">设备信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="设备" prop="deviceIds">
            <el-select
              v-model="formData.deviceIds"
              placeholder="请选择设备"
              multiple
              clearable
            >
              <el-option
                v-for="device in devices"
                :key="device.id"
                :label="device.name"
                :value="device.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider content-position="center">人脸信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="人脸图片" prop="faceInfo">
            <el-upload
              class="avatar-uploader"
              action=""
              :auto-upload="false"
              :limit="1"
              :file-list="fileList"
              :on-change="handleFileChange"
              :on-remove="handleRemove"
              :on-exceed="handleExceed"
              :before-upload="beforeUpload"
              list-type="picture-card"
              accept="image/*"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="人脸库类型" prop="faceLibType">
            <el-select
              v-model="formData.faceLibType"
              placeholder="请选择人脸库类型"
            >
              <el-option label="名单库" value="blackFD" />
              <el-option label="静态库" value="staticFD" />
              <!--              <el-option label="非授权名单人" value="blackList" />-->
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否保存人脸底图">
            <el-switch v-model="formData.saveFacePic" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item
            label="人脸类型"
            prop="faceType"
            placeholder="请选择人脸类型"
          >
            <el-select v-model="formData.faceType">
              <el-option label="普通人脸" value="normalFace" />
              <el-option label="巡更人脸" value="patrolFace" />
              <el-option label="胁迫人脸" value="hijackFace" />
              <el-option label="超级人脸" value="superFace" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否删除该人脸">
            <el-switch v-model="formData.deleteFP" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider content-position="center">人员信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="工号（人员ID）" prop="employeeNo">
            <el-input
              v-model="formData.employeeNo"
              :disabled="isEdit"
              placeholder="请输入工号"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="formData.name" placeholder="请输入姓名" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item
            label="人员类型"
            prop="userType"
            placeholder="请选择人员类型"
          >
            <el-select v-model="formData.userType">
              <el-option label="普通人（主人）" value="normal" />
              <el-option label="访客" value="visitor" />
              <el-option label="非授权名单人" value="blackList" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="人脸图片对应的人员性别" prop="gender">
            <el-select v-model="formData.gender">
              <el-option label="男" value="male" />
              <el-option label="女" value="female" />
              <el-option label="未知" value="unknown" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="人员验证方式">
            <el-select v-model="formData.userVerifyMode">
              <el-option label="同设备" value="" />
              <el-option label="刷卡" value="card" />
              <el-option label="人脸" value="face" />
              <el-option label="刷卡+密码" value="cardAndPw" />
              <el-option label="人脸+密码" value="faceAndPw" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="设备是否进行人员重复添加校验">
            <el-switch v-model="formData.checkUser" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="是否删除该人员">
            <el-switch v-model="formData.deleteUser" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="是否启用有效期" prop="Valid.enable">
            <el-switch v-model="formData.Valid.enable" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="有效期">
            <el-date-picker
              v-model="formData.ValidTimes"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              value-format="yyyy-MM-ddTHH:mm:ss"
              :disabled="!formData.Valid.enable"
              style="width: 400px"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="门权限" prop="doorRight">
            <el-input v-model="formData.doorRight" placeholder="如: 1,3" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否关门延迟">
            <el-switch v-model="formData.closeDelayEnabled" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider content-position="center">其他信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="门权限计划">
            <div style="display: flex; gap: 10px; margin-bottom: 5px">
              <el-input-number
                v-model="formData.RightPlan.doorNo"
                :min="1"
                placeholder="如: 1"
              />
              <el-input
                v-model="formData.RightPlan.planTemplateNo"
                placeholder="如: 1,3,5"
              />
              <el-button type="primary" @click="addRightPlan"
                >添加门权限计划</el-button
              >
            </div>
            <el-table
              :data="formData.RightPlan"
              style="width: 100%; height: 200px"
              border
              size="small"
              placeholder="请添加门权限计划（锁权限计划）"
            >
              <el-table-column prop="doorNo" label="门编号（锁ID）" />
              <el-table-column prop="planTemplateNo" label="计划模板编号" />
              <el-table-column label="操作">
                <template #default="{ row }">
                  <el-button type="text" @click="removeRightPlan(row)"
                    >删除</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="首次认证权限" prop="leaderPermission">
            <el-select
              v-model="formData.leaderPermission"
              multiple
              clearable
              placeholder="请选择权限"
            >
              <el-option label="1" value="1" />
              <el-option label="2" value="2" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="所属群组">
            <el-input v-model="formData.belongGroup" placeholder="如:1,3,5" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码">
            <el-input
              v-model="formData.password"
              type="password"
              placeholder="请输入密码"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="最大认证次数">
            <el-input-number
              v-model="formData.maxOpenDoorTime"
              :min="0"
              placeholder="0为无次数限制"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="已认证次数">
            <el-input-number v-model="formData.openDoorTime" :min="0" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="反锁开门权限">
            <el-switch v-model="formData.doubleLockRight" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否具有设备本地UI访问权限">
            <el-switch v-model="formData.localUIRight" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col>
          <el-form-item label="人员信息扩展">
            <!--        <el-input v-model="value" placeholder="请输入扩展内容" />-->
            <el-button
              type="primary"
              @click="addPersonInfoExtend"
              style="margin-top: 5px"
            >
              添加扩展信息
            </el-button>
            <el-table
              :data="formData.PersonInfoExtends"
              style="width: 100%"
              border
              size="small"
            >
              <el-table-column prop="value" label="扩展内容">
                <template #default="{ row }">
                  <el-input v-model="row.value" placeholder="请输入扩展内容" />
                </template>
              </el-table-column>
              <el-table-column label="操作">
                <template #default="{ row }">
                  <el-button type="text" @click="removePersonInfoExtend(row)"
                    >删除</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="动态权限码">
            <el-input v-model="formData.dynamicCode" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="呼叫号码列表">
            <el-input
              v-model="formData.callNumbers"
              placeholder="如:1-1-1-401,1-1-1-402"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="组织架构">
            <el-input v-model="formData.groupName" placeholder="组织架构名称" />
          </el-form-item>
        </el-col>
      </el-row>
      <!--      <el-row :gutter="20">-->
      <!--        <el-col :span="12">-->
      <!--          <el-form-item label="层号列表">-->
      <!--            <el-input v-model="form.floorNumbers" placeholder="如: 1,2,3" />-->
      <!--          </el-form-item>-->
      <!--        </el-col>-->
      <!--      </el-row>-->
      <!--      <el-row :gutter="20">-->
      <!--        <el-col :span="12">-->
      <!--          <el-form-item label="房间号">-->
      <!--            <el-input-number v-model="form.roomNumber" :min="0" />-->
      <!--          </el-form-item>-->
      <!--        </el-col>-->
      <!--        <el-col :span="12">-->
      <!--          <el-form-item label="层号">-->
      <!--            <el-input-number v-model="form.floorNumber" :min="0" />-->
      <!--          </el-form-item>-->
      <!--        </el-col>-->
      <!--      </el-row>-->
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleSubmit">{{
        isEdit ? "修改" : "新增"
      }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "UserEditDialog",
  props: {
    visible: Boolean,
    deviceId: {
      type: Number,
      default: 0,
    },
    userData: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      localVisible: this.visible,
      devices: [],
      loading: false,
      isEdit: false,
      fileList: [],
      formData: this.getDefaultFormData(),
      formRules: {
        deviceIds: [
          {
            required: true,
            type: "array",
            message: "请选择设备",
            trigger: "change",
          },
        ],
        employeeNo: [
          { required: true, message: "请输入工号", trigger: "blur" },
        ],
        userType: [
          { required: true, message: "请选择人员类型", trigger: "change" },
        ],
        faceType: [
          { required: true, message: "请选择人脸类型", trigger: "change" },
        ],
        faceLibType: [
          { required: true, message: "请选择人脸库类型", trigger: "change" },
        ],
        // // 添加有效期的验证规则
        // "Valid.enable": [
        //   {
        //     required: true,
        //     message: "请选择是否启用有效期",
        //     trigger: "change",
        //   },
        // ],
      },
    };
  },
  watch: {
    visible(val) {
      console.log("visible", val);
      this.localVisible = val;
    },
    localVisible(val) {
      this.$emit("update:visible", val);
    },
    userData: {
      immediate: true,
      handler(newVal) {
        console.log("userData", newVal);
        if (newVal && Object.keys(newVal).length > 0) {
          // 编辑
          this.formData = {
            ...newVal,
            ValidTimes: newVal.Valid
              ? [newVal.Valid.beginTime, newVal.Valid.endTime]
              : [null, null],
          };
          if (this.formData.modelData && this.formData.modelData.preview) {
            this.fileList = [
              { name: "face", url: this.formData.modelData.preview },
            ];
            this.formData.modelData = { preview: this.formData.faceURL };
          }
          this.isEdit = true;
        } else {
          // 新增
          this.getDefaultFormData();
          this.isEdit = false;
        }
      },
    },
  },
  mounted() {
    this.getDeviceInfo();
  },
  methods: {
    getDefaultFormData() {
      this.formData = {
        deviceIds: [],
        employeeNo: "", // 工号
        deleteUser: false, // 是否删除该人员
        name: null, // 姓名
        userType: "normal", // 人员类型
        closeDelayEnabled: false, // 是否关门延迟
        Valid: {
          // 有效期
          enable: false, // Valid里的 enable 字段
          timeType: "local",
        },
        belongGroup: null, // 所属群组
        password: null, // 密码
        doorRight: null, // 门权限
        RightPlan: [], // 门权限计划
        maxOpenDoorTime: null, // 最大认证次数
        openDoorTime: null, // 已认证次数
        doubleLockRight: true, // 反锁开门权限
        localUIRight: false, // 是否具有设备本地UI访问权限
        userVerifyMode: "", // 人员验证方式
        checkUser: true, // 设备是否进行人员重复添加校验
        gender: "male", // 性别
        PersonInfoExtends: [], // 人员信息扩展
        dynamicCode: null, // 动态权限码
        callNumbers: null, // 呼叫号码列表
        groupName: null, // 组织架构
        // floorNumbers: "",
        // floorNumber: "",
        // roomNumber: "",
        faceLibType: "blackFD",
        faceType: "normalFace",
        saveFacePic: true,
        deleteFP: false,
        leaderPermission: null,
      };
      this.fileList = [];
    },
    addPersonInfoExtend() {
      const newItem = { value: "" };
      this.$set(
        this.formData.PersonInfoExtends,
        this.formData.PersonInfoExtends.length,
        newItem,
      );
    },
    beforeUpload(file) {
      const isImage = file.type.startsWith("image/");
      if (!isImage) {
        this.$message.error("只能上传图片文件！");
        return false;
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error("图片大小不能超过 2MB！");
        return false;
      }
      return true;
    },
    // 处理图片选择 - 替换旧图片
    handleFileChange(file, fileList) {
      // 检查文件是否通过验证
      if (!this.beforeUpload(file.raw)) {
        this.fileList = [];
        return;
      }
      console.log("清空之前", this.fileList);
      // 清空之前的文件
      this.handleRemove();
      // 保存原始文件对象
      this.formData.faceFile = file.raw;
      // 更新文件列表（确保只有一个文件）
      this.fileList = [file];
      console.log("清空之后", this.fileList);
    },
    handleRemove() {
      this.formData.modelData = null;
      this.formData.faceFile = null;
      this.fileList = [];
    },
    handleExceed(files, fileList) {
      // 当超过限制时，用新文件替换旧文件
      if (files.length > 0) {
        // 先清空现有文件
        this.handleRemove();
        // 然后处理新文件
        this.handleFileChange(files[0], [files[0]]);
      }
    },
    addRightPlan() {
      const doorNo = this.formData.RightPlan.doorNo; // 直接从表单字段取值
      const planTemplateNo = this.formData.RightPlan.planTemplateNo;
      if (
        !this.formData.RightPlan.doorNo ||
        !this.formData.RightPlan.planTemplateNo
      ) {
        this.$message.warning("请填写门编号和计划模板编号");
        return;
      }
      // 直接创建新对象并push
      this.formData.RightPlan.push({
        doorNo: Number(doorNo), // 转为数字（可选）
        planTemplateNo: planTemplateNo, // 保持字符串
      });
      // 清空临时表单
      this.formData.RightPlan.doorNo = null;
      this.formData.RightPlan.planTemplateNo = "";
    },
    removeRightPlan(row) {
      const index = this.formData.RightPlan.indexOf(row);
      if (index > -1) this.formData.RightPlan.splice(index, 1);
    },
    handleCancel() {
      this.$emit("cancel");
    },
    async handleSubmit() {
      this.$refs.userForm.validate(async (valid) => {
        if (!valid) {
          this.$message.error("请检查表单填写项");
          return;
        }
        // 1. 创建 FormData（专门用于文件上传）
        const formData = new FormData();

        // 2. 添加文件（单独放）
        if (this.formData.faceFile) {
          formData.append("file", this.formData.faceFile);
        } else {
          this.$message.warning("请先选择人脸图片");
          return;
        }

        let validInfo; // 先声明（用 let，因为后面要赋值）
        if (this.formData.Valid.enable === false) {
          validInfo = null;
        } else {
          const validTimes = Array.isArray(this.formData.ValidTimes)
            ? this.formData.ValidTimes
            : [];
          validInfo = {
            enable: this.formData.Valid.enable,
            beginTime: validTimes[0] || null,
            endTime: validTimes[1] || null,
            timeType: this.formData.Valid?.timeType || "local",
          };
        }
        let callNumbersArray = null;
        if (
          this.formData.callNumbers &&
          typeof this.formData.callNumbers === "string"
        ) {
          // 按逗号分割，得到如 ["1-1-1-401", "1-1-1-402"]
          callNumbersArray = this.formData.callNumbers
            .split(",") // 按逗号分割成数组
            .map((item) => item.trim()) // 去掉每个元素两边的空格
            .filter((item) => item !== ""); // 过滤掉空字符串（比如用户输入了多余的逗号）
        }
        const peopleInfo = {
          isEdit: this.isEdit,
          deviceIds: [...this.formData.deviceIds],
          employeeNo: this.formData.employeeNo,
          deleteUser: this.formData.deleteUser,
          name: this.formData.name,
          userType: this.formData.userType,
          closeDelayEnabled: this.formData.closeDelayEnabled,
          Valid: validInfo,
          belongGroup: this.formData.belongGroup,
          password: this.formData.password,
          doorRight: this.formData.doorRight,
          RightPlan: this.formData.RightPlan,
          maxOpenDoorTime: this.formData.maxOpenDoorTime,
          openDoorTime: this.formData.openDoorTime,
          doubleLockRight: this.formData.doubleLockRight,
          localUIRight: this.formData.localUIRight,
          userVerifyMode: this.formData.userVerifyMode,
          checkUser: this.formData.checkUser,
          gender: this.formData.gender,
          PersonInfoExtends: this.formData.PersonInfoExtends, // 人员信息扩展
          dynamicCode: this.formData.dynamicCode,
          callNumbers: callNumbersArray, // 呼叫号码列表
          groupName: this.formData.groupName, // 组织架构
          // floorNumbers: this.formData.floorNumbers, // 层号列表扩展
          // floorNumber: this.formData.floorNumber,
          // roomNumber: this.formData.roomNumber,

          // employeeNo: "999",
          // deleteUser: false,
          // name: "999",
          // userType: "visitor",
          // closeDelayEnabled: false,
          // Valid:{
          //   enable: true,
          //   beginTime: "2017-08-01T17:00:00",
          //   endTime: "2017-08-01T17:00:00",
          //   timeType: "local"
          // },
          // belongGroup: "1,3,5",
          // password: "123456",
          // doorRight: "1",
          // RightPlan[
          //   {
          //     doorNo: 1,
          //     planTemplateNo: "1,3,5"
          //   }
          // ],
          // maxOpenDoorTime: 0,
          // openDoorTime: 0,
          // doubleLockRight: true,
          // localUIRight: true,
          // userVerifyMode: "face",
          // checkUser: true,
          // gender: "male",
          // PersonInfoExtends:[
          //   {
          //     value:"test",
          //   }
          // ],
          // dynamicCode: "123456",
          // callNumbers: [
          //   "1-1-1-401",
          //   "1-1-1-402",
          //   "1-1-1-403",
          // ],
          // groupName:"test",
          // // floorNumbers: [1,2],
          // // floorNumber: 1,
          // // roomNumber: 123,
        };
        formData.append("peopleInfo", JSON.stringify(peopleInfo));
        let faceInfo;
        if (this.formData.faceFile) {
          faceInfo = {
            isEdit: this.isEdit,
            deviceIds: [...this.formData.deviceIds],
            faceURL: null, // 【可选】图片url（字符串类型）
            faceLibType: this.formData.faceLibType, // 【必填】人脸库类型（枚举：blackFD-名单库，staticFD-静态库）
            FDID: "1", // 【必填】人脸库ID（字符串类型，最大63字节）
            FPID: this.formData.employeeNo, // 【可选】人脸记录ID（字符串类型，最长63字节）
            deleteFP: this.formData.deleteFP, // 【可选】是否删除该人脸（布尔类型）
            modelData: null, // 【可选】目标模型数据（字符串类型，base64编码）
            PicFeaturePoint: null,
            faceType: this.formData.faceType, // 【可选】人脸类型（枚举：normalFace-普通人脸等）
            saveFacePic: this.formData.saveFacePic, // 【可选】是否保存人脸底图（布尔类型）
            leaderPermission: null, // 【可选】首次认证权限（数组类型，为空表示清空权限）
          };
          formData.append("faceInfo", JSON.stringify(faceInfo));
        }
        console.log("提交表单数据：", formData);
        this.$emit("submit", formData);
      });
    },
    async getDeviceInfo() {
      try {
        this.loading = true;
        const res = await request({
          url: "/device/list",
          method: "POST",
          data: { deviceType: "peopleDoor", paginate: false, enable: true },
        });
        if (res && res.status) {
          this.devices = Array.isArray(res.list) ? res.list : [];
          // - this.deviceId 有值 → 选它
          // - this.deviceId 为空 → 默认选第一个设备
          if (this.devices.length > 0) {
            this.$nextTick(() => {
              if (this.deviceId) {
                this.formData.deviceIds = [this.deviceId];
              } else {
                this.formData.deviceIds = [this.devices[0].id];
              }
            });
          }
        } else {
          this.devices = [];
        }
      } catch (err) {
        console.error("获取设备信息失败:", err);
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>

<style>
.el-dialog.custom-dialog {
  width: 1400px !important;
  background-color: #003c68 !important;
}

/* 防止主题样式覆盖 background */
.el-dialog.custom-dialog .el-dialog__body {
  background-color: #003c68 !important;
}

/* 设置对话框标题颜色为白色 */
.custom-dialog .el-dialog__title {
  color: white !important;
}

.user-form .el-form-item .el-input,
.user-form .el-form-item .el-input-number,
.user-form .el-form-item .el-select,
.user-form .el-form-item .el-date-picker {
  width: 250px; /* 统一右侧控件宽度 */
}
</style>
