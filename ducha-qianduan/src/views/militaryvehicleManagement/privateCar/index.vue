<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="vehiclenumber">
        <!-- <el-input
          v-model="queryParams.vehiclenumber"
          placeholder="请输入车牌号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select
          v-model="queryParams.peopleId"
          placeholder="请选择车辆所有人"
          @change="handleQuery"
          filterable
          class="input_xiala"
        >
          <el-option
            v-for="item in renYuans"
            :key="item.id"
            :label="item.name"
            :value="item.id"
            class="input_xiala"
          >
          </el-option>
        </el-select>
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
          v-hasPermi="['militaryvehicleManagement:privateCar:add']"
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
          v-hasPermi="['militaryvehicleManagement:privateCar:edit']"
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
          v-hasPermi="['militaryvehicleManagement:privateCar:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          class="box"
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['militaryvehicleManagement:privateCar:export']"
          >导出</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="privateCarList"
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
      <el-table-column label="车辆所有人" align="center" prop="peopleName" />
      <el-table-column label="车牌号" align="center" prop="vehiclenumber" />
      <el-table-column label="车辆型号" align="center" prop="vehiclemodel" />
      <el-table-column label="车辆颜色" align="center" prop="vehiclecolor" />
      <el-table-column
        label="车辆类型"
        align="center"
        prop="vehiclecategory"
        :formatter="carLei"
      />
      <el-table-column label="联系方式" align="center" prop="contactnumber" />
      <el-table-column
        label="证件类型"
        align="center"
        prop="documenttype"
        :formatter="zhenJian"
      />
      <el-table-column
        label="证件号"
        align="center"
        prop="certificatenumber"
        width="170"
      />
<!--      <el-table-column-->
<!--        label="车辆在位情况"-->
<!--        align="center"-->
<!--        prop="state"-->
<!--        width="100"-->
<!--        :formatter="zaiWei"-->
<!--      >-->
<!--        <template slot-scope="scope">-->
<!--          <span v-if="scope.row.state == 1" style="color: #13ce66">在位</span>-->
<!--          <span v-if="scope.row.state == 2" style="colo: #00eaff">值班</span>-->
<!--          <span v-if="scope.row.state == 3" style="color: #ff4949">出车</span>-->
<!--          <span v-if="scope.row.state == 4" style="color: #ffba00">待修</span>-->
<!--        </template>-->
      </el-table-column>
      <el-table-column
        label="备注"
        align="center"
        prop="remarks"
        show-overflow-tooltip
      />
      <!-- <el-table-column label="创建人" align="center" prop="adminId" />
      <el-table-column label="创建时间" align="center" prop="createtime" /> -->

      <!-- <el-table-column
        label="临时车辆所有人"
        align="center"
        prop="peopleName"
      />
      <el-table-column label="修改时间" align="center" prop="updatetime" />
      <el-table-column label="授权开始时间" align="center" prop="authStime" />
      <el-table-column label="授权结束时间" align="center" prop="authEtime" /> -->
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
            v-hasPermi="['militaryvehicleManagement:privateCar:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['militaryvehicleManagement:privateCar:remove']"
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

    <!-- 添加或修改私家车管理对话框 -->
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
        <el-form-item label="车辆类型" prop="vehiclecategory">
          <el-select
            v-model="form.vehiclecategory"
            placeholder="请输入车辆类型"
            clearable
          >
            <el-option
              v-for="(item, i) in privateCar"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="车辆所有人" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请输入车辆所有人"
            filterable
            clearable
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="suoYouRen(item.name)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="车牌号" prop="vehiclenumber">
          <el-input
            v-model="form.vehiclenumber"
            @blur="trim(form.vehiclenumber)"
            placeholder="请输入车牌号"
          />
        </el-form-item>
        <el-form-item label="车辆颜色" prop="vehiclecolor">
          <el-input v-model="form.vehiclecolor" placeholder="请输入车辆颜色" />
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
        <el-form-item label="车辆类别" prop="chelaingleibie">
          <el-select v-model="form.chelaingleibie" placeholder="请选择车辆类别">
            <el-option
              label="私家车"
              value="PRIVATE"
              class="input_xiala"
            ></el-option>
            <el-option
              label="军车"
              value="MILITARY"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系方式" prop="contactnumber">
          <el-input v-model="form.contactnumber" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="证件类型" prop="documenttype">
          <el-select
            v-model="form.documenttype"
            placeholder="请选择证件类型"
            clearable
          >
            <el-option
              v-for="(item, i) in documentType"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证件号" prop="certificatenumber">
          <el-input
            v-model="form.certificatenumber"
            placeholder="请输入证件号"
          />
        </el-form-item>
        <el-form-item label="授权开始时间" prop="authStime">
          <!-- <el-input v-model="form.authStime" placeholder="请输入授权开始时间" /> -->
          <el-date-picker
            clearable
            size="small"
            v-model="form.authStime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请输入授权开始时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="授权结束时间" prop="authEtime">
          <!-- <el-input v-model="form.authEtime" placeholder="请输入授权结束时间" /> -->
          <el-date-picker
            clearable
            size="small"
            v-model="form.authEtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请输入授权结束时间"
          >
          </el-date-picker>
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
  listPrivateCar,
  getPrivateCar,
  delPrivateCar,
  addPrivateCar,
  updatePrivateCar,
  exportPrivateCar,
  getRenYuanCar,
} from "@/api/militaryvehicleManagement/privateCar";
// console.log("listPrivateCar", listPrivateCar)
// 查询字典
import { getDicts } from "@/api/system/dict/data";
import { getAll } from "@/api/grassrootsregistration/bdglmeeting";
export default {
  name: "PrivateCar",
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
      // 私家车管理表格数据
      privateCarList: [],
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
        vehiclecategory: null,
        peopleId: null,
        peopleName: null,
        contactnumber: null,
        documenttype: null,
        certificatenumber: null,
        remarks: null,
        adminId: null,
        createtime: null,
        updatetime: null,
        authStime: null,
        authEtime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        adminId: [
          { required: true, message: "创建人不能为空", trigger: "blur" },
        ],
      },
      // 车辆类型
      privateCar: [],
      // 人员列表
      renYuan: [],
      // 证件类别
      documentType: [],
      // 外面的人员
      renYuans: [],
      //车辆状态
      private: [],
      //车辆型号
      brand: [],
    };
  },
  created() {
    this.getList();
    getDicts("privateCar").then((response) => {
      this.privateCar = response.data;
    });
    getDicts("documentType").then((response) => {
      this.documentType = response.data;
    });
    getDicts("privateStatus").then((response) => {
      this.private = response.data;
    });
    getDicts("brand").then((response) => {
      this.brand = response.data;
    });
    this.getSuo();
  },
  methods: {
    // 翻译
    zaiWei(rows) {
      var name = "";
      this.private.map((item) => {
        if (item.dictValue == rows.state) {
          name = item.dictLabel;
        }
      });
      return name;
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
    // 获取搜索所有人
    getSuo() {
      getAll().then((res) => {
        this.renYuans = res.data;
      });
    },
    // 证件类型翻译
    zhenJian(row, column) {
      var zhenJian = "";
      this.documentType.map((item) => {
        if (item.dictValue == row.documenttype) {
          zhenJian = item.dictLabel;
        }
      });
      return zhenJian;
    },
    // 车辆类型翻译
    carLei(row, column) {
      var carLei = "";
      this.privateCar.map((item) => {
        if (item.dictValue == row.vehiclecategory) {
          carLei = item.dictLabel;
        }
      });
      return carLei;
    },
    // 点击车辆所有人选中触发
    suoYouRen(name) {
      this.form.peopleName = name;
    },
    // 人员列表
    getRen() {
      getRenYuanCar().then((response) => {
        this.renYuan = response.data;
      });
    },
    /** 查询私家车管理列表 */
    getList() {
      this.loading = true;
      // console.log("listPrivateCar", listPrivateCar)
      console.log("请求参数是：", this.queryParams); // 👈 打印请求参数
      listPrivateCar(this.queryParams).then((response) => {
        console.log("后端返回的数据结构", response.rows);
        this.privateCarList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 车牌去空格 */
    trim(a) {
      console.log(this.form.vehiclenumber);
      var reg = /[\t\r\f\n\s]*/g;
      if (typeof a === "string") {
        var b = a.replace(reg, "");
      }
      this.form.vehiclenumber = b;
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
        vehiclecategory: null,
        peopleId: null,
        peopleName: null,
        contactnumber: null,
        documenttype: null,
        certificatenumber: null,
        remarks: null,
        adminId: null,
        createtime: null,
        updatetime: null,
        authStime: null,
        authEtime: null,
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
      this.queryParams.peopleId = null;
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
      this.getRen();
      this.reset();
      this.open = true;
      this.title = "添加私家车管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getRen();
      const id = row.id || this.ids;
      getPrivateCar(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改私家车管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updatePrivateCar(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPrivateCar(this.form).then((response) => {
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
        .confirm("是否确认删除私家车管理的数据项？")
        .then(function () {
          return delPrivateCar(ids);
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
        .confirm("是否确认导出所有私家车管理数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportPrivateCar(queryParams);
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
  width: 610px;
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
  width: 620px;
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
  padding: 30px 50px;
}
::v-deep .el-dialog__body {
  margin: 10px 24px 20px 30px;
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
  margin-right: 48px;
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
::v-deep .el-dialog__body {
  padding: 30px 63px;
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
/* 文本域提示文字颜色 */
::v-deep ::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}
.box {
  background-color: #ffba00 !important;
}
</style>
