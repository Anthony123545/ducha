<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入单位名称"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item label prop="occupant">
        <el-input
          v-model="queryParams.occupant"
          placeholder="请输入入住人姓名"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <!-- <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button> -->
        <el-button icon="el-icon-refresh" size="btr" @click="resetQuery">重置</el-button>
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
          v-hasPermi="['single:dormitory:add']"
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
          v-hasPermi="['single:dormitory:edit']"
        >修改</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['single:dormitory:remove']"
        >删除</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['single:dormitory:export']"
        >导出</el-button>
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="dormitoryList"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50">
        <template scope="scope">
          <span>
            {{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
            }}
          </span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="ID" align="center" prop="id" /> -->
      <el-table-column label="单位名称" align="center" prop="unitName" />
      <el-table-column label="入住人姓名" align="center" prop="occupant" />
      <el-table-column label="入住时间" align="center" prop="entryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="退房时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="位置" align="center" prop="position" />
      <!-- <el-table-column label="位置2" align="center" prop="position2" /> -->
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['single:dormitory:edit']"
          >修改</el-button>
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['single:dormitory:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
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
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" :inline="true">
        <el-form-item label="位置" prop="position">
          <el-input v-model="form.position" placeholder="请输入位置(例如：12#10)" />
        </el-form-item>
        <el-form-item label="单位名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="入住人姓名" prop="occupant">
          <el-input v-model="form.occupant" placeholder="请输入入住人姓名" />
        </el-form-item>
        <el-form-item label="入住时间" prop="entryTime">
          <el-date-picker
            clearable
            v-model="form.entryTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择入住时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="退房时间" prop="endTime">
          <el-date-picker
            clearable
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择退房时间"
          ></el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="位置2" prop="position2">
          <el-input v-model="form.position2" placeholder="请输入位置2" />
        </el-form-item>-->
        <div class="jibens">备注</div>
        <el-form-item prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
  listDormitory,
  getDormitory,
  delDormitory,
  addDormitory,
  updateDormitory,
  exportCheckin
} from "@/api/single/dormitory";

export default {
  name: "Dormitory",
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
      // 单身干部宿舍表格数据
      dormitoryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitName: null,
        occupant: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        // unitName: [
        //   { required: true, message: "单位名称不能为空", trigger: "blur" }
        // ],
        // occupant: [
        //   { required: true, message: "入住人姓名不能为空", trigger: "blur" }
        // ],
        // entryTime: [
        //   { required: true, message: "入住时间不能为空", trigger: "blur" }
        // ],
        // endTime: [
        //   { required: true, message: "退房时间不能为空", trigger: "blur" }
        // ],
        position: [{ validator: validatePass, trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询单身干部宿舍列表 */
    getList() {
      this.loading = true;
      listDormitory(this.queryParams).then(response => {
        this.dormitoryList = response.rows;
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
        unitName: null,
        occupant: null,
        entryTime: null,
        endTime: null,
        position: null,
        position2: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加单身干部宿舍";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getDormitory(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改单身干部宿舍";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDormitory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDormitory(this.form).then(response => {
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
        .confirm('是否确认删除单身干部宿舍编号为"' + ids + '"的数据项？')
        .then(function() {
          return delDormitory(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.$modal
        .confirm("是否确认导出所有临时来队数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportCheckin(this.queryParams);
        })
        .then(response => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
      // this.download(
      //   "single/dormitory/export",
      //   {
      //     ...this.queryParams
      //   },
      //   `dormitory_${new Date().getTime()}.xlsx`
      // );
    }
  }
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
::v-deep .el-icon-circle-close:before{
  margin-left: -48px;
}
</style>
