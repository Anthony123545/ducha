<template>
  <div class="app-container">
    <!-- 1. 录入表单卡片 -->
    <el-card shadow="never" style="margin-bottom: 20px">
      <div slot="header"><span>录入安全隐患</span></div>
      <el-form :model="form" inline size="small">
        <el-form-item label="排查对象">
          <el-input
            v-model="form.checkTarget"
            placeholder="请输入"
            style="width: 150px"
          />
        </el-form-item>
        <el-form-item label="隐患名称">
          <el-input
            v-model="form.hazardName"
            placeholder="请输入"
            style="width: 150px"
          />
        </el-form-item>
        <el-form-item label="隐患详情">
          <el-input
            v-model="form.description"
            placeholder="请输入"
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="等级">
          <el-select
            v-model="form.riskLevelCn"
            placeholder="请选择"
            style="width: 120px"
          >
            <el-option label="一般隐患" value="一般隐患"></el-option>
            <el-option label="较大隐患" value="较大隐患"></el-option>
            <el-option label="重大隐患" value="重大隐患"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="addProblem">录入问题</el-button>
        </el-form-item>
      </el-form>
      <div style="color: #909399; font-size: 12px">
        注：更多字段（如责任人、时限等）可在录入接口中扩展
      </div>
    </el-card>

    <!-- 2. 隐患台账卡片 -->
    <el-card shadow="always">
      <div
        slot="header"
        style="
          display: flex;
          justify-content: space-between;
          align-items: center;
        "
      >
        <span>安全隐患台账（实时显示）</span>
        <!-- 新增：导出按钮 -->
        <el-button
          type="warning"
          size="mini"
          icon="el-icon-download"
          @click="handleExport"
        >
          导出 Word {{ multipleSelection.length > 0 ? "(选中项)" : "(全部)" }}
        </el-button>
      </div>

      <!-- 修改点：添加 @selection-change 用于处理勾选 -->
      <el-table
        :data="problemList"
        border
        style="width: 100%"
        fit
        size="mini"
        @selection-change="handleSelectionChange"
      >
        <!-- 新增：多选框列，用于导出选择 -->
        <el-table-column type="selection" width="40" align="center" />
        <el-table-column type="index" label="序号" width="50" align="center" />
        <el-table-column prop="checkTarget" label="排查对象" width="100" />
        <el-table-column prop="hazardName" label="隐患名称" width="120" />
        <el-table-column prop="description" label="隐患详情" min-width="180" />
        <el-table-column prop="hazardCategory" label="隐患类别" width="100" />

        <el-table-column label="隐患等级" width="100">
          <template slot-scope="scope">
            <el-tag
              size="mini"
              :type="
                scope.row.riskLevel === 'VERY_HIGH'
                  ? 'danger'
                  : scope.row.riskLevel === 'HIGH'
                    ? 'warning'
                    : 'info'
              "
            >
              {{ riskToCn(scope.row.riskLevel) }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="hazardSource" label="隐患来源" width="100" />

        <el-table-column label="发现时间" width="110">
          <template slot-scope="scope">{{
            formatDateOnly(scope.row.foundAt)
          }}</template>
        </el-table-column>

        <el-table-column label="入账时间" width="110">
          <template slot-scope="scope">{{
            formatDateOnly(scope.row.entryTime || scope.row.foundAt)
          }}</template>
        </el-table-column>

        <el-table-column prop="dept" label="责任单位" width="120" />
        <el-table-column prop="person" label="责任人" width="100" />
        <el-table-column prop="currentSituation" label="隐患现状" width="150" />

        <el-table-column label="整治时限" width="110">
          <template slot-scope="scope">{{
            formatDateOnly(scope.row.deadline)
          }}</template>
        </el-table-column>

        <el-table-column label="完成时间" width="110">
          <template slot-scope="scope">{{
            formatDateOnly(scope.row.completionTime)
          }}</template>
        </el-table-column>

        <el-table-column
          prop="rectificationMeasures"
          label="整治措施"
          min-width="200"
          show-overflow-tooltip
        />
        <el-table-column label="验收时间" width="110">
          <template slot-scope="scope">{{
            formatDateOnly(scope.row.acceptanceTime)
          }}</template>
        </el-table-column>
        <el-table-column prop="acceptanceUnit" label="验收单位" width="120" />
        <el-table-column
          prop="acceptanceConclusion"
          label="验收结论"
          width="120"
        />
        <el-table-column label="销账时间" width="110">
          <template slot-scope="scope">{{
            formatDateOnly(scope.row.closeTime)
          }}</template>
        </el-table-column>
        <el-table-column
          prop="remark"
          label="备注"
          width="150"
          show-overflow-tooltip
        />

        <el-table-column label="操作" width="160" fixed="right">
          <template slot-scope="scope">
            <!-- 新增：修改按键 -->
            <el-button size="mini" type="text" @click="handleEdit(scope.row)"
              >修改</el-button
            >
            <el-button
              size="mini"
              type="text"
              :disabled="scope.row.status === 'RECTIFIED'"
              @click="markResolved(scope.row)"
              >整改</el-button
            >
            <el-button
              size="mini"
              type="text"
              style="color: red"
              @click="deleteProblem(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 10px">隐患总数：{{ total }}</div>
    </el-card>

    <!-- 新增：修改弹窗 -->
    <el-dialog
      title="修改隐患记录"
      :visible.sync="editDialogVisible"
      width="500px"
    >
      <el-form :model="editForm" label-width="100px" size="small">
        <el-form-item label="排查对象"
          ><el-input v-model="editForm.checkTarget"
        /></el-form-item>
        <el-form-item label="隐患名称"
          ><el-input v-model="editForm.hazardName"
        /></el-form-item>
        <el-form-item label="隐患详情"
          ><el-input type="textarea" v-model="editForm.description"
        /></el-form-item>
        <el-form-item label="等级">
          <el-select v-model="editForm.riskLevel" style="width: 100%">
            <el-option label="一般隐患" value="LOW"></el-option>
            <el-option label="较大隐患" value="HIGH"></el-option>
            <el-option label="重大隐患" value="VERY_HIGH"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="责任单位"
          ><el-input v-model="editForm.dept"
        /></el-form-item>
        <el-form-item label="责任人"
          ><el-input v-model="editForm.person"
        /></el-form-item>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="整治措施">
              <el-input
                type="textarea"
                :rows="3"
                v-model="editForm.rectificationMeasures"
                placeholder="请输入具体整治措施"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="验收单位"
              ><el-input v-model="editForm.acceptanceUnit"
            /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="验收时间">
              <el-date-picker
                v-model="editForm.acceptanceTime"
                type="date"
                style="width: 100%"
                value-format="yyyy-MM-dd"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="验收结论"
              ><el-input v-model="editForm.acceptanceConclusion"
            /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销账时间">
              <el-date-picker
                v-model="editForm.closeTime"
                type="date"
                style="width: 100%"
                value-format="yyyy-MM-dd"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注"
              ><el-input v-model="editForm.remark"
            /></el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer">
        <el-button size="small" @click="editDialogVisible = false"
          >取消</el-button
        >
        <el-button size="small" type="primary" @click="confirmEdit"
          >保存修改</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  fetchIssues,
  createIssue,
  updateIssueStatus, // 假设你有更新状态的接口
  deleteIssue,
  // 假设后端增加了 updateIssue 和 exportWord 接口
} from "@/api/safety/safety";
import request from "@/utils/request"; // 用于文件下载请求

export default {
  name: "RiskWarning",
  data() {
    return {
      form: {
        checkTarget: "",
        hazardName: "",
        description: "",
        riskLevelCn: "一般隐患",
        hazardCategory: "其他",
        hazardSource: "日常检查",
        dept: "",
        person: "",
      },
      problemList: [],
      multipleSelection: [], // 存储选中的行
      total: 0,
      pageNum: 1,
      pageSize: 100,
      _timer: null,

      // 修改功能相关
      editDialogVisible: false,
      editForm: {},
    };
  },
  mounted() {
    this.loadList();
    this._timer = setInterval(this.loadList, 8000);
    this.$once("hook:beforeDestroy", () => {
      if (this._timer) clearInterval(this._timer);
    });
  },
  methods: {
    // 处理勾选
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    // 1. 修改功能：打开弹窗
    handleEdit(row) {
      this.editForm = JSON.parse(JSON.stringify(row)); // 深拷贝防止直接改表格
      this.editDialogVisible = true;
    },

    // 1. 修改功能：提交保存
    async confirmEdit() {
      try {
        // 注意：这里需要后端提供 update 接口，通常是 put 请求
        await request({
          url: "/safety/issue", // 请根据实际接口路径修改
          method: "put",
          data: this.editForm,
        });
        this.$message.success("修改成功");
        this.editDialogVisible = false;
        this.loadList();
      } catch (e) {
        this.$message.error("修改失败");
      }
    },

    // 2. 导出功能
    async handleExport() {
      const ids = this.multipleSelection.map((item) => item.id);
      const isPartial = ids.length > 0;

      try {
        await this.$confirm(
          isPartial
            ? `确认导出选中的 ${ids.length} 条记录吗？`
            : "确认导出全部台账记录吗？",
          "提示",
          { type: "info" },
        );

        // 导出请求
        // 后端接口需支持接收 ids 数组，并返回 word 文件流
        const response = await request({
          url: "/api/safety/issues/export/word",
          method: "post",
          data: { ids: isPartial ? ids : [] }, // 如果 ids 为空，后端逻辑应为导出全部
          responseType: "blob",
        });

        // 处理文件下载
        const url = window.URL.createObjectURL(new Blob([response]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute(
          "download",
          `安全隐患台账_${new Date().getTime()}.docx`,
        );
        document.body.appendChild(link);
        link.click();
        this.$message.success("导出成功，请查看下载列表");
      } catch (e) {
        if (e !== "cancel") this.$message.error("导出失败");
      }
    },

    async addProblem() {
      if (!this.form.description || !this.form.hazardName) {
        this.$message.warning("请填写隐患名称和详情！");
        return;
      }
      const payload = {
        ...this.form,
        riskLevel: this.cnToRisk(this.form.riskLevelCn),
        foundAt: new Date().toISOString(),
        entryTime: new Date().toISOString(),
      };
      try {
        await createIssue(payload);
        this.$message.success("录入成功！");
        this.form.hazardName = "";
        this.form.description = "";
        this.loadList();
      } catch (e) {
        this.$message.error("录入失败");
      }
    },

    async loadList() {
      try {
        const params = { pageNum: this.pageNum, pageSize: this.pageSize };
        const res = await fetchIssues(params);
        const data =
          (res && (res.records || res.rows)) ||
          (res && res.data && res.data.records) ||
          [];
        this.problemList = data;
        this.total =
          (res && res.total) || (res && res.data && res.data.total) || 0;
      } catch (e) {
        console.error(e);
      }
    },

    formatDateOnly(dt) {
      const d = this.parseDate(dt);
      if (!d) return "-";
      return `${d.getMonth() + 1}月${d.getDate()}日`;
    },

    async markResolved(row) {
      try {
        await updateIssueStatus(row.id, "RECTIFIED");
        this.$message.success("已标记为整改完成");
        this.loadList();
      } catch (e) {
        this.$message.error("操作失败");
      }
    },

    async deleteProblem(row) {
      try {
        await this.$confirm("确认删除该条隐患记录吗？", "提示", {
          type: "warning",
        });
        await deleteIssue(row.id);
        this.$message.success("删除成功");
        this.loadList();
      } catch (e) {
        if (e !== "cancel") console.error(e);
      }
    },

    cnToRisk(cn) {
      if (cn === "重大隐患") return "VERY_HIGH";
      if (cn === "较大隐患") return "HIGH";
      return "LOW";
    },
    riskToCn(en) {
      if (en === "VERY_HIGH") return "重大隐患";
      if (en === "HIGH") return "较大隐患";
      return "一般隐患";
    },
    parseDate(dt) {
      if (!dt) return null;
      const d = new Date(dt);
      return isNaN(d) ? null : d;
    },
  },
};
</script>

<style scoped>
.app-container {
  padding: 20px;
  background-color: #f0f2f5;
  min-height: 100vh;
}
::v-deep .el-form-item__label {
  color: #333333 !important;
  font-weight: 500;
}
::v-deep .el-table th {
  background-color: #f8f9fa !important;
  color: #333333 !important;
  font-weight: bold;
}
::v-deep .el-input__inner {
  color: #333 !important;
}
</style>
