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
          v-hasPermi="['materialManagement:distribution:add']"
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
          v-hasPermi="['materialManagement:distribution:edit']"
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
          v-hasPermi="['materialManagement:distribution:remove']"
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
          v-hasPermi="['materialManagement:distribution:export']"
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
      :data="distributionList"
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
      <!-- <el-table-column label="物品类别" align="center" prop="wuPinType">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.category"
            :value="scope.row.wuPinType"
          />
        </template>
      </el-table-column>-->
      <el-table-column label="物品名称" align="center" prop="name" />
      <el-table-column label="条形码编号" align="center" prop="barcode" />
      <el-table-column label="物品规格" align="center" prop="materialSpecification" />
      <el-table-column label="物品型号" align="center" prop="materialXh" />
      <el-table-column label="营具类型" align="center" prop="yingJuType">
        <!-- <template slot-scope="scope">
          <dict-tag :options="dict.type.camp_equipment" :value="scope.row.yingJuType" />
        </template>-->
      </el-table-column>
      <!-- <el-table-column
        label="责任单位"
        align="center"
        prop="responsibilityUnit"
      />-->
      <el-table-column label="出库数量" align="center" prop="putOut" />
      <el-table-column label="价值" align="center" prop="price" />
      <el-table-column label="发放时间" align="center" prop="putOutTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.putOutTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发放单位" align="center" prop="responsibilityUnit" width="150px" />
      <el-table-column label="发放对象" align="center" prop="outPeople"></el-table-column>
      <el-table-column label="接收单位" align="center" prop="outUnit" width="150px" />
      <el-table-column label="接收人" align="center" prop="recipient" />
      <el-table-column label="存放位置" align="center" prop="storageLocation" />
      <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column label="库房" align="center" prop="warehouse">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.storage_room" :value="scope.row.warehouse" />
        </template>
      </el-table-column>
      <el-table-column label="库存数量" align="center" prop="total" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="120">
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['materialManagement:distribution:edit']"
          >修改</el-button>
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['materialManagement:distribution:remove']"
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

    <!-- 添加或修改物资发放对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="条形码编号" prop>
          <el-input v-model="form.barcode" @blur="barcodefun" placeholder="请输入条形码编号" />
        </el-form-item>
        <div class="jiben">基本信息</div>
        <el-form-item label="物品类别" prop="wuPinType">
          <el-select v-model="form.wuPinType" placeholder="请选择物品类别">
            <el-option
              v-for="dict in dict.type.category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
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
        <el-form-item label="物品规格" prop="materialSpecification">
          <el-input v-model="form.materialSpecification" placeholder="请输入物品规格" readonly />
        </el-form-item>
        <el-form-item label="物品型号" prop="materialXh">
          <el-input v-model="form.materialXh" placeholder="请输入物资型号" readonly />
        </el-form-item>
        <el-form-item label="库房" prop="warehouse">
          <el-select v-model="form.warehouse" placeholder="请选择库房">
            <el-option
              v-for="dict in dict.type.storage_room"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="存放位置" prop="storageLocation">
          <el-input v-model="form.storageLocation" placeholder="请输入存放位置" />
        </el-form-item>
        <el-form-item label="现有数量" prop="total">
          <el-input v-model="form.total" placeholder="请输入现有数量" readonly />
        </el-form-item>

        <el-form-item label="出库数量" prop="putOut">
          <el-input v-model="form.putOut" placeholder="请输入出库数量" @blur="shuLiang" />
        </el-form-item>

        <el-form-item label="发放时间" prop="putOutTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.putOutTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择发放时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="营具类型" prop="yingJuType">
          <el-select v-model="form.yingJuType" placeholder="请选择营具类型">
            <el-option
              v-for="dict in dict.type.camp_equipment"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价值" prop="price">
          <el-input v-model="form.price" placeholder="请输入价值" readonly />
        </el-form-item>
        <el-form-item label="发放单位" prop="responsibilityUnit">
          <el-input v-model="form.responsibilityUnit" readonly />
        </el-form-item>
        <el-form-item label="发放对象" prop="outPeople">
          <el-select v-model="form.outPeople" placeholder="请选择发放对象">
            <el-option
              v-for="dict in dict.type.distribution_object"
              :key="dict.value"
              :label="dict.label"
              :value="dict.label"
              @click.native="dianJi(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接收人" prop="recipient" v-if="ren">
          <el-select v-model="form.recipient" placeholder="请选择接收人" @change="rens"   filterable>
            <el-option
              v-for="item in renYuan"
              :key="item.id"
              :label="item.name"
              :value="item"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接收单位" prop="outUnitId" v-if="danWei">
          <treeselect
            v-model="form.outUnitId"
            placeholder="请选择接收单位"
            :options="deptOptions"
            @select="danWeis"
          ></treeselect>
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item label prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>

        <!-- <el-form-item label="责任单位" prop="responsibilityUnit">
          <el-input
            v-model="form.responsibilityUnit"
            placeholder="请输入责任单位"
          />
        </el-form-item>
        <el-form-item label="责任人" prop="personLiable">
          <el-input v-model="form.personLiable" placeholder="请输入责任人" />
        </el-form-item>
        <el-form-item label="创建人" prop="createName">
          <el-input v-model="form.createName" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="购买时间" prop="buyingTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.buyingTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择购买时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="档案id" prop="archivesId">
          <el-input v-model="form.archivesId" placeholder="请输入档案id" />
        </el-form-item>
        <el-form-item label="入库表id" prop="registrationId">
          <el-input
            v-model="form.registrationId"
            placeholder="请输入入库表id"
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
  listDistribution,
  getDistribution,
  delDistribution,
  addDistribution,
  updateDistribution,
  exportDistribution,
  exportMaterialRegWuPingList,
  treeselect
} from "@/api/materialManagement/distribution";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getZhuChiRen } from "@/api/materialManagement/archives";
import { getBarCode } from "@/api/materialManagement/materialRegistration";

export default {
  components: {
    Treeselect
  },
  name: "Distribution",
  dicts: ["category", "camp_equipment", "storage_room", "distribution_object"],
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
      // 物资发放表格数据
      distributionList: [],
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
        putOut: null,
        createName: null,
        putOutTime: null,
        storageLocation: null,
        buyingTime: null,
        price: null,
        warehouse: null,
        archivesId: null,
        total: null,
        outUnit: null,
        outPeople: null,
        recipient: null,
        registrationId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "请选择物品名称", trigger: "blur" }],
        yingJuType: [
          { required: true, message: "请选择营具类型", trigger: "blur" }
        ],
        warehouse: [{ required: true, message: "请选择库房", trigger: "blur" }],
        putOut: [{ required: true, message: "请输入出库数量", trigger: "blur" }]
      },
      // 获取物品名称列表
      itemlist: [],
      // 树形单位
      deptOptions: [],
      // 显示与隐藏接收人
      ren: true,
      // 显示与隐藏接收单位
      danWei: false,
      // 人员列表
      renYuan: [],
      // 总数量
      yinJunShu: ""
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
          this.form.total = res.data.materialInventory;
          this.form.price = res.data.price;
          this.form.id = null;
        }
      });
    },
    // 数量的判断
    shuLiang() {
      if (this.form.putOut > this.form.total) {
        return this.$modal.msgWarning(`库存数量为${this.form.total}`);
      }
    },
    // 点击接收单位触发
    danWeis(data) {
      this.form.outUnit = data.label;
    },
    // 点击选择接收人触发
    rens(data) {
      this.form.recipientId = data.id;

      this.$set(this.form, "recipient", data.name);
    },
    // 点击发放对象
    dianJi(value) {
      if (value == "0") {
        this.danWei = false;
        this.ren = true;
        this.form.outUnitId = null;
        this.form.outUnit = "";
        this.form.recipient = "";
        this.form.recipientId = "";
      } else if (value == "2") {
        this.ren = false;
        this.danWei = true;
        this.form.recipient = "";
        this.form.recipientId = "";
        this.form.outUnitId = null;
        this.form.outUnit = "";
      }
    },
    // 获取人员
    getRenYuan(id) {
      getZhuChiRen(id).then(res => {
        this.renYuan = res.data;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 点击物品名称触发
    wuPing(data) {
      this.form.responsibilityUnit = data.responsibilityUnit;
      this.form.price = data.price;
      this.form.archivesId = data.archivesId;
      this.form.responsibilityUnitId = data.unitId;
      this.form.registrationId = data.id;
      this.form.total = data.total;
      this.yinJunShu = data.total;
      this.form.materialSpecification = data.materialSpecification;
      this.form.materialXh = data.materialXh;
      this.getRenYuan(data.unitId);
    },
    // 获取物品名称
    getMingCheng() {
      exportMaterialRegWuPingList().then(response => {
        this.itemlist = response.rows;
      });
    },
    /** 查询物资发放列表 */
    getList() {
      this.loading = true;
      listDistribution(this.queryParams).then(response => {
        this.distributionList = response.rows;
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
        putOut: null,
        createName: null,
        createTime: null,
        putOutTime: null,
        storageLocation: null,
        buyingTime: null,
        price: null,
        remark: null,
        warehouse: null,
        archivesId: null,
        total: null,
        outUnit: null,
        outPeople: null,
        recipient: null,
        registrationId: null
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
      this.getTreeselect();
      this.getMingCheng();
      this.reset();
      this.open = true;
      this.title = "添加物资发放";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getTreeselect();
      this.reset();
      const id = row.id || this.ids;

      if (row.outPeople == "单位") {
        this.danWei = true;
        this.ren = false;
      } else {
        this.ren = true;
        this.danWei = false;
      }
      getDistribution(id).then(response => {
        this.form = response.data;
        this.getRenYuan(this.form.responsibilityUnitId);
        this.open = true;
        this.title = "修改物资发放";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          console.log(this.form.id);
          if (this.form.id != null) {
            updateDistribution(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if (this.form.putOut > this.form.total) {
              return;
            }
            addDistribution(this.form).then(response => {
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
        .confirm('是否确认删除物资发放编号为"' + ids + '"的数据项？')
        .then(function() {
          return delDistribution(ids);
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
        .confirm("是否确认导出所有物资发放数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportDistribution(queryParams);
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
