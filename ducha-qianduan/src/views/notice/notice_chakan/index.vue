<template>
  <div class="app-container">
    <el-card class="box-card" shadow="always">
      <div slot="header" class="clearfix">
        <span>通知查看</span>
      </div>

      <el-table :data="noticeList" style="width: 100%">
        <el-table-column prop="title" label="标题" width="200" />
        <el-table-column prop="description" label="描述" />

        <!-- 附件列：显示文件名 + 预览按钮 -->
        <el-table-column prop="fileName" label="附件" width="240">
          <template slot-scope="scope">
            <span v-if="scope.row.fileUrl">{{ scope.row.fileName }}</span>
            <span v-else>-</span>
            <el-button
              v-if="scope.row.fileUrl"
              size="mini"
              type="text"
              @click="previewNotice(scope.row)"
            >
              预览
            </el-button>
          </template>
        </el-table-column>

        <!-- 操作列：这里也给一个预览按钮，实际上和上面一致 -->
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              :disabled="!scope.row.fileUrl"
              @click="previewNotice(scope.row)"
            >
              预览
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 预览弹窗 -->
    <el-dialog
      title="通知预览"
      :visible.sync="previewDialogVisible"
      width="60%"
      top="5vh"
    >
      <div>
        <h3>{{ previewData.title }}</h3>
        <p>{{ previewData.description }}</p>

        <div v-if="previewData.fileUrl">
          <!-- PDF -->
          <embed
            v-if="isPdf(previewData.fileName)"
            :src="previewData.fileUrl"
            type="application/pdf"
            width="100%"
            height="600px"
          />
          <!-- 图片 -->
          <img
            v-else-if="isImage(previewData.fileName)"
            :src="previewData.fileUrl"
            alt="通知附件"
            style="max-width:100%; max-height:600px;"
          />
          <!-- 其他类型：直接给下载链接 / 新窗口打开 -->
          <el-link
            v-else
            :href="previewData.fileUrl"
            target="_blank"
            type="primary"
          >
            点击下载附件
          </el-link>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="previewDialogVisible = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "NoticeView",
  data() {
    return {
      previewDialogVisible: false,
      noticeList: [],
      previewData: {
        title: "",
        description: "",
        fileUrl: "",
        fileName: "",
      },
    };
  },
  created() {
    this.getNoticeList();
  },
  methods: {
    // 从后端加载通知列表
    getNoticeList() {
      request({
        url: "/notice/list",
        method: "get",
      })
        .then((res) => {
          // 后端大概率返回的是 attachment 字段，这里统一映射到 fileUrl / fileName
          this.noticeList = (res || []).map((item) => {
            const fileUrl = item.attachment || item.fileUrl || "";
            let fileName = item.fileName;

            // 如果后端没有 fileName，就从 URL 里简单截取一下
            if (!fileName && fileUrl) {
              const parts = fileUrl.split("/");
              fileName = parts[parts.length - 1] || "附件";
            }

            return {
              ...item,
              fileUrl,
              fileName,
            };
          });
        })
        .catch(() => {
          this.$message.error("加载列表失败");
        });
    },

    // 打开预览弹窗
    previewNotice(notice) {
      if (!notice.fileUrl) {
        this.$message.warning("该通知暂无附件");
        return;
      }
      this.previewData = { ...notice };
      this.previewDialogVisible = true;
    },

    // 判断是否为 PDF：fileName 为空时回退用 URL 判断
    isPdf(fileName) {
      const name = (fileName || this.previewData.fileUrl || "").toLowerCase();
      return name.endsWith(".pdf");
    },

    // 判断是否为图片：同样回退到 URL
    isImage(fileName) {
      const name = fileName || this.previewData.fileUrl || "";
      return /\.(jpg|jpeg|png|gif|bmp)$/i.test(name);
    },
  },
};
</script>

<style scoped>
.box-card {
  margin-bottom: 20px;
}
</style>
