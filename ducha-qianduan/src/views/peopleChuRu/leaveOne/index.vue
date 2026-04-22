<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label prop="unitId">
        <treeselect
          v-model="queryParams.unitId"
          :options="deptOptions"
          placeholder="请选择单位"
          @select="selectPeos"
          @open="blurSelect"
        />
      </el-form-item>
      <el-form-item>
        <!-- <el-button type="primary" size="btn" @click="handleQuery"
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
          v-hasPermi="['peopleChuRu:leaveOne:add']"
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
          v-hasPermi="['peopleChuRu:leaveOne:edit']"
          >修改</el-button
        >
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['peopleChuRu:leaveOne:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="leaveOneList"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      @selection-change="handleSelectionChange"
      :cell-class-name="(row) => (row.row.statusun == 1 ? 'baccolor' : '')"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="序号" align="center" type="index" /> -->
      <el-table-column label="序号" align="center" prop="id">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请假人员" align="center" prop="peopleId" />
      <el-table-column label="单位" align="center" prop="unitId" :formatter="unit3Format" />
      <!-- <el-table-column label="单位" align="center" prop="unitName" /> -->
      <el-table-column label="请假开始时间" align="center" prop="startTime" width="180">
        <!-- <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.startTime, "{y}-{m}-{d} {h}:{m}:{s}")
          }}</span>
        </template>-->
      </el-table-column>
      <el-table-column label="请假结束时间" align="center" prop="endTime" width="180">
        <!-- <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.endTime, "{y}-{m}-{d} {h}:{m}:{s}")
          }}</span>
        </template>-->
      </el-table-column>
      <el-table-column label="请假类型" align="center" prop="leaveType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.vocation_state" :value="scope.row.leaveType" />
        </template>
      </el-table-column>
      <el-table-column label="请假原因" align="center" prop="leaveReason" show-overflow-tooltip />
      <el-table-column label="离队时间" align="center" prop="departureTime" width="180">
        <!-- <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.departureTime, "{y}-{m}-{d} {h}:{m}:{s}")
          }}</span>
        </template>-->
      </el-table-column>
      <el-table-column label="归队时间" align="center" prop="returnTime" width="180">
        <!-- <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.returnTime, "{y}-{m}-{d} {h}:{m}:{s}")
          }}</span>
        </template>-->
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.returnTime == null"
            type="text"
            size="btu"
            @click="handleReturnTeam(scope.row)"
          >归队</el-button>
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['peopleChuRu:leaveOne:remove']"
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

    <!-- 添加请假对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      :close-on-click-modal="false"
      width="500px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" inline>
        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="deptOptions"
            placeholder="请选择单位"
            @select="selectPeo"
            @open="blurSelect"
          />
        </el-form-item>
        <el-form-item label="人员" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请选择请假人员"
            ref="headerSearchSelect"
            clearable
            filterable
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="
                renYuanname(item.name)
              "
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="批准部门人" prop="approvalDepartment">
          <el-input v-model="form.approvalDepartment" type="text" placeholder="请输入批准部门人" />
        </el-form-item>
        <el-form-item label="请假类型" prop="leaveType">
          <el-select v-model="form.leaveType" placeholder="请选择请假类型" clearable>
            <el-option
              v-for="dict in ziDian"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="请假开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="请假结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="离队时间" prop="departureTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.departureTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="请选择离队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <div class="jiben">请假原因</div>
        <el-form-item label prop="leaveReason">
          <el-input v-model="form.leaveReason" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 归队请假对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open1"
      :close-on-click-modal="false"
      width="500px"
      append-to-body
    >
      <el-form ref="form1" :model="form1" :rules="rules1" label-width="80px" inline>
        <el-form-item label="单位" prop="unitId">
          <treeselect v-model="form1.unitId" :options="deptOptions" placeholder="请选择单位" />
        </el-form-item>
        <el-form-item label="人员" prop="peopleId">
          <el-select
            v-model="form1.peopleId"
            placeholder="请选择请假人员"
            ref="headerSearchSelect"
            clearable
            filterable
          >
            <el-option v-for="(item, i) in renYuan" :key="i" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="批准部门" prop="approvalDepartment">
          <el-input v-model="form1.approvalDepartment" type="text" placeholder="请输入批准部门人" />
        </el-form-item>
        <el-form-item label="请假类型" prop="leaveType">
          <el-select v-model="form1.leaveType" placeholder="请选择请假类型" clearable>
            <el-option
              v-for="dict in ziDian"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="请假开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form1.startTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="请假结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form1.endTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="离队时间" prop="departureTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form1.departureTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="请选择离队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="归队时间" prop="returnTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form1.returnTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="请选择归队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <div class="jiben">请假原因</div>
        <el-form-item label prop="leaveReason">
          <el-input v-model="form1.leaveReason" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm1">确 定</el-button>
        <el-button @click="cancel1" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listLeaveOne,
  getLeaveOne,
  delLeaveOne,
  addLeaveOne,
  updateLeaveOne,
  exportLeaveOne,
  getDept2,
  returnTeam
} from "@/api/peopleChuRu/leaveOne";
import {
  getUser,
  getDept,
  getQuanBu
} from "@/api/grassrootsregistration/bdglmeeting";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import Cookies from "js-cookie";
// 查询字典
import { getDicts } from "@/api/system/dict/data";
import { listPeople } from "@/api/peopleManage/people";

export default {
  name: "LeaveOne",
  components: {
    Treeselect
  },
  dicts: ["vocation_state"],
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
      // 人员请假表格数据
      leaveOneList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        peopleId: null,
        unitId: null,
        startTime: null,
        endTime: null,
        leaveType: null,
        leaveReason: null,
        departureTime: null,
        returnTime: null,
        returnStatus: null,
        isReturn: null,
        updatetime: null,
        adminId: null,
        createtime: null,
        peopleId1: null,
        postId: null,
        peopleId2: null,
        peopleId3: null,
        peopleId4: null,
        status1: null,
        status2: null,
        status3: null,
        status4: null,
        contents: null,
        statusd: null,
        rejoin: null,
        unitName: null,
        peopleName: null,
        peopleName1: null,
        peopleName2: null,
        peopleName3: null,
        peopleName4: null,
        reasons: null,
        specialApproval: null,
        actualreturntime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        startTime: [
          { required: true, message: "开始不能为空", trigger: "blur" }
        ],
        unitId: [{ required: true, message: "单位不能为空", trigger: "blur" }],
        peopleId: [
          { required: true, message: "请假人员不能为空", trigger: "blur" }
        ],
        approvalDepartment: [
          { required: true, message: "批准部门人不能为空", trigger: "blur" }
        ]
      },
      // 树形结构列表
      deptOptions: [],
      renYuan: [],
      unitlist: [],
      // 查询字典请假列表
      dictType: {
        dictType: "vocation_state"
      },
      // 字典的列表
      ziDian: [],
      // 修改拿到的数据
      result: {},
      form1: {},
      rules1: {
        returnTime: [
          { required: true, message: "归队时间不能为空", trigger: "blur" }
        ],
        startTime: [
          { required: true, message: "开始不能为空", trigger: "blur" }
        ],
        unitId: [{ required: true, message: "单位不能为空", trigger: "blur" }],
        peopleId: [
          { required: true, message: "请假人员不能为空", trigger: "blur" }
        ],
        approvalDepartment: [
          { required: true, message: "批准部门人不能为空", trigger: "blur" }
        ]
      },
      open1: false
    };
  },
  created() {
    this.getdict();
    this.getList();
    // 获取单位树形
    this.getBuMeng();
    
  },
  mounted() {
    getDept2().then(res => {
      this.unitlist = res.data;
    });
  },
  methods: {
    renYuanname(name) {
      listPeople({ name: name }).then(response => {
        this.form.leaveType = response.rows[0].reign + "";
      });
    },
    // 查询请假类型
    getdict() {
      getDicts("vocation_state").then(res => {
        // res.data.splice(0, 1);
        this.ziDian = res.data;
      });
    },
    unit3Format(row, column) {
      var deptName = "";
      this.unitlist.map(item => {
        if (item.deptId == row.unitId) {
          deptName = item.deptName;
        }
      });
      return deptName;
    },
    // 获取部门列表
    getBuMeng() {
      getDept().then(res => {
        this.deptOptions = res.data;
      });
    },
    selectPeos(data) {
      this.queryParams.unitId = data.id;
      this.ChongZhiRen();
      this.form.unitId = data.id;
      this.getYong(this.form.unitId);
      this.getRen(this.form.unitId, 0);
      this.getList();
    },
    // 选择部门单位触发
    selectPeo(data) {
      this.ChongZhiRen();
      this.form.unitId = data.id;
      this.getYong(this.form.unitId);
      this.getRen(this.form.unitId, 0);
    },
    // 重置表单人员
    ChongZhiRen() {
      this.form.peopleId = null;
      this.form.peopleId1 = null;
      this.form.peopleId2 = null;
      this.form.peopleId3 = null;
      this.form.peopleId4 = null;
    },
    // 获取用户列表
    getYong(id) {
      getUser(id).then(res => {
        this.yongHu = res.data;
      });
    },
    // 获取人员
    getRen(id, num) {
      getQuanBu(id, num).then(response => {
        this.renYuan = response.data;
      });
    },
    // 让单位失去焦点
    blurSelect() {
      this.$refs.headerSearchSelect.blur();
    },
    // 请假人选中
    qingRen(name) {
      this.form.peopleName = name;
    },
    /** 查询人员请假列表 */
    getList() {
      this.loading = true;
      // 获取单位树形
      this.getBuMeng();
      listLeaveOne(this.queryParams).then(response => {
        this.leaveOneList = response.rows;
        this.chaoFn(response.rows);
        this.total = response.total;
        this.loading = false;
      });
    },
    // 计算超时
    chaoFn(rows) {
      rows.forEach((item, i) => {
        if (
          Date.parse(new Date(item.endTime)) <
          Date.parse(new Date(item.returnTime))
        ) {
          this.leaveOneList[i].statusun = 1;
        }
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    cancel1() {
      this.open1 = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        peopleId: null,
        unitId: null,
        startTime: null,
        endTime: null,
        leaveType: null,
        leaveReason: null,
        departureTime: null,
        returnTime: null,
        returnStatus: "0",
        isReturn: null,
        updatetime: null,
        adminId: null,
        createtime: null,
        peopleId1: null,
        postId: null,
        peopleId2: null,
        peopleId3: null,
        peopleId4: null,
        status1: null,
        status2: null,
        status3: null,
        status4: null,
        contents: null,
        statusd: null,
        rejoin: null,
        unitName: null,
        peopleName: null,
        peopleName1: null,
        peopleName2: null,
        peopleName3: null,
        peopleName4: null,
        reasons: null,
        specialApproval: null,
        actualreturntime: null
      };
      this.form1 = {
        id: null,
        peopleId: null,
        unitId: null,
        startTime: null,
        endTime: null
      };
      this.resetForm("form");
      this.resetForm("form1");
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加人员请假";
      // 获取单位树形
      this.getBuMeng();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getLeaveOne(id).then(response => {
        this.result = response.data;
        this.form1 = this.result;
        // 获取单位树形
        this.getBuMeng();
        this.getRen(this.form1.unitId);
        this.open1 = true;
        this.title = "人员请假归队";
      });
    },
    /** 归队按钮操作 */
    handleReturnTeam(row) {
      this.$modal
        .confirm(`是否确认将"${row.peopleId}"归队？`)
        .then(() => {
          return returnTeam(row.id);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("归队成功");
        })
        .catch(() => {});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addLeaveOne(this.form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    submitForm1() {
      this.$refs["form1"].validate(valid => {
        if (valid) {
          updateLeaveOne(this.form1).then(response => {
            this.$modal.msgSuccess("人员归队成功");
            this.open1 = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除人员请假的数据项？")
        .then(function() {
          return delLeaveOne(ids);
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
        .confirm("是否确认导出所有人员请假数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportLeaveOne(queryParams);
        })
        .then(response => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    }
  }
};
</script>
<style scoped>
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
  width: 800px !important;
}
::v-deep .el-textarea__inner {
  width: 920px;
  height: 104px;
  margin: auto;
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
  width: 108px !important;
}
::v-deep .el-input__inner {
  /* width: 200px !important;
  height: 36px; */
  background: transparent;
  color: #fff;
  border: 1px solid white !important;
}
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
/* 基本信息背景 */
.jiben {
  width: 920px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  background-size: 100%;
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
::v-deep .el-input__inner {
  cursor: pointer !important;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 18px 50px !important;
  margin-right: 19px !important;
}
::v-deep .el-dialog__body {
  margin: 10px 0px 20px 51px;
  padding-top: 20px !important;
  box-sizing: border-box;
  /* padding: 30px 12px 30px 28px; */
}
.contents {
  padding: 0px 40px !important;
}
/* 下拉菜单 */
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.el-select-dropdown__item:hover {
  color: #000 !important;
}
.el-select-dropdown__item {
  color: #000 !important;
}
/* 下拉菜单字体/背景颜色 */
.el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
  background-color: #004d86;
  color: #fff;
}
.el-select-dropdown__item {
  color: #fff;
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
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
  color: #fff;
}
/* 单位下拉菜单选中字体颜色 */
::v-deep .vue-treeselect__single-value {
  color: #fff !important;
}
/* 分页按钮 */
::v-deep .el-pagination.is-background .el-pager li {
  background-color: #004d86;
  color: #fff;
}
::v-deep .el-pagination.is-background .btn-next {
  background-color: #004d86;
  color: #fff;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 18px 50px;
  margin-right: 42px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px !important;
}
::v-deep .el-dialog__body {
  padding: none !important;
}
/* 小手样式 */
::v-deep .el-table__cell {
  cursor: pointer;
}
.el-button--mini {
  width: 80px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #1890ff;
}
/* ::v-deep .el-table__body tr.hover-row > td.el-table__cell {
  background-color: none;
} */
::v-deep .el-table .el-table__body .el-table__row.hover-row td {
  background-color: #004d86;
}
::v-deep .el-table__fixed-right {
  height: 100% !important;
}
::v-deep .vue-treeselect__control {
  background-color: transparent !important;
}
::v-deep .el-table__row .el-table_4_column_41 .el-input__inner {
  background: transparent !important;
}

::v-deep .baccolor {
  color: rgb(255, 73, 73) !important;
}
</style>
