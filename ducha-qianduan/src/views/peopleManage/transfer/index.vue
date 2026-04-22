<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="" prop="peopleId">
        <el-input
          v-model="queryParams.cName"
          placeholder="请输入调动人员"
          clearable
          size="small"
          @input="handleQuery"
        />
        <!-- <el-select
          v-model="queryParams.peopleId"
          clearable
          ref="peopleId"
          placeholder="请选择调动人员"
          @change="handleQuery"
        >
          <el-option
            v-for="dict in peopleIds"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
            style="width: 200px; height: 36px"
          ></el-option>
        </el-select> -->
      </el-form-item>
      <el-form-item label="" prop="transferId">
        <el-select
          v-model="queryParams.transferId"
          @change="handleQuery"
          clearable
          placeholder="请选择调动类型"
        >
          <el-option
            v-for="dict in transferIds"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
            style="width: 200px; height: 36px"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="transferDate">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.transferDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择调动日期"
          @change="handleQuery"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <!-- <el-button type="primary" size="btn" @click="handleQuery"
          >搜索</el-button
        > -->
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
          v-hasPermi="['peopleManage:transfer:add']"
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
          v-hasPermi="['peopleManage:transfer:edit']"
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
          v-hasPermi="['peopleManage:transfer:remove']"
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
          v-hasPermi="['peopleManage:transfer:export']"
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
      :data="transferList"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <!-- <el-table-column label="调动人员姓名" align="center" prop="id" /> -->
      <el-table-column label="调动人员姓名" align="center" prop="cName" />
      <!-- <el-table-column label="调动人员id" align="center" prop="peopleId" /> -->
      <el-table-column label="调动类型" align="center" prop="transferId">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_transferId"
            :value="scope.row.transferId"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="调动日期"
        align="center"
        prop="transferDate"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.transferDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="调动生效日期" align="center" prop="transferTake" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.transferTake, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>-->
      <!-- <el-table-column label="调动前军衔" align="center" prop="militaryRank1" /> -->
      <el-table-column label="调动后军衔" align="center" prop="militaryRank2">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_user_rank"
            :value="scope.row.militaryRank2"
          />
        </template>
      </el-table-column>
      <!-- <el-table-column label="调动前职务" align="center" prop="post1" /> -->
      <el-table-column label="调动后职务" align="center" prop="post2">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.post_Level" :value="scope.row.post2" />
        </template>
      </el-table-column>
      <!-- <el-table-column label="调动前单位" align="center" prop="unit1" /> -->
      <el-table-column
        label="调动后单位"
        align="center"
        prop="unit2"
        :formatter="unit2Format"
      />
      <el-table-column
        label="调动后部职别"
        align="center"
        prop="unit2"
        :formatter="unit2Format"
      />
      <!-- <el-table-column label="调动后部职别" align="center" prop="aduty" /> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['peopleManage:transfer:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            @click="handleDelete(scope.row)"
            v-hasPermi="['peopleManage:transfer:remove']"
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

    <!-- 添加或修改调动管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1400px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        :inline="true"
        label-width="120px"
      >
        <div class="jiben">基本信息</div>
        <el-form-item label="调动人员" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            clearable
            @change="peopleIdfun"
            ref="peopleId"
            placeholder="请选择调动人员"
          >
            <el-option
              v-for="dict in peopleIds"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
              style="width: 200px; height: 36px"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调动类型" prop="transferId">
          <el-select
            v-model="form.transferId"
            clearable
            placeholder="请选择调动类型"
          >
            <el-option
              v-for="dict in transferIds"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
              style="width: 200px; height: 36px"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调动日期" prop="transferDate">
          <el-date-picker
            clearable
            size="small"
            v-model="form.transferDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择调动日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="调动生效日期" prop="transferTake">
          <el-date-picker
            clearable
            size="small"
            v-model="form.transferTake"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择调动生效日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="调动前军衔" prop="militaryRank1">
          <el-select
            v-model="form.militaryRank1"
            clearable
            placeholder="请选择调动前军衔"
          >
            <el-option
              v-for="dict in rankIds"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调动后军衔" prop="militaryRank2">
          <el-select
            v-model="form.militaryRank2"
            clearable
            placeholder="请选择调动后军衔"
          >
            <el-option
              v-for="dict in rankIds"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调动前职务" prop="post1">
          <el-select
            v-model="form.post1"
            clearable
            placeholder="请选择调动前职务"
          >
            <el-option
              v-for="dict in postIds"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调动后职务" prop="post2">
          <el-select
            v-model="form.post2"
            clearable
            placeholder="请选择调动后职务"
          >
            <el-option
              v-for="dict in postIds"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调动前单位" prop="unit1">
          <el-select
            v-model="form.unit1"
            clearable
            placeholder="请选择调动前单位"
          >
            <el-option
              v-for="dict in unitId"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict.deptId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调动后单位" prop="unit2">
          <el-select
            v-model="form.unit2"
            clearable
            placeholder="请选择调动后单位"
          >
            <el-option
              v-for="dict in unitId"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict.deptId"
            ></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="调动后部职别" prop="aduty">
          <el-input v-model="form.aduty" placeholder="请输入调动后部职别" />
        </el-form-item> -->
        <el-form-item label="调动命令号" prop="commandNumber">
          <el-input
            v-model="form.commandNumber"
            placeholder="请输入调动命令号"
          />
        </el-form-item>
        <div class="jiben">调动原因</div>
        <el-form-item label prop="reason">
          <el-input
            v-model="form.reason"
            type="textarea"
            placeholder="请输入调动原因"
          />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item label prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入备注"
          />
        </el-form-item>
        <!-- <el-form-item label="预留字段" prop="field1">
          <el-input v-model="form.field1" placeholder="请输入预留字段" />
        </el-form-item>
        <el-form-item label="调动人员姓名" prop="cName">
          <el-input v-model="form.cName" placeholder="请输入调动人员姓名" />
        </el-form-item>-->
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
  listTransfer,
  getTransfer,
  delTransfer,
  addTransfer,
  updateTransfer,
  exportTransfer,
  listPeople,
  getPeople,
  getDept,
} from "@/api/peopleManage/transfer";

export default {
  name: "Transfer",
  dicts: [
    "sys_user_sex",
    "post_Level",
    "sys_user_rank",
    "personnel_type",
    "Degree_level",
    "degree",
    "Caucus",
    "marryList",
    "sys_yw",
    "healthy",
    "bloodType",
    "sys_yes_no",
    "sys_yes_no",
    "sys_transferId",
  ],
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
      // 调动管理表格数据
      transferList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        peopleId: null,
        transferId: null,
        transferDate: null,
        transferTake: null,
        militaryRank1: null,
        militaryRank2: null,
        post1: null,
        post2: null,
        unit1: null,
        unit2: null,
        aduty: null,
        commandNumber: null,
        reason: null,
        field1: null,
        createtime: null,
        updatetime: null,
        cName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        peopleId: [
          { required: true, message: "调动人员不能为空", trigger: "change" },
        ],
        transferId: [
          { required: true, message: "调动类型不能为空", trigger: "change" },
        ],
        transferDate: [
          { required: true, message: "调动日期不能为空", trigger: "change" },
        ],
        transferTake: [
          {
            required: true,
            message: "调动生效日期不能为空",
            trigger: "change",
          },
        ],
        militaryRank1: [
          { required: true, message: "调动前军衔不能为空", trigger: "change" },
        ],
        militaryRank2: [
          { required: true, message: "调动后军衔不能为空", trigger: "change" },
        ],
        post1: [
          { required: true, message: "调动前职务不能为空", trigger: "change" },
        ],
        post2: [
          { required: true, message: "调动后职务不能为空", trigger: "change" },
        ],
        unit1: [
          { required: true, message: "调动前单位不能为空", trigger: "change" },
        ],
        unit2: [
          { required: true, message: "调动后单位不能为空", trigger: "change" },
        ],
        // aduty: [
        //   {
        //     required: true,
        //     message: "调动后部职别不能为空",
        //     trigger: "change",
        //   },
        // ],
        commandNumber: [
          { required: true, message: "调动命令号不能为空", trigger: "change" },
        ],
      },
      //调动人员对象
      peopleIds: [],
      //调动类型
      transferIds: [],
      //职务层级
      postIds: [],
      //军衔
      rankIds: [],
      //单位
      unitId: [],
    };
  },
  created() {
    this.getList();
    //调动人员对象
    listPeople().then((res) => {
      if (res.code == 200) {
        this.peopleIds = res.rows;
      }
    });
    //调动类型
    this.getDicts("sys_transferId").then((response) => {
      this.transferIds = response.data;
    });
    //职务层级
    this.getDicts("post_Level").then((response) => {
      this.postIds = response.data;
    });
    //军衔
    this.getDicts("sys_user_rank").then((response) => {
      this.rankIds = response.data;
    });
    //部门数据
    getDept().then((res) => {
      if (res.code == 200) {
        this.unitId = res.data;
      }
    });
  },
  methods: {
    // 获取部门列表
    getBuMen() {
      //部门数据
      getDept().then((res) => {
        if (res.code == 200) {
          this.unitId = res.data;
        }
      });
    },
    /** 查询调动管理列表 */
    getList() {
      this.loading = true;
      listTransfer(this.queryParams).then((response) => {
        this.transferList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //单位翻译
    unit2Format(row, column) {
      var deptName = "";
      this.unitId.map((item) => {
        if (item.deptId == row.unit2) {
          deptName = item.deptName;
        }
      });
      return deptName;
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
        peopleId: null,
        transferId: null,
        transferDate: null,
        transferTake: null,
        militaryRank1: null,
        militaryRank2: null,
        post1: null,
        post2: null,
        unit1: null,
        unit2: null,
        aduty: null,
        commandNumber: null,
        reason: null,
        remark: null,
        field1: null,
        createtime: null,
        updatetime: null,
        cName: null,
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
      this.queryParams.cName = null;
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
      this.title = "添加调动管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getBuMen();
      const id = row.id || this.ids;
      getTransfer(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改调动管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            this.form.cName = this.$refs["peopleId"].selectedLabel;
            updateTransfer(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.cName = this.$refs["peopleId"].selectedLabel;
            addTransfer(this.form).then((response) => {
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
        .confirm("是否确认删除调动管理的数据项？")
        .then(function () {
          return delTransfer(ids);
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
        .confirm("是否确认导出所有调动管理数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportTransfer(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    //获取人员基本信息
    //获取人员基本信息
    peopleIdfun() {
      getPeople(this.form.peopleId).then((res) => {
        if (res.code == 200) {
          this.form.militaryRank1 = res.data.nowMilitaryRank;
          this.form.post1 = res.data.postId+"";
          this.form.unit1 = res.data.deptId;
        }
      });
    },
  },
};
</script>
<style scoped>
::v-deep .el-dialog {
  width: 1030px !important;
  background-color: #004d86;
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
.el-form-item ::v-deep .el-input__inner {
  width: 200px;
  height: 36px;
  background: transparent !important;
  color: #fff;
  border: 1px solid #fff !important;
}
::v-deep .el-date-editor.el-input {
  width: 200px;
  height: 36px;
}
::v-deep .el-upload--picture-card {
  height: 210px;
  width: 170px;
  line-height: 210px;
}
.el-upload--picture-card /deep/ .el-upload--picture-card i {
  font-size: 48px;
}
::v-deep .el-upload__tip {
  display: none;
}
::v-deep .el-textarea__inner {
  width: 980px;
  height: 104px;
}
.div-box-title span {
  width: 25px;
  height: 25px;
  display: inline-block;
  background-color: #1c86e1;
  line-height: 25px;
  text-align: center;
  font-size: 16px;
  cursor: pointer;
  margin-left: 865px;
}
.el-select-dropdown__item:hover {
  color: #1c86e1;
}
.el-select-dropdown__item {
  color: #000;
}
.el-tree {
  background-color: #00365f;
  color: #fff;
}
::v-deep .el-tree-node__content:hover {
  color: #1c86e1;
}
::v-deep .el-upload-list--picture-card .el-upload-list__item {
  height: 210px;
  width: 170px;
}
::v-deep .el-dialog__footer {
  padding: 20px 30px !important;
}
</style>
