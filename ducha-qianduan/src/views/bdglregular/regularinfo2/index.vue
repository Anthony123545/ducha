<template>
  <div class="app-container">
    <!-- 顶部按钮区 -->
    <div class="mb8">
      <el-button type="primary" @click="handleAdd">
        {{ queryParams.caoZuoType === '1' ? '加分录入' : '减分录入' }}
      </el-button>
      <el-button type="danger" @click="batchDelete">删除</el-button>
      <el-select v-model="queryParams.danWei" placeholder="请选择受检单位" style="margin-left:10px;">
        <el-option label="单位A" value="单位A" />
        <el-option label="单位B" value="单位B" />
      </el-select>
      <el-select v-model="queryParams.status" placeholder="请选择状态" style="margin-left:10px;">
        <el-option label="已审批" value="1" />
        <el-option label="未审批" value="0" />
      </el-select>
      <el-button style="margin-left:10px;" @click="resetQuery">重置</el-button>
    </div>

    <!-- 加分/减分切换 -->
    <div class="mb8">
      <el-radio-group v-model="queryParams.caoZuoType" @change="caoZuoTypefun">
        <el-radio-button label="1">加分</el-radio-button>
        <el-radio-button label="0">减分</el-radio-button>
      </el-radio-group>
    </div>

    <!-- 表格 -->
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="id" label="序号" width="60" />
      <el-table-column prop="danWei" label="单位" width="120" />
      <el-table-column prop="leiXing" label="类型" width="120" />
      <el-table-column prop="jianChaShiJian" label="检查时间" width="180" />
      <el-table-column prop="jianChaDiDian" label="检查地点" width="120" />
      <el-table-column label="分值" width="80">
        <template slot-scope="scope">
          <span :style="{ color: scope.row.fenZhi > 0 ? 'green' : 'red' }">
            {{ scope.row.fenZhi }}
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="shiYou" label="事由" />
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.row)">
            修改
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div style="margin-top: 20px; text-align:right;">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="tableData.length"
        :page-size="10"
      />
    </div>

    <!-- 修改弹窗 -->
    <el-dialog title="修改记录" :visible.sync="editDialogVisible" width="600px">
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="单位">
          <el-input v-model="editForm.danWei" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="editForm.leiXing">
            <el-option label="日常检查" value="日常检查" />
            <el-option label="专项检查" value="专项检查" />
          </el-select>
        </el-form-item>
        <el-form-item label="检查时间">
          <el-date-picker
            v-model="editForm.jianChaShiJian"
            type="datetime"
            placeholder="选择时间"
          />
        </el-form-item>
        <el-form-item label="检查地点">
          <el-input v-model="editForm.jianChaDiDian" />
        </el-form-item>
        <el-form-item label="分值">
          <el-input-number v-model="editForm.fenZhi" :min="-10" :max="10" />
        </el-form-item>
        <el-form-item label="事由">
          <el-input v-model="editForm.shiYou" type="textarea" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitEdit">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      queryParams: {
        caoZuoType: "1", // 1=加分, 0=减分
        danWei: "",
        status: "",
      },
      tableData: [
        {
          id: 1,
          danWei: "单位A",
          leiXing: "日常检查",
          jianChaShiJian: "2025-09-04 16:38:13",
          jianChaDiDian: "11111",
          fenZhi: 3,
          shiYou: "表现良好",
        },
      ],
      editDialogVisible: false,
      editForm: {
        id: null,
        danWei: "",
        leiXing: "",
        jianChaShiJian: "",
        jianChaDiDian: "",
        fenZhi: 0,
        shiYou: "",
      },
    };
  },
  methods: {
    caoZuoTypefun() {
      this.getList();
    },
    getList() {
      console.log("获取数据，操作类型:", this.queryParams.caoZuoType);
    },
    resetQuery() {
      this.queryParams = { caoZuoType: "1", danWei: "", status: "" };
      this.getList();
    },
    // 新增（这里只是占位，等你接后端接口）
    handleAdd() {
      this.$message.info("这里是新增功能，可以弹出新增表单");
    },
    // 修改
    handleEdit(row) {
      this.editForm = { ...row };
      this.editDialogVisible = true;
    },
    submitEdit() {
      const index = this.tableData.findIndex((item) => item.id === this.editForm.id);
      if (index !== -1) {
        this.tableData.splice(index, 1, { ...this.editForm });
      }
      this.editDialogVisible = false;
      this.$message.success("修改成功！");
    },
    // 删除
    handleDelete(row) {
      this.tableData = this.tableData.filter((item) => item.id !== row.id);
      this.$message.success("删除成功！");
    },
    // 批量删除
    batchDelete() {
      this.$message.warning("批量删除逻辑请自己实现~");
    },
  },
};
</script>
