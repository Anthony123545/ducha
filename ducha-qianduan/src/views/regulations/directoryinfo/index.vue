<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="目录" prop="directory">
        <el-input
          v-model="queryParams.directory"
          placeholder="请输入目录"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父目录ID" prop="parentId">
        <el-input
          v-model="queryParams.parentId"
          placeholder="请输入父目录ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="祖级列表" prop="ancestors">
        <el-input
          v-model="queryParams.ancestors"
          placeholder="请输入祖级列表"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="子分类" prop="subclassification">
        <el-input
          v-model="queryParams.subclassification"
          placeholder="请输入子分类"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件数" prop="documentsNumber">
        <el-input
          v-model="queryParams.documentsNumber"
          placeholder="请输入文件数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人姓名" prop="createUser">
        <el-input
          v-model="queryParams.createUser"
          placeholder="请输入创建人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人ID" prop="createUserid">
        <el-input
          v-model="queryParams.createUserid"
          placeholder="请输入创建人ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="修改人姓名" prop="updateUser">
        <el-input
          v-model="queryParams.updateUser"
          placeholder="请输入修改人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="修人姓名" prop="updateUserid">
        <el-input
          v-model="queryParams.updateUserid"
          placeholder="请输入修人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
          v-hasPermi="['regulations:directoryinfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['regulations:directoryinfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['regulations:directoryinfo:remove']"
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
          v-hasPermi="['regulations:directoryinfo:export']"
        >导出</el-button>
      </el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="directoryinfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="目录" align="center" prop="directory" />
      <el-table-column label="父目录ID" align="center" prop="parentId" />
      <el-table-column label="祖级列表" align="center" prop="ancestors" />
      <el-table-column label="子分类" align="center" prop="subclassification" />
      <el-table-column label="文件数" align="center" prop="documentsNumber" />
      <el-table-column label="创建人姓名" align="center" prop="createUser" />
      <el-table-column label="创建人ID" align="center" prop="createUserid" />
      <el-table-column label="修改人姓名" align="center" prop="updateUser" />
      <el-table-column label="修人姓名" align="center" prop="updateUserid" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['regulations:directoryinfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['regulations:directoryinfo:remove']"
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

    <!-- 添加或修改规章制度目录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="目录" prop="directory">
          <el-input v-model="form.directory" placeholder="请输入目录" />
        </el-form-item>
        <el-form-item label="父目录ID" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父目录ID" />
        </el-form-item>
        <el-form-item label="子分类" prop="subclassification">
          <el-input v-model="form.subclassification" placeholder="请输入子分类" />
        </el-form-item>
        <el-form-item label="文件数" prop="documentsNumber">
          <el-input v-model="form.documentsNumber" placeholder="请输入文件数" />
        </el-form-item>
        <el-form-item label="创建人姓名" prop="createUser">
          <el-input v-model="form.createUser" placeholder="请输入创建人姓名" />
        </el-form-item>
        <el-form-item label="创建人ID" prop="createUserid">
          <el-input v-model="form.createUserid" placeholder="请输入创建人ID" />
        </el-form-item>
        <el-form-item label="修改人姓名" prop="updateUser">
          <el-input v-model="form.updateUser" placeholder="请输入修改人姓名" />
        </el-form-item>
        <el-form-item label="修人姓名" prop="updateUserid">
          <el-input v-model="form.updateUserid" placeholder="请输入修人姓名" />
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
import { listDirectoryinfo, getDirectoryinfo, delDirectoryinfo, addDirectoryinfo, updateDirectoryinfo, exportDirectoryinfo } from "@/api/regulations/directoryinfo";

export default {
  name: "Directoryinfo",
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
      // 规章制度目录表格数据
      directoryinfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        directory: null,
        parentId: null,
        ancestors: null,
        subclassification: null,
        documentsNumber: null,
        createUser: null,
        createUserid: null,
        updateUser: null,
        updateUserid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询规章制度目录列表 */
    getList() {
      this.loading = true;
      listDirectoryinfo(this.queryParams).then(response => {
        this.directoryinfoList = response.rows;
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
        directory: null,
        parentId: null,
        ancestors: null,
        subclassification: null,
        documentsNumber: null,
        createUser: null,
        createUserid: null,
        createTime: null,
        updateUser: null,
        updateUserid: null,
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加规章制度目录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDirectoryinfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改规章制度目录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDirectoryinfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDirectoryinfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除规章制度目录编号为"' + ids + '"的数据项？').then(function() {
        return delDirectoryinfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有规章制度目录数据项？').then(() => {
        this.exportLoading = true;
        return exportDirectoryinfo(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
