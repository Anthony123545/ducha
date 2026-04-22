<template> 
  <div class="app-container">
    <!-- 新增公告 -->
    <el-card shadow="always">
      <div slot="header">
        <span>新增公告</span>
      </div>
      <el-form
        :model="form"
        :rules="rules"
        ref="formRef"
        label-width="80px"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>

        <el-form-item label="正文" prop="content">
          <el-input
            type="textarea"
            v-model="form.content"
            placeholder="请输入公告正文"
            :rows="4"
          />
        </el-form-item>

        <el-form-item label="发布人" prop="publisher">
          <el-input v-model="form.publisher" placeholder="请输入发布人" />
        </el-form-item>

        <el-form-item label="发布时间" prop="publishTime">
          <el-date-picker
            v-model="form.publishTime"
            type="datetime"
            placeholder="选择日期时间"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="success" @click="addNotice">新增</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 公告列表 -->
    <el-card shadow="always" style="margin-top: 20px;">
      <div slot="header">
        <span>公告列表</span>
      </div>

      <el-table :data="noticeList" border style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          width="60"
          align="center"
        />

        <el-table-column label="标题" width="250">
          <template slot-scope="scope">
            <!-- 鼠标悬停预览详情 -->
            <el-popover trigger="hover" placement="top" width="300">
              <p><b>标题：</b>{{ scope.row.title }}</p>
              <p><b>正文：</b>{{ scope.row.content }}</p>
              <p><b>发布人：</b>{{ scope.row.publisher }}</p>
              <p><b>发布时间：</b>{{ scope.row.publishTime }}</p>
              <div
                slot="reference"
                class="title-cell"
              >
                {{ scope.row.title }}
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column prop="publisher" label="发布人" width="120" />
        <el-table-column prop="publishTime" label="发布时间" width="180" />

        <el-table-column label="状态" width="100">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.status === 1 ? 'success' : 'info'"
              disable-transitions
            >
              {{ scope.row.status === 1 ? '已发布' : '未发布' }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="320">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="editNotice(scope.row)"
            >
              编辑
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="deleteNotice(scope.row)"
            >
              删除
            </el-button>
            <el-button
              size="mini"
              type="success"
              :disabled="scope.row.status === 1"
              @click="publishNotice(scope.row)"
            >
              发布
            </el-button>
            <el-button
              size="mini"
              type="info"
              @click="viewNotice(scope.row)"
            >
              查看
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 编辑弹窗 -->
    <el-dialog
      title="编辑公告"
      :visible.sync="editDialogVisible"
      width="600px"
    >
      <el-form
        :model="editForm"
        :rules="rules"
        ref="editFormRef"
        label-width="80px"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="editForm.title" />
        </el-form-item>

        <el-form-item label="正文" prop="content">
          <el-input
            type="textarea"
            v-model="editForm.content"
            :rows="4"
          />
        </el-form-item>

        <el-form-item label="发布人" prop="publisher">
          <el-input v-model="editForm.publisher" />
        </el-form-item>

        <el-form-item label="发布时间" prop="publishTime">
          <el-date-picker
            v-model="editForm.publishTime"
            type="datetime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          />
        </el-form-item>
      </el-form>

      <div slot="footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmEdit">确定</el-button>
      </div>
    </el-dialog>

    <!-- 查看弹窗 -->
    <el-dialog
      title="查看公告"
      :visible.sync="viewDialogVisible"
      width="600px"
      :close-on-click-modal="false"
    >
      <el-form label-width="80px">
        <el-form-item label="标题">
          <span>{{ viewForm.title }}</span>
        </el-form-item>

        <el-form-item label="正文">
          <div
            style="white-space: pre-wrap; border: 1px solid #e5e5e5; padding: 10px; border-radius: 4px; background: #fafafa;"
          >
            {{ viewForm.content }}
          </div>
        </el-form-item>

        <el-form-item label="发布人">
          <span>{{ viewForm.publisher }}</span>
        </el-form-item>

        <el-form-item label="发布时间">
          <span>{{ viewForm.publishTime }}</span>
        </el-form-item>

        <el-form-item label="状态">
          <el-tag :type="viewForm.status === 1 ? 'success' : 'info'">
            {{ viewForm.status === 1 ? '已发布' : '未发布' }}
          </el-tag>
        </el-form-item>
      </el-form>

      <div slot="footer">
        <el-button type="primary" @click="viewDialogVisible = false">
          关闭
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "NoticeManage",
  data() {
    return {
      // 新增表单
      form: {
        id: null,
        title: "",
        content: "",
        publisher: "",
        publishTime: ""
      },
      // 列表数据（从后端获取）
      noticeList: [],
      // 编辑弹窗
      editDialogVisible: false,
      editForm: {
        id: null,
        title: "",
        content: "",
        publisher: "",
        publishTime: "",
        status: 0
      },
      // 查看弹窗
      viewDialogVisible: false,
      viewForm: {
        id: null,
        title: "",
        content: "",
        publisher: "",
        publishTime: "",
        status: 0
      },
      // 校验规则
      rules: {
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        content: [
          { required: true, message: "请输入公告正文", trigger: "blur" }
        ],
        publisher: [
          { required: true, message: "请输入发布人", trigger: "blur" }
        ],
        publishTime: [
          { required: true, message: "请选择发布时间", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getNoticeList();
  },
  methods: {
    /** 获取公告列表 */
    getNoticeList() {
      request({
        url: "/web/announcement/list",
        method: "get"
      })
        .then(res => {
          this.noticeList = res || [];
        })
        .catch(() => {
          this.$message.error("加载公告列表失败！");
        });
    },

    /** 新增公告 */
    addNotice() {
      this.$refs.formRef.validate(valid => {
        if (!valid) return;
        request({
          url: "/web/announcement/add",
          method: "post",
          data: this.form
        })
          .then(() => {
            this.$message.success("新增成功！");
            this.resetForm();
            this.getNoticeList();
          })
          .catch(() => {
            this.$message.error("新增失败！");
          });
      });
    },

    /** 重置新增表单 */
    resetForm() {
      if (this.$refs.formRef) {
        this.$refs.formRef.resetFields();
      }
      this.form = {
        id: null,
        title: "",
        content: "",
        publisher: "",
        publishTime: ""
      };
    },

    /** 删除公告 */
    deleteNotice(row) {
      this.$confirm("确认删除该公告吗？", "提示", { type: "warning" })
        .then(() => {
          return request({
            url: `/web/announcement/delete/${row.id}`,
            method: "delete"
          });
        })
        .then(() => {
          this.$message.success("删除成功！");
          this.getNoticeList();
        })
        .catch(err => {
          if (err !== "cancel") {
            this.$message.error("删除失败！");
          }
        });
    },

    /** 打开编辑弹窗 */
    editNotice(row) {
      this.editForm = { ...row };
      this.editDialogVisible = true;
    },

    /** 确认编辑 */
    confirmEdit() {
      this.$refs.editFormRef.validate(valid => {
        if (!valid) return;
        request({
          url: "/web/announcement/update",
          method: "put",
          data: this.editForm
        })
          .then(() => {
            this.$message.success("修改成功！");
            this.editDialogVisible = false;
            this.getNoticeList();
          })
          .catch(() => {
            this.$message.error("修改失败！");
          });
      });
    },

    /** 发布公告 */
    publishNotice(row) {
      request({
        url: `/web/announcement/publish/${row.id}`,
        method: "put"
      })
        .then(() => {
          this.$message.success("公告已发布！");
          this.getNoticeList();
        })
        .catch(() => {
          this.$message.error("发布失败！");
        });
    },

    /** 查看公告 */
    viewNotice(row) {
      this.viewForm = { ...row };
      this.viewDialogVisible = true;
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 20px;
}

.title-cell {
  cursor: pointer;
  color: #409eff;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
