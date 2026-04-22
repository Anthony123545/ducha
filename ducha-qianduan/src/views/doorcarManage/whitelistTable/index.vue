<template>
  <div style="padding: 10px">
    <el-tabs
      v-model="chooseDevice"
      type="card"
      @tab-click="handleTabClickDevice"
      style="height: 100%; width: 100%; color: #ea0808"
    >
      <el-tab-pane
        v-for="device in devices"
        :key="device.id"
        :label="device.name"
        :name="String(device.id)"
        style="height: 100%; width: 100%; color: #dc0909"
      >
        <el-tabs
          v-model="chooseVideo"
          type="card"
          @tab-click="handleTabClickVideo"
          style="height: 100%; width: 100%; color: #ea0808"
        >
          <el-tab-pane
            v-for="video in device.videosJson"
            :key="video.videoId"
            :label="video.videoName"
            :name="String(video.videoId)"
            style="height: 100%; width: 100%; color: #dc0909"
          >
            <!-- 搜索和操作 -->
            <div
              style="
                height: 50px;
                display: flex;
                align-items: center;
                gap: 10px;
              "
            >
              <!-- 选择设备 -->
              <el-select
                v-model="selectedDeviceId"
                placeholder="选择设备"
                size="small"
                style="width: 150px"
                clearable
                @change="handleDeviceChange"
              >
                <el-option
                  v-for="device in devices"
                  :key="device.id"
                  :label="device.name"
                  :value="device.id"
                />
              </el-select>
              <!-- 选择名单类型 -->
              <el-select
                v-model="selectedType"
                placeholder="选择名单类型"
                size="small"
                style="width: 150px"
                clearable
                @change="handleTypeChange"
              >
                <el-option
                  v-for="(label, value) in listTypeMap"
                  :key="value"
                  :label="label"
                  :value="parseInt(value)"
                />
              </el-select>
              <!-- 输入车牌号 -->
              <el-input
                v-model="inputLicensePlate"
                placeholder="输入车牌号"
                size="small"
                style="width: 150px"
                clearable
                @keyup.enter.native="handleSearch"
              />
              <el-button
                type="primary"
                size="small"
                @click="handleSearch"
                style="margin-left: 10px"
                >查询</el-button
              >
              <el-button type="primary" size="small" @click="handleAdd"
                >新增</el-button
              >
              <el-button
                :disabled="multipleSelection.length === 0"
                type="danger"
                size="small"
                @click="handleBatchDelete"
                >批量删除</el-button
              >
              <el-button
                type="danger"
                size="small"
                @click="reconnectDevice(video)"
              >
                重新连接
              </el-button>
            </div>
            <!-- 表格 -->
            <el-table
              :data="tableData"
              style="width: 100%; margin-top: 10px"
              border
              :header-cell-style="changeHeaderCellStyle"
              @selection-change="handleSelectionChange"
              v-loading="loading"
              element-loading-text="加载中..."
            >
              <el-table-column type="selection" align="center" width="60" />
              <el-table-column
                label="序号"
                align="center"
                min-width="60"
                sortable
              >
                <template #default="scope">
                  {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
                </template>
              </el-table-column>
              <el-table-column
                prop="deviceId"
                label="设备ID"
                align="center"
                min-width="100"
                sortable
              />
              <el-table-column
                prop="licensePlate"
                label="车牌号"
                align="center"
                min-width="120"
                sortable
              />
              <el-table-column label="所属人员" align="center" min-width="100">
                <template slot-scope="scope">
                  <span>{{ scope.row.personName || "--" }}</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="type"
                label="名单类型"
                :formatter="formatListType"
                align="center"
                min-width="100"
                sortable
              />
              <el-table-column
                prop="accessStartTime"
                label="开始时间"
                :formatter="formatTime"
                align="center"
                min-width="150"
                sortable
              />
              <el-table-column
                prop="accessEndTime"
                label="结束时间"
                :formatter="formatTime"
                align="center"
                min-width="150"
                sortable
              />
              <el-table-column
                prop="plateType"
                label="车牌类型"
                align="center"
                min-width="150"
                :formatter="formatPlateType"
                sortable
              />
              <el-table-column
                prop="plateColor"
                label="车牌颜色"
                align="center"
                min-width="100"
                :formatter="formatPlateColor"
                sortable
              />
              <el-table-column
                prop="channel"
                label="通道号"
                align="center"
                min-width="80"
                sortable
              />
              <!--              <el-table-column-->
              <!--                prop="cardNo"-->
              <!--                label="卡号"-->
              <!--                align="center"-->
              <!--                min-width="120"-->
              <!--                sortable-->
              <!--              />-->
              <el-table-column
                label="操作"
                fixed="right"
                align="center"
                min-width="120"
              >
                <template #default="{ row }">
                  <el-button
                    type="primary"
                    size="small"
                    @click="handleEdit(row)"
                    >编辑</el-button
                  >
                  <el-button
                    type="danger"
                    size="small"
                    @click="handleDelete(row)"
                    >删除</el-button
                  >
                </template>
              </el-table-column>
            </el-table>

            <!-- 分页 -->
            <div style="margin-top: 15px; text-align: right">
              <el-pagination
                background
                :current-page="currentPage"
                :page-size="pageSize"
                :total="total"
                :page-sizes="[10, 15, 20, 25, 30]"
                layout="total, prev, pager, next, jumper, sizes"
                @current-change="handlePageChange"
                @size-change="handleSizeChange"
              />
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-tab-pane>
    </el-tabs>
    <!-- 新增/编辑弹窗 -->
    <NameListEditDialog
      v-if="dialogVisible"
      :visible.sync="dialogVisible"
      :name-list-data="currentNameListData"
      @submit="handleNameListSubmit"
      @cancel="handleNameListCancel"
    />
  </div>
</template>

<script>
import request from "@/utils/request";
import dayjs from "dayjs";
import NameListEditDialog from "./NameListEditDialog.vue";
import {
  listTypeMap,
  plateColorMap,
  plateTypeMap,
  formatByMap,
} from "./enums.js";

export default {
  name: "NameListManagement",
  components: {
    NameListEditDialog,
  },
  data() {
    return {
      // 下拉数据
      chooseDevice: "",
      chooseVideo: "",
      devices: [],
      listTypeMap,
      plateColorMap,
      plateTypeMap,
      // 搜索条件
      selectedDeviceId: "",
      selectedType: null,
      inputLicensePlate: "",
      // 表格数据
      tableData: [],
      loading: false,
      total: 0,
      pageSize: 10,
      currentPage: 1,
      // 多选
      multipleSelection: [],
      // 弹窗
      dialogVisible: false,
      currentNameListData: {},
      privateCarList: [], // 缓存私家车对照表（车牌 + 人名）
      currentSearchID: "0",
    };
  },
  mounted() {
    this.getDeviceInfo();
    this.getPrivateCarList(); // 初始化对照表缓存
  },
  methods: {
    // 获取私家车对照表
    async getPrivateCarList() {
      try {
        // 这里的 URL 使用你提供的私家车 Controller 对应的路径
        const res = await request({
          url: "/militaryvehicleManagement/privateCar/list",
          method: "get",
          params: { pageSize: 100000 }, // 尽量一次性拿完，避免分页导致查不到人名
        });
        // 这里的 rows 是若依/标准 RuoYi 后端返回的通用格式
        this.privateCarList = res.rows || [];
        console.log(
          "私家车对照表加载成功，共",
          this.privateCarList.length,
          "条",
        );
      } catch (error) {
        console.error("加载私家车对照表失败", error);
      }
    },
    handleTabClickDevice(tab) {
      console.log("切换到设备：", tab.name);
      const device = this.devices.find((d) => d.id === Number(tab.name));
      console.log("选中的设备对象：", device);
    },
    handleTabClickVideo(tab) {
      console.log("切换到视频：", tab.name);
      this.selectedVideoId = tab.name;
      this.handleSearch();
    },
    formatPlateColor(row) {
      return formatByMap(plateColorMap, row.plateColor);
    },
    formatPlateType(row) {
      return formatByMap(plateTypeMap, row.plateType);
    },
    formatListType(row) {
      return formatByMap(listTypeMap, row.type);
    },
    handleDeviceChange(value) {
      if (value === "") {
        this.selectedDeviceId = null;
      }
      this.currentPage = 1;
      this.fetchData();
    },
    handleTypeChange(value) {
      if (value === "") {
        this.selectedType = null;
      }
      this.currentPage = 1; // 重置分页
      this.fetchData();
    },
    // 查询按钮
    handleSearch() {
      this.currentPage = 1;
      this.currentSearchID = "0";
      this.fetchData();
    },
    // 新增名单
    handleAdd() {
      this.currentNameListData = {}; // 清空
      this.dialogVisible = true;
    },
    // 编辑名单
    handleEdit(row) {
      this.currentNameListData = JSON.parse(JSON.stringify(row)); // 深拷贝
      this.dialogVisible = true;
    },
    // 获取名单列表
    async fetchData(retryTimes = 0) {
      this.loading = true;
      try {
        const data = {
          deviceId: this.selectedDeviceId,
          videoId: this.chooseVideo,
          type: this.selectedType,
          licensePlate: this.inputLicensePlate,
          searchID: this.currentSearchID,
          searchResultPosition: (this.currentPage - 1) * this.pageSize,
          maxResults: this.pageSize,
        };

        const res = await request({
          url: "/nameList/list",
          method: "POST",
          data: data,
        });
        // 判断是否报错（假设后端返回 status: false）
        // 如果后端返回错误且重试次数小于10
        if ((!res || res.status === false) && retryTimes < 10) {
          console.warn(`第 ${retryTimes + 1} 次查询失败，正在尝试重试...`);
          // 延迟 500ms 后重试，给设备喘息时间
          await new Promise((resolve) => setTimeout(resolve, 500));
          return this.fetchData(retryTimes + 1);
        }
        if (res && res.nameLists) {
          if (res.searchID) {
            this.currentSearchID = res.searchID;
          }
          // --- 关键匹配逻辑 ---
          this.tableData = res.nameLists.map((item) => {
            // 从缓存的 privateCarList 中寻找
            // 闸机字段: licensePlate  私家车字段: vehiclenumber
            const matchedCar = this.privateCarList.find(
              (car) =>
                car.vehiclenumber &&
                car.vehiclenumber.trim() === item.licensePlate.trim(),
            );

            return {
              ...item,
              // 匹配到了就显示 peopleName，没匹配到就显示空
              personName: matchedCar ? matchedCar.peopleName : "",
            };
          });
          // --- 匹配结束 ---

          this.total = Number(res?.searchInfo?.totalMatches) || 0;
        } else {
          this.tableData = [];
          this.total = 0;
          if (retryTimes >= 10) {
            this.$message.error(res.message || "设备多次连接失败，请检查网络");
          }
        }
      } catch (e) {
        if (retryTimes < 10) {
          await new Promise((resolve) => setTimeout(resolve, 500));
          return this.fetchData(retryTimes + 1);
        }
        this.tableData = [];
        this.$message.error("获取名单列表失败");
      } finally {
        this.loading = false;
      }
    },
    async handleNameListSubmit(formData) {
      const isEdit = formData.isEdit; // 从子组件传来的 isEdit
      const url = isEdit ? "/nameList/update" : "/nameList/add";
      console.log("提交名单数据：", formData);

      try {
        const res = await request({
          url,
          method: "POST",
          data: {
            deviceIds: formData.deviceIds,
            videoId: this.chooseVideo,
            nameListData: formData,
          },
        });
        console.log("保存结果：", res);
        if (res && res.status) {
          this.$message.success(res.message || "保存成功");
          this.dialogVisible = false;
        } else {
          this.$message.error(res.message || "保存失败");
        }
      } catch (error) {
        console.error("保存名单失败：", error);
        this.$message.error("保存失败，请检查网络或接口");
      } finally {
        this.loading = false;
      }
    },
    handleNameListCancel() {
      this.dialogVisible = false;
    },
    // 删除名单（单条或批量统一）
    handleDelete(row) {
      this.$confirm(
        `确定要删除车牌号为【${row.licensePlate}】的名单吗？`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        },
      )
        .then(() => this.deleteIds(row))
        .catch(() => this.$message.info("已取消删除"));
    },
    handleBatchDelete() {
      const ids = this.multipleSelection.map((i) => i.id);
      if (!ids.length) {
        this.$message.warning("请先选择要删除的名单");
        return;
      }
      this.$confirm(
        `确定要删除选中的 ${ids.length} 条名单吗？`,
        "批量删除确认",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        },
      )
        .then(() => this.deleteIds(ids))
        .catch(() => this.$message.info("已取消删除"));
    },
    // 通用删除方法
    async deleteIds(row) {
      console.log("111111");
      console.log("row:", row);
      const deletedata = Array.isArray(row) ? row : [row];
      if (deletedata.length === 0) return;
      try {
        console.log("22222");
        console.log("videoId:", this.selectedVideoId);
        const res = await request({
          url: "/nameList/delete",
          method: "POST",
          data: {
            list: deletedata,
            deviceId: this.selectedDeviceId,
            videoId: this.chooseVideo,
          }, // 注意：统一传 ids 数组
        });

        if (res && res.status) {
          this.$message.success(res.message);
          this.fetchData();
          this.multipleSelection = [];
        } else {
          this.$message.error(res.message);
        }
      } catch (e) {
        console.error("删除失败", e);
        this.$message.error("请求出错，请稍后重试");
      }
    },
    // 重连设备
    reconnectDevice(video) {
      console.log("🔄 重新连接设备：", video);
      const data = {
        videoId: this.selectedVideoId,
      };
      console.log("连接设备参数：", data);
      video.loading = true; // 设置 loading
      request({
        url: "/controlCarCamera/login",
        method: "POST",
        data: data,
      })
        .then((res) => {
          console.log("连接设备结果：", res);
          if (res && res.status) {
            this.$message.success(
              `设备 ${video.videoName} 连接成功 ${res.message ? "：" + res.message : ""}`,
            );
          } else {
            this.$message.error(
              `设备 ${video.videoName} 连接失败 ${res.message ? "：" + res.message : ""}`,
            );
          }
        })
        .catch((err) => {
          console.error("连接设备失败:", err);
          this.$message.error(`设备 ${videoName.name} 连接失败`);
        })
        .finally(() => {
          video.loading = false; // ✅ 修正这里
        });
    },
    // 表格样式
    changeHeaderCellStyle() {
      return "background: #004279; color: #fff;";
    },
    // 分页控制
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handlePageChange(page) {
      this.currentPage = page;
      this.fetchData();
    },
    handleSizeChange(size) {
      this.pageSize = size;
      this.currentPage = 1;
      this.fetchData();
    },
    // 时间格式化
    formatTime(row, column, cellValue) {
      return cellValue ? dayjs(cellValue).format("YYYY-MM-DD HH:mm:ss") : "";
    },
    // 获取设备列表
    async getDeviceInfo() {
      try {
        this.loading = true;
        const data = {
          deviceName: this.searchDeviceName?.trim() || null,
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
        if (res && res.status === true) {
          // 确保列表为数组
          this.devices = Array.isArray(res.list)
            ? res.list.map((device) => {
                let videos = [];
                try {
                  videos =
                    typeof device.videosJson === "string"
                      ? JSON.parse(device.videosJson || "[]")
                      : device.videosJson;
                  videos = Array.isArray(videos) ? videos : [];
                } catch (err) {
                  console.error("解析 videosJson 失败", err);
                  videos = [];
                }
                return {
                  ...device,
                  videosJson: videos,
                };
              })
            : [];
          this.total = typeof res.total === "number" ? res.total : 0;
          this.$message.success(res.message || "获取设备列表成功");
          if (this.devices.length > 0) {
            this.selectedDeviceId = this.devices[0].id;
            this.chooseDevice = String(this.selectedDeviceId);
            // 选择第一个设备的第一个视频
            this.chooseVideo = this.devices[0].videosJson[0]
              ? String(this.devices[0].videosJson[0].videoId)
              : null;
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
.nameList-edit-dialog .el-form-item {
  margin-bottom: 18px;
}
</style>
