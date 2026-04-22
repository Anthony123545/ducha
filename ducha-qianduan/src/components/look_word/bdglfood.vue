<template>
  <div>
    <el-button class="box1" @click="daochu">导出</el-button>
    <div class="word" id="contents" style="margin-top: 10px">
      <p
        style="
          font-size: 16pt;
          font-weight: 600;
          margin-botttom: 30px;
          color: black;
          font-family: &quot;heiti&quot;;
          text-align: center;
        "
      >
        《伙食管理登记簿》
      </p>

      <table
        border="1"
        class="b1"
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
          font-size: 16px;
          font-weight: 500;
        "
        width="561"
        align="center"
        bordercolor="black"
      >
        <!-- 930px -->
        <tr
          class="aaa"
          style="
            border: none;
            color: black;
            font-size: 12pt;
            line-height: 30px;
            font-family: &quot;songti&quot;;
          "
        >
          <td
            class="JR_right"
            colspan="5"
            align="left"
            style="
              border: none;
              line-height: 45px;
              font-family: &quot;宋体&quot;;
            "
          >
            日期：<span style="font-family: &quot;宋体&quot;">
              {{ message.recordDate }}</span
            >
          </td>
          <td
            class="JR_right"
            colspan="6"
            align="left"
            style="
              border: none;
              line-height: 45px;
              font-family: &quot;宋体&quot;;
            "
          >
            {{ week }}
          </td>
          <td
            class="JR_right"
            colspan="4"
            align="left"
            style="
              border: none;
              line-height: 45px;
              font-family: &quot;宋体&quot;;
            "
          >
            单位：千克/元
          </td>
        </tr>
        <tbody style="font-size: 16px; font-weight: 500">
          <!-- 区分早中晚一日三餐 -->
          <tr class="JR_tr">
            <td
              class="JR_left"
              width="31"
              height="62"
              colspan="1"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
            >
              <span style="display: block; text-align: center"
                >区
                <div style="margin-top: 3px">分</div>
              </span>
            </td>
            <td
              colspan="3"
              width="118"
              class="JR_left"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center" style="display: block; text-align: center"
                >主食</span
              >
            </td>
            <td
              colspan="11"
              class="JR_left"
              width="408"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
            >
              <span align="center" style="display: block; text-align: center">
                菜谱
              </span>
            </td>
          </tr>
          <!-- 早 -->
          <tr class="JR_tr">
            <td
              colspan="1"
              class="JR_left"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              height="30"
              align="center"
              width="31"
            >
              <span align="center" style="display: block; text-align: center"
                >早</span
              >
            </td>
            <td
              colspan="3"
              style="
                color: black;
                font-family: &quot;仿宋_GB2312&quot;;
                font-size: 1em;
              "
              class="JR_right"
              width="118"
              align="center"
            >
              <span style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].stapleFood
              }}</span>
            </td>
            <td
              colspan="11"
              style="
                color: black;
                font-family: &quot;仿宋_GB2312&quot;;
                font-size: 1em;
              "
              class="JR_right"
              width="408"
              align="center"
            >
              <span style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].menu
              }}</span>
            </td>
          </tr>
          <!-- 中 -->
          <tr class="JR_tr">
            <td
              colspan="1"
              class="JR_left"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              height="30"
              align="center"
              width="31"
            >
              <span style="display: block; text-align: center">中</span>
            </td>
            <td
              colspan="3"
              style="
                color: black;
                font-family: &quot;仿宋_GB2312&quot;;
                font-size: 1em;
              "
              class="JR_right"
              width="118"
              align="center"
            >
              <span style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].stapleFood
              }}</span>
            </td>
            <td
              colspan="11"
              style="
                color: black;
                font-family: &quot;仿宋_GB2312&quot;;
                font-size: 1em;
              "
              width="408"
              align="center"
            >
              <span style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].menu
              }}</span>
            </td>
          </tr>
          <!-- 晚 -->
          <tr class="JR_tr">
            <td
              colspan="1"
              class="JR_left"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              height="30"
              align="center"
              width="31"
            >
              <span style="display: block; text-align: center">晚</span>
            </td>
            <td
              colspan="3"
              style="
                color: black;
                font-family: &quot;仿宋_GB2312&quot;;
                font-size: 1em;
              "
              class="JR_right"
              width="118"
              align="center"
            >
              <span style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].stapleFood
              }}</span>
            </td>
            <td
              colspan="11"
              style="
                color: black;
                font-family: &quot;仿宋_GB2312&quot;;
                font-size: 1em;
              "
              class="JR_right"
              width="408"
              align="center"
            >
              <span style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].menu
              }}</span>
            </td>
          </tr>
          <!-- 第二个区分 -->
          <tr class="JR_tr">
            <td
              rowspan="2"
              colspan="1"
              class="JR_left"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              height="72"
              align="center"
              width="31"
            >
              <span style="display: block; text-align: center"
                >区
                <div style="margin-top: 3px">分</div>
              </span>
            </td>
            <td
              colspan="3"
              class="JR_left"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="118"
              align="center"
              height="32"
            >
              <span align="center" style="display: block; text-align: center"
                >就餐人数</span
              >
            </td>
            <td
              colspan="11"
              class="JR_left"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="406"
              align="center"
            >
              <span align="center" style="display: block; text-align: center"
                >当日消耗</span
              >
            </td>
          </tr>
          <!-- 总数 -->
          <tr class="JR_tr">
            <td
              class="JR_left"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="40"
              height="39"
              align="center"
            >
              <span align="center" style="display: block; text-align: center"
                >总数</span
              >
            </td>
            <td
              class="JR_left"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="40"
              align="center"
            >
              <span align="center" style="display: block; text-align: center"
                >连队</span
              >
            </td>
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="32"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center"
                >外
                <div>来</div>
              </span>
            </td>
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="35"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center"
                >面
                <div>粉</div>
              </span>
            </td>
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="35"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center"
                >大
                <div>米</div>
              </span>
            </td>
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="35"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center"
                >肉
                <div>类</div>
              </span>
            </td>
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="50"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center"
                >鱼禽
                <div>类</div>
              </span>
            </td>
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="35"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center"
                >食
                <div>油</div>
              </span>
            </td>
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="35"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center"
                >黄
                <div>豆</div>
              </span>
            </td>
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="35"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center"
                >蔬
                <div>菜</div>
              </span>
            </td>
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="50"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">
                水果
                <div>（奶）</div>
              </span>
            </td>
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="35"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center"
                >糖</span
              >
            </td>
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="35"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">
                调
                <div>料</div>
              </span>
            </td>
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="35"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">
                燃
                <div>料</div>
              </span>
            </td>
          </tr>
          <!-- 早第二格子 -->
          <tr class="JR_tr">
            <td
              colspan="1"
              class="JR_left"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              height="30"
              align="center"
              width="31"
            >
              <span align="center" style="display: block; text-align: center"
                >早</span
              >
            </td>
            <td
              class="JR_left"
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="40"
              align="center"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].jiucanCount
              }}</span>
            </td>
            <td
              class="JR_left"
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="40"
              align="center"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].lianduiCount
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="32"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].wailaiCount
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="31"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].mianfen
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="31"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].dami
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="31"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].rolei
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="44"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].yulei
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="25"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].youlei
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="25"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].huangdo
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="38"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].suchai
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="58"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">
                {{ message.bdglFoodMeal[0].shuiguo }}
              </span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="32"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[0].tang
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="30"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">
                {{ message.bdglFoodMeal[0].tiaoliao }}
              </span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="31"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">
                {{ message.bdglFoodMeal[0].ranliao }}
              </span>
            </td>
          </tr>
          <!-- 中第二格子 -->
          <tr class="JR_tr">
            <td
              colspan="1"
              class="JR_left"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              height="30"
              align="center"
              width="31"
            >
              <span style="display: block; text-align: center">中</span>
            </td>
            <td
              class="JR_left"
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="40"
              align="center"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].jiucanCount
              }}</span>
            </td>
            <td
              class="JR_left"
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="40"
              align="center"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].lianduiCount
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="32"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].wailaiCount
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="31"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].mianfen
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="31"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].dami
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="31"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].rolei
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="44"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].yulei
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="25"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].youlei
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="25"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].huangdo
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="38"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].suchai
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="58"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">
                {{ message.bdglFoodMeal[1].shuiguo }}
              </span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="32"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[1].tang
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="30"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">
                {{ message.bdglFoodMeal[1].tiaoliao }}
              </span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="31"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">
                {{ message.bdglFoodMeal[1].ranliao }}
              </span>
            </td>
          </tr>
          <!-- 晚第二 -->
          <tr class="JR_tr">
            <td
              colspan="1"
              class="JR_left"
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
              "
              height="30"
              align="center"
              width="31"
            >
              <span style="display: block; text-align: center">晚</span>
            </td>
            <td
              class="JR_left"
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="40"
              align="center"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].jiucanCount
              }}</span>
            </td>
            <td
              class="JR_left"
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="40"
              align="center"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].lianduiCount
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="32"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].wailaiCount
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="31"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].mianfen
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="31"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].dami
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="31"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].rolei
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="44"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].yulei
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="25"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].youlei
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="25"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].huangdo
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="38"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].suchai
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="58"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">
                {{ message.bdglFoodMeal[2].shuiguo }}
              </span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="32"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">{{
                message.bdglFoodMeal[2].tang
              }}</span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="30"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">
                {{ message.bdglFoodMeal[2].tiaoliao }}
              </span>
            </td>
            <td
              style="
                font-family: &quot;仿宋_GB2312&quot;;
                text-align: center;
                font-size: 1em;
              "
              width="31"
              align="center"
              class="JR_left"
            >
              <span align="center" style="display: block; text-align: center">
                {{ message.bdglFoodMeal[2].ranliao }}
              </span>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- 实物检查验收 -->
      <table
        border="1"
        cellspacing="0"
        cellpadding="0"
        class="b1"
        style="border-collapse: collapse; border-top: 0px"
        width="561"
        align="center"
      >
        <tbody style="font-size: 16px; font-weight: 500" ref="shiwu">
          <tr height="29" align="center">
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
                border-top: 0;
              "
              colspan="7"
            >
              <span align="center" style="display: block; text-align: center"
                >实物检查验收</span
              >
            </td>
          </tr>
          <!-- 区分 -->
          <tr
            class="JR_tr"
            style="
              font-family: &quot;宋体&quot;;
              text-align: center;
              font-size: 1em;
            "
            height="62"
          >
            <td class="JR_left" align="center" width="30">
              <span align="center" style="display: block; text-align: center"
                >区
                <div style="margin-top: 3px">分</div>
              </span>
            </td>
            <td width="100" class="JR_left" align="center">
              <span align="center" style="display: block; text-align: center"
                >品名</span
              >
            </td>
            <td width="48" class="JR_left" align="center">
              <span align="center" style="display: block; text-align: center"
                >重量</span
              >
            </td>
            <td width="94" class="JR_left" align="center">
              <span align="center" style="display: block; text-align: center"
                >单价</span
              >
            </td>
            <td width="94" class="JR_left" align="center">
              <span align="center" style="display: block; text-align: center"
                >金额</span
              >
            </td>
            <td width="93" class="JR_left" align="center">
              <span align="center" style="display: block; text-align: center"
                >购买或自产</span
              >
            </td>
            <td width="83" class="JR_left" align="center">
              <span align="center" style="display: block; text-align: center"
                >备注</span
              >
            </td>
          </tr>
        </tbody>
      </table>
      <!-- 交退伙食费 -->
      <table
        border="1"
        cellspacing="0"
        cellpadding="0"
        class="b1"
        style="border-collapse: collapse; border-top: 0px"
        width="561"
        align="center"
      >
        <tbody>
          <tr height="29" align="center">
            <td
              style="
                font-family: &quot;宋体&quot;;
                text-align: center;
                font-size: 1em;
                border-top: 0;
              "
              colspan="8"
              align="center"
            >
              <span align="center" style="display: block; text-align: center"
                >交退伙食费</span
              >
            </td>
          </tr>
          <tr
            class="JR_tr"
            style="text-align: center; font-family: &quot;宋体&quot;"
            align="center"
          >
            <td width="99" colspan="1" rowspan="2" class="JR_left">
              <p align="center">姓名</p>
            </td>
            <td width="186" colspan="3" class="JR_left" align="center">
              <p align="center">交伙食费</p>
            </td>
            <td
              width="99"
              colspan="1"
              rowspan="2"
              class="JR_left"
              align="center"
            >
              <p align="center">姓名</p>
            </td>
            <td width="171" colspan="3" class="JR_left" align="center">
              <p align="center">退伙食费</p>
            </td>
          </tr>

          <tr
            class="JR_tr"
            style="font-family: &quot;宋体&quot;"
            align="center"
          >
            <td width="35" colspan="1" class="JR_left">
              <span align="center"
                >天
                <div>数</div>
              </span>
            </td>
            <td width="72" colspan="1" class="JR_left">
              <p align="center">标准</p>
            </td>
            <td width="72" colspan="1" class="JR_left">
              <p align="center">金额</p>
            </td>
            <td width="33" colspan="1" class="JR_left">
              <span align="center"
                >天
                <div>数</div>
              </span>
            </td>
            <td width="74" colspan="1" class="JR_left">
              <p align="center">标准</p>
            </td>
            <td width="57" colspan="1" class="JR_left">
              <p align="center">金额</p>
            </td>
          </tr>

          <tr
            align="center"
            v-for="(item, i) in by_reset"
            :key="i + ' '"
            class="JR_tr"
            style="text-align: center; font-family: &quot;仿宋&quot;"
          >
            <td colspan="1" class="JR_right" align="center" height="33">
              <p align="center">{{ item.peopleName }}</p>
            </td>
            <td colspan="1" class="JR_right" align="center">{{ item.sky }}</td>
            <td colspan="1" class="JR_right" align="center">
              <p align="center">{{ item.standard }}</p>
            </td>
            <td colspan="1" class="JR_right" align="center">
              <p align="center">{{ item.money }}</p>
            </td>

            <td colspan="1" v-if="isWho" class="JR_right" align="center">
              <p align="center">{{ reset[i].peopleName }}</p>
            </td>
            <td colspan="1" v-if="isWho" class="JR_right" align="center">
              <p align="center">{{ reset[i].sky }}</p>
            </td>
            <td colspan="1" v-if="isWho" class="JR_right" align="center">
              <p align="center">{{ reset[i].standard }}</p>
            </td>
            <td colspan="1" v-if="isWho" class="JR_right" align="center">
              <p align="center">{{ reset[i].money }}</p>
            </td>
            <td colspan="1" v-if="!isWho" class="JR_right" align="center">
              <p align="center">{{ by[i].peopleName }}</p>
            </td>
            <td colspan="1" v-if="!isWho" class="JR_right" align="center">
              <p align="center">{{ by[i].sky }}</p>
            </td>
            <td colspan="1" v-if="!isWho" class="JR_right" align="center">
              <p align="center">{{ by[i].standard }}</p>
            </td>
            <td colspan="1" v-if="!isWho" class="JR_right" align="center">
              <p align="center">{{ by[i].money }}</p>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- 在食堂购物 -->
      <table
        border="1"
        cellspacing="0"
        cellpadding="0"
        class="b1"
        style="border-collapse: collapse; border-top: 0px"
        width="561"
        align="center"
      >
        <tbody>
          <tr
            class="JR_tr"
            align="center"
            height="29"
            style="font-family: &quot;宋体&quot;"
          >
            <td width="" colspan="11" class="JR_left" style="border-top: 0">
              <span>在食堂购物</span>
            </td>
          </tr>
          <tr
            class="JR_tr"
            style="text-align: center; font-family: &quot;宋体&quot;"
          >
            <td width="61" rowspan="2" colspan="1" class="JR_left" height="57">
              <span align="center">姓名</span>
            </td>
            <td width="140" colspan="3" class="JR_left" height="28">
              <span align="center">主食</span>
            </td>
            <td width="152" colspan="3" class="JR_left">
              <span align="center">副食</span>
            </td>
            <td width="152" colspan="3" class="JR_left">
              <span align="center">其他</span>
            </td>
            <td width="48" rowspan="2" colspan="1" class="JR_left">
              <span align="center">合计</span>
            </td>
          </tr>
          <!-- 品名 -->
          <tr
            class="JR_tr"
            style="font-family: &quot;宋体&quot;"
            align="center"
          >
            <td width="44" colspan="1" class="JR_left" height="28">
              <span align="center">品名</span>
            </td>
            <td width="41" colspan="1" class="JR_left">
              <span align="center">重量</span>
            </td>
            <td width="49" colspan="1" class="JR_left">
              <span align="center">金额</span>
            </td>
            <td width="48" colspan="1" class="JR_left">
              <span align="center">品名</span>
            </td>
            <td width="48" colspan="1" class="JR_left">
              <span align="center">重量</span>
            </td>
            <td width="48" colspan="1" class="JR_left">
              <span align="center">金额</span>
            </td>
            <td width="48" colspan="1" class="JR_left">
              <span align="center">品名</span>
            </td>
            <td width="48" colspan="1" class="JR_left">
              <span align="center">重量</span>
            </td>
            <td width="48" colspan="1" class="JR_left">
              <span align="center">金额</span>
            </td>
          </tr>
          <tr
            class="JR_tr"
            v-for="item in message.bdglFoodShoppings"
            :key="item.id"
            height="27"
            style="font-family: &quot;仿宋_GB2312&quot;"
            align="center"
          >
            <td width="" class="JR_right">
              <span align="center">
                {{ item.peoplename }}
              </span>
            </td>
            <td width="" class="JR_right">
              <span align="center">{{ item.zName }}</span>
            </td>
            <td width="" class="JR_right">
              <span align="center">
                {{ item.zWeight }}
              </span>
            </td>
            <td width="" class="JR_right">
              <span align="center">{{ item.zMoney }}</span>
            </td>
            <td width="" class="JR_right">
              <span align="center">{{ item.fName }}</span>
            </td>
            <td width="" class="JR_right">
              <span align="center">{{ item.fWeight }}</span>
            </td>
            <td width="" class="JR_right">
              <span align="center">{{ item.fMoney }}</span>
            </td>
            <td width="" class="JR_right">
              <span align="center">{{ item.qName }}</span>
            </td>
            <td width="" class="JR_right">
              <span align="center">{{ item.qWeight }}</span>
            </td>
            <td width="" class="JR_right">
              <span align="center">{{ item.qMoney }}</span>
            </td>
            <td width="" class="JR_right">
              <span align="center">{{ item.total }}</span>
            </td>
          </tr>
          <!-- 炊 事 人 员 及 食 堂 卫 生 -->

          <tr class="JR_tr" align="center" height="217">
            <td
              width=""
              colspan="1"
              class="JR_left"
              style="font-family: &quot;宋体&quot;"
            >
              <span align="center">
                炊<br />
                事<br />
                人<br />
                员<br />
                及<br />
                食<br />
                堂<br />
                卫<br />
                生
              </span>
            </td>
            <td
              style="font-family: &quot;仿宋_GB2312&quot;"
              colspan="5"
              v-html="message.contents"
              class="JR_right"
            >
              <p></p>
            </td>
            <td width="" class="JR_left" style="font-family: &quot;宋体&quot;">
              <span align="center">
                帮<br />
                厨<br />
                人<br />
                员
              </span>
            </td>
            <td
              width=""
              colspan="4"
              style="font-family: &quot;仿宋_GB2312&quot;"
              class="JR_right"
            >
              <span align="center">{{ message.peopleNames }}</span>
            </td>
          </tr>
          <!-- 就 餐 人 员 意 见 -->
          <tr class="JR_tr" height="123" align="center">
            <td
              width=""
              colspan="1"
              class="JR_left"
              style="font-family: &quot;宋体&quot;"
            >
              <span align="center">
                就<br />
                餐<br />
                人<br />
                员<br />
                意<br />
                见
              </span>
            </td>
            <td
              class="JR_right"
              width=""
              colspan="10"
              style="font-family: &quot;仿宋_GB2312&quot;"
              v-html="message.repastContent"
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
import "@/utils/lodJs.js";
import exportWord from "@/utils/exportWord.js";
export default {
  name: "bdgleven",
  props: ["message"],
  dicts: ["purchaseFood"],
  data() {
    return {
      title: "伙食管理登记簿",
      isWho: null,
      result: {
        money: "",
        peopleId: "",
        sky: "",
        standard: "",
        weel: "",
      },
      by: [],
      reset: [],
      by_reset: null,
      faith: {
        fileName: "军事训练登记簿", //文档名
        fileType: ".doc", //文档类型  经测试  可以doc xls html 其他的自己去试
        // header: "测试页眉", //页眉标题
        // footer: "测试页脚", //页脚标题
        direction: "", //默认或不填写为纵向，Horizontal 为横向
        dom: "#contents", // dom节点
      },
    };
  },
  created() {
    this.isWhy();
    this.week = this.getWeek(this.message.recordDate);
    // 品名
    this.getDicts("Degree_level").then((response) => {
      this.educationId = response.data;
    });
  },
  mounted() {
    this.aaa();
  },
  methods: {
    daochu() {
      exportWord(this.faith);
    },
    aaa() {
      let name = [];
      let name1 = [];
      for (var i in this.message.bdglFoodKindData) {
        name.push(i);
        name1.push(i.split("").join("<br/>"));
      }
      let dom = "";
      for (let index = 0; index < name.length; index++) {
        dom += "<tr>";
        let rowspan = null;
        if (this.message.bdglFoodKindData[name[index]].length == 0) {
          rowspan = 1;
        } else {
          rowspan = this.message.bdglFoodKindData[name[index]].length;
        }
        dom += `<td " rowspan="${rowspan}">
        <p align="center">${name1[index]}</p>
        </td>`;
        if (this.message.bdglFoodKindData[name[index]].length == 0) {
          dom += `
            <td ></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          `;
        } else {
          for (
            let j = 0;
            j < this.message.bdglFoodKindData[name[index]].length;
            j++
          ) {
            let result = this.message.bdglFoodKindData[name[index]][j];

            if (j == 0) {
              dom += `
              <td height='31' align='center'>${result.foodId}</td>
              <td align='center'>${result.weight}</td>
              <td align='center'>${result.unitPrice}</td>
              <td align='center'>${result.money}</td>
              <td align='center'>${result.types}</td>
              <td align='center'>${result.remark}</td>
            `;
            } else {
              dom += `
                <tr align='center'>
                  <td height='31' align='center'>${result.foodId}</td>
                  <td align='center'>${result.weight}</td>
                  <td align='center'>${result.unitPrice}</td>
                  <td align='center'>${result.money}</td>
                  <td align='center'>${result.types}</td>
                  <td align='center'>${result.remark}</td>
                </tr>
              `;
            }
          }
        }
        dom += "</tr>";
      }

      this.$refs.shiwu.innerHTML += dom;
    },
    isWhy() {
      let item;
      let i;
      for (item in this.message.bdglFoodBoardWagess) {
        if (this.message.bdglFoodBoardWagess[item].type === "交") {
          this.by.push(this.message.bdglFoodBoardWagess[item]);
        } else {
          this.reset.push(this.message.bdglFoodBoardWagess[item]);
        }
      }
      // console.log(this.by, 111);
      // console.log(this.reset, 222);

      if (this.by.length > this.by_reset || this.reset.length > this.by_reset) {
        // console.log(this.by.length);
        if (this.by.length >= this.reset.length) {
          this.by_reset = this.by;
          this.isWho = "by";
        } else {
          this.by_reset = this.reset;
        }
      }
      if (this.isWho === "by") {
        let item = this.by.length - this.reset.length;
        for (i = 0; i < item; i++) {
          this.reset.push(this.result);
        }
      } else {
        let item = this.reset.length - this.by.length;
        for (i = 0; i < item; i++) {
          this.by.push(this.result);
        }
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
  },
};
</script>
<style scoped media="print">
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

@page {
  size: auto;
  margin: 0mm;
}
</style>
