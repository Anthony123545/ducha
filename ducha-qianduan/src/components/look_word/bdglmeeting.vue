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
          text-align: center;
          font-family: 'heiti';
        "
      >
        《团支部工作记录本》
      </p>
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
        "
        width="623"
        align="center"
        bordercolor="black"
        class="b1"
      >
        <tbody style="font-size: 16px; font-weight: 500">
          <tr class="JR_tr" height="39">
            <th class="" colspan="2">
              <p
                align="center"
                style="
                  font-size: 18px;
                  font-family: '楷体_GB2312';
                  font-weight: 400;
                "
              >
                团支部委员会会议
              </p>
            </th>
          </tr>
          <tr class="JR_tr" height="39" style="font-size: 19px">
            <td
              width="110"
              class="JR_left"
              style="border-right: none !important; font-family: '楷体_GB2312'"
            >
              <p align="center">&nbsp;&nbsp;时 &nbsp;间：</p>
            </td>
            <td
              class="JR_right"
              align="left"
              style="border-left: none !important; font-family: '仿宋_GB2312'"
            >
              <p style="text-indent: 8px">
                {{ message.convokeTime | dataFormaty }}
              </p>
            </td>
          </tr>
          <tr class="JR_tr" height="39" style="font-size: 19px">
            <td
              class="JR_left"
              style="border-right: none !important; font-family: '楷体_GB2312'"
            >
              <p align="center">&nbsp;&nbsp;地 &nbsp;点：</p>
            </td>
            <td
              class="JR_right"
              align="left"
              style="
                border-left: none !important;

                font-family: '仿宋_GB2312';
              "
            >
              <p style="text-indent: 8px">{{ message.address }}</p>
            </td>
          </tr>
          <tr class="JR_tr" height="39" style="font-size: 19px">
            <td
              class="JR_left"
              style="border-right: none !important; font-family: '楷体_GB2312'"
            >
              <p align="center">&nbsp;&nbsp;主持人：</p>
            </td>
            <td
              class="JR_right"
              align="left"
              style="border-left: none; font-family: '仿宋_GB2312'"
            >
              <p style="text-indent: 8px">{{ message.compereRen }}</p>
            </td>
          </tr>
          <tr class="JR_tr" style="font-size: 19px" height="46">
            <td
              class="JR_left"
              style="border-right: none !important; font-family: '楷体_GB2312'"
            >
              <p align="center">&nbsp;&nbsp;参加人：</p>
            </td>
            <td
              class="JR_right"
              align="left"
              style="border-left: none !important; font-family: '仿宋_GB2312'"
            >
              <p style="text-indent: 8px">{{ message.joinId }}</p>
            </td>
          </tr>
          <tr class="JR_tr" height="39" style="font-size: 19px">
            <td
              class="JR_left"
              style="border-right: none !important; font-family: '楷体_GB2312'"
            >
              <p align="center">&nbsp;&nbsp;列席人：</p>
            </td>
            <td
              class="JR_right"
              align="left"
              style="border-left: none !important; font-family: '仿宋_GB2312'"
            >
              <p style="text-indent: 8px">{{ message.peopleIds }}</p>
            </td>
          </tr>
          <tr class="JR_tr" height="39" style="font-size: 19px">
            <td
              class="JR_left"
              style="border-right: none !important; font-family: '楷体_GB2312'"
            >
              <p align="center">&nbsp;&nbsp;缺席人：</p>
            </td>
            <td
              class="JR_right"
              align="left"
              style="border-left: none !important; font-family: '仿宋_GB2312'"
            >
              <p style="text-indent: 8px">{{ message.absentIds }}</p>
            </td>
          </tr>
          <tr class="JR_tr" height="39" style="font-size: 19px">
            <td
              class="JR_left"
              style="border-right: none !important; font-family: '楷体_GB2312'"
            >
              <p align="center">&nbsp;&nbsp;记录人：</p>
            </td>
            <td
              class="JR_right"
              align="left"
              style="border-left: none !important; font-family: '仿宋_GB2312'"
            >
              <p style="text-indent: 8px">{{ message.peopleJiLu }}</p>
            </td>
          </tr>
          <tr class="JR_tr" height="40" style="font-size: 19px">
            <td
              class="JR_left"
              style="border-right: none !important; font-family: '楷体_GB2312'"
            >
              <p align="center">&nbsp;&nbsp;议 &nbsp;题：</p>
            </td>
            <td
              class="colors"
              align="left"
              style="border-left: none !important; font-family: '仿宋_GB2312'"
              v-html="message.title"
            ></td>
          </tr>
          <!-- <tr class="JR_tr" ></tr> -->
          <tr class="JR_tr" height="40" style="font-size: 19px">
            <td
              class="JR_left"
              style="border-right: 0; font-family: '楷体_GB2312'"
            >
              <p align="center">&nbsp;&nbsp; 内 &nbsp;容：</p>
            </td>
            <td class="TableData" style="border-left: 0"></td>
          </tr>
          <tr align="left" class="JR_tr" height="50" style="font-size: 19px">
            <td
              colspan="2"
              class="colors"
              v-html="message.contents"
              style="
                text-indent: 30px;
                padding: 0px 10px;
                border: 1px solid black;
                font-family: '仿宋_GB2312';
              "
            ></td>
          </tr>
        </tbody>
      </table>

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
        "
        width="623"
        align="center"
        bordercolor="black"
        class="b1"
        border-top="none"
      >
        <tbody>
          <tr class="JR_tr" height="39" style="font-size: 19px">
            <td
              colspan="2"
              class="JR_bottom"
              style="
                border-top: none;
                border-left: 1px solid black;
                font-family: '仿宋_GB2312';
              "
            >
              <p style="text-align: right; margin-right: 20px">
                主持人：
                {{ message.compereRen }}
              </p>
            </td>
          </tr>
          <tr class="JR_tr" height="39" style="font-size: 19px">
            <td
              colspan="2"
              class="JR_bottom"
              style="border-left: 1px solid black; font-family: '仿宋_GB2312'"
            >
              <p style="text-align: right; margin-right: 20px">
                记录人：
                {{ message.peopleJiLu }}
              </p>
            </td>
          </tr>

          <tr class="JR_tr" height="39" style="font-size: 19px">
            <td
              colspan="2"
              class="JR_bottom"
              style="border-left: 1px solid black; font-family: '仿宋_GB2312'"
            >
              <p style="text-align: right; margin-right: 20px">
                时间：
                {{ message.convokeTime | dataFormaty }}
              </p>
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
      title: "团支部工作记录本",
      faith: {
        fileName: "团支部工作记录本", //文档名
        fileType: ".doc", //文档类型  经测试  可以doc xls html 其他的自己去试
        // header: '测试页眉',//页眉标题
        // footer: '测试页脚',//页脚标题
        direction: "", //默认或不填写为纵向，Horizontal 为横向
        dom: "#contents", // dom节点
        style: ".colors{color: black !important}",
      },
    };
  },
  created() {},
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
span {
  color: #000;
}

::v-deep .colors {
  color: black !important ;
}
::v-deep .colors p span {
  color: black !important ;
}
</style>