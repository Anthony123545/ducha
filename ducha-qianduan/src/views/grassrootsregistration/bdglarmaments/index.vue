<!-- 军械装备登记本领导 -->
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
        <li v-for="(item1, i) in item" :key="i">
          <div class="names">{{ item1.deptName }}</div>
          <div class="btn">
            <el-button size="btJS" @click="chaKan(item1.deptId)"
              >查看</el-button
            >
          </div>
        </li>
      </ul>
    </div>
    <div v-if="status == 0">
      <ul>
        <li v-for="(item1, i) in lieList" :key="i">
          <div class="names">{{ item1.deptName }}</div>
          <div class="btn">
            <el-button size="btJS" @click="chaKan(item1.deptId)"
              >查看</el-button
            >
          </div>
        </li>
      </ul>
    </div>
    <!-- 查看 -->
    <el-dialog
      :visible.sync="menuRoleVisible"
      :title="title"
      append-to-body
      id="chakan"
      :close-on-click-modal="false"
    >
      <bdglarmament
        v-if="menuRoleVisible"
        ref="menuRole"
        :message="wordInfo"
      ></bdglarmament>
    </el-dialog>
  </div>
</template>

<script>
import {
  armamentunits,
  armamentList,
} from "@/api/grassrootsregistration/bdglarmament";
import bdglarmament from "@/components/look_word/bdglarmament.vue";
export default {
  data() {
    return {
      lieList: [],
      //查看页面
      menuRoleVisible: false,
      title: null,
      form: {},
      year: null,
      status: null,
      form1:{}
    };
  },

  created() {
    this.getSoulist();
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
      armamentList().then((res) => {
        res.rows.forEach((item) => {
          this.status = 1;
        });
        this.lieList = res.rows;
      });
    },
    getSoulist() {
      if (this.form.year == null || this.form.year == "") {
        armamentList().then((res) => {
          res.rows.forEach((item) => {
            this.status = 1;
          });
          this.lieList = res.rows;
        });
      } else {
        armamentList(this.form.year).then((res) => {
          res.rows.forEach((item) => {
            this.status = 0;
          });
          this.lieList = res.rows;
        });
      }
    },
    chaKan(row) {
      this.form1.unitid = row;
      if (this.form.year) {
        this.form1.year = this.form.year;
      }
      armamentunits(this.form1).then((response) => {
        if (response.data.length > 0) {
          this.wordInfo = response.data;
          this.title = "查看军械装备登记簿";
          this.menuRoleVisible = true;
        } else {
          this.$message.success("当前没有数据");
        }
      });
    },
  },
  components: {
    bdglarmament,
  },

  computed: {},

  mounted() {},
  watch: {},
};
</script>
<style  scoped>
::v-deep .el-dialog {
  width: 1060px !important;
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
  display: flex;
  /* justify-content: space-between; */
  padding: 0 22px;
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
</style>
