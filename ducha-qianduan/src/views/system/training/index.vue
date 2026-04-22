<template>
  <div class="app-container" style="height: 88vh">
    <div class="form-box">
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        inline
      >
        <el-form-item label="合成训练" prop="synthesis">
          <el-input v-model="form.synthesis" placeholder="" />
        </el-form-item>
        <el-form-item label="专业训练" prop="professional">
          <el-input v-model="form.professional" placeholder="" />
        </el-form-item>
        <el-form-item label="共同训练" prop="common">
          <el-input v-model="form.common" placeholder="" />
        </el-form-item>
        <el-form-item label="考核人次" prop="peopleNumber">
          <el-input v-model="form.peopleNumber" placeholder="" />
        </el-form-item>
        <el-form-item label="优良人数" prop="youliang">
          <el-input v-model="form.youliang" placeholder="" />
        </el-form-item>
        <el-form-item label="及格人数" prop="jige">
          <el-input v-model="form.jige" placeholder="" />
        </el-form-item>
        <el-form-item label="未及格人数" prop="weijige">
          <el-input v-model="form.weijige" placeholder="" />
        </el-form-item>
        <!-- <el-form-item label="参训率" prop="canxunlv">
          <el-input
            v-model="form.canxunlv"
            placeholder=""
            clearable
            disabled
          />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" size="btq"
          >确 定</el-button
        >
        <!-- <el-button @click="cancel" size="btn">取 消</el-button> -->
      </div>
    </div>
    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:training:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:training:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:training:remove']"
          >删除</el-button
        >
      </el-col> -->
    <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:training:export']"
          >导出</el-button
        >
      </el-col> -->
    <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    <!-- </el-row> -->

    <!-- <el-table
      v-loading="loading"
      :data="trainingList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合成训练" align="center" prop="synthesis" />
      <el-table-column label="专业训练" align="center" prop="professional" />
      <el-table-column label="共同训练" align="center" prop="common" />
      <el-table-column label="考核人次" align="center" prop="peopleNumber" />
      <el-table-column label="优良人数" align="center" prop="youliang" />
      <el-table-column label="及格人数" align="center" prop="jige" />
      <el-table-column label="未及格人数" align="center" prop="weijige" />
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
            v-hasPermi="['system:training:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:training:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table> -->

    <!-- <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    /> -->

    <!-- 添加或修改态势军事训练月统计情况对话框 -->
    <!-- <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body> -->
    <!-- </el-dialog> -->
  </div>
</template>

<script>
import {
  listTraining,
  getTraining,
  delTraining,
  addTraining,
  updateTraining,
} from "@/api/system/training";

export default {
  name: "Training",
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
      // 态势军事训练月统计情况表格数据
      trainingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        synthesis: null,
        professional: null,
        common: null,
        peopleNumber: null,
        youliang: null,
        jige: null,
        weijige: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        synthesis: [],
      },
    };
  },
  created() {
    // this.getList();
  },
  methods: {
    /** 查询态势军事训练月统计情况列表 */
    getList() {
      this.loading = true;
      listTraining(this.queryParams).then((response) => {
        this.trainingList = response.rows;
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
        synthesis: null,
        professional: null,
        common: null,
        peopleNumber: null,
        youliang: null,
        jige: null,
        weijige: null,
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
      this.title = "添加态势军事训练月统计情况";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTraining(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改态势军事训练月统计情况";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTraining(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTraining(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.reset();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm(
          '是否确认删除态势军事训练月统计情况编号为"' + ids + '"的数据项？'
        )
        .then(function () {
          return delTraining(ids);
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
        "system/training/export",
        {
          ...this.queryParams,
        },
        `training_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
<style scoped>
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
.box {
  background-color: #ffba00 !important;
}
::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent !important;
  color: #fff;
}
::v-deep .el-form {
  margin-top: 40px;
}
::v-deep .dialog-footer {
  margin-left: 44%;
}
::v-deep .el-form-item__label {
  margin-bottom: 30px;
}
.form-box {
  width: 658px;
  margin: auto;
  margin-top: 50px;
}
</style>
