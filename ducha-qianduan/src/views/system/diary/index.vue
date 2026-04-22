<template>
  <div class="app-container">
    <!-- <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="设置是否开启" prop="setnum">
        <el-input
          v-model="queryParams.setnum"
          placeholder="请输入设置是否开启"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新人名字" prop="upname">
        <el-input
          v-model="queryParams.upname"
          placeholder="请输入更新人名字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间" prop="updates">
        <el-date-picker
          clearable
          v-model="queryParams.updates"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择更新时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="类别" prop="types">
        <el-input
          v-model="queryParams.types"
          placeholder="请输入类别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form> -->

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:diary:add']"
          >新增</el-button
        >
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:diary:edit']"
          >修改</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:diary:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:diary:export']"
          >导出</el-button
        > -->
      <!-- </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="diaryList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否开启" align="center" prop="setnum">
        <template slot-scope="scope">
          <span v-if="scope.row.setnum == '0'">关闭</span>
          <span v-else>开启</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="upname" />
      <el-table-column
        label="更新时间"
        align="center"
        prop="updates"
        width="180"
      >
      </el-table-column>
      <!-- <el-table-column label="类别" align="center" prop="types" /> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:diary:edit']"
            >修改</el-button
          >
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:diary:remove']"
          >删除</el-button> -->
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

    <!-- 添加或修改要事日记设置 对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        inline
      >
        <el-form-item label="是否开启" prop="setnum">
          <!-- <el-input v-model="form.setnum" placeholder="请输入设置是否开启" /> -->
          <el-radio v-model="form.setnum" :label="0">关闭</el-radio>
          <el-radio v-model="form.setnum" :label="1">开启</el-radio>
        </el-form-item>
        <el-form-item label="更新人" prop="upname">
          <el-input v-model="form.upname" placeholder="请输入更新人名字" />
        </el-form-item>
        <el-form-item label="更新时间" prop="updates">
          <el-date-picker
            clearable
            v-model="form.updates"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择更新时间"
          >
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="类别" prop="types">
          <el-input v-model="form.types" placeholder="请输入类别" />
        </el-form-item> -->
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
  listDiary,
  getDiary,
  delDiary,
  addDiary,
  updateDiary,
} from "@/api/system/diary";

export default {
  name: "Diary",
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
      // 要事日记设置 表格数据
      diaryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        setnum: null,
        upname: null,
        updates: null,
        types: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询要事日记设置 列表 */
    getList() {
      this.loading = true;
      listDiary(this.queryParams).then((response) => {
        this.diaryList = response.rows;
        this.total = response.total;
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
        setnum: null,
        upname: null,
        updates: null,
        types: null,
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
      this.title = "添加要事日记设置 ";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getDiary(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改要事日记设置 ";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateDiary(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDiary(this.form).then((response) => {
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
        .confirm('是否确认删除要事日记设置 编号为"' + ids + '"的数据项？')
        .then(function () {
          return delDiary(ids);
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
        "system/diary/export",
        {
          ...this.queryParams,
        },
        `diary_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>

<style scoped>
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
}
::v-deep .el-textarea__inner {
  width: 700px;
  height: 104px;
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
  width: 660px;
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
/* 刷新图标 */
/* .el-icon-refresh {
    width: 76px;
    height: 36px;
    color: #fff;
    background-color: #1d96ff !important;
    border-radius: 4px;
    border: none;
    margin-left: 20px;
    font-size: 14px;
  } */
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
::v-deep .el-pagination.is-background .btn-prev,
.el-pagination.is-background .btn-next,
.el-pagination.is-background .el-pager li {
  background: #004d86 !important;
  color: #fff !important;
}
/* 备注输入框 */
.textareas ::v-deep .el-form-item__content {
  width: 100%;
}
/* 计数器按钮 */
::v-deep .el-input-number__increase {
  background-color: transparent !important;
  border: none;
  color: #cccccc;
  border-bottom: none !important;
}
::v-deep .el-input-number__decrease {
  background-color: transparent !important;
  border: none !important;
  color: #cccccc;
}
/* 菜单权限下拉 */
::v-deep .tree-border {
  background: #004d86 !important;
}
.el-tree {
  color: #fff !important;
  width: 300px;
}
::v-deep .el-tree-node__content:hover {
  background-color: #024274 !important;
}
.el-checkbox {
  color: rgb(204, 198, 198);
}
/* 状态 */
.el-radio {
  color: rgb(204, 198, 198);
}
/* 日期背景颜色 */
::v-deep .el-date-editor .el-range-input {
  background: #00365f;
}
::v-deep .el-textarea__inner {
  width: 684px;
  height: 104px;
}

::v-deep .el-tree-node__content:hover {
  color: #fff;
  background-color: #024274;
  background-repeat: no-repeat;
}
::v-deep
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  color: #fff !important;
  background-color: #024274;
  background-repeat: no-repeat;
}
::v-deep .el-tree-node__label {
  color: #fff !important;
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
::v-deep .el-date-editor .el-range-input {
  color: #fff;
}
.el-dialog__body {
  padding: 30px 0px 0px 27px;
}
::v-deep .el-dialog__footer {
  margin-right: 109px !important;
}

::v-deep .el-dialog__body {
  margin-left: 53px;
}
</style>
