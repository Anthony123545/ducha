<template>
  <div class="app-container">
    <el-table
      v-loading="loading"
      :data="approvalList"
      style="width: 100%"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column prop="kind" label="分类" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.electronic" :value="scope.row.kind" />
        </template>
      </el-table-column>
      <el-table-column prop="version" label="型号" align="center" />
      <el-table-column prop="number" label="编号" align="center" />
      <el-table-column prop="peopleName" label="责任人" align="center" />
      <el-table-column prop="unitId" label="使用单位" align="center" :formatter="deptIdformatter" />
      <el-table-column prop="people2Name" label="用途" align="center" />
      <el-table-column prop="status" label="审批状态" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 'pending'" type="warning">待审批</el-tag>
          <el-tag v-else-if="scope.row.status === 'approved'" type="success">已通过</el-tag>
          <el-tag v-else type="danger">已驳回</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="220" fixed="right">
        <template slot-scope="scope">
          <el-button
            type="success"
            size="mini"
            @click="handleApprove(scope.row, 'approved')"
          >同意</el-button>
          <el-button
            type="danger"
            size="mini"
            @click="handleApprove(scope.row, 'rejected')"
          >驳回</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { listSecret, updateSecret, updateStatus } from "@/api/bdglSecret/secret";
import { getDept } from "@/api/grassrootsregistration/bdglmeeting";

export default {
  name: "Approval",
  dicts: ["electronic"],
  data() {
    return {
      loading: false,
      approvalList: [],
      unitId: []
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    // 查询申请列表
    getList() {
      this.loading = true;
      listSecret({ status: "pending" }).then(res => {
        this.approvalList = res.rows;
        this.loading = false;
        console.log("表格数据：", this.approvalList);
      });
    },
    // 单位翻译
    deptIdformatter(row) {
      let deptName = "";
      this.unitId.map(item => {
        if (item.deptId == row.unitId) {
          deptName = item.deptName;
        }
      });
      return deptName;
    },
    getTreeselect() {
      getDept().then(res => {
        this.unitId = res.data;
      });
    },
    // 审批操作
    handleApprove(row, result) {
      const newData = { ...row, status: result };
      updateStatus({ id: row.id, status: result }).then(() => {
        this.$modal.msgSuccess(result === "approved" ? "审批通过" : "已驳回");
        this.getList();
      });
    }
  }
};
</script>

<style scoped>
.app-container {
  background: #fff;
  padding: 20px;
}
</style>
