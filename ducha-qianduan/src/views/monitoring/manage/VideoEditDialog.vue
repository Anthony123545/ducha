<template>
  <el-dialog
    :visible.sync="localVisible"
    :title="isEdit ? '编辑视频信息' : '新增视频信息'"
    @close="handleCancel"
    custom-class="custom-dialog"
  >
    <el-form
      ref="videoForm"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      class="video-form"
    >
      <!-- 视频名称 + 设备类型 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="视频名称" prop="name">
            <el-input v-model="formData.name" placeholder="请输入视频名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="设备类型" prop="deviceType">
            <el-select v-model="formData.deviceType" placeholder="选择设备类型">
              <el-option
                v-for="item in deviceTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- IP 地址 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="设备IP" prop="ip">
            <el-input v-model="formData.ip" placeholder="请输入设备 IP 地址" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="登录端口" prop="loginPort">
            <el-input-number v-model.number="formData.port" :min="1" />
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 用户名 / 密码 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="formData.username" placeholder="请输入用户名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="formData.password"
              type="password"
              show-password
              placeholder="请输入密码"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 通道号 / 通道名称 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="通道名称" prop="channelName">
            <el-input
              v-model="formData.channelName"
              placeholder="请输入通道名称"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="通道号" prop="channel">
            <el-input-number v-model="formData.channel" :min="1" />
          </el-form-item>
        </el-col>
      </el-row>
      <!-- RTSP端口 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="RTSP端口" prop="rtspPort">
            <el-input-number v-model.number="formData.rtspPort" :min="1" />
          </el-form-item>
        </el-col>
      </el-row>
      <!--  码流类型 / 码流号 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="码流类型" prop="streamType">
            <el-select
              v-model="formData.streamType"
              placeholder="请选择码流类型"
              @change="onStreamTypeChange"
            >
              <el-option label="主码流" :value="1" />
              <el-option label="子码流" :value="2" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="码流号" prop="streamCode">
            <el-input
              v-model="formData.streamCode"
              placeholder="例如 01 或 02"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <!-- RTSP地址 -->
      <el-form-item label="RTSP地址" prop="rtspUrl" class="rtsp-item">
        <el-input
          v-model="formData.rtspUrl"
          placeholder="自动生成RTSP地址"
          @input="onRtspManualEdit"
        />
        <el-button
          v-if="rtspManuallyEdited"
          type="text"
          @click="resetRtspUrl"
          size="small"
        >
          重置为自动生成
        </el-button>
      </el-form-item>
      <!-- 启用 -->
      <el-row>
        <el-col :span="12">
          <el-form-item label="启用" prop="enable">
            <el-switch
              v-model="formData.enable"
              :active-value="true"
              :inactive-value="false"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleSubmit">保存</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: "VideoEditDialog",
  props: {
    visible: Boolean,
    videoData: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      localVisible: this.visible,
      isEdit: false,
      rtspManuallyEdited: false,
      deviceTypeOptions: [
        { label: "普通摄像头", value: 1 },
        { label: "人员通道闸机摄像头", value: 2 },
        { label: "车辆通道闸机摄像头", value: 3 },
      ],
      formData: this.getEmptyForm(),
      formRules: {
        name: [
          {
            required: true,
            message: "请输入视频名称",
            trigger: ["blur", "change"],
          },
          {
            min: 1,
            max: 50,
            message: "视频名称长度应在1-50个字符之间",
            trigger: ["blur", "change"],
          },
        ],
        deviceType: [
          { required: true, message: "请选择设备类型", trigger: "change" },
        ],
        ip: [
          { required: true, message: "请输入设备 IP 地址", trigger: "blur" },
          {
            pattern:
              /^(25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)){3}$/,
            message: "请输入有效的 IPv4 地址，例如 192.168.1.100",
            trigger: "blur",
          },
        ],
        port: [
          { required: true, message: "请输入登录端口号", trigger: "blur" },
          {
            type: "number",
            min: 1,
            max: 65535,
            message: "端口号应为 1-65535",
            trigger: "blur",
          },
        ],
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { max: 50, message: "用户名长度不能超过50个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { max: 50, message: "密码长度不能超过50个字符", trigger: "blur" },
        ],
        rtspPort: [
          { required: true, message: "请输入 RTSP 端口号", trigger: "blur" },
          {
            type: "number",
            min: 1,
            max: 65535,
            message: "RTSP 端口号应为 1-65535",
            trigger: "blur",
          },
        ],
        channel: [
          { required: true, message: "请输入通道号", trigger: "blur" },
          {
            type: "number",
            min: 1,
            max: 999,
            message: "通道号必须在1-999之间",
            trigger: "blur",
          },
        ],
        streamType: [
          { required: true, message: "请选择码流类型", trigger: "change" },
        ],
        streamCode: [
          { required: true, message: "请输入码流号", trigger: "blur" },
          {
            pattern: /^[0-9]{2}$/,
            message: "码流号应为两位数字，例如 01 或 02",
            trigger: "blur",
          },
        ],
        rtspUrl: [
          { required: true, message: "RTSP 地址不能为空", trigger: "blur" },
          {
            pattern:
              /^rtsp:\/\/(?:[\w\.-]+(?::[\w\.-]*)?@)?[\w\.-]+(?::\d+)?\/.*$/,
            message: "请输入有效的 RTSP 地址，以 rtsp:// 开头",
            trigger: "blur",
          },
        ],
        enable: [
          { required: true, message: "请选择是否启用", trigger: "change" },
        ],
      },
    };
  },
  // 计算属性用于生成RTSP地址
  computed: {
    computedRtspUrl() {
      if (
        this.rtspManuallyEdited ||
        !this.formData.ip ||
        !this.formData.rtspPort ||
        !this.formData.channel
      ) {
        return this.formData.rtspUrl;
      }

      const f = this.formData;
      const auth = f.username
        ? f.password
          ? `${f.username}:${f.password}@`
          : `${f.username}@`
        : "";
      const streamCode = f.streamCode || "01";
      return `rtsp://${auth}${f.ip}:${f.rtspPort}/Streaming/Channels/${f.channel}${streamCode}`;
    },
  },
  watch: {
    visible: {
      handler: function (val) {
        this.localVisible = val;
      },
      immediate: true,
    },
    localVisible(val) {
      this.$emit("update:visible", val);
    },
    videoData: {
      immediate: true,
      handler(newVal) {
        try {
          // 判断是编辑还是新增
          this.isEdit = !!(newVal && Object.keys(newVal).length);
          // 用空表单 + videoData 覆盖，保证字段完整
          Object.assign(this.formData, this.getEmptyForm(), newVal || {});
          // 初始化时重置手动编辑状态
          this.rtspManuallyEdited = false;
          // 确保deviceType是数字类型
          this.formData.deviceType = Number(this.formData.deviceType);
          // 码流号和码流类型保持一致
          if (
            this.formData.streamType === 2 &&
            this.formData.streamCode === "01"
          ) {
            this.formData.streamCode = "02";
          }
        } catch (error) {
          console.error("处理视频数据时出错:", error);
          this.$message.error("数据加载失败，请重试");
        }
      },
    },
    // 监听计算属性变化，更新表单中的RTSP地址
    computedRtspUrl: {
      handler: function (newUrl) {
        if (!this.rtspManuallyEdited && newUrl !== this.formData.rtspUrl) {
          this.$set(this.formData, "rtspUrl", newUrl);
        }
      },
      immediate: true,
    },
  },
  methods: {
    onRtspManualEdit() {
      this.rtspManuallyEdited = true;
    },
    /** 重置RTSP地址为自动生成模式 */
    resetRtspUrl() {
      this.rtspManuallyEdited = false;
      // 强制更新RTSP地址
      this.$set(this.formData, "rtspUrl", this.computedRtspUrl);
    },
    /** 码流类型切换时自动更新码流号 */
    onStreamTypeChange(val) {
      const newStreamCode = val === 2 ? "02" : "01";
      if (this.formData.streamCode !== newStreamCode) {
        this.$set(this.formData, "streamCode", newStreamCode);

        // 如果已经手动编辑过RTSP地址，询问是否重新生成
        if (this.rtspManuallyEdited) {
          this.$confirm(
            "修改码流类型会影响RTSP地址，是否重新生成RTSP地址？",
            "提示",
            {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            },
          )
            .then(() => {
              this.resetRtspUrl();
            })
            .catch(() => {
              // 用户取消，保持手动编辑状态
            });
        } else {
          // 重置手动编辑状态，允许计算属性更新RTSP地址
          this.rtspManuallyEdited = false;
        }
      }
    },
    /** 取消操作 */
    handleCancel() {
      try {
        // 重置表单
        if (this.$refs.videoForm) {
          this.$refs.videoForm.resetFields();
        }
        this.$emit("cancel");
      } catch (error) {
        console.error("取消操作时出错:", error);
      }
    },
    /** 提交表单 */
    handleSubmit() {
      this.$refs.videoForm.validate((valid) => {
        if (!valid) {
          this.$message.error("请检查表单填写项");
          return;
        }
        try {
          // 深度克隆formData，避免引用问题
          const formData = JSON.parse(JSON.stringify(this.formData));
          this.$emit("submit", formData);
        } catch (error) {
          console.error("提交表单时出错:", error);
          this.$message.error("提交失败，请重试");
        }
      });
    },
    /** 获取空表单对象 */
    getEmptyForm() {
      return {
        id: null, // 视频记录的唯一ID（新增时为空）
        name: "", // 视频名称
        deviceType: 1, // 设备类型，默认是网络摄像机 (IPC)
        ip: "", // 设备IP地址
        port: 8000, // 登录端口号（HTTP默认80，HTTPS默认443）
        username: "", // 登录设备的用户名
        password: "", // 登录设备的密码
        channel: 1, // 通道号（默认第1通道）
        channelName: "", // 通道名称，可选（例如“前门摄像头”）
        streamType: 1, // 码流类型 1=主码流, 2=子码流
        streamCode: "01", // 码流号，两位字符串，01=主码流, 02=子码流
        rtspPort: 554, // RTSP 视频流端口，默认 554
        rtspUrl: "", // 自动生成的 RTSP 地址
        enable: true, // 是否启用该视频，true=启用, false=禁用
      };
    },
  },
  // 组件销毁时清理资源
  beforeDestroy() {
    // 清理可能的内存泄漏
    if (this.$refs.videoForm) {
      this.$refs.videoForm.resetFields();
    }
  },
};
</script>

<style>
.custom-dialog {
  background-color: #003c68;
  width: 800px !important;
}
/* 设置对话框标题颜色为白色 */
.custom-dialog .el-dialog__title {
  color: white !important;
}

.video-form .el-form-item .el-input,
.video-form .el-form-item .el-input-number,
.video-form .el-form-item .el-select {
  width: 200px;
}
.video-form .el-form-item .el-date-editor {
  width: 300px; /* 统一右侧控件宽度 */
}
.video-form .rtsp-item .el-input {
  width: 90% !important;
}
</style>
