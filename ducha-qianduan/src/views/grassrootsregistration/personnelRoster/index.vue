<!--人员记录本领导 -->
<template>
  <div class="personnel-roster-container">
    <!-- 左侧部门列表 -->
    <div class="department-list">
      <div class="department-title">部门列表</div>
      <div 
        v-for="(item, i) in lieList" 
        :key="i"
        class="department-item"
        :class="{ active: selectedDeptId === item.deptId }"
        @click="selectDepartment(item.deptId)"
      >
        <div class="dept-name">{{ item.deptName }}</div>
        <div class="people-count">{{item.peopleNum}}人</div>
      </div>
      <div v-if="lieList.length === 0" class="empty-tip">暂无部门数据</div>
    </div>

    <!-- 右侧人员详情 -->
    <div class="personnel-details">
      <div v-if="selectedDeptId" class="details-content">
        <div class="details-header">
          <h3>{{ currentDeptName }}</h3>
          <div class="total-count">共 {{ total }} 人</div>
        </div>
        
        <el-table
          v-loading="loading"
          :data="bdglevenList"
          :header-cell-style="{ background: '#003C69', color: 'white' }"
          style="width: 100%"
        >
          <el-table-column label="序号" type="index" width="50" align="center">
            <template slot-scope="scope">
              <span>{{
                (queryParams.pageNum - 1) * queryParams.pageSize +
                scope.$index +
                1
              }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="单位"
            align="center"
            prop="deptName"
            show-overflow-tooltip
            width="150"
          />
          <el-table-column label="姓名" align="center" prop="name" />
          <el-table-column label="性别" align="center" prop="sex">
            <template slot-scope="scope">
              <dict-tag
                :options="dict.type.sys_user_sex"
                :value="scope.row.sex"
              />
            </template>
          </el-table-column>
          <el-table-column label="年龄" align="center" prop="age" />
          <el-table-column
            label="身份证号"
            align="center"
            prop="idcard"
            width="170"
            show-overflow-tooltip
          />
          <el-table-column
            label="部职别"
            align="center"
            width="200"
            prop="duty"
            show-overflow-tooltip
          />
          <el-table-column label="职务" align="center" width="120" prop="postId">
            <template slot-scope="scope">
              <dict-tag
                :options="dict.type.post_Level"
                :value="scope.row.postId"
              />
            </template>
          </el-table-column>
          <el-table-column
            label="职务等级时间"
            align="center"
            prop="postDate"
            width="180"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.postDate, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="籍贯"
            align="center"
            prop="origin"
            width="150"
          />
          <el-table-column
            label="在位情况"
            align="center"
            prop="reign"
            width="90"
          >
            <template slot-scope="scope">
              <dict-tag
                :options="dict.type.people_state"
                :value="scope.row.reign"
              />
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
            width="100"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button size="btk" type="text" @click="handleChakan(scope.row)"
                ><span class="chakan">查看</span>
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="handlePagination"
        />
      </div>
      
      <div v-else class="no-selection">
        <div class="empty-icon">👥</div>
        <div class="empty-text">请选择一个部门查看人员详情</div>
      </div>
    </div>

    <!-- 人员详情弹窗 -->
    <div class="fff">
      <el-dialog
        :visible.sync="menuRoleVisible"
        :title="title"
        append-to-body
        id="chakan"
        :close-on-click-modal="false"
      >
        <personnelRoster
          v-if="menuRoleVisible"
          ref="menuRole"
          :message="wordInfo"
          :renshu="renshu"
        ></personnelRoster>
      </el-dialog>
    </div>

    <div class="fff">
      <el-dialog
        :visible.sync="menuRoleVisible"
        :title="title"
        append-to-body
        id="chakan"
        :close-on-click-modal="false"
      >
        <personnelRoster
          v-if="menuRoleVisible"
          ref="menuRole"
          :message="wordInfo"
          :renshu="renshu"
        ></personnelRoster>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import personnelRoster from "@/components/look_word/personnelRoster.vue";
import {
  getDept,
  treeselect,
} from "@/api/peopleManage/people";
import {
  getPersonnelRosterList,
  getPersonnelRosterOnUnitList,
} from "@/api/grassrootsregistration/personnelRoster";
import {
  getRenYuanXiangQing,
} from "@/api/grassrootsregistration/bdglmeeting";

export default {
  name: "People",
  components: {
    personnelRoster,
  },
  dicts: [
    "sys_user_sex",
    "post_Level",
    "sys_user_rank",
    "personnel_type",
    "Degree_level",
    "degree",
    "Caucus",
    "marryList",
    "sys_yw",
    "healthy",
    "bloodType",
    "sys_yes_no",
    "sys_yes_no",
  ],
  data() {
    return {
      //查看页面
      menuRoleVisible: false,
      wordInfo: [],
      renshu: {},
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedBdglPeople: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 人员管理表格数据
      peopleList: [],
      // 人员管理表格数据
      bdglPeopleList: [],
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
      year: null,
      status: null,
      arr: [],
      lieList: [],
      // 表单校验
      rules: {},
      //性别数组
      sexList: [],
      //血型数组
      bloodType: [],
      //政治面貌
      partyIds: [],
      //健康状况
      healthy: [],
      //婚姻情况
      marryList: [],
      //学历
      educationId: [],
      //学位
      degree: [],
      //是否编制
      authorizedStrength: [],
      //子女情况
      childrenList: [],
      //人员类别
      classId: [],
      //职务层级
      postIds: [],
      //军衔
      rankIds: [],
      //单位
      deptId: [],
      //民族
      nationIds: [],
      unitId: [],
      jtlist: [
        {
          name: null,
          characterRelationship: null,
          phoneNumber: null,
          unitName: null,
        },
      ],
      defaultProps: {
        children: "children",
        label: "label",
      },
      deptOptions: [],
      value: null,
      tableStatus: false,
      bdglevenList: [],
      // 新增：选中的部门ID
      selectedDeptId: null,
      // 新增：当前选中部门名称
      currentDeptName: '',
    };
  },
  // updated() {
  //   this.changeColor();
  // },
  created() {
    this.getList();
    //部门数据
    getDept().then((res) => {
      if (res.code == 200) {
        this.unitId = res.data;
      }
    });
    treeselect().then((res) => {
      if (res.code == 200) {
        this.deptOptions = res.data;
      }
    });
  },
  methods: {
    // 选择部门
    selectDepartment(deptId) {
      this.selectedDeptId = deptId;
      // 获取部门名称
      const selectedDept = this.lieList.find(item => item.deptId === deptId);
      if (selectedDept) {
        this.currentDeptName = selectedDept.deptName;
      }
      
      // 查询该部门的人员数据
      this.queryParams.unitId = deptId;
      this.queryParams.pageNum = 1; // 重置页码
      this.loading = true;
      
      getPersonnelRosterOnUnitList(this.queryParams).then((res) => {
        this.bdglevenList = res.rows || [];
        this.total = res.total || 0;
        this.loading = false;
      }).catch(error => {
        console.error("获取部门人员数据失败:", error);
        this.bdglevenList = [];
        this.total = 0;
        this.loading = false;
      });
    },
    
    // 处理分页
    handlePagination() {
      getPersonnelRosterOnUnitList(this.queryParams).then((res) => {
        this.bdglevenList = res.rows || [];
        this.total = res.total || 0;
      }).catch(error => {
        console.error("分页查询失败:", error);
        this.bdglevenList = [];
        this.total = 0;
      });
    },
    /** 查询人员管理列表 */
    getList() {
      this.loading = true;
      getPersonnelRosterList().then((res) => {
        if (res.code === 200) {
          this.lieList = res.rows || [];
          this.status = this.lieList.length > 0 ? 1 : 0;
        } else {
          this.lieList = [];
          this.status = 0;
        }
        this.loading = false;
      }).catch(error => {
        console.error("获取数据失败:", error);
        this.lieList = [];
        this.status = 0;
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
        name: null,
      };
      this.bdglPeopleList = [];
      this.resetForm("form");
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //查看按钮操作
    handleChakan(row) {
      // 是否隐藏按钮
      const deptId = row.deptId;
      getRenYuanXiangQing(deptId).then((response) => {
        this.renshu = response.data[0];
        this.wordInfo = response.data[1];
        this.title = "查看人员名册";
        this.menuRoleVisible = true;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有人员管理数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportPeople(queryParams);
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
<style  scoped>
/* 主容器样式 */
.personnel-roster-container {
  display: flex;
  height: calc(100vh - 60px);
  padding: 20px;
  gap: 20px;
}

/* 左侧部门列表样式 */
.department-list {
  width: 300px;
  background: rgba(0, 60, 105, 0.3);
  border: 1px solid #718a9d;
  border-radius: 8px;
  padding: 20px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
}

.department-title {
  font-size: 18px;
  font-weight: bold;
  color: #fff;
  margin-bottom: 20px;
  text-align: center;
  padding-bottom: 10px;
  border-bottom: 1px solid #718a9d;
}

.department-item {
  background: rgba(0, 60, 105, 0.5);
  border: 1px solid #3d83b8;
  border-radius: 6px;
  padding: 15px;
  margin-bottom: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.department-item:hover {
  background: rgba(0, 60, 105, 0.8);
  border-color: #90cef1;
  transform: translateX(5px);
}

.department-item.active {
  background: rgba(0, 120, 215, 0.7);
  border-color: #90cef1;
  box-shadow: 0 0 10px rgba(144, 206, 252, 0.3);
}

.dept-name {
  color: #fff;
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 8px;
}

.people-count {
  color: #90cef1;
  font-size: 14px;
}

.empty-tip {
  color: #90cef1;
  text-align: center;
  padding: 40px 0;
  font-size: 14px;
}

/* 右侧人员详情样式 */
.personnel-details {
  flex: 1;
  background: rgba(0, 60, 105, 0.3);
  border: 1px solid #718a9d;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.details-content {
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 20px;
}

.details-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #718a9d;
}

.details-header h3 {
  color: #fff;
  font-size: 20px;
  margin: 0;
}

.total-count {
  color: #90cef1;
  font-size: 16px;
  font-weight: 500;
}

.no-selection {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: #90cef1;
}

.empty-icon {
  font-size: 64px;
  margin-bottom: 20px;
}

.empty-text {
  font-size: 18px;
}

/* 表格样式 */
::v-deep .el-table {
  flex: 1;
  background: transparent;
}

::v-deep .el-table__header-wrapper th {
  background-color: #003C69 !important;
  color: white !important;
  border-bottom: 1px solid #718a9d;
}

::v-deep .el-table__body-wrapper {
  overflow-y: auto;
  flex: 1;
}

::v-deep .el-table__row {
  background-color: rgba(0, 54, 95, 0.8);
  color: #fff;
}

::v-deep .el-table__row:nth-child(even) {
  background-color: rgba(0, 54, 95, 0.6);
}

::v-deep .el-table__row:hover {
  background-color: #3c749d !important;
}

::v-deep .el-table__body tr:hover > td {
  background-color: transparent !important;
}

/* 分页样式 */
::v-deep .pagination-container {
  margin-top: 20px;
  padding: 10px 0;
  background: transparent !important;
}

::v-deep .el-pagination .el-select .el-input .el-input__inner {
  background: #00365f !important;
  border-color: #718a9d;
  color: #fff;
}

::v-deep .el-pagination.is-background .btn-prev,
::v-deep .el-pagination.is-background .btn-next {
  background: #00365f !important;
  border-color: #718a9d;
  color: #fff;
}

::v-deep .el-pagination__editor.el-input .el-input__inner {
  background: #00365f !important;
  border-color: #718a9d;
  color: #fff;
}

::v-deep .el-pagination.is-background .el-pager li {
  background: #00365f;
  color: #fff;
}

::v-deep .el-pagination.is-background .el-pager li.active {
  background: #3c749d;
  color: #fff;
}

/* 弹窗样式保持不变 */
#chakan ::v-deep .el-dialog {
  min-height: 700px;
  background: #fff !important;
}

#chakan /deep/ .el-dialog__body {
  padding: 30px 30px 30px 30px;
}

img {
  object-fit: cover;
}

/* 滚动条样式 */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: rgba(0, 60, 105, 0.3);
}

::-webkit-scrollbar-thumb {
  background: #3d83b8;
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: #90cef1;
}

/* 查看按钮样式 */
.chakan {
  color: #90cef1;
}

.chakan:hover {
  color: #fff;
  text-decoration: underline;
}
</style>