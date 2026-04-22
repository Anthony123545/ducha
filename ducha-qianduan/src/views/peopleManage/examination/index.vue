<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="" prop="cName">
        <el-input
          v-model="queryParams.cName"
          placeholder="请输入人员名称"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <!-- <el-button type="primary" size="btn" @click="handleQuery"
          >搜索</el-button
        > -->
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
          v-hasPermi="['peopleManage:examination:add']"
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
          v-hasPermi="['peopleManage:examination:edit']"
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
          v-hasPermi="['peopleManage:examination:remove']"
          >删除</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['peopleManage:examination:export']"
        >导出</el-button>
      </el-col> -->
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="examinationList"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="人员名称" align="center" prop="id" /> -->
      <el-table-column label="序号" align="center" type="index" />
      <!-- <el-table-column label="人员名称" align="center" prop="cName" /> -->
      <!-- <el-table-column label="政审对象" align="center" prop="peopleId"  /> -->
      <el-table-column label="政审对象" align="center" prop="cName" />
      <el-table-column label="性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex" />
        </template>
      </el-table-column>
      <!-- <el-table-column label="出生年月" align="center" prop="birthDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="党团情况" align="center" prop="partyId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.Caucus" :value="scope.row.partyId" />
        </template>
      </el-table-column>
      <!-- <el-table-column label="民族" align="center" prop="nationId"  /> -->
      <!-- <el-table-column label="籍贯" align="center" prop="origin" />
      <el-table-column label="入伍年月" align="center" prop="enlistDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.enlistDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="奖惩情况" align="center" prop="prize" />
      <el-table-column label="入伍简介" align="center" prop="enlistdesc" />
      <el-table-column label="政治思想态度" align="center" prop="politicsAttitude" />
      <el-table-column label="参加组织情况" align="center" prop="organization" />
      <el-table-column label="家庭成员情况" align="center" prop="family" />
      <el-table-column label="社会关系情况" align="center" prop="socialRelations" />
      <el-table-column label="配偶家庭情况" align="center" prop="family2" />
      <el-table-column label="本人自我鉴定" align="center" prop="selfIdentification" />
      <el-table-column label="单位审查报告" align="center" prop="unitExamination" /> -->
      <el-table-column label="操作人员" align="center" prop="adminId" />
      <el-table-column label="操作时间" align="center" prop="createtime" />
      <!-- <el-table-column label="操作时间" align="center" prop="updatetime" /> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-row :gutter="10" class="mb8 buttons-row-strict">
            <el-button
              size="btu"
              type="text"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['peopleManage:examination:edit']"
              >修改</el-button
            >
            <el-button
              size="btd"
              type="text"
              @click="handleDelete(scope.row)"
              v-hasPermi="['peopleManage:examination:remove']"
              >删除</el-button
            >
          </el-row>
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

    <!-- 添加或修改人员政审对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1400px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree" style="overflow: -Scroll; overflow-x: hidden">
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          :inline="true"
          label-width="120px"
        >
          <div class="jiben">基本信息</div>
          <el-form-item label="政审对象" prop="peopleId">
            <el-select
              v-model="form.peopleId"
              clearable
              @change="peopleIdfun"
              ref="peopleId"
              placeholder="请输入政审对象"
            >
              <el-option
                v-for="dict in peopleIds"
                :key="dict.id"
                :label="dict.name"
                :value="dict.id"
                style="width: 200px; height: 36px"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-select v-model="form.sex" clearable placeholder="请输入性别">
              <el-option
                v-for="dict in sexList"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
                style="width: 200px; height: 36px"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="出生年月" prop="birthDate">
            <el-date-picker
              clearable
              size="small"
              v-model="form.birthDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择出生年月"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="党团情况" prop="partyId">
            <el-select
              v-model="form.partyId"
              clearable
              placeholder="请输入党团情况"
            >
              <el-option
                v-for="dict in partyIds"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
                style="width: 200px; height: 36px"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="民族" prop="nationId">
            <el-select
              v-model="form.nationId"
              clearable
              placeholder="请输入民族"
            >
              <el-option
                v-for="dict in nationIds"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
                style="width: 200px; height: 36px"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="籍贯" prop="origin">
            <el-input v-model="form.origin" placeholder="请输入籍贯" />
          </el-form-item>
          <el-form-item label="入伍年月" prop="enlistDate">
            <el-date-picker
              clearable
              size="small"
              v-model="form.enlistDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择入伍年月"
            ></el-date-picker>
          </el-form-item>
          <div class="jiben">奖惩情况</div>
          <el-form-item label prop="prize">
            <el-input
              v-model="form.prize"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
          <div class="jiben">入伍简介</div>
          <el-form-item label prop="enlistdesc">
            <el-input
              v-model="form.enlistdesc"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
          <div class="jiben">政治思想态度</div>
          <el-form-item label prop="politicsAttitude">
            <el-input
              v-model="form.politicsAttitude"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
          <div class="jiben">参加组织情况</div>
          <el-form-item label prop="organization">
            <el-input
              v-model="form.organization"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
          <div class="jiben">家庭成员情况</div>
          <el-form-item label prop="family">
            <el-input
              v-model="form.family"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
          <div class="jiben">社会关系情况</div>
          <el-form-item label prop="socialRelations">
            <el-input
              v-model="form.socialRelations"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
          <div class="jiben">配偶家庭情况</div>
          <el-form-item label prop="family2">
            <el-input
              v-model="form.family2"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
          <div class="jiben">本人自我鉴定</div>
          <el-form-item label prop="selfIdentification">
            <el-input
              v-model="form.selfIdentification"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
          <div class="jiben">单位审查报告</div>
          <el-form-item label prop="unitExamination">
            <el-input
              v-model="form.unitExamination"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listExamination,
  getExamination,
  delExamination,
  addExamination,
  updateExamination,
  exportExamination,
  listPeople,
  getPeople,
} from "@/api/peopleManage/examination";

export default {
  name: "Examination",
  dicts: [
    "sys_user_sex",
    "post_Level",
    "sys_user_rank",
    "personnel_type",
    "Degree_level",
    "degree",
    "Caucus",
    "marryList",
    "sys_yw",
    "healthy",
    "bloodType",
    "sys_yes_no",
    "sys_yes_no",
  ],
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
      // 人员政审表格数据
      examinationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        peopleId: null,
        sex: null,
        birthDate: null,
        partyId: null,
        nationId: null,
        origin: null,
        enlistDate: null,
        prize: null,
        enlistdesc: null,
        politicsAttitude: null,
        organization: null,
        family: null,
        socialRelations: null,
        family2: null,
        selfIdentification: null,
        unitExamination: null,
        adminId: null,
        createtime: null,
        updatetime: null,
        cName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        peopleId: [
          { required: true, message: "政审对象不能为空", trigger: "blur" },
        ],
        sex: [{ required: true, message: "性别不能为空", trigger: "change" }],
        birthDate: [
          { required: true, message: "出生年月不能为空", trigger: "blur" },
        ],
        partyId: [
          { required: true, message: "党团情况不能为空", trigger: "blur" },
        ],
        nationId: [
          { required: true, message: "民族不能为空", trigger: "blur" },
        ],
        origin: [{ required: true, message: "籍贯不能为空", trigger: "blur" }],
        enlistDate: [
          { required: true, message: "入伍年月不能为空", trigger: "blur" },
        ],
        createtime: [
          { required: true, message: "操作时间不能为空", trigger: "blur" },
        ],
        updatetime: [
          { required: true, message: "操作时间不能为空", trigger: "blur" },
        ],
      },
      //性别
      sexList: [],
      //政治面貌
      partyIds: [],
      //民族
      nationIds: [],
      //政审对象
      peopleIds: [],
    };
  },
  created() {
    //性别
    this.getDicts("sys_user_sex").then((response) => {
      this.sexList = response.data;
    });
    //政治面貌
    this.getDicts("Caucus").then((response) => {
      this.partyIds = response.data;
    });
    //民族
    this.getDicts("sys_familyName").then((response) => {
      this.nationIds = response.data;
    });
    //政审对象
    listPeople().then((res) => {
      if (res.code == 200) {
        this.peopleIds = res.rows;
      }
    });
    this.getList();
  },
  methods: {
    /** 查询人员政审列表 */
    getList() {
      this.loading = true;
      listExamination(this.queryParams).then((response) => {
        this.examinationList = response.rows;
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
        peopleId: null,
        sex: null,
        birthDate: null,
        partyId: null,
        nationId: null,
        origin: null,
        enlistDate: null,
        prize: null,
        enlistdesc: null,
        politicsAttitude: null,
        organization: null,
        family: null,
        socialRelations: null,
        family2: null,
        selfIdentification: null,
        unitExamination: null,
        adminId: null,
        createtime: null,
        updatetime: null,
        cName: null,
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
      this.title = "添加人员政审";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getExamination(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改人员政审";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            this.form.cName = this.$refs["peopleId"].selectedLabel;
            updateExamination(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.cName = this.$refs["peopleId"].selectedLabel;
            addExamination(this.form).then((response) => {
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
        .confirm("是否确认删除人员政审的数据项？")
        .then(function () {
          return delExamination(ids);
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
        .confirm("是否确认导出所有人员政审数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportExamination(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    //获取人员基本信息
    peopleIdfun() {
      getPeople(this.form.peopleId).then((res) => {
        if (res.code == 200) {
          this.form.sex = res.data.sex;
          this.form.birthDate = res.data.birthDate;
          this.form.partyId = res.data.partyId;
          this.form.nationId = res.data.nationId;
          this.form.origin = res.data.origin;
          this.form.enlistDate = res.data.enlistDate;
          this.form.prize = res.data.prize;
        }
      });
    },
  },
};
</script>
<style scoped>
::v-deep .el-dialog {
  width: 1070px !important;
  background-color: #004d86;
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
.el-form-item ::v-deep .el-input__inner {
  width: 200px;
  height: 36px;
  background: transparent !important;
  color: #fff;
  border: 1px solid #fff !important;
}
::v-deep .el-date-editor.el-input {
  width: 200px;
  height: 36px;
}
::v-deep .el-upload--picture-card {
  height: 210px;
  width: 170px;
  line-height: 210px;
}
.el-upload--picture-card /deep/ .el-upload--picture-card i {
  font-size: 48px;
}
::v-deep .el-upload__tip {
  display: none;
}
::v-deep .el-textarea__inner {
  width: 980px;
  height: 104px;
}
.div-box-title span {
  width: 25px;
  height: 25px;
  display: inline-block;
  background-color: #1c86e1;
  line-height: 25px;
  text-align: center;
  font-size: 16px;
  cursor: pointer;
  margin-left: 865px;
}
.el-select-dropdown__item:hover {
  color: #1c86e1;
}
.el-select-dropdown__item {
  color: #000;
}
.el-tree {
  background-color: #00365f;
  color: #fff;
}
::v-deep .el-tree-node__content:hover {
  color: #1c86e1;
}
::v-deep .el-upload-list--picture-card .el-upload-list__item {
  height: 210px;
  width: 170px;
}
::v-deep .el-table__header-wrapper .has-gutter tr {
  background-color: #003c69;
  color: #fff;
}
::v-deep .el-dialog__body {
  padding: 30px 0px 30px 38px !important;
}
::v-deep .el-dialog__footer {
  padding: 20px 52px !important;
}
</style>
