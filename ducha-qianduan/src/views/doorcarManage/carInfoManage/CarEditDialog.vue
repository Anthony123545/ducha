<template>
  <el-dialog
    :visible.sync="localVisible"
    :title="isEdit ? '修改车辆信息' : '新增车辆信息'"
    @close="handleCancel"
    custom-class="custom-dialog"
  >
    <el-form
      ref="carForm"
      :model="formData"
      :rules="formRules"
      label-width="150px"
      class="car-form"
    >
      <!-- 一、车牌信息 -->
      <el-divider content-position="center">车牌信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="车牌号码" prop="plateNumber">
            <el-input
              v-model="formData.plateNumber"
              placeholder="请输入车牌号码"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车牌颜色" prop="plateColor">
            <el-select
              v-model="formData.plateColor"
              placeholder="请选择车牌颜色"
              filterable
              clearable
              class="full-width-select"
            >
              <el-option label="蓝色" value="blue" />
              <el-option label="黄色" value="yellow" />
              <el-option label="绿色" value="green" />
              <el-option label="白色" value="white" />
              <el-option label="黑色" value="black" />
              <el-option label="红色" value="red" />
              <el-option label="灰色" value="gray" />
              <el-option label="橙色" value="orange" />
              <el-option label="棕色" value="brown" />
              <el-option label="粉色" value="pink" />
              <el-option label="紫色" value="purple" />
              <el-option label="其他" value="other" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="车牌类型" prop="plateType">
            <el-select
              v-model="formData.plateType"
              placeholder="请选择车牌类型"
              filterable
              clearable
              class="full-width-select"
            >
              <el-option label="普通车牌" value="normal" />
              <el-option label="临时车牌" value="temporary" />
              <el-option label="新能源车牌" value="newEnergy" />
              <el-option label="军车牌" value="military" />
              <el-option label="警车牌" value="police" />
              <el-option label="教练车牌" value="coach" />
              <el-option label="挂车牌" value="trailer" />
              <el-option label="领馆车牌" value="consulate" />
              <el-option label="港澳车牌" value="hkMacao" />
              <el-option label="应急车牌" value="emergency" />
              <el-option label="其他" value="other" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 二、车辆信息 -->
      <el-divider content-position="center">车辆信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="车辆类型" prop="vehicleType">
            <el-select
              v-model="formData.vehicleType"
              placeholder="请选择车辆类型"
              filterable
              clearable
              class="full-width-select"
            >
              <el-option label="小型车" value="small" />
              <el-option label="大型车" value="large" />
              <el-option label="中型车" value="medium" />
              <el-option label="电动车" value="electric" />
              <el-option label="摩托车" value="motorcycle" />
              <el-option label="货车" value="truck" />
              <el-option label="客车" value="bus" />
              <el-option label="特种车辆" value="special" />
              <el-option label="其他" value="other" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车辆颜色" prop="vehicleColor">
            <el-select
              v-model="formData.vehicleColor"
              placeholder="请选择或输入车辆颜色"
              filterable
              allow-create
              default-first-option
              clearable
              class="full-width-select"
            >
              <el-option label="白色" value="white" />
              <el-option label="黑色" value="black" />
              <el-option label="银色" value="silver" />
              <el-option label="灰色" value="gray" />
              <el-option label="红色" value="red" />
              <el-option label="蓝色" value="blue" />
              <el-option label="黄色" value="yellow" />
              <el-option label="绿色" value="green" />
              <el-option label="橙色" value="orange" />
              <el-option label="棕色" value="brown" />
              <el-option label="紫色" value="purple" />
              <el-option label="粉色" value="pink" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="车辆品牌" prop="vehicleBrand">
            <el-select
              v-model="formData.vehicleBrand"
              placeholder="请选择或输入品牌"
              filterable
              allow-create
              clearable
              default-first-option
              class="full-width-select"
            >
              <el-option label="丰田" value="Toyota" />
              <el-option label="本田" value="Honda" />
              <el-option label="日产" value="Nissan" />
              <el-option label="大众" value="Volkswagen" />
              <el-option label="宝马" value="BMW" />
              <el-option label="奔驰" value="Mercedes-Benz" />
              <el-option label="奥迪" value="Audi" />
              <el-option label="比亚迪" value="BYD" />
              <el-option label="特斯拉" value="Tesla" />
              <el-option label="福特" value="Ford" />
              <el-option label="长安" value="Changan" />
              <el-option label="吉利" value="Geely" />
              <el-option label="五菱" value="Wuling" />
              <el-option label="其他" value="other" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车辆排量" prop="displacement">
            <el-input
              v-model="formData.displacement"
              placeholder="请输入排量（例如：1.5T / 2.0L）"
              clearable
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="发动机号" prop="engineNo">
            <el-input
              v-model="formData.engineNo"
              placeholder="请输入发动机号"
              clearable
              maxlength="30"
              show-word-limit
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车架号" prop="vin">
            <el-input
              v-model="formData.vin"
              placeholder="请输入车架号（17位字母或数字）"
              clearable
              maxlength="17"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="排放量号" prop="emissionNo">
            <el-input
              v-model="formData.emissionNo"
              placeholder="请输入排放量号"
              clearable
              maxlength="20"
              show-word-limit
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="识别代码" prop="identifyCode">
            <el-input
              v-model="formData.identifyCode"
              placeholder="请输入识别代码"
              clearable
              maxlength="20"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="车队名称" prop="fleetName">
            <el-input
              v-model="formData.fleetName"
              placeholder="请输入车队名称"
              clearable
              maxlength="50"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 三、卡片信息 -->
      <el-divider content-position="center">卡片信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="发卡人员" prop="issuerName">
            <el-input
              v-model="formData.issuerName"
              placeholder="请输入发卡人员"
              clearable
              maxlength="30"
              show-word-limit
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发卡器类型" prop="issuerDeviceType">
            <el-input
              v-model="formData.issuerDeviceType"
              placeholder="请输入发卡器类型"
              clearable
              maxlength="30"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="停车类型" prop="parkingType">
            <el-select
              v-model="formData.parkingType"
              placeholder="请选择停车类型"
              filterable
              clearable
              class="full-width-select"
            >
              <el-option label="临时车" value="temp" />
              <el-option label="月租车" value="monthly" />
              <el-option label="固定车位" value="fixed" />
              <el-option label="临时/访客车" value="visitor" />
              <el-option label="员工车" value="employee" />
              <el-option label="其他" value="other" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="包时信息" prop="packageInfo">
            <el-input
              v-model="formData.packageInfo"
              placeholder="请输入包时信息"
              clearable
              maxlength="50"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 四、车主信息 -->
      <el-divider content-position="center">车主信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="车主姓名" prop="ownerName">
            <el-input
              v-model="formData.ownerName"
              placeholder="请输入车主姓名"
              clearable
              maxlength="30"
              show-word-limit
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号码" prop="phoneNumber">
            <el-input
              v-model="formData.phoneNumber"
              placeholder="请输入手机号码"
              clearable
              maxlength="11"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="证件号码" prop="certificateNo">
            <el-input
              v-model="formData.certificateNo"
              placeholder="请输入证件号码"
              clearable
              maxlength="20"
              show-word-limit
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="住址" prop="address">
            <el-input
              v-model="formData.address"
              placeholder="请输入住址"
              clearable
              maxlength="100"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="职位" prop="position">
            <el-input
              v-model="formData.position"
              placeholder="请输入职位"
              clearable
              maxlength="30"
              show-word-limit
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="所属公司" prop="company">
            <el-input
              v-model="formData.company"
              placeholder="请输入公司名称"
              clearable
              maxlength="50"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="所在部门" prop="departmentName">
            <el-input
              v-model="formData.departmentName"
              placeholder="请输入部门名称"
              clearable
              maxlength="50"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 五、分组与权限信息 -->
      <el-divider content-position="center">分组与权限信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="分组名称" prop="groupName">
            <el-input
              v-model="formData.groupName"
              placeholder="请输入分组名称"
              clearable
              maxlength="30"
              show-word-limit
            />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="车辆群组" prop="vehicleGroup">
            <el-input
              v-model="formData.vehicleGroup"
              placeholder="请输入车辆群组"
              clearable
              maxlength="30"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="场库权限" prop="parkingPermission">
            <el-select
              v-model="formData.parkingPermission"
              multiple
              placeholder="请选择场库"
              filterable
              clearable
              class="full-width-select"
            >
              <el-option
                v-for="item in parkingList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="场库名称" prop="parkingName">
            <el-input
              v-model="formData.parkingName"
              placeholder="请输入场库名称"
              clearable
              maxlength="50"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="每日有效时段" prop="dailyValidTime">
            <el-time-picker
              is-range
              v-model="formData.dailyValidTime"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              format="HH:mm"
              clearable
              class="full-width-picker"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="历史有效期" prop="historyValidTime">
            <el-date-picker
              v-model="formData.historyValidTime"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
              clearable
              class="full-width-picker"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 六、其他信息 -->
      <el-divider content-position="center">其他信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="数据版本" prop="dataVersion">
            <el-input
              v-model="formData.dataVersion"
              placeholder="请输入版本号"
              clearable
              maxlength="20"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="备注" prop="remarks">
            <el-input
              v-model="formData.remarks"
              type="textarea"
              rows="3"
              placeholder="请输入备注"
              clearable
              maxlength="200"
              show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleSubmit">保存</el-button>
    </div>
  </el-dialog>
</template>

<script>

export default {
  name: "CarEditDialog",
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    carData: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      localVisible: this.visible,
      isEdit: false,
      parkingList: [
        { id: 1, name: "A区停车场" },
        { id: 2, name: "B区停车场" },
      ],
      tempRightPlan: {
        doorNo: null,
        planTemplateNo: "",
      },
      formData: {
        plateNumber: "",
        plateColor: "",
        plateType: "",
        vehicleType: "",
        vehicleColor: "",
        vehicleBrand: "",
        engineNo: "",
        emissionNo: "",
        vin: "",
        displacement: "",
        identifyCode: "",
        fleetName: "",
        issuerName: "",
        issuerDeviceType: "",
        parkingType: "",
        packageInfo: "",
        ownerName: "",
        phoneNumber: "",
        certificateNo: "",
        address: "",
        position: "",
        company: "",
        departmentName: "",
        groupName: "",
        vehicleGroup: "",
        parkingPermission: [],
        parkingName: "",
        dailyValidTime: [],
        historyValidTime: [],
        remarks: "",
        dataVersion: "",
      },
      formRules: {
        plateNumber: [{ required: true, message: "请输入车牌号码", trigger: "blur" }],
        plateColor: [{ required: true, message: "请选择车牌颜色", trigger: "change" }],
        plateType: [{ required: true, message: "请选择车牌类型", trigger: "change" }],
        vehicleType: [{ required: true, message: "请选择车辆类型", trigger: "change" }],
        ownerName: [{ required: true, message: "请输入车主姓名", trigger: "blur" }],
        phoneNumber: [
          { required: true, message: "请输入手机号码", trigger: "blur" },
          { pattern: /^1[3-9]\d{9}$/, message: "请输入正确的手机号", trigger: "blur" },
        ],
      },
    };
  },
  watch: {
    visible(val) {
      this.localVisible = val;
    },
    localVisible(val) {
      this.$emit('update:visible', val)
    },
    carData: {
      immediate: true,
      handler(newVal) {
        this.isEdit = newVal && Object.keys(newVal).length > 0;
        this.formData = {
          ...this.getEmptyForm(),
          ...(this.isEdit ? newVal : {})
        };
        console.log(this.isEdit ? '修改this.formData' : '新增this.formData', this.formData);
      }
    },
  },
  methods: {
    handleCancel() {
      this.localVisible = false; // ✅ 修改本地值，不触发 prop 报错
      this.$emit("cancel");
    },
    handleSubmit() {
      this.$refs.carForm.validate((valid) => {
        if (!valid) {
          this.$message.error("请检查表单填写项");
          return;
        }
        this.$emit("submit", { ...this.formData });
      });
    },
    getEmptyForm() {
      return {
        plateNumber: "",
        plateColor: "",
        plateType: "",
        vehicleType: "",
        vehicleColor: "",
        vehicleBrand: "",
        engineNo: "",
        emissionNo: "",
        vin: "",
        displacement: "",
        identifyCode: "",
        fleetName: "",
        issuerName: "",
        issuerDeviceType: "",
        parkingType: "",
        packageInfo: "",
        ownerName: "",
        phoneNumber: "",
        certificateNo: "",
        address: "",
        position: "",
        company: "",
        departmentName: "",
        groupName: "",
        vehicleGroup: "",
        parkingPermission: [],
        parkingName: "",
        dailyValidTime: [],
        historyValidTime: [],
        remarks: "",
        dataVersion: "",
      };
    }
  },
};
</script>
<style>
.custom-dialog {
  width: 1200px !important;
  background-color: #003c68;
}
/* 设置对话框标题颜色为白色 */
.custom-dialog .el-dialog__title {
  color: white !important;
}

.car-form .el-form-item .el-input,
.car-form .el-form-item .el-input-number,
.car-form .el-form-item .el-select {
  width: 300px;
}
.car-form .el-form-item .el-date-editor {
  width: 400px; /* 统一右侧控件宽度 */
}

</style>
