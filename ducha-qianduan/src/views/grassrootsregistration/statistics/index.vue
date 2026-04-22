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
        <el-input placeholder="请输入单位" v-model="queryParams.unitName" @input="handleQuery" style />
      </el-form-item>
      <!-- <el-form-item label="" prop="unitId">
        <el-input
          v-model="queryParams.unitId"
          placeholder="请输入单位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="" prop="shouldBeTo">
        <el-input
          v-model="queryParams.shouldBeTo"
          placeholder="请输入应到"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="" prop="actualArrival">
        <el-input
          v-model="queryParams.actualArrival"
          placeholder="请输入实到"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="" prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入单位名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item>
        <!-- <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >-->
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
          v-hasPermi="['grassrootsregistration:statistics:add']"
        >新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['grassrootsregistration:statistics:edit']"
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
          v-hasPermi="['grassrootsregistration:statistics:remove']"
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
          v-hasPermi="['grassrootsregistration:statistics:export']"
          >导出</el-button
      >-->
      <!-- </el-col> -->
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>-->
    </el-row>

    <el-table
      v-loading="loading"
      :data="statisticsList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50" align="center">
        <template scope="scope">
          <span>
            {{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
            }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="单位名称" align="center" prop="unitName" />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="时间" align="center" prop="time" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- <el-button
            size="btk"
            type="text"
            @click="handleChakan(scope.row)"
            v-hasPermi="['grassrootsregistration:bdgldiary:edit']"
            ><span class="chakan">查看</span></el-button
          >-->
          <el-button size="btc" type="text" @click.native="ViewRead(scope.row)">
            <span>
              <a target="_blank">预览</a>
            </span>
          </el-button>
          <el-button size="btxia" type="text">
            <span class="chakan1">
              <a :href="downurl + scope.row.filePath" :download="scope.row.fileName">下载</a>
            </span>
          </el-button>
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['grassrootsregistration:statistics:edit']"
          >修改</el-button>
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['grassrootsregistration:statistics:remove']"
          >删除</el-button>
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

    <!-- 添加或修改军事训练月统计班对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="450px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="jiben">基本信息</div>

      <el-form
        ref="form"
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
            placeholder="请选择单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="时间" prop="time">
          <el-date-picker
            clearable
            size="small"
            v-model="form.time"
            type="date"
            placeholder="选择时间"
            class="text"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
        <FileUpload v-model="fielList" :limit="1" @input="getFileList" />
        <!-- <el-form-item label="上传文件" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入单位名称" />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="menuRoleVisible"
      :title="title"
      append-to-body
      id="chakan"
      :close-on-click-modal="false"
    >
      <statistics v-if="menuRoleVisible" ref="menuRole" :message="wordInfo"></statistics>
    </el-dialog>

    <!-- 添加预览doc文件弹框 -->
    <el-dialog
      style="color: pink"
      :title="title3"
      :visible.sync="open33"
      width="850px"
      append-to-body
      id="fileinfodocxs"
      class="el-dialog__header"
      :close-on-click-modal="false"
    >
      <div ref="file"></div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="open33 = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import statistics from "@/components/look_word/statistics.vue";
import {
  listStatistics,
  getStatistics,
  delStatistics,
  addStatistics,
  updateStatistics,
  exportStatistics
} from "@/api/grassrootsregistration/statistics";
import { getDept } from "@/api/grassrootsregistration/bdgleven";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import axios from "axios";
import PDF from "vue-pdf";
const docx = require("docx-preview");
window.JSZip = require("jszip");

export default {
  name: "Statistics",
  components: {
    Treeselect,
    statistics
  },
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
      // 军事训练月统计班表格数据
      statisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitId: null,
        shouldBeTo: null,
        actualArrival: null,
        unitName: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 获取部门列表
      deptOptions: [],
      // 获取文件的数组
      fielList: [],
      open33: false,
      title3: "预览文件",
      // 下载文件路径
      downurl: process.env.VUE_APP_BASE_API
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    // 阅读按钮操作
    ViewRead(row) {
      if (
        row.actualArrival == ".doc" ||
        row.actualArrival == ".docx" ||
        row.actualArrival == ".pdf"
      ) {
        this.open33 = true;
        axios({
          method: "get",
          responseType: "blob", // 设置响应文件格式
          url: process.env.VUE_APP_BASE_API + row.filePath
        }).then(({ data }) => {
          $(".doc").attr("id", "printArea");

          docx.renderAsync(data, this.$refs.file); // 渲染到页面预览
        });
      } else {
        console.log(row.actualArrival);
        this.$modal.msgWarning("只能阅读word文档");
      }
    },
    // 选择单位触发
    selectPeo(val) {
      this.form.unitName = val.label;
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 查询军事训练月统计班列表 */
    getList() {
      this.loading = true;
      listStatistics(this.queryParams).then(response => {
        this.statisticsList = response.rows;
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
        shouldBeTo: null,
        actualArrival: null,
        unitName: null
      };
      this.fielList = [];
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加军事训练月统计表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getStatistics(id).then(response => {
        this.form = response.data;
        if(response.data.fileName != null){
          this.fielList = [
            { name: response.data.fileName, url: response.data.filePath }
          ];
        }else{
          this.fielList = [];
        }

        this.open = true;
        this.title = "修改军事训练月统计表";
      });
    },
    //查看按钮操作
    // handleChakan(row) {
    //   this.opens = false;
    //   const id = row.id || this.ids;
    //   getStatistics(id).then((response) => {
    //     this.wordInfo = response.data;

    //     this.title = "军事训练月统计报表";
    //     this.menuRoleVisible = true;
    //   });
    // },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStatistics(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if (this.fielList.length > 0) {
              this.form.fileName = this.fielList[0].name;
              this.form.filePath = this.fielList[0].url;
              this.form.actualArrival = this.fielList[0].houZUI;
            }
            addStatistics(this.form).then(response => {
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
        .confirm("是否确认删除该数据项？")
        .then(function() {
          return delStatistics(ids);
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
        .confirm("是否确认导出所有军事训练月统计表数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportStatistics(queryParams);
        })
        .then(response => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    getFileList(fileList) {
      console.log(fileList);
      if (fileList.length > 0) {
        this.form.fileName = fileList[0].name;
        this.form.filePath = fileList[0].url;
        this.form.actualArrival = fileList[0].houZUI;
      }else{
        this.form.fileName = null;
        this.form.filePath = null;
        this.form.actualArrival = null;
      }
    },
  }
};
</script>
<style scoped>
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
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
::v-deep .el-dialog .el-input__inner {
  width: 260px !important;
  height: 36px;
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
  width: 338px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  background-size: 100%;
  margin-bottom: 25px;
  margin-left: 0px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
::v-deep .el-dialog__body {
  margin: 10px 30px 20px 30px;
  padding-top: 20px !important;
  box-sizing: border-box;
}
.el-dialog__body .el-form-item {
  margin-left: -23px;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 0px 10px 25px 0px;
  margin-right: 54px;
}
::v-deep .el-input--small .el-input__inner {
  height: 36px;
  line-height: 36px;
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
::v-deep .vue-treeselect__control {
  width: 260px;
}
::v-deep .vue-treeselect__menu-container {
  width: 260px;
}
::v-deep .vue-treeselect__single-value {
  color: #fff;
}
::v-deep .upload-file {
  padding-left: 35px;
}
::v-deep .el-upload__tip {
  color: #fff;
}
::v-deep .el-icon-document {
  color: #fff;
}

::v-deep .docx-wrapper {
  background: none;
  padding: 0;
}
::v-deep .docx-wrapper > section.docx {
  box-shadow: none;
}
::v-deep .docx-wrapper section {
  padding: 0pt 90pt !important;
}
::v-deep .el-input__inner {
  /* width: 200px !important;
  height: 36px; */
  background: transparent;
  color: #fff;
  border-color: #fff !important;
}
::v-deep .el-button--btxia.is-active,
.el-button--btxia:active {
  background: #E6A23C;
  border-color: #E6A23C;
  color: #FFFFFF;
}

::v-deep .el-button--btxia:focus,
.el-button--btxia:hover {
  background: #E6A23C;
  border-color: #E6A23C;
  color: #FFFFFF;
}

::v-deep .el-button--btxia {
  width: 46px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  border-color: #E6A23C;
  background: #E6A23C;
  height: 29px;
}
</style>
