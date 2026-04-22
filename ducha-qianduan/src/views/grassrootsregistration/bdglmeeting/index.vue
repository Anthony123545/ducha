<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="deptName">
        <el-input
          placeholder="请输入单位"
          v-model="queryParams.deptName"
          @input="handleQuery"
        />
      </el-form-item>
      <!--
      <el-form-item label="" prop="compereId">
        <el-select
          v-model="queryParams.compereId"
          placeholder="请输入主持人"
          @change="Renyuan"
        >
          <el-option
            v-for="item in renYuans"
            :key="item.id"
            :label="item.name"
            :value="item.id"
            class="input_xiala"
          >
          </el-option>
        </el-select>
      </el-form-item> -->
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
          v-hasPermi="['grassrootsregistration:bdglmeeting:add']"
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
          v-hasPermi="['grassrootsregistration:bdglmeeting:edit']"
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
          v-hasPermi="['grassrootsregistration:bdglmeeting:remove']"
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
          v-hasPermi="['grassrootsregistration:bdglmeeting:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="bdglmeetingList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="deptName" />
      <el-table-column
        label="召开时间"
        align="center"
        prop="convokeTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.convokeTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="召开地点" align="center" prop="address" />
      <el-table-column
        label="会议形式"
        align="center"
        show-overflow-tooltip
        prop="meetingForm"
        :formatter="meetingFormFn"
      />
      <el-table-column
        label="会议时间"
        align="center"
        show-overflow-tooltip
        prop="convokeTime"
      />
      <!-- <el-table-column label="主持人" align="center" prop="compereRen" />
      <el-table-column
        label="参加人"
        align="center"
        prop="joinId"
        show-overflow-tooltip
      /> -->
      <el-table-column
        label="列席人"
        align="center"
        prop="peopleIds"
        show-overflow-tooltip
      />
      <el-table-column
        label="缺席人"
        align="center"
        prop="absentIds"
        show-overflow-tooltip
      />
      <el-table-column label="记录人" align="center" prop="peopleJiLu" />
      <!-- <el-table-column
        label="会议议题"
        align="center"
        prop="title"
        show-overflow-tooltip
      /> -->
      <el-table-column label="会议议题" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ onEditor(scope.row.title) }}</span>
        </template>
      </el-table-column>
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
            v-hasPermi="['grassrootsregistration:bdglmeeting:edit']"
            ><span class="edit">修改</span></el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglmeeting:remove']"
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

    <!-- 添加或修改团支部会议登记本对话框 -->
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
          ref="form"
          :model="form"
          :rules="rules"
          label-width="80px"
          :inline="true"
        >
          <el-form-item label="单位" prop="unitId">
            <treeselect
              v-model="form.unitId"
              :options="deptOptions"
              placeholder="请选择单位"
              @select="selectPeo"
              @open="blurSelect"
            />
          </el-form-item>
          <el-form-item label="召开时间" prop="convokeTime">
            <el-date-picker
              clearable
              size="small"
              v-model="form.convokeTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择召开时间"
              :editable="false"
              @change="timeDay"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="召开地点" prop="address" class="address">
            <el-input v-model="form.address" placeholder="请输入召开地点" />
          </el-form-item>
          <el-form-item label="会议形式" prop="meetingForm">
            <el-select v-model="form.meetingForm" placeholder="请选择会议形式">
              <el-option
                v-for="(item, i) in huiYiList"
                :key="i"
                :label="item.dictLabel"
                :value="item.dictValue"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="主持人" prop="compereId">
            <el-select
              v-model="form.compereId"
              placeholder="请选择主持人"
              ref="headerSearchSelect"
            >
              <el-option
                v-for="(item, i) in renYuan"
                :key="i"
                :label="item.name"
                :value="item.id"
                :disabled="item.status == 1"
                @click.native="compere(item.name)"
                class="input_xiala"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="记录人" prop="peopleId">
            <el-select v-model="form.peopleId" placeholder="请选择记录人">
              <el-option
                v-for="(item, i) in renYuan"
                :key="i"
                :label="item.name"
                :value="item.id"
                @click.native="people(item.name)"
                class="input_xiala"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="会议议题" prop="title">
          <el-input v-model="form.title" placeholder="请输入会议议题" />
        </el-form-item> -->

          <el-form-item label="参加人" prop="joinId" class="joinId">
            <el-select
              ref="abc"
              v-model="form1.joinId"
              multiple
              placeholder="请选择参加人"
              class="input_xiala"
              filterable
              @remove-tag="removeSelect"
              @change="changeSelect"
            >
              <el-option
                v-for="item in renYuan"
                :key="item.id"
                :label="item.name"
                :value="item.name"
                class="input_xiala"
                :disabled="item.disabled"
                :class="item.disabled == true ? 'colors' : ''"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="列席人" class="joinId">
            <el-select
              ref="abcd"
              v-model="form1.peopleIds"
              multiple
              placeholder="请选择列席人"
              class="input_xiala"
              filterable
              @remove-tag="removeSelect"
              @change="changeSelect"
            >
              <el-option
                v-for="item in renYuans"
                :key="item.id"
                :label="item.name"
                :disabled="item.disabled"
                :value="item.name"
                class="input_xiala"
                :class="item.disabled == true ? 'colors' : ''"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="缺席人" class="joinId">
            <el-select
              ref="abcde"
              v-model="form1.absentIds"
              multiple
              placeholder="请选择缺席人"
              class="input_xiala"
              filterable
              @remove-tag="removeSelect"
              @change="changeSelect"
            >
              <el-option
                v-for="item in renYuan"
                :key="item.id"
                :label="item.name"
                :value="item.name"
                :disabled="item.disabled"
                class="input_xiala"
                :class="item.disabled == true ? 'colors' : ''"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <div class="jiben">会议议题</div>
          <quill-editor
            class="quill-editor"
            ref="myTextEditor"
            v-model="form.title"
            style=" height: 150px; width: 920px;
           margin-bottom: 60px  margin-left: 0px;  margin-bottom: 55px;"
          ></quill-editor>

          <div class="jiben">会议内容</div>
          <ImageUpload v-model="form.fileUrl"></ImageUpload>
          <!-- <quill-editor
            class="quill-editor"
            ref="myTextEditor"
            v-model="form.contents"
            style="height: 150px; width: 920px;
           margin-bottom: 60px  margin-left: 0px;  margin-bottom: 55px;"
          ></quill-editor> -->
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(1)">确 定</el-button>
        <el-button @click="cancel" size="btn">取消</el-button>
      </div>
    </el-dialog>
    <!-- 查看 -->
    <!-- <el-dialog
      :visible.sync="menuRoleVisible"
      :title="title"
      append-to-body
      id="chakan"
      :close-on-click-modal="false"
    >
      <bdglmeeting
        v-if="menuRoleVisible"
        ref="menuRole"
        :message="wordInfo"
      ></bdglmeeting>
    </el-dialog> -->
    <el-dialog
      :visible.sync="menuRoleVisible"
      :title="title"
      append-to-body
      id="chakan"
      :close-on-click-modal="false"
    >
      <div
        style="
          margin-bottom: 20px;
          margin-left: 28%;
          font-size: 16px;
          font-weight: 700;
        "
      >
        会议形式:<span style="margin-right: 30px">{{ names }}</span>
        会议时间：<span>{{ tiemse }}</span>
      </div>
      <div v-for="item in arr" :key="item" class="divbox">
        <img :src="baseUrl + item" alt="" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
//引入查看表格
import bdglmeeting from "@/components/look_word/bdglmeeting.vue";
import { getZhuChiRen } from "@/api/grassrootsregistration/bdglparty";
import {
  listBdglmeeting,
  getBdglmeeting,
  delBdglmeeting,
  addBdglmeeting,
  updateBdglmeeting,
  exportBdglmeeting,
  getDept,
  getShouBu,
  getAll,
} from "@/api/grassrootsregistration/bdglmeeting";

// 富文本
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
import { quillEditor } from "vue-quill-editor";
// 树形
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { jieZhiList } from "@/api/grassrootsregistration/bdgldiary";

export default {
  name: "Bdglmeeting",
  components: {
    quillEditor,
    Treeselect,
    bdglmeeting,
  },
  dicts: ["sys_huiyi"],
  data() {
    return {
      //查看页面
      menuRoleVisible: false,
      wordInfo: {},

      //判断关闭
      style: "2",
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
      // 团支部会议登记本表格数据
      bdglmeetingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        convokeTime: null,
        address: null,
        compereId: null,
        joinId: null,
        peopleIds: null,
        absentIds: null,
        peopleId: null,
        title: null,
        contents: null,
        createtime: null,
        updatetime: null,
        unitId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitId: [
          { required: true, message: "单位名称不能为空", trigger: "blur" },
        ],
        convokeTime: [
          { required: true, message: "召开时间不能为空", trigger: "blur" },
        ],
        address: [
          { required: true, message: "召开地点不能为空", trigger: "blur" },
        ],
        compereId: [
          { required: true, message: "主持人不能为空", trigger: "blur" },
        ],
        peopleId: [
          { required: true, message: "记录人不能为空", trigger: "blur" },
        ],
        title: [
          { required: true, message: "会议议题不能为空", trigger: "blur" },
        ],
        joinId: [
          { required: true, message: "参加人不能为空", trigger: "blur" },
        ],
        peopleIds: [
          { required: true, message: "列席人不能为空", trigger: "blur" },
        ],
        absentIds: [
          { required: true, message: "缺席人不能为空", trigger: "blur" },
        ],
        meetingForm: [
          { required: true, message: "会议形式不能为空", trigger: "blur" },
        ],
      },
      //下拉菜单
      options: [],
      // 外面的部门
      buMeng: [],
      // 主持人 人员
      renYuan: [],
      // 外面的人员
      renYuans: [],
      // 单位
      unitArr: [],
      // 部门列表
      deptOptions: [],
      // 树形配置对象
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 修改拿到的数据
      result: {},
      changeArr: [], //存放选中选项的数组
      // 表单参数
      form1: {
        joinId: "",
        peopleIds: "",
        absentIds: "",
      },
      baseUrl: process.env.VUE_APP_BASE_API,
      arr: [],
      // 会议形式
      huiYiList: [],
      names: null,
      tiemse: null,
      // 当月日期
      current_month: [],
      // 下月日期
      current_month1: [],
      // 日期开关状态
      dayTime: null,
    };
  },
  created() {
    this.getList();
    this.getBuMen();
    this.getSuo();
    this.onEditor();
    // 获取当月
    this.getDay();
    // 获取下月
    this.getDayX();
    //会议形式
    this.getDicts("sys_huiyi").then((response) => {
      this.huiYiList = response.data;
    });
    jieZhiList().then((res) => {
      this.dayTime = res.rows[0].time;
    });
    //会议形式
    this.getDicts("sys_tuan").then((response) => {
      this.huiYiList = response.data;
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
    timeDay(val) {
    // 如果没有选择日期或时间限制未开启，则不进行验证
    if (!val || this.dayTime != 1) return;
    
    const selectedDate = new Date(val);
    selectedDate.setHours(0, 0, 0, 0);
    const selectedTimestamp = selectedDate.getTime();
    
    // 获取明天的日期
    const tomorrow = new Date();
    tomorrow.setHours(0, 0, 0, 0);
    tomorrow.setDate(tomorrow.getDate() + 1);
    
    // 如果选择的日期在明天及之后，则提示并清空选择
    if (selectedTimestamp >= tomorrow.getTime()) {
      this.$confirm("会议时间不能晚于明天，请重新选择", "提示", {
        confirmButtonText: "确定",
        type: "warning"
        }).then(() => {
          this.form.convokeTime = null;
        }).catch(() => {
          this.form.convokeTime = null;
        });
      }
    },
    // 翻译
    meetingFormFn(row) {
      var name = null;
      this.huiYiList.map((item) => {
        if (item.dictValue == row.meetingForm) {
          name = item.dictLabel;
        }
      });
      return name;
    },
    //change方法
    changeSelect() {
      for (var key in this.form1) {
        this.form1[key].forEach((item) => {
          this.changeArr.push(item);
        });
      }
      //有可能会出现重复的元素，所以对changeArr数组进行去重操作
      this.changeArr = this.quchong(this.changeArr);
      //对changeArr进行遍历，对hobbyList进行遍历，
      //changeArr有哪一项，就把hobbyList中该项的disabled属性置为true，禁用
      this.changeArr.forEach((item) => {
        this.renYuan.forEach((yitem) => {
          if (item === yitem.name) {
            yitem.disabled = true;
          }
        });
      });
    },
    //remove-tag方法，移除某一项时触发该方法
    removeSelect(val) {
      //将移除的那一项，从changeArr数组中找到下标，并移除
      var str = this.changeArr.findIndex((item) => item === val);
      this.changeArr.splice(str, 1);
      //对hobbyList数组遍历，将移除的那项disabled属性置为false，启用
      this.renYuan.forEach((item) => {
        if (val === item.name) {
          item.disabled = false;
        }
      });
    },
    //数组去重的方法，避免changeArr出现重复的元素
    quchong(arr) {
      return arr.filter(function (item, index, arr) {
        return arr.indexOf(item, 0) === index;
      });
    },
    // 获取搜索所有人
    getSuo() {
      getAll().then((res) => {
        this.renYuans = res.data;
      });
    },
    // 转义视图不加标签
    onEditor(str) {
      if (str != null) {
        var str = str.replace(/<\/?[^>]*>/g, "");
        str = str.replace(/[ | ]*\n/g, "\n");
        str = str.replace(/ /gi, "");
      }
      return str;
    },
    // 获取搜索下拉部门
    getBuMen() {
      getShouBu().then((res) => {
        this.buMeng = res.data;
      });
    },
    // 重置表单人员
    ChongZhiRen() {
      // 主持人
      this.form.compereId = null;
      // 记录人
      this.form.peopleId = null;
      // 参加人
      this.form1.joinId = [];
      // 列席人
      this.form1.peopleIds = [];
      // 缺席人
      this.form1.absentIds = [];
    },
    // 获取主持人
    getZhuChi(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 单位选择触发
    selectPeo(val) {
      this.ChongZhiRen();
      this.form.unitId = val.id;
      this.getZhuChi(this.form.unitId);
      this.changeArr = [];
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 搜索时间选择触发
    tiem() {
      this.getList();
    },
    // 搜索主持人选中触发
    Renyuan() {
      this.getList();
    },
    //  主持人选中触发
    compere(name) {
      this.form.compereRen = name;
    },
    //  记录人选中触发
    people(name) {
      this.form.peopleJiLu = name;
    },
    /** 查询团支部会议登记本列表 */
    getList() {
      this.loading = true;
      listBdglmeeting(this.queryParams).then((response) => {
        this.bdglmeetingList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 搜索单位触发
    company() {
      this.getList();
      // 联动人员
      getZhuChiRen(this.queryParams.unitId).then((res) => {
        this.renYuans = res.data;
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
        convokeTime: null,
        address: null,
        compereId: null,
        joinId: null,
        peopleIds: null,
        absentIds: null,
        peopleId: null,
        title: null,
        contents: null,
        createtime: null,
        updatetime: null,
        unitId: null,
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
      this.getSuo();
      this.reset();
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 对话框重置按钮操作 */
    resertwo() {
      this.reset();
    },
    // 让select失去焦点
    blurSelect() {
      // this.$refs.headerSearchSelect.blur();
      // this.$refs.aa.blur();
      // this.$refs.bb.blur();
      // this.$refs.cc.blur();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.form1.absentIds = [];
      this.form1.joinId = [];
      this.form1.peopleIds = [];
      // 主持人 人员
      this.renYuan = [];
      this.reset();
      this.style = 2;
      this.getTreeselect();
      this.open = true;
      this.title = "添加团支部会议登记本";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.form1.absentIds = [];
      this.form1.joinId = [];
      this.form1.peopleIds = [];
      this.reset();
      this.form.unitId = row.unitId;
      this.getZhuChi(this.form.unitId);
      this.getTreeselect();
      const id = row.id || this.ids;
      getBdglmeeting(id).then((response) => {
        this.result = response.data;
        for (var k in this.result) {
          if (k === "deptName") {
            delete this.result[k];
          }
        }
        if (this.result.joinId !== "") {
          this.form1.joinId = response.data.joinId.split(",");
        }
        if (this.result.peopleIds !== "" && this.result.peopleIds != null) {
          this.form1.peopleIds = response.data.peopleIds.split(",");
        }
        if (this.result.absentIds !== "" && this.result.absentIds != null) {
          this.form1.absentIds = response.data.absentIds.split(",");
        }
        this.changeSelect();
        this.form = this.result;
        this.open = true;
        this.title = "修改团支部会议登记本";
      });
    },

    //查看按钮操作
    handleChakan(row) {
      // 是否隐藏按钮
      this.opens = false;
      const id = row.id || this.ids;
      getBdglmeeting(id).then((response) => {
        if (response.data.fileUrl != null) {
          if (response.data.fileUrl != "") {
            this.arr = response.data.fileUrl.split(",");
            this.tiemse = response.data.convokeTime;
            this.huiYiList.map((item) => {
              if (item.dictValue == response.data.meetingForm) {
                this.names = item.dictLabel;
              }
            });
            this.title = "查看团支部会议登记本";
            this.menuRoleVisible = true;
            this.tableStatus = false;
            return this.names;
          }
        }
        // this.wordInfo = response.data;
      });
    },

    /** 提交按钮 */
    submitForm(style) {
      this.style = style;
      this.form.joinId = this.form1.joinId;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            if (this.form1.joinId !== undefined) {
              this.form.joinId = this.form1.joinId.join(",");
            }
            if (
              this.form1.peopleIds !== undefined &&
              this.form1.peopleIds != null &&
              this.form1.peopleIds != ""
            ) {
              this.form.peopleIds = this.form1.peopleIds.join(",");
            } else {
              this.form.peopleIds = "";
            }
            if (
              this.form1.absentIds !== undefined &&
              this.form1.absentIds != null &&
              this.form1.absentIds != ""
            ) {
              this.form.absentIds = this.form1.absentIds.join(",");
            } else {
              this.form.absentIds = "";
            }
            updateBdglmeeting(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if (
              this.form1.peopleIds != null &&
              this.form1.peopleIds.length != 0 &&
              this.form1.peopleIds != ""
            ) {
              this.form.peopleIds = this.form1.peopleIds.join(",");
            } else {
              this.form.peopleIds = "";
            }
            if (
              this.form1.absentIds != null &&
              this.form1.absentIds != "" &&
              this.form1.absentIds.length != 0
            ) {
              this.form.absentIds = this.form1.absentIds.join(",");
            } else {
              this.form.absentIds = "";
            }
            this.form.joinId = this.form1.joinId.join(",");
            addBdglmeeting(this.form).then((response) => {
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
        // .confirm('是否确认删除团支部会议登记本编号为"' + ids + '"的数据项？')
        .confirm("是否确认删除该数据？")
        .then(function () {
          return delBdglmeeting(ids);
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
        .confirm("是否确认导出所有团支部会议登记本数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBdglmeeting(queryParams);
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
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
}

::v-deep .el-textarea__inner {
  width: 884px;
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
  /* width: 200px !important;
  height: 36px; */
  background: transparent;
  color: #fff;
  border: 1px solid white !important;
  border-color: #fff !important;
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
  box-sizing: border-box;
  padding: 30px 0px 30px 42px;
}

/* 调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}

.contents {
  padding: 0px 40px !important;
}

/* 下拉菜单字体/背景颜色 */
.el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
  background-color: #196299;
  color: #111;
}

.el-select-dropdown__item {
  color: #111;
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

/* 富文本 */
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

/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}

/* 单位下拉菜单选中字体颜色 */
::v-deep .vue-treeselect__single-value {
  color: #fff !important;
}

/* 出席人 */
::v-deep .joinId {
  width: 650px !important;
}

::v-deep .joinId .el-input__inner {
  width: 510px !important;
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

::v-deep .el-input__inner {
  cursor: pointer !important;
}

::v-deep .ql-toolbar.ql-snow .ql-formats {
  margin-right: 7px;
}

/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 30px 55px;
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

#chakan /deep/ .el-dialog__body {
  padding: 30px 30px 30px 112px;
}
#chakan ::v-deep .el-dialog {
  min-height: 700px;
  background: #fff !important;
}

::v-deep .el-select__input {
  color: #fff !important;
}

.colors {
  color: #ccc !important;
}
/* 图片上传 */
::v-deep .el-upload--picture-card {
  background-color: transparent;
}
::v-deep .el-upload__tip {
  color: #fff;
}
::v-deep img {
  object-fit: cover;
}
.divbox {
  width: 1016px !important;
  min-height: 540px;
}
.divbox img {
  width: 700px;
  /*height: 500px;*/
  border-radius: 10px;
}
</style>
