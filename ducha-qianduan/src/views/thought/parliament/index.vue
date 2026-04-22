<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['thought:parliament:add']"
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
          v-hasPermi="['thought:parliament:edit']"
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
          v-hasPermi="['thought:parliament:remove']"
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
          v-hasPermi="['thought:parliament:export']"
        >导出</el-button> -->
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="parliamentList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键id" align="center" prop="id" /> -->
      <el-table-column label="序号" type="index" width="50" align="center" />
      <!-- <el-table-column label="单位名称" align="center" prop="unitName" /> -->
      <!-- <el-table-column label="单位id" align="center" prop="unitId" /> -->

      <!-- <el-table-column label="创建人姓名" align="center" prop="createName" /> -->
      <!-- <el-table-column label="创建人id" align="center" prop="createId" /> -->
      <!-- <el-table-column label="文件路径" align="center" prop="filePath" /> -->
      <!-- <el-table-column label="文件名称" align="center" prop="fileName" show-overflow-tooltip/> -->
      <el-table-column
        label="议会主题"
        align="center"
        prop="talkTheme"
        show-overflow-tooltip
      />
      <el-table-column
        label="会议内容"
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
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="组织人" align="center" prop="organizer" />
      <el-table-column
        label="参加人"
        align="center"
        prop="participants"
        show-overflow-tooltip
        :formatter="unit2Format"
      />
      <!-- <el-table-column label="参加人id" align="center" prop="participantsId" /> -->

      <el-table-column
        label="记录人"
        align="center"
        prop="recorder"
        show-overflow-tooltip
      />
      <!-- <el-table-column label="记录人id" align="center" prop="recorderId" /> -->

      <!-- <el-table-column label="组织人id" align="center" prop="organizerId" /> -->
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
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="300px"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button size="btk" type="text" @click.native="ViewRead(scope.row)">
            <a target="_blank">预览</a>
          </el-button>
          <el-button
            size="btk"
            type="text"
            @click="handlelook(scope.row)"
            v-hasPermi="['thought:parliament:query']"
            >查看</el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['thought:parliament:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['thought:parliament:remove']"
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

    <!-- 添加或修改党委议教会对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1080px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="100px"
        :inline="true"
      >
        <!-- <el-form-item label="单位名称" prop="unitName">
          <el-select v-model="form.unitName" placeholder="请选择单位名称">
            <el-option label="请选择字典生成" value="" />
          </el-select>
          
        </el-form-item> -->
        <div class="jiben">基本信息</div>
        <el-form-item prop="talkTheme" label="议会主题">
          <el-input v-model="form.talkTheme" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            editable
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
        <!-- <el-form-item prop="unitName" label="单位名称"> -->
        <!-- <el-input
          v-model="queryParams.unitId"
          placeholder="请输入单位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        /> -->
        <!-- <treeselect
          v-model="form.unitName"
          :options="users"
          placeholder="请选择到访单位"
          class="threeselects"
          @select="selectPeo1"
        />
      </el-form-item> -->
        <!-- <el-form-item label="单位id" prop="unitId">
          <el-input v-model="form.unitId" placeholder="请输入单位id" />
        </el-form-item> -->

        <!-- <el-form-item label="创建人姓名" prop="createName">
          <el-input v-model="form.createName" placeholder="请输入创建人姓名" />
        </el-form-item> -->
        <!-- <el-form-item label="创建人id" prop="createId">
          <el-input v-model="form.createId" placeholder="请输入创建人id" />
        </el-form-item> -->
        <!-- <el-form-item label="文件路径" prop="filePath">
          <el-input v-model="form.filePath" placeholder="请输入文件路径" />
        </el-form-item>
        <el-form-item label="文件名称" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入文件名称" />
        </el-form-item> -->
        <el-form-item label="组 织 人" prop="organizer">
          <!-- <el-input v-model="form." placeholder="请输入组织人" /> -->
          <el-select
            filterable
            v-model="form.organizer"
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
        <el-form-item label="记 录 人" prop="recorder">
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
        <!-- <el-form-item label="记录人id" prop="recorderId">
          <el-input v-model="form.recorderId" placeholder="请输入记录人id" />
        </el-form-item> -->
        <!-- <el-form-item label="参加人" prop="participants">
          <el-input v-model="form.participants" placeholder="请输入参加人" />
        </el-form-item> -->
        <el-form-item label="参 加 人" prop="participants" label-width="100px">
          <div>
            <!-- <el-select
                multiple
                v-model="form.participants"
                placeholder="请选择参加人"
                id="execute"
                ref="peoplenames"
                 class="canjiaren"
              >
                <el-option
                  v-for="item in executor"
                  :key="item.deptId"
                  :label="item.nickName"
                  :value="item.userId"
                  @click.native="returnExecutor(item.deptId, item.nickName)"
                />
              </el-select> -->
            <el-select
              v-model="form.participants"
              ref="participants"
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
          </div>
        </el-form-item>
        <!-- <el-form-item label="参加人id" prop="participantsId">
          <el-input v-model="form.participantsId" placeholder="请输入参加人id" />
        </el-form-item> -->

        <!-- <el-form-item label="组织人id" prop="organizerId">
          <el-input v-model="form.organizerId" placeholder="请输入组织人id" />
        </el-form-item> -->

        <div class="jiben">会议内容</div>
        <el-form-item>
          <!-- <editor v-model="form.talkContent" :min-height="192"/> -->
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
          <FileUpload v-model="form.fileName" :limit="1" />
        </el-form-item>
        <!-- <a :href="form.filePath" download class="downloadfile">下载附件</a> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看按钮操作 -->
    <el-dialog
      :title="title1"
      :visible.sync="open1"
      width="1080px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form1"
        :rules="rules"
        label-width="100px"
        :inline="true"
      >
        <div class="jiben">基本信息</div>
        <el-form-item prop="talkTheme" label="议会主题">
          <el-input
            v-model="form1.talkTheme"
            placeholder="请输入内容"
            disabled
          />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            disabled
            editable
            v-model="form1.startTime"
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
            disabled
            v-model="form1.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="组 织 人" prop="organizer">
          <el-select
            disabled
            filterable
            v-model="form1.organizer"
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
        <el-form-item label="记 录 人" prop="recorder">
          <!-- <el-input v-model="form.recorder" placeholder="请输入记录人" /> -->
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
        <el-form-item label="参 加 人" prop="participants" label-width="100px">
          <div>
            <el-select
              v-model="form1.participants"
              ref="participants"
              placeholder="请选择参加人"
              class="canjiaren"
              multiple
              filterable
              disabled
            >
              <el-option
                v-for="item in executor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                @click.native="returnExecutor(item.id, item.name)"
              />
            </el-select>
          </div>
        </el-form-item>
        <div class="jiben">会议内容</div>
        <el-form-item>
          <!-- <div class="thistext">{{onEditor(form1.talkContent)}}</div> -->
          <quill-editor
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
          <span class="filename">{{ form1.fileName }}</span>
          <a
            :href="form1.filePath"
            :download="form1.fileName"
            title="下载"
            style="color: white; margin-left: 10px"
            class="downloadfile"
            >下载附件</a
          >
          <!-- <FileUpload disabled v-model="form1.fileName" /> -->
          <!-- <div style="color:white;width:600px;height:30px;border:1px solid white;line-height:30px">{{form1.fileName}}</div> -->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="open1 = false">关 闭</el-button>
        <!-- <el-button @click="open1=false" size="btn">取 消</el-button> -->
      </div>
    </el-dialog>

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
  listParliament,
  getParliament,
  delParliament,
  addParliament,
  updateParliament,
  exportParliament,
  getAll,
} from "@/api/thought/parliament";
// 树形
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
// 单位
import { getDept2 } from "@/api/grassrootsregistration/bdglmeeting";
import axios from "axios";
import PDF from "vue-pdf";
const docx = require("docx-preview");
window.JSZip = require("jszip");
export default {
  name: "Parliament",
  components: {
    Treeselect,
  },
  data() {
    return {
      // 虚拟字段
      fid: "",
      open33: false,
      title3: "预览文件",
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
      // 党委议教会表格数据
      parliamentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitName: null,
        unitId: null,
        startTime: null,
        endTime: null,
        createName: null,
        createId: null,
        filePath: null,
        fileName: null,
        talkContent: null,
        recorder: null,
        recorderId: null,
        participants: null,
        participantsId: null,
        organizer: null,
        organizerId: null,
        talkTheme: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        talkTheme: [
          { required: true, message: "议会主题不能为空", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" },
        ],
        organizer: [
          { required: true, message: "组织人不能为空", trigger: "blur" },
        ],
        recorder: [
          { required: true, message: "记录人不能为空", trigger: "blur" },
        ],
        participants: [
          { required: true, message: "参加人不能为空", trigger: "blur" },
        ],
      },
      // 获取单位列表
      users: [],
      // 获取单位列表
      userss: [],
      unitName: "",
      // 组织人
      executor: [],
      // 记录人
      recodeexecutor: [],
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
    };
  },
  components: {
    PDF,
  },
  created() {
    this.getList();
    getDept2().then((res) => {
      this.userss = res.data;
    });
    this.getSuoYou();
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
      console.log(str);
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
        row.participants.split(",").forEach((items, index) => {
          if (item.id == items) {
            deptName += item.name + "  ";
          }
        });
      });
      return deptName;
    },
    zuzhiPeople(id) {
      this.form.organizerId = id;
    },
    jiluPeople(id) {
      this.form.recorderId = id;
    },
    // 获取所有人员
    getSuoYou() {
      getAll().then((response) => {
        this.executors = response.data;
        // 添加的人员
        this.executor = response.data;
        // 记录人
        this.recodeexecutor = response.data;
        // console.log( this.recodeexecutor)
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
    selectPeo(val) {
      this.queryParams.unitName = val.label;
      this.getList();
    },
    // 到访单位选中触发
    selectPeo1(val) {
      this.unitName = val.label;
      this.form.unitId = val.id;

      // 获取执行单位列表
      // getDept2().then((res) => {

      //   this.users = res.data;

      // });
      // this.queryParams.unitId = val.id;
      // this.queryParams.peopleName = "";
      // this.queryParams.unitName = val.label;

      // this.getZhuChi(this.queryParams.unitId);
      // this.getList();
    },
    /** 查询党委议教会列表 */
    getList() {
      this.loading = true;
      listParliament(this.queryParams).then((response) => {
        this.parliamentList = response.rows;
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
        unitName: null,
        unitId: null,
        startTime: null,
        endTime: null,
        createTime: null,
        updateTime: null,
        createName: null,
        createId: null,
        filePath: null,
        fileName: null,
        talkContent: null,
        recorder: null,
        recorderId: null,
        participants: null,
        participantsId: null,
        organizer: null,
        organizerId: null,
        talkTheme: null,
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
      this.getList();
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
      this.title = "添加党委议教会";
      getDept2().then((res) => {
        this.users = res.data;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getParliament(id).then((response) => {
        this.form = response.data;
        this.form.participants = this.form.participants.split(",").map(Number);
        this.open = true;
        this.title = "修改党委议教会";
      });
    },
    // 查看按钮操作
    handlelook(row) {
      this.reset();
      const id = row.id || this.ids;
      getParliament(id).then((response) => {
        this.form1 = response.data;
        this.form1.participants = this.form1.participants
          .split(",")
          .map(Number);
        this.form1.filePath =
          process.env.VUE_APP_BASE_API + this.form1.filePath;

        this.open1 = true;
        this.title1 = "查看党委议教会";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if (this.form.fileName) {
        this.form.filePath = this.form.fileName[0].url;
        this.form.fileName = this.form.fileName[0].name;
      }

      this.form.participants = this.form.participants.join(",");
      this.form.participantsId = this.form.participants;

      this.form.unitName = this.unitName;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateParliament(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addParliament(this.form).then((response) => {
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
          return delParliament(ids);
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
        .confirm("是否确认导出所有党委议教会数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportParliament(queryParams);
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
::v-deep .fujian .el-form-item__content {
  display: flex;
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
::v-deep .canjiaren .el-input__inner {
  width: 560px !important;
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
  width: 1010px;
  /* height: 80px !important; */
}
::v-deep .el-form-item__content .el-textarea .el-textarea__inner {
  /* width: 1000px; */
  height: 80px !important;
}
::v-deep .el-dialog__body {
  padding-left: 35px;
  padding-right: 0px !important;
  padding-bottom: 0px !important;
}
/*  ::v-deep .yuedu .el-dialog__body{
  padding-left: 126px !important;
}*/
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
::v-deep .el-input__suffix {
  right: 22px;
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
  /* color: white; */
}
::v-deep .vue-treeselect__single-value {
  color: white;
}
/* 所搜到访单位 */
::v-deep .waibudaofang .vue-treeselect__control {
  background-color: #00365f !important;
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
::v-deep .el-table .el-table__fixed-header-wrapper th {
  background-color: #004d86;
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
