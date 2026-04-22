<template>
  <div class="app-container">
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
          v-hasPermi="['thought:getready:add']"
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
          v-hasPermi="['thought:getready:edit']"
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
          v-hasPermi="['thought:getready:remove']"
          >删除</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5"> -->
      <!-- <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['thought:getready:export']"
          >导出</el-button
        > -->
      <!-- </el-col> -->
      <!-- </el-row> -->
      <el-col :span="20" :xs="24">
        <el-table
          v-loading="loading"
          :data="getreadyList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column
            label="序号"
            type="index"
            width="50"
            align="center"
          />
          <el-table-column
            label="教育议题"
            align="center"
            prop="talkTheme"
            show-overflow-tooltip
          />
          <el-table-column
            label="教育议题简介"
            align="center"
            prop="talkIntroduction"
            show-overflow-tooltip
          />
          <el-table-column
            label="教育内容"
            align="center"
            prop="talkContent"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <span>{{ onEditor(scope.row.talkContent) }}</span>
            </template>
          </el-table-column>

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
            label="组织人"
            align="center"
            prop="createName"
            width="100"
          />
          <el-table-column
            label="记录人"
            align="center"
            prop="recorder"
            width="100"
          />
          <el-table-column
            label="参加人"
            align="center"
            prop="talkPeople"
            show-overflow-tooltip
            :formatter="unit2Format"
          />
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
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
            width="300px"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button
                size="btk"
                type="text"
                @click.native="ViewRead(scope.row)"
              >
                <a target="_blank">预览</a>
              </el-button>
              <el-button
                size="btk"
                type="text"
                @click="handlelook(scope.row)"
                v-hasPermi="['thought:getready:query']"
                >查看</el-button
              >
              <el-button
                size="btu"
                type="text"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['thought:getready:edit']"
                >修改</el-button
              >
              <el-button
                size="btd"
                type="text"
                @click="handleDelete(scope.row)"
                v-hasPermi="['thought:getready:remove']"
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

    <!-- 添加或修改教育准备会对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1080px"
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
          <el-form-item label="教育议题" prop="talkTheme">
            <el-input v-model="form.talkTheme" placeholder="请输入内容" />
          </el-form-item>
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
          <el-form-item label="组织人" prop="createName">
            <!-- <el-input
              v-model="form.createName"
              placeholder="请选择用户名称"
            /> -->
            <el-select
              filterable
              v-model="form.createName"
              ref="createName"
              placeholder="请选择组织人"
            >
              <el-option
                v-for="item in executor"
                :key="item.id"
                :label="item.name"
                :value="item.name"
                @click.native="zuzhiPeople(item.id)"
              />
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="创建人id" prop="createId">
          <el-input v-model="form.createId" placeholder="请输入创建人id" />
        </el-form-item> -->

          <!-- <el-form-item label="文件名称" prop="fileName">
            <el-input v-model="form.fileName" placeholder="请输入文件名称" />
          </el-form-item>
          <el-form-item label="文件路径" prop="filePath">
            <el-input v-model="form.filePath" placeholder="请输入文件路径" />
          </el-form-item> -->
          <el-form-item label="记录人" prop="recorder">
            <!-- <el-input v-model="form.recorder" placeholder="请输入记录人" /> -->
            <el-select
              v-model="form.recorder"
              ref="recorder"
              placeholder="请选择记录人"
              filterable
            >
              <el-option
                v-for="item in recodeexecutor"
                :key="item.id"
                :label="item.name"
                :value="item.name"
                @click.native="jiluPeople(item.id)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="参加人" prop="talkPeople" label-width="100px">
            <!-- <el-input v-model="form.talkPeople" placeholder="请输入谈心人员" /> -->
            <el-select
              v-model="form.talkPeople"
              ref="peoplenames"
              placeholder="请选择参加人"
              class="canjiaren"
              multiple
              filterable
            >
              <el-option
                v-for="item in executor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                @click.native="returnExecutor(item.id, item.name)"
              />
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="记录人id" prop="recorderId">
          <el-input v-model="form.recorderId" placeholder="请输入记录人id" />
        </el-form-item> -->
          <div class="jiben">教育议题简介</div>
          <el-form-item prop="talkIntroduction">
            <el-input
              v-model="form.talkIntroduction"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
          <!-- <div class="jiben">会议主题</div> -->

          <div class="jiben">教育内容</div>
          <el-form-item>
            <!-- <editor v-model="form.talkContent" :min-height="192"/> -->
            <!-- <quill-editor
            class="quill-editor"
            ref="myTextEditor"
            v-model="form.talkContent"
            style="
              height: 150px;
              width: 1010px;
              margin-left: 0px;
              margin-bottom: 60px;
            "
          ></quill-editor> -->
            <quill-editor
              class="quill-editor"
              ref="myTextEditor"
              v-model="form.talkContent"
              style="
                height: 150px;
                width: 1010px;
                margin-left: 0px;
                color: white;
                margin-bottom: 60px;
              "
            ></quill-editor>
          </el-form-item>
          <div class="jiben">附件</div>
          <el-form-item>
            <FileUpload v-model="form.fileName" />
          </el-form-item>
          <!-- <el-input
              v-model="form.fileName"
             
            /> -->
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看教育准备会 -->
    <el-dialog
      :title="title1"
      :visible.sync="open1"
      width="1080px"
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
          <el-form-item label="教育议题" prop="talkTheme">
            <el-input
              v-model="form1.talkTheme"
              disabled
              placeholder="请输入内容"
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
          <el-form-item label="组织人" prop="createName">
            <el-select
              disabled
              filterable
              v-model="form1.createName"
              ref="createName"
              placeholder="请选择组织人"
            >
              <el-option
                v-for="item in executor"
                :key="item.id"
                :label="item.name"
                :value="item.name"
                @click.native="zuzhiPeople(item.id)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="记录人" prop="recorder">
            <el-select
              disabled
              v-model="form1.recorder"
              ref="recorder"
              placeholder="请选择记录人"
              filterable
            >
              <el-option
                v-for="item in recodeexecutor"
                :key="item.id"
                :label="item.name"
                :value="item.name"
                @click.native="jiluPeople(item.id)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="参加人" prop="talkPeople" label-width="100px">
            <el-select
              disabled
              v-model="form1.talkPeople"
              ref="peoplenames"
              placeholder="请选择参加人"
              class="canjiaren"
              multiple
              filterable
            >
              <el-option
                v-for="item in executor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                @click.native="returnExecutor(item.id, item.name)"
              />
            </el-select>
          </el-form-item>
          <div class="jiben">教育议题简介</div>
          <el-form-item prop="talkIntroduction">
            <el-input
              disabled
              v-model="form1.talkIntroduction"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
          <div class="jiben">教育内容</div>
          <el-form-item>
            <quill-editor
              disabled
              class="quill-editor"
              ref="myTextEditor"
              v-model="form1.talkContent"
              style="
                height: 150px;
                width: 1010px;
                margin-left: 0px;
                color: white;
                margin-bottom: 60px;
              "
            ></quill-editor>
          </el-form-item>
          <div class="jiben">附件</div>
          <el-form-item class="fujian">
            <!-- <FileUpload v-model="form1.fileName" /> -->
            <span class="filename">{{ form1.fileName }}</span>
            <a
              :href="form1.filePath"
              :download="form1.fileName"
              title="下载"
              style="color: white; margin-left: 10px"
              class="downloadfile"
              >下载附件</a
            >
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="open1 = false">关 闭</el-button>
        <!-- <el-button @click="open1=false" size="btn">取 消</el-button> -->
      </div>
    </el-dialog>
    <!-- </div> -->
    <!-- 添加预览doc文件弹框 -->
    <el-dialog
      style="color: pink"
      :title="title3"
      :visible.sync="open33"
      width="1200px"
      append-to-body
      id="fileinfodocx"
      class="el-dialog__header"
      custom-class="yuedu"
      :close-on-click-modal="false"
    >
      <div ref="file"></div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="open33 = false">关 闭</el-button>
        <!-- <el-button type="primary" @click="open33 = false">
     关 闭</el-button
     > -->
      </div>
    </el-dialog>
    <!-- 预览PDF文件弹框 -->
    <el-dialog
      :title="title3"
      :visible.sync="previewDialog"
      width="1200px"
      append-to-body
      id="fileinfopdf"
      class="el-dialog__header"
      :close-on-click-modal="false"
    >
      <template>
        <div style="display: flex">
          <el-button
            :class="gopage == true ? 'bg' : ''"
            class="gopage"
            :theme="'default'"
            type="submit"
            :title="'上一页'"
            @click.native="prePage"
          >
            上一页</el-button
          >
          <div
            style="
              width: 50px;
              height: 28px;
              background: #f4f4f4;
              text-align: center;
              line-height: 28px;
            "
          >
            {{ pageNum }}/{{ pageTotalNum }}
          </div>
          <el-button
            :class="gopage == false ? 'bg' : ''"
            :theme="'default'"
            class="downpage"
            type="submit"
            :title="'下一页'"
            @click.native="nextPage"
          >
            下一页</el-button
          >
        </div>
        <PDF
          ref="pdf"
          :src="pdfurl"
          :page="pageNum"
          :rotate="pageRotate"
          @progress="loadedRatio = $event"
          @page-loaded="pageLoaded($event)"
          @num-pages="pageTotalNum = $event"
          @error="pdfError($event)"
          @link-clicked="page = $event"
        ></PDF>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="previewDialog = false"
            >关 闭</el-button
          >
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {
  listGetready,
  getGetready,
  delGetready,
  addGetready,
  updateGetready,
  exportGetready,
} from "@/api/thought/getready";
import { exportBdgShuju } from "@/api/grassrootsregistration/bdgldiary";
import { allUser } from "@/api/workingArrangements/workTask";
import { getAll } from "@/api/thought/parliament";
import { getWeiShu } from "@/api/grassrootsregistration/bdglmeeting";
import axios from "axios";
import PDF from "vue-pdf";
const docx = require("docx-preview");
window.JSZip = require("jszip");
// 富文本
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
import { quillEditor } from "vue-quill-editor";
export default {
  name: "Getready",
  data() {
    return {
      tiem: "",
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
      // 教育准备会表格数据
      getreadyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        talkPeople: null,
        startTime: null,
        endTime: null,
        createName: null,
        createId: null,
        filePath: null,
        fileName: null,
        talkContent: null,
        talkTheme: null,
        talkIntroduction: null,
        recorder: null,
        recorderId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        talkTheme: [
          { required: true, message: "教育议题不能为空", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" },
        ],
        createName: [
          { required: true, message: "组织人不能为空", trigger: "blur" },
        ],
        recorder: [
          { required: true, message: "记录人不能为空", trigger: "blur" },
        ],
        talkPeople: [
          { required: true, message: "参加人不能为空", trigger: "blur" },
        ],
      },
      // 获取人员
      executors: [],
      // 记录人
      recodeexecutor: [],
      // 添加列表的人员
      executor: [],
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
      rylist: [],
      form1: {},
      open1: false,
      title1: "",
      // 下载文件路径
      downurl: process.env.VUE_APP_BASE_API,
      // pdf文件
      previewDialog: false,
      pdfurl: "",
      pageNum: 1,
      pageTotalNum: 1,
      pageRotate: 0,
      // 加载进度
      loadedRatio: 0,
      curPageNum: 0,
      gopage: true,
      // 虚拟字段
      fid: "",
      open33: false,
      title3: "预览文件",
    };
  },
  components: {
    quillEditor,
  },
  created() {
    this.getList();
    this.getSuoYou();
    // 获取外部部门树形
    this.treeselect();
    allUser().then((res) => {
      this.rylist = res.rows;
    });
  },
  components: {
    PDF,
  },
  methods: {
    // 预览弹框
    submitForm3() {
      this.open33 = false;
    },
    /**
     * 预览PDF
     */
    previewPDF(row, index) {
      this.previewDialog = true;
    },
    // 上一页函数，
    prePage() {
      this.gopage = true;
      var page = this.pageNum;
      page = page > 1 ? page - 1 : this.pageTotalNum;
      this.pageNum = page;
    },
    // 下一页函数
    nextPage() {
      this.gopage = false;
      var page = this.pageNum;
      page = page < this.pageTotalNum ? page + 1 : 1;
      this.pageNum = page;
    },
    // 页面顺时针翻转90度。
    clock() {
      this.pageRotate += 90;
    },
    // 页面逆时针翻转90度。
    counterClock() {
      this.pageRotate -= 90;
    },
    // 页面加载回调函数，其中e为当前页数
    pageLoaded(e) {
      this.curPageNum = e;
    },
    // 错误时回调函数。
    pdfError(error) {
      console.error(error);
    },
    // 打印全部
    pdfPrintAll() {
      /**
       * 打印界面字符乱码是因为你pdf中使用了自定义字体导致的,谷歌浏览器打印的时候预览界面真的变成了真·方块字 ,解决方案如下：
       * 用文章最后的pdfjsWrapper.js在替换掉node_modules/vue-pdf/src/pdfjsWrapper.js
       */

      this.$refs.pdf.print();
    },
    // 阅读按钮操作
    ViewRead(row) {
      console.log(row);
      var path = row.filePath;
      var index = path.lastIndexOf(".");
      var str = path.substring(index + 1, path.length);
      if (str == "docx" || str == "doc") {
        this.open33 = true;
        axios({
          method: "get",
          responseType: "blob", // 设置响应文件格式
          url: process.env.VUE_APP_BASE_API + row.filePath,
        }).then(({ data }) => {
          $(".docx").attr("id", "printArea");
          docx.renderAsync(data, this.$refs.file); // 渲染到页面预览
        });
      } else if (str == "pdf") {
        this.previewDialog = true;
        this.pdfurl = process.env.VUE_APP_BASE_API + row.filePath;
        this.pageNum = 1;
      } else {
        this.$message.error("只能预览Doc,Docx,Pdf格式的文件！");
      }
    },
    // 获取执行人
    unit2Format(row) {
      var deptName = "";
      this.recodeexecutor.map((item) => {
        row.talkPeople.split(",").forEach((items, index) => {
          if (item.id == items) {
            deptName += item.name + "  ";
          }
        });
      });
      return deptName;
    },
    returnExecutor(id, name) {
      // this.form.talkPeople=id
    },
    zuzhiPeople(id) {
      this.form.createId = id;
    },
    jiluPeople(id) {
      this.form.recorderId = id;
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
    // 搜索时间触发
    shiJian(data) {
      this.queryParams.diaryTime = data;
      this.timeDate = data;
      this.getList();
      this.getShuJu(data);
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

    // 获取所有人员
    getSuoYou() {
      getAll().then((response) => {
        this.executors = response.data;
        // 添加的人员
        this.executor = response.data;
        // 记录人
        this.recodeexecutor = response.data;
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
    /** 查询教育准备会列表 */
    getList() {
      this.loading = true;
      listGetready(this.queryParams).then((response) => {
        this.getreadyList = response.rows;
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
        talkPeople: null,
        startTime: null,
        endTime: null,
        createTime: null,
        updateTime: null,
        createName: null,
        createId: null,
        filePath: null,
        fileName: null,
        talkContent: null,
        talkTheme: null,
        talkIntroduction: null,
        recorder: null,
        recorderId: null,
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
      this.queryParams = {};
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
      this.title = "添加教育准备会";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getGetready(id).then((response) => {
        this.form = response.data;
        this.form.talkPeople = this.form.talkPeople.split(",").map(Number);
        this.open = true;
        this.title = "修改教育准备会";
      });
    },
    // 查看按钮操作
    handlelook(row) {
      this.reset();
      const id = row.id || this.ids;
      getGetready(id).then((response) => {
        this.form1 = response.data;
        this.form1.talkPeople = this.form1.talkPeople.split(",").map(Number);
        this.form1.filePath =
          process.env.VUE_APP_BASE_API + this.form1.filePath;

        this.open1 = true;
        this.title1 = "查看教育准备会";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if (this.form.fileName) {
        this.form.filePath = this.form.fileName[0].url;
        this.form.fileName = this.form.fileName[0].name;
      }
      this.form.talkPeople = this.form.talkPeople.join(",");
      this.form.talkPeopleId = this.form.talkPeople;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateGetready(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGetready(this.form).then((response) => {
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
          return delGetready(ids);
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
        .confirm("是否确认导出所有教育准备会数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportGetready(queryParams);
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
/* 附件 */
::v-deep .fujian .el-form-item__content {
  display: flex;
}
/* 备注 */
.thistext {
  color: white;
  width: 900px;
  border: 1px solid #fff;
  border-radius: 4px;
  height: auto;
  min-height: 30px;
  text-indent: 1em;
  padding: 2px;
  line-height: 30px;
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

::v-deep .el-pagination__sizes .el-input__suffix {
  right: 6px;
}
::v-deep .ql-snow .ql-picker-options .ql-picker-item:focus {
  outline: none !important;
}
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
/* normal */
::v-deep .ql-active {
  outline: none !important;
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
  width: 1010px;
  /* height: 80px !important; */
}
::v-deep .el-form-item__content .el-textarea .el-textarea__inner {
  /* width: 1000px; */
  height: 80px !important;
}
::v-deep .el-dialog__body {
  padding-left: 27px;
  padding-right: 0px !important;
  padding-bottom: 0px !important;
}
/* 富文本内容 */
/* ::v-deep .editor{
  width: 1010px;
  color: white;
} */
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

::v-deep .data .el-date-editor.el-input {
  width: 100%;
  border: none !important;
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
::v-deep .canjiaren .el-input__inner {
  width: 560px;
}
/* 计划附件 */
::v-deep .el-upload__tip {
  color: white;
  position: absolute;
  top: -5px;
  width: 500px;
  left: 90px;
  color: white;
}
::v-deep .el-link.el-link--default {
  color: white;
  text-indent: 0.5em;
}

::v-deep .el-form-item__content .el-textarea .el-textarea__inner {
  color: white !important;
}
/* 卡片样式 */
.app-container {
  height: calc(100vh - 108px) !important;
}
::v-deep .el-select__input {
  color: white;
}
/* doc文件样式修改 */
::v-deep .docx {
  padding: 0 !important;
}
::v-deep .docx-wrapper > section.docx {
  /* padding: 0 !important; */
  margin-bottom: 0px !important;
  box-shadow: none;
}
/* 预览弹框背景去除 */
::v-deep .docx-wrapper {
  padding: 0 !important;
  background: none !important;
}
::v-deep .docx {
  width: 100% !important;
}

/*  ::v-deep .yuedu .el-dialog__body {
  padding-left: 126px !important;
} */

/* 上一页样式 */
.gopage {
  width: 60px;
  height: 28px;
  background: #f4f4f4;
  color: #60627d;
  border-radius: 14px 0px 0px 14px;
}
/* 下一页样式 */
.downpage {
  width: 60px;
  height: 28px;
  background: #f4f4f4;
  border-radius: 0px 14px 14px 0px;
}
.bg {
  background: #1d96ff;
  color: white;
}
::v-deep 
.el-table .el-table__fixed-header-wrapper th {
  background-color: #004d86 ;
  color: #fff;
}

#fileinfodocx ::v-deep .el-dialog__body {
  padding-right: 29px !important;
}
#fileinfopdf ::v-deep .el-dialog__body {
  padding-right: 29px !important;
}
::v-deep .docx p, p.docx_1{
  line-height: 2;
}
</style>
