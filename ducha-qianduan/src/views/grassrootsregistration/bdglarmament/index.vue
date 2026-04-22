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
      <el-form-item label="" prop="recordDate">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.recordDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择登记日期"
          @change="tiem"
          :editable="false"
        >
        </el-date-picker>
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
          v-hasPermi="['grassrootsregistration:bdglarmament:add']"
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
          v-hasPermi="['grassrootsregistration:bdglarmament:edit']"
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
          v-hasPermi="['grassrootsregistration:bdglarmament:remove']"
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
          v-hasPermi="['grassrootsregistration:bdglarmament:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="bdglarmamentList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      style="width: 100%"
    >
      <el-table-column type="selection" width="55" align="center" />
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
      <el-table-column
        label="名称"
        align="center"
        prop="name"
        show-overflow-tooltip
      />
      <el-table-column
        label="登记日期"
        align="center"
        prop="recordDate"
        width="120"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.recordDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计量单位" align="center" prop="metering" />
      <el-table-column
        label="交接依据"
        align="center"
        prop="gist"
        width="100"
        show-overflow-tooltip
      />
      <el-table-column label="编制数量" align="center" prop="prepareNumber" />
      <el-table-column label="现有总数" align="center" prop="sum" />
      <el-table-column label="完好数量" align="center" prop="intactNumber" />
      <el-table-column label="待修数量" align="center" prop="repairNumber" />
      <el-table-column label="送修数量" align="center" prop="sendNumber" />
      <el-table-column label="标准配套" align="center" prop="standard" />
      <el-table-column label="现有配套" align="center" prop="existing" />
      <el-table-column label="缺陷配套" align="center" prop="flaw" />
      <el-table-column label="登记人" align="center" prop="peopleName" />
      <el-table-column label="核对人" align="center" prop="people1Name" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
        fixed="right"
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
            v-hasPermi="['grassrootsregistration:bdglarmament:edit']"
            ><span class="edit">修改</span></el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglarmament:remove']"
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

    <!-- 添加或修改军械装备登记簿对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1016px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="jiben">基本信息</div>

      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <el-form-item label="单位" prop="unitId">
          <treeselect
            @select="selectPeo"
            v-model="form.unitId"
            :options="rese"
            placeholder="选择单位"
          />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="登记日期" prop="recordDate">
          <el-date-picker
            clearable
            size="small"
            v-model="form.recordDate"
            type="date"
            :editable="false"
            value-format="yyyy-MM-dd"
            placeholder="请选择登记日期"
            @change="timeDay"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="计量单位" prop="metering">
          <el-input v-model="form.metering" placeholder="请输入计量单位" />
        </el-form-item>
        <el-form-item label="交接依据" prop="gist">
          <el-input v-model="form.gist" placeholder="请输入交接依据" />
        </el-form-item>
        <el-form-item label="编制数量" prop="prepareNumber">
          <!-- <el-input v-model="form.prepareNumber" placeholder="请输入编制数量" /> -->
          <el-input-number
            v-model="form.prepareNumber"
            controls-position="right"
            :min="0"
            :max="99999999"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="现有总数" prop="sum">
          <!-- <el-input v-model="form.sum" placeholder="请输入现有总数" /> -->
          <el-input-number
            v-model="form.sum"
            controls-position="right"
            :min="0"
            :max="99999999"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="完好数量" prop="intactNumber">
          <!-- <el-input v-model="form.intactNumber" placeholder="请输入完好数量" /> -->
          <el-input-number
            v-model="form.intactNumber"
            controls-position="right"
            :min="0"
            :max="99999999"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="待修数量" prop="repairNumber">
          <!-- <el-input v-model="form.repairNumber" placeholder="请输入待修数量" /> -->
          <el-input-number
            v-model="form.repairNumber"
            controls-position="right"
            :min="0"
            :max="99999999"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="送修数量" prop="sendNumber">
          <!-- <el-input v-model="form.sendNumber" placeholder="请输入送修数量" /> -->
          <el-input-number
            v-model="form.sendNumber"
            controls-position="right"
            :min="0"
            :max="99999999"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="标准配套" prop="standard">
          <el-input v-model="form.standard" placeholder="请输入标准配套" />
        </el-form-item>
        <el-form-item label="现有配套" prop="existing">
          <el-input v-model="form.existing" placeholder="请输入现有配套" />
        </el-form-item>
        <el-form-item label="缺陷配套" prop="flaw">
          <el-input v-model="form.flaw" placeholder="请输入缺陷配套" />
        </el-form-item>
        <el-form-item label="登记人" prop="peopleId">
          <!-- <el-input v-model="form.peopleId" placeholder="请输入登记人" /> -->
          <el-select v-model="form.peopleId" placeholder="请选择人员" clearable>
            <el-option
              v-for="item in renYuan"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              :disabled="item.status == 1"
              @click.native="dengJi(item.name)"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="核对人" prop="peopleId1">
          <el-select
            v-model="form.peopleId1"
            placeholder="请选择人员"
            clearable
          >
            <el-option
              v-for="item in renYuan"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              :disabled="item.status == 1"
              @click.native="heDui(item.name)"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(1)">确 定 </el-button>
        <el-button @click="cancel" size="btn">取消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :visible.sync="menuRoleVisible"
      :title="title"
      append-to-body
      id="chakan"
      :close-on-click-modal="false"
    >
      <bdglarmament
        v-if="menuRoleVisible"
        ref="menuRole"
        :message="wordInfo"
      ></bdglarmament>
    </el-dialog>
  </div>
</template>

<script>
import bdglarmament from "@/components/look_word/bdglarmament.vue";

import {
  listBdglarmament,
  getBdglarmament,
  delBdglarmament,
  addBdglarmament,
  updateBdglarmament,
  exportBdglarmament,
  armamentunits,
} from "@/api/grassrootsregistration/bdglarmament";
import {
  getZhuChiRen,
  getShouBu,
} from "@/api/grassrootsregistration/bdglmeeting";
import { getDept } from "@/api/grassrootsregistration/bdgleven";

// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { jieZhiList } from "@/api/grassrootsregistration/bdgldiary";

export default {
  name: "Bdglarmament",
  components: { Treeselect, bdglarmament },
  data() {
    return {
      //查看页面
      menuRoleVisible: false,
      wordInfo: {},
      //判断关闭
      // 树形
      rese: [],
      // 单位
      deptOptions: [],
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
      // 军械装备登记簿表格数据
      bdglarmamentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        recordDate: null,
        metering: null,
        gist: null,
        prepareNumber: null,
        sum: null,
        intactNumber: null,
        repairNumber: null,
        sendNumber: null,
        standard: null,
        existing: null,
        flaw: null,
        peopleId: null,
        peopleId1: null,
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
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        recordDate: [
          { required: true, message: "登记时间不能为空", trigger: "blur" },
        ],
      },
      //下拉
      options: [],
      // 人员
      renYuan: [],
      roleOptions: [],
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
      if (!val) return;

      // 用户输入的时间戳（秒）
      const date1 = Math.floor(new Date(val).getTime() / 1000);

      // 获取当月第一天 00:00:00
      const now = new Date(val);  // 这里用 val 保证选中月份为准
      const firstDay = new Date(now.getFullYear(), now.getMonth(), 1).getTime() / 1000;

      // 获取当月最后一天 23:59:59
      const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0, 23, 59, 59).getTime() / 1000;

      // 判断是否在当月范围内
      if (this.dayTime === 1 && (date1 < firstDay || date1 > lastDay)) {
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
    },
    getYear() {
      var myDate = new Date();
      var tYear = myDate.getFullYear();
      this.form1.year = tYear;
    },
    // 搜索时间触发
    tiem() {
      this.getList();
    },
    // 搜索单位触发
    host() {
      this.getList();
    },
    // 核对人触发
    heDui(name) {
      this.form.people1Name = name;
    },
    // 登记触发
    dengJi(name) {
      this.form.peopleName = name;
    },
    // 获取主持人
    getZhuChi(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 重置人员
    ChongZhi() {
      (this.form.peopleId = null), (this.form.peopleId1 = null);
    },
    // 选择单位触发
    selectPeo(val) {
      this.ChongZhi();
      this.form.unitId = val.id;
      this.getZhuChi(this.form.unitId);
    },
    // //获取部门列表
    getdept() {
      getShouBu().then((res) => {
        this.deptOptions = res.data;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.rese = response.data;
      });
    },
    /** 查询军械装备登记簿列表 */
    getList() {
      this.loading = true;
      listBdglarmament(this.queryParams).then((response) => {
        this.bdglarmamentList = response.rows;
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
        recordDate: null,
        metering: null,
        gist: null,
        prepareNumber: null,
        sum: null,
        intactNumber: null,
        repairNumber: null,
        sendNumber: null,
        standard: null,
        existing: null,
        flaw: null,
        peopleId: null,
        peopleId1: null,
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

    //判断对话框添加事件
    close() {
      if (this.style != 1) {
        this.$confirm("您编辑的内容尚未保存，是否保存？", "保存提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.open = false;
          })
          .catch(() => {
            this.open = true;
          });
      }
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      // 人员
      this.renYuan = [];
      this.getTreeselect();
      this.style = 2;
      this.reset();
      this.open = true;
      this.title = "添加军械装备登记簿";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.form.unitId = row.unitId;
      this.form.unitId = row.unitId;
      this.getZhuChi(this.form.unitId);
      this.getTreeselect();
      this.reset();
      const id = row.id || this.ids;
      getBdglarmament(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改军械装备登记簿";
      });
    },
    //查看按钮操作
    handleChakan(row) {
      // 是否隐藏按钮
      this.opens = false;
      this.form1.unitid = row.unitId;
      armamentunits(this.form1).then((response) => {
        this.wordInfo = response.data;
        this.title = "查看军械装备登记簿";
        this.menuRoleVisible = true;
      });
    },
    /** 提交按钮 */
    submitForm(style) {
      this.style = style;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateBdglarmament(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBdglarmament(this.form).then((response) => {
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
        .confirm("是否确认删除该数据？")
        // .confirm('是否确认删除军械装备登记簿编号为"' + ids + '"的数据项？')
        .then(function () {
          return delBdglarmament(ids);
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
        .confirm("是否确认导出所有军械装备登记簿数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBdglarmament(queryParams);
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
  width: 1060px !important;
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
