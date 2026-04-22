<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
    >
      <el-form-item prop="deptName" >
        <el-input
          v-model="queryParams.deptName"
          placeholder="请输入单位名称"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="单位状态"
          clearable
          size="small"
          @change="handleQuery"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
            class="input_xiala"
          />
        </el-select>
      </el-form-item>
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
          v-hasPermi="['system:dept:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
          >展开/折叠</el-button
        >
      </el-col>
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    </el-row>
    <!-- @selection-change="handleSelectionChange" -->
    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="deptList"
      row-key="deptId"
      :default-expand-all="isExpandAll"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="49"></el-table-column>
      <el-table-column
        prop="deptName"
        label="单位名称"
        width="260"
      ></el-table-column>
      <el-table-column
        prop="orderNum"
        label="排序"
        width="250"
      ></el-table-column>
      <el-table-column prop="status" label="状态" width="250">
        <template slot-scope="scope">
          <span v-if="scope.row.status == '0'" style="color: #13ce66"
            >正常</span
          >
          <span v-if="scope.row.status == '1'" style="color=#FF4949">停用</span>
          <!-- <dict-tag
            :options="dict.type.sys_normal_disable"
            :value="scope.row.status"
          /> -->
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="350"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
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
            v-hasPermi="['system:dept:edit']"
          >
            <span class="edit">修改</span>
          </el-button>
          <el-button
            size="btc"
            type="text"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:dept:add']"
          >
            <span class="plus">新增</span>
          </el-button>
          <el-button
            v-if="scope.row.parentId != 0"
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:dept:remove']"
          >
            <span class="delete">删除</span>
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改单位对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="700px"
      append-to-body
      :closeOnClickModal="false"
      :expand-on-click-node="false"
    >
      <div class="jiben">基本信息</div>

      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="120px"
        :inline="true"
      >
        <el-row>
          <el-col :span="12" v-if="form.parentId !== 0">
            <el-form-item label="上级单位" prop="parentId">
              <treeselect
                v-model="form.parentId"
                :options="deptOptions"
                :normalizer="normalizer"
                placeholder="选择上级单位"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位名称" prop="deptName">
              <el-input v-model="form.deptName" placeholder="请输入单位名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单位级别">
              <el-select v-model="form.type" clearable>
                <el-option
                  v-for="dict in yinLianList"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number
                v-model="form.orderNum"
                controls-position="right"
                :min="0"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="负责人" prop="leader">
              <el-input
                v-model="form.leader"
                placeholder="请输入负责人"
                maxlength="20"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input
                v-model="form.phone"
                placeholder="请输入联系电话"
                maxlength="11"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input
                v-model="form.email"
                placeholder="请输入邮箱"
                maxlength="50"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否展示">
              <el-select v-model="form.isShow" clearable placeholder>
                <el-option
                  v-for="dict in authorizedStrength"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="单位状态">
            <el-radio-group v-model="form.status">
              <el-radio
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.value"
                >{{ dict.label }}</el-radio
              >
            </el-radio-group>
          </el-form-item>
        </el-row>
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
  listDept,
  getDept,
  delDept,
  addDept,
  updateDept,
  listDeptExcludeChild,
} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Dept",
  dicts: ["sys_normal_disable"],
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 表格树数据
      deptList: [],
      // 单位树选项
      deptOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 是否展开
      expand: false,
      // 查询参数
      queryParams: {
        deptName: undefined,
        status: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: "上级单位不能为空", trigger: "blur" },
        ],
        deptName: [
          { required: true, message: "单位名称不能为空", trigger: "blur" },
        ],
        orderNum: [
          { required: true, message: "显示排序不能为空", trigger: "blur" },
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
      },
      //是否编制
      authorizedStrength: [],
      // 是否营连级
      yinLianList:[]
    };
  },
  created() {
    this.getList();
    //是否编制
    this.getDicts("sys_yes_no").then((response) => {
      this.authorizedStrength = response.data;
    });
     //是否编制
     this.getDicts("sys_types").then((response) => {
      this.yinLianList = response.data;
    });
  },
  methods: {
    /** 查询单位列表 */
    getList() {
      this.loading = true;
      listDept(this.queryParams).then((response) => {
        this.deptList = this.handleTree(response.data, "deptId");
        this.loading = false;
      });
    },
    /** 转换单位数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children,
      };
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        deptId: undefined,
        parentId: undefined,
        deptName: undefined,
        orderNum: undefined,
        leader: undefined,
        phone: undefined,
        email: undefined,
        status: "0",
        isShow: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      if (row != undefined) {
        this.form.parentId = row.deptId;
      }
      this.open = true;
      this.title = "添加单位";
      listDept().then((response) => {
        this.deptOptions = this.handleTree(response.data, "deptId");
      });
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      getDept(row.deptId).then((response) => {
        this.form = response.data;
        this.form.type=response.data.type+""
        this.open = true;
        this.title = "修改单位";
      });
      listDeptExcludeChild(row.deptId).then((response) => {
        this.deptOptions = this.handleTree(response.data, "deptId");
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.deptId != undefined) {
            updateDept(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDept(this.form).then((response) => {
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
      this.$modal
        .confirm("是否确认删除名称数据项？")
        .then(function () {
          return delDept(row.deptId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
  },
};
</script>

<style  scoped>
/* 表格状态 */
::v-deep .el-tag {
  /* background-color: #1d96ff; */
  border: 0px;
  color: #1d96ff;
  text-align: center;
  line-height: 28px;
}

/* 设置按钮透明边框  */
.el-button {
  border: 1px solid transparent;
}
.el-button--warning.is-plain {
  width: auto;
}
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
}
::v-deep .el-textarea__inner {
  width: 930px;
  height: 104px;
  margin: auto !important;
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
::v-deep .el-pagination.is-background .btn-prev,
.el-pagination.is-background .btn-next,
.el-pagination.is-background .el-pager li {
  background: #004d86 !important;
  color: #fff !important;
}
/* 状态 */
.el-radio {
  color: rgb(204, 198, 198);
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
/* 调整输入框提示文字颜色 */
::v-deep .vue-treeselect__placeholder {
  color: #bdbdbd4f !important;
}
::v-deep input::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}
::v-deep input:-moz-placeholder {
  color: #bdbdbd4f !important;
}
</style>>

