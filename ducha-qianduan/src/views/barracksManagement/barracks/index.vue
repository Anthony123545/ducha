<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="number">
        <el-input
          v-model="queryParams.number"
          clearable
          size="small"
          placeholder="请输入坐落号"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="number1" style="margin-left: -20px">
        <el-input
          v-model="queryParams.number1"
          clearable
          size="small"
          placeholder="请输入分栋号"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item style="margin-left: -20px">
        <!-- <el-button type="" size="btn" @click="handleQuery">搜索</el-button> -->
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
          v-hasPermi="['barracksManagement:barracks:add']"
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
          v-hasPermi="['barracksManagement:barracks:edit']"
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
          v-hasPermi="['barracksManagement:barracks:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['barracksManagement:barracks:export']"
        >导出</el-button>
      </el-col>
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>-->
    </el-row>
    <div class="hezi">
      <div class="tji">营房统计</div>
      <div style="display: flex; flex-wrap: wrap; padding: 11px 20px">
        <div style="width:30%; display: flex; align-items: center; height: 100px">
          <div class="div-l" style="flex: 2; text-align: right; margin-right: 30px">
            <span>公寓</span>
          </div>
          <div class="div-r" style="flex: 1; text-align: right; margin-right: 10%">
            <span>
              <span style="font-size: 25px; margin-right: 18px">{{ tongJiList.gongYu }}</span>
            </span>
          </div>
        </div>
        <div style="width:30%; display: flex; align-items: center; height: 100px">
          <div class="div-l" style="flex: 2; text-align: right; margin-right: 30px">
            <span>宿舍</span>
          </div>
          <div class="div-r" style="flex: 1; text-align: right; margin-right: 10%">
            <span>
              <span style="font-size: 25px; margin-right: 18px">{{ tongJiList.suShe }}</span>
            </span>
          </div>
        </div>
        <div style="width:30%; display: flex; align-items: center; height: 100px">
          <div class="div-l" style="flex: 2; text-align: right; margin-right: 30px">
            <span>营房</span>
          </div>
          <div class="div-r" style="flex: 1; text-align: right; margin-right: 10%">
            <span>
              <span style="font-size: 25px; margin-right: 18px">{{ tongJiList.yingFang }}</span>
            </span>
          </div>
        </div>
      </div>
    </div>
    <el-table v-loading="loading" :data="barracksList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50"></el-table-column>
      <el-table-column label="坐落号" align="center" prop="number" width="150px" />
      <el-table-column label="分栋号" align="center" prop="number1" />
      <el-table-column label="分栋名称" align="center" prop="name" />
      <el-table-column label="营房用途" align="center" prop="behoof" />
      <el-table-column label="利用现状" align="center" prop="statusQuo" />
      <el-table-column label="建筑年代" align="center" prop="period" />
      <el-table-column label="建筑面积" align="center" prop="area" />
      <el-table-column label="录入时间" align="center" prop="createtime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, "{y}-{m}-{d}") }}</span>
          <!-- <span>{{ dateFormat('YYYY-mm-dd HH:MM:SS',scope.row.createtime) }}</span> -->
          <!-- rTime -->
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" show-overflow-tooltip />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['barracksManagement:barracks:edit']"
          >修改</el-button>
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['barracksManagement:barracks:remove']"
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

    <!-- 添加或修改营房档案对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="坐落号" prop="number">
          <el-input v-model="form.number" />
        </el-form-item>
        <el-form-item label="分栋号" prop="number1">
          <el-input v-model="form.number1" />
        </el-form-item>
        <el-form-item label="分栋名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="营房用途" prop="behoof">
          <el-input v-model="form.behoof" />
        </el-form-item>
        <el-form-item label="利用现状" prop="statusQuo">
          <el-input v-model="form.statusQuo" />
        </el-form-item>
        <el-form-item label="建筑年代" prop="period">
          <el-input v-model="form.period" />
        </el-form-item>
        <el-form-item label="建筑面积" prop="area">
          <el-input v-model="form.area" />
        </el-form-item>
        <div class="jibens">备注</div>
        <el-form-item prop="remark">
          <!-- <el-input v-model="form.remark" placeholder="请输入备注" /> -->
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
  listBarracks,
  getBarracks,
  delBarracks,
  addBarracks,
  updateBarracks,
  exportBarracks,
  getnumber
} from "@/api/barracksManagement/barracks";

export default {
  name: "Barracks",
  data() {
    return {
      tongJiList: [
        { name: "共有营房", num: 0 },
        { name: "宿舍", num: 0 },
        { name: "办公用房", num: 0 },
        { name: "专业用房", num: 0 },
        { name: "公共用房", num: 0 },
        { name: "公寓", num: 0 }
      ],
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
      // 营房档案表格数据
      barracksList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        number: null,
        number1: null,
        name: null,
        behoof: null,
        statusQuo: null,
        period: null,
        area: null,
        createtime: null,
        updatetime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        number: [
          { required: true, message: "坐落号不能为空", trigger: "blur" }
        ],
        number1: [
          { required: true, message: "分栋号不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "分栋名称不能为空", trigger: "blur" }
        ],
        behoof: [
          { required: true, message: "营房用途不能为空", trigger: "blur" }
        ],
        statusQuo: [
          { required: true, message: "利用现状不能为空", trigger: "blur" }
        ],
        period: [
          { required: true, message: "建筑年代不能为空", trigger: "blur" }
        ],
        area: [{ required: true, message: "建筑面积不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getnumberfun() {
      getnumber().then(res => {
        this.tongJiList = res.data;
      });
    },
    /** 查询营房档案列表 */
    getList() {
      this.loading = true;
      listBarracks(this.queryParams).then(response => {
        this.barracksList = response.rows;
        this.total = response.total;
        this.loading = false;
        this.getnumberfun();
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
        number: null,
        number1: null,
        name: null,
        behoof: null,
        statusQuo: null,
        period: null,
        area: null,
        remark: null,
        createtime: null,
        updatetime: null
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
      this.title = "添加营房档案";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getBarracks(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改营房档案";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBarracks(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBarracks(this.form).then(response => {
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
      // this.$modal
      //   .confirm("是否确认删除？")
      //   .then(function () {
      //     return delBarracks(ids);
      //   })
      //   .then(() => {
      //     this.getList();
      //     this.$modal.msgSuccess("删除成功");
      //   })
      //   .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有营房档案数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBarracks(queryParams);
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
/* 标题下划线 */
::v-deep .el-dialog__header {
  border-bottom: 1px solid white;
}
/* 文本域 */
::v-deep .el-textarea__inner {
  width: 606px;
  height: 80px;
}
::v-deep .el-form-item__label {
  text-align: end;
}
::v-deep .el-dialog__body {
  padding: 0px;
  margin-left: 100px;
  margin-top: 20px;
}
::v-deep .el-table .el-table__header-wrapper th,
.el-table .el-table__fixed-header-wrapper th {
  background-color: rgb(0, 60, 105);
  color: white;
}
/* 文字多余部分省略 */
::v-deep .el-table__cell .cell {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.el-button--btr1 {
  /* border-color: #20B2AA; */
  color: #ffffff;
  width: 76px;
  height: 36px;
  background-color: #1d96ff;
  border-radius: 4px;
  font-size: 14px;
  text-align: center;
  /* margin-top: 1px; */
  padding-top: 11px !important;
}
.el-button--btr2 {
  /* border-color: #20B2AA; */
  color: #ffffff;
  width: 76px;
  height: 36px;
  background-color: #1d96ff;
  border-radius: 4px;
  font-size: 14px;
  text-align: center;
  /* margin-top: 1px; */

  padding-top: 12px !important;
}
::v-deep .el-table__cell .cell {
  text-align: center;
}
::v-deep .el-input__inner {
  border: 1px solid white !important;
}
/* 备注样式 */
.jibens {
  width: 650px;
  height: 32px;
  background-image: url("/static/img/小标题底.b7c6f45a.png");
  background-size: 100%;
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}
::v-deep .el-form-item__content .el-input__inner {
  color: white;
}

.el-form--inline .el-form-item {
  margin-right: 28px;
}
::v-deep .el-dialog__footer {
  margin-right: 72px;
}
::v-deep .el-form-item__content .el-input__inner {
  width: 200px;
  height: 36px;
}
.hezi {
  margin-bottom: 13px;
  margin-top: 15px;
  position: relative;
  background: rgb(0, 60, 105);
  color: #fff;
  font-size: 18px;
  font-weight: 600;
}

.tji {
  width: 176px;
  height: 36px;
  text-align: center;
  line-height: 36px;
  position: absolute;
  top: -18px;
  right: 87px;
  color: #fff;
  font-size: 18px;
  font-weight: 600;
  background: #004d86;
}
</style>
