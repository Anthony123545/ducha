<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="durgBrand">
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
          v-hasPermi="['medicalhealth:bdgldurgputin:add']"
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
          v-hasPermi="['medicalhealth:bdgldurgputin:edit']"
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
          v-hasPermi="['medicalhealth:bdgldurgputin:remove']"
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
          v-hasPermi="['medicalhealth:bdgldurgputin:export']"
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
      :data="bdgldurgputinList"
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
      <!-- <el-table-column label="药品编码" align="center" prop="drugNo" /> -->
      <el-table-column label="药品名称" align="center" prop="durgName" />
      <el-table-column label="入库数量" align="center" prop="putIn" />
      <el-table-column label="入库位置" align="center" prop="place" />
      <el-table-column label="药品来源" align="center" prop="source" />
      <!-- <el-table-column label="入库说明" align="center" prop="remarks" /> -->
      <el-table-column label="过期日期" align="center" prop="overduedate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.overduedate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入库时间" align="center" prop="putTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.putTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="编号" align="center" prop="number" />
      <el-table-column label="剩余库存" align="center" prop="surplus" />
      <!-- <el-table-column label="是否销毁" align="center" prop="status" /> -->
      <el-table-column label="品牌" align="center" prop="durgBrand" />
      <el-table-column label="生产厂商" align="center" prop="manufacturer" />
      <el-table-column label="规格" align="center" prop="specifications" />
      <el-table-column label="库房" align="center" prop="storeHouse">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.storage_room" :value="scope.row.storeHouse" />
        </template>
      </el-table-column>
      <!-- <el-table-column label="库房" align="center" prop="unitId" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['medicalhealth:bdgldurgputin:edit']"
          >修改</el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medicalhealth:bdgldurgputin:remove']"
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

    <!-- 添加或修改药品入库登记对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" inline>
        <el-form-item label="药品编码" prop="number">
          <el-input v-model="form.number" @blur="getYaoPing" placeholder="请输入药品编码" />
        </el-form-item>

        <!-- <el-form-item label="药品编码" prop="drugNo">
          <el-input v-model="form.drugNo" placeholder="请输入药品编码" />
        </el-form-item>-->

        <el-form-item label="药品名称" prop="durgName">
          <el-input v-model="form.durgName" placeholder="请选择药品名称" />
          <!-- <el-select v-model="form.durgName" placeholder="请选择药品名称" filterable>
            <el-option
              class="input_xiala"
              v-for="(item, i) in yaoList"
              :key="i"
              :value="item.durgName"
              :label="item.yaoPinName"
              @click.native="yapPing(item)"
            ></el-option>
          </el-select>-->
        </el-form-item>
        <el-form-item label="药品来源" prop="source">
          <el-input v-model="form.source" placeholder="请输入药品来源" />
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
        <el-form-item label="入库时间" prop="putTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.putTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择入库时间"
          ></el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="入库说明" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入入库说明" />
        </el-form-item>-->
        <el-form-item label="过期日期" prop="overduedate">
          <el-date-picker
            clearable
            size="small"
            v-model="form.overduedate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择过期日期"
          ></el-date-picker>
        </el-form-item>
        <div class="jiben">入库说明</div>
        <el-form-item prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <div class="jiben" v-if="updateReasonshow">修改原因</div>
        <el-form-item prop="updateReason" v-if="updateReasonshow">
          <el-input v-model="form.updateReason" type="textarea" placeholder="请输入修改原因" />
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
  listBdgldurgputin,
  updateBdgldurgputin,
  delBdgldurgputin,
  addBdgldurgputin,
  exportBdgldurgputin,
  exportYao,
  getBdgldurgputin
} from "@/api/medicalhealth/bdgldurgputin";

export default {
  name: "Bdgldurgputin",
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
      // 药品入库登记表格数据
      bdgldurgputinList: [],
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
        updateReason: [
          { required: true, message: "修改原因不能为空", trigger: "blur" }
        ],
        number: [
          { required: true, message: "药品编码不能为空", trigger: "blur" }
        ]
      },
      // 药品列表
      yaoList: [],
      updateReasonshow: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 编号
    planName() {
      let date = new Date();
      let year = date.getFullYear();
      let month =
        date.getMonth() + 1 <= 9
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let day = date.getDate();
      let hour = date.getHours() <= 9 ? "0" + date.getHours() : date.getHours();
      let minute =
        date.getMinutes <= 9 ? "0" + date.getMinutes() : date.getMinutes();
      this.form.number = year + month + day + hour + minute;
    },
    // 药品名称触发赋值
    yapPing(data) {
      this.form.unitId = data.unitId;
      this.form.unitName = data.unitName;
      this.form.manufacturer = data.manufacturer;
      this.form.specifications = data.specifications;
      this.form.storeHouse = data.storeHouse;
      this.form.durgArchivesId = data.id;
      this.form.durgBrand = data.durgBrand;
      this.planName();
    },
    // 查询药品名称列表
    getYaoPing() {
      if (this.form.number) {
        exportYao({ drugNo: this.form.number }).then(response => {
          if (response.code == 200) {
            if (response.data) {
              this.form = response.data;
              this.form.putIn = null;

              // this.$set(this.form, "durgName", response.data.yaoPinName);
              this.form.durgName = response.data.yaoPinName;

              this.form.durgArchivesId = response.data.id;
              this.form.id = null;
              this.form.remarks = null;
              this.$set(this.form, "number", response.data.drugNo);
            } else {
              this.$modal.msgSuccess("未有该药品编码!");
              this.reset();
            }
          }
        });
      }
    },
    /** 查询药品入库登记列表 */
    getList() {
      this.loading = true;
      listBdgldurgputin(this.queryParams).then(response => {
        this.bdgldurgputinList = response.rows;
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
      this.reset();
      // this.getYaoPing();
      this.open = true;
      this.updateReasonshow = false;
      this.title = "添加药品入库登记";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;

      getBdgldurgputin(id).then(response => {
        this.form = response.data;
        // this.getYaoPing();
        this.open = true;
        this.updateReasonshow = true;
        this.title = "修改药品入库登记";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBdgldurgputin(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.surplus = this.form.putIn;
            addBdgldurgputin(this.form).then(response => {
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
        .confirm("是否确认删除药品入库登记的数据项？")
        .then(function() {
          return delBdgldurgputin(ids);
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
        .confirm("是否确认导出所有药品入库登记数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBdgldurgputin(queryParams);
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
