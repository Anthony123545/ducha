<template>
  <div>
    <el-button class="box1" @click="daochu">导出</el-button>

    <div class="word" id="contents">
      <p
        style="
          font-size: 16pt;
          font-weight: 600;

          color: black;
          font-family: 'heiti';
          text-align: center;
        "
      >
        《训练器材、教材登记簿》
      </p>
      <!-- <div class="fTitle">顶级分类训练器材、教材登记簿</div> -->
      <p
        align="left"
        style="
          font-size: 19px;
          font-weight: 500;
          color: black;
          margin-left: 7px;
          margin-bottom: 4px;
          font-family: '黑体';
        "
      >
        审核：
      </p>

      <!--<p align="center" style="font-size: 14pt;font-weight: bold;">顶级分类-训练器材、教材登记簿</p>-->
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
        width="933"
        bordercolor="black"
      >
        <tbody style="font-size: 16px; font-weight: 500">
          <tr class="JR_tr">
            <td
              width="35"
              valign="center"
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center"
                >序号</span
              >
            </td>
            <td
              width="63"
              valign="center"
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center"
                >类别</span
              >
            </td>
            <td
              width="82"
              valign="center"
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center"
                >名称</span
              >
            </td>
            <td
              width="268"
              height="45"
              valign="center"
              colspan="5"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center"
                >库存情况</span
              >
            </td>
            <td
              width="173"
              valign="center"
              colspan="3"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center"
                >入库情况</span
              >
            </td>
            <td
              width="177"
              valign="center"
              colspan="3"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center"
                >出库情况</span
              >
            </td>
            <td
              width="53"
              valign="center"
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center"
                >质量<br />状况</span
              >
            </td>
            <td
              width="54"
              valign="center"
              rowspan="2"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center"
                >备注</span
              >
            </td>
          </tr>
          <tr class="JR_tr" style="border-bottom: none">
            <td
              width="64"
              valign="center"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center">
                配发 <br />
                单位
              </span>
            </td>
            <td
              width="54"
              valign="center"
              class="JR_left"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center">
                配发 <br />
                时间
              </span>
            </td>
            <td
              width="46"
              valign="center"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center">
                配发<br />
                数量
              </span>
            </td>
            <td
              width="47"
              valign="center"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center">
                自筹<br />
                数量
              </span>
            </td>
            <td
              width="55"
              valign="center"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center">
                库存<br />
                数量
              </span>
            </td>
            <td
              width="53"
              valign="center"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center">
                时间
              </span>
            </td>
            <td
              width="46"
              valign="center"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center">
                数量
              </span>
            </td>
            <td
              width="72"
              valign="center"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center">
                经手人
              </span>
            </td>
            <td
              width="55"
              valign="center"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center">
                时间
              </span>
            </td>
            <td
              width="46"
              valign="center"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center">
                数量
              </span>
            </td>
            <td
              width="75"
              valign="center"
              style="font-family: '黑体'; text-align: center; font-size: 16px"
            >
              <span align="center" style="display: block; text-align: center">
                经手人
              </span>
            </td>
          </tr>
        </tbody>

        <tbody id="tbody" style="font-size: 16px; font-weight: 500">
          <tr
            class="JR_tr"
            style="font-family: 'fangsong'"
            v-for="(item, i) in message"
            :key="i"
          >
            <td
              width="35"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" v-if="message">{{ item.id }}</span>
            </td>
            <td
              width="63"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" v-if="message">{{ item.type }}</span>
            </td>
            <td
              width="82"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.title
              }}</span>
            </td>
            <td
              width="64"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.unitName
              }}</span>
            </td>
            <td
              width="54"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.granttime | dataFormatyss
              }}</span>
            </td>
            <td
              width="46"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.grantnum
              }}</span>
            </td>
            <td
              width="47"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.ownnum
              }}</span>
            </td>
            <td
              width="55"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.stocknum
              }}</span>
            </td>
            <td
              width="53"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.intime
              }}</span>
            </td>
            <td
              width="46"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.innum
              }}</span>
            </td>
            <td
              width="72"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.peopleName
              }}</span>
            </td>
            <td
              width="55"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span
                align="center"
                class="JR_right"
                v-if="message && item.outtime"
                >{{ item.outtime }}</span
              >
            </td>
            <td
              width="46"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.outnum
              }}</span>
            </td>
            <td
              width="75"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.people1Name
              }}</span>
            </td>
            <td
              width="54"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.quality
              }}</span>
            </td>
            <td
              width="55"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" v-if="message">{{
                item.info
              }}</span>
            </td>
          </tr>
        </tbody>
        <tr style="border-bottom: 1px solid #333">
          <td
            width="37"
            height="89px"
            valign="center"
            style="font-family: '黑体'; text-align: center; font-size: 16px"
          >
            <span align="center">
              填<br />
              写<br />
              说<br />
              明 <br />
            </span>
          </td>
          <td
            width="897"
            valign="center"
            colspan="15"
            v-html="message.info"
            style="
              font-family: '仿宋_GB2312';
              text-align: center;
              font-size: 16px;
            "
          >
            <span></span>
          </td>
        </tr>
        <!-- </tbody> -->
        <tr
          class="JR_tr"
          style="border: none; color: black; font-size: 12pt; line-height: 30px"
        >
          <td
            class="JR_right"
            colspan="5"
            align="left"
            style="font-family: '黑体'; font-size: 19px; border: none"
          >
            <span style="margin-left: 7px">军事主官：</span>
          </td>
          <td
            class="JR_right"
            colspan="7"
            align="left"
            style="font-family: '黑体'; font-size: 19px; border: none"
          >
            <span style="margin-left: 4px">政治主官：</span>
          </td>
          <td
            class="JR_right"
            colspan="4"
            align="left"
            style="font-family: '黑体'; font-size: 19px; border: none"
          >
            <span style="margin-left: 43px">时间：</span>
          </td>
        </tr>
      </table>

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
        width="933"
        bordercolor="black"
      ></table>
      <!-- <div
        class="a1"
        style="
          width: 100%;
          height: 50px;
          font-size: 16px;
          font-weight: 600;
          color: black;
          display: flex;
          justify-content: space-between;
        "
      >
        <p style="">军事主官：</p>
        <p class="aa">政治主官：</p>
        <p style="">时间：</p>
      </div> -->
    </div>
  </div>
</template>
<script>
import exportWord from "@/utils/exportWord.js";
import { lookequipuntits } from "@/api/grassrootsregistration/bdglequip";
export default {
  name: "bdgleven",
  props: ["message"],
  data() {
    return {
      title: "训练器材、教材登记簿",
      faith: {
        fileName: "训练器材、教材登记簿", //文档名
        fileType: ".doc", //文档类型  经测试  可以doc xls html 其他的自己去试
        // header: "测试页眉", //页眉标题
        // footer: "测试页脚", //页脚标题
        direction: "Horizontal", //默认或不填写为纵向，Horizontal 为横向
        dom: "#contents", // dom节点
        num: 0,
      },
    };
  },
  mounted() {
    if (this.message.length > 0) {
      this.message.forEach((item, i) => {
        if (item.outtime) {
          item.outtime = this.zhuanhuan(item.outtime);
        } else {
          item.outtime = "";
        }
        if (item.intime) {
          item.intime = this.zhuanhuan(item.intime);
        } else {
          item.intime = "";
        }
        if (item.granttime) {
          item.granttime = this.zhuanhuan(item.granttime);
        } else {
          item.outtime = "";
        }
        item.ids = i + 1;
      });
      //循环遍历
      for (var i = 0; i < this.message.length; i++) {
        //循环开始行
        var start_row;
        //需合并的行数
        var rowspan = 1;
        //循环到最后一行时
        if (i == this.message.length - 1) {
          //如果最后一行和倒数第二行属性不同，则rowspan=1；否则直接结束循环
          if (this.message[i].type != this.message[i - 1].type) {
            this.message[i].rowspan = rowspan;
          }
          break;
        }
        //内层循环记录rowspan的数量
        for (var j = i; j < this.message.length - 1; j++) {
          //记录循环结束的行数
          start_row = j;
          //属性相同则rowspan+1；否则直接结束内循环
          if (this.message[j].type == this.message[j + 1].type) {
            rowspan++;
          } else {
            break;
          }
        }
        //为数组添加rowspan属性
        this.message[i].rowspan = rowspan;
        //控制外循环从内循环结束的行数开始
        i = start_row;
      }
      //页面渲染
      var tbody = document.getElementById("tbody");
      var html = ``;
      for (var i = 0; i < this.message.length; i++) {
        html += `<tr><td
              width="35"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right" >${this.message[i].ids}</span>
            </td>`;
        //动态添加rowspan属性
        if (this.message[i].rowspan != null) {
          html += `<td width="63" rowspan = '${this.message[i].rowspan}'>${this.message[i].type}</td>`;
        }
        html += `
      <td
              width="82"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].title}</span>
            </td>
           
            <td
              width="64"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].unitName}</span>
            </td>
            <td
              width="54"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].granttime}</span>
            </td>
            <td
              width="46"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].grantnum}</span>
            </td>
            <td
              width="47"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].ownnum}</span>
            </td>
            <td
              width="55"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].stocknum}</span>
            </td>
            <td
              width="53"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].intime}</span>
            </td>
            <td
              width="46"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].innum}</span>
            </td>
            <td
              width="72"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].peopleName}</span>
            </td>
            <td
              width="55"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].outtime}</span>
            </td>
            <td
              width="46"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].outnum}</span>
            </td>
            <td
              width="75"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].people1Name}</span>
            </td>
            <td
              width="54"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].quality}</span>
            </td>
            <td
              width="55"
              valign="center"
              style="
                font-family: '仿宋_GB2312';
                text-align: center;
                font-size: 16px;
              "
            >
              <span align="center" class="JR_right">${this.message[i].info}</span>
            </td>
          </tr>
`;
      }
      tbody.innerHTML = html;
    }
  },
  methods: {
    daochu() {
      // // exportWord(this.faith);
      lookequipuntits(this.message[0].unitId).then((res) => {
        window.open(res.msg, "_self");
      });
    },
    zhuanhuan(originVal) {
      const dt = new Date(originVal);
      if (originVal) {
        const y = dt.getFullYear();
        const m = (dt.getMonth() + 1 + "").padStart(2, "0");
        const d = (dt.getDate() + "").padStart(2, "0");
        return `${y}.${m}`;
      }
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