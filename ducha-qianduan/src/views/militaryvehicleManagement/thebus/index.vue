<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入所属单位"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item prop="vehiclenumber">
        <el-input
          v-model="queryParams.vehiclenumber"
          placeholder="请输入车牌号"
          clearable
          size="small"
        />
      </el-form-item>
      <!-- 查询 + 新增 + 批量删除 + 导出 一行 -->
      <el-form-item class="btns-form-item">
        <el-button size="btr" @click="handleQuery">查询</el-button>
        <el-button size="btr" @click="resetQuery">重置</el-button>

        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['militaryvehicleManagement:thebus:add']"
        >新增</el-button>

        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['militaryvehicleManagement:thebus:remove']"
        >批量删除</el-button>

        <el-button
          class="box"
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['medicalhealth:bdgldurgarchives:export']"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <!-- 原来的 el-row 按钮区域已删除 -->

    <el-table
      v-loading="loading"
      :data="thebusList"
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
      <el-table-column
        label="车牌号"
        align="center"
        prop="vehiclenumber"
        width="100"
      />
      <el-table-column
        label="车辆型号"
        align="center"
        prop="vehiclemodel"
        :formatter="vehicleModel"
        width="130"
      />
      <el-table-column
        label="车辆负责人"
        align="center"
        prop="peopleNames"
        width="120"
      />
      <el-table-column
        label="所属单位"
        align="center"
        prop="unitName"
        width="200px"
      />
      <el-table-column
        label="联系方式"
        align="center"
        prop="contactnumber"
        width="120"
      />
      <el-table-column label="发动机号" align="center" prop="engineNumber" width="150"/>
      <el-table-column label="车架号" align="center" prop="frameNumber" width="190"/>
      <el-table-column
        label="车辆来源"
        align="center"
        prop="source"
        width="200"
      />
      <el-table-column label="车辆颜色" align="center" prop="vehiclecolor" />
      <el-table-column label="出厂日期" align="center" prop="date1" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.date1, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="车辆在位情况"
        align="center"
        prop="status"
        width="100"
        :formatter="zaiWei"
      >
         <template slot-scope="scope">
          <span v-if="scope.row.status == 1" style="color: #13ce66">在位</span>
          <span v-if="scope.row.status == 2" style="colo: #00eaff">值班</span>
          <span v-if="scope.row.status == 3" style="color: #ff4949">出车</span>
          <span v-if="scope.row.status == 4" style="color: #ffba00">待修</span>
        </template>
      </el-table-column>
      <el-table-column label="所在位置" align="center" prop="position" />
      <el-table-column
        label="是否上牌"
        align="center"
        prop="isListing"
        :formatter="licensePlate"
      />
      <el-table-column
        label="固定座位数"
        align="center"
        prop="seatCount"
        width="120"
      />
      <el-table-column
        label="备注"
        align="center"
        prop="remarks"
        width="120"
        show-overflow-tooltip
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebus:edit']"
          >修改</el-button>
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebus:remove']"
          >删除</el-button>
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

    <!-- 添加或修改军车管理对话框 -->
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
        <el-form-item label="车属单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="treeList"
            placeholder="选择单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="车辆负责人" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请输入负责人"
            filterable
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.peopleName"
              :value="item.peopleId"
              @click.native="cheRen(item)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系方式" prop="contactnumber">
          <el-input
            v-model="form.contactnumber"
            placeholder="请输入联系方式"
            readonly
          />
        </el-form-item>
        <el-form-item label="车辆型号" prop="vehiclemodel">
          <el-select v-model="form.vehiclemodel" placeholder="请输入车辆型号">
            <el-option
              v-for="(item, i) in brand"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="车辆颜色" prop="vehiclecolor">
          <el-input v-model="form.vehiclecolor" placeholder="请输入车辆颜色" />
        </el-form-item>
        <el-form-item label="车位号" prop="parkingspacenumber">
          <el-input
            v-model="form.parkingspacenumber"
            placeholder="请输入车位号"
          />
        </el-form-item>
        <el-form-item label="发动机号" prop="engineNumber">
          <el-input v-model="form.engineNumber" placeholder="请输入发动机号" />
        </el-form-item>
        <el-form-item label="车架号" prop="frameNumber">
          <el-input v-model="form.frameNumber" placeholder="请输入车架号" />
        </el-form-item>
        <el-form-item label="车辆来源" prop="source">
          <el-input v-model="form.source" placeholder="请输入车辆来源" />
        </el-form-item>
        <el-form-item label="出厂日期" prop="date1">
          <el-date-picker
            clearable
            size="small"
            v-model="form.date1"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择出厂日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="ETC品牌" prop="etcBrand">
          <el-input v-model="form.etcBrand" placeholder="请输入ETC品牌" />
        </el-form-item>
        <el-form-item label="ETC编码" prop="etcNumber">
          <el-input v-model="form.etcNumber" placeholder="请输入ETC编码" />
        </el-form-item>
        <el-form-item label="所在位置" prop="position">
          <el-input v-model="form.position" placeholder="请输入所在位置" />
        </el-form-item>
        <el-form-item label="是否上牌" prop="isListing">
          <el-select v-model="form.isListing" placeholder="请输入是否上牌">
            <el-option
              v-for="(item, i) in license"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="车牌号" prop="vehiclenumber">
          <el-input v-model="form.vehiclenumber"  placeholder="请输入号码" />
        </el-form-item>
        <el-form-item label="固定座位数" prop="seatCount">
          <el-input v-model="form.seatCount" placeholder="请输入固定座位数" />
        </el-form-item>
        <el-form-item label="车辆类别" prop="chelaingleibie">
          <el-select v-model="form.chelaingleibie" placeholder="请选择车辆类别">
            <el-option label="私家车" value="PRIVATE" class="input_xiala"></el-option>
            <el-option label="军车" value="MILITARY" class="input_xiala"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审查结果" prop="shenhejieguo">
          <el-input v-model="form.shenhejieguo" placeholder="请输入审查结果" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select
            v-model="form.status"
            placeholder="请输入是否在位"
            :disabled="chuF"
          >
            <el-option
              v-for="(item, i) in military"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用途" prop="carUse">
          <el-input v-model="form.carUse" placeholder="请输入用途" />
        </el-form-item>
        <el-form-item label="排序" prop="orders">
          <el-input
            v-model="form.orders"
            oninput="value=value.replace(/[^\d]/g,'')"
            placeholder="请输入排序"
          />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item prop="remarks">
          <el-input
            v-model="form.remarks"
            type="textarea"
            placeholder="请输入内容"
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
  listThebus,
  getThebus,
  delThebus,
  addThebus,
  updateThebus,
  exportThebus,
  getDept,
  getRen,
} from "@/api/militaryvehicleManagement/thebus";
import { updatestatus } from "@/api/combatduty/militarydutyinfo";

// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
// 查询字典
import { getDicts } from "@/api/system/dict/data";
import {parseTime} from "../../../utils/supervision";

export default {
  name: "Thebus",
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
      // 军车管理表格数据
      thebusList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        vehiclenumber: null,
        vehiclemodel: null,
        vehiclecolor: null,
        parkingspacenumber: null,
        contactnumber: null,
        remarks: null,
        adminId: null,
        createtime: null,
        updatetime: null,
        status: null,
        unitId: null,
        peopleId: null,
        engineNumber: null,
        frameNumber: null,
        source: null,
        date1: null,
        etcBrand: null,
        etcNumber: null,
        position: null,
        isListing: null,
        seatCount: null,
        unitName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orders: [{ required: true, message: "排序不能为空", trigger: "blur" }],
      },
      // 树形列表
      treeList: [],
      // 人员列表
      renYuan: [],
      // 是否上牌
      license: [],
      // 车辆状态
      military: [],
      // 车辆型号
      brand: [],
      chuF: false,
    };
  },
  created() {
    this.getList();
    this.getCatPai();
    getDicts("license").then((response) => {
      this.license = response.data;
    });
    getDicts("military").then((response) => {
      this.military = response.data;
    });
  },
  methods: {
    parseTime,
    // 更改军车状态
    getStatuse() {
      updatestatus().then((res) => {});
    },
    // 翻译
    zaiWei(rows) {
      var name = "";
      this.military.map((item) => {
        if (item.dictValue == rows.status) {
          name = item.dictLabel;
        }
      });
      return name;
    },
    // 车辆型号
    getCatPai() {
      getDicts("brand").then((response) => {
        this.brand = response.data;
      });
    },
    // 重置人员
    ChongZhi() {
      (this.form.peopleId = null), (this.form.contactnumber = null);
    },
    // 翻译车辆型号
    vehicleModel(row, column) {
      var vehiclemodel = "";
      this.brand.map((item) => {
        if (item.dictValue == row.vehiclemodel) {
          vehiclemodel = item.dictLabel;
        }
      });
      return vehiclemodel;
    },
    // 翻译车辆在位情况
    vehiclePresence(row, column) {
      var status = "";
      this.military.map((item) => {
        if (item.dictValue == row.status) {
          status = item.dictLabel;
        }
      });
      return status;
    },
    // 翻译是否上牌
    licensePlate(row) {
      var isListing = "";
      this.license.map((item) => {
        if (item.dictValue == row.isListing) {
          isListing = item.dictLabel;
        }
      });
      return isListing;
    },
    // 选择车辆人员触发
    cheRen(item) {
      this.form.contactnumber = item.contact;
      this.form.peopleNames = item.peopleName;
    },
    // 选择部门单位触发
    selectPeo(data) {
      this.form.unitName = data.label;
      this.ChongZhi();
      this.getRen(data.id);
    },
    // 获取人员列表
    getRen(id) {
      getRen(id).then((res) => {
        this.renYuan = res.rows;
        console.log("人员", res.rows);
      });
    },
    //  获取单位列表
    getTreeList() {
      getDept().then((res) => {
        this.treeList = res.data;
      });
    },
    /** 查询军车管理列表 */
    getList() {
      this.loading = true;
      listThebus(this.queryParams).then((response) => {
        this.thebusList = response.rows;
        this.total = response.total;
        this.loading = false;
        this.getStatuse();
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
        vehiclenumber: null,
        vehiclemodel: null,
        vehiclecolor: null,
        parkingspacenumber: null,
        contactnumber: null,
        remarks: null,
        adminId: null,
        createtime: null,
        updatetime: null,
        status: null,
        unitId: null,
        peopleId: null,
        engineNumber: null,
        frameNumber: null,
        source: null,
        date1: null,
        etcBrand: null,
        etcNumber: null,
        position: null,
        isListing: null,
        seatCount: null,
        unitName: null,
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
      this.getTreeList();
      this.renYuan = [];
      this.open = true;
      this.chuF = false;
      this.title = "添加军车管理";
      this.form.status = "1";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeList();
      const id = row.id || this.ids;
      getThebus(id).then((response) => {
        this.form = response.data;
        this.getRen(this.form.unitId);
        if (this.form.status == "3") {
          this.chuF = true;
        } else {
          this.chuF = false;
        }
        this.open = true;
        this.title = "修改军车管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateThebus(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addThebus(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作（支持单条或批量） */
    handleDelete(row) {
      const ids = row && row.id ? row.id : this.ids;
      this.$modal
        .confirm("是否确认删除军车管理的数据项？")
        .then(function () {
          return delThebus(ids);
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
        .confirm("是否确认导出所有军车管理数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportThebus(queryParams);
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
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}

/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}

/* 查询区按钮一行显示 */
::v-deep .btns-form-item .el-form-item__content {
  width: auto !important;
  display: flex;
  align-items: center;
}

/* 查询区的小按钮宽度自适应，避免“批量删除”挤出 */
::v-deep .btns-form-item .el-button--mini {
  width: auto !important;
  padding: 3px 16px;
}

::v-deep .el-input__inner {
  cursor: pointer !important;
}

/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 30px 50px;
}

::v-deep .el-dialog__body {
  margin: 10px 24px 20px 34px;
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
  margin-right: 22px;
}

/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px !important;
}

::v-deep .el-dialog__body {
  padding: 30px 35px;
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

::v-deep .el-table .el-table__body .el-table__row.hover-row td {
  background-color: #004d86;
}

/* 文本域提示文字颜色 */
::v-deep ::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}

::v-deep .el-input__inner {
  border: 1px solid white !important;
}

.box {
  background-color: #ffba00 !important;
}

::v-deep .el-input.is-disabled .el-input__inner {
  background: transparent;
  color: #fff;
}
</style>
