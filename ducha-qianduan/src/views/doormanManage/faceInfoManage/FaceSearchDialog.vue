<template>
  <el-dialog
    title="查询人脸信息条件"
    :visible.sync="localVisible"
    :before-close="handleCancel"
    custom-class="custom-dialog"
  >
    <el-form
      ref="searchForm"
      :model="formData"
      :rules="formRules"
      label-width="180px"
      class="face-form"
    >
      <!-- 查询控制 -->
      <el-divider></el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="查询结果在结果列表中的起始位置">
            <el-input-number v-model="formData.searchResultPosition" :min="0" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="本次查询(每页显示)最大记录数">
            <el-input-number
              v-model="formData.maxResults"
              :min="1"
              :max="1000"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider></el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="人脸比对库唯一标示ID">
            <el-input v-model="formData.FDID" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="人脸库类型">
            <el-select v-model="formData.faceLibType" placeholder="可不填">
              <el-option label="名单库" value="blackFD" />
              <el-option label="静态库" value="staticFD" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="人脸库人员出生开始时间">
            <el-input v-model="formData.startTime" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="人脸库人员出生结束时间">
            <el-input v-model="formData.endTime" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别">
            <el-select v-model="formData.sex" placeholder="请选择">
              <el-option label="男" value="male" />
              <el-option label="女" value="female" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="出生日期">
            <el-date-picker
              v-model="formData.bornTime"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="省份代码">
            <el-input v-model="formData.province" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="城市代码">
            <el-input v-model="formData.city" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="证件类型">
            <el-input v-model="formData.certificateType" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="证件号">
            <el-input v-model="formData.certificateNumber" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="电话号码">
            <el-input v-model="formData.phoneNumber" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider></el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="建模状态 (旧)">
            <el-select v-model="formData.modelStatus" placeholder="请选择">
              <el-option label="未建模" value="0" />
              <el-option label="建模中" value="1" />
              <el-option label="已建模" value="2" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="建模状态 (新)">
            <el-input
              v-model="formData.modelingStatus"
              placeholder="如: modeling/finished"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider></el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="人脸模式">
            <el-input v-model="formData.FaceModeList" placeholder="模式数据" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="相似度">
            <el-input-number
              v-model="formData.similarity"
              :min="0"
              :max="100"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="最小评分">
            <el-input-number v-model="formData.faceScore" :min="0" :max="100" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最大评分">
            <el-input-number
              v-model="formData.faceScoreMax"
              :min="0"
              :max="100"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider></el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="排序字段">
            <el-select v-model="formData.sortord" placeholder="请选择排序字段">
              <el-option label="importTime" value="importTime" />
              <el-option label="similarity" value="similarity" />
              <el-option label="faceScore" value="faceScore" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="排序方式">
            <el-select v-model="formData.order">
              <el-option label="升序" value="ascend" />
              <el-option label="降序" value="descend" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider></el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="出现时间范围">
            <el-date-picker
              v-model="formData.appearTimeRange"
              type="datetimerange"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              value-format="yyyy-MM-dd HH:mm:ss"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入库时间范围">
            <el-date-picker
              v-model="formData.importTimeRange"
              type="datetimerange"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              value-format="yyyy-MM-dd HH:mm:ss"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider></el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="频次是否返回">
            <el-switch
              v-model="formData.OccurrencesInfo.enabled"
              active-text="是"
              inactive-text="否"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="出现频次">
            <el-input-number
              v-model="formData.OccurrencesInfo.occurrences"
              :min="0"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="频次搜索类型">
            <el-select
              v-model="formData.OccurrencesInfo.occurrencesSearchType"
              placeholder="选择类型"
            >
              <el-option label="大于" value="greater" />
              <el-option label="小于" value="less" />
              <el-option label="等于" value="equal" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 底部操作 -->
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleSubmit">确定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "FaceSearchDialog",
  props: {
    visible: Boolean,
    faceData: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      localVisible: this.visible,
      formData: {
        searchID: "",
        searchResultPosition: 0,
        maxResults: 100,
        FDID: "",
        FPID: "",
        faceLibType: "",
        name: "",
        sex: "",
        bornTime: "",
        province: "",
        city: "",
        certificateType: "",
        certificateNumber: "",
        phoneNumber: "",
        modelStatus: "",
        modelingStatus: "",
        FaceModeList: "",
        similarity: 0,
        faceScore: 0,
        faceScoreMax: 100,
        sortord: "importTime",
        order: "descend",
        appearTimeRange: [],
        importTimeRange: [],
        OccurrencesInfo: {
          enabled: false,
          occurrences: 0,
          occurrencesSearchType: "",
        },
        occurrences: 0,
        occurrencesSearchType: "",
      },
      formRules: {
        faceLibType: [
          { required: true, message: "请选择人脸库类型", trigger: "change" },
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
    userData: {
      immediate: true,
      handler(newVal) {
        this.formData = {
          ...this.resetForm(),
          ...newVal,
        };
        console.log("查询条件 this.formData", this.formData);
      },
    },
  },
  methods: {
    resetForm() {
      this.formData = {
        searchID: "",
        searchResultPosition: 0,
        maxResults: 100,
        FDID: "",
        FPID: "",
        faceLibType: "", // ← 新增
        name: "",
        sex: "",
        bornTime: "",
        province: "",
        city: "",
        certificateType: "",
        certificateNumber: "",
        phoneNumber: "",
        modelStatus: "",
        modelingStatus: "",
        FaceModeList: "",
        similarity: 0,
        faceScore: 0,
        faceScoreMax: 100,
        sortord: "importTime",
        order: "descend",
        appearTimeRange: [],
        importTimeRange: [],
        OccurrencesInfo: {
          enabled: false,
          occurrences: 0,
          occurrencesSearchType: "",
        },
        occurrences: 0,
        occurrencesSearchType: "",
      };
    },
    handleCancel() {
      this.$emit("cancel");
    },
    handleSubmit() {
      this.$refs.searchForm.validate((valid) => {
        if (!valid) {
          this.$message.error("请检查表单填写项");
          return false;
        }
        const data = {
          ...this.formData,
        };
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

.face-form .el-form-item .el-input,
.face-form .el-form-item .el-input-number,
.face-form .el-form-item .el-select,
.face-form .el-form-item .el-date-picker {
  width: 250px; /* 统一右侧控件宽度 */
}
</style>
