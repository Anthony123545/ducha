<template>
  <div class="box">
    <el-button class="box1" @click="daochu">导出</el-button>
    <div class="word" id="contents">
      <div
        class="title"
        style="
          font-size: 16pt;
          font-weight: 600;
          text-align: center;
          margin-bottom: 10px;
        "
      >
        《{{ title }}》
      </div>
      <table
        border="0"
        style="
          width: 585px;
          color: black;
          font-size: 16px;
          font-weight: 500;
          border: 0;
        "
        id="b1"
      >
        <tr style="border: 0; font-family: &quot;宋体&quot;">
          <td style="border: 0; width: 173px" align="center">
            {{ message.blogDate | dataFormats }}
          </td>
          <td style="border: 0; font-family: &quot;宋体&quot;" align="left">
            <span>{{ message.week }}</span>
            天气：<span>{{ message.weather }}</span>
          </td>
          <td
            style="border: 0; font-family: &quot;宋体&quot;; width: 127px"
            align="center"
          >
            值班员:
            <span>{{ message.peopleName }}</span>
          </td>
        </tr>
      </table>

      <table
        style="
          margin-bottom: 30px;
          border-collapse: collapse;
          table-layout: fixed;
          text-align: center;
          color: black !important;
          width: 100%;
        "
        id="b2"
      >
        <!-- 表头 -->
        <tr class="JR_tr" align="center" style="font-family: &quot;宋体&quot;">
          <td colspan="2" rowspan="2" class="JR_left">实力</td>

          <td colspan="8" class="JR_left">编制数</td>
          <td colspan="8" class="JR_left">实力数</td>
        </tr>

        <!-- 编制数 / 实力数内容 -->
        <tr class="JR_tr" align="center">
          <!-- 编制数 -->
          <td class="JR_left">军官</td>
          <td class="JR_left">{{ message.orgcadre }}</td>

          <td class="JR_left">士兵</td>
          <td class="JR_left">{{ message.orgsoldier }}</td>

          <td class="JR_left">其他</td>
          <td class="JR_left">{{ message.orgother }}</td>

          <td class="JR_left">合计</td>
          <td class="JR_left">
            {{ message.orgcadre + message.orgsoldier + message.orgother }}
          </td>

          <!-- 实力数 -->
          <td class="JR_left">军官</td>
          <td class="JR_left">{{ message.nowcadre }}</td>

          <td class="JR_left">士兵</td>
          <td class="JR_left">{{ message.nowsoldier }}</td>

          <td class="JR_left">其他</td>
          <td class="JR_left">{{ message.nowother }}</td>

          <td class="JR_left">合计</td>
          <td class="JR_left">
            {{ message.nowcadre + message.nowsoldier + message.nowother }}
          </td>
        </tr>
        <!--              </table>-->
        <!-- 训练、教育或者执行其他任务情况 -->
        <tr class="JR_tr" align="center">
          <td rowspan="6" class="JR_left" width="48">
            <p
              align="center"
              style="margin: 0 15px; font-family: &quot;宋体&quot;"
            >
              训 <br />
              练 <br />
              、 <br />
              教 <br />
              育 <br />
              或 <br />
              者 <br />
              执 <br />
              行 <br />
              其 <br />
              他 <br />
              任 <br />
              务 <br />
              情 <br />
              况
            </p>
          </td>
          <td
            rowspan="2"
            width="51"
            class="JR_left"
            style="font-family: &quot;宋体&quot;"
          >
            区<br />
            分
          </td>
          <td
            colspan="10"
            rowspan="2"
            style="letter-spacing: 20px; font-family: &quot;宋体&quot;"
            class="JR_left"
            align="center"
          >
            内容
          </td>
          <!--          <td-->
          <!--            colspan="3"-->
          <!--            class="JR_left"-->
          <!--            height="22"-->
          <!--            style="font-family: &quot;宋体&quot;"-->
          <!--          >-->
          <!--            人数-->
          <!--          </td>-->
        </tr>
        <tr class="JR_tr" height="30" align="center">
          <td
            colspan="2"
            class="JR_left"
            height="34"
            style="font-family: &quot;宋体&quot;"
          >
            应到<br />
            人数
          </td>
          <td colspan="2" class="JR_left" style="font-family: &quot;宋体&quot;">
            实到<br />
            人数
          </td>
          <td
            colspan="2"
            class="JR_left"
            width="52"
            style="font-family: &quot;宋体&quot;"
          >
            到课率
          </td>
        </tr>
        <tr
          v-for="(item, i) in bdglDiaryWeekworkRegisterList"
          :key="i + '     '"
        >
          <td height="88">{{ item.dateline }}</td>
          <td align="left" colspan="10">
            <p v-html="item.contents" style="margin-left: 7px"></p>
          </td>
          <td colspan="2" v-html="item.shouldArrive"></td>
          <td colspan="2" v-html="item.realTo"></td>
          <td colspan="2" v-html="bbb(item.percentage)"></td>
        </tr>
        <tr v-for="(item, i) in meiShuJu" :key="i + '      '">
          <td height="88">{{ item.dateline }}</td>
          <td align="left" colspan="9">
            <p style="margin-left: 7px"></p>
          </td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <!-- 公差勤务 -->
        <tr class="JR_tr" height="45">
          <td colspan="2">
            <p
              align="center"
              class="JR_left"
              style="font-family: &quot;宋体&quot;"
            >
              公差勤务
            </p>
          </td>
          <td
            align="left"
            style="font-family: &quot;仿宋_GB2312&quot;"
            colspan="16"
            class="JR_right"
            v-html="message.tolerance"
          ></td>
        </tr>
        <!-- 人员装备变动 -->
        <tr class="JR_tr" height="61">
          <td colspan="2" class="JR_left">
            <p align="center" style="font-family: &quot;宋体&quot;">
              人员或 <br />
              装备变动
            </p>
          </td>
          <td
            align="left"
            style="font-family: &quot;仿宋_GB2312&quot;"
            colspan="16"
            class="JR_right"
            height="56"
            v-html="message.transfer"
          ></td>
        </tr>
        <!-- 武器装备检查情况 -->
        <tr class="JR_tr" height="207">
          <td rowspan=":count($xlian)" colspan="2" class="JR_left">
            <p align="center" style="font-family: &quot;宋体&quot;">
              装 内 <br />
              备 务 <br />
              、 卫 <br />
              军 生 <br />
              容 检 <br />
              风 查 <br />
              纪 情 <br />
              、 况 <br />
            </p>
          </td>
          <td
            align="left"
            style="font-family: &quot;仿宋_GB2312&quot;"
            colspan="16"
            class="JR_right"
            v-html="message.internal"
          ></td>
        </tr>
      </table>

      <table
        style="
          border-collapse: collapse;
          table-layout: fixed;
          text-align: center;
          color: black !important;
        "
        id="b2"
      >
        <!-- 查铺查哨 -->
        <tr class="JR_tr" style="font-family: &quot;宋体&quot;">
          <td :rowspan="chapu.rowspan" class="JR_left" width="57">
            <p align="center" style="font-family: &quot;宋体&quot;">
              查 <br />
              铺 <br />
              查 <br />
              哨
            </p>
          </td>
          <td class="JR_left" width="70" height="37">
            <span align="center" style="font-family: &quot;宋体&quot;"
              >检查人</span
            >
          </td>
          <td class="JR_left" width="70">
            <span align="center" style="font-family: &quot;宋体&quot;"
              >时　间</span
            >
          </td>
          <td class="JR_left" width="70">
            <span align="center" style="font-family: &quot;宋体&quot;"
              >领班员</span
            >
          </td>
          <td class="JR_left" colspan="2" width="80">
            <span align="center" style="font-family: &quot;宋体&quot;"
              >卫兵姓名</span
            >
          </td>
          <td class="JR_left" colspan="8" width="280">
            <span
              align="center"
              style="letter-spacing: 10px; font-family: &quot;宋体&quot;"
            >
              检　查　情　况
            </span>
          </td>
        </tr>
        <tr
          v-for="(item, index) in message.inspect"
          :key="index + '  '"
          class="JR_tr"
          style="font-family: &quot;仿宋&quot;"
        >
          <td class="JR_right" height="33">
            <span align="center">{{ item.peopleName }}</span>
          </td>
          <td class="JR_right">
            <span align="center">{{ item.examineDate }}</span>
          </td>
          <td class="JR_right">
            <span align="center">{{ item.foremanName }}</span>
          </td>
          <td class="JR_right" colspan="2">
            <span align="center">{{ item.sentryName }}</span>
          </td>
          <td class="JR_right" colspan="8">
            <span align="center">{{ item.dialogue }}</span>
          </td>
        </tr>
        <tr
          v-for="index in chapu.list"
          :key="index + ''"
          class="JR_tr"
          style="font-family: &quot;仿宋&quot;"
        >
          <td class="JR_right" height="33"></td>
          <td class="JR_right"></td>
          <td class="JR_right"></td>
          <td class="JR_right" colspan="2"></td>
          <td class="JR_right" colspan="8"></td>
        </tr>
        <tr class="JR_tr" style="font-family: &quot;宋体&quot;">
          <td class="JR_left" rowspan="4">
            <p align="center" style="font-family: &quot;宋体&quot;">
              派 <br />
              班 <br />
              情 <br />
              况
            </p>
          </td>
          <td class="JR_left" height="26">
            <span
              align="center"
              style="font-family: &quot;宋体&quot;; height: 20px"
              >职务</span
            >
          </td>
          <td class="JR_left">
            <p align="center" style="font-family: &quot;宋体&quot;">交班者</p>
          </td>
          <td class="JR_left">
            <p align="center" style="font-family: &quot;宋体&quot;">接班者</p>
          </td>
          <td class="JR_left" colspan="2">
            <p align="center" style="font-family: &quot;宋体&quot;">组织者</p>
          </td>
          <td class="JR_left" colspan="8">
            <p
              align="center"
              style="letter-spacing: 10px; font-family: &quot;宋体&quot;"
            >
              交 接　情　况
            </p>
          </td>
        </tr>
        <tr class="JR_tr" height="30" align="center">
          <td class="JR_left" style="font-family: &quot;宋体&quot;">值班员</td>
          <td class="JR_right">
            <p align="center" style="font-family: &quot;仿宋&quot;">
              {{ message.dutya }}
            </p>
          </td>
          <td class="JR_right">
            <p align="center" style="font-family: &quot;仿宋&quot;">
              {{ message.dutyb }}
            </p>
          </td>
          <td class="JR_right" colspan="2">
            <p align="center" style="font-family: &quot;仿宋&quot;">
              {{ message.dutylead }}
            </p>
          </td>
          <td class="JR_right" colspan="8">
            <span align="center">
              {{ message.dutyinfoa }}
            </span>
          </td>
        </tr>
        <tr class="JR_tr" height="30" align="center">
          <td class="JR_left" style="font-family: &quot;宋体&quot;">值日员</td>
          <td class="JR_right">
            <p align="center" style="font-family: &quot;仿宋&quot;">
              {{ message.dutyc }}
            </p>
          </td>
          <td class="JR_right">
            <p align="center" style="font-family: &quot;仿宋&quot;">
              {{ message.dutyd }}
            </p>
          </td>
          <td
            class="JR_left"
            rowspan="2"
            colspan="2"
            style="font-family: &quot;仿宋&quot;"
          >
            <div style="margin-bottom: 10px">交接时间</div>
            <span v-if="message.dutyDate">{{
              message.dutyDate | dataFormatshiF
            }}</span>
            <span v-else>时 分</span>
          </td>
          <td class="JR_right" colspan="8">
            <span align="center" style="仿宋">
              {{ message.dutyinfoc }}
            </span>
          </td>
        </tr>
        <tr class="JR_tr" height="30" align="center">
          <td class="JR_left" style="font-family: &quot;宋体&quot;">
            厨房值班
          </td>
          <td class="JR_right">
            <p align="center" style="font-family: &quot;仿宋&quot;">
              {{ message.dutye }}
            </p>
          </td>
          <td class="JR_right">
            <p align="center" style="font-family: &quot;仿宋&quot;">
              {{ message.dutyf }}
            </p>
          </td>
          <td
            class="JR_right"
            colspan="8"
            style="font-family: &quot;仿宋&quot;"
          >
            <span align="center">
              {{ message.dutyinfoe }}
            </span>
          </td>
        </tr>
        <!-- 临时来队亲属 -->
        <tr class="JR_tr" style="font-family: &quot;宋体&quot;">
          <td :rowspan="qinshu.rowspan" class="JR_left">
            <span align="center" style="font-family: &quot;宋体&quot;">
              临 <br />
              时 <br />
              来 <br />
              队 <br />
              亲 <br />
              属
            </span>
          </td>
          <td class="JR_left" rowspan="2">
            <span align="center" style="font-family: &quot;宋体&quot;"
              >军人姓名</span
            >
          </td>
          <td class="JR_left" rowspan="2">
            <span align="center" style="font-family: &quot;宋体&quot;"
              >亲属姓名</span
            >
          </td>
          <td class="JR_left" rowspan="2">
            <span align="center" style="font-family: &quot;宋体&quot;"
              >关&nbsp; 系</span
            >
          </td>
          <td class="JR_left" height="31" colspan="2">
            <span align="center" style="font-family: &quot;宋体&quot;"
              >时　间</span
            >
          </td>
          <td class="JR_left" colspan="8">
            <span
              align="center"
              style="letter-spacing: 3px; font-family: &quot;宋体&quot;"
            >
              病 号 及 处 理 情 况
            </span>
          </td>
        </tr>
        <tr class="JR_tr" align="center">
          <td
            class="JR_left"
            width="42"
            height="23"
            style="font-family: &quot;宋体&quot;"
          >
            来队
          </td>
          <td
            class="JR_left"
            width="38"
            height="23"
            style="font-family: &quot;宋体&quot;"
          >
            离队
          </td>
          <td
            style="font-family: &quot;仿宋&quot;"
            class="JR_right"
            colspan="8"
            :rowspan="qinshu.rowspan - 1"
            v-html="message.invalid"
          ></td>
        </tr>
        <tr
          style="font-family: &quot;仿宋&quot;"
          class="JR_tr"
          v-for="(item1, i) in message.kinsfolk"
          :key="i + ' '"
        >
          <td class="JR_right" height="33">
            <span align="center">{{ item1.peopleName }}</span>
          </td>
          <td class="JR_right">
            <span align="center">{{ item1.name }}</span>
          </td>

          <td class="JR_right">
            <span align="center">{{ item1.relation }}</span>
          </td>
          <td class="JR_right">
            <span align="center">{{ item1.comeDate }}</span>
          </td>
          <td class="JR_right">
            <span align="center">{{ item1.leaveDate }}</span>
          </td>
        </tr>
        <tr
          style="font-family: &quot;仿宋&quot;"
          class="JR_tr"
          v-for="i in qinshu.list"
          :key="i + '  '"
        >
          <td class="JR_right" height="33"></td>
          <td class="JR_right"></td>

          <td class="JR_right"></td>
          <td class="JR_right"></td>
          <td class="JR_right"></td>
        </tr>
        <tr class="JR_tr" align="center" style="font-family: &quot;宋体&quot;">
          <td :rowspan="qingjia.rowspan" class="JR_left">
            <span align="center" style="font-family: &quot;宋体&quot;">
              请 <br />
              假 <br />
              外 <br />
              出 <br />
              、 <br />
              休 <br />
              假
            </span>
          </td>
          <td class="JR_left" style="font-family: &quot;宋体&quot;" height="36">
            姓&nbsp; 名
          </td>
          <td class="JR_left" style="font-family: &quot;宋体&quot;">
            职&nbsp; 务
          </td>
          <td class="JR_left" colspan="2" style="font-family: &quot;宋体&quot;">
            事&nbsp;&nbsp;&nbsp; 由
          </td>
          <td class="JR_left" colspan="1" style="font-family: &quot;宋体&quot;">
            时数或 <br />
            天&nbsp; 数
          </td>
          <td class="JR_left" colspan="2" style="font-family: &quot;宋体&quot;">
            准假部 <br />
            门(人)
          </td>
          <td class="JR_left" colspan="2" style="font-family: &quot;宋体&quot;">
            离 队 <br />
            时 间
          </td>
          <td class="JR_left" colspan="2" style="font-family: &quot;宋体&quot;">
            归 队 <br />
            时 间
          </td>
          <td class="JR_left" colspan="3" style="font-family: &quot;宋体&quot;">
            超(误)假 <br />
            时(天)数
          </td>
        </tr>
        <tr
          class="JR_tr"
          style="font-family: &quot; 仿宋&quot;"
          v-for="(item, i) in message.bdglLeaveOneList"
          :key="i + '   '"
        >
          <td class="JR_right">
            <p align="center">{{ item.peopleName }}</p>
          </td>
          <td class="JR_right">
            <p align="center">
              {{ item.postName }}
              <!-- <dict-tag :options="dict.type.post_Level" :value="item.postId" /> -->
            </p>
          </td>
          <td class="JR_right" colspan="2">
            <p align="center">{{ item.leaveReason }}</p>
          </td>
          <td class="JR_right" colspan="1">
            <p align="center">{{ item.shiShu }}</p>
          </td>
          <td class="JR_right" colspan="2">
            <p align="center">{{ item.approvalDepartment }}</p>
          </td>
          <td class="JR_right" colspan="2">
            <p align="center">{{ item.departureTime | dataFormatshiF }}</p>
          </td>
          <td class="JR_right" colspan="2">
            <p align="center">{{ item.returnTime | dataFormatshiF }}</p>
          </td>
          <td class="JR_right" colspan="3">
            <p align="center">{{ item.chao }}</p>
          </td>
        </tr>
        <tr
          class="JR_tr"
          height="30"
          style="font-family: &quot;仿宋&quot;"
          v-for="i in num"
          :key="i + '    '"
        >
          <td class="JR_right"></td>
          <td class="JR_right">
            <p align="center"></p>
          </td>
          <td class="JR_right" colspan="2"></td>
          <td class="JR_right" colspan="1"></td>
          <td class="JR_right" colspan="2"></td>
          <td class="JR_right" colspan="2"></td>
          <td class="JR_right" colspan="2"></td>
          <td class="JR_right" colspan="3"></td>
        </tr>

        <!-- 上级通知 -->
        <tr class="JR_tr">
          <td
            class="JR_left"
            width="101"
            height="255"
            style="font-family: &quot;宋体&quot;"
          >
            <span align="center" style="font-family: &quot;宋体&quot;">
              上 <br />
              级 <br />
              通 <br />
              知 <br />
              、 <br />
              指 <br />
              示 <br />
              及 <br />
              其 <br />
              他 <br />
              重 <br />
              要 <br />
              事 <br />
              项
            </span>
          </td>
          <td
            class="JR_right"
            align="left"
            colspan="15"
            style="white-space: normal; font-family: &quot;仿宋&quot;"
            v-html="message.notice"
          ></td>
        </tr>
      </table>

      <table
        id="b1"
        border="none"
        style="
          width: 600px;
          color: black;
          font-size: 16px;
          font-weight: 500;
          border: none;
        "
      >
        <tr style="border: none; font-family: &quot;宋体&quot;">
          <td style="border: none" align="center" width="264">
            <p style="border: none; font-family: &quot;宋体&quot;">
              连队首长签字: {{ message.sz }}
            </p>
          </td>
          <td style="border: none; width: 172px"></td>
          <td
            style="border: none; font-family: &quot;宋体&quot;"
            align="center"
          >
            <p
              style="
                border: none;
                font-family: &quot;宋体&quot;;
                margin-left: 30px;
              "
            >
              {{ message.blogDate | dataFormaty }}
            </p>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
// import '@/utils/jquery.wordexport.js'
import exportWord from "@/utils/exportWord.js";

export default {
  name: "bdgdiarys",
  props: ["message"],
  dicts: ["post_Level"],
  data() {
    return {
      title: "连队要事日记",
      chapu: {
        rowspan: 0,
        list: 0,
      },
      qinshu: {
        rowspan: 0,
        list: 0,
      },
      week: "",
      qinshu2: 0,
      number: 6,
      time: [],
      guiTime: [],
      faith2: [],
      faith1: [],
      qingjia: {
        rowspan: 0,
        list: 0,
      },
      xlist: {},
      meiShuJu: [],
      xLRen: [],
      faith: {
        fileName: "连队要事日记", //文档名
        fileType: ".doc", //文档类型  经测试  可以doc xls html 其他的自己去试
        // header: '测试页眉',//页眉标题
        // footer: '测试页脚',//页脚标题
        direction: "", //默认或不填写为纵向，Horizontal 为横向
        dom: "#contents", // dom节点
        dev: "margin:80.0pt 62.3pt 102.0pt 62.3pt;",
        style: `table {
                  border-collapse: collapse;
                  table-layout: fixed;
                  text-align: center;
                  color: black !important;
                  font-size: 1em;
                }

               #b2 td {
                  border: 1px solid !important;
                  padding: 0;
                  border-bottom: 1px solid !important;
                }

                .JR_left{font-size: 1em; font-family: '楷体_GB2312';}
                .JR_right{ font-size: 1em; font-family: 仿宋_GB2312_GB2312;}
                `,
      },
      num: 0,
      zao: [],
      shang: [],
      xia: [],
      wan: [],
      bdglDiaryWeekworkRegisterList: [],
      typeTime: ["早上", "上午", "下午", "晚上"],
    };
  },
  created() {
    this.chapus();
    this.qinshus();
    this.week = this.getWeek(this.message.blogDate);
    this.qinshu2 = this.qinshus2();
    this.xunlian();
    this.qingjias();
    this.xunlians();
    this.tiemSGui();
    if (this.message.bdglLeaveOneList) {
      if (this.message.bdglLeaveOneList.length >= 6) {
        this.num = 0;
      } else {
        this.num = 7 - this.message.bdglLeaveOneList.length;
      }
    } else {
      this.num = 7;
    }
  },
  mounted() {},

  methods: {
    // 计算天数
    tiemSGui() {
      var nums = null;
      var chao = null;
      if (this.message.bdglLeaveOneList != null) {
        this.message.bdglLeaveOneList.forEach((item, i) => {
          nums =
            Date.parse(new Date(item.endTime)) -
            Date.parse(new Date(item.startTime));
          chao =
            Date.parse(new Date(item.returnTime)) -
            Date.parse(new Date(item.endTime));
          if (nums % 1 != 0) {
            // nums = (nums / 3600 / 1000).toFixed(1);
          } else {
            // nums = nums / 3600 / 1000;
            nums = (nums / 3600 / 1000).toFixed(1);
          }
          if (chao >= 0) {
            if (chao % 1 != 0) {
              // chao = (chao / 3600 / 1000).toFixed(1);
            } else {
              // chao = chao / 3600 / 1000;
              chao = (chao / 3600 / 1000).toFixed(1);
            }
            this.message.bdglLeaveOneList[i].chao = chao;
          }
          this.message.bdglLeaveOneList[i].shiShu = nums;
        });
      }
    },
    xunlianRen(...args) {
      for (let i = 0; i < args.length; i++) {
        this.bdglDiaryWeekworkRegisterList.push(this.tool(args[i]));
      }
    },

    tool(i) {
      let dateline = "",
        contents = "",
        realTo = "",
        shouldArrive = "",
        percentage = "";
      i.forEach((item) => {
        dateline = item.dateline;
        contents += item.contents + "<br/>";
        realTo += item.realTo + "<br/>";
        shouldArrive += item.shouldArrive + "<br/>";
        percentage += item.percentage + "<br/>";
      });
      // console.log({ dateline, contents, realTo, shouldArrive, percentage });
      return { dateline, contents, realTo, shouldArrive, percentage };
    },

    daochu() {
      exportWord(this.faith);
    },
    aaa(raw) {
      // console.log(raw);
      if (raw != "" && raw != null) {
        return raw.replaceAll(",", "<br/>");
      } else {
        return (raw = null);
      }
    },
    bbb(raw) {
      if (raw != "" && raw != null) {
        // console.log(raw, 1);
        return raw.replaceAll(".00", "");
      } else {
        return (raw = null);
      }
    },
    xunlians() {
      if (this.message.bdglDiaryWeekworkRegisterList) {
        for (
          let index = 0;
          index < this.message.bdglDiaryWeekworkRegisterList.length;
          index++
        ) {
          this.faith1.push(
            this.message.bdglDiaryWeekworkRegisterList[index].dateline,
          );
        }
        if (this.faith1.indexOf("早晨") == -1) {
          this.message.bdglDiaryWeekworkRegisterList.push({
            dateline: "早晨",
            contents: "",
            percentage: "",
            realTo: "",
            shouldArrive: "",
          });
        }
        if (this.faith1.indexOf("上午") == -1) {
          this.message.bdglDiaryWeekworkRegisterList.push({
            dateline: "上午",
            contents: "",
            percentage: "",
            realTo: "",
            shouldArrive: "",
          });
        }
        if (this.faith1.indexOf("下午") == -1) {
          this.message.bdglDiaryWeekworkRegisterList.push({
            dateline: "下午",
            contents: "",
            percentage: "",
            realTo: "",
            shouldArrive: "",
          });
        }
        if (this.faith1.indexOf("晚上") == -1) {
          this.message.bdglDiaryWeekworkRegisterList.push({
            dateline: "晚上",
            contents: "",
            percentage: "",
            realTo: "",
            shouldArrive: "",
          });
        }
        for (
          let index = 0;
          index < this.message.bdglDiaryWeekworkRegisterList.length;
          index++
        ) {
          if (
            this.message.bdglDiaryWeekworkRegisterList[index].dateline == "早晨"
          ) {
            this.zao.push(this.message.bdglDiaryWeekworkRegisterList[index]);
          }

          if (
            this.message.bdglDiaryWeekworkRegisterList[index].dateline == "上午"
          ) {
            this.shang.push(this.message.bdglDiaryWeekworkRegisterList[index]);
          }

          if (
            this.message.bdglDiaryWeekworkRegisterList[index].dateline == "下午"
          ) {
            this.xia.push(this.message.bdglDiaryWeekworkRegisterList[index]);
          }

          if (
            this.message.bdglDiaryWeekworkRegisterList[index].dateline == "晚上"
          ) {
            this.wan.push(this.message.bdglDiaryWeekworkRegisterList[index]);
          }
        }
        this.xunlianRen(this.zao, this.shang, this.xia, this.wan);
        // this.faith2 = [...this.zao, ...this.shang, ...this.xia, ...this.wan];
        this.message.bdglDiaryWeekworkRegisterList = this.faith2;
      } else {
        this.number = 6;
        this.meiShuJu = [
          {
            dateline: "早晨",
          },
          {
            dateline: "上午",
          },
          {
            dateline: "下午",
          },
          {
            dateline: "晚上",
          },
        ];
      }
    },
    chapus() {
      if (this.message.inspect != null) {
        if (this.message.inspect.length >= 2) {
          this.chapu.rowspan = this.message.inspect.length + 1;
          this.chapu.list = 0;
        } else {
          this.chapu.rowspan =
            2 - this.message.inspect.length + this.message.inspect.length + 1;
          this.chapu.list = 2 - this.message.inspect.length;
        }
      } else {
        this.chapu.rowspan = 3;
        this.chapu.list = 2;
      }
    },
    qinshus() {
      if (this.message.kinsfolk != null) {
        if (this.message.kinsfolk.length >= 3) {
          this.qinshu.rowspan = 2 + this.message.kinsfolk.length;
          this.qinshu.list = 0;
        } else {
          this.qinshu.rowspan =
            3 - this.message.kinsfolk.length + this.message.kinsfolk.length + 2;
          this.qinshu.list = 3 - this.message.kinsfolk.length;
        }
      } else {
        this.qinshu.rowspan = 5;
        this.qinshu.list = 3;
      }
    },

    qinshus2() {
      if (this.message.kinsfolk != null) {
        return this.message.kinsfolk.length + 1;
      }
      return 3;
    },
    xunlian() {
      this.getTian();
      this.getCao();
      if (this.message.weekworkRegisters != null) {
        if (this.message.weekworkRegisters.length > 4) {
          this.number += this.message.weekworkRegisters.length - 4;
        }
      }
    },
    qingjias() {
      if (this.message.bdglLeaves != null) {
        if (this.message.bdglLeaves.length >= 7) {
          this.qingjia.rowspan = this.message.bdglLeaves.length + 1;
          this.qingjia.list = 0;
        } else {
          this.qingjia.rowspan =
            7 -
            this.message.bdglLeaves.length +
            this.message.bdglLeaves.length +
            1;
          this.qingjia.list = 7 - this.message.bdglLeaves.length;
        }
      } else {
        this.qingjia.rowspan = 8;
        this.qingjia.list = 7;
      }
    },

    /*
      获取当前星期几
     */
    getWeek(dateString) {
      var dateArray = dateString.split("-");
      var date = new Date(
        dateArray[0],
        parseInt(dateArray[1] - 1),
        dateArray[2],
      );
      return "星期" + "日一二三四五六".charAt(date.getDay());
    },
    // 计算请假天数
    getTian() {
      if (this.message.bdglLeaves != null) {
        this.message.bdglLeaves.forEach((element) => {
          var dateBegin = new Date(element.startTime).getTime();
          var dateBegins = new Date(element.endTime).getTime();
          var num = dateBegins - dateBegin;
          num = num / (1000 * 3600 * 24);
          this.time.push(num);
        });
      }
    },
    // 计算超时天数
    getCao() {
      if (this.message.bdglLeaves != null) {
        this.message.bdglLeaves.forEach((element) => {
          var dateBegin = new Date(element.actualreturntime).getTime();
          var dateBegins = new Date(element.returnTime).getTime();
          var num = dateBegin - dateBegins;
          num = num / (1000 * 3600 * 24);
          if (num < 0) {
            num = 0;
          }
          this.guiTime.push(num);
        });
      }
    },
    /*
      标准时间转换格式
     */
    getDate(timesDate) {
      var date = new Date(timesDate);
      var y = date.getFullYear();
      var m = date.getMonth() + 1;
      m = m < 10 ? "0" + m : m;
      var d = date.getDate();
      d = d < 10 ? "0" + d : d;
      var h = date.getHours();
      var minute = date.getMinutes();
      minute = minute < 10 ? "0" + minute : minute;
      let time = y + "-" + m + "-" + d; //这里如果不需要小时 分  后边的可以不需要拼接
      return time;
    },
  },
};
</script>
<style scoped>
.box {
  height: 100%;
  margin-left: 0 !important;
}

.word {
  width: 711px;
  margin: 60px auto;
}

.title {
  color: #000000;
}

.spacing {
  letter-spacing: 19px;
}

.el-dialog__wrapper .el-dialog__body td {
  border: 1px solid !important;
  padding: 0;
  border-bottom: 1px solid !important;
}

#b1 td {
  padding: 0;
  border: 0 !important;
}

.box1 {
  position: absolute;
  right: 30px;
  top: -60px;
  border: 1px solid rgb(129, 130, 131);
  border-radius: 5px;
}

.distinguish {
  min-height: 67px;
  writing-mode: tb-rl;
  margin: 4px auto 0px;
  letter-spacing: 9px;
}
</style>
