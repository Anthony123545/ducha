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
      </el-form-item>
      <el-form-item>
        <el-button size="btr" @click="handleQuery">搜索</el-button>
        <el-button size="btr" @click="resetQuery">重置</el-button>
      </el-form-item>-->
      <!-- :highlightedDates="year" -->
      <vue-horizontal-calendar
        :minDate="starttimes"
        :maxDate="endtimes"
        :highlightedDates="year"
        choosedDatePos="center"
        v-on:change="dateChange2"
      ></vue-horizontal-calendar>
      <el-form-item></el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8"></el-row>
    <div class="tables" :class="{ sollore: length > 3 }" v-if="length > 1">
      <div v-for="(item, i) in length" :key="i" style="width: 400px; margin-right: 20px">
        <el-table
          v-loading="loading"
          :data="bdglevenList[i]"
          :header-cell-style="{ background: '#003C69', color: 'white' }"
        >
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
          <!-- <el-table-column label="编号" align="center" prop="pin" width="50" /> -->
          <el-table-column label="姓名" align="center" prop="ename" />
          <el-table-column label="时间" align="center" prop="checktime" show-overflow-tooltip />
        </el-table>
      </div>
    </div>
    <div v-else>
      <el-table
        v-loading="loading"
        :data="bdglevenList"
        :header-cell-style="{ background: '#003C69', color: 'white' }"
      >
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
        <!-- <el-table-column label="编号" align="center" prop="pin" /> -->
        <el-table-column label="姓名" align="center" prop="ename" />
        <el-table-column label="时间" align="center" prop="checktime" show-overflow-tooltip />
      </el-table>
    </div>
  </div>
</template>
<script>
import axios from "axios";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {
  listIpaddressconfig,
  attendancerecordList
} from "@/api/access/accessControl";
//日期
import VueHorizontalCalendar from "vue-horizontal-calendar";
export default {
  name: "Raccess",
  components: {
    Treeselect,
    VueHorizontalCalendar
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
      //year: ["2022/10/31"],
      year: [],
      //当月第一天
      starttimes: null,
      //当月最后一天
      endtimes: null,
      // new date
      xinDate: null,
      // length
      length: 0,
      // 考勤机ipList
      ipList: [],
      ipDi: null,
      qian: null,
      hou: null
    };
  },
  created() {
    // // 获取列表
    this.getTiem();
    listIpaddressconfig().then(res => {
      this.ipList = res.rows;
    });
  },
  methods: {
    // 重置
    resetQuery() {
      // this.getTiem();
      this.ipDi = null;
    },
    dateChange2(day) {
      this.year = [];
      let date = new Date(day.timestamp); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
      let Y = date.getFullYear() + "-";
      let M =
        (date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1) + "-";
      let D =
        (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + " ";
      let h =
        (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":";
      let m =
        (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) +
        ":";
      let s =
        date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      var qian = Y + M + D + h + m + s;
      this.xinDate = qian;
      // console.log(qian);
      var hou = Y + M + D + "23:59:59";
      this.hou = hou;
      this.yearFn();
      this.getList(qian, hou);
    },
    // 计算已经过去的日期
    yearFn() {
      //date1：开始日期，date2结束日期
      var a1 = Date.parse(new Date(this.starttimes));
      var a2 = Date.parse(new Date(this.xinDate));
      var day = parseInt((a2 - a1) / (1000 * 60 * 60 * 24)); //核心：时间戳相减，然后除以天数
      var R = 0;
      // console.log(day);
      var a3 = new Date();
      let Y = a3.getFullYear();
      let M =
        a3.getMonth() + 1 < 10 ? "0" + (a3.getMonth() + 1) : a3.getMonth() + 1;
      for (let index = 1; index <= day; index++) {
        R = index < 10 ? "0" + index : index;
        this.year.push(`${Y}/${M}/${R}`);
      }
      // console.log(Y, M);

      // this.year = ["2022/10/01", "2022/10/02", "2022/10/03", "2022/10/04"];
    },
    // 获取当月的时间
    getTiem() {
      var date = new Date();
      // 当月第一天
      var firstDay = date.setDate(1);
      // 当月最后一天
      const currentMonth = date.getMonth();
      const nextMonth = currentMonth + 1;
      const nextMonthFirstDay = new Date(date.getFullYear(), nextMonth, 1);
      var lastDay = new Date(nextMonthFirstDay);
      // 当月第一天
      this.starttimes =
        new Date(firstDay).toISOString().slice(0, 10) + " " + "00:00:00";
      // console.log(this.starttimes);
      // 当月最后一天
      this.endtimes =
        new Date(lastDay).toISOString().slice(0, 10) + " " + "23:59:50";
    },
    group(array, subGroupLength) {
      let index = 0;
      let newArray = [];
      while (index < array.length) {
        newArray.push(array.slice(index, (index += subGroupLength)));
      }
      return newArray;
    },
    // 点击搜索
    handleQuery() {
      this.getList(this.xinDate, this.hou);
    },
    // 获取列表
    getList(startTime, endTime, number) {
      this.loading = false;
      attendancerecordList({
        startTime,
        endTime,
        createTime: startTime
      }).then(res => {
        this.loading = false;
        var groupedArray = this.group(res.rows, 15);
        this.length = groupedArray.length;
        this.bdglevenList = groupedArray;
        // this.length = res.total;
        if (groupedArray.length > 1) {
          this.bdglevenList = groupedArray;
        } else {
          this.bdglevenList = res.rows;
        }
      });
      // axios
      //   .post(
      //     // `http://${this.ipDi}/api/v2/transaction/get/?key=2633p84hnnlavzcy9afjcbtj1jdnega0ftscjkka6h7a`,
      //     `http://${this.ipDi}/api/v2/grassrootsregistration/attendancerecord/list`,
      //     {
      //       starttime,
      //       endtime
      //     }
      //   )
      //   .then(res => {
      //     this.loading = false;
      //     var groupedArray = this.group(res.data.data.items, 15);
      //     this.length = groupedArray.length;
      //     if (groupedArray.length > 1) {
      //       this.bdglevenList = groupedArray;
      //     } else {
      //       this.bdglevenList = res.data.data.items;
      //     }
      //   });
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

/* 日历样式 */
::v-deep .horizontal-calendar {
  border-top: 1px solid rgb(0, 60, 105) !important;
}
::v-deep .horizontal-calendar {
  border-bottom: 1px solid rgb(0, 60, 105);
  background-color: rgb(0, 60, 105);
}
::v-deep .horizontal-calendar .date-item {
  background-color: transparent;
  border-right: 1px solid rgb(0, 60, 105);
}
::v-deep .horizontal-calendar .date-item-date {
  color: #fff;
}
::v-deep .horizontal-calendar .date-item-day {
  color: #fff !important;
}
::v-deep .horizontal-calendar .left-arrow {
  background-color: transparent;
  border-left: 1px solid rgb(0, 60, 105) !important;
  border-right: 1px solid rgb(0, 60, 105) !important;
}
::v-deep .horizontal-calendar .right-arrow {
  background-color: transparent;
  border-left: 1px solid rgb(0, 60, 105) !important;
  border-right: 1px solid rgb(0, 60, 105) !important;
}
::v-deep .horizontal-calendar .left-arrow:hover {
  background-color: rgb(0, 60, 105) !important;
}
::v-deep .horizontal-calendar .right-arrow:hover {
  background-color: rgb(0, 60, 105) !important;
}
::v-deep .horizontal-calendar .icon {
  color: #fff;
}
::v-deep .date-highlighted {
  background: rgba(255, 255, 0, 0.5) !important;
  border-right: 1px solid rgba(255, 255, 0, 0.1) !important;
}
/* 当前日期 */
::v-deep .today {
  background-color: rgba(13, 141, 224, 0.5) !important;
}

.tables {
  display: -webkit-box;
}
.sollore {
  overflow-x: scroll;
}
</style>

