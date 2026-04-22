<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!-- 原来的按钮 form-item -->

       <el-form-item class="btns-form-item">
        <el-button size="btr" @click="resetQuery">重置</el-button>

        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['militaryvehicleManagement:driver:add']"
        >新增</el-button>

        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['militaryvehicleManagement:driver:remove']"
        >批量删除</el-button>

        <el-button
          class="box"
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['militaryvehicleManagement:driver:export']"
        >导出</el-button>
      </el-form-item>
    </el-form>


    <el-table
      v-loading="loading"
      :data="driverList"
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
        label="单位"
        align="center"
        prop="unitName"
        width="150px"
      />
      <el-table-column label="姓名" align="center" prop="peopleName" />
      <el-table-column
        label="性别"
        align="center"
        prop="sexList"
        :formatter="sexLian"
      />
      <el-table-column label="驾龄" align="center" prop="drivingAge">
        <template scope="scope">
          <span>{{ scope.row.drivingAge }}年</span>
        </template>
      </el-table-column>
      <el-table-column
        label="入伍时间"
        align="center"
        prop="enlistDate"
        width="180"
      />

      <el-table-column
        label="军衔"
        align="center"
        prop="rankId"
        :formatter="ranks"
      />

      <el-table-column
        label="联系方式"
        align="center"
        prop="contact"
        width="180"
      />
      <!-- <el-table-column
        label="核发日期"
        align="center"
        prop="hefariqi"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.hefariqi, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="准驾车型" align="center" prop="drivingModel">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.quasi_driving_model"
            :value="scope.row.drivingModel"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="有效日期"
        align="center"
        prop="youxiaoriqi"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.youxiaoriqi, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审验结果" align="center" prop="result" />
      <el-table-column
        label="职业技能"
        align="center"
        prop="vocationalSkills"
        :formatter="jiNeng"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="150"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['militaryvehicleManagement:driver:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['militaryvehicleManagement:driver:remove']"
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

    <!-- 添加或修改军车司机设置对话框 -->
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
        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="treeList"
            placeholder="选择单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="姓名" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请输入人员姓名"
            filterable
            clearable
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="cheRen(item)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系方式" prop="contact">
          <el-input
            v-model="form.contact"
            placeholder="请输入联系方式"
            readonly
          />
        </el-form-item>
        <el-form-item label="驾驶证号" prop="driversLicense">
          <el-input
            v-model="form.driversLicense"
            placeholder="请输入驾驶证号"
          />
        </el-form-item>
        <el-form-item label="出生日期" prop="birthDate">
          <el-input
            v-model="form.birthDate"
            placeholder="请输入生日"
            readonly
          />
        </el-form-item>
        <el-form-item label="入伍时间" prop="enlistDate">
          <el-input
            v-model="form.enlistDate"
            placeholder="请输入入伍时间"
            readonly
          />
        </el-form-item>
        <el-form-item label="性别" prop="sexList">
          <el-select v-model="form.sexList" placeholder="请输入性别">
            <el-option
              v-for="(item, i) in sex"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="军衔" prop="rankId">
          <el-select v-model="form.rankId" placeholder="请输入军衔" filterable clearable>
            <el-option
              v-for="(item, i) in rank"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="驾龄" prop="drivingAge">
          <el-input
            v-model="form.drivingAge"
            placeholder="请输入驾龄"
            disabled
          />
        </el-form-item>
        <el-form-item label="准驾车型" prop="drivingModel">
          <el-select
            v-model="form.drivingModel"
            placeholder="请选择准驾车型"
            filterable
            clearable
          >
            <el-option
              v-for="(item, i) in quasiDrivingList"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="初领日期" prop="chulingriq">
          <el-date-picker
            clearable
            size="small"
            v-model="form.chulingriq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择初领日期"
            @change="collectionDate"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="核发日期" prop="hefariqi">
          <el-date-picker
            clearable
            size="small"
            v-model="form.hefariqi"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择核发日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="有效日期" prop="youxiaoriqi">
          <el-date-picker
            clearable
            size="small"
            v-model="form.youxiaoriqi"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择有效日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审验结果" prop="result">
          <el-input v-model="form.result" placeholder="请输入审验结果" />
        </el-form-item>
        <el-form-item label="职业技能" prop="vocationalSkills">
          <el-select
            v-model="form.vocationalSkills"
            placeholder="请输入职业技能"
            filterable
            clearable
          >
            <el-option
              v-for="(item, i) in vocational"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            >
            </el-option>
          </el-select>
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
  listDriver,
  getDriver,
  delDriver,
  addDriver,
  updateDriver,
  exportDriver,
} from "@/api/militaryvehicleManagement/driver";

// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

import { getZhuChiRen, getDept } from "@/api/militaryvehicleManagement/thebus";
// 查询字典
import { getDicts } from "@/api/system/dict/data";
import { getAll } from "@/api/grassrootsregistration/bdglmeeting";

export default {
  name: "Driver",
  components: {
    Treeselect,
  },
  dicts: ["quasi_driving_model"],
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
      // 军车司机设置表格数据
      driverList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        peopleId: null,
        unitId: null,
        contact: null,
        driversLicense: null,
        birthDate: null,
        enlistDate: null,
        sexList: null,
        rankId: null,
        drivingAge: null,
        drivingModel: null,
        date1: null,
        date2: null,
        date3: null,
        result: null,
        vocationalSkills: null,
        createtime: null,
        updatetime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        chulingriq: [
          { required: true, message: "请选择初领日期", trigger: "blur" },
        ],
      },
      // 树形列表
      treeList: [],
      // 人员列表
      renYuan: [],
      // 性别
      sex: [],
      // 军衔
      rank: [],
      // 职业技能
      vocational: [],
      // 外面的人员
      renYuans: [],
      // 准驾车型
      quasiDrivingList: [],
    };
  },
  created() {
    this.getList();
    getDicts("sys_user_sex").then((response) => {
      this.sex = response.data;
    });
    getDicts("post_Level").then((response) => {
      this.rank = response.data;
    });
    getDicts("vocational").then((response) => {
      this.vocational = response.data;
    });
    getDicts("quasi_driving_model").then((response) => {
      this.quasiDrivingList = response.data;
    });
    this.getSuo();
  },
  methods: {
    // 初领时间计算驾龄
    collectionDate(value) {
      var today = new Date();
      var y = today.getFullYear();
      var M = today.getMonth() + 1;
      var d = today.getDate();
      if (M < 10) {
        M = "0" + M;
      }
      if (d < 10) {
        d = "0" + d;
      }
      var tiem = y + "-" + M + "-" + d;
      let times1 = Date.parse(tiem);
      let times2 = Date.parse(value);
      let times3 = (times1 - times2) / 31104000000;
      this.form.drivingAge = Math.trunc(times3);
    },
    // 获取搜索所有人
    getSuo() {
      getAll().then((res) => {
        this.renYuans = res.rows;
      });
    },
    // 重置人员联系
    ChongZhi() {
      this.form.peopleId = null;
      this.form.contact = null;
      this.form.enlistDate = null;
      this.form.birthDate = null;
    },
    // 字典翻译sex
    sexLian(row) {
      var sex = "";
      this.sex.map((item) => {
        if (item.dictValue == row.sexList) {
          sex = item.dictLabel;
        }
      });
      return sex;
    },
    // 字典翻译技能
    jiNeng(row) {
      var jiNeng = "";
      this.vocational.map((item) => {
        if (item.dictValue == row.vocationalSkills) {
          jiNeng = item.dictLabel;
        }
      });
      return jiNeng;
    },
    // 字典翻译军衔
    ranks(row) {
      var ranks = "";
      this.rank.map((item) => {
        if (item.dictValue == row.rankId) {
          ranks = item.dictLabel;
        }
      });
      return ranks;
    },
    // 选择人员触发
    cheRen(item) {
      this.form.peopleName = item.name;
      this.form.contact = item.phone;
      this.form.birthDate = item.birthDate;
      this.form.enlistDate = item.enlistDate;
    },
    // 获取人员列表
    getRen(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 选择部门单位触发
    selectPeo(data) {
      this.form.unitName = data.label;
      this.ChongZhi();
      this.getRen(data.id);
    },
    //  获取单位列表
    getTreeList() {
      getDept().then((res) => {
        this.treeList = res.data;
      });
    },
    /** 查询军车司机设置列表 */
    getList() {
      this.loading = true;
      listDriver(this.queryParams).then((response) => {
        this.driverList = response.rows;
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
        peopleId: null,
        unitId: null,
        contact: null,
        driversLicense: null,
        birthDate: null,
        enlistDate: null,
        sexList: null,
        rankId: null,
        drivingAge: null,
        drivingModel: null,
        date1: null,
        date2: null,
        date3: null,
        result: null,
        vocationalSkills: null,
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
      this.queryParams.unitName = null;
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
      this.getTreeList();
      this.reset();
      this.renYuan = [];
      this.open = true;
      this.title = "添加军车司机设置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeList();
      const id = row.id || this.ids;
      getDriver(id).then((response) => {
        this.form = response.data;
        this.getRen(this.form.unitId);
        this.open = true;
        this.title = "修改军车司机设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateDriver(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDriver(this.form).then((response) => {
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
        .confirm("是否确认删除军车司机的数据项？")
        .then(function () {
          return delDriver(ids);
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
        .confirm("是否确认导出所有军车司机设置数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportDriver(queryParams);
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
::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent !important;
  color: #fff;
}
::v-deep .el-form-item__content {
  width: 200px;
}

/* 查询区按钮一行显示 */
::v-deep .btns-form-item .el-form-item__content {
  width: auto !important;
  display: flex;
  align-items: center;
}

/* 查询区按钮一行显示 */
::v-deep .btns-form-item .el-form-item__content {
  width: auto !important;
  display: flex;
  align-items: center;
}

/* 查询区小按钮宽度自适应，避免“批量删除”溢出 */
::v-deep .btns-form-item .el-button--mini {
  width: auto !important;
  padding: 0 16px;
}
</style>
