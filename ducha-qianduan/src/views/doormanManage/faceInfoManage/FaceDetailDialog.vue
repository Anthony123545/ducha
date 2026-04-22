<template>
  <el-dialog
    title="人脸详情"
    :visible.sync="visible"
    width="1000px"
    height="800px"
    class="user-detail-dialog"
    @close="$emit('update:visible', false)"
  >
    <el-descriptions
      :column="2"
      border
      :label-style="{ width: '200px', fontWeight: '600', color: '#333' }"
      :content-style="{ width: '300px' }"
    >
      <el-descriptions-item label="人脸比对库唯一标识ID">{{
        detail.FDID
      }}</el-descriptions-item>
      <el-descriptions-item label="检测阈值[0-100]">{{
        detail.thresholdValue
      }}</el-descriptions-item>
      <el-descriptions-item label="人员出生日期">{{
        detail.bornTime
      }}</el-descriptions-item>
      <el-descriptions-item label="姓名">{{
        detail.name
      }}</el-descriptions-item>
      <el-descriptions-item label="性别">
        {{ genderMap[detail.sex] || detail.sex || "--" }}
      </el-descriptions-item>
      <el-descriptions-item label="省份">{{
        detail.province
      }}</el-descriptions-item>
      <el-descriptions-item label="城市">{{
        detail.city
      }}</el-descriptions-item>
      <el-descriptions-item label="证件类型">{{
        detail.certificateType
      }}</el-descriptions-item>
      <el-descriptions-item label="证件号">{{
        detail.certificateNumber
      }}</el-descriptions-item>
      <el-descriptions-item label="人脸图片">
        <el-image
          v-if="detail.picURL"
          :src="detail.picURL"
          fit="cover"
          style="width: 120px; height: 120px; border-radius: 8px"
        />
        <span v-else>无图片</span>
      </el-descriptions-item>
      <el-descriptions-item label="图片ID">{{
        detail.PID
      }}</el-descriptions-item>
      <el-descriptions-item
        label="人员扩展信息列表"
        content-style="padding: 0;"
      >
        <el-table
          v-if="
            detail.PersonInfoExtendList && detail.PersonInfoExtendList.length
          "
          :data="detail.PersonInfoExtendList"
          border
          size="small"
          style="width: 100%; max-width: 500px"
        >
          <el-table-column
            prop="id"
            label="序号"
            min-width="50"
            align="center"
          />
          <el-table-column
            prop="name"
            label="扩展名称"
            min-width="50"
            align="center"
          />
          <el-table-column
            prop="value"
            label="扩展内容"
            min-width="100"
            align="center"
          />
          <el-table-column
            prop="enable"
            label="启用状态"
            align="center"
            min-width="50"
          >
            <template #default="{ row }">
              <el-tag :type="row.enable ? 'success' : 'info'">
                {{ row.enable ? "启用" : "禁用" }}
              </el-tag>
            </template>
          </el-table-column>
        </el-table>
        <div v-else style="color: #999">无人员扩展信息</div>
      </el-descriptions-item>
      <el-descriptions-item label="相似度[0.0,100.0]">{{
        detail.similarity
      }}</el-descriptions-item>
      <el-descriptions-item label="建模状态" content-style="padding: 0;">
        <el-table
          v-if="detail.ModelingStatus"
          :data="[detail.ModelingStatus]"
          border
          size="small"
          style="width: 100%; max-width: 500px"
        >
          <el-table-column
            prop="FDID"
            label="人脸库ID"
            min-width="60"
            align="center"
          />
          <el-table-column
            prop="PID"
            label="图片ID"
            min-width="60"
            align="center"
          />
          <el-table-column
            prop="name"
            label="图片名称"
            min-width="60"
            align="center"
          />
          <el-table-column
            prop="status"
            label="建模状态"
            min-width="60"
            align="center"
          >
            <template #default="{ row }">
              <el-tag
                :type="
                  row.status === 'success'
                    ? 'success'
                    : row.status === 'failed'
                      ? 'danger'
                      : 'info'
                "
              >
                {{ row.status }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="reason"
            label="失败原因"
            min-width="60"
            align="center"
          />
          <el-table-column
            prop="customHumanID"
            label="客户自定义人员ID"
            min-width="60"
            align="center"
          />
          <el-table-column
            prop="customFaceLibID"
            label="客户自定义人脸库ID"
            min-width="60"
            align="center"
          />
          <el-table-column
            prop="modeData"
            label="模型数据(Base64)"
            min-width="60"
            align="center"
            show-overflow-tooltip
          />
        </el-table>
        <div v-else style="color: #999">无建模状态信息</div>
      </el-descriptions-item>
      <el-descriptions-item label="是否不保存原始人脸库图片">{{
        detail.isNoSaveFDPicture
      }}</el-descriptions-item>
      <el-descriptions-item label="人员ID">{{
        detail.humanId
      }}</el-descriptions-item>
      <el-descriptions-item label="建模状态（旧）">{{
        detail.modelStatus
      }}</el-descriptions-item>
      <el-descriptions-item label="人脸比对成功总次数">{{
        detail.FMCount
      }}</el-descriptions-item>
      <el-descriptions-item label="入库时间">{{
        detail.importTime
      }}</el-descriptions-item>
      <el-descriptions-item label="底库图片唯一标识ID">{{
        detail.UUPID
      }}</el-descriptions-item>
      <el-descriptions-item label="出现频次">{{
        detail.occurrences
      }}</el-descriptions-item>
      <el-descriptions-item label="IMSI采集信息" content-style="padding: 0;">
        <el-table
          v-if="detail.IMSIList && detail.IMSIList.length"
          :data="detail.IMSIList"
          border
          size="small"
          style="width: 100%; max-width: 500px"
        >
          <el-table-column
            prop="IMSI"
            label="IMSI号码"
            min-width="60"
            align="center"
          />
          <el-table-column
            prop="count"
            label="比中次数"
            min-width="30"
            align="center"
          />
          <el-table-column
            prop="lastFMTime"
            label="最近比对时间"
            min-width="50"
            align="center"
          />
        </el-table>
        <div v-else style="color: #999">无IMSI采集信息</div>
      </el-descriptions-item>
      <el-descriptions-item label="最近比对成功时间">{{
        detail.lastFMTime
      }}</el-descriptions-item>
    </el-descriptions>

    <div slot="footer" class="dialog-footer">
      <el-button @click="$emit('update:visible', false)">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
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
  },
  data() {
    return {
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
};
</script>

<style scoped>
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
