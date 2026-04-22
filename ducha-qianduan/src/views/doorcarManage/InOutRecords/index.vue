<template>
  <div style="padding: 10px">
    <div style="height: 50px; display: flex; align-items: center; gap: 10px">
      <!-- 选择设备 -->
      <el-select
        v-model="selectedDeviceId"
        placeholder="选择设备"
        size="small"
        style="width: 200px"
        clearable
        @change="handleSearch()"
      >
        <el-option
          style="color: black"
          v-for="device in devices"
          :key="device.id"
          :label="device.name"
          :value="device.id"
        />
      </el-select>
      <el-input
        v-model="inputPlateNumber"
        placeholder="输入车牌号"
        size="small"
        style="width: 200px"
        clearable
        @keyup.enter.native="handleSearch()"
      />
      <el-select
        v-model="eventType"
        placeholder="选择进出类型"
        size="small"
        style="width: 200px"
        clearable
        @change="handleSearch()"
      >
        <el-option label="全部" :value="''" />
        <el-option label="进入" :value="'in'" />
        <el-option label="离开" :value="'out'" />
      </el-select>
      <el-date-picker
        v-model="timeRange"
        type="datetimerange"
        start-placeholder="选择开始时间"
        end-placeholder="选择结束时间"
        size="small"
        style="width: 400px"
      />
      <el-button type="primary" size="small" @click="handleSearch()"
        >查询</el-button
      >
      <!--      <el-button type="success" @click="handleAdd()" style="margin-left: 10px">新增</el-button>-->
      <el-button
        :disabled="multipleSelection.length === 0"
        type="danger"
        size="small"
        @click="handleBatchDelete"
        >批量删除</el-button
      >
    </div>

    <el-table
      v-loading="loading"
      :data="tableData"
      style="width: 100%; margin-top: 10px"
      border
      :header-cell-style="changeHeaderCellStyle"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column
        label="序号"
        type="index"
        width="60"
        align="center"
        sortable
      >
        <template slot-scope="scope">
          <span>{{ (currentPage - 1) * pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="plateNumber"
        label="车牌号"
        min-width="120"
        align="center"
        sortable
      />
      <el-table-column
        prop="deviceId"
        label="设备"
        min-width="120"
        align="center"
        sortable
      />
      <el-table-column
        prop="eventType"
        label="类型"
        min-width="100"
        align="center"
        sortable
      >
        <template #default="{ row }">
          {{
            row.eventType === "in"
              ? "进入"
              : row.eventType === "out"
                ? "离开"
                : row.eventType || "-"
          }}
        </template>
      </el-table-column>
      <el-table-column label="抓拍时间" min-width="300" align="center" sortable>
        <template #default="{ row }">
          {{ formatDateTime(row.captureTime) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="photoUrl"
        label="照片"
        min-width="200"
        align="center"
        sortable
      >
        <template #default="{ row }">
          <img
            v-if="row.photoUrl"
            :src="row.photoUrl"
            :alt="`人员 ${row.name || ''} 的抓拍照片`"
            style="
              width: 100px;
              height: 80px;
              object-fit: cover;
              border-radius: 6px;
            "
          />
          <span v-else style="color: #999">无照片</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="remark"
        label="备注"
        min-width="200"
        align="center"
        sortable
      />
      <el-table-column label="操作" width="150" align="center">
        <template #default="{ row }">
          <!--          <el-button type="text" size="small" @click="handleEdit(row)">编辑</el-button>-->
          <el-button type="danger" size="small" @click="handleDelete(row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      layout="total, prev, pager, next, jumper, sizes"
      :total="total"
      :current-page.sync="currentPage"
      :page-size.sync="pageSize"
      :page-sizes="[10, 25, 50, 100]"
      @current-change="handlePageChange"
      @size-change="handleSizeChange"
      style="margin-top: 10px; text-align: right"
    />
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      // 设备列表
      devices: [],
      selectedDeviceId: null,
      inputPlateNumber: "",
      eventType: "",
      timeRange: [],
      // 表格数据
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      loading: false,
      // 表格多选
      multipleSelection: [],
    };
  },
  mounted() {
    this.getDeviceInfo();
  },
  methods: {
    // 2. 修改：新增时间格式化方法
    formatDateTime(dateStr) {
      if (!dateStr) return "-";
      try {
        // 创建日期对象
        const date = new Date(dateStr);
        // 获取年、月、日、时、分、秒
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, "0");
        const day = String(date.getDate()).padStart(2, "0");
        const hours = String(date.getHours()).padStart(2, "0");
        const minutes = String(date.getMinutes()).padStart(2, "0");
        const seconds = String(date.getSeconds()).padStart(2, "0");
        // 拼接成普通时间格式：YYYY-MM-DD HH:mm:ss
        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      } catch (e) {
        console.error("时间格式化失败：", e);
        return dateStr; // 格式化失败时返回原始值
      }
    },
    // 监听多选变化
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    async handleSearch() {
      console.log("查询");
      this.loading = true;
      try {
        const params = {
          deviceId: this.selectedDeviceId,
          plateNumber: this.inputPlateNumber,
          eventType: this.eventType,
          page: this.currentPage,
          size: this.pageSize,
        };
        if (this.timeRange.length === 2) {
          params.startTime = this.timeRange[0];
          params.endTime = this.timeRange[1];
        }
        console.log("查询参数:", params);
        const res = await request({
          url: "/carCamera/accessRecord/list",
          method: "POST",
          data: params,
        });
        console.log("查询结果:", res);
        if (res && res.list) {
          this.tableData = res.list;
          this.total = res.total;
        } else {
          this.tableData = [];
          this.total = 0;
          this.$message.warning("未获取到数据");
        }
        console.log("查询结果:this.tableData", this.tableData);
      } finally {
        this.loading = false;
      }
    },
    // 单条删除
    handleDelete(row) {
      this.$confirm(
        `确定要删除【${row.name || "未知姓名"}】的进出记录吗？`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        },
      )
        .then(() => {
          const data = {
            id: row.id,
          };
          this.deleteUser(data, "/carCamera/accessRecord/delete");
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    // 批量删除
    handleBatchDelete() {
      const list = this.multipleSelection.map((item) => item.id);
      if (list.length === 0) {
        this.$message.warning("请先选择要删除的人员进出记录");
        return;
      }
      this.$confirm(
        `确定要删除选中的 ${list.length} 个人员进出记录吗？`,
        "批量删除确认",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        },
      )
        .then(() => {
          const data = {
            ids: list,
          };
          this.deleteUser(data, "/carCamera/accessRecord/batchDelete");
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    // 通用删除方法
    async deleteUser(data, url) {
      console.log("删除人员进出记录参数：", data);
      console.log("删除人员进出记录url：", url);
      try {
        const res = await request({
          url: url,
          method: "POST",
          data: data,
        });
        console.log("删除人员进出记录结果：", res);
        if (res.success === true) {
          this.$message.success("删除成功");
          this.multipleSelection = [];
          this.handleSearch();
        } else {
          this.$message.error(res.msg || "删除失败");
        }
      } catch (e) {
        console.error("删除人员进出记录失败：", e);
        this.$message.error("请求出错，请稍后重试");
      }
    },
    // 表格样式修改
    changeHeaderCellStyle(row, column, rowIndex, columnIndex) {
      if (row.columnIndex === 0) {
        return "background: #004279 ; color:#fff;"; // 修改的样式
      } else {
        return "background: #004279 ;color:#fff; ";
      }
    },
    // 切换页码
    handlePageChange(page) {
      this.currentPage = page;
      this.handleSearch();

      // 等待数据加载完成后滚动到顶部
      this.$nextTick(() => {
        this.scrollToTop();
      });
    },
    scrollToTop() {
      // 方法1: 使用 window.scrollTo
      window.scrollTo({
        top: 0,
        behavior: "smooth", // 平滑滚动
      });
    },
    // 修改每页条数
    handleSizeChange(size) {
      this.pageSize = size;
      this.currentPage = 1; // 切换条数后回到第一页
      this.handleSearch();
    },
    async getDeviceInfo() {
      try {
        this.loading = true;
        const data = {
          deviceType: "carDoor",
          enable: true,
          paginate: false,
        };
        console.log("获取设备列表参数：", data);
        const res = await request({
          url: "/device/list",
          method: "POST",
          data: data,
        });
        console.log("获取设备列表结果：", res);
        // 判断返回结构
        if (res && res.status === true) {
          this.devices = Array.isArray(res.list) ? res.list : [];
          this.total = typeof res.total === "number" ? res.total : 0;
          this.$message.success(res.message || "获取设备列表成功");
          if (this.devices.length > 0) {
            this.selectedDeviceId = this.devices[0].id;
            console.log("选中的设备ID:", this.selectedDeviceId);
            // 获取该设备的信息
            await this.handleSearch();
          }
        } else {
          this.devices = [];
          this.total = 0;
          this.$message.error(res.message || "获取设备列表失败");
        }
      } catch (error) {
        console.error("获取设备信息失败:", error);
        this.$message.error("获取设备列表失败，请检查网络或后端接口！");
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>
