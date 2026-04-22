<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="摄像头名称" prop="cname">
        <el-input
          v-model="queryParams.cname"
          placeholder="请输入摄像头名称"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="访问地址" prop="caddr">
        <el-input
          v-model="queryParams.caddr"
          placeholder="请输入访问地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="cstatus">
        <el-select v-model="queryParams.cstatus" placeholder="请选择状态" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createtime">
        <el-date-picker clearable size="small"
          v-model="queryParams.createtime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="单位id" prop="unitid">
        <el-input
          v-model="queryParams.unitid"
          placeholder="请输入单位id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位名称" prop="unitname">
        <el-input
          v-model="queryParams.unitname"
          placeholder="请输入单位名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="存入菜单ID" prop="powerArea">
        <el-input
          v-model="queryParams.powerArea"
          placeholder="请输入存入菜单ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="归属地" prop="belongLocal">
        <el-input
          v-model="queryParams.belongLocal"
          placeholder="请输入归属地"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item>
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
          v-hasPermi="['monitoringcp:cameracp:add']"
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
          v-hasPermi="['monitoringcp:cameracp:edit']"
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
          v-hasPermi="['monitoringcp:cameracp:remove']"
          >删除</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['monitoringcp:cameracp:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="cameracpList"
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
      <el-table-column label="摄像头名称" align="center" prop="cname" />
      <el-table-column
        label="访问地址"
        align="center"
        prop="caddr"
        width="300px"
      />
      <el-table-column
        label="单位名称"
        align="center"
        prop="unitname"
        width="150px"
      />
      <el-table-column label="属性ID" align="center" prop="unitid" />
      <el-table-column label="状态" align="center" prop="cstatus">
        <template slot-scope="scope">
          <span v-if="scope.row.cstatus == '1'" style="color: #13ce66"
            >显示</span
          >
          <span v-if="scope.row.cstatus == '0'" style="color: #ccc">隐藏</span>
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        align="center"
        prop="createtime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="单位id" align="center" prop="unitid" /> -->
      <!-- <el-table-column label="存入菜单ID" align="center" prop="powerArea" /> -->
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
            v-hasPermi="['monitoringcp:cameracp:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['monitoringcp:cameracp:remove']"
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

    <!-- 添加或修改安防监控对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        inline
      >
        <el-form-item label="单位名称" prop="unitname">
          <treeselect
            @select="selectPeo"
            v-model="form.unitid"
            :options="deptOptions"
            placeholder="选择单位"
          />
          <!-- <el-input v-model="form.unitname" placeholder="请输入单位名称" /> -->
        </el-form-item>
        <el-form-item label="属性ID" prop="belongLocal">
          <el-input v-model="form.belongLocal" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="摄像头名称" prop="cname">
          <el-input v-model="form.cname" placeholder="请输入摄像头名称" />
        </el-form-item>
        <el-form-item label="访问地址" prop="caddr">
          <el-input v-model="form.caddr" placeholder="请输入访问地址" />
        </el-form-item>

        <!-- <el-form-item label="单屏">
          <el-switch
            v-model="form.cstatus"
            active-color="#13ce66"
            inactive-color="#ccc"
            active-value="1"
            inactive-value="0"
          >
          </el-switch>
          <span class="greed" v-if="form.cstatus == '1'"></span>
          <span class="CCC" v-if="form.cstatus == '0'"></span>
        </el-form-item> -->
        <!-- <el-form-item label="单位id" prop="unitid">
          <el-input v-model="form.unitid" placeholder="请输入单位id" />
        </el-form-item> -->

        <!-- <el-form-item label="存入菜单ID" prop="powerArea">
          <el-input v-model="form.powerArea" placeholder="请输入存入菜单ID" />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" type="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listCameracp,
  getCameracp,
  delCameracp,
  addCameracp,
  updateCameracp,
  exportCameracp,
} from "@/api/monitoringcp/cameracp";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

import { getDept } from "@/api/grassrootsregistration/bdgleven";

export default {
  components: { Treeselect },
  name: "Cameracp",
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
      // 安防监控表格数据
      cameracpList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cname: null,
        caddr: null,
        cstatus: null,
        createtime: null,
        unitid: null,
        unitname: null,
        powerArea: null,
        belongLocal: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 单位
      deptOptions: [],
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    // 选择单位触发
    selectPeo(val) {
      this.form.unitname = val.label;
      this.form.belongLocal = val.id;
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.deptOptions = response.data;
      });
    },
    /** 查询安防监控列表 */
    getList() {
      this.loading = true;
      listCameracp(this.queryParams).then((response) => {
        this.cameracpList = response.rows;
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
        cname: null,
        caddr: null,
        cstatus: "0",
        createtime: null,
        unitid: null,
        unitname: null,
        powerArea: null,
        belongLocal: null,
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
      this.title = "添加安防监控";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getCameracp(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改安防监控";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateCameracp(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCameracp(this.form).then((response) => {
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
        .confirm("是否确认删除当前安防监控据项？")
        .then(function () {
          return delCameracp(ids);
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
        .confirm("是否确认导出所有安防监控数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportCameracp(queryParams);
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
}
::v-deep .el-textarea__inner {
  width: 920px;
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
  border-color: #fff !important;
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
  padding: 30px 50px;
}
::v-deep .el-dialog__body {
  margin: 10px 30px 20px 62px;
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
/* 刷新图标
.el-icon-refresh {
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
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 18px 42px 30px 0px;
  margin-right: 66px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 800px !important;
}
::v-deep .el-dialog__body {
  padding: none !important;
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
::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent;
  color: #fff;
}
.greed {
  position: absolute;
  top: 26px;
  right: -10px;
  color: #13ce66;
}
.CCC {
  position: absolute;
  top: 26px;
  right: 118px;
  color: #cccccc;
}
</style>
