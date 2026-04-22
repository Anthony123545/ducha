<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="查询类型" prop="searchType">
        <el-select v-model="queryParams.searchType" placeholder="选择查询类型" clearable size="small">
          <el-option label="姓名" value="name"></el-option>
          <el-option label="所属单位" value="company"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="关键字" prop="searchKeyword">
        <el-input
          v-model="queryParams.searchKeyword"
          placeholder="请输入关键字"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item>
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
          v-hasPermi="['peopleManage:visitor:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['peopleManage:visitor:remove']"
        >删除</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['peopleManage:visitor:export']"
        >导出</el-button>
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="visitorList"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="工号" align="center" prop="employeeNumber" />
      <el-table-column label="身份证号" align="center" prop="idCard" width="170" />
      <el-table-column label="电话号码" align="center" prop="phone" />
      <el-table-column label="所属单位" align="center" prop="company" />
      <el-table-column label="车牌号" align="center" prop="plateNumber" />
      <el-table-column label="照片" align="center" prop="avatar">
          <template slot-scope="scope">
            <el-image v-if="scope.row.avatar" :src="baseUrl + scope.row.avatar" style="width: 40px; height: 40px;" :preview-src-list="[baseUrl + scope.row.avatar]"></el-image>
            <span v-else>无照片</span>
          </template>
        </el-table-column>
      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['peopleManage:visitor:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row)"
            v-hasPermi="['peopleManage:visitor:remove']"
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

    <!-- 添加或修改访客管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入访客姓名" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="电话号码" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话号码" />
        </el-form-item>
        <el-form-item label="所属单位" prop="company">
          <el-input v-model="form.company" placeholder="请输入所属单位" />
        </el-form-item>
        <el-form-item label="车牌号" prop="plateNumber">
          <el-input v-model="form.plateNumber" placeholder="请输入车牌号（非必填）" />
        </el-form-item>
        <el-form-item label="照片" prop="avatar">
          <imageUpload :limit="1" v-model="form.avatar" style="height: 200px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listVisitor, getVisitor, addVisitor, updateVisitor, delVisitor, exportVisitor } from '@/api/peopleManage/visitor';

export default {
  name: 'Visitor',
  dicts: [],
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
        // 访客管理表格数据
        visitorList: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          searchType: 'name',
          searchKeyword: null,
          name: null,
          idCard: null,
          phone: null,
          company: null
        },
        // 表单参数
        form: {
          employeeNumber: null,
          plateNumber: null,
          avatar: null
        },
        // 基础URL
        baseUrl: process.env.VUE_APP_BASE_API,
        // 表单校验
        rules: {
          name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
          idCard: [
            { required: true, message: "身份证号不能为空", trigger: "blur" }
          ],
          phone: [
            { required: true, message: "电话号码不能为空", trigger: "blur" },
            { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur" }
          ],
          company: [{ required: true, message: "所属单位不能为空", trigger: "blur" }]
        }
      };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询访客管理列表 */
    getList() {
      this.loading = true;
      listVisitor(this.queryParams).then(response => {
        this.visitorList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      // 根据选择的查询类型设置相应的查询参数
      if (this.queryParams.searchType && this.queryParams.searchKeyword) {
        // 清除其他查询字段的值，避免干扰
        this.queryParams.name = null;
        this.queryParams.company = null;
        
        // 根据选择的类型设置对应的查询字段
        if (this.queryParams.searchType === 'name') {
          this.queryParams.name = this.queryParams.searchKeyword;
        } else if (this.queryParams.searchType === 'company') {
          this.queryParams.company = this.queryParams.searchKeyword;
        }
      } else {
        // 如果没有选择查询类型或没有输入关键字，清除查询字段
        this.queryParams.name = null;
        this.queryParams.company = null;
      }
      
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // 重置查询参数
      this.queryParams.searchType = 'name';
      this.queryParams.searchKeyword = null;
      this.queryParams.name = null;
      this.queryParams.company = null;
      this.queryParams.idCard = null;
      this.queryParams.phone = null;
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
      this.title = "添加访客管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getVisitor(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改访客管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 直接使用form数据
          if (this.form.id != null) {
            updateVisitor(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addVisitor(this.form).then(response => {
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
        .confirm("是否确认删除访客管理的数据项？")
        .then(function() {
          return delVisitor(ids);
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
      this.$modal.confirm("是否确认导出所有访客管理数据？").then(() => {
        this.exportLoading = true;
        return exportVisitor(queryParams);
      }).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {
        this.exportLoading = false;
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
          name: null,
          employeeNumber: null,
          idCard: null,
          phone: null,
          company: null,
          plateNumber: null,
          avatar: null
        };
      this.resetForm("form");
    }
  }
};
</script>

<style scoped>
/* 查询表单样式修复 */
::v-deep .el-form--inline .el-form-item {
  margin-right: 10px;
  margin-bottom: 10px;
}

::v-deep .el-form--inline .el-form-item__label {
  color: #fff;
  padding-right: 8px;
}

::v-deep .el-dialog {
  background-color: #004d86;
}

::v-deep .el-dialog__header {
  border-bottom: 1px solid #718a9d;
}

::v-deep .el-dialog__title {
  color: #fff;
}

::v-deep .el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}

.el-form-item ::v-deep .el-input__inner,
.el-form-item ::v-deep .el-select__input,
.el-form-item ::v-deep .el-textarea__inner {
  background: transparent !important;
  color: #fff;
  border: 1px solid #fff !important;
}

::v-deep .el-date-editor .el-input__inner {
  background: transparent !important;
  color: #fff;
  border: 1px solid #fff !important;
}

::v-deep .el-select-dropdown__item {
  color: #000 !important;
}

::v-deep .el-dialog__body {
  color: #fff;
}
</style>