<template>
  <div class="app-container">
    <el-row>
      <el-col :span="1.5" class="isSubmit">
        <el-radio-group v-model="queryParams.type" @change="caoZuoTypefun">
          <el-radio-button label="null">所有值班</el-radio-button>
          <el-radio-button label="1">今日值班</el-radio-button>
          <el-radio-button label="2">次日值班</el-radio-button>
        </el-radio-group>
      </el-col>
    </el-row>
    <hr />
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="auto"
    >
      <el-form-item label="" prop="attendedTime">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.attendedTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择值班时间"
          @change="handleQuery"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="" prop="chiefDutyName">
        <el-input
          v-model="queryParams.chiefDutyName"
          placeholder="请输入值班首长"
          clearable
          size="small"
          @input="handleQuery"
        />
        <!-- <el-select
          v-model="queryParams.chiefDutyId"
          filterable
          clearable
          placeholder="请选择值班首长"
          @change="handleQuery"
        >
          <el-option
            v-for="item in rylist"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select> -->
      </el-form-item>
      <!-- <el-form-item label="值班首长ID" prop="chiefDutyId">
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
      </el-form-item>-->
      <el-form-item label="" prop="combatDutyName">
        <el-input
          v-model="queryParams.combatDutyName"
          placeholder="请输入作战值班员"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <!-- <el-button type="primary" size="btn" @click="handleQuery">搜索</el-button> -->
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
          v-hasPermi="['combatduty:basedutyinfo:add']"
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
          v-hasPermi="['combatduty:basedutyinfo:edit']"
        >修改</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['combatduty:basedutyinfo:remove']"
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
          v-hasPermi="['combatduty:basedutyinfo:export']"
        >导出</el-button>
      </el-col>-->

      <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="basedutyinfoList"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="主键ID" align="center" prop="id" /> -->
      <el-table-column label="值班时间" align="center" prop="attendedTime" />
      <el-table-column label="值班首长" align="center" prop="chiefDutyName" />
      <!-- <el-table-column label="值班首长" align="center" prop="chiefDutyId" :formatter="chiefDutyIdFormat" /> -->
      <!-- <el-table-column label="值班首长电话" align="center" prop="chiefDutyPhone" /> -->
      <el-table-column
        label="作战值班员"
        align="center"
        prop="combatDutyName"
      />
      <!-- <el-table-column label="作战值班员" align="center" prop="combatDutyId" :formatter="combatDutyIdFormat" /> -->
      <!-- <el-table-column label="作战值班员手机号" align="center" prop="combatDutyPhone" /> -->
      <el-table-column label="总值班员" align="center" prop="dutyOffierName" />
      <!-- <el-table-column label="总值班员" align="center" prop="dutyOffierId" :formatter="dutyOffierIdFormat" /> -->
      <!-- <el-table-column label="总值班员电话" align="center" prop="dutyOffierPhone" /> -->
      <el-table-column
        label="值班分队1"
        align="center"
        prop="detachmentoneName"
      />
      <!-- <el-table-column
        label="值班分队1"
        align="center"
        prop="detachmentoneId"
        :formatter="detachmentoneIdFormat"
      /> -->
      <!-- <el-table-column label="值班分队1人数" align="center" prop="detachmentoneNumber" /> -->
      <el-table-column
        label="值班分队1指挥员"
        align="center"
        prop="detachmentonePeopleName"
      />
      <!-- <el-table-column label="值班分队1指挥员" align="center" prop="detachmentonePeopleId" :formatter="detachmentonePeopleIdFormat" /> -->
      <!-- <el-table-column label="值班分队1指挥员电话" align="center" prop="detachmentonePeoplePhone" />  -->
      <el-table-column
        label="值班分队2"
        align="center"
        prop="detachmentwoName"
      />
      <!-- <el-table-column
        label="值班分队2"
        align="center"
        prop="detachmentwoId"
        :formatter="detachmentwoIdFormat"
      /> -->
      <!-- <el-table-column label="值班分队2人数" align="center" prop="detachmentwoNumber" /> -->
      <el-table-column
        label="值班分队2指挥员"
        align="center"
        prop="detachmentwoPeopleName"
      />
      <!-- <el-table-column label="值班分队2指挥员" align="center" prop="detachmentwoPeopleId" :formatter="detachmentwoPeopleIdFormat" /> -->
      <!-- <el-table-column label="值班分队2指挥员电话" align="center" prop="detachmentwoPeoplePhone" />
      <el-table-column label="备注" align="center" prop="remarks" />-->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['combatduty:basedutyinfo:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['combatduty:basedutyinfo:remove']"
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

    <!-- 添加或修改基地值班对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1016px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        :inline="true"
        label-width="150px"
      >
        <div class="jiben">基本信息</div>
        <el-form-item label="值班时间" prop="attendedTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.attendedTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
            v-if="qdflg == 2"
          ></el-date-picker>
          <el-date-picker
            v-model="form.attendedTime"
            type="daterange"
            range-separator="至"
            start-placeholder
            end-placeholder
            value-format="yyyy-MM-dd"
            v-if="qdflg == 1"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="值班首长" prop="chiefDutyName">
          <el-input v-model="form.chiefDutyName" placeholder="" />
          <!-- <el-select
            v-model="form.chiefDutyName"
            @change="chiefDutyNamefun"
            filterable
            clearable
            placeholder=""
          >
            <el-option
              v-for="item in rylist"
              :key="item.id"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select> -->
        </el-form-item>
        <!-- <el-form-item label="值班首长ID" prop="chiefDutyId">
          <el-input v-model="form.chiefDutyId" placeholder="请输入值班首长ID" />
        </el-form-item>-->
        <el-form-item label="值班首长电话" prop="chiefDutyPhone">
          <el-input v-model="form.chiefDutyPhone" placeholder="" />
        </el-form-item>
        <el-form-item label="作战值班员" prop="combatDutyName">
          <el-input v-model="form.combatDutyName" placeholder="" />
          <!-- <el-select
            v-model="form.combatDutyName"
            @change="combatDutyNamefun"
            filterable
            clearable
            placeholder=""
          >
            <el-option
              v-for="item in rylist"
              :key="item.id"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select> -->
        </el-form-item>
        <!-- <el-form-item label="作战值班员Id" prop="combatDutyId">
          <el-input v-model="form.combatDutyId" placeholder="请输入作战值班员Id" />
        </el-form-item>-->
        <el-form-item label="作战值班员手机" prop="combatDutyPhone">
          <el-input v-model="form.combatDutyPhone" placeholder="" />
        </el-form-item>
        <el-form-item label="总值班员" prop="dutyOffierName">
          <el-input v-model="form.dutyOffierName" placeholder="" />
          <!-- <el-select
            v-model="form.dutyOffierName"
            @change="dutyOffierNamefun"
            filterable
            clearable
            placeholder=""
          >
            <el-option
              v-for="item in rylist"
              :key="item.id"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select> -->
        </el-form-item>
        <!-- <el-form-item label="总值班员ID" prop="dutyOffierId">
          <el-input v-model="form.dutyOffierId" placeholder="请输入总值班员ID" />
        </el-form-item>-->
        <el-form-item label="总值班员电话" prop="dutyOffierPhone">
          <el-input v-model="form.dutyOffierPhone" placeholder="" />
        </el-form-item>
        <el-form-item label="值班分队1" prop="detachmentoneName">
          <el-input v-model="form.detachmentoneName" placeholder="" />
          <!-- <el-select
            v-model="form.detachmentoneName"
            @change="detachmentoneNamechange"
            clearable
            placeholder=""
          >
            <el-option
              v-for="dict in deptOptions"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict"
            ></el-option>
          </el-select> -->
        </el-form-item>
        <!-- <el-form-item label="值班分队1ID" prop="detachmentoneId">
          <el-input v-model="form.detachmentoneId" placeholder="请输入值班分队1ID" />
        </el-form-item>-->
        <el-form-item label="值班分队1人数" prop="detachmentoneNumber">
          <el-input
            v-model="form.detachmentoneNumber"
            type="number"
            :min="0"
            placeholder=""
          />
          <!-- <el-input-number v-model="form.detachmentoneNumber" controls-position="right" :min="0"></el-input-number> -->
          <!-- <el-input-number v-model="form.detachmentoneNumber" type='Number' controls-position="right" :min="0" /> -->
          <!-- <el-input
            v-model="form.detachmentoneNumber"
            type="number"
            placeholder="请输入值班分队1人数"
          />-->
        </el-form-item>
        <el-form-item label="值班分队1指挥员" prop="detachmentonePeopleName">
          <el-input v-model="form.detachmentonePeopleName" placeholder="" />
          <!-- <el-select
            v-model="form.detachmentonePeopleName"
            filterable
            clearable
            placeholder=""
            @change="detachmentonePeopleNamefun"
          >
            <el-option
              v-for="item in rylist"
              :key="item.id"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select> -->
        </el-form-item>
        <!-- <el-form-item label="值班分队1指挥员ID" prop="detachmentonePeopleId">
          <el-input v-model="form.detachmentonePeopleId" placeholder="请输入值班分队1指挥员ID" />
        </el-form-item>-->
        <el-form-item
          label="值班分队1指挥员电话"
          prop="detachmentonePeoplePhone"
        >
          <el-input v-model="form.detachmentonePeoplePhone" placeholder="" />
        </el-form-item>
        <el-form-item label="值班分队2" prop="detachmentwoName">
          <el-input v-model="form.detachmentwoName" placeholder="" />
          <!-- <el-select
            v-model="form.detachmentwoName"
            @change="detachmentwoNamechange"
            clearable
            placeholder=""
          >
            <el-option
              v-for="dict in deptOptions"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict"
            ></el-option>
          </el-select> -->
        </el-form-item>
        <!-- <el-form-item label="值班分队2ID" prop="detachmentwoId">
          <el-input v-model="form.detachmentwoId" placeholder="请输入值班分队2ID" />
        </el-form-item>-->
        <el-form-item label="值班分队2人数" prop="detachmentwoNumber">
          <!-- <el-input-number v-model="form.detachmentwoNumber" controls-position="right" :min="0" /> -->
          <el-input
            v-model="form.detachmentwoNumber"
            type="number"
            :min="0"
            placeholder=""
          />
          <!-- <el-input
            v-model="form.detachmentwoNumber"
            type="number"
            placeholder="请输入值班分队2人数"
          />-->
        </el-form-item>
        <el-form-item label="值班分队2指挥员" prop="detachmentwoPeopleName">
          <el-input v-model="form.detachmentwoPeopleName" placeholder="" />
          <!-- <el-select
            v-model="form.detachmentwoPeopleName"
            filterable
            clearable
            placeholder=""
            @change="detachmentwoPeopleNamefun"
          >
            <el-option
              v-for="item in rylist"
              :key="item.id"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select> -->
        </el-form-item>
        <!-- <el-form-item label="值班分队2指挥员ID" prop="detachmentwoPeopleId">
          <el-input v-model="form.detachmentwoPeopleId" placeholder="请输入值班分队2指挥员ID" />
        </el-form-item>-->
        <el-form-item
          label="值班分队2指挥员电话"
          prop="detachmentwoPeoplePhone"
        >
          <el-input v-model="form.detachmentwoPeoplePhone" placeholder="" />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item label prop="remarks">
          <el-input
            v-model="form.remarks"
            type="textarea"
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
  listBasedutyinfo,
  getBasedutyinfo,
  delBasedutyinfo,
  addBasedutyinfo,
  updateBasedutyinfo,
  exportBasedutyinfo,
  rllist,
  listPeople,
  getDept,
} from "@/api/combatduty/basedutyinfo";

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
        remarks: null,
        type: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //人员数据
      rylist: [],
      //单位数据
      deptOptions: [],
      qdflg: null,
    };
  },
  created() {
    this.getList();
    this.gettreeselect();
    this.getrylist();
  },
  methods: {
    //获取人员
    getrylist() {
      listPeople().then((res) => {
        if (res.code == 200) {
          this.rylist = res.rows;
        }
      });
    },
    gettreeselect() {
      getDept().then((res) => {
        if (res.code == 200) {
          this.deptOptions = res.data;
        }
      });
    },
    //值班一队
    // detachmentoneIdFormat(row, column) {
    //   var deptName = "";
    //   this.deptOptions.map(item => {
    //     if (item.deptId == row.detachmentoneId) {
    //       deptName = item.deptName;
    //     }
    //   });
    //   return deptName;
    // },
    //值班二队
    // detachmentwoIdFormat(row, column) {
    //   var deptName = "";
    //   this.deptOptions.map(item => {
    //     if (item.deptId == row.detachmentwoId) {
    //       deptName = item.deptName;
    //     }
    //   });
    //   return deptName;
    // },
    //首长
    chiefDutyIdFormat(row, column) {
      var deptName = "";
      this.rylist.map((item) => {
        if (item.id == row.chiefDutyId) {
          deptName = item.name;
        }
      });
      return deptName;
    },
    combatDutyIdFormat(row, column) {
      var deptName = "";
      this.rylist.map((item) => {
        if (item.id == row.combatDutyId) {
          deptName = item.name;
        }
      });
      return deptName;
    },
    detachmentonePeopleIdFormat(row, column) {
      var deptName = "";
      this.rylist.map((item) => {
        if (item.id == row.detachmentonePeopleId) {
          deptName = item.name;
        }
      });
      return deptName;
    },
    detachmentwoPeopleIdFormat(row, column) {
      var deptName = "";
      this.rylist.map((item) => {
        if (item.id == row.detachmentwoPeopleId) {
          deptName = item.name;
        }
      });
      return deptName;
    },
    dutyOffierIdFormat(row, column) {
      var deptName = "";
      this.rylist.map((item) => {
        if (item.id == row.chiefDutyId) {
          deptName = item.name;
        }
      });
      return deptName;
    },
    /** 查询基地值班列表 */
    getList() {
      this.loading = true;
      listBasedutyinfo(this.queryParams).then((response) => {
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
        remarks: null,
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
      this.title = "添加基地值班";
      this.getrylist();
      this.gettreeselect();
      this.qdflg = 1;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getBasedutyinfo(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基地值班";
        this.qdflg = 2;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateBasedutyinfo(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.timequjian = this.form.attendedTime;
            this.form.attendedTime = this.form.attendedTime.join(",");
            addBasedutyinfo(this.form).then((response) => {
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
        .confirm("是否确认删除该数据项？")
        .then(function () {
          return delBasedutyinfo(ids);
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
        .confirm("是否确认导出所有基地值班数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBasedutyinfo(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    //值班分类
    caoZuoTypefun() {
      this.getList();
    },
    // //首长电话
    // chiefDutyNamefun(val) {
    //   this.form.chiefDutyId = val.id;
    //   this.form.chiefDutyName = val.name;
    //   this.form.chiefDutyPhone = val.phone;
    // },
    // //作战值班电话
    // combatDutyNamefun(val) {
    //   this.form.combatDutyId = val.id;
    //   this.form.combatDutyName = val.name;
    //   this.form.combatDutyPhone = val.phone;
    // },
    // //总值班员
    // dutyOffierNamefun(val) {
    //   this.form.dutyOffierId = val.id;
    //   this.form.dutyOffierName = val.name;
    //   this.form.dutyOffierPhone = val.phone;
    // },
    // //值班分队1指挥员
    // detachmentonePeopleNamefun(val) {
    //   this.form.detachmentonePeopleId = val.id;
    //   this.form.detachmentonePeopleName = val.name;
    //   this.form.detachmentonePeoplePhone = val.phone;
    // },
    // //值班分队2指挥员
    // detachmentwoPeopleNamefun(val) {
    //   this.form.detachmentwoPeopleId = val.id;
    //   this.form.detachmentwoPeopleName = val.name;
    //   this.form.detachmentwoPeoplePhone = val.phone;
    // },
    detachmentoneNamechange(val) {
      this.form.detachmentoneId = val.deptId;
      this.form.detachmentoneName = val.deptName;
    },
    detachmentwoNamechange(val) {
      this.form.detachmentwoId = val.deptId;
      this.form.detachmentwoName = val.deptName;
    },
  },
};
</script>
<style scoped>
::v-deep .el-dialog {
  width: 1150px !important;
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
  background: transparent;
  color: #fff !important;
  border: 1px solid #fff !important;
}

.el-select-dropdown__item {
  /* background-color: white; */
  color: #000 !important;
}
::v-deep .el-date-editor.el-input {
  width: 200px;
  height: 36px;
}

.pictureUploading-img img {
  width: 60px;
  height: 210px;
  margin-right: 10px;
}

::v-deep .el-upload--picture-card {
  height: 210px;
  width: 170px;
  line-height: 210px;
  background-image: url("../../../assets/images/头像.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
::v-deep .el-upload--picture-card .el-icon-plus {
  display: none;
}
.el-upload--picture-card /deep/ .el-upload--picture-card i {
  font-size: 48px;
}
::v-deep .el-upload__tip {
  display: none;
}
::v-deep .el-textarea__inner {
  width: 1070px;
  height: 104px;
}
.addspan {
  width: 30px;
  height: 30px;
  display: inline-block;
  background-color: #1c86e1;
  line-height: 30px;
  text-align: center;
  font-size: 16px;
  cursor: pointer;
  position: absolute;
  right: 45px;
  color: #fff;
}
.addspan2 {
  width: 30px;
  height: 30px;
  display: inline-block;
  background-color: #1c86e1;
  line-height: 30px;
  text-align: center;
  font-size: 16px;
  cursor: pointer;
  position: absolute;
  right: 10px;
  color: #fff;
}
.shanchuspan {
  width: 30px;
  height: 30px;
  display: inline-block;
  background-color: #ff4949;
  line-height: 30px;
  text-align: center;
  font-size: 14px;
  cursor: pointer;
  position: absolute;
  right: 10px;
  color: #fff;
}
.el-select-dropdown__item:hover {
  color: #1c86e1;
}
.el-select-dropdown__item {
  color: #fff;
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
.dadui {
  height: 40px;
  background: #003156;
  /* border-bottom: 1px solid #718A9D ; */
  background-image: url(../../../images/矩形底部边框.png);
  background-repeat: no-repeat;
  background-position: 0px 39px;
  font-size: 16px;
  color: #1d96ff;
}
.dadui img {
  margin: 10px 10px 0px 10px;
}

.data {
  height: 40px;
  background: #003156;
  margin-bottom: 12px;
  color: #fff;
  display: flex;
  font-size: 13px;
  line-height: 25px;
  position: relative;
}
.el-tree {
  padding: 10px;
  background: #003156;
  color: #fff;
}

.data p {
  position: absolute;
  top: -5px;
  left: 15px;
}
.data .a1 {
  width: 128px;
  height: 26px;
  background: rgba(23, 74, 112, 0.4);
  border-radius: 13px;
  position: absolute;
  top: 7px;
  left: 60px;
}
::v-deep .data .el-date-editor.el-input {
  width: 100%;
  border: none;
}
::v-deep .data .el-input__inner {
  width: 100% !important;
  border: none;
}
::v-deep .data .el-input--medium .el-input__icon {
  line-height: 36px;
  position: absolute;
  right: -223px;
  top: 2px;
}
::v-deep .data .el-input__inner {
  background-color: transparent;
  color: #fff;
  text-align: center;
  margin-top: 2px;
}
::v-deep .data .el-input__suffix {
  position: absolute;
  right: 281px;
}
::v-deep
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  color: #00365f !important;
  /* background-color: #003156 !important; */
}
::v-deep .el-range-input {
  background: #004d86;
  color: #fff;
  font-size: 12px;
}
::v-deep .el-range-separator {
  color: #fff;
  font-size: 12px;
}
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
::v-deep .el-dialog__body {
  padding: 30px 0px 0px 40px !important;
}
::v-deep .el-dialog__footer {
  margin-right: 20px !important;
}
.isSubmit {
  margin-left: 40% !important;
  margin-right: 30%;
  margin-bottom: 5px;
  padding-bottom: 5px;
}
.el-radio-button {
  width: 122px !important;
  height: 30px !important;
  line-height: 30px !important;
  border: none !important;
}
::v-deep .el-radio-button__inner {
  width: 120px !important;
  height: 30px !important;
  line-height: 8px !important;
  background: #196299;
  border: none;
  color: #fff;
  line-height: 12px !important;
}
::v-deep .el-radio-button:first-child .el-radio-button__inner {
  border-left: none !important;
}
::v-deep .el-radio-button__orig-radio:checked + .el-radio-button__inner {
  color: #ffffff;
  background-color: #006cff !important;
  border-color: #006cff !important;
  -webkit-box-shadow: -1px 0 0 0 #006cff !important;
  box-shadow: -1px 0 0 0 #006cff !important;
  line-height: 12px !important;
}
/* 将水平线设为红色： */
hr {
  border: none;
  height: 1px;
  background-color: rgba(204, 204, 204, 0.5);
  margin-bottom: 15px;
}
</style>

