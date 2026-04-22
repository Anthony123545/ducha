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
      <el-form-item label="" prop="xingMing">
        <el-input
          v-model="queryParams.xingMing"
          placeholder="请输入姓名"
          clearable
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
          v-hasPermi="['barracksManagement:scadre:add']"
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
          v-hasPermi="['barracksManagement:scadre:edit']"
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
          v-hasPermi="['barracksManagement:scadre:remove']"
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
          v-hasPermi="['barracksManagement:scadre:export']"
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
      :data="dormList"
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
      <el-table-column label="姓名" align="center" prop="xingMing" />
      <el-table-column label="房间号" align="center" prop="roomNumber" />
      <el-table-column label="建筑面积" align="center" prop="area" />
      <el-table-column
        label="物业费标准"
        align="center"
        prop="propertyFeeStandard"
      />

      <el-table-column label="房租标准" align="center" prop="rentStandard" />
      <el-table-column label="月水费" align="center" prop="waterBil" />
      <el-table-column label="月房租" align="center" prop="monthlyRent" />
      <el-table-column
        label="月物业费"
        align="center"
        prop="monthlyPropertyFee"
      />
      <el-table-column label="总金额" align="center" prop="totalAmount" />
      <!-- <el-table-column
        label="录入时间"
        align="center"
        prop="createtime"
        width="150px"
      >
        <template slot-scope="scope">
          <span>{{
            dateFormat("YYYY-mm-dd HH:MM:SS", scope.row.createtime)
          }}</span>
        </template>
      </el-table-column> -->
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
            v-hasPermi="['barracksManagement:scadre:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['barracksManagement:scadre:remove']"
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

    <!-- 添加或修改单身干部宿舍对话框 -->
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
        label-width="90px"
        :inline="true"
      >
        <el-form-item label="姓名" prop="xingMing">
          <el-input v-model="form.xingMing" placeholder="" />
        </el-form-item>
        <el-form-item label="房间号" prop="roomNumber">
          <el-input v-model="form.roomNumber" placeholder="" />
        </el-form-item>
        <el-form-item label="建筑面积" prop="area">
          <el-input v-model="form.area" placeholder="" />
        </el-form-item>
        <el-form-item label="使用面积" prop="usageArea">
          <el-input v-model="form.usageArea" placeholder="" />
        </el-form-item>
        <el-form-item label="物业标准" prop="propertyFeeStandard">
          <el-input v-model="form.propertyFeeStandard" placeholder="" />
        </el-form-item>
        <el-form-item label="房租标准" prop="rentStandard">
          <el-input v-model="form.rentStandard" placeholder="" />
        </el-form-item>
        <el-form-item label="水电费" prop="waterBil">
          <el-input v-model="form.waterBil" placeholder="" />
        </el-form-item>
        <el-form-item label="月房租" prop="monthlyRent">
          <el-input v-model="form.monthlyRent" placeholder="" />
        </el-form-item>
        <el-form-item label="月物业费" prop="monthlyPropertyFee">
          <el-input v-model="form.monthlyPropertyFee" placeholder="" />
        </el-form-item>
        <el-form-item label="总金额" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="" />
        </el-form-item>
        <div class="jibens">备注</div>
        <el-form-item label="" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            autosize
            placeholder="请输入备注"
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
  listDorm,
  getDorm,
  delDorm,
  addDorm,
  updateDorm,
  exportScadred,
} from "@/api/barracksManagement/scadred";

export default {
  name: "Dorm",
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
      // 单身干部宿舍表格数据
      dormList: [],
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
        updatetime: null,
        tier: null,
        roomNumber: null,
        element: null,
        xingMing: null,
        usageArea: null,
        propertyFeeStandard: null,
        rentStandard: null,
        waterBil: null,
        monthlyRent: null,
        monthlyPropertyFee: null,
        totalAmount: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        xingMing: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
        ],
        area: [
          { required: true, message: "建筑面积不能为空", trigger: "blur" },
        ],
        usageArea: [
          { required: true, message: "使用面积不能为空", trigger: "blur" },
        ],
        propertyFeeStandard: [
          { required: true, message: "物业标准不能为空", trigger: "blur" },
        ],
        rentStandard: [
          { required: true, message: "房租标准不能为空", trigger: "blur" },
        ],
        waterBil: [
          { required: true, message: "水电费不能为空", trigger: "blur" },
        ],
        monthlyRent: [
          { required: true, message: "月房租不能为空", trigger: "blur" },
        ],
        monthlyPropertyFee: [
          { required: true, message: "月物业费不能为空", trigger: "blur" },
        ],
        totalAmounnt: [
          { required: true, message: "总金额不能为空", trigger: "blur" },
        ],
        roomNumber: [
          { required: true, message: "房间号不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    dateFormat(fmt, date) {
      let ret = "";
      date = new Date(date);
      const opt = {
        "Y+": date.getFullYear().toString(), // 年
        "m+": (date.getMonth() + 1).toString(), // 月
        "d+": date.getDate().toString(), // 日
        "H+": date.getHours().toString(), // 时
        "M+": date.getMinutes().toString(), // 分
        "S+": date.getSeconds().toString(), // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
      };
      for (let k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
          fmt = fmt.replace(
            ret[1],
            ret[1].length == 1 ? opt[k] : opt[k].padStart(ret[1].length, "0")
          );
        }
      }
      return fmt;
    },
    /** 查询单身干部宿舍列表 */
    getList() {
      this.loading = true;
      listDorm(this.queryParams).then((response) => {
        this.dormList = response.rows;
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
        number: null,
        number1: null,
        name: null,
        behoof: null,
        statusQuo: null,
        period: null,
        area: null,
        remark: null,
        createtime: null,
        updatetime: null,
        tier: null,
        roomNumber: null,
        element: null,
        xingMing: null,
        usageArea: null,
        propertyFeeStandard: null,
        rentStandard: null,
        waterBil: null,
        monthlyRent: null,
        monthlyPropertyFee: null,
        totalAmount: null,
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
      this.title = "添加单身干部宿舍";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getDorm(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改单身干部宿舍";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateDorm(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDorm(this.form).then((response) => {
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
        .then(function () {
          return delDorm(ids);
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
        .confirm("是否确认导出所有公寓房档案数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportScadred(queryParams);
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
  width: 610px;
  height: 80px;
}
::v-deep .el-form-item__label {
  text-align: end;
}
::v-deep .el-dialog__body {
  padding: 0px;
  margin-left: 96px;
  margin-top: 20px;
}
::v-deep .el-table .el-table__header-wrapper th,
.el-table .el-table__fixed-header-wrapper th {
  background-color: rgb(0, 60, 105);
  color: white;
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
  width: 650px;
  height: 32px;
  background-image: url("/static/img/小标题底.b7c6f45a.png");
  background-size: 100%;
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}
::v-deep .el-dialog__footer {
  margin-right: 74px;
}
::v-deep .el-form-item__content .el-input__inner {
  width: 200px;
  height: 36px;
}
::v-deep .el-textarea__inner {
  min-height: 120px !important;
}
</style>