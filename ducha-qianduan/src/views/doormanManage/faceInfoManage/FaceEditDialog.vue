<template>
  <el-dialog
    :visible.sync="localVisible"
    :title="isEdit ? '修改人脸' : '新增人脸'"
    :before-close="handleClose"
    custom-class="custom-dialog"
  >
    <el-form
      :model="form"
      :rules="rules"
      ref="userForm"
      label-width="150px"
      class="user-form"
    >
      <!-- 人脸URL -->
      <el-row :gutter="20">
        <el-col :span="12">
          <!--          <el-form-item label="人脸url" prop="faceURL">-->
          <!--            <el-input-->
          <!--              v-model="form.faceURL"-->
          <!--              :disabled="isEdit"-->
          <!--              placeholder="请输入人脸URL"-->
          <!--            />-->
          <!--          </el-form-item>-->
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
            >
              <img
                v-if="form.facePreview"
                :src="form.facePreview"
                class="avatar"
              />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="人脸库类型" prop="faceLibType">
            <el-select v-model="form.faceLibType" placeholder="请选择人员类型">
              <el-option label="名单库" value="blackFD" />
              <el-option label="静态库" value="staticFD" />
              <el-option label="非授权名单人" value="blackList" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 人脸库ID / 人脸记录ID -->
      <el-row :gutter="20" v-if="!isEdit">
        <el-col :span="12">
          <el-form-item label="人脸库ID" prop="FDID">
            <el-input v-model="form.FDID" placeholder="请输入人脸库ID" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="人脸记录ID" prop="FPID">
            <el-input v-model="form.FPID" placeholder="请输入人脸记录ID" />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 姓名 / 性别 / 出生日期 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="人脸图片对应的人员姓名" prop="name">
            <el-input v-model="form.name" placeholder="请输入姓名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="人脸图片对应的人员性别" prop="gender">
            <el-select v-model="form.gender">
              <el-option label="男" value="male" />
              <el-option label="女" value="female" />
              <el-option label="未知" value="unknown" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="人脸图片对应的人员出生日期" prop="bornTime">
            <el-date-picker
              v-model="form.bornTime"
              type="date"
              placeholder="请选择出生日期"
              value-format="yyyy-MM-dd"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="人脸图片对应的人员出生城市编号" prop="city">
            <el-input v-model="form.city" placeholder="请输入城市编号" />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 证件信息 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="证件类型" prop="certificateType">
            <el-select v-model="form.certificateType">
              <el-option label="身份证" value="ID" />
              <el-option label="其他" value="other" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="证件号" prop="certificateNumber">
            <el-input
              v-model="form.certificateNumber"
              placeholder="请输入证件号"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 备注 / 标签 / 地址 / 自定义信息 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="备注信息" prop="caseInfo">
            <el-input v-model="form.caseInfo" placeholder="请输入备注信息" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="自定义标签" prop="tag">
            <el-input v-model="form.tag" placeholder="请输入标签, 用逗号分隔" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="人员住址" prop="address">
            <el-input v-model="form.address" placeholder="请输入住址" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="自定义信息" prop="customInfo">
            <el-input
              v-model="form.customInfo"
              placeholder="请输入自定义信息"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 模型数据 / 保存人脸底图 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="目标模型数据" prop="modelData">
            <el-input v-model="form.modelData" placeholder="请输入模型数据" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="PBG对接" prop="transfer">
            <el-switch v-model="form.transfer" />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 操作类型 / 终端列表 / PBG对接 -->
      <el-row :gutter="20" v-if="!isEdit">
        <el-col :span="12">
          <el-form-item label="操作类型" prop="operateType">
            <el-input
              v-model="form.operateType"
              placeholder="byTerminal:按终端操作"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="终端ID列表" prop="terminalNoList">
            <el-input
              v-model="form.terminalNoList"
              placeholder="请输入终端ID列表"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="图片特征点坐标" prop="PicFeaturePoints">
            <el-button
              type="primary"
              style="margin-top: 10px"
              @click="addFeaturePoint"
            >
              添加特征点
            </el-button>
            <el-table
              :data="form.PicFeaturePoints"
              style="width: 100%"
              border
              size="small"
            >
              <el-table-column prop="featurePointType" label="特征点类型">
                <template #default="{ row }">
                  <el-select
                    v-model="row.featurePointType"
                    placeholder="请选择"
                  >
                    <el-option label="人脸" value="face" />
                    <el-option label="左眼" value="leftEye" />
                    <el-option label="右眼" value="rightEye" />
                    <el-option label="左嘴角" value="leftMouthCorner" />
                    <el-option label="右嘴角" value="rightMouthCorner" />
                    <el-option label="鼻子" value="nose" />
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="X">
                <template #default="{ row }">
                  <el-input-number
                    v-model="row.coordinatePoint.x"
                    :min="0"
                    :max="1000"
                  />
                </template>
              </el-table-column>
              <el-table-column label="Y">
                <template #default="{ row }">
                  <el-input-number
                    v-model="row.coordinatePoint.y"
                    :min="0"
                    :max="1000"
                  />
                </template>
              </el-table-column>
              <el-table-column label="宽度">
                <template #default="{ row }">
                  <el-input-number
                    v-model="row.coordinatePoint.width"
                    :min="0"
                    :max="1000"
                  />
                </template>
              </el-table-column>
              <el-table-column label="高度">
                <template #default="{ row }">
                  <el-input-number
                    v-model="row.coordinatePoint.height"
                    :min="0"
                    :max="1000"
                  />
                </template>
              </el-table-column>
              <el-table-column label="操作">
                <template #default="{ row }">
                  <el-button type="text" @click="removeFeaturePoint(row)"
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
          <el-form-item label="人脸类型" prop="faceType">
            <el-select v-model="form.faceType">
              <el-option label="普通人脸（默认）" value="normalFace" />
              <el-option label="巡更人脸" value="patrolFace" />
              <el-option label="胁迫人脸," value="hijackFace" />
              <el-option label="超级人脸," value="superFace" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="是否保存人脸底图" prop="saveFacePic">
            <el-switch v-model="form.saveFacePic" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="首次认证权限" prop="leaderPermission">
            <el-input
              v-model="form.leaderPermission"
              placeholder="请输入首次认证权限, 用逗号分隔"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取消</el-button>
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
    visible: { type: Boolean, default: false },
    faceData: Object,
    deviceId: {
      type: [Number, String],
      required: true,
    },
  },
  data() {
    return {
      localVisible: this.visible,
      isEdit: false,
      tempRightPlan: {
        doorNo: null,
        planTemplateNo: "",
      },
      fileList: [], // 上传列表
      form: {
        faceURL: "", // opt, string, 人脸url, 可选字段，填写人脸图片的URL
        faceLibType: "blackFD", // req, enum, 人脸库类型, 必填字段, 可选值: blackFD-名单库, staticFD-静态库, blackList-非授权名单人
        FDID: "", // req, string, 人脸库ID, 必填字段, 多个人脸库用逗号隔开, 最大长度63字节
        FPID: "", // opt, string, 人脸记录ID, 可选字段, 与非视频工号（人员ID）一致, 最大长度63字节
        name: "", // req, string, 姓名, 必填字段, 最大长度96字节
        gender: "male", // opt, enum, 性别, 可选字段, male-男, female-女, unknown-未知, 最大长度32
        bornTime: "", // req, string, 出生日期, 必填字段, 格式: yyyy-MM-dd, 最大长度20
        city: "", // opt, string, 出生城市编号, 可选字段, 最大长度32
        certificateType: "ID", // opt, enum, 证件类型, 可选字段, ID-身份证, other-其他, 最大长度10
        certificateNumber: "", // opt, string, 证件号, 可选字段, 最大长度32
        caseInfo: "", // opt, string, 备注信息, 可选字段, 最大长度192字节
        tag: "", // opt, string, 自定义标签, 可选字段, 逗号分隔, 每个标签最大48字节, 最多4个标签
        address: "", // opt, string, 住址, 可选字段, 最大长度192字节
        customInfo: "", // opt, string, 自定义信息, 可选字段, 最大长度192字节
        modelData: "", // opt, string, 目标模型数据, 可选字段, base64加密的非建模二进制数据
        transfer: false, // opt, bool, PBG对接, 可选字段, 是否进行转存操作
        operateType: "byTerminal", // opt, enum, 操作类型, 可选字段, byTerminal-按终端操作
        terminalNoList: [], // opt, array[int], 终端ID列表, type为byTerminal时必填
        saveFacePic: true, // opt, bool, 是否保存人脸底图
        leaderPermission: [], // opt, array[int], 首次认证权限, 取值范围1~4, 数组为空表示清空权限
        PicFeaturePoints: [], // 新增：初始化特征点数组
        faceType: "normalFace", // 新增：人脸类型默认值
        ValidTimes: ["", ""], // 初始化一个长度为2的数组，用于存储开始时间和结束时间
      },
      rules: {
        faceLibType: [
          { required: true, message: "请选择人脸库类型", trigger: "change" },
        ],
        FDID: [{ required: true, message: "请输入人脸库ID", trigger: "blur" }],
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        bornTime: [
          { required: true, message: "请选择出生日期", trigger: "change" },
        ],
      },
    };
  },
  watch: {
    visible(val) {
      this.localVisible = val;
    },
    localVisible(val) {
      this.$emit("update:visible", val);
    },
    faceData: {
      immediate: true,
      handler(val) {
        this.isEdit = true;
        if (val && Object.keys(val).length) {
          console.log("faceData is not null");
          this.form = Object.assign({}, this.form, val);
          // 额外保障：如果faceData中没有以下字段，手动初始化
          if (!this.form.ValidTimes) this.form.ValidTimes = ["", ""];
          if (!this.form.PicFeaturePoints) this.form.PicFeaturePoints = [];
          if (!this.form.faceType) this.form.faceType = "normalFace";
        } else {
          console.log("faceData is null");
          this.isEdit = false;
          // 重置表单
          this.form = {
            faceURL: "", // opt, string, 人脸url, 可选字段，填写人脸图片的URL
            faceLibType: "blackFD", // req, enum, 人脸库类型, 必填字段, 可选值: blackFD-名单库, staticFD-静态库, blackList-非授权名单人
            FDID: "", // req, string, 人脸库ID, 必填字段, 多个人脸库用逗号隔开, 最大长度63字节
            FPID: "", // opt, string, 人脸记录ID, 可选字段, 与非视频工号（人员ID）一致, 最大长度63字节
            name: "", // req, string, 姓名, 必填字段, 最大长度96字节
            gender: "male", // opt, enum, 性别, 可选字段, male-男, female-女, unknown-未知, 最大长度32
            bornTime: "", // req, string, 出生日期, 必填字段, 格式: yyyy-MM-dd, 最大长度20
            city: "", // opt, string, 出生城市编号, 可选字段, 最大长度32
            certificateType: "ID", // opt, enum, 证件类型, 可选字段, ID-身份证, other-其他, 最大长度10
            certificateNumber: "", // opt, string, 证件号, 可选字段, 最大长度32
            caseInfo: "", // opt, string, 备注信息, 可选字段, 最大长度192字节
            tag: "", // opt, string, 自定义标签, 可选字段, 逗号分隔, 每个标签最大48字节, 最多4个标签
            address: "", // opt, string, 住址, 可选字段, 最大长度192字节
            customInfo: "", // opt, string, 自定义信息, 可选字段, 最大长度192字节
            modelData: "", // opt, string, 目标模型数据, 可选字段, base64加密的非建模二进制数据
            transfer: false, // opt, bool, PBG对接, 可选字段, 是否进行转存操作
            operateType: "byTerminal", // opt, enum, 操作类型, 可选字段, byTerminal-按终端操作
            terminalNoList: [], // opt, array[int], 终端ID列表, type为byTerminal时必填
            saveFacePic: true, // opt, bool, 是否保存人脸底图
            leaderPermission: [], // opt, array[int], 首次认证权限, 取值范围1~4, 数组为空表示清空权限
            PicFeaturePoints: [], // 保留初始化
            faceType: "normalFace", // 保留初始化
            ValidTimes: ["", ""], // 保留初始化
          };
        }
      },
    },
  },
  methods: {
    addFeaturePoint() {
      // 向特征点数组添加一条默认数据
      this.form.PicFeaturePoints.push({
        featurePointType: "face", // 默认人脸类型
        coordinatePoint: {
          // 默认坐标信息
          x: 0,
          y: 0,
          width: 0,
          height: 0,
        },
      });
    },
    // 上传前校验
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
    // 文件选中后
    handleFileChange(file, fileList) {
      // 保存原始文件对象
      this.form.faceFile = file.raw;
      this.fileList = fileList;

      // 生成本地预览
      const reader = new FileReader();
      reader.onload = (e) => {
        this.form.modelData = {
          file: file.raw,
          preview: e.target.result,
        };
      };
      reader.readAsDataURL(file.raw);
    },
    // 删除图片
    handleRemove(file, fileList) {
      this.form.faceFile = null;
      this.form.facePreview = "";
      this.fileList = fileList;
    },
    // 上传超出数量（覆盖旧图）
    handleExceed(files, fileList) {
      this.$message.warning("只能上传一张图片，将替换旧图片");
      this.fileList = [];
      this.form.faceFile = null;
      this.form.facePreview = "";
    },
    addRightPlan() {
      if (!this.tempRightPlan.doorNo || !this.tempRightPlan.planTemplateNo) {
        this.$message.warning("请填写门编号和计划模板编号");
        return;
      }
      // 深拷贝一份到数组
      this.form.RightPlan.push({ ...this.tempRightPlan });
      // 清空临时表单
      this.tempRightPlan.doorNo = null;
      this.tempRightPlan.planTemplateNo = "";
    },
    removeRightPlan(row) {
      const index = this.form.RightPlan.indexOf(row);
      if (index > -1) this.form.RightPlan.splice(index, 1);
    },
    handleClose() {
      this.$emit("update:visible", false);
    },
    async handleSubmit() {
      this.$refs.userForm.validate(async (valid) => {
        if (!valid) {
          this.$message.error("请完善必填信息！");
          return;
        }
        // 处理 modelData：剥离 Data URL 前缀，只保留纯Base64
        let pureBase64 = "";
        if (this.form.facePreview) {
          // 判断是否包含前缀，有则截取前缀后面的内容
          pureBase64 = this.form.facePreview.includes("base64,")
            ? this.form.facePreview.split("base64,")[1]
            : this.form.facePreview;
        }
        const data = {
          modelData: pureBase64, // base64字符串
          faceLibType: this.form.faceLibType,
          deviceId: this.deviceId,
          employeeNo: this.form.employeeNo,
          name: this.form.name,
          userType: this.form.userType,
          gender: this.form.gender,
          Valid: {
            enable: true,
            beginTime: this.form.ValidTimes[0] + "+08:00",
            endTime: this.form.ValidTimes[1] + "+08:00",
            timeType: "local",
          },
          roomNumber: this.form.roomNumber,
          floorNumber: this.form.floorNumber,
          doorRight: this.form.doorRight,
          userVerifyMode: this.form.userVerifyMode,
          dynamicCode: this.form.dynamicCode,
          belongGroup: this.form.belongGroup,
          checkUser: true,
          operateType: "byTerminal",
          terminalNoList: this.form.terminalNoList,
        };
        console.log("data", data);
        this.$emit("submit", data);
      });
    },
  },
};
</script>
<style>
.custom-dialog {
  background-color: #003c68;
  width: 1200px !important;
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
