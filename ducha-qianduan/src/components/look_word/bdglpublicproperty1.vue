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
        <!--<p align="center" style="font-size: 14pt;font-weight: bold;">营产，公物管理登记簿</p>-->
        <tbody style="font-size: 15px; font-weight: 500">
          <tr class="JR_tr" style="font-size: 16px; font-family: 'heiti'">
            <td
              width="45"
              height="40"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                序号
              </span>
            </td>
            <td
              width="63"
              valign="center"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                名称
              </span>
            </td>
            <td
              width="92"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                规格型号
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
                数量
              </span>
            </td>
            <td
              width="63"
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
              width="64"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                总价值
              </span>
            </td>
            <td
              width="120"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                装备时间
              </span>
            </td>
            <td
              width="91"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                保管人
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
                取得方式
              </span>
            </td>
            <td
              width="73"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                质量状况
              </span>
            </td>
            <!-- <th width="74" valign="center" nowrap="" class="JR_left">
              <p align="center" style="margin: 10px 0; font-family: 'heiti'">
                固定资产负责人
              </p>
            </th> -->
            <td
              width="74"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center">
                放置地点
              </span>
            </td>
            <td
              width="56"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 1em"
            >
              <span align="center" style="display: block; text-align: center"
                >备注</span
              >
            </td>
          </tr>

          <tr
            class="JR_tr"
            style="font-family: 'fangsong'"
            v-for="(item, i) in message"
            :key="i + '  '"
          >
            <td
              width="45"
              height="45"
              valign="center"
              nowrap=""
              class="JR_right"
              style="font-family: 'Calibri'; text-align: center; font-size: 1em"
            >
              <span align="center">{{ i + 1 }}</span>
            </td>
            <td
              width="63"
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
              width="92"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">{{ item.version }}</span>
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
              <span align="center">{{ item.quantity }}</span>
            </td>
            <td
              width="63"
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
              width="64"
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
                >{{ item.totalPrice
                }}<span style="margin-left: 5px">元</span></span
              >
            </td>
            <td
              width="120"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">{{ item.equipTime | dataFormaty }}</span>
            </td>
            <td
              width="91"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">{{ item.peopleName }}</span>
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
              <span align="center">{{ item.acquire }}</span>
            </td>

            <td
              width="73"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">{{ item.quality }}</span>
            </td>
            <!-- <td width="75" valign="center" nowrap="" class="JR_right">
              <p align="center">是</p>
            </td> -->
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
              <span align="center">{{ item.address }}</span>
            </td>
            <td
              width="54"
              valign="center"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center">{{ item.remark }}</span>
            </td>
          </tr>
          <tr class="JR_tr" v-for="(item1, i1) in num" :key="i1 + ''">
            <td width="45" height="45" valign="center" nowrap="">
              <span align="center" style="margin: 5px"> </span>
            </td>
            <td width="63" valign="center">
              <span align="center"></span>
            </td>
            <td width="92" valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td width="55" valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td width="63" valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td width="64" valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td width="120" valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td width="91" valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td width="74" valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td width="73" valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <!-- <td width="75" height="45px" valign="center" nowrap="">
              <p align="center"></p>
            </td> -->
            <td width="74" valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td width="54" valign="center" nowrap="">
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
