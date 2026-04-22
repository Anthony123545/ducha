<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="100px"
    >
      <el-form-item prop="unitId" style="margin-left: -40px" label-width="50px">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请选择单位"
          @input="handleQuery"
        />
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
          v-hasPermi="['barracksManagement:flats:add']"
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
          v-hasPermi="['barracksManagement:flats:edit']"
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
          v-hasPermi="['barracksManagement:flats:remove']"
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
          v-hasPermi="['barracksManagement:flats:export']"
          >导出</el-button
        >
      </el-col>
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="flatsList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50"> </el-table-column>
      <el-table-column
        label="单位"
        align="center"
        prop="unitName"
        width="130"
      />
      <el-table-column
        label="人员"
        align="center"
        prop="peopleName"
        width="130"
      />
      <el-table-column label="部职别" align="center" prop="duty" width="130" />
      <!-- <el-table-column
        label="是否符合申请条件"
        align="center"
        prop="whether"
        width="150"
      /> -->
      <el-table-column
        label="住房待遇等级"
        align="center"
        prop="grade"
        width="150"
      />
      <el-table-column
        label="申请住房户型"
        align="center"
        prop="type"
        width="150"
      />
      <el-table-column
        label="出生时间"
        align="center"
        prop="birthDate"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="入伍年月"
        align="center"
        prop="enlistDate"
        width="160"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.enlistDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="参加工作时间"
        align="center"
        prop="jobTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.jobTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="地方普通高等学校入学时间"
        align="center"
        prop="entranceDate"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entranceDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="享受相应住房待遇职级时间"
        align="center"
        prop="jobDate"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.jobDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="任现职级时间"
        align="center"
        prop="jobsDate"
        width="150"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.jobsDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="配偶是否军队人员"
        align="center"
        prop="whethers"
        width="150"
      />
      <el-table-column
        label="军衔"
        align="center"
        prop="rankId"
        :formatter="unit4Format"
        width="150"
      />
      <el-table-column
        label="军衔时间"
        align="center"
        prop="rankDate"
        width="150"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.rankDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="备注"
        align="center"
        prop="remark"
        show-overflow-tooltip
        width="150"
      />
      <!-- <el-table-column label="登记时间" align="center" prop="createtime" />
      <el-table-column label="登记人" align="center" prop="adminId" /> -->
      <el-table-column
        label="配偶姓名"
        align="center"
        prop="pName"
        width="150"
      />
      <el-table-column
        label="配偶单位"
        align="center"
        prop="pUnit"
        width="150"
      />
      <el-table-column
        label="配偶户口所在地"
        align="center"
        prop="pAccount"
        width="150"
      />
      <el-table-column label="结婚时间" align="center" prop="pTime" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.pTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="配偶入伍或随军时间"
        align="center"
        prop="pTimeOne"
        width="150"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.pTimeOne, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="配偶现住房"
        align="center"
        prop="pCeace"
        width="150"
      />
      <el-table-column
        label="申请住房类别"
        align="center"
        prop="type"
        width="150"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="150px"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['barracksManagement:flats:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['barracksManagement:flats:remove']"
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

    <!-- 添加或修改公寓住房申请人员资格审查登记对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1250px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="110px"
        :inline="true"
      >
        <div class="jiben">个人信息</div>
        <el-form-item label="单位" prop="unitId">
          <!-- <el-input v-model="form.unitId" placeholder="请输入单位" /> -->
          <treeselect
            v-model="form.unitId"
            :options="users"
            placeholder=""
            class="threeselects"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="人员" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder=""
            ref="peoplenames"
            filterable
          >
            <el-option
              v-for="item in executor"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              @click.native="peopleIds(item.name, item)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="部职别" prop="duty" style="margin-left: -5px">
          <el-input v-model="form.duty" />
        </el-form-item>

        <el-form-item label="军衔" prop="rankId">
          <!-- <el-input  v-model="form.rankId" class="zhufnag" :disabled="true" /> -->
          <!-- <el-input v-model="form.rankName" placeholder="请输入军衔" /> -->
          <el-select v-model="form.rankId" :disabled="true" placeholder="">
            <!-- <el-option label="请选择字典生成" value="" /> -->
            <el-option
              v-for="(item, i) in ziDian1"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictSort"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="军衔时间" prop="rankDate">
          <el-date-picker
            clearable
            size="small"
            v-model="form.rankDate"
            type="date"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="出生时间"
          style="margin-left: 13px"
          prop="birthDate"
          placeholder=""
        >
          <el-date-picker
            clearable
            size="small"
            v-model="form.birthDate"
            type="date"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="住房待遇等级"
          prop="grade"
          style="margin-left: 0px"
        >
          <el-input v-model="form.grade" />
        </el-form-item>
        <!-- <el-form-item label="配偶现住房" prop="pCeace" style="margin-left:25px">
          <el-input v-model="form.pCeace" />
        </el-form-item> -->
        <el-form-item
          label="入伍年月"
          prop="enlistDate"
          style="margin-left: 0px"
        >
          <el-date-picker
            clearable
            size="small"
            v-model="form.enlistDate"
            type="date"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结婚时间" prop="pTime" style="margin-left: 13px">
          <el-date-picker
            clearable
            size="small"
            v-model="form.pTime"
            type="date"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="享受相应住房待遇职级时间"
          prop="jobDate"
          label-width="200px"
          style="margin-left: -90px"
        >
          <el-date-picker
            clearable
            size="small"
            v-model="form.jobDate"
            type="date"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item
          label="任现职级时间"
          style="margin-left: 15px"
          prop="jobsDate"
        >
          <el-date-picker
            clearable
            size="small"
            v-model="form.jobsDate"
            type="date"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="参加工作时间"
          prop="jobTime"
          style="margin-left: 15px"
        >
          <el-date-picker
            clearable
            size="small"
            v-model="form.jobTime"
            type="date"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="地方普通高等学校入学时间"
          style="margin-left: -70px"
          prop="entranceDate"
          label-width="180px"
        >
          <el-date-picker
            clearable
            size="small"
            v-model="form.entranceDate"
            type="date"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>

        <!-- <el-form-item label="登记人" prop="adminId">
          <el-input v-model="form.adminId" placeholder="请输入登记人" />
        </el-form-item> -->
        <div class="jiben">配偶信息</div>

        <el-form-item label="配偶姓名" prop="pName">
          <el-input v-model="form.pName" />
        </el-form-item>
        <el-form-item label="配偶单位" prop="pUnit">
          <el-input v-model="form.pUnit" />
        </el-form-item>

        <el-form-item
          label="配偶现住房"
          prop="pCeace"
          style="margin-left: -10px"
        >
          <el-input v-model="form.pCeace" />
        </el-form-item>
        <el-form-item
          label="配偶是否军队人员(含职工)"
          style="margin-left: -70px"
          label-width="180px"
          prop="whethers"
        >
          <el-input v-model="form.whethers" />
        </el-form-item>

        <el-form-item
          label="配偶户口所在地"
          prop="pAccount"
          style="margin-left: -20px"
          label-width="130px"
        >
          <el-input v-model="form.pAccount" />
        </el-form-item>
        <el-form-item
          style="margin-left: -80px"
          label="配偶入伍或随军时间"
          prop="pTimeOne"
          label-width="180px"
        >
          <el-date-picker
            clearable
            size="small"
            v-model="form.pTimeOne"
            type="date"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <div class="jiben">信息确认</div>
        <!-- <el-form-item
          label="是否符合申请条件"
          prop="whether"
          style="margin-left: -20px"
          label-width="127px"
        >
          <el-input v-model="form.whether" />
        </el-form-item> -->
        <!-- <el-form-item
          label="申请住房户型"
          prop="houseType"
          style="margin-left: 5px"
        >
          <el-input v-model="form.houseType" class="zhufnag" />
        </el-form-item> -->
        <el-form-item
          label="申请住房类别"
          prop="type"
          style="margin-left: -13px"
        >
          <el-select v-model="form.type" placeholder="">
            <!-- <el-option label="请选择字典生成" value="" /> -->
            <el-option
              v-for="(item, i) in ziDian"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictLabel"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
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
  listFlats,
  getFlats,
  delFlats,
  addFlats,
  updateFlats,
  exportFlats,
} from "@/api/barracksManagement/flats";
import { getDicts } from "@/api/system/dict/data";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getDept2, getAll } from "@/api/grassrootsregistration/bdglmeeting";
import { deptUser } from "@/api/doormanManage/bdglVisit";
import { allUser, getDeptList } from "@/api/workingArrangements/workTask";
export default {
  name: "Flats",
  components: { Treeselect },
  data() {
    return {
      // 执行单位列表
      users: [],
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
      // 公寓住房申请人员资格审查登记表格数据
      flatsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitId: null,
        peopleId: null,
        duty: null,
        whether: null,
        grade: null,
        houseType: null,
        birthDate: null,
        enlistDate: null,
        jobTime: null,
        entranceDate: null,
        jobDate: null,
        jobsDate: null,
        whethers: null,
        rankId: null,
        rankDate: null,
        createtime: null,
        updatetime: null,
        adminId: null,
        pName: null,
        pUnit: null,
        pAccount: null,
        pTime: null,
        pTimeOne: null,
        pCeace: null,
        type: null,
      },
      // 查询字典
      dictType: {
        dictType: "accommodation_type",
        // 军衔
        dictType1: "sys_user_rank",
      },
      ziDian: [],
      ziDian1: [],
      // 单位
      getdeptList: [],
      // 表单参数
      form: {},
      rylist: [],
      executor: [],
      // 获取外面人员
      executors: [],
      // 表单校验
      rules: {
        enlistDate: [
          { required: true, message: "入伍年月不能为空", trigger: "blur" },
        ],
        // rankId: [{ required: true, message: "军衔不能为空", trigger: "blur" }],
        rankDate: [
          { required: true, message: "军衔时间不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getSuoYou();
    this.getList();
    this.getdict();
    this.getdict1();
    allUser().then((res) => {
      this.rylist = res.rows;
    });

    getDeptList().then((res) => {
      this.getdeptList = res.data;
    });
    // this.selectPeo1();
    // 获取执行单位列表
    getDept2().then((res) => {
      this.users = res.data;
    });
  },
  methods: {
    // 获取所有人员
    getSuoYou() {
      getAll().then((response) => {
        this.executors = response.rows;
      });
    },
    unit4Format(row, column) {
      var deptName = "";

      this.ziDian1.map((item) => {
        if (item.dictSort == row.rankId) {
          deptName = item.dictLabel;
        }
      });
      return deptName;
    },
    // 查询计划类型
    getdict1() {
      getDicts(this.dictType.dictType).then((res) => {
        this.ziDian = res.data;
      });
    },
    // 查询计划类型
    getdict() {
      getDicts(this.dictType.dictType1).then((res) => {
        this.ziDian1 = res.data;
      });
    },
    // 军衔
    junxian(id, name) {
      this.form.rankId = id;
      this.form.rankName = name;
    },
    //  peopleIds(val){
    //   this.form.peopleId=val.id;
    //   this.form.peopleName=val.name;

    //   this.form.rankId=val.rankId
    //   // getDicts(this.dictType.dictType1).then((res) => {
    //   //   this.ziDian1 = res.data;
    //   //   this.ziDian1.map((item) => {
    //   //   if (item.dictSort == row.type) {
    //   //     this.ziDian1 = item.dictLabel;
    //   //   }
    //   // });
    //   // });
    // },
    peopleIds(name, item) {
      this.form.rankName = "";
      this.form.peopleName = name;
      this.form.duty = item.duty;
      this.form.rankDate = item.rankDate;
      this.form.birthDate = item.birthDate;
      // this.form.grade=item.grade
      this.form.enlistDate = item.enlistDate;
      // this.form.pTime=item.pTime
      this.form.rankId = item.rankId;
      this.form.jobDate = item.jobDate;
      this.form.jobsDate = item.jobsDate;
      this.form.jobTime = item.jobTime;
      this.ziDian1.forEach((item) => {
        if (this.form.rankId == item.dictSort)
          this.form.rankName = item.dictLabel;
      });
    },
    peopleIds1(id, name) {
      this.queryParams.id = id;
      this.queryParams.peopleName = name;

      this.getList();
    },
    leibie() {
      this.getList();
    },
    // 到访单位选中触发
    selectPeo1(val) {
      this.queryParams.unitId = val.id;
      // this.queryParams.peopleName = "";
      // this.queryParams.unitName = val.label;

      this.getZhuChi(this.queryParams.unitId);
      this.getList();
    },
    // 获取执行人
    getZhuChi(id) {
      deptUser(id).then((res) => {
        this.executor = res.data;
        this.executors = res.data;

        // this.form.unitName=this.executor
      });
    },
    // 到访单位选中触发
    selectPeo(val) {
      this.form.unitId = val.id;

      this.form.peopleName = "";
      this.form.name = "";
      this.form.rankName = "";
      this.form.peopleId = "";
      // this.form.name=""
      this.form.unitName = val.label;

      this.getZhuChi(this.form.unitId);
    },
    //  // 查询计划类型
    // getdict() {
    //   getDicts(this.dictType.dictType).then((res) => {
    //     this.ziDian = res.data;
    //   });
    //   getDicts(this.dictType.dictType1).then((res) => {
    //     this.ziDian1 = res.data;
    //   });
    // },
    /** 查询公寓住房申请人员资格审查登记列表 */
    getList() {
      this.loading = true;
      listFlats(this.queryParams).then((response) => {
        this.flatsList = response.rows;

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
        unitId: null,
        peopleId: null,
        duty: null,
        whether: null,
        grade: null,
        houseType: null,
        birthDate: null,
        enlistDate: null,
        jobTime: null,
        entranceDate: null,
        jobDate: null,
        jobsDate: null,
        whethers: null,
        rankId: null,
        rankDate: null,
        remark: null,
        createtime: null,
        updatetime: null,
        adminId: null,
        pName: null,
        pUnit: null,
        pAccount: null,
        pTime: null,
        pTimeOne: null,
        pCeace: null,
        type: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      // this.queryParams.name=""
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {};
      this.resetForm("queryForm");
      this.handleQuery();
      this.getList();
      this.getSuoYou();
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
      this.title = "添加公寓住房申请人员资格审查登记";
      // 获取执行单位列表
      getDept2().then((res) => {
        this.users = res.data;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getFlats(id).then((response) => {
        this.form = response.data;
        this.getZhuChi(this.form.unitId);
        // this.form.name=this.form.peopleName
        // this.form.peopleId = this.form.peopleName;
        // this.executor.forEach
        this.ziDian1.forEach((item) => {
          if (item.dictSort == this.form.rankId) {
            this.form.rankName = item.dictLabel;
          }
        });
        this.open = true;
        this.title = "修改公寓住房申请人员资格审查登记";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateFlats(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFlats(this.form).then((response) => {
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
          return delFlats(ids);
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
        .confirm("是否确认导出所有公寓住房申请人员资格审查登记数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportFlats(queryParams);
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
/* 标题弹框 */
::v-deep .el-dialog__title {
  color: white;
  /* border-bottom: 1px solid white; */
}
/* 标题下划线 */
::v-deep .el-dialog__header {
  border-bottom: 1px solid white;
}
/* 文本域 */
::v-deep .el-textarea__inner {
  width: 1150px;
  height: 80px;
  margin-left: -47px;
}
::v-deep .el-form-item__label {
  text-align: end;
}
::v-deep .el-dialog__body {
  padding: 0px;
  margin-left: 58px;
  margin-top: 20px;
}
::v-deep .el-table .el-table__header-wrapper th,
.el-table .el-table__fixed-header-wrapper th {
  background-color: rgb(0, 60, 105);
  color: white;
}
::v-deep .el-input__suffix {
  right: 20px;
}
::v-deep .zhufnag .el-form-item__label {
  margin-left: 16px;
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
.peiou .el-input__inner {
  width: 300px;
}
::v-deep .vue-treeselect__single-value {
  color: white;
}
::v-deep .el-date-editor .el-input__inner {
  height: 36px;
}
::v-deep .vue-treeselect__control {
  background-color: transparent;
}
::v-deep .el-table__cell .cell {
  text-align: center;
}
::v-deep .el-select .el-input__inner {
  background-color: transparent;
  color: white;
}
/* 文字多余部分省略 */
::v-deep .el-table__cell .cell {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
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
::v-deep .peiou .el-date-editor .el-input__inner {
  width: 330px;
}
::v-deep .shifou .el-input--medium .el-input__inner {
  width: 330px;
}
::v-deep .shifou1 .el-input--medium .el-input__inner {
  width: 330px;
}
::v-deep .el-form-item__label {
  margin-left: 25px;
}
::v-deep .peious .el-input__inner {
  width: 351px;
}
::v-deep .el-form {
  margin-left: 40px;
}
.jiben {
  margin-left: -48px;
}
::v-deep .dialog-footer {
  margin-right: 34px;
}
::v-deep .el-input__inner {
  background: transparent;
  color: #fff;
}
</style>
