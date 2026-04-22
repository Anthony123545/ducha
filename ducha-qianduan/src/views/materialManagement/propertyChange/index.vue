<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!-- <el-form-item label="变更后人员名称" prop="propertyId">
        <el-input
          v-model="queryParams.propertyId"
          placeholder="请输入变更后人员名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item label prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入物品名称"
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
          v-hasPermi="['materialManagement:propertyChange:add']"
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
          v-hasPermi="['materialManagement:propertyChange:edit']"
        >修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['materialManagement:propertyChange:remove']"
        >删除</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['materialManagement:propertyChange:export']"
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
      :data="propertyChangeList"
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
      <el-table-column label="物品名称" align="center" prop="name" />
      <el-table-column label="条形码编号" align="center" prop="barcode" />
      <el-table-column label="物品编号" align="center" prop="rukuNumber" />
      <el-table-column label="变更前所属单位" align="center" prop="beforeUnitName" />
      <el-table-column label="变更前人员名称" align="center" prop="beforePeopleName" />
      <el-table-column label="变更后所属单位" align="center" prop="afterUnitName" />
      <el-table-column label="变更后人员名称" align="center" prop="afterPeopleName" />

      <el-table-column label="变更时间" align="center" prop="changeTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.changeTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['materialManagement:propertyChange:edit']"
          >修改</el-button>
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['materialManagement:propertyChange:remove']"
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

    <!-- 添加或修改营具物资变更对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1016px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <!-- <el-form-item label="变更后人员名称" prop="propertyId">
          <el-input
            v-model="form.propertyId"
            placeholder="请输入变更后人员名称"
          />
        </el-form-item>-->
        <el-form-item label="条形码编号" prop>
          <el-input v-model="form.barcode" @blur="barcodefun" placeholder="请输入条形码编号" />
        </el-form-item>
        <div class="jiben">基本信息</div>
        <el-form-item label="物品名称" prop="name">
          <el-select v-model="form.name" placeholder="请选择物品名称">
            <el-option
              v-for="(item, i) in itemlist"
              :key="i"
              :value="item.name"
              :label="item.names"
              class="input_xiala"
              @click.native="wuPing(item)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="营具编号" prop="rukuNumber">
          <el-input v-model="form.rukuNumber" placeholder="请输入营具编号" />
        </el-form-item>
        <el-form-item label="变更时间" prop="changeTime">
          <!-- <el-input v-model="form.changeTime" placeholder="请输入变更时间" /> -->
          <el-date-picker
            clearable
            size="small"
            v-model="form.changeTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择变更时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="变更前所属单位" prop="beforeUnitId">
          <el-input placeholder="请输入变更前所属单位" v-model="form.beforeUnitName" readonly />
          <!-- <treeselect
            v-model="form.beforeUnitId"
            placeholder="请选择变更前所属单位"
            :options="deptOptions"
            @select="danWei1"
          ></treeselect>-->
        </el-form-item>
        <el-form-item label="变更前人员名称" prop="beforePeopleId">
          <el-input placeholder="请输入变更前人员名称" v-model="form.beforePeopleName" readonly />
          <!-- <el-select
            v-model="form.beforePeopleId"
            placeholder="请输入变更前人员名称"
          >
            <el-option
              v-for="item in renYuan1"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              class="input_xiala"
              @click.native="rens1(item.name)"
            >
            </el-option>
          </el-select>-->
        </el-form-item>
        <el-form-item label="变更后所属单位" prop="afterUnitId">
          <!-- <el-select
            v-model="form.afterUnitId"
            placeholder="请输入变更后所属单位"
          ></el-select>-->
          <treeselect
            v-model="form.afterUnitId"
            placeholder="请选择变更后所属单位"
            :options="deptOptions"
            @select="danWei2"
          ></treeselect>
        </el-form-item>
        <el-form-item label="变更后人员名称" prop="afterPeopleId">
          <el-select v-model="form.afterPeopleId" placeholder="请输入变更后人员名称">
            <el-option
              v-for="item in renYuan2"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              class="input_xiala"
              @click.native="rens2(item.name)"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- <el-form-item label="变更前单位名称" prop="beforeUnitName">
          <el-input
            v-model="form.beforeUnitName"
            placeholder="请输入变更前单位名称"
          />
        </el-form-item>
        <el-form-item label="变更后单位名称" prop="afterUnitName">
          <el-input
            v-model="form.afterUnitName"
            placeholder="请输入变更后单位名称"
          />
        </el-form-item>
        <el-form-item label="变更前人员名称" prop="beforePeopleName">
          <el-input
            v-model="form.beforePeopleName"
            placeholder="请输入变更前人员名称"
          />
        </el-form-item>
        <el-form-item label="变更后人员名称" prop="afterPeopleName">
          <el-input
            v-model="form.afterPeopleName"
            placeholder="请输入变更后人员名称"
          />
        </el-form-item>-->
        <div class="jiben">备注</div>
        <el-form-item label prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
  listPropertyChange,
  getPropertyChange,
  delPropertyChange,
  addPropertyChange,
  updatePropertyChange,
  exportPropertyChange
} from "@/api/materialManagement/propertyChange";
import { exportMaterialRegWuPingList } from "@/api/materialManagement/materialRegistration";
import { getDept, getZhuChiRen } from "@/api/materialManagement/archives";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getBarCode } from "@/api/materialManagement/materialRegistration";

export default {
  name: "PropertyChange",
  components: {
    Treeselect
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
      // 营具物资变更表格数据
      propertyChangeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        propertyId: null,
        name: null,
        rukuNumber: null,
        changeTime: null,
        beforeUnitId: null,
        afterUnitId: null,
        beforePeopleId: null,
        afterPeopleId: null,
        createtime: null,
        updatetime: null,
        beforeUnitName: null,
        afterUnitName: null,
        beforePeopleName: null,
        afterPeopleName: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "营具名称不能为空", trigger: "blur" }]
      },
      // 获取物品名称列表
      itemlist: [],
      // 树形单位
      deptOptions: [],
      // 人员列表
      renYuan1: [],
      renYuan2: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //条形码获取
    barcodefun() {
      getBarCode({ barcode: this.form.barcode }).then(res => {
        if (res.code == 200) {
          this.form = res.data;
          this.form.archivesId = res.data.id;
          this.form.propertyId = res.data.id;
          this.form.rukuNumber = res.data.materialXh;
          this.form.beforePeopleName = res.data.personLiable;
          this.form.beforeUnitName = res.data.responsibilityUnit;
          this.form.id = null;
        }
      });
    },
    // 物品名称触发
    wuPing(data) {
      this.form.beforeUnitName = data.responsibilityUnit;
      this.form.beforeUnitId = data.unitId;
      this.form.beforePeopleName = data.personLiable;
      this.form.beforePeopleId = data.personLiableId;
      this.form.propertyId = data.id;
      this.planName();
    },
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
      this.form.rukuNumber = year + month + day + hour + minute;
    },
    // 点击选择接收人触发
    rens1(name) {
      this.form.beforePeopleName = name;
    },
    rens2(name) {
      this.form.afterPeopleName = name;
    },
    // 点击接收单位触发
    danWei1(data) {
      this.form.beforeUnitName = data.label;
      this.getRenYuan1(data.id);
    },
    danWei2(data) {
      this.form.afterUnitName = data.label;
      this.getRenYuan2(data.id);
    },
    // 获取人员
    getRenYuan1(id) {
      getZhuChiRen(id).then(res => {
        this.renYuan1 = res.data;
      });
    },
    getRenYuan2(id) {
      getZhuChiRen(id).then(res => {
        this.renYuan2 = res.data;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 获取物品名称
    getMingCheng() {
      exportMaterialRegWuPingList().then(response => {
        this.itemlist = response.rows;
      });
    },
    /** 查询营具物资变更列表 */
    getList() {
      this.loading = true;
      listPropertyChange(this.queryParams).then(response => {
        this.propertyChangeList = response.rows;
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
        propertyId: null,
        name: null,
        rukuNumber: null,
        changeTime: null,
        beforeUnitId: null,
        afterUnitId: null,
        beforePeopleId: null,
        afterPeopleId: null,
        remark: null,
        createtime: null,
        updatetime: null,
        beforeUnitName: null,
        afterUnitName: null,
        beforePeopleName: null,
        afterPeopleName: null
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
      this.getMingCheng();
      this.getTreeselect();
      this.open = true;
      this.title = "添加营具物资变更";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getMingCheng();
      this.reset();
      const id = row.id || this.ids;
      getPropertyChange(id).then(response => {
        this.getTreeselect();
        this.form = response.data;
        this.getRenYuan1(this.form.beforeUnitId);
        this.getRenYuan2(this.form.afterUnitId);
        this.open = true;
        this.title = "修改营具物资变更";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePropertyChange(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPropertyChange(this.form).then(response => {
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
        .confirm('是否确认删除营具物资变更编号为"' + ids + '"的数据项？')
        .then(function() {
          return delPropertyChange(ids);
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
        .confirm("是否确认导出所有营具物资变更数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportPropertyChange(queryParams);
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
  width: 1016px !important;
  background: #004d86 !important;
}
::v-deep .el-textarea__inner {
  width: 965px;
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
::v-deep .el-dialog .el-input__inner {
  width: 200px !important;
  height: 36px;
  background: transparent;
  color: #fff;
  border: 1px solid white !important;
  border-color: #fff !important;
}
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
::v-deep .el-dialog__footer {
  margin-right: 6px !important;
}
.contents {
  padding: 0px 40px !important;
}
::v-deep .el-form-item__label {
  width: 115px !important;
}
::v-deep .el-dialog__body {
  padding: 30px 0px 30px 26px !important;
}
::v-deep .el-form .el-input__inner {
  height: 36px !important;
}
::v-deep .vue-treeselect__single-value {
  color: #fff !important;
}
</style>
