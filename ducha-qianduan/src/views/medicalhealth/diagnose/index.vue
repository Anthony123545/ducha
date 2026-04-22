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
          v-hasPermi="['medicalhealth:diagnose:add']"
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
          v-hasPermi="['medicalhealth:diagnose:edit']"
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
          v-hasPermi="['medicalhealth:diagnose:remove']"
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
          v-hasPermi="['medicalhealth:diagnose:export']"
          >导出</el-button
        >
      </el-col> -->
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="diagnoseList"
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
      <el-table-column label="人员" align="center" prop="peopleName" />
      <el-table-column label="地点" align="center" prop="address" />
      <el-table-column
        label="外诊日期"
        align="center"
        prop="startTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="时长" align="center" prop="duration" >
        <template slot-scope="scope">
          <span>{{scope.row.duration+"天"}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="返回日期"
        align="center"
        prop="endTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="陪护人"
        align="center"
        prop="accompanyingPerson"
      />
      <el-table-column label="登记时间" align="center" prop="createtime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updatetime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatetime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="病情" align="center" prop="id" /> -->
      <el-table-column label="病情" align="center" prop="illness" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="180px"
      >
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.state == null"
            size="btu"
            type="text"
            @click="handleUpdateGuiDui(scope.row)"
            v-hasPermi="['medicalhealth:diagnose:update']"
            >归队</el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['medicalhealth:diagnose:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medicalhealth:diagnose:remove']"
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

    <!-- 添加或修改外诊管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="700px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="jiben">基本信息</div>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            placeholder="请输入单位"
            :options="deptOptions"
            @select="shuxing"
          />
        </el-form-item>
        <el-form-item label="人员" prop="peopleId">
          <el-select v-model="form.peopleId" placeholder="请输入人员">
            <el-option
              class="input_xiala"
              v-for="(item, i) in renYuan"
              :key="i"
              :value="item.id"
              :label="item.name"
              @click.native="xingMing(item.name)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="陪护" prop="accompanyingPerson">
          <el-input
            v-model="form.accompanyingPerson"
            placeholder="请输入陪护人"
          />
        </el-form-item>
        <el-form-item label="地点" prop="address">
          <el-input v-model="form.address" placeholder="请输入地点" />
        </el-form-item>
        <el-form-item label="时长" prop="duration">
          <el-input v-model="form.duration" placeholder="请输入时长" disabled />
        </el-form-item>
        <el-form-item label="外诊日期" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择外诊日期"
            @change="changeTiem"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="返回日期" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择返回日期"
            @change="fanChangeTiem"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="登记时间" prop="createtime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.createtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择登记时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="更新时间" prop="updatetime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.updatetime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择更新时间"
          >
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="登记人" prop="adminId">
          <el-input v-model="form.adminId" placeholder="请输入登记人" />
        </el-form-item> -->
        <div class="jiben">病情</div>
        <el-form-item label="" prop="illness">
          <el-input
            v-model="form.illness"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listDiagnose,
  getDiagnose,
  delDiagnose,
  addDiagnose,
  updateDiagnose,
  exportDiagnose,
  getDept,
  getZhuChiRen,
  weiZhengGuiDui,
} from "@/api/medicalhealth/diagnose";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Diagnose",
  components: {
    Treeselect,
  },
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
      // 外诊管理表格数据
      diagnoseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitId: null,
        peopleId: null,
        address: null,
        startTime: null,
        duration: null,
        endTime: null,
        adminId: null,
        createtime: null,
        updatetime: null,
        illness: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 树形单位
      deptOptions: [],
      // 人员列表
      renYuan: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 外诊时间计算离队时长
    changeTiem() {
      if (this.form.startTime != null && this.form.endTime != null) {
        // 外出
        var Outside = new Date(this.form.startTime);
        // 返回
        var returns = new Date(this.form.endTime);
        this.form.duration = (returns - Outside) / 86400000;
      }
    },
    // 返回时间计算时长
    fanChangeTiem() {
      if (this.form.startTime != null && this.form.endTime != null) {
        // 外出
        var Outside1 = new Date(this.form.startTime);
        // 返回
        var returns1 = new Date(this.form.endTime);
        this.form.duration = (returns1 - Outside1) / 86400000;
      }
    },
    // 归队按钮触发
    handleUpdateGuiDui(row) {
      this.reset();
      this.$modal
        .confirm("是否确认归队？", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
        })
        .then(() => {
          this.form = row;
          if (this.form.id != null) {
            this.form.state = "1";
            weiZhengGuiDui(this.form).then((response) => {
              this.$modal.msgSuccess("归队成功");
              this.getList();
            });
          }
        })
        .catch(() => {});
    },

    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.deptOptions = response.data;
      });
    },
    /** 查询外诊管理列表 */
    getList() {
      this.loading = true;
      listDiagnose(this.queryParams).then((response) => {
        this.diagnoseList = response.rows;
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
        peopleId: null,
        address: null,
        startTime: null,
        duration: null,
        endTime: null,
        adminId: null,
        createtime: null,
        updatetime: null,
        illness: null,
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
      this.getTreeselect();
      this.reset();
      this.open = true;
      this.title = "添加外诊管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids;
      getDiagnose(id).then((response) => {
        this.form = response.data;
        this.getRenYuan(this.form.unitId);
        this.open = true;
        this.title = "修改外诊管理";
      });
    },
    //点击单位触发
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
    // 点击人员触发
    xingMing(name) {
      this.form.peopleName = name;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateDiagnose(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDiagnose(this.form).then((response) => {
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
      this.$confirm("是否确认删除外诊管理的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delDiagnose(ids);
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
      this.$confirm("是否确认导出所有外诊管理数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.exportLoading = true;
          return exportDiagnose(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
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
  width: 570px;
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
::v-deep .el-input__inner {
  /* width: 200px !important;
  height: 36px; */
  background: transparent;
  color: #fff;
  border: 1px solid white !important;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
/* ::v-deep .el-dialog__body {
  margin-left: 70px !important;
} */
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
/* 单位下拉菜单选中字体颜色 */
::v-deep .vue-treeselect__single-value {
  color: #fff !important;
}
/* 时间选择 */
::v-deep .el-input--small .el-input__inner {
  width: 200px;
  height: 36px;
  line-height: 36px;
}
/* 基本信息背景 */
.jiben {
  width: 576px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  background-size: 100%;
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}
::v-deep .el-dialog__footer {
  margin-right: 46px;
}
::v-deep .el-dialog__body {
  padding: 30px 0px 0px 65px;
}
::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent;
  color: #fff;
}
</style>
