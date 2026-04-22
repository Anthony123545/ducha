<template>
  <el-dialog
    :visible.sync="localVisible"
    :title="isEdit ? '编辑授权名单信息' : '新增授权名单信息'"
    @close="handleCancel"
    custom-class="custom-dialog"
  >
    <el-form
      ref="nameListForm"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      class="name-list-form"
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

      <el-divider content-position="center">车牌信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="车牌号码" prop="licensePlate">
            <el-input
              v-model="formData.licensePlate"
              :disabled="isEdit"
              placeholder="请输入车牌号码"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="名单类型" prop="type">
            <el-select v-model="formData.type" placeholder="请选择">
              <el-option
                v-for="(label, value) in listTypeMap"
                :key="value"
                :label="label"
                :value="parseInt(value)"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="有效开始时间" prop="accessStartTime">
            <el-date-picker
              v-model="formData.accessStartTime"
              type="datetime"
              placeholder="选择有效开始时间"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="有效结束时间" prop="accessEndTime">
            <el-date-picker
              v-model="formData.accessEndTime"
              type="datetime"
              placeholder="选择有效结束时间"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="车牌类型" prop="plateType">
            <el-select v-model="formData.plateType" placeholder="选择类型">
              <el-option
                v-for="(label, value) in plateTypeMap"
                :key="value"
                :label="label"
                :value="parseInt(value)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车牌颜色" prop="plateColor">
            <el-select
              v-model="formData.plateColor"
              placeholder="选择颜色"
              :disabled="isEdit"
            >
              <el-option
                v-for="(label, value) in plateColorMap"
                :key="value"
                :label="label"
                :value="parseInt(value)"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider content-position="center">其他信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="通道号" prop="channel">
            <el-input-number v-model="formData.channel" :min="1" :max="16" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="卡号" prop="cardNo">
            <el-input v-model="formData.cardNo" placeholder="请输入卡号" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <span slot="footer" class="dialog-footer">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading"
        >保存</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
import request from "@/utils/request";
import { listTypeMap, plateColorMap, plateTypeMap } from "./enums.js";

export default {
  name: "NameListEditDialog",
  props: {
    visible: Boolean,
    nameListData: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      localVisible: this.visible,
      devices: [],
      listTypeMap,
      plateColorMap,
      plateTypeMap,
      loading: false,
      isEdit: false,
      oldLicensePlate: null,
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
        licensePlate: [
          { required: true, message: "请输入车牌号", trigger: "blur" },
        ],
        type: [
          { required: true, message: "请选择名单类型", trigger: "change" },
        ],
        accessStartTime: [
          { required: true, message: "请选择开始时间", trigger: "change" },
        ],
        accessEndTime: [
          { required: true, message: "请选择结束时间", trigger: "change" },
          {
            validator: (rule, value, callback) => {
              if (!value) return callback();
              const start = this.formData.accessStartTime
                ? new Date(this.formData.accessStartTime)
                : null;
              const end = new Date(value);
              if (start && end <= start)
                callback(new Error("结束时间必须大于开始时间"));
              else callback();
            },
            trigger: "change",
          },
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
    nameListData: {
      immediate: true,
      handler(newVal) {
        if (newVal && Object.keys(newVal).length > 0) {
          // 编辑
          this.formData = {
            ...this.getDefaultFormData(),
            ...JSON.parse(JSON.stringify(newVal)),
          };
          this.oldLicensePlate = newVal.licensePlate;
          this.isEdit = true;
        } else {
          // 新增
          this.resetForm();
          this.oldLicensePlate = null;
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
      return {
        deviceIds: [],
        licensePlate: "",
        type: 0,
        accessStartTime: null,
        accessEndTime: null,
        cardNo: "",
        channel: 1,
        plateType: 0xff,
        plateColor: 0xff,
      };
    },
    resetForm() {
      this.formData = this.getDefaultFormData();
    },
    handleCancel() {
      this.localVisible = false;
      this.$emit("cancel");
    },
    formatDateTime(val) {
      return val ? new Date(val).toISOString() : null;
    },
    async handleSubmit() {
      this.$refs.nameListForm.validate(async (valid) => {
        if (!valid) {
          this.$message.error("请检查表单填写项");
          return;
        }
        const data = {
          ...this.formData,
          isEdit: this.isEdit,
          deviceIds: [...this.formData.deviceIds],
          accessStartTime: this.formatDateTime(this.formData.accessStartTime),
          accessEndTime: this.formatDateTime(this.formData.accessEndTime),
        };
        console.log("提交表单数据：", data);
        this.$emit("submit", data);
      });
    },
    async getDeviceInfo() {
      try {
        this.loading = true;
        const res = await request({
          url: "/device/list",
          method: "POST",
          data: { deviceType: "carDoor", paginate: false, enable: true },
        });
        if (res && res.status) {
          this.devices = Array.isArray(res.list) ? res.list : [];
          if (
            this.devices.length > 0 &&
            (!this.formData.deviceIds || this.formData.deviceIds.length === 0)
          ) {
            this.formData.deviceIds = [this.devices[0].id];
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
.custom-dialog {
  width: 850px !important;
  background-color: #003c68;
}
.custom-dialog .el-dialog__title {
  color: white !important;
}
.name-list-form .el-form-item .el-input,
.name-list-form .el-form-item .el-input-number,
.name-list-form .el-form-item .el-select,
.name-list-form .el-form-item .el-date-picker {
  width: 250px;
}
</style>
