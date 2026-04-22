<template>
  <div class="app-container">
    <!-- 搜索条件 -->
    <el-form :inline="true" :model="queryParams" class="demo-form-inline">
      <el-form-item label="标题">
        <el-input v-model="queryParams.title" placeholder="请输入标题" clearable />
      </el-form-item>
      <el-form-item label="时间">
        <el-date-picker
          v-model="queryParams.dateRange"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
        />
      </el-form-item>
      <!-- PDF 预览 -->
      <el-dialog title="PDF预览" :visible.sync="previewDialog" width="80%">
        <iframe :src="pdfurl" width="100%" height="600px"></iframe>
      </el-dialog>

      <!-- Word 预览 -->
      <el-dialog title="Word预览" :visible.sync="wordPreviewDialog" width="80%">
        <div ref="wordContainer" style="height:600px; overflow:auto;"></div>
      </el-dialog>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">搜索</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col>
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
      </el-col>
    </el-row>

    <!-- 通知列表 -->
    <el-table :data="filteredList" border style="width: 100%">
      <el-table-column type="index" label="序号" width="60" />
      <el-table-column prop="title" label="标题" width="200">
        <template slot-scope="scope">
          <el-tooltip effect="dark" :content="scope.row.description" placement="top">
            <span>{{ scope.row.title }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" width="300">
        <template slot-scope="scope">
          <el-tooltip
            effect="dark"
            :content="scope.row.description"
            placement="top"
          >
            <!-- 这里加个样式，让文字超出时显示省略号 -->
            <span class="desc-ellipsis">{{ scope.row.description }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="发布时间"  width="150" />
      <el-table-column label="附件">
        <template slot-scope="scope">
          <el-button v-if="scope.row.attachment" type="text" @click="previewAttachment(scope.row)">
            预览
          </el-button>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" @click="deleteNotice(scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增通知弹窗 -->
    <el-dialog
      title="新增通知"
      :visible.sync="open"
      width="600px"
      :close-on-click-modal="false"
    >
      <el-form :model="form" ref="formRef" label-width="80px">
        <el-form-item label="标题" prop="title" :rules="[{ required: true, message: '请输入标题' }]">
          <el-input v-model="form.title" placeholder="请输入通知标题" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="form.description" :rows="4" placeholder="请输入通知描述" />
        </el-form-item>
        <el-form-item label="附件">
          <el-upload
            action="#"
            :file-list="form.fileList"
            :auto-upload="false"
            :on-change="handleFileChange"
          >
            <el-button size="small" type="primary">选择文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确认新增</el-button>
      </div>
    </el-dialog>

    <!-- 预览文件弹窗 -->
<!--    <el-dialog-->
<!--      title="文件预览"-->
<!--      :visible.sync="previewVisible"-->
<!--      width="800px"-->
<!--      top="5vh"-->
<!--    >-->
<!--      <div v-if="previewType === 'image'">-->
<!--        <img :src="previewUrl" alt="预览图片" style="max-width:100%; max-height:70vh;" />-->
<!--      </div>-->
<!--      <div v-else-if="previewType === 'pdf'">-->
<!--        <embed :src="previewUrl" type="application/pdf" width="100%" height="600px" />-->
<!--      </div>-->
<!--      <div v-else-if="previewType === 'text'">-->
<!--        <iframe :src="previewUrl" width="100%" height="600px"></iframe>-->
<!--      </div>-->
<!--      <div v-else>-->
<!--        <p>暂不支持该文件类型的预览，请下载查看。</p>-->
<!--      </div>-->
<!--    </el-dialog>-->
    <!-- PDF 预览 -->
    <el-dialog title="PDF预览" :visible.sync="previewDialog" width="80%">
      <iframe :src="pdfurl" width="100%" height="600px"></iframe>
    </el-dialog>

    <!-- Word 预览 -->
    <el-dialog title="Word预览" :visible.sync="wordPreviewDialog" width="80%">
      <div ref="wordContainer" style="height:600px; overflow:auto;"></div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import axios from "axios";
import docx from "docx-preview";
import {rankList, updateFileinfo} from "@/api/regulations/fileinfo";

export default {
  name: "NoticeManage",
  data() {
    return {
      previewDialog: false,
      wordPreviewDialog: false,
      pdfurl: "",
      open: false,
      previewVisible: false,
      previewUrl: "",
      previewType: "",
      queryParams: {
        title: "",
        dateRange: [],
      },
      form: {
        title: "",
        description: "",
        fileList: [],
        fileUrl: null,
      },

      noticeList: [
        // {
        //   title: "系统维护通知",
        //   description: "本周六晚 22:00-24:00 系统维护，请提前保存数据。",
        //   date: "2025-09-07",
        //   fileUrl: null,
        //   fileType: "",
        // },
        // {
        //   title: "宿舍线路检查维修",
        //   description: "本周日晚 22:00-24:00 请大家提前做好准备。",
        //   date: "2025-09-08",
        //   fileUrl: null,
        //   fileType: "",
        // },
      ],
    };
  },
  computed: {
    filteredList() {
      return this.noticeList.filter((item) => {
        const matchTitle = this.queryParams.title
          ? item.title.includes(this.queryParams.title)
          : true;
        const matchDate =
          this.queryParams.dateRange.length === 2
            ? item.date >= this.queryParams.dateRange[0] &&
            item.date <= this.queryParams.dateRange[1]
            : true;
        return matchTitle && matchDate;
      });
    },
  },
  mounted() {
    this.getNoticeList();
  },
  methods: {
    // 打开新增弹窗
    handleAdd() {
      this.open = true;
      this.form = { title: "", description: "", fileList: [], attachmentUrl: "" };
    },
    // 选择文件后处理
    handleFileChange(file, fileList) {
      this.form.fileList = fileList;
      if (file.raw) {
        // 这里仍然只做预览，不上传
        this.form.attachmentUrl = URL.createObjectURL(file.raw);
        const type = file.raw.type;
        if (type.startsWith("image/")) this.form.fileType = "image";
        else if (type === "application/pdf") this.form.fileType = "pdf";
        else if (type.startsWith("text/")) this.form.fileType = "text";
        else this.form.fileType = "other";
      }
    },
    // 提交新增
    submitForm() {
      this.$refs.formRef.validate((valid) => {
        if (!valid) return;
        request({
          url: "/notice/add",
          method: "post",
          data: {
            title: this.form.title,
            description: this.form.description,
            attachment: this.form.attachmentUrl, // 你目前没有上传接口，就传URL
          },
        })
          .then(() => {
            this.$message.success("新增成功");
            this.getNoticeList(); // ✅ 后端刷新
            this.open = false;
          })
          .catch(() => {
            this.$message.error("新增失败");
          });
      });
    },
    // 获取列表（页面加载 / 操作后刷新使用）
    getNoticeList() {
      request({
        url: "/notice/list",
        method: "get",
      })
        .then((res) => {
          this.noticeList = res; // 后端直接返回数组
        })
        .catch(() => {
          this.$message.error("加载列表失败");
        });
    },
    // 搜索（按标题 + 时间范围）
    handleQuery() {
      const start = this.queryParams.dateRange?.[0] || "";
      const end = this.queryParams.dateRange?.[1] || "";
      request({
        url: "/notice/select",
        method: "get",
        params: {
          title: this.queryParams.title,
          start,
          end,
        },
      })
        .then((res) => {
          this.noticeList = res;
        })
        .catch(() => {
          this.$message.error("搜索失败");
        });
    },
    // 重置
    resetQuery() {
      this.queryParams = { title: "", dateRange: [] };
      this.getNoticeList();
    },
    // 删除
    deleteNotice(row) {
      if (!row || !row.id) {
        this.$message.error("删除失败：找不到ID");
        return;
      }
      this.$confirm("确认删除该公告吗？", "提示", { type: "warning" }).then(() => {
        request({
          url: `/notice/delete/${row.id}`, // 注意需要后端返回 id 字段
          method: "delete",
        })
          .then(() => {
            this.$message.success("删除成功");
            this.getNoticeList();
          })
          .catch(() => {
            this.$message.error("删除失败");
          });
      });
    },
    // 预览
    // previewFile(url) {
    //   this.previewUrl = row.attachment;
    //   this.previewType = row.fileType || "other";
    //   this.previewVisible = true;
    // },
    previewAttachment(row) {
      if (!row.attachment) {
        this.$message.error("附件地址为空");
        return;
      }

      const url = row.attachment;
      const lower = url.toLowerCase();

      // 如果以后你在后端把 file_type 填好，也可以用 row.fileType 来判断
      if (lower.endsWith(".pdf")) {
        // PDF 预览
        this.previewDialog = true;
        this.pdfurl = url;
      } else if (lower.endsWith(".doc") || lower.endsWith(".docx")) {
        // Word 预览
        this.wordPreviewDialog = true;
        axios({
          url,
          method: "get",
          responseType: "blob",
        }).then((res) => {
          const container = this.$refs.wordContainer;
          container.innerHTML = "";
          docx.renderAsync(res.data, container);
        });
      } else {
        // 其他类型就直接新窗口打开，或者按需要提示
        window.open(url);
      }
    }
  },
};
</script>
