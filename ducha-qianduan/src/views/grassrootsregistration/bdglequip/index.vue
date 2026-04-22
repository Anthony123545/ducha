<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="unitName">
        <el-input
          placeholder="请输入配发单位"
          v-model="queryParams.unitName"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item label="" prop="granttime">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.granttime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择配发时间"
          @change="item"
          :editable="false"
        >
        </el-date-picker>
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
          v-hasPermi="['grassrootsregistration:bdglequip:add']"
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
          v-hasPermi="['grassrootsregistration:bdglequip:edit']"
          >修改</el-button
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
          v-hasPermi="['grassrootsregistration:bdglequip:remove']"
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
          v-hasPermi="['grassrootsregistration:bdglequip:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="bdglequipList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      style="width: 100%"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="序号" align="center" prop="id" /> -->
      <el-table-column label="序号" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="单位"
        align="center"
        prop="unitName1"
        width="150"
      />
      <el-table-column label="类型" align="center" prop="type" />
      <el-table-column label="物品名称" align="center" prop="title" />
      <el-table-column
        label="配发单位"
        align="center"
        prop="unitName"
        width="150"
      />
      <el-table-column label="配发时间" align="center" prop="granttime" />
      <el-table-column label="配发数量" align="center" prop="grantnum" />
      <el-table-column label="库存数量" align="center" prop="stocknum" />
      <el-table-column label="入库时间" align="center" prop="intime" />
      <el-table-column label="入库数量" align="center" prop="innum" />
      <el-table-column label="出库时间" align="center" prop="outtime" />
      <el-table-column label="出库数量" align="center" prop="outnum" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
      >
        <template slot-scope="scope">
          <el-button
            size="btk"
            type="text"
            @click="handleChakan(scope.row)"
            v-hasPermi="['grassrootsregistration:bdgldiary:edit']"
            ><span class="chakan">查看</span></el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglequip:edit']"
            ><span class="btu">修改</span></el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglequip:remove']"
            ><span class="delete">删除</span></el-button
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

    <!-- 添加或修改训练器材/教材登记薄对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1016px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="jiben">基本信息</div>

      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <el-form-item label="单位" prop="unitId1">
          <treeselect
            v-model="form.unitId1"
            :options="deptOptions"
            placeholder="选择单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option
              v-for="item in ziDian"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictLabel"
              :disabled="item.status == 1"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="物品名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入物品名称" />
        </el-form-item>
        <el-form-item label="配发单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="deptOption"
            placeholder="选择单位"
            @select="selectPeos"
          />
        </el-form-item>
        <el-form-item label="配发时间" prop="granttime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.granttime"
            type="date"
            :editable="false"
            value-format="yyyy-MM-dd"
            placeholder="请选择配发时间"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item label="配发数量" prop="grantnum">
          <el-input-number
            v-model="form.grantnum"
            controls-position="right"
            :min="0"
            :max="99999999"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="自筹数量" prop="ownnum">
          <el-input-number
            v-model="form.ownnum"
            controls-position="right"
            :min="0"
            :max="99999999"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="库存数量" prop="stocknum">
          <el-input-number
            v-model="form.stocknum"
            controls-position="right"
            :min="0"
            :max="99999999"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="入库时间" prop="intime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.intime"
            type="date"
            :editable="false"
            value-format="yyyy-MM-dd"
            placeholder="请选择入库时间"
            @change="timeDay"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="入库数量" prop="innum">
          <el-input-number
            v-model="form.innum"
            controls-position="right"
            :min="0"
            :max="99999999"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="入库经手人" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请选择入库经手人"
            clearable
          >
            <el-option
              v-for="item in renYuan"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              :disabled="item.status == 1"
              @click.native="ruKu(item.name)"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出库时间" prop="outtime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.outtime"
            type="date"
            :editable="false"
            value-format="yyyy-MM-dd"
            placeholder="请选择出库时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="出库数量" prop="outnum">
          <el-input-number
            v-model="form.outnum"
            controls-position="right"
            :min="0"
            :max="99999999"
            class="input_xiala"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="出库经手人" prop="peopleId1">
          <el-select
            v-model="form.peopleId1"
            placeholder="请选择出库经手人"
            clearable
          >
            <el-option
              v-for="item in renYuan"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              :disabled="item.status == 1"
              @click.native="chuKu(item.name)"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="质量情况" prop="quality">
          <el-input v-model="form.quality" placeholder="请输入质量情况" />
        </el-form-item>

        <div class="jiben">备注</div>
        <div class="box4">
          <el-input
            type="textarea"
            :rows="3"
            placeholder="请输入内容"
            v-model="form.info"
            autosize
          >
          </el-input>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(1)">确 定</el-button>
        <el-button @click="cancel" size="btn">取消</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="menuRoleVisible"
      :title="title"
      append-to-body
      id="chakan"
      :close-on-click-modal="false"
    >
      <bdglequip
        v-if="menuRoleVisible"
        ref="menuRole"
        :message="wordInfo"
      ></bdglequip>
    </el-dialog>
  </div>
</template>

<script>
import bdglequip from "@/components/look_word/bdglequip.vue";
import {
  listBdglequip,
  getBdglequip,
  delBdglequip,
  addBdglequip,
  updateBdglequip,
  exportBdglequip,
  getList,
  equipuntits,
} from "@/api/grassrootsregistration/bdglequip";
import {
  getZhuChiRen,
  getShouBu,
} from "@/api/grassrootsregistration/bdglmeeting";
import { getDicts } from "@/api/system/dict/data";
import { getDept, getDept2 } from "@/api/grassrootsregistration/bdglmeeting";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { jieZhiList } from "@/api/grassrootsregistration/bdgldiary";

export default {
  name: "Bdglequip",
  components: {
    Treeselect,
    bdglequip,
  },
  data() {
    return {
      //查看页面
      menuRoleVisible: false,
      wordInfo: [],
      //判断关闭
      style: "2",
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
      // 训练器材/教材登记薄表格数据
      bdglequipList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        title: null,
        unitId: null,
        granttime: null,
        grantnum: null,
        ownnum: null,
        stocknum: null,
        intime: null,
        innum: null,
        peopleId: null,
        outtime: null,
        outnum: null,
        peopleId1: null,
        quality: null,
        info: null,
        unitId1: null,
        createtime: null,
        adminId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitId1: [
          { required: true, message: "单位名称不能为空", trigger: "blur" },
        ],
        type: [{ required: true, message: "类型不能为空", trigger: "blur" }],
        unitId: [
          { required: true, message: "配发单位不能为空", trigger: "blur" },
        ],
        title: [
          { required: true, message: "物品名称不能为空", trigger: "blur" },
        ],
        granttime: [
          { required: true, message: "配发时间不能为空", trigger: "blur" },
        ],
        intime: [
          { required: true, message: "入库时间不能为空", trigger: "blur" },
        ],
        peopleId: [
          { required: true, message: "入库经手人不能为空", trigger: "blur" },
        ],
      },
      roleOptions: [],
      //人员
      renYuan: [],
      // 树形1
      deptOptions: [],
      // 树形2
      deptOption: [],
      // 树形配置对象
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 查询字典
      dictType: {
        dictType: "xunLian",
      },
      // 字典的列表
      ziDian: [],
      // 搜索的部门
      BuMeng: [],
      // 派发单位获取
      PaiFa: [],
      // 派发单位
      defaultProp: {
        children: "children",
        label: "deptName",
      },
      // 时间默认
      times: null,
      form1: {},
      // 当月日期
      current_month: [],
      // 下月日期
      current_month1: [],
      // 日期开关状态
      dayTime: null,
    };
  },
  created() {
    this.getList();
    this.getBumen();
    this.getTiem();
    this.getYear();
    this.getYear();
    // 获取当月
    this.getDay();
    // 获取下月
    this.getDayX();
    jieZhiList().then((res) => {
      this.dayTime = res.rows[0].time;
    });
  },
  methods: {
    // 获取当月日期
    getDay() {
      // 获取标准时间
      let now = new Date();
      // 年
      var year = new Date().getFullYear();
      // 月
      var month = new Date().getMonth() + 1;
      // 日
      var day = new Date(year, month, 0).getDate();
      // console.log(now);
      let current_month_num = day;
      this.current_month = [];
      for (let i = 1; i <= current_month_num; i++) {
        let day = now.setDate(i);
        let date = new Date(day); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        let Y = date.getFullYear() + "-";
        let M =
          (date.getMonth() + 1 < 10
            ? "0" + (date.getMonth() + 1)
            : date.getMonth() + 1) + "-";
        let D =
          (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "";
        this.current_month.push(Y + M + D);
        // console.log(this.current_month);
      }
    },
    // 获取下个月日期
    getDayX() {
      // 获取标准时间
      let now = new Date();
      // 年
      var year = new Date().getFullYear();
      // 月
      var month = new Date().getMonth() + 2;
      // 日
      var day = new Date(year, month, 0).getDate();
      let current_month_num1 = day;
      for (let i = 1; i <= current_month_num1; i++) {
        let day = now.setDate(i);
        let date = new Date(day); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        let Y = date.getFullYear() + "-";
        let M =
          (date.getMonth() + 2 < 10
            ? "0" + (date.getMonth() + 2)
            : date.getMonth() + 2) + "-";
        let D =
          (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "";
        this.current_month.push(Y + M + D);
      }
    },
    timeDay(val) {
      var date1 = Math.round(new Date(val) / 1000);
      // 当月时间
      this.current_month.forEach((element) => {
        // 十号之前是1675987200
        var date2 = Math.round(new Date(element) / 1000);
        if (date1 > 1678406400 && this.dayTime == 1) {
          this.$confirm("请在规定时间内填写", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              this.form.intime = null;
            })
            .catch(() => {
              this.form.intime = null;
            });
        }
        if (date1 < 1675180250 && this.dayTime == 1) {
          this.$confirm("请在规定时间内填写", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              this.form.intime = null;
            })
            .catch(() => {
              this.form.intime = null;
            });
        }
      });
    },
    getYear() {
      var myDate = new Date();
      var tYear = myDate.getFullYear();
      this.form1.year = tYear;
    },
    // 获取当前日期
    getTiem() {
      var today = new Date();
      var y = today.getFullYear();
      var M = today.getMonth() + 1;
      var d = today.getDate();
      if (M < 10) {
        M = "0" + M;
      }
      if (d < 10) {
        d = "0" + d;
      }
      this.times = y + "-" + M + "-" + d;
    },
    // 重置表单人员
    ChongZhiRen() {
      this.form.peopleId = null;
      this.form.peopleId1 = null;
    },
    // 获取主持人
    getZhuChi(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 单位选择时触发
    selectPeo(val) {
      this.ChongZhiRen();
      this.form.unitId1 = val.id;
      this.getZhuChi(this.form.unitId1);
    },
    // 查询配发部门列表
    getPaiFa() {
      getList().then((res) => {
        this.PaiFa = res.data;
      });
    },
    // 外面时间搜索
    item() {
      this.getList();
    },
    // 外面搜索单位
    company() {
      this.getList();
    },
    // 查询搜索单位
    getBumen() {
      getShouBu().then((res) => {
        this.BuMeng = res.data;
      });
    },
    // 查询电子产品
    getdict() {
      getDicts(this.dictType.dictType).then((res) => {
        this.ziDian = res.data;
      });
    },
    // 入库选中触发
    ruKu(name) {
      this.form.peopleName = name;
    },
    // 出库选中触发
    chuKu(name) {
      this.form.people1Name = name;
    },
    // 配发单位选中触发
    selectPeos(val) {
      this.form.unitId = val.id;
      this.getZhuChi(this.form.unitId);
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.deptOptions = response.data;
      });
    },
    /** 查询部门下拉树结构2 */
    getTreeselect2() {
      getDept2().then((response) => {
        this.deptOption = response.data;
      });
    },
    /** 查询训练器材/教材登记薄列表 */
    getList() {
      this.loading = true;
      listBdglequip(this.queryParams).then((response) => {
        this.bdglequipList = response.rows;
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
        type: null,
        title: null,
        unitId: null,
        granttime: null,
        grantnum: null,
        ownnum: null,
        stocknum: null,
        intime: null,
        innum: null,
        peopleId: null,
        outtime: null,
        outnum: null,
        peopleId1: null,
        quality: null,
        info: null,
        unitId1: null,
        createtime: null,
        adminId: null,
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
    /** 对话框重置按钮操作 */
    resertwo() {
      this.reset();
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      //人员
      this.renYuan = [];
      this.getTreeselect();
      this.getTreeselect2();
      this.getPaiFa();
      this.getdict();
      this.reset();
      this.open = true;
      this.style = 2;
      this.title = "添加训练器材/教材登记薄";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.form.unitId = row.unitId;
      this.getZhuChi(this.form.unitId);
      this.getTreeselect();
      this.getTreeselect2();
      this.getBumen();
      this.getdict();
      this.reset();
      const id = row.id || this.ids;
      getBdglequip(id).then((response) => {
        this.form = response.data;
        this.getZhuChi(response.data.unitId);
        this.getZhuChi(response.data.unitId1);
        this.open = true;
        this.title = "修改训练器材/教材登记薄";
      });
    },
    //查看按钮操作
    handleChakan(row) {
      this.form1.unitid = row.unitId;
      // 是否隐藏按钮
      this.opens = false;
      equipuntits(this.form1).then((response) => {
        this.wordInfo = response.data;
        this.title = "查看训练器材/教材登记薄";
        this.menuRoleVisible = true;
      });
    },

    /** 提交按钮 */
    submitForm(style) {
      this.style = style;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            // 出库经手人
            if (this.form.peopleId1 == null) {
              this.form.people1Name = " ";
            }
            // 质量情况
            if (this.form.quality == null) {
              this.form.quality = " ";
            }
            updateBdglequip(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            // 出库经手人
            if (this.form.peopleId1 == null) {
              this.form.people1Name = " ";
            }
            // 质量情况
            if (this.form.quality == null) {
              this.form.quality = " ";
            }
            addBdglequip(this.form).then((response) => {
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
        .confirm("是否确认删除该数据？")
        .then(function () {
          return delBdglequip(ids);
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
        .confirm("是否确认导出所有训练器材/教材登记薄数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBdglequip(queryParams);
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
  border-color: #fff !important;
}
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
/* 基本信息背景 */
.jiben {
  width: 920px;
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
::v-deep .el-input__inner {
  cursor: pointer !important;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 30px 50px;
}
::v-deep .el-dialog__body {
  margin: 10px 35px 20px 44px;
  padding-top: 20px !important;
  box-sizing: border-box;
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
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 18px 42px 30px 0px;
  margin-right: 30px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px !important;
}
::v-deep .el-dialog__body {
  padding: none !important;
}
::v-deep .box4 .el-textarea__inner {
  min-height: 120px !important;
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
/* 文本域提示文字颜色 */
::v-deep ::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}
#chakan /deep/ .el-dialog__body {
  padding: 30px 0px 30px 23px;
}
</style>