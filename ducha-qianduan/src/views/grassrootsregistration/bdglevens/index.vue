<!-- 文连务会记录本领导 -->
<template>
  <div class="arr_conten">
    <el-form
      :model="form"
      ref="form"
      :inline="true"
      label-width="68px"
      style="padding-left: 30px"
    >
      <el-form-item prop="year">
        <el-date-picker
          v-model="form.year"
          type="year"
          placeholder="选择年"
          @change="getSoulist"
          value-format="yyyy"
          :editable="false"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button size="btr" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div v-if="status == 1">
      <ul v-for="(item, i) in lieList" :key="i + ' '">
        <li v-for="(item1, i1) in item" :key="i1">
          <div class="names">{{ item1.deptName }}</div>
          <div class="btn">
            <el-button size="btJS" @click="chaKan(item1.deptId)"
              >查看</el-button
            >
          </div>
        </li>
      </ul>
    </div>
    <!-- 搜索的时候列表 -->
    <div v-if="status == 0">
      <ul>
        <li v-for="(item, i) in lieList" :key="i">
          <div class="names">{{ item.deptName }}</div>
          <div class="btn">
            <el-button
              size="btJS"
              @click="chaKan(item.deptId)"
              v-hasPermi="['grassrootsregistration:bdglevens:query']"
              >查看</el-button
            >
          </div>
        </li>
      </ul>
    </div>

    <!-- 查看第一次 -->
    <el-dialog
      :visible.sync="tableStatus"
      :title="title"
      append-to-body
      id="list"
      :close-on-click-modal="false"
      custom-class="lieBiao"
    >
      <el-table
        v-loading="loading"
        :data="bdglevenList"
        :header-cell-style="{ background: '#003C69', color: 'white' }"
      >
        <el-table-column label="序号" type="index" width="50" align="center">
          <template scope="scope">
            <span>{{
              (queryParams.pageNum - 1) * queryParams.pageSize +
              scope.$index +
              1
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="会议议题" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ onEditor(scope.row.title) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="主持人" align="center" prop="compereRen" />
        <el-table-column
          label="出席人"
          align="center"
          prop="joinId"
          show-overflow-tooltip
        />
        <el-table-column label="召开地点" align="center" prop="address" />
        <el-table-column label="会议时间" align="center" prop="convokeTime" />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          width="200"
        >
          <template slot-scope="scope">
            <el-button size="btk" type="text" @click="handleChakan(scope.row)"
              ><span class="chakan">查看</span>
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="chaKan1"
      />
    </el-dialog>

    <!-- 查看第二次
    <el-dialog
      :visible.sync="menuRoleVisible"
      :title="title"
      append-to-body
      id="chakan"
      :close-on-click-modal="false"
      :before-close="closes"
    >
      <bdgleven
        v-if="menuRoleVisible"
        ref="menuRole"
        :message="wordInfo"
      ></bdgleven>
    </el-dialog> -->
    <!-- 查看第二次 -->
    <el-dialog
      :visible.sync="menuRoleVisible"
      :title="title"
      append-to-body
      id="chakan"
      :close-on-click-modal="false"
      :before-close="closes"
    >
      <div
        style="
          margin-bottom: 20px;
          margin-left: 28%;
          font-size: 16px;
          font-weight: 700;
        "
      >
        会议时间：<span>{{ tiemse }}</span>
      </div>
      <div v-for="item in arr" :key="item" class="divbox">
        <img :src="baseUrl + item" alt="" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getMeetRecordList,
  getBdgleven,
  getMeetRecordOnUnitList,
} from "@/api/grassrootsregistration/bdgleven";
import bdgleven from "@/components/look_word/bdgleven.vue";
export default {
  dicts: ["sys_huiyi"],
  data() {
    return {
      lieList: [],
      //查看页面
      menuRoleVisible: false,
      title: null,
      tableStatus: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 连务会记录本表格数据
      bdglevenList: [],
      loading: false,
      total: 0,
      unitId: undefined,
      form: {},
      year: null,
      status: null,
      arr: [],
      baseUrl: process.env.VUE_APP_BASE_API,
      // 会议形式
      huiYiList: [],
      names: null,
      tiemse: null,
      form1: {
        pageNum: 1,
        pageSize: 10,
      },
    };
  },

  created() {
    this.getSoulist();
    //会议形式
    this.getDicts("sys_huiyi").then((response) => {
      this.huiYiList = response.data;
    });
    this.getYear();
  },
  methods: {
    // 获取当前年
    getYear() {
      var myDate = new Date();
      var tYear = myDate.getFullYear();
      this.form1.year = tYear;
    },
    resetQuery() {
      this.form.year = null;
      this.getYear();
      getMeetRecordList().then((res) => {
        res.rows.forEach((item) => {
          this.status = 1;
        });
        this.lieList = res.rows;
      });
    },
    getSoulist() {
      if (this.form.year == null || this.form.year == "") {
        getMeetRecordList().then((res) => {
          res.rows.forEach((item) => {
            this.status = 1;
          });
          this.lieList = res.rows;
        });
      } else {
        getMeetRecordList(this.form.year).then((res) => {
          res.rows.forEach((item) => {
            this.status = 0;
          });
          this.lieList = res.rows;
        });
      }
    },
    chaKan(row) {
      this.form1.unitId = row;
      if (this.form.year) {
        this.form1.year = this.form.year;
      }
      getMeetRecordOnUnitList(this.form1).then((res) => {
        this.bdglevenList = res.rows;
        this.total = res.total;
        this.title = "查看连务会记录本";
        this.tableStatus = true;
      });
    },
    chaKan1(row) {
      this.queryParams.year=this.form1.year;
      this.queryParams.unitId=this.form1.unitId
      getMeetRecordOnUnitList(this.queryParams).then((res) => {
        this.bdglevenList = res.rows;
        this.total = res.total;
        this.title = "查看连务会记录本";
        this.tableStatus = true;
      });
    },
    handleChakan(row) {
      const id = row.id || this.ids;
      getBdgleven(id).then((response) => {
        if (response.data.fileUrl != null) {
          if (response.data.fileUrl != "") {
            this.arr = response.data.fileUrl.split(",");
            this.tiemse = response.data.convokeTime;
            this.huiYiList.map((item) => {
              if (item.dictValue == response.data.meetingForm) {
                this.names = item.dictLabel;
              }
            });
            this.title = "查看连务会记录本";
            this.menuRoleVisible = true;
            this.tableStatus = false;
            return this.name;
          }
        }
      });
    },
    // 转义视图不加标签
    onEditor(str) {
      //可以在这里进行对应的操作 quill为富文本对象,html为富文本对象内内容由html标签组成,text为quill内容里面的所有文本内容
      if (str) {
        var str = str.replace(/<\/?[^>]*>/g, ""); //去除HTML tag
        str = str.replace(/[ | ]*\n/g, "\n"); //去除行尾空白
        return str;
      }
    },
    closes() {
      this.menuRoleVisible = false;
      this.tableStatus = true;
    },
  },
  components: {
    bdgleven,
  },

  computed: {},

  mounted() {},
  watch: {},
};
</script>
<style  scoped>
::v-deep .el-dialog {
  width: 1070px !important;
}

::v-deep .el-dialog__body {
  margin: 10px 30px 20px 44px;
  padding-top: 20px !important;
  box-sizing: border-box;
}

::v-deep .el-dialog__header {
  border-bottom: 1px solid #718a9d;
}

.arr_conten {
  padding-top: 20px;
}

ul {
  /* justify-content: space-between; */
  padding: 0 22px;
  display: flex;
  flex-wrap: wrap;
}
.span {
  display: flex;
  flex-wrap: wrap;
}

li {
  list-style: none;
  background: url("../../../assets/images/book.png") no-repeat;
  height: 140px;
  width: 222px;
  /* margin: 20px 0; */
  margin-bottom: 20px;
  background-size: 100% 100%;
  flex: 0 0 16%;
  margin-right: 10px;
}

.names {
  color: #fff;
  font-size: 14px;
  text-align: center;
  padding: 26px 26px 38px 26px;
  letter-spacing: 1px;
}

.btn {
  text-align: center;
}

::v-deep .lieBiao {
  background: #004d86 !important;
  min-height: 42vh !important;
}

::v-deep .el-dialog__title {
  color: #fff;
  font: 18px;
}

::v-deep .el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}

::v-deep .pagination-container {
  /* display: none;  */
  position: relative;
  height: 49px;
  margin-bottom: 10px;
  margin-top: 6vh;
  padding: 10px 20px !important;
  background: transparent !important;
}

::v-deep .el-pagination .el-select .el-input .el-input__inner {
  background: #00365f !important;
}

::v-deep .el-pagination.is-background .btn-prev,
.el-pagination.is-background .btn-next {
  background: #00365f !important;
}

::v-deep .el-pagination__editor.el-input .el-input__inner {
  background: #00365f !important;
}

::v-deep .el-pagination.is-background .btn-next {
  background: #00365f !important;
}

::v-deep .el-pagination.is-background .el-pager li {
  background: #00365f;
}

::v-deep .el-table__body tr:hover > td {
  background-color: #3c749d !important;
}
::v-deep .el-table tr:nth-child(even) {
  background: #00365f;
}

::v-deep .el-input__inner {
  cursor: pointer !important;
}

::v-deep .el-input__inner {
  background: transparent;
  color: #fff;
  border: 1px solid white !important;
  border-color: #fff !important;
}

::v-deep .el-input--medium .el-input__inner {
  height: 36px;
  line-height: 37px;
}
#chakan ::v-deep .el-dialog {
  min-height: 700px;
  background: #fff !important;
}

#chakan /deep/ .el-dialog__body {
  padding: 30px 30px 30px 153px;
}
.divbox {
  width: 1016px !important;
  min-height: 540px;
}
.divbox img {
  width: 700px;
  height: 500px;
  border-radius: 10px;
}
img {
  object-fit: cover;
}
</style>
