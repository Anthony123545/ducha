<template>
  <el-dialog
    :visible.sync="localVisible"
    :title="formData.id ? '编辑名单' : '新增名单'"
    @close="handleCancel"
    custom-class="custom-dialog"
  >
    <el-form
      ref="editForm"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      label-position="right"
      class="nameList-form"
    >
      <!-- 基本信息 -->
      <div class="form-row">
        <el-form-item label="设备ID" prop="deviceId">
          <el-select v-model="formData.deviceId" placeholder="请选择设备">
            <el-option
              v-for="device in devices"
              :key="device.id"
              :label="device.name"
              :value="device.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="车牌号" prop="plateNumber">
          <el-input v-model="formData.plateNumber" placeholder="请输入车牌号" />
        </el-form-item>
      </div>

      <div class="form-row">
        <el-form-item label="事件类型" prop="eventType">
          <el-radio-group v-model="formData.eventType">
            <el-radio label="in">进</el-radio>
            <el-radio label="out">出</el-radio>
          </el-radio-group>
        </el-form-item>
      </div>

      <div class="form-row">
        <el-form-item label="抓拍时间" prop="captureTime">
          <el-date-picker
            v-model="formData.captureTime"
            type="datetime"
            placeholder="选择时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="图片地址">
          <el-input v-model="formData.photoUrl" placeholder="可输入或留空" />
        </el-form-item>
      </div>

      <div class="form-row">
        <el-form-item label="备注">
          <el-input
            v-model="formData.remark"
            type="textarea"
            placeholder="可选填"
          />
        </el-form-item>
      </div>
    </el-form>

    <span slot="footer" class="dialog-footer">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleSubmit">保存</el-button>
    </span>
  </el-dialog>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "NameListEditDialog",
  props: {
    visible: Boolean,
    accessRecordData: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      localVisible: this.visible, // ✅ 本地副本
      devices: [],
      loading: false,
      formData: {
        id: null,
        deviceId: null,
        plateNumber: "",
        eventType: "",
        photoUrl: "",
        captureTime: null,
        remark: "",
      },
      formRules: {
        plateNumber: [
          { required: true, message: "请输入车牌号", trigger: "blur" },
        ],
        eventType: [
          { required: true, message: "请选择事件类型", trigger: "change" },
        ],
        captureTime: [
          { required: true, message: "请选择抓拍时间", trigger: "change" },
        ],
        deviceId: [
          { required: true, message: "请选择设备", trigger: "change" },
        ],
      },
    };
  },
  watch: {
    visible(val) {
      this.localVisible = val; // ✅ 同步父组件的变化
    },
    localVisible(val) {
      this.$emit("update:visible", val); // ✅ 通知父组件更新
    },
    accessRecordData: {
      immediate: true,
      handler(newVal) {
        // console.log("newVal", newVal);
        if (!newVal || Object.keys(newVal).length === 0) {
          this.resetForm();
          console.log("新增名单", this.formData);
        } else {
          this.formData = {
            ...newVal,
          };
          console.log("编辑名单", this.formData);
        }
      },
    },
  },
  mounted() {
    this.getDeviceInfo();
  },
  methods: {
    parseJsonArray(field) {
      if (!field) return [];
      if (Array.isArray(field)) return field;
      try {
        return JSON.parse(field);
      } catch (e) {
        return [];
      }
    },
    resetForm() {
      this.formData = {
        id: null,
        deviceId: null,
        plateNumber: "",
        eventType: "",
        photoUrl: "",
        captureTime: null,
        remark: "",
      };
    },
    handleCancel() {
      this.localVisible = false; // ✅ 修改本地值，不触发 prop 报错
      this.$emit("cancel");
    },
    handleSubmit() {
      this.$refs.editForm.validate((valid) => {
        if (!valid) {
          this.$message.error("请检查表单填写项");
          return false;
        }
        // 提交时将数组转 JSON 字符串
        const data = {
          ...this.formData,
        };
        console.log("data", data);
        this.$emit("submit", data);
      });
    },
    async getDeviceInfo() {
      try {
        this.loading = true;
        const data = {
          deviceType: "carDoor",
          enable: true,
          paginate: false
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
          console.log("设备列表：", list);
          if (list.length > 0 && !this.formData.deviceId) {
            this.formData.deviceId = list[0].id;
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

<style>
.custom-dialog {
  background-color: #003c68;
  width: 800px !important;
}

/* 标签文字颜色深色背景下可读 */
.custom-dialog .el-dialog__title {
  color: #fff;
}

.custom-dialog .el-dialog__header {
  background-color: #003c68;
  color: #ffffff;
}

.custom-dialog .el-dialog__headerbtn {
  color: #ffffff;
}

.nameList-form {
  background-color: #003c68;
  padding: 15px 20px;
}

/* 两列布局 */
.form-row {
  display: flex;
  gap: 20px;
  margin-bottom: 10px;
}

/* 标签文字颜色深色背景下可读 */
.nameList-form .el-form-item__label {
  color: #fff;
}

/* 输入框文字颜色 */
.nameList-form .el-input__inner,
.nameList-form .el-select .el-input__inner,
.nameList-form .el-input-number__input {
  color: #000;
  background-color: #fff;
}

/* 修改输入框 placeholder 文字颜色为黑色 */
.nameList-form input::placeholder,
.nameList-form .el-input__inner::placeholder {
  color: #dbe1e7 !important;
  opacity: 1; /* 避免部分浏览器透明度影响 */
}

/* 针对 select 的 placeholder（实际上是 input） */
.nameList-form .el-select .el-input__inner::placeholder {
  color: #dbe1e7 !important;
}

/* 针对 el-input-number */
.nameList-form .el-input-number__input::placeholder {
  color: #dbe1e7 !important;
}

/* 对话框底部按钮右对齐 */
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
