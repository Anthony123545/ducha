<template>
  <div>
    <el-button class="box1" @click="daochu">导出</el-button>
    <div class="word" id="contents">
      <!-- <div class="fTitle">大队连务会记录本</div> -->
      <p
        style="
          font-size: 16pt;
          font-weight: 600;
          margin-botttom: 30px;
          color: black;
          font-family: '宋体';
          text-align: center;
        "
      >
        {{ message[0].unitname
        }}军事训练登记表
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
        width="900"
        bordercolor="black"
      >
        <tbody style="font-size: 16px">
          <tr class="JR_tr">
            <th
              width="99"
              rowspan="2"
              class="JR_left"
              height="50"
              style="font-family: '黑体'; text-align: center; font-weight: 400"
            >
              <span align="center" style="display: block; text-align: center">
                训练类型
              </span>
            </th>
            <th
              width="150"
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-weight: 400"
            >
              <span align="center" style="display: block; text-align: center">
                训练内容
              </span>
            </th>
            <th
              width="98"
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-weight: 400"
            >
              <span align="center" style="display: block; text-align: center">
                训练时间
              </span>
            </th>
            <th
              width="98"
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-weight: 400"
            >
              <span align="center" style="display: block; text-align: center">
                训练地点
              </span>
            </th>
            <th
              width="100"
              height="25"
              colspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-weight: 400"
            >
              <span align="center" style="display: block; text-align: center">
                参训兵力
              </span>
            </th>
            <th
              width="99"
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-weight: 400"
            >
              <span align="center" style="display: block; text-align: center">
                参训率
              </span>
            </th>
            <th
              width="101"
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-weight: 400"
            >
              <span align="center" style="display: block; text-align: center">
                效果(成绩)
              </span>
            </th>
            <th
              width="50"
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-weight: 400"
            >
              <span align="center" style="display: block; text-align: center">
                消耗
              </span>
            </th>
            <th
              width="120"
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-weight: 400"
            >
              <span align="center" style="display: block; text-align: center">
                训练装备台套
              </span>
            </th>
            <th
              width="50"
              nowrap=""
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-weight: 400"
            >
              <span align="center" style="display: block; text-align: center">
                备注
              </span>
            </th>
          </tr>
          <tr class="JR_tr">
            <th
              width="50"
              height="25"
              nowrap=""
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-weight: 400"
            >
              <span align="center" style="display: block; text-align: center"
                >应训</span
              >
            </th>
            <th
              width="50"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-weight: 400"
            >
              <span align="center" style="display: block; text-align: center"
                >实训</span
              >
            </th>
          </tr>

          <tr class="JR_tr" v-for="(message, i) in message" :key="i + ''">
            <td
              valign="center"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
              height="44"
              width="99"
            >
              <span align="center">
                {{ message.name }}
              </span>
            </td>
            <td
              width="150"
              class="JR_right"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center">
                {{ message.trainingContent }}
              </span>
            </td>
            <td
              width="98"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center">
                {{ message.drillTime }}
              </span>
            </td>
            <td
              width="98"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center">
                {{ message.address }}
              </span>
            </td>
            <td
              width="50"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center"> {{ message.yingxun }}</span>
            </td>
            <td
              width="50"
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center"> {{ message.shixun }}</span>
            </td>
            <td
              width="99"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center">
                {{ message.participationRate }}
              </span>
            </td>
            <td
              width="101"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center">
                {{ message.pass }}
              </span>
            </td>
            <td
              width="50"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center">
                {{ message.excellent }}
              </span>
            </td>

            <td
              width="120"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center">
                {{ message.equipment }}
              </span>
            </td>
            <td
              width="50"
              nowrap=""
              class="JR_right"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center">
                {{ message.fail }}
              </span>
            </td>
          </tr>

          <tr class="JR_tr" v-for="(item1, i1) in num" :key="i1 + ' '">
            <td height="44" width="99" class="JR_right">
              <span align="center"> </span>
            </td>
            <td width="150" class="JR_right">
              <span align="center"></span>
            </td>
            <td width="98" class="JR_right">
              <span align="center"></span>
            </td>
            <td width="98" class="JR_right">
              <span align="center"></span>
            </td>
            <td width="50" class="JR_right">
              <span align="center"></span>
            </td>
            <td width="50" class="JR_right">
              <span align="center"></span>
            </td>
            <td width="99" nowrap="" class="JR_right">
              <span align="center"></span>
            </td>
            <td width="101" nowrap="" class="JR_right">
              <span align="center"></span>
            </td>
            <td width="50" nowrap="" class="JR_right">
              <span align="center"></span>
            </td>
            <td width="120" nowrap="" class="JR_right">
              <span align="center"></span>
            </td>
            <td width="50" nowrap="" class="JR_right">
              <span align="center"></span>
            </td>
          </tr>
        </tbody>
      </table>

      <div
        style="
          font-size: 14pt;
          font-weight: 900;
          margin: 10px 50px 20px 700px;
          color: black;
          font-family: '黑体';
        "
      >
        军事主管：{{ message.peopleId }}
      </div>
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
      title: "军事训练登记簿",
      num: 0,
      faith: {
        fileName: "军事训练登记簿", //文档名
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
.a1 {
  margin-right: 30px;
}
.box1 {
  position: absolute;
  right: 30px;
  top: 80px;
  border: 1px solid rgb(129, 130, 131);
  border-radius: 5px;
}
</style>
