<template>
  <div class="flexlayout guizhangzhidu">
    <!-- 文件区域 左边-->
    <div class="tablesfile">
      <div class="jichu">文件</div>
      <el-table
        v-loading="loading1"
        :data="fileinfoList"
        @selection-change="handleSelectionChange"
        :header-cell-style="{ background: '#003C69', color: 'white' }"
      >
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column
          label="文件"
          align="center"
          width="650"
          prop="directory"
          class="fileNAME"
        >
          <template slot-scope="scope">
            <div style="display: flex; flex-warp: warp; margin-top: 10px">
              <div
                style="padding-right: 12vh; width: 40px; height: 52px"
                class="uploadPeople"
              >
                <!-- 根据后缀名控制图片显示doc还是pdf形式 -->
                <img
                  v-if="scope.row.parentDirId == '.pdf'"
                  src="../../../images/PDF.png"
                  alt=""
                />
                <img
                  v-else-if="
                    scope.row.parentDirId == '.doc' ||
                    scope.row.parentDirId == '.docx'
                  "
                  src="../../../images/doc.png"
                  alt=""
                />
                <img
                  v-else-if="
                    scope.row.parentDirId == '.ppt' ||
                    scope.row.parentDirId == '.pptx'
                  "
                  src="../../../images/ppt.png"
                  alt=""
                />
                <img
                  v-else-if="
                    scope.row.parentDirId == '.mp4'
                  "
                  src="../../../images/MP4.png"
                  alt=""
                />
              </div>
              <!-- 文件名称 fileName -->
              <div>
                <div style="text-align: start; width: 100%">
                  {{ scope.row.fileName }}
                </div>
                <!-- 上传人 createUser -->
                <!-- 页数 theNumberPages-->
                <!-- 时间 createTime -->
                <!-- 阅读量 readVolume -->
                <div style="display: flex">
                  <!-- <span
                    style="
                      font-size: 14px;
                      color: rgba(204, 204, 204, 1);

                      width: 100px;
                      margin-left: 30px;
                      display: block;
                      text-align: start;
                    "
                    >上传人：{{ scope.row.createUser }}</span
                  > -->

                  <span
                    style="
                      font-size: 14px;
                      color: rgba(204, 204, 204, 1);
                      width: 180px;
                      display: block;
                      text-align: start;
                    "
                    >时间：{{ scope.row.createTime }}</span
                  >
                  <span
                    style="
                      font-size: 14px;
                      color: rgba(204, 204, 204, 1);
                      width: 80px;
                      display: block;
                    "
                    >阅读量：{{ scope.row.readVolume }}</span
                  >
                  <!-- <span
                    style="
                      font-size: 14px;
                      color: rgba(204, 204, 204, 1);

                      width: 80px;
                      display: block;
                      text-align: start;
                    "
                    >页数：{{ scope.row.theNumberPages }}</span
                  > -->
                  <!-- <span
                    style="
                      font-size: 14px;
                      color: rgba(204, 204, 204, 1);
                      width: 100px;
                      display: block;
                    "
                    >分类：{{ scope.row.directoryName }}</span
                  > -->
                </div>
              </div>
            </div>
          </template>
        </el-table-column>
        <!-- <el-table-column
          label="文件"
          align="center"
          class-name="small-padding fixed-width"
          label-width="30px"
        >
          <template slot-scope="scope">
            {{scope.row.directoryName}}
          </template>
        </el-table-column> -->
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          label-width="180px"
        >
          <template slot-scope="scope">
            <el-button
              size="read"
              type="text"
              @click.native="ViewRead(scope.row)"
              v-if="scope.row.parentDirId != '.mp4'"
            >
              <span>
                <!-- <iframe :src="'http://192.168.5.177:8080'+scope.row.parentDirName" target="_blank">阅读</iframe> -->
                <!-- :href="'http://192.168.2.250:8089' + scope.row.parentDirName" -->
                <a target="_blank">阅读</a>
              </span>
            </el-button>
            <el-button
              size="read"
              type="text"
              @click.native="vivdeo(scope.row)"
              v-if="scope.row.parentDirId == '.mp4'"
            >
              <span>
                <a target="_blank">播放</a>
              </span>
            </el-button>
            <el-button size="read" type="text" @click="handleupLoad(scope.row)">
              <span class="chakan1">
                <a
                  :href="downurl + scope.row.parentDirName"
                  :download="scope.row.fileName"
                  >下载</a
                >
              </span>
            </el-button>
            <el-button size="dels" type="text" @click="handledels(scope.row)">
              <span> 删除 </span>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum"
        :page-sizes="[2, 5, 8, 10]"
        :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
      <!-- <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      /> -->
    </div>
    <!-- 右边 -->
    <div class="rights">
      <!-- 目录 -->
      <div class="catalog">
        <div class="jichu">
          目录
          <el-button
            class="uploadfilesbtn"
            :class="addbtn ? 'margins' : ''"
            @click="upload"
            v-if="this.level == 1"
            >上传文件</el-button
          >
          <el-button class="turnback" @click="turnback" v-if="this.level != 1"
            >返回上级</el-button
          >
        </div>
        <el-table
          v-loading="loading"
          :data="teacherList"
          @selection-change="handleSelectionChange"
          :header-cell-style="{ background: '#003C69', color: 'white' }"
        >
          <el-table-column label="序号" type="index" width="50">
          </el-table-column>
          <el-table-column label="目录" align="center" prop="directory">
            <template slot-scope="scope">
              <div
                @click="mulus(scope.row)"
                :class="scope.row.subclassification == 0 ? '' : 'undeline'"
                style="position: relative; cursor: pointer"
              >
                <img
                  v-if="scope.row.subclassification != 0"
                  src="../../../images/文件夹.png"
                  alt=""
                  style="
                    margin-right: 5px;
                    position: absolute;
                    left: 10px;
                    top: 2px;
                  "
                />
                <!-- 分类图片 -->
                <img
                  v-if="scope.row.subclassification == 0"
                  src="../../../images/分类icon.png"
                  alt=""
                  style="
                    margin-right: 5px;
                    position: absolute;
                    left: 10px;
                    top: 2px;
                  "
                />
                <span style="margin-left: 25px">
                  {{ scope.row.directory }}</span
                >
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="子分类"
            align="center"
            prop="subclassification"
          />
          <el-table-column label="文件" align="center" prop="documentsNumber" />
          <el-table-column
            label="创建时间"
            align="center"
            prop="createTime"
            width="200px"
          />
        </el-table>
        <el-pagination
          class="pagination"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange1"
          :current-page="queryParams1.pageNum"
          :page-sizes="[2, 5, 8, 10]"
          :page-size="queryParams1.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total1"
        >
        </el-pagination>
      </div>
      <!-- 上传文件弹框 -->
      <div class="big">
        <el-dialog
          style="color: pink"
          :title="title"
          :visible.sync="open"
          width="520px"
          append-to-body
          class="el-dialog__header"
          :close-on-click-modal="false"
          id="uploadfile"
        >
          <el-form
            ref="form"
            :model="form"
            :rules="rules"
            label-width="130px"
            :inline="true"
            style="margin-left: 50px"
          >
            <el-form-item
              label-width="40px"
              prop="directory"
              class="change_plan_type"
              label="目录"
            >
              <treeselect
                v-model="form.directoryName"
                :options="users"
                :value="11"
                class="threeselects"
                @select="selectPeo1"
                placeholder="请选择目录"
              >
                <div slot="value-label" slot-scope="{ node }">
                  {{ node.raw.directory }}
                </div>
                <label slot="option-label" slot-scope="{ node }">
                  {{ node.raw.directory }}
                </label>
              </treeselect>
            </el-form-item>
            <el-form-item style="margin-left: 30px; margin-top: 30px">
              <FileUpload v-model="moreFile" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel" size="btn">取 消</el-button>
          </div>
        </el-dialog>
      </div>
      <!-- 排行 -->
      <div class="Ranking">
        <div class="jichu">排行</div>
        <table style="color: white">
          <thead>
            <!-- <tr style="font-size:14px;height:25px">
              <th>序号</th>
              <th>分类</th>
              <th>文件</th>
              <th>阅读量</th>
            </tr> -->
          </thead>
          <tbody>
            <tr
              class="borderBottom"
              v-for="(item, index) in Ranking"
              :key="item.id"
            >
              <!-- 作战类 documentCategory -->
              <!-- 上月阅读量 readnumber1 -->
              <!-- 本月阅读量 readnumber2 -->
              <!-- 排序 -->
              <td class="numbers">{{ index + 1 }}</td>
              <!-- 作战类类别 -->
              <td class="find">{{ item.directoryName }}</td>
              <!-- 文件名 -->
              <td class="thisweek" style="position: relative">
                <!-- <img src="../../../images/upRow.png" alt="" style="position:absolute;top:18px;right:158px" > -->
                <!-- <img src="../../../images/downRow.png" alt=""  style="position:absolute;top:17px;right:158px"> -->

                <span style="margin-left: 5px">{{ item.fileName }}</span>
              </td>
              <!-- 阅读量 -->
              <td class="readnumber">
                阅读量
                <span
                  style="margin-left: 5px"
                  :class="index < 3 ? 'green' : 'red'"
                  >{{ item.readVolume }}</span
                >
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- 添加预览doc文件弹框 -->
      <el-dialog
        style="color: pink"
        :title="title3"
        :visible.sync="open33"
        width="1200px"
        append-to-body
        id="fileinfodocx"
        class="el-dialog__header"
        :close-on-click-modal="false"
      >
        <div ref="file"></div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm3">关 闭</el-button>
          <!-- <el-button @click="open33 = false" size="" class="aa1">
            取 消</el-button
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
          <!-- <div style="margin-bottom:20px"> -->
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
            <!-- <el-button :theme="'default'" type="submit" :title="'顺时针旋转'" @click.native="clock" > 顺时针旋转</el-button>
            <el-button :theme="'default'" type="submit" :title="'逆时针旋转'" @click.native="counterClock" > 逆时针旋转</el-button>
           <el-button :theme="'default'" type="submit" :title="'打印'" @click.native="pdfPrintAll"> 打印</el-button> -->
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
          <!-- </div> -->
          <!-- <div class="el-dialog__footer"> -->
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="previewDialog = false"
              >关 闭</el-button
            >
            <!-- <el-button @click="previewDialog = false" size="" class="aa1"
              >取 消</el-button
            > -->
          </div>
        </template>
      </el-dialog>
      <!-- 视频播放 -->
      <el-dialog
        :title="title"
        :visible.sync="vivdeos"
        width="850px"
        append-to-body
        custom-class="video"
        :close-on-click-modal="false"
        @close="vueVideo"
      >
        <video width="700" height="500" controls ref="vueref">
          <source :src="downurl + videoUrl" type="video/mp4" />
        </video>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="videoi">关 闭</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import PDF from "vue-pdf";
const docx = require("docx-preview");
window.JSZip = require("jszip");
// import {
//   // 获取文件列表接口
//   listFileinfo,
//   // 修改文件列表接口
//   getFileinfo,
//   // 删除文件列表接口
//   delFileinfo,
//   // 上传文件
//   addFileinfo,
//   // 修改文件列表接口
//   updateFileinfo,
//   // 导出文件列表接口
//   exportFileinfo,
//   // 获取排行接口
//   rankList,
// } from "@/api/regulations/fileinfo";
import { listTeacher, listTeachers } from "@/api/thought/teacher";
import {
  listTeacherFile,
  getTeacherFile,
  delTeacherFile,
  addTeacherFile,
  updateTeacherFile,
  exportTeacherFile,
  listmulu,
  listread,
  rankList,
} from "@/api/thought/teacherFile";
import {
  // 查询目录列表接口
  listDirectoryinfo,
  // 添加目录列表接口
  addDirectoryinfo,
  // 删除目录列表接口
  delDirectoryinfo,
} from "@/api/regulations/directoryinfo";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getDept2, getAll } from "@/api/grassrootsregistration/bdglmeeting";
export default {
  name: "Fileinfo",

  data() {
    return {
      list: [
        {
          id: 1,
          file: "文件",
        },
        {
          id: 2,
          file: "文件",
        },
        {
          id: 3,
          file: "文件",
        },
      ],
      // 树形配置
      defaultProps: {
        children: "children",
        label: "id",
      },
      // 分页器1
      total: 0,
      // 分页器2
      total1: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 8,
        catalog: "",
        category: "",
        fileName: null,
        readVolume: null,
        theNumberPages: null,
        createUser: null,
        createUserid: null,
        directoryId: null,
        directoryName: null,
        parentDirId: null,
        parentDirName: null,
        fileSize: null,
      },
      queryParams1: {
        pageNum: 1,
        pageSize: 5,
        catalog: "",
        category: "",
        fileName: null,
        readVolume: null,
        theNumberPages: null,
        createUser: null,
        createUserid: null,
        directoryId: null,
        directoryName: null,
        parentDirId: null,
        parentDirName: null,
        fileSize: null,
      },
      // 上传文件
      open: false,
      // 新建分类
      open1: false,
      // 添加子分类
      open2: false,
      // 表单参数
      form: {},
      // 新建目录
      form1: {},
      // 新建子分类
      form2: {},
      // 规章制度文件记录表格数据
      fileinfoList: [],
      // 表单校验
      rules: {},
      // 目录
      directory: [],
      // 父节点id
      parentId: "",
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 子分类
      subclassification: [],
      // 刷新
      loading1: false,
      // 文件
      file: {},
      //文件类型判断
      fileType: 1,
      // 添加子分类名称
      title2: "",
      // 添加新建分类名称
      title1: "",
      // 添加上传文件名称
      title: "",
      // 排行数据
      Ranking: [],
      // 添加按钮
      addbtn: true,
      // 虚拟字段
      fid: "",
      open33: false,
      title3: "预览文件",
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
      // 层级
      level: 1,
      teacherList: [],
      parentId: 0,
      // 返回上级父id
      parentIds: "",
      parentDirectory: "",
      // 添加按钮传值
      parentDirectorys: "",
      level: 1,
      levels: "",
      parentIdss: "",
      dellevel: "",
      // 子分类数量
      subclassification: "",
      // 父目录id
      fatherparentId: "",
      users: [],

      dir: "",
      // 多文件上传
      moreFile: [],
      // 视频播放
      vivdeos: false,
      videoUrl: null,
    };
  },
  created() {
    // 获取执行单位列表
    listmulu({ parentId: 0 }).then((res) => {
      this.users = res.rows;
    });

    this.getList();
    // 获取目录接口
    this.ListDirectoryinfo();
    // 获取排行目录接口
    // rankList().then((res) => {
    //   this.Ranking = res.data;

    // });
    this.RankList();
    $(".app-wrapper").addClass("openSidebar");

    $(".app-wrapper").removeClass("hideSidebar");
    $(".sidebar-logo-container").addClass("collapse");
  },
  components: {
    PDF,
    Treeselect,
  },
  methods: {
    vueVideo() {
      if (this.$refs.vueref.paused == false) {
        this.$refs.vueref.pause();
      }
    },
    videoi() {
      this.videoUrl = null;
      this.vivdeos = false;
    },
    // 视频播放
    vivdeo(row) {
      this.videoUrl = row.parentDirName;
      this.vivdeos = true;
      this.title = "播放视频";
    },
    selectPeo1(row) {
      this.dir = row.directory;
      this.form.directoryId = row.id;
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
    // 预览弹框
    submitForm3() {
      this.open33 = false;
    },
    RankList() {
      rankList().then((res) => {
        this.Ranking = res.data;
      });
    },
    handleSizeChange(val) {},
    handleCurrentChange(val) {
      this.queryParams.pageNum = val;
      listTeacherFile({
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
      }).then((res) => {
        this.fileinfoList = res.rows;
        this.total = res.total;
      });
    },
    handleCurrentChange1(val) {
      this.queryParams1.pageNum = val;
      // this.queryParams.common = "1";

      // if (this.parentId) {
      //   this.queryParams1.common = null;
      // } else {
      //   this.queryParams1.common = "1";
      // }

      if (this.level == 1) {
        listTeacher({
          level: this.level,

          pageNum: this.queryParams1.pageNum,
          pageSize: this.queryParams1.pageSize,
        }).then((res) => {
          this.teacherList = res.rows;

          this.teacherList.map((item) => {
            if (item.subclassification != 0) {
              // this.queryParams1.pageNum=1
              this.addbtn = true;
            }
          });
          // this.addbtn=true
          this.total1 = res.total;
          this.loading = false;
        });
      } else {
        listTeacher({
          level: this.level,

          parentDirectory: this.parentDirectory,
          pageNum: this.queryParams1.pageNum,
          pageSize: this.queryParams1.pageSize,
        }).then((res) => {
          this.teacherList = res.rows;

          this.teacherList.map((item) => {
            if (item.subclassification != 0) {
              // this.queryParams1.pageNum=1
              this.addbtn = true;
            }
          });
          // this.addbtn=true
          this.total1 = res.total;
          this.loading = false;
        });
      }
    },
    // 文件删除按钮操作
    handledels(row) {
      this.$modal
        .confirm("是否确认删除该文件")
        .then(function () {
          return delTeacherFile(row.id);
        })
        .then(() => {
          this.ListDirectoryinfo();
          this.getList();
          this.RankList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    // 子分类id
    soncategory(id) {
      this.form.directoryId = id;
    },
    // 获取目录列表
    ListDirectoryinfo() {
      this.loading = true;
      this.queryParams1.parentId = 0;
      if (this.level != 1) {
        listTeacher({
          parentDirectory: this.parentDirectory,
          level: this.level,
          pageNum: this.queryParams1.pageNum,
          pageSize: this.queryParams1.pageSize,
        }).then((response) => {
          this.teacherList = response.rows;
          this.total1 = response.total;
          this.loading = false;
        });
      } else {
        listTeacher(this.queryParams1).then((response) => {
          this.teacherList = response.rows;
          this.total1 = response.total;
          this.loading = false;
        });
      }
    },
    // 点击目录
    mulus(row) {
      this.parentId = row.id;
      this.parentDirectory = row.parentDirectory;
      this.subclassification = row.subclassification;
      // 文件
      listTeacherFile({
        directoryId: row.id,
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
      }).then((res) => {
        this.fileinfoList = res.rows;
        this.total = res.total;
      });
      if (row.subclassification == 0) {
        return;
      } else {
        this.level += 1;
        this.fatherparentId = row.id;

        this.loading = true;
        this.getLists();
      }
    },
    getLists() {
      listTeacher({
        parentId: this.parentId,
        pageNum: this.queryParams1.pageNum,
        pageSize: this.queryParams1.pageSize,
      }).then((response) => {
        this.teacherList = response.rows;
        this.parentIds = this.teacherList[0].parentId;
        this.total1 = response.total;
        this.loading = false;
      });
    },
    // 返回上级
    turnback(row) {
      if (this.level == 1) {
        this.level == 1;
        return;
      } else {
        this.level -= 1;
      }
      // this.loading=true

      if (this.level == 1) {
        listTeachers({
          level: this.level,
          pageNum: this.queryParams1.pageNum,
          pageSize: this.queryParams1.pageSize,
        }).then((res) => {
          this.teacherList = res.rows;

          this.fatherparentId = res.rows[0].parentId;
          this.total1 = res.total;
          this.loading = false;
        });
      } else if (this.level > 1) {
        listTeachers({
          parentDirectory: this.parentDirectory,
          level: this.level,
          pageNum: this.queryParams1.pageNum,
          pageSize: this.queryParams1.pageSize,
          parentId: this.fatherparentId,
        }).then((res) => {
          this.teacherList = res.rows;
          this.fatherparentId = res.rows[0].parentId;

          this.total1 = res.total;
          this.loading = false;
        });
      }
    },
    // 选中父目录之后
    // chooseDirect(id) {
    //   this.subclassification = [];
    //   this.parentId = id;
    //   // 获取到父目录id了
    //   listDirectoryinfo({ parentId: this.parentId }).then((res) => {
    //     this.subclassification = res.rows;
    //   });
    // },
    /** 查询规章制度文件记录列表 */
    getList() {
      this.loading = true;
      listTeacherFile(this.queryParams).then((response) => {
        this.fileinfoList = response.rows;
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
        fileName: null,
        readVolume: null,
        theNumberPages: null,
        createUser: null,
        createUserid: null,
        createTime: null,
        directoryId: null,
        directoryName: null,
        parentDirId: null,
        parentDirName: null,
        fileSize: null,
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
      this.title = "添加规章制度文件记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getFileinfo(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改规章制度文件记录";
      });
    },
    /** 上传文件提交按钮 */
    submitForm() {
      this.form.directoryName = this.dir;

      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.form.thoughtTeacherFiles = this.moreFile;

          this.form.thoughtTeacherFiles.forEach((item) => {
            // 文件大小
            item.fileSize = item.fileLength;
            // 文件路径
            item.parentDirName = item.url;
            // 文件后缀
            item.parentDirId = item.houZUI;
            // 页数
            item.theNumberPages = item.pageCount;
            // 文件名称
            item.fileName = item.name;
          });

          //输出结果
          // 上传文件接口
          addTeacherFile(this.form).then((response) => {
            // this.ListDirectoryinfo();
            this.$modal.msgSuccess("上传成功");
            this.open = false;
            this.getList();
            this.RankList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id;
      this.parentId = row.parentId;

      this.$modal
        .confirm("是否确认删除该文件？")
        .then(function () {
          return delDirectoryinfo(ids);
        })
        .then(() => {
          this.getList();
        })
        .then(() => {
          // this.loading = true;
          // listTeacherFile(this.queryParams).then((response) => {
          //   this.fileinfoList = response.rows;
          //   this.total = response.total;
          //   this.loading = false;
          // });
          listDirectoryinfo({
            parentId: this.parentId,
            pageNum: this.queryParams1.pageNum,
            pageSize: this.queryParams1.pageSize,
            //  common: this.queryParams1.common,
          }).then((res) => {
            this.directory = res.rows;
            this.total1 = res.total;

            if (this.parentId == 0) {
              this.addbtn = true;
              this.RankList();

              this.loading = false;
              this.loading1 = false;
              this.$modal.msgSuccess("删除成功");
            } else {
              this.addbtn = false;
              this.RankList();

              this.loading = false;
              this.loading1 = false;
              this.$modal.msgSuccess("删除成功");
            }
          });
          this.getList();
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有规章制度文件记录数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportFileinfo(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    // 上传
    upload() {
      // this.getList()
      this.reset();
      this.open = true;
      this.title = "上传文件";
      this.ListDirectoryinfo();
    },
    // 目录管理
    mange() {
      this.open1 = true;
      this.title1 = "新建分类";
    },
    // 添加
    ViewProgress(row) {
      // 传入父亲目录id
      this.form2.parentId = row.id;

      this.open2 = true;
      this.title2 = "请输入子分类名称";
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open1 = false;
      this.open2 = false;
      this.comstatus = 0;
      this.reset();
    },
    // 阅读按钮操作
    ViewRead(row) {
      if (row.parentDirId == ".docx" || row.parentDirId == ".doc") {
        this.open33 = true;
        axios({
          method: "get",
          responseType: "blob", // 设置响应文件格式
          url: process.env.VUE_APP_BASE_API + row.parentDirName,
        }).then(({ data }) => {
          $(".docx").attr("id", "printArea");

          docx.renderAsync(data, this.$refs.file); // 渲染到页面预览
        });
      } else if (row.parentDirId == ".pdf") {
        this.pageNum = 1;
        this.previewDialog = true;
        this.pdfurl = process.env.VUE_APP_BASE_API + row.parentDirName;
      } else {
        this.$message.error("只支持Word跟pdt文件！");
      }
      listread(row).then((res) => {
        this.getList();
        rankList().then((res) => {
          this.Ranking = res.data;
        });
      });
      // window.open("www.baidu.com","_self");
    },
    // 下载按钮操作
    handleupLoad() {},
  },
};
</script>

<style scoped>
/* ::v-deep .el-dialog__body{
  overflow: auto !important;
  height: 600px !important;
} */
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
.red {
  color: #ff4949;
}
.green {
  color: #13ce66;
}
::v-deep .upload-file-list .el-upload-list__item {
  margin-left: -30px !important;
}
::v-deep .vue-treeselect__placeholder {
  color: #bdbdbd4f !important;
}
::v-deep .el-table__row .el-table__cell {
  text-align: center !important;
}
.jichu {
  color: white;
  width: 100%;
  height: 40px;
  line-height: 40px;
  border-left: 4px solid rgba(29, 150, 255, 1);
  text-indent: 1em;
}
/* 文件表格 */
.tablesfile {
  /* height: 87vh; */
  background: #00365f;
  width: calc(100% - 695px);
  position: absolute;
  left: 10px;
  top: 10px;
  bottom: 10px;
}
/* 弹性布局 */
.flexlayout {
  display: flex;
  justify-content: space-around;
  align-items: flex-start;
  height: 100%;
  display: flex;
  width: 100%;
  margin: 20px 20px 20px;
}
/* 目录 */
.catalog {
  width: 100%;
  height: 50%;
  position: relative;
  background-color: #00365f;
}
/* 弹框按钮 */
.dialog-footer .el-button {
  width: 60px;
  height: 30px;
}
/* 分页器样式 */
.pagination {
  position: absolute;
  bottom: 20px;
  right: 10px;
}
/* 排行 */
.Ranking {
  width: 100%;
  height: 48.8%;
  right: 12%;
  top: 45%;
  background-color: #00365f;
  margin-top: 10px;
  /* margin-bottom: 20px; */
}
/* 右边栏样式 */
.rights {
  /* height: 100vh; */
  /* height: 85vh; */
  width: 665px;
  float: right;
  position: absolute;
  top: 10px;
  right: 10px;
  bottom: 10px;
  margin-left: 10px;
}

/* 阅读 */
.el-button--read {
  color: #ffffff;
  width: 50px;
  height: 30px;
  background-color: #1d96ff;
  border-radius: 4px;
  font-size: 14px;
  line-height: 5px;
  margin-right: 10px;
}
/* 阅读按钮1样式 */
.el-button--reads {
  color: #ffffff;
  width: 50px;
  height: 30px;
  background-color: #1d96ff;
  border-radius: 4px;
  font-size: 14px;
  /* background-image: url("../../../images/uploads.png"); */
  background-repeat: no-repeat;
  background-position: 8px 6px;
  /* text-align: center; */
  line-height: 30px;
  margin-right: 10px;
}
/* 删除按钮样式 */
.el-button--dels {
  color: #ffffff;
  width: 50px;
  height: 30px;
  background-color: #ff4949;
  border-radius: 4px;
  font-size: 14px;
  background-repeat: no-repeat;
  background-position: 8px 6px;
  /* text-align: center; */
  line-height: 5px;
  margin-right: 10px;
}
/* 查看按钮样式 */
.el-button--reads .chakan {
  margin-left: 7px;
}
/* 阅读按钮样式 */
.el-button--reads .chakan1 {
  padding-top: 2px;
  /* margin-left: 7px; */
}
/* 表格 */
.tdfirst {
  width: 60px;
  text-align: center;
}
.tdsecound {
  width: 880px;
}
.tdthird {
  width: 70px;
}
.tdfourth {
  width: 70px;
}
tr {
  width: 100%;
  height: 50px;
}
.lefttable tr:nth-child(even) {
  background-color: #00365f;
}
.lefttable tr:nth-child(odd) {
  background-color: rgb(0, 60, 105);
}
/* 表格 */
.numbers {
  width: 50px;
  text-align: center;
  /* background: pink; */
  /* margin-left: 10px; */
}
.find {
  /* background-color: skyblue; */
  font-size: 14px;
  width: 200px;
  text-align: center;
}
.readnumber {
  font-size: 14px;
  width: 220px;
  text-align: center;
}
.thisweek {
  font-size: 14px;
  width: 210px;
  text-align: center;
}
.borderBottom {
  width: 100%;
  height: 50px;
  border-bottom: 1px solid #718a9dff;
}
table {
  border-collapse: collapse;
  width: 100%;
}
/* 左边表格 */
.lefttable tr {
  width: 100%;
  height: 60px;
  background-color: pink;
}
/* 上传文件 */
.uploadfilesbtn {
  width: 80px;
  height: 33px;
  line-height: 33px;
  background-color: #1d96ff;
  color: white;
  border-radius: 4px;
  font-size: 14px;
  text-align: center;
}
.margins {
  margin-left: 490px;
}
/* 返回目录按钮 */
.turnback {
  width: 86px;
  height: 33px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  line-height: 33px;

  text-align: center;
  margin-left: 490px;
  color: #ccc;
  background: #00365f;
}
/* 目录管理 */
.manage {
  /* display: inline-block; */
  width: 80px;
  height: 33px;
  line-height: 33px;
  background-color: #1d96ff;
  color: white;
  font-size: 14px;
  border-radius: 4px;
  margin-left: 10px;
}
.pagination-container {
  background: #fff;
  padding: 32px 16px;
}
.pagination-container.hidden {
  display: none;
}
/* 弹框样式修改 */
::v-deep .el-dialog {
  background-color: #004d86 !important;
}

::v-deep .el-dialog__title {
  color: white;
}
::v-deep .el-dialog__header {
  border-bottom: 1px solid #718a9dff;
}

::v-deep .select_file {
  margin-left: -30px;
}
/* 计划附件 */
::v-deep .el-upload__tip {
  color: white;
  position: absolute;
  top: -5px;
  width: 500px;
  left: 80px;
  color: white;
}
::v-deep .el-form-item__content {
  width: 230px;
}
/* 选取附件 */
::v-deep .el-upload-list__item {
  width: 250px;
}
::v-deep .el-upload-list__item a {
  color: white;
  text-indent: 0.5em;
}
::v-deep .el-upload-list__item a .el-link--inner {
  padding: 8px;
  line-height: 10px;
}
::v-deep .el-select-dropdown__item {
  color: white;
}
/* 输入框背景 */
::v-deep .el-input__inner {
  background-color: #004d86;
  border: 1px solid white !important;
  /* width: 270px; */
}
::v-deep .filename .el-input__inner {
  width: 270px;
  color: white;
}
::v-deep .el-select-dropdown__wrap {
  background-color: white !important;
}
.el-select-dropdown__item {
  text-indent: 1em;
  color: white;
  border: none !important;
}
::v-deep .el-select-dropdown__wrap .el-scrollbar__view {
  background-color: white !important;
}
::v-deep .pagination-container {
  display: block !important;
}

/* 添加按钮 */
.el-button--add {
  width: 55px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #13ce66;
}
/* 删除按钮 */
.el-button--del {
  width: 46px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #ff4949;
}
/* 下划线 */
.undeline {
  /* text-decoration: underline; */
  /* text-align: start; */
  color: #00f0ff;
  cursor: pointer;
}
/* 序号居中 */
::v-deep .el-table_1_column_1,
.el-table_3_column_9 {
  text-align: center;
}
::v-deep .el-table_2_column_4 {
  text-align: center;
}
/* 导入按钮 */
.imports {
  color: skyblue;
  display: inline-block;
  margin-top: 30px;
  margin-right: 150px;
  /* padding-top: 10px; */
}
.is-ready li {
  width: 300px !important;
}
.cursor {
  cursor: pointer;
  color: #409eff;
  font-size: 16px;
}
* {
  padding: 0;
  margin: 0;
}
html,
body,
#app {
  height: 100%;
  margin: 0;
  padding: 0;
}
/* .el-table td.el-table__cell div {
  margin: auto;
} */
/* 下拉框样式 */
::v-deep
  .guizhangzhidu
  .el-select-dropdown
  .el-select-dropdown__wrap
  .el-scrollbar__view {
  background-color: transparent !important;
  text-indent: 1em !important;
}
::v-deep .guizhangzhidu .el-select-dropdown__list {
  background: white !important;
}
::v-deep .el-table_2_column_13 .cell {
  text-align: center !important;
}
.el-select-dropdown__item {
  color: black !important;
}
::v-deep .el-form-item__content .el-input__inner {
  color: white !important;
}
::v-deep .el-pagination {
  margin-top: 10px;
  position: absolute;
  right: 5px;
}
::v-deep .el-pager li.active {
  background-color: #1d96ff;
  color: white;
}
::v-deep .el-pager .number {
  background-color: #196299;
  color: white;
}
::v-deep .el-pagination .el-select .el-input .el-input__inner {
  background-color: #196299 !important ;
}
::v-deep .el-pagination__editor.el-input .el-input__inner {
  background-color: #196299 !important ;
}
::v-deep .btn-prev {
  background-color: transparent;
  color: white;
}
::v-deep .btn-next {
  background-color: transparent;
  color: white;
}
.pagination-container {
  background: #fff;
  padding: 32px 16px;
}
.pagination-container.hidden {
  display: none;
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
.aa1 {
  background-color: #1263aa !important;
  color: #fff !important;
}
::v-deep .el-dialog__footer .dialog-footer .el-button--btn {
  padding-top: 00px !important;
}
/* .userAgree{
  height: 600px;
  overflow: auto;
} */
/*定义滚动条宽高及背景，宽高分别对应横竖滚动条的尺寸*/
.el-dialog__body::-webkit-scrollbar {
  width: 14px;
  /* height: 2px !important; */
  background: #3d83b8;
}

/*定义滑块，内阴影及圆角*/
.el-dialog__body::-webkit-scrollbar-thumb {
  /* border-radius: 20px; */
  /* height: 1px;
  width: 3px; */
  background: rgba(144, 206, 252, 1);
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
::v-deep .docx {
  width: 100% !important;
}
/* 上传文件弹框样式 */
::v-deep #uploadfile .el-dialog__body {
  padding: 30px 62px !important;
}
::v-deep .el-upload-list__item {
  width: 350px;
}
::v-deep .el-pagination .el-pagination__sizes .el-select .el-input__inner {
  display: none !important;
}
::v-deep .el-input__suffix {
  display: none !important;
}

::v-deep .video .el-dialog__body {
  padding: 30px 78px;
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
