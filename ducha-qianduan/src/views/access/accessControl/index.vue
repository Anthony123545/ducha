<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="ename">
        <el-input v-model="queryParams.ename" @input="handleQuery" placeholder="请输入名字"></el-input>
      </el-form-item>
      <el-form-item prop="starttime">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.starttime"
          type="datetime"
          placeholder="请选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          @change="handleQuery"
        ></el-date-picker>
      </el-form-item>
      <el-form-item prop="endtime">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.endtime"
          type="datetime"
          placeholder="请选择结束时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          @change="endtimechange"
        ></el-date-picker>
      </el-form-item>
      <el-form-item prop="sn">
        <el-select v-model="queryParams.sn" placeholder="请选择编号" @change="handleQuery" clearable>
          <el-option v-for="item in snList" :key="item.id" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <!-- <el-button size="btr" @click="handleQuery">搜索</el-button> -->
        <el-button size="btr" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleDao">导出</el-button>
      </el-col>
    </el-row>

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
      <el-table-column label="姓名" align="center" prop="ename" />
      <!-- <el-table-column
        label="单位"
        align="center"
        prop="deptname"
        show-overflow-tooltip
      />-->
      <el-table-column label="时间" align="center" prop="checktime"></el-table-column>
      <el-table-column label="打卡状态" align="center" prop="stateno">
        <template slot-scope="scope">
          <span v-if="scope.row.stateno == '0'">上班签到</span>
          <span v-if="scope.row.stateno == '1'">下班签到</span>
          <span v-if="scope.row.stateno == '2'">外出</span>
          <span v-if="scope.row.stateno == '3'">外出返回</span>
          <span v-if="scope.row.stateno == '4'">加班签到</span>
          <span v-if="scope.row.stateno == '5'">加班签退</span>
        </template>
      </el-table-column>
      <el-table-column label="设备编号" align="center" prop="sn">
        <template slot-scope="scope">
          <span
            v-if="scope.row.sn != 'COIV223360001'&&scope.row.sn != 'COIV223360129'&&scope.row.sn != 'COIV223360045'"
          >{{scope.row.sn}}</span>
          <span v-if="scope.row.sn == 'COIV223360001'">#19</span>
          <span v-if="scope.row.sn == 'COIV223360129'">支援保障营</span>
          <span v-if="scope.row.sn == 'COIV223360045'">测控营</span>
          <!-- <span v-if="scope.row.sn=='COIV223360001'">#19</span>
          <span v-if="scope.row.sn=='COIV223360001'">#19</span>-->
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getToat"
    />
  </div>
</template>

<script>
import axios from "axios";
import { listBarracks, attendancerecordList } from "@/api/access/accessControl";
export default {
  name: "access",
  data() {
    return {
      // 查询参数
      queryParams: {
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
      forms: {},
      snList: [
        { id: "COIV223360001", name: "#19" },
        { id: "COIV223360129", name: "支援保障营" },
        { id: "COIV223360045", name: " 测控营" }
      ],
      starttimes: "",
      endtimes: ""
    };
  },
  created() {
    this.getTiem();
  },
  methods: {
    getToat() {
      if (this.queryParams.starttime && this.queryParams.endtime) {
        this.getList(
          this.queryParams.starttime,
          this.queryParams.endtime,
          this.queryParams.ename
        );
      } else {
        this.getTiem();
      }
    },
    // 导出操作
    handleDao() {
      if (this.queryParams.starttime && this.queryParams.endtime) {
        var forms = {};
        forms.startTime = this.queryParams.starttime;
        forms.endTime = this.queryParams.endtime;
        listBarracks(forms).then(res => {
          // console.log(res);
          this.$download.name(res.msg);
          // this.exportLoading = false;
        });
      } else {
        this.$message.error("导出必须有开始结束时间！");
      }
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
      this.starttimes =
        new Date(firstDay).toISOString().slice(0, 10) + " " + "00:00:00";
      this.endtimes =
        new Date(lastDay).toISOString().slice(0, 10) + " " + "23:50:00";
      this.getList(this.starttimes, this.endtimes);
    },

    handleQuery() {
      if (!this.queryParams.starttime && !this.queryParams.endtime) {
        // this.queryParams.starttime = this.starttimes;
        // this.queryParams.endtime = this.endtimes;
        this.getList(
          this.starttimes,
          this.endtimes,
          this.queryParams.ename,
          this.queryParams.sn
        );
      } else {
        this.getList(
          this.queryParams.starttime,
          this.queryParams.endtime,
          this.queryParams.ename,
          this.queryParams.sn
        );
      }
    },
    endtimechange() {
      if (this.queryParams.starttime) {
        this.handleQuery();
      } else {
        this.$modal.msgSuccess("请先选择开始时间");
      }
    },
    // 重置
    resetQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.queryParams.starttime = "";
      this.queryParams.endtime = "";
      this.queryParams.ename = "";
      this.queryParams.sn = "";
      this.getTiem();
    },
    getList(starttimes, endtimes, name, sn) {
      this.loading = true;
      var formes = {};
      formes.startTime = starttimes;
      formes.endTime = endtimes;
      formes.endtime = starttimes;
      formes.starttime = endtimes;
      formes.ename = name;
      formes.sn = sn;
      formes.pageNum = this.queryParams.pageNum;
      formes.pageSize = this.queryParams.pageSize;
      attendancerecordList(formes).then(res => {
        this.loading = false;
        this.bdglevenList = res.rows;
        this.total = res.total;
      });
      // axios
      //   .post(
      //     "http://192.168.5.188:85/api/v2/transaction/get/?key=2633p84hnnlavzcy9afjcbtj1jdnega0ftscjkka6h7a",
      //     {
      //       starttime,
      //       endtime,
      //     }
      //   )
      //   .then((res) => {
      //     this.loading = false;
      //     if (res.data.ret != 174) {
      //       this.bdglevenList = res.data.data.items;
      //       this.total = res.data.data.items.length;
      //     }
      //   });
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
  width: 882px;
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

::v-deep .el-dialog__body {
  /* margin: 0px 20px; */
  box-sizing: border-box;
  padding: 30px 0px 30px 42px;
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
/* .el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
  background-color: #004d86;
  color: #fff;
} */
/* .el-select-dropdown__item {
  color: #fff;
} */
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
::v-deep .el-select__input {
  color: #fff !important;
}
</style>
