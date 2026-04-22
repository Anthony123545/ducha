<template>
  <el-dialog
    :visible.sync="localVisible"
    title="查询事件数据"
    @close="handleCancel"
    custom-class="custom-dialog"
  >
    <el-form
      ref="searchForm"
      :model="formData"
      :rules="formRules"
      label-width="180px"
      class="user-form"
    >
      <!-- 主次类型 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="报警主类型">
            <el-select
              v-model="formData.dwMajor"
              placeholder="请选择报警主类型"
              filterable
              clearable
            >
              <el-option
                v-for="option in majorTypeOptions"
                :key="option.value"
                :label="option.label"
                :value="option.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="报警次类型">
            <el-select
              v-model="formData.dwMinor"
              placeholder="请选择报警次类型"
              filterable
              clearable
              value-key="value"
            >
              <el-option
                v-for="item in minorTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 时间范围 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="开始时间">
            <el-date-picker
              v-model="formData.struStartTime"
              type="datetime"
              placeholder="请选择开始时间"
              value-format="yyyy-MM-dd HH:mm:ss"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="结束时间">
            <el-date-picker
              v-model="formData.struEndTime"
              type="datetime"
              placeholder="请选择结束时间"
              value-format="yyyy-MM-dd HH:mm:ss"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 卡号、姓名 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="卡号">
            <el-input v-model="formData.byCardNo" placeholder="为空默认全部" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="持卡人姓名">
            <el-input v-model="formData.byName" placeholder="为空默认全部" />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 图片与时间类型 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="是否带图片 (0-不带,1-带)">
            <el-select v-model="formData.byPicEnable" placeholder="选择">
              <el-option label="不带图片" :value="0" />
              <el-option label="带图片" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="时间类型">
            <el-select v-model="formData.byTimeType" placeholder="选择">
              <el-option label="设备本地时间(0)" :value="0" />
              <el-option label="UTC时间(1)" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 流水号 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="起始流水号">
            <el-input
              v-model="formData.dwBeginSerialNo"
              placeholder="0 默认全部"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="结束流水号">
            <el-input
              v-model="formData.dwEndSerialNo"
              placeholder="0 默认全部"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- IOT通道与归纳类型 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="IOT通道号">
            <el-input v-model="formData.dwIOTChannelNo" placeholder="0-无效" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="归纳事件类型">
            <el-select
              v-model="formData.wInductiveEventType"
              placeholder="选择归纳事件类型（0-无效）"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option :label="'0 - 无效（按主次类型区分）'" :value="0" />
              <el-option-group label="HIKVISION门禁主机">
                <el-option label="1 - 认证通过" :value="1" />
                <el-option label="2 - 认证失败" :value="2" />
                <el-option label="3 - 开门动作" :value="3" />
                <el-option label="4 - 关门动作" :value="4" />
                <el-option label="5 - 门异常" :value="5" />
                <el-option label="6 - 远程操作" :value="6" />
                <el-option label="7 - 校时事件" :value="7" />
                <el-option label="8 - 设备异常事件" :value="8" />
                <el-option label="9 - 设备恢复正常事件" :value="9" />
                <el-option label="10 - 报警事件" :value="10" />
                <el-option label="11 - 报警恢复事件" :value="11" />
                <el-option label="12 - 呼叫中心" :value="12" />
              </el-option-group>

              <el-option-group label="HIKVISION可视对讲">
                <el-option label="1 - 防拆报警" :value="101" />
                <el-option label="2 - 劫持报警" :value="102" />
                <el-option label="3 - 多次密码开锁失败" :value="103" />
                <el-option label="4 - 门没开报警" :value="104" />
                <el-option label="5 - 门没关报警" :value="105" />
                <el-option label="6 - 通话对讲报警" :value="106" />
                <el-option label="7 - 密码开锁" :value="107" />
                <el-option label="8 - 劫持开锁" :value="108" />
                <el-option label="9 - 刷卡开锁" :value="109" />
                <el-option label="10 - 户主开锁" :value="110" />
                <el-option label="11 - 中心平台开锁" :value="111" />
                <el-option label="12 - 设备对讲" :value="112" />
              </el-option-group>

              <el-option-group label="HIKVISION报警主机">
                <el-option label="1 - 防区事件" :value="201" />
                <el-option label="2 - 主机事件" :value="202" />
              </el-option-group>

              <el-option-group label="第三方 GJD 报警主机">
                <el-option label="1 - 对象侦测报警" :value="301" />
                <el-option label="2 - 人为防拆报警" :value="302" />
                <el-option label="3 - 弱光检测报警" :value="303" />
                <el-option label="4 - 定时报警" :value="304" />
                <el-option label="5 - 温度检测报警" :value="305" />
              </el-option-group>

              <el-option-group label="第三方 Luminite 报警主机">
                <el-option label="1 - 检测到移动" :value="401" />
                <el-option label="2 - 检测到干扰" :value="402" />
                <el-option label="3 - 传感器遮挡" :value="403" />
                <el-option label="4 - 震动报警" :value="404" />
                <el-option label="5 - 传感器问题" :value="405" />
                <el-option label="6 - 位置移动" :value="406" />
                <el-option label="7 - 电池不足" :value="407" />
                <el-option label="8 - 手动冲突数据" :value="408" />
                <el-option label="9 - 接收干扰信息" :value="409" />
                <el-option label="10 - 防拆弹簧松弛" :value="410" />
              </el-option-group>

              <el-option-group label="第三方 OPTEX 报警主机">
                <el-option label="1 - 远&近事件" :value="501" />
                <el-option label="2 - 远距离事件" :value="502" />
                <el-option label="3 - 近距离事件" :value="503" />
                <el-option label="4 - 缓慢移动检测" :value="504" />
                <el-option label="5 - 干扰报警" :value="505" />
                <el-option label="6 - 防遮挡报警" :value="506" />
                <el-option label="7 - 防旋转报警" :value="507" />
                <el-option label="8 - 异常电路激活" :value="508" />
                <el-option label="9 - 污物覆盖" :value="509" />
                <el-option label="10 - 传感器异常" :value="510" />
                <el-option label="11 - 设备故障" :value="511" />
                <el-option label="12 - 设备不在线" :value="512" />
                <el-option label="13 - 设备已连接" :value="513" />
                <el-option label="14~25 - 区域A/B报警" :value="514" />
              </el-option-group>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 搜索方式与监控点 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="搜索方式">
            <el-select v-model="formData.bySearchType" placeholder="选择方式">
              <el-option label="0-保留" :value="0" />
              <el-option
                label="1-按事件源搜索（此时通道号为非视频通道号）"
                :value="1"
              />
              <el-option label="2-按监控点ID搜索" :value="2" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="监控点ID">
            <el-input
              v-model="formData.szMonitorID"
              placeholder="由设备序列号、通道类型、编号组成，例如门禁点：设备序列号+“DOOR”+门编号"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 工号 -->
      <el-row>
        <el-col :span="12">
          <el-form-item label="工号（人员ID）">
            <el-input v-model="formData.byEmployeeNo" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 底部 -->
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="submitForm">确定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { minorTypeMap } from "./eventTypeMap";
export default {
  name: "EventSearchDialog",
  props: {
    visible: Boolean,
    eventData: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      localVisible: this.visible,
      formData: {
        dwMajor: 0,
        dwMinor: 0,
        struStartTime: "",
        struEndTime: "",
        byCardNo: "",
        byName: "",
        byPicEnable: 0,
        byTimeType: 0,
        dwBeginSerialNo: 0,
        dwEndSerialNo: 0,
        dwIOTChannelNo: 0,
        wInductiveEventType: 0,
        bySearchType: 0,
        szMonitorID: "",
        byEmployeeNo: "",
        // 导入的 minorTypeMap
        minorTypeMap: minorTypeMap,
        // 用于存储所有次类型的标签
        minorLabels: {},
      },
      formRules: {},
      majorTypeMap: {
        0x1: "报警",
        0x2: "异常",
        0x3: "操作",
        0x5: "事件",
      },
      minorTypeMap: minorTypeMap,
      minorLabels: {},
      selectedMinorOption: null, // 用于绑定 el-select 的选中项
    };
  },
  watch: {
    visible(val) {
      this.localVisible = val;
    },
    localVisible(val) {
      this.$emit("update:visible", val);
    },
    eventData: {
      immediate: true,
      handler(newVal) {
        this.formData = {
          ...this.resetForm(),
          ...newVal,
        };
        console.log("查询条件 this.formData", this.formData);
      },
    },

    "formData.dwMajor"(newMajor) {
      if (newMajor && this.minorTypeMap[newMajor]) {
        this.minorLabels = this.minorTypeMap[newMajor];
      } else {
        this.minorLabels = {};
      }
    },
  },
  computed: {
    majorTypeOptions() {
      const options = [];
      for (const [value, label] of Object.entries(this.majorTypeMap)) {
        options.push({
          value: Number(value), // 确保是数字
          label: label,
        });
      }
      return options.sort((a, b) => a.value - b.value);
    },
    minorTypeOptions() {
      const majorKey = Number(this.formData.dwMajor); // 转换为数字
      console.log("当前主类型 (数字):", majorKey);

      if (!majorKey || !this.minorTypeMap[majorKey]) {
        console.log("无可用次类型");
        return [];
      }

      const options = [];
      const minors = this.minorTypeMap[majorKey];

      for (const [value, label] of Object.entries(minors)) {
        options.push({
          value: Number(value), // 确保是数字
          label: label,
        });
      }

      return options.sort((a, b) => a.value - b.value);
    },
  },
  methods: {
    resetForm() {
      this.formData = {
        dwMajor: 0,
        dwMinor: 0,
        struStartTime: "",
        struEndTime: "",
        byCardNo: "",
        byName: "",
        byPicEnable: 0,
        byTimeType: 0,
        dwBeginSerialNo: 0,
        dwEndSerialNo: 0,
        dwIOTChannelNo: 0,
        wInductiveEventType: 0,
        bySearchType: 0,
        szMonitorID: "",
        byEmployeeNo: "",
      };
    },
    handleCancel() {
      this.$emit("cancel");
    },
    submitForm() {
      const data = {
        ...this.formData,
      };
      this.$emit("submit", data);
    },
  },
};
</script>

<style>
.custom-dialog {
  background-color: #003c68;
  width: 1000px !important;
}

.user-form .el-input,
.user-form .el-select,
.user-form .el-date-picker {
  width: 100%;
}
</style>
