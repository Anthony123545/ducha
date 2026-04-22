<template>
  <div>
    <el-button class="box1" @click="daochu">导出</el-button>
    <div class="word" id="contents">
      <p
        style="
          font-size: 16pt;
          font-weight: 600;
          text-align: center;
          margin-botttom: 30px;
          color: black;
          font-family: 'songti';
        "
      >
        《连队要事日记》
      </p>
      <!-- <div style="margin-bottom: 5px; color: black">
        <span class="tTime">{{ message.blogDate }}</span>
        <span class="tWeek" style="margin-left: 40px">星期</span>
        <span class="tWeather" style="margin-left: 50px"
          >天气：{{ message.weather }}</span
        >
        <span style="margin-left: 380px">值班员:{{ message.peopleName }}</span>
      </div> -->

      <table
        class="b1"
        border="none"
        style="
          border-collapse: collapse;
          mso-border-left-alt: 0.5pt solid windowtext;
          mso-border-top-alt: 0.5pt solid windowtext;
          mso-border-right-alt: 0.5pt solid windowtext;
          mso-border-bottom-alt: 0.5pt solid windowtext;
          mso-border-insideh: 0.5pt solid windowtext;
          mso-border-insidev: 0.5pt solid windowtext;
          mso-padding-alt: 0pt 0pt 0pt 0pt;
          width: 930px;
          color: black;
          font-size: 16px;
          font-weight: 500;
          border: none;
        "
      >
        <tr
          height="30"
          align="center"
          style="border: none; font-family: 'songti'"
        >
          <td
            colspan="3"
            height="40"
            style="border: none; font-family: 'fangsong'"
          >
            {{ message.blogDate | dataFormats }}
          </td>
          <td colspan="2" style="border: none; font-family: 'songti'">
            <span style="font-family: 'fangsong'">{{ message.week }}</span>
          </td>
          <td colspan="6" style="border: none; font-family: 'songti'">
            天气：<span style="font-family: 'fangsong'">{{
              message.weather
            }}</span>
          </td>
          <td colspan="3" style="border: none; font-family: 'songti'">
            值班员:
            <span style="font-family: 'fangsong'">{{
              message.peopleName
            }}</span>
          </td>
        </tr>
      </table>
      <table
        class="b1"
        border="1"
        style="
          border-collapse: collapse;
          mso-border-left-alt: 0.5pt solid windowtext;
          mso-border-top-alt: 0.5pt solid windowtext;
          mso-border-right-alt: 0.5pt solid windowtext;
          mso-border-bottom-alt: 0.5pt solid windowtext;
          mso-border-insideh: 0.5pt solid windowtext;
          mso-border-insidev: 0.5pt solid windowtext;
          mso-padding-alt: 0pt 0pt 0pt 0pt;
          width: 930px;
          margin-bottom: 8px;
          border-color: black;
          color: black;
          font-size: 16px;
          font-weight: 500;
        "
      >
        <!-- 实力 -->
        <tr
          class="JR_tr"
          height="30"
          align="center"
          style="font-family: 'songti'"
        >
          <td
            colspan="2"
            rowspan="2"
            class="JR_left"
            style="font-family: 'songti'"
          >
            实力
          </td>
          <td
            colspan="6"
            style="letter-spacing: 10px; font-family: 'songti'"
            class="JR_left"
          >
            编制人数
          </td>
          <td
            colspan="6"
            style="letter-spacing: 10px; font-family: 'songti'"
            class="JR_left"
          >
            现有人数
          </td>
        </tr>
        <tr class="JR_tr" height="30" align="center">
          <td width="68" class="JR_left" style="font-family: 'songti'">干部</td>
          <td width="68" class="JR_left" style="font-family: 'fangsong'">
            {{ message.orgcadre }}
          </td>
          <td width="68" class="JR_left" style="font-family: 'songti'">战士</td>
          <td width="68" class="JR_left" style="font-family: 'fangsong'">
            {{ message.orgsoldier }}
          </td>
          <td width="68" class="JR_left" style="font-family: 'songti'">合计</td>
          <td width="68" class="JR_left" style="font-family: 'fangsong'">
            {{ message.orgcadre + message.orgsoldier }}
          </td>
          <td width="68" class="JR_left" style="font-family: 'songti'">干部</td>
          <td width="68" class="JR_left" style="font-family: 'fangsong'">
            {{ message.nowcadre }}
          </td>
          <td width="68" class="JR_left" style="font-family: 'songti'">战士</td>
          <td width="68" class="JR_left" style="font-family: 'fangsong'">
            {{ message.nowsoldier }}
          </td>
          <td width="68" class="JR_left" style="font-family: 'songti'">合计</td>
          <td width="68" class="JR_left" style="font-family: 'fangsong'">
            {{ message.nowcadre + message.nowsoldier }}
          </td>
        </tr>
        <!-- 训练、教育或者执行其他任务情况 -->
        <tr class="JR_tr" height="20" align="center">
          <td :rowspan="number" class="JR_left">
            <p align="center" style="margin: 0 15px; font-family: 'songti'">
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
            width="68"
            class="JR_left"
            style="font-family: 'songti'"
          >
            区分
          </td>
          <td
            colspan="9"
            rowspan="2"
            style="letter-spacing: 20px; font-family: 'songti'"
            class="JR_left"
            align="center"
          >
            内容
          </td>
          <td colspan="3" class="JR_left" style="font-family: 'songti'">
            人数
          </td>
        </tr>
        <tr class="JR_tr" height="30" align="center">
          <td class="JR_left" style="font-family: 'songti'">应到</td>
          <td class="JR_left" style="font-family: 'songti'">实到</td>
          <td class="JR_left" style="font-family: 'songti'">到课率</td>
        </tr>
        <tr
          style="font-family: 'fangsong'"
          class="JR_tr"
          height="70"
          align="center"
          v-for="(items, index) in message.weekworkRegisters"
          :key="index + ' '"
          v-show="
            message.weekworkRegisters != null ||
            message.weekworkRegisters.length > 0
          "
        >
          <td class="JR_right" height="70">{{ items.dateline }}</td>
          <td
            style="text-align: left"
            colspan="9"
            v-html="
              items.startTime +
              '-' +
              items.endTime +
              items.contents +
              '(组织人：' +
              items.organizer +
              ')'
            "
          ></td>
          <td class="JR_right" height="70">{{ items.shouldArrive }}</td>
          <td class="JR_right" height="70">{{ items.realTo }}</td>
          <td class="JR_right" height="70">{{ items.percentage }}</td>
        </tr>
        <tr
          class="JR_tr"
          height="70"
          align="center"
          v-if="
            message.weekworkRegisters == null ||
            message.weekworkRegisters.length < 4 ||
            message.weekworkRegisters.length == 0
          "
        >
          <td class="JR_left" height="70" style="font-family: 'songti'"></td>
          <td class="JR_right" colspan="9"></td>
          <td class="JR_right"></td>
          <td class="JR_right"></td>
          <td class="JR_right"></td>
        </tr>
        <tr
          class="JR_tr"
          height="70"
          align="center"
          v-if="
            message.weekworkRegisters == null ||
            message.weekworkRegisters.length < 3 ||
            message.weekworkRegisters.length == 0
          "
        >
          <td class="JR_left" height="70" style="font-family: 'songti'"></td>
          <td class="JR_right" colspan="9"></td>
          <td class="JR_right"></td>
          <td class="JR_right"></td>
          <td class="JR_right"></td>
        </tr>
        <tr
          class="JR_tr"
          height="70"
          align="center"
          v-if="
            message.weekworkRegisters == null ||
            message.weekworkRegisters.length < 2 ||
            message.weekworkRegisters.length == 0
          "
        >
          <td class="JR_left" height="70" style="font-family: 'songti'"></td>
          <td class="JR_right" colspan="9"></td>
          <td class="JR_right"></td>
          <td class="JR_right"></td>
          <td class="JR_right"></td>
        </tr>
        <tr
          class="JR_tr"
          height="70"
          align="center"
          v-if="
            message.weekworkRegisters == null ||
            message.weekworkRegisters.length < 1 ||
            message.weekworkRegisters.length == 0
          "
        >
          <td class="JR_left" height="70" style="font-family: 'songti'"></td>
          <td class="JR_right" colspan="9"></td>
          <td class="JR_right"></td>
          <td class="JR_right"></td>
          <td class="JR_right"></td>
        </tr>
        <!-- 公差勤务 -->
        <tr class="JR_tr">
          <td colspan="2">
            <p align="center" class="JR_left" style="font-family: 'songti'">
              公差勤务
            </p>
          </td>
          <td
            style="font-family: 'fangsong'"
            colspan="12"
            class="JR_right"
            v-html="message.tolerance"
          ></td>
        </tr>
        <tr class="JR_tr">
          <!-- 人员装备变动 -->
          <td colspan="2" class="JR_left">
            <p align="center" style="font-family: 'songti'">
              人员或 <br />
              装备变动
            </p>
          </td>
          <td
            style="font-family: 'fangsong'"
            colspan="12"
            class="JR_right"
            v-html="message.transfer"
          >
            <p></p>
          </td>
        </tr>
        <!-- 武器装备检查情况 -->
        <tr class="JR_tr">
          <td height="150" rowspan=":count($xlian)" colspan="2" class="JR_left">
            <p align="center" style="font-family: 'songti'">
              武 内 <br />
              器 务 <br />
              装 卫 <br />
              备 生 <br />
              、 检 <br />
              军 查 <br />
              容 情 <br />
              风 况 <br />
              记 <br />
            </p>
          </td>
          <td
            style="font-family: 'fangsong'"
            colspan="12"
            class="JR_right"
            v-html="message.internal"
          >
            <p></p>
          </td>
        </tr>
        <!-- 查铺查哨 -->
        <tr class="JR_tr" style="font-family: 'songti'">
          <td :rowspan="chapu" class="JR_left">
            <p align="center" style="font-family: 'songti'">
              查 <br />
              铺 <br />
              查 <br />
              哨
            </p>
          </td>
          <td class="JR_left">
            <p align="center" style="font-family: 'songti'">检查人</p>
          </td>
          <td class="JR_left">
            <p align="center" style="font-family: 'songti'">时　间</p>
          </td>
          <td class="JR_left">
            <p align="center" style="font-family: 'songti'">领班员</p>
          </td>
          <td class="JR_left" colspan="2">
            <p align="center" style="font-family: 'songti'">卫兵姓名</p>
          </td>
          <td class="JR_left" colspan="8">
            <p
              align="center"
              style="letter-spacing: 10px; font-family: 'songti'"
            >
              检　查　情　况
            </p>
          </td>
        </tr>
        <tr
          v-for="(item, index) in message.inspect"
          :key="index + '  '"
          class="JR_tr"
          style="font-family: 'fangsong'"
        >
          <td class="JR_right">
            <p align="center">{{ item.peopleName }}</p>
          </td>
          <td class="JR_right">
            <p align="center">{{ item.examineDate }}</p>
          </td>
          <td class="JR_right">
            <p align="center">{{ item.foremanName }}</p>
          </td>
          <td class="JR_right" colspan="2">
            <p align="center">{{ item.sentryName }}</p>
          </td>
          <td class="JR_right" colspan="8">
            <p align="center">{{ item.dialogue }}</p>
          </td>
        </tr>
        <!-- <tr class="JR_tr" height="30">
          <td class="JR_right"><p align="center">&nbsp;</p></td>
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="8"><p align="center"></p></td>
        </tr>
        <tr class="JR_tr" height="30">
          <td class="JR_right"><p align="center">&nbsp;</p></td>
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="8"><p align="center"></p></td>
        </tr> -->
        <!-- 派班情况 -->
        <tr class="JR_tr" style="font-family: 'songti'">
          <td class="JR_left" rowspan="4">
            <p align="center" style="font-family: 'songti'">
              派 <br />
              班 <br />
              情 <br />
              况
            </p>
          </td>
          <td class="JR_left">
            <p align="center" style="font-family: 'songti'">职务</p>
          </td>
          <td class="JR_left">
            <p align="center" style="font-family: 'songti'">交班者</p>
          </td>
          <td class="JR_left">
            <p align="center" style="font-family: 'songti'">接班者</p>
          </td>
          <td class="JR_left" colspan="2">
            <p align="center" style="font-family: 'songti'">组织者</p>
          </td>
          <td class="JR_left" colspan="8">
            <p
              align="center"
              style="letter-spacing: 10px; font-family: 'songti'"
            >
              交 接　情　况
            </p>
          </td>
        </tr>
        <tr class="JR_tr" height="30" align="center">
          <td class="JR_left" style="font-family: 'songti'">值班员</td>
          <td class="JR_right">
            <p align="center" style="font-family: 'fangsong'">
              {{ message.dutya }}
            </p>
          </td>
          <td class="JR_right">
            <p align="center" style="font-family: 'fangsong'">
              {{ message.dutyb }}
            </p>
          </td>
          <td class="JR_right" colspan="2">
            <p align="center" style="font-family: 'fangsong'">
              {{ message.dutylead }}
            </p>
          </td>
          <td class="JR_right" colspan="8"><p align="center"></p></td>
        </tr>
        <tr class="JR_tr" height="30" align="center">
          <td class="JR_left" style="font-family: 'songti'">值日员</td>
          <td class="JR_right">
            <p align="center" style="font-family: 'fangsong'">
              {{ message.dutyc }}
            </p>
          </td>
          <td class="JR_right">
            <p align="center" style="font-family: 'fangsong'">
              {{ message.dutyd }}
            </p>
          </td>
          <td
            class="JR_left"
            colspan="2"
            rowspan="2"
            style="font-family: 'fangsong'"
          >
            <div style="margin-bottom: 10px">交接时间</div>
            {{ message.dutyDate | dataFormat }}
          </td>
          <td class="JR_right" colspan="8"></td>
        </tr>
        <tr class="JR_tr" height="30" align="center">
          <td class="JR_left" style="font-family: 'songti'">厨房值班</td>
          <td class="JR_right">
            <p align="center" style="font-family: 'fangsong'">
              {{ message.dutye }}
            </p>
          </td>
          <td class="JR_right">
            <p align="center" style="font-family: 'fangsong'">
              {{ message.dutyf }}
            </p>
          </td>
          <td class="JR_right" colspan="8" style="font-family: 'fangsong'"></td>
        </tr>

        <!-- 临时来队亲属 -->
        <tr class="JR_tr" height="30" style="font-family: 'songti'">
          <td :rowspan="qinshu" class="JR_left">
            <p align="center" style="margin: 0 15px; font-family: 'songti'">
              临 <br />
              时 <br />
              来 <br />
              队 <br />
              亲 <br />
              属
            </p>
          </td>
          <td class="JR_left" height="30" rowspan="2">
            <p align="center" style="font-family: 'songti'">军人姓名</p>
          </td>
          <td class="JR_left" height="30" rowspan="2">
            <p align="center" style="font-family: 'songti'">亲属姓名</p>
          </td>
          <td class="JR_left" height="30" rowspan="2">
            <p align="center" style="font-family: 'songti'">关&nbsp; 系</p>
          </td>
          <td class="JR_left" height="30" colspan="2">
            <p align="center" style="font-family: 'songti'">时　间</p>
          </td>
          <td class="JR_left" height="30" colspan="8">
            <p
              align="center"
              style="letter-spacing: 5px; font-family: 'songti'"
            >
              病 号 及 处 理 情 况
            </p>
          </td>
        </tr>
        <tr class="JR_tr" height="30" align="center">
          <td class="JR_left" height="30" style="font-family: 'songti'">
            来队
          </td>
          <td class="JR_left" height="30" style="font-family: 'songti'">
            离队
          </td>
          <td
            style="font-family: 'fangsong'"
            class="JR_right"
            height="30"
            colspan="8"
            :rowspan="qinshu2"
            v-html="message.invalid"
          >
            <p align="left" style="font-family: 'fangsong'"></p>
          </td>
        </tr>
        <tr
          style="font-family: 'fangsong'"
          class="JR_tr"
          v-for="(item1, i) in message.kinsfolk"
          :key="i + ''"
          v-show="message.kinsfolk != null || message.kinsfolk.length > 0"
        >
          <td class="JR_right" height="30">
            <p align="center">{{ item1.peopleName }}</p>
          </td>
          <td class="JR_right" height="30">
            <p align="center">{{ item1.name }}</p>
          </td>

          <td class="JR_right" height="30">
            <p align="center">{{ item1.relation }}</p>
          </td>
          <td class="JR_right" height="30">
            <p align="center">{{ item1.comeDate }}</p>
          </td>
          <td class="JR_right" height="30">
            <p align="center">{{ item1.leaveDate }}</p>
          </td>
        </tr>

        <tr
          style="font-family: 'fangsong'"
          class="JR_tr"
          v-if="message.kinsfolk == null || message.kinsfolk.length == 0"
        >
          <td class="JR_right" height="30">
            <p align="center"></p>
          </td>
          <td class="JR_right" height="30">
            <p align="center"></p>
          </td>

          <td class="JR_right" height="30">
            <p align="center"></p>
          </td>
          <td class="JR_right" height="30">
            <p align="center"></p>
          </td>
          <td class="JR_right" height="30">
            <p align="center"></p>
          </td>
        </tr>
        <tr
          style="font-family: 'fangsong'"
          class="JR_tr"
          v-if="message.kinsfolk == null || message.kinsfolk.length == 0"
        >
          <td class="JR_right" height="30">
            <p align="center"></p>
          </td>
          <td class="JR_right" height="30">
            <p align="center"></p>
          </td>

          <td class="JR_right" height="30">
            <p align="center"></p>
          </td>
          <td class="JR_right" height="30">
            <p align="center"></p>
          </td>
          <td class="JR_right" height="30">
            <p align="center"></p>
          </td>
        </tr>
        <!-- <tr></tr> -->
        <!-- <tr class="JR_tr" height="30">
          <td class="JR_right" height="30"><p align="center"></p></td>
          <td class="JR_right" height="30"><p align="center"></p></td>
          <td class="JR_right" height="30"><p align="center"></p></td>
          <td class="JR_right" height="30"><p align="center"></p></td>
          <td class="JR_right" height="30"><p align="center"></p></td>
        </tr> -->
        <!-- 请假外出休假 -->
        <tr
          class="JR_tr"
          align="center"
          height="30"
          style="font-family: 'songti'"
        >
          <td :rowspan="qingjia" class="JR_left">
            <p align="center" style="margin: 0 15px; font-family: 'songti'">
              请 <br />
              假 <br />
              外 <br />
              出 <br />
              、 <br />
              探 <br />
              亲 <br />
              休 <br />
              假
            </p>
          </td>
          <td class="JR_left" style="font-family: 'songti'">姓&nbsp; 名</td>
          <td class="JR_left" style="font-family: 'songti'">职&nbsp; 务</td>
          <td class="JR_left" colspan="2" style="font-family: 'songti'">
            事&nbsp;&nbsp;&nbsp; 由
          </td>
          <td class="JR_left" colspan="2" style="font-family: 'songti'">
            时数或 <br />
            天&nbsp; 数
          </td>
          <td class="JR_left" colspan="2" style="font-family: 'songti'">
            准假部 <br />
            门(人)
          </td>
          <td class="JR_left" colspan="2" style="font-family: 'songti'">
            离 队 <br />
            时 间
          </td>
          <td class="JR_left" colspan="2" style="font-family: 'songti'">
            归 队 <br />
            时 间
          </td>
          <td class="JR_left" colspan="3" style="font-family: 'songti'">
            超(误)假 <br />
            时(天)数
          </td>
        </tr>
        <tr
          class="JR_tr"
          height="30"
          style="font-family: 'fangsong'"
          v-for="(item, i) in message.bdglLeaves"
          :key="i"
        >
          <td class="JR_right">
            <p align="center">{{ item.peopleName }}</p>
          </td>
          <td class="JR_right">
            <p align="center">
              <dict-tag :options="dict.type.post_Level" :value="item.postId" />
            </p>
          </td>
          <td class="JR_right" colspan="2">
            <p align="center">{{ item.leaveReason }}</p>
          </td>
          <td class="JR_right" colspan="2">
            <p align="center">{{ time[i] }}</p>
          </td>
          <td class="JR_right" colspan="2">
            <p align="center">{{ item.peopleName1 }}</p>
          </td>
          <td class="JR_right" colspan="2">
            <p align="center">{{ item.departureTime }}</p>
          </td>
          <td class="JR_right" colspan="2">
            <p align="center">{{ item.returnTime }}</p>
          </td>
          <td class="JR_right" colspan="3">
            <p align="center">{{ guiTime[i] }}</p>
          </td>
        </tr>
        <tr
          class="JR_tr"
          height="30"
          style="font-family: 'fangsong'"
          v-if="
            this.message.bdglLeaves == null ||
            this.message.bdglLeaves.length < 5
          "
        >
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="3"><p align="center"></p></td>
        </tr>
        <tr
          class="JR_tr"
          height="30"
          style="font-family: 'fangsong'"
          v-if="
            this.message.bdglLeaves == null ||
            this.message.bdglLeaves.length < 4
          "
        >
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="3"><p align="center"></p></td>
        </tr>
        <tr
          class="JR_tr"
          height="30"
          style="font-family: 'fangsong'"
          v-if="
            this.message.bdglLeaves == null ||
            this.message.bdglLeaves.length < 3
          "
        >
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="3"><p align="center"></p></td>
        </tr>
        <tr
          class="JR_tr"
          height="30"
          style="font-family: 'fangsong'"
          v-if="
            this.message.bdglLeaves == null ||
            this.message.bdglLeaves.length < 2
          "
        >
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="3"><p align="center"></p></td>
        </tr>
        <tr
          class="JR_tr"
          height="30"
          style="font-family: 'fangsong'"
          v-if="
            this.message.bdglLeaves == null ||
            this.message.bdglLeaves.length < 1
          "
        >
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="2"><p align="center"></p></td>
          <td class="JR_right" colspan="3"><p align="center"></p></td>
        </tr>
        <!-- 上级通知 -->
        <tr class="JR_tr">
          <td class="JR_left" width="101" style="font-family: 'songti'">
            <p align="center" style="margin: 0 15px; font-family: 'songti'">
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
            </p>
          </td>
          <td
            class="JR_right"
            colspan="15"
            style="white-space: normal; font-family: 'fangsong'"
            v-html="message.notice"
          ></td>
        </tr>
      </table>

      <table
        class="b1"
        border="none"
        style="
          border-collapse: collapse;
          mso-border-left-alt: 0.5pt solid windowtext;
          mso-border-top-alt: 0.5pt solid windowtext;
          mso-border-right-alt: 0.5pt solid windowtext;
          mso-border-bottom-alt: 0.5pt solid windowtext;
          mso-border-insideh: 0.5pt solid windowtext;
          mso-border-insidev: 0.5pt solid windowtext;
          mso-padding-alt: 0pt 0pt 0pt 0pt;
          width: 930px;
          color: black;
          font-size: 16px;
          font-weight: 500;
          border: none;
        "
      >
        <tr
          height="30"
          style="
            border: none;
            font-family: 'songti';
            display: flex;
            justify-content: space-between;
            height: 70px;
          "
        >
          <td colspan="2" style="border: none">
            <p style="border: none; font-family: 'songti'">
              连队首长签字: {{ message.sz }}
            </p>
          </td>
          <td colspan="10" style="border: none; width: 250px"></td>
          <td
            colspan="2"
            style="border: none; font-family: '宋体'; display: inline-block"
          >
            <p style="border: none; font-family: '宋体'">
              时&nbsp;&nbsp;间:
              {{ message.blogDate | dataFormaty }}
            </p>
          </td>
        </tr>
      </table>
      <!-- <span
        style="
          margin-left: 48px;
          color: black;
          font-family: 'fangsong';
          font-size: 16px;
          font-weight: 600;
        "
        >连队首长签字:</span
      >
      <span
        style="
          margin-left: 615px;
          color: black;
          letter-spacing: 15px;
          font-family: 'fangsong';
          font-size: 16px;
          font-weight: 600;
        "
        >&nbsp;&nbsp;时&nbsp;&nbsp;&nbsp;&nbsp;分</span
      > -->
    </div>
  </div>
</template>
<script>
import { lookWord } from "@/utils/look_word.js";
import "@/utils/lodJs.js";
export default {
  name: "bdgleven",
  props: ["message"],
  dicts: ["post_Level"],
  data() {
    return {
      title: "连队要事日记",
      style:
        ".b1{width:630px !important};.JR_tr {height: 40px;};.JR_left{text-align:center;font-size: 1em; font-family: '楷体_GB2312';};.JR_right{ font-size: 1em; font-family: 仿宋_GB2312;};.JR_bottom{border-left:0; font-size: 1em; font-family: 仿宋_GB2312; text-align: right}",
      chapu: 4,
      qinshu: 4,
      week: "",
      qinshu2: 3,
      number: 6,
      time: [],
      guiTime: [],
      qingjia: 1,
    };
  },
  created() {
    this.chapu = this.chapus();
    this.qinshu = this.qinshus();
    this.week = this.getWeek(this.message.blogDate);
    this.qinshu2 = this.qinshus2();
    this.xunlian();
    this.qingjias();
  },
  mounted() {},
  methods: {
    daochu() {
      lookWord(this.title, "#contents", this.style);
    },
    chapus() {
      if (this.message.inspect != null) return this.message.inspect.length + 1;
    },
    qinshus() {
      if (this.message.kinsfolk != null) {
        return this.message.kinsfolk.length + 2;
      }
      return 4;
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
        if (this.message.bdglLeaves.length > 5) {
          this.qingjia += this.message.bdglLeaves.length;
        } else {
          this.qingjia +=
            5 - this.message.bdglLeaves.length + this.message.bdglLeaves.length;
        }
      } else {
        this.qingjia += 5;
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
        dateArray[2]
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
tbody {
  color: #fff;
  text-align: center;
  margin: auto;
}
tr:last-child {
  border-bottom: 1px solid;
}
::v-deep .el-dialog {
  margin-right: 100px !important;
}
.tTime {
  margin-left: 100px;
}
.box1 {
  position: absolute;
  right: 30px;
  top: 80px;
  border: 1px solid rgb(129, 130, 131);
  border-radius: 5px;
}
</style>