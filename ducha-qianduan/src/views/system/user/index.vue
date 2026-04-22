<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            class="userAgrees"
            style="height: 700px; overflow: auto"
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
            highlight-current
          />
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          v-show="showSearch"
          label-width="68px"
        >
          <el-form-item prop="nickName">
            <el-input
              v-model="queryParams.nickName"
              placeholder="请输入用户名称"
              size="small"
              style="width: 240px"
              @input="handleQuery"
            />
          </el-form-item>
          <el-form-item prop="phonenumber">
            <el-input
              v-model="queryParams.phonenumber"
              placeholder="请输入手机号码"
              size="small"
              style="width: 240px"
              @input="handleQuery"
            />
          </el-form-item>
          <el-form-item prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="用户状态"
              size="small"
              style="width: 240px"
              @change="handleQuery"
            >
              <el-option
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
                class="input_xiala"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="
                width: 200px;
                height: 36px;
                background: #00365f;
                border: 1px solid white;
              "
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
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
              v-hasPermi="['system:user:add']"
              >新增</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:user:edit']"
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
              v-hasPermi="['system:user:remove']"
              >删除</el-button
            >
          </el-col>
          <!-- <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['system:user:import']"
              >导入</el-button
            >
          </el-col> -->
          <!-- <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              :loading="exportLoading"
              @click="handleExport"
              v-hasPermi="['system:user:export']"
              >导出</el-button
            >
          </el-col> -->
          <!-- <right-toolbar
            :showSearch.sync="showSearch"
            @queryTable="getList"
            :columns="columns"
          ></right-toolbar> -->
        </el-row>

        <el-table
          v-loading="loading"
          :data="userList"
          @selection-change="handleSelectionChange"
          :header-cell-style="{ background: '#003c69', color: 'white' }"
        >
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column
            label="用户编号"
            align="center"
            key="userId"
            prop="userId"
            v-if="columns[0].visible"
          />
          <el-table-column
            label="用户名称"
            align="center"
            key="userName"
            prop="userName"
            v-if="columns[1].visible"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="用户昵称"
            align="center"
            key="nickName"
            prop="nickName"
            v-if="columns[2].visible"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="部门"
            align="center"
            key="deptName"
            prop="dept.deptName"
            v-if="columns[3].visible"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="手机号码"
            align="center"
            key="phonenumber"
            prop="phonenumber"
            v-if="columns[4].visible"
            width="120"
          />
          <el-table-column
            label="状态"
            align="center"
            key="status"
            v-if="columns[5].visible"
          >
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column
            label="创建时间"
            align="center"
            prop="createTime"
            v-if="columns[6].visible"
            width="160"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="200"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope" v-if="scope.row.userId !== 1">
              <el-button
                size="btu"
                type="text"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']"
              >
                <span class="edit">修改</span>
              </el-button>
              <el-button
                size="btd"
                type="text"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:user:remove']"
              >
                <span class="delete">删除</span>
              </el-button>
              <el-dropdown
                size="mini"
                @command="(command) => handleCommand(command, scope.row)"
                v-hasPermi="['system:user:resetPwd', 'system:user:edit']"
              >
                <span class="more">更多</span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item
                    command="handleResetPwd"
                    icon="el-icon-key"
                    v-hasPermi="['system:user:resetPwd']"
                    >重置密码</el-dropdown-item
                  >
                  <el-dropdown-item
                    command="handleAuthRole"
                    icon="el-icon-circle-check"
                    v-hasPermi="['system:user:edit']"
                    >分配角色</el-dropdown-item
                  >
                </el-dropdown-menu>
              </el-dropdown>
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

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1016px"
      append-to-body
      :closeOnClickModal="false"
      :expand-on-click-node="false"
    >
      <div class="jiben">基本信息</div>

      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <el-row>
          <el-col :span="8">
            <!-- <el-form-item label="归属部门" prop="deptId">
              <treeselect
                v-model="form.deptId"
                :options="deptOptions"
                :show-count="true"
                placeholder="请选择归属部门"
                @node-click="deptIdClick"
              />
            </el-form-item>-->
            <el-form-item label="归属部门" prop="deptId">
              <el-select
                v-model="form.deptId"
                @change="deptIdClick"
                clearable
                placeholder="请选择归属部门"
              >
                <el-option
                  v-for="dict in unitId"
                  :key="dict.deptId"
                  :label="dict.deptName"
                  :value="dict.deptId"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="用户昵称" prop="nickName">
              <el-select
                v-model="form.nickName"
                filterable
                clearable
                placeholder
                @change="unitNamechange"
              >
                <el-option
                  v-for="item in nickNamelist"
                  :key="item.id"
                  :label="item.name"
                  :value="item"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机号码" prop="phonenumber">
              <el-input
                v-model="form.phonenumber"
                placeholder="请输入手机号码"
                maxlength="11"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="邮箱" prop="email">
              <el-input
                v-model="form.email"
                placeholder="请输入邮箱"
                maxlength="50"
              />
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item
              v-if="form.userId == undefined"
              label="账户名称"
              prop="userName"
            >
              <el-input
                v-model="form.userName"
                placeholder="请输入账户名称"
                maxlength="30"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              v-if="form.userId == undefined"
              label="账户密码"
              prop="password"
            >
              <el-input
                v-model="form.password"
                placeholder="请输入账户密码"
                type="password"
                maxlength="20"
                show-password
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row></el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="用户性别">
              <el-select v-model="form.sex" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.sys_user_sex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="角色">
              <el-select v-model="form.roleIds" multiple placeholder="请选择">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                  :disabled="item.status == 1"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="岗位">
              <el-select v-model="form.postIds" multiple placeholder="请选择">
                <el-option
                  v-for="item in postOptions"
                  :key="item.postId"
                  :label="item.postName"
                  :value="item.postId"
                  :disabled="item.status == 1"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="权限">
              <el-select
                v-model="form.permissions"
                style="width: 200px"
                placeholder="请选择权限"
                clearable
                multiple
              >
                <el-option
                  v-for="dict in permissionslist"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                  >{{ dict.label }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="jiben">备注</div>

        <el-row>
          <el-col :span="24">
            <el-form-item label style="width: 100%" class="textareas">
              <el-input
                v-model="form.remark"
                type="textarea"
                placeholder="请输入内容"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
      :closeOnClickModal="false"
      :expand-on-click-node="false"
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox
              v-model="upload.updateSupport"
            />是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link
            type="primary"
            :underline="false"
            style="font-size: 12px; vertical-align: baseline"
            @click="importTemplate"
            >下载模板</el-link
          >
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listUser,
  getUser,
  delUser,
  addUser,
  updateUser,
  exportUser,
  resetUserPwd,
  changeUserStatus,
  importTemplate,
  yhlist,
  getDept,
} from "@/api/system/user";
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "User",
  dicts: ["sys_normal_disable", "sys_user_sex"],
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      disabled: false,
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
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
      // 表单参数
      form: {},
      defaultProps: {
        label: "label",
        children: "children",
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/user/importData",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined,
        permissions: null,
      },
      // 列信息
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true },
      ],
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "用户名称不能为空", trigger: "blur" },
          {
            min: 2,
            max: 20,
            message: "用户名称长度必须介于 2 和 20 之间",
            trigger: "blur",
          },
        ],
        nickName: [
          { required: true, message: "用户昵称不能为空", trigger: "change" },
        ],
        deptId: [
          { required: true, message: "归属部门不能为空", trigger: "change" },
        ],
        password: [
          { required: true, message: "用户密码不能为空", trigger: "blur" },
          {
            min: 5,
            max: 20,
            message: "用户密码长度必须介于 5 和 20 之间",
            trigger: "blur",
          },
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        phonenumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
      },
      //用户昵称数组
      nickNamelist: [],
      //归属部门
      unitId: [],
      //权限
      permissionslist: [],
    };
  },
  updated() {
    // this.changeColor();
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
  },

  created() {
    this.getList();
    this.getTreeselect();
    this.getConfigKey("sys.user.initPassword").then((response) => {
      this.initPassword = response.msg;
    });
    //归属部门数据
    getDept().then((res) => {
      if (res.code == 200) {
        this.unitId = res.data;
      }
    });
    //权限
    this.getDicts("sys_permissions").then((response) => {
      this.permissionslist = response.data;
    });
  },
  methods: {
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then((response) => {
        this.deptOptions = response.data;
      });
    },

    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal
        .confirm('确认要"' + text + '""' + row.userName + '"用户吗？')
        .then(function () {
          return changeUserStatus(row.userId, row.status);
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.status = row.status === "0" ? "1" : "0";
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
        userId: undefined,
        deptId: undefined,
        userName: undefined,
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        postIds: [],
        roleIds: [],
        permissions: null,
        peopleId: null,
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
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleResetPwd":
          this.handleResetPwd(row);
          break;
        case "handleAuthRole":
          this.handleAuthRole(row);
          break;
        default:
          break;
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      this.disabled = false;
      getUser().then((response) => {
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.open = true;
        this.title = "添加用户";
        this.form.password = this.initPassword;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      this.disabled = true;
      const userId = row.userId || this.ids;
      getUser(userId).then((response) => {
        this.form = response.data;
        if (this.form.permissions) {
          this.form.permissions = this.form.permissions.split(",");
        }

        // if(this.form.permissions){
        //   this.form.permissions = this.form.permissions.split(",");
        // }
        this.deptIdClick();
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.form.postIds = response.postIds;
        this.form.roleIds = response.roleIds;
        this.open = true;
        this.title = "修改用户";
        this.form.password = "";
      });
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.userName + '"的新密码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{5,20}$/,
        inputErrorMessage: "用户密码长度必须介于 5 和 20 之间",
      })
        .then(({ value }) => {
          resetUserPwd(row.userId, value).then((response) => {
            this.$modal.msgSuccess("修改成功，新密码是：" + value);
          });
        })
        .catch(() => {});
    },
    /** 分配角色操作 */
    handleAuthRole: function (row) {
      const userId = row.userId;
      this.$router.push("/system/user-auth/role/" + userId);
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (this.form.permissions == "") {
          this.form.permissions = null;
        } else if (this.form.permissions) {
          this.form.permissions = this.form.permissions.toString();
        }

        if (valid) {
          // this.form.permissions = this.form.permissions.join(",");
          if (this.form.userId != undefined) {
            updateUser(this.form).then((response) => {
              if (response.code == 200) {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
              //  else {
              //   this.open=false;
              // }
            });
          } else {
            addUser(this.form).then((response) => {
              if (response.code == 200) {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
              // else {
              //   this.open=false;
              // }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const userIds = row.userId || this.ids;
      this.$modal
        .confirm("是否确认删除用户的数据项？", {
          cancelButtonClass: "btn-custom-cancel",
        })
        .then(function () {
          return delUser(userIds);
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
        .confirm("是否确认导出所有用户数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportUser(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then((response) => {
        this.$download.name(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    // 隔行变色
    // changeColor() {
    //   var content = document.getElementsByClassName("el-tree-node__content");
    //   for (var i = 0; i < content.length; i++) {
    //     if (i % 2 === 0) {
    //       content[i].style.background = "#00365F ";
    //     } else {
    //       content[i].style.background = "#003C69";
    //     }
    //   }
    // },
    //获取用户昵称数组
    deptIdClick() {
      var userId = 0;
      yhlist(this.form.deptId, userId).then((res) => {
        if (res.code == 200) {
          this.nickNamelist = res.data;
        }
      });
    },
    //获取相关信息
    unitNamechange(val) {
      this.form.phonenumber = val.phone;
      this.form.sex = val.sex;
      this.form.nickName = val.name;
      this.form.peopleId = val.id;
      this.getList();
    },
  },
};
</script>

<style scoped>
/* ::v-deep .el-tree-node__content {
  color: #fff;
  height: 30px;
  text-align: center;
}
::v-deep .el-tree-node__content:hover {
  background-color: #004d86 !important;
} */
/* .el-input.is-disabled .el-input__inner{
  background: #004d86 !important;
} */
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
}
::v-deep .el-textarea__inner {
  width: 930px;
  height: 104px;
  margin-left: 23px;
}
::v-deep .el-input.is-disabled .el-input__inner {
  background: #004d86 !important;
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
}
/* 单位框背景颜色
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
} */
/* 基本信息背景 */
.jiben {
  width: 930px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  background-size: 930px;
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
  margin-left: 24px;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
/* 对话框底部确定取消按钮位置 */
::v-deep .el-dialog__footer {
  margin-right: 25px !important;
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
/* ::v-deep .vue-treeselect__control {
  background: #004d86 !important;
  color: #fff;
} */
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
::v-deep .el-pagination.is-background .btn-prev,
.el-pagination.is-background .btn-next,
.el-pagination.is-background .el-pager li {
  background: #004d86 !important;
  color: #fff !important;
}
/* 备注输入框 */
.textareas ::v-deep .el-form-item__content {
  width: 100%;
}
/* 日期背景颜色 */
::v-deep .el-date-editor .el-range-input {
  background: #00365f;
}
/* 状态 */
.el-radio {
  color: rgb(204, 198, 198);
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
.el-tree {
  background: #003156;
  color: #fff;
  margin-right: 2px;
}
::v-deep .el-tree-node__content {
  height: 32px !important;
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
::v-deep .el-tree-node__content:hover {
  color: #fff;
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
::v-deep
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  color: #fff !important;
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
::v-deep .el-tree-node__label {
  color: #fff !important;
}
::v-deep .el-date-editor .el-range-input {
  color: #fff;
}
</style>
