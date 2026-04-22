<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="unitName">
        <el-input
          placeholder="请输入单位"
          v-model="queryParams.unitName"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item label="" prop="time">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择时间"
          :editable="false"
          @change="tiem"
        >
        </el-date-picker>
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
          v-hasPermi="['grassrootsregistration:bdglweekwork:add']"
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
          v-hasPermi="['grassrootsregistration:bdglweekwork:edit']"
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
          v-hasPermi="['grassrootsregistration:bdglweekwork:remove']"
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
          v-hasPermi="['grassrootsregistration:bdglweekwork:export']"
          >导出</el-button
        >
      </el-col> -->
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="bdglweekworkList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      style="width: 100%"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="工作标题" align="center" prop="name" />
      <el-table-column
        label="开始时间"
        align="center"
        prop="startTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="结束时间"
        align="center"
        prop="endTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="连值班领导" align="center" prop="peopleName" />
      <el-table-column label="电话" align="center" prop="phone" />
      <el-table-column label="军事主官" align="center" prop="people1Name" />
      <el-table-column label="政治主官" align="center" prop="people2Name" />
      <!-- <el-table-column label="时间" align="center" prop="time" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.time, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="单位" align="center" prop="unitId" />
      <el-table-column label="填写人" align="center" prop="adminId" /> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
      >
        <template slot-scope="scope">
          <el-button
            size="btk"
            type="text"
            @click="handleChakan(scope.row)"
            v-hasPermi="['grassrootsregistration:bdgldiary:edit']"
            ><span class="chakan">查看</span></el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglweekwork:edit']"
            ><span class="edit">修改</span></el-button
          >
          <el-button
            size="small"
            type="warning"
            @click="handleCopy(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglweekwork:edit']"
            ><span class="edit">复制</span></el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglweekwork:remove']"
            ><span class="delete">删除</span></el-button
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

    <!-- 添加或修改一周工作安排对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1016px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree">
        <div class="jiben">基本信息</div>
        <el-form
          ref="refform"
          :model="form"
          :rules="rules"
          label-width="80px"
          append-to-body
          :inline="true"
        >
          <el-form-item label="单位" prop="unitId">
            <treeselect
              v-model="form.unitId"
              :options="deptOptions"
              :show-count="true"
              placeholder="请选择单位"
              @select="department"
              @open="blurSelect"
            />
            <!-- @input="department" -->
          </el-form-item>
          <el-form-item label="连值班领导" prop="peopleId">
            <el-select v-model="form.peopleId" placeholder="请选择值连值班领导">
              <el-option
                v-for="item in renYuan"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                @click.native="zhiBan(item.name)"
                class="input_xiala"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入电话" />
          </el-form-item>
          <el-form-item label="军事主官" prop="peopleId1">
            <el-select
              ref="aa"
              v-model="form.peopleId1"
              placeholder="请选择军事主官"
            >
              <el-option
                v-for="item in renYuan"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                @click.native="junShi(item.name)"
                class="input_xiala"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="政治主官" prop="peopleId2">
            <!-- <el-input v-model="form.peopleId2" placeholder="请输入政治长官" /> -->
            <el-select v-model="form.peopleId2" placeholder="请选择政治主官">
              <el-option
                v-for="item in renYuan"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                @click.native="zhengZhi(item.name)"
                class="input_xiala"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="工作标题" prop="name">
            <el-input v-model="form.name" placeholder="请输入工作标题" />
          </el-form-item>
          <el-form-item label="开始时间" prop="startTime">
            <el-date-picker
              clearable
              size="small"
              :editable="false"
              v-model="form.startTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择开始时间"
              @change="startTimes"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间" prop="endTime">
            <el-date-picker
              clearable
              size="small"
              :editable="false"
              v-model="form.endTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择结束时间"
              @change="endTimes"
            >
            </el-date-picker>
          </el-form-item>
          <!-- 文本对话框--工作登记表单 -->
          <div class="jiben">工作登记</div>
          <div class="box1" v-for="(item, i) in tomList" :key="i">
            <el-form
              ref="form"
              :model="form"
              :rules="rules"
              label-width="80px"
              :inline="true"
            >
              <el-form-item label="" prop="daytime">
                <el-date-picker
                  v-model="item.time"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="选择时间"
                  @change="
                    (val) => {
                      change_time(val, i);
                    }
                  "
                >
                </el-date-picker>
              </el-form-item>
              <el-form-item label="" prop="week">
                <!-- <el-select v-model="item.week" placeholder="星期">
                <el-option
                  v-for="item in week"
                  :key="item.value"
                  :label="item.label"
                  :value="item.label"
                  class="input_xiala"
                >
                </el-option>
              </el-select> -->
                <el-input v-model="item.week" disabled />
              </el-form-item>
              <el-form-item label="" prop="day">
                <el-select
                  v-model="item.dateline"
                  @change="datelinefun"
                  placeholder="早晨"
                >
                  <el-option
                    v-for="item in day"
                    :key="item.value"
                    :label="item.label"
                    :value="item.label"
                    class="input_xiala"
                  >
                  </el-option>
                </el-select>
              </el-form-item>

              <el-time-select
                class="b1"
                placeholder="起始时间"
                v-model="item.startTime"
                :picker-options="option"
              >
              </el-time-select>
              <el-time-select
                class="b1"
                placeholder="结束时间"
                v-model="item.endTime"
                :picker-options="option"
              >
              </el-time-select>
              <el-form-item label="" prop="unitId5">
                <el-input v-model="item.unitId1" placeholder="单位" readonly />
              </el-form-item>
              <div class="box2">
                <el-form-item label="" prop="organizer">
                  <el-input
                    v-model="item.organizer"
                    placeholder="组织人"
                    clearable
                    size="small"
                  />
                </el-form-item>
                <el-form-item label="" prop="personnel">
                  <el-input
                    v-model="item.peopleIds"
                    placeholder="参加人员"
                    clearable
                    size="small"
                  />
                </el-form-item>
                <el-form-item label="" prop="place">
                  <el-input
                    v-model="item.address"
                    placeholder="地点"
                    clearable
                    size="small"
                  />
                </el-form-item>
              </div>
              <div class="box3">
                <el-form-item label="" prop="content">
                  <el-input
                    type="textarea"
                    v-model="item.contents"
                    placeholder="内容"
                    clearable
                    size="small"
                  />
                </el-form-item>
              </div>
              <!-- 工作登记-添加删除按钮 -->
              <div class="btn">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  @click="tianJia"
                  v-if="i == tomList.length - 1"
                ></el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  @click="jtDelete(i)"
                  v-if="i != 0"
                ></el-button>
              </div>
            </el-form>
          </div>

          <div class="jiben">备注</div>
          <div class="box4">
            <el-input
              type="textarea"
              :rows="5"
              placeholder="请输入内容"
              v-model="form.remark"
              autosize
            >
            </el-input>
          </div>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="resertwo" size="btn">重置</el-button>
      </div>
    </el-dialog>
    <!-- 查看 -->
    <el-dialog
      :visible.sync="menuRoleVisible"
      :title="title"
      append-to-body
      id="chakan"
      :close-on-click-modal="false"
    >
      <bdglweekwork
        v-if="menuRoleVisible"
        ref="menuRole"
        :message="wordInfo"
      ></bdglweekwork>
    </el-dialog>
  </div>
</template>

<script>
import bdglweekwork from "@/components/look_word/bdglweekwork.vue";

import {
  listBdglweekwork,
  getBdglweekwork,
  delBdglweekwork,
  addBdglweekwork,
  updateBdglweekwork,
  exportBdglweekwork,
  exportBdglgetWeekWork,
} from "@/api/grassrootsregistration/bdglweekwork";

import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {
  getZhuChiRen,
  getDept,
  getShouBu,
} from "@/api/grassrootsregistration/bdglmeeting";

import { jieZhiList } from "@/api/grassrootsregistration/bdgldiary";
import request from "@/utils/request";
export default {
  name: "Bdglweekwork",
  components: { Treeselect, bdglweekwork },
  data() {
    return {
      //查看页面
      menuRoleVisible: false,
      wordInfo: {},
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
      // 一周工作安排表格数据
      bdglweekworkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        startTime: null,
        endTime: null,
        peopleId: null,
        phone: null,
        peopleId1: null,
        peopleId2: null,
        time: null,
        unitId: null,
        adminId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitId: [
          { required: true, message: "单位名称不能为空", trigger: "change" },
        ],
        startTime: [
          { required: true, message: "时间不能为空", trigger: "change" },
        ],
      },
      //下拉s
      options: [],
      //备注文本内容
      textarea: "",
      //工作等级时间（开始-结束）
      startTime: "",
      endTime: "",
      //星期选择
      week: [
        {
          value: "选项1",
          label: "星期一",
        },
        {
          value: "选项2",
          label: "星期二",
        },
        {
          value: "选项3",
          label: "星期三",
        },
        {
          value: "选项4",
          label: "星期四",
        },
        {
          value: "选项5",
          label: "星期五",
        },
        {
          value: "选项6",
          label: "星期六",
        },
        {
          value: "选项7",
          label: "星期日",
        },
      ],
      //早中晚选中
      day: [
        {
          value: "选项1",
          label: "早晨",
        },
        {
          value: "选项2",
          label: "上午",
        },
        {
          value: "选项3",
          label: "下午",
        },
        {
          value: "选项4",
          label: "晚上",
        },
      ],
      //  树形列表
      deptOptions: [],
      // 人员列表
      renYuan: [],
      // 一周工作安排字段
      tomList: [
        {
          week: null,
          time: null,
          deteline: null,
          startTime: null,
          endTime: null,
          unitId1: null,
          peopleIds: null,
          address: null,
          contents: null,
          organizer: null,
        },
      ],
      // 搜索单位列表
      deptOptionss: [],
      // 已有时间不能发送
      code: 0,
      // 单位的保存
      names: "",
      // start: "06:00",
      // end: "08:00",

      option: {
        start: "06:00",
        step: "00:05",
        end: "08:00",
      },
      // 工作安排时间
      gongZuoTiem: null,
      // 当月日期
      current_month: [],
      // 下月日期
      current_month1: [],
      // 日期开关状态
      dayTime: null,
    };
  },
  created() {
    console.log("this.queryParams", this.queryParams);
    this.getList();
    // 获取部门
    this.getDept();
    // 获取当月
    this.getDay();
    // 获取下月
    this.getDayX();
    jieZhiList().then((res) => {
      this.dayTime = res.rows[0].time;
    });
  },
  methods: {
    // 获取当月日期
    getDay() {
      // 获取标准时间
      let now = new Date();
      // 年
      var year = new Date().getFullYear();
      // 月
      var month = new Date().getMonth() + 1;
      // 日
      var day = new Date(year, month, 0).getDate();
      // console.log(now);
      let current_month_num = day;
      this.current_month = [];
      for (let i = 1; i <= current_month_num; i++) {
        let day = now.setDate(i);
        let date = new Date(day); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        let Y = date.getFullYear() + "-";
        let M =
          (date.getMonth() + 1 < 10
            ? "0" + (date.getMonth() + 1)
            : date.getMonth() + 1) + "-";
        let D =
          (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "";
        this.current_month.push(Y + M + D);
        // console.log(this.current_month);
      }
    },
    // 获取下个月日期
    getDayX() {
      // 获取标准时间
      let now = new Date();
      // 年
      var year = new Date().getFullYear();
      // 月
      var month = new Date().getMonth() + 2;
      // 日
      var day = new Date(year, month, 0).getDate();
      let current_month_num1 = day;
      for (let i = 1; i <= current_month_num1; i++) {
        let day = now.setDate(i);
        let date = new Date(day); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        let Y = date.getFullYear() + "-";
        let M =
          (date.getMonth() + 2 < 10
            ? "0" + (date.getMonth() + 2)
            : date.getMonth() + 2) + "-";
        let D =
          (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "";
        this.current_month.push(Y + M + D);
      }
    },
    //增加工作登记表单
    tianJia() {
      this.tomList.push({
        week: null,
        time: null,
        deteline: null,
        startTime: null,
        endTime: null,
        unitId1: this.names || this.form.unitName,
        peopleIds: null,
        address: null,
        contents: null,
        organizer: null,
      });
    },
    //点击删除工作登记
    jtDelete(i) {
      this.tomList.splice(i, 1);
    },
    // 搜索时间触发
    tiem() {
      this.getList();
    },

    change_time(date, i) {
      this.gongZuoTiem = date;
      if (
        new Date(this.gongZuoTiem) >= new Date(this.form.startTime) &&
        new Date(this.gongZuoTiem) <= new Date(this.form.endTime)
      ) {
      } else {
        this.$modal.msgWarning("请选择开始时间与结束时间区级内的日期");
      }
      if (date) {
        this.tomList[i].week = this.getNowWeek(date);
      } else {
        this.tomList[i].week = "";
      }
    },

    // 搜索单位触发
    host() {
      this.getList();
    },
    // 查询单位列表
    getDept() {
      getShouBu().then((res) => {
        this.deptOptionss = res.data;
      });
    },
    // 政治人员选中触发
    zhengZhi(name) {
      this.form.people2Name = name;
    },
    // 军事人员选中触发
    junShi(name) {
      this.form.people1Name = name;
    },
    // 值班领导选中触发
    zhiBan(name) {
      this.form.peopleName = name;
    },
    // 获取人员
    ZhuChiRen(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 重置人员
    ChongZhi() {
      (this.form.peopleId = null),
        (this.form.peopleId1 = null),
        (this.form.peopleId2 = null);
    },
    // 选中单位触发
    department(data) {
      this.ChongZhi();
      this.form.unitId = data.id;
      this.form.unitName = data.label;
      this.names = data.label;
      this.ZhuChiRen(this.form.unitId);
      this.tomList.forEach((item, i) => {
        this.tomList[i].unitId1 = this.form.unitName;
      });
      if (
        this.form.unitId != null &&
        this.form.startTime != null &&
        this.form.endTime != null
      ) {
        this.form.bdglWeekworkRegisterList = null;
        exportBdglgetWeekWork(this.form).then((res) => {
          this.code = res.code;
          if (res.code == 200) {
            this.$modal.msgWarning(res.msg);
          }
        });
      }
    },
    // 开始时间触发
    startTimes(val) {
      if (
        this.form.unitId != null &&
        this.form.startTime != null &&
        this.form.endTime != null
      ) {
        this.form.bdglWeekworkRegisterList = null;
        exportBdglgetWeekWork(this.form).then((res) => {
          this.code = res.code;
          if (res.code == 200) {
            this.$modal.msgWarning(res.msg);
          }
        });
      }

      // 选择的时间戳
      var date1 = Math.round(new Date(val).getTime() / 1000);

      // 获取当月的第一天和最后一天
      let now = new Date(val); // 用传入的 val 来确定月份
      let firstDay = new Date(now.getFullYear(), now.getMonth(), 1, 0, 0, 0);
      let lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0, 23, 59, 59);

      let startTimestamp = Math.round(firstDay.getTime() / 1000);
      let endTimestamp = Math.round(lastDay.getTime() / 1000);

      // 限制范围
      if ((date1 < startTimestamp || date1 > endTimestamp) && this.dayTime == 1) {
        this.$confirm("请在当月规定时间内填写", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.form.startTime = null;
          })
          .catch(() => {
            this.form.startTime = null;
          });
      }
    },
    // 结束时间触发
    endTimes() {
      if (
        this.form.unitId != null &&
        this.form.startTime != null &&
        this.form.endTime != null
      ) {
        this.form.unitName = null;
        this.form.bdglWeekworkRegisterList = null;
        exportBdglgetWeekWork(this.form).then((res) => {
          this.code = res.code;
          if (res.code == 200) {
            this.$modal.msgWarning(res.msg);
          }
        });
      }
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.deptOptions = response.data;
      });
    },
    /** 查询一周工作安排列表 */
    // getList() {
    //   this.loading = true;
    //   listBdglweekwork(this.queryParams).then((response) => {
    //     this.bdglweekworkList = response.rows;
    //     console.log("周工作列表",response.rows)
    //     this.total = response.total;
    //     this.loading = false;
    //   });
    // },
    getList() {
      this.loading = true;
      request({
        url: "/grassrootsregistration/bdglweekwork/list",
        method: "get",
        params: this.queryParams
      }).then(response => {
        console.log("直接请求接口返回：", response);
        this.bdglweekworkList = response.rows || [];
        this.total = response.total || 0;
        this.loading = false;
      }).catch(() => {
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
        name: null,
        startTime: null,
        endTime: null,
        peopleId: null,
        phone: null,
        peopleId1: null,
        peopleId2: null,
        time: null,
        remark: null,
        unitId: null,
        adminId: null,
        peopleName: null,
        people1Name: null,
        people2Name: null,
        unitId: null,
        adminId: null,
        //工作登记
        week: null,
        daytime: null,
        day: null,
        department: null,
        personnel: null,
        place: null,
        content: null,
      };
      // 一周工作安排字段
      (this.tomList = [
        {
          week: null,
          time: null,
          deteline: null,
          startTime: null,
          endTime: null,
          unitId1: null,
          peopleIds: null,
          address: null,
          contents: null,
          organizer: null,
        },
      ]),
        this.resetForm("refform");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.startTime = null;
      this.handleQuery();
    },
    /** 对话框重置按钮操作 */
    resertwo() {
      this.reset();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      // 人员列表
      (this.renYuan = []), this.getTreeselect();
      this.names = null;
      this.reset();
      this.open = true;
      this.title = "添加一周工作安排";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      this.form.unitId = row.unitId;
      this.ZhuChiRen(this.form.unitId);
      const id = row.id || this.ids;
      getBdglweekwork(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改一周工作安排";
        // 赋值
        this.tomList = response.data.bdglWeekworkRegisterList;
        this.gongZuoTiem = response.data.bdglWeekworkRegisterList[0].time;
      });
    },
     /** 复制按钮操作 */
     handleCopy(row) { 
      this.reset();
      this.getTreeselect();
      this.form.unitId = row.unitId;
      this.ZhuChiRen(this.form.unitId);
      const id = row.id || this.ids;
      getBdglweekwork(id).then((response) => {
        console.log("复制按钮0",this.form)
        this.form = response.data;
        console.log("复制按钮1",this.form)
        this.tomList = response.data.bdglWeekworkRegisterList;
        // 添加安全检查防止数组为空
        if (response.data.bdglWeekworkRegisterList && response.data.bdglWeekworkRegisterList.length > 0) {
          this.gongZuoTiem = response.data.bdglWeekworkRegisterList[0].time;
        }
        // 在获取数据完成后再执行添加操作，确保数据一致性
        this.form.id = null;
        for (let i = 0; i < this.form.bdglWeekworkRegisterList.length; i++) {
          this.form.bdglWeekworkRegisterList[i].id = null;
        }
        //name前面加"副本"+1,2,3 + "-",如果name已经有"副本"+1,2,3 + "-"，则进行+1操作
        if (this.form.name.includes("副本")) {
          // 使用正则表达式匹配"副本"+数字的模式
          const reg = /副本(\d+)$/;
          const match = this.form.name.match(reg);
          
          if (match) {
            const num = parseInt(match[1]);
            const baseName = this.form.name.substring(0, this.form.name.length - match[1].length);
            this.form.name = baseName + (num + 1);
          } else {
            // 如果有"副本"但不匹配模式，则直接添加"-副本1"
            this.form.name = this.form.name + "-副本1";
          }
        } else {
          this.form.name = this.form.name + "-副本1";
        }

        return addBdglweekwork(this.form);
      }).then((response) => {
        this.$modal.msgSuccess("新增副本成功");
        this.getList();
      }).catch((error) => {
        // 添加错误处理
        this.$modal.msgError("操作失败，请重试");
        console.error("复制操作失败:", error);
      });
      // copyBdglweekwork(id).then((response) => {
      //   if (response.code == 200) {
      //     this.$modal.msgSuccess(response.msg);
      //     this.getList();
      //   } else {
      //     this.$modal.msgError(response.msg);
      //   }
      // });

    },
    //查看按钮操作
    handleChakan(row) {
      // 是否隐藏按钮
      this.opens = false;
      const id = row.id || this.ids;
      getBdglweekwork(id).then((response) => {
        this.wordInfo = response.data;
        this.title = "查看一周工作安排";
        this.menuRoleVisible = true;
      });
    },
    // 让select失去焦点
    blurSelect() {
      this.$refs.aa.blur();
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["refform"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            if (
              new Date(this.gongZuoTiem) >= new Date(this.form.startTime) &&
              new Date(this.gongZuoTiem) <= new Date(this.form.endTime)
            ) {
              this.form.bdglWeekworkRegisterList = this.tomList;
              var num = 0;
              var num1 = this.form.bdglWeekworkRegisterList.length;
              for (
                let index = 0;
                index < this.form.bdglWeekworkRegisterList.length;
                index++
              ) {
                if (
                  this.form.bdglWeekworkRegisterList[index].peopleIds == "" ||
                  this.form.bdglWeekworkRegisterList[index].peopleIds == null ||
                  this.form.bdglWeekworkRegisterList[index].address == "" ||
                  this.form.bdglWeekworkRegisterList[index].address == null ||
                  this.form.bdglWeekworkRegisterList[index].contents == "" ||
                  this.form.bdglWeekworkRegisterList[index].contents == null ||
                  this.form.bdglWeekworkRegisterList[index].organizer == "" ||
                  this.form.bdglWeekworkRegisterList[index].organizer == null
                ) {
                  return this.$modal.msgWarning(
                    "请填写工作登记内容，组织人，参加人，地点"
                  );
                } else {
                  num += 1;
                }
              }
              if (num1 === num) {
                updateBdglweekwork(this.form).then((response) => {
                  this.$modal.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                });
              }
            } else {
              this.$modal.msgWarning("请选择开始时间与结束时间区级内的日期");
            }
          } else {
            if (
              new Date(this.gongZuoTiem) >= new Date(this.form.startTime) &&
              new Date(this.gongZuoTiem) <= new Date(this.form.endTime)
            ) {
              this.form.bdglWeekworkRegisterList = this.tomList;
              if (this.code != 200) {
                var num2 = 0;
                var num3 = this.form.bdglWeekworkRegisterList.length;
                for (
                  let index = 0;
                  index < this.form.bdglWeekworkRegisterList.length;
                  index++
                ) {
                  if (
                    this.form.bdglWeekworkRegisterList[index].peopleIds == "" ||
                    this.form.bdglWeekworkRegisterList[index].peopleIds ==
                      null ||
                    this.form.bdglWeekworkRegisterList[index].address == "" ||
                    this.form.bdglWeekworkRegisterList[index].address == null ||
                    this.form.bdglWeekworkRegisterList[index].contents == "" ||
                    this.form.bdglWeekworkRegisterList[index].contents ==
                      null ||
                    this.form.bdglWeekworkRegisterList[index].organizer == "" ||
                    this.form.bdglWeekworkRegisterList[index].organizer == null
                  ) {
                    return this.$modal.msgWarning(
                      "请填写工作登记内容，组织人，参加人，地点"
                    );
                  } else {
                    num2 += 1;
                  }
                }
                if (num2 === num3) {
                  addBdglweekwork(this.form).then((response) => {
                    this.$modal.msgSuccess("新增成功");
                    this.open = false;
                    this.getList();
                  });
                }
                // if (this.form.unitId) {
                // }
              }
            } else {
              this.$modal.msgWarning("请选择开始时间与结束时间区级内的日期");
            }
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        // .confirm('是否确认删除一周工作安排编号为"' + ids + '"的数据项？')
        .confirm("是否确认删除该数据？")
        .then(function () {
          return delBdglweekwork(ids);
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
        .confirm("是否确认导出所有一周工作安排数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBdglweekwork(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    datelinefun(val) {
      if (val == "早晨") {
        this.option.start = "06:00";
        this.option.end = "08:00";
      } else if (val == "上午") {
        this.option.start = "08:00";
        this.option.end = "012:00";
      } else if (val == "下午") {
        this.option.start = "12:00";
        this.option.end = "18:00";
      } else if (val == "晚上") {
        this.option.start = "18:00";
        this.option.end = "23:00";
      }
    },
  },
};
</script>
<style scoped>
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
}
.el-textarea__inner {
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
  border-color: #fff !important;
}
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
/* 基本信息背景 */
.jiben {
  width: 887px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  margin-bottom: 25px;
  margin-left: 30px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
::v-deep .el-dialog__body {
  /* margin: 10px 30px 20px 30px; */
  margin: 10px 0px 20px 52px;
  padding-top: 20px !important;
  box-sizing: border-box;
  padding-bottom: 0px !important;
  padding-left: 0px !important;
  padding-right: 0px !important;
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
/* 新增工作登记 */
.box1 {
  width: 887px;
  min-height: 100px;
  /* margin: auto; */
  margin-left: 32px;
  border: 1px solid white;
  color: #fff;
  border-radius: 5px;
  margin-bottom: 30px;
  display: flex;
  padding: 10px 10px;
  position: relative;
}
.btn {
  position: absolute;
  bottom: 31px;
  right: 200px;
}
/* 新增工作登记开始结束时间 */
.box1 .el-date-editor.el-input {
  margin-right: 10px;
  width: 134px;
}
/* 新增-工作登记-表单长度 */
::v-deep .box1 .el-input--small .el-input__inner {
  width: 250px;
}
::v-deep .box1 .el-form-item__content {
  width: 130px;
}
/* 新增-工作登记-人员地点 */
::v-deep .box1 .box2 .el-form-item__content {
  width: 250px;
}
/* 新增-工作登记-内容 */
::v-deep .box3 .el-form-item el-form-item--medium {
  width: 510px !important;
  height: 0px;
}
::v-deep .box3 .el-form-item__content {
  width: 510px !important;
}
::v-deep .box3 .el-input el-input--small el-input--suffix {
  width: 510px !important;
}
::v-deep .box3 .el-form-item__content .el-input__inner {
  width: 510px !important;
}
.box4 {
  width: 890px;
  min-height: 100px;
  /* border: 1px solid white; */
  margin-left: 31px;
}
::v-deep .box4 .el-textarea__inner {
  min-height: 120px !important;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 18px 50px;
  /* margin-right: 42px; */
  margin-right: 36px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px !important;
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
/* 文本域提示文字颜色 */
::v-deep ::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}
::v-deep .el-picker-panel .el-scrollbar .time-select-item {
  color: #fff !important;
}
::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent !important;
  color: #fff;
}
#chakan /deep/ .el-dialog__body {
  margin: 10px 0px 20px 0px;
}
</style>
