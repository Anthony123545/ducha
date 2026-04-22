<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="deptName">
        <el-input
          placeholder="请输入单位"
          v-model="queryParams.deptName"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="confidential">
        <el-input
          v-model="queryParams.confidential"
          placeholder="请输入文件密级和保密期限"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
          @input="wenJie"
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
          v-hasPermi="['grassrootsregistration:bdglfilemanagement:add']"
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
          v-hasPermi="['grassrootsregistration:bdglfilemanagement:edit']"
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
          v-hasPermi="['grassrootsregistration:bdglfilemanagement:remove']"
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
          v-hasPermi="['grassrootsregistration:bdglfilemanagement:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="bdglfilemanagementList"
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
      <el-table-column label="单位" align="center" prop="deptName" />
      <el-table-column label="借阅人" align="center" prop="borrowName" />
      <el-table-column label="文件名称" align="center" prop="name" />
      <el-table-column
        label="文件密级和保密期限"
        align="center"
        prop="confidential"
      />
      <el-table-column label="文件份数" align="center" prop="copies" />
      <el-table-column label="签收位置" align="center" prop="signLocation" />
      <el-table-column
        label="收文时间"
        align="center"
        prop="incomingDispatchesTime"
        width="200"
      >
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.incomingDispatchesTime, "{y}-{m}-{d}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="借出时间"
        align="center"
        prop="loanTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.loanTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="清退时间（销毁时间）"
        align="center"
        prop="destroyTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.destroyTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="接收人（监销人）"
        align="center"
        prop="supervisorName"
      />

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
            v-hasPermi="['grassrootsregistration:bdglfilemanagement:edit']"
            ><span class="edit">修改</span></el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglfilemanagement:remove']"
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

    <!-- 添加或修改文件管理登计本对话框 -->
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
        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="deptOptions"
            placeholder="请选择单位"
            @select="selectPeo"
            @open="blurSelect"
          />
        </el-form-item>
        <el-form-item label="文件编号" prop="number">
          <el-input
            v-model="form.number"
            placeholder="请输入文件编号"
            disabled
          />
        </el-form-item>
        <el-form-item label="文件名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入文件名称" />
        </el-form-item>
        <el-form-item label="收文时间" prop="incomingDispatchesTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.incomingDispatchesTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择收文时间"
            :editable="false"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="文件密级和保密期限" prop="confidential">
          <el-input
            v-model="form.confidential"
            placeholder="请输入文件密级和保密期限"
          />
        </el-form-item>
        <el-form-item label="文件份数" prop="copies">
          <el-input-number
            v-model="form.copies"
            controls-position="right"
            :min="0"
            :max="99999999"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="签收人" prop="peopleId">
          <el-select
            ref="aa"
            v-model="form.peopleId"
            placeholder="请选择签收人"
            clearable
          >
            <el-option
              v-for="item in renYuan"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              :disabled="item.status == 1"
              @click.native="qianShou(item.name)"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="签收位置" prop="signLocation">
          <el-input v-model="form.signLocation" placeholder="请输入签收位置" />
        </el-form-item>
        <!--        <el-form-item label="借阅人" prop="borrowId">-->
        <!--          <el-select-->
        <!--            v-model="form.borrowId"-->
        <!--            placeholder="请选择借阅人"-->
        <!--            clearable-->
        <!--          >-->
        <!--            <el-option-->
        <!--              v-for="item in renYuan"-->
        <!--              :key="item.id"-->
        <!--              :label="item.name"-->
        <!--              :value="item.id"-->
        <!--              :disabled="item.status == 1"-->
        <!--              @click.native="jieYue(item.name)"-->
        <!--              class="input_xiala"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="借出时间" prop="loanTime">-->
        <!--          <el-date-picker-->
        <!--            clearable-->
        <!--            size="small"-->
        <!--            v-model="form.loanTime"-->
        <!--            type="date"-->
        <!--            :editable="false"-->
        <!--            value-format="yyyy-MM-dd"-->
        <!--            placeholder="请选择借出时间"-->
        <!--          >-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="归还时间" prop="returnTime">-->
        <!--          <el-date-picker-->
        <!--            clearable-->
        <!--            size="small"-->
        <!--            v-model="form.returnTime"-->
        <!--            type="date"-->
        <!--            :editable="false"-->
        <!--            value-format="yyyy-MM-dd"-->
        <!--            placeholder="请选择归还时间"-->
        <!--          >-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!-- 借阅记录（1~6组） -->
        <div style="width: 100%; margin-top: 10px">
          <div
            v-for="(item, index) in form.borrowRecords"
            :key="index"
            style="
              border: 1px dashed #6fa3c6;
              padding: 10px;
              margin-bottom: 10px;
            "
          >
            <div style="margin-bottom: 6px">
              借阅记录 {{ index + 1 }}
              <el-button
                v-if="form.borrowRecords.length > 1"
                type="text"
                style="color: #ff6b6b"
                @click="removeBorrowRecord(index)"
              >
                删除
              </el-button>
            </div>

            <!-- 借阅人（只绑定 ID） -->
            <el-form-item label="借阅人">
              <el-select
                v-model="item.borrowId"
                placeholder="请选择借阅人"
                clearable
              >
                <el-option
                  v-for="p in renYuan"
                  :key="p.id"
                  :label="p.name"
                  :value="p.id"
                  :disabled="p.status == 1"
                />
              </el-select>
            </el-form-item>

            <!-- 借出时间 -->
            <el-form-item label="借出时间">
              <el-date-picker
                v-model="item.loanTime"
                type="date"
                value-format="yyyy-MM-dd"
                :editable="false"
                placeholder="请选择借出时间"
              />
            </el-form-item>

            <!-- 归还时间 -->
            <el-form-item label="归还时间">
              <el-date-picker
                v-model="item.returnTime"
                type="date"
                value-format="yyyy-MM-dd"
                :editable="false"
                placeholder="请选择归还时间"
              />
            </el-form-item>
          </div>

          <!-- 新增一组 -->
          <el-button
            v-if="form.borrowRecords.length < 6"
            type="primary"
            plain
            size="mini"
            icon="el-icon-plus"
            @click="addBorrowRecord"
          >
            新增借阅记录
          </el-button>
        </div>

        <el-form-item label="注销人" prop="logoutId">
          <!-- <el-input v-model="form.logoutId" placeholder="请输入注销人" /> -->
          <el-select
            v-model="form.logoutId"
            placeholder="请选择注销人"
            clearable
          >
            <el-option
              v-for="item in renYuan"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              :disabled="item.status == 1"
              @click.native="zhuXiao(item.name)"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="清退时间" prop="destroyTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.destroyTime"
            type="date"
            value-format="yyyy-MM-dd"
            :editable="false"
            placeholder="请选择清退时间"
          />
        </el-form-item>

        <el-form-item label="接收人" prop="supervisorId">
          <el-select
            ref="aa"
            v-model="form.supervisorId"
            placeholder="请选择接收人"
            clearable
          >
            <el-option
              v-for="item in renYuan"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              :disabled="item.status == 1"
              @click.native="jianXiao(item.name)"
              class="input_xiala"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="beizhu">
          <el-input
            type="textarea"
            v-model="form.beizhu"
            placeholder="请输入备注"
            :rows="3"
          />
        </el-form-item>
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
      <bdglfilemanagement
        v-if="menuRoleVisible"
        ref="menuRole"
        :message="wordInfo"
      ></bdglfilemanagement>
    </el-dialog>
  </div>
</template>

<script>
import bdglfilemanagement from "@/components/look_word/bdglfilemanagement.vue";
import {
  listBdglfilemanagement,
  getBdglfilemanagement,
  delBdglfilemanagement,
  addBdglfilemanagement,
  updateBdglfilemanagement,
  exportBdglfilemanagement,
  fileunits,
  getBdglfilemanagementDetail,
} from "@/api/grassrootsregistration/bdglfilemanagement";
import {
  getZhuChiRen,
  getShouBu,
} from "@/api/grassrootsregistration/bdglmeeting";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getDept } from "@/api/grassrootsregistration/bdgleven";
import { parseTime } from "../../../utils/supervision";
export default {
  name: "Bdglfilemanagement",
  components: {
    Treeselect,
    bdglfilemanagement,
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
      // 文件管理登计本表格数据
      bdglfilemanagementList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        number: null,
        name: null,
        incomingDispatchesTime: null,
        confidential: null,
        copies: null,
        peopleId: null,
        borrowId: null,
        loanTime: null,
        returnTime: null,
        logoutId: null,
        unitId: null,
        createtime: null,
        updatetime: null,
        signLocation: null,
      },
      // 表单参数
      form: {
        borrowRecords: [],
        beizhu: null,
      },
      // 表单校验
      rules: {
        unitId: [
          { required: true, message: "单位名称不能为空", trigger: "blur" },
        ],
        name: [
          { required: true, message: "文件名称不能为空", trigger: "blur" },
        ],
        confidential: [
          { required: true, message: "文件密级不能为空", trigger: "blur" },
        ],
        copies: [
          { required: true, message: "文件分数不能为空", trigger: "blur" },
        ],
        peopleId: [
          { required: true, message: "签收人人不能为空", trigger: "blur" },
        ],
      },
      // 获取外面的单位
      deptOption: [],
      // 单位
      deptOptions: [],
      // 人员
      renYuan: [],
      // 树形配置对象
      defaultProps: {
        children: "children",
        label: "label",
      },
      form1: {},
    };
  },
  created() {
    this.getList();
    this.getdept();
    this.getYear();
  },
  methods: {
    addBorrowRecord() {
      if (this.form.borrowRecords.length >= 6) return;
      this.form.borrowRecords.push({
        borrowId: null,
        loanTime: null,
        returnTime: null,
      });
    },

    // 删除一组
    removeBorrowRecord(index) {
      this.form.borrowRecords.splice(index, 1);
    },

    parseTime,
    getYear() {
      var myDate = new Date();
      var tYear = myDate.getFullYear();
      this.form1.year = tYear;
    },
    // 计划编号
    planName() {
      let date = new Date();
      let year = date.getFullYear();
      let month =
        date.getMonth() + 1 <= 9
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let day = date.getDate();
      let hour = date.getHours() <= 9 ? "0" + date.getHours() : date.getHours();
      let minute =
        date.getMinutes <= 9 ? "0" + date.getMinutes() : date.getMinutes();
      this.form.number = year + month + day + hour + minute;
    },
    // 获取主持人
    getZhuChi(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 重置表单人员
    ChongZhiRen() {
      this.form.peopleId = null;
      ((this.form.logoutId = null), (this.form.borrowId = null));
    },

    // 单位选择触发
    selectPeo(val) {
      this.ChongZhiRen();
      this.form.unitId = val.id;
      this.getZhuChi(this.form.unitId);
      this.planName();
    },
    // 搜索密级触发
    wenJie() {
      this.getList();
    },
    // 搜索单位触发
    host() {
      this.getList();
    },
    // //获取搜索部门列表
    getdept() {
      getShouBu().then((res) => {
        this.deptOption = res.data;
      });
    },
    // 注销人选中触发
    zhuXiao(name) {
      this.form.logoutName = name;
    },
    // 监销人选中
    jianXiao(name) {
      this.form.supervisorName = name;
    },
    // 借阅人选中触发
    jieYue(name) {
      this.form.borrowName = name;
    },
    // 签收人选中触发
    qianShou(name) {
      this.form.peopleName = name;
    },

    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.deptOptions = response.data;
      });
    },
    /** 查询文件管理登计本列表 */
    getList() {
      this.loading = true;
      listBdglfilemanagement(this.queryParams).then((response) => {
        this.bdglfilemanagementList = response.rows;
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
        name: null,
        incomingDispatchesTime: null,
        confidential: null,
        copies: null,
        peopleId: null,
        // borrowId: null,
        // loanTime: null,
        // returnTime: null,
        logoutId: null,
        unitId: null,
        createtime: null,
        updatetime: null,
        signLocation: null,
        destroyTime: null,
        supervisorId: null,
        supervisorName: null,
        borrowRecords: [
          {
            borrowId: null,
            loanTime: null,
            returnTime: null,
          },
        ],
        beizhu: null,
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
    // 让select失去焦点
    blurSelect() {
      this.$refs.aa.blur();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      // 人员
      ((this.renYuan = []), this.getTreeselect());
      this.reset();
      this.open = true;
      this.title = "添加文件管理登计本";
      this.style = 2;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.form.unitId = row.unitId;
      this.getZhuChi(this.form.unitId);
      this.getTreeselect();
      const id = row.id || this.ids;
      getBdglfilemanagement(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改文件管理登计本";
      });
      getBdglfilemanagementDetail(id).then((response) => {
        this.form = response.data;

        // 兜底：保证 borrowRecords 一定是数组
        if (!this.form.borrowRecords || this.form.borrowRecords.length === 0) {
          this.form.borrowRecords = [
            {
              borrowId: null,
              loanTime: null,
              returnTime: null,
            },
          ];
        }

        this.open = true;
        this.title = "修改文件管理登计本";
      });
    },

    //查看按钮操作
    handleChakan(row) {
      this.form1.unitId = row.unitId;
      // 是否隐藏按钮
      this.opens = false;
      listBdglfilemanagement(this.form1).then((response) => {
        // fileunits(unitId).then((response) => {
        this.wordInfo = response.rows;
        this.title = "查看文件管理登计本";
        this.menuRoleVisible = true;
      });
    },

    /** 提交按钮 */
    submitForm(style) {
      this.style = style;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateBdglfilemanagement(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBdglfilemanagement(this.form).then((response) => {
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
        // .confirm('是否确认删除文件管理登计本编号为"' + ids + '"的数据项？')
        .then(function () {
          return delBdglfilemanagement(ids);
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
        .confirm("是否确认导出所有文件管理登计本数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBdglfilemanagement(queryParams);
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
  width: 882px;
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
::v-deep .el-dialog__body {
  margin: 10px 30px 20px 44px;
  padding-top: 20px !important;
  box-sizing: border-box;
  /* padding: 30px 12px 30px 28px; */
}
::v-deep .el-input__inner {
  cursor: pointer !important;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 30px 50px;
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
  padding: 18px 62px 30px 0px;
  margin-right: 15px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px !important;
}
::v-deep .el-dialog__body {
  padding: none !important;
}
/* 文件编号背景颜色 */
::v-deep .el-input.is-disabled .el-input__inner {
  background: transparent !important;
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
</style>
