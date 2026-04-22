<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
      class="biao"
    >
      <el-form-item prop="workName">
        <el-input
          v-model="queryParams.workName"
          placeholder="请输入任务名称"
          clearable
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="workNum">
        <el-input
          v-model="queryParams.workNum"
          placeholder="请输入任务编号"
          clearable
          @input="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item prop="workType">
        <el-select
          v-model="queryParams.workType"
          placeholder="请选择任务类型"
          clearable
        >
          <el-option
            v-for="dict in dict.type.work_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item> -->
      <el-form-item>
        <!-- <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button> -->
        <el-button
          type="btr"
          icon="el-icon-refresh"
          size="mini"
          @click="resetQuery"
          >重置</el-button
        >
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
          v-hasPermi="['jobTaskRecord:jobTask:add']"
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
          v-hasPermi="['jobTaskRecord:jobTask:edit']"
          >修改</el-button
        >
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jobTaskRecord:jobTask:remove']"
          >删除</el-button
        >
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jobTaskRecord:jobTask:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="jobTaskList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="50">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="任务名称"
        align="center"
        prop="workName"
        width="200px"
      />
      <!-- <el-table-column label="任务编号" align="center" prop="workNum" /> -->
      <el-table-column label="任务类型" align="center" prop="workType">
      </el-table-column>
      <el-table-column label="任务描述" align="center" prop="workDetail" />
      <el-table-column label="任务备注" align="center" prop="workRemark" />
      <el-table-column
        label="任务开始时间"
        align="center"
        prop="workStartTime"
        width="150"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.workStartTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="任务结束时间"
        align="center"
        prop="workEndTime"
        width="150"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.workEndTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="任务优先级"
        align="center"
        prop="workPriority"
        width="100"
      >
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.work_priority"
            :value="scope.row.workPriority"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
      >
        <template slot-scope="scope">
          <el-button size="btk" type="text" @click="handleChakan1(scope.row)">
            <!-- 多人进度查看 -->
            <span class="chakan">查看</span>
          </el-button>
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jobTaskRecord:jobTask:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jobTaskRecord:jobTask:remove']"
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

    <!-- 添加或修改工作任务计划对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="980px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        inline
      >
        <div class="jiben">基本信息</div>
        <el-form-item label="任务名称" prop="workName">
          <el-input placeholder="请选择任务名称" v-model="form.workName" />
        </el-form-item>
        <el-form-item label="任务编号" prop="workNum">
          <el-input
            placeholder="请选择任务编号"
            label="任务编号"
            v-model="form.workNum"
            class="ipt"
          ></el-input>
        </el-form-item>
        <el-form-item label="发布单位" prop="publishUnit">
          <treeselect
            v-model="form.publishUnit"
            :options="part"
            @select="selectPeo"
            class="threeselects"
            disabled
            placeholder="请选择发布单位"
          />
        </el-form-item>
        <el-form-item label="任务类型" prop="workType">
          <el-input
            placeholder="请输入任务类型"
            v-model="form.workType"
            style="width: 200px"
          ></el-input>
        </el-form-item>
        <el-form-item label="发布人" prop="publishPeople">
          <el-input
            placeholder="请输入发布人"
            v-model="form.publishPeople"
            :disabled="true"
            class="ipt"
          ></el-input>
        </el-form-item>
        <el-form-item label="优先级" prop="workPriority">
          <el-select
            filterable
            clearable
            placeholder="请选择优先级"
            v-model="form.workPriority"
          >
            <el-option
              v-for="item in warehouse"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue"
            />
          </el-select>
        </el-form-item>
        <div>
          <el-form-item
            label="执行单位"
            prop="executorUnit"
            label-width="100px"
          >
            <div>
              <el-select
                multiple
                filterable
                v-model="form.executorUnit"
                id="execute"
                ref="peoplenames"
                placeholder="请选择执行单位"
                @remove-tag="selent"
              >
                <el-option
                  v-for="(item, i) in danWeisList"
                  :key="i"
                  :label="item.deptName"
                  :value="item.deptId"
                  id="execute"
                  @click.native="danFn()"
                />
              </el-select>
            </div>
          </el-form-item>
        </div>

        <div>
          <el-form-item
            label="执行人"
            prop="executorPeople"
            label-width="100px"
          >
            <el-select
              multiple
              filterable
              v-model="form.executorPeople"
              id="execute"
              ref="peoplenames"
              placeholder="请选择执行人"
            >
              <el-option
                v-for="(item, i1) in executor"
                :key="i1 + ' '"
                :label="item.nickName"
                :value="item.nickName"
                id="execute"
              />
            </el-select>
          </el-form-item>
        </div>
        <el-form-item label="开始时间" prop="workStartTime">
          <el-date-picker
            placeholder="请选择开始时间"
            clearable
            size="small"
            v-model="form.workStartTime"
            type="datetime"
            style="width: 200px"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" label-width="100px" prop="workEndTime">
          <el-date-picker
            placeholder="请选择结束时间"
            clearable
            size="small"
            v-model="form.workEndTime"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <div class="jiben">任务附件</div>
        <el-form-item style="margin-left: 30px">
          <fileUpload v-model="wenJian" :limit="1" />
        </el-form-item>
        <div class="jiben">任务描述</div>
        <el-form-item prop="workDetail">
          <el-input
            v-model="form.workDetail"
            type="textarea"
            placeholder="请输入内容"
            style="width: 950px"
          />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item prop="workRemark">
          <el-input
            v-model="form.workRemark"
            type="textarea"
            placeholder="请输入内容"
            style="width: 950px"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 多人进度查看弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="open1"
      width="980px"
      append-to-body
      class="el-dialog__header jinduchakan"
      :close-on-click-modal="false"
    >
      <el-form
        ref="moreTable"
        :model="moreTable"
        :rules="rules"
        label-width="100px"
        :inline="true"
        class="jinduchakans"
      >
        <div class="jiben">完成进度</div>
        <el-progress
          :text-inside="true"
          :stroke-width="24"
          :percentage="moreTable.progressRate"
          style="margin-bottom: 20px"
          color="rgba(41, 231, 205, 1)"
        ></el-progress>
        <div class="jiben">任务信息</div>
        <el-form-item label="发布人" prop="publishPeople" label-width="auto">
          <el-input
            placeholder="请输入发布人"
            v-model="moreTable.publishPeople"
            :disabled="true"
            class="ipt"
            style="color: #fff; width: 350px"
          ></el-input>
        </el-form-item>
        <el-form-item prop="comWeek" label="任务周期" label-width="100px">
          <div
            style="
              width: 350px;
              border: 1px solid #fff;
              border-radius: 4px;
              min-height: 35px;
              height: auto !important;
              line-height: 35px;
              text-indent: 1em;
              margin-bottom: 20px;
              color: #fff;
            "
          >
            {{ moreTable.workStartTime }} 至 {{ moreTable.workEndTime }}
          </div>
        </el-form-item>
        <div class="jiben">附件文档</div>
        <el-form-item prop="file">
          <el-input
            label="附件名称"
            v-model="moreTable.workAnnexName"
            style="width: 820px"
            class="inputs"
          />
          <a
            :href="url + moreTable.workAnnex"
            :download="moreTable.workAnnexName"
            title="下载"
            style="color: white; margin-left: 10px"
            class="downloadfile"
            >下载附件</a
          >
        </el-form-item>
        <div class="jiben">任务描述</div>
        <div class="thistext">{{ moreTable.workDetail }}</div>
        <div class="jiben">备注</div>
        <div class="thistext">{{ moreTable.workRemark }}</div>
        <div class="jiben">执行人信息</div>
        <el-table
          :data="moreTable.jobTaskRecordExecutorList"
          border
          style="width: 100%; background: #004d86"
          class="tabless"
        >
          <el-table-column
            prop="executorPeople"
            label="执行人"
            width="180"
          ></el-table-column>
          <el-table-column prop="workRate" label="进度">
            <template slot-scope="scope">
              <el-progress
                :text-inside="true"
                :stroke-width="24"
                :percentage="scope.row.workRate"
                style="margin-bottom: 20px"
                color="rgba(41, 231, 205, 1)"
              ></el-progress>
            </template>
          </el-table-column>
          <el-table-column
            prop="workFinishTime"
            label="完成时间"
            width="180"
          ></el-table-column>
          <el-table-column label="状态" width="180">
            <template slot-scope="scope">
              <div>
                {{ scope.row.workFinishTime == 100 ? "完成" : "未完成" }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="下载">
            <template slot-scope="scope">
              <a
                :href="url + scope.row.workFeedbackFileUrl"
                :download="scope.row.workFeedbackFileName"
                >下载附件</a
              >
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel1">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listJobTask,
  getJobTask,
  delJobTask,
  addJobTask,
  updateJobTask,
  queryTaskProgress,
} from "@/api/jobTaskRecord/jobTask";
import { getUnitUsers } from "@/api/workingArrangements/workTask";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import Cookies from "js-cookie";
import { getDept } from "@/api/workingArrangements/workTask";
import { listDept } from "@/api/system/dept";
import { getDicts } from "@/api/system/dict/data";

export default {
  name: "JobTask",
  dicts: ["work_priority"],
  components: { Treeselect },
  data() {
    return {
      url: process.env.VUE_APP_BASE_API,
      // 遮罩层
      loading: true,
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
      // 工作任务计划表格数据
      jobTaskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workName: null,
        workNum: null,
        workType: null,
        workStartTime: null,
        workPriority: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        workName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" },
        ],
        workNum: [{ required: true, message: "编号不能为空", trigger: "blur" }],
        executorPeople: [
          { required: true, message: "执行人不能为空", trigger: "blur" },
        ],
        workType: {
          required: true,
          message: "计划类型不能为空",
          trigger: "blur",
        },
        workStartTime: {
          required: true,
          message: "开始时间不能为空",
          trigger: "blur",
        },
        workEndTime: {
          required: true,
          message: "结束时间不能为空",
          trigger: "blur",
        },
        workPriority: {
          required: true,
          message: "优先级不能为空",
          trigger: "blur",
        },
        workDetail: {
          required: true,
          message: "任务描述不能为空",
          trigger: "blur",
        },
      },
      // 发布单位
      part: [],
      //获取单位所有不是树形结构
      danWeisList: [],
      // 执行人
      executor: [],
      // 优先级
      warehouse: [],
      // 文件上传
      wenJian: null,
      open1: false,
      moreTable: {},
    };
  },
  created() {
    this.getList();
    this.getdept();
    getDicts("work_priority").then((response) => {
      this.warehouse = response.data;
    });
  },
  methods: {
    cancel1() {
      this.open1 = false;
    },
    // 多人进度查看
    handleChakan1(row) {
      this.reset();
      this.open1 = true;
      this.title = "进度查看";
      // 获取当前行的id
      queryTaskProgress(row.id).then((res) => {
        this.moreTable = res.data;
      });
    },
    // 获取当前时间
    getDay() {
      var today = new Date();
      var y = today.getFullYear();
      var M = today.getMonth() + 1;
      var d = today.getDate();
      var h = today.getHours();
      var m = today.getMinutes();
      var s = today.getSeconds();
      var w = today.getDay();
      if (M < 10) {
        M = "0" + M;
      }
      if (d < 10) {
        d = "0" + d;
      }
      if (h < 10) {
        h = "0" + h;
      }
      if (m < 10) {
        m = "0" + m;
      }
      if (s < 10) {
        s = "0" + s;
      }
      this.form.workStartTime =
        y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
    },
    //获取所有单位
    danWeis() {
      listDept().then((res) => {
        this.danWeisList = res.data;
      });
    },
    //点击多选单位
    danFn() {
      this.renyuanEs();
    },
    //获取多选单位下的人员
    renyuanEs() {
      if (this.form.executorUnit.length > 0) {
        var ids = this.form.executorUnit.join(",");
        getUnitUsers(ids).then((res) => {
          this.executor = res.data;
        });
      } else {
        this.executor = [];
      }
    },
    //删除所有单位的某一个触发
    selent() {
      this.renyuanEs();
      this.form.executorPeople = [];
    },
    // 发布单位选中触发
    selectPeo(val) {
      this.form.publishUnit = val.id;
    },
    // 发布单位接口
    getdept() {
      getDept().then((res) => {
        this.part = res.data;
      });
    },
    /** 查询工作任务计划列表 */
    getList() {
      this.loading = true;
      listJobTask(this.queryParams).then((response) => {
        this.jobTaskList = response.rows;
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
        workName: null,
        workNum: null,
        workType: null,
        publishUnit: null,
        publishPeople: null,
        workAnnex: null,
        workDetail: null,
        workRemark: null,
        workStartTime: null,
        workEndTime: null,
        workPriority: null,
        createBy: null,
        createTime: null,
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
      this.form.publishUnit = Cookies.get("deptId");
      this.form.publishPeople = Cookies.get("nickName");
      this.form.userId = Cookies.get("userId");
      this.wenJian=null;
      this.danWeis();
      this.getDay();
      this.open = true;
      this.title = "添加工作任务计划";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.danWeis();
      const id = row.id || this.ids;
      getJobTask(id).then((response) => {
        this.form = response.data;
        if (response.data.executorUnit) {
          this.form.executorUnit = response.data.executorUnit.map(Number);
        }
        this.wenJian = this.form.workAnnex;
        this.open = true;
        this.title = "修改工作任务计划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if (this.wenJian) {
        this.form.workAnnexName = this.wenJian[0].name;
        this.form.workAnnex = this.wenJian[0].url;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJobTask(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addJobTask(this.form).then((response) => {
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
        .confirm("是否确认删除工作任务计划编号的数据项？")
        .then(function () {
          return delJobTask(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "jobTaskRecord/jobTask/export",
        {
          ...this.queryParams,
        },
        `jobTask_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
<style scoped>
::v-deep .biao .el-input__inner {
  height: 36px;
  background-color: transparent;
  color: #fff;
  width: 200px;
  border: 1px solid white !important;
}
::v-deep .el-form-item__label {
  width: 100px !important;
}
::v-deep .el-select__input {
  color: #fff;
}
::v-deep .el-form--inline .el-form-item {
  margin-right: 0px;
}
::v-deep .biao .el-form-item {
  margin-right: 10px !important;
}
::v-deep .threeselects {
  width: 217px;
}
::v-deep .el-dialog {
  background-color: #004d86 !important;
}
::v-deep .el-dialog__title {
  color: #fff;
}

::v-deep .el-link.el-link--default {
  color: #fff;
}
::v-deep .el-upload__tip {
  color: #fff;
}
::v-deep .el-progress-bar__outer {
  background-color: rgba(25, 98, 153, 1);
}
::v-deep .el-input__inner {
  background-color: transparent;
  width: 200px;
}
::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent;
}
::v-deep .vue-treeselect__control {
  background: transparent;
}
::v-deep .vue-treeselect__single-value {
  color: #fff;
}

::v-deep .el-input__inner {
  height: 36px;
  line-height: 36px;
  color: #fff;
  border: 1px solid white !important;
}
::v-deep .el-select-dropdown {
  background-color: white;
}
::v-deep .el-dialog__wrapper .el-form-item__label {
  color: white !important;
}

::v-deep .el-form-item__label {
  width: 30%;
}
::v-deep .el-textarea {
  width: 70%;
}
::v-deep .el-dialog__header {
  border-bottom: 1px solid #718a9d;
}
::v-deep .el-textarea__inner {
  width: 945px;
  height: 104px;
}
/* 表宽度 */
::v-deep .el-input {
  width: 200px;
}
::v-deep .serialNumber {
  border: 1px solid white;
  border-radius: 5px;
  width: 200px;
  text-indent: 1em;
  color: white;
  line-height: 36px;
}
::v-deep .el-date-editor.el-input,
.el-date-editor {
  width: 200px;
}
/* 执行 */
::v-deep #execute {
  width: 500px;
}
::v-deep .el-input--suffix {
  width: auto;
}
/* 计划附件 */
::v-deep .el-upload__tip {
  position: absolute;
  top: -5px;
  width: 500px;
  left: 100px;
  color: white;
}
/* 上传附件样式 */
::v-deep .el-icon-document {
  padding: 3px 5px;
  color: white;
}
::v-deep .el-upload-list__item:hover {
  background-color: #00365f;
}
/* 上传附件的删除按钮样式 */
::v-deep .el-link--inner {
  margin-left: 3px;
}
::v-deep .el-select-dropdown__item:hover {
  color: #1890ff !important;
}
/* 下拉框的颜色 */
::v-deep .el-scrollbar li {
  background-color: white;
  color: black;
}
::v-deep .el-scrollbar li:hover {
  background-color: #00365f;
  color: white;
}
::v-deep .el-select-dropdown__wrap .el-scrollbar__view {
  background-color: white;
}
/* 修改计划类型样式 */
::v-deep .el-input--small .el-input__inner {
  height: 36px;
  line-height: 36px;
}
::v-deep .el-input--medium .el-input__inner {
  background-color: rgba(0, 0, 0, 0);
}
/* 修改编号样式 */
::v-deep .ipt .el-input__inner {
  background-color: #004d86 !important;
}
/* 进度查看 */
::v-deep .el-button--btlook.is-active,
.el-button--btlook:active {
  background: #32a5d3;
  border-color: #32a5d3;
  color: #ffffff;
}
::v-deep .el-button--btlook:focus,
.el-button--btlook:hover {
  background: #32a5d3;
  border-color: #32a5d3;
  color: #ffffff;
}
::v-deep .el-button--btlook {
  width: 70px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #32a5d3;
}
::v-deep .big .el-input__inner {
  width: 940px;
}
/* 树形 */
::v-deep .threeselects {
  width: 200px;
}
::v-deep .threeselects .vue-treeselect__input {
  background-color: #004d86;
  /* color: white; */
}
::v-deep .el-form-item__content {
  /* color: white; */
  border-bottom: 1px solid #004d86;
}
::v-deep .vue-treeselect__control {
  background-color: #004d86;
  border-bottom: 1px solid white;
  /* color: whi; */
  /* color: white; */
}
::v-deep .vue-treeselect__placeholder {
  color: white;
}

::v-deep .change_plan_type .el-input__inner {
  width: 200px;
}
/* 表格样式 */
::v-deep .el-table__empty-block {
  background-color: #004d86;
}
::v-deep .el-table {
  background-color: #004d86;
}
::v-deep .comtimer .el-input__inner {
  width: 940px;
}
/* 备注 */
::v-deep .thistext {
  color: white;
  width: 930px;
  border: 1px solid #fff;
  border-radius: 4px;
  height: auto;
  min-height: 35px;
  text-indent: 1em;
  line-height: 35px;
  margin-bottom: 20px;
  /* margin: auto; */
}
/* 完成进度 */
::v-deep .complue .el-input__inner {
  width: 940px;
}
::v-deep .complue {
  width: 940px;
}
/* 进度 */
::v-deep .jinduProgress {
  width: 740px;
  position: absolute;
  left: 210px;
  top: 5px;
}
/* 表格 */
::v-deep .tabless .el-table__row {
  background-color: #004d86;
}

::v-deep .has-gutter {
  background-color: #004d86;
}
::v-deep .el-dialog__body .el-table tr:nth-child(odd) {
  background-color: #004d86 !important;
}
::v-deep .el-dialog__body .el-table tr:nth-child(even) {
  background-color: #004d86 !important;
}
::v-deep .el-dialog__body table td {
  border-bottom: 1px solid white !important;
}
.el-dialog__body .el-table th.is-leaf {
  border: 1px solid white;
}
/* 下载按钮 */
::v-deep .downloadfile {
  border: 1px solid white;
  display: inline-block;
  width: 100px;
  height: 33px;
  border-radius: 5px;
  line-height: 33px;
  text-align: center;
}
::v-deep .el-table__row {
  border-bottom: 1px solid white;
}
::v-deep .el-input-number__increase {
  background-color: #004d86;
}
::v-deep .el-input-number__decrease {
  background-color: #004d86;
}
/* 进度条百分比 */
::v-deep .el-progress-bar__innerText {
  color: #004d86;
  /* font-weight: 200; */
}
::v-deep .el-progress-bar__outer {
  background-color: rgba(25, 98, 153, 1);
}
/* 上下箭头 */
::v-deep .el-icon-arrow-up,
.el-icon-arrow-down {
  color: white;
}
::v-deep .vue-treeselect__single-value {
  color: white;
}

::v-deep .el-table__empty-block {
  border-bottom: 1px solid white;
}
::v-deep .el-table__body-wrapper .el-table__empty-block {
  border: none !important;
}
::v-deep .plantypes .el-form-item__label {
  text-align: center;
}
::v-deep .el-form-item__content {
  border-bottom: none !important;
}
::v-deep .jinduchakan .el-table__body-wrapper .el-table__empty-block {
  border-bottom: 2px solid white !important;
  border-left: 2px solid white !important;
  border-right: 2px solid white !important;
}
/* 去除默认字体 */
::v-deep .el-select-dropdown__item.selected {
  color: #000;
  font-weight: bold;
}

::v-deep .el-scrollbar li:hover {
  color: black;
}

::v-deep .jinduchakans .el-table th.is-leaf {
  border: 1px solid white !important;
}

::v-deep .el-pagination .el-input__inner {
  width: 100px;
  height: 28px;
}
::v-deep .el-pagination__jump .el-input__inner {
  width: 46px;
}
::v-deep .el-pagination__editor.el-input {
    width: 50px;
}

::v-deep .inputs .el-input__inner {
  width: 820px;
}
</style>
