<template>
  <div class="app-container">
    <!-- <div class="box-right"> -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" v-if="this.level == 1" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['thought:teacher:add']">新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['thought:teacher:edit']"
        >修改</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5"> -->
      <!-- <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['thought:teacher:remove']"
          >删除</el-button
        >
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          size="mini"
          @click="upload"
          v-hasPermi="['thought:teacher:add']"
          >上传文件</el-button
        >
      </el-col> -->
      <el-col :span="1.5">
        <el-button class="turnback" @click.native="turnback" v-if="this.level != 1">返回上级</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['thought:teacher:export']"
        >导出</el-button>
      </el-col> -->
    </el-row>

    <el-table v-loading="loading" :data="teacherList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" align="center" prop="id" />
      <el-table-column label="目录" align="center" prop="directory" show-overflow-tooltip>
        <template slot-scope="scope">
          <div @click="mulus(scope.row)" :class="scope.row.subclassification == 0 ? 'fenlei' : 'undeline'"
            style="position: relative; cursor: pointer">
            <img v-if="scope.row.subclassification != 0" src="../../../images/文件夹.png" alt="" style="
                margin-right: 5px;
                position: absolute;
                left: 80px;
                top: 2px;
              " />
            <!--  -->
            <!-- 分类图片 -->
            <img v-if="scope.row.subclassification == 0" src="../../../images/分类icon.png" alt="" style="
                margin-right: 5px;
                position: absolute;
                left: 80px;
                top: 2px;
              " />
            <span style="position: relative;right:-148px"> {{ scope.row.directory }}</span>
          </div>
        </template>
      </el-table-column>
      <!-- <el-table-column label="父目录ID" align="center" prop="parentId" /> -->
      <!-- <el-table-column label="祖级列表" align="center" prop="ancestors" /> -->
      <el-table-column label="子分类数量" align="center" prop="subclassification" />
      <el-table-column label="文件数" align="center" prop="documentsNumber" />
      <!-- <el-table-column label="创建人姓名" align="center" prop="createUser" />
      <el-table-column label="创建人ID" align="center" prop="createUserid" />
      <el-table-column label="修改人姓名" align="center" prop="updateUser" />
      <el-table-column label="修人姓名" align="center" prop="updateUserid" />
      <el-table-column label="父级文件夹名称" align="center" prop="parentDirectory" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="btu" type="text" @click="ViewProgress(scope.row)" v-hasPermi="['thought:teacher:edit']">添加
          </el-button>
          <el-button size="btd" type="text" @click="handleDelete(scope.row)" v-hasPermi="['thought:teacher:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改教案库设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="400px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="目录" prop="directory">
          <el-input v-model="form.directory" placeholder="请输入目录" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 添加子分类弹框 -->
    <el-dialog style="color: pink" :title="title2" :visible.sync="open2" width="400px" append-to-body
      class="el-dialog__header" :close-on-click-modal="false">
      <el-form ref="form2" :model="form2" :rules="rules" label-width="100px" :inline="true">
        <el-form-item prop="directory" label="子分类">
          <el-input v-model="form2.directory" placeholder="请输入子分类名称" class="filename" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm2">确 定</el-button>
        <el-button @click="cancel" size="btn" class="aa1"> 取 消</el-button>
      </div>
    </el-dialog>
    <!-- 上传文件弹框 -->
    <div class="big">
      <el-dialog style="color: pink" :title="title3" :visible.sync="open3" width="400px" append-to-body
        class="el-dialog__header" :close-on-click-modal="false">
        <el-form ref="form" :model="form" :rules="rules" label-width="130px" :inline="true" style="margin-left: 45px">
          <el-form-item label-width="100px" prop="directory" class="change_plan_type">
            <!-- <treeselect
                v-model="form.directory"
                :options="users"
                :value="directory"
                placeholder="请选择单位"
                class="threeselects"
                @select="selectPeo"
              /> -->
          </el-form-item>
          <el-form-item style="margin-left: 25px; margin-top: 15px">
            <FileUpload v-model="form.file" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel" size="btn">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
  <!-- </div> -->
</template>

<script>
import {
  listTeacher,
  getTeacher,
  delTeacher,
  addTeacher,
  updateTeacher,
  exportTeacher,
  listtree,
  listTeachers,
} from "@/api/thought/teacher";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Teacher",
  components: {
    Treeselect,
  },
  data() {
    return {
      // 添加按钮
      addbtn: true,
      // 遮罩层
      loading: false,
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
      // 教案库设置表格数据
      teacherList: [],
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
        parentDirectory: null,
      },
      // 表单参数
      form: {},
      // 子分类目录
      form2: {},
      open2: false,
      form3: {},
      open3: false,
      // 表单校验
      rules: {},
      // 左侧树形
      deptOptions: "",
      //左侧时间选择
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      // 树形配置
      defaultProps: {
        children: "children",
        label: "directory",
      },
      // 目录列表
      users: [],
      parentId: "",
      title3: "",
      title: "",
      title2: "",
      // 返回上级父id
      parentIds: "",
      parentDirectory: "",
      // 添加按钮传值
      parentDirectorys: "",
      level: 1,
      levels: "",
      parentIdss: "",
      dellevel: "",
      // 子分类数量
      subclassification: "",
      // 父目录id
      fatherparentId: "",
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 点击目录
    mulus(row) {
      this.parentId = row.id;
      this.parentDirectory = row.parentDirectory;
      this.subclassification = row.subclassification;
      if (row.subclassification == 0) {
        return;
      } else {
        this.level += 1;
        this.fatherparentId = row.id;

        this.loading = true;
        this.getLists();
      }
    },
    getLists() {
      listTeacher({
        parentId: this.parentId,
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize,
      }).then((response) => {
        this.teacherList = response.rows;
        this.parentIds = this.teacherList[0].parentId;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 返回上级
    turnback(row) {
      if (this.level == 1) {
        this.level == 1;
        return;
      } else {
        this.level -= 1;
      }
      // this.loading=true
      if (this.level == 1) {
        listTeachers({
          level: this.level,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        }).then((res) => {
          this.teacherList = res.rows;

          this.fatherparentId = res.rows[0].parentId;
          this.total = res.total;
          this.loading = false;
        });
      } else if (this.level > 1) {
        listTeachers({
          parentDirectory: this.parentDirectory,
          level: this.level,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
          parentId: this.fatherparentId,
        }).then((res) => {
          this.teacherList = res.rows;
          this.fatherparentId = res.rows[0].parentId;

          this.total = res.total;
          this.loading = false;
        });
      }
    },
    // 添加子目录
    ViewProgress(row) {
      // 传入父亲目录id
      this.form2.parentId = row.id;
      this.form2.parentDirectory = row.parentDirectory;
      this.parentDirectorys = row.parentDirectory;
      this.levels = (parseInt(row.level) + 1).toString();
      this.level = this.levels;
      this.parentIdss = row.id;
      this.fatherparentId = row.id;
      this.open2 = true;
      this.title2 = "添加子分类";
    },
    /** 查询教案库设置列表 */
    getList() {
      this.loading = true;
      this.addbtn = true;
      this.queryParams.parentId = 0;
      if (this.level != 1) {
        listTeacher({
          parentDirectory: this.parentDirectory,
          level: this.level,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
        }).then((response) => {
          this.teacherList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      } else {
        listTeacher(this.queryParams).then((response) => {
          this.teacherList = response.rows;

          this.total = response.total;
          this.loading = false;
        });
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open2 = false;
      this.open3 = false;
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
        updateTime: null,
        parentDirectory: null,
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
      this.reset();
      this.open = true;
      this.title = "添加目录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTeacher(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改教案库设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTeacher(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTeacher(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    // 添加子分类
    submitForm2() {
      this.$refs["form2"].validate((valid) => {
        if (valid) {
          addTeacher(this.form2).then((response) => {
            this.$modal.msgSuccess("上传子分类成功");
            this.open2 = false;
            this.form2 = {};
            // this.getList();
            listTeacher({
              parentDirectory: this.parentDirectorys,
              level: this.levels,
              parentId: this.parentIdss,
              pageNum: this.queryParams.pageNum,
              pageSize: this.queryParams.pageSize,
            }).then((response) => {
              this.teacherList = response.rows;
              this.total = response.total;
              this.loading = false;
            });
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;

      this.$modal
        .confirm("是否确认删除？")
        .then(function () {
          return delTeacher(ids);
        })
        .then(() => {
          if (row.level == 1) {
            row.parentDirectory = null;
          }

          // if(row.pageX){
          //   row.parentId=0
          //   row.parentDirectory=""

          //   row.level=this.level
          //   this.level=this.level
          // }
          listTeacher({
            parentDirectory: row.parentDirectory,
            level: row.level,
            parentId: row.parentId,
            pageNum: this.queryParams.pageNum,
            pageSize: this.queryParams.pageSize,
          }).then((response) => {
            // this.teacherList = response.rows;
            this.total = response.total;
            if (this.total > 0) {
              this.teacherList = response.rows;
            } else {
              this.dellevel = (parseInt(row.level) - 1).toString();
              this.level = this.dellevel;

              listTeacher({
                parentDirectory: row.parentDirectory,
                level: this.dellevel,
                pageNum: this.queryParams.pageNum,
                pageSize: this.queryParams.pageSize,
              }).then((response) => {
                this.total = response.total;
                this.teacherList = response.rows;
              });
            }
            this.loading = false;
          });
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有教案库设置数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportTeacher(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => { });
    },
  },
};
</script>
<style scoped>
/* 返回目录按钮 */
.turnback {
  width: 86px;
  height: 33px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  line-height: 10px;
  padding-left: 15px !important;
  /* margin-left: 30px; */
  /* text-align: center; */
  /* margin-left: 480px; */
  color: #ccc;
  background: #00365f;
}

.turnback:hover,
.turnback:focus,
.turnback:active {
  background: #00365f;
  border: 1px solid #ccc;
  color: #ccc;
}

/* 树形样式 */
::v-deep .vue-treeselect__control {
  background-color: #004d86;
  width: 216px;
  margin-right: 25px;
  color: white;
}

.threeselects {
  width: 216px;
  margin-right: 22px;
}

::v-deep .vue-treeselect__single-value {
  color: white;
}

/* 计划附件 */
::v-deep .el-upload__tip {
  color: white;
  position: absolute;
  top: -5px;
  width: 500px;
  left: 70px;
  color: white;
}

::v-deep .el-input__inner {
  height: 36px;
}

::v-deep .el-form-item__content {
  width: 230px;
}

::v-deep .select_file {
  margin-left: -25px;
}

/* 选取附件 */
::v-deep .el-upload-list__item {
  width: 400px;
}

::v-deep .el-upload-list__item a {
  color: white;
  text-indent: 0.5em;
}

::v-deep .el-select-dropdown__item {
  color: white;
}

::v-deep .el-dialog {
  background-color: #004d86 !important;
}

/* 输入框 */
::v-deep .el-dialog .el-input__inner {
  background-color: #004d86 !important;
  width: 216px;
  color: white;
  margin-right: 20px;
  border: 1px solid white !important;
}

::v-deep .el-input__inner {
  border: 1px solid white !important;
}

/* 表格样式 */
::v-deep .el-table .el-table__header-wrapper th {
  background-color: #004d86 !important;
  color: white;
}

/* 标题弹框 */
::v-deep .el-dialog__title {
  color: white;
  /* border-bottom: 1px solid white; */
}

/* 标题下划线 */
::v-deep .el-dialog__header {
  border-bottom: 1px solid white;
}

/* 左侧树形样式 */
::v-deep .data .el-date-editor.el-input {
  width: 100%;
  border: none;
}

::v-deep .data .el-input__inner {
  width: 100% !important;
  border: none !important;
}

::v-deep .data .el-input--medium .el-input__icon {
  line-height: 36px;
  position: absolute;
  right: -223px;
  top: 2px;
}

::v-deep .data .el-input__inner {
  background-color: transparent !important;
  color: #fff;
  text-align: center;
  margin-top: 2px;
}

::v-deep .el-dialog {
  background-color: #004d86 !important;
}

::v-deep .el-input__suffix {
  right: 22px;
}

::v-deep .el-tree-node__content {
  height: 32px;
}

::v-deep .el-tree--highlight-current .el-tree-node.is-current>.el-tree-node__content {
  height: 32px;
  color: #fff !important;
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}

/* 选择目录 */
::v-deep .choose .el-input__inner {
  width: 240px;
}

.undeline {
  /* text-decoration: underline; */
  /* text-align: start; */
  color: #00f0ff;
  cursor: pointer;
}

::v-deep .undeline img, .fenlei img{
  left: 20px !important;
}

::v-deep .undeline span, .fenlei span {
  position: relative;
  width: 176px;
  display: inline-block;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin-left: 10px;
  right: 0px !important;
}

::v-deep .el-input__suffix {
  right: 10px;
}

/* 树形样式 */
.vue-treeselect__control {
  background-color: #003156 !important;
}
</style>
