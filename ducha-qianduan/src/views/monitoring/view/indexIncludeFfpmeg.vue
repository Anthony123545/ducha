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
            <!-- 加载遮罩 -->
            <div v-if="gridLoadingStatus[index]" class="video-overlay loading">
              <div class="loading-spinner"></div>
              <div class="overlay-text">视频加载中...</div>
            </div>
            <!-- 错误遮罩 -->
            <div v-if="gridErrorStatus[index]" class="video-overlay error">
              <div class="overlay-text">{{ gridErrorStatus[index] }}</div>
              <el-button
                type="primary"
                size="mini"
                @click.stop="retryVideo(index, screenVideos[index])"
              >
                重试
              </el-button>
            </div>
            <!-- 空状态提示 -->
            <div v-if="!screenVideos[index] && !gridLoadingStatus[index]" class="video-overlay empty">
              <div class="overlay-text">点击选择分屏</div>
            </div>
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
                  :disabled="!scope.row.hlsUrl || scope.row.isPlaying"
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
import Hls from "hls.js";

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
      hlsInstances: [], // 每个元素是对应格子的 HLS 实例
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
    this.initScreenData();
  },
  beforeDestroy() {
    // 移除所有事件监听器
    window.removeEventListener("resize", this.resizeContainer);
    window.removeEventListener("fullscreenchange", this.handleFullscreenChange);
    // 清理所有HLS实例和资源
    this.cleanupAllHlsInstances();
  },
  methods: {
    /* ----------- 工具方法 ----------- */
    initScreenData() {
      // 初始化所有分屏相关数据数组
      this.screenVideos = Array(this.screenMode).fill("");
      this.hlsInstances = Array(this.screenMode).fill(null);
      this.gridLoadingStatus = Array(this.screenMode).fill(false);
      this.gridErrorStatus = Array(this.screenMode).fill(null);
    },
    getVideoEl(index) {
      const ref = this.$refs["video" + index];
      if (!ref) return null;
      return Array.isArray(ref) ? ref[0] : ref;
    },
    destroyHls(index) {
      // 清理特定索引的HLS实例和视频元素
      try {
        // 清理HLS实例
        const hls = this.hlsInstances[index];
        if (hls) {
          hls.destroy();
          this.hlsInstances[index] = null;
        }

        // 清理视频元素
        const videoEl = this.getVideoEl(index);
        if (videoEl) {
          videoEl.pause();
          videoEl.src = '';
          videoEl.load();
        }

        // 重置状态
        this.screenVideos[index] = "";
        this.gridLoadingStatus[index] = false;
        this.gridErrorStatus[index] = null;
      } catch (error) {
        console.error(`清理索引 ${index} 的HLS实例时出错:`, error);
      }
    },
    cleanupAllHlsInstances() {
      // 清理所有HLS实例
      for (let i = 0; i < this.hlsInstances.length; i++) {
        this.destroyHls(i);
      }
      // 清空所有数组
      this.screenVideos = [];
      this.hlsInstances = [];
      this.gridLoadingStatus = [];
      this.gridErrorStatus = [];
    },
    toggleHideTable() {
      this.isHideTable = !this.isHideTable;
    },
    toggleFullScreen() {
      const el = this.$refs.container;
      if (!document.fullscreenElement) {
        // 进入全屏
        const requestMethod = el.requestFullscreen || el.webkitRequestFullscreen || el.msRequestFullscreen;
        if (requestMethod) {
          requestMethod.call(el);
          this.isFullScreen = true;
          this.isHideTable = true;
        }
      } else {
        // 退出全屏
        const exitMethod = document.exitFullscreen || document.webkitExitFullscreen || document.msExitFullscreen;
        if (exitMethod) {
          exitMethod.call(document);
        }
      }
    },
    handleFullscreenChange() {
      // 监听全屏状态变化，保持isFullScreen状态同步
      this.isFullScreen = !!document.fullscreenElement;
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
    async handleScreenModeChange(mode) {
      try {
        // 1. 保存当前播放的视频地址和选中状态
        const oldVideos = [...this.screenVideos];
        const oldSelectedIndex = this.selectedGridIndex;

        // 2. 计算新的网格布局
        const cols = Math.ceil(Math.sqrt(mode));
        const rows = Math.ceil(mode / cols);

        // 3. 清空旧实例，但保留旧视频地址信息用于恢复
        this.cleanupAllHlsInstances();

        // 4. 更新分屏模式和网格布局
        this.screenMode = mode;
        this.gridCols = cols;
        this.gridRows = rows;

        // 5. 重新初始化数据数组
        this.screenVideos = Array(mode).fill('');
        this.hlsInstances = Array(mode).fill(null);
        this.gridLoadingStatus = Array(mode).fill(false);
        this.gridErrorStatus = Array(mode).fill(null);

        // 6. 在DOM更新后，恢复之前的视频播放
        this.$nextTick(() => {
          // 计算可以保留的视频数量
          const copyCount = Math.min(oldVideos.length, mode);

          // 为每个有效的视频地址重新创建播放实例
          for (let i = 0; i < copyCount; i++) {
            if (oldVideos[i]) {
              console.log(`恢复视频 [${i}]: ${oldVideos[i]}`);
              this.playVideoInGrid(i, oldVideos[i]);
            }
          }

          // 7. 如果新的分屏模式仍包含之前选中的格子，则恢复选中状态
          if (oldSelectedIndex !== null && oldSelectedIndex < mode) {
            this.selectedGridIndex = oldSelectedIndex;
          }
        });
      } catch (error) {
        console.error('分屏切换时发生错误:', error);
        this.$message.error('分屏切换失败，请重试');
        // 出错时重置为1分屏
        this.screenMode = 1;
        this.gridCols = 1;
        this.gridRows = 1;
        this.initScreenData();
      }
    },
    /**
     * 在指定分屏中播放视频
     * @param {number} index - 分屏索引
     * @param {string} hlsUrl - HLS视频流地址
     */
    async playVideoInGrid(index, hlsUrl) {
      // 重置该格子状态
      this.destroyHls(index);
      this.gridLoadingStatus[index] = true;
      this.gridErrorStatus[index] = null;
      this.screenVideos[index] = hlsUrl;

      const videoEl = this.getVideoEl(index);
      if (!videoEl) {
        this.gridLoadingStatus[index] = false;
        this.gridErrorStatus[index] = '视频元素未找到';
        return;
      }

      try {
        if (Hls.isSupported()) {
          // 使用HLS.js库播放
          this.playWithHls(index, hlsUrl, videoEl);
        } else if (videoEl.canPlayType('application/vnd.apple.mpegurl')) {
          // 对于原生支持HLS的浏览器（如Safari）
          this.playWithNativeHls(index, hlsUrl, videoEl);
        } else {
          this.handleBrowserNotSupported(index);
        }
      } catch (e) {
        console.error(`播放视频时出错 [${index}]:`, e);
        this.gridErrorStatus[index] = '播放异常';
        this.gridLoadingStatus[index] = false;
      }
    },

    /**
     * 使用HLS.js播放视频
     * @param {number} index - 分屏索引
     * @param {string} hlsUrl - HLS视频流地址
     * @param {HTMLVideoElement} videoEl - 视频元素
     */
    playWithHls(index, hlsUrl, videoEl) {
      const hls = new Hls({
        enableWorker: true,
        lowLatencyMode: true,
        maxBufferLength: 10,
        maxMaxBufferLength: 30
      });

      // 配置HLS事件监听器
      this.setupHlsEventListeners(hls, index, hlsUrl, videoEl);

      // 开始加载源并附加到媒体元素
      hls.loadSource(hlsUrl);
      hls.attachMedia(videoEl);

      // 保存HLS实例
      this.hlsInstances[index] = hls;
    },

    /**
     * 设置HLS实例的事件监听器
     * @param {Hls} hls - HLS实例
     * @param {number} index - 分屏索引
     * @param {string} hlsUrl - HLS视频流地址
     * @param {HTMLVideoElement} videoEl - 视频元素
     */
    setupHlsEventListeners(hls, index, hlsUrl, videoEl) {
      // 错误处理
      hls.on(Hls.Events.ERROR, (event, data) => this.handleHlsError(hls, index, hlsUrl, data));

      // 清单解析完成
      hls.on(Hls.Events.MANIFEST_PARSED, () => {
        this.gridLoadingStatus[index] = false;
        console.log(`视频流已解析完成 [${index}]`);
      });

      // 媒体附加完成
      hls.on(Hls.Events.MEDIA_ATTACHED, () => {
        this.attemptAutoPlay(videoEl, index);
      });
    },

    /**
     * 处理HLS错误
     * @param {Hls} hls - HLS实例
     * @param {number} index - 分屏索引
     * @param {string} hlsUrl - HLS视频流地址
     * @param {Object} data - 错误数据
     */
    handleHlsError(hls, index, hlsUrl, data) {
      console.error(`视频播放错误 [${index}]:`, data);

      // 根据错误类型处理
      if (data.fatal) {
        switch (data.type) {
          case Hls.ErrorTypes.NETWORK_ERROR:
            this.gridErrorStatus[index] = '网络错误，请检查连接';
            hls.loadSource(hlsUrl); // 尝试重新加载
            break;
          case Hls.ErrorTypes.MEDIA_ERROR:
            this.gridErrorStatus[index] = '视频格式错误';
            hls.destroy();
            break;
          default:
            this.gridErrorStatus[index] = '播放错误';
            hls.destroy();
        }
        this.gridLoadingStatus[index] = false;
      }
    },

    /**
     * 尝试自动播放视频
     * @param {HTMLVideoElement} videoEl - 视频元素
     * @param {number} index - 分屏索引
     */
    attemptAutoPlay(videoEl, index) {
      videoEl.play().catch(error => {
        console.warn(`自动播放失败，需要用户交互 [${index}]:`, error);
        // 自动播放策略：某些浏览器要求用户交互后才能播放视频
        this.$message.info('点击视频画面开始播放');
      });
    },

    /**
     * 使用浏览器原生支持播放HLS视频
     * @param {number} index - 分屏索引
     * @param {string} hlsUrl - HLS视频流地址
     * @param {HTMLVideoElement} videoEl - 视频元素
     */
    playWithNativeHls(index, hlsUrl, videoEl) {
      videoEl.src = hlsUrl;
      videoEl.play().catch(error => {
        console.warn(`自动播放失败 [${index}]:`, error);
        this.$message.info('点击视频画面开始播放');
      });
      this.gridLoadingStatus[index] = false;
    },

    /**
     * 处理浏览器不支持HLS的情况
     * @param {number} index - 分屏索引
     */
    handleBrowserNotSupported(index) {
      this.gridErrorStatus[index] = '浏览器不支持HLS播放';
      this.gridLoadingStatus[index] = false;
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

        if (res && res.status) {
          this.$message.success(res.message || "推流任务已提交");
          this.$set(row, 'hlsUrl', res.data);

          // 检查推流状态
          this.checkStreamStatus(row.id, camera);
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
    /**
     * 检查推流状态
     * @param {string} id - 摄像头ID
     * @param {Object} camera - 摄像头数据对象
     * @returns {Promise<boolean>} 推流是否成功
     */
    async checkStreamStatus(id, camera) {
      const row = camera.row;

      // 使用轮询方式检查推流状态，最多检查5次，间隔2秒
      const config = {
        maxAttempts: 5,
        interval: 2000
      };

      return this.pollStreamStatus(id, row, 0, config);
    },

    /**
     * 轮询检查推流状态
     * @param {string} id - 摄像头ID
     * @param {Object} row - 摄像头数据行
     * @param {number} attempts - 当前尝试次数
     * @param {Object} config - 轮询配置
     * @returns {Promise<boolean>} 推流是否成功
     */
    async pollStreamStatus(id, row, attempts, config) {
      attempts++;

      try {
        const res = await request({
          url: `/videobyzlm/status/${id}`,
          method: "GET",
        });

        console.log(`第${attempts}次查询推流结果 [${id}]:`, res);

        // 处理不同的状态结果
        if (res && res.status && res.statusValue === "RUNNING") {
          return this.handleStreamSuccess(row, res);
        } else if (res && res.statusValue === "failed") {
          return this.handleStreamFailure(res);
        } else if (attempts >= config.maxAttempts) {
          return this.handleStreamTimeout();
        } else {
          // 继续轮询
          return this.scheduleNextPoll(id, row, attempts, config);
        }
      } catch (e) {
        console.error(`检查推流状态失败 [${id}]:`, e);

        // 出错时继续轮询或失败
        if (attempts < config.maxAttempts) {
          return this.scheduleNextPoll(id, row, attempts, config);
        } else {
          this.$message.error('查询推流状态失败，请重试');
          return false;
        }
      }
    },

    /**
     * 处理推流成功
     * @param {Object} row - 摄像头数据行
     * @param {Object} res - 响应数据
     * @returns {boolean} 总是返回true
     */
    handleStreamSuccess(row, res) {
      // 推流成功，更新HLS地址
      this.$set(row, 'hlsUrl', res.hlsUrl);
      this.$message.success('推流成功，可以播放视频了');
      return true;
    },

    /**
     * 处理推流失败
     * @param {Object} res - 响应数据
     * @returns {boolean} 总是返回false
     */
    handleStreamFailure(res) {
      this.$message.error(res.message || "推流失败");
      return false;
    },

    /**
     * 处理推流超时
     * @returns {boolean} 总是返回false
     */
    handleStreamTimeout() {
      this.$message.warning('推流超时，可能需要手动重试');
      return false;
    },

    /**
     * 安排下一次轮询
     * @param {string} id - 摄像头ID
     * @param {Object} row - 摄像头数据行
     * @param {number} attempts - 当前尝试次数
     * @param {Object} config - 轮询配置
     * @returns {Promise<boolean>} 推流是否成功
     */
    scheduleNextPoll(id, row, attempts, config) {
      return new Promise(resolve => {
        setTimeout(async () => {
          const result = await this.pollStreamStatus(id, row, attempts, config);
          resolve(result);
        }, config.interval);
      });
    },
    async playStreamForCamera(camera) {
      console.log("播放流：", camera);
      if (!camera?.hlsUrl) {
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

      await this.playVideoInGrid(targetIndex, camera.hlsUrl);
      camera.windowIndex = targetIndex;
    },
    async stopStream(camera, showMsg = true) {
      const row = camera.row || camera;
      if (!row || !row.id) return;

      // 防止重复停止
      if (row.isStopping) return;

      try {
        // 设置停止中状态
        this.$set(row, 'isStopping', true);

        // 先调用后端停止接口（最重要！）
        try {
          await request({ url: `/videobyzlm/stop/${row.id}`, method: "GET" });
        } catch (error) {
          console.error(`调用停止推流接口失败 [${row.id}]:`, error);
          // 即使后端接口失败，也要清理前端资源
        }

        // 清理前端资源
        if (row.windowIndex != null) {
          this.destroyHls(row.windowIndex);
          this.$set(row, 'windowIndex', null);
        }

        // 重置状态
        this.$set(row, 'isPlaying', false);

        if (showMsg) {
          this.$message.success("已停止推流");
        }
      } catch (error) {
        console.error(`停止推流时出错 [${row.id}]:`, error);
        if (showMsg) {
          this.$message.error("停止推流失败，请重试");
        }
      } finally {
        // 重置停止状态
        this.$set(row, 'isStopping', false);
      }
    },
    // 重试播放视频
    retryVideo(index, hlsUrl) {
      if (!hlsUrl) {
        this.$message.warning('没有可用的视频流地址');
        return;
      }

      this.playVideoInGrid(index, hlsUrl);
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
  overflow: hidden;
}

.video-grid-item.selected {
  border: 3px solid gold;
  box-shadow: 0 0 8px rgba(255, 215, 0, 0.6);
}

/* 视频覆盖层样式 */
.video-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  font-size: 14px;
  z-index: 10;
}

/* 加载状态 */
.video-overlay.loading .loading-spinner {
  width: 30px;
  height: 30px;
  border: 3px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  border-top-color: #fff;
  animation: spin 1s ease-in-out infinite;
  margin-bottom: 10px;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* 错误状态 */
.video-overlay.error {
  background-color: rgba(255, 0, 0, 0.3);
}

.video-overlay.error .el-button {
  margin-top: 10px;
}

/* 空状态 */
.video-overlay.empty {
  background-color: rgba(0, 0, 0, 0.5);
  opacity: 0.7;
}

.overlay-text {
  margin-bottom: 10px;
  text-align: center;
  padding: 0 10px;
}

.table-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
</style>
