<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="type">
        <el-select
          v-model="queryParams.type"
          placeholder="请选择类型"
          clearable
          size="small"
          @change="handleQuery"
        >
          <el-option
            v-for="dict in dict.type.type_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item prop="attendedTime">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.attendedTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择值班日期"
          @change="handleQuery"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item prop="unitName">
        <treeselect
          v-model="queryParams.unitName"
          :options="deptOptions1"
          placeholder="请选择单位"
          @select="selectPeos"
        />
      </el-form-item>
      <el-form-item label="" prop="equipmentName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入装备名称"
          clearable
          size="small"
          @input="handleQuery"
          @keyup.enter.native="handleQuery"
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
          v-hasPermi="['combatduty:regisdutyinfo:add']"
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
          v-hasPermi="['combatduty:regisdutyinfo:edit']"
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
          v-hasPermi="['combatduty:regisdutyinfo:remove']"
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
          v-hasPermi="['combatduty:regisdutyinfo:export']"
          >导出</el-button
        >
      </el-col> -->
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    </el-row>
    <div class="biao_ti">
      <div class="tji">装备分类统计</div>
      <div style="margin-right: 23%">
        <span style="font-size: 24px">试验</span>
        <span style="margin-left: 20px; font-size: 24px">{{ number1 }}</span>
      </div>
      <div>
        <span style="font-size: 24px">通用</span>
        <span style="margin-left: 20px; font-size: 24px">{{ number2 }}</span>
      </div>
    </div>

    <el-table
      v-loading="loading"
      :data="regisdutyinfoList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <!-- <el-table-column label="主键ID" align="center" prop="id" /> -->
      <el-table-column label="单位名称" align="center" prop="unitName" />
      <!-- <el-table-column label="单位ID" align="center" prop="unitId" /> -->
      <el-table-column label="装备名称" align="center" prop="equipmentName">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.equipmentName" placement="top">
            <div
              style="
                overflow: hidden;
                display: -webkit-box;
                text-overflow: ellipsis;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                white-space: normal;
              "
            >
              {{ scope.row.equipmentName }}
            </div>
          </el-tooltip>
        </template>
      </el-table-column>
      <!-- <el-table-column label="装备ID" align="center" prop="equipmentId" /> -->
      <el-table-column label="装备数量" align="center" prop="equipmentNumber" />
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.type_type" :value="scope.row.type" />
        </template>
      </el-table-column>
      <el-table-column label="装备状态" align="center" prop="equipmentStates">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.equipment_status"
            :value="scope.row.equipmentStates"
          />
        </template>
      </el-table-column>
      <el-table-column label="待命位置" align="center" prop="standbyPosition" />
      <el-table-column
        label="值班日期"
        align="center"
        prop="attendedTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.attendedTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="装备情况"
        show-overflow-tooltip
        align="center"
        prop="remarks"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['combatduty:regisdutyinfo:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['combatduty:regisdutyinfo:remove']"
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

    <!-- 添加装备登记对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1016px"
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
        <el-row
          v-for="(item, i) in relatives"
          :key="i"
          style="margin-bottom: 20px"
        >
          <el-form-item label="单位名称" prop="unitName">
            <el-select v-model="item.unitName" clearable placeholder="">
              <el-option
                v-for="dict in deptOptions"
                :key="dict.deptId"
                :label="dict.deptName"
                :value="dict"
                @click.native="unitNameClick(dict, i)"
              ></el-option>
            </el-select>
          </el-form-item>
          <!-- @change="unitNamechange" -->
          <!-- <el-form-item label="单位ID" prop="unitId">
          <el-input v-model="form.unitId" placeholder="请输入单位ID" />
        </el-form-item> -->
          <el-form-item label="装备名称" prop="equipmentName">
            <el-input v-model="item.equipmentName" placeholder="" />
          </el-form-item>
          <!-- <el-form-item label="装备ID" prop="equipmentId">
          <el-input v-model="form.equipmentId" placeholder="请输入装备ID" />
        </el-form-item> -->
          <el-form-item label="装备数量" prop="equipmentNumber">
            <el-input
              v-model="item.equipmentNumber"
              placeholder=""
              type="Number"
            />
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-select v-model="item.type" placeholder="">
              <el-option
                v-for="dict in dict.type.type_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="装备状态" prop="equipmentStates">
            <el-select v-model="item.equipmentStates" placeholder="">
              <el-option
                v-for="dict in dict.type.equipment_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="待命位置" prop="standbyPosition">
            <el-input v-model="item.standbyPosition" placeholder="" />
          </el-form-item>
          <el-form-item label="值班日期" prop="attendedTime">
            <el-date-picker
              clearablerelatives
              size="small"
              v-model="item.attendedTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder=""
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="是否值班" prop="isnoduty">
            <el-select v-model="item.isonduty" placeholder="">
              <el-option
                v-for="dict in zhiban"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="addFormss"
              v-if="i == relatives.length - 1"
            ></el-button>
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              v-if="i > 0"
              @click="delFormss(i, item.id)"
            ></el-button>
          </el-form-item>
        </el-row>
        <div class="jiben">装备情况</div>
        <el-form-item label="" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 修改装备登记对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open1"
      width="1016px"
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
        <el-form-item label="单位名称" prop="unitName">
          <el-select
            v-model="form.unitName"
            clearable
            placeholder=""
            @change="unitNamechange"
          >
            <el-option
              v-for="dict in deptOptions"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict"
            ></el-option>
          </el-select>
        </el-form-item>
        <!-- @change="unitNamechange" -->
        <!-- <el-form-item label="单位ID" prop="unitId">
          <el-input v-model="form.unitId" placeholder="请输入单位ID" />
        </el-form-item> -->
        <el-form-item label="装备名称" prop="equipmentName">
          <el-input v-model="form.equipmentName" placeholder="" />
        </el-form-item>
        <!-- <el-form-item label="装备ID" prop="equipmentId">
          <el-input v-model="form.equipmentId" placeholder="请输入装备ID" />
        </el-form-item> -->
        <el-form-item label="装备数量" prop="equipmentNumber">
          <el-input
            v-model="form.equipmentNumber"
            placeholder=""
            type="Number"
          />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="">
            <el-option
              v-for="dict in dict.type.type_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="装备状态" prop="equipmentStates">
          <el-select v-model="form.equipmentStates" placeholder="">
            <el-option
              v-for="dict in dict.type.equipment_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="待命位置" prop="standbyPosition">
          <el-input v-model="form.standbyPosition" placeholder="" />
        </el-form-item>
        <el-form-item label="值班日期" prop="attendedTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.attendedTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否值班" prop="isnoduty">
          <el-select v-model="form.isonduty" placeholder="">
            <el-option
              v-for="dict in zhiban"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <div class="jiben">装备情况</div>
        <el-form-item label="" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="" />
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
  listRegisdutyinfo,
  getRegisdutyinfo,
  delRegisdutyinfo,
  addRegisdutyinfo,
  updateRegisdutyinfo,
  exportRegisdutyinfo,
  getDept,
  getNumber,
} from "@/api/combatduty/regisdutyinfo";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getDeptss } from "@/api/bdglregular/regularinfo";

export default {
  name: "Regisdutyinfo",
  dicts: ["type_type", "equipment_status"],
  components: {
    Treeselect,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 是否值班
      zhiban: [
        {
          label: "是",
          value: "0",
        },
        {
          label: "否",
          value: "1",
        },
      ],
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
      // 装备登记表格数据
      regisdutyinfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitName: null,
        unitId: null,
        equipmentName: null,
        equipmentId: null,
        equipmentNumber: null,
        type: null,
        equipmentStates: null,
        standbyPosition: null,
        attendedTime: null,
        remarks: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //单位数据
      deptOptions: [],
      deptOptions1: [],
      // 添加多个基本信息
      relatives: [
        {
          unitId: null,
          equipmentName: null,
          equipmentNumber: null,
          type: null,
          equipmentStates: null,
          attendedTime: null,
          standbyPosition: null,
          isonduty: null,
        },
      ],
      // 修改弹出层显示与隐藏
      open1: false,
      // 统计数量
      number1: null,
      number2: null,
    };
  },
  created() {
    this.getList();
    this.gettreeselect();
    this.getNumberAll();
    getDeptss().then((res) => {
      this.deptOptions1 = res.data;
    });
  },
  methods: {
    selectPeos(data) {
      this.queryParams.unitId = data.id;
      this.queryParams.unitName=null
      this.getList();
    },
    // 获取数量统计
    getNumberAll() {
      getNumber().then((res) => {
        console.log(res);
        this.number1 = res.data[0].typenumber;
        this.number2 = res.data[1].typenumber;
      });
    },
    // 单位提交
    unitNameClick(date, i) {
      this.relatives[i].unitName = date.deptName;
      this.relatives[i].unitId = date.deptId;
    },
    //增加多个基本信息
    addFormss() {
      this.relatives.push({
        unitId: null,
        equipmentName: null,
        equipmentNumber: null,
        type: null,
        equipmentStates: null,
        attendedTime: null,
        standbyPosition: null,
        isonduty: null,
      });
    },
    //删除多个基本信息
    delFormss(e, id) {
      this.relatives.splice(e, 1);
    },
    gettreeselect() {
      getDept().then((res) => {
        if (res.code == 200) {
          this.deptOptions = res.data;
        }
      });
    },
    /** 查询装备登记列表 */
    getList() {
      this.loading = true;
      listRegisdutyinfo(this.queryParams).then((response) => {
        this.regisdutyinfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open1 = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        unitName: null,
        unitId: null,
        equipmentName: null,
        equipmentId: null,
        equipmentNumber: null,
        type: null,
        equipmentStates: null,
        standbyPosition: null,
        attendedTime: null,
        remarks: null,
      };
      this.relatives = [
        {
          unitId: null,
          equipmentName: null,
          equipmentNumber: null,
          type: null,
          equipmentStates: null,
          attendedTime: null,
          standbyPosition: null,
          isonduty: null,
        },
      ];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.unitId=null
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
      this.open = true;
      this.title = "添加装备登记";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getRegisdutyinfo(id).then((response) => {
        this.form = response.data;
        this.open1 = true;
        this.title = "修改装备登记";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateRegisdutyinfo(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open1 = false;
              this.getList();
            });
          } else {
            this.form.regisDutyList = this.relatives;
            addRegisdutyinfo(this.form).then((response) => {
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
        .confirm("是否确认删除该数据项？")
        .then(function () {
          return delRegisdutyinfo(ids);
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
        .confirm("是否确认导出所有装备登记数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportRegisdutyinfo(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    unitNamechange(val) {
      this.form.unitId = val.deptId;
      this.form.unitName = val.deptName;
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
  height: 124px;
  /* margin-left: 0px; */
}
::v-deep .el-dialog__body {
  padding: 30px 0px 30px 48px;
}
::v-deep .el-dialog__footer {
  margin-right: 29px !important;
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
/* 对话框表单间距
::v-deep .el-dialog__body .el-form-item__label {
  width: 175px !important;
}
::v-deep .el-dialog__body .el-form--inline .el-form-item {
  display: inline-block;
  margin-right: 10px;
  vertical-align: top;
  padding-left: 50px;
} */

::v-deep .el-input__inner {
  /* width: 200px !important;
  height: 36px; */
  background: transparent !important;
  color: #fff;
}

/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
/* 基本信息背景 */
.jiben {
  width: 952px;
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
.el-radio-group {
  width: 216px;
}
.el-select-dropdown__item {
  /* background-color: white; */
  color: #000 !important;
}
.el-form-item ::v-deep .el-input__inner {
  width: 200px;
  height: 36px;
  background: #004d86;
  color: #fff important;
  border: 1px solid #fff !important;
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
.biao_ti {
  display: flex;
  margin: 0 auto;
  justify-content: center;
  height: 100px;
  align-items: center;
  color: #fff;
  background: rgb(0, 60, 105);
  margin-bottom: 30px;
}
.tji {
  width: 176px;
  height: 36px;
  text-align: center;
  line-height: 36px;
  position: absolute;
  top: 111px;
  right: 171px;
  color: #fff;
  font-size: 18px;
  font-weight: 600;
  background: #004d86;
}
/* 树形样式 */
::v-deep .vue-treeselect {
  width: 200px;
}
::v-deep .vue-treeselect__control {
  background: #00365f !important;
}
::v-deep .vue-treeselect__single-value {
  color: #fff;
}
</style>
