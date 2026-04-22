<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="" prop="chePaiNum">
        <el-input
          size="small"
          v-model="queryParams.chePaiNum"
          type="datetime"
          placeholder="输入车牌号"
          class="text"
        >
        </el-input>
      </el-form-item>
      <el-form-item label="" prop="startTime">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.startTime"
          type="datetime"
          placeholder="选择进入开始时间"
          class="text"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="" prop="endTime">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.endTime"
          type="datetime"
          placeholder="选择离开的结束时间"
          class="text"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="btr" @click="handleQuery"
          >搜索</el-button
        >
        <el-button size="btr" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          class="box"
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['militaryvehicleManagement:privateCar:export']"
          >导出</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="privateCarList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="车牌号" align="center" prop="chePaiNum" />
      <el-table-column label="进入次数" align="center" prop="inCount" />
      <el-table-column label="离开次数" align="center" prop="outCount" />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

  <script>
import {
  personalCarStat,
  exportCarStat,
} from "@/api/militaryvehicleManagement/thebusSi";
// 查询字典
import { getDicts } from "@/api/system/dict/data";
export default {
  name: "PrivateCar",
  data() {
    return {
      // 遮罩层
      loading: false,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 私家车管理表格数据
      privateCarList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        adminId: [
          { required: true, message: "创建人不能为空", trigger: "blur" },
        ],
      },
      // 车辆类别
      privateCar: [],
      // 人员列表
      renYuan: [],
      // 证件类别
      documentType: [],
      // 外面的人员
      renYuans: [],
    };
  },
  created() {
    // this.getList();
    getDicts("privateCar").then((response) => {
      this.privateCar = response.data;
    });
    getDicts("documentType").then((response) => {
      this.documentType = response.data;
    });
  },
  methods: {
    // 证件类型翻译
    zhenJian(row, column) {
      var zhenJian = "";
      this.documentType.map((item) => {
        if (item.dictValue == row.documenttype) {
          zhenJian = item.dictLabel;
        }
      });
      return zhenJian;
    },
    // 车辆类别翻译
    carLei(row, column) {
      var carLei = "";
      this.privateCar.map((item) => {
        if (item.dictValue == row.vehiclecategory) {
          carLei = item.dictLabel;
        }
      });
      return carLei;
    },
    // 点击车辆所有人选中触发
    suoYouRen(name) {
      this.form.peopleName = name;
    },
    // 人员列表
    getRen() {
      getRenYuanCar().then((response) => {
        this.renYuan = response.data;
      });
    },
    /** 查询私家车管理列表 */
    getList() {
      this.loading = true;
      personalCarStat(this.queryParams).then((response) => {
        this.privateCarList = response.data;
        if (this.total) {
          this.total = response.total;
        }else{
            this.total = 0;
        }
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        peopleId: null,
        peopleName: null,
        contactnumber: null,
        documenttype: null,
        certificatenumber: null,
        remarks: null,
        adminId: null,
        createtime: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
     this.queryParams.startTime="";
     this.queryParams.endTime="";
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getRen();
      this.reset();
      this.open = true;
      this.title = "添加私家车管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getRen();
      const id = row.id || this.ids;
      getPrivateCar(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改私家车管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updatePrivateCar(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPrivateCar(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除私家车管理的数据项？")
        .then(function () {
          return delPrivateCar(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有私家车统计数据项？")
        .then(() => {
          if (this.queryParams.endTime && this.queryParams.startTime) {
            this.exportLoading = true;
            return exportCarStat(queryParams);
          } else {
            this.$message.error("请选择进入时间跟离开时间");
          }
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
  },
};
</script>
  <style scoped>
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
  width: 800px !important;
}
::v-deep .el-textarea__inner {
  width: 610px;
  height: 104px;
  margin: auto;
}
::v-deep .el-dialog__header {
  border-bottom: 1px solid #718a9d;
}
::v-deep .el-dialog__title {
  color: #fff;
  font: 18px;
}
::v-deep .el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}
::v-deep .el-form-item__label {
  font: 16px;
  color: #fff;
  width: 100px !important;
}
::v-deep .el-input__inner {
  /* width: 200px !important;
    height: 36px; */
  background: transparent;
  color: #fff;
  border: 1px solid white !important;
}
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
/* 基本信息背景 */
.jiben {
  width: 620px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  background-size: 100%;
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
::v-deep .el-input__inner {
  cursor: pointer !important;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 30px 50px;
}
::v-deep .el-dialog__body {
  margin: 10px 24px 20px 30px;
  padding-top: 20px !important;
  box-sizing: border-box;
  /* padding: 30px 12px 30px 28px; */
}
.contents {
  padding: 0px 40px !important;
}
/* 下拉菜单 */
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
/* 下拉菜单字体/背景颜色 */
.el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
  background-color: #004d86;
  color: #fff;
}
.el-select-dropdown__item {
  color: #fff;
}
/* 时间选择 */
::v-deep .el-input--small .el-input__inner {
  width: 200px;
  height: 36px;
  line-height: 36px;
}
.el-date-editor.el-input {
  width: 200px;
  height: 36px;
  margin-right: 48px;
}
::v-deep .el-date-editor.el-input .el-input__inner {
  height: 36px;
  line-height: 36px;
}
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
  color: #fff;
}
/* 单位下拉菜单选中字体颜色 */
::v-deep .vue-treeselect__single-value {
  color: #fff !important;
}
/* 分页按钮 */
::v-deep .el-pagination.is-background .el-pager li {
  background-color: #004d86;
  color: #fff;
}
::v-deep .el-pagination.is-background .btn-next {
  background-color: #004d86;
  color: #fff;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 18px 50px;
  margin-right: 42px;
}
::v-deep .el-dialog__body {
  padding: 30px 63px;
}
/* 小手样式 */
::v-deep .el-table__cell {
  cursor: pointer;
}
.el-button--mini {
  width: 80px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #1890ff;
}
/* 调整输入框提示文字颜色 */
::v-deep .vue-treeselect__placeholder {
  color: #bdbdbd4f !important;
}
::v-deep input::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}
::v-deep input:-moz-placeholder {
  color: #bdbdbd4f !important;
}
/* 文本域提示文字颜色 */
::v-deep ::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}
.box {
  background-color: #ffba00 !important;
}
</style>
