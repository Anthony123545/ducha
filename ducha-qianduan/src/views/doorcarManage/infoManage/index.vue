<template>
  <div style="height: 100%; width: 100%">
    <!--      &lt;!&ndash; 左侧内容 &ndash;&gt;-->
    <!--      <div :span="12" class="left-col">-->
    <!-- 设备信息 -->
    <!--        <el-card class="flex-card device-card" shadow="hover">-->
    <!--          <div slot="header">-->
    <!--            <span>设备信息</span>-->
    <!--          </div>-->
    <!--          &lt;!&ndash; 加载中 &ndash;&gt;-->
    <!--          <el-skeleton v-if="deviceLoading" :rows="3" animated />-->
    <!--          &lt;!&ndash; 无设备时 &ndash;&gt;-->
    <!--          <el-empty-->
    <!--            v-else-if="devices.length === 0"-->
    <!--            description="暂无设备，请检查设备连接或配置"-->
    <!--          />-->
    <!-- 有设备时 -->
    <el-tabs
      v-model="chooseDevice"
      type="card"
      @tab-click="handleTabClick"
      style="height: 100%; width: 100%; color: #ea0808"
    >
      <el-tab-pane
        v-for="device in devices"
        :key="device.id"
        :label="device.name"
        :name="String(device.id)"
        style="height: 100%; width: 100%; color: #dc0909"
      >
        <div style="display: flex; height: 100%; width: 100%">
          <div style="flex: 0.4; height: 100%">
            <el-card>
              <div
                style="
                  height: 150px;
                  display: flex;
                  align-items: center;
                  margin-bottom: 10px;
                "
              >
                <!-- 左侧设备信息 -->
                <el-col
                  :span="14"
                  style="
                    height: 100%;
                    display: flex;
                    align-items: center;
                    padding: 0 10px;
                  "
                >
                  <div
                    style="
                      width: 100%;
                      display: flex;
                      flex-direction: column;
                      justify-content: center;
                      height: 100%;
                    "
                  >
                    <p>
                      <strong>设备位置：</strong
                      >{{ device.location || "未配置" }}
                    </p>
                    <p><strong>设备 IP：</strong>{{ device.ip || "-" }}</p>
                    <p><strong>设备端口：</strong>{{ device.port || "-" }}</p>
                  </div>
                </el-col>
                <!-- 中间状态 -->
                <el-col
                  :span="6"
                  style="
                    height: 100%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                  "
                >
                  <div
                    style="
                      height: 100%;
                      display: flex;
                      align-items: center;
                      justify-content: center;
                      width: 100%;
                    "
                  >
                    <el-result
                      :icon="'success'"
                      :title="'运行正常'"
                      style="width: 100%"
                    />
<!--                    <el-result-->
<!--                      :icon="device.isConnect ? 'success' : 'error'"-->
<!--                      :title="device.isConnect ? '运行正常' : '连接失败'"-->
<!--                      style="width: 100%"-->
<!--                    />-->
                  </div>
                </el-col>
                <!-- 右侧按钮 -->
                <el-col
                  :span="4"
                  style="
                    height: 100%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                  "
                >
                  <el-button
                    type="danger"
                    size="mini"
                    @click="reconnectDevice(device)"
                  >
                    重新连接
                  </el-button>
                </el-col>
              </div>
              <!-- 视频流 -->
              <el-divider
                v-if="
                  device && device.videosInfo && device.videosInfo.length > 0
                "
              ></el-divider>
              <div
                v-if="
                  device && device.videosInfo && device.videosInfo.length > 0
                "
                style="
                  height: 40%;
                  display: flex;
                  gap: 10px;
                  margin-bottom: 10px;
                  align-items: center;
                "
              >
                <!-- 左箭头 -->
                <el-image
                  v-if="device.videosInfo.length > 2"
                  style="
                    width: 25px;
                    height: 25px;
                    margin-left: 5px;
                    flex-shrink: 0;
                  "
                  :style="{
                    cursor:
                      device.videoStartIndex <= 0 ? 'not-allowed' : 'pointer',
                    opacity: device.videoStartIndex <= 0 ? 0.4 : 1,
                  }"
                  @click="device.videoStartIndex > 0 && scrolVideolLeft(device)"
                  :src="'/resources/leftArrow.png'"
                  fit="contain"
                />
                <div
                  v-for="(video, index) in device.videosInfo.slice(
                    device.videoStartIndex,
                    device.videoStartIndex + 2,
                  )"
                  @click="handleVideoClick(device, video, index)"
                  :key="video.id"
                  class="video-box"
                >
                  <div class="video-info">
                    <div class="info-text">
                      <div><strong>名称:</strong> {{ video.videoName }}</div>
                    </div>
                    <div class="video-play">
                      <video
                        :ref="
                          'video' +
                          device.id +
                          '-' +
                          (device.videoStartIndex + index)
                        "
                        width="100%"
                        height="100%"
                        controls
                        autoplay
                        muted
                        style="
                          width: 100%;
                          height: 100%;
                          background-color: black;
                        "
                      >
                        您的浏览器不支持视频播放。
                      </video>
                    </div>
                  </div>
                  <div class="video-name">{{ video.videoName }}</div>
                </div>
                <!-- 右箭头 -->
                <el-image
                  v-if="device.videosInfo.length > 2"
                  style="
                    width: 25px;
                    height: 25px;
                    margin-right: 5px;
                    flex-shrink: 0;
                  "
                  :style="{
                    cursor:
                      device.videoStartIndex + 2 >= device.videosInfo.length
                        ? 'not-allowed'
                        : 'pointer',
                    opacity:
                      device.videoStartIndex + 2 >= device.videosInfo.length
                        ? 0.4
                        : 1,
                  }"
                  @click="
                    device.videoStartIndex + 2 < device.videosInfo.length &&
                    scrollVideoRight(device)
                  "
                  :src="'/resources/rightArrow.png'"
                  fit="contain"
                />
              </div>
              <!-- 当前窗口指示点 -->
              <div
                v-if="device.videosInfo.length > 2"
                style="text-align: center; margin-top: 5px"
              >
                <span
                  v-for="(video, idx) in device.videosInfo.length - 1"
                  :key="idx"
                  :style="{
                    display: 'inline-block',
                    width: '8px',
                    height: '8px',
                    borderRadius: '50%',
                    margin: '0 3px',
                    backgroundColor:
                      device.videoStartIndex === idx ? '#409EFF' : '#ccc',
                    cursor: 'pointer',
                  }"
                  @click="
                    device.videoStartIndex = idx;
                    handleWindowChange(device, device.videoStartIndex);
                  "
                ></span>
              </div>
              <!-- 门 -->
              <el-divider
                v-if="device && device.doorsInfo && device.doorsInfo.length > 0"
              ></el-divider>
              <div
                v-if="device && device.doorsInfo && device.doorsInfo.length > 0"
                style="
                  height: 40%;
                  display: flex;
                  gap: 10px;
                  margin-bottom: 10px;
                  align-items: center;
                "
              >
                <!-- 左箭头 -->
                <el-image
                  v-if="device.doorsInfo.length > 2"
                  style="
                    width: 25px;
                    height: 25px;
                    margin-left: 5px;
                    flex-shrink: 0;
                  "
                  :style="{
                    cursor:
                      device.doorStartIndex <= 0 ? 'not-allowed' : 'pointer',
                    opacity: device.doorStartIndex <= 0 ? 0.4 : 1,
                  }"
                  @click="device.doorStartIndex > 0 && scrolDoorlLeft(device)"
                  :src="'/resources/leftArrow.png'"
                  fit="contain"
                />
                <div
                  v-for="(door, index) in device.doorsInfo.slice(
                    device.doorStartIndex,
                    device.doorStartIndex + 2,
                  )"
                  :key="door.id"
                  class="door-box"
                >
                  <div style="display: flex; width: 100%; gap: 10px">
                    <!-- 左侧：门图标和名称 (60%宽度) -->
                    <div
                      style="
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        justify-content: center;
                        width: 60%;
                      "
                    >
                      <div
                        class="door-label"
                        style="font-weight: 600; margin-bottom: 5px"
                      >
                        门： {{ door.doorName }}
                      </div>
                      <img
                        src="/resources/door.png"
                        class="stat-icon"
                        style="width: 150px; height: 150px; object-fit: contain"
                      />
                    </div>
                    <!-- 右侧：控制按钮 (40%宽度) -->
                    <div
                      style="
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                        align-items: center;
                        width: 40%;
                        gap: 8px;
                        padding: 10px;
                      "
                    >
                      <el-button
                        size="mini"
                        type="primary"
                        @click="toggleDoor(device, door, 0)"
                        style="width: 80px; margin: 0 auto; display: block"
                      >
                        关门
                      </el-button>
                      <el-button
                        size="mini"
                        type="primary"
                        @click="toggleDoor(device, door, 1)"
                        style="width: 80px; margin: 0 auto; display: block"
                      >
                        开门
                      </el-button>
                      <!--                      <el-button-->
                      <!--                        size="mini"-->
                      <!--                        type="primary"-->
                      <!--                        @click="toggleDoor(device, door, 2)"-->
                      <!--                        style="width: 80px; margin: 0 auto; display: block"-->
                      <!--                      >-->
                      <!--                        常开-->
                      <!--                      </el-button>-->
                      <!--                      <el-button-->
                      <!--                        size="mini"-->
                      <!--                        type="primary"-->
                      <!--                        @click="toggleDoor(device, door, 3)"-->
                      <!--                        style="width: 80px; margin: 0 auto; display: block"-->
                      <!--                      >-->
                      <!--                        常关-->
                      <!--                      </el-button>-->
                    </div>
                  </div>
                </div>
                <!-- 右箭头 -->
                <el-image
                  v-if="device.doorsInfo.length > 2"
                  style="
                    width: 25px;
                    height: 25px;
                    margin-right: 5px;
                    flex-shrink: 0;
                  "
                  :style="{
                    cursor:
                      device.doorStartIndex + 2 >= device.doorsInfo.length
                        ? 'not-allowed'
                        : 'pointer',
                    opacity:
                      device.doorStartIndex + 2 >= device.doorsInfo.length
                        ? 0.4
                        : 1,
                  }"
                  @click="
                    device.doorStartIndex + 2 < device.doorsInfo.length &&
                    scrollDoorRight(device)
                  "
                  :src="'/resources/rightArrow.png'"
                  fit="contain"
                />
              </div>
              <!-- 当前窗口指示点 -->
              <div
                v-if="device.doorsInfo.length > 2"
                style="text-align: center; margin-top: 5px"
              >
                <span
                  v-for="(door, idx) in device.doorsInfo.length - 1"
                  :key="idx"
                  :style="{
                    display: 'inline-block',
                    width: '8px',
                    height: '8px',
                    borderRadius: '50%',
                    margin: '0 3px',
                    backgroundColor:
                      device.doorStartIndex === idx ? '#409EFF' : '#ccc',
                    cursor: 'pointer',
                  }"
                  @click="
                    device.doorStartIndex = idx;
                    handleWindowChange(device, device.doorStartIndex);
                  "
                ></span>
              </div>
              <!-- 统计信息-->
              <el-divider></el-divider>
              <div class="stats-module">
<!--                <el-row :gutter="20">-->
                  <!-- 车辆信息 -->
<!--                  <el-col :span="12">-->
<!--                    <div class="overview-item">-->
<!--                      <div class="overview-stats">-->
<!--                        <div class="stat-item">-->
<!--                          <img src="/resources/car.png" class="stat-icon" />-->
<!--                          <div class="stat-text">-->
<!--                            <p class="stat-label">-->
<!--                              <strong>车辆总数：</strong>-->
<!--                            </p>-->
<!--                            <p class="stat-value">{{ device.carCount }}</p>-->
<!--                          </div>-->
<!--                        </div>-->
<!--                        <div class="stat-item">-->
<!--                          <img src="/resources/car.png" class="stat-icon" />-->
<!--                          <div class="stat-text">-->
<!--                            <p class="stat-label">-->
<!--                              <strong>营区内车辆数：</strong>-->
<!--                            </p>-->
<!--                            <p class="stat-value">-->
<!--                              {{ device.carInHomeCount }}-->
<!--                            </p>-->
<!--                          </div>-->
<!--                        </div>-->
<!--                        <div class="stat-item">-->
<!--                          <img src="/resources/car.png" class="stat-icon" />-->
<!--                          <div class="stat-text">-->
<!--                            <p class="stat-label">-->
<!--                              <strong>营区外车辆数：</strong>-->
<!--                            </p>-->
<!--                            <p class="stat-value">-->
<!--                              {{ device.carOutHomeCount }}-->
<!--                            </p>-->
<!--                          </div>-->
<!--                        </div>-->
<!--                      </div>-->
<!--                      <div :ref="'chart1-' + device.id" class="chart-box"></div>-->
<!--                    </div>-->
<!--                  </el-col>-->
                  <!-- 卡片信息 -->
<!--                  <el-col :span="12">-->
<!--                    <div class="overview-item">-->
<!--                      <div class="overview-stats">-->
<!--                        &lt;!&ndash; 占位，不显示 &ndash;&gt;-->
<!--                        <div class="stat-item" style="visibility: hidden">-->
<!--                          <img src="/resources/car.png" class="stat-icon" />-->
<!--                          <div class="stat-text">-->
<!--                            <p class="stat-label">-->
<!--                              <strong>车辆总数：</strong>-->
<!--                            </p>-->
<!--                            <p class="stat-value">{{ device.carCount }}</p>-->
<!--                          </div>-->
<!--                        </div>-->
<!--                        <div class="stat-item">-->
<!--                          <img src="/resources/car.png" class="stat-icon" />-->
<!--                          <div class="stat-text">-->
<!--                            <p class="stat-label">-->
<!--                              <strong>车辆白名单数：</strong>-->
<!--                            </p>-->
<!--                            <p class="stat-value">-->
<!--                              {{ device.whiteNameListCount }}-->
<!--                            </p>-->
<!--                          </div>-->
<!--                        </div>-->
<!--                        <div class="stat-item">-->
<!--                          <img src="/resources/car.png" class="stat-icon" />-->
<!--                          <div class="stat-text">-->
<!--                            <p class="stat-label">-->
<!--                              <strong>车辆白黑单数：</strong>-->
<!--                            </p>-->
<!--                            <p class="stat-value">-->
<!--                              {{ device.blackNameListCount }}-->
<!--                            </p>-->
<!--                          </div>-->
<!--                        </div>-->
<!--                      </div>-->
<!--                      <div :ref="'chart2-' + device.id" class="chart-box"></div>-->
<!--                    </div>-->
<!--                  </el-col>-->
<!--                </el-row>-->
              </div>
            </el-card>
          </div>
          <div style="flex: 0.6; height: 100%">
            <el-card
              style="height: 100%; display: flex; flex-direction: column"
            >
              <div
                style="
                  display: flex;
                  justify-content: space-between;
                  align-items: center;
                  margin-bottom: 10px;
                "
              >
                <div style="float: left">实时事件</div>
                <div
                  style="
                    float: right;
                    display: flex;
                    align-items: center;
                    gap: 10px;
                  "
                >
                  <span>{{ wsStatusText }}</span>
                  <div
                    class="indicator"
                    :class="{
                      connected: $store.getters.webSocketState === 'open',
                      connecting:
                        $store.getters.webSocketState === 'connecting',
                      error: $store.getters.webSocketState === 'error',
                      disconnected: $store.getters.webSocketState === 'closed',
                    }"
                  ></div>
                  <el-button
                    size="mini"
                    type="primary"
                    @click="reconnectWebSocket"
                    >重连</el-button
                  >
                </div>
              </div>
              <div style="flex: 1; overflow: auto">
                <!--                工号 姓名 卡号 门 时间-->
                <el-table
                  :data="device.eventData"
                  size="mini"
                  border
                  :header-cell-style="changeHeaderCellStyle"
                  :row-style="{ height: '30px' }"
                  style="width: 100%; height: 100%"
                >
                  <el-table-column
                    type="index"
                    label="序号"
                    width="50"
                    align="center"
                  />
                  <el-table-column
                    prop="alarmTime"
                    label="时间"
                    min-width="140"
                    align="center"
                  />
                  <el-table-column
                    prop="plateNo"
                    label="车牌号"
                    min-width="140"
                    align="center"
                  />
                  <el-table-column
                    prop="netUser"
                    label="操作用户"
                    min-width="60"
                    align="center"
                  />
                  <el-table-column
                    prop="操作IP"
                    label="IP"
                    min-width="80"
                    align="center"
                  />
                  <el-table-column
                    prop="iotChannelNo"
                    label="IOT通道号"
                    min-width="80"
                    align="center"
                  />

                  <el-table-column
                    prop="acsEventInfo.doorNo"
                    label="事件说明"
                    min-width="80"
                    align="center"
                  >
                    <template #default>
                      <span>车辆经过</span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="acsEventInfo.doorNo"
                    label="设备名称"
                    min-width="80"
                    align="center"
                  >
                    <template #default>
                      <span>车行闸道</span>
                    </template>
                  </el-table-column>

<!--                  <el-table-column label="主类型" min-width="50" align="center">-->
<!--                    <template #default="{ row }">-->
<!--                      {{ getMajorTypeText(row.majorType) }}-->
<!--                    </template>-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    label="次类型"-->
<!--                    min-width="120"-->
<!--                    align="center"-->
<!--                  >-->
<!--                    <template #default="{ row }">-->
<!--                      {{ getMinorTypeText(row.majorType, row.minorType) }}-->
<!--                    </template>-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    prop="netUser"-->
<!--                    label="用户"-->
<!--                    min-width="60"-->
<!--                    align="center"-->
<!--                  />-->
<!--                  <el-table-column-->
<!--                    prop="remoteIP"-->
<!--                    label="IP"-->
<!--                    min-width="80"-->
<!--                    align="center"-->
<!--                  />-->
<!--                  <el-table-column-->
<!--                    prop="iotChannelNo"-->
<!--                    label="IOT通道号"-->
<!--                    min-width="80"-->
<!--                    align="center"-->
<!--                  />-->
<!--                  <el-table-column-->
<!--                    prop="acsEventInfo.employeeNo"-->
<!--                    label="工号"-->
<!--                    min-width="80"-->
<!--                    align="center"-->
<!--                  />-->
<!--                  &lt;!&ndash;                  <el-table-column&ndash;&gt;-->
<!--                  &lt;!&ndash;                    prop="acsEventInfo.employeeNo"&ndash;&gt;-->
<!--                  &lt;!&ndash;                    label="姓名"&ndash;&gt;-->
<!--                  &lt;!&ndash;                    min-width="80"&ndash;&gt;-->
<!--                  &lt;!&ndash;                    align="center"&ndash;&gt;-->
<!--                  &lt;!&ndash;                  />&ndash;&gt;-->
<!--                  <el-table-column-->
<!--                    prop="acsEventInfo.cardNo"-->
<!--                    label="卡号"-->
<!--                    min-width="80"-->
<!--                    align="center"-->
<!--                  />-->
<!--                  <el-table-column-->
<!--                    prop="acsEventInfo.doorNo"-->
<!--                    label="门号"-->
<!--                    min-width="80"-->
<!--                    align="center"-->
<!--                  />-->
<!--                  <el-table-column-->
<!--                    prop="acsEventInfo.deviceNo"-->
<!--                    label="设备号"-->
<!--                    min-width="80"-->
<!--                    align="center"-->
<!--                  />-->
                </el-table>
              </div>
            </el-card>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import request from "@/utils/request";
import { minorTypeMap } from "./eventTypeMap";
import * as echarts from "echarts";
import _ from "lodash";
import flvjs from "flv.js";

export default {
  name: "Dashboard",
  data() {
    return {
      deviceLoading: false,
      chooseDevice: "",
      selectedDevice: null,
      devices: [],
      // 视频流
      screenVideos: [], // 每个元素代表一个格子的视频流地址，'' 表示未使用
      playerInstances: [], // 每个元素是对应格子的播放器实例（HLS或FLV）
      gridLoadingStatus: [], // 每个格子的加载状态
      gridErrorStatus: [], // 每个格子的错误状态
      // 事件信息
      eventsMaxlength: 15, // 显示多少条数据
      majorTypeMap: {
        1: "报警",
        2: "异常",
        3: "操作",
        5: "事件",
      },
    };
  },
  computed: {
    wsStatusText() {
      switch (this.$store.getters.webSocketState) {
        case "open":
          return "已连接";
        case "connecting":
          return "连接中";
        case "error":
          return "连接失败";
        default:
          return "未连接";
      }
    },
    wsStatusType() {
      switch (this.$store.getters.webSocketState) {
        case "open":
          return "success";
        case "closed":
          return "info";
        case "connecting":
          return "warning";
        case "error":
          return "danger";
      }
    },
  },
  mounted() {
    // 只调用一次 getDeviceInfo() 来获取设备并初始化图表
    this.getDeviceInfo();
    // 监听窗口变化和自定义事件
    window.addEventListener("resize", this.resizeChart);
    window.addEventListener("carGate", this.handleWsEvent);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.resizeChart);
    window.removeEventListener("carGate", this.handleWsEvent);
    this.cleanupAllplayerInstances();
    this.devices.forEach((d) => {
      // 销毁对象中的所有图表
      if (d.charts && typeof d.charts === "object") {
        Object.values(d.charts).forEach((chart) => {
          if (chart && typeof chart.dispose === "function") {
            chart.dispose();
          }
        });
        d.charts = {}; // 清空对象
      }
      // 兼容旧的单个chart
      if (d.chart) {
        d.chart.dispose();
        d.chart = null;
      }
    });
    // 销毁chartsMap中的图表
    if (this.chartsMap) {
      Object.values(this.chartsMap).forEach((chart) => {
        if (chart && typeof chart.dispose === "function") {
          chart.dispose();
        }
      });
      this.chartsMap = {};
    }
    // 停止定时器
    this.stopDeviceStatusPolling();
  },
  methods: {
    handleVideoClick(device, video, index) {
      const realIndex = device.videoStartIndex + index;
      const current = this.screenVideos[realIndex];
      if (!current) {
        // --- 当前无播放 → 开启推流 ---
        console.log("开启推流", realIndex);
        this.startStream(device.id, video.videoInfo, realIndex);
      } else {
        // --- 当前已有播放 → 终止推流 ---
        console.log("终止推流", realIndex);
        this.stopStream(device.id, video.videoInfo, false);
      }
    },
    // 滑动左右
    scrolVideolLeft: _.throttle(function (device) {
      if (device.videoStartIndex <= 0) return;
      const oldStart = device.videoStartIndex;
      device.videoStartIndex--; // 方法 A：立即更新状态
      this.handleWindowChange(device, oldStart);
    }, 200), // 方法 B：节流 200ms
    scrollVideoRight: _.throttle(function (device) {
      if (device.videoStartIndex + 2 >= device.videosInfo.length) return;
      const oldStart = device.videoStartIndex;
      device.videoStartIndex++; // 方法 A：立即更新状态
      this.handleWindowChange(device, oldStart);
    }, 200),
    scrolDoorlLeft: _.throttle(function (device) {
      if (device.doorStartIndex <= 0) return;
      const oldStart = device.doorStartIndex;
      device.doorStartIndex--; // 方法 A：立即更新状态
      // this.handleWindowChange(device, oldStart);
    }, 200), // 方法 B：节流 200ms
    scrollDoorRight: _.throttle(function (device) {
      if (device.doorStartIndex + 2 >= device.doorsInfo.length) return;
      const oldStart = device.doorStartIndex;
      device.doorStartIndex++; // 方法 A：立即更新状态
      // this.handleWindowChange(device, oldStart);
    }, 200),
    /* ----------- 工具方法 ----------- */
    getVideoEl(deviceId, index) {
      const key = `video${deviceId}-${index}`;
      const ref = this.$refs[key];
      if (!ref) return null;
      return Array.isArray(ref) ? ref[0] : ref;
    },
    destroyPlayer(deviceId, index) {
      // 清理 FLV 实例
      const flvPlayer = this.playerInstances[index];
      if (flvPlayer) {
        try {
          flvPlayer.pause();
          flvPlayer.off && flvPlayer.off(flvjs.Events.ERROR);
          flvPlayer.off && flvPlayer.off(flvjs.Events.METADATA_ARRIVED);
          flvPlayer.off && flvPlayer.off(flvjs.Events.STATISTICS_INFO);
          flvPlayer.unload();
          flvPlayer.detachMediaElement();
          flvPlayer.destroy();
        } catch (e) {
          console.warn(`FLV destroy error [${index}]`, e);
        }
      }
      this.playerInstances[index] = null;
      // 彻底清理 Video 标签
      if (deviceId != null) {
        const refKey = `video-${deviceId}-${index}`;
        let videoEl = this.$refs[refKey];
        if (Array.isArray(videoEl)) videoEl = videoEl[0];
        if (videoEl) {
          try {
            videoEl.pause();
            videoEl.removeAttribute("src");
            videoEl.srcObject = null;
            videoEl.load(); // 强制重置 MSE buffer
          } catch (e) {
            console.warn(`Video reset error [${index}]`, e);
          }
        }
      }
      // 清空状态
      this.screenVideos[index] = "";
      this.gridLoadingStatus[index] = null;
      this.gridErrorStatus[index] = null;
      if (this.flvErrorTimes) this.flvErrorTimes[index] = 0;
      console.log(`✔ 播放器已完全销毁 index=${index}`);
    },
    cleanupAllplayerInstances() {
      for (let i = 0; i < this.playerInstances.length; i++) {
        this.destroyPlayer(i);
      }
    },
    handleWindowChange(device, oldStart) {
      const newStart = device.videoStartIndex;
      const oldVisible = [oldStart, oldStart + 1, oldStart + 2];
      const newVisible = [newStart, newStart + 1, newStart + 2];
      // 1. 离开的窗口 => 停止推流（重点）
      oldVisible.forEach((index) => {
        if (!newVisible.includes(index)) {
          const video = device.videosInfo[index];
          if (video && video.videoInfo) {
            this.stopStream(device.id, video.videoInfo, false);
          }
          this.destroyPlayer(device.id, index);
        }
      });
      // 2. 新进入窗口 => 如果有内容，恢复播放
      newVisible.forEach((index) => {
        if (!oldVisible.includes(index)) {
          const url = this.screenVideos[index];
          if (url) {
            this.playVideoInGrid(device.id, index, url);
          }
        }
      });
    },
    /* ----------- 推流管理 ----------- */
    async playVideoInGrid(deviceId, index, url) {
      if (!url) return;
      // 清理旧实例
      this.destroyPlayer(deviceId, index);
      this.screenVideos[index] = url;
      const videoEl = this.getVideoEl(deviceId, index);
      if (!videoEl) {
        this.gridLoadingStatus[index] = false;
        this.gridErrorStatus[index] = "视频元素未找到";
        return;
      }
      if (url.endsWith(".flv") || url.startsWith("flv://")) {
        await this.playFlvInGrid(index, url, videoEl);
      } else {
        this.gridLoadingStatus[index] = false;
        this.gridErrorStatus[index] = "不支持的流类型";
      }
    },
    // FLV 播放
    async playFlvInGrid(index, flvUrl, videoEl) {
      if (!flvjs.isSupported()) {
        this.gridLoadingStatus[index] = false;
        this.gridErrorStatus[index] = "浏览器不支持 FLV 播放";
        return;
      }

      const flvPlayer = flvjs.createPlayer({
        type: "flv",
        url: flvUrl,
        isLive: true,
        hasVideo: true,
        hasAudio: false,
        lazyLoad: true,
        stashInitialSize: 512, // 初始缓冲，减小延迟
        lazyLoadMaxDuration: 10, // 最大缓冲 10s
        lazyLoadRecoverDuration: 5,
        autoCleanupMaxBackwardDuration: 5,
        autoCleanupSourceBuffer: true,
      });

      flvPlayer.attachMediaElement(videoEl);
      this.playerInstances[index] = flvPlayer;

      // 初始化错误计数
      if (!this.flvErrorTimes) this.flvErrorTimes = [];
      this.flvErrorTimes[index] = 0;

      // 绑定错误事件（避免重复绑定）
      flvPlayer.on(flvjs.Events.ERROR, () => {
        const times = ++this.flvErrorTimes[index];
        console.warn(`FLV 错误 [${index}]`, times);

        if (times <= 3) {
          this.destroyPlayer(index);
          setTimeout(() => this.playVideoInGrid(index, flvUrl), 800);
        } else {
          this.gridLoadingStatus[index] = false;
          this.gridErrorStatus[index] = "FLV 播放失败";
        }
      });

      // 加载并播放
      try {
        flvPlayer.load();
        await flvPlayer.play().catch(() => {});
      } catch (e) {
        console.error(`FLV 播放失败 [${index}]`, e);
      }
    },
    async startStream(deviceId, camera, realIndex) {
      console.log("开始推流:", camera);
      const row = camera;
      // 防止重复点击
      if (row.isStarting) return;
      try {
        // 设置启动中的状态
        this.$set(row, "isStarting", true);
        const res = await request({
          url: `/videobyzlm/start/${camera.id}`,
          method: "GET",
        });
        console.log("添加推流结果:", res);
        if (res && res.status) {
          this.$message.success(res.message || "推流任务已提交");
          // 后端返回的数据
          const data = res.data || {};
          // playUrl 可以先赋值给播放器，但不代表在线 // 保存播放地址
          this.$set(row, "playUrl", data.playUrl || "");
          // 在线状态 根据 online 字段判断是否真正上线
          this.$set(row, "isOnline", data.online === true);
          // 如果没上线，等待 3 秒后查询一次
          if (!data.online) {
            setTimeout(
              () => this.checkStreamStatus(deviceId, row.id, camera, realIndex),
              3000,
            );
          } else {
            // 已上线 → 直接播放
            await this.playStreamForCamera(deviceId, camera, realIndex);
          }
        } else {
          this.$message.warning(res.message || "推流启动失败");
        }
      } catch (e) {
        console.error("推流请求失败:", e);
        this.$message.error("推流请求失败，请检查网络或服务器状态！");
      } finally {
        // 重置状态
        this.$set(row, "isStarting", false);
      }
    },
    async checkStreamStatus(
      deviceId,
      id,
      camera,
      realIndex,
      attempts = 0,
      config = { maxAttempts: 5, interval: 1000 },
    ) {
      const row = camera.row;
      attempts++;
      try {
        const res = await request({
          url: `/videobyzlm/status/${id}`,
          method: "GET",
        });
        console.log(`第${attempts}次查询推流结果 [${id}]:`, res);
        // 后端返回格式
        const data = res.data || {};
        // 状态值
        const online = data.online === true;
        const playUrl = data.playUrl;
        if (online && playUrl) {
          this.$set(row, "isOnline", true);
          this.$set(row, "playUrl", playUrl);

          this.$message.success("推流已上线");
          await this.playStreamForCamera(deviceId, camera, realIndex);
          return true;
        }
        if (attempts >= config.maxAttempts) {
          this.$set(row, "isOnline", false);
          this.$message.warning("推流未上线，请稍后重试");
          return false;
        }
        // 未上线 → 等待并继续查
        await new Promise((resolve) => setTimeout(resolve, config.interval));
        return this.checkStreamStatus(id, camera, attempts, config);
      } catch (e) {
        console.error(`检查推流状态失败 [${id}]:`, e);
        if (attempts < config.maxAttempts) {
          await new Promise((resolve) => setTimeout(resolve, config.interval));
          return this.checkStreamStatus(id, camera, attempts, config);
        } else {
          this.$set(row, "isOnline", false);
          this.$message.error("查询推流状态失败，请重试");
          return false;
        }
      }
    },
    async playStreamForCamera(deviceId, camera, realIndex) {
      console.log("播放流：", camera);
      if (!camera?.playUrl) {
        this.$message.warning("请先启动推流！");
        return;
      }
      await this.playVideoInGrid(deviceId, realIndex, camera.playUrl);
      camera.windowIndex = realIndex;
    },
    async stopStream(deviceId, camera, showMsg = true) {
      const row = camera.row || camera;
      if (!row || !row.id) return;
      // 防止重复停止
      if (row.isStopping) return;
      this.$set(row, "isStopping", true);
      try {
        const res = await request({
          url: `/videobyzlm/stop/${row.id}`,
          method: "GET",
        });
        console.log("停止推流结果:", res);
        if (res && res.status) {
          // 1️⃣ 销毁播放器并清理 Video 标签
          if (row.windowIndex != null) {
            this.destroyPlayer(deviceId, row.windowIndex);
            this.$set(row, "windowIndex", null);
          }
          // 2️⃣ 重置状态字段
          this.$set(row, "isPlaying", false);
          this.$set(row, "isOnline", false);
          this.$set(row, "playUrl", null);
          if (showMsg) this.$message.success(res.message || "已停止推流");
        } else {
          if (showMsg) this.$message.warning(res.message || "停止推流失败");
        }
      } catch (err) {
        console.error(`停止推流时出错 [${row.id}]:`, err);
        if (showMsg) this.$message.error("停止推流失败，请重试");
      } finally {
        this.$set(row, "isStopping", false);
      }
    },
    getDeviceInfo() {
      this.deviceLoading = true;
      const data = {
        deviceType: "carDoor",
        enable: true,
        paginate: false,
      };
      // console.log("获取设备列表参数：", data);
      return request({
        url: "/device/list",
        method: "POST",
        data: data,
      })
        .then((res) => {
          // console.log("获取设备列表结果：", res);
          if (res && res.status === true) {
            // ✅ 确保是数组
            this.devices = Array.isArray(res.list) ? res.list : [];
            // ✅ 对每个设备进行 JSON 解析
            this.devices.forEach((device) => {
              try {
                if (typeof device.videosJson === "string") {
                  device.videosInfo = JSON.parse(device.videosJson);
                }
              } catch (e) {
                console.warn("解析 videosJson 出错：", device.videosJson, e);
                device.videosInfo = [];
              }
              try {
                if (typeof device.doorsJson === "string") {
                  device.doorsInfo = JSON.parse(device.doorsJson);
                }
              } catch (e) {
                console.warn("解析 doorsJson 出错：", device.doorsJson, e);
                device.doorsInfo = [];
              }
            });
            // ✅ 初始化 videoStartIndex，保证响应式
            this.devices.forEach((device) => {
              if (!device.hasOwnProperty("videoStartIndex")) {
                this.$set(device, "videoStartIndex", 0);
              }
            });
            // ✅ 初始化 doorStartIndex，保证响应式
            this.devices.forEach((device) => {
              if (!device.hasOwnProperty("doorStartIndex")) {
                this.$set(device, "doorStartIndex", 0);
              }
            });
            this.total = typeof res.total === "number" ? res.total : 0;
            // 获取设备列表后，默认选择第一个设备并获取其车辆数和黑白名单数
            if (this.devices.length > 0) {
              // 获取设备列表后，默认选择第一个设备并获取其车辆数和黑白名单数
              this.chooseDevice = String(this.devices[0].id); // 或者根据你的字段
              this.selectedDevice = this.devices[0]; // ✅ 更新当前选中设备
              // 清理上一个定时器
              this.stopDeviceStatusPolling();
              // 立即获取设备状态
              this.getDeviceStatus(this.selectedDevice);
              // 启动定时轮询
              this.startDeviceStatusPolling();
              // 检查websocket连接状态
              this.reconnectWebSocket();
              // 使用 Promise.all 等待两个异步请求完成
              Promise.all([
                this.getCarCount(this.devices[0].id),
                this.getNameListCount(this.devices[0].id),
              ])
                .then(([carCount, nameListCount]) => {
                  // 给第一个设备赋值
                  device.carCount = peopleCount.carCount;
                  device.carInHomeCount = peopleCount.carInHomeCount;
                  device.carOutHomeCount = peopleCount.carOutHomeCount;
                  device.nameListCount = nameListCount.nameListCount;
                  device.whiteNameListCount = nameListCount.whiteNameListCount;
                  device.blackNameListCount = nameListCount.blackNameListCount;
                  // 初始化第一个设备的图表
                  this.initChart(this.devices[0]);
                })
                .catch((error) => {
                  console.error("获取车辆数或黑白名单数失败：", error);
                });
              // 处理视频 // 重建数组
              console.log("this.selectedDevice：", this.selectedDevice);
              const mode = this.selectedDevice.videosInfo.length;
              this.screenVideos = Array(mode).fill("");
              this.playerInstances = Array(mode).fill(null);
            }
            this.$message.success(
              `获取设备信息成功 ${res.message ? "：" + res.message : ""}`,
            );
          } else {
            this.devices = [];
            this.total = 0;
            this.chooseDevice = "";
            this.$message.error(
              `获取设备信息失败 ${res.message ? "：" + res.message : ""}`,
            );
          }
        })
        .catch((error) => {
          console.error("获取设备信息失败:", error);
          this.devices = [];
          this.total = 0;
          this.chooseDevice = "";
          this.$message.error("获取设备列表失败，请检查网络或后端接口！");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    handleTabClick(tab) {
      console.log("切换到设备：", tab.name);
      const device = this.devices.find((d) => d.id === Number(tab.name));
      console.log("选中的设备对象：", device);
      if (device) {
        // 更新选中设备
        this.selectedDevice = device;
        // 清理上一个定时器
        this.stopDeviceStatusPolling();
        // 立即刷新设备状态
        this.getDeviceStatus(device);
        // 启动新设备定时轮询
        this.startDeviceStatusPolling();
        // 检查websocket连接状态
        this.reconnectWebSocket();
        // 使用 Promise.all 等待两个异步请求完成
        Promise.all([
          this.getCarCount(this.devices[0].id),
          this.getNameListCount(this.devices[0].id),
        ])
          .then(([carCount, nameListCount]) => {
            // 给第一个设备赋值
            device.carCount = carCount.carCount;
            device.carInHomeCount = carCount.carInHomeCount;
            device.carOutHomeCount = carCount.carOutHomeCount;
            device.nameListCount = nameListCount.nameListCount;
            device.whiteNameListCount = nameListCount.whiteNameListCount;
            device.blackNameListCount = nameListCount.blackNameListCount;
            // 初始化第一个设备的图表
            this.initChart(device);
          })
          .catch((error) => {
            console.error("获取车辆数或黑白名单数失败：", error);
          });
        // 处理视频 // 重建数组
        console.log("this.selectedDevice：", this.selectedDevice);
        const mode = this.selectedDevice.videosInfo.length;
        this.screenVideos = Array(mode).fill("");
        this.playerInstances = Array(mode).fill(null);
      }
    },
    // 获取车辆数
    async getCarCount(deviceId) {
      try {
        const res = await request({
          url: `/carDoor/getCarCount`,
          method: "POST",
          data: {
            deviceId: deviceId,
          },
        });
        console.log("获取车辆结果：", res);
        // 判断请求是否成功，成功则返回车辆数，否则返回 0
        if (res.status) {
          return res.data; // 返回车辆数总数
        } else {
          // this.$message.error(
          //   `获取车辆数失败 ${res.message ? "：" + res.message : ""}`,
          // );
          return 0; // 请求失败时返回 0
        }
      } catch (error) {
        // console.error("获取车辆数失败：", error);
        // this.$message.error("获取车辆数失败，请检查网络或后端接口！");
        return 0; // 请求异常时返回 0
      }
    },
    // 获取名单数量
    async getNameListCount(deviceId) {
      try {
        const res = await request({
          url: `/carDoor/getNameListCount`,
          method: "POST",
          data: {
            deviceId: deviceId,
          },
        });
        console.log("走到这一步了吗");
        console.log("获取名单数结果：", res);
        // 判断请求是否成功，成功则返回人脸数，否则返回 0
        if (res.status) {
          return res.data; // 返回人脸总数
        } else {
          this.$message.error(
            // `获取名单数失败 ${res.message ? "：" + res.message : ""}`,
          );
          return 0; // 请求失败时返回 0
        }
      } catch (error) {
        // console.error("获取名单数失败：", error);
        // this.$message.error("获取名单数失败，请检查网络或后端接口！");
        return 0; // 请求异常时返回 0
      }
    },
    initChart(device) {
      this.initUserChart(
        device,
        `chart1-${device.id}`,
        "人脸录入情况",
        device.bindFaceUserNumber,
        device.userNumber - device.bindFaceUserNumber,
      ); // 车辆
      this.initUserChart(
        device,
        `chart2-${device.id}`,
        "卡片录入情况",
        device.bindCardUserNumber,
        device.userNumber - device.bindCardUserNumber,
      ); // 授权名单
    },
    initUserChart(device, ref, title, num1, num2) {
      const chartRefArr = this.$refs[ref];
      const chartRef = Array.isArray(chartRefArr)
        ? chartRefArr[0]
        : chartRefArr;
      if (!chartRef) {
        console.warn("chart容器未渲染");
        return;
      }
      // ✅ 检查已有实例，存在就销毁
      const oldInstance = echarts.getInstanceByDom(chartRef);
      if (oldInstance) {
        oldInstance.dispose();
      }
      const chart = echarts.init(chartRef);
      chart.setOption({
        tooltip: { trigger: "item" },
        legend: { bottom: 0 },
        series: [
          {
            name: title,
            type: "pie",
            radius: ["35%", "75%"],
            avoidLabelOverlap: false,
            label: {
              show: true,
              position: "inside", // 'outside' 或 'inside'
              formatter: "{b}\n{d}%",
              fontSize: 12,
              align: "center",
              verticalAlign: "middle",
            },
            data: [
              { value: num1, name: "已录入" },
              { value: num2, name: "未录入" },
            ],
          },
        ],
      });
      // ✅ 使用数组存储设备的多个图表实例，避免重复
      if (!device.charts) {
        device.charts = [];
      }
      // 检查是否已经存在相同类型的图表
      const chartType = ref; // 使用ref作为标识，如 'chart1-device1'
      const existingIndex = device.charts.findIndex(
        (c) => c._chartType === chartType,
      );
      if (existingIndex > -1) {
        // 如果已存在，先销毁旧的，再替换
        device.charts[existingIndex].dispose();
        device.charts[existingIndex] = chart;
      } else {
        // 如果不存在，直接添加
        device.charts.push(chart);
      }
      // 给图表实例添加标识，方便后续查找
      chart._chartType = chartType;
      return chart;
    },
    resizeChart() {
      this.devices.forEach((d) => {
        if (d.chart) d.chart.resize();
      });
    },
    reconnectWebSocket() {
      this.$store.dispatch("ws/reconnectWebSocket");
    },
    handleWsEvent(e) {
      const msg = e.detail;
      if (!msg || msg.type !== "carGate" || !msg.payload) return;

      const deviceId = String(msg.deviceId);
      const device = this.devices.find((d) => String(d.id) === deviceId);
      const data = JSON.parse(msg.payload);
      if (typeof data.acsEventInfo === "string") {
        data.acsEventInfo = JSON.parse(data.acsEventInfo);
        console.log("acsEventInfo", data.acsEventInfo);
      }
      // 构造事件对象（带设备信息）
      const event = {
        ...data,
        deviceId,
        deviceName: device ? device.name : "未知设备",
        time: data.alarmTime || new Date().toISOString(),
      };
      // console.log("event", event);
      // ===== 分配到对应设备 =====
      if (device) {
        // 确保 eventData 已定义
        if (!Array.isArray(device.eventData)) {
          this.$set(device, "eventData", []); // ✅ Vue 响应式保证
        }
        // 添加最新事件到头部
        device.eventData.unshift(event);
        // 限制最大数量
        if (device.eventData.length > this.eventsMaxlength) {
          device.eventData.splice(this.eventsMaxlength);
        }
      } else {
        console.warn("未找到对应设备，deviceId =", deviceId, msg);
      }
    },
    getMajorTypeText(value) {
      return this.majorTypeMap[value] || `未知(${value})`;
    },
    getMinorTypeText(major, minor) {
      const minorMap = minorTypeMap[major];
      if (!minorMap) {
        return `未知(${minor})`;
      }
      // 先查固定的次类型
      if (minorMap[minor]) {
        return minorMap[minor];
      }
      // 门禁自定义报警/异常/操作/事件动态生成
      if (major === 1 && minor >= 0x900 && minor <= 0x93f) {
        return `门禁自定义报警${minor - 0x900 + 1}`;
      }
      if (major === 2 && minor >= 0x900 && minor <= 0x93f) {
        return `门禁自定义异常${minor - 0x900 + 1}`;
      }
      if (major === 3 && minor >= 0x900 && minor <= 0x93f) {
        return `门禁自定义操作${minor - 0x900 + 1}`;
      }
      if (major === 5 && minor >= 0x500 && minor <= 0x53f) {
        return `门禁自定义事件${minor - 0x500 + 1}`;
      }
      return `未知(${minor})`;
    },
    startDeviceStatusPolling() {
      if (!this.selectedDevice) return;
      this.deviceStatusTimer = setInterval(() => {
        this.getDeviceStatus(this.selectedDevice);
      }, 5000); // 每 5 秒轮询一次
    },
    stopDeviceStatusPolling() {
      if (this.deviceStatusTimer) {
        clearInterval(this.deviceStatusTimer);
        this.deviceStatusTimer = null;
      }
    },
    // 获取设备状态
    getDeviceStatus(device) {
      // console.log("📡 查询设备状态：", device.name);
      const data = {
        deviceId: device.id,
      };
      // console.log("请求参数：", data);
      device.loading = true;
      request({
        url: "/controlPeopleDoor/status",
        method: "POST",
        data: data,
      })
        .then((res) => {
          // console.log("接口返回结果：", res);
          // 确保 online 为布尔值
          device.isConnect =
            res && typeof res.online === "boolean" ? res.online : false;
          // if (res && res.status) {
          //   // this.$message.success(`设备 ${device.name} 在线状态获取成功 ${res.message ? '：' + res.message : ''}`);
          // } else {
          //   this.$message
          //     .error
          //     // `设备 ${device.name} 状态获取失败 ${res.message ? "：" + res.message : ""}`,
          //     ();
          // }
        })
        .catch((err) => {
          console.error("获取设备状态失败:", err);
          device.online = false;
          // this.$message.error(`设备 ${device.name} 状态获取失败`);
        })
        .finally(() => {
          device.loading = false;
        });
    },
    // 重连设备
    reconnectDevice(device) {
      console.log("🔄 重新连接设备：", device.name);
      const data = {
        deviceId: device.id,
      };
      console.log("连接设备参数：", data);
      device.loading = true; // 设置 loading
      request({
        url: "/controlCarDoor/login",
        method: "POST",
        data: data,
      })
        .then((res) => {
          console.log("连接设备结果：", res);
          if (res && res.status) {
            this.$message.success(
              `设备 ${device.name} 连接成功 ${res.message ? "：" + res.message : ""}`,
            );
          } else {
            this.$message.error(
              `设备 ${device.name} 连接失败 ${res.message ? "：" + res.message : ""}`,
            );
          }
        })
        .catch((err) => {
          console.error("连接设备失败:", err);
          this.$message.error(`设备 ${device.name} 连接失败`);
        })
        .finally(() => {
          device.loading = false; // ✅ 修正这里
        });
    },
    // 控制门
    toggleDoor(device, door, command) {
      if (door.loading) return;
      door.loading = true;
      const data = {
        deviceId: device.id,
        gatewayIndex: door.doorIndex,
        command: command,
      };
      console.log("控制门参数：", data);
      request({
        url: "/controlCarDoor/controlDoorStatus",
        method: "POST",
        data: data,
      })
        .then((res) => {
          console.log("控制门结果：", res);
          if (res && res.status) {
            this.$message.success(
              `门 ${device.name} 操作成功 ${res.message ? "：" + res.message : ""}`,
            );
          } else {
            this.$message.error(
              `门 ${device.name} 操作失败 ${res.message ? "：" + res.message : ""}`,
            );
          }
        })
        .catch((err) => {
          console.error("操作门失败:", err);
          this.$message.error(`操作门 ${door.doorId} 失败`);
        })
        .finally(() => {
          door.loading = false;
        });
    },
    // 表格样式修改
    changeHeaderCellStyle(row, column, rowIndex, columnIndex) {
      if (row.columnIndex === 0) {
        return "background: #004279 ; color:#fff;"; // 修改的样式
      } else {
        return "background: #004279 ;color:#fff; ";
      }
    },
    getPicTransType(value) {
      const map = {
        0: "二进制",
        1: "url",
      };
      return map[value] || `未知(${value})`;
    },
    getTimeType(value) {
      const map = {
        0: "设备本地时间",
        1: "UTC时间",
      };
      return map[value] || `未知(${value})`;
    },
    formatTime(t) {
      return `${t.dwYear}-${t.dwMonth}-${t.dwDay} ${t.dwHour}:${t.dwMinute}:${t.dwSecond}`;
    },
    // 格式化设备类型
    formatDeviceType(row) {
      console.log("设备类型", row);
      const map = {
        1: "普通摄像头",
        2: "人员通道闸机摄像头",
        3: "车辆通道闸机摄像头",
      };
      return map[row.deviceType] || row.deviceType || "-";
    },
  },
};
</script>

<style scoped>
/* 添加模块之间的间距 */
.stats-module {
  margin-bottom: 10px; /* 根据需要调整 */
}

/* 统计项样式 */
.overview-stats {
  display: flex;
  flex-wrap: wrap; /* 如果需要多行展示 */
  gap: 15px; /* 项之间的间距 */
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 10px; /* 图标与文本之间的间距 */
}

.stat-icon {
  width: 24px;
  height: 24px;
}

.stat-text p {
  margin: 0;
}

.stat-label {
  font-size: 14px;
  color: #666;
}

.stat-value {
  font-size: 16px;
  font-weight: bold;
}

/* 图表容器样式 */
.overview-chart {
  margin-top: 15px;
  /* 其他图表样式 */
}

.chart-box {
  height: 150px;
  background-color: #f5f5f5;
  border-radius: 4px;
}

/* 未激活文字颜色 */
.el-tabs >>> .el-tabs__item {
  color: #ffffff;
}

/* 激活 tab 文字颜色 */
.el-tabs >>> .el-tabs__item.is-active {
  color: #13cc65;
}

/* tabs header 背景 */
.el-tabs >>> .el-tabs__header {
  background-color: #0a1f3a;
}

/* 视频流 */
.video-box {
  flex: 1 1 200px; /* 最小宽 200px，可扩展 */
  max-width: 50%; /* 限制最大宽度 */
  min-width: 30%;
  height: 250px; /* 增加高度让视频显示更大 */
  background: #000;
  color: #fff;
  display: flex;
  flex-direction: column; /* 垂直排列 */
  padding: 5px;
  /*align-items: center;    /* 水平居中 */
  /*justify-content: flex-end; /* 名称在下方 */
  text-align: center;
}
.video-info {
  display: flex;
  flex-direction: column;
  height: 100%;
}
.info-text {
  margin-bottom: 5px;
  font-size: 12px;
}
.video-play {
  flex: 1;
  width: 100%;
}
.video-play MonitorHK {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保证视频铺满 */
}
.video-name {
  margin-top: 5px;
  font-weight: bold;
  font-size: 14px;
  color: #fff;
}

/* 门 */
.door-box {
  flex: 1 1 200px; /* 最小宽 200px，可扩展 */
  max-width: 50%; /* 限制最大宽度 */
  min-width: 30%;
  height: 200px; /* 增加高度让视频显示更大 */
  background: #f1f1f1;
  color: #fff;
  display: flex;
  flex-direction: column; /* 垂直排列 */
  padding: 5px;
  /*align-items: center;    /* 水平居中 */
  /*justify-content: flex-end; /* 名称在下方 */
  text-align: center;
}

/* ✅ 通用禁用灰色样式 */
.gray-disabled {
  background-color: #eeeeee !important;
  border-color: #d3d3d3 !important;
  color: #999 !important;
  cursor: not-allowed;
  opacity: 1 !important; /* 避免 Element 默认的半透明效果 */
}

/* 门 */
.door-container {
  display: flex; /* 必须加上 flex */
  justify-content: center; /* 水平居中 */
  gap: 10px; /* 门之间的间距 */
  margin-bottom: 10px;
  flex-wrap: nowrap; /* 默认不换行 */
}

.door-container.multi-row {
  flex-wrap: wrap; /* 超过三个门时换行 */
}

.door-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 180px; /* 固定宽度 */
  height: 180px; /* 固定高度 */
  justify-content: space-between; /* 顶部门名，中间bar，底部按钮均分 */
  background-color: #f0f0f0; /* 可选背景色 */
  border-radius: 6px; /* 可选圆角 */
  padding: 10px;
}

.door-label {
  margin-top: 10px;
  font-weight: bold;
  margin-bottom: 5px;
  font-size: 18px;
}

/* 模拟道闸底座 */
.barrier {
  width: 100%; /* 自适应门宽度 */
  max-width: 150px; /* 最大横杆宽度 */
  height: 20px;
  background-color: #444;
  position: relative;
  border-radius: 4px;
  margin-bottom: 5px;
}

/* 横杆 */
.bar {
  width: 100%; /* 随barrier宽度 */
  height: 8px;
  background-color: #2aabd2;
  position: absolute;
  top: 6px;
  left: 0;
  transform-origin: left center;
  transition: transform 0.8s ease;
  border-radius: 4px;
}

/* 人员统计 */
.overview-stats {
  display: flex;
  flex-direction: column;
  gap: 16px;
  min-width: 300px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 12px;
}

.stat-icon {
  width: 50px;
  height: 50px;
  border-radius: 8px;
}

.stat-text {
  display: flex;
  flex-direction: column;
}

.stat-label {
  color: #888;
  font-size: 14px;
  margin: 0;
}

.stat-value {
  font-size: 18px;
  font-weight: bold;
  margin: 0;
  color: #333;
}

.overview-chart {
  flex: 1;
  min-width: 300px;
}

.chart-box {
  width: 100%;
  height: 240px;
}

/* 开关状态 */
.bar.closed {
  transform: rotate(0deg);
  background-color: #2aabd2;
}

.bar.open {
  transform: rotate(-70deg);
  animation: swing 1s ease-out;
  background-color: #67c23a;
}

@keyframes swing {
  0% {
    transform: rotate(-20deg);
  }
  50% {
    transform: rotate(-75deg);
  }
  100% {
    transform: rotate(-70deg);
  }
}

/* websocket状态 */
.ws-badge-dot ::v-deep(.el-badge__dot) {
  width: 20px; /* 圆点宽度 */
  height: 20px; /* 圆点高度 */
  border-radius: 50%; /* 圆形 */
  top: 0; /* 可根据需要微调垂直位置 */
  right: 0; /* 可根据需要微调水平位置 */
}

/* 状态 */
.indicator {
  width: 14px;
  height: 14px;
  border-radius: 50%;
  margin-right: 6px;
  transition: all 0.3s;
  vertical-align: middle;
}

/* 状态颜色 */
.indicator.connected {
  background-color: #67c23a; /* success 绿 */
  box-shadow: 0 0 6px #67c23a;
}
.indicator.connecting {
  background-color: #e6a23c; /* warning 橙 */
  box-shadow: 0 0 6px #e6a23c;
}
.indicator.error {
  background-color: #f56c6c; /* danger 红 */
  box-shadow: 0 0 6px #f56c6c;
}
.indicator.disconnected {
  background-color: #909399; /* info 灰 */
  box-shadow: 0 0 6px #909399;
}
</style>
