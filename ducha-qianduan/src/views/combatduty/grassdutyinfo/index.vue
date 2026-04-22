<template>
  <div class="app-container">
    <el-row>
      <el-col :span="1.5" class="isSubmit">
        <el-radio-group v-model="queryParams.type" @change="caoZuoTypefun">
          <el-radio-button label="1">今日值班</el-radio-button>
          <el-radio-button label="2">次日值班</el-radio-button>
        </el-radio-group>
      </el-col>
    </el-row>
    <hr />
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="data">
          <p>日期</p>
          <div class="a1"></div>
          <el-date-picker
            v-model="queryParams.attendedTime"
            type="date"
            :editable="false"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            clearable
            @change="tiemes"
          >
          </el-date-picker>
        </div>
        <div class="dadui">
          <img src="../../../images/星星.png" alt />
          大队
        </div>
        <div class="tree">
          <el-tree
            class="userAgrees"
            style="height: calc(100vh - 170px); overflow: auto"
            :data="deptOptionss"
            :props="defaultProps"
            @node-click="handleNodeClick"
            default-expand-all
            highlight-current
            :expand-on-click-node="false"
          ></el-tree>
        </div>
      </el-col>
      <el-col :span="1.5" class="mb8">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['combatduty:grassdutyinfo:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['combatduty:grassdutyinfo:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['combatduty:grassdutyinfo:export']"
          >导出今日</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" @click="handlelie"
          >值班要素</el-button
        >
      </el-col>
      <el-col :span="20" :xs="24">
        <el-table
          v-loading="loading"
          :data="grassdutyinfoList"
          @selection-change="handleSelectionChange"
          :header-cell-style="{ background: '#003c69', color: 'white' }"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序号" align="center">
            <template slot-scope="scope">
              <span>{{
                (queryParams.pageNum - 1) * queryParams.pageSize +
                scope.$index +
                1
              }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column label="值班岗位" align="center" prop="id" /> -->
          <el-table-column
            label="单位名称"
            align="center"
            prop="unitName"
            width="150"
          />
          <el-table-column
            label="值班首长"
            align="center"
            prop="chiefDutyName"
          />
          <el-table-column
            label="值班首长职务"
            align="center"
            prop="chiefDutyZhiwu"
            width="100"
          >
            <template slot-scope="scope">
              <dict-tag
                :options="dict.type.sys_duty_hief"
                :value="scope.row.chiefDutyZhiwu"
              />
            </template>
          </el-table-column>
          <el-table-column
            label="办公电话"
            align="center"
            prop="telephoneBg"
            width="120"
          />
          <el-table-column
            label="宿舍电话"
            align="center"
            prop="telephoneSs"
            width="120"
          />
          <el-table-column
            label="值班首长手机"
            align="center"
            prop="chiefDutyPhone"
            width="120"
          />
          <!-- <el-table-column label="值班员" align="center" prop="dutyOffierName" /> -->
          <!-- <el-table-column
            label="值班员"
            align="center"
            prop="dutyOffierId"
            :formatter="dutyOffierIdFormat"
          /> -->
          <el-table-column
            label="值班员"
            align="center"
            prop="dutyOffierName"
          />
          <el-table-column
            label="值班员职务"
            align="center"
            prop="dutyOffierZw"
            width="120"
          >
            <template slot-scope="scope">
              <dict-tag
                :options="dict.type.sys_zhiban"
                :value="scope.row.dutyOffierZw"
              />
            </template>
          </el-table-column>
          <!-- <el-table-column label="值班员手机" align="center" prop="dutyOffierPhone" />
      <el-table-column label="值班电话" align="center" prop="dutyPhone" />-->
          <el-table-column
            label="值班岗位"
            align="center"
            prop="dutyGangwei"
            :formatter="dutyGangweiFormat"
            width="150"
          />
          <el-table-column
            label="值班时间"
            align="center"
            prop="attendedTime"
            width="180"
          >
            <template slot-scope="scope">
              <span>{{
                parseTime(scope.row.attendedTime, "{y}-{m}-{d}")
              }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="200"
            class-name="small-padding fixed-width"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button
                size="btu"
                type="text"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['combatduty:grassdutyinfo:edit']"
                >修改</el-button
              >
              <el-button
                size="btd"
                type="text"
                @click="handleDelete(scope.row)"
                v-hasPermi="['combatduty:grassdutyinfo:remove']"
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
      </el-col>
    </el-row>

    <!-- 添加或修改基层值班对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1037px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
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
        <el-form-item label="单位名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="值班首长" prop="chiefDutyName">
          <el-select
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
          </el-select>
        </el-form-item>
        <el-form-item label="值班首长电话" prop="chiefDutyPhone">
          <el-input v-model="form.chiefDutyPhone" placeholder="" />
        </el-form-item>
        <el-form-item label="值班首长职务" prop="chiefDutyZhiwu">
          <el-select v-model="form.chiefDutyZhiwu" placeholder="" clearable>
            <el-option
              v-for="dict in dict.type.sys_duty_hief"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="办公电话" prop="telephoneBg">
          <el-input v-model="form.telephoneBg" placeholder="" />
        </el-form-item>
        <el-form-item label="宿舍电话" prop="telephoneSs">
          <el-input v-model="form.telephoneSs" placeholder="" />
        </el-form-item>

        <el-form-item label="值班员" prop="dutyOffierName">
          <el-select
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
          </el-select>
        </el-form-item>
        <el-form-item label="值班员电话" prop="dutyOffierPhone">
          <el-input v-model="form.dutyOffierPhone" placeholder="" />
        </el-form-item>
        <el-form-item label="值班员职务" prop="dutyOffierZw">
          <el-select v-model="form.dutyOffierZw" placeholder="" clearable>
            <el-option
              v-for="dict in dict.type.sys_zhiban"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="值班电话" prop="dutyPhone">
          <el-input v-model="form.dutyPhone" placeholder="" />
        </el-form-item>
        <el-form-item label="值班岗位" prop="dutyGangwei">
          <el-select v-model="form.dutyGangwei" clearable placeholder="">
            <el-option
              v-for="dict in deptOptions"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict.deptId"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 列表的展示 -->
    <el-dialog
      :title="title"
      :visible.sync="open1"
      width="1700px"
      append-to-body
      :close-on-click-modal="false"
      custom-class="box1"
    >
      <table
        border="1"
        style="
          border-collapse: collapse;
          border: none;
          mso-border-left-alt: 0.5pt solid windowtext;
          mso-border-top-alt: 0.5pt solid windowtext;
          mso-border-right-alt: 0.5pt solid windowtext;
          mso-border-bottom-alt: 0.5pt solid windowtext;
          mso-border-insideh: 0.5pt solid windowtext;
          mso-border-insidev: 0.5pt solid windowtext;
          mso-padding-alt: 0pt 0pt 0pt 0pt;
          display: inline-block;
          color: #fff;
        "
        width="650px"
        v-if="table1.length > 0"
      >
        <tbody style="border-bottom: 1px solid">
          <tr align="center" class="tr">
            <td width="220" height="40">类别</td>
            <td width="220" height="40">值班首长</td>
            <td width="220" height="40">电话</td>
          </tr>
          <tr align="center" class="tr" v-for="(item, i) in table1" :key="i">
            <td width="220" height="40">{{ item.unitName }}</td>
            <td width="220" height="40">{{ item.dutyOffierName }}</td>
            <td width="220" height="40">{{ item.telephoneBg }}</td>
          </tr>
        </tbody>
      </table>
      <table
        border="1"
        style="
          border-collapse: collapse;
          border: none;
          mso-border-left-alt: 0.5pt solid windowtext;
          mso-border-top-alt: 0.5pt solid windowtext;
          mso-border-right-alt: 0.5pt solid windowtext;
          mso-border-bottom-alt: 0.5pt solid windowtext;
          mso-border-insideh: 0.5pt solid windowtext;
          mso-border-insidev: 0.5pt solid windowtext;
          mso-padding-alt: 0pt 0pt 0pt 0pt;
          display: inline-block;
          color: #fff;
        "
        width="700px"
        v-if="table2.length > 0"
      >
        <tbody
          style="border-bottom: 1px solid; border-left: 1px solid transparent"
        >
          <tr align="center" class="tr">
            <td width="220" height="40">类别</td>
            <td width="220" height="40">值班首长</td>
            <td width="220" height="40">电话</td>
          </tr>
          <tr align="center" class="tr" v-for="(item, i) in table2" :key="i">
            <td width="220" height="40">{{ item.unitName }}</td>
            <td width="220" height="40">{{ item.dutyOffierName }}</td>
            <td width="220" height="40">{{ item.telephoneBg }}</td>
          </tr>
        </tbody>
      </table>
      <table
        border="1"
        style="
          border-collapse: collapse;
          border: none;
          mso-border-left-alt: 0.5pt solid windowtext;
          mso-border-top-alt: 0.5pt solid windowtext;
          mso-border-right-alt: 0.5pt solid windowtext;
          mso-border-bottom-alt: 0.5pt solid windowtext;
          mso-border-insideh: 0.5pt solid windowtext;
          mso-border-insidev: 0.5pt solid windowtext;
          mso-padding-alt: 0pt 0pt 0pt 0pt;
          display: inline-block;
          color: #fff;
        "
        width="700px"
        v-if="table3.length > 0"
      >
        <tbody
          style="border-bottom: 1px solid; border-left: 1px solid transparent"
        >
          <tr align="center" class="tr">
            <td width="220" height="40">类别</td>
            <td width="220" height="40">值班首长</td>
            <td width="220" height="40">电话</td>
          </tr>
          <tr align="center" class="tr" v-for="(item, i) in table3" :key="i">
            <td width="220" height="40">{{ item.unitName }}</td>
            <td width="220" height="40">{{ item.dutyOffierName }}</td>
            <td width="220" height="40">{{ item.telephoneBg }}</td>
          </tr>
        </tbody>
      </table>

      <div slot="footer" class="dialog-footer">
        <el-button @click="open1 = false" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listGrassdutyinfo,
  getGrassdutyinfo,
  delGrassdutyinfo,
  addGrassdutyinfo,
  updateGrassdutyinfo,
  exportGrassdutyinfo,
  listPeople,
  getDept,
  listPeoples,
  todaydutyinfo
} from "@/api/combatduty/grassdutyinfo";
import { getWeiShu } from "@/api/grassrootsregistration/bdglmeeting";
import { getUserProfile } from "@/api/system/user";

export default {
  name: "Grassdutyinfo",
  dicts: ["sys_duty_hief", "sys_duty_hief", "sys_zhiban"],
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
      // 基层值班表格数据
      grassdutyinfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        attendedTime: null,
        unitName: null,
        unitId: null,
        chiefDutyName: null,
        chiefDutyId: null,
        chiefDutyZhiwu: null,
        telephoneBg: null,
        telephoneSs: null,
        chiefDutyPhone: null,
        dutyOffierName: null,
        dutyOffierId: null,
        dutyOffierZw: null,
        dutyOffierPhone: null,
        dutyPhone: null,
        dutyGangwei: null,
        type: 1,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        attendedTime: [
          { required: true, message: "值班时间不能为空", trigger: "blur" },
        ],
        chiefDutyName: [
          { required: true, message: "值班首长不能为空", trigger: "blur" },
        ],
        chiefDutyPhone: [
          { required: true, message: "值班首长电话不能为空", trigger: "blur" },
        ],
        chiefDutyZhiwu: [
          { required: true, message: "值班首长职务不能为空", trigger: "blur" },
        ],
        telephoneBg: [
          { required: true, message: "办公电话不能为空", trigger: "blur" },
        ],
        telephoneSs: [
          { required: true, message: "宿舍电话不能为空", trigger: "blur" },
        ],
        dutyOffierName: [
          { required: true, message: "值班员不能为空", trigger: "blur" },
        ],
        dutyOffierPhone: [
          { required: true, message: "值班员电话不能为空", trigger: "blur" },
        ],
        dutyOffierZw: [
          { required: true, message: "值班员职务不能为空", trigger: "blur" },
        ],
        dutyPhone: [
          { required: true, message: "值班电话不能为空", trigger: "blur" },
        ],
        dutyGangwei: [
          { required: true, message: "值班岗位不能为空", trigger: "blur" },
        ],
      },
      //人员数据
      rylist: [],
      //单位数据
      deptOptions: [],
      qdflg: null,
      // 左侧树形
      deptOptionss: [],
      // 树形配置
      defaultProps: {
        children: "children",
        label: "label",
      },
      danWie: "",
      deptId: null,
      // 获取全部人员列表
      QuanRenList: [],
      open1: false,
      // 获取今日值班数据列表
      dayList: [],
      table1: [],
      table2: [],
      table3: [],
    };
  },
  created() {
    this.getUser();
    this.getList();
    this.gettreeselect();
    this.treeselect();
    this.getQuanRen();
    this.getAllDay();
  },
  methods: {
    // 获取今日列表所有单位
    getAllDay() {
      todaydutyinfo().then((res) => {
        this.dayList = res.data;
        this.getJi();
      });
    },
    getJi() {
     if(this.dayList.length>0){
      this.dayList.forEach((element, i) => {
        if (i >= 0 && i <= 7) {
          this.table1.push(element);
        } else if (i > 7 && i <= 15) {
          this.table2.push(element);
        } else if (i > 15 && i <= 33) {
          this.table3.push(element);
        }
      });
     }
      // console.log(this.table1);
    },
    // 列表展示
    handlelie() {
      this.open1 = true;
      this.title = "值班要素";
    },
    // 获取全部人员列表
    getQuanRen() {
      listPeoples().then((res) => {});
    },
    // 点击外面时间触发
    tiemes() {
      this.queryParams.type = null;
      this.getList();
    },
    // 获取登录信息
    getUser() {
      getUserProfile().then((response) => {
        console.log("获取登录信息", response)
        this.deptId = response.data.dept.deptId;
        this.danWie = response.data.dept.deptName;
      });
    },
    //外面树形选择搜索
    handleNodeClick(data) {
      this.queryParams.type = null;
      this.queryParams.unitId = data.id;
      this.getList();
    },
    // 获取外面部门树形
    treeselect() {
      getWeiShu().then((res) => {
        this.deptOptionss = res.data;
      });
    },
    //获取人员
    getrylist(id) {
      listPeople({ deptId: id }).then((res) => {
        if (res.code == 200) {
          this.rylist = res.data;
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
    dutyOffierIdFormat(row, column) {
      var deptName = "";
      this.rylist.map((item) => {
        if (item.id == row.dutyOffierId) {
          deptName = item.name;
        }
      });
      return deptName;
    },
    //单位
    // unitIdFormat(row, column) {
    //   var deptName = "";
    //   this.deptOptions.map(item => {
    //     if (item.deptId == row.unitId) {
    //       deptName = item.deptName;
    //     }
    //   });
    //   return deptName;
    // },
    //岗位
    dutyGangweiFormat(row, column) {
      var deptName = "";
      this.deptOptions.map((item) => {
        if (item.deptId == Number(row.dutyGangwei)) {
          deptName = item.deptName;
        }
      });
      return deptName;
    },
    /** 查询基层值班列表 */
    getList() {
      this.loading = true;
      listGrassdutyinfo(this.queryParams).then((response) => {
        this.grassdutyinfoList = response.rows;
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
        unitName: null,
        unitId: null,
        chiefDutyName: null,
        chiefDutyId: null,
        chiefDutyZhiwu: null,
        telephoneBg: null,
        telephoneSs: null,
        chiefDutyPhone: null,
        dutyOffierName: null,
        dutyOffierId: null,
        dutyOffierZw: null,
        dutyOffierPhone: null,
        dutyPhone: null,
        dutyGangwei: null,
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
      this.queryParams.chiefDutyId = null;
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
      this.title = "添加基层值班";
      this.qdflg = 1;
      this.form.unitName = this.danWie;
      this.form.unitId = this.deptId;
      console.log("添加基层值班 this.form.unitId", this.form.unitId);
      console.log("添加基层值班 this.form.unitName", this.form.unitName);
      this.getrylist(this.form.unitId);
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getGrassdutyinfo(id).then((response) => {
        this.form = response.data;
        this.form.dutyGangwei = Number(this.form.dutyGangwei);
        this.open = true;
        this.title = "修改基层值班";
        this.qdflg = 2;
        this.getrylist(this.form.unitId);
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            console.log(1);
            updateGrassdutyinfo(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.timequjian = this.form.attendedTime;
            this.form.attendedTime = this.form.attendedTime.join(",");
            addGrassdutyinfo(this.form).then((response) => {
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
          return delGrassdutyinfo(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      var queryParams = {};
      queryParams.type=this.queryParams.type;
      this.$modal
        .confirm("是否确认导出今日所有基层值班数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportGrassdutyinfo(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    //值班分类
    caoZuoTypefun() {
      this.queryParams.unitId = null;
      this.queryParams.attendedTime = null;
      this.getList();
    },
    //首长电话
    chiefDutyNamefun(val) {
      this.form.chiefDutyId = val.id;
      this.form.chiefDutyName = val.name;
      this.form.chiefDutyPhone = val.phone;
    },
    //总值班员
    dutyOffierNamefun(val) {
      this.form.dutyOffierId = val.id;
      this.form.dutyOffierName = val.name;
      this.form.dutyOffierPhone = val.phone;
    },
    unitNamechange(val) {
      this.form.unitId = val.deptId;
      this.form.unitName = val.deptName;
    },
    // detachmentwoNamechange(val) {
    //   this.form.detachmentwoId = val.deptId;
    //   this.form.detachmentwoName = val.deptName;
    // }
  },
};
</script>
<style scoped>
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
}
::v-deep .el-textarea__inner {
  width: 884px;
  height: 104px;
  margin-left: 29px;
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
  width: 108px !important;
}
::v-deep .el-input__inner {
  /* width: 200px !important;
  height: 36px; */
  background: transparent !important;
  color: #fff;
}
::v-deep .el-dialog__body {
  padding: 30px 0px 30px 46px;
}
::v-deep .el-dialog__footer {
  margin-right: 30px !important;
}
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
/* 基本信息背景 */
.jiben {
  width: 952px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
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
::v-deep .el-range-input {
  background: #004d86;
  color: #fff;
  font-size: 12px;
}
::v-deep .el-range-separator {
  color: #fff;
  font-size: 12px;
}
.el-form-item ::v-deep .el-input__inner {
  width: 200px;
  height: 36px;
  background: #004d86;
  color: #fff;
  border: 1px solid #fff !important;
}
::v-deep .el-date-editor.el-input {
  width: 200px;
  height: 36px;
}
.el-select-dropdown__item {
  /* background-color: white; */
  color: #000 !important;
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
/* 头部样式 */
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
/* 划过效果 */
::v-deep .el-tree-node__content:hover {
  /* color: #fff; */
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
/* .app-container {
  display: flex;
} */
/* 左侧树状盒子 */
.app-container .box-lfet {
  width: 15%;
  min-height: 740px;
  /* background: #003156; */
  /* padding: 10px; */
  margin-right: 10px;
  /* border: 1px solid white; */
}
/* 右侧内容盒子 */
.app-container .box-right {
  flex: 1;
}
.app-container .box-lfet .data {
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
  background: #003156;
  color: #fff;
  margin-right: 2px;
  padding-top: 7px;
}
::v-deep .el-dialog {
  background-color: #004d86 !important;
}
::v-deep .el-input__suffix {
  right: 60px;
}
::v-deep .el-tree-node__content {
  height: 32px;
}
::v-deep
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  height: 32px;
  color: #fff !important;
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
::v-deep .canjiaren .el-input__inner {
  width: 560px;
}
::v-deep .el-input__suffix-inner {
  margin-right: -80px;
}
.app-container .data {
  height: 40px;
  background: #003156;
  margin-bottom: 12px;
  color: #fff;
  display: flex;
  font-size: 13px;
  line-height: 25px;
  position: relative;
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
  right: -232px;
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
::v-deep .data .el-icon-date:before {
  content: none;
}
::v-deep .box1 .el-dialog__body {
  display: flex;
  margin-top: 43px;
  font-size: 16px;
  margin-right: 31px;
}
</style>
