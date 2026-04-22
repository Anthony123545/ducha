<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item>
        <el-input
          v-model="queryParams.name"
          placeholder="请输入审批人员名称"
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
          v-hasPermi="['militaryvehicleManagement:thebusConfig:add']"
          >新增</el-button
        >

        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['militaryvehicleManagement:thebusConfig:remove']"
          >批量删除</el-button
        >
      </el-form-item>
    </el-form>

    <!-- 原来的 el-row 新增/删除 区域删除 -->

    <el-table
      v-loading="loading"
      :data="accessPeopleList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" align="center" prop="id">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="审批人员id"
        align="center"
        prop="userId"
        min-width="100"
      />
      <el-table-column
        label="审批人员名称"
        align="center"
        prop="name"
        min-width="100"
      />
      <el-table-column
        label="审批人员职别"
        align="center"
        prop="level"
        min-width="100"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        min-width="200"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebusConfig:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebusConfig:remove']"
            >删除</el-button
          >
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

    <!-- 添加审批人员 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div
        style="display: flex; flex-direction: column; gap: 12px; width: 100px"
      >
        <el-form label-width="100px">
          <el-form-item label="人员名称">
            <el-select
              v-model="shenpiname"
              filterable
              placeholder="请选择人员名称"
              style="width: 100%"
            >
              <el-option
                v-for="item in userRen"
                :key="item.userId"
                :label="item.nickName"
                :value="item.userName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="人员职别">
            <el-select
              v-model="shenpizhibie"
              placeholder="请选择人员职别"
              style="width: 200px"
            >
              <el-option
                v-for="level in levels"
                :key="level.value"
                :label="level.label"
                :value="level.value"
              />
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取消</el-button>
      </div>
    </el-dialog>

    <!-- 修改审批人员 -->
    <el-dialog
      :title="title"
      :visible.sync="opens"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div>
        <el-form label-width="100px">
          <el-form-item label="人员名称">
            <el-select
              v-model="form.name"
              filterable
              placeholder="请选择人员名称"
              style="width: 100%"
            >
              <el-option
                v-for="item in userRen"
                :key="item.userId"
                :label="item.userName"
                :value="item.userName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="人员职别">
            <el-select v-model="form.level" placeholder="请选择人员职别">
              <el-option label="初级" :value="1"></el-option>
              <el-option label="中级" :value="2"></el-option>
              <el-option label="高级" :value="3"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm1">确 定</el-button>
        <el-button @click="cancel" size="btn">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listThebusConfig,
  getThebusConfig,
  delThebusConfig,
  addThebusConfig,
  updateThebusConfig,
  exportThebusConfig,
  addAccessPeople,
  listAccessPeople,
  delAccessPeople,
  getAccessPeople,
  updateAccessPeople,
  getThebusUserByRole,
  getThebusUserByRoleId,
} from "@/api/militaryvehicleManagement/thebusConfig";
import { getThebusUser } from "@/api/militaryvehicleManagement/thebusConfig";

export default {
  name: "ThebusConfig",
  data() {
    return {
      loading: true,
      exportLoading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      accessPeopleList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: "",
        level: null,
      },
      form: {},
      rules: {},
      shenpiname: "",
      shenpizhibie: "",
      levels: [
        { label: "初级", value: 1 },
        { label: "中级", value: 2 },
        { label: "高级", value: 3 },
      ],
      userRen: [],
      opens: false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 获取所有用户审批人
    getUserRen() {
      const roleId = 45;
      getThebusUserByRoleId(roleId).then((response) => {
        this.userRen = response.data;
      });
    },
    /** 查询审批人员列表*/
    getList() {
      this.loading = true;
      listAccessPeople(this.queryParams).then((response) => {
        this.accessPeopleList = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.opens = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: "",
        level: null,
      };
      this.resetForm("form");
      this.shenpiname = "";
      this.shenpizhibie = "";
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.name = null;
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
      this.title = "添加审批人员";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getUserRen();
      const id = row.id || this.ids;
      getAccessPeople(id).then((response) => {
        this.form = response.data;
        this.opens = true;
        this.title = "修改审批人员";
      });
    },
    /** 新增提交 */
    submitForm() {
      const formData = {
        name: this.shenpiname,
        level: this.shenpizhibie,
      };
      addAccessPeople(formData).then((response) => {
        this.$modal.msgSuccess("新增成功");
        this.open = false;
        this.getList();
      });
    },
    // 修改提交
    submitForm1() {
      if (this.form.id != null) {
        updateAccessPeople(this.form).then((response) => {
          this.$modal.msgSuccess("修改成功");
          this.opens = false;
          this.getList();
        });
      }
    },
    /** 删除按钮操作（单条 / 批量） */
    handleDelete(row) {
      const ids = row && row.id ? [row.id] : this.ids;
      this.$modal
        .confirm("是否确认删除审批人员的数据项？")
        .then(function () {
          return delAccessPeople(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作（暂未使用） */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有军车管理设置数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportThebusConfig(queryParams);
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

/* 默认表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}

/* 顶部按钮一行显示 */
::v-deep .btns-form-item .el-form-item__content {
  width: auto !important;
  display: flex;
  align-items: center;
}

/* 让顶部 mini 按钮宽度自适应文字 */
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
