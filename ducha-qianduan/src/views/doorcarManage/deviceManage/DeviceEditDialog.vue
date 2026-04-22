<template>
  <el-dialog
    :visible.sync="localVisible"
    :title="isEdit ? '编辑设备信息' : '新增设备信息'"
    @close="handleCancel"
    custom-class="custom-dialog"
  >
    <el-form
      ref="deviceForm"
      :model="formData"
      :rules="formRules"
      label-width="140px"
      class="device-form"
    >
      <!-- 一、基本信息 -->
      <el-divider content-position="center">基本信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="设备名称" prop="name">
            <el-input v-model="formData.name" placeholder="请输入设备名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="设备序列号" prop="serialNumber">
            <el-input
              v-model="formData.serialNumber"
              placeholder="请输入序列号"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="设备类型" prop="type">
            <el-select
              v-model="formData.type"
              placeholder="请选择设备类型"
              disabled
            >
              <el-option label="人行摆闸" value="peopleDoor" />
              <el-option label="车道闸" value="carDoor" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="设备位置" prop="location">
            <el-input
              v-model="formData.location"
              placeholder="请输入设备位置"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="进出方向" prop="accessPermission">
            <el-select
              v-model="formData.accessPermission"
              placeholder="请选择进出方向"
            >
              <el-option label="只准进" value="in" />
              <el-option label="只准出" value="out" />
              <el-option label="既可进也可出" value="all" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否更新出入状态" prop="isUpdateStatus">
            <el-switch
              v-model="formData.isUpdateStatus"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="true"
              :inactive-value="false"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="IP地址" prop="ip">
            <el-input v-model="formData.ip" placeholder="请输入IP" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="端口号" prop="port">
            <el-input-number v-model.number="formData.port" :min="1" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="账号" prop="account">
            <el-input v-model="formData.account" placeholder="请输入账号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="formData.password"
              type="password"
              show-password
              placeholder="请输入密码"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="固件版本" prop="firmwareVersion">
            <el-input
              v-model="formData.firmwareVersion"
              placeholder="请输入固件版本"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="启用" prop="enable">
            <el-switch
              v-model="formData.enable"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="true"
              :inactive-value="false"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="备注" prop="remark" style="flex: 1">
            <el-input v-model="formData.remark" placeholder="请输入备注" />
          </el-form-item>
        </el-col>
      </el-row>
      <!--二、门体信息 -->
      <el-divider content-position="center">门信息</el-divider>
      <el-row>
        <el-input
          size="small"
          v-model.number="formData.doorInfo.doorIndex"
          placeholder="请输入门序号"
          style="width: 150px; margin-right: 10px"
        />
        <el-input
          size="small"
          v-model="formData.doorInfo.doorName"
          placeholder="请输入门名称"
          style="width: 200px; margin-right: 10px"
        />
        <el-select
          size="small"
          v-model="formData.doorInfo.direction"
          placeholder="选择方向"
          style="width: 120px; margin-right: 10px;"
        >
          <el-option label="进" value="in" />
          <el-option label="出" value="out" />
          <el-option label="都允许" value="all" />
        </el-select>
        <el-input
          size="small"
          v-model="formData.doorInfo.remark"
          placeholder="请输入备注"
          style="width: 150px; margin-right: 10px"
        />
        <el-button size="small" type="primary" @click="addDoor"
        >添加门</el-button
        >
        <!-- 门列表 -->
        <el-table
          :data="formData.doorInfo.doors"
          style="width: 100%; margin-top: 10px"
          border
        >
          <el-table-column prop="doorIndex" label="门序号" width="80" />
          <el-table-column prop="doorName" label="名称" width="300" />
          <el-table-column label="方向" width="200">
            <template slot-scope="scope">
              <span>{{ getDirectionText(scope.row.direction) }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="remark" label="备注" min-width="100" />
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <!-- 使用 doorId 删除 -->
              <el-button
                size="small"
                type="danger"
                @click="deleteDoor(scope.row.doorId)"
              >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-row>
      <!-- 三、摄像头信息 -->
      <el-divider content-position="center">摄像头信息</el-divider>
      <el-row>
        <!-- 摄像头序号 -->
        <el-input
          size="small"
          v-model.number="formData.videoInfo.videoIndex"
          placeholder="请输入摄像头序号"
          style="width: 150px; margin-right: 10px"
        />
        <!-- 选择摄像头 -->
        <el-select
          size="small"
          v-model="formData.videoInfo.videoId"
          placeholder="选择摄像头"
          style="width: 200px; margin-right: 10px"
        >
          <el-option
            v-for="item in videoOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
        <!-- 方向选择 -->
        <el-select
          size="small"
          v-model="formData.videoInfo.direction"
          placeholder="选择方向"
          style="width: 120px; margin-right: 10px;"
        >
          <el-option label="进" value="in" />
          <el-option label="出" value="out" />
          <el-option label="都允许" value="both" />
        </el-select>
        <!-- 用户可输入的备注 -->
        <el-input
          size="small"
          v-model="formData.videoInfo.remark"
          placeholder="备注"
          style="width: 150px; margin-right: 10px"
        />
        <el-button size="small" type="primary" @click="addVideo"
        >添加摄像头</el-button
        >
        <!-- 摄像头列表 -->
        <el-table
          :data="formData.videoInfo.videos"
          style="width: 100%; margin-top: 10px"
          border
        >
          <el-table-column prop="videoIndex" label="序号" width="80" />
          <el-table-column prop="videoName" label="名称" width="300" />
          <el-table-column label="方向" width="200">
            <template slot-scope="scope">
              <span>{{ getDirectionText(scope.row.direction) }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="remark" label="备注" min-width="100" />
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button
                size="small"
                type="danger"
                @click="deleteVideo(scope.row.videoId)"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleSubmit">保存</el-button>
    </span>
  </el-dialog>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "DeviceEditDialog",
  props: {
    visible: Boolean,
    deviceData: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      localVisible: this.visible,
      isEdit: false,
      videoOptions: [],
      formData: this.getEmptyForm(), // 使用工厂函数初始化 formData
      formRules: {
        name: [{ required: true, message: "请输入设备名称", trigger: "blur" }],
        serialNumber: [
          { required: true, message: "请输入设备序列号", trigger: "blur" },
        ],
        type: [
          { required: true, message: "请选择设备类型", trigger: "change" },
        ],
        accessPermission: [
          { required: true, message: "请选择进出方向", trigger: "change" },
        ],
        ip: [
          { required: true, message: "请输入设备IP", trigger: "blur" },
          {
            pattern:
              /^(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}$/,
            message: "请输入合法IP地址",
            trigger: "blur",
          },
        ],
        port: [
          { required: true, message: "请输入端口号", trigger: "blur" },
          { type: "number", message: "端口号必须为数字", trigger: "blur" },
        ],
        account: [
          { required: true, message: "请输入登录账号", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入登录密码", trigger: "blur" },
        ],
      },
    };
  },
  watch: {
    visible(val) {
      this.localVisible = val;
    },
    localVisible(val) {
      this.$emit("update:visible", val);
    },
    deviceData: {
      immediate: true,
      handler(newVal) {
        this.isEdit = newVal && Object.keys(newVal).length > 0;
        if (this.isEdit) {
          // 编辑模式：数据库的 JSON 字段需要解析成对象
          this.formData = {
            ...this.getEmptyForm(),
            ...newVal,
            doorInfo: newVal.doorInfo
              ? { ...this.getEmptyForm().doorInfo, ...newVal.doorInfo }
              : this.getEmptyForm().doorInfo,
            videoInfo: newVal.videoInfo
              ? { ...this.getEmptyForm().videoInfo, ...newVal.videoInfo }
              : this.getEmptyForm().videoInfo,
          };
          // 确保数组字段解析
          this.formData.doorInfo.doors = newVal.doorsJson
            ? JSON.parse(newVal.doorsJson)
            : [];
          this.formData.videoInfo.videos = newVal.videosJson
            ? JSON.parse(newVal.videosJson)
            : [];
          console.log("修改 this.formData", this.formData);
        } else {
          // 新增模式：空表单
          const empty = this.getEmptyForm();
          this.formData = {
            ...empty,
            doorInfo: empty.doorInfo,
            videoInfo: empty.videoInfo,
          };
          this.formData.type = "carDoor";
          console.log("新增 this.formData", this.formData);
        }
        this.getVideoInfo();
      },
    },
  },
  methods: {
    async getVideoInfo() {
      this.loading = true;
      try {
        const data = {
          deviceType: 3, // 为空时传 null
          enable: true,
          paginate: false,
        };
        console.log("查询参数", data);
        // 发起分页+查询请求
        const res = await request({
          url: "/videobyzlm/list",
          method: "POST",
          data: data,
        });
        // 判断返回结构
        if (res && res.list) {
          this.videoOptions = res.list || [];
          this.$message.success(res.message || "获取视频信息成功");
        } else {
          this.videoOptions = [];
          this.$message.warning(res.message || "获取视频信息失败");
        }
        console.log("视频列表", this.videoOptions);
      } catch (error) {
        console.error("获取视频信息失败:", error);
        this.$message.error("获取视频列表失败，请检查网络或后端接口！");
      } finally {
        this.loading = false;
      }
    },
    // 添加门
    addDoor() {
      const { doorIndex, doorName, direction, remark, doors } =
        this.formData.doorInfo;
      // 必填检查
      if (doorIndex == null || !doorName) {
        this.$message.warning("请输入门序号和门名称");
        return;
      }
      // 必填检查
      if (direction == null) {
        this.$message.warning("请选择门方向");
        return;
      }
      // 最大数量限制
      const MAX_DOORS = 5;
      if (doors.length >= MAX_DOORS) {
        this.$message.warning(`最多只能添加 ${MAX_DOORS} 个门`);
        return;
      }
      // 重复门序号检查
      if (doors.some((d) => d.doorIndex === doorIndex)) {
        this.$message.warning("门序号已存在");
        return;
      }
      // 生成唯一ID
      const doorId = Date.now() + Math.floor(Math.random() * 1000);
      // 添加门
      doors.push({
        doorId,
        doorIndex,
        doorName,
        direction, // 新增：方向字段 (in: 进, out: 出, all: 都允许)
        remark: remark || "",
      });
      // 重置输入框
      this.resetDoorInput();
    },
    // 删除门
    deleteDoor(id) {
      this.formData.doorInfo.doors = this.formData.doorInfo.doors.filter(
        (v) => v.doorId !== id,
      );
      // 删除后序号重排
      this.reorderDoorIndex();
    },
    reorderDoorIndex() {
      this.formData.doorInfo.doors.forEach((item, index) => {
        item.doorIndex = index + 1;
      });
    },
    // 重置门输入框
    resetDoorInput() {
      this.formData.doorInfo.doorIndex = null;
      // this.formData.doorInfo.doorId = null;
      this.formData.doorInfo.direction = null;
      this.formData.doorInfo.doorName = "";
      this.formData.doorInfo.remark = "";
    },
    addVideo() {
      const { videoId, direction, remark, videos } = this.formData.videoInfo;
      if (!videoId) {
        this.$message.warning("请选择摄像头");
        return;
      }
      // 必填检查
      if (direction == null) {
        this.$message.warning("请选择门方向");
        return;
      }
      const videoInfo = this.videoOptions.find((v) => v.id === videoId);
      if (!videoInfo) {
        this.$message.warning("摄像头不存在");
        return;
      }
      // 添加摄像头
      videos.push({
        videoId,
        videoName: videoInfo.name,
        direction, // 新增：方向字段 (in: 进, out: 出, all: 都允许)
        remark: remark || "",
        videoInfo,
      });
      // 重置输入框，包括选择框
      this.resetVideoInput();
      // 重排序号
      this.reorderVideoIndex();
    },
    deleteVideo(id) {
      this.formData.videoInfo.videos = this.formData.videoInfo.videos.filter(
        (v) => v.videoId !== id,
      );
      // 删除后序号重排
      this.reorderVideoIndex();
    },
    reorderVideoIndex() {
      this.formData.videoInfo.videos.forEach((item, index) => {
        item.videoIndex = index + 1;
      });
    },
    // 重置表单
    resetVideoInput() {
      this.formData.videoInfo.videoIndex = null; // 可选，如果你序号自动重排，也可以不保留
      this.formData.videoInfo.videoId = null; // 置空选择框
      this.formData.videoInfo.videoName = ""; // 自动填充的名称也清空
      this.formData.videoInfo.direction = null;
      this.formData.videoInfo.remark = "";
    },
    getDirectionText(direction) {
      const directionMap = {
        'in': '进',
        'out': '出',
        'all': '都允许'
      };
      return directionMap[direction] || direction;
    },
    handleCancel() {
      this.$emit("cancel");
    },
    handleSubmit() {
      this.$refs.deviceForm.validate((valid) => {
        if (!valid) {
          this.$message.error("请检查表单填写项");
          return false;
        }
        const data = {
          ...this.formData,
          doorsJson: JSON.stringify(this.formData.doorInfo.doors),
          videosJson: JSON.stringify(this.formData.videoInfo.videos),
        };
        this.$emit("submit", data);
      });
    },
    getEmptyForm() {
      return {
        id: null, // 设备ID（数据库主键，新增时为null）

        name: "", // 设备名称，表单必填
        serialNumber: "", // 设备序列号，表单必填
        type: "", // 设备类型：peopleDoor / carDoor / peopleDoor|carDoor
        location: "", // 设备安装位置
        accessPermission: "", // 访问权限
        isUpdateStatus: false, // 是否更新出入状态

        ip: "", // 设备IP地址
        port: 8000, // 设备端口号，默认8000
        account: "", // 设备登录账号
        password: "", // 设备登录密码

        isConnect: true, // 设备是否已连接，状态标记，不提交后端
        status: "offline", // 设备当前状态：online/offline

        firmwareVersion: "", // 固件版本号
        enable: true, // 是否启用（开关），对应数据库字段 enable
        remark: "", // 设备备注
        createTime: "", // 创建时间，数据库自动生成
        updateTime: "", // 更新时间，数据库自动生成

        doorsJson: [], // 原始数据库字段doorsJson（JSON字符串解析后存入 doorInfo.doors）
        videosJson: [], // 原始数据库字段videosJson（JSON字符串解析后存入 videoInfo.videos）
        doorInfo: {
          // 门体信息（用于表格增删改查）
          doorIndex: null, // 当前编辑门的门序号（用于表单输入框）
          doorId: null,
          doorName: "", // 当前编辑门的名称（用于表单输入框）
          direction: null,
          remark: "", // 当前编辑门的备注（用于表单输入框）
          doors: [], // 门列表，实际存储每个门的对象数组，提交时转为JSON字符串
        },
        videoInfo: {
          // 摄像头信息（用于表格增删改查）
          videoIndex: null, // 当前编辑摄像头的序号（用于表单输入框）
          videoId: null,
          videoName: "", // 当前编辑摄像头名称（用于表单输入框）
          direction: null,
          remark: "", // 摄像头备注
          videos: [], // 摄像头列表，实际存储每个摄像头对象数组，提交时转为JSON字符串
        },
      };
    },
  },
};
</script>

<style>
.el-dialog.custom-dialog {
  width: 1200px !important;
  background-color: #003c68 !important;
}

/* 防止主题样式覆盖 background */
.el-dialog.custom-dialog .el-dialog__body {
  background-color: #003c68 !important;
}

/* 设置对话框标题颜色为白色 */
.custom-dialog .el-dialog__title {
  color: white !important;
}

.device-form .el-form-item .el-input,
.device-form .el-form-item .el-input-number,
.device-form .el-form-item .el-select {
  width: 300px;
}
.device-form .el-form-item .el-date-editor {
  width: 400px; /* 统一右侧控件宽度 */
}
</style>
