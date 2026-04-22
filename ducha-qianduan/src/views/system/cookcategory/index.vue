<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:cookcategory:add']"
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
          v-hasPermi="['system:cookcategory:edit']"
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
          v-hasPermi="['system:cookcategory:remove']"
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
          v-hasPermi="['system:cookcategory:export']"
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
      :data="cookcategoryList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="修改时间" align="center" prop="id" /> -->
      <el-table-column label="食品分类" align="center" prop="typelist">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.food_type"
            :value="scope.row.typelist"
          />
        </template>
      </el-table-column>
      <el-table-column label="一日三餐" align="center" prop="timelist">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.three_meals"
            :value="scope.row.timelist"
          />
        </template>
      </el-table-column>
      <el-table-column label="食品名称" align="center" prop="name" />
      <!-- <el-table-column label="添加时间" align="center" prop="createtime" /> -->
      <el-table-column label="添加时间" align="center" prop="createtime">
        <template slot-scope="scope">
          {{ scope.row.createtime | dataFormat }}
        </template>
      </el-table-column>

      <el-table-column label="修改时间" align="center" prop="updatetime" />
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
            v-hasPermi="['system:cookcategory:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:cookcategory:remove']"
            >删除</el-button
          >
        </template></el-table-column
      ></el-table
    >

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改食品分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="食品分类" prop="typelist">
          <el-select v-model="form.typelist" placeholder="请选择食品分类">
            <el-option
              v-for="dict in dict.type.food_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="一日三餐" prop="timelist">
          <el-select v-model="form.timelist" placeholder="请选择一日三餐">
            <el-option
              v-for="dict in dict.type.three_meals"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="食品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入食品名称" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listCookcategory,
  getCookcategory,
  delCookcategory,
  addCookcategory,
  updateCookcategory,
  exportCookcategory,
} from "@/api/system/cookcategory";
import Cookies from "js-cookie";

export default {
  name: "Cookcategory",
  dicts: ["food_type", "three_meals"],
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
      // 食品分类表格数据
      cookcategoryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        typelist: null,
        timelist: null,
        name: null,
        createtime: null,
        updatetime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        typelist: [
          { required: true, message: "食品分类不能为空", trigger: "change" },
        ],
        timelist: [
          { required: true, message: "一日三餐不能为空", trigger: "change" },
        ],
        name: [
          { required: true, message: "食品名称不能为空", trigger: "blur" },
        ],
        createtime: [
          { required: true, message: "添加时间不能为空", trigger: "blur" },
        ],
        updatetime: [
          { required: true, message: "修改时间不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    // if (Cookies.get("shuaxin") != "true") {
    //   window.location.reload();
    // }
    // Cookies.set("shuaxin", "true");
    this.getList();
  },
  methods: {
    /** 查询食品分类列表 */
    getList() {
      this.loading = true;
      listCookcategory(this.queryParams).then((response) => {
        this.cookcategoryList = response.rows;
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
        typelist: null,
        timelist: null,
        name: null,
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
      this.title = "添加食品分类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getCookcategory(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改食品分类";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateCookcategory(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCookcategory(this.form).then((response) => {
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
        .confirm("是否确认删除食品分类的数据项？")
        .then(function () {
          return delCookcategory(ids);
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
        .confirm("是否确认导出所有食品分类数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportCookcategory(queryParams);
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
  margin: 200px auto !important;
}
::v-deep .el-dialog .el-input__inner {
  height: 36px;
}
::v-deep .el-input__inner {
  height: 28px;
  background: transparent;
  color: #fff !important;
}
::v-deep .el-dialog__title {
  line-height: 24px;
  font-size: 18px;
  color: #fff !important;
}
::v-deep .el-dialog__body {
  padding: 30px 100px;
  color: #606266;
  font-size: 14px;
  word-break: break-all;
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
</style>
