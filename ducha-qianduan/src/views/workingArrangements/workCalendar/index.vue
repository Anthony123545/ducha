<template>
  <div class="box">
    <el-button
      type="warning"
      plain
      icon="el-icon-download"
      size="mini"
      @click="handleExports"
      class="buttons"
      >导出</el-button
    >
    <el-calendar id="calendar">
      <template slot="dateCell" slot-scope="{ date, data }">
        <div class="calendar-day">
          {{ data.day.split("-").slice(2).join("-") }}
        </div>
        <div @click="chooseDay(data.day)">
          <div>
            <div
              v-for="(item, n) in attendanceDetailsData2"
              :key="item.workStartTime + n"
              v-if="item.workStartTime == data.day"
            >
              <p
                v-for="(a, b) in item.data"
                :key="a.workStartTime + b"
                v-if="b <= '2'"
                style="color: #49edf4"
              >
                {{ a.workDetail }}
              </p>
            </div>
          </div>
        </div>
      </template>
    </el-calendar>

    <!-- 日历查看 -->
    <el-dialog
      :title="title"
      :visible.sync="open1"
      width="980px"
      append-to-body
      :close-on-click-modal="false"
      @close="closes"
    >
      <div class="jiben" style="margin-top: 30px">任务详情</div>
      <el-table
        :data="worktaskplanList"
        :header-cell-style="{ background: '#2a6b9a', color: 'white' }"
        class="jindu"
        :row-class-name="rowStyle"
      >
        <el-table-column label="序号" align="center" prop width="50">
          <template scope="scope">
            <span>
              {{
                (queryParams.pageNum - 1) * queryParams.pageSize +
                scope.$index +
                1
              }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="任务名称" prop="workName" width="200" />
        <el-table-column label="任务类型" prop="workType" width="250" />
        <el-table-column
          label="任务描述"
          prop="workDetail"
          width="100"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column label="开始时间" prop="workStartTime" width="150" />
        <el-table-column label="结束时间" prop="workEndTime" width="150" />
        <el-table-column label="任务优先级" prop="taskStatus" width="100">
          <template slot-scope="scope">
            <dict-tag
              :options="dict.type.work_priority"
              :value="scope.row.workPriority"
            />
          </template>
        </el-table-column>
        <el-table-column label="进度" prop="workRate" width="100">
          <template slot-scope="scope">
            <span v-if="scope.row.workRate">{{ scope.row.workRate }}%</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          width="200"
          fixed="right"
        >
          <template slot-scope="scope">
            <el-button
              size="btk"
              type="text"
              @click="handleUpdateCha(scope.row)"
              >查看</el-button
            >
            <el-button
              size="btlook"
              type="text"
              @click="ViewProgress(scope.row)"
              v-if="scope.row.workRate != 100"
            >
              <span>进度录入</span>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel1">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 进度录入弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="980px"
      append-to-body
      class="el-dialog__header"
      :close-on-click-modal="false"
    >
      <el-form
        ref="Progress"
        :model="Progress"
        :rules="rules1"
        label-width="100px"
        :inline="true"
      >
        <div class="jiben">完成进度</div>
        <el-form-item prop="workRate">
          <div>
            <el-input-number
              v-model="Progress.workRate"
              controls-position="right"
              :min="0"
              :max="100"
            ></el-input-number>
            <el-progress
              :text-inside="true"
              :stroke-width="24"
              :percentage="Progress.workRate"
              color="rgba(41, 231, 205, 1)"
              class="jinduProgress"
            ></el-progress>
          </div>
        </el-form-item>
        <div class="jiben">附件</div>
        <el-form-item>
          <fileUpload v-model="Progress.file" />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item prop="workFeedbackRemark">
          <el-input
            v-model="Progress.workFeedbackRemark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <div class="jiben">完成时间</div>
        <el-form-item prop="workFinishTime">
          <el-date-picker
            class="comtimer"
            clearable
            size="small"
            v-model="Progress.workFinishTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择完成时间"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看任务进度 -->
    <el-dialog
      :title="title"
      :visible.sync="open2"
      width="980px"
      append-to-body
      class="el-dialog__header jinduchakan"
      :close-on-click-modal="false"
    >
      <el-form
        ref="moreTable"
        :model="moreTable"
        :rules="rulesb"
        label-width="100px"
        :inline="true"
        class="jinduchakans"
      >
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
            v-model="moreTable.fileName"
            style="width: 820px"
            class="inputs"
          />
          <a
            :href="url + moreTable.fileName"
            :download="moreTable.name"
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
          :data="workProgresses"
          border
          style="width: 100%; background: #004d86"
          class="tabless"
        >
          <el-table-column
            prop="executorPeople"
            label="执行人"
            width="180"
          ></el-table-column>
          <el-table-column label="任务周期" width="190">
            <template slot-scope="scope">
              <div v-if="scope.row.workStartTime && scope.row.workEndTime">
                {{ scope.row.workStartTime }}至{{ scope.row.workEndTime }}
              </div>
            </template>
          </el-table-column>
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
              <div>{{ scope.row.workRate == 100 ? "完成" : "未完成" }}</div>
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
        <el-button @click="cancel2">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 导出弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="open5"
      width="450px"
      append-to-body
      class="el-dialog__header jinduchakan"
      :close-on-click-modal="false"
    >
      <el-form
        ref="queryParamsTime"
        :model="queryParamsTime"
        :rules="queryParamsRules"
        label-width="100px"
        :inline="true"
      >
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            placeholder="请选择开始时间"
            clearable
            size="small"
            v-model="queryParamsTime.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            style="width: 200px"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" label-width="100px" prop="endTime">
          <el-date-picker
            placeholder="请选择结束时间"
            clearable
            size="small"
            v-model="queryParamsTime.endTime"
            type="date"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleExport">导 出</el-button>
        <el-button @click="cancelDao">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  peopleList,
  progressInput,
  queryTask,
  exportCurrentUserTask,
} from "@/api/workingArrangements/workCalendar";
export default {
  name: "workCalendar",
  dicts: ["work_priority"],
  data() {
    return {
      //日历数组
      attendanceDetailsData: [],
      attendanceDetailsData2: [],
      // 弹框的显示与隐藏
      open: false,
      title: null,
      // 进度录入参数
      Progress: {
        executorPeople: null,
        workRate: null,
        workFinishTime: null,
        workFeedbackFileName: null,
        workFeedbackFileUrl: null,
        workId: null,
        workFeedbackRemark: null,
        file: [{ name: null, url: null }],
      },
      rules1: {},
      form: {},
      rules: {},
      open1: false,
      // 查看列表数据
      worktaskplanList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 进度录入
      jinDu: {
        executorPeople: null,
        id: null,
      },
      // 时间
      day: null,
      // 查看任务进度
      open2: false,
      // 单人进度
      moreTable: {},
      rulesb: {},
      url: process.env.VUE_APP_BASE_API,
      // 执行人查看进度
      workProgresses: [],
      form1: {},
      open5: false,
      queryParamsTime: {},
      queryParamsRules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    rowStyle({ row, rowIndex }) {
      if (row.workRate == 100) {
        return "success_class";
      } else {
        return "error_class";
      }
      return "";
    },
    cancelDao() {
      this.open5 = false;
    },
    getList() {
      peopleList().then((res) => {
        this.worktaskplanList = [];
        let workStartTimedata = [];
        this.attendanceDetailsData2 = [];
        this.attendanceDetailsData = res.rows;
        res.rows.forEach((item, i) => {
          if (workStartTimedata.indexOf(item.workStartTime) === -1) {
            this.attendanceDetailsData2.push({
              workStartTime: item.workStartTime,
              data: [item],
            });
            workStartTimedata.push(item.workStartTime);
          } else {
            this.attendanceDetailsData2.forEach((n) => {
              if (n.workStartTime == item.workStartTime) {
                n.data.push(item);
              }
            });
          }
          if (item.workStartTime == this.day) {
            this.worktaskplanList.push(item);
          }
        });
      });
    },
    submitForm2() {},
    cancel2() {
      this.open2 = false;
    },
    // 进度录入按钮
    ViewProgress(row) {
      console.log(row);
      this.Progress.file = [{}];
      this.jinDu.executorPeople = row.executorPeople;
      this.jinDu.id = row.id;
      this.Progress.workRate = row.workRate;
      if (row.workFeedbackFileName) {
        this.Progress.file[0].name = row.workFeedbackFileName;
        this.Progress.file[0].url = row.workFeedbackFileUrl;
      } else {
        this.Progress.file = [];
      }
      if (row.workRate) {
      } else {
        this.Progress.workRate = 0;
      }
      this.Progress.workFeedbackRemark = row.workFeedbackRemark;
      this.Progress.workFinishTime = row.workFinishTime;
      this.title = "进度录入";
      this.open = true;
    },
    // 查看
    handleUpdateCha(row) {
      this.workProgresses = [];
      queryTask(row.id).then((res) => {
        this.moreTable = res.data;
        this.moreTable.fileName = res.data.workAnnex;
        this.moreTable.name = res.data.workAnnexName;
        this.workProgresses.push(res.data);
        this.open2 = true;
        this.title = "查看进度";
      });
    },
    cancel1() {
      this.open1 = false;
      this.executorPeople = null;
    },
    cancel() {
      this.open = false;
      // this.reset();
    },
    closes() {
      this.worktaskplanList = [];
    },
    // 进度录入
    submitForm() {
      this.$refs["Progress"].validate((valid) => {
        if (valid) {
          this.Progress.workId = this.jinDu.id;
          this.Progress.executorPeople = this.jinDu.executorPeople;
          if (this.Progress.file.length != 0) {
            this.Progress.workFeedbackFileName = this.Progress.file[0].name;
            this.Progress.workFeedbackFileUrl = this.Progress.file[0].url;
          }
          progressInput(this.Progress).then((res) => {
            this.$modal.msgSuccess("上传成功");
            this.getList();
            this.open = false;
          });
        }
      });
    },
    reset() {
      this.Progress = {
        executorPeople: null,
        workRate: null,
        workFinishTime: null,
        workFeedbackFileName: null,
        workFeedbackFileUrl: null,
        workId: null,
        workFeedbackRemark: null,
      };
      this.resetForm("Progress");
    },
    chooseDay(day) {
      this.worktaskplanList = [];
      this.day = day;
      this.attendanceDetailsData.forEach((item, i) => {
        if (item.workStartTime == day) {
          this.worktaskplanList.push(item);
        }
      });
      this.open1 = true;
      this.title = "查看进度";
    },
    handleSelected(day) {
      let flag = -1; //默认显示为-1
      this.attendanceDetailsData.forEach((item) => {
        //this.attendanceDetailsData是后台返回的数据
        if (item.workStartTime == day) {
          //判断显示数据
          flag = item.id;
          return;
        }
      });
      return flag;
    },
    // 导出
    handleExports() {
      this.queryParamsTime = {};
      this.open5 = true;
      this.title = "导出";
    },
    handleExport() {
      this.$modal
        .confirm("是否确认导出数据项？")
        .then(() => {
          return exportCurrentUserTask(this.queryParamsTime);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.open5 = false;
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
::v-deep .el-dialog__title {
  color: #fff;
}
.is-selected {
  color: #1989fa;
}
::v-deep .el-calendar-table td {
  height: 11vh;
  border-bottom: 1px solid #dfe6ec !important;
}
::v-deep .el-calendar-table .el-calendar-day {
  height: 100%;
}
/* ::v-deep .el-dialog__body .el-table tr:nth-child(even) {
  background-color: #2a6b9a !important;
} */

::v-deep .jindu .el-table__cell {
  border: none !important;
}

::v-deep .jindu .el-dialog__body .el-table th.is-leaf {
  border: none !important;
}
::v-deep .el-calendar {
  background: #114a80 !important;
}
::v-deep .el-calendar__title {
  color: #fff;
}
::v-deep .el-calendar .el-button--plain:nth-child(1) {
  background: #1d96ff !important;
  color: #fff;
}
::v-deep .el-calendar .el-button--plain:nth-child(2) {
  background: #67c23a !important;
  color: #fff;
}
::v-deep .el-calendar .el-button--plain:nth-child(3) {
  background: #ff4949 !important;
  color: #fff;
}
::v-deep .el-calendar-table thead th {
  color: #fff;
}
::v-deep .el-calendar-table__row .current {
  color: #fff;
}
::v-deep .el-calendar-table .el-calendar-day:hover {
  background: #114a80 !important;
}
::v-deep .el-calendar-table td.is-selected {
  background: #114a80 !important;
}
.buttons {
  position: absolute;
  left: 9%;
  top: 1%;
}

.jinduchakans .el-table th.is-leaf {
  border: 1px solid white !important;
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
  width: 510px;
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
  background-color: #004d86 ;
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
::v-deep .el-icon-arrow-up {
  color: white;
}
::v-deep .el-icon-arrow-down {
  color: #fff;
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
::v-deep .inputs .el-input__inner {
  width: 820px;
}
::v-deep .el-table .success_class {
  background-color: green !important;
}
 
::v-deep .el-table .error_class {
  background-color: #004d86 !important;
}

</style>
