<template>
  <div ref="container" class="full-container">
    <!-- 左侧视频区域 -->
    <div
      :style="{
        flex: isHideTable ? '1 1 100%' : '0 0 80%',
        height: '100%',
        padding: '0px 0 0px 0px',
        boxSizing: 'border-box',
      }"
    >
      <!-- 标题栏 + 分屏选择 -->
      <div class="top-bar">
        <!-- 左侧：全屏切换按钮 -->
        <el-image
          style="width: 20px; height: 20px; cursor: pointer; flex-shrink: 0"
          @click="toggleFullScreen"
          :src="
            isFullScreen
              ? require('@/assets/img/small.svg')
              : require('@/assets/img/iconMore.svg')
          "
          fit="contain"
        />
        <!-- 中间标题 -->
        <div
          style="
            margin-left: 10px;
            font-size: 20px;
            font-weight: bold;
            color: #ffffff;
          "
        >
          安防监控大屏
        </div>
        <!-- 占位撑开，使右侧工具挤到右侧 -->
        <div style="flex: 1"></div>
        <!-- 右侧工具区域 -->
        <el-select
          v-model="screenMode"
          @change="handleScreenModeChange"
          placeholder="选择分屏"
          clearable
          size="small"
          style="width: 150px; margin-right: 10px"
          :popper-append-to-body="!isFullScreen"
        >
          <el-option label="1x1" :value="1" />
          <el-option label="1x2" :value="2" />
          <el-option label="2x2" :value="4" />
          <el-option label="3x3" :value="9" />
          <el-option label="3x4" :value="12" />
          <el-option label="4x4" :value="16" />
        </el-select>
        <!-- 显示 / 隐藏表格按钮 -->
        <el-image
          style="
            width: 25px;
            height: 25px;
            margin-right: 5px;
            cursor: pointer;
            flex-shrink: 0;
            background-color: #b3d4fc;
          "
          @click="toggleHideTable"
          :src="
            isHideTable
              ? '/resources/hideTable.png'
              : '/resources/showTable.png'
          "
          fit="contain"
        />
      </div>
      <!-- 视频分屏区域 -->
      <div class="video-content">
        <div
          class="video-play"
          :style="{
            display: 'grid',
            gridTemplateColumns: `repeat(${gridCols}, 1fr)`,
            gridTemplateRows: `repeat(${gridRows}, 1fr)`,
            gap: '2px',
          }"
        >
          <div
            v-for="(item, index) in screenMode"
            :key="index"
            class="video-grid-item"
            :class="{ selected: selectedGridIndex === index }"
            @click="selectGrid(index)"
          >
            <video
              :ref="'video' + index"
              controls
              autoplay
              muted
              style="width: 100%; height: 100%; background-color: black"
            >
              您的浏览器不支持视频播放。
            </video>
          </div>
        </div>
      </div>
    </div>
    <!-- 右侧控制表格 -->
    <div
      v-show="!isHideTable"
      style="
        flex: 0 0 20%;
        display: flex;
        flex-direction: column;
        min-width: 200px;
        height: 100%;
      "
    >
      <!-- 搜索栏 -->
      <div class="top-bar">
        <el-select
          v-model="searchDeviceType"
          placeholder="设备类型"
          size="small"
          clearable
          @change="handleSearch"
          style="width: 150px"
          :popper-append-to-body="!isFullScreen"
        >
          <el-option
            v-for="item in deviceTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-input
          v-model="searchName"
          placeholder="视频名称"
          size="small"
          clearable
          @keyup.enter.native="handleSearch"
          style="width: 150px; margin-left: 5px"
        />
        <el-button
          type="primary"
          size="small"
          @click="handleSearch"
          style="margin-left: 5px"
          >查询</el-button
        >
        <!-- 占位撑开，使右侧工具挤到右侧 -->
        <div style="flex: 1"></div>
      </div>
      <!-- 表格 + 分页容器 -->
      <div class="table-content">
        <!-- 表格滚动区域 -->
        <div style="flex: 1; overflow: auto; background-color: #022844">
          <el-table
            :data="bigcamaraData.data"
            border
            style="width: max-content; min-width: 100%"
            :header-cell-style="changeHeaderCellStyle"
          >
            <el-table-column
              type="index"
              label="序号"
              min-width="50"
              align="center"
              :index="
                (index) =>
                  (bigcamaraData.currentPage - 1) * bigcamaraData.pageSize +
                  index +
                  1
              "
            />
            <el-table-column
              prop="name"
              label="视频名称"
              min-width="50"
              align="center"
            />
            <el-table-column label="操作" align="center" min-width="40">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  @click="startStream(scope)"
                  :disabled="scope.row.isStarting"
                  :loading="scope.row.isStarting"
                >
                  {{ scope.row.isStarting ? '推流中...' : '推流' }}
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  @click="playStreamForCamera(scope)"
                  :disabled="!scope.row.playUrl || scope.row.isPlaying"
                >
                  播放
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  @click="stopStream(scope)"
                  :disabled="scope.row.isStopping"
                  :loading="scope.row.isStopping"
                >
                  终止推流
                </el-button>
              </template>
            </el-table-column>
<!--            <el-table-column label="操作" align="center" min-width="40">-->
<!--              <template slot-scope="scope">-->
<!--                <el-button size="mini" type="text" @click="startStream(scope)"-->
<!--                  >推流</el-button-->
<!--                >-->
<!--                <el-button-->
<!--                  size="mini"-->
<!--                  type="text"-->
<!--                  @click="playStreamForCamera(scope)"-->
<!--                  >播放</el-button-->
<!--                >-->
<!--                <el-button size="mini" type="text" @click="stopStream(scope)"-->
<!--                  >终止推流</el-button-->
<!--                >-->
<!--              </template>-->
<!--            </el-table-column>-->
          </el-table>
        </div>
        <!-- 分页固定在底部 -->
        <div
          style="
            height: 40px;
            flex-shrink: 0;
            padding-top: 5px;
            background-color: #022844;
          "
        >
          <el-pagination
            class="custom-pagination"
            style="text-align: right"
            :current-page="bigcamaraData.currentPage"
            :page-size="bigcamaraData.pageSize"
            :total="bigcamaraData.total"
            :page-sizes="[15, 20, 25, 30]"
            layout="total, prev, pager, next, jumper, sizes"
            @current-change="handlePageChange"
            @size-change="handleSizeChange"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import flvjs from "flv.js";

export default {
  name: "videoShow",
  data() {
    return {
      // 分屏
      selectedGridIndex: null, // 当前选中的分屏格子索引，null 表示未选中任何格子
      screenMode: 1, // 当前分屏数，如 1、4、9、16
      gridCols: 1, // 当前网格列数
      gridRows: 1, // 当前网格行数
      screenVideos: [], // 每个元素代表一个格子的视频流地址，'' 表示未使用
      playerInstances: [], // 每个元素是对应格子的播放器实例（HLS或FLV）
      gridLoadingStatus: [], // 每个格子的加载状态
      gridErrorStatus: [], // 每个格子的错误状态
      selectedCamera: null, // 当前选中的摄像头
      // 全屏
      isFullScreen: false,
      isHideTable: false,
      // 视频数据
      bigcamaraData: {
        data: [],
        currentPage: 1,
        pageSize: 15,
        total: 0,
      },
      // 搜索条件
      searchName: "",
      searchDeviceType: "",
      deviceTypeOptions: [
        { label: "普通摄像头", value: 1 },
        { label: "人员通道闸机摄像头", value: 2 },
        { label: "车辆通道闸机摄像头", value: 3 },
      ],
    };
  },
  mounted() {
    if (!this.bigcamaraData.data.length) {
      this.getVideoInfo();
    }
    window.addEventListener("resize", this.resizeContainer);
    window.addEventListener("fullscreenchange", this.handleFullscreenChange);
    this.initAllPlayerInstances();
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.resizeContainer);
    window.removeEventListener("fullscreenchange", this.handleFullscreenChange);
    this.cleanupAllPlayerInstances();
  },
  methods: {
    /* ----------- 工具方法 ----------- */
    getVideoEl(index) {
      const ref = this.$refs["video" + index];
      if (!ref) return null;
      return Array.isArray(ref) ? ref[0] : ref;
    },
    initAllPlayerInstances() {
      this.screenVideos = Array(this.screenMode).fill('');
      this.playerInstances = Array(this.screenMode).fill(null);
      this.gridLoadingStatus = Array(this.screenMode).fill(false);
      this.gridErrorStatus = Array(this.screenMode).fill(null);
    },
    cleanupAllPlayerInstances() {
      for (let i = 0; i < this.playerInstances.length; i++) {
        this.destroyPlayer(i);
      }
    },
    toggleHideTable() {
      this.isHideTable = !this.isHideTable;
    },
    toggleFullScreen() {
      const el = this.$refs.container;
      if (!document.fullscreenElement) {
        el.requestFullscreen?.();
        this.isFullScreen = true;
        this.isHideTable = true;
      } else {
        document.exitFullscreen?.();
        this.isFullScreen = false;
      }
    },
    resizeContainer() {
      const container = this.$refs.container;
      if (!container) return;
      const rect = container.getBoundingClientRect();
      const top = rect.top;
      const left = rect.left;
      const width = window.innerWidth - left;
      const height = window.innerHeight - top;
      // console.log("容器样式:", { width, height, top, left });
    },
    changeHeaderCellStyle() {
      return "background: #004279; color:#fff;";
    },
    handlePageChange(page) {
      this.bigcamaraData.currentPage = page;
      this.getVideoInfo();
    },
    handleSizeChange(size) {
      this.bigcamaraData.currentPage = 1;
      this.bigcamaraData.pageSize = size;
      this.getVideoInfo();
    },
    handleSearch() {
      this.bigcamaraData.currentPage = 1;
      this.getVideoInfo();
    },
    async getVideoInfo() {
      try {
        const res = await request({
          url: "/videobyzlm/list",
          method: "POST",
          data: {
            page: this.bigcamaraData.currentPage,
            size: this.bigcamaraData.pageSize,
            name: this.searchName?.trim() || null,
            deviceType: this.searchDeviceType || null,
          },
        });
        if (res && res.list) {
          this.bigcamaraData.total = res.total || 0;
          this.bigcamaraData.data = res.list.map((item) => ({
            id: item.id,
            name: item.name,
            ip: item.ip,
            port: item.port,
            userName: item.username,
            password: item.password,
            url: item.url,
            playUrl: item.playUrl,
          }));
        } else {
          this.bigcamaraData.data = [];
          this.$message.warning("未获取到视频数据");
        }
      } catch (e) {
        console.error(e);
        this.$message.error("获取视频列表失败，请检查接口！");
      }
    },
    /* ----------- 分屏管理 ----------- */
    selectGrid(index) {
      this.selectedGridIndex = this.selectedGridIndex === index ? null : index;
    },
    async handleScreenModeChange(newMode) {
      const oldMode = this.screenMode;
      this.screenMode = newMode;
      const oldVideos = [...this.screenVideos];
      const oldPlayers = [...this.playerInstances];
      // 计算新布局
      this.gridCols = Math.ceil(Math.sqrt(newMode));
      this.gridRows = Math.ceil(newMode / this.gridCols);
      // 重建数组
      this.screenVideos = Array(newMode).fill("");
      this.playerInstances = Array(newMode).fill(null);
      // 1️⃣ 复制留下的播放器（保留原来的播放状态）
      const keepCount = Math.min(oldMode, newMode);
      for (let i = 0; i < keepCount; i++) {
        this.screenVideos[i] = oldVideos[i];
        this.playerInstances[i] = oldPlayers[i];
      }
      // 2️⃣ 如果窗口变小 → 销毁超出的播放器
      if (newMode < oldMode) {
        for (let i = newMode; i < oldMode; i++) {
          this.destroyPlayer(i);
        }
      }
      // 3️⃣ 刷新 DOM 后恢复显示（避免位置错乱）
      this.$nextTick(() => {
        for (let i = 0; i < keepCount; i++) {
          const videoEl = this.getVideoEl(i);
          const player = this.playerInstances[i];
          if (videoEl && player) {
            // 必须先 detach 再 re-attach
            try { player.detachMediaElement(); } catch {}
            player.attachMediaElement(videoEl);
            // ⭐⭐ 核心补丁：重新 load 和 play
            try {
              player.unload();   // 清空旧 SourceBuffer
              player.load();     // 重建 SourceBuffer
              player.play();     // 重新播放
            } catch(e){
              console.warn("replay error", e)
            }
          }
        }
      })
    },
    async playVideoInGrid(index, url) {
      if (!url) return;
      console.log(`开始播放 [${index}]: ${url}`);
      // 正确计算正在播放数量（排除自身格子）
      const activeCount = this.screenVideos.filter((v, i) => v !== "" && i !== index).length;
      const maxActive = 25;
      if (activeCount >= maxActive && !this.screenVideos[index]) {
        this.$message.warning(`同时最多播放 ${maxActive} 个视频`);
        return;
      }
      // 清理旧实例
      this.destroyPlayer(index);

      this.screenVideos[index] = url;
      const videoEl = this.getVideoEl(index);
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
        type: 'flv',
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
    destroyPlayer(index) {
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
      const videoEl = this.getVideoEl(index);
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
      // 清空状态
      this.screenVideos[index] = "";
      this.gridLoadingStatus[index] = null;
      this.gridErrorStatus[index] = null;
      if (this.flvErrorTimes) this.flvErrorTimes[index] = 0;
      console.log(`✔ 播放器已完全销毁 index=${index}`);
    },
    /* ----------- 推流管理 ----------- */
    async startStream(camera) {
      const row = camera.row;
      // 防止重复点击
      if (row.isStarting) return;
      try {
        // 设置启动中的状态
        this.$set(row, 'isStarting', true);
        // 如果已选中其他摄像头，先停止其推流
        if (this.selectedCamera && this.selectedCamera.id !== row.id) {
          await this.stopStream(this.selectedCamera, false);
        }
        this.selectedCamera = row;

        const res = await request({
          url: `/videobyzlm/start/${row.id}`,
          method: "GET",
        });
        console.log("添加推流结果:", res);
        if (res && res.status) {
          this.$message.success(res.message || "推流任务已提交");
          // 后端返回的数据
          const data = res.data || {};
          // playUrl 可以先赋值给播放器，但不代表在线 // 保存播放地址
          this.$set(row, 'playUrl', data.playUrl || "");
          // 在线状态 根据 online 字段判断是否真正上线
          this.$set(row, 'isOnline', data.online === true);
          // 如果没上线，等待 3 秒后查询一次
          if (!data.online) {
            setTimeout(() => this.checkStreamStatus(row.id, camera), 3000);
          } else {
            // 已上线 → 直接播放
            await this.playStreamForCamera(camera);
          }
        } else {
          this.$message.warning(res.message || "推流启动失败");
        }
      } catch (e) {
        console.error("推流请求失败:", e);
        this.$message.error("推流请求失败，请检查网络或服务器状态！");
      } finally {
        // 重置状态
        this.$set(row, 'isStarting', false);
      }
    },
    async checkStreamStatus(id, camera, attempts = 0, config = { maxAttempts: 5, interval: 1000 }) {
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
          this.$set(row, 'isOnline', true);
          this.$set(row, 'playUrl', playUrl);

          this.$message.success('推流已上线');
          await this.playStreamForCamera(camera);
          return true;
        }
        if (attempts >= config.maxAttempts) {
          this.$set(row, 'isOnline', false);
          this.$message.warning('推流未上线，请稍后重试');
          return false;
        }
        // 未上线 → 等待并继续查
        await new Promise(resolve => setTimeout(resolve, config.interval));
        return this.checkStreamStatus(id, camera, attempts, config);

      } catch (e) {
        console.error(`检查推流状态失败 [${id}]:`, e);
        if (attempts < config.maxAttempts) {
          await new Promise(resolve => setTimeout(resolve, config.interval));
          return this.checkStreamStatus(id, camera, attempts, config);
        } else {
          this.$set(row, 'isOnline', false);
          this.$message.error('查询推流状态失败，请重试');
          return false;
        }
      }
    },
    async playStreamForCamera(camera) {
      console.log("播放流：", camera);
      if (!camera?.row?.playUrl) {
        this.$message.warning("请先启动推流！");
        return;
      }
      // 找到第一个空闲格子 // 优先使用用户选中的格子
      let targetIndex = this.selectedGridIndex;
      if (targetIndex == null) {
        targetIndex = this.screenVideos.findIndex((v) => v === "");
      }
      if (targetIndex === -1) {
        this.$message.warning("请先选择分屏或等待空闲格子！");
        return;
      }
      console.log("播放流到索引：", targetIndex);
      await this.playVideoInGrid(targetIndex, camera.row.playUrl);
      camera.windowIndex = targetIndex;
    },
    async stopStream(camera, showMsg = true) {
      const row = camera.row || camera;
      if (!row || !row.id) return;
      // 防止重复停止
      if (row.isStopping) return;
      this.$set(row, 'isStopping', true);
      try {
        const res = await request({
          url: `/videobyzlm/stop/${row.id}`,
          method: "GET",
        });
        console.log("停止推流结果:", res);
        if (res && res.status) {
          // 1️⃣ 销毁播放器并清理 Video 标签
          if (row.windowIndex != null) {
            this.destroyPlayer(row.windowIndex);
            this.$set(row, 'windowIndex', null);
          }
          // 2️⃣ 重置状态字段
          this.$set(row, 'isPlaying', false);
          this.$set(row, 'isOnline', false);
          this.$set(row, 'playUrl', null);
          if (showMsg) this.$message.success(res.message || "已停止推流");
        } else {
          if (showMsg) this.$message.warning(res.message || "停止推流失败");
        }
      } catch (err) {
        console.error(`停止推流时出错 [${row.id}]:`, err);
        if (showMsg) this.$message.error("停止推流失败，请重试");
      } finally {
        this.$set(row, 'isStopping', false);
      }
    },
  },
};
</script>

<style scoped>
.full-container {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: row;
}

.top-bar {
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-left: 5px;
  position: relative;
  z-index: 200;
}

.video-content {
  height: calc(100% - 40px);
  width: 100%;
}

.video-play {
  height: 100%;
  width: 100%;
}

.video-grid-item {
  position: relative;
  cursor: pointer;
  transition: border-color 0.2s ease;
  pointer-events: auto !important; /* 保留格子的点击能力 */
}

.video-grid-item.selected {
  border: 3px solid gold;
  box-shadow: 0 0 8px rgba(255, 215, 0, 0.6);
}

.table-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
</style>
