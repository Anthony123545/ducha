<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="" prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入单位名称"
          clearable
          size="small"
          @input="handleQuery"
        />
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
          v-hasPermi="['barracksManagement:maintenanceRecord:add']"
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
          v-hasPermi="['barracksManagement:maintenanceRecord:edit']"
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
          v-hasPermi="['barracksManagement:maintenanceRecord:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['barracksManagement:maintenanceRecord:export']"
          >导出</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="maintenanceRecordList"
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
      <el-table-column
        label="单位名称"
        align="center"
        prop="unitName"
        width="200"
      />
      <el-table-column label="负责人" align="center" prop="personCharge" />
      <el-table-column label="材料" align="center" prop="materialScience" />
      <el-table-column label="发现时间" align="center" prop="time" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.time, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="整改时间"
        align="center"
        prop="rectificationTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.rectificationTime, "{y}-{m}-{d}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="问题"
        align="center"
        prop="problem"
        show-overflow-tooltip
      />
      <el-table-column
        label="备注"
        align="center"
        prop="remark"
        show-overflow-tooltip
      />
      <el-table-column label="经费情况" align="center" prop="funding" />
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
            v-hasPermi="['barracksManagement:maintenanceRecord:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['barracksManagement:maintenanceRecord:remove']"
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

    <!-- 添加或修改五小工维修记录对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="900px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <el-form-item label="单位名称" prop="unitName">
          <treeselect
            v-model="form.unitId"
            :options="treeList"
            placeholder="选择单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="负责人" prop="personCharge">
          <el-input v-model="form.personCharge" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="发现时间" prop="time">
          <el-date-picker
            clearable
            size="small"
            v-model="form.time"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择发现时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="整改时间" prop="rectificationTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.rectificationTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择整改时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="材料" prop="materialScience">
          <el-input v-model="form.materialScience" placeholder="请输入材料" />
        </el-form-item>
        <el-form-item label="经费情况" prop="funding">
          <el-input v-model="form.funding" placeholder="请输入经费情况" />
        </el-form-item>
        <div class="jiben">问题</div>
        <el-form-item label="" prop="problem">
          <el-input
            v-model="form.problem"
            type="textarea"
            placeholder="请输入内容"
            autosize
          />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item label="" prop="remark">
          <el-input
            v-model="form.remark"
            placeholder="请输入备注"
            type="textarea"
            autosize
          />
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
  listMaintenanceRecord,
  getMaintenanceRecord,
  delMaintenanceRecord,
  addMaintenanceRecord,
  updateMaintenanceRecord,
  exportMaintenanceRecord,
} from "@/api/barracksManagement/maintenanceRecord";
import { getDept } from "@/api/militaryvehicleManagement/thebus";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "MaintenanceRecord",
  components: {
    Treeselect,
  },
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
      // 五小工维修记录表格数据
      maintenanceRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitName: null,
        unitId: null,
        problem: null,
        time: null,
        personCharge: null,
        rectificationTime: null,
        materialScience: null,
        funding: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 树形列表
      treeList: [],
      // 人员列表
      renYuan: [],
    };
  },
  created() {
    this.getList();
    this.getTreeList();
  },
  methods: {
    // 选择部门单位触发
    selectPeo(data) {
      this.form.unitName = data.label;
    },
    //  获取单位列表
    getTreeList() {
      getDept().then((res) => {
        this.treeList = res.data;
      });
    },
    /** 查询五小工维修记录列表 */
    getList() {
      this.loading = true;
      listMaintenanceRecord(this.queryParams).then((response) => {
        this.maintenanceRecordList = response.rows;
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
        unitName: null,
        unitId: null,
        problem: null,
        time: null,
        personCharge: null,
        rectificationTime: null,
        materialScience: null,
        remark: null,
        funding: null,
        createTime: null,
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
      this.title = "添加五小工维修记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getTreeList();
      this.reset();
      const id = row.id || this.ids;
      getMaintenanceRecord(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改五小工维修记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateMaintenanceRecord(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMaintenanceRecord(this.form).then((response) => {
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
        .confirm("是否确认删除五小工维修记录")
        .then(function () {
          return delMaintenanceRecord(ids);
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
        .confirm("是否确认导出所有五小工维修记录数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportMaintenanceRecord(queryParams);
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
  width: 920px;
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
  padding: 18px 50px !important;
  margin-right: 24px !important;
}
::v-deep .el-dialog__body {
  margin: 10px 0px 20px 51px;
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
.el-select-dropdown__item:hover {
  color: #000 !important;
}
.el-select-dropdown__item {
  color: #000 !important;
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
  margin-right: 42px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px !important;
}
::v-deep .el-dialog__body {
  padding: none !important;
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
/* ::v-deep .el-table__body tr.hover-row > td.el-table__cell {
  background-color: none;
} */
::v-deep .el-table .el-table__body .el-table__row.hover-row td {
  background-color: #004d86;
}
::v-deep .el-table__fixed-right {
  height: 100% !important;
}
::v-deep .vue-treeselect__control {
  background-color: transparent !important;
}
::v-deep .el-form-item__content .el-textarea .el-textarea__inner {
  width: 916px;
  min-height: 120px !important;
}
</style>