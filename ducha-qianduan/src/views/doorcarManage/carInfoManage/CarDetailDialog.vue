<template>
  <el-dialog
    title="车辆详情"
    :visible.sync="visible"
    width="1000px"
    class="car-detail-dialog"
    @close="$emit('update:visible', false)"
  >
    <!-- 车牌信息 -->
    <el-descriptions
      title="车牌信息"
      :column="2"
      border
      :label-style="{ width: '120px', fontWeight: '600', color: '#333' }"
      :content-style="{ width: '180px' }"
    >
      <el-descriptions-item label="车牌号码">{{
        detail.plateNumber || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="车牌颜色">
        {{ plateColorMap[detail.plateColor] || detail.plateColor || "--" }}
      </el-descriptions-item>
      <el-descriptions-item label="车牌类型">
        {{ plateTypeMap[detail.plateType] || detail.plateType || "--" }}
      </el-descriptions-item>
    </el-descriptions>

    <!-- 车辆信息 -->
    <el-descriptions
      title="车辆信息"
      :column="2"
      border
      :label-style="{ width: '120px', fontWeight: '600', color: '#333' }"
      :content-style="{ width: '180px' }"
      style="margin-top: 20px"
    >
      <el-descriptions-item label="车辆类型">
        {{ vehicleTypeMap[detail.vehicleType] || detail.vehicleType || "--" }}
      </el-descriptions-item>
      <el-descriptions-item label="车辆颜色">{{
        detail.vehicleColor || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="车辆品牌">{{
        detail.vehicleBrand || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="车辆排量">{{
        detail.displacement || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="发动机号">{{
        detail.engineNo || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="车架号">{{
        detail.vin || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="排放编号">{{
        detail.emissionNo || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="识别代码">{{
        detail.identifyCode || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="车队名称">{{
        detail.fleetName || "--"
      }}</el-descriptions-item>
    </el-descriptions>

    <!-- 车主信息 -->
    <el-descriptions
      title="车主信息"
      :column="2"
      border
      :label-style="{ width: '120px', fontWeight: '600', color: '#333' }"
      :content-style="{ width: '180px' }"
      style="margin-top: 20px"
    >
      <el-descriptions-item label="车主姓名">{{
        detail.ownerName || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="证件号码">{{
        detail.certificateNo || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="职位">{{
        detail.position || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="所在部门">{{
        detail.departmentName || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="手机号码">{{
        detail.phoneNumber || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="住址">{{
        detail.address || "--"
      }}</el-descriptions-item>
      <el-descriptions-item label="所属公司">{{
        detail.company || "--"
      }}</el-descriptions-item>
    </el-descriptions>

    <!-- 车辆图片 -->
    <el-descriptions
      :column="1"
      border
      :label-style="{ width: '120px', fontWeight: '600', color: '#333' }"
      style="margin-top: 20px"
    >
      <el-descriptions-item label="车辆图片">
        <el-image
          v-if="detail.carImage"
          :src="detail.carImage"
          fit="cover"
          style="width: 200px; height: 150px; border-radius: 8px"
        />
        <span v-else>无图片</span>
      </el-descriptions-item>
    </el-descriptions>

    <!-- 扩展信息（保留原有逻辑） -->
    <el-descriptions
      :column="1"
      border
      :label-style="{ width: '120px', fontWeight: '600', color: '#333' }"
      style="margin-top: 20px"
    >
      <el-descriptions-item label="车辆扩展信息" content-style="padding: 0;">
        <el-table
          v-if="detail.CarInfoExtendList && detail.CarInfoExtendList.length"
          :data="detail.CarInfoExtendList"
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
        <div v-else style="color: #999">无车辆扩展信息</div>
      </el-descriptions-item>
    </el-descriptions>

    <div slot="footer" class="dialog-footer">
      <el-button @click="$emit('update:visible', false)">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "CarDetailDialog",
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
      // 车牌颜色映射
      plateColorMap: {
        yellow: "黄色",
        blue: "蓝色",
        green: "绿色",
        white: "白色",
        black: "黑色",
      },
      // 车牌类型映射
      plateTypeMap: {
        normal: "普通车牌",
        newEnergy: "新能源车牌",
        police: "警用车牌",
        military: "军用车牌",
      },
      // 车辆类型映射
      vehicleTypeMap: {
        small: "小型车",
        medium: "中型车",
        large: "大型车",
        suv: "SUV",
        truck: "货车",
        bus: "客车",
      },
    };
  },
};
</script>

<style scoped>
.car-detail-dialog .el-descriptions {
  background: #fafafa;
  border-radius: 8px;
  padding: 10px 15px;
}

.car-detail-dialog .el-descriptions__label {
  width: 120px !important;
  font-weight: 600;
  color: #333;
  background: #f5f7fa;
  text-align: right;
}

.car-detail-dialog .el-descriptions__content {
  color: #555;
  word-break: break-all;
}

.car-detail-dialog .el-descriptions__title {
  font-size: 16px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 10px;
}

.dialog-footer {
  text-align: right;
  padding-top: 10px;
  border-top: 1px solid #ebeef5;
}
</style>
