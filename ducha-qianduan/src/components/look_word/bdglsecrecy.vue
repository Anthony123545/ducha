<template>
  <div>
    <el-button class="box1" @click="daochu">导出</el-button>

    <div class="word" id="contents">
      <p
        style="
          font-size: 16pt;
          font-weight: 600;
          margin-botttom: 30px;
          color: black;
          font-family: 'heiti';
          text-align: center;
        "
      >
        《网络、移动电话、涉密载体使用管理登记本》
      </p>
      <!-- <div class="fTitle">网络、移动电话、涉密载体使用管理登记本</div> -->
      <table
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
          width: 900px;
        "
        bordercolor="black"
        class="size"
      >
        <tbody style="font-size: 16px; font-weight: 500">
          <tr class="JR_tr">
            <td
              valign="center"
              style="font-family: '黑体'; text-align: center"
              class="JR_left"
              width="89"
              height="55"
            >
              <span style="display: block; text-align: center"
                >种类（名 称）
              </span>
            </td>
            <td
              valign="center"
              style="font-family: '黑体'; text-align: center"
              class="JR_left"
              width="101"
            >
              <span style="display: block; text-align: center">型号</span>
            </td>
            <td
              width="101"
              valign="center"
              style="font-family: '黑体'; text-align: center"
              class="JR_left"
            >
              <span style="display: block; text-align: center">编号</span>
            </td>
            <td
              width="75"
              valign="center"
              style="font-family: '黑体'; text-align: center"
              class="JR_left"
            >
              <span style="display: block; text-align: center">密级</span>
            </td>
            <td
              valign="center"
              width="102"
              style="font-family: '黑体'; text-align: center"
              class="JR_left"
            >
              <span style="display: block; text-align: center">责任人</span>
            </td>
            <td
              valign="center"
              width="102"
              style="font-family: '黑体'; text-align: center"
              class="JR_left"
            >
              <span style="display: block; text-align: center">使用人</span>
            </td>
            <td
              valign="center"
              width="102"
              style="font-family: '黑体'; text-align: center"
              class="JR_left"
            >
              <span style="display: block; text-align: center">批准人</span>
            </td>
            <td
              valign="center"
              width="102"
              style="font-family: '黑体'; text-align: center"
              class="JR_left"
            >
              <span style="display: block; text-align: center">
                使用起止 <br />
                时间
              </span>
            </td>
            <td
              valign="center"
              width="102"
              style="font-family: '黑体'; text-align: center"
              class="JR_left"
            >
              <span style="display: block; text-align: center"
                >移交（出借）时间</span
              >
            </td>
          </tr>

          <tr class="JR_tr" v-for="(item, i) in message" :key="i + ' '">
            <td
              valign="center"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
              height="40"
            >
              <span align="center">
                {{ item.kind }}
              </span>
            </td>
            <td
              valign="center"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">
                {{ item.version }}
              </span>
            </td>
            <td
              valign="center"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">
                {{ item.number }}
              </span>
            </td>
            <td
              valign="center"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">
                {{ item.confidential }}
              </span>
            </td>
            <td
              valign="center"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">
                {{ item.peopleName }}
              </span>
            </td>
            <td
              valign="center"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">
                {{ item.people1Name }}
              </span>
            </td>
            <td
              valign="center"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">
                {{ item.people2Name }}
              </span>
            </td>
            <td
              valign="center"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <!--<p align="center">2022-01-18<br>2022-01-18</p>-->
              <span align="center">
                {{ item.startTime }}
              </span>
            </td>
            <td
              valign="center"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">
                {{ item.hindOverTime }}<br />
                (移交/出售)
              </span>
            </td>
          </tr>

          <tr class="JR_tr" v-for="(item1, i1) in num" :key="i1 + ''">
            <td valign="center" class="JR_right" height="40">
              <span align="center"></span>
            </td>
            <td valign="center" class="JR_right">
              <span align="center"></span>
            </td>
            <td valign="center" class="JR_right">
              <span align="center"></span>
            </td>
            <td valign="center" class="JR_right">
              <span align="center"></span>
            </td>
            <td valign="center" class="JR_right">
              <span align="center"></span>
            </td>
            <td valign="center" class="JR_right">
              <span align="center"></span>
            </td>
            <td valign="center" class="JR_right">
              <span align="center"></span>
            </td>
            <td valign="center" class="JR_right">
              <!--<p align="center">2022-01-20<br>2022-01-20</p>-->
              <span align="center"></span>
            </td>
            <td valign="center" class="JR_right">
              <span align="center">
                <br />
              </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
import "@/utils/lodJs.js";
import exportWord from "@/utils/exportWord.js";
export default {
  name: "bdgleven",
  props: ["message"],
  data() {
    return {
      title: "网络、移动电话、涉密载体使用管理登记本",
      // style:
      //   "font-weight: 550;.size{width:860px !important};.JR_tr {height: 40px;};.JR_left{font-size: 1em; font-family: '楷体_GB2312';};.JR_right{};.JR_bottom{border-left:0; font-size: 1.331em; font-family: 仿宋_GB2312; text-align: right}",
      num: 0,
      faith: {
        fileName: "网络、移动电话、涉密载体使用管理登记本", //文档名
        fileType: ".doc", //文档类型  经测试  可以doc xls html 其他的自己去试
        // header: "测试页眉", //页眉标题
        // footer: "测试页脚", //页脚标题
        direction: "Horizontal", //默认或不填写为纵向，Horizontal 为横向
        dom: "#contents", // dom节点
      },
    };
  },
  created() {
    if (this.message.length >= 6) {
      this.num = 0;
    } else {
      this.num = 6 - this.message.length;
    }
  },
  methods: {
    daochu() {
      exportWord(this.faith);
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
::v-deep .contents {
  width: 1047px !important;
}
.box1 {
  position: absolute;
  right: 30px;
  top: 80px;
  border: 1px solid rgb(129, 130, 131);
  border-radius: 5px;
}
</style>