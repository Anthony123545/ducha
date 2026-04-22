<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="100px"
    >
      <!-- <el-form-item label="单位名称" prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入单位名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="位置" prop="common">
        <el-input
          v-model="queryParams.common"
          placeholder="请输入位置"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          clearable
          size="small"
          placeholder="请输入单位名称"
          @input="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="入住时间" prop="entryTime">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.entryTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择入住时间"
        >
        </el-date-picker>
      </el-form-item> -->
      <!-- <el-form-item label="退房时间" prop="endTime">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择退房时间"
        >
        </el-date-picker>
      </el-form-item> -->
      <el-form-item>
        <!-- <el-button size="btn" @click="handleQuery">搜索</el-button> -->
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
          v-hasPermi="['barracksManagement:checkin:add']"
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
          v-hasPermi="['barracksManagement:checkin:edit']"
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
          v-hasPermi="['barracksManagement:checkin:remove']"
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
          v-hasPermi="['barracksManagement:checkin:export']"
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
      :data="checkinList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>

      <el-table-column label="单位名称" align="center" prop="unitName" />
      <el-table-column label="位置" align="center" prop="common" />
      <el-table-column label="入住人姓名" align="center" prop="occupant" />
      <el-table-column label="入住时间" align="center" prop="entryTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="退房时间" align="center" prop="endTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="备注"
        align="center"
        prop="remark"
        show-overflow-tooltip
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['barracksManagement:checkin:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['barracksManagement:checkin:remove']"
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

    <!-- 添加或修改临时来队对话框 -->
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
        label-width="100px"
        :inline="true"
      >
        <el-form-item label="位置" prop="common">
          <el-input v-model="form.common" />
        </el-form-item>
        <el-form-item label="入住单位" prop="unitName">
          <el-input v-model="form.unitName" />
        </el-form-item>
        <!-- <el-form-item label="单位id" prop="unitId">
          <el-input v-model="form.unitId" placeholder="请输入单位id" />
        </el-form-item> -->
        <!-- <el-form-item label="创建人id" prop="creatId">
          <el-input v-model="form.creatId" placeholder="请输入创建人id" />
        </el-form-item> -->
        <el-form-item label="入住人员" prop="occupant">
          <el-input v-model="form.occupant" />
        </el-form-item>
        <el-form-item label="入住时间" prop="entryTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.entryTime"
            type="date"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="退房时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <div class="jibens">备注</div>
        <el-form-item prop="remark">
          <el-input
            v-model="form.remark"
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
  listCheckin,
  getCheckin,
  delCheckin,
  addCheckin,
  updateCheckin,
  exportCheckin,
} from "@/api/barracksManagement/checkin";

export default {
  name: "Checkin",
  data() {
    var validatePass = (rule, value, callback) => {
      var MobileRegex = /^(\d+)*#+(\d+)$/;
      if (!MobileRegex.test(value)) {
        callback(new Error("位置格式不正确！"));
      } else {
        callback();
      }
    };
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
      // 临时来队表格数据
      checkinList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitName: null,
        unitId: null,
        creatTime: null,
        creatId: null,
        position: null,
        occupant: null,
        entryTime: null,
        endTime: null,
        creatName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        common: [{ validator: validatePass, trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询临时来队列表 */
    getList() {
      this.loading = true;
      listCheckin(this.queryParams).then((response) => {
        this.checkinList = response.rows;

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
        creatTime: null,
        creatId: null,
        position: null,
        occupant: null,
        entryTime: null,
        endTime: null,
        creatName: null,
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
      this.queryParams = {};
      this.resetForm("queryForm");
      this.handleQuery();
      this.getList();
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
      this.title = "添加临时来队";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getCheckin(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改临时来队";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateCheckin(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCheckin(this.form).then((response) => {
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
        .confirm("是否确认删除？")
        .then(function () {
          return delCheckin(ids);
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
        .confirm("是否确认导出所有临时来队数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportCheckin(queryParams);
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
::v-deep .el-dialog {
  background-color: #004d86 !important;
}
/* 输入框 */
::v-deep .el-dialog .el-input__inner {
  background-color: #004d86 !important;
  width: 220px;
  color: white;
  margin-right: 20px;
}
/* 标题弹框 */
::v-deep .el-dialog__title {
  color: white;
  /* border-bottom: 1px solid white; */
}
::v-deep .el-input__inner {
  border: 1px solid white !important;
}
/* 标题下划线 */
::v-deep .el-dialog__header {
  border-bottom: 1px solid white;
}
/* 文本域 */
::v-deep .el-textarea__inner {
  width: 634px;
  height: 80px;
}
::v-deep .el-form-item__label {
  text-align: end;
}
::v-deep .el-dialog__body {
  padding: 0px;
  margin-left: 84px;
  margin-top: 20px;
}
::v-deep .el-table .el-table__header-wrapper th,
.el-table .el-table__fixed-header-wrapper th {
  background-color: rgb(0, 60, 105);
  color: white;
}
::v-deep .el-date-editor .el-input__inner {
  height: 36px;
}
::v-deep .el-input--small .el-input__inner {
  color: white;
}
::v-deep .el-table__cell .cell {
  text-align: center;
}
/* 文字多余部分省略 */
::v-deep .el-table__cell .cell {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.jibens {
  width: 670px;
  height: 32px;
  background-image: url("/static/img/小标题底.b7c6f45a.png");
  background-size: 100%;
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}
.dialog-footer {
  margin-right: 60px;
}
::v-deep .el-form-item__content .el-input__inner {
  width: 200px;
  height: 36px;
}
</style>
