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
          placeholder="请输入调出人员"
          clearable
          size="small"
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
          v-hasPermi="['peopleManage:transfer:add']"
          >调出</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="transferList"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="人员姓名" align="center" prop="cName" />
      <el-table-column label="部职别" align="center" prop="filed2">
      </el-table-column>
      <el-table-column label="入伍时间" align="center" prop="filed1">
      </el-table-column>
      <el-table-column
        label="调出日期"
        align="center"
        prop="createtime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="btk"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['peopleManage:recall:edit']"
            >审核</el-button
          >
          <el-button
            size="btd"
            @click="handleDelete(scope.row)"
            v-hasPermi="['peopleManage:recall:remove']"
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
      custom-class="box1"
      @close="cancel"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        :inline="true"
        label-width="120px"
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
          width="441px"
        >
          <tbody style="border-bottom: 1px solid">
            <tr align="center" class="tr">
              <td width="220" height="40">调出人员</td>
              <td width="220" height="56">
                <el-form-item prop="cName">
                  <el-select
                    v-model="form.cName"
                    placeholder="请选择人员"
                    clearable
                    filterable
                    :disabled="que"
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.name"
                      class="input_xiala"
                      @click.native="ren(item)"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </td>
            </tr>
            <tr
              align="center"
              class="tr"
              v-for="item in shenList"
              :key="item.id"
            >
              <td width="220" height="40">{{ item.name }}</td>
              <td width="220" height="40">
                <el-button
                  type="bde"
                  style="margin: 9px 0"
                  :disabled="true"
                  v-if="item.num == 1"
                  >已同意</el-button
                >
                <el-button
                  type="bde"
                  style="margin: 9px 0"
                  @click="shenHe(item.type)"
                  v-else
                  >审 核</el-button
                >
              </td>
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
          width="441px"
        >
          <tbody style="border-bottom: 1px solid; border-left: transparent">
            <tr
              align="center"
              class="tr"
              v-for="item in shenListRight"
              :key="item.id"
            >
              <td width="220" height="40">{{ item.name }}</td>
              <td width="220" height="40">
                <el-button
                  type="bde"
                  style="margin: 9px 0"
                  :disabled="true"
                  v-if="item.num == 1"
                  >已同意</el-button
                >
                <el-button
                  type="bde"
                  style="margin: 9px 0"
                  @click="shenHe1(item.type)"
                  v-else
                  >审 核</el-button
                >
              </td>
            </tr>
          </tbody>
        </table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-if="que1"
          >确 定</el-button
        >
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
  <script>
import {
  recallList,
  recall,
  recallDell,
  getAuthorization,
  getAuthorizations,
} from "@/api/peopleManage/seconded";
import { getAll } from "@/api/grassrootsregistration/bdglmeeting";

export default {
  name: "Transfer",
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
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
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
      // 人员列表
      renYuan: [],
      //审核内容左
      shenList: [
        {
          id: 1,
          name: "运输营房审核：",
          type: "transportation",
        },
        {
          id: 2,
          name: "财务审核：",
          type: "finance",
        },
        {
          id: 3,
          name: "保障处处长审核：",
          type: "guaranteez",
        },
        {
          id: 4,
          name: "宣传审核：",
          type: "propaganda",
        },
        {
          id: 5,
          name: "组织纪检审核：",
          type: "tissueembolus",
        },
        {
          id: 6,
          name: "政治工作处主任审核：",
          type: "zzgzczr",
        },
        {
          id: 7,
          name: "保密室审核：",
          type: "bms",
        },
        {
          id: 8,
          name: "训练管理审核：",
          type: "xlgl",
        },
        {
          id: 9,
          name: "副参谋长审核：",
          type: "fcmz",
        },
        {
          id: 10,
          name: "总师审核：",
          type: "zsz",
        },
        {
          id: 11,
          name: "副站长审核：",
          type: "fzz",
        },
        {
          id: 12,
          name: "站长审核：",
          type: "zz",
        },
      ],
      //审核内容右
      shenListRight: [
        {
          id: 1,
          name: "装备审核：",
          type: "zb",
        },
        {
          id: 2,
          name: "供应审核：",
          type: "gy",
        },
        {
          id: 3,
          name: "保障处副处长审核：",
          type: "guaranteef",
        },
        {
          id: 4,
          name: "保卫审核：",
          type: "bw",
        },
        {
          id: 5,
          name: "人力资源审核：",
          type: "rlzy",
        },
        {
          id: 6,
          name: "政治工作处副主任审核：",
          type: "zzggcfzr",
        },
        {
          id: 7,
          name: "图书室审核：",
          type: "tss",
        },
        {
          id: 8,
          name: "机要兼信息保障审核：",
          type: "jyjxxbz",
        },
        {
          id: 9,
          name: "作试审核：",
          type: "zs",
        },
        {
          id: 10,
          name: "参谋长审核：",
          type: "cmz",
        },
        {
          id: 11,
          name: "副政委审核：",
          type: "fzw",
        },
        {
          id: 12,
          name: "政委审核：",
          type: "zw",
        },
      ],
      form1: {},
      //   审核确定按钮的显示与隐藏
      que: false,
      que1: true,
    };
  },
  created() {
    this.getList();
    this.getQuanR();
  },
  methods: {
    //审核左面
    shenHe(value) {
      this.form.type = value;
      this.$confirm("是否通过审核", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          console.log(this.form);
          getAuthorization(this.form).then((res) => {
            this.$message({
              type: "success",
              message: "审核成功!",
            });
            this.open = false;
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消审核",
          });
        });
    },
    // 审核右面
    shenHe1(value) {
      this.form.type = value;
      this.$confirm("是否通过审核", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          getAuthorization(this.form).then((res) => {
            this.$message({
              type: "success",
              message: "审核成功!",
            });
            this.open = false;
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消审核",
          });
        });
    },
    // 获取全部人员
    getQuanR() {
      getAll().then((res) => {
        this.renYuan = res.data;
      });
    },
    // 获取人员信息
    ren(value) {
      this.form.filed1 = value.enlistDate;
      this.form.filed2 = value.duty;
      this.form.peopleId = value.id;
    },
    /** 查询调动管理列表 */
    getList() {
      this.loading = true;
      recallList(this.queryParams).then((response) => {
        this.transferList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.shenList.forEach((item) => {
        item.num = 0;
      });
      this.shenListRight.forEach((item) => {
        item.num = 0;
      });
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        peopleId: null,
        filed1: null,
        cName: null,
        filed1: null,
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
      this.que = false;
      this.que1 = true;
      this.shenList.forEach((item) => {
        item.num = 0;
      });
      this.shenListRight.forEach((item) => {
        item.num = 0;
      });
      this.title = "添加调动管理";
    },
    /** 审核按钮操作 */
    handleUpdate(row) {
      // 赋值名字
      this.form = row;
      this.form1.id = row.id;
      this.form1.peopleId = row.peopleId;
      getAuthorizations(this.form1).then((res) => {
        // 循环左面的列表
        res.rows.forEach((element) => {
          this.shenList.forEach((item) => {
            if (element.bdglPeopleRecallAuditlog.type == item.type) {
              item.num = 1;
            }
          });
        });
        res.rows.forEach((element) => {
          this.shenListRight.forEach((item) => {
            if (element.bdglPeopleRecallAuditlog.type == item.type) {
              item.num = 1;
            }
          });
        });
        this.open = true;
        this.title = "审批";
        this.que = true;
        this.que1 = false;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
          } else {
            recall(this.form).then((res) => {
              this.$modal.msgSuccess("调出成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      console.log(row);
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除？")
        .then(function () {
          return recallDell(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
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

::v-deep .box1 .el-form {
  display: flex;
}
::v-deep .box1 .el-form-item {
  margin-bottom: 0px;
}
::v-deep .box1 .el-input__inner {
  border: transparent !important;
  width: 200px;
  height: 30px;
  text-align: center;
}
::v-deep .el-dialog__body {
  padding: 30px 74px;
}

::v-deep .el-button.is-disabled,
.el-button.is-disabled:hover,
.el-button.is-disabled:focus {
  color: #fff;
  cursor: not-allowed;
  background-image: none;
  background-color: #13ce66;
  border-color: #13ce66;
}
</style>
