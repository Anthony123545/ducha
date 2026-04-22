<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="durgName">
        <el-input
          v-model="queryParams.durgName"
          clearable
          size="small"
          placeholder="请输入防疫品名称"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <!-- <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        > -->
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
          v-hasPermi="['medicalhealth:antiepidemicputout:add']"
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
          v-hasPermi="['medicalhealth:antiepidemicputout:edit']"
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
          v-hasPermi="['medicalhealth:antiepidemicputout:remove']"
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
          v-hasPermi="['medicalhealth:antiepidemicputout:export']"
          >导出</el-button
        >
      </el-col> -->
    <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    <!-- </el-row> -->

    <el-table
      v-loading="loading"
      :data="antiepidemicputoutList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="防疫品名称" align="center" prop="durgName" />
      <el-table-column label="品牌" align="center" prop="durgBrand" />
      <el-table-column label="规格" align="center" prop="specifications" />
      <el-table-column label="库房" align="center" prop="storeHouse">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.storage_room"
            :value="scope.row.storeHouse"
          />
        </template>
      </el-table-column>
      <el-table-column label="发放单位" align="center" prop="unitName1" />
      <el-table-column label="领用单位" align="center" prop="unitName" />
      <el-table-column label="使用人" align="center" prop="peopleName" />
      <el-table-column label="领用数量" align="center" prop="putOut" />
      <el-table-column
        label="使用时间"
        align="center"
        prop="putTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.putTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="领用人" align="center" prop="peopleId" />
      <el-table-column label="录入人(创建人)" align="center" prop="adminId" />
      <el-table-column
        label="录入时间"
        align="center"
        prop="createtime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="更新时间"
        align="center"
        prop="updatetime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatetime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>

      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="使用人" align="center" prop="unitId1" />
      <el-table-column label="使用人" align="center" prop="storeHouse" />
      <el-table-column label="领用人姓名" align="center" prop="peopleName" />
      <el-table-column label="领用人单位" align="center" prop="unitName" />
      <el-table-column label="发放单位名称" align="center" prop="unitName1" /> -->
      <!-- <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['medicalhealth:antiepidemicputout:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medicalhealth:antiepidemicputout:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column> -->
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改防疫用品发放登记对话框 -->
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
        <el-form-item label="防疫品名称" prop="durgArchivesId">
          <el-select v-model="form.durgName" placeholder="请选择防疫品名称">
            <el-option
              v-for="(item, i) in fangYi"
              :key="i"
              :value="item.durgName"
              :label="item.name"
              @click.native="fangYiPingXuan(item)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发放数量" prop="putOut">
          <el-input
            v-model="form.putOut"
            placeholder="请输入发放数量"
            @blur="shuLiang"
          />
        </el-form-item>
        <el-form-item label="所在位置" prop="place">
          <el-input v-model="form.place" placeholder="请输入存放位置" />
        </el-form-item>
        <el-form-item label="使用时间" prop="putTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.putTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择领用时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="领用单位" prop="unitId">
          <!-- <el-select v-model="form.unitId" placeholder="请选择领用单位">
            <el-option label="请选择字典生成" value="" />
          </el-select> -->
          <treeselect
            v-model="form.unitId"
            :options="deptOptions"
            placeholder="选择单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="使用人" prop="peopleId">
          <!-- <el-select v-model="form.unitId1" placeholder="">
            <el-option label="请选择字典生成" value="" />
          </el-select> -->
          <el-select v-model="form.peopleId" placeholder="请选择使用人">
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="compere(item.name)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item prop="remarks">
          <el-input
            v-model="form.remarks"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <!-- <el-form-item label="领用人" prop="peopleId">
          <el-input v-model="form.peopleId" placeholder="请输入领用人" />
        </el-form-item>
        <el-form-item label="录入人(创建人)" prop="adminId">
          <el-input v-model="form.adminId" placeholder="请输入录入人(创建人)" />
        </el-form-item>
        <el-form-item label="品牌" prop="durgBrand">
          <el-input v-model="form.durgBrand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="规格" prop="specifications">
          <el-input v-model="form.specifications" placeholder="请输入规格" />
        </el-form-item> -->
        <!-- <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
       
        <el-form-item label="使用人" prop="storeHouse">
          <el-input v-model="form.storeHouse" placeholder="请输入使用人" />
        </el-form-item>
        <el-form-item label="领用人姓名" prop="peopleName">
          <el-input v-model="form.peopleName" placeholder="请输入领用人姓名" />
        </el-form-item>
        <el-form-item label="领用人单位" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入领用人单位" />
        </el-form-item>
        <el-form-item label="发放单位名称" prop="unitName1">
          <el-input v-model="form.unitName1" placeholder="请输入发放单位名称" />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listAntiepidemicputout,
  getAntiepidemicputout,
  delAntiepidemicputout,
  addAntiepidemicputout,
  updateAntiepidemicputout,
  exportAntiepidemicputout,
} from "@/api/medicalhealth/antiepidemicputout";
import { exportAntiepidemiFangYi } from "@/api/medicalhealth/antiepidemicputin";
import { getZhuChiRen } from "@/api/grassrootsregistration/bdglmeeting";
import { getDept } from "@/api/medicalhealth/bdgldurgarchives";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Antiepidemicputout",
  dicts: ["storage_room"],
  components: {
    Treeselect,
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
      // 防疫用品发放登记表格数据
      antiepidemicputoutList: [],
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
        putTime: null,
        peopleId: null,
        adminId: null,
        createtime: null,
        updatetime: null,
        durgBrand: null,
        specifications: null,
        unitId: null,
        unitId1: null,
        storeHouse: null,
        peopleName: null,
        unitName: null,
        unitName1: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 防疫品名称
      fangYi: [],
      // 树形单位
      deptOptions: [],
      // 人员
      renYuan: [],
      yaoPingShu: "",
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 数量的判断
    shuLiang() {
      if (this.form.putOut > this.yaoPingShu) {
        return this.$modal.msgWarning(`库存数量为${this.yaoPingShu}`);
      }
    },
    //  人员点击触发
    compere(name) {
      this.form.peopleName = name;
    },
    // 获取人员
    getZhuChi(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 新增选择单位触发
    selectPeo(val) {
      this.form.unitId = val.id;
      // 选中单位触发获取人员
      this.getZhuChi(this.form.unitId);
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 选择防疫品
    fangYiPingXuan(data) {
      this.form.durgArchivesId = data.id;
      this.form.durgBrand = data.durgBrand;
      this.form.specifications = data.specifications;
      this.form.storeHouse = data.storeHouse;
      this.form.unitName = data.unitName;
      this.yaoPingShu = data.amount;
    },
    // 查询防疫品名称列表
    fangYiPing() {
      exportAntiepidemiFangYi().then((response) => {
        this.fangYi = response.data;
      });
    },
    /** 查询防疫用品发放登记列表 */
    getList() {
      this.loading = true;
      listAntiepidemicputout(this.queryParams).then((response) => {
        this.antiepidemicputoutList = response.rows;
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
        putTime: null,
        peopleId: null,
        adminId: null,
        createtime: null,
        updatetime: null,
        durgBrand: null,
        specifications: null,
        unitId: null,
        remark: null,
        unitId1: null,
        storeHouse: null,
        peopleName: null,
        unitName: null,
        unitName1: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.fangYiPing();
      this.getTreeselect();
      this.reset();
      this.open = true;
      this.title = "添加防疫用品发放登记";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getAntiepidemicputout(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改防疫用品发放登记";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateAntiepidemicputout(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if (this.form.putOut > this.yaoPingShu) {
              return;
            }
            addAntiepidemicputout(this.form).then((response) => {
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
        .confirm("是否确认删除防疫用品发放登记的数据项？")
        .then(function () {
          return delAntiepidemicputout(ids);
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
        .confirm("是否确认导出所有防疫用品发放登记数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportAntiepidemicputout(queryParams);
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
</style>
