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
          placeholder="请输入耗材名称"
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
      :data="putinList"
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
      <el-table-column label="耗材名称" align="center" prop="durgName" />
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="库房" align="center" prop="storeHouse">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.storage_room" :value="scope.row.storeHouse" />
        </template>
      </el-table-column>
      <el-table-column label="品牌" align="center" prop="durgBrand" />
      <el-table-column label="生产厂商" align="center" prop="manufacturer" />
      <el-table-column label="规格" align="center" prop="specifications" />
      <el-table-column label="入库数量" align="center" prop="putIn" />
      <el-table-column label="入库位置" align="center" prop="place" />
      <el-table-column label="剩余库存" align="center" prop="surplus" />
      <el-table-column label="耗材来源" align="center" prop="source" />
      <!-- <el-table-column label="入库说明" align="center" prop="remarks" /> -->
      <el-table-column label="录入人" align="center" prop="userName" />
      <el-table-column label="入库时间" align="center" prop="putTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.putTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="过期日期" align="center" prop="overduedate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.overduedate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>

      <!--
      <el-table-column label="编号" align="center" prop="number" />
      <el-table-column label="剩余库存" align="center" prop="surplus" />
      <el-table-column label="是否销毁" align="center" prop="status" />-->

      <!-- <el-table-column label="功能主治" align="center" prop="productIntroduction" /> -->

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button size="btu" type="text" @click="handleUpdate(scope.row)">修改</el-button>
          <!-- <el-button
            size="mini"
            type="btd"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:putin:remove']"
            >删除</el-button
          >-->
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

    <!-- 添加或修改耗材入库登记对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="700px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :inline="true">
        <div class="jiben">基本信息</div>
        <el-form-item label="耗材编码" prop="number">
          <el-input v-model="form.number" @blur="getHaoCai" placeholder="请输入耗材编码" />
        </el-form-item>
        <el-form-item label="耗材名称" prop="durgName">
          <el-input v-model="form.durgName" placeholder="请输入耗材名称" />
          <!-- <el-select v-model="form.durgName" placeholder="请选择耗材名称" filterable>
            <el-option
              v-for="(item, i) in Consumables"
              :key="i"
              :label="item.name"
              :value="item.durgName"
              @click.native="haocailie(item)"
            ></el-option>
          </el-select>-->
        </el-form-item>
        <el-form-item label="入库数量" prop="putIn">
          <el-input-number
            v-model="form.putIn"
            controls-position="right"
            :min="0"
            placeholder="请输入入库数量"
          ></el-input-number>
          <!-- <el-input v-model="form.putIn" placeholder="请输入入库数量" /> -->
        </el-form-item>
        <el-form-item label="入库位置" prop="place">
          <el-input v-model="form.place" placeholder="请输入入库位置" />
        </el-form-item>
        <el-form-item label="耗材来源" prop="source">
          <el-input v-model="form.source" placeholder="请输入耗材来源" />
        </el-form-item>
        <el-form-item label="入库时间" prop="putTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.putTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择入库时间"
            :editable="false"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="过期日期" prop="overduedate">
          <el-date-picker
            clearable
            size="small"
            v-model="form.overduedate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择过期日期"
            :editable="false"
          ></el-date-picker>
        </el-form-item>
        <div class="jiben">入库说明</div>
        <el-form-item label prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
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
  listPutin,
  getPutin,
  delPutin,
  addPutin,
  updatePutin,
  exportPutin,
  exportHaoCai
} from "@/api/medicalhealth/consumeputin";

export default {
  name: "Putin",
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
      // 耗材入库登记表格数据
      putinList: [],
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
        place: null,
        source: null,
        remarks: null,
        overduedate: null,
        putTime: null,
        adminId: null,
        number: null,
        surplus: null,
        status: null,
        durgBrand: null,
        manufacturer: null,
        specifications: null,
        productIntroduction: null,
        storeHouse: null,
        unitId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        durgArchivesId: [
          {
            required: true,
            message: "耗材名称不能为空",
            trigger: "blur"
          }
        ],
        unitId: [
          {
            required: true,
            message: "入库数量不能为空",
            trigger: "blur"
          }
        ],
        storeHouse: [
          {
            required: true,
            message: "入库位置不能为空",
            trigger: "blur"
          }
        ],
        number: [
          {
            required: true,
            message: "耗材编码不能为空",
            trigger: "blur"
          }
        ]
      },
      // 耗材列表
      Consumables: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 选中耗材
    haocailie(data) {
      this.form.unitId = data.unitId;
      this.form.unitName = data.unitName;
      this.form.durgArchivesId = data.id;
      this.form.durgBrand = data.durgBrand;
      this.form.manufacturer = data.manufacturer;
      this.form.specifications = data.specifications;
      this.form.storeHouse = data.storeHouse;
    },
    // 获取耗材列表
    getHaoCai() {
      if (this.form.number) {
        exportHaoCai({ barCode: this.form.number }).then(res => {
          if (res.code == 200) {
            if (res.data) {
              this.form = res.data;
              this.$set(this.form, "durgName", res.data.durgName);
              this.form.durgArchivesId = res.data.id;
              this.form.putIn = null;
              this.form.remarks = null;
              this.form.id = null;
              this.$set(this.form, "number", res.data.barCode);
            } else {
              this.$modal.msgSuccess("未有该耗材编码!");
              this.reset();
            }
          }
        });
      }
    },
    /** 查询耗材入库登记列表 */
    getList() {
      this.loading = true;
      listPutin(this.queryParams).then(response => {
        this.putinList = response.rows;
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
        place: null,
        source: null,
        remarks: null,
        overduedate: null,
        putTime: null,
        adminId: null,
        createTime: null,
        updateTime: null,
        number: null,
        surplus: null,
        status: "0",
        durgBrand: null,
        manufacturer: null,
        specifications: null,
        productIntroduction: null,
        storeHouse: null,
        unitId: null
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
      // this.getHaoCai();
      this.reset();
      this.open = true;
      this.title = "添加耗材入库登记";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPutin(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改耗材入库登记";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePutin(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.surplus = this.form.putIn;
            addPutin(this.form).then(response => {
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
        .confirm("是否确认删除耗材入库登记的数据项？")
        .then(function() {
          return delPutin(ids);
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
        .confirm("是否确认导出所有耗材入库登记数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportPutin(queryParams);
        })
        .then(response => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    }
  }
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
  width: 610px;
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
::v-deep .el-dialog__body {
  padding: 30px 0px 0px 42px;
}
::v-deep .el-dialog__footer {
  margin-right: 30px;
}
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
</style>
