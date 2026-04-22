<template>
  <div>
    <el-button class="box1" @click="daochu">导出</el-button>
    <div class="word" id="contents">
      <!-- <div class="word" id="contents"> -->
      <p
        style="
          font-size: 16pt;
          font-weight: 600;
          color: black;
          font-family: 'heiti';
          text-align: center;
        "
      >
        《营产、公物管理登记簿》
      </p>

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
        width="895"
        bordercolor="black"
      >
        <tbody style="font-size: 15px; font-weight: 500">
          <tr class="JR_tr" style="font-size: 16px; font-family: 'heiti'">
            <td
              width="80"
              height="40"
              valign="center"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                物资名称
              </span>
            </td>
            <td
              width="75"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                单 位
              </span>
            </td>
            <td
              width="55"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                数 量
              </span>
            </td>
            <td
              width="55"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                单 价
              </span>
            </td>
            <td
              width="55"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                来 源
              </span>
            </td>
            <td
              width="74"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                借用情况
              </span>
            </td>
            <td
              width="74"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                使用情况
              </span>
            </td>
            <td
              width="74"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                归还情况
              </span>
            </td>
          </tr>

          <tr
            class="JR_tr"
            style="font-family: 'fangsong'"
            v-for="(item, i) in message"
            :key="i + '  '"
          >
            <td
              width="80"
              height="45"
              valign="center"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center" width="66">{{ item.name }}</span>
            </td>
            <td
              width="75"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">{{ item.unitName }}</span>
            </td>
            <td
              width="55"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center"
                >{{ item.quantity
                }}<span style="margin-left: 6px">个</span></span
              >
            </td>
            <td
              width="55"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center"
                >{{ item.price }}<span style="margin-left: 6px">元</span></span
              >
            </td>
            <td
              width="55"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">{{ item.source }}</span>
            </td>
            <td
              width="74"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">{{ item.borrowingSituation }}</span>
            </td>
            <td
              width="74"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">{{ item.usage }}</span>
            </td>
            <td
              width="74"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">{{ item.returnSituation }}</span>
            </td>
          </tr>

          <tr class="JR_tr" v-for="(item1, i1) in num" :key="i1 + ''">
            <td
              width="80"
              height="45"
              valign="center"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center" width="66"></span>
            </td>
            <td
              width="75"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center"></span>
            </td>
            <td
              width="55"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center"></span>
            </td>
            <td
              width="55"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center"></span>
            </td>
            <td
              width="55"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center"></span>
            </td>
            <td
              width="74"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center"></span>
            </td>
            <td
              width="74"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center"></span>
            </td>
            <td
              width="74"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center"></span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
import exportWord from "@/utils/exportWord.js";
export default {
  name: "bdgleven",
  props: ["message"],
  data() {
    return {
      title: "营产、公物管理登记簿",
      num: 0,
      faith: {
        fileName: "网络、移动电话、涉密载体使用管理登记本", //文档名
        fileType: ".doc", //文档类型  经测试  可以doc xls html 其他的自己去试
        // header: "测试页眉", //页眉标题
        // footer: "测试页脚", //页脚标题
        direction: "Horizontal", //默认或不填写为纵向，Horizontal 为横向
        dom: "#contents", // dom节点
      },
      // style:
      //   ".b1{width:860px !important};.JR_tr {height: 40px;width:105px};.JR_left{font-size: 1.131em; font-family: '楷体_GB2312';};.JR_right{ font-size: 1.231em; font-family: 仿宋_GB2312;};.JR_bottom{border-left:0; font-size: 1.331em; font-family: 仿宋_GB2312; text-align: right}",
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
.box1 {
  position: absolute;
  right: 30px;
  top: 80px;
  border: 1px solid rgb(129, 130, 131);
  border-radius: 5px;
}
</style>
