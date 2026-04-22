<template>
  <div>
    <el-button class="box1" @click="daochu">导出</el-button>

    <div class="word" id="contents">
      <p class="title">文件管理登记本</p>

      <table border="1" width="908" align="center" class="word-table">
        <tbody>
          <!-- 基本信息 -->
          <tr>
            <td rowspan="3" style="width: 80px; text-align: center">
              接收<br />登记
            </td>
            <td colspan="2">文件标题</td>
            <td colspan="4">{{ row.name }}</td>
            <td colspan="1">来文单位</td>
            <td colspan="2">{{ row.sourceUnit }}</td>
          </tr>

          <tr>
            <td colspan="2">文件编号</td>
            <td colspan="1">{{ row.number }}</td>
            <td colspan="1">密级和保密期限</td>
            <td colspan="2">{{ row.confidential }}</td>
            <td>份数</td>
            <td>{{ row.copies }}</td>
          </tr>

          <tr>
            <td colspan="2">收文时间</td>
            <td colspan="1">{{ format(row.incomingDispatchesTime) }}</td>
            <td colspan="1">签收人</td>
            <td colspan="2">{{ row.peopleName }}</td>
            <td colspan="1">存放位置</td>
            <td colspan="2">{{ row.storageLocation }}</td>
          </tr>

          <!-- 借阅记录 -->
          <tr>
            <td rowspan="4" style="text-align: center">阅看<br />使用</td>
            <td>序号</td>
            <td>借阅人</td>
            <td>借阅时间</td>
            <td>归还时间</td>
            <td>序号</td>
            <td>借阅人</td>
            <td>借阅时间</td>
            <td>归还时间</td>
          </tr>

          <tr v-for="i in 3" :key="i">
            <td>{{ i }}</td>
            <td>{{ getBorrowName(i - 1) }}</td>
            <td>{{ getBorrowLoanTime(i - 1) }}</td>
            <td>{{ getBorrowReturnTime(i - 1) }}</td>

            <td>{{ i + 3 }}</td>
            <td>{{ getBorrowName(i + 2) }}</td>
            <td>{{ getBorrowLoanTime(i + 2) }}</td>
            <td>{{ getBorrowReturnTime(i + 2) }}</td>
          </tr>

          <!-- 清退 -->
          <tr>
            <td rowspan="1" style="text-align: center">清退<br />销毁</td>
            <td colspan="2">清退人</td>
            <td colspan="1">{{ row.logoutName }}</td>
            <td colspan="1">清退时间</td>
            <td colspan="2">{{ format(row.destroyTime) }}</td>
            <td colspan="1">接收人</td>
            <td colspan="1">{{ row.supervisorId }}</td>
          </tr>
          <tr>
            <td rowspan="1" style="text-align: center">备注</td>
            <td colspan="8">{{ row.beizhu }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import exportWord from "@/utils/exportWord";

export default {
  name: "bdglfilemanagement",
  props: {
    message: {
      type: Array,
      required: true,
      default: () => [],
    },
  },
  data() {
    return {
      row: {
        borrowRecords: [],
      },
      faith: {
        fileName: "文件管理登记本",
        fileType: ".doc",
        direction: "Horizontal",
        dom: "#contents",
      },
    };
  },
  created() {
    this.row = this.message[0] || {};
    if (!this.row.borrowRecords) {
      this.row.borrowRecords = [];
    }
  },
  methods: {
    getBorrow(index) {
      return (
        this.row.borrowRecords[index] || {
          borrowId: "",
          loanTime: "",
          returnTime: "",
        }
      );
    },
    getBorrowName(index) {
      const item = this.getBorrow(index);
      return item.peopleName || "";
    },
    getBorrowLoanTime(index) {
      const item = this.getBorrow(index);
      return this.format(item.loanTime);
    },
    getBorrowReturnTime(index) {
      const item = this.getBorrow(index);
      return this.format(item.returnTime);
    },
    format(val) {
      if (!val) return "";
      const d = new Date(val);
      const y = d.getFullYear();
      const m = String(d.getMonth() + 1).padStart(2, "0");
      const day = String(d.getDate()).padStart(2, "0");
      return `${y}-${m}-${day}`;
    },
    daochu() {
      exportWord(this.faith);
    },
  },
};
</script>

<style scoped>
.title {
  font-size: 18pt;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
}

.word-table {
  border-collapse: collapse;
  font-family: 仿宋_GB2312;
  font-size: 16px;
}

.word-table td {
  padding: 6px;
  text-align: center;
}

.box1 {
  position: absolute;
  right: 30px;
  top: 80px;
}
</style>
