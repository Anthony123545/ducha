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
          v-hasPermi="['peopleManage:typestatistics:add']"
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
          v-hasPermi="['peopleManage:typestatistics:edit']"
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
          v-hasPermi="['peopleManage:typestatistics:remove']"
          >删除</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['peopleManage:typestatistics:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="typestatisticsList"
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
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column
        label="干部编制人数"
        align="center"
        prop="inpreparationGb"
      />
      <el-table-column
        label="士官编制人数"
        align="center"
        prop="inpreparationSg"
      />
      <el-table-column
        label="义务兵编制人数"
        align="center"
        prop="inpreparationYwb"
      />
      <el-table-column
        label="干部在位人数"
        align="center"
        prop="incumbentsGb"
      />
      <el-table-column
        label="士官在位人数"
        align="center"
        prop="incumbentsSg"
      />
      <el-table-column
        label="义务兵在位人数"
        align="center"
        prop="incumbentsYwb"
      />
      <el-table-column label="干部超" align="center" prop="outpreparationGb" />
      <el-table-column label="士官超" align="center" prop="outpreparationSg" />
      <el-table-column
        label="义务兵超"
        align="center"
        prop="outpreparationYwb"
      />
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
            v-hasPermi="['peopleManage:typestatistics:edit']"
            >修改</el-button
          >
          <el-button
          size="btd"
          type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['peopleManage:typestatistics:remove']"
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

    <!-- 添加或修改人员类别数量对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        inline
      >
        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="rese"
            placeholder="选择单位"
          />
        </el-form-item>
        <el-form-item label="干部编制人数" prop="inpreparationGb">
          <el-input
            v-model="form.inpreparationGb"
            placeholder="请输入干部编制人数"
          />
        </el-form-item>
        <el-form-item label="士官编制人数" prop="inpreparationSg">
          <el-input
            v-model="form.inpreparationSg"
            placeholder="请输入士官编制人数"
          />
        </el-form-item>
        <el-form-item label="义务兵编制人数" prop="inpreparationYwb">
          <el-input
            v-model="form.inpreparationYwb"
            placeholder="请输入义务兵编制人数"
          />
        </el-form-item>
        <el-form-item label="干部在位人数" prop="incumbentsGb">
          <el-input
            v-model="form.incumbentsGb"
            placeholder="请输入干部在位人数"
          />
        </el-form-item>
        <el-form-item label="士官在位人数" prop="incumbentsSg">
          <el-input
            v-model="form.incumbentsSg"
            placeholder="请输入士官在位人数"
          />
        </el-form-item>
        <el-form-item label="义务兵在位人数" prop="incumbentsYwb">
          <el-input
            v-model="form.incumbentsYwb"
            placeholder="请输入义务兵在位人数"
          />
        </el-form-item>
        <el-form-item label="干部超" prop="outpreparationGb">
          <el-input
            v-model="form.outpreparationGb"
            placeholder="请输入干部超"
          />
        </el-form-item>
        <el-form-item label="士官超" prop="outpreparationSg">
          <el-input
            v-model="form.outpreparationSg"
            placeholder="请输入士官超"
          />
        </el-form-item>
        <el-form-item label="义务兵超" prop="outpreparationYwb">
          <el-input
            v-model="form.outpreparationYwb"
            placeholder="请输入义务兵超"
          />
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
  listTypestatistics,
  getTypestatistics,
  delTypestatistics,
  addTypestatistics,
  updateTypestatistics,
} from "@/api/peopleManage/typestatistics";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getDept } from "@/api/grassrootsregistration/bdgleven";

export default {
  components: { Treeselect },
  name: "Typestatistics",
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
      // 人员类别数量表格数据
      typestatisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitId: null,
        inpreparationGb: null,
        inpreparationSg: null,
        inpreparationYwb: null,
        incumbentsGb: null,
        incumbentsSg: null,
        incumbentsYwb: null,
        outpreparationGb: null,
        outpreparationSg: null,
        outpreparationYwb: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitId: [
          { required: true, message: "单位id不能为空", trigger: "blur" },
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" },
        ],
        createBy: [
          { required: true, message: "创建者不能为空", trigger: "blur" },
        ],
      },
      // 树形
      rese: [],
    };
  },
  created() {
    this.getList();
    this.getTreeselect()
  },
  methods: {
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.rese = response.data;
        console.log(response.data);
      });
    },
    /** 查询人员类别数量列表 */
    getList() {
      this.loading = true;
      listTypestatistics(this.queryParams).then((response) => {
        this.typestatisticsList = response.rows;
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
        unitId: null,
        inpreparationGb: null,
        inpreparationSg: null,
        inpreparationYwb: null,
        incumbentsGb: null,
        incumbentsSg: null,
        incumbentsYwb: null,
        outpreparationGb: null,
        outpreparationSg: null,
        outpreparationYwb: null,
        createTime: null,
        createBy: null,
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
      this.title = "添加人员类别数量";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTypestatistics(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改人员类别数量";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTypestatistics(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTypestatistics(this.form).then((response) => {
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
        .confirm('是否确认删除人员类别数量的数据项？')
        .then(function () {
          return delTypestatistics(ids);
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
        "peopleManage/typestatistics/export",
        {
          ...this.queryParams,
        },
        `typestatistics_${new Date().getTime()}.xlsx`
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
  margin: 10px 30px 20px 50px;
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
  margin-right: 30px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 760px !important;
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
#chakan /deep/ .el-dialog__body {
  padding: 30px 0px 30px 5px;
}
</style>
