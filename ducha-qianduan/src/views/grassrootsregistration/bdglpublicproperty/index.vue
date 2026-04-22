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
          placeholder="请输入单位"
          v-model="queryParams.unitName"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item>
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
          v-hasPermi="['grassrootsregistration:bdglpublicproperty:add']"
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
          v-hasPermi="['grassrootsregistration:bdglpublicproperty:edit']"
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
          v-hasPermi="['grassrootsregistration:bdglpublicproperty:remove']"
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
          v-hasPermi="['grassrootsregistration:bdglpublicproperty:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="bdglpublicpropertyList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      style="width: 100%"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="序号" align="center" prop="id" /> -->
      <el-table-column label="序号" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="单位"
        align="center"
        prop="unitName"
        width="150"
      />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="规格型号" align="center" prop="version" />
      <el-table-column label="数量" align="center" prop="quantity" />
      <el-table-column label="单价" align="center" prop="price" />
      <el-table-column label="总价" align="center" prop="totalPrice" />
      <el-table-column
        label="装备时间"
        align="center"
        prop="equipTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.equipTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="保管人" align="center" prop="peopleName" />
      <el-table-column label="取得方式" align="center" prop="acquire" />
      <el-table-column label="质量状况" align="center" prop="quality" />
      <el-table-column label="放置地点" align="center" prop="address" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
      >
        <template slot-scope="scope">
          <el-button
            size="btk"
            type="text"
            @click="handleChakan(scope.row)"
            v-hasPermi="['grassrootsregistration:bdgldiary:edit']"
            ><span class="chakan">查看</span></el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglpublicproperty:edit']"
            ><span class="edit">修改</span></el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglpublicproperty:remove']"
            ><span class="delete">删除</span></el-button
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

    <!-- 添加或修改营产，公物管理登记簿对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1125px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <div class="jiben">基本信息</div>

        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="deptOptions"
            placeholder="请选择单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="规格型号" prop="version">
          <el-input v-model="form.version" placeholder="请输入规格型号" />
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input
            v-model="form.quantity"
            controls-position="right"
            oninput="value=value.replace(/[^\d]/g,'')"
            @blur="shuLiang"
          ></el-input>
        </el-form-item>
        <el-form-item label="单价" prop="price">
          <el-input
            v-model="form.price"
            controls-position="right"
            oninput="value=value.replace(/[^\d]/g,'')"
            @blur="danJia"
          ></el-input>
        </el-form-item>
        <el-form-item label="总价" prop="totalPrice">
          <el-input
            v-model="form.totalPrice"
            controls-position="right"
            readonly
          ></el-input>
        </el-form-item>
        <el-form-item label="来源" prop="source">
          <el-input v-model="form.source"></el-input>
        </el-form-item>
        <el-form-item label="借用情况" prop="borrowingSituation">
          <el-input v-model="form.borrowingSituation"></el-input>
        </el-form-item>
        <el-form-item label="使用情况" prop="usage">
          <el-input v-model="form.usage"></el-input>
        </el-form-item>
        <el-form-item label="归还情况" prop="returnSituation">
          <el-input v-model="form.returnSituation"></el-input>
        </el-form-item>
        <el-form-item label="装备时间" prop="equipTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.equipTime"
            type="date"
            :editable="false"
            value-format="yyyy-MM-dd"
            placeholder="请选择装备时间"
            @change="timeDay"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="保管人" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请选择保管人"
            clearable
          >
            <el-option
              v-for="item in roleOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              :disabled="item.status == 1"
              @click.native="baoGuan(item.name)"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="取得方式" prop="acquire">
          <el-input v-model="form.acquire" placeholder="请输入取得方式" />
        </el-form-item>
        <el-form-item label="质量状况" prop="quality">
          <el-input v-model="form.quality" placeholder="请输入质量状况" />
        </el-form-item>
        <el-form-item label="放置地点" prop="address">
          <el-input v-model="form.address" placeholder="请输入放置地点" />
        </el-form-item>

        <div class="jiben">备注</div>
        <div class="box4">
          <el-input
            type="textarea"
            :rows="3"
            placeholder="请输入内容"
            v-model="form.remark"
            autosize
          >
          </el-input>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(1)">确 定</el-button>
        <el-button @click="cancel" size="btn">取消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :visible.sync="menuRoleVisible"
      :title="title"
      append-to-body
      id="chakan"
      :close-on-click-modal="false"
      width="1107px"
    >
      <bdglpublicproperty
        v-if="menuRoleVisible"
        ref="menuRole"
        :message="wordInfo"
      ></bdglpublicproperty>
    </el-dialog>
  </div>
</template>

<script>
import bdglpublicproperty from "@/components/look_word/bdglpublicproperty.vue";

import {
  listBdglpublicproperty,
  getBdglpublicproperty,
  delBdglpublicproperty,
  addBdglpublicproperty,
  updateBdglpublicproperty,
  exportBdglpublicproperty,
  publiccpropertyunits,
} from "@/api/grassrootsregistration/bdglpublicproperty";
import {
  getZhuChiRen,
  getShouBu,
} from "@/api/grassrootsregistration/bdglmeeting";

// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getDept } from "@/api/grassrootsregistration/bdgleven";
import { jieZhiList } from "@/api/grassrootsregistration/bdgldiary";

export default {
  name: "Bdglpublicproperty",
  components: {
    Treeselect,
    bdglpublicproperty,
  },
  data() {
    return {
      //查看页面
      menuRoleVisible: false,
      wordInfo: [],
      //判断关闭
      style: "2",
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
      // 营产，公物管理登记簿表格数据
      bdglpublicpropertyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        version: null,
        quantity: null,
        price: null,
        totalPrice: null,
        equipTime: null,
        peopleId: null,
        acquire: null,
        quality: null,
        address: null,
        unitId: null,
        createtime: null,
        updatetime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitId: [
          { required: true, message: "单位名称不能为空", trigger: "blur" },
        ],
        equipTime: [
          { required: true, message: "装备时间不能为空", trigger: "blur" },
        ],
      },
      //下拉
      options: [],
      // 获取单位树形
      deptOptions: [],
      // 人员的列表
      roleOptions: [],
      // 获取单位列表
      deptOptionss: [],
      form1: {},
      // 当月日期
      current_month: [],
      // 下月日期
      current_month1: [],
      // 日期开关状态
      dayTime: null,
    };
  },
  created() {
    this.getList();
    this.getdept();
    this.getYear();
    // 获取当月
    this.getDay();
    // 获取下月
    this.getDayX();
    jieZhiList().then((res) => {
      this.dayTime = res.rows[0].time;
    });
  },
  methods: {
    // 获取当月日期
    getDay() {
      // 获取标准时间
      let now = new Date();
      // 年
      var year = new Date().getFullYear();
      // 月
      var month = new Date().getMonth() + 1;
      // 日
      var day = new Date(year, month, 0).getDate();
      // console.log(now);
      let current_month_num = day;
      this.current_month = [];
      for (let i = 1; i <= current_month_num; i++) {
        let day = now.setDate(i);
        let date = new Date(day); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        let Y = date.getFullYear() + "-";
        let M =
          (date.getMonth() + 1 < 10
            ? "0" + (date.getMonth() + 1)
            : date.getMonth() + 1) + "-";
        let D =
          (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "";
        this.current_month.push(Y + M + D);
        // console.log(this.current_month);
      }
    },
    // 获取下个月日期
    getDayX() {
      // 获取标准时间
      let now = new Date();
      // 年
      var year = new Date().getFullYear();
      // 月
      var month = new Date().getMonth() + 2;
      // 日
      var day = new Date(year, month, 0).getDate();
      let current_month_num1 = day;
      for (let i = 1; i <= current_month_num1; i++) {
        let day = now.setDate(i);
        let date = new Date(day); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        let Y = date.getFullYear() + "-";
        let M =
          (date.getMonth() + 2 < 10
            ? "0" + (date.getMonth() + 2)
            : date.getMonth() + 2) + "-";
        let D =
          (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "";
        this.current_month.push(Y + M + D);
      }
    },
    timeDay(val) {
      var date1 = Math.round(new Date(val) / 1000);

      // 获取本月第一天 00:00:00
      var now = new Date();
      var firstDay = new Date(now.getFullYear(), now.getMonth(), 1, 0, 0, 0);
      var monthStart = Math.round(firstDay.getTime() / 1000);

      // 获取本月最后一天 23:59:59
      var lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0, 23, 59, 59);
      var monthEnd = Math.round(lastDay.getTime() / 1000);

      if (this.dayTime == 1) {
        if (date1 > monthEnd || date1 < monthStart) {
          this.$confirm("请在规定时间内填写", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              this.form.recordDate = null;
            })
            .catch(() => {
              this.form.recordDate = null;
            });
        }
      }
    },
    // 获取当前年
    getYear() {
      var myDate = new Date();
      var tYear = myDate.getFullYear();
      this.form1.year = tYear;
    },
    // 数量输入完毕失去焦点触发
    shuLiang() {
      if (this.form.price !== null && this.form.quantity !== null) {
        this.form.totalPrice = this.form.price * this.form.quantity;
      }
    },
    // 单价失去焦点触发
    danJia() {
      if (this.form.price !== null && this.form.quantity !== null) {
        this.form.totalPrice = this.form.price * this.form.quantity;
      }
    },
    // 搜索单位触发
    host() {
      this.getList();
    },
    // //获取部门列表
    getdept() {
      getShouBu().then((res) => {
        this.deptOptionss = res.data;
      });
    },
    // 选中保管人触发
    baoGuan(name) {
      this.form.peopleName = name;
    },
    // 重置表单人员
    ChongZhiRen() {
      this.form.peopleId = null;
    },
    // 单位选择触发
    selectPeo(val) {
      this.ChongZhiRen();
      this.form.unitId = val.id;
      this.getZhuChi(this.form.unitId);
    },
    // 获取人员
    getZhuChi(id) {
      getZhuChiRen(id).then((res) => {
        this.roleOptions = res.data;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.deptOptions = response.data;
      });
    },
    /** 查询营产，公物管理登记簿列表 */
    getList() {
      this.loading = true;
      listBdglpublicproperty(this.queryParams).then((response) => {
        this.bdglpublicpropertyList = response.rows;
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
        version: null,
        quantity: null,
        price: null,
        totalPrice: null,
        equipTime: null,
        peopleId: null,
        acquire: null,
        quality: null,
        address: null,
        remark: null,
        unitId: null,
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
    /** 对话框重置按钮操作 */
    resertwo() {
      this.reset();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      // 人员的列表
      (this.roleOptions = []), this.getTreeselect();
      this.reset();
      this.open = true;
      this.style = 2;
      this.title = "添加营产，公物管理登记簿";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getTreeselect();
      this.reset();
      this.form.unitId = row.unitId;
      this.getZhuChi(this.form.unitId);
      const id = row.id || this.ids;
      getBdglpublicproperty(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改营产，公物管理登记簿";
      });
    },
    //查看按钮操作
    handleChakan(row) {
      this.form1.unitid = row.unitId;
      // 是否隐藏按钮
      this.opens = false;
      publiccpropertyunits(this.form1).then((response) => {
        this.wordInfo = response.data;
        this.title = "查看营产/公物管理登记簿";
        this.menuRoleVisible = true;
      });
    },
    /** 提交按钮 */
    submitForm(style) {
      this.style = style;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateBdglpublicproperty(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBdglpublicproperty(this.form).then((response) => {
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
        // .confirm(
        //   '是否确认删除营产，公物管理登记簿编号为"' + ids + '"的数据项？'
        // )
        .confirm("是否确认删除该数据？")
        .then(function () {
          return delBdglpublicproperty(ids);
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
        .confirm("是否确认导出所有营产，公物管理登记簿数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBdglpublicproperty(queryParams);
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
  border: 1px solid white !important;
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
  margin: 10px 30px 20px 87px;
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
  margin-right: 61px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px;
}
::v-deep .el-dialog__body {
  padding: none !important;
}
/* ::v-deep .el-table--enable-row-transition .el-table__body td.el-table__cell{
  background: #004d86;
} */
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
/* 文本域提示文字颜色 */
::v-deep ::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}
#chakan /deep/ .el-dialog__body {
  padding: 30px 0px 30px 16px;
}
</style>
