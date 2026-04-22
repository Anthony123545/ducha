<template>
  <div>
    <el-button class="box1" @click="daochu">导出</el-button>

    <div class="word" id="contents" style="font-family: 'fangsong'">
      <p
        style="
          font-size: 16pt;
          font-weight: 600;
          margin-botttom: 30px;
          color: black;
          text-align: center;
          font-family: 'geiti';
        "
      >
        《人员名册》
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
        align="center"
        bordercolor="black"
      >
        <tbody style="font-size: 16px; font-weight: 500">
          <tr
            class="JR_tr"
            height="40"
            align="center"
            style="font-family: 'heiti'"
          >
            <td class="JR_left" rowspan="2" style="font-family: 'heiti'">
              日期
            </td>
            <td class="JR_left" colspan="3" style="font-family: 'heiti'">
              编制人数
            </td>
            <td class="JR_left" colspan="3" style="font-family: 'heiti'">
              在位人数
            </td>
            <td class="JR_left" colspan="3" style="font-family: 'heiti'">
              超（缺）编人员情况
            </td>
          </tr>
          <tr
            class="JR_tr"
            height="40"
            align="center"
            style="font-family: 'heiti'"
          >
            <td class="JR_left" style="font-family: 'heiti'">干部</td>
            <td class="JR_left" style="font-family: 'heiti'">士官</td>
            <td class="JR_left" style="font-family: 'heiti'">义务兵</td>
            <td class="JR_left" style="font-family: 'heiti'">干部</td>
            <td class="JR_left" style="font-family: 'heiti'">士官</td>
            <td class="JR_left" style="font-family: 'heiti'">义务兵</td>
            <td class="JR_left" style="font-family: 'heiti'">干部</td>
            <td class="JR_left" style="font-family: 'heiti'">士官</td>
            <td class="JR_left" style="font-family: 'heiti'">义务兵</td>
          </tr>
          <tr
            class="JR_tr"
            height="40"
            align="center"
            style="font-family: 'fangsong'"
          >
            <td class="JR_right">{{nowDate}}</td>
            <td class="JR_right">{{ renshu.inpreparationGb }}</td>
            <td class="JR_right">{{ renshu.inpreparationSg }}</td>
            <td class="JR_right">{{ renshu.inpreparationYwb }}</td>
            <td class="JR_right">{{ renshu.incumbentsGb }}</td>
            <td class="JR_right">{{ renshu.incumbentsSg }}</td>
            <td class="JR_right">{{ renshu.incumbentsYwb }}</td>
            <td class="JR_right">{{ renshu.outpreparationGb }}</td>
            <td class="JR_right">{{ renshu.outpreparationSg }}</td>
            <td class="JR_right">{{ renshu.outpreparationYwb }}</td>
          </tr>

          <tr class="JR_tr" height="40" align="center">
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
        </tbody>
      </table>

      <p
        align="right"
        style="
          margin: 20px 200px 5px 0px;
          font-size: 16px;
          color: black;
          font-family: 'fangsong';
          font-size: 18px;
          font-weight: 500;
        "
      >
        日期
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
        align="center"
        bordercolor="black"
      >
        <tbody style="font-size: 16px; font-weight: 500">
          <tr
            class="JR_tr"
            height="40"
            align="center"
            style="font-family: 'heiti'"
          >
            <td class="JR_left" style="font-family: 'heiti'">姓名</td>
            <td class="JR_left" style="font-family: 'heiti'">职务</td>
            <td class="JR_left" style="font-family: 'heiti'">军衔</td>
            <td class="JR_left" style="font-family: 'heiti'">名族</td>
            <td class="JR_left" style="font-family: 'heiti'">籍贯</td>
            <td class="JR_left" style="font-family: 'heiti'">出生<br />年月</td>
            <td class="JR_left" style="font-family: 'heiti'">入伍<br />年月</td>
            <td class="JR_left" style="font-family: 'heiti'">
              入党（团）<br />
              时间
            </td>
            <td class="JR_left" style="font-family: 'heiti'">文化<br />程度</td>
            <td class="JR_left" style="font-family: 'heiti'">婚姻<br />状况</td>
            <td class="JR_left" style="font-family: 'heiti'">
              家庭住址及联系方式
            </td>
            <td class="JR_left" style="font-family: 'heiti'">备注</td>
          </tr>
          <tr
            class="JR_tr"
            height="40"
            align="center"
            style="font-family: 'fangsong'"
            v-for="(item, i) in message"
            :key="i"
          >
            <td class="JR_right">{{ item.name }}</td>
            <td class="JR_right">
              <dict-tag :options="dict.type.post_Level" :value="item.postId" />
            </td>
            <td class="JR_right">
              <dict-tag :options="dict.type.sys_user_rank" :value="item.rankId" />
            </td>
            <td class="JR_right">
              <dict-tag
                :options="dict.type.sys_familyName"
                :value="item.nationId"
              />
            </td>
            <td class="JR_right">{{ item.origin }}</td>
            <td class="JR_right">{{ item.birthDate }}</td>
            <td class="JR_right">{{ item.enlistDate }}</td>
            <td class="JR_right">{{ item.partyDate }}</td>
            <td class="JR_right">
              <dict-tag
                :options="dict.type.Degree_level"
                :value="item.educationId"
              />
            </td>
            <td class="JR_right">
              <dict-tag
                :options="dict.type.marryList"
                :value="item.marryList"
              />
            </td>
            <td class="JR_right">{{ item.live }}{{ item.phone }}</td>
            <td class="JR_right"></td>
          </tr>
          <tr class="JR_tr" height="40" align="center">
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
import { lookWord } from "@/utils/look_word.js";

import "@/utils/lodJs.js";
export default {
  name: "bdgleven",
  props: ["message", "renshu"],
  dicts: [
    "post_Level",
    "sys_user_rank",
    "sys_familyName",
    "Degree_level",
    "marryList",
  ],
  data() {
    return {
      title: "人员名册",
      style:
        ".b1{width:860px !important};.JR_tr {height: 40px;};.JR_left{font-size: 1.331em; font-family: '楷体_GB2312';};.JR_right{ font-size: 1.331em; font-family: 仿宋_GB2312;};.JR_bottom{border-left:0; font-size: 1.331em; font-family: 仿宋_GB2312; text-align: right}",
      //学历
      educationId: [],
      //军衔
      rankIds: [],
      //民族
      nationIds: [],
      //职务层级
      postIds: [],
      //婚姻情况
      marryList: [],
      xueLi: "",
      nowDate:null
    };
  },
  created() {
    this.getTiems()
    //学历
    this.getDicts("Degree_level").then((response) => {
      this.educationId = response.data;
    });
    //婚姻情况
    this.getDicts("marryList").then((response) => {
      this.marryList = response.data;
    });
    //职务层级
    this.getDicts("post_Level").then((response) => {
      this.postIds = response.data;
    });
    //军衔
    this.getDicts("sys_user_rank").then((response) => {
      this.rankIds = response.data;
    });
    //民族
    this.getDicts("sys_familyName").then((response) => {
      this.nationIds = response.data;
    });
    // 获取当前时间
  },
  methods: {
    // 获取日期
    getTiems() {
      //获取当前日期
      var date = new Date();

      // 获取当前月份
      var nowMonth = date.getMonth() + 1;

      // 获取当前是几号
      var strDate = date.getDate();

      // 添加分隔符“-”
      var seperator = "-";

      // 对月份进行处理，1-9月在前面添加一个“0”
      if (nowMonth >= 1 && nowMonth <= 9) {
        nowMonth = "0" + nowMonth;
      }

      // 对月份进行处理，1-9号在前面添加一个“0”
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      this.nowDate =
        date.getFullYear() + seperator + nowMonth + seperator + strDate;
    },
    daochu() {
      lookWord(this.title, "#contents", this.style, true);
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