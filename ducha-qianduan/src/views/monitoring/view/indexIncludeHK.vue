<template>
  <div ref="container" :style="containerStyle">
    <div
      ref="myCol1"
      :style="{
        flex: 'none',
        width: isFullScreen ? '100%' : '75%',
        height: '100%',
        padding: '10px 0 10px 10px', // 上10px 右0 下10px 左10px
      }"
    >
      <div style="height: 30px; display: flex; align-items: center">
        <el-image
          style="width: 20px; height: 20px; cursor: pointer; flex-shrink: 0"
          @click="showFull"
          :src="
            isFullScreen
              ? require('@/assets/img/small.svg')
              : require('@/assets/img/iconMore.svg')
          "
          fit="contain"
        />
        <div style="display: flex; align-items: center">
          <el-select
            v-model="selectedScreen"
            placeholder="选择分屏"
            clearable
            @change="changeWndNum"
            style="margin-left: 10px; flex-shrink: 0; width: 120px"
          >
            <el-option label="1x1" :value="1" style="color: black" />
            <el-option label="1x2" :value="12" style="color: black" />
            <el-option label="2x1" :value="21" style="color: black" />
            <el-option label="2x2" :value="2" style="color: black" />
            <el-option label="3x3" :value="3" style="color: black" />
            <el-option label="4x4" :value="4" style="color: black" />
          </el-select>
        </div>
        <div
          style="
            margin-left: 8px;
            display: flex;
            align-items: center;
            font-size: 20px;
            font-weight: bold;
            height: 20px;
            line-height: 20px;
          "
        >
          安防监控大屏
        </div>
      </div>
      <div style="height: calc(100% - 30px); width: 100%;">
<!--        <h2>HLS 摄像头实时播放</h2>-->
<!--        <div>-->
<!--          <label>摄像头 ID：</label>-->
<!--          <input v-model="cameraId" placeholder="例如：14_1" />-->
<!--          <input v-model="rtspUrl" placeholder="RTSP 地址" />-->
<!--          <button @click="startStream">获取推流</button>-->
<!--          <button @click="playStream">播放</button>-->
<!--          <button @click="stopStream">停止播放</button>-->
<!--        </div>-->
        <div class="video-box">
          <div class="video-info">
            <div class="video-play">
              <div v-show="hlsUrl">
                <video ref="videoPlayer" width="100%" height="100%" controls autoplay muted style="background-color: black;">
                  您的浏览器不支持视频播放。
                </video>
              </div>
            </div>
          </div>
        </div>
<!--        <div v-show="hlsUrl" style="margin-top: 20px;">-->
<!--          <video ref="videoPlayer" width="800" height="600" controls autoplay muted style="background-color: black;">-->
<!--            您的浏览器不支持视频播放。-->
<!--          </video>-->
<!--        </div>-->
<!--        <div v-if="errorMsg" style="color: red; margin-top: 10px;">-->
<!--          {{ errorMsg }}-->
<!--        </div>-->
<!--        </div>-->
<!--        <MonitorHK-->
<!--          ref="hkbigCamara"-->
<!--          style="width: 100%; height: 100%"-->
<!--          v-if="HKbigCamaraShow"-->
<!--          @handleVideoPlay="handleVideoPlay"-->
<!--          @changeWindow="handleChangeWindow"-->
<!--          :num="selectedScreen"-->
<!--          :bofang="isBofang"-->
<!--          :chooseWindow="selectWindow"-->
<!--          :companyVideoData="bigcamaraData.data"-->
<!--          :isFullScreen="isFullScreen"-->
<!--          @init="HKbigCamaraInit"-->
<!--        />-->
      </div>
    </div>
    <div
      ref="myCol2"
      :style="{
        flex: 'none',
        width: isFullScreen ? '0%' : '25%',
        height: '100%',
        padding: '10px 10px 10px 0px', // 上10px 右10px 下10px 左0px
        boxSizing: 'border-box', // 让padding参与宽度计算
        display: 'flex', //  新增
        flexDirection: 'column', //  新增
      }"
    >
      <!-- 搜索栏 -->
      <div
        style="
          height: 36px;
          display: flex;
          align-items: center;
          gap: 8px;
          padding: 0 4px;
        "
      >
        <!-- 输入框：回车自动查询 -->
        <el-input
          v-model="searchName"
          placeholder="视频名称"
          size="small"
          clearable
          @keyup.enter.native="handleSearch"
          style="width: 120px"
        />

        <!-- 选择框：改变后自动查询 -->
        <el-select
          v-model="searchDeviceType"
          placeholder="设备类型"
          size="small"
          clearable
          @change="handleSearch"
          style="width: 120px"
        >
          <el-option
            style="color: black"
            v-for="item in deviceTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <!-- 查询按钮 -->
        <el-button type="primary" size="small" @click="handleSearch"
          >查询</el-button
        >
        <el-button type="primary" size="small" @click="batchPlayVideos"
        >批量播放</el-button
        >
        <el-button type="primary" size="small" @click="stopAllVideo"
        >关闭</el-button
        >
      </div>
      <div style="display: flex; flex-direction: column; overflow: hidden">
        <el-table
          style="width: 100%"
          :data="bigcamaraData.data"
          border
          :header-cell-style="changeHeaderCellStyle"
        >
          <el-table-column
            type="index"
            label="序号"
            min-width="30"
            align="center"
          />
          <el-table-column
            prop="name"
            label="视频名称"
            min-width="50"
            align="center"
          />
          <el-table-column
            label="播放窗口"
            :formatter="(row) => row.windowList.join(',')"
            align="center"
            min-width="50"
          />
          <el-table-column label="操作" align="center" min-width="50">
            <template #default="scope">
              <div>
                <el-button
                  size="mini"
                  type="text"
                  @click="begin_bigcamaraData(scope)"
                >
                  登录
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  @click="stopall_bigcamaraData(scope)"
                >
                  暂停所有
                </el-button>
              </div>
              <div>
                <el-button size="mini" type="text" @click="startStream(scope)">推流</el-button>
                <el-button size="mini" type="text" @click="playStream">播放</el-button>
                <el-button size="mini" type="text" @click="stopStream(scope)">终止推流</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination
          class="custom-pagination"
          style="margin-top: 20px; text-align: right;"
          :current-page="bigcamaraData.currentPage"
          :page-size="bigcamaraData.pageSize"
          :total="bigcamaraData.total"
          :page-sizes="[10, 15, 20, 25, 30]"
          layout="total, prev, pager, next, jumper, sizes"
          @current-change="handlePageChange"
          @size-change="handleSizeChange"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, ref } from "vue";
import myFlylineChartEnhanced from "../components/my-flyline-chart-enhanced/index";
import myscrollBoard from "../components/myscroll-board/src/main";
import sacleBox from "../components/sacle-box/index";
// var uploadedDataURL = require('../../public/json/taiyuan.json')
import "./taskScreen.css";
import Videojs from "video.js"; // 引入Videojs
import MonitorHK from ".";
import { isInteger, toInteger } from "lodash";
import request from "@/utils/request";
import Hls from 'hls.js';

export default {
  name: "monitorScreen",
  components: {
    myFlylineChartEnhanced,
    sacleBox,
    myscrollBoard,
    MonitorHK,
  },
  props: {
    title: {
      type: String,
      default: "",
    },
    videoData: {
      type: Array,
    },
  },
  setup() {
    const myCol1 = ref(null);
    const myCol2 = ref(null);

    onMounted(() => {
      // 获取第一个 el-col 的宽高
      const width1 = myCol1.value.offsetWidth;
      const height1 = myCol1.value.offsetHeight;
      console.log("el-col1 宽度:", width1, "高度:", height1);

      // 获取第二个 el-col 的宽高
      const width2 = myCol2.value.offsetWidth;
      const height2 = myCol2.value.offsetHeight;
      console.log("el-col2 宽度:", width2, "高度:", height2);
    });
    return { myCol1, myCol2 };
  },
  data() {
    return {
      selectedCamera: null,
      cameraId: '14_1', // 可以是动态的，比如从列表选择
      rtspUrl: "",       // 后端返回的 rtsp 地址
      hlsUrl: '',       // 后端返回的 .m3u8 地址
      hls: null,        // hls.js 实例
      errorMsg: '',     // 错误提示

      containerStyle: {
        display: "flex",
        justifyContent: "center",
        position: "absolute", // 绝对定位
        top: "0px",
        left: "0px",
        width: "100%",
        height: "100%",
        backgroundColor: '#0c5cab',
      },
      isFullScreen: false,
      selectedScreen: 1, // 分屏数
      selectWindow: 0, // 当前选中窗口
      selectCamaraRow: 0,
      stopCamara: false,
      stopCamaraRow: 0,
      isBofang: false, // 播放状态
      dataSrc:
        "https://cctvwbndbd.a.bdydns.com/cctvwbnd/cctv1_2/index.m3u8?BR=single",
      isShow: false,
      HKbigCamaraShow: false, // 控制显示隐藏
      bigcamaraData: {
        // header: ['0','1', '2', '3','4', '5', '6','7'],
        // waitTime:1500,
        data: [],
        length: 0,
        total: 0,
        currentPage: 1,
        pageSize: 10,
        paged: true,
      },
      bigcamaraSrc: [],
      bigcamaraName: [],
      searchName: "",
      searchDeviceType: "",
      deviceTypeOptions: [
        { label: "普通摄像头", value: 1 },
        { label: "人员通道闸机摄像头", value: 2},
        { label: "车辆通道闸机摄像头", value: 3},
      ],
    };
  },
  mounted() {
    // this.resizeContainer();
    window.addEventListener("resize", this.resizeContainer);
  },
  beforeUnmount() {
    window.removeEventListener("resize", this.resizeContainer);
  },
  created() {
    this.init();
  },
  computed: {
    resizeContainer() {
      const container = this.$refs.container;
      if (!container) return;

      // 获取容器左上角相对于视口的位置
      const rect = container.getBoundingClientRect();
      const top = rect.top;
      const left = rect.left;

      // 可用宽高 = 视口大小 - 左上角偏移
      const width = window.innerWidth - left;
      const height = window.innerHeight - top;
      console.log("容器样式", this.containerStyle);
      // this.containerStyle.width = `${width}px`
      // this.containerStyle.height = `${height}px`
      // this.containerStyle.top = `${top}px`
      // this.containerStyle.left = `${left}px`
      //
      // console.log("容器样式", this.containerStyle)
    },
  },
  activated() {
    if (!this.bigcamaraData.data.length) {
      this.getVideoInfo();
    }
  },
  methods: {
    // 初始化
    init() {
      setTimeout(() => {
        this.HKbigCamaraShow = true;
      }, 500);
      // 只在第一次加载时初始化
      if (!this.bigcamaraData.data.length) {
        this.getVideoInfo();
      }
    },
    // 查询视频流信息
    handleSearch() {
      this.currentPage = 1;
      this.getVideoInfo();
    },
    // 分页切换
    async getVideoInfo() {
      try {
        // 发起分页+查询请求
        const res = await request({
          url: "/video/list",
          method: "POST",
          data: {
            page: this.bigcamaraData.currentPage,
            size: this.bigcamaraData.pageSize,
            name: this.searchName?.trim() || null, // 去掉多余空格，避免 "" 导致条件错乱
            deviceType: this.searchDeviceType || null, // 为空时传 null
          },
        });

        // 判断返回结构
        if (res && res.list) {
          this.bigcamaraData.total = res.total || 0;
          this.bigcamaraData.data = res.list.map((item) => ({
            id: item.id,
            name: item.name,
            ip: item.ip,
            port: item.port,
            userName: item.username,
            password: item.password,
            channelName: item.channelName,
            url: item.url,
            playUrl: item.playUrl,
            windowList: [],
          }));
        } else {
          this.bigcamaraData.total = 0;
          this.bigcamaraData.data = [];
          this.$message.warning("未获取到视频数据");
        }
      } catch (error) {
        console.error("获取视频信息失败:", error);
        this.$message.error("获取视频列表失败，请检查网络或后端接口！");
      }
    },
    // 批量播放视频（顺序播放，稳定版）
    async batchPlayVideos() {
      // 1. 映射布局到实际窗口数
      const layoutMap = {
        1: 1,   // 1x1
        12: 2,  // 1x2
        21: 2,  // 2x1
        2: 4,   // 2x2
        3: 9,   // 3x3
        4: 16,  // 4x4
      };

      const totalWindows = layoutMap[this.selectedScreen] || 1;
      const videoList = this.bigcamaraData.data;

      if (!videoList.length) return this.$message.warning("没有可播放的视频");

      console.log("批量播放开始，窗口数:", totalWindows, "视频数:", videoList.length);

      // 2. 停止所有窗口播放
      await this.$refs.hkbigCamara.stopAllRealPlay();

      // 3. 清空所有视频 windowList
      videoList.forEach(video => {
        this.$set(video, 'windowList', []);
      });

      // 4. 窗口数和视频数取最小
      const playCount = Math.min(totalWindows, videoList.length);
      console.log(`批量播放 ${playCount} 个视频`);

      // 5. 顺序播放视频
      for (let i = 0; i < playCount; i++) {
        try {
          await this.playCameraVideoInSelectWindow({ row: videoList[i], $index: i }, i);
        } catch (err) {
          console.error(`窗口 ${i} 播放失败`, err);
        }
      }

      this.$message.success("批量播放完成");
      console.log("批量播放结束，当前窗口列表状态:", videoList.map(v => v.windowList));
    },
    // 播放某个视频到指定窗口
    async playCameraVideoInSelectWindow(camera, iWndIndex) {
      const bofangyuan = {
        name: camera.row.name,
        channelName: camera.row.channelName,
        ip: camera.row.ip,
        port: camera.row.port,
        username: camera.row.userName,
        password: camera.row.password,
        url: camera.row.url,
        playUrl: camera.row.playUrl,
        windowList: camera.row.windowList || [],
        iWndIndex: iWndIndex,
        isbofang: 1,
        channelId: camera.row.channelId || 1, // 默认通道1
      };
      console.log("播放视频源", bofangyuan);

      try {
        const loginResult = await this.$refs.hkbigCamara.login([bofangyuan]);
        const deviceResult = loginResult[0]; // 单设备登录结果

        if (!deviceResult.success) {
          this.$message.error(`登录摄像机失败: ${bofangyuan.ip}, 错误码: ${deviceResult.errorCode}`);
          console.error("登录失败信息:", deviceResult);
          return; // 登录失败就不继续播放
        }

        // 2. 停止目标窗口已有视频
        await this.checkWindow(iWndIndex);

        // 3. 播放视频
        await this.$refs.hkbigCamara.playVideoInSelectWindow(bofangyuan);

        // 4. 更新 windowList（保证响应式）
        this.$set(
          camera.row,
          'windowList',
          [...new Set([...camera.row.windowList, iWndIndex])].sort((a, b) => a - b)
        );

        this.isBofang = true;
        console.log("窗口列表更新:", camera.row.windowList);

      } catch (err) {
        this.$message.error(`播放摄像机异常: ${bofangyuan.ip}`);
        console.error("播放异常信息:", err);
      }
    },
    // 停止播放所有窗口的视频
    stopAllVideo() {
      this.$refs.hkbigCamara.stopAllRealPlay();
    },
    // 处理播放视频的返回结果
    handleVideoPlay(event) {
      console.log("错误码:", event);
      // 统一事件结构
      const { code, iWndIndex } = typeof event === 'object' ? event : { code: event, iWndIndex: this.selectWindow };
      const targetData = this.bigcamaraData.data[this.selectCamaraRow];

      switch(code) {
        case "playSuccess":
          this.$message.success(`窗口 ${iWndIndex + 1} 播放成功`);
          if(!targetData.windowList.includes(iWndIndex)) {
            targetData.windowList.push(iWndIndex);
          }
          break;

        case "playFail":
          this.$message.error(`窗口 ${iWndIndex + 1} 播放失败`);
          break;

        case "stopSuccess":
          this.$message.success(`窗口 ${iWndIndex + 1} 停止成功`);
          targetData.windowList = targetData.windowList.filter(win => win != iWndIndex);
          break;

        case "stopFail":
          this.$message.error(`窗口 ${iWndIndex + 1} 停止失败`);
          break;

        case "stopAllSuccess":
          this.$message.success("所有窗口停止成功");
          targetData.windowList = [];
          break;

        case "stopAllFail":
          this.$message.error("所有窗口停止失败");
          break;
      }

      console.log("当前窗口列表:", targetData.windowList);
    },
    // 改变窗口
    handleChangeWindow(value) {
      this.selectWindow = value;
      console.log("当前选中的窗口this.selectWindow", this.selectWindow);
    },
    // 停止指定窗口的视频播放
    async stopone_bigcamaraData(camera) {
      console.log("当前选中的摄像机", camera);
      const selectedCamera = this.bigcamaraData.data[camera.$index];
      try {
        await this.$refs.hkbigCamara.stopOneWindowPlayVideo(this.selectWindow); // 停止播放
        // 更新数据，移除已停止窗口
        selectedCamera.windowList = selectedCamera.windowList.filter(
          (element) => element !== this.selectWindow,
        );
      } catch (error) {
        console.error("停止单个视频失败:", error);
      }
    },
    async startStream(camera) {
      console.log("当前选中的摄像机索引:", camera.$index);
      console.log("当前选中的摄像机行数据:", camera.row);
      // === 判断是否需要切换摄像头 ===
      if (this.selectedCamera && this.selectedCamera.id !== camera.row.id) {
        console.log("终止上一个摄像头的所有操作...");
        await this.stopStream(this.selectedCamera, false); // false 表示不提示
      }
      // 设置当前摄像头
      this.selectedCamera = camera.row;
      this.loading = true;
      try {
        const res = await request({
          url: `/video/start/${camera.row.id}`,
          method: "GET",
        });
        console.log("获取视频信息成功:", res);
        if (res && res.status) {
          this.$message.success(res.message || "推流启动成功");
          this.hlsUrl = res.data;
          this.playStream(); // 自动播放
        } else {
          this.$message.warning(res.message || "推流启动失败");
        }
      } catch (error) {
        console.error("获取视频信息失败:", error);
        this.$message.error("获取视频信息失败，请检查网络或后端接口！");
      } finally {
        this.loading = false;
      }
    },
    playStream() {
      if (!this.hlsUrl) {
        this.$message.warning("未获取推流地址，请先启动推流");
        return;
      }
      const videoEl = this.$refs.videoPlayer;
      if (!videoEl) {
        this.$message.error("视频元素未找到！");
        return;
      }
      // 清理旧实例
      if (this.hls) {
        this.hls.destroy();
        this.hls = null;
      }

      // 创建新的 HLS 实例
      if (Hls.isSupported()) {
        this.hls = new Hls();
        this.hls.loadSource(this.hlsUrl);
        this.hls.attachMedia(videoEl);

        this.hls.on(Hls.Events.MANIFEST_PARSED, () => {
          videoEl.play().catch(err => {
            console.warn("自动播放被阻止:", err);
          });
        });

        this.hls.on(Hls.Events.ERROR, (event, data) => {
          console.error("HLS 播放错误:", data);
          if (data.fatal) {
            switch (data.type) {
              case Hls.ErrorTypes.NETWORK_ERROR:
                this.hls.startLoad();
                break;
              case Hls.ErrorTypes.MEDIA_ERROR:
                this.hls.recoverMediaError();
                break;
              default:
                this.stopStream(this.selectedCamera);
                break;
            }
          }
        });
      } else if (videoEl.canPlayType("application/vnd.apple.mpegurl")) {
        // Safari 原生支持
        videoEl.src = this.hlsUrl;
        videoEl.play();
      } else {
        this.errorMsg = "当前浏览器不支持 HLS 播放";
      }
    },
    async stopStream(camera, showMsg = true) {
      if (!camera || !camera.id) {
        console.warn("stopStream 调用时 camera 参数无效:", camera);
        return;
      }
      console.log("停止摄像头:", camera.id);
      // === 前端停止播放 ===
      if (this.hls) {
        this.hls.destroy();
        this.hls = null;
      }
      const videoEl = this.$refs.videoPlayer;
      if (videoEl) {
        videoEl.pause();
        videoEl.src = "";
      }
      this.hlsUrl = "";
      // === 后端停止推流 ===
      try {
        const res = await request({
          url: `/video/stop/${camera.id}`,
          method: "GET",
        });
        console.log("停止推流结果:", res);
        if (res && res.status) {
          if (showMsg) this.$message.success(res.message || "停止推流成功");
        } else {
          if (showMsg) this.$message.warning(res.message || "停止推流失败");
        }
      } catch (error) {
        console.error("停止推流失败:", error);
        if (showMsg) this.$message.error("停止推流失败，请检查网络或后端接口！");
      }
    },
    async begin_bigcamaraData(camera) {
      console.log("当前选中的摄像机", camera);
      console.log("当前选中的窗口", this.selectWindow);
      this.selectCamaraRow = camera.$index;
      const selectedCamera = this.bigcamaraData.data[camera.$index];

      // 限制单摄像机最多 6 个窗口
      if (selectedCamera.windowList.length >= 6) {
        return this.$message.warning("摄像机已满");
      }

      // 停止当前窗口已有视频
      await this.checkWindow(this.selectWindow);

      // 当前选择视频源
      const bofangyuan = {
        name: camera.row.name,
        channelName: camera.row.channelName,
        ip: camera.row.ip,
        port: camera.row.port,
        username: camera.row.userName,
        password: camera.row.password,
        url: camera.row.url,
        playUrl: camera.row.playUrl,
        windowList: camera.row.windowList,
        isbofang: 1,
      };
      console.log("bofangyuan", bofangyuan);

      try {
        const result = await this.$refs.hkbigCamara.login([bofangyuan]);
        // login 返回的是数组，每台设备有 success/errorCode
        const deviceResult = result[0]; // 这里只有一台设备
        if (deviceResult.success) {
          // 登录成功，更新窗口列表
          selectedCamera.windowList = [...new Set([...selectedCamera.windowList, this.selectWindow])].sort((a, b) => a - b);
          this.isBofang = true;
          console.log("当前窗口列表:", selectedCamera.windowList);
        } else {
          this.$message.error(`登录摄像机失败: ${bofangyuan.ip}, 错误码: ${deviceResult.errorCode}`);
          console.error("登录失败信息:", deviceResult);
        }
      } catch (err) {
        // 捕获意外错误
        this.$message.error(`登录摄像机异常: ${bofangyuan.ip}`);
        console.error("登录异常:", err);
      }
    },
    // 停止指定摄像机所有窗口的视频播放
    async stopall_bigcamaraData(camera) {
      console.log("当前选中的摄像机", camera);
      const selectedCamera = this.bigcamaraData.data[camera.$index];
      if (!selectedCamera.windowList.length) {
        this.$message.info("该摄像机当前没有正在播放的视频");
        return;
      }

      const windows = [...selectedCamera.windowList]; // 防止引用问题
      try {
        await this.$refs.hkbigCamara.stopallPlayVideo(windows);
        selectedCamera.windowList = [];
        this.$message.success("停止该视频流在所有窗口上播放-操作成功");
      } catch (error) {
        console.error("停止该视频流在所有窗口上播放-操作失败:", error);
        this.$message.error("停止该视频流在所有窗口上播放-操作失败");
      }
    },
    // 检查指定窗口是否正在播放其他视频，如在播放则停止
    checkWindow(selectWindow) {
      console.log("检查窗口", selectWindow);
      return new Promise((resolve) => {
        const promises = [];
        console.log("this.bigcamaraData.data", this.bigcamaraData.data);
        this.bigcamaraData.data.forEach((camera) => {
          // console.log("camera", camera);
          if (camera.windowList.length > 0) {
            camera.windowList.forEach((win, winIndex) => {
              if (parseInt(win) === selectWindow) {
                console.log(`窗口 ${selectWindow} 已被占用，停止播放...`);
                this.stopCamara = true;
                this.stopCamaraRow = winIndex;
                // 停止播放并更新数据
                const stopPromise = this.$refs.hkbigCamara.stopOneWindowPlayVideo(selectWindow)
                  .then(() => {
                    camera.windowList = camera.windowList.filter(
                      (element) => element !== selectWindow,
                    );
                    console.log("当前窗口列表:", camera.windowList);
                  })
                  .catch((error) => {
                    console.error("停止视频失败:", error);
                  });
                promises.push(stopPromise); // Promise 已经加入数组
              } else {
                console.log(`窗口 ${win} 未被占用`);
              }
            });
          } else {
            // console.log("当前视频没有窗口正在播放：", camera.name);
          }
        });

        if (promises.length === 0) {
          resolve();
        } else {
          Promise.all(promises).then(() => resolve());
        }
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
    changeCellStyle(row, column, rowIndex, columnIndex) {
      if (row.columnIndex === 0) {
        return "background: #0A427C  ; color:#fff;"; // 修改的样式
      } else {
        return "background: #0A427C ; color:#fff;";
      }
    },
    // 页码改变
    handlePageChange(page) {
      this.bigcamaraData.currentPage = page;
      this.getVideoInfo(); // 重新加载
    },
    // 每页条数改变
    handleSizeChange(size) {
      this.bigcamaraData.currentPage = 1;
      this.bigcamaraData.pageSize = size;
      this.getVideoInfo(); // 重新加载
    },
    // 窗口分割数
    changeWndNum(val) {
      // 这里可以直接使用 val，因为 val 已经是选中的值了，不需要再次转换
      console.log("changeWndNum", val);
      this.selectedScreen = val; // 不需要转换为整数，直接使用原始值
      console.log("当前选择的分屏数量", this.selectedScreen);
    },
    // 播放大屏
    HKbigCamaraInit() {
      this.HKbigCamaraShow = true;
    },
    // 全屏切换
    showFull() {
      console.log("showFull", this.isFullScreen);
      this.isFullScreen = !this.isFullScreen;
    },
  },
};
</script>
<style scoped>
/* 视频流 */
.video-box {
  height: 100%;
  width: 100%;
  background: #000;
  color: #fff;
  display: flex;
  flex-direction: column;
  padding: 5px;
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
  position: relative; /* 关键：为绝对定位的视频提供参考 */
  min-height: 0; /* 关键：允许flex项收缩 */
}

.video-play video {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保证视频铺满容器 */
}

.video-name {
  margin-top: 5px;
  font-weight: bold;
  font-size: 14px;
  color: #fff;
  flex-shrink: 0; /* 防止名称被压缩 */
}


.screen-header {
  background: #11497f;
  color: #fff;
  padding: 16px;
}

.controls {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 20px;
}

.label {
  font-size: 14px;
}

.radio-group {
  margin-left: 8px;
}

.close-btn {
  margin-left: auto; /* 靠右 */
}

.video-section {
  margin-top: 20px;
  background: #166fca;
  color: #333;
  padding: 12px;
  border-radius: 6px;
}

.pagination-row {
  margin-top: 10px;
}

#box {
  display: none;
  width: 315px;
  height: 180px;
  background: #ccc;
  border: 1px solid #333;
  padding: 12px;
  text-align: center;
}
.player {
  background-color: #d9b3b3;
  height: 50%;
  width: 50%;
  border: 1px solid white;
  color: white;
  text-align: center;
}

.monitor_video_box {
  display: flex;
  align-items: flex-start;
  color: #fff;
  height: 100%;
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
}
.full_video_box {
  width: 80%;
  height: 100%;
  /* border:1px solid red; */
  padding: 10px;
  box-sizing: border-box;
  /*background-image:url('@/assets/img/opitty.png');*/
  background-repeat: no-repeat;
  background-size: 100% 100%;
  margin-right: 15px;
}
.full_video_list {
  width: 20%;
  height: 100%;
  /*border:1px solid yellow;*/
  /*padding:17px 10px 10px 10px;*/
  /*box-sizing: border-box;*/
  /*background-image:url('@/assets/img/opitty.png');*/
  /*background-repeat:no-repeat;*/
  /*background-size:100% 100%;*/

  /*background: #0027a8;*/
}
.full_video_list_card {
  width: 100%;
  height: 49%;
  /*border:1px solid yellow;*/
  padding: 17px 10px 10px 10px;
  box-sizing: border-box;
  /*background-image:url('@/assets/img/opitty.png');*/
  background-repeat: no-repeat;
  background-size: 100% 100%;

  background: #004279;
}

.full_video_list_card_list {
  margin-top: 30px;
}

.full_video_list_camara {
  position: relative;
  z-index: 1;
  margin-top: 4%;
  width: 100%;
  height: 49%;
  /*border:1px solid yellow;*/
  padding: 17px 10px 10px 10px;
  box-sizing: border-box;
  /*background-image:url('@/assets/img/opitty.png');*/
  background-repeat: no-repeat;
  background-size: 100% 100%;

  /*background: #13864d;*/
  background: #004279;
  /*margin: 10px;*/
}
.full_video_list_camara_list {
  margin-top: 30px;
}
.video_title_new {
  font-size: 18px;
  font-weight: 600;
  margin: 10px 0;
}

.exit_box_hanlde {
  /*display: flex;*/
  /*justify-content: end;*/
  /*padding-right: 4px;*/
  display: flex;
  justify-content: flex-end;
  margin-bottom: 10px;
  margin-top: -40px;
}

#videoPlayerTask {
  width: 100%;
  height: 95%;
}

#videoPlayer_first {
  width: 419px;
  height: 182px;
  margin-right: 10px;
  margin-bottom: 10px;
}

#videoPlayer_second {
  width: 419px;
  height: 182px;
  margin-bottom: 10px;
}

#videoPlayer_third {
  width: 419px;
  height: 182px;
  margin-right: 10px;
  margin-bottom: 10px;
}

#videoPlayer_fourth {
  width: 419px;
  height: 182px;
}

/*分页样式*/
/* 全局或 scoped 样式 */
.custom-pagination {
  color: #000; /* 分页文字颜色 */
}

.custom-pagination .el-pager li {
  color: #000;
  border-color: #000;
}

.custom-pagination .el-pager li.active {
  background-color: #000;
  color: #da2121;
}

.custom-pagination .el-pager li:hover {
  color: #000;
}
</style>
