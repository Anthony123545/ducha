<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="auto"
    >
      <el-form-item prop="attendedTime">
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
      <el-form-item prop="chiefDutyName">
        <el-input
          v-model="queryParams.chiefDutyName"
          placeholder="请输入值班首长"
          clearable
          size="small"
          @input="handleQuery"
        />
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
          v-hasPermi="['combatduty:teamdutyinfo:add']"
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
          v-hasPermi="['combatduty:teamdutyinfo:edit']"
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
          v-hasPermi="['combatduty:teamdutyinfo:remove']"
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
          v-hasPermi="['combatduty:teamdutyinfo:export']"
        >导出</el-button>
      </el-col>-->
      <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="teamdutyinfoList"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <!-- <el-table-column label="值班分队指挥员电话" align="center" prop="id" /> -->
      <el-table-column label="值班首长" align="center" prop="chiefDutyName" />
      <!-- <el-table-column
        label="值班首长"
        align="center"
        prop="chiefDutyId"
        :formatter="chiefDutyIdFormat"
      /> -->
      <!-- <el-table-column label="值班首长电话" align="center" prop="chiefDutyPhone" /> -->
      <el-table-column
        label="作战值班员"
        align="center"
        prop="combatDutyName"
      />
      <!-- <el-table-column
        label="作战值班员"
        align="center"
        prop="combatDutyId"
        :formatter="combatDutyIdFormat"
      /> -->
      <!-- <el-table-column label="作战值班员电话" align="center" prop="combatDutyPhone" /> -->
      <el-table-column
        label="通信值班员"
        align="center"
        prop="dutyOffierName"
      />
      <!-- <el-table-column
        label="通信值班员"
        align="center"
        prop="dutyOffierId"
        :formatter="dutyOffierIdFormat"
      /> -->
      <!-- <el-table-column label="通信值班员电话" align="center" prop="dutyOffierPhone" /> -->
      <el-table-column
        label="机要值班员"
        align="center"
        prop="confidentialName"
      />
      <!-- <el-table-column
        label="机要值班员"
        align="center"
        prop="confidentialId"
        :formatter="confidentialIdFormat"
      /> -->
      <!-- <el-table-column label="机要值班员电话" align="center" prop="confidentialPhone" />-->
      <el-table-column
        label="政治工作部值班员"
        align="center"
        prop="politicalName"
      />
      <!-- <el-table-column
        label="政治工作部值班员"
        align="center"
        prop="politicalId"
        :formatter="politicalIdFormat"
      /> -->
      <!-- <el-table-column label="政治工作部值班员电话" align="center" prop="politicalPhone" /> -->
      <el-table-column
        label="保障部值班员"
        align="center"
        prop="guaranteeName"
      />
      <!-- <el-table-column
        label="保障部值班员"
        align="center"
        prop="guaranteeId"
        :formatter="guaranteeIdFormat"
      /> -->
      <!-- <el-table-column label="保障部值班员电话" align="center" prop="guaranteePhone" /> -->
      <el-table-column label="值班通信员" align="center" prop="messengerName" />
      <!-- <el-table-column
        label="值班通信员"
        align="center"
        prop="messengerId"
        :formatter="messengerIdFormat"
      /> -->
      <!-- <el-table-column label="值班通信员电话" align="center" prop="messengerPhone" /> -->
      <!-- <el-table-column label="值班分队" align="center" prop="detachmentName" /> -->
      <!-- <el-table-column
        label="值班分队"
        align="center"
        prop="detachmentId"
        :formatter="detachmentIdFormat"
      /> -->
      <!-- <el-table-column label="值班分队人数" align="center" prop="detachmentNumber" />
      <el-table-column label="值班分队指挥员" align="center" prop="detachmentPeopleName" />
      <el-table-column label="值班分队指挥员ID" align="center" prop="detachmentPeopleId" />
      <el-table-column label="值班分队指挥员电话" align="center" prop="detachmentPeoplePhone" />-->
      <el-table-column label="值班时间" align="center" prop="attendedTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.attendedTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
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
            v-hasPermi="['combatduty:teamdutyinfo:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['combatduty:teamdutyinfo:remove']"
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

    <!-- 添加或修改大队值班对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1016px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree" style="overflow: -Scroll; overflow-x: hidden">
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="155px"
          :inline="true"
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
          <!-- <el-form-item label="值班首长" prop="chiefDutyId">
          <el-select v-model="form.chiefDutyId" filterable clearable placeholder="请选择值班首长">
            <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>-->
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
          <!-- <el-form-item label="作战值班员" prop="combatDutyId">
          <el-select v-model="form.combatDutyId" filterable clearable placeholder="请选择作战值班员">
            <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>-->
          <!-- <el-form-item label="作战值班员ID" prop="combatDutyId">
          <el-input
            v-model="form.combatDutyId"
            placeholder="请输入作战值班员ID"
          />
        </el-form-item>-->
          <el-form-item label="作战值班员电话" prop="combatDutyPhone">
            <el-input v-model="form.combatDutyPhone" placeholder="" />
          </el-form-item>
          <el-form-item label="通信值班员" prop="dutyOffierName">
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
          <!-- <el-form-item label="通信值班员" prop="dutyOffierId">
          <el-select v-model="form.dutyOffierId" filterable clearable placeholder="请选择通信值班员">
            <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>-->
          <el-form-item label="通信值班员电话" prop="dutyOffierPhone">
            <el-input v-model="form.dutyOffierPhone" placeholder="" />
          </el-form-item>
          <el-form-item label="机要值班员" prop="confidentialName">
            <el-input v-model="form.confidentialName" placeholder="" />
            <!-- <el-select
              v-model="form.confidentialName"
              @change="confidentialNamechange"
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
          <!-- <el-form-item label="机要值班员" prop="confidentialId">
          <el-select v-model="form.confidentialId" filterable clearable placeholder="请选择机要值班员">
            <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>-->
          <el-form-item label="机要值班员电话" prop="confidentialPhone">
            <el-input v-model="form.confidentialPhone" placeholder="" />
          </el-form-item>
          <el-form-item label="政治工作部值班员" prop="politicalName">
            <el-input v-model="form.politicalName" placeholder="" />
            <!-- <el-select
              v-model="form.politicalName"
              @change="politicalNamechange"
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
          <!-- <el-form-item label="政治工作部值班员" prop="politicalId">
          <el-select v-model="form.politicalId" filterable clearable placeholder="请选择机政治工作部值班员">
            <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>-->
          <el-form-item label="政治工作部值班员电话" prop="politicalPhone">
            <el-input v-model="form.politicalPhone" placeholder="" />
          </el-form-item>
          <el-form-item label="保障部值班员" prop="guaranteeName">
            <el-input v-model="form.guaranteeName" placeholder="" />
            <!-- <el-select
              v-model="form.guaranteeName"
              @change="guaranteeNamechange"
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
          <!-- <el-form-item label="保障部值班员" prop="guaranteeId">
          <el-select v-model="form.guaranteeId" filterable clearable placeholder="请选择保障部值班员">
            <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>-->
          <el-form-item label="保障部值班员电话" prop="guaranteePhone">
            <el-input v-model="form.guaranteePhone" placeholder="" />
          </el-form-item>
          <el-form-item label="值班通信员" prop="messengerName">
            <el-input v-model="form.messengerName" placeholder="" />
            <!-- <el-select
              v-model="form.messengerName"
              @change="messengerNamechange"
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
          <!-- <el-form-item label="值班通信员" prop="messengerId">
          <el-select v-model="form.messengerId" filterable clearable placeholder="请选择值班通信员">
            <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>-->
          <el-form-item label="值班通信员电话" prop="messengerPhone">
            <el-input v-model="form.messengerPhone" placeholder="" />
          </el-form-item>
          <div class="jiben">值班分队</div>
          <el-row
            v-for="(item, i) in relatives"
            :key="i"
            style="margin-bottom: 20px"
          >
            <!-- <el-form v-for="(item, i) in relatives" :key="i"> -->
            <el-form-item label="值班分队" prop="detachmentId">
              <el-select v-model="item.detachmentId" clearable placeholder="">
                <el-option
                  v-for="dict in deptOptions"
                  :key="dict.deptId"
                  :label="dict.deptName"
                  :value="dict.deptId"
                ></el-option>
              </el-select>
            </el-form-item>
            <!-- <el-form-item label="值班分队" prop="detachmentId">
          <el-select v-model="form.detachmentId" ref="deptId" clearable placeholder="请选择值班分队">
              @change="detachmentNamechange"
            <el-option
              v-for="dict in deptOptions"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict.deptId"
            ></el-option>
          </el-select>
        </el-form-item> -->
            <el-form-item label="值班分队人数" prop="detachmentNumber">
              <el-input
                v-model="item.detachmentNumber"
                type="number"
                :min="0"
                placeholder=""
              />
            </el-form-item>
            <el-form-item label="值班分队指挥员" prop="detachmentPeopleName">
              <el-input v-model="item.detachmentPeopleName" placeholder="" />
              <!-- <el-select
              v-model="form.detachmentPeopleName"
              filterable
              clearable
              placeholder=""
              @change="detachmentPeopleNamechange"
            >
              <el-option
                v-for="item in rylist"
                :key="item.id"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select> -->
            </el-form-item>
            <!-- <el-form-item label="值班分队指挥员" prop="detachmentPeopleId">
          <el-select
            v-model="form.detachmentPeopleId"
            filterable
            clearable
            placeholder="请选择值班分队指挥员"
          >
            <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>-->
            <el-form-item
              label="值班分队指挥员电话"
              prop="detachmentPeoplePhone"
            >
              <el-input v-model="item.detachmentPeoplePhone" placeholder="" />
            </el-form-item>
            <el-form-item style="margin-left: 156px">
              <el-button
                type="primary"
                icon="el-icon-plus"
                size="mini"
                @click="addFormss"
                v-if="i == relatives.length - 1"
              ></el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                v-if="i > 0"
                @click="delFormss(i, item.id)"
              ></el-button>
            </el-form-item>
            <!-- </el-form> -->
          </el-row>
          <div class="jiben">值班情况</div>
          <el-form-item label prop="zhibanqingkaung">
            <el-input
              v-model="form.zhibanqingkaung"
              type="textarea"
              placeholder=""
            />
          </el-form-item>
          <div class="jiben">兵力情况</div>
          <el-form-item label prop="bingliqingkuang">
            <el-input
              v-model="form.bingliqingkuang"
              type="textarea"
              placeholder=""
            />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listTeamdutyinfo,
  getTeamdutyinfo,
  delTeamdutyinfo,
  addTeamdutyinfo,
  updateTeamdutyinfo,
  exportTeamdutyinfo,
  rllist,
  listPeople,
  getDept,
  delTeamdutyinfos,
} from "@/api/combatduty/teamdutyinfo";

export default {
  name: "Teamdutyinfo",
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
      // 大队值班表格数据
      teamdutyinfoList: [],
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
        confidentialName: null,
        confidentialId: null,
        confidentialPhone: null,
        politicalName: null,
        politicalId: null,
        politicalPhone: null,
        guaranteeName: null,
        guaranteeId: null,
        guaranteePhone: null,
        messengerName: null,
        messengerId: null,
        messengerPhone: null,
        detachmentName: null,
        detachmentId: null,
        detachmentNumber: null,
        detachmentPeopleName: null,
        detachmentPeopleId: null,
        detachmentPeoplePhone: null,
        zhibanqingkaung: null,
        bingliqingkuang: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        // dutyOffierPhone: [
        //   {
        //     required: true,
        //     message: "通信值班员电话不能为空",
        //     trigger: "blur",
        //   },
        // ],
      },
      qdflg: null,
      //人员数据
      rylist: [],
      //单位数据
      deptOptions: [],
      // 添加多个分队值班
      relatives: [
        {
          detachmentId: null,
          detachmentNumber: null,
          detachmentPeopleName: null,
          detachmentPeoplePhone: null,
        },
      ],
    };
  },
  created() {
    this.getList();
    this.gettreeselect();
    this.getrylist();
  },
  methods: {
    //增加多个分队值班
    addFormss() {
      this.relatives.push({
        detachmentId: null,
        detachmentNumber: null,
        detachmentPeopleName: null,
        detachmentPeoplePhone: null,
      });
    },
    //删除多个分队值班
    delFormss(e, id) {
      this.relatives.splice(e, 1);
      if (id) {
        delTeamdutyinfos(id).then(() => {});
      }
    },
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
    // detachmentIdFormat(row, column) {
    //   var deptName = "";
    //   this.deptOptions.map(item => {
    //     if (item.deptId == row.detachmentId) {
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
    dutyOffierIdFormat(row, column) {
      var deptName = "";
      this.rylist.map((item) => {
        if (item.id == row.dutyOffierId) {
          deptName = item.name;
        }
      });
      return deptName;
    },
    confidentialIdFormat(row, column) {
      var deptName = "";
      this.rylist.map((item) => {
        if (item.id == row.confidentialId) {
          deptName = item.name;
        }
      });
      return deptName;
    },
    politicalIdFormat(row, column) {
      var deptName = "";
      this.rylist.map((item) => {
        if (item.id == row.politicalId) {
          deptName = item.name;
        }
      });
      return deptName;
    },
    guaranteeIdFormat(row, column) {
      var deptName = "";
      this.rylist.map((item) => {
        if (item.id == row.guaranteeId) {
          deptName = item.name;
        }
      });
      return deptName;
    },
    messengerIdFormat(row, column) {
      var deptName = "";
      this.rylist.map((item) => {
        if (item.id == row.messengerId) {
          deptName = item.name;
        }
      });
      return deptName;
    },
    /** 查询大队值班列表 */
    getList() {
      this.loading = true;
      listTeamdutyinfo(this.queryParams).then((response) => {
        this.teamdutyinfoList = response.rows;

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
        confidentialName: null,
        confidentialId: null,
        confidentialPhone: null,
        politicalName: null,
        politicalId: null,
        politicalPhone: null,
        guaranteeName: null,
        guaranteeId: null,
        guaranteePhone: null,
        messengerName: null,
        messengerId: null,
        messengerPhone: null,
        detachmentName: null,
        detachmentId: null,
        detachmentNumber: null,
        detachmentPeopleName: null,
        detachmentPeopleId: null,
        detachmentPeoplePhone: null,
        zhibanqingkaung: null,
        bingliqingkuang: null,
      };
      this.relatives = [
        {
          detachmentId: null,
          detachmentNumber: null,
          detachmentPeopleName: null,
          detachmentPeoplePhone: null,
        },
      ];
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
      this.title = "添加大队值班";
      this.qdflg = 1;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTeamdutyinfo(id).then((response) => {
        this.form = response.data;
        if (this.form.teamduties.length != 0) {
          this.relatives = this.form.teamduties;
        }
        this.open = true;
        this.title = "修改大队值班";
        this.qdflg = 2;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            this.form.teamduties = this.relatives;
            updateTeamdutyinfo(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.timequjian = this.form.attendedTime;
            this.form.attendedTime = this.form.attendedTime.join(",");
            if (
              this.relatives[0].detachmentId ||
              this.relatives[0].detachmentNumber ||
              this.relatives[0].detachmentPeopleName ||
              this.relatives[0].detachmentPeoplePhone
            ) {
              this.form.teamduties = this.relatives;
            }
            addTeamdutyinfo(this.form).then((response) => {
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
          return delTeamdutyinfo(ids);
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
        .confirm("是否确认导出所有大队值班数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportTeamdutyinfo(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    //首长电话
    chiefDutyNamefun(val) {
      this.form.chiefDutyId = val.id;
      this.form.chiefDutyName = val.name;
      this.form.chiefDutyPhone = val.phone;
    },
    //作战值班电话
    combatDutyNamefun(val) {
      this.form.combatDutyId = val.id;
      this.form.combatDutyName = val.name;
      this.form.combatDutyPhone = val.phone;
    },
    //总值班员
    dutyOffierNamefun(val) {
      this.form.dutyOffierId = val.id;
      this.form.dutyOffierName = val.name;
      this.form.dutyOffierPhone = val.phone;
    },
    //机要值班员
    confidentialNamechange(val) {
      this.form.confidentialId = val.id;
      this.form.confidentialName = val.name;
      this.form.confidentialPhone = val.phone;
    },
    //政治工作部值班员
    politicalNamechange(val) {
      this.form.politicalId = val.id;
      this.form.politicalName = val.name;
      this.form.politicalPhone = val.phone;
    },
    //保障部值班员
    guaranteeNamechange(val) {
      this.form.guaranteeId = val.id;
      this.form.guaranteeName = val.name;
      this.form.guaranteePhone = val.phone;
    },
    //值班通信员
    messengerNamechange(val) {
      this.form.messengerId = val.id;
      this.form.messengerName = val.name;
      this.form.messengerPhone = val.phone;
    },
    //值班分队指挥员
    detachmentPeopleNamechange(val) {
      this.form.detachmentPeopleId = val.id;
      this.form.detachmentPeopleName = val.name;
      this.form.detachmentPeoplePhone = val.phone;
    },
    detachmentNamechange(val) {
      this.form.detachmentId = val.deptId;
      this.form.detachmentName = val.deptName;
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
  width: 1086px;
  height: 104px;
}
::v-deep .el-dialog__footer {
  margin-right: 20px !important;
}
::v-deep .el-dialog__body {
  padding: 30px 0px 30px 25px;
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
</style>

