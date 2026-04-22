<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="审批模板名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入审批模板名称"
          clearable
          size="small"
        />
      </el-form-item>

      <!-- 查询 / 重置 / 新增 / 批量删除 在同一行 -->
      <el-form-item class="btns-form-item">
        <el-button size="btr" @click="handleQuery">查询</el-button>
        <el-button size="btr" @click="resetQuery">重置</el-button>

        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['militaryvehicleManagement:thebusThemplate:add']"
        >新增</el-button>

        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['militaryvehicleManagement:thebusThemplate:remove']"
        >批量删除</el-button>
      </el-form-item>
    </el-form>

    <!-- 原来的 el-row 新增/删除 整块去掉 -->

    <el-table
      v-loading="loading"
      :data="thebusThemplateList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="审批模板id" align="center" prop="id" />
      <el-table-column label="审批模板名称" align="center" prop="name" />
      <el-table-column label="一级审批人" align="center" prop="peopleName1" />
      <el-table-column label="二级审批人" align="center" prop="peopleName2" />
      <el-table-column label="三级审批人" align="center" prop="peopleName3" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="更新时间" align="center" prop="updateTime" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebusThemplate:edit']"
          >修改</el-button>
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebusThemplate:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改军车审批模板对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="审批模板名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入审批模板名称" />
        </el-form-item>
        <el-form-item label="一级审批人" prop="people1Id">
          <el-select
            v-model="form.people1Id"
            placeholder="请选择一级审批人"
            filterable
            clearable
          >
            <el-option
              v-for="(item, i) in userRen.filter(u => u.level === 1)"
              :key="i"
              :label="item.name"
              :value="item.id"
              class="input_xiala"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="二级审批人" prop="people2Id">
          <el-select
            v-model="form.people2Id"
            placeholder="请选择二级审批人"
            filterable
            clearable
          >
            <el-option
              v-for="(item, i) in userRen.filter(u => u.level === 2)"
              :key="i"
              :label="item.name"
              :value="item.id"
              class="input_xiala"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="三级审批人" prop="people3Id">
          <el-select
            v-model="form.people3Id"
            placeholder="请选择三级审批人"
            filterable
            clearable
          >
            <el-option
              v-for="(item, i) in userRen.filter(u => u.level === 3)"
              :key="i"
              :label="item.name"
              :value="item.id"
              class="input_xiala"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listThebusThemplate,
  getThebusThemplate,
  delThebusThemplate,
  addThebusThemplate,
  updateThebusThemplate,
  exportThebusThemplate,
} from "@/api/militaryvehicleManagement/thebusThemplate";
import {
  getThebusUser,
  listAccessPeople,
  listAllAccessPeople,
} from "@/api/militaryvehicleManagement/thebusConfig";

export default {
  name: "ThebusThemplate",
  data() {
    return {
      loading: true,
      exportLoading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      thebusThemplateList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        people1Id: null,
        people2Id: null,
        people3Id: null,
        createtime: null,
        updatetime: null,
      },
      form: {},
      rules: {},
      userRen: [],
    };
  },
  created() {
    this.getList();
    this.getUserRen();
  },
  methods: {
    /** 查询军车审批模板列表 */
    getList() {
      this.loading = true;
      listThebusThemplate(this.queryParams).then((response) => {
        this.thebusThemplateList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 获取所有用户审批人
    getUserRen() {
      listAllAccessPeople().then((response) => {
        this.userRen = response.data;
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
        name: null,
        people1Id: null,
        people2Id: null,
        people3Id: null,
        createtime: null,
        updatetime: null,
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
      this.getUserRen();
      this.open = true;
      this.title = "添加军车审批模板";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getThebusThemplate(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改军车审批模板";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateThebusThemplate(this.form).then(() => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addThebusThemplate(this.form).then(() => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作：单条 / 批量 */
    handleDelete(row) {
      const ids = row && row.id ? [row.id] : this.ids;
      if (!ids || ids.length === 0) return;
      this.$modal
        .confirm("是否确认删除选中的军车审批模板数据项？")
        .then(function () {
          return delThebusThemplate(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作（保留原逻辑） */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有军车审批模板数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportThebusThemplate(queryParams);
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
.box {
  background-color: #ffba00 !important;
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
  background-image: url(../../../../images/小标题底.png);
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}
/* 调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}

/* 顶部按钮一行布局 */
::v-deep .btns-form-item .el-form-item__content {
  width: auto !important;
  display: flex;
  align-items: center;
}
::v-deep .btns-form-item .el-button--mini {
  width: auto !important;
  padding: 3px 16px;
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
  color: #111;
}
.el-select-dropdown__item {
  color: #111;
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
  padding: 30px 0px;
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
/* 查铺查哨-临时来对亲属 */
.box3 {
  width: 680px;
  min-height: 70px;
  border-radius: 5px;
  margin-bottom: 30px;
  padding-top: 15px;
  padding-left: 15px;
}
/* 表单长度 */
::v-deep .box3 .el-form-item__content {
  width: 116px;
}
::v-deep .box3 .el-input__inner {
  width: 116px;
  height: 37px;
}
/* 添加删除按钮 */
::v-deep .box3 .el-button {
  width: 32px;
  height: 32px;
  text-align: center;
  margin-right: 3px;
  margin-left: 8px;
}
::v-deep .box3 .el-button .el-icon-plus {
  margin-left: -6px;
}
::v-deep .box3 .el-button .el-icon-delete {
  margin-left: -6px;
}
.box3 > .box4 {
  width: 100px;
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
.box {
  background-color: #ffba00 !important;
}
.el-button--btc {
  width: 34px !important;
}
::v-deep .el-icon-plus:before {
  margin-left: 6px !important;
}
::v-deep .el-icon-delete:before {
  margin-left: 5px !important;
}
/* 删除 bty */
.el-button--bty.is-active,
.el-button--bty:active {
  background: #ff4949;
  border-color: #ff4949;
  color: #ffffff;
}
.el-button--bty:focus,
.el-button--bty:hover {
  background: #f76a6a;
  border-color: #f76a6a;
  color: #ffffff;
}
.el-button--bty {
  width: 36px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #ff4949;
}

::v-deep .el-table__body {
  width: 1460px !important;
}
::v-deep .el-table__header {
  width: 1460px !important;
}
</style>
