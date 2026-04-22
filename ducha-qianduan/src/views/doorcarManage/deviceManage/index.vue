<template>
  <div style="padding: 10px">
    <!-- 搜索和新增 -->
    <div style="height: 50px; display: flex; align-items: center; gap: 10px">
      <!-- 输入框：回车自动查询 -->
      <el-input
        v-model="searchDeviceName"
        placeholder="设备名称"
        size="small"
        style="width: 150px"
        clearable
        @keyup.enter.native="handleSearch"
      />
      <el-button type="primary" size="small" @click="handleSearch"
                 style="margin-left: 10px">查询</el-button
      >
      <el-button type="primary" size="small" @click="handleAdd">新增</el-button>
      <el-button
        :disabled="multipleSelection.length <= 0"
        type="danger"
        size="small"
        @click="handleBatchDelete"
      >批量删除</el-button
      >
    </div>
    <el-table
      :data="devices"
      style="width: 100%; margin-top: 10px"
      border
      :header-cell-style="changeHeaderCellStyle"
      @selection-change="handleSelectionChange"
      v-loading="loading"
    >
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" align="center" min-width="50" sortable>
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        prop="id"
        label="设备ID"
        align="center"
        width="60"
        sortable
      />
      <el-table-column
        prop="name"
        label="设备名称"
        align="center"
        min-width="100"
        sortable
      />
      <el-table-column
        prop="serialNumber"
        label="设备序列号"
        align="center"
        min-width="100"
        sortable
      />
      <el-table-column label="设备类型" align="center" min-width="100">
        <template slot-scope="scope">
          <span>
            {{ scope.row.type === 'carDoor' ? '车闸' : scope.row.type === 'peopleDoor' ? '人行摆闸' : '' }}
          </span>
        </template>
      </el-table-column>
      <el-table-column
        prop="location"
        label="设备位置"
        align="center"
        min-width="150"
        sortable
      />
      <el-table-column label="进出方向" align="center" min-width="100">
        <template slot-scope="scope">
          <span>
            {{ scope.row.accessPermission === 'in' ? '只准进' : scope.row.accessPermission === 'out' ? '只准出' : '既可进也可出' }}
          </span>
        </template>
      </el-table-column>
      <el-table-column
        prop="isUpdateStatus"
        label="是否更新车辆出入状态"
        align="center"
        min-width="100"
        sortable
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.isUpdateStatus ? 'success' : 'danger'">
            {{ scope.row.isUpdateStatus ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="ip"
        label="设备IP"
        align="center"
        min-width="100"
        sortable
      />
      <el-table-column
        prop="port"
        label="设备Port"
        align="center"
        min-width="100"
        sortable
      />
      <el-table-column
        prop="account"
        label="账号"
        align="center"
        min-width="100"
        sortable
      />
      <el-table-column
        prop="password"
        label="密码"
        align="center"
        min-width="100"
        sortable
      />
      <!--      <el-table-column-->
      <!--        prop="status"-->
      <!--        label="设备状态"-->
      <!--        align="center"-->
      <!--        min-width="100"-->
      <!--        sortable-->
      <!--      />-->
      <el-table-column
        prop="firmwareVersion"
        label="固件版本"
        align="center"
        min-width="100"
        sortable
      />
      <el-table-column
        prop="remark"
        label="备注"
        align="center"
        min-width="100"
        sortable
      />
      <el-table-column
        prop="enable"
        label="启用状态"
        align="center"
        min-width="100"
        sortable
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.enable ? 'success' : 'danger'">
            {{ scope.row.enable ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        fixed="right"
        align="center"
        min-width="100"
      >
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleEdit(row)"
          >编辑</el-button
          >
          <el-button type="danger" size="small" @click="handleDelete(row)"
          >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      class="custom-pagination"
      style="margin-top: 20px; text-align: right"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      :page-sizes="[10, 15, 20, 25, 30]"
      layout="total, prev, pager, next, jumper, sizes"
      @current-change="handlePageChange"
      @size-change="handleSizeChange"
    />
    <!-- 新增/编辑弹窗 -->
    <DeviceEditDialog
      :visible.sync="dialogVisible"
      :device-data="currentDeviceData"
      @submit="handleDeviceSubmit"
      @cancel="handleDeviceCancel"
    />
  </div>
</template>

<script>
import DeviceEditDialog from "./DeviceEditDialog.vue";
import request from "@/utils/request";

export default {
  name: "DeviceManagement",
  components: {
    DeviceEditDialog,
  },
  data() {
    return {
      searchDeviceName: "",

      loading: false,
      devices: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,

      dialogVisible: false,
      currentDeviceData: {},

      multipleSelection: [], // 存储选中的多行数据
    };
  },
  mounted() {
    this.getDeviceInfo();
  },
  methods: {
    handleSearch() {
      this.currentPage = 1;
      this.getDeviceInfo();
    },
    handleAdd() {
      this.currentDeviceData = {};
      this.dialogVisible = true;
    },
    handleEdit(row) {
      this.currentDeviceData = { ...row };
      this.dialogVisible = true;
    },
    handlePageChange(page) {
      this.currentPage = page;
      this.getDeviceInfo();
    },
    handleSizeChange(size) {
      this.currentPage = 1;
      this.pageSize = size;
      this.getDeviceInfo();
    },
    // 监听多选变化
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log("多选数据：", val);
    },
    // 表格样式修改
    changeHeaderCellStyle(row, column, rowIndex, columnIndex) {
      if (row.columnIndex === 0) {
        return "background: #004279 ; color:#fff;"; // 修改的样式
      } else {
        return "background: #004279 ;color:#fff; ";
      }
    },
    // 单条删除
    handleDelete(row) {
      this.$confirm(`确定要删除设备号为【${row.id}】的设备吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          try {
            this.loading = true;
            const res = await request({
              url: `/device/delete/${row.id}`, // ✅ 对应后端 @DeleteMapping("/delete/{id}")
              method: "DELETE",
            });
            console.log("删除结果：", res);
            if (res.status) {
              this.$message.success(res.message || "删除成功");
              this.getDeviceInfo();
            } else {
              this.$message.error(res.message || "删除失败");
            }
          } catch (error) {
            console.error("删除失败:", error);
            this.$message.error("删除设备失败，请稍后重试");
          } finally {
            this.loading = false;
          }
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    async handleDeviceSubmit(deviceData) {
      const url = deviceData.id ? "/device/update" : "/device/add";
      console.log("提交设备参数：", deviceData);

      try {
        const res = await request({ url, method: "POST", data: deviceData });
        console.log("提交设备结果：", res);

        if (res && res.status === true) {
          this.$message.success(res.message || "操作成功");
          this.dialogVisible = false;
          this.loading = true;
          try {
            await this.getDeviceInfo();
          } finally {
            this.loading = false;
          }
        } else {
          this.$message.error(res?.message || "操作失败");
        }
      } catch (error) {
        console.error("提交设备失败:", error);
        this.$message.error("提交设备失败，请检查网络或后端接口");
      }
    },
    handleDeviceCancel() {
      this.dialogVisible = false;
    },
    // 批量删除
    handleBatchDelete() {
      const ids = this.multipleSelection.map((item) => item.id);
      if (ids.length === 0) {
        this.$message.warning("请先选择要删除的设备");
        return;
      }
      this.$confirm(`确定要删除选中的 ${ids.length} 个设备吗？`, "批量删除确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          try {
            this.loading = true;
            const res = await request({
              url: "/device/deleteBatch", // ✅ 对应后端 @PostMapping("/delete/batch")
              method: "POST",
              data: ids, // ✅ body是数组，例如 [1,2,3]
            });
            console.log("批量删除结果：", res);
            if (res.status) {
              this.$message.success(res.message || "批量删除成功");
              this.getDeviceInfo();
            } else {
              this.$message.error(res.message || "批量删除失败");
            }
          } catch (error) {
            console.error("批量删除失败:", error);
            this.$message.error("批量删除请求出错，请稍后重试");
          } finally {
            this.loading = false;
          }
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    async getDeviceInfo() {
      try {
        this.loading = true;
        const data = {
          deviceName: this.searchDeviceName?.trim() || null, // 去掉多余空格，避免 "" 导致条件错乱
          deviceType: "carDoor",
          page: this.currentPage,
          size: this.pageSize,
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

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
