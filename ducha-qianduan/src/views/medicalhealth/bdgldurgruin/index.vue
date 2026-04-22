<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="durgArchivesId">
        <el-input
          v-model="queryParams.durgBrand"
          placeholder="请输入品牌"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <!-- <el-button type="primary" size="btn" @click="handleQuery"
          >搜索</el-button
        > -->
        <el-button size="btr" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['medicalhealth:bdgldurgruin:add']"
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
          v-hasPermi="['medicalhealth:bdgldurgruin:edit']"
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
          v-hasPermi="['medicalhealth:bdgldurgruin:remove']"
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
          v-hasPermi="['medicalhealth:bdgldurgruin:export']"
          >导出</el-button
        >
      </el-col>
    </el-row> -->

    <el-table
      v-loading="loading"
      :data="bdgldurgruinList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="药品名称"
        align="center"
        prop="durgArchivesId"
        :formatter="yao"
      />
      <el-table-column label="厂家" align="center" prop="manufacturer" />
      <el-table-column label="规格" align="center" prop="specifications" />
      <el-table-column label="品牌" align="center" prop="durgBrand" />
      <el-table-column label="销毁数量" align="center" prop="putIn" />
      <el-table-column label="销毁时间" align="center" prop="createtime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="销毁时间" align="center" prop="putinId" /> -->
      <!-- <el-table-column label="销毁时间" align="center" prop="status" /> -->
      <!-- <el-table-column
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
            v-hasPermi="['medicalhealth:bdgldurgruin:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medicalhealth:bdgldurgruin:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column> -->
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改药品销毁记录对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="700px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="120px"
        :inline="true"
      >
        <el-form-item label="销毁时间" prop="durgArchivesId">
          <el-input
            v-model="form.durgArchivesId"
            placeholder="请输入销毁时间"
            :editable="false"
          />
        </el-form-item>
        <el-form-item label="销毁数量" prop="putIn">
          <el-input v-model="form.putIn" placeholder="请输入销毁数量" />
        </el-form-item>
        <el-form-item label="厂家" prop="manufacturer">
          <el-input v-model="form.manufacturer" placeholder="请输入厂家" />
        </el-form-item>
        <el-form-item label="规格" prop="specifications">
          <el-input v-model="form.specifications" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="品牌" prop="durgBrand">
          <el-input v-model="form.durgBrand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="品牌" prop="putinId">
          <el-input v-model="form.putinId" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="品牌">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
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
  listBdgldurgruin,
  getBdgldurgruin,
  delBdgldurgruin,
  addBdgldurgruin,
  updateBdgldurgruin,
  exportBdgldurgruin,
} from "@/api/medicalhealth/bdgldurgruin";
import { listBdgldurgarchives } from "@/api/medicalhealth/bdgldurgarchives";

export default {
  name: "Bdgldurgruin",
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
      // 药品销毁记录表格数据
      bdgldurgruinList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        durgArchivesId: null,
        putIn: null,
        manufacturer: null,
        specifications: null,
        durgBrand: null,
        createtime: null,
        putinId: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        durgArchivesId: [
          {
            required: true,
            message: "销毁时间不能为空",
            trigger: "blur",
          },
        ],
        putIn: [
          {
            required: true,
            message: "销毁数量不能为空",
            trigger: "blur",
          },
        ],
      },
      // 获取药品名称
      yaoList: [],
    };
  },
  created() {
    this.getList();
    this.getYaoPingMingCheng();
  },
  methods: {
    // 翻译药品名称
    yao(row) {
      var yao = "";
      this.yaoList.map((item) => {
        if (item.id == row.durgArchivesId) {
          yao = item.durgName;
        }
      });
      return yao;
    },
    // 获取药品名称
    getYaoPingMingCheng() {
      listBdgldurgarchives().then((response) => {
        this.yaoList = response.rows;
      });
    },
    /** 查询药品销毁记录列表 */
    getList() {
      this.loading = true;
      listBdgldurgruin(this.queryParams).then((response) => {
        this.bdgldurgruinList = response.rows;
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
        durgArchivesId: null,
        putIn: null,
        manufacturer: null,
        specifications: null,
        durgBrand: null,
        createtime: null,
        putinId: null,
        status: 0,
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
      this.queryParams.durgBrand = null;
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
      this.title = "添加药品销毁记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getBdgldurgruin(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改药品销毁记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateBdgldurgruin(this.form).then((response) => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBdgldurgruin(this.form).then((response) => {
              this.msgSuccess("新增成功");
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
      this.$confirm("是否确认删除药品销毁记录的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delBdgldurgruin(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有药品销毁记录数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.exportLoading = true;
          return exportBdgldurgruin(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
  },
};
</script>
<style scoped>
/* 设置按钮透明边框  */
.el-button {
  border: 1px solid transparent;
}
.el-button--warning.is-plain {
  width: auto;
}
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
}
::v-deep .el-textarea__inner {
  width: 660px;
  height: 104px;
  margin: auto !important;
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
/* 输入框显示手指 */
::v-deep .el-input__inner {
  cursor: pointer !important;
}
</style>
