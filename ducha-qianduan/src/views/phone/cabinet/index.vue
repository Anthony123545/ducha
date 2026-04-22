<template>
  <div class="app-container">
    <div class="box" v-if="phone">
      <ul>
        <li class="oneUl" v-for="(item, i) in cabinetList" :key="i">
          <div
            @click="opens(item.number)"
            style="
              width: 265px;
              height: 230px;
              cursor: pointer;
              text-align: center;
            "
          >
            <div class="title">{{ item.departmentInfo.name }}</div>
            <table class="table">
              <tr>
                <td style="text-align: left; width: 86px">柜子编号</td>
                <td style="text-align: right; width: 145px">
                  {{ item.number }}
                </td>
              </tr>
              <tr>
                <td style="text-align: left; width: 86px">柜子IP</td>
                <td style="text-align: right; width: 145px">
                  {{ item.ip }}
                </td>
              </tr>
              <tr>
                <td style="text-align: left; width: 86px">格子数量</td>
                <td style="text-align: right; width: 145px; color: #00f6ff">
                  {{ item.gridCount }}
                </td>
              </tr>
              <tr>
                <td style="text-align: left; width: 86px">连接信息</td>
                <td
                  style="text-align: right; width: 145px; color: #13ce66"
                  v-if="item.connected != '断开连接'"
                >
                  {{ item.connected }}
                </td>
                <td
                  style="text-align: right; width: 145px; color: #ff4949"
                  v-else
                >
                  {{ item.connected }}
                </td>
              </tr>
              <tr>
                <td style="text-align: left; width: 86px">开门状态</td>
                <td
                  style="text-align: right; width: 145px; color: #13ce66"
                  v-if="item.connected != '允许开启'"
                >
                  {{ item.allowOpen }}
                </td>
                <td
                  style="text-align: right; width: 145px; color: #ff4949"
                  v-else
                >
                  {{ item.allowOpen }}
                </td>
              </tr>
            </table>
          </div>
        </li>
      </ul>
    </div>
    <div class="box1" v-if="phone2">
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
      >
        <el-form-item prop="gridNumber">
          <div
            style="
              width: 87px;
              height: 36px;
              text-align: center;
              border-radius: 4px;
              margin-left: 31px;
              background-color: #196299;
              cursor: pointer;
            "
            @click="dianJi"
          >
            <i class="el-icon-arrow-left" style="color: #fff">返回</i>
          </div>
        </el-form-item>
        <!-- <el-form-item prop="gridNumber">
          <el-input
            v-model="queryParams.cabinetNum"
            placeholder="请输入格子编号"
            clearable
            size="small"
            @input="opens"
          />
        </el-form-item>
        <el-form-item>
          <el-button size="btp" @click="resetQuery">重置</el-button>
        </el-form-item> -->
        <div class="shuJu">
          <span class="box4 public"> 已存放 :{{ green }}个</span>
          <span class="box5 public"> 未放入 :{{ red }}个</span>
          <span class="box6"> 已请假 :{{ yellow }}个</span>
          <span class="box7"> 未绑定 :{{ grey }}个</span>
        </div>
      </el-form>
      <ul class="twoUl">
        <li
          v-for="(item, i) in gridList"
          :key="i"
          :class="{
            greed: item.gridStateCode == '3',
            red: item.gridStateCode == '2',
            yellow: item.gridStateCode == '4',
            grey: item.gridStateCode == '1',
          }"
          class="orging"
        >
          <div class="two_div">
            <span>{{ item.deviceId }}</span>
          </div>
          <el-button
            size="mini"
            @click.native="buttOn(item.staffInfoWithId, item)"
            >{{ item.staffInfoWithId.Name }}</el-button
          >
        </li>
      </ul>
    </div>

    <!-- 格子记录弹出层 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="860px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree">
        <div class="box2">
          <el-form
            ref="form"
            :model="form"
            :rules="rules"
            inline
            label-position="right"
          >
            <div class="jiben">基本信息</div>
            <el-form-item label="柜子编号 :" prop="number">
              <div style="width: 100px">{{ cabinetNumber }}</div>
            </el-form-item>
            <el-form-item label="格子编号 :" prop="storageQuantity">
              <div style="width: 100px">{{ deviceId }}</div>
            </el-form-item>
            <el-form-item label="存物状态 :" prop="address">
              <div style="width: 100px">{{ stoneState }}</div>
            </el-form-item>
            <el-form-item label="发生时间 :" prop="gridNumber">
              <div style="width: 120px">{{ happenTime }}</div>
            </el-form-item>

            <div style="margin-top: -30px; margin-bottom: 7px">
              <img src="../../../assets/images/虚线1.png" alt="" />
            </div>
            <el-form-item label="绑定人员 :" prop="number">
              <div style="width: 100px">{{ jiBen.Name }}</div>
            </el-form-item>
            <el-form-item label="性ㅤㅤ别 :" prop="address">
              <div style="width: 100px" v-if="jiBen.Sex == '1'">男</div>
              <div style="width: 100px" v-if="jiBen.Sex == '2'">女</div>
              <div style="width: 100px" v-else></div>
            </el-form-item>
            <el-form-item label="请假状态 :" prop="storageQuantity">
              <div style="width: 100px" v-if="jiBen.VacationState == '1'">
                请假
              </div>
              <div style="width: 100px" v-if="jiBen.VacationState == '2'">
                正常
              </div>
              <div style="width: 100px" v-else></div>
            </el-form-item>
            <!-- <el-form-item label="所在单位 :" prop="gridNumber">
              <div style="width: 120px">{{ departmentName }}</div>
            </el-form-item> -->
            <div class="jiben">存取记录</div>
            <el-table
              v-loading="loading"
              :data="tableData"
              style="width: 100%; padding-right: 40px"
              :header-cell-style="{ background: '#004274', color: 'white' }"
            >
              <el-table-column label="序号" width="50px">
                <template scope="scope">
                  <span>{{
                    (queryParams.pageNo - 1) * queryParams.pageSize +
                    scope.$index +
                    1
                  }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="cabinetNumber" label="编号">
              </el-table-column>
              <el-table-column prop="staffName" label="姓名"> </el-table-column>
              <el-table-column prop="happenTime" label="发生时间" width="140px">
              </el-table-column>
              <el-table-column prop="warnState" label="存物状态">
              </el-table-column>
            </el-table>
          </el-form>
        </div>
      </div>
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNo"
        :limit.sync="queryParams.pageSize"
        @pagination="offMeng"
      />
      <!-- <div slot="footer" class="dialog-footer"></div> -->
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import {
  getCabinet,
  delCabinet,
  addCabinet,
  updateCabinet,
  exportCabinet,
  xianPhone,
  xianPhoneGeZi,
  xianPhoneOppe,
  getToken,
} from "@/api/phone/cabinet";
import { from } from "../../../../public/assets/libs/moment/src/lib/moment/from";
import Cookies from "js-cookie";

export default {
  name: "Cabinet",
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
      // 手机柜表格数据
      cabinetList: [],
      // 手机柜格子列表
      gridList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        number: null,
        address: null,
        gridNumber: null,
        storageQuantity: null,
        exceptionsNumber: null,
        unitId: null,
        unitName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 手机柜第一个显示与隐藏
      phone: true,
      // 第二个显示与隐藏
      phone2: false,
      tableData: [],
      // 统计个数
      red: 0,
      green: 0,
      yellow: 0,
      grey: 0,
      // 基本人员数据
      jiBen: {},
      // 柜子编号
      cabinetNumber: null,
      // 格子编号
      deviceId: null,
      // 存物状态
      stoneState: null,
      // 发生时间
      happenTime: null,
      // 单位名字
      departmentName: null,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 开关们记录查询
    offMeng() {
      this.loading = true;
      const params = this.queryParams;
      xianPhoneOppe(params).then((res) => {
        this.tableData = res.data.infos;
        this.total = res.data.totalCount;
        this.happenTime = res.data.infos[0].happenTime;
        this.departmentName = res.data.infos[0].departmentName;
        this.loading = false;
      });
    },
    // 点击名字触发
    buttOn(row, data) {
      this.jiBen = row;
      this.deviceId = data.deviceId;
      this.stoneState = data.stoneState;
      this.queryParams.pageNo = 1;
      this.offMeng();
      // this.open = true;
      this.title = "柜子记录";
    },
    // 点击返回触发
    dianJi() {
      this.phone2 = false;
      this.phone = true;
      this.queryParams.gridNumber = null;
      this.red = 0;
      this.green = 0;
      this.yellow = 0;
      this.grey = 0;
    },
    // 点击手机柜触发
    opens(cabinetNum) {
      this.phone = false;
      this.phone2 = true;
      this.cabinetNumber = cabinetNum;
      xianPhoneGeZi(this.cabinetNumber).then((res) => {
        this.gridList = res.data.infos;

        this.gridList.forEach((item) => {
          if (item.gridStateCode == "2") {
            this.red += 1;
          } else if (item.gridStateCode == "3") {
            this.green += 1;
          } else if (item.gridStateCode == "4") {
            this.yellow += 1;
          } else {
            this.grey += 1;
          }
        });
      });
    },
    /** 查询手机柜列表 */
    getList() {
      console.log("查询手机柜列表");
      xianPhone().then((res) => {
        console.log("查询手机柜列表", res);
        this.cabinetList = res.data.infos;
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
        number: null,
        address: null,
        gridNumber: null,
        storageQuantity: null,
        exceptionsNumber: null,
        unitId: null,
        unitName: null,
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
      this.title = "添加手机柜";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getCabinet(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改手机柜";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateCabinet(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCabinet(this.form).then((response) => {
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
        .confirm("是否确认删除手机柜的数据项？")
        .then(function () {
          return delCabinet(ids);
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
        .confirm("是否确认导出所有手机柜数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportCabinet(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
  },
  watch: {},
};
</script>
<style scoped>
* {
  padding: 0;
}
ul {
  list-style-type: none;
}
.box ul {
  /* display: flex;
  flex-flow: wrap; */
  display: flex;
  flex-flow: wrap;
  margin-left: 2%;
  padding-left: -1%;
  padding-top: 28px;
}
.oneUl {
  flex: 1;
  /* display: inline-block; */
  background: url("../../../assets/images/柜子2.png") no-repeat;
  /* width: 306px; */
  height: 285px;
  padding: 22px;
  min-width: 308px;
  margin-bottom: 40px;
}
.title {
  color: #fff;
  height: 16px;
  display: inline-table;
}
.table {
  margin-top: 18px;
  line-height: 32px;
  padding-left: 12px;
  color: #fff;
  cursor: pointer;
}
.mb9 {
  margin-bottom: 20px;
  margin-top: 10px;
}
::v-deep .el-input__inner {
  color: #fff;
  /* width: 200px; */
  height: 36px;
}
.red {
  background: url("../../../assets/images/抽屉-红色1.png") no-repeat !important;
}
.greed {
  background: url("../../../assets/images/抽屉-绿色1.png") no-repeat !important;
}
.yellow {
  background: url("../../../assets/images/抽屉-黄色1.png") no-repeat !important;
}
.grey {
  background: url("../../../assets/images/柜子-灰.png") no-repeat !important;
}
.orging {
  background: url("../../../assets/images/柜子-灰.png") no-repeat;
}

.box1 {
  padding-top: 17px;
  padding-left: 24px;
}
.twoUl li {
  height: 114px;
  padding: 20px;
  margin-right: 22px;
  margin-bottom: 43px;
  min-width: 175px;

  /* margin-bottom: 43px;
  flex: 1;
  max-width: 173px;
  margin-right: 2%; */
}
.twoUl {
  /* display: inline-block;
  padding-left: 0px;
  margin-left: 10px;
  margin-top: 0px;
  margin-bottom: 38px;
  margin-right: 14px; */
  padding-left: 12px;
  display: flex;
  flex-flow: wrap;
  margin-top: 0px;
}
.two_div {
  text-align: center;
  margin-top: 10px;
  padding-top: 10px;
  color: #fff;
}
::v-deep .el-button--mini {
  margin-left: 25px !important;
  margin-top: 6px !important;
  background: #1263aa;
  border-color: #1263aa;
  color: #ffffff;
  width: 82px;
  height: 30px;
}
.shuJu {
  height: 16px;
  margin-top: 26px;
  margin-right: 90px;
  float: right;
  color: #fff;
  font-size: 14px;
}

.box4::before {
  display: inline-block;
  content: "";
  width: 8px;
  height: 8px;
  /* border-radius: 50%; */
  background-color: #67c23a;
  margin-left: 26px;
}
.box5::before {
  display: inline-block;
  content: "";
  width: 8px;
  height: 8px;
  /* border-radius: 50%; */
  background-color: #ff4949;
  margin-left: 26px;
}
.box6::before {
  display: inline-block;
  content: "";
  width: 8px;
  height: 8px;
  /* border-radius: 50%; */
  background-color: #ffba00;
  margin-left: 26px;
}
.box7::before {
  display: inline-block;
  content: "";
  width: 8px;
  height: 8px;
  /* border-radius: 50%; */
  background-color: #98a7bc;
  margin-left: 26px;
}
::v-deep .el-dialog {
  background-color: #004d86;
  margin: auto;
}
::v-deep .el-dialog__header {
  border-bottom: 1px solid #718a9d;
  margin: auto;
}
::v-deep .el-dialog__title {
  color: #fff;
  font: 18px;
}
.box2 form .el-form-item {
  color: #fff;
  margin-right: 20px;
}

/*  鼠标经过颜色 */
::v-deep .el-table__body tr:hover > td {
  background-color: transparent !important;
}
::v-deep .box1 form {
  margin-left: -14px;
}
::v-deep .box1 form .el-form-item {
  margin-top: 17px;
}
::v-deep .el-form-item {
  margin-bottom: 29px;
}
.jiben {
  width: 100%;
  height: 32px;
  background-image: url(/static/img/小标题底.b7c6f45a.png);
  margin-bottom: 18px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
  background-size: contain;
}
::v-deep .el-table tr:nth-child(odd) {
  background: #004d86;
}
::v-deep .el-dialog__body {
  padding: 30px 0px 30px 40px;
}
::v-deep .el-form-item__label {
  padding: 0 0 0 0;
}
/* 重置按钮 */
.el-button--btp.is-active,
.el-button--btp:active {
  background: #3da1f8;
  border-color: #3da1f8;
  color: #ffffff;
}

.el-button--btp:focus,
.el-button--btp:hover {
  background: #3da1f8;
  border-color: #3da1f8;
  color: #ffffff;
}

.el-button--btp {
  color: #ffffff;
  width: 76px;
  height: 36px;
  background-color: #1d96ff;
  border-radius: 4px;
  font-size: 14px;
  text-align: center;
  margin-top: 1px;
}
::v-deep .el-dialog {
  border-radius: 8px;
}
::v-deep .has-gutter tr {
  height: 60px;
}
/* 分页样式 */
::v-deep .pagination-container {
  background: #004d85 !important;
}
::v-deep .el-pagination__total {
  margin-top: 2px !important;
}
::v-deep .el-pagination .el-select .el-input .el-input__inner {
  background-color: #196299 !important;
  height: 30px;
}
::v-deep .el-pagination.is-background .btn-prev,
.el-pagination.is-background .btn-next {
  background-color: #265070 !important;
}
::v-deep .el-pagination.is-background .el-pager li {
  background-color: #196299 !important;
}
::v-deep .el-pagination__editor.el-input .el-input__inner {
  background-color: #196299 !important;
}
::v-deep .el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #1890ff !important;
}
::v-deep .el-pagination.is-background .btn-next {
  background: #265070 !important;
}
::v-deep .el-pagination__sizes {
  display: none !important;
}
::v-deep .el-table {
  background-color: transparent;
}
::v-deep .el-pagination {
  padding: 2px 54px;
}
</style>
