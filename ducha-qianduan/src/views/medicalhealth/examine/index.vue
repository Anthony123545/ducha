<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="" prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入单位"
          clearable
          size="small"
        />
      </el-form-item>
      <!-- <el-form-item label="" prop="peopleName">
        <el-input
          v-model="queryParams.peopleName"
          placeholder="请输入姓名"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="" prop="job">
        <el-input
          v-model="queryParams.job"
          placeholder="请输入工作岗位"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item label="" prop="classId">
        <el-select
          v-model="queryParams.classId"
          placeholder="请选择人员类别"
          clearable
        >
          <el-option
            v-for="(item, i) in renLie"
            :key="i"
            :label="item.dictLabel"
            :value="item.dictValue"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <input
        class="input"
        v-model.number="queryParams.minAge"
        placeholder="输⼊年龄段"
        type="number"
      />
      <span class="span">----</span>
      <input
        class="input1"
        v-model.number="queryParams.maxAge"
        placeholder="输⼊年龄段"
        type="number"
      />
      <el-form-item>
        <el-button type="primary" size="btn" @click="handleQuery"
          >搜索</el-button
        >
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
          v-hasPermi="['medicalhealth:examine:add']"
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
          v-hasPermi="['medicalhealth:examine:edit']"
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
          v-hasPermi="['medicalhealth:examine:remove']"
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
          v-hasPermi="['medicalhealth:examine:export']"
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
      :data="examineList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="姓名" align="center" prop="peopleName" />
      <el-table-column label="年龄" align="center" prop="age" />
      <el-table-column
        label="身份证号"
        align="center"
        prop="idMunber"
        width="170"
      />
      <el-table-column label="工作岗位" align="center" prop="job" />
      <el-table-column label="人员类别" align="center" prop="classId">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.personnel_type"
            :value="scope.row.classId"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="btp"
            type="text"
            @click="tianJia(scope.row)"
            v-hasPermi="['medicalhealth:examine:edit']"
            >添加体检信息</el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdates(scope.row)"
            v-hasPermi="['medicalhealth:examine:edit']"
            >查看</el-button
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

    <!-- 添加或修改人员体检信息对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="qq">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="单位" prop="unitId">
            <treeselect
              v-model="form.unitId"
              placeholder="请选择单位"
              :options="deptOptions"
              @select="shuxing"
            />
          </el-form-item>
          <el-form-item label="姓名" prop="peopleId">
            <el-select v-model="form.peopleId" placeholder="请选择姓名">
              <el-option
                class="input_xiala"
                v-for="(item, i) in renYuan"
                :key="i"
                :value="item.id"
                :label="item.name"
                @click.native="xingMing(item)"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="工作岗位" prop="job">
            <el-input v-model="form.job" placeholder="" />
          </el-form-item>
          <el-form-item label="年龄" prop="age">
            <el-input v-model="form.age" placeholder="" disabled />
          </el-form-item>
          <el-form-item label="身份证号" prop="idMunber">
            <el-input v-model="form.idMunber" placeholder="" disabled />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加体检信息 -->

    <el-dialog
      title="添加体检信息"
      :visible.sync="opene"
      width="980px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="ww">
        <el-form
          ref="form"
          :model="form"
          :rules="rules1"
          :inline="true"
          style="margin-left: 30px"
        >
          <el-form-item label="年份" prop="year">
            <el-input v-model="form.year" placeholder="请输入年份" />
          </el-form-item>
          <div class="xiangMu">
            <el-form-item label="项目" prop="month">
              <el-select v-model="form.month" placeholder="请选择项目">
                <el-option
                  v-for="dict in dict.type.project"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="jiben">附件</div>
          <el-form-item>
            <FileUpload v-model="fuWenList" />
          </el-form-item>
          <div class="jiben">体检信息</div>
          <el-form-item>
            <el-input
              v-model="form.contents"
              type="textarea"
              placeholder=""
              style="width: 870px"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormT">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看体检信息 -->

    <el-dialog
      :title="title"
      :visible.sync="openes"
      width="985px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        :inline="true"
        style="margin-left: 30px"
      >
        <div class="jiben">体检人员</div>
        <el-form-item
          label="单位"
          prop="yeardate"
          style="margin-right: 45px; margin-left: 17px"
        >
          <el-input v-model="form.unitName" disabled />
        </el-form-item>
        <el-form-item label="姓名" prop="yeardate" style="margin-right: 45px">
          <el-input v-model="form.peopleName" disabled />
        </el-form-item>
        <el-form-item label="工作岗位" prop="yeardate">
          <el-input v-model="form.job" disabled />
        </el-form-item>
        <div
          class="ee"
          v-for="(item, index) in form.examineDetails"
          :key="item.id"
        >
          <div class="jiben">
            体检信息
            <span style="margin-left: 226px">
              时间 ： <span>{{ item.createTime }}</span>
            </span>
            <el-button
              style="margin-left: 326px"
              size="btd"
              type="text"
              @click="shanchus(item, index)"
              >删除</el-button
            >
          </div>
          <el-form-item label="年份" prop="yeardate" style="margin-left: 17px">
            <el-input v-model="item.year" disabled />
          </el-form-item>
          <div class="xiangMu">
            <el-form-item label="项目" prop="type">
              <el-select v-model="item.month" placeholder="请选择项目" disabled>
                <el-option
                  v-for="dict in dict.type.project"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </div>

          <div
            style="
              margin-bottom: 20px;
              margin-left: 17px;
              display: flex;
              align-items: center;
            "
          >
            <span class="filename">{{ item.fileName }}</span>
            <a
              :href="url + item.filePath"
              :download="item.fileName"
              title="下载"
              style="color: white; margin-left: 10px"
              class="downloadfile"
              >下载附件</a
            >
          </div>
          <el-form-item style="margin-left: 17px">
            <el-input
              v-model="item.contents"
              type="textarea"
              placeholder=""
              disabled
              style="width: 839px"
            >
            </el-input>
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer" style="margin-right: 32px">
        <el-button @click="cancelss" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listExamine,
  getExamine,
  delExamine,
  addExamine,
  updateExamine,
  exportExamine,
  getDept,
  getZhuChiRen,
  details,
  delshanchu,
} from "@/api/medicalhealth/examine";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

// 富文本
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
import { quillEditor } from "vue-quill-editor";

// 查询字典
import { getDicts } from "@/api/system/dict/data";

export default {
  name: "Examine",
  components: {
    Treeselect,
    quillEditor,
  },
  dicts: ["personnel_type", "project"],
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
      // 人员体检信息表格数据
      examineList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示添加体检弹出层
      opene: false,
      // 是否显示查看体检弹出层
      openes: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitId: null,
        peopleId: null,
        job: null,
        classId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 表单校验
      rules1: {
        year: [{ required: true, message: "年份不能为空", trigger: "blur" }],
        month: [{ required: true, message: "项目不能为空", trigger: "change" }],
      },
      // 树形单位
      deptOptions: [],
      // 人员列表
      renYuan: [],
      // 人员类别
      renLie: [],
      // 附件列表
      fuWenList: [],
      idss: null,
      // 查看数据
      topView: [],
      url: process.env.VUE_APP_BASE_API,
    };
  },
  created() {
    this.getList();
    this.getRenLei();
  },
  methods: {
    // 删除
    shanchus(row, index) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除？")
        .then(function () {
          return delshanchu(ids);
        })
        .then(() => {
          this.form.examineDetails.splice(index, 1);
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    // 获取人员类别
    getRenLei() {
      // 字典查询数据
      this.getDicts("personnel_type").then((response) => {
        this.renLie = response.data;
      });
    },
    // 点击查看人员体检信息
    handleUpdates(row) {
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids;
      getExamine(id).then((response) => {
        this.form = response.data;
        this.openes = true;
        this.title = "查看人员体检信息";
      });
    },
    // 点击添加人员体检信息
    tianJia(row) {
      this.opene = true;
      this.idss = row.id;
      this.reset();
    },
    // 点击查看人员体检信息
    chaKan() {
      this.openes = true;
    },
    cancelss() {
      this.openes = false;
    },
    // 点击人员触发
    xingMing(data) {
      this.$set(this.form, "age", data.age);
      this.$set(this.form, "idMunber", data.idcard);
      this.classId = data.classId;
      this.form.peopleName = data.name;
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.deptOptions = response.data;
      });
    },
    /** 查询人员体检信息列表 */
    getList() {
      this.loading = true;
      listExamine(this.queryParams).then((response) => {
        this.examineList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.opene = false;
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        unitId: null,
        peopleId: null,
        job: null,
        classId: null,
      };
      this.fuWenList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.minAge = null;
      this.queryParams.maxAge = null;
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
      this.getTreeselect();
      this.reset();
      this.open = true;
      this.title = "添加人员体检信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids;
      getExamine(id).then((response) => {
        this.form = response.data;
        this.form.yeardate = null;
        this.form.checkInformation = null;
        this.form.type = null;
        // this.getRenYuan(this.form.unitId);
        this.opene = true;
        this.title = "新增人员体检信息";
      });
    },
    // 点击单位触发
    shuxing(data) {
      this.form.unitId = data.id;
      this.form.unitName = data.label;
      this.getRenYuan(this.form.unitId);
    },
    // 获取人员
    getRenYuan(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 体检信息提交
    submitFormT() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.fuWenList.length > 0) {
            this.form.fileName = this.fuWenList[0].name;
            this.form.filePath = this.fuWenList[0].url;
          }
          this.form.examineId = this.idss;
          details(this.form).then((response) => {
            this.$modal.msgSuccess("新增成功");
            this.opene = false;
            this.getList();
            this.reset();
          });
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      // this.$refs["form"].validate((valid) => {
      // if (valid) {
      if (this.form.id != null) {
        updateExamine(this.form).then((response) => {
          this.$modal.msgSuccess("修改成功");
          this.open = false;
          this.opene = false;
          this.getList();
        });
      } else {
        addExamine(this.form).then((response) => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      }
      // }
      // });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm("是否确认删除人员体检信息的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delExamine(ids);
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
      this.$confirm("是否确认导出所有人员体检信息数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.exportLoading = true;
          return exportExamine(queryParams);
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
/* 单位树形下拉框 */
::v-deep .vue-treeselect {
  width: 280px !important;
}
::v-deep .upload-file-list {
  width: 540px !important;
}
/* 对话框背景颜色 */
.qq {
  padding: 30px 30px 20px 30px !important;
}
::v-deep .qq .vue-treeselect__control {
  width: 280px !important;
}
::v-deep .qq .el-input__inner {
  width: 280px !important;
}
.qq .el-dialog__footer {
  margin-right: 70px !important;
}
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
::v-deep .el-input__inner {
  /* width: 200px !important; */
  height: 36px;
  background: transparent;
  color: #fff;
  border: 1px solid white !important;
  margin-top: -10px;
}
/* 添加体检信息 */
.ww .el-form {
  margin-bottom: 70px !important;
}
::v-deep .el-dialog__footer {
  margin-right: 27px !important;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
.ee .el-form {
  margin-bottom: 70px !important;
}

/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
/* 单位下拉菜单选中字体颜色 */
::v-deep .vue-treeselect__single-value {
  color: #fff !important;
}
::v-deep .qq > .el-form-item__content > .vue-treeselect__control {
  width: 280px !important;
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
::v-deep .ql-toolbar.ql-snow .ql-formats {
  margin-right: 7px;
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
::v-deep .ql-editor ql-blank {
  color: #fff !important;
}
.xiangMu {
  display: inline-block;
}
.xiangMu > .el-form-item {
  margin-left: 35px;
}
/* 体检 btp */
.el-button--btp.is-active,
.el-button--btp:active {
  background: #1d96ff;
  border-color: #1d96ff;
  color: #ffffff;
}

.el-button--btp:focus,
.el-button--btp:hover {
  background: #1d96ff;
  border-color: #1d96ff;
  color: #ffffff;
}

.el-button--btp {
  width: 116px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #1d96ff;
}
.el-select-dropdown__item.selected {
  color: #111;
}
::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent;
  color: #fff;
}
.input {
  width: 150px;
  height: 36px;
  line-height: 36px;
  border: 1px solid #fff;
  background-color: transparent;
  outline: none;
  color: #fff;
  padding: 0 20px;
  font-size: 14px;
  border-radius: 4px;
}
.input1 {
  width: 150px;
  height: 36px;
  line-height: 36px;
  border: 1px solid #fff;
  background-color: transparent;
  outline: none;
  color: #fff;
  padding: 0 20px;
  font-size: 14px;
  margin-right: 10px;
  border-radius: 4px;
}
.span {
  margin: 0 5px;
  color: #fff;
}
.input,
.input1::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
}
.input,
.input1[type="number"] {
  -moz-appearance: textfield;
}
::v-deep .el-upload__tip {
  color: #fff;
  margin-top: 0px;
  margin-left: 0px;
}
::v-deep .el-link.el-link--default {
  color: #fff;
  padding-left: 19px;
}
::v-deep .upload-file-uploader {
  display: flex;
  width: 500px;
}
::v-deep .el-upload {
  margin-right: 13px;
}
::v-deep .upload-file-list .el-upload-list .el-upload-list--text {
  width: 442px;
}
/* 附件名称样式 */
.filename {
  display: inline-block;
  color: white;
  width: 728px;
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
::v-deep .el-textarea__inner {
  min-height: 145px !important;
}
::v-deep .upload-file-list {
  width: 500px;
}
</style>