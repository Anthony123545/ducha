<template>
  <div>
    <el-button class="box1" @click="daochu">导出</el-button>

    <div class="word" id="contents">
      <p
        style="
          font-size: 16pt;
          font-weight: 600;
          color: black;
          text-align: center;
          font-family: 'heiti';
        "
      >
        军械装备登记表（总表）
      </p>
      <p
        style="
          font-size: 16px;
          font-weight: 600;
          margin: 0px 0px 3px 13px;
          color: black;
          font-family: '宋体';
        "
        v-if="message.length>0 "
      >
        装备名称：<span style="font-family: '仿宋_GB2312'"
          >{{ message[0].name }}
        </span>
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
        width="936"
        bordercolor="black"
      >
        <tbody style="font-size: 16px; font-weight: 500">
          <tr
            class="JR_tr"
            style="font-size: 16px; font-weight: 500; font-family: '宋体'"
            align="center"
          >
            <td
              width="36"
              valign="center"
              align="center"
              nowrap=""
              rowspan="2"
              class="JR_left"
              height="78"
            >
              序<br />号
            </td>
            <td
              class="JR_left"
              width="110"
              valign="center"
              nowrap=""
              colspan="3"
              height="37"
            >
              <span align="center">登记日期</span>
            </td>
            <td width="45" valign="center" rowspan="2" class="JR_left">
              <span align="center">单位</span>
            </td>
            <td width="45" valign="center" rowspan="2" class="JR_left">
              <span align="center">编制数</span>
            </td>
            <td
              width="92"
              valign="center"
              nowrap=""
              rowspan="2"
              class="JR_left"
              height="78"
            >
              <p align="center">
                交接依据<br />
                来 &nbsp;&nbsp;&nbsp;源
              </p>
            </td>
            <td
              width="171"
              valign="center"
              nowrap=""
              colspan="4"
              class="JR_left"
              height="35"
            >
              <span align="center">现有数</span>
            </td>
            <td
              width="214"
              valign="center"
              nowrap=""
              colspan="3"
              class="JR_left"
              height="35"
            >
              <span align="center">附品、工具、装护具配套情况</span>
            </td>
            <td
              width="73"
              valign="center"
              nowrap=""
              rowspan="2"
              class="JR_left"
            >
              <span align="center">登记人</span>
            </td>
            <td
              width="73"
              valign="center"
              nowrap=""
              rowspan="2"
              class="JR_left"
            >
              <span align="center">核对人</span>
            </td>
          </tr>
          <tr
            class="JR_tr"
            style="font-size: 16px; font-weight: 500; font-family: '宋体'"
            height="40"
            align="center"
          >
            <td width="36.6" valign="center" nowrap="" class="JR_left">
              <span>年</span>
            </td>
            <td width="36.6" valign="center" nowrap="" class="JR_left">
              <span>月</span>
            </td>
            <td width="36.6" valign="center" nowrap="" class="JR_left">
              <span>日</span>
            </td>
            <td width="44" valign="center" nowrap="" class="JR_left">
              <span>合计</span>
            </td>
            <td width="42" valign="center" nowrap="" class="JR_left">
              <span
                >完好
                <div>数</div>
              </span>
            </td>
            <td width="42" valign="center" nowrap="" class="JR_left">
              <span
                >待修
                <div>数</div>
              </span>
            </td>
            <td width="42" valign="center" nowrap="" class="JR_left">
              <span
                >送修
                <div>数</div>
              </span>
            </td>
            <td width="64" valign="center" nowrap="" class="JR_left">
              <span
                >标准
                <div>（套）</div>
              </span>
            </td>
            <td width="53" valign="center" nowrap="" class="JR_left">
              <span
                >现有
                <div>（套）</div>
              </span>
            </td>
            <td width="92" valign="center" nowrap="" class="JR_left">
              <span
                >缺陷种类及
                <div>数量</div>
              </span>
            </td>
          </tr>
          <tr
            class="JR_tr"
            style="
              font-size: 16px;
              font-weight: 500;
              font-family: '仿宋_GB2312';
            "
            v-for="(item, i) in message"
            :key="i + ' '"
            height="39"
            align="center"
          >
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ i + 1 }}</span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="item.recordDate != null&&message">
                {{ item.recordDate.split("-")[0] }}
              </span>
            </td>
            <td w valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="item.recordDate != null&&message">
                {{ item.recordDate.split("-")[1] }}
              </span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="item.recordDate != null&&message">
                {{ item.recordDate.split("-")[2] }}
              </span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ item.metering }}</span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ item.prepareNumber }}</span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ item.gist }}</span>
            </td>

            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ item.sum }}</span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ item.intactNumber }}</span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ item.repairNumber }}</span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ item.sendNumber }}</span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ item.standard }}</span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ item.existing }}</span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ item.flaw }}</span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ item.peopleName }}</span>
            </td>
            <td valign="center" nowrap="" class="JR_right">
              <span align="center" v-if="message">{{ item.people1Name }}</span>
            </td>
          </tr>
          <tr
            class="JR_tr"
            v-for="(item1, i1) in num"
            :key="i1 + ''"
            style="
              font-size: 16px;
              font-weight: 500;
              font-family: '仿宋_GB2312';
            "
            height="39"
            align="center"
          >
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
            <td valign="center" nowrap="">
              <span align="center"></span>
            </td>
          </tr>
          <tr class="JR_tr" align="center">
            <td
              width="37"
              valign="center"
              nowrap=""
              class="JR_left"
              style="font-family: '宋体'"
              height="96"
            >
              <span
                >备
                <div>注</div>
              </span>
            </td>
            <td valign="top" colspan="15" class="JR_right">
              <p></p>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
import exportWord from "@/utils/exportWord.js";

import "@/utils/lodJs.js";
export default {
  name: "bdgleven",
  props: ["message"],
  data() {
    return {
      title: "军械装备登记表（总表）",
      num: 0,
      style:
        ".b1{width:860px !important};.JR_tr {height: 40px;};.JR_left{font-size: 1.201em; font-family: '楷体_GB2312';};.JR_right{ font-size: 1.231em; font-family: 仿宋_GB2312;};.JR_bottom{border-left:0; font-size: 1.331em; font-family: 仿宋_GB2312; text-align: right}",
      faith: {
        fileName: "军械装备登记表（总表）", //文档名
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
  border: black;
  color: black;
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
