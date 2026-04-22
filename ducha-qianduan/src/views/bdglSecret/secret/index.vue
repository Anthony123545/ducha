<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="" prop="unitId">
        <treeselect
          v-model="queryParams.unitId"
          :options="deptOptions"
          placeholder="请选择单位"
          @select="selectshou"
        />
      </el-form-item>
      <el-form-item label="" prop="kind">
        <el-select
          v-model="queryParams.kind"
          placeholder="请选择类型"
          clearable
          size="small"
          @change="secrecy"
        >
          <el-option
            v-for="item in ziDian"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue"
            class="input_xiala"
          />
        </el-select>
      </el-form-item>
      <el-form-item prop="peopleName">
        <el-input
          v-model="queryParams.peopleName"
          placeholder="请输入责任人"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-refresh" size="btr" @click="resetQuery">重置</el-button>
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
          v-hasPermi="['bdglSecret:secret:add']"
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
          v-hasPermi="['bdglSecret:secret:edit']"
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
          v-hasPermi="['bdglSecret:secret:remove']"
          >删除</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['grassrootsregistration:bdglsecrecy:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <div class="hezi">
      <div class="tji">涉密物品统计</div>
      <div style="display: flex; justify-content: space-evenly; padding: 41px 20px">
        <div style="width: 173px; display: flex; justify-content: space-evenly; align-items: center; ">
          <span>移动载体</span>
          <span><span style="font-size: 25px; margin-right: 18px">{{ tongJiList[3] }}</span>台</span>
        </div>
        <div style="width: 173px; display: flex; justify-content: space-between; align-items: center;">
          <span>军用手机</span>
          <span><span style="font-size: 25px; margin-right: 18px">{{ tongJiList[0] }}</span>台</span>
        </div>
        <div style="width: 173px; display: flex; justify-content: space-evenly; align-items: center;">
          <span>电脑</span>
          <span><span style="font-size: 25px; margin-right: 18px">{{ tongJiList[1] }}</span>台</span>
        </div>
        <div style="width: 173px; display: flex; justify-content: space-evenly; align-items: center;">
          <span>相机</span>
          <span><span style="font-size: 25px; margin-right: 18px">{{ tongJiList[2] }}</span>台</span>
        </div>
      </div>
    </div>

    <el-table v-loading="loading" :data="bdglsecrecyList" @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }" style="width: 100%">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="序号" align="center" prop="id" /> -->
      <el-table-column label="序号" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="分类" align="center" prop="kind">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.electronic" :value="scope.row.kind" />
        </template>
      </el-table-column>
      <el-table-column label="型号" align="center" prop="version" width="200" />
      <el-table-column label="编号" align="center" prop="number" width="200" />
      <!-- <el-table-column label="密级" align="center" prop="confidential" /> -->
      <!-- <el-table-column label="使用人" align="center" prop="people1Name" /> -->
      <el-table-column label="使用单位" align="center" prop="unitId" :formatter="deptIdformatter" width="200"/>
      <el-table-column label="责任人" align="center" prop="peopleName" />
      <el-table-column label="用途" align="center" show-overflow-tooltip prop="people2Name" width="200"/>
      <el-table-column label="启用时间" align="center" prop="startTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="停用时间" align="center" prop="endTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="归还时间" align="center" prop="hindOverTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.hindOverTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" show-overflow-tooltip prop="remarks" width="200"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200" fixed="right">
        <template slot-scope="scope">
          <!-- <el-button
            size="btk"
            type="text"
            @click="handleChakan(scope.row)"
            v-hasPermi="['grassrootsregistration:bdgldiary:edit']"
            ><span class="chakan">查看</span></el-button
          > -->
          <el-button size="btu" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['bdglSecret:secret:edit']">
            <span class="edit">修改</span>
          </el-button>
          <el-button size="btd" type="text" @click="handleDelete(scope.row)" v-hasPermi="['bdglSecret:secret:remove']">
            <span class="delete">删除</span>
          </el-button>
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

    <!-- 添加或修改网络/手机/涉密载体登记本对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1016px" append-to-body :close-on-click-modal="false">
      <div class="jiben">基本信息</div>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="使用单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="deptOptions"
            placeholder="请选择使用单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="类型" prop="kind">
          <el-select v-model="form.kind" placeholder="请选择类型">
            <el-option
              v-for="item in ziDian"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="编号" prop="number">
          <el-input v-model="form.number" placeholder="请输入编号" />
        </el-form-item>
        <!-- <el-form-item label="使用人" prop="peopleId1">
          <el-select
            ref="aa"
            v-model="form.peopleId1"
            placeholder="请选择使用人"
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="shiYongRen(item.name)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="型号" prop="version">
          <el-input v-model="form.version" placeholder="请输入型号" />
        </el-form-item>
        <!-- <el-form-item label="密级" prop="confidential">
          <el-input v-model="form.confidential" placeholder="请输入密级" />
        </el-form-item> -->
        <el-form-item label="责任人" prop="peopleId">
          <el-select v-model="form.peopleId" placeholder="请选择负责人">
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="zheRenRe(item.name)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="批准人" prop="peopleId2">
          <el-select v-model="form.peopleId2" placeholder="请选择批准人">
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="piZhuiRen(item.name)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="启用时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="date"
            :editable="false"
            value-format="yyyy-MM-dd"
            placeholder="请选择启用时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="停用时间" prop="endTime">
          <el-date-picker
            ref="bb"
            clearable
            size="small"
            v-model="form.endTime"
            type="date"
            :editable="false"
            value-format="yyyy-MM-dd"
            placeholder="请选择停用时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="归还时间" prop="hindOverTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.hindOverTime"
            type="date"
            :editable="false"
            value-format="yyyy-MM-dd"
            placeholder="请选择归还时间"
          >
          </el-date-picker>
        </el-form-item>
        <div class="jiben">用途</div>
        <el-form-item label="" prop="people2Name">
          <el-input
            v-model="form.people2Name"
            type="textarea"
            placeholder="请输入用途"
          />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item label="" prop="remarks">
          <el-input
            v-model="form.remarks"
            type="textarea"
            placeholder="请输入备注"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(1)">确 定</el-button>
        <el-button @click="cancel" size="btn">取消</el-button>
      </div>
    </el-dialog>
    <div class="fff">
      <el-dialog
        :visible.sync="menuRoleVisible"
        :title="title"
        append-to-body
        id="chakan"
        :close-on-click-modal="false"
      >
        <bdglsecrecy
          v-if="menuRoleVisible"
          ref="menuRole"
          :message="wordInfo"
        ></bdglsecrecy>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import bdglsecrecy from "@/components/look_word/bdglsecrecy.vue";
import {
  listSecret,
  getSecret,
  delSecret,
  addSecret,
  updateSecret,
  exportSecret,
  getDepts,
  getNumber,
} from "@/api/bdglSecret/secret";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getDicts } from "@/api/system/dict/data";
import {
  getZhuChiRen,
  getDept,
} from "@/api/grassrootsregistration/bdglmeeting";
export default {
  name: "Bdglsecrecy",
  components: { Treeselect, bdglsecrecy },
  dicts: ["electronic"],
  data() {
    return {
      //查看页面
      menuRoleVisible: false,
      wordInfo: {},
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
      // 总条数
      total: 0,
      // 网络/手机/涉密载体登记本表格数据
      bdglsecrecyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        secrecyType: null,
        number: null,
        name: null,
        kind: null,
        version: null,
        confidential: null,
        peopleId: null,
        peopleId1: null,
        peopleId2: null,
        people2Name: null,
        startTime: null,
        endTime: null,
        hindOverTime: null,
        unitId: null,
        remarks: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitId: [
          { required: true, message: "单位名称不能为空", trigger: "blur" },
        ],
        kind: [{ required: true, message: "种类不能为空", trigger: "blur" }],
        confidential: [
          { required: true, message: "密级不能为空", trigger: "blur" },
        ],
        peopleId: [
          { required: true, message: "责任人不能为空", trigger: "blur" },
        ],
        peopleId1: [
          { required: true, message: "使用人不能为空", trigger: "blur" },
        ],
        // peopleId2: [
        //   { required: true, message: "批准人不能为空", trigger: "blur" },
        // ],
        startTime: [
          { required: true, message: "起用时间不能为空", trigger: "blur" },
        ],
      },
      //下拉
      options: [],
      // 部门树结构
      deptOptions: [],
      // 查询字典
      dictType: {
        dictType: "electronic",
      },
      // 字典列表
      ziDian: [],
      // 人员列表
      renYuan: [],
      // 树形配置对象
      defaultProps: {
        children: "children",
        label: "label",
      },
      unitId: [],
      //获取统计数据
      tongJiList: {},
    };
  },
  created() {
    this.getList();
    this.getdict();
    this.getTreeselect();
    //部门数据
    getDepts().then((res) => {
      this.unitId = res.data;
    });
    this.tongJi();
  },
  methods: {
    //获取统计数量
    tongJi(unitids) {
      getNumber(unitids).then((res) => {
        this.tongJiList = res.data;
      });
    },
    selectshou(data) {
      this.queryParams.unitId = data.id;
      this.getList();
      this.tongJi({ unitid: this.queryParams.unitId });
    },
    //单位翻译
    deptIdformatter(row, column) {
      var deptName = "";
      this.unitId.map((item) => {
        if (item.deptId == row.unitId) {
          deptName = item.deptName;
        }
      });
      return deptName;
    },
    // 种类选择触发
    zhongLeis() {
      this.planName();
    },
    // 计划编号
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
    // 重置表单人员
    ChongZhiRen() {
      // 批准人
      this.form.peopleId2 = null;
      // 使用人
      this.form.peopleId1 = null;
      // 责任人
      this.form.peopleId = null;
    },
    // 获取主持人
    getZhuChi(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 单位选择触发
    selectPeo(val) {
      this.ChongZhiRen();
      this.form.unitId = val.id;
      // 获取人员
      this.getZhuChi(this.form.unitId);
    },
    /** 查询网络/手机/涉密载体登记本列表 */
    getList() {
      this.loading = true;
      listSecret(this.queryParams).then((response) => {
        this.bdglsecrecyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 查询电子产品
    getdict() {
      console.log('this.dictType.dictType', this.dictType.dictType)
      getDicts(this.dictType.dictType).then((res) => {
        console.log('getdict res', res)
        this.ziDian = res.data;
      });
    },
    // 选中添加部门单位触发
    department() {
      getZhuChiRen(this.form.unitId).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 选中责任人触发
    zheRenRe(name) {
      // this.form.peopleId = val.id;
      this.form.peopleName = name;
    },
    // 选中使用人触发
    shiYongRen(name) {
      this.form.people1Name = name;
    },
    // 选中批准人触发
    piZhuiRen(name) {
      // this.form.peopleId2 = val.id;
      this.form.people2Name = name;
    },
    // 搜索选中分类触发
    secrecy() {
      this.getList();
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
        secrecyType: null,
        number: null,
        name: null,
        kind: null,
        version: null,
        confidential: null,
        peopleId: null,
        peopleId1: null,
        peopleId2: null,
        people2Name: null,
        startTime: null,
        endTime: null,
        hindOverTime: null,
        unitId: null,
        remarks: null,
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
      this.reset();
      this.resetForm("queryForm");
      this.queryParams.unitId = null;
      this.tongJi({ unitid: this.queryParams.unitId });
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
      // 人员列表
      this.renYuan = [];
      // 获取分类
      this.reset();
      this.getTreeselect();
      // 查询字典
      this.getdict();
      this.style = 2;
      this.open = true;
      this.title = "添加网络/手机/涉密载体登记本";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      // 获取分类
      this.getdict();
      this.form.unitId = row.unitId;
      this.getZhuChi(this.form.unitId);
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids;
      getSecret(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改网络/手机/涉密载体登记本";
      });
    },
    //查看按钮操作
    handleChakan(row) {
      // 是否隐藏按钮
      this.opens = false;
      const id = row.id || this.ids;
      getSecret(id).then((response) => {
        this.wordInfo = response.data;
        this.title = "查看网络/手机/涉密载体登记本";
        this.menuRoleVisible = true;
      });
    },

    /** 提交按钮 */
    submitForm(style) {
      this.style = style;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateSecret(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.tongJi({ unitid: this.queryParams.unitId });
            });
          } else {
            addSecret(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.tongJi({ unitid: this.queryParams.unitId });
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
        //   '是否确认删除网络/手机/涉密载体登记本编号为"' + ids + '"的数据项？'
        // )
        .confirm("是否确认删除该数据？")
        .then(function () {
          return delSecret(ids);
        })
        .then(() => {
          this.getList();
          this.tongJi({ unitid: this.queryParams.unitId });
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有网络/手机/涉密载体登记本数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportSecret(queryParams);
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
  background: transparent !important;
  color: #fff;
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

::v-deep .el-dialog__body {
  margin: 10px 30px 20px 50px;
  padding-top: 20px !important;
  box-sizing: border-box;
  /* padding: 30px 12px 30px 28px; */
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

::v-deep .el-input__inner {
  cursor: pointer !important;
}

/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 18px 40px 30px 0px;
  margin-right: 32px;
}

/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px !important;
}

::v-deep .el-dialog__body {
  padding: none !important;
}

/* 文件编号背景颜色 */
::v-deep .el-input.is-disabled .el-input__inner {
  background: transparent !important;
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

.hezi {
  height: 100px;
  margin-bottom: 13px;
  margin-top: 15px;
  position: relative;
  background: rgb(0, 60, 105);
  color: #fff;
  font-size: 18px;
  font-weight: 600;
}

.tji {
  width: 176px;
  height: 36px;
  text-align: center;
  line-height: 36px;
  position: absolute;
  top: -18px;
  right: 87px;
  color: #fff;
  font-size: 18px;
  font-weight: 600;
  background: #004d86;
}
</style>
