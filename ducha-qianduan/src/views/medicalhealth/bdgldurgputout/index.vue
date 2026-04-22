<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="durgBrand">
        <el-input
          v-model="queryParams.durgBrand"
          placeholder="请输入品牌"
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
          v-hasPermi="['medicalhealth:bdgldurgputout:add']"
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
          v-hasPermi="['medicalhealth:bdgldurgputout:edit']"
          >修改</el-button
        >
      </el-col>-->
      <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['medicalhealth:bdgldurgputout:remove']"
        >删除</el-button>
      </el-col>-->
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['medicalhealth:bdgldurgputout:export']"
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
      :data="bdgldurgputoutList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center">
        <template scope="scope">
          <span>
            {{
            (queryParams.pageNum - 1) * queryParams.pageSize +
            scope.$index +
            1
            }}
          </span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="药品ID" align="center" prop="durgArchivesId" /> -->
      <el-table-column label="出库数量" align="center" prop="putOut" />
      <!-- <el-table-column label="出库位置" align="center" prop="place" /> -->
      <el-table-column label="药品用途" align="center" prop="purpose" />
      <!-- <el-table-column label="出库说明" align="center" prop="remarks" /> -->
      <el-table-column label="出库时间" align="center" prop="putTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.putTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="领用人" align="center" prop="receiveId" /> -->
      <!-- <el-table-column label="录入人(创建人)" align="center" prop="adminId" /> -->
      <el-table-column label="品牌" align="center" prop="durgBrand" />
      <el-table-column label="生产厂商" align="center" prop="manufacturer" />
      <el-table-column label="规格" align="center" prop="specifications" />
      <!-- <el-table-column
        label="功能主治"
        align="center"
        prop="productIntroduction"
      />-->
      <el-table-column label="出库编号" align="center" prop="number" />
      <el-table-column label="库房" align="center" prop="storeHouse">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.storage_room" :value="scope.row.storeHouse" />
        </template>
      </el-table-column>
      <!-- <el-table-column label="库房" align="center" prop="unitId" /> -->
      <el-table-column label="单位" align="center" prop="unitName1" />
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            icon
            @click="handleUpdate(scope.row)"
            v-hasPermi="['medicalhealth:bdgldurgputout:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medicalhealth:bdgldurgputout:remove']"
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

    <!-- 添加或修改药品出库登记对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" inline>
        <el-form-item label="出库编号" prop="number">
          <el-input v-model="form.number" @blur="getYaoPing" placeholder="请输入出库编号" />
        </el-form-item>
        <el-form-item label="药品名称" prop="putOut">
          <el-input v-model="form.durgName" placeholder="请输入药品名称" />
          <!-- <el-select v-model="form.durgName" placeholder="请选择药品名称" filterable>
            <el-option
              class="input_xiala"
              v-for="(item, i) in yaoList"
              :key="i"
              :value="item.id"
              :label="item.yaoPinName"
              @click.native="yapPing(item)"
            ></el-option>
          </el-select>-->
        </el-form-item>
        <el-form-item label="使用单位" prop="unitId1">
          <!-- <el-input v-model="form.unitId1" placeholder="请输入使用单位" /> -->
          <treeselect
            v-model="form.unitId1"
            :options="deptOptions"
            placeholder="选择使用单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="使用人" prop="receiveId">
          <!-- <el-input v-model="form.receiveId" placeholder="请输入使用人" /> -->
          <el-select v-model="form.receiveId" placeholder="请选择使用人" filterable>
            <el-option
              class="input_xiala"
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="shiyongren(item.name)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出库数量" prop="putOut">
          <el-input-number
            v-model="form.putOut"
            controls-position="right"
            :min="0"
            placeholder="请输入出库数量"
            @blur="shuLiang"
          ></el-input-number>
          <!-- <el-input v-model="form.putOut" placeholder="请输入出库数量" @blur="shuLiang" /> -->
        </el-form-item>
        <el-form-item label="出库位置" prop="place">
          <el-input v-model="form.place" placeholder="请输入出库位置" />
        </el-form-item>
        <el-form-item label="出库时间" prop="putTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.putTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择出库时间"
          ></el-date-picker>
        </el-form-item>
        <div class="jiben">药品用途</div>
        <el-form-item prop="purpose">
          <el-input v-model="form.purpose" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <div class="jiben">出库说明</div>
        <el-form-item prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <!-- <div class="jiben" v-if="updateReasonshow">修改原因</div>
        <el-form-item prop="updateReason" v-if="updateReasonshow">
          <el-input v-model="form.updateReason" type="textarea" placeholder="请输入修改原因" />
        </el-form-item>-->
        <!-- <el-form-item label="录入人(创建人)" prop="adminId">
          <el-input v-model="form.adminId" placeholder="请输入录入人(创建人)" />
        </el-form-item>
        <el-form-item label="品牌" prop="durgBrand">
          <el-input v-model="form.durgBrand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="生产厂商" prop="manufacturer">
          <el-input v-model="form.manufacturer" placeholder="请输入生产厂商" />
        </el-form-item>
        <el-form-item label="规格" prop="specifications">
          <el-input v-model="form.specifications" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="功能主治" prop="productIntroduction">
          <el-input
            v-model="form.productIntroduction"
            placeholder="请输入功能主治"
          />
        </el-form-item>

        <el-form-item label="库房" prop="storeHouse">
          <el-input v-model="form.storeHouse" placeholder="请输入库房" />
        </el-form-item>
        <el-form-item label="库房" prop="unitId">
          <el-input v-model="form.unitId" placeholder="请输入库房" />
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
  listBdgldurgputout,
  getBdgldurgputout,
  delBdgldurgputout,
  addBdgldurgputout,
  updateBdgldurgputout,
  exportBdgldurgputout
} from "@/api/medicalhealth/bdgldurgputout";
import { exportChuYao } from "@/api/medicalhealth/bdgldurgputin";
import { getDept2 } from "@/api/medicalhealth/bdgldurgarchives";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
// 人员
import { getZhuChiRen } from "@/api/grassrootsregistration/bdglmeeting";

import { exportYao } from "@/api/medicalhealth/bdgldurgputin";

export default {
  name: "Bdgldurgputout",
  components: {
    Treeselect
  },
  dicts: ["storage_room"],
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
      // 药品出库登记表格数据
      bdgldurgputoutList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        durgArchivesId: null,
        putOut: null,
        place: null,
        purpose: null,
        remarks: null,
        putTime: null,
        receiveId: null,
        adminId: null,
        durgBrand: null,
        manufacturer: null,
        specifications: null,
        productIntroduction: null,
        number: null,
        storeHouse: null,
        unitId: null,
        unitId1: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        updateReason: [
          { required: true, message: "修改原因不能为空", trigger: "blur" }
        ],
        number: [
          { required: true, message: "药品编码不能为空", trigger: "blur" }
        ]
      },
      // 药品列表
      yaoList: [],
      // 树形单位
      deptOptions: [],
      // 人员
      renYuan: [],
      // 数量
      yaoPingShu: "",
      updateReasonshow: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
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
      this.form.number = year + month + day + hour + minute;
    },
    // 使用人选择触发
    shiyongren(name) {
      this.form.peopleName = name;
    },
    // 数量的判断
    shuLiang() {
      if (this.form.putOut > this.yaoPingShu) {
        this.form.putOut = 0;
        return this.$modal.msgWarning(`未过期库存数量为${this.yaoPingShu}`);
      }
    },
    // 新增选择单位触发
    selectPeo(val) {
      this.form.unitId1 = val.id;
      this.form.unitName1 = val.label;
      // 选中单位触发获取人员
      this.getZhuChi(this.form.unitId1);
      // this.form.unitId = null;
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept2().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 获取主持人
    getZhuChi(id) {
      getZhuChiRen(id).then(res => {
        this.renYuan = res.data;
      });
    },
    // 药品名称触发赋值
    yapPing(data) {
      this.form.unitId = data.unitId;
      this.form.unitName = data.unitName;
      this.form.manufacturer = data.manufacturer;
      this.form.specifications = data.specifications;
      this.form.storeHouse = data.storeHouse;
      this.form.durgArchivesId = data.durgArchivesId;
      this.form.durgBrand = data.durgBrand;
      this.yaoPingShu = data.surplus;
      this.form.inId = data.id;
      this.planName();
    },
    // 查询药品名称列表
    getYaoPing() {
      if (this.form.number) {
        exportYao({ drugNo: this.form.number }).then(response => {
          if (response.code == 200) {
            if (response.data) {
              this.form = response.data;
              this.form.putOut = null;
              this.form.durgName = response.data.yaoPinName;
              this.form.durgArchivesId = response.data.id;
              this.form.id = null;
              // this.form.number = num;
              this.yaoPingShu = response.data.amount - response.data.overdue;
              this.form.createTime = null;
              this.form.updateTime = null;
              this.form.remarks = null;
              this.$set(this.form, "number", response.data.drugNo);
              this.form.remarks = null;
            } else {
              this.$modal.msgSuccess("未有该药品编码!");
              this.reset();
            }
          }
        });
      }
      // exportChuYao({ drugNo: this.form.number }).then(res => {
      //   if (res.code == 200) {
      //     if (response.data) {
      //       this.form = response.data;
      //       this.form.putOut = null;
      //       this.form.durgName = response.data.yaoPinName;
      //       this.form.durgArchivesId = this.form.id;
      //       this.form.id = null;
      //       this.form.number = response.data.drugNo;
      //       this.yaoPingShu = response.data.amount;
      //       this.form.createTime = null;
      //       this.form.updateTime = null;
      //     } else {
      //       this.$modal.msgSuccess("未有该药品编码!");
      //     }
      //   }
      // });
      // this.yaoPingShu = response.data.surplus;
    },
    /** 查询药品出库登记列表 */
    getList() {
      this.loading = true;
      listBdgldurgputout(this.queryParams).then(response => {
        this.bdgldurgputoutList = response.rows;
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
        durgArchivesId: null,
        putOut: null,
        place: null,
        purpose: null,
        remarks: null,
        putTime: null,
        receiveId: null,
        adminId: null,
        createTime: null,
        updateTime: null,
        durgBrand: null,
        manufacturer: null,
        specifications: null,
        productIntroduction: null,
        number: null,
        storeHouse: null,
        unitId: null,
        unitId1: null
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
      // this.getYaoPing();
      this.open = true;
      this.updateReasonshow = false;
      this.title = "添加药品出库登记";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getBdgldurgputout(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.updateReasonshow = true;
        this.title = "修改药品出库登记";
        this.getYaoPing();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            if (this.form.putOut > this.yaoPingShu) {
              return;
            }
            updateBdgldurgputout(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if (this.form.putOut > this.yaoPingShu) {
              return;
            }
            addBdgldurgputout(this.form).then(response => {
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
      this.$confirm("是否确认删除药品出库登记的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delBdgldurgputout(ids);
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
      this.$confirm("是否确认导出所有药品出库登记数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.exportLoading = true;
          return exportBdgldurgputout(queryParams);
        })
        .then(response => {
          this.download(response.msg);
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
}
::v-deep .el-textarea__inner {
  width: 920px;
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
</style>
