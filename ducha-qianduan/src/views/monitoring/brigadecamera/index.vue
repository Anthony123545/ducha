<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5" style="padding-top: 32px">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['monitoring:brigadecamera:add']"
          >新增</el-button
        >
      </el-col>
    </el-row>

    <div class="box">
      <ul>
        <li class="oneUl" v-for="(item, i) in cabinetList" :key="i">
          <div
            style="width: 265px; cursor: pointer; text-align: center"
            @click="opens(item.cameraaddress)"
          >
            <div class="title">{{ item.cameraname }}</div>
          </div>
          <div style="margin-top: 8px; margin-left: -14px">
            <el-button
              size="btcks"
              @click.prevent="handleUpdate(item)"
              v-hasPermi="['monitoring:brigadecamera:edit']"
              >修改</el-button
            >
          </div>
        </li>
      </ul>
    </div>

    <!-- 添加或修改摄像头管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        inline
        style="padding-left: 54px"
      >
        <el-form-item label="名称" prop="cameraname">
          <el-input v-model="form.cameraname" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="单位名称" prop="unitname">
          <el-input v-model="form.unitname" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="摄像头地址" prop="cameraaddress">
          <el-input
            v-model="form.cameraaddress"
            placeholder="请输入摄像头地址"
          />
        </el-form-item>
        <el-form-item label="创建时间" prop="cretetime">
          <el-date-picker
            clearable
            v-model="form.cretetime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择创建时间"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="margin-right: 108px">
        <el-button size="btcksti" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listBrigadecamera,
  getBrigadecamera,
  delBrigadecamera,
  addBrigadecamera,
  updateBrigadecamera,
} from "@/api/monitoring/brigadecamera";

export default {
  name: "Brigadecamera",
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
      // 摄像头管理表格数据
      brigadecameraList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cameraname: null,
        unitname: null,
        cameraaddress: null,
        cretetime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 手机柜表格数据
      cabinetList: [],
      // 手机柜格子列表
      gridList: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    opens(url) {
      window.open(url);
      console.log(url);
    },
    /** 查询摄像头管理列表 */
    getList() {
      this.loading = true;
      listBrigadecamera(this.queryParams).then((response) => {
        console.log(response.rows);
        this.cabinetList = response.rows;
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
        cameraname: null,
        unitname: null,
        cameraaddress: null,
        cretetime: null,
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
      this.title = "添加摄像头管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getBrigadecamera(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改摄像头管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateBrigadecamera(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBrigadecamera(this.form).then((response) => {
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
        .confirm("是否确认删除摄像头管理编号的数据项？")
        .then(function () {
          return delBrigadecamera(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
  },
};
</script>
<style scoped>
/* 调整表单间距 */
::v-deep .el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 200px;
}
::v-deep .el-dialog {
  width: 500px !important;
  background-color: #004d86;
  margin: auto;
}

::v-deep .el-dialog__header {
  border-bottom: 1px solid #718a9d;
  margin: auto;
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

* {
  padding: 0;
}
ul {
  list-style-type: none;
}
.box ul {
  /* display: flex;
  flex-flow: wrap; */
  display: flex;
  flex-flow: wrap;
  margin-left: 3%;
  padding-left: -1%;
  padding-top: 28px;
}
.oneUl {
  flex: 1;
  /* display: inline-block; */
  background: url("../../../assets/images/jiankong.png") no-repeat;
  /* width: 306px; */
  height: 219px;
  padding: 22px;
  min-width: 308px;
}
.title {
  color: #fff;
  height: 16px;
  display: inline-table;
  margin-right: 35%;
  margin-top: 15%;
}
.table {
  margin-top: 18px;
  line-height: 32px;
  padding-left: 12px;
  color: #fff;
  cursor: pointer;
}
.mb9 {
  margin-bottom: 20px;
  margin-top: 10px;
}
/* 表单背景 */
::v-deep .el-input__inner {
  background-color: transparent !important;
  color: #fff;
  /* border: 1px solid white !important; */
  border-color: #fff !important;
}

.box1 {
  padding-top: 17px;
  padding-left: 24px;
}
.twoUl li {
  height: 114px;
  padding: 20px;
  margin-right: 22px;
  margin-bottom: 43px;
  min-width: 175px;
}
.twoUl {
  padding-left: 12px;
  display: flex;
  flex-flow: wrap;
  margin-top: 0px;
}
.two_div {
  text-align: center;
  margin-top: 10px;
  padding-top: 10px;
  color: #fff;
}
::v-deep .el-button--mini {
  margin-left: 53px !important;
  margin-top: 6px !important;
}
.shuJu {
  height: 16px;
  margin-top: 26px;
  margin-right: 90px;
  float: right;
  color: #fff;
  font-size: 14px;
}

::v-deep .el-dialog {
  border-radius: 8px;
}
::v-deep .has-gutter tr {
  height: 60px;
}
/* 分页样式 */
::v-deep .pagination-container {
  background: #004d85 !important;
}
::v-deep .el-pagination__total {
  margin-top: 2px !important;
}
::v-deep .el-pagination .el-select .el-input .el-input__inner {
  background-color: #196299 !important;
  height: 30px;
}
::v-deep .el-pagination.is-background .btn-prev,
.el-pagination.is-background .btn-next {
  background-color: #265070 !important;
}
::v-deep .el-pagination.is-background .el-pager li {
  background-color: #196299 !important;
}
::v-deep .el-pagination__editor.el-input .el-input__inner {
  background-color: #196299 !important;
}
::v-deep .el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #1890ff !important;
}
::v-deep .el-pagination.is-background .btn-next {
  background: #265070 !important;
}
::v-deep .el-pagination__sizes {
  display: none !important;
}
::v-deep .el-table {
  background-color: transparent;
}
::v-deep .el-pagination {
  padding: 2px 54px;
}
::v-deep .el-button--btcks {
  color: #ffffff;
  width: 48px;
  height: 28px;
  background-color: #1d96ff;
  border-radius: 4px;
  font-size: 14px;
  text-align: center;
  margin-top: 1px;
}
::v-deep .el-button--btcks:focus,
.el-button--btcks:hover {
  background: #1d96ff;
  border-color: #1d96ff;
  color: #ffffff;
}

::v-deep .el-button--btcks.is-active,
.el-button--btcks:active {
  background: #1d96ff;
  border-color: #1d96ff;
  color: #ffffff;
}
::v-deep .el-button--btcksti {
  color: #ffffff;
  width: 76px;
  height: 36px;
  background-color: #1d96ff;
  border-radius: 4px;
  font-size: 14px;
  text-align: center;
  margin-top: 1px;
}
::v-deep .el-button--btcksti:focus,
.el-button--btcksti:hover {
  background: #1d96ff;
  border-color: #1d96ff;
  color: #ffffff;
}

::v-deep .el-button--btcksti.is-active,
.el-button--btcksti:active {
  background: #1d96ff;
  border-color: #1d96ff;
  color: #ffffff;
}
</style>
