<template>
  <div class="app-container">
    <div class="a4-wrapper">
      <div class="a4-card" id="export-content">
        <div class="title">互联网、智能电子设备使用管理登记表</div>
        <table class="detail-table">
          <!-- 表格内容和之前一致，此处省略 -->
          <tr>
            <td class="label">姓 名</td>
            <td class="value">{{ message.name || "-" }}</td>
            <td class="label">职 别</td>
            <td class="value">{{ message.jobTitle || message.dept || "-" }}</td>
          </tr>
          <tr>
            <td class="label">电话号码1<br />及启停时间</td>
            <td class="value">
              {{ message.phone1 || "-" }}<br />{{ message.phone1Time || "-" }}
            </td>
            <td class="label">电话号码2<br />及启停时间</td>
            <td class="value">
              {{ message.phone2 || "-" }}<br />{{ message.phone2Time || "-" }}
            </td>
          </tr>
          <tr>
            <td class="label">电话号码3<br />及启停时间</td>
            <td class="value">
              {{ message.phone3 || "-" }}<br />{{ message.phone3Time || "-" }}
            </td>
            <td class="label">电话号码4<br />及启停时间</td>
            <td class="value">
              {{ message.phone4 || "-" }}<br />{{ message.phone4Time || "-" }}
            </td>
          </tr>
          <tr>
            <td class="label">智能手机1<br />品牌型号</td>
            <td class="value">{{ message.smartPhone1 || "-" }}</td>
            <td class="label">智能手机2<br />品牌型号</td>
            <td class="value">{{ message.smartPhone2 || "-" }}</td>
          </tr>
          <tr>
            <td class="label">智能手机3<br />品牌型号</td>
            <td class="value">{{ message.smartPhone3 || "-" }}</td>
            <td class="label">非智能手机<br />品牌型号</td>
            <td class="value">{{ message.nonSmartPhone || "-" }}</td>
          </tr>
          <tr>
            <td class="label">其他智能<br />电子设备</td>
            <td class="value" colspan="3">{{ message.otherDevice || "-" }}</td>
          </tr>
          <tr>
            <td class="label">微信账号</td>
            <td class="value">{{ message.wechat || "-" }}</td>
            <td class="label">QQ账号</td>
            <td class="value">{{ message.qq || "-" }}</td>
          </tr>
          <tr>
            <td class="label">微博账号</td>
            <td class="value">{{ message.weibo || "-" }}</td>
            <td class="label">抖音账号</td>
            <td class="value">{{ message.douyin || "-" }}</td>
          </tr>
          <tr>
            <td class="label">其他社交<br />媒体账号</td>
            <td class="value" colspan="3">{{ message.otherSocial || "-" }}</td>
          </tr>
          <tr>
            <td class="label">使用人<br />保密保证</td>
            <td class="value" colspan="3">
              <p>1.本人如实提供以上信息,对信息的真实性、完整性负责;</p>
              <p>
                2.本人知悉互联网和智能电子设备保密法规制度,将严格遵守保密纪律;
              </p>
              <p>3.如发现窃密泄密问题线索,将第一时间上报。</p>
              <p class="sign">
                使用人: {{ message.userName || "-" }}<br />
                {{ message.date || message.useDate || "-" }}
              </p>
            </td>
          </tr>
          <tr>
            <td class="label">备 注</td>
            <td class="value" colspan="3">
              <p>1.此表每年集中填写1次,在新兵入伍、人员调动等时机,应当视情补</p>
              <p>充填写;</p>
              <p>2.个人填写此表后,在1年内新开手机号码、社交媒体账号或购买智</p>
              <p>
                能电子设备,可以在表中空白处补充填写,并注明补充填写日期,不得涂
              </p>
              <p>抹原有内容。</p>
            </td>
          </tr>
          <tr>
            <td
              class="label"
              colspan="4"
              style="text-align: right; background: #fff; border: none"
            ></td>
          </tr>
        </table>
        <div class="footer-btn">
          <el-button type="primary" icon="el-icon-download" @click="exportWord">
            导出 Word
          </el-button>
          <el-button @click="handleBack">返回</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
// 仅新增：引入标准版导出工具（和参考代码一致）
import "@/utils/lodJs.js";
import exportWord from "@/utils/exportWord.js";

export default {
  name: "bdglsecrecy1",
  props: {
    message: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API || "",
      // 仅新增：导出配置（参考标准版）
      exportConfig: {
        fileName: "智能电子设备使用登记表", // 导出文件名
        fileType: ".doc", // 导出格式
        direction: "Horizontal", // 横向导出
        dom: "#export-content", // 要导出的DOM节点ID
      },
    };
  },
  watch: {
    message: {
      immediate: true,
      deep: true,
      handler(newVal) {
        console.log("子组件接收的父组件数据：", newVal);
        if (Object.keys(newVal).length === 0) {
          this.$message.warning("暂无详情数据");
        }
      },
    },
  },
  methods: {
    // 仅修改此方法：替换为纯前端导出逻辑，删除所有后端接口相关代码
    exportWord() {
      try {
        // 调用标准版导出工具（纯前端DOM导出，无后端请求）
        exportWord(this.exportConfig);
        this.$message.success("导出成功！");
      } catch (error) {
        console.error("导出失败：", error);
        this.$message.error("导出失败，请重试");
      }
    },
    handleBack() {
      this.$emit("close");
    },
  },
};
</script>

<style scoped>
/* 所有样式完全保留，仅新增导出容器的基础样式（不影响原有样式） */
#export-content {
  width: 100%;
}

.a4-wrapper {
  width: 100%;
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20px 0;
  display: flex;
  justify-content: center;
}

.a4-card {
  width: 210mm;
  min-height: 297mm;
  padding: 25mm;
  box-sizing: border-box;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  margin: 0 auto;
}

.title {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 30px;
  font-family: "宋体", SimSun, serif;
}

.detail-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
  font-family: "宋体", SimSun, serif;
}

.detail-table td {
  border: 1px solid #000;
  padding: 10px 8px;
  vertical-align: top;
  line-height: 1.8;
}

.label {
  width: 180px;
  font-weight: bold;
  text-align: center;
  background: #f7f7f7;
}

.value {
  text-align: left;
  &:empty::before {
    content: "-";
    color: #999;
  }
}

.time {
  color: #666;
}

.footer-btn {
  margin-top: 40px;
  text-align: center;
}

@media print {
  .a4-wrapper {
    background: none;
    padding: 0;
  }
  .a4-card {
    box-shadow: none;
    margin: 0;
    width: 210mm;
    height: 297mm;
  }
  .footer-btn {
    display: none;
  }
}
</style>
