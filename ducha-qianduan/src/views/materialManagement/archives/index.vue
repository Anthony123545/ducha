<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入物品名称"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="responsibilityUnit">
        <el-input
          v-model="queryParams.responsibilityUnit"
          placeholder="请输入单位"
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
          v-hasPermi="['materialManagement:archives:add']"
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
          v-hasPermi="['materialManagement:archives:edit']"
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
          v-hasPermi="['materialManagement:archives:remove']"
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
          v-hasPermi="['materialManagement:archives:export']"
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
      :data="archivesList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize +
            scope.$index +
            1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="物品类型" align="center" prop="wuPinType">
        <!-- <template slot-scope="scope">
          <dict-tag
            :options="dict.type.category"
            :value="scope.row.wuPinType"
          />
        </template>-->
      </el-table-column>
      <el-table-column label="条形码编号" align="center" prop="barcode" />
      <el-table-column label="物品名称" align="center" prop="name" />
      <el-table-column label="物品规格" align="center" prop="materialSpecification" />
      <el-table-column label="物品型号" align="center" prop="materialXh" />
      <el-table-column label="营具类型" align="center" prop="yingJuType">
        <!-- <template slot-scope="scope">
          <dict-tag
            :options="dict.type.camp_equipment"
            :value="scope.row.yingJuType"
          />
        </template>-->
      </el-table-column>
      <el-table-column label="责任单位" align="center" prop="responsibilityUnit" />
      <el-table-column label="责任人" align="center" prop="personLiable" />
      <el-table-column label="总数量" align="center" prop="materialTotal" />
      <el-table-column label="库存数量" align="center" prop="materialInventory" />
      <el-table-column label="在用数量" align="center" prop="inUseTotal" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['materialManagement:archives:edit']"
          >修改</el-button>
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['materialManagement:archives:remove']"
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

    <!-- 添加或修改物资档案对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="条形码编号" prop="barcode">
          <el-input v-model="form.barcode" placeholder="请输入条形码编号" />
        </el-form-item>
        <el-form-item label="物品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入物品名称" />
        </el-form-item>
        <el-form-item label="类别" prop="wuPinType">
          <el-select v-model="form.wuPinType" placeholder="请选择类别">
            <el-option
              v-for="dict in dict.type.category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.label"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="营具规格" prop="materialSpecification">
          <el-input v-model="form.materialSpecification" placeholder="请输入营具规格" />
        </el-form-item>
        <el-form-item label="营具型号" prop="materialXh">
          <el-input v-model="form.materialXh" placeholder="请输入营具型号" />
        </el-form-item>
        <el-form-item label="物品类型" prop="yingJuType">
          <el-select v-model="form.yingJuType" placeholder="请选择物品类型">
            <el-option
              v-for="dict in dict.type.camp_equipment"
              :key="dict.value"
              :label="dict.label"
              :value="dict.label"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="责任单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            placeholder="请选择责任单位"
            :options="deptOptions"
            @select="shuxing"
          ></treeselect>
        </el-form-item>

        <el-form-item label="责任人" prop="personLiableId">
          <el-select v-model="form.personLiableId" placeholder="请选择责任人"   filterable>
            <el-option
              class="input_xiala"
              v-for="(item, i) in renYuan"
              :key="i"
              :value="item.id"
              :label="item.name"
              @click.native="xingMing(item.name)"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- <el-form-item label="总数量" prop="materialTotal">
          <el-input v-model="form.materialTotal" placeholder="请输入总数量" />
        </el-form-item>
        <el-form-item label="库存数量" prop="materialInventory">
          <el-input
            v-model="form.materialInventory"
            placeholder="请输入库存数量"
          />
        </el-form-item>
        <el-form-item label="在用数量" prop="inUseTotal">
          <el-input v-model="form.inUseTotal" placeholder="请输入在用数量" />
        </el-form-item>
        <el-form-item label="创建人" prop="createName">
          <el-input v-model="form.createName" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="库房" prop="warehouse">
          <el-input v-model="form.warehouse" placeholder="请输入库房" />
        </el-form-item>
        <el-form-item label="单位id" prop="unitId">
          <el-input v-model="form.unitId" placeholder="请输入单位id" />
        </el-form-item>
        <el-form-item label="责任人id" prop="personLiableId">
          <el-input
            v-model="form.personLiableId"
            placeholder="请输入责任人id"
          />
        </el-form-item>-->
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
  listArchives,
  getArchives,
  delArchives,
  addArchives,
  updateArchives,
  exportArchives,
  getDept,
  getZhuChiRen
} from "@/api/materialManagement/archives";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getAll } from "@/api/grassrootsregistration/bdglmeeting";
export default {
  name: "Archives",
  components: {
    Treeselect
  },
  dicts: ["category", "camp_equipment"],
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
      // 物资档案表格数据
      archivesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wuPinType: null,
        name: null,
        materialSpecification: null,
        materialXh: null,
        yingJuType: null,
        responsibilityUnit: null,
        personLiable: null,
        materialTotal: null,
        materialInventory: null,
        inUseTotal: null,
        createName: null,
        warehouse: null,
        unitId: null,
        personLiableId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "请填写物品名称", trigger: "blur" }],
        barcode: [
          { required: true, message: "请填写条形码编号", trigger: "blur" }
        ],
        yingJuType: [
          { required: true, message: "请选择物品类型", trigger: "blur" }
        ]
      },
      // 树形单位
      deptOptions: [],
      // 人员列表
      renYuan: [],
      // 外面的人员
      renYuans: []
    };
  },
  created() {
    this.getList();
    this.getSuo();
  },
  methods: {
    // 获取搜索所有人
    getSuo() {
      getAll().then(res => {
        this.renYuans = res.rows;
      });
    },
    // 点击责任人触发
    xingMing(name) {
      this.form.personLiable = name;
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 点击单位触发
    shuxing(data) {
      this.form.unitId = data.id;
      this.form.responsibilityUnit = data.label;
      this.getRenYuan(this.form.unitId);
    },
    // 获取人员
    getRenYuan(id) {
      getZhuChiRen(id).then(res => {
        this.renYuan = res.data;
      });
    },

    /** 查询物资档案列表 */
    getList() {
      this.loading = true;
      listArchives(this.queryParams).then(response => {
        this.archivesList = response.rows;
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
        wuPinType: null,
        name: null,
        materialSpecification: null,
        materialXh: null,
        yingJuType: null,
        responsibilityUnit: null,
        personLiable: null,
        materialTotal: null,
        materialInventory: null,
        inUseTotal: null,
        createName: null,
        createTime: null,
        warehouse: null,
        unitId: null,
        personLiableId: null
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
      this.getTreeselect();
      this.open = true;
      this.title = "添加物资档案";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids;
      getArchives(id).then(response => {
        this.form = response.data;
        this.form.unitId = response.data.unitId;
        this.getRenYuan(this.form.unitId);
        this.open = true;
        this.title = "修改物资档案";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateArchives(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addArchives(this.form).then(response => {
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
        .confirm('是否确认删除物资档案编号为"' + ids + '"的数据项？')
        .then(function() {
          return delArchives(ids);
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
        .confirm("是否确认导出所有物资档案数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportArchives(queryParams);
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
  width: 800px !important;
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
  margin-right: 94px !important;
}
.contents {
  padding: 0px 40px !important;
}
::v-deep .el-form-item__label {
  width: 115px !important;
}
::v-deep .el-dialog__body {
  padding: 30px 0px 30px 44px !important;
}
::v-deep .vue-treeselect__single-value {
  color: #fff !important;
}
::v-deep .el-form .el-input__inner {
  height: 36px !important;
  color: #fff;
}
</style>
