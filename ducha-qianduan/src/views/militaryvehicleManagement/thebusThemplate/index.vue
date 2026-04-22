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
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="一级审批人" prop="people1Id">
        <el-input
          v-model="queryParams.people1Id"
          placeholder="请输入一级审批人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="二级审批人" prop="people2Id">
        <el-input
          v-model="queryParams.people2Id"
          placeholder="请输入二级审批人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="三级审批人" prop="people3Id">
        <el-input
          v-model="queryParams.people3Id"
          placeholder="请输入三级审批人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="三级审批人" prop="createtime">
        <el-input
          v-model="queryParams.createtime"
          placeholder="请输入三级审批人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="三级审批人" prop="updatetime">
        <el-input
          v-model="queryParams.updatetime"
          placeholder="请输入三级审批人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
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
        <el-button  size="btr" @click="resetQuery"
          >重置</el-button
        >
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
          v-hasPermi="['militaryvehicleManagement:thebusThemplate:add']"
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
          v-hasPermi="['militaryvehicleManagement:thebusThemplate:edit']"
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
          v-hasPermi="['militaryvehicleManagement:thebusThemplate:remove']"
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
          v-hasPermi="['militaryvehicleManagement:thebusThemplate:export']"
          >导出</el-button
        >
      </el-col> -->
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="thebusThemplateList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="三级审批人" align="center" prop="id" />
      <el-table-column label="审批模板名称" align="center" prop="name" />
      <el-table-column label="一级审批人" align="center" prop="people1Id" />
      <el-table-column label="二级审批人" align="center" prop="people2Id" />
      <el-table-column label="三级审批人" align="center" prop="people3Id" />
      <el-table-column label="三级审批人" align="center" prop="createtime" />
      <el-table-column label="三级审批人" align="center" prop="updatetime" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebusThemplate:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebusThemplate:remove']"
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

    <!-- 添加或修改军车审批模板对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="审批模板名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入审批模板名称" />
        </el-form-item>
        <el-form-item label="一级审批人" prop="people1Id">
          <el-input v-model="form.people1Id" placeholder="请输入一级审批人" />
        </el-form-item>
        <el-form-item label="二级审批人" prop="people2Id">
          <el-input v-model="form.people2Id" placeholder="请输入二级审批人" />
        </el-form-item>
        <el-form-item label="三级审批人" prop="people3Id">
          <el-input v-model="form.people3Id" placeholder="请输入三级审批人" />
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

export default {
  name: "ThebusThemplate",
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
      // 军车审批模板表格数据
      thebusThemplateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
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
    /** 查询军车审批模板列表 */
    getList() {
      this.loading = true;
      listThebusThemplate(this.queryParams).then((response) => {
        this.thebusThemplateList = response.rows;
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
            updateThebusThemplate(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addThebusThemplate(this.form).then((response) => {
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
        .confirm('是否确认删除军车审批模板编号为"' + ids + '"的数据项？')
        .then(function () {
          return delThebusThemplate(ids);
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
.box{
  background-color: #ffba00 !important;
}
</style>
