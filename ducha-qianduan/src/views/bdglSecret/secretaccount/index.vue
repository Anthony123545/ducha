<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label prop="unitname">
        <el-input
          v-model="queryParams.unitname"
          placeholder="请输入单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label prop="model">
        <el-input
          v-model="queryParams.model"
          placeholder="请输入型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label prop="confidentialitylevel">
        <el-select
          v-model="queryParams.confidentialitylevel"
          placeholder="请选择密级"
          clearable
        >
          <el-option
            v-for="item in ziDian"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue"
            class="input_xiala"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="persoeliable">
        <el-select
          v-model="queryParams.persoeliable"
          placeholder="请选择责任人"
          filterable
          clearable
        >
          <el-option
            v-for="item in renList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
            class="input_xiala"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="btr" @click="handleQuery"
          >搜索</el-button
        >
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
          v-hasPermi="['bdglSecret:secretaccount:add']"
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
          v-hasPermi="['bdglSecret:secretaccount:edit']"
          >修改</el-button
        >
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['bdglSecret:secretaccount:remove']"
          >删除</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['bdglSecret:secretaccount:export']"
        >导出</el-button>
      </el-col> -->
    </el-row>
    <div class="hezi">
      <div class="tji">涉密物品统计</div>
      <div style="display: flex; flex-wrap: wrap; padding: 11px 20px">
        <div
          style="width: 25%; display: flex; align-items: center; height: 100px"
          v-for="item in tongJiList"
          :key="item.name"
        >
          <div
            class="div-l"
            style="flex: 2; text-align: right; margin-right: 30px"
          >
            <span>{{ item.name }}</span>
          </div>
          <div
            class="div-r"
            style="flex: 1; text-align: right; margin-right: 120px"
          >
            <span>
              <span style="font-size: 25px; margin-right: 18px">
                {{ item.num }} </span
              >台
            </span>
          </div>
        </div>
      </div>
    </div>
    <el-table
      v-loading="loading"
      :data="secretaccountList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      style="width: 100%"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50" align="center">
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
      <el-table-column label="单位" align="center" prop="unitname" />
      <el-table-column
        label="类型"
        align="center"
        prop="stype"
        :formatter="stypeFn"
      />
      <el-table-column
        label="密级"
        align="center"
        prop="confidentialitylevel"
        :formatter="confidentialitylevelFn"
      ></el-table-column>
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="型号" align="center" prop="model" />
      <el-table-column
        label="硬盘序列号"
        align="center"
        prop="harddisknumber"
      />
      <el-table-column
        label="MAC地址/物理地"
        align="center"
        prop="macaddress"
      />
      <el-table-column label="保密编码" align="center" prop="levelnumber" />
      <el-table-column
        label="责任人"
        align="center"
        prop="persoeliable"
        :formatter="persoeliableFn"
      />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bdglSecret:secretaccount:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bdglSecret:secretaccount:remove']"
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

    <!-- 添加或修改涉密计算机台账对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        inline
      >
        <el-form-item label="单位" prop="unitname">
          <treeselect
            v-model="form.unitid"
            :options="deptOptions"
            placeholder="请选择使用单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="密级" prop="confidentialitylevel">
          <el-select
            v-model="form.confidentialitylevel"
            placeholder="请选择密级"
            clearable
          >
            <el-option
              v-for="item in ziDian"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类型" prop="stype">
          <el-select
            v-model="form.stype"
            placeholder="请选择类型"
            clearable
            size="small"
          >
            <el-option
              v-for="item in stypeList"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="责任人" prop="persoeliable">
          <el-select
            v-model="form.persoeliable"
            placeholder="请选择负责人"
            filterable
            clearable
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="型号" prop="model">
          <el-input v-model="form.model" placeholder="请输入型号" />
        </el-form-item>
        <el-form-item label="硬盘序列号" prop="harddisknumber">
          <el-input
            v-model="form.harddisknumber"
            placeholder="请输入硬盘序列号"
          />
        </el-form-item>
        <el-form-item label="MAC地址/物理地" prop="macaddress">
          <el-input
            v-model="form.macaddress"
            placeholder="请输入MAC地址/物理地"
          />
        </el-form-item>

        <el-form-item label="保密编码" prop="levelnumber">
          <el-input v-model="form.levelnumber" placeholder="请输入保密编码" />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item label prop="remarks">
          <el-input
            v-model="form.remarks"
            type="textarea"
            placeholder="请输入备注"
          />
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
  listSecretaccount,
  getSecretaccount,
  delSecretaccount,
  addSecretaccount,
  updateSecretaccount,
  getNumber,
} from "@/api/bdglSecret/secretaccount";
// 单位
import {
  getZhuChiRen,
  getDept,
} from "@/api/grassrootsregistration/bdglmeeting";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getDicts } from "@/api/system/dict/data";
import { getAll } from "@/api/grassrootsregistration/bdglmeeting";
import { listUsers } from "@/api/workingArrangements/worktaskplan";

export default {
  name: "Secretaccount",
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 涉密计算机台账表格数据
      secretaccountList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitname: null,
        stype: null,
        brand: null,
        model: null,
        harddisknumber: null,
        macaddress: null,
        confidentialitylevel: null,
        levelnumber: null,

        persoeliable: null,
        remarks: null,
        unitid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 部门树结构
      deptOptions: [],
      // 人员列表
      renYuan: [],
      // 密级列表
      ziDian: [],
      // 类型
      stypeList: [],
      suoyouLiit: [],
      //获取统计数据
      tongJiList: [],
      // 搜索所有人员
      renList:[]
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getdict();
    this.getdicts();
    this.getSuoYou();
    this.gettongji();
    // 获取搜索所有人员
    getAll().then((res) => {
      this.renList = res.data;
    });
  },
  methods: {
    //获取涉密物品统计
    gettongji() {
      getNumber().then((res) => {
        if (res.code == 200) {
          this.tongJiList = res.data;
        } else {
          this.tongJiList = [];
        }
      });
      // Object.keys(obj).forEach(key => {
      //   console.log(obj[key]); // foo
      // });
    },
    // 获取所有人员
    getSuoYou() {
      getAll().then((response) => {
        this.suoyouLiit = response.data;
      });
    },
    persoeliableFn(row) {
      var name = "";
      this.suoyouLiit.map((item) => {
        if (item.id == row.persoeliable) {
          name = item.name;
        }
      });
      return name;
    },
    confidentialitylevelFn(row) {
      var name = null;
      this.ziDian.map((item) => {
        if (row.confidentialitylevel == item.dictValue) {
          name = item.dictLabel;
        }
      });
      return name;
    },
    stypeFn(row) {
      var name = null;
      this.stypeList.map((item) => {
        if (row.stype == item.dictValue) {
          name = item.dictLabel;
        }
      });
      return name;
    },
    // 查询电子产品
    getdicts() {
      getDicts("electronic").then((res) => {
        this.stypeList = res.data;
      });
    },
    // 查询密级
    getdict() {
      getDicts("brigade_secret").then((res) => {
        this.ziDian = res.data;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 单位选择触发
    selectPeo(val) {
      this.ChongZhiRen();
      this.form.unitname = val.label;
      this.form.unitid = val.id;
      // 获取人员
      this.getZhuChi(this.form.unitid);
    },
    // 重置表单人员
    ChongZhiRen() {
      // 责任人
      this.form.persoeliable = null;
    },
    // 获取主持人
    getZhuChi(id) {
      console.log("获取主持人", id);
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    /** 查询涉密计算机台账列表 */
    getList() {
      this.loading = true;
      listSecretaccount(this.queryParams).then((response) => {
        this.secretaccountList = response.rows;
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
        unitname: null,
        stype: null,
        brand: null,
        model: null,
        harddisknumber: null,
        macaddress: null,
        confidentialitylevel: null,
        levelnumber: null,

        persoeliable: null,
        remarks: null,
        unitid: null,
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
      this.reset();
      this.open = true;
      this.title = "添加涉密计算机台账";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getSecretaccount(id).then((response) => {
        this.form = response.data;
        this.form.persoeliable = response.data.persoeliable * 1;
        this.open = true;
        this.title = "修改涉密计算机台账";
        this.getZhuChi(this.form.unitid);
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateSecretaccount(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.gettongji();
            });
          } else {
            addSecretaccount(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.gettongji();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除涉密计算机台账的数据项？")
        .then(function () {
          return delSecretaccount(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
          this.gettongji();
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "bdglSecret/secretaccount/export",
        {
          ...this.queryParams,
        },
        `secretaccount_${new Date().getTime()}.xlsx`
      );
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
  width: 671px;
  height: 104px;
  margin-left: 37px;
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
  width: 148px !important;
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
  margin: 10px 30px 20px 41px;
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
  margin-right: 71px;
}

/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 876px !important;
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
