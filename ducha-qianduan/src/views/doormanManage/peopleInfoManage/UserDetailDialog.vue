<template>
  <el-dialog
    title="人员详情"
    :visible.sync="localVisible"
    @close="handleCancel"
    custom-class="custom-dialog"
  >
    <el-descriptions
      :column="2"
      border
      :label-style="{ width: '100px', fontWeight: '600', color: '#333' }"
      :content-style="{ width: '200px' }"
    >
      <el-descriptions-item label="人脸图片">
        <div v-if="detail.faceImage || detail.picUR">
          <img
            :src="detail.faceImage || detail.picURL"
            alt="人脸图片"
            style="
              max-width: 150px;
              max-height: 150px;
              border: 1px solid #ddd;
              border-radius: 4px;
            "
          />
        </div>
        <div v-else style="color: #999; padding: 20px; text-align: center">
          <i
            class="el-icon-picture"
            style="font-size: 24px; display: block; margin-bottom: 8px"
          ></i>
          暂无图片
        </div>
      </el-descriptions-item>
      <el-descriptions-item label="工号">{{
        detail.employeeNo || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="姓名">{{
        detail.name || "--"
      }}</el-descriptions-item>

      <el-descriptions-item label="人员类型">
        {{ userTypeMap[detail.userType] || detail.userType || "--" }}
      </el-descriptions-item>

      <el-descriptions-item label="是否关门延迟">
        {{ detail.closeDelayEnabled ? "是" : "否" }}
      </el-descriptions-item>

      <el-descriptions-item label="有效期">
        {{
          detail.Valid
            ? detail.Valid.beginTime + " ~ " + detail.Valid.endTime
            : "--"
        }}
      </el-descriptions-item>

      <el-descriptions-item label="所属群组">{{
        detail.belongGroup || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="密码">{{
        detail.password || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="门权限">{{
        detail.doorRight || "--"
      }}</el-descriptions-item>

      <el-descriptions-item label="门权限计划">
        <div v-if="detail.RightPlan && detail.RightPlan.length">
          <div
            v-for="(item, idx) in detail.RightPlan"
            :key="idx"
            class="door-plan"
          >
            门编号（锁ID）: {{ item.doorNo }} → 计划模板编号:
            {{ item.planTemplateNo }}
          </div>
        </div>
        <span v-else>--</span>
      </el-descriptions-item>

      <el-descriptions-item label="最大认证次数">{{
        detail.maxOpenDoorTime || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="已认证次数">{{
        detail.openDoorTime || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="房间号">{{
        detail.roomNumber || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="层号">{{
        detail.floorNumber || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="反锁开门权限">{{
        detail.doubleLockRight ? "有权限" : "无权限"
      }}</el-descriptions-item>
      <el-descriptions-item label="本地UI访问权限">{{
        detail.localUIRight ? "有权限" : "无权限"
      }}</el-descriptions-item>

      <el-descriptions-item label="人员验证方式">
        {{
          verifyModeMap[detail.userVerifyMode] || detail.userVerifyMode || "--"
        }}
      </el-descriptions-item>

      <el-descriptions-item label="动态权限码">{{
        detail.dynamicCode || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="呼叫号码列表">{{
        detail.callNumbers ? detail.callNumbers.join(", ") : "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="层号列表">{{
        detail.floorNumbers ? detail.floorNumbers.join(", ") : "--"
      }}</el-descriptions-item>

      <el-descriptions-item label="关联人脸数量">{{
        detail.numOfFace || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="关联指纹数量">{{
        detail.numOfFP || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="关联卡数量">{{
        detail.numOfCard || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="人脸图片对应的人员性别">
        {{ genderMap[detail.gender] || detail.gender || "--" }}
      </el-descriptions-item>

      <el-descriptions-item label="人员信息扩展">
        {{
          detail.PersonInfoExtends && detail.PersonInfoExtends[0]
            ? detail.PersonInfoExtends[0].value
            : "--"
        }}
      </el-descriptions-item>
      <el-descriptions-item label="组织架构">{{
        detail.groupName || "--"
      }}</el-descriptions-item>
    </el-descriptions>

    <div slot="footer" class="dialog-footer">
      <el-button @click="$emit('update:visible', false)">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "UserDetailDialog",
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    detail: {
      type: Object,
      default: () => ({}),
    },
    deviceId: {
      type: [Number, String],
      required: true,
    },
  },
  data() {
    return {
      localVisible: this.visible,
      loading: false,
      userTypeMap: {
        normal: "普通人（主人）",
        visitor: "访客",
        blackList: "黑名单",
      },
      genderMap: {
        male: "男",
        female: "女",
        unknown: "未知",
      },
      verifyModeMap: {
        cardAndPw: "刷卡+密码",
        card: "刷卡",
        cardOrPw: "刷卡或密码",
        fp: "指纹",
        fpAndPw: "指纹+密码",
        fpOrCard: "指纹或刷卡",
        fpAndCard: "指纹+刷卡",
        fpAndCardAndPw: "指纹+刷卡+密码",
        faceOrFpOrCardOrPw: "人脸或指纹或刷卡或密码",
        faceAndFp: "人脸+指纹",
        faceAndPw: "人脸+密码",
        faceAndCard: "人脸+刷卡",
        face: "人脸",
        employeeNoAndPw: "工号+密码",
        fpOrPw: "指纹或密码",
        employeeNoAndFp: "工号+指纹",
        employeeNoAndFpAndPw: "工号+指纹+密码",
        faceAndFpAndCard: "人脸+指纹+刷卡",
        faceAndPwAndFp: "人脸+密码+指纹",
        employeeNoAndFace: "工号+人脸",
        faceOrfaceAndCard: "人脸或人脸+刷卡",
        fpOrface: "指纹或人脸",
        cardOrfaceOrPw: "刷卡或人脸或密码",
        cardOrFace: "刷卡或人脸",
        cardOrFaceOrFp: "刷卡或人脸或指纹",
        employeeNoAndFaceAndPw: "工号+人脸+密码",
        cardOrFaceOrFaceAndCard: "刷卡或人脸或人脸+刷卡",
      },
    };
  },
  watch: {
    visible: {
      immediate: true, // 立即执行一次
      handler(newVal) {
        this.localVisible = newVal;
        console.log("visible:", newVal);
        if (newVal && this.detail.employeeNo) {
          // 弹框打开时自动查询人脸图片
          this.$nextTick(() => {
            this.handleSearchFaceInfo(this.detail.employeeNo);
          });
        }
        if (newVal) {
          // 弹框打开时使用已有数据，不额外请求接口
          this.localVisible = true;
        }
      },
    },
    localVisible(val) {
      this.$emit("update:visible", val);
    },
  },
  methods: {
    handleCancel() {
      this.$emit("cancel");
    },
    async handleSearchFaceInfo(employeeNo) {
      if (!employeeNo) {
        console.warn("工号为空，跳过人脸查询");
        return;
      }
      this.loading = true;
      try {
        console.log("开始查询人脸信息，工号:", employeeNo);
        if (!this.deviceId) {
          this.$message.error("请选择设备！");
          return;
        }
        const data = {
          deviceId: this.deviceId,
          // searchID: Date.now().toString(),
          searchResultPosition: 0,
          maxResults: 1,
          faceLibType: "blackFD",
          FDID: "1",
          name: this.detail.name,
          employeeNo: this.detail.employeeNo,
          FPID: employeeNo,
        };
        console.log("请求参数:", data);
        const res = await request({
          url: "/faceInfo/list",
          method: "POST",
          data: data,
        });
        console.log("人脸查询响应:", res);
        if (res && res.status) {
          // 根据您的接口实际返回结构调整
          if (res.faceImage) {
            // 如果faceImage在根级别
            this.$set(this.detail, "faceImage", res.faceImage);
            this.$message.success("人脸图片加载成功");
          } else {
            this.$set(this.detail, "faceImage", "");
            this.$message.warning("未查询到人脸图片数据");
          }
        } else {
          this.$set(this.detail, "faceImage", "");
          this.$message.warning(res?.message || "查询失败");
        }
      } catch (error) {
        console.error("查询人脸信息失败:", error);
        this.$set(this.detail, "faceImage", "");
        this.$message.error("查询失败：" + (error.message || "网络错误"));
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>

<style scoped>
.custom-dialog {
  background-color: #003c68;
  width: 1200px !important;
}
/* 设置对话框标题颜色为白色 */
.custom-dialog .el-dialog__title {
  color: white !important;
}

.user-detail-dialog .el-descriptions {
  background: #fafafa;
  border-radius: 8px;
  padding: 10px 15px;
}

.user-detail-dialog .el-descriptions__label {
  width: 180px !important;
  font-weight: 600;
  color: #333;
  background: #f5f7fa;
  text-align: right;
}

.user-detail-dialog .el-descriptions__content {
  color: #555;
  word-break: break-all;
}

.door-plan {
  background: #fff;
  border: 1px solid #ebeef5;
  border-radius: 6px;
  padding: 6px;
  margin-top: 4px;
  font-size: 13px;
  color: #606266;
}

.dialog-footer {
  text-align: right;
  padding-top: 10px;
  border-top: 1px solid #ebeef5;
}
</style>
