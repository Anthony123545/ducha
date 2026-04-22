<template>
  <div>
    <el-button class="box1" @click="daochu">导出</el-button>

    <div class="word" id="contents" style="font-family: 'heiti'">
      <!-- <div class="fTitle">大队连务会记录本</div> -->
      <p
        style="
          font-size: 16pt;
          font-weight: 600;
          margin-botttom: 30px;
          color: black;
          text-align: center;
        "
      >
        《连务会记录本》
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
        width="600"
        align="center"
        bordercolor="black"
        class="size"
      >
        <tbody style="font-size: 16px; font-weight: 500">
          <tr class="JR_tr" style="font-family: '黑体'; height: 40px">
            <td width="70" class="JR_left">
              <p align="center">&nbsp;&nbsp;时 &nbsp;&nbsp;&nbsp;&nbsp;间</p>
            </td>
            <td
              class="JR_right"
              style="font-family: '仿宋_GB2312'"
              align="left"
            >
              <p style="text-indent: 8px">
                {{ message.convokeTime | dataFormaty }}
              </p>
            </td>
          </tr>
          <tr class="JR_tr" style="font-family: '黑体'; height: 40px">
            <td class="JR_left">
              <p align="center">&nbsp;&nbsp;地 &nbsp;&nbsp;&nbsp;&nbsp;点</p>
            </td>
            <td class="JR_right" style="font-family: '仿宋_GB2312'">
              <p align="left" style="text-indent: 8px">
                {{ message.address }}
              </p>
            </td>
          </tr>
          <tr class="JR_tr" style="font-family: '黑体'; height: 40px">
            <td class="JR_left">
              <p align="center">&nbsp;&nbsp;出席人</p>
            </td>
            <td class="JR_right" style="font-family: '仿宋_GB2312'">
              <p align="left" style="text-indent: 8px">
                {{ message.joinId }}
              </p>
            </td>
          </tr>
          <tr class="JR_tr" style="font-family: '黑体'; height: 40px">
            <td class="JR_left">
              <p align="center">&nbsp;&nbsp;主持人</p>
            </td>
            <td class="JR_right" style="font-family: '仿宋_GB2312'">
              <p align="left" style="text-indent: 8px">
                {{ message.compereRen }}
              </p>
            </td>
          </tr>
          <tr class="JR_tr" style="font-family: '黑体'; height: 40px">
            <td class="JR_left">
              <p align="center">&nbsp;&nbsp;记录人</p>
            </td>
            <td class="JR_right" style="font-family: '仿宋_GB2312'">
              <p align="left" style="text-indent: 8px">
                {{ message.peopleJiLu }}
              </p>
            </td>
          </tr>

          <tr class="JR_tr" style="font-family: '黑体'; height: 206px">
            <td class="JR_left">
              <p align="right" style="text-align: center; line-height: 20px">
                议<br />题
              </p>
            </td>
            <td
              class="JR_right"
              align="left"
              style="
                line-height: 20px;
                text-indent: 20px;
                font-family: '仿宋_GB2312';
              "
              v-html="message.title"
            >
              <!-- {{ message.title }} -->
            </td>
          </tr>
          <tr class="JR_tr" style="font-family: '黑体'; height: 206px">
            <td class="JR_left">
              <p align="right" style="text-align: center; line-height: 20px">
                会<br />议<br />内<br />容
              </p>
            </td>
            <td
              align="left"
              class="JR_right"
              style="
                line-height: 20px;
                text-indent: 20px;
                font-family: '仿宋_GB2312';
              "
              v-html="message.contents"
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
          mso-border-right-alt: 0.5pt solid windowtext;
          mso-border-bottom-alt: 0.5pt solid windowtext;
          mso-border-insideh: 0.5pt solid windowtext;
          mso-border-insidev: 0.5pt solid windowtext;
          mso-padding-alt: 0pt 0pt 0pt 0pt;
        "
        width="600"
        align="center"
        bordercolor="black"
        class="size"
        border-top="none"
      >
        <tbody>
          <tr
            class="JR_trs"
            style="
              border-top: none;
              border-left: 1px solid black;
              font-family: '仿宋_GB2312';
              font-size: 19px;
              height: 39px;
            "
            align="center"
          >
            <td colspan="2" class="JR_bottom" style="border-top: 0">
              <p style="text-align: right; margin-right: 20px">
                主持人：
                {{ message.compereRen }}
              </p>
            </td>
            <!-- <td style="width: 100px; border-top: none; border-left: none"></td> -->
          </tr>
          <tr
            class="JR_trs"
            style="
              border-top: none;
              border-left: 1px solid black;
              font-family: '仿宋_GB2312';
              font-size: 19px;
              height: 39px;
            "
            align="center"
          >
            <td colspan="2" class="JR_bottom">
              <p style="text-align: right; margin-right: 20px">
                记录人：
                {{ message.peopleJiLu }}
              </p>
            </td>
            <!-- <td style="width: 100px; border-left: none"></td> -->
          </tr>

          <tr
            class="JR_trs"
            style="
              border-top: none;
              border-left: 1px solid black;
              font-family: '仿宋_GB2312';
              font-size: 19px;
              height: 39px;
            "
          >
            <td colspan="2" class="JR_bottom">
              <p style="text-align: right; margin-right: 20px">
                时间：
                {{ message.convokeTime | dataFormaty }}
              </p>
            </td>
            <!-- <td style="width: 100px; border-left: none"></td> -->
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
      title: "连务会记录本",
      faith: {
        fileName: "连务会记录本", //文档名
        fileType: ".doc", //文档类型  经测试  可以doc xls html 其他的自己去试
        // header: "测试页眉", //页眉标题
        // footer: "测试页脚", //页脚标题
        direction: "", //默认或不填写为纵向，Horizontal 为横向
        dom: "#contents", // dom节点
      },
    };
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

/* @font-face {
  font-family: 'siYuan';
  src: url('../../assets/css/siyuan.OTF');
}
@font-face {
  font-family: 'siyuan-x';
  src: url('../../assets/css/SOURCEHANSANSCN-REGULAR.OTF');
} */
.box1 {
  position: absolute;
  right: 30px;
  top: 80px;
  border: 1px solid rgb(129, 130, 131);
  border-radius: 5px;
}
.JR_trs {
  font-size: 16px !important;
}
</style>