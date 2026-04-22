<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!-- <el-input
          v-model="queryParams.privateCarId"
          placeholder="请输入车牌号"
          clearable
          size="small"
          @input="handleQuery"
      />-->
      <el-form-item label prop="vehiclenumber">
        <el-input
          v-model="queryParams.vehiclenumber"
          placeholder="请输入车牌号"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label prop="peopleName">
        <el-input
          v-model="queryParams.peopleName"
          placeholder="请输入姓名"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>-->
      <el-form-item label prop="jinrushiajin">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.jinrushiajin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          @change="handleQuery"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label prop="likaishitian">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.likaishitian"
          type="datetime"
          placeholder="选择结束时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          @change="handleQuery"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <!-- <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >-->
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
          v-hasPermi="['militaryvehicleManagement:privateCarAccessLog:add']"
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
          v-hasPermi="['militaryvehicleManagement:privateCarAccessLog:edit']"
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
          v-hasPermi="['militaryvehicleManagement:privateCarAccessLog:remove']"
          >删除</el-button
        >
    </el-col>-->
    <!-- <el-col :span="1.5">
        <el-button
          class="box"
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['militaryvehicleManagement:privateCarAccessLog:export']"
          >导出</el-button
        >
    </el-col>-->
    <!-- </el-row> -->

    <el-table
      v-loading="loading"
      :data="privateAccessLogList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop>
        <template scope="scope">
          <span>
            {{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
            }}
          </span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="姓名" align="center" prop="peopleName" /> -->
      <el-table-column label="车牌号" align="center" prop="vehiclenumber" />
      <el-table-column label="进入时间" align="center" prop="jinrushiajin" />
      <el-table-column label="离开时间" align="center" prop="likaishitian" />
      <el-table-column label="备注" align="center" prop="remark" show-overflow-tooltip />
      <!-- <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['militaryvehicleManagement:privateCarAccessLog:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['militaryvehicleManagement:privateCarAccessLog:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改私家车进出查询对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" inline>
        <el-form-item label="车牌号" prop="privateCarId">
          <el-select v-model="form.privateCarId" placeholder="请输入车牌号">
            <el-option
              v-for="(item, i) in carList"
              :key="i"
              :label="item.vehiclenumber"
              :value="item.id"
              @click.native="chePaiHao(item.vehiclenumber)"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="车辆型号" prop="vehiclemodel">
          <el-input v-model="form.vehiclemodel" placeholder="请输入车辆型号" />
        </el-form-item>
        <el-form-item label="车辆类别" prop="vehiclecategory">
          <el-select v-model="form.vehiclecategory" placeholder="请输入车辆类别">
            <el-option
              v-for="(item, i) in privateCar"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="车辆颜色" prop="vehiclecolor">
          <el-input v-model="form.vehiclecolor" placeholder="请输入车辆颜色" />
        </el-form-item>
        <el-form-item label="车辆所有人" prop="peopleId">
          <el-select v-model="form.peopleId" placeholder="请输入车辆所有人">
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="suoYouRen(item.name)"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系方式" prop="contactnumber">
          <el-input v-model="form.contactnumber" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="进入时间" prop="jinrushiajin">
          <el-date-picker
            clearable
            size="small"
            v-model="form.jinrushiajin"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请输入进入时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="离开时间" prop="likaishitian">
          <el-date-picker
            clearable
            size="small"
            v-model="form.likaishitian"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请输入离开时间"
          ></el-date-picker>
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
  listPrivateAccessLog,
  getPrivateAccessLog,
  delPrivateAccessLog,
  addPrivateAccessLog,
  updatePrivateAccessLog,
  exportPrivateAccessLog
} from "@/api/militaryvehicleManagement/privateAccessLog";
// 查询字典
import { getDicts } from "@/api/system/dict/data";
import {
  getRenYuanCar,
  listPrivateCar
} from "@/api/militaryvehicleManagement/privateCar";
import { getAll } from "@/api/grassrootsregistration/bdglmeeting";

export default {
  name: "PrivateAccessLog",
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
      // 私家车进出查询表格数据
      privateAccessLogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        privateCarId: null,
        vehiclenumber: null,
        vehiclecategory: null,
        vehiclemodel: null,
        vehiclecolor: null,
        peopleId: null,
        contactnumber: null,
        time1: null,
        time2: null,
        adminId: null,
        createtime: null,
        updatetime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        privateCarId: [
          { required: true, message: "车牌号不能为空", trigger: "blur" }
        ]
      },
      // 车辆类别
      privateCar: [],
      // 人员列表
      renYuan: [],
      // 车牌号列表
      carList: [],
      // 外面的人员
      renYuans: []
    };
  },
  created() {
    this.getList();
    getDicts("privateCar").then(response => {
      this.privateCar = response.data;
    });
    this.getSuo();
  },
  methods: {
    // 获取搜索所有人
    getSuo() {
      getAll().then(res => {
        this.renYuans = res.rows;
      });
    },
    // 车辆类别翻译
    carLei(row, column) {
      var carLei = "";
      this.privateCar.map(item => {
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
    // 选择车牌号触发
    chePaiHao(name) {
      this.form.vehiclenumber = name;
    },
    // 获取车牌号列表
    getCarPai() {
      listPrivateCar().then(response => {
        this.carList = response.rows;
      });
    },
    // 人员列表
    getRen() {
      getRenYuanCar().then(response => {
        this.renYuan = response.rows;
      });
    },
    /** 查询私家车进出查询列表 */
    getList() {
      this.loading = true;
      listPrivateAccessLog(this.queryParams).then(response => {
        this.privateAccessLogList = response.rows;
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
        privateCarId: null,
        vehiclenumber: null,
        vehiclecategory: null,
        vehiclemodel: null,
        vehiclecolor: null,
        peopleId: null,
        contactnumber: null,
        time1: null,
        time2: null,
        adminId: null,
        createtime: null,
        updatetime: null
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getCarPai();
      this.getRen();
      this.open = true;
      this.title = "添加私家车进出查询";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getCarPai();
      this.getRen();
      const id = row.id || this.ids;
      getPrivateAccessLog(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改私家车进出查询";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePrivateAccessLog(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPrivateAccessLog(this.form).then(response => {
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
        .confirm("是否确认删除私家车进出查询的数据项？")
        .then(function() {
          return delPrivateAccessLog(ids);
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
        .confirm("是否确认导出所有私家车进出查询数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportPrivateAccessLog(queryParams);
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
  width: 820px !important;
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
  padding: 30px 60px;
}
::v-deep .el-dialog__body {
  margin: 22px 28px -14px 28px;
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
  margin-right: 76px;
}
::v-deep .el-dialog__body {
  padding: 30px 60px;
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
::v-deep .el-input__inner {
  border: 1px solid white !important;
}
.box {
  background-color: #ffba00 !important;
}
</style>
