<template>
  <div class="app-container">
    <div class="form-box">
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        inline
      >
        <el-form-item label="在位总数" prop="totalNumber">
          <el-input v-model="form.totalNumber" placeholder="" />
        </el-form-item>
        <el-form-item label="纳编在位" prop="nabianPlace">
          <el-input v-model="form.nabianPlace" placeholder="" />
        </el-form-item>
        <el-form-item label="未纳编在位" prop="notInPlace">
          <el-input v-model="form.notInPlace" placeholder="" />
        </el-form-item>
        <el-form-item label="纳编外出" prop="nabianOut">
          <el-input v-model="form.nabianOut" placeholder="" />
        </el-form-item>
        <el-form-item label="未纳编外出" prop="notNabianOut">
          <el-input v-model="form.notNabianOut" placeholder="" />
        </el-form-item>
        <el-form-item label="是否启用" prop="toggle">
          <el-radio-group v-model="form.toggle">
            <el-radio :label="0">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" size="btq"
          >确 定</el-button
        >
      </div>
    </div>

    <div class="form-box">
      <el-form
        ref="form1"
        :model="form1"
        :rules="rules"
        label-width="80px"
        inline
      >
        <el-form-item label="请假人数" prop="leaveNum">
          <el-input v-model="form1.leaveNum" placeholder="" />
        </el-form-item>
        <el-form-item label="学习人数" prop="studyNum">
          <el-input v-model="form1.studyNum" placeholder="" />
        </el-form-item>
        <el-form-item label="特殊人数" prop="speciaNum">
          <el-input v-model="form1.speciaNum" placeholder="" />
        </el-form-item>
        <el-form-item label="出差人数" prop="businessTravelNum">
          <el-input v-model="form1.businessTravelNum" placeholder="" />
        </el-form-item>
        <el-form-item label="休假人数" prop="vacationNum">
          <el-input v-model="form1.vacationNum" placeholder="" />
        </el-form-item>
        <el-form-item label="是否启用" prop="isEnable">
          <el-radio-group v-model="form1.isEnable">
            <el-radio :label="0">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm1" size="btq"
          >确 定</el-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import {
  listPeopleStatistics,
  listPeopleStatistics1,
  getPeopleStatistics,
  delPeopleStatistics,
  addPeopleStatistics,
  updatePeopleStatistics1,
} from "@/api/combatduty/PeopleStatistics";

export default {
  name: "PeopleStatistics",
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 人员变动表格数据
      PeopleStatisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        totalNumber: null,
        nabianPlace: null,
        notInPlace: null,
        nabianOut: null,
        notNabianOut: null,
        toggle: null,
        leaveNum: null,
        studyNum: null,
        specialNum: null,
        busibessTravelNum: null,
        vacationNum: null,
        isEnable: null,
      },
      // 表单参数
      form: {},
      form1: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询人员变动列表 */
    getList() {
      this.loading = true;
      listPeopleStatistics(this.queryParams).then((response) => {
        console.log(response);
        if (response.rows.length > 0) {
          this.form = response.rows[0];
          this.form.toggle = response.rows[0].toggle * 1;
          this.total = response.total;
          this.loading = false;
        }
      });
      listPeopleStatistics1(this.queryParams).then((response) => {
        console.log(response);
        if (response.code === 200) {
          this.form1 = response.data;
          this.form1.isEnable = response.data.isEnable * 1;
        }
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
        totalNumber: null,
        nabianPlace: null,
        notInPlace: null,
        nabianOut: null,
        notNabianOut: null,
        toggle: null,
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加人员变动";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPeopleStatistics(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改人员变动";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          addPeopleStatistics(this.form).then((response) => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    submitForm1() {
      this.$refs["form1"].validate((valid) => {
        if (valid) {
          updatePeopleStatistics1(this.form1).then((response) => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除人员变动的数据项？")
        .then(function () {
          return delPeopleStatistics(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "combatduty/PeopleStatistics/export",
        {
          ...this.queryParams,
        },
        `PeopleStatistics_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
<style scoped>
.app-container {
  min-height: 88vh;
}
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
  width: 820px !important;
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
  width: 920px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
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
  padding: 30px 60px;
}
::v-deep .el-dialog__body {
  margin: 22px 28px -14px 28px;
  padding-top: 20px !important;
  box-sizing: border-box;
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
  line-height: 36px;
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
  margin-right: 76px;
}
::v-deep .el-dialog__body {
  padding: 30px 60px;
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
::v-deep .el-input__inner {
  border: 1px solid white !important;
}
::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent !important;
  color: #fff;
}
::v-deep .el-form {
  margin-top: 40px;
}
::v-deep .dialog-footer {
  margin-left: 78%;
  margin-top: -12%;
}
::v-deep .el-form-item__label {
  margin-bottom: 30px;
}
.form-box {
  width: 800px;
  margin-top: 56px;
}
::v-deep .el-radio {
  color: #fff;
}
::v-deep .el-radio__input.is-checked + .el-radio__label {
  color: #fff;
}
.app-container {
  display: flex;
}
</style>

