<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['peopleChuRu:peopleBorrow:add']"
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
          v-hasPermi="['peopleChuRu:peopleBorrow:edit']"
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
          v-hasPermi="['peopleChuRu:peopleBorrow:remove']"
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
          v-hasPermi="['peopleChuRu:peopleBorrow:export']"
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
      :data="peopleBorrowList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id">
        <template scope="scope">
          <span>{{
              (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
            }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column
        label="借调人姓名"
        align="center"
        prop="transferPeople"
      />
      <!-- <el-table-column
        label="借调人id"
        align="center"
        prop="transferPeopleId"
      /> -->

      <!-- <el-table-column label="单位id" align="center" prop="unitId" /> -->
      <el-table-column
        label="借调单位"
        align="center"
        prop="transferUnitName"
      />
      <!-- <el-table-column
        label="借调单位id"
        align="center"
        prop="transferUnitId"
      /> -->
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
      <el-table-column label="借调事由" align="center" prop="transferReason" />
      <el-table-column label="职位" align="center" prop="jobTitle">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.post_Level"
            :value="scope.row.jobTitle"
          />
        </template>
      </el-table-column>
      <el-table-column label="军衔" align="center" prop="rankId">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_user_rank"
            :value="scope.row.rankId"
          />
        </template>
      </el-table-column>
      <!-- <el-table-column label="创建人" align="center" prop="creatName" /> -->
      <el-table-column label="人员状态" align="center" prop="state">
        <template slot-scope="scope">
          <span v-if="scope.row.state == '0'" style="color: #ff4949"
          >未归队</span
          >
          <span v-if="scope.row.state == '1'" style="color: #13ce66"
          >已归队</span
          >
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.common == '1'"
            size="btu"
            type="text"
            v-hasPermi="['peopleChuRu:bdglLeave:guidui']"
            @click="handle(scope.row)"
          >归队</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['peopleChuRu:peopleBorrow:remove']"
          >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    /> -->

    <!-- 添加或修改人员借调对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="800px"
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
        <el-form-item label="单位" prop="unitId">
          <!-- <el-input v-model="form.unitName" placeholder="请输入单位" /> -->
          <treeselect
            v-model="form.unitId"
            :options="deptOptions"
            placeholder="请选择单位"
            @select="selectPeo"
            @open="blurSelect"
          />
        </el-form-item>
        <el-form-item />
        <el-form-item label="借调人姓名" prop="transferPeople">
          <el-select
            v-model="form.transferPeopleId"
            placeholder="请输入借调人姓名"
            ref="headerSearchSelect"
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="
                jieRen(item.name, item.rankId, item.jobTitle, item)
              "
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="借调单位名称" prop="transferUnitName">
          <el-input
            v-model="form.transferUnitName"
            placeholder="请输入借调单位名称"
          />
        </el-form-item>
        <el-form-item label="借调开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择借调开始时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="借调结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择借调结束时间"
          >
          </el-date-picker>
        </el-form-item>
        <div class="jiben">借调事由</div>
        <el-form-item prop="transferReason">
          <el-input
            v-model="form.transferReason"
            type="textarea"
            placeholder="请输入借调原因"
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
  listPeopleBorrow,
  getPeopleBorrow,
  delPeopleBorrow,
  addPeopleBorrow,
  updatePeopleBorrow,
  exportPeopleBorrow,
} from "@/api/peopleChuRu/peopleBorrow";

import { getZaiRen, getDept } from "@/api/grassrootsregistration/bdglmeeting";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "PeopleBorrow",
  dicts: ["post_Level", "sys_user_rank"],
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
      // 人员借调表格数据
      peopleBorrowList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        transferPeople: null,
        transferPeopleId: null,
        unitName: null,
        unitId: null,
        transferUnitName: null,
        transferUnitId: null,
        startTime: null,
        endTime: null,
        transferReason: null,
        jobTitle: null,
        rankId: null,
        creatName: null,
        state: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitId: [{ required: true, message: "单位不能为空", trigger: "blur" }],
        transferPeople: [
          { required: true, message: "人员不能为空", trigger: "blur" },
        ],
      },
      // 树形结构列表
      deptOptions: [],
      // 人员列表
      renYuan: [],
      // 归队按钮的显示与隐藏
      anNiu: true,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 让select失去焦点
    blurSelect() {
      this.$refs.headerSearchSelect.blur();
    },
    // 重置表单人员
    ChongZhiRen() {
      this.form.transferPeopleId = null;
    },
    // 请假人选中
    jieRen(name, rankId, jobTitle, data) {
      this.form.transferPeople = name;
      this.form.rankId = rankId;
      this.form.jobTitle = jobTitle;
    },
    // 获取部门列表
    getBuMeng() {
      getDept().then((res) => {
        this.deptOptions = res.data;
      });
    },
    // 选择部门单位触发
    selectPeo(data) {
      this.form.unitId = data.id;
      this.getRen(this.form.unitId, 0);
      this.ChongZhiRen();
    },
    // 获取人员
    getRen(id, num) {
      getZaiRen(id, num).then((response) => {
        this.renYuan = response.data;
      });
    },
    /** 查询人员借调列表 */
    getList() {
      this.loading = true;
      listPeopleBorrow(this.queryParams).then((response) => {
        this.peopleBorrowList = response.rows;
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
        transferPeople: null,
        transferPeopleId: null,
        unitName: null,
        unitId: null,
        transferUnitName: null,
        transferUnitId: null,
        startTime: null,
        endTime: null,
        transferReason: null,
        jobTitle: null,
        rankId: null,
        createTime: null,
        creatName: null,
        state: null,
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
      this.getBuMeng();
      (this.renYuan = []), this.reset();
      this.open = true;
      this.title = "添加人员借调";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getBuMeng();
      this.reset();
      const id = row.id || this.ids;
      getPeopleBorrow(id).then((response) => {
        this.form = response.data;
        this.getRen(this.form.unitId);
        this.open = true;
        this.title = "修改人员借调";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updatePeopleBorrow(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.state = "0";
            addPeopleBorrow(this.form).then((response) => {
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
        .confirm("是否确认删除人员借调的数据项？")
        .then(function () {
          return delPeopleBorrow(ids);
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
        .confirm("是否确认导出所有人员借调数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportPeopleBorrow(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    // 点击归队弹出询问框
    handle(row) {
      this.form.id = row.id;
      this.$confirm("是否确认归队？", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.form.state = "1";
          updatePeopleBorrow(this.form).then((response) => {
            this.$modal.msgSuccess("归队成功");
            this.getList();
          });
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
  width: 650px;
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
  width: 100px !important;
}

::v-deep .el-input__inner {
  /* width: 200px !important;
  height: 36px; */
  background: transparent;
  color: #fff;
  border-color: #fff;
  border: 1px solid white !important;
}

/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}

/* 基本信息背景 */
.jiben {
  width: 660px;
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
  margin-right: 28px !important;
}

::v-deep .el-dialog__body {
  /* margin: 10px 30px 20px 30px; */
  margin-left: 56px;
  margin-top: 10px;
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

/* 刷新图标 */
/* .el-icon-refresh {
  width: 76px;
  height: 36px;
  color: #fff;
  background-color: #1d96ff !important;
  border-radius: 4px;
  border: none;
  margin-left: 20px;
  font-size: 14px;
} */
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
/* ::v-deep .el-dialog {
  width: 1060px !important;
} */
::v-deep .el-dialog__body {
  padding: none !important;
}

/* 表格对其 */
.el-form--inline .el-form-item {
  margin-right: 50px;
}
</style>
