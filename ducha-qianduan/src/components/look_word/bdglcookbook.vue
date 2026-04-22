<template>
  <div>
    <el-button class="box1" @click="daochu">导出</el-button>
    <div class="word" id="contents">
      <p
        style="
          text-align: center;
          color: black;
          font-size: 18pt;
          font-weight: 600;
          font-family: 'heiti';
        "
      >
        食堂食谱表
      </p>
      <p
        style="
          font-size: 16px;
          font-weight: 500;
          margin-left: 10px;
          color: black;
          font-family: 'fangsong';
        "
      >
        使用时间:{{ message.startTime | dataFormaty }}—{{
          message.endTime | dataFormaty
        }}
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
        width="930"
        bordercolor="black"
      >
        <colgroup>
          <col width="51" />
          <col width="58" />
          <col width="125" />
          <col width="130" />
          <col width="128" />
          <col width="130" />
          <col width="121" span="3" />
        </colgroup>
        <tbody style="font-size: 15px; font-weight: 500">
          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td colspan="2" align="center" class="JR_left">时间</td>
            <td width="111" align="center" class="JR_left">星期一</td>
            <td width="111" align="center" class="JR_left">星期二</td>
            <td width="111" align="center" class="JR_left">星期三</td>
            <td width="111" align="center" class="JR_left">星期四</td>
            <td width="111" align="center" class="JR_left">星期五</td>
            <td width="111" align="center" class="JR_left">星期六</td>
            <td width="111" align="center" class="JR_left">星期天</td>
          </tr>

          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td
              width="99"
              rowspan="4"
              align="center"
              class="JR_left"
              style="font-family: 'fangsong'"
            >
              早餐
            </td>
            <td
              width="70"
              rowspan="2"
              align="center"
              class="JR_left"
              style="font-family: 'fangsong'"
            >
              主食
            </td>
            <td
              align="center"
              style="white-space: normal; height: 45"
              class="JR_right"
            >
              炊事员：{{ message.cookbookRegistrations[0].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[6].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[12].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[18].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[24].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[30].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[36].peopleNames }}
            </td>
          </tr>
          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[0].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[6].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[12].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[18].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[24].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[30].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[36].cookcategoryNames }}
            </td>
          </tr>
          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td
              width="70"
              rowspan="2"
              align="center"
              class="JR_left"
              style="font-family: 'fangsong'"
            >
              副食
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[1].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[7].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[13].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[19].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[25].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[31].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[37].peopleNames }}
            </td>
          </tr>
          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[1].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[7].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[13].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[19].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[25].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[31].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[37].cookcategoryNames }}
            </td>
          </tr>

          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td
              width="99"
              rowspan="4"
              align="center"
              class="JR_left"
              style="font-family: 'fangsong'"
            >
              午餐
            </td>
            <td
              width="70"
              rowspan="2"
              align="center"
              class="JR_left"
              style="font-family: 'fangsong'"
            >
              主食
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[2].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[8].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[14].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[20].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[26].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[32].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[38].peopleNames }}
            </td>
          </tr>
          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[2].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[8].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[14].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[20].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[26].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[32].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[38].cookcategoryNames }}
            </td>
          </tr>
          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td
              width="70"
              rowspan="2"
              align="center"
              class="JR_left"
              style="font-family: 'fangsong'"
            >
              副食
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[3].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[9].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[15].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[21].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[27].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[33].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[39].peopleNames }}
            </td>
          </tr>
          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[3].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[9].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[15].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[21].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[27].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[33].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal">
              {{ message.cookbookRegistrations[39].cookcategoryNames }}
            </td>
          </tr>

          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td
              width="99"
              rowspan="4"
              align="center"
              class="JR_left"
              style="font-family: 'fangsong'"
            >
              晚餐
            </td>
            <td
              width="70"
              rowspan="2"
              align="center"
              class="JR_left"
              style="font-family: 'fangsong'"
            >
              主食
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[4].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[10].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[16].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[22].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[28].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[34].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[40].peopleNames }}
            </td>
          </tr>
          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[4].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[10].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[16].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[22].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[28].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[34].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[40].cookcategoryNames }}
            </td>
          </tr>
          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td
              width="70"
              rowspan="2"
              align="center"
              class="JR_left"
              style="font-family: 'fangsong'"
            >
              副食
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[5].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[11].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[17].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[23].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[29].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[35].peopleNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              炊事员：{{ message.cookbookRegistrations[41].peopleNames }}
            </td>
          </tr>
          <tr height="45" class="JR_tr" style="font-family: '宋体'">
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[5].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[11].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[17].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[23].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[29].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[35].cookcategoryNames }}
            </td>
            <td align="center" style="white-space: normal" class="JR_right">
              {{ message.cookbookRegistrations[41].cookcategoryNames }}
            </td>
          </tr>
        </tbody>
        <tr
          class="b1"
          style="
            border: none;
            color: black;
            font-size: 16px;
            font-weight: 500;
            line-height: 30px;
            width: 930px;
            font-family: 'fangsong';
          "
        >
          <td
            colspan="4"
            align="left"
            style="
              border: none;
              line-height: 45px;
              width: 360px;
              padding-left: 34px;
            "
          >
            单位领导：
          </td>
          <td
            colspan="3"
            align="left"
            style="
              border: none;
              line-height: 45px;
              width: 360px;
              text-align: center;
            "
          >
            经委会：
          </td>
          <td
            colspan="3"
            align="left"
            style="
              border: none;
              line-height: 45px;
              text-align: center;
              padding-left: 81px;
            "
          >
            制表：
          </td>
        </tr>
      </table>
      <!-- <div
        style="width: 100%; height: 50px; margin-top: 20px; color: black"
        class="bottom"
      >
        <span style="margin-left: 30px">单位领导：</span>
        <span style="margin-left: 35%">经委会：</span>
        <span style="margin-left: 318px">制表：</span>
      </div> -->
    </div>
  </div>
</template>
<script>
// import { lookWord } from "@/utils/look_word.js";
import exportWord from "@/utils/exportWord.js";
import "@/utils/lodJs.js";
export default {
  name: "bdgleven",
  props: ["message"],
  data() {
    return {
      title: "食堂食谱表",
      faith: {
        fileName: "食堂食谱表", //文档名
        fileType: ".doc", //文档类型  经测试  可以doc xls html 其他的自己去试
        direction: "Horizontal", //默认或不填写为纵向，Horizontal 为横向
        dom: "#contents", // dom节点
        dev: "margin:102.0pt 62.3pt 102.0pt 62.3pt;",
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
.bottom {
  font-size: 14px;
  display: flex;
  justify-content: center;
}
</style>