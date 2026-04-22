<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="peopleType">
        <el-input
          v-model="queryParams.shenpiname"
          placeholder="请输入审批模板名称"
          size="small"
          @input="handleQuery"
        />
        <!-- <el-select
          v-model="queryParams.peopleType"
          placeholder="请选择审批模板名称"
          clearable
          size="small"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select> -->
      </el-form-item>
      <el-form-item>
        <!-- <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        > -->
        <el-button size="btr" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['militaryvehicleManagement:thebusConfig:add']"
          >新增</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['militaryvehicleManagement:thebusConfig:edit']"
          >修改</el-button
        >
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['militaryvehicleManagement:thebusConfig:remove']"
          >删除</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          class="box"
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['militaryvehicleManagement:thebusConfig:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="thebusConfigList"
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
      <el-table-column label="审批模板名称" align="center" prop="shenpiname" />
      <el-table-column
        label="一级审批人"
        align="center"
        prop="oneshenpirenname"
      />
      <el-table-column
        label="二级审批人"
        align="center"
        prop="twoshenpirenname"
      />
      <el-table-column
        label="三级审批人"
        align="center"
        prop="threeshenpirenname"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
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

    <!-- 添加修改军车管理设置对话框 -->
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
      >
        <div class="box3">
          <el-form v-for="(item1, e) in checkShop" :key="e">
            <div>
              <el-form-item label="" prop="" class="box4">
                <el-input
                  v-model="item1.shenpiname"
                  placeholder="审批模板名称"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="onshenpirenid">
                <el-select
                  v-model="item1.onshenpirenid"
                  placeholder="请选择一级审批人"
                  filterable
                  clearable
                >
                  <el-option
                    v-for="(item, i) in userRen"
                    :key="i"
                    :label="item.nickName"
                    :value="item.userId"
                    class="input_xiala"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="" prop="twoshenpirenid">
                <el-select
                  v-model="item1.twoshenpirenid"
                  placeholder="请选择二级审批人"
                  filterable
                  clearable
                >
                  <el-option
                    v-for="(item, i) in userRen"
                    :key="i"
                    :label="item.nickName"
                    :value="item.userId"
                    class="input_xiala"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="" prop="threeshenpirenid">
                <el-select
                  v-model="item1.threeshenpirenid"
                  placeholder="请选择三级审批人"
                  filterable
                  clearable
                >
                  <el-option
                    v-for="(item, i) in userRen"
                    :key="i"
                    :label="item.nickName"
                    :value="item.userId"
                    class="input_xiala"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  size="btc"
                  @click="addForms"
                  v-if="e == checkShop.length - 1"
                ></el-button>
                <el-button
                  type="primary"
                  icon="el-icon-delete"
                  size="bty"
                  v-if="e > 0"
                  @click="delForms(e)"
                ></el-button>
              </el-form-item>
            </div>
          </el-form>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取消</el-button>
      </div>
    </el-dialog>
    <!-- 修改军车管理设置 -->
    <el-dialog
      :title="title"
      :visible.sync="opens"
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
      >
        <div>
          <el-form-item label="" prop="" class="box4">
            <el-input
              v-model="form.shenpiname"
              placeholder="审批模板名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="" prop="onshenpirenid">
            <el-select
              v-model="form.onshenpirenid"
              placeholder="请选择一级审批人"
              filterable
              clearable
            >
              <el-option
                v-for="(item, i) in userRen"
                :key="i"
                :label="item.nickName"
                :value="item.userId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="" prop="twoshenpirenid">
            <el-select
              v-model="form.twoshenpirenid"
              placeholder="请选择二级审批人"
              filterable
              clearable
            >
              <el-option
                v-for="(item, i) in userRen"
                :key="i"
                :label="item.nickName"
                :value="item.userId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="" prop="threeshenpirenid">
            <el-select
              v-model="form.threeshenpirenid"
              placeholder="请选择三级审批人"
              filterable
              clearable
            >
              <el-option
                v-for="(item, i) in userRen"
                :key="i"
                :label="item.nickName"
                :value="item.userId"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </div>
      </el-form>
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
} from "@/api/militaryvehicleManagement/thebusConfig";
import { getThebusUser } from "@/api/militaryvehicleManagement/thebusConfig";

export default {
  name: "ThebusConfig",
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 军车管理设置表格数据
      thebusConfigList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        peopleType: null,
        authAutoOpen: null,
        carAutoBack: null,
        noauthOpen: null,
        time1: null,
        time2: null,
        defaultUnitId: null,
        defaultPeopleId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //添加对话框查铺表格为空
      checkShop: [
        {
          shenpiname: null,
          onshenpirenid: null,
          twoshenpirenid: null,
          threeshenpirenid: null,
        },
      ],
      // 获取审批人
      userRen: [],
      // 修改弹出框
      opens: false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 获取所有用户审批人
    getUserRen() {
      getThebusUser().then((response) => {
        this.userRen = response.rows;
      });
    },
    // 删除对话框查铺
    delForms(i) {
      this.checkShop.splice(i, 1);
    },

    // 增加对话框查铺
    addForms() {
      this.checkShop.push({
        shenpiname: null,
        onshenpirenid: null,
        twoshenpirenid: null,
        threeshenpirenid: null,
      });
    },
    /** 查询军车管理设置列表 */
    getList() {
      this.loading = true;
      listThebusConfig(this.queryParams).then((response) => {
        this.thebusConfigList = response.rows;
        this.total = response.total;
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
        peopleType: null,
        authAutoOpen: null,
        carAutoBack: null,
        noauthOpen: null,
        time1: null,
        time2: null,
        defaultUnitId: null,
        defaultPeopleId: null,
      };
      (this.checkShop = [
        {
          shenpiname: null,
          onshenpirenid: null,
          twoshenpirenid: null,
          threeshenpirenid: null,
        },
      ]),
        this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.shenpiname = null;
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
      this.title = "添加军车管理设置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getUserRen();
      const id = row.id || this.ids;
      getThebusConfig(id).then((response) => {
        this.form = response.data;
        // this.checkShop.push(this.form);
        this.opens = true;
        this.title = "修改军车管理设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.checkShop.forEach((item) => {
        if (
          item.twoshenpirenid ||
          item.onshenpirenid ||
          item.threeshenpirenid
        ) {
          this.$refs["form"].validate((valid) => {
            if (valid) {
              addThebusConfig(this.checkShop).then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          });
        } else {
          this.$message({
            showClose: true,
            message: "审批人必须有一个",
            type: "warning",
          });
        }
      });
    },
    // 修改提交
    submitForm1() {
      if (
        this.form.twoshenpirenid ||
        this.form.onshenpirenid ||
        this.form.threeshenpirenid
      ) {
        if (this.form.id != null) {
          updateThebusConfig(this.form).then((response) => {
            this.$modal.msgSuccess("修改成功");
            this.opens = false;
            this.getList();
          });
        }
      } else {
        this.$message({
          showClose: true,
          message: "审批人必须有一个",
          type: "warning",
        });
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除军车管理的数据项？")
        .then(function () {
          return delThebusConfig(ids);
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
  /* background: #409eff; */
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
  margin-left: -6px;
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
/* 删除 btd */
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

::v-deep .el-table__body{
  width: 1460px !important;
}
::v-deep .el-table__header{
  width: 1460px !important;
}
</style>
