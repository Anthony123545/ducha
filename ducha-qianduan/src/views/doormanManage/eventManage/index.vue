<template>
  <div style="padding: 10px">
    <!-- 搜索栏 -->
    <div style="margin-bottom: 10px">
      <!-- 选择设备 -->
      <el-select
        v-model="selectedDeviceId"
        placeholder="选择设备"
        size="small"
        style="width: 150px"
        clearable
        @change="handleSearch"
      >
        <el-option
          style="color: black"
          v-for="device in devices"
          :key="device.id"
          :label="device.name"
          :value="device.id"
        />
      </el-select>
      <el-button
        type="primary"
        @click="handleSearchAddCondition()"
        style="margin-left: 10px"
        >点击输入查询条件</el-button
      >
      <el-button
        type="primary"
        @click="handleSearch()"
        style="margin-left: 10px"
        >查询</el-button
      >
    </div>

    <!-- 人员表格 -->
    <el-table
      v-loading="loading"
      :data="tableData"
      style="width: 100%; margin-top: 10px"
      border
      :header-cell-style="changeHeaderCellStyle"
    >
      <el-table-column label="序号" align="center" min-width="50" sortable>
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        prop="employeeNo"
        label="工号"
        width="100"
        align="center"
        sortable
      />
      <el-table-column
        prop="dwMajor"
        label="报警主类型"
        min-width="100"
        align="center"
        sortable
      />
      <el-table-column
        prop="dwMinor"
        label="报警次类型"
        min-width="100"
        align="center"
        sortable
      />
      <el-table-column
        prop="wInductiveEventType"
        label="归纳事件类型"
        min-width="100"
        align="center"
        sortable
      >
        <template #default="{ row }">
          {{ (getInductiveEventName(row.wInductiveEventType), "access") }}
        </template>
      </el-table-column>
      <el-table-column
        prop="struTime"
        label="事件时间"
        min-width="200"
        align="center"
        sortable
      >
        <template #default="{ row }">
          {{ formatNetDvrTime(row.struTime) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="sNetUser"
        label="用户"
        min-width="100"
        align="center"
        sortable
      />
      <el-table-column
        prop="struRemoteHostAddr"
        label="设备IP"
        min-width="100"
        align="center"
        sortable
      />
      <el-table-column label="操作" fixed="right" width="150" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="small"
            @click="viewFaceInfo(scope.row)"
            >查看</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div style="margin-top: 10px; text-align: right">
      <el-pagination
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        :page-sizes="[5, 10, 20, 50, 100]"
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
      />
    </div>
    <!-- 搜索弹窗 -->
    <event-search-dialog
      :visible.sync="searchVisible"
      :event-data="currentEvent"
      @submit="handleSearchEventSubmit"
      @cancel="handleSearchEventCancel"
    />
    <!-- 查看详情弹窗 -->
    <event-detail-dialog
      :visible.sync="viewVisible"
      :event-data="currentEvent"
    />
  </div>
</template>

<script>
import request from "@/utils/request";
import EventSearchDialog from "./EventSearchDialog.vue";
import EventDetailDialog from "./EventDetailDialog.vue";
import DeviceEditDialog from "@/views/doorcarManage/deviceManage/DeviceEditDialog.vue";

export default {
  name: "EventManage",
  components: {
    DeviceEditDialog,
    EventSearchDialog,
    EventDetailDialog,
  },
  data() {
    return {
      currentEvent: null,
      loading: false,
      devices: [], // 设备列表
      selectedDeviceId: null, // 当前选择的设备

      tableData: [
        // {
        //   dwMajor: 1,
        //   dwMinor: 2,
        //   wInductiveEventType: 3,
        //   struTime: {
        //     dwYear: 2025,
        //     dwMonth: 10,
        //     dwDay: 24,
        //     dwHour: 19,
        //     dwMinute: 30,
        //     dwSecond: 45,
        //   },
        //   sNetUser: "admin",
        //   struRemoteHostAddr: "192.168.1.100",
        // },
        // {
        //   dwMajor: 1,
        //   dwMinor: 5,
        //   wInductiveEventType: 1,
        //   struTime: {
        //     dwYear: 2025,
        //     dwMonth: 10,
        //     dwDay: 24,
        //     dwHour: 20,
        //     dwMinute: 15,
        //     dwSecond: 10,
        //   },
        //   sNetUser: "operator",
        //   struRemoteHostAddr: "192.168.1.101",
        // },
      ],
      total: 0,
      currentPage: 1,
      pageSize: 10,

      searchCondition: null,

      searchVisible: false,
      viewVisible: false,
    };
  },
  mounted() {
    this.getDeviceInfo();
    // this.getDeviceInfo().then(() => {
    //   // if (this.devices.length > 0) {
    //     console.log("✅ 自动查询第一个设备事件");
    //     this.handleSearch();
    //   // }
    // });
  },
  methods: {
    async handleSearch() {
      console.log("提交事件参数：", this.searchCondition);
      try {
        this.loading = true;
        if (!this.selectedDeviceId) {
          this.$message.error("请选择设备");
          return;
        }
        // 添加设备ID到查询参数中
        const queryParams = {
          ...this.searchCondition,
          deviceId: this.selectedDeviceId, // 添加当前选择的设备ID
          page: this.currentPage,
          size: this.pageSize,
        };
        console.log("完整查询参数:", queryParams);
        const res = await request({
          url: "/peopleEvent/list",
          method: "POST",
          data: queryParams,
        });
        // 更新表格数据
        if (res && res.status === true) {
          this.tableData = res.list;
          this.total = res.total || 0;
          this.$message.success(res.message || `查询到 ${this.total} 条记录`);
        } else {
          this.tableData = [];
          this.total = 0;
          this.$message.error(res.message || "查询失败");
        }
      } catch (error) {
        console.error("查询失败:", error);
        this.$message.error("查询失败，请检查网络连接");
      } finally {
        this.loading = false;
      }
    },
    viewFaceInfo(row) {
      this.currentEvent = row;
      this.viewVisible = true;
    },
    formatNetDvrTime(time) {
      if (!time) return "-";
      const { dwYear, dwMonth, dwDay, dwHour, dwMinute, dwSecond } = time;
      return (
        `${dwYear}-${String(dwMonth).padStart(2, "0")}-${String(dwDay).padStart(2, "0")} ` +
        `${String(dwHour).padStart(2, "0")}:${String(dwMinute).padStart(2, "0")}:${String(dwSecond).padStart(2, "0")}`
      );
    },
    getInductiveEventName(type, deviceType = "access") {
      const maps = {
        // 1️⃣ HIKVISION 门禁主机
        access: {
          0: "无效",
          1: "认证通过",
          2: "认证失败",
          3: "开门动作",
          4: "关门动作",
          5: "门异常",
          6: "远程操作",
          7: "校时事件",
          8: "设备异常事件",
          9: "设备恢复正常事件",
          10: "报警事件",
          11: "报警恢复事件",
          12: "呼叫中心",
        },

        // 2️⃣ HIKVISION 可视对讲
        intercom: {
          1: "防拆报警",
          2: "劫持报警",
          3: "多次密码开锁失败报警",
          4: "门未开报警",
          5: "门未关报警",
          6: "通话对讲报警",
          7: "密码开锁",
          8: "劫持开锁",
          9: "刷卡开锁",
          10: "户主开锁",
          11: "中心平台开锁",
          12: "设备对讲",
        },

        // 3️⃣ HIKVISION 报警主机
        alarm: {
          1: "防区事件",
          2: "主机事件",
        },

        // 4️⃣ 第三方 GJD 报警主机
        gjd: {
          1: "对象侦测报警",
          2: "人为防拆报警",
          3: "弱光检测报警",
          4: "定时报警（周期性）",
          5: "温度检测报警",
        },

        // 5️⃣ 第三方 Luminite 报警主机
        luminite: {
          1: "探测到移动",
          2: "传感器被干扰",
          3: "信号被遮挡",
          4: "检测到震动",
          5: "传感器故障",
          6: "传感器位置移动",
          7: "电池不足",
          8: "手动冲突数据",
          9: "接收机收到干扰信息",
          10: "防拆弹簧松弛",
        },

        // 6️⃣ 第三方 OPTEX 报警主机
        optex: {
          1: "远&近发生事件",
          2: "远距离事件",
          3: "近距离事件",
          4: "缓慢移动检测",
          5: "干扰检测",
          6: "防遮挡报警",
          7: "防旋转报警",
          8: "异常电路激活",
          9: "污物覆盖（自检异常）",
          10: "传感器异常",
          11: "设备故障",
          12: "设备不在线",
          13: "设备已连接",
          14: "区域A1事件",
          15: "区域A2事件",
          16: "区域B1事件",
          17: "区域B2事件",
          18: "区域A11事件",
          19: "区域A12事件",
          20: "区域A21事件",
          21: "区域A22事件",
          22: "区域B11事件",
          23: "区域B12事件",
          24: "区域B21事件",
          25: "区域B22事件",
        },
      };

      // 防御性处理
      if (!type) return "无效";
      const map = maps[deviceType] || maps.access;
      return map[type] || `未知(${type})`;
    },
    handleSearchAddCondition() {
      console.log("查询");
      this.searchVisible = true;
    },
    handleSearchEventSubmit(eventData) {
      this.searchCondition = eventData;
      this.searchVisible = false;
      this.handleSearch();
    },
    handleSearchEventCancel() {
      this.searchVisible = false;
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
      // this.searchUserInfo()
    },
    // 修改每页条数
    handleSizeChange(size) {
      this.pageSize = size;
      this.currentPage = 1; // 切换条数后回到第一页
      // this.searchUserInfo()
    },
    // 获取设备列表
    async getDeviceInfo() {
      try {
        this.loading = true;
        const data = {
          deviceType: "peopleDoor",
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
          console.log("设备列表:", this.devices);
          if (this.devices.length > 0) {
            this.selectedDeviceId = this.devices[0].id;
            console.log("选中的设备ID:", this.selectedDeviceId);
            // 获取该设备的信息
            // this.handleSearch()
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

<style scoped>
.car-event-page {
  display: flex;
  padding: 10px;
}
.query-panel {
  width: 280px;
  padding: 10px;
  border-right: 1px solid #ddd;
}
.table-panel {
  flex: 1;
  padding: 10px;
}
.quick-btns {
  display: flex;
  gap: 6px;
  margin: 6px 0;
}
.pagination {
  margin-top: 10px;
  text-align: right;
}
</style>
