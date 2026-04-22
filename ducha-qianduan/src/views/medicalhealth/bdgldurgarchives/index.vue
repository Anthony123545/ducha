<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="durgName">
        <el-input
          v-model="queryParams.durgName"
          placeholder="请输入药品名称"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <!-- <el-button type="primary" size="btn" @click="handleQuery"
          >搜索</el-button
        >-->
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
          v-hasPermi="['medicalhealth:bdgldurgarchives:add']"
        >新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['medicalhealth:bdgldurgarchives:edit']"
          >修改</el-button
        >
      </el-col>-->
      <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['medicalhealth:bdgldurgarchives:remove']"
        >删除</el-button>
      </el-col>-->
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['medicalhealth:bdgldurgarchives:export']"
          >导出</el-button
        >
      </el-col>-->
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>-->
    </el-row>

    <el-table
      v-loading="loading"
      :data="bdgldurgarchivesList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center">
        <template scope="scope">
          <span>
            {{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
            }}
          </span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="单位" align="center" prop="unitName" /> -->
      <el-table-column label="药品编码" align="center" prop="drugNo" />
      <el-table-column label="药品名称" align="center" prop="durgName" />
      <el-table-column label="品牌" align="center" prop="durgBrand" />
      <el-table-column label="规格" align="center" prop="specifications" />
      <el-table-column label="厂家" align="center" prop="manufacturer" />

      <el-table-column
        label="功能主治"
        align="center"
        prop="productIntroduction"
        show-overflow-tooltip
      />
      <el-table-column label="库房" align="center" prop="storeHouse" :formatter="kuFang" />

      <el-table-column label="库存数量" align="center" prop="amount" />
      <el-table-column label="入库数量" align="center" prop="putIn" />
      <el-table-column label="出库数量" align="center" prop="putOut" />
      <el-table-column label="过期数量" align="center" prop="overdue" />
      <!-- <el-table-column label="录入人" align="center" prop="userName" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['medicalhealth:bdgldurgarchives:edit']"
          >修改</el-button>
          <!-- <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medicalhealth:bdgldurgarchives:remove']"
          >删除</el-button>-->
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

    <!-- 添加或修改药品库存档案对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" inline>
        <el-form-item label="药品编码" prop="drugNo">
          <el-input
            v-model="form.drugNo"
            :disabled="disabled"
            placeholder="请输入药品编码"
            @blur="No(form.drugNo)"
          />
        </el-form-item>

        <el-form-item label="药品名称" prop="durgName">
          <el-input v-model="form.durgName" placeholder="请输入药品名称" />
        </el-form-item>
        <!-- <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="deptOptions"
            placeholder="选择单位"
          />
        </el-form-item>-->
        <el-form-item label="库房" prop="storeHouse">
          <el-select v-model="form.storeHouse" placeholder="请选择库房">
            <el-option
              class="input_xiala"
              v-for="(item, i) in warehouse"
              :key="i"
              :value="item.dictValue"
              :label="item.dictLabel"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="品牌" prop="durgBrand">
          <el-input v-model="form.durgBrand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="规格" prop="specifications">
          <el-input v-model="form.specifications" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="厂家" prop="manufacturer">
          <el-input v-model="form.manufacturer" placeholder="请输入厂家" />
        </el-form-item>
        <div class="jiben">功能主治</div>
        <el-form-item prop="productIntroduction">
          <el-input v-model="form.productIntroduction" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <!-- <el-form-item label="库存数量" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入库存数量" />
        </el-form-item>
        <el-form-item label="入库数量" prop="putIn">
          <el-input v-model="form.putIn" placeholder="请输入入库数量" />
        </el-form-item>
        <el-form-item label="出库数量" prop="putOut">
          <el-input v-model="form.putOut" placeholder="请输入出库数量" />
        </el-form-item>
        <el-form-item label="过期数量" prop="overdue">
          <el-input v-model="form.overdue" placeholder="请输入过期数量" />
        </el-form-item>-->

        <!-- <el-form-item label="录入人(创建人)" prop="adminId">
          <el-input v-model="form.adminId" placeholder="请输入录入人(创建人)" />
        </el-form-item>-->
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
  listBdgldurgarchives,
  getBdgldurgarchives,
  delBdgldurgarchives,
  addBdgldurgarchives,
  updateBdgldurgarchives,
  exportBdgldurgarchives,
  getDept
} from "@/api/medicalhealth/bdgldurgarchives";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
// 查询字典
import { getDicts } from "@/api/system/dict/data";

export default {
  name: "Bdgldurgarchives",
  components: {
    Treeselect
  },
  dicts: ["storage_room"],
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
      // 药品库存档案表格数据
      bdgldurgarchivesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        drugNo: null,
        durgName: null,
        durgBrand: null,
        manufacturer: null,
        specifications: null,
        productIntroduction: null,
        storeHouse: null,
        unitId: null,
        amount: null,
        putIn: null,
        putOut: null,
        overdue: null,
        remarks: null,
        adminId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        adminId: [
          {
            required: true,
            message: "录入人(创建人)不能为空",
            trigger: "blur"
          }
        ]
      },
      // 树形单位
      deptOptions: [],
      // 库房
      warehouse: [],
      disabled: false
    };
  },
  created() {
    this.getList();
    getDicts("storage_room").then(response => {
      this.warehouse = response.data;
    });
  },
  methods: {
    // 字典查询库房翻译
    kuFang(row) {
      var kufang = "";
      this.warehouse.map(item => {
        if (item.dictValue == row.storeHouse) {
          kufang = item.dictLabel;
        }
      });
      return kufang;
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 查询药品库存档案列表 */
    getList() {
      this.loading = true;
      listBdgldurgarchives(this.queryParams).then(response => {
        this.bdgldurgarchivesList = response.rows;
        console.log(response.rows);
        this.total = response.total;
        this.loading = false;
      });
    },

    getLists() {
      this.loading = true;
      console.log(this.form);
      const durgName = this.form.durgName;
      listBdgldurgarchives(durgName).then(response => {
        this.total = response.total;
        if (response.total > 0) {
          this.$modal.msgSuccess("该药品以录入");
        }
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 药品编码
    No(a) {
      if (a) {
        this.loading = true;
        listBdgldurgarchives({ drugNo: this.form.drugNo }).then(response => {
          this.total = response.total;
          this.loading = false;
          if (response.total > 0) {
            this.$modal.msgError("请勿重复录入");
          }
        });
      }
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        drugNo: null,
        durgName: null,
        durgBrand: null,
        manufacturer: null,
        specifications: null,
        productIntroduction: null,
        storeHouse: null,
        unitId: null,
        amount: null,
        putIn: null,
        putOut: null,
        overdue: null,
        remarks: null,
        adminId: null,
        createTime: null,
        updateTime: null
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getTreeselect();
      this.reset();
      this.open = true;
      this.title = "添加药品库存档案";
      this.disabled = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids;
      getBdgldurgarchives(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改药品库存档案";
        this.disabled = false;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBdgldurgarchives(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBdgldurgarchives(this.form).then(response => {
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
      this.$confirm("是否确认删除药品库存档案的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delBdgldurgarchives(ids);
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
      this.$confirm("是否确认导出所有药品库存档案数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.exportLoading = true;
          return exportBdgldurgarchives(queryParams);
        })
        .then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    }
  }
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
/* 刷新图标 */
/* .el-icon-refresh {
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
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 18px 42px 30px 0px;
  margin-right: 30px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px !important;
}
::v-deep .el-dialog__body {
  padding: none !important;
}
::v-deep .box4 .el-textarea__inner {
  min-height: 120px !important;
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
  background: #004d86;
  color: #fff;
}
</style>
