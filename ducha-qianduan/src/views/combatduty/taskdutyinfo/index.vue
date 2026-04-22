<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="auto"
    >
      <!-- <el-form-item prop="unitName">
        <el-select
          v-model="queryParams.unitName"
          clearable
          placeholder="请选择单位"
        >
          <el-option
            v-for="dict in deptOptions"
            :key="dict.deptId"
            :label="dict.deptName"
            :value="dict.deptId"
          ></el-option>
        </el-select>
      </el-form-item> -->
      <!-- <el-form-item label="单位" prop="unitName">
        <el-select v-model="queryParams.unitName" placeholder="请选择单位" clearable size="small">
          <el-option label="请选择字典生成" value />
        </el-select>
      </el-form-item>-->
      <!-- <el-form-item label="部门ID" prop="unitId">
        <el-input
          v-model="queryParams.unitId"
          placeholder="请输入部门ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item prop="commanderName">
        <el-select
          v-model="queryParams.commanderId"
          @change="handleQuery"
          filterable
          clearable
          placeholder="请选择指挥员"
        >
          <el-option
            v-for="item in rylist"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="指挥员" prop="commanderName">
        <el-select v-model="queryParams.commanderName" placeholder="请选择指挥员" clearable size="small">
          <el-option label="请选择字典生成" value />
        </el-select>
      </el-form-item>-->
      <!--   <el-form-item label="指挥员ID" prop="commanderId">
        <el-input
          v-model="queryParams.commanderId"
          placeholder="请输入指挥员ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="指挥员电话" prop="commanderPhone">
        <el-input
          v-model="queryParams.commanderPhone"
          placeholder="请输入指挥员电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参加人员ID" prop="participantsId">
        <el-input
          v-model="queryParams.participantsId"
          placeholder="请输入参加人员ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <!-- <el-form-item label="开始时间" prop="startTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="所在位置" prop="adress">
        <el-input
          v-model="queryParams.adress"
          placeholder="请输入所在位置"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
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
          v-hasPermi="['combatduty:taskdutyinfo:add']"
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
          v-hasPermi="['combatduty:taskdutyinfo:edit']"
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
          v-hasPermi="['combatduty:taskdutyinfo:remove']"
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
          v-hasPermi="['combatduty:taskdutyinfo:export']"
        >导出</el-button>
      </el-col>-->
      <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="taskdutyinfoList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <!-- <el-table-column label="主键ID" align="center" prop="id" /> -->
      <!-- <el-table-column label="单位" align="center" prop="unitName" /> -->
      <!-- <el-table-column label="部门ID" align="center" prop="unitId" /> -->
      <el-table-column label="指挥员" align="center" prop="commanderName" />
      <!-- <el-table-column label="指挥员ID" align="center" prop="commanderId" /> -->
      <el-table-column
        label="指挥员电话"
        align="center"
        prop="commanderPhone"
      />
      <!-- <el-table-column label="参加人员" align="center" prop="participantsName" /> -->
      <!-- <el-table-column label="参加人员ID" align="center" prop="participantsId" /> -->
      <el-table-column
        label="参加人数"
        align="center"
        prop="participantsName"
      />
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
      <el-table-column label="所在位置" align="center" prop="adress" />
      <el-table-column
        label="事由"
        show-overflow-tooltip
        align="center"
        prop="question"
      />
      <el-table-column
        label="备注"
        show-overflow-tooltip
        align="center"
        prop="remarks"
      />
      <el-table-column
        label="操作"
        align="center"
        width="200"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="btk"
            type="text"
            @click="handlelook(scope.row)"
            v-hasPermi="['combatduty:taskdutyinfo:edit']"
            >查看</el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['combatduty:taskdutyinfo:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['combatduty:taskdutyinfo:remove']"
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

    <!-- 添加或修改执行任务对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1016px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <div class="jiben">基本信息</div>
        <!-- <el-form-item label="单位" prop="unitName">
          <el-select
            v-model="form.unitName"
            @change="unitNamechange"
            clearable
            placeholder=""
          >
            <el-option
              v-for="dict in deptOptions"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict"
            ></el-option>
          </el-select>
        </el-form-item> -->
        <!-- <el-form-item label="部门ID" prop="unitId">
          <el-input v-model="form.unitId" placeholder="请输入部门ID" />
        </el-form-item>-->
        <el-form-item label="指挥员" prop="commanderName">
          <el-select
            v-model="form.commanderName"
            @change="driverNamefun"
            filterable
            clearable
            placeholder=""
          >
            <el-option
              v-for="item in rylist"
              :key="item.id"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="指挥员" prop="commanderId">
          <el-select v-model="form.commanderId" @change="driverNamefun" filterable clearable placeholder="请选择指挥员">
            <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item"></el-option>
          </el-select>
        </el-form-item>-->
        <el-form-item label="指挥员电话" prop="commanderPhone">
          <el-input v-model="form.commanderPhone" placeholder="" />
        </el-form-item>
        <el-form-item label="参加人数" prop="participantsName">
          <el-input v-model="form.participantsName" placeholder="" />
        </el-form-item>
        <!-- <el-form-item label="参加人员">
          <el-select
            v-model="form.participantsId"
            multiple
            filterable
            clearable
            placeholder="请选择参加人员"
          >
            <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>-->
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="所在位置" prop="adress">
          <el-input v-model="form.adress" placeholder="" />
        </el-form-item>
        <div class="jiben">人员名册</div>
        <el-form-item>
          <FileUpload v-model="form.taskdutyFus" />
        </el-form-item>
        <!-- <el-form-item label="" prop="participantsIds">
          <el-select
            v-model="form.participantsIds"
            multiple
            filterable
            clearable
            placeholder=""
          >
            <el-option
              v-for="item in rylist"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item> -->
        <div class="jiben">事由</div>
        <el-form-item label prop="question">
          <el-input v-model="form.question" type="textarea" placeholder="" />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item label prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看执行任务对话框 -->
    <el-dialog
      :title="title1"
      :visible.sync="open1"
      width="1016px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <div class="jiben">基本信息</div>
        <el-form-item label="指挥员" prop="commanderName">
          <el-select
            v-model="form.commanderName"
            @change="driverNamefun"
            filterable
            clearable
            disabled
            placeholder=""
          >
            <el-option
              v-for="item in rylist"
              :key="item.id"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="指挥员电话" prop="commanderPhone">
          <el-input disabled v-model="form.commanderPhone" placeholder="" />
        </el-form-item>
        <el-form-item label="参加人数" prop="participantsName">
          <el-input disabled v-model="form.participantsName" placeholder="" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            clearable
            disabled
            size="small"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            disabled
            clearable
            size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="所在位置" prop="adress">
          <el-input disabled v-model="form.adress" placeholder="" />
        </el-form-item>
        <div class="jiben">人员名册</div>
        <!-- <el-form-item>
          <FileUpload v-model="form.taskdutyFus" />
        </el-form-item> -->
        <div
          v-for="item in this.form.taskdutyFus"
          :key="item"
          style="margin-bottom: 10px"
        >
          <span class="wordfile" style="color: white; margin-left: 10px">{{
            item.filename
          }}</span>
          <!-- <div> -->
          <span
            href="item.filepath"
            style="color: white; margin-left: 10px; cursor: pointer"
            @click="downloadss(item.filepath, item.filename)"
            >下载附件</span
          >
          <!-- </div> -->
        </div>
        <div class="jiben">事由</div>
        <el-form-item label prop="question">
          <el-input
            disabled
            v-model="form.question"
            type="textarea"
            placeholder=""
          />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item label prop="remarks">
          <el-input
            disabled
            v-model="form.remarks"
            type="textarea"
            placeholder=""
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <!-- <el-button type="primary" @click="submitForm">确 定</el-button> -->
        <el-button @click="open1 = false" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listTaskdutyinfo,
  getTaskdutyinfo,
  delTaskdutyinfo,
  addTaskdutyinfo,
  updateTaskdutyinfo,
  exportTaskdutyinfo,
  rllist,
  listPeople,
  getDept,
} from "@/api/combatduty/taskdutyinfo";

export default {
  name: "Taskdutyinfo",
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
      // 执行任务表格数据
      taskdutyinfoList: [],
      // 弹出层标题
      title: "",
      title1: "",
      // 是否显示弹出层
      open: false,
      open1: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitName: null,
        unitId: null,
        commanderName: null,
        commanderId: null,
        commanderPhone: null,
        participantsId: null,
        participantsIds: null,
        startTime: null,
        endTime: null,
        adress: null,
        question: null,
        remarks: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        // participantsName:[
        //     { type: 'Number', required: true, message: '请选择日期', trigger: 'change' }
        //   ],
      },
      //人员数据
      rylist: [],
      //单位数据
      deptOptions: [],
      qdflg: null,
    };
  },
  created() {
    this.getList();
    this.gettreeselect();
    this.getrylist();
  },
  methods: {
    downloadss(URL, name) {
      let xhr = new XMLHttpRequest();
      xhr.open("get", URL, true);
      xhr.setRequestHeader("Content-Type", `application/pdf`);
      xhr.responseType = "blob";
      let that = this;
      xhr.onload = function () {
        if (this.status == 200) {
          //接受二进制文件流
          var blob = this.response; // that.downloadExportFile(blob, fileName);
          let downloadElement = document.createElement("a");
          let href = blob;
          if (typeof blob == "string") {
            downloadElement.target = "_blank";
          } else {
            href = window.URL.createObjectURL(blob); //创建下载的链接
          }
          downloadElement.href = href;

          downloadElement.download = name; // tagFileName + //下载后文件名
          document.body.appendChild(downloadElement);
          downloadElement.click(); //点击下载
          document.body.removeChild(downloadElement); //下载完成移除元素
          if (typeof blob != "string") {
            window.URL.revokeObjectURL(href); //释放掉blob对象
          }
        }
      };
      xhr.send();
    },
    //获取人员
    getrylist() {
      listPeople().then((res) => {
        if (res.code == 200) {
          this.rylist = res.rows;
        }
      });
    },
    gettreeselect() {
      getDept().then((res) => {
        if (res.code == 200) {
          this.deptOptions = res.data;
        }
      });
    },
    //单位翻译
    // unitNameFormat(row, column) {
    //   var deptName = "";
    //   this.deptOptions.map(item => {
    //     if (item.deptId == row.unitName) {
    //       deptName = item.deptName;
    //     }
    //   });
    //   return deptName;
    // },
    unit2Format(row, column) {
      var deptName = "";
      this.rylist.map((item) => {
        row.participantsIds.forEach((items, index) => {
          if (item.id == items) {
            deptName += item.name + "  ";
          }
        });
      });

      return deptName;
    },
    /** 查询执行任务列表 */
    getList() {
      this.loading = true;
      listTaskdutyinfo(this.queryParams).then((response) => {
        this.taskdutyinfoList = response.rows;
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
        commanderName: null,
        commanderId: null,
        commanderPhone: null,
        participantsId: null,
        participantsIds: null,
        startTime: null,
        endTime: null,
        adress: null,
        question: null,
        remarks: null,
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
      this.queryParams.commanderId = null;
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
      this.title = "添加执行任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTaskdutyinfo(id).then((response) => {
        this.form = response.data;

        this.form.taskdutyFus.forEach((item) => {
          // 文件路径
          item.url = item.filepath;
          // 文件名称
          item.name = item.filename;
        });

        //  var fileNameS=[];
        //  var filename= this.form.filename.split(',')
        //  var filepath= this.form.filepath.split(',')
        //   filename.map((item,i)=>{
        //       fileNameS.push({name:filename[i],url: filepath[i]})
        //   })
        //    this.form.fileNameS=fileNameS

        this.open = true;
        this.title = "修改执行任务";
      });
    },
    // 查看按钮操作
    handlelook(row) {
      this.reset();
      const id = row.id || this.ids;
      getTaskdutyinfo(id).then((response) => {
        this.form = response.data;
        this.form.taskdutyFus.forEach((item) => {
          item.filepath = process.env.VUE_APP_BASE_API + item.filepath;
        });

        this.open1 = true;
        this.title1 = "查看执行任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      // this.form.filename = "";
      // this.form.filepath = "";

      // for (var i of this.form.fileNameS) {

      //   this.form.filename += i.name + ",";
      //   this.form.filepath += i.url + ",";
      // }
      // this.form.filename = this.form.filename.substring(
      //   0,
      //   this.form.filename.length - 1
      // );
      // this.form.filepath = this.form.filepath.substring(
      //   0,
      //   this.form.filepath.length - 1
      // );

      if (this.form.taskdutyFus) {
        this.form.taskdutyFus.forEach((item) => {
          // 文件路径
          item.filepath = item.url;
          item.url = item.filepath;
          // 文件名称
          item.filename = item.name;
          item.name = item.filename;
        });
      }

      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTaskdutyinfo(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTaskdutyinfo(this.form).then((response) => {
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
        .then(function () {
          return delTaskdutyinfo(ids);
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
        .confirm("是否确认导出所有执行任务数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportTaskdutyinfo(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    //联系方式
    driverNamefun(val) {
      this.form.commanderName = val.name;
      this.form.commanderPhone = val.phone;
      this.form.commanderId = val.id;
    },
    unitNamechange(val) {
      this.form.unitId = val.deptId;
      this.form.unitName = val.deptName;
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
  width: 920px;
  height: 124px;
}
::v-deep .el-dialog__body {
  padding: 30px 0px 30px 48px;
}
::v-deep .el-dialog__footer {
  margin-right: 28px !important;
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
/* 对话框表单间距
::v-deep .el-dialog__body .el-form-item__label {
  width: 175px !important;
}
::v-deep .el-dialog__body .el-form--inline .el-form-item {
  display: inline-block;
  margin-right: 10px;
  vertical-align: top;
  padding-left: 50px;
} */

::v-deep .el-input__inner {
  /* width: 200px !important;
  height: 36px; */
  background: transparent !important;
  color: #fff;
}
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
/* 基本信息背景 */
.jiben {
  width: 952px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
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
/* 计数器按钮 */
::v-deep .el-input-number__increase {
  background-color: transparent !important;
  border: none;
  color: #cccccc;
  border-bottom: none !important;
}
::v-deep .el-input-number__decrease {
  background-color: transparent !important;
  border: none !important;
  color: #cccccc;
}
.el-radio-group {
  width: 216px;
}
/* 状态 */
.el-checkbox__label {
  color: rgb(204, 198, 198);
}
.el-select-dropdown__item {
  /* background-color: white; */
  color: #000 !important;
}
.el-form-item ::v-deep .el-input__inner {
  width: 200px;
  height: 36px;
  background: #004d86;
  color: #fff important;
  border: 1px solid #fff !important;
}
::v-deep .el-input-number__increase {
  background-color: transparent !important;
  border: none;
  color: #cccccc;
  border-bottom: none !important;
}
::v-deep .el-input-number__decrease {
  background-color: transparent !important;
  border: none !important;
  color: #cccccc;
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
/* 选取文件 */
::v-deep .el-upload-list__item {
  width: 300px;
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
.wordfile {
  width: 840px;
  display: inline-block;
  height: 30px;
  line-height: 30px;
  border: 1px solid white;
  text-indent: 20px;
  /* text-align: center; */
  margin-bottom: 10px;
}
::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent;
  color: #fff;
}
</style>
