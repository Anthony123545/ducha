<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!-- <el-form-item prop="offduty">
        <el-select v-model="ipDi" placeholder="请选择考勤地址" clearable>
          <el-option v-for="item in ipList" :key="item.id" :label="item.ip" :value="item.ip"></el-option>
        </el-select>
      </el-form-item>-->
      <el-form-item prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item prop="offduty">
        <el-select
          v-model="queryParams.offduty"
          placeholder="请选择是否在职"
          clearable
        >
          <el-option
            v-for="item in renList"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue"
          >
          </el-option>
        </el-select>
      </el-form-item>-->
      <el-form-item>
        <!-- <el-button size="btr" @click="handleQuery">搜索</el-button> -->
        <el-button size="btr" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8"></el-row>

    <el-table
      v-loading="loading"
      :data="bdglevenList"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <!-- <el-table-column label="序号" align="center" prop="id" /> -->
      <el-table-column label="序号" type="index" width="50" align="center">
        <template scope="scope">
          <span>
            {{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
            }}
          </span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="编号" align="center" prop="pin" /> -->
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="单位" align="center" prop="deptname" show-overflow-tooltip />
      <el-table-column label="手机号码" align="center" prop="mobile" />
      <el-table-column label="办公电话" align="center" prop="telephone" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <span v-if="scope.row.status == 0">在职</span>
          <span v-else>离职</span>
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
  </div>
</template>
  <script>
import axios from "axios";
import { getDicts } from "@/api/system/dict/data";
import { getDept } from "@/api/grassrootsregistration/bdglmeeting";
import { listIpaddressconfig, employee } from "@/api/access/accessControl";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "access",
  components: {
    Treeselect
  },
  data() {
    return {
      // 查询参数
      queryParams: {
        number: 0,
        pageNum: 1,
        pageSize: 10
      },
      bdglevenList: [],
      // 显示搜索条件
      showSearch: true,
      // 遮罩层
      loading: false,
      // 总条数
      total: 0,
      //   人员数据
      renList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      form: {},
      rules: {
        name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ]
      },
      // 树形结构列表
      deptOptions: [],
      // 考勤机ipList
      ipList: [],
      ipDi: null
      // 192.168.5.188:85
    };
  },
  created() {
    // 获取列表
    this.getTiem();
    // 字典在位情况
    getDicts("sys_zai").then(res => {
      this.renList = res.data;
    });
    listIpaddressconfig().then(res => {
      this.ipList = res.rows;
    });
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 获取部门列表
    getBuMeng() {
      getDept().then(res => {
        this.deptOptions = res.data;
      });
    },
    //   获取人员
    getTiem() {
      this.getList(this.queryParams.number);
    },
    // 点击搜索
    handleQuery() {
      this.getList(this.queryParams.number);
    },
    // 重置
    resetQuery() {
      // this.getTiem();
      // this.ipDi = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    getList(number) {
      this.loading = false;
      // axios
      //   .post(
      //     `http://${this.ipDi}/api/v2/employee/get/?key=2633p84hnnlavzcy9afjcbtj1jdnega0ftscjkka6h7a`,
      //     {
      //       number
      //     }
      //   )
      //   .then(res => {
      //     this.loading = false;
      //     this.bdglevenList = res.data.data.items;
      //     this.total = res.data.data.items.length;
      //   });
      employee(this.queryParams).then(res => {
        console.log(res);
        if (res.code == 200) {
          this.loading = false;
          this.bdglevenList = res.rows;
          this.total = res.total;
        }
      });
    }
  }
};
</script>
<style scoped>
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
  width: 800px !important;
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
/* 基本信息背景 */
.jiben {
  width: 720px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  background-size: 100%;
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
  padding: 18px 50px !important;
  margin-right: 77px !important;
}
::v-deep .el-dialog__body {
  margin: 10px 0px 20px 51px;
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
/* 下拉菜单字体/背景颜色 */
.el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
  background-color: #004d86;
  color: #111;
}
.el-select-dropdown__item {
  color: #111;
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
  background: transparent !important;
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
  margin-right: 42px;
}
::v-deep .el-dialog__body {
  padding: none !important;
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
/* ::v-deep .el-table__body tr.hover-row > td.el-table__cell {
  background-color: none;
} */
::v-deep .el-table .el-table__body .el-table__row.hover-row td {
  background-color: #004d86;
}
::v-deep .el-table__fixed-right {
  height: 100% !important;
}
::v-deep .box {
  background-color: #ffba00 !important;
}
::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent;
  color: #fff;
}
</style>

