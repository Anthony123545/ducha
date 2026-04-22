<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="值班时间" prop="attendedTime">
        <el-input
          v-model="queryParams.attendedTime"
          placeholder="请输入值班时间"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班首长" prop="chiefDutyName">
        <el-input
          v-model="queryParams.chiefDutyName"
          placeholder="请输入值班首长"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班首长ID" prop="chiefDutyId">
        <el-input
          v-model="queryParams.chiefDutyId"
          placeholder="请输入值班首长ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班首长电话" prop="chiefDutyPhone">
        <el-input
          v-model="queryParams.chiefDutyPhone"
          placeholder="请输入值班首长电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作战值班员" prop="combatDutyName">
        <el-input
          v-model="queryParams.combatDutyName"
          placeholder="请输入作战值班员"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作战值班员Id" prop="combatDutyId">
        <el-input
          v-model="queryParams.combatDutyId"
          placeholder="请输入作战值班员Id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作战值班员手机号" prop="combatDutyPhone">
        <el-input
          v-model="queryParams.combatDutyPhone"
          placeholder="请输入作战值班员手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总值班员" prop="dutyOffierName">
        <el-input
          v-model="queryParams.dutyOffierName"
          placeholder="请输入总值班员"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总值班员ID" prop="dutyOffierId">
        <el-input
          v-model="queryParams.dutyOffierId"
          placeholder="请输入总值班员ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总值班员电话" prop="dutyOffierPhone">
        <el-input
          v-model="queryParams.dutyOffierPhone"
          placeholder="请输入总值班员电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班分队1" prop="detachmentoneName">
        <el-input
          v-model="queryParams.detachmentoneName"
          placeholder="请输入值班分队1"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班分队1ID" prop="detachmentoneId">
        <el-input
          v-model="queryParams.detachmentoneId"
          placeholder="请输入值班分队1ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班分队1人数" prop="detachmentoneNumber">
        <el-input
          v-model="queryParams.detachmentoneNumber"
          placeholder="请输入值班分队1人数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班分队1指挥员" prop="detachmentonePeopleName">
        <el-input
          v-model="queryParams.detachmentonePeopleName"
          placeholder="请输入值班分队1指挥员"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班分队1指挥员ID" prop="detachmentonePeopleId">
        <el-input
          v-model="queryParams.detachmentonePeopleId"
          placeholder="请输入值班分队1指挥员ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班分队1指挥员电话" prop="detachmentonePeoplePhone">
        <el-input
          v-model="queryParams.detachmentonePeoplePhone"
          placeholder="请输入值班分队1指挥员电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班分队2" prop="detachmentwoName">
        <el-input
          v-model="queryParams.detachmentwoName"
          placeholder="请输入值班分队2"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班分队2ID" prop="detachmentwoId">
        <el-input
          v-model="queryParams.detachmentwoId"
          placeholder="请输入值班分队2ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班分队2人数" prop="detachmentwoNumber">
        <el-input
          v-model="queryParams.detachmentwoNumber"
          placeholder="请输入值班分队2人数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班分队2指挥员" prop="detachmentwoPeopleName">
        <el-input
          v-model="queryParams.detachmentwoPeopleName"
          placeholder="请输入值班分队2指挥员"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班分队2指挥员ID" prop="detachmentwoPeopleId">
        <el-input
          v-model="queryParams.detachmentwoPeopleId"
          placeholder="请输入值班分队2指挥员ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值班分队2指挥员电话" prop="detachmentwoPeoplePhone">
        <el-input
          v-model="queryParams.detachmentwoPeoplePhone"
          placeholder="请输入值班分队2指挥员电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input
          v-model="queryParams.remarks"
          placeholder="请输入备注"
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
          v-hasPermi="['combatduty:basedutyinfo:add']"
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
          v-hasPermi="['combatduty:basedutyinfo:edit']"
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
          v-hasPermi="['combatduty:basedutyinfo:remove']"
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
          v-hasPermi="['combatduty:basedutyinfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="basedutyinfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="值班时间" align="center" prop="attendedTime" />
      <el-table-column label="值班首长" align="center" prop="chiefDutyName" />
      <el-table-column label="值班首长ID" align="center" prop="chiefDutyId" />
      <el-table-column label="值班首长电话" align="center" prop="chiefDutyPhone" />
      <el-table-column label="作战值班员" align="center" prop="combatDutyName" />
      <el-table-column label="作战值班员Id" align="center" prop="combatDutyId" />
      <el-table-column label="作战值班员手机号" align="center" prop="combatDutyPhone" />
      <el-table-column label="总值班员" align="center" prop="dutyOffierName" />
      <el-table-column label="总值班员ID" align="center" prop="dutyOffierId" />
      <el-table-column label="总值班员电话" align="center" prop="dutyOffierPhone" />
      <el-table-column label="值班分队1" align="center" prop="detachmentoneName" />
      <el-table-column label="值班分队1ID" align="center" prop="detachmentoneId" />
      <el-table-column label="值班分队1人数" align="center" prop="detachmentoneNumber" />
      <el-table-column label="值班分队1指挥员" align="center" prop="detachmentonePeopleName" />
      <el-table-column label="值班分队1指挥员ID" align="center" prop="detachmentonePeopleId" />
      <el-table-column label="值班分队1指挥员电话" align="center" prop="detachmentonePeoplePhone" />
      <el-table-column label="值班分队2" align="center" prop="detachmentwoName" />
      <el-table-column label="值班分队2ID" align="center" prop="detachmentwoId" />
      <el-table-column label="值班分队2人数" align="center" prop="detachmentwoNumber" />
      <el-table-column label="值班分队2指挥员" align="center" prop="detachmentwoPeopleName" />
      <el-table-column label="值班分队2指挥员ID" align="center" prop="detachmentwoPeopleId" />
      <el-table-column label="值班分队2指挥员电话" align="center" prop="detachmentwoPeoplePhone" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['combatduty:basedutyinfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['combatduty:basedutyinfo:remove']"
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

    <!-- 添加或修改基地值班对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="值班时间" prop="attendedTime">
          <el-input v-model="form.attendedTime" placeholder="请输入值班时间" />
        </el-form-item>
        <el-form-item label="值班首长" prop="chiefDutyName">
          <el-input v-model="form.chiefDutyName" placeholder="请输入值班首长" />
        </el-form-item>
        <el-form-item label="值班首长ID" prop="chiefDutyId">
          <el-input v-model="form.chiefDutyId" placeholder="请输入值班首长ID" />
        </el-form-item>
        <el-form-item label="值班首长电话" prop="chiefDutyPhone">
          <el-input v-model="form.chiefDutyPhone" placeholder="请输入值班首长电话" />
        </el-form-item>
        <el-form-item label="作战值班员" prop="combatDutyName">
          <el-input v-model="form.combatDutyName" placeholder="请输入作战值班员" />
        </el-form-item>
        <el-form-item label="作战值班员Id" prop="combatDutyId">
          <el-input v-model="form.combatDutyId" placeholder="请输入作战值班员Id" />
        </el-form-item>
        <el-form-item label="作战值班员手机号" prop="combatDutyPhone">
          <el-input v-model="form.combatDutyPhone" placeholder="请输入作战值班员手机号" />
        </el-form-item>
        <el-form-item label="总值班员" prop="dutyOffierName">
          <el-input v-model="form.dutyOffierName" placeholder="请输入总值班员" />
        </el-form-item>
        <el-form-item label="总值班员ID" prop="dutyOffierId">
          <el-input v-model="form.dutyOffierId" placeholder="请输入总值班员ID" />
        </el-form-item>
        <el-form-item label="总值班员电话" prop="dutyOffierPhone">
          <el-input v-model="form.dutyOffierPhone" placeholder="请输入总值班员电话" />
        </el-form-item>
        <el-form-item label="值班分队1" prop="detachmentoneName">
          <el-input v-model="form.detachmentoneName" placeholder="请输入值班分队1" />
        </el-form-item>
        <el-form-item label="值班分队1ID" prop="detachmentoneId">
          <el-input v-model="form.detachmentoneId" placeholder="请输入值班分队1ID" />
        </el-form-item>
        <el-form-item label="值班分队1人数" prop="detachmentoneNumber">
          <el-input v-model="form.detachmentoneNumber" placeholder="请输入值班分队1人数" />
        </el-form-item>
        <el-form-item label="值班分队1指挥员" prop="detachmentonePeopleName">
          <el-input v-model="form.detachmentonePeopleName" placeholder="请输入值班分队1指挥员" />
        </el-form-item>
        <el-form-item label="值班分队1指挥员ID" prop="detachmentonePeopleId">
          <el-input v-model="form.detachmentonePeopleId" placeholder="请输入值班分队1指挥员ID" />
        </el-form-item>
        <el-form-item label="值班分队1指挥员电话" prop="detachmentonePeoplePhone">
          <el-input v-model="form.detachmentonePeoplePhone" placeholder="请输入值班分队1指挥员电话" />
        </el-form-item>
        <el-form-item label="值班分队2" prop="detachmentwoName">
          <el-input v-model="form.detachmentwoName" placeholder="请输入值班分队2" />
        </el-form-item>
        <el-form-item label="值班分队2ID" prop="detachmentwoId">
          <el-input v-model="form.detachmentwoId" placeholder="请输入值班分队2ID" />
        </el-form-item>
        <el-form-item label="值班分队2人数" prop="detachmentwoNumber">
          <el-input v-model="form.detachmentwoNumber" placeholder="请输入值班分队2人数" />
        </el-form-item>
        <el-form-item label="值班分队2指挥员" prop="detachmentwoPeopleName">
          <el-input v-model="form.detachmentwoPeopleName" placeholder="请输入值班分队2指挥员" />
        </el-form-item>
        <el-form-item label="值班分队2指挥员ID" prop="detachmentwoPeopleId">
          <el-input v-model="form.detachmentwoPeopleId" placeholder="请输入值班分队2指挥员ID" />
        </el-form-item>
        <el-form-item label="值班分队2指挥员电话" prop="detachmentwoPeoplePhone">
          <el-input v-model="form.detachmentwoPeoplePhone" placeholder="请输入值班分队2指挥员电话" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBasedutyinfo, getBasedutyinfo, delBasedutyinfo, addBasedutyinfo, updateBasedutyinfo, exportBasedutyinfo } from "@/api/combatduty/basedutyinfo";

export default {
  name: "Basedutyinfo",
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
      // 基地值班表格数据
      basedutyinfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        attendedTime: null,
        chiefDutyName: null,
        chiefDutyId: null,
        chiefDutyPhone: null,
        combatDutyName: null,
        combatDutyId: null,
        combatDutyPhone: null,
        dutyOffierName: null,
        dutyOffierId: null,
        dutyOffierPhone: null,
        detachmentoneName: null,
        detachmentoneId: null,
        detachmentoneNumber: null,
        detachmentonePeopleName: null,
        detachmentonePeopleId: null,
        detachmentonePeoplePhone: null,
        detachmentwoName: null,
        detachmentwoId: null,
        detachmentwoNumber: null,
        detachmentwoPeopleName: null,
        detachmentwoPeopleId: null,
        detachmentwoPeoplePhone: null,
        remarks: null
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
    /** 查询基地值班列表 */
    getList() {
      this.loading = true;
      listBasedutyinfo(this.queryParams).then(response => {
        this.basedutyinfoList = response.rows;
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
        attendedTime: null,
        chiefDutyName: null,
        chiefDutyId: null,
        chiefDutyPhone: null,
        combatDutyName: null,
        combatDutyId: null,
        combatDutyPhone: null,
        dutyOffierName: null,
        dutyOffierId: null,
        dutyOffierPhone: null,
        detachmentoneName: null,
        detachmentoneId: null,
        detachmentoneNumber: null,
        detachmentonePeopleName: null,
        detachmentonePeopleId: null,
        detachmentonePeoplePhone: null,
        detachmentwoName: null,
        detachmentwoId: null,
        detachmentwoNumber: null,
        detachmentwoPeopleName: null,
        detachmentwoPeopleId: null,
        detachmentwoPeoplePhone: null,
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加基地值班";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBasedutyinfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基地值班";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBasedutyinfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBasedutyinfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除基地值班编号为"' + ids + '"的数据项？').then(function() {
        return delBasedutyinfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有基地值班数据项？').then(() => {
        this.exportLoading = true;
        return exportBasedutyinfo(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
