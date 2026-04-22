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
      <el-form-item label prop="contacts">
        <el-input
          v-model="queryParams.contacts"
          placeholder="请输入联系人"
          clearable
          @keyup.enter.native="handleQuery"
          @input="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="联系方式" prop="contactInformation">
        <el-input
          v-model="queryParams.contactInformation"
          placeholder="请输入联系方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="维修地址" prop="addressUnit">
        <el-input
          v-model="queryParams.addressUnit"
          placeholder="请输入维修地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="维修类型" prop="repairType">
        <el-select v-model="queryParams.repairType" placeholder="请选择维修类型" clearable>
          <el-option
            v-for="dict in dict.type.repair_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="具体描述" prop="description">
        <el-input
          v-model="queryParams.description"
          placeholder="请输入具体描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="完成状态" prop="state">
        <el-input
          v-model="queryParams.state"
          placeholder="请输入完成状态"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item>
        <!-- <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button> -->
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
          v-hasPermi="['barracksManagement:repair:add']"
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
          v-hasPermi="['barracksManagement:repair:edit']"
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
          v-hasPermi="['barracksManagement:repair:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['barracksManagement:repair:export']"
        >导出</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="repairList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="${comment}" align="center" prop="id" /> -->
      <el-table-column label="序号" type="index" width="100" align="center">
        <template scope="scope">
          <span>
            {{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
            }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="联系人" align="center" prop="contacts" width="130" />
      <el-table-column label="联系方式" align="center" prop="contactInformation" width="130" />
      <el-table-column label="维修类型" align="center" prop="repairType" width="130">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.repair_type" :value="scope.row.repairType" />
        </template>
      </el-table-column>
      <el-table-column label="维修地址" align="center" prop="addressUnit" width="250" />
      <el-table-column label="具体描述" align="center" prop="description" width="500" />
      <el-table-column label="完成状态" align="center" prop="state" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.state==0" style="color: rgb(255 106 0)">未完成</span>
          <span v-if="scope.row.state==1" style="color: rgb(2 255 2)">已完成</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="btk"
            v-if="scope.row.state==0"
            type="text"
            @click="handlewancheng(scope.row)"
          >
            <span class="chakan">完成</span>
          </el-button>
          <el-button
            size="btu"
            type="text"
            v-if="scope.row.state==0"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['barracksManagement:repair:edit']"
          >
            <span>修改</span>
          </el-button>
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['barracksManagement:repair:remove']"
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

    <!-- 添加或修改维修申请对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1080px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="120px">
        <div class="jiben">基本信息</div>
        <el-form-item label="联系人" prop="contacts">
          <el-input v-model="form.contacts" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系方式" prop="contactInformation">
          <el-input v-model="form.contactInformation" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="维修地址" prop="addressUnit">
          <el-input v-model="form.addressUnit" placeholder="请输入维修地址" />
        </el-form-item>
        <el-form-item label="维修类型" prop="repairType">
          <el-select v-model="form.repairType" placeholder="请选择维修类型">
            <el-option
              v-for="dict in dict.type.repair_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <div class="jiben">具体描述</div>
        <el-form-item label prop="description">
          <el-input type="textarea" v-model="form.description" placeholder="请输入具体描述" />
        </el-form-item>
        <!-- <el-form-item label="完成状态" prop="state">
          <el-input v-model="form.state" placeholder="请输入完成状态" />
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
  listRepair,
  getRepair,
  delRepair,
  addRepair,
  updateRepair,
  repairSuccess
} from "@/api/barracksManagement/repair";

export default {
  name: "Repair",
  dicts: ["repair_type"],
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
      // 维修申请表格数据
      repairList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contacts: null,
        contactInformation: null,
        addressUnit: null,
        repairType: null,
        description: null,
        state: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        contactInformation: [
          // { required: true, message: "手机号码不能为空", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handlewancheng(row) {
      row.state = 1;
      repairSuccess(row).then(res => {
        if (res.code == 200) {
          this.$modal.msgSuccess("完成已通过");
        }
      });
    },
    /** 查询维修申请列表 */
    getList() {
      this.loading = true;
      listRepair(this.queryParams).then(response => {
        this.repairList = response.rows;
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
        contacts: null,
        contactInformation: null,
        addressUnit: null,
        repairType: null,
        description: null,
        state: null
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
      this.title = "添加维修申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getRepair(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改维修申请";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRepair(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.state = 0;
            addRepair(this.form).then(response => {
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
        .confirm('是否确认删除维修申请编号为"' + ids + '"的数据项？')
        .then(function() {
          return delRepair(ids);
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
        "barracksManagement/repair/export",
        {
          ...this.queryParams
        },
        `repair_${new Date().getTime()}.xlsx`
      );
    }
  }
};
</script>
<style scoped >
::v-deep .el-input--small .el-input__inner {
  color: #fff;
  height: 36px;
  line-height: 36px;
}

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

::v-deep .el-input--small .el-input__inner {
  width: 200px;
  height: 36px;
  line-height: 36px;
}
::v-deep .el-input--medium .el-input__inner {
  background: #004d86;
  color: #fff;
}

.el-form-item ::v-deep .el-input__inner {
  width: 200px;
  height: 36px;
  background: transparent !important;
  color: #fff;
  border: 1px solid #fff !important;
}

::v-deep .el-textarea__inner {
  width: 990px;
  height: 104px;
}
</style>
