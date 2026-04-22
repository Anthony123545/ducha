<template>
  <div class="app-container">
    <!-- <div class="box-lfet"> -->
    <!-- <div class="data">
        <p>单位</p>
        <div class="a1"></div>
        <el-date-picker
          v-model="time"
          type="date"
          :editable="false"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
          @change="shiJian"
        >
        </el-date-picker>
      </div> -->
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="dadui">
          <img src="../../../images/星星.png" alt />
          大队
        </div>
        <div class="tree">
          <el-tree
            class="userAgrees"
            style="height: calc(100vh - 170px); overflow: auto"
            :data="deptOptions"
            :props="defaultProps"
            @node-click="handleNodeClick"
            default-expand-all
            highlight-current
            :expand-on-click-node="false"
          ></el-tree>
        </div>
      </el-col>
      <el-col :span="1.5" class="mb8">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['thought:weekjob:add']"
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
          v-hasPermi="['thought:weekjob:edit']"
          >修改</el-button
        >
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['thought:weekjob:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <!-- <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            :loading="exportLoading"
            @click="handleExport"
            v-hasPermi="['thought:weekjob:export']"
            >导出</el-button
          > -->
      </el-col>
      <!-- </el-row> -->
      <el-col :span="20" :xs="24">
        <el-table
          v-loading="loading"
          :data="weekjobList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <!-- <el-table-column label="主键id" align="center" prop="id" /> -->
          <el-table-column
            label="序号"
            type="index"
            width="50"
            align="center"
          />
          <el-table-column
            label="单位名称"
            align="center"
            prop="unitName"
            show-overflow-tooltip
            :formatter="unit2Format"
          />
          <el-table-column
            label="工作标题"
            align="center"
            prop="jobTitle"
            show-overflow-tooltip
          />
          <el-table-column
            label="开始时间"
            align="center"
            prop="startTime"
            width="100"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="结束时间"
            align="center"
            prop="endTime"
            width="100"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.endTime, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="连值班领导"
            align="center"
            prop="dutyLeader"
            show-overflow-tooltip
          />
          <el-table-column
            label="电话"
            align="center"
            prop="phone"
            show-overflow-tooltip
          />
          <el-table-column
            label="军事长官 "
            align="center"
            prop="militaryOfficer"
            show-overflow-tooltip
          />
          <!-- <el-table-column label="军事长官id" align="center" prop="militaryOfficerId" /> -->
          <el-table-column
            label="政治长官"
            align="center"
            prop="politicalOfficer"
            show-overflow-tooltip
          />

          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
            width="200px"
          >
            <template slot-scope="scope">
              <el-button
                size="btk"
                type="text"
                @click="handlelook(scope.row)"
                v-hasPermi="['thought:weekjob:query']"
                >查看</el-button
              >
              <el-button
                size="btu"
                type="text"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['thought:weekjob:edit']"
                >修改</el-button
              >

              <el-button
                size="btd"
                type="text"
                @click="handleDelete(scope.row)"
                v-hasPermi="['thought:weekjob:remove']"
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
      </el-col>
    </el-row>

    <!-- 添加或修改周工作对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1100px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree">
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="100px"
          :inline="true"
        >
          <div class="jiben">基本信息</div>
          <el-form-item label="单位" prop="unitId">
            <!-- <el-input v-model="form.unitName" placeholder="请输入单位名称" /> -->
            <treeselect
              v-model="form.unitId"
              :options="users"
              placeholder="请选择单位"
              class="threeselects"
              @select="selectPeo"
            >
            </treeselect>
            <!-- </el-form-item> -->
          </el-form-item>
          <el-form-item label="连值班领导" prop="dutyLeader">
            <!-- <el-input v-model="form.dutyLeader" placeholder="请输入值班领导" /> -->
            <el-select
              filterable
              v-model="form.dutyLeader"
              placeholder="请选择连值班领导"
              ref="peoplenames"
            >
              <el-option
                v-for="item in executor"
                :key="item.id"
                :label="item.name"
                :value="item.name"
                @click.native="peopleIds(item)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入电话" />
          </el-form-item>
          <el-form-item label="军事长官 " prop="militaryOfficer">
            <!-- <el-input
            v-model="form.militaryOfficer"
            placeholder="请输入军事长官 "
          /> -->
            <el-select
              filterable
              v-model="form.militaryOfficer"
              ref="militaryOfficer"
              placeholder="请选择军事长官"
            >
              <el-option
                v-for="item in junshiexecutor"
                :key="item.id"
                :label="item.name"
                :value="item.name"
                @click.native="zuzhiPeople(item.id)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="政治长官" prop="politicalOfficer">
            <!-- <el-input
            v-model="form.politicalOfficer"
            placeholder="请输入政治长官"
          /> -->
            <el-select
              filterable
              v-model="form.politicalOfficer"
              ref="militaryOfficer"
              placeholder="请选择政治长官"
            >
              <el-option
                v-for="item in zhengzhiexecutor"
                :key="item.id"
                :label="item.name"
                :value="item.name"
                @click.native="jiluPeople(item.id)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="工作标题" prop="jobTitle">
            <el-input v-model="form.jobTitle" placeholder="请输入工作标题" />
          </el-form-item>
          <!-- <el-form-item label="创建人姓名" prop="creatName">
          <el-input v-model="form.creatName" placeholder="请输入创建人姓名" />
        </el-form-item>
        <el-form-item label="创建时间" prop="creatTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.creatTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择创建时间"
          >
          </el-date-picker>
        </el-form-item> -->

          <!-- <el-form-item label="创建人id" prop="creatId">
          <el-input v-model="form.creatId" placeholder="请输入创建人id" />
        </el-form-item> -->

          <!-- <el-form-item label="单位id" prop="unitId">
          <el-input v-model="form.unitId" placeholder="请输入单位id" />
        </el-form-item> -->

          <!-- <el-form-item label="值班领导id" prop="dutyLeaderId">
          <el-input v-model="form.dutyLeaderId" placeholder="请输入值班领导id" />
        </el-form-item> -->

          <!-- <el-form-item label="军事长官id" prop="militaryOfficerId">
          <el-input v-model="form.militaryOfficerId" placeholder="请输入军事长官id" />
        </el-form-item> -->

          <!-- <el-form-item label="政治长官id" prop="politicalOfficerId">
          <el-input v-model="form.politicalOfficerId" placeholder="请输入政治长官id" />
        </el-form-item> -->

          <el-form-item label="开始时间" prop="startTime">
            <el-date-picker
              editable
              clearable
              size="small"
              v-model="form.startTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择开始时间"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间" prop="endTime" label-width="115px">
            <el-date-picker
              editable
              clearable
              size="small"
              v-model="form.endTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择结束时间"
            >
            </el-date-picker>
          </el-form-item>
          <div class="jiben">工作登记</div>
          <div class="box3" v-for="(item, i) in tomList" :key="i">
            <el-form
              ref="refform"
              :model="form"
              :rules="rules"
              label-width="80px"
              :inline="true"
            >
              <el-form-item label="" prop="daytime" style="margin-left:22px">
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
                <el-input placeholder="星期" v-model="item.week" disabled />
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  size="mini"
                  style="padding: 11px 10px 10px 10px"
                  @click="addForms"
                  v-if="i == tomList.length - 1"
                ></el-button>
                <el-button
                  type="danger"
                  icon="el-icon-close"
                  size="mini"
                  style="padding: 11px 10px 10px 10px"
                  v-if="i > 0"
                  @click="delForms(i)"
                ></el-button>
              </el-form-item>
              <el-form-item label="" prop="" style="margin-left:22px">
                <quill-editor
                  class="quill-editor"
                  ref="myTextEditor"
                  v-model="item.contents"
                  style="
                    height: 150px;
                    width: 990px;
                    margin-left: 0px;
                    color: white;
                    margin-bottom: 60px;
                  "
                ></quill-editor>
              </el-form-item>
            </el-form>
          </div>
          <!-- <div class="zhankai" @click="zhankai">展开更多工作登记</div> -->
        </el-form>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看按钮操作 -->
    <!-- 查看周工作对话框 -->
    <el-dialog
      :title="title1"
      :visible.sync="open1"
      width="1100px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree">
        <el-form
          ref="form"
          :model="form1"
          :rules="rules"
          label-width="100px"
          :inline="true"
        >
          <div class="jiben">基本信息</div>
          <el-form-item label="单位" prop="unitId">
            <treeselect
              disabled
              v-model="form1.unitId"
              :options="users"
              placeholder="请选择到访单位"
              class="threeselects"
              @select="selectPeo"
            >
            </treeselect>
            <!-- </el-form-item> -->
          </el-form-item>
          <el-form-item label="连值班领导" prop="dutyLeader">
            <el-select
              disabled
              filterable
              v-model="form1.dutyLeader"
              placeholder="请选择人员"
              ref="peoplenames"
            >
              <el-option
                v-for="item in executor"
                :key="item.id"
                :label="item.name"
                :value="item.name"
                @click.native="peopleIds(item)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="form1.phone" placeholder="请输入电话" disabled />
          </el-form-item>
          <el-form-item label="军事长官 " prop="militaryOfficer">
            <el-select
              disabled
              filterable
              v-model="form1.militaryOfficer"
              ref="militaryOfficer"
              placeholder="请选择军事长官"
            >
              <el-option
                v-for="item in junshiexecutor"
                :key="item.id"
                :label="item.name"
                :value="item.name"
                @click.native="zuzhiPeople(item.id)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="政治长官" prop="politicalOfficer">
            <el-select
              disabled
              filterable
              v-model="form1.politicalOfficer"
              ref="militaryOfficer"
              placeholder="请选择军事长官"
            >
              <el-option
                v-for="item in zhengzhiexecutor"
                :key="item.id"
                :label="item.name"
                :value="item.name"
                @click.native="jiluPeople(item.id)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="工作标题" prop="jobTitle">
            <el-input
              v-model="form1.jobTitle"
              placeholder="请输入工作标题"
              disabled
            />
          </el-form-item>
          <el-form-item label="开始时间" prop="startTime">
            <el-date-picker
              disabled
              editable
              clearable
              size="small"
              v-model="form1.startTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择开始时间"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间" prop="endTime" label-width="115px">
            <el-date-picker
              disabled
              editable
              clearable
              size="small"
              v-model="form1.endTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择结束时间"
            >
            </el-date-picker>
          </el-form-item>
          <div class="jiben">工作登记</div>
          <div class="box3" v-for="(item, i) in tomList" :key="i">
            <el-form
              ref="refform"
              :model="form1"
              :rules="rules"
              label-width="80px"
              :inline="true"
            >
              <el-form-item label="" prop="daytime" style="margin-left:22px">
                <el-date-picker
                  disabled
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
                <el-input placeholder="星期" v-model="item.week" disabled />
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  size="mini"
                  style="padding: 11px 10px 10px 10px"
                  @click="addForms"
                  v-if="i == tomList.length - 1"
                ></el-button>
                <el-button
                  type="danger"
                  icon="el-icon-close"
                  size="mini"
                  style="padding: 11px 10px 10px 10px"
                  v-if="i > 0"
                  @click="delForms(i)"
                ></el-button>
              </el-form-item>
              <el-form-item label="" prop="" style="margin-left:22px" >
                <quill-editor
                  class="quill-editor"
                  ref="myTextEditor"
                  v-model="item.contents"
                  style="
                    height: 150px;
                   width: 990px;
                    margin-left: 0px;
                    color: white;
                    margin-bottom: 60px;
                  "
                ></quill-editor>
              </el-form-item>
            </el-form>
          </div>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="open1 = false">关 闭</el-button>
        <!-- <el-button @click="open1=false" size="btn">取 消</el-button> -->
      </div>
    </el-dialog>
    <!-- </div> -->
  </div>
</template>

<script>
import {
  listWeekjob,
  getWeekjob,
  delWeekjob,
  addWeekjob,
  updateWeekjob,
  exportWeekjob,
} from "@/api/thought/weekjob";
import {
  getDept2,
  getAll,
  getWeiShu,
} from "@/api/grassrootsregistration/bdglmeeting";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { deptUser } from "@/api/doormanManage/bdglVisit";
import { getDeptList } from "@/api/workingArrangements/workTask";
export default {
  name: "Weekjob",
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
      // 周工作表格数据
      weekjobList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        creatTime: null,
        creatName: null,
        creatId: null,
        unitName: null,
        unitId: null,
        dutyLeader: null,
        dutyLeaderId: null,
        phone: null,
        militaryOfficer: null,
        militaryOfficerId: null,
        politicalOfficer: null,
        politicalOfficerId: null,
        jobTitle: null,
        startTime: null,
        endTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitName: [
          { required: true, message: "单位不能为空", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" },
        ],
        dutyLeader: [
          { required: true, message: "连值班领导不能为空", trigger: "blur" },
        ],
        phone: [{ required: true, message: "电话不能为空", trigger: "blur" }],
        militaryOfficer: [
          { required: true, message: "军事长官不能为空", trigger: "blur" },
        ],
        politicalOfficer: [
          { required: true, message: "政治长官不能为空", trigger: "blur" },
        ],
        jobTitle: [
          { required: true, message: "工作标题不能为空", trigger: "blur" },
        ],
      },
      // 左侧树形
      deptOptions: [],
      //左侧时间选择
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      // 树形配置
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 搜索时间的保存
      timeDate: "",
      users: [],
      // 连值班领导
      executor: [],
      //  军事长官列表
      junshiexecutor: [],
      // 政治长官列表
      zhengzhiexecutor: [],
      // 单位名称
      unitName: "",
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
      time: "",
      //添加对话框查铺表格为空
      tomList: [
        {
          // 开始时间
          time: null,
          // 星期
          week: null,
          content: null,
        },
      ],
      getdeptList: [],
      form1: {},
      open1: false,
      title1: "",
    };
  },
  created() {
    this.getList();
    this.treeselect();
    this.getSuoYou();
    // 获取执行单位列表
    getDept2().then((res) => {
      this.users = res.data;
    });
    // 获取部门列表
    getDeptList().then((res) => {
      this.getdeptList = res.data;
    });
  },
  methods: {
    unit2Format(row) {
      var deptName = "";
      this.getdeptList.map((item) => {
        // if(ite)
        if (item.deptId == row.unitId) {
          deptName = item.deptName;
        }
      });
      return deptName;
    },
    // 搜索时间触发
    shiJian(data) {
      this.queryParams.diaryTime = data;
      this.timeDate = data;
      this.getList();
      this.getShuJu(data);
    },
    // 获取部门数据
    getShuJu(data) {
      exportBdgShuju(data).then((res) => {
        this.DataTime = data;
        this.registrationData = res;
        this.cg = res.cg;
        this.sb = res.sb;
        // this.yishenpi = res.yiShenPi;
        // this.weishenpi = res.weiShenPi;
        this.tiems = res.time;
      });
    },
    // 增加工作登记
    addForms() {
      this.tomList.push({
        time: null,
        week: null,
        content: null,
      });
    },
    // 删除对话框工作登记
    delForms(i) {
      this.tomList.splice(i, 1);
    },
    // 选中时间获取星期
    change_time(date, i) {
      if (date) {
        // 通过全局的getNowWeek方法获取当前时间是星期几
        this.tomList[i].week = this.getNowWeek(date);
      } else {
        this.tomList[i].week = "";
      }
    },

    // 军事长官id
    zuzhiPeople(id) {
      this.form.militaryOfficerId = id;
    },
    // 政治长官id
    jiluPeople(id) {
      this.form.politicalOfficerId = id;
    },
    // 获取所有人员
    getSuoYou() {
      getAll().then((response) => {
        // 军事长官列表
        this.junshiexecutor = response.rows;
        // 政治长官列表
        this.zhengzhiexecutor = response.rows;
        // // 记录人
        // this.recodeexecutor=response.rows
      });
    },
    // 选中单位
    selectPeo(val) {
      this.form.dutyLeader = "";
      this.form.unitId = val.id;
      // this.unitName = val.label;
      this.form.phone = "";
      this.getZhuChi(this.form.unitId);
    },
    // 获取电话
    peopleIds(item) {
      this.form.dutyLeaderId = item.id;

      this.form.phone = item.phone;
    },
    // 获取执行人
    getZhuChi(id) {
      deptUser(id).then((res) => {
        // 连值班领导
        this.executor = res.data;
        this.executors = res.data;

        // this.form.unitName=this.executor
      });
    },
    // 获取外面部门树形
    treeselect() {
      getWeiShu().then((res) => {
        this.deptOptions = res.data;
      });
    },
    //外面树形选择搜索
    handleNodeClick(data) {
      this.queryParams.unitId = data.id;
      this.getList();
    },
    /** 查询周工作列表 */
    getList() {
      this.loading = true;
      listWeekjob(this.queryParams).then((response) => {
        this.weekjobList = response.rows;

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
        creatTime: null,
        updateTime: null,
        creatName: null,
        creatId: null,
        remark: null,
        unitName: null,
        unitId: null,
        dutyLeader: null,
        dutyLeaderId: null,
        phone: null,
        militaryOfficer: null,
        militaryOfficerId: null,
        politicalOfficer: null,
        politicalOfficerId: null,
        jobTitle: null,
        startTime: null,
        endTime: null,
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
      (this.tomList = [
        {
          // 开始时间
          time: null,
          // 星期
          week: null,
          content: null,
        },
      ]),
        this.reset();

      this.open = true;
      this.title = "添加周工作";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWeekjob(id).then((response) => {
        this.form = response.data;
        this.form.unitName = this.form.unitName;
        this.tomList = this.form.thoughtWeekjobFuList;

        this.open = true;
        this.title = "修改周工作";
      });
    },
    handlelook(row) {
      this.reset();
      const id = row.id || this.ids;
      getWeekjob(id).then((response) => {
        this.form1 = response.data;
        this.tomList = this.form1.thoughtWeekjobFuList;
        this.open1 = true;
        this.title1 = "查看周工作";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.form.unitName = this.form.unitId;

      if (this.tomList) {
        this.form.thoughtWeekjobFuList = this.tomList;
      }

      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateWeekjob(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWeekjob(this.form).then((response) => {
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
        .confirm("是否确认删除？")
        .then(function () {
          return delWeekjob(ids);
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
        .confirm("是否确认导出所有周工作数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportWeekjob(queryParams);
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
/*定义滚动条宽高及背景，宽高分别对应横竖滚动条的尺寸*/
.userAgree::-webkit-scrollbar {
  width: 14px;
  /* height: 2px !important; */
  background: #3d83b8;
}

/* 备注 */
.thistext {
  color: white;
  width: 1010px;
  border: 1px solid #fff;
  border-radius: 4px;
  height: auto;
  min-height: 35px;
  text-indent: 1em;
  padding: 2px;
  line-height: 35px;
  margin-bottom: 20px;
  /* margin: auto; */
}
/* 附件名称样式 */
.filename {
  display: inline-block;
  color: white;
  width: 900px;
  height: 30px;
  border: 1px solid white;
  line-height: 30px;
  text-indent: 1em;
}
/* 下载按钮 */
.downloadfile {
  border: 1px solid white;
  display: inline-block;
  width: 100px;
  height: 33px;
  border-radius: 5px;
  line-height: 33px;
  text-align: center;
}

.zhankai {
  margin-top: -30px;
  width: 1015px;
  height: 36px;
  background: #196299;
  border-radius: 4px;
  font-size: 14px;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #fefefe;
  line-height: 36px;
  padding-left: 10px;
  background-image: url("../../../images/renoften/展开.png");
  background-repeat: no-repeat;
  background-position: 980px 12px;
}
/* ::v-deep .box3{
  border: 1px solid white;
  width: 98%;
  height: 270px;
  padding-left: 10px;
  padding-top: 10px;
  margin-top: 10px;
} */
::v-deep .el-pagination__sizes .el-input__suffix {
  right: 6px;
}
/* 树形样式 */
::v-deep .vue-treeselect__control {
  background-color: #004d86;
  width: 216px;
  margin-right: 25px;
  color: white;
}
.threeselects {
  width: 216px;
  margin-right: 22px;
}
::v-deep .vue-treeselect__single-value {
  color: white;
}
::v-deep .el-dialog {
  background-color: #004d86 !important;
}
/* 输入框 */
::v-deep .el-dialog .el-input__inner {
  background-color: #004d86 !important;
  width: 216px;
  color: white;
  margin-right: 20px;
  border: 1px solid white !important;
}
::v-deep .el-input__inner {
  border: 1px solid white !important;
}
/* 表格样式 */
::v-deep .el-table .el-table__header-wrapper th {
  background-color: #004d86 !important;
  color: white;
}
/* 标题弹框 */
::v-deep .el-dialog__title {
  color: white;
  /* border-bottom: 1px solid white; */
}
/* 标题下划线 */
::v-deep .el-dialog__header {
  border-bottom: 1px solid white;
}
/* 日历样式 */
::v-deep .el-date-editor .el-input__inner {
  height: 36px !important;
  line-height: 36px;
}
::v-deep .datesend .el-form-item__label {
  width: 115px !important;
}
/* 会议文本域样式 */
::v-deep .el-form-item__content .el-textarea {
  width: 660px;
  /* height: 80px !important; */
}
::v-deep .el-form-item__content .el-textarea .el-textarea__inner {
  /* width: 1000px; */
  height: 80px !important;
}
::v-deep .el-dialog__body {
  padding-left: 45px !important;
  padding-right: 0px !important;
  padding-bottom: 0px !important;
}
/* 富文本内容 */
/* ::v-deep .editor{
  width: 1010px;
  color: white;
} */
/* 富文本 */
::v-deep .ql-snow .ql-picker:focus {
  outline: none !important;
}
::v-deep .ql-toolbar.ql-snow .ql-picker.ql-expanded .ql-picker-label:focus {
  outline: none !important;
}
::v-deep .ql-toolbar.ql-snow .ql-picker-label {
  border: none !important;
}
::v-deep .ql-formats:focus {
  outline: none !important;
}
::v-deep .ql-toolbar.ql-snow .ql-picker.ql-expanded .ql-picker-options {
  border-color: none !important;
  color: black !important;
}
::v-deep .ql-snow .ql-picker-options .ql-picker-item:focus {
  outline: none !important;
}
/* normal */
::v-deep .ql-active {
  outline: none !important;
}
::v-deep .ql-snow .ql-fill,
.ql-snow .ql-stroke.ql-fill {
  fill: #fff !important;
}
::v-deep .ql-snow .ql-stroke {
  stroke: #fff !important;
}
::v-deep .ql-snow .ql-picker {
  color: #fff !important;
}
::v-deep .ql-editor ql-blank {
  color: #fff !important;
}
::v-deep .ql-snow .ql-picker-label {
  position: absolute;
  bottom: 5px;
}
::v-deep .ql-snow .ql-picker:not(.ql-color-picker):not(.ql-icon-picker) svg {
  margin-top: -2px;
}
::v-deep .ql-toolbar.ql-snow {
  display: flex;
  justify-content: space-around;
}
/* 富文本删除功能 */

/* 富文本删除功能 */
::v-deep .ql-blockquote {
  display: none !important;
}
::v-deep .ql-strike {
  display: none !important;
}
::v-deep .ql-script {
  display: none !important;
}
::v-deep .ql-code-block {
  display: none !important;
}
::v-deep .ql-direction {
  display: none !important;
}
.ql-toolbar.ql-snow .ql-formats {
  margin-right: 7px !important;
}
::v-deep .el-input__inner {
  /* height: 36px !important; */
  color: white;
  background-color: #00365f !important;
}
/* 文字多余部分省略 */
::v-deep .el-table__cell .cell {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
::v-deep .el-input__suffix {
  right: 22px;
}
/* 基本样式 */
/* .jiben {
  width: 660px;
} */
::v-deep .data .el-date-editor.el-input {
  width: 100%;
  border: none;
}
::v-deep .data .el-input__inner {
  width: 100% !important;
  border: none !important;
}
::v-deep .data .el-input--medium .el-input__icon {
  line-height: 36px;
  position: absolute;
  right: -223px;
  top: 2px;
}
::v-deep .data .el-input__inner {
  background-color: transparent !important;
  color: #fff;
  text-align: center;
  margin-top: 2px;
}
::v-deep .data .el-input__suffix {
  position: absolute;
  right: 281px;
}
.app-container {
  display: flex;
}
.app-container .box-lfet .data {
  height: 40px;
  background: #003156;
  margin-bottom: 12px;
  color: #fff;
  display: flex;
  font-size: 13px;
  line-height: 25px;
  position: relative;
}

.box-lfet .data p {
  position: absolute;
  top: -5px;
  left: 15px;
}
.box-lfet .data .a1 {
  width: 128px;
  height: 26px;
  background: rgba(23, 74, 112, 0.4);
  border-radius: 13px;
  position: absolute;
  top: 7px;
  left: 60px;
}
::v-deep .data .el-date-editor.el-input {
  width: 100%;
  border: none;
}
::v-deep .data .el-input__inner {
  width: 100% !important;
  border: none;
}
::v-deep .data .el-input--medium .el-input__icon {
  line-height: 36px;
  position: absolute;
  right: -223px;
  top: 2px;
}
::v-deep .data .el-input__inner {
  background-color: transparent;
  color: #fff;
  text-align: center;
  margin-top: 2px;
}
::v-deep .data .el-input__suffix {
  position: absolute;
  right: 281px;
}
/* 头部样式 */
.dadui {
  height: 40px;
  background: #003156;
  /* border-bottom: 1px solid #718A9D ; */
  background-image: url(../../../images/矩形底部边框.png);
  background-repeat: no-repeat;
  background-position: 0px 39px;
  font-size: 16px;
  color: #1d96ff;
}
.dadui img {
  margin: 10px 10px 0px 10px;
}

.box-lfet .data p {
  position: absolute;
  top: -5px;
  left: 15px;
}
.box-lfet .data .a1 {
  width: 128px;
  height: 26px;
  background: rgba(23, 74, 112, 0.4);
  border-radius: 13px;
  position: absolute;
  top: 7px;
  left: 60px;
}
/* 划过效果 */
::v-deep .el-tree-node__content:hover {
  /* color: #fff; */
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
.app-container {
  display: flex;
}
/* 左侧树状盒子 */
.app-container .box-lfet {
  width: 15%;
  min-height: 740px;
  /* background: #003156; */
  /* padding: 10px; */
  margin-right: 10px;
  /* border: 1px solid white; */
}
/* 右侧内容盒子 */
.app-container .box-right {
  flex: 1;
}
.app-container .box-lfet .data {
  height: 40px;
  background: #003156;
  margin-bottom: 12px;
  color: #fff;
  display: flex;
  font-size: 13px;
  line-height: 25px;
  position: relative;
}
.el-tree {
  background: #003156;
  color: #fff;
  margin-right: 2px;
  padding-top: 7px;
}
::v-deep .el-dialog {
  background-color: #004d86 !important;
}
::v-deep .el-input__suffix {
  right: 22px;
}
::v-deep .el-tree-node__content {
  height: 32px;
}
::v-deep
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  height: 32px;
  color: #fff !important;
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
/* 卡片样式 */
.app-container {
  height: calc(100vh - 108px) !important;
}
</style>
