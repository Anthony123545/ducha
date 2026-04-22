<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名称"
          clearable
          size="small"
          @input="handleQuery"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="阅读量" prop="readVolume">
        <el-input
          v-model="queryParams.readVolume"
          placeholder="请输入阅读量"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="页数" prop="theNumberPages">
        <el-input
          v-model="queryParams.theNumberPages"
          placeholder="请输入页数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="" prop="createUser">
        <el-input
          v-model="queryParams.createUser"
          placeholder="请输入上传人"
          clearable
          size="small"
          @input="handleQuery"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="上传人ID" prop="createUserid">
        <el-input
          v-model="queryParams.createUserid"
          placeholder="请输入上传人ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目录id" prop="directoryId">
        <el-input
          v-model="queryParams.directoryId"
          placeholder="请输入目录id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目录名称" prop="directoryName">
        <el-input
          v-model="queryParams.directoryName"
          placeholder="请输入目录名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件后缀" prop="parentDirId">
        <el-input
          v-model="queryParams.parentDirId"
          placeholder="请输入文件后缀"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件位置" prop="parentDirName">
        <el-input
          v-model="queryParams.parentDirName"
          placeholder="请输入文件位置"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件大小" prop="fileSize">
        <el-input
          v-model="queryParams.fileSize"
          placeholder="请输入文件大小"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item>
        <!-- <el-button type="primary" icon="el-icon-search" size="btn" @click="handleQuery">搜索</el-button> -->
        <el-button icon="el-icon-refresh" size="btr" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['zbqk:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['zbqk:info:edit']"
        >修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['zbqk:info:remove']"
        >删除</el-button>
      </el-col>
       <el-col :span="1.5">
        <el-button
        style="width:88px;height:36px"
        icon="el-icon-upload2"
         size="read"
              type="text"
            @click="upload"
            >上传</el-button>
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="infoList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="80" />
      <el-table-column label="" align="center" width="90" >
          <template slot-scope="scope">
            <div style="display: flex; flex-warp: warp; margin-top: 10px">
              <div style="padding-right: 12vh; width: 40px; height: 52px" class="uploadPeople">
                <!-- 根据后缀名控制图片显示doc还是pdf形式 -->
                <img v-if="scope.row.parentDirId == '.pdf'" src="../../../images/PDF.png" alt />
                <img
                  v-else-if="
                    scope.row.parentDirId == '.doc' ||
                    scope.row.parentDirId == '.docx'
                  "
                  src="../../../images/doc.png"
                  alt
                />
                <img
                  v-else-if="
                    scope.row.parentDirId == '.ppt' ||
                    scope.row.parentDirId == '.pptx'
                  "
                  src="../../../images/ppt.png"
                  alt
                />
              </div>
            </div>
          </template>
        </el-table-column>
      <!-- <el-table-column label="主键ID" align="center" prop="id" /> -->
      <el-table-column label="文件名称" align="center" prop="fileName" />
      <!-- <el-table-column label="阅读量" align="center" prop="readVolume" /> -->
      <!-- <el-table-column label="页数" align="center" prop="theNumberPages" /> -->
      <el-table-column label="上传人" align="center" prop="createUser" />
      <!-- <el-table-column label="上传人ID" align="center" prop="createUserid" />
      <el-table-column label="目录id" align="center" prop="directoryId" /> -->
      <!-- <el-table-column label="目录名称" align="center" prop="directoryName" />
      <el-table-column label="文件后缀" align="center" prop="parentDirId" /> -->
      <!-- <el-table-column label="文件位置" align="center" prop="parentDirName" />
      <el-table-column label="文件大小" align="center" prop="fileSize" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
            <el-button
              size="read"
              type="text"
              @click.native="ViewRead(scope.row)"
            >
              <span>
                <!-- <iframe :src="'http://192.168.5.177:8080'+scope.row.parentDirName" target="_blank">阅读</iframe> -->
                <!-- :href="'http://192.168.2.250:8089' + scope.row.parentDirName" -->
                <a target="_blank">阅读</a>
              </span>
            </el-button>
            <el-button size="btu"
            type="text">
              <span class="chakan1">
                <a
                  :href="downurl + scope.row.parentDirName"
                  :download="scope.row.fileName"
                  >下载</a
                >
              </span>
            </el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['zbqk:info:edit']"
          >修改</el-button> -->
          <el-button
            size="dels" type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['zbqk:info:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改战备情况对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文件名称" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入文件名称" />
        </el-form-item>
        <el-form-item label="阅读量" prop="readVolume">
          <el-input v-model="form.readVolume" placeholder="请输入阅读量" />
        </el-form-item>
        <el-form-item label="页数" prop="theNumberPages">
          <el-input v-model="form.theNumberPages" placeholder="请输入页数" />
        </el-form-item>
        <el-form-item label="上传人" prop="createUser">
          <el-input v-model="form.createUser" placeholder="请输入上传人" />
        </el-form-item>
        <el-form-item label="上传人ID" prop="createUserid">
          <el-input v-model="form.createUserid" placeholder="请输入上传人ID" />
        </el-form-item>
        <el-form-item label="目录id" prop="directoryId">
          <el-input v-model="form.directoryId" placeholder="请输入目录id" />
        </el-form-item>
        <el-form-item label="目录名称" prop="directoryName">
          <el-input v-model="form.directoryName" placeholder="请输入目录名称" />
        </el-form-item>
        <el-form-item label="文件后缀" prop="parentDirId">
          <el-input v-model="form.parentDirId" placeholder="请输入文件后缀" />
        </el-form-item>
        <el-form-item label="文件位置" prop="parentDirName">
          <el-input v-model="form.parentDirName" placeholder="请输入文件位置" />
        </el-form-item>
        <el-form-item label="文件大小" prop="fileSize">
          <el-input v-model="form.fileSize" placeholder="请输入文件大小" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
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
          <!-- </div> -->
        </template>
      </el-dialog>
            <!-- 上传文件弹框 -->
      <div class="big">
        <el-dialog
          style="color: pink"
          :title="title"
          :visible.sync="uploadopen"
          width="520px"
          append-to-body
          class="el-dialog__header"
          :close-on-click-modal="false"
        >
          <el-form
            ref="form"
            :model="form"
            :rules="rules"
            label-width="130px"
            :inline="true"
          >
            <el-form-item style=" margin-top: 10px">
              <FileUpload v-model="moreFile" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="UploadsubmitForm">确 定</el-button>
            <el-button @click="uploadopen = false" size="btn">取 消</el-button>
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
import {
  listInfo,
  getInfo,
  delInfo,
  addInfo,
  updateInfo,
  exportInfo,
  addFileinfo,
  updateFileinfo,
} from "@/api/combatduty/info";

export default {
  name: "Info",
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
      // 战备情况表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
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
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      previewDialog: false,
      open33: false,
      title3: "预览文件",
      // 下载文件路径
      downurl: process.env.VUE_APP_BASE_API,
      pageRotate: 0,
      gopage: true,
      previewDialog: false,
      pdfurl: "",
      pageNum: 1,
      pageTotalNum: 1,
      pageRotate: 0,
      // 加载进度
      loadedRatio: 0,
      curPageNum: 0,
      gopage: true,
      // 多文件上传列表
      moreFile: [],
      nunms: 0,
      // 点击右侧一级分类进去
      Ids: null,
      last: null,
      uploadopen: false,
      // 多文件上传列表
      moreFile: [],
    };
  },
  components: {
    PDF,
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询战备情况列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then((response) => {
        this.infoList = response.rows;
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
      this.title = "添加战备情况";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getInfo(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改战备情况";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then((response) => {
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
        .confirm('是否确认删除战备情况的数据项？')
        .then(function () {
          return delInfo(ids);
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
        .confirm("是否确认导出所有战备情况数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportInfo(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
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
        this.previewDialog = true;
        this.pdfurl = process.env.VUE_APP_BASE_API + row.parentDirName;
        console.log(this.pdfurl, row.parentDirName, 123);
        this.pageNum = 1;
      } else {
        this.$message.error("只能阅读Word跟pdf文件");
      }
      // row.common = "1";
      // updateFileinfo(row).then((res) => {
      //   this.getList();
      // });
      // window.open("www.baidu.com","_self");
    },
    UploadsubmitForm() {
      // this.form.bdglFileInfos = this.moreFile;
      let bdglFileInfos = {};
      this.moreFile.forEach((item) => {
        bdglFileInfos = {
          fileSize: item.fileLength,
          parentDirName: item.url,
          parentDirId: item.houZUI,
          theNumberPages: item.pageCount,
          fileName: item.name,
        };
      });
      console.log(bdglFileInfos);
      if (bdglFileInfos.fileSize == "0.0KB") {
        this.$modal.msgSuccess("您好！上传文件不能为空文件。");
      } else {
        // 上传文件接口
        addFileinfo(bdglFileInfos).then((response) => {
          this.$modal.msgSuccess("上传成功");
          this.uploadopen = false;
          this.getList();
        });
      }
    },
    // 上传
    upload() {
      // this.getList()
      this.reset();
      this.uploadopen = true;
      this.title = "上传文件";
      this.moreFile = "";
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
  },
};
</script>
<style scoped>
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
  background-color: #13ce66;
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
.bg {
  background: #1d96ff;
  color: white;
}
/* 计划附件 */
::v-deep .el-upload__tip {
  margin-left: 2px;
}
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
::v-deep .vue-treeselect__placeholder {
  color: #bdbdbd4f !important;
}
::v-deep .el-table__row .el-table__cell {
  text-align: center !important;
}
::v-deep .el-input--small .el-input__inner {
  color: #ffffff;
}
::v-deep .el-table_2_column_9{
  text-align: center;
}
::v-deep .el-dialog{
  background-color: #004d86;
}
::v-deep .el-dialog__header{
  border-bottom: 1px solid #fff;
}
::v-deep .el-dialog__title{
  color: #fff;
}
::v-deep .el-upload__tip{
  color: #fff;
}
::v-deep .el-link--inner{
  color: #fff;
}
::v-deep .el-form-item .el-input__inner {
  width: 200px;
  height: 36px;
  background: transparent;
  color: #fff !important;
  border: 1px solid #fff !important;
}
::v-deep .docx p, p.docx_Normal{
  line-height: 2.00;
}
</style>
