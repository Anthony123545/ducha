<template>
  <div class="video-manage-container">
    <!-- 搜索和新增 -->
    <div class="search-add-bar">
      <!-- 选择框：改变后自动查询 -->
      <el-select
        v-model="searchDeviceType"
        placeholder="设备类型"
        size="small"
        class="search-select"
        clearable
        @change="handleSearch"
      >
        <el-option
          style="color: black"
          v-for="item in deviceTypeOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <!-- 输入框：回车自动查询 -->
      <el-input
        v-model="searchName"
        placeholder="视频名称"
        size="small"
        class="search-input"
        clearable
        @keyup.enter.native="handleSearch"
      />
      <el-button
        type="primary"
        size="small"
        @click="handleSearch"
        class="search-btn"
        >查询</el-button
      >
      <el-button type="primary" size="small" @click="handleAdd">新增</el-button>
      <el-button
        type="danger"
        size="small"
        @click="handleBatchDelete"
        :disabled="!multipleSelection.length"
        >批量删除</el-button
      >
      <el-button size="small" type="success" @click="selectFile"
        >Excel 导入</el-button
      >
      <input
        type="file"
        ref="fileInput"
        style="display: none"
        @change="handleFileChange"
      />
    </div>

    <!-- 视频表格 -->
    <el-table
      :data="tableData"
      class="video-table"
      border
      :header-cell-style="changeHeaderCellStyle"
      @selection-change="handleSelectionChange"
      v-loading="loading"
      element-loading-text="加载中..."
    >
      <el-table-column type="selection" width="60" align="center" />
      <el-table-column
        label="序号"
        width="60"
        align="center"
        :index="(index) => (currentPage - 1) * pageSize + index + 1"
        type="index"
      />
      <el-table-column
        prop="name"
        label="视频名称"
        min-width="80"
        align="center"
        sortable
      />
      <el-table-column
        prop="deviceType"
        label="设备类型"
        min-width="80"
        align="center"
        :formatter="formatDeviceType"
        sortable
      />
      <el-table-column
        prop="ip"
        label="IP地址"
        min-width="60"
        align="center"
        sortable
      />
      <el-table-column
        prop="username"
        label="用户名"
        min-width="50"
        align="center"
        sortable
      />
      <el-table-column
        prop="password"
        label="密码"
        min-width="50"
        align="center"
        sortable
      >
        <template #default="{row}">
          <el-input
            v-model="row.password"
            type="password"
            :disabled="true"
            size="small"
          ></el-input>
        </template>
      </el-table-column>
      <el-table-column
        prop="channelName"
        label="通道名称"
        min-width="50"
        align="center"
        sortable
      />
      <el-table-column
        prop="channel"
        label="通道号"
        min-width="50"
        align="center"
        sortable
      />
      <el-table-column
        prop="streamType"
        label="码流类型"
        min-width="50"
        align="center"
        sortable
      >
        <template #default="scope">
          <span>
            {{ streamTypeMap[scope.row.streamType] || '-' }}
          </span>
        </template>
      </el-table-column>
      <el-table-column
        prop="streamCode"
        label="码流号"
        min-width="50"
        align="center"
        sortable
      />
      <el-table-column
        prop="rtspPort"
        label="RTSP端口号"
        min-width="50"
        align="center"
        sortable
      />
      <el-table-column
        prop="rtspUrl"
        label="RTSP地址"
        min-width="200"
        align="center"
        sortable
      >
        <template #default="{ row }">
          <a :href="row.rtspUrl" target="_blank" class="rtsp-link">{{ row.rtspUrl }}</a>
        </template>
      </el-table-column>
      <el-table-column
        prop="enable"
        label="启用"
        width="80"
        align="center"
        sortable
      >
        <template #default="{ row }">
          <el-tag :type="row.enable ? 'success' : 'danger'">
            {{ row.enable ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>

      <!-- 操作列 -->
      <el-table-column label="操作" fixed="right" width="150" align="center">
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
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      :page-sizes="[10, 15, 20, 25, 30]"
      layout="total, prev, pager, next, jumper, sizes"
      @current-change="handlePageChange"
      @size-change="handleSizeChange"
    />
    <!-- 新增/编辑弹窗 -->
    <VideoEditDialog
      :visible.sync="dialogVisible"
      :video-data="currentVideoData"
      @submit="handleVideoSubmit"
      @cancel="handleVideoCancel"
    />
  </div>
</template>

<script>
import VideoEditDialog from "./VideoEditDialog.vue";
import request from "@/utils/request";
import { debounce } from 'lodash';

export default {
  name: "VideoManage",
  components: {
    VideoEditDialog,
  },
  data() {
    return {
      // 搜索条件
      searchName: "",
      searchDeviceType: "",
      multipleSelection: [], // 多选数组
      excelFile: null, // Excel 文件
      // 设备类型
      deviceTypeOptions: [
        { label: "普通摄像头", value: "1" },
        { label: "人员通道闸机摄像头", value: "2" },
        { label: "车辆通道闸机摄像头", value: "3" },
      ],
      // 码流类型映射
      streamTypeMap: {
        1: '主码流',
        2: '子码流'
      },
      // 表格数据
      tableData: [],
      loading: false,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      // 弹窗
      dialogVisible: false,
      currentVideoData: {},
    };
  },
  mounted() {
    this.getVideoInfo();
  },
  methods: {
    selectFile() {
      this.$refs.fileInput.click();
    },
    handleFileChange(e) {
      const file = e.target.files[0];
      if (!file) return;
      const isExcel =
        file.type === "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" ||
        file.type === "application/vnd.ms-excel" ||
        file.name.endsWith('.xlsx') ||
        file.name.endsWith('.xls');
      if (!isExcel) {
        this.$message.error("只能上传 Excel 文件");
        e.target.value = null; // 重置文件输入
        return;
      }
      this.uploadExcel(file);
      e.target.value = null; // 上传完重置
    },
    async uploadExcel(file) {
      const formData = new FormData();
      formData.append("file", file);
      try {
        this.loading = true;
        const res = await request({
          url: "/video/importExcel",
          method: "POST",
          data: formData,
          headers: { "Content-Type": "multipart/form-data" },
        });
        if (res && res.status) {
          this.$message.success(res.message || "导入成功");
          this.handleSearch();
        } else {
          const errorMsg = res?.message || '导入失败';
          const details = res?.errors?.join('<br/>') || '';
          this.$message({
            dangerouslyUseHTMLString: true,
            message: details ? `${errorMsg}，请查看错误信息：<br/>${details}` : errorMsg,
            type: "error",
          });
        }
      } catch (err) {
        console.error("上传失败", err);
        this.$message.error("上传失败，请检查网络或后端接口");
      } finally {
        this.loading = false;
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 添加防抖处理
    handleSearch: debounce(function() {
      this.currentPage = 1;
      this.getVideoInfo();
    }, 300),
    handleAdd() {
      this.currentVideoData = {};
      this.dialogVisible = true;
    },
    handleEdit(row) {
      // 深拷贝避免直接修改表格数据
      this.currentVideoData = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    async handleDelete(row) {
      try {
        await this.$confirm(
          `确认删除视频「${row.name || row.ip || row.id}」吗？`,
          "提示",
          {
            type: "warning",
          }
        );
        this.loading = true;
        const res = await request({
          url: `/video/delete/${row.id}`,
          method: "DELETE",
        });
        if (res && res.status) {
          this.$message.success(res.message || "删除成功");
          await this.getVideoInfo();
        } else {
          this.$message.error(res?.message || "删除失败");
        }
      } catch (error) {
        // 用户取消操作不显示错误信息
        if (error !== 'cancel') {
          this.$message.error("删除操作失败");
        }
      } finally {
        this.loading = false;
      }
    },
    async handleBatchDelete() {
      if (!this.multipleSelection.length) {
        this.$message.warning("请先选择要删除的视频");
        return;
      }
      try {
        await this.$confirm(
          `确认删除选中的 ${this.multipleSelection.length} 个视频吗？`,
          "提示",
          { type: "warning" }
        );
        const ids = this.multipleSelection.map((item) => item.id);
        this.loading = true;
        const res = await request({
          url: "/video/batchDelete",
          method: "POST",
          data: { ids },
        });
        if (res && res.status) {
          this.$message.success(res.message || "批量删除成功");
        } else if (res?.errors && res.errors.length) {
          // 处理部分失败的情况
          this.$message({
            dangerouslyUseHTMLString: true,
            type: "error",
            message:
              `${res?.message || "部分视频删除失败"}<br/>${res.errors.join("<br/>")}`,
          });
        } else {
          this.$message.error(res?.message || "批量删除失败");
        }
        this.handleSearch();
      } catch (error) {
        // 用户取消操作不显示错误信息
        if (error !== 'cancel') {
          this.$message.error("批量删除失败，请检查网络或后端接口");
        }
      } finally {
        this.loading = false;
      }
    },
    // 表格样式修改
    changeHeaderCellStyle(row, column, rowIndex, columnIndex) {
      return "background: #004279; color: #fff;";
    },
    handlePageChange(page) {
      this.currentPage = page;
      this.getVideoInfo();
    },
    handleSizeChange(size) {
      this.currentPage = 1;
      this.pageSize = size;
      this.getVideoInfo();
    },
    async handleVideoSubmit(videoData) {
      const url = videoData.id ? "/video/update" : "/video/add";
      try {
        this.loading = true;
        const res = await request({
          url,
          method: "POST",
          data: videoData
        });
        if (res && res.status === true) {
          this.$message.success(res.message || "操作成功");
          this.dialogVisible = false;
          await this.getVideoInfo();
        } else {
          this.$message.error(res?.message || "操作失败");
        }
      } catch (error) {
        console.error("提交视频失败:", error);
        this.$message.error("提交视频失败，请检查网络或后端接口");
      } finally {
        this.loading = false;
      }
    },
    handleVideoCancel() {
      this.dialogVisible = false;
    },
    async getVideoInfo() {
      this.loading = true;
      try {
        const data = {
          name: this.searchName?.trim() || null, // 去掉多余空格，避免 "" 导致条件错乱
          deviceType: this.searchDeviceType || null, // 为空时传 null
          page: this.currentPage,
          size: this.pageSize,
        };
        // 发起分页+查询请求
        const res = await request({
          url: "/video/list",
          method: "POST",
          data: data,
        });
        // 判断返回结构
        if (res && res.list) {
          this.tableData = res.list || [];
          this.total = res.total || 0;
        } else {
          this.tableData = [];
          this.total = 0;
          this.$message.warning("未获取到视频数据");
        }
      } catch (error) {
        console.error("获取视频信息失败:", error);
        this.$message.error("获取视频列表失败，请检查网络或后端接口！");
      } finally {
        this.loading = false;
      }
    },
    // 格式化设备类型
    formatDeviceType(row) {
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
.video-manage-container {
  padding: 10px;
}

.search-add-bar {
  height: 50px;
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
}

.search-select {
  width: 200px;
}

.search-input {
  width: 200px;
}

.search-btn {
  margin-left: 10px;
}

.video-table {
  width: 100%;
  margin-top: 10px;
}

.custom-pagination {
  margin-top: 20px;
  text-align: right;
}

.rtsp-link {
  color: #409EFF;
  text-decoration: none;
  max-width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: inline-block;
}

.rtsp-link:hover {
  color: #66b1ff;
  text-decoration: underline;
}
</style>
