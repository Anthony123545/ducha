<template>
  <div class="app-container">
    <h4 class="form-header h4">基本信息</h4>
    <el-form ref="form" :model="form" label-width="80px">
      <el-row>
        <el-col :span="8" :offset="2">
          <el-form-item label="用户昵称" prop="nickName">
            <el-input v-model="form.nickName" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="8" :offset="2">
          <el-form-item label="登录账号" prop="phonenumber">
            <el-input v-model="form.userName" disabled />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <h4 class="form-header h4">角色信息</h4>
    <el-table
      class="back"
      v-loading="loading"
      :row-key="getRowKey"
      @row-click="clickRow"
      ref="table"
      @selection-change="handleSelectionChange"
      :data="roles.slice((pageNum - 1) * pageSize, pageNum * pageSize)"
      :header-cell-style="{ background: '#00365F', color: 'white' }"
    >
      <el-table-column label="序号" type="index" align="center">
        <template slot-scope="scope">
          <span>{{ (pageNum - 1) * pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column
        type="selection"
        :reserve-selection="true"
        width="55"
      ></el-table-column>
      <el-table-column label="角色编号" align="center" prop="roleId" />
      <el-table-column label="角色名称" align="center" prop="roleName" />
      <el-table-column label="权限字符" align="center" prop="roleKey" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="pageNum"
      :limit.sync="pageSize"
    />

    <el-form label-width="100px">
      <el-form-item
        style="text-align: center; margin-left: -120px; margin-top: 30px"
      >
        <el-button type="primary" @click="submitForm()">提交</el-button>
        <el-button @click="close()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getAuthRole, updateAuthRole } from "@/api/system/user";

export default {
  name: "AuthRole",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 分页信息
      total: 0,
      pageNum: 1,
      pageSize: 10,
      // 选中角色编号
      roleIds: [],
      // 角色信息
      roles: [],
      // 用户信息
      form: {},
    };
  },
  created() {
    const userId = this.$route.params && this.$route.params.userId;
    if (userId) {
      this.loading = true;
      getAuthRole(userId).then((response) => {
        this.form = response.user;
        this.roles = response.roles;
        this.total = this.roles.length;
        this.$nextTick(() => {
          this.roles.forEach((row) => {
            if (row.flag) {
              this.$refs.table.toggleRowSelection(row);
            }
          });
        });
        this.loading = false;
      });
    }
  },
  methods: {
    /** 单击选中行数据 */
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.roleIds = selection.map((item) => item.roleId);
    },
    // 保存选中的数据编号
    getRowKey(row) {
      return row.roleId;
    },
    /** 提交按钮 */
    submitForm() {
      const userId = this.form.userId;
      const roleIds = this.roleIds.join(",");
      updateAuthRole({ userId: userId, roleIds: roleIds }).then((response) => {
        this.$modal.msgSuccess("授权成功");
        this.close();
      });
    },
    /** 关闭按钮 */
    close() {
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/system/user" });
    },
  },
};
</script>
<style scoped>
/* 设置背景颜色 */
.app-container {
  background-color: #00365f;
}
/* 设置表格单双数颜色 */
::v-deep.el-table tr:nth-child(odd) {
  background: #00365f;
}
::v-deep.el-table tr:nth-child(even) {
  background: #003c69 !important;
}
/* 设置字体白色 */
.el-table {
  color: white;
}
.form-header{
  color: #fff;
}
::v-deep .el-pagination__total {
  color: #fff !important;
}
::v-deep .el-pagination__jump{
  color: #fff !important;
}
::v-deep .el-form-item__label {
  color: white !important;
}
/* 表格行划过颜色 */
::v-deep .el-table__body tr:hover > td {
  background-color: #004d86 !important;
}
/* 表格文字居中 */
::v-deep .cell {
  text-align: center;
}
/* 暂无数据背景 */
::v-deep .pagination-container {
  background: #00365f;
}
/* 去掉上面的线 */
::v-deep .el-table th.is-leaf {
  border: none;
}
/* 去掉最下面的那一条线 */
::v-deep .el-table::before {
  height: 0px;
}
/* 去掉表格内的线 */
::v-deep table td {
  border-bottom: none !important;
}
::v-deep .refresh {
  background-color: #004d86 !important;
}
::v-deep .el-button el-button--default el-button--mini {
  background-color: #196299;
}
/* input表单颜色 */
::v-deep .el-input--small .el-input__inner{
  background: #004D86;
  border: 0px;
  color: #fff !important;
}
/* 时间日历设置 */
::v-deep .el-range-input{
background: #004D86;
color: #fff;
}
::v-deep .el-range-editor--small .el-range-separator {
    color: white ;
}
/* 分页按钮 */
::v-deep .el-pagination .el-select .el-input .el-input__inner {
    background: #004D86;
    border: 0px;
    color: #fff;
}
::v-deep .el-pagination button:disabled {
    color: #fff !important;
    background-color: #004D86;
    cursor: not-allowed;
}
::v-deep .el-pagination__editor.el-input .el-input__inner {
    background: #004D86;
    color: #fff;
    border: 0px;
}
::v-deep .el-pagination.is-background .el-pager li:not(.disabled).active {
    background-color: #004D86;
    color: #FFFFFF;
}
::v-deep .el-input.is-disabled .el-input__inner {
    background-color: #004D86;
    border: 0px;
    color: #cecece;
    cursor: not-allowed;
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