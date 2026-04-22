<template>
  <div>
    <el-button class="box1" @click="daochu">导出</el-button>
    <div id="contents">
      <p
        style="
          text-align: center;
          margin-top: 10px;
          font-size: 16pt;
          font-weight: 600;
          color: black;
          font-family: 'heiti';
        "
        id="qTitle"
      >
        《周工作安排表》
      </p>

      <!-- <div style="width: 100%; height: 30px; margin-top: 10px; color: black">
        <span style="margin-left: 5%"
          >连值班领导：{{ message.peopleName }} &nbsp;</span
        >
        <span style="margin-left: 25%">电话：{{ message.phone }} &nbsp; </span>
        <span style="margin-left: 20%" id="qtime"
          >{{ message.startTime }}———{{ message.endTime }}</span
        >
      </div> -->

      <table
        class="b1"
        border="1"
        style="
          border-collapse: collapse;
          border: none;
          mso-border-left-alt: 0.5pt solid windowtext;
          mso-border-top-alt: 0.5pt solid windowtext;
          mso-border-right-alt: 0.5pt solid windowtext;
          mso-border-bottom-alt: 0.5pt solid windowtext;
          mso-border-insideh: 0.5pt solid windowtext;
          mso-border-insidev: 0.5pt solid windowtext;
          mso-padding-alt: 0pt 0pt 0pt 0pt;
        "
        width="907"
        align="center"
        bordercolor="black"
        id="tabless"
      >
        <tr
          class="JR_tr"
          style="
            border: none;
            color: black;
            font-weight: 500;
            line-height: 20px;
            width: 930px;
            margin-left: 8px;
          "
        >
          <td
            class="JR_left"
            colspan="3"
            align="left"
            style="border: none; padding-left: 10px; font-family: '楷体_GB2312'"
          >
            连值班领导：{{ message.peopleName }}
          </td>
          <td
            class="JR_left"
            colspan="3"
            align="left"
            style="
              border: none;
              padding-left: 190px;
              font-family: '楷体_GB2312';
            "
          >
            电话：{{ message.phone }}
          </td>
          <td
            class="JR_left"
            colspan="2"
            align="left"
            style="border: none; text-align: right; font-family: '楷体_GB2312'"
          >
            {{ message.startTime | dataFormaty }}—{{
              message.endTime | dataFormaty
            }}
          </td>
        </tr>
        <tr
          class="JR_tr"
          style="
            font-size: 16px;
            font-weight: 500;
            font-family: 'heiti';
            border-bottom: none;
          "
        >
          <td
            width="157"
            height="23"
            valign="center"
            colspan="3"
            class="JR_left"
            style="font-family: '黑体'; text-align: center; font-size: 16px"
          >
            日期
          </td>
          <td
            width="245"
            valign="center"
            class="JR_left"
            style="font-family: '黑体'; text-align: center; font-size: 16px"
          >
            内容
          </td>

          <td
            width="83"
            valign="center"
            class="JR_left"
            style="font-family: '黑体'; text-align: center; font-size: 16px"
          >
            负责单位
          </td>
          <td
            width="150"
            valign="center"
            class="JR_left"
            style="font-family: '黑体'; text-align: center; font-size: 16px"
          >
            参加人员
          </td>
          <td
            width="123"
            valign="center"
            class="JR_left"
            style="font-family: '黑体'; text-align: center; font-size: 16px"
          >
            地点
          </td>
          <td
            width="180"
            valign="center"
            class="JR_left"
            style="font-family: '黑体'; text-align: center; font-size: 16px"
          >
            时间
          </td>
        </tr>

        <tr
          class="JR_tr"
          v-for="(item, i) in message.bdglWeekworkRegisterList"
          :key="i"
          style="text-align: center; height: 45px; font-family: 'fangsong'"
        >
          <td
            style="text-align: center; width: 57px"
            rowspan="1"
            class="JR_right"
          >
            {{ item.week }}
          </td>
          <td
            style="text-align: center; width: 50px"
            rowspan="1"
            class="JR_right"
          >
            {{ item.time }}
          </td>
          <td
            style="text-align: center; width: 47px"
            rowspan="1"
            class="JR_right"
          >
            {{ item.dateline }}
          </td>
          <td
            style="text-align: center; width: 245px"
            rowspan="1"
            class="JR_right"
          >
            {{ item.contents }}
          </td>
          <td
            style="text-align: center; width: 83px"
            rowspan="1"
            class="JR_right"
          >
            {{ item.unitId1 }}
          </td>
          <td
            style="text-align: center; width: 150px"
            rowspan="1"
            class="JR_right"
          >
            {{ item.peopleIds }}
          </td>
          <td
            style="text-align: center; width: 123px"
            rowspan="1"
            class="JR_right"
          >
            {{ item.address }}
          </td>
          <td
            style="text-align: center; width: 158px"
            rowspan="1"
            class="JR_right"
          >
            {{ item.startTime }}—{{ item.endTime }}
          </td>
        </tr>
        <!-- <tr style="border-bottom: 1px solid #333">
          <td
            width="37"
            height="89px"
            colspan="1"
            valign="center"
            style="
              font-family: '仿宋_GB2312';
              text-align: center;
              font-size: 16px;
            "
          >
            备注
          </td>
          <td
            width="897"
            valign="center"
            colspan="7"

            style="
              font-family: '仿宋_GB2312';
              text-align: center;
              font-size: 16px;
            "
          >
            {{ message.remark }}
          </td>
        </tr> -->
        <tr
          class="JR_tr"
          style="
            color: black;
            font-weight: 500;
            line-height: 30px;
            width: 930px;
            margin-left: 8px;
            border: 1px solid black;
            font-size: 14px;
          "
        >
          <td
            class="JR_left"
            colspan="1"
            align="center"
            style="
              border-right: 1px solid black;
              line-height: 45px;
              font-size: 16px;
              font-family: 'fangsong';
              font-size: 14px;
            "
          >
            备注
          </td>
          <td
            class="JR_left"
            colspan="7"
            align="left"
            style="
              border: none;
              font-family: 'fangsong';
              font-size: 14px;
              padding: 10px;
            "
          >
            {{ message.remark }}
          </td>
          <td
            class="JR_left"
            colspan="0"
            align="left"
            style="border: none; text-align: center; display: none"
          ></td>
        </tr>
        <tr
          class="JR_tr"
          style="border: none; color: black; font-size: 12pt; line-height: 30px"
        >
          <td
            class="JR_right"
            colspan="4"
            width="401"
            align="left"
            style="
              font-family: '仿宋_GB2312';
              font-size: 14px;
              border: none;
              padding-left: 22px;
            "
          >
            军事主官：{{ message.people1Name }}
          </td>
          <td
            class="JR_right"
            colspan="3"
            width="382"
            align="left"
            style="
              font-family: '仿宋_GB2312';
              font-size: 14px;
              border: none;
              padding-left: 10px;
            "
          >
            政治主官：{{ message.people2Name }}
          </td>
          <td
            class="JR_right"
            colspan="1"
            width="180"
            align="left"
            style="
              font-family: '仿宋_GB2312';
              font-size: 14px;
              border: none;
              padding-left: 10px;
            "
          >
            时间：{{ message.startTime | dataFormaty }}
          </td>
        </tr>
      </table>

      <table
        class="b1"
        border="1"
        style="
          border-collapse: collapse;
          border: none;
          mso-border-left-alt: 0.5pt solid windowtext;
          mso-border-top-alt: 0.5pt solid windowtext;
          mso-border-right-alt: 0.5pt solid windowtext;
          mso-border-bottom-alt: 0.5pt solid windowtext;
          mso-border-insideh: 0.5pt solid windowtext;
          mso-border-insidev: 0.5pt solid windowtext;
          mso-padding-alt: 0pt 0pt 0pt 0pt;
          margin-left: 77px;
        "
        width="907"
        align="center"
        bordercolor="black"
      ></table>
    </div>
  </div>
</template>
<script>
//引入lookWord
import { lookWord } from "@/utils/look_word.js";
import "@/utils/lodJs.js";
import { lookequipuntits } from "@/api/grassrootsregistration/bdglweekwork";
export default {
  name: "bdgleven",
  props: ["message"],
  data() {
    return {
      title: "周工作安排表",
      faith: {
        fileName: "周工作安排表", //文档名
        fileType: ".doc", //文档类型  经测试  可以doc xls html 其他的自己去试
        // header: "测试页眉", //页眉标题
        // footer: "测试页脚", //页脚标题
        direction: "Horizontal", //默认或不填写为纵向，Horizontal 为横向
        dom: "#contents", // dom节点
      },
      // style:
      //   ".b1{width:860px !important};.JR_tr {height: 40px;};.JR_left{text-align:center;font-size: 1.231em; font-family: '楷体_GB2312';};.JR_right{ font-size: 1.131em; font-family: 仿宋_GB2312;};.JR_bottom{border-left:0; font-size: 1.331em; font-family: 仿宋_GB2312; text-align: right}",
    };
  },
  mounted() {
    this.message.bdglWeekworkRegisterList.forEach((item) => {
      item.time = this.zhuantime(item.time);
    });
    this.mergeCell("tabless", [0, 1, 2]);
  },
  methods: {
    mergeCell(tableId, cols) {
      var table = document.getElementById(tableId);
      var table_rows = table.rows;
      // 需要合并的列的数组
      cols.forEach((v, k) => {
        // 循环table每一行
        for (let i = 0; i < table_rows.length - 1; i++) {
          // row
          let now_row = table_rows[i];
          let next_row = table_rows[i + 1];
          // col
          let now_col = now_row.cells[v];
          let next_col = next_row.cells[v];

          if (now_col.innerHTML == next_col.innerHTML) {
            if (
              now_row.cells[1].innerHTML != next_row.cells[1].innerHTML &&
              now_row.cells[2].innerHTML == next_row.cells[2].innerHTML
            ) {
              $(next_col).addClass("has");
            } else {
              // 标记为需要删除
              $(next_col).addClass("remove");
              // 递归判断当前对象时候已经被删除
              this.setParentSpan(table, i, v);
            }
          }
        }
      });
      $(".remove").remove();
    },
    // (递归方法，用于多行统计) pram => table表格 当前行 对应的列
    setParentSpan(table, row, col) {
      var col_item = table.rows[row].cells[col];
      if ($(col_item).hasClass("remove")) {
        this.setParentSpan(table, --row, col);
      } else {
        col_item.rowSpan += 1;
      }
    },
    zhuantime(originVal) {
      const dt = new Date(originVal);
      if (originVal) {
        const y = dt.getFullYear();
        const m = (dt.getMonth() + 1 + "").padStart(2);
        const d = (dt.getDate() + "").padStart(2);
        return `${m}.${d}`;
      }
    },
    daochu() {
      // lookWord(this.title, "#contents", this.style, true);
      lookequipuntits(this.message.bdglWeekworkRegisterList[0].weekworkId).then(
        (res) => {
          window.open(res.msg, "_self");
        }
      );
    },
  },
};
</script>
<style scoped>
tbody {
  color: #fff;
  text-align: center;
}
tr:last-child {
  border-bottom: 1px solid;
}
.box1 {
  position: absolute;
  right: 30px;
  top: 80px;
  border: 1px solid rgb(129, 130, 131);
  border-radius: 5px;
}
</style>
