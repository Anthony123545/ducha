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
      <!-- <el-form-item label="上传人" prop="upPeople">
        <el-input
          v-model="queryParams.upPeople"
          placeholder="请输入上传人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item label prop="upTime">
        <el-date-picker
          clearable
          v-model="queryParams.upTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择上传时间"
          @change="time"
        ></el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="上传单位" prop="upUnit">
        <el-input
          v-model="queryParams.upUnit"
          placeholder="请输入上传单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input
          v-model="queryParams.remarks"
          placeholder="请输入备注"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item>
        <!-- <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button> -->
        <el-button icon size="btr" @click="resetQuery">重置</el-button>
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
          v-hasPermi="['grassrootsregistration:cookbookPrc:add']"
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
          v-hasPermi="['grassrootsregistration:cookbookPrc:edit']"
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
          v-hasPermi="['grassrootsregistration:cookbookPrc:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['grassrootsregistration:cookbookPrc:export']"
        >导出</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="cookbookPrcList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="${comment}" align="center" prop="id" />-->
      <el-table-column label="序号" type="index" width="100" align="center">
        <template scope="scope">
          <span>
            {{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
            }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="上传图片" align="center" prop="urlImage">
        <template slot-scope="scope">
          <img :src="downurl +scope.row.urlImage" alt :width="50" :height="50" />
          <!-- <image :src="downurl +scope.row.urlImage" :width="50" :height="50" /> -->
          <!-- <span>{{ parseTime(scope.row.upTime, '{y}-{m}-{d}') }}</span> -->
        </template>
      </el-table-column>
      <!-- <el-table-column label="上传人" align="center" prop="upPeople" /> -->
      <el-table-column label="上传时间" align="center" prop="upTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.upTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="上传单位" align="center" prop="upUnit" /> -->
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="btk" type="text" @click="handleChakan(scope.row)">
            <span class="chakan">预览</span>
          </el-button>
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['grassrootsregistration:cookbookPrc:edit']"
          >
            <span>修改</span>
          </el-button>
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['grassrootsregistration:cookbookPrc:remove']"
          >
            <span class="delete">删除</span>
          </el-button>
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

    <!-- 添加或修改一周工作食普图对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1016px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="图片存放地址">
          <image-upload v-model="form.urlImage" />
        </el-form-item>-->
        <!-- <el-form-item label="上传时间" prop="upTime">
          <el-date-picker
            clearable
            v-model="form.upTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择上传时间"
          ></el-date-picker>
        </el-form-item>-->
        <div class="jiben">图片上传</div>
        <!-- <quill-editor
            class="quill-editor"
            ref="myTextEditor"
            v-model="form.contents"
            style="height: 150px; width: 920px; margin-left: 0px"
        ></quill-editor>-->
        <ImageUpload v-model="form.urlImage"></ImageUpload>
        <div class="jiben" style="margin-top:20px;">上传时间</div>
        <el-date-picker
          clearable
          v-model="form.upTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择上传时间"
        ></el-date-picker>
        <div class="jiben" style="margin-top:20px;">备注</div>
        <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" autosize />
        <!-- <el-input v-model="form.remarks" placeholder="请输入备注" /> -->
        <!-- <el-form-item label="上传人" prop="upPeople">
          <el-input v-model="form.upPeople" placeholder="请输入上传人" />
        </el-form-item>-->

        <!-- <el-form-item label="上传单位" prop="upUnit">
          <el-input v-model="form.upUnit" placeholder="请输入上传单位" />
        </el-form-item>-->
        <!-- <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注" />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :title="title"
      :visible.sync="open2"
      width="1016px"
      append-to-body
      :close-on-click-modal="false"
    >
      <img class="urlImage" :src="downurl + urlImage" alt />
      <div slot="footer" class="dialog-footer">
        <!-- <el-button type="primary" @click="submitForm2">确 定</el-button> -->
        <el-button @click="cancel2" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listCookbookPrc,
  getCookbookPrc,
  delCookbookPrc,
  addCookbookPrc,
  updateCookbookPrc
} from "@/api/grassrootsregistration/cookbookPrc";

export default {
  name: "CookbookPrc",
  data() {
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
      // 一周工作食普图表格数据
      cookbookPrcList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        urlImage: null,
        upPeople: null,
        upTime: null,
        upUnit: null,
        remarks: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 下载文件路径
      downurl: process.env.VUE_APP_BASE_API,
      open2: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    cancel2() {
      this.open2 = false;
    },
    // 搜索时间触发
    time() {
      this.getList();
    },
    handleChakan(row) {
      this.title = "图片预览";
      this.urlImage = row.urlImage;
      this.open2 = true;
    },
    /** 查询一周工作食普图列表 */
    getList() {
      this.loading = true;
      listCookbookPrc(this.queryParams).then(response => {
        this.cookbookPrcList = response.rows;
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
        urlImage: null,
        upPeople: null,
        upTime: null,
        upUnit: null,
        remarks: null
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
      this.title = "添加一周工作食普图";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getCookbookPrc(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改一周工作食普图";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCookbookPrc(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCookbookPrc(this.form).then(response => {
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
        .confirm('是否确认删除一周工作食普图编号为"' + ids + '"的数据项？')
        .then(function() {
          return delCookbookPrc(ids);
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
        "grassrootsregistration/cookbookPrc/export",
        {
          ...this.queryParams
        },
        `cookbookPrc_${new Date().getTime()}.xlsx`
      );
    }
  }
};
</script>
<style scoped>
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
}

::v-deep .el-textarea__inner {
  width: 920px;
  height: 104px;
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

::v-deep .el-textarea__inner {
  width: 920px;
  /* margin: auto; */
  min-height: 120px !important;
}
#chakan /deep/ .el-dialog__body {
  padding: 30px 64px;
  margin-left: 39px;
}
.shouForm /deep/ .el-input__inner {
  width: 220px;
}

::v-deep .boxtext .el-textarea__inner {
  width: 810px;
}

/* 时间选择 */
::v-deep .el-input--small .el-input__inner {
  width: 200px;
  height: 36px;
  line-height: 36px;
}
::v-deep .el-input--medium .el-input__inner {
  background: #004d86;
  color: #fff;
}
::v-deep .el-date-editor.el-input .el-input__inner {
  height: 36px;
  line-height: 36px;
}
::v-deep .el-upload--picture-card {
  background: #004d86;
  color: #fff;
}
::v-deep .el-upload__tip {
  color: #fff;
}

::v-deep .el-input--small .el-input__inner {
  color: #fff;
}
.urlImage {
  width: 970px;
  object-fit: contain;
}
</style>
