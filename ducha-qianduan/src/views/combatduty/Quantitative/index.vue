<template>
  <div class="wrap">
    <div class="wrap-top">
      <div class="wrap-top-left">
        <div class="wrap-top-left-top">
          <!-- <img src="../../../images/Quantitative/" alt=""> -->
          <div class="wrap-top-left-top-head"><b>基地值班</b></div>
          <div style="width: 100%; display: flex">
            <div class="wrap-top-left-top-item">
              <div class="wrap-top-left-top-item-left">值 班 首 长</div>
              <div
                class="wrap-top-left-top-item-right wrap-top-left-top-item-name"
                :title="BaseOnDuty.chiefDutyName"
              >
                {{ this.BaseOnDuty.chiefDutyName }}
              </div>
            </div>
            <div class="wrap-top-left-top-item">
              <div class="wrap-top-left-top-item-left">总 值 班 员</div>
              <div
                class="wrap-top-left-top-item-right wrap-top-left-top-item-name"
                :title="BaseOnDuty.dutyOffierName"
              >
                {{ this.BaseOnDuty.dutyOffierName }}
              </div>
            </div>
            <div class="wrap-top-left-top-item">
              <div class="wrap-top-left-top-item-left zuozhan">
                作 战 值 班 员
              </div>
              <div
                class="wrap-top-left-top-item-right wrap-top-left-top-item-name"
                :title="BaseOnDuty.combatDutyName"
              >
                {{ this.BaseOnDuty.combatDutyName }}
              </div>
            </div>
          </div>
          <div style="width: 100%; display: flex">
            <div class="wrap-top-left-top-item">
              <div class="wrap-top-left-top-item-left">值 班 分 队 一</div>
              <div
                class="wrap-top-left-top-item-right wrap-top-left-top-item-name box1"
                :title="BaseOnDuty.detachmentoneName"
              >
                {{ this.BaseOnDuty.detachmentoneName }}
              </div>
            </div>
            <div class="wrap-top-left-top-item">
              <div class="wrap-top-left-top-item-left">一分队指挥员</div>
              <div
                class="wrap-top-left-top-item-right wrap-top-left-top-item-name"
                :title="BaseOnDuty.detachmentonePeopleName"
              >
                {{ this.BaseOnDuty.detachmentonePeopleName }}
              </div>
            </div>
            <div class="wrap-top-left-top-item">
              <div class="wrap-top-left-top-item-left zuozhan">
                一 分 队 人 数
              </div>
              <div class="wrap-top-left-top-item-right2">
                <span> {{ this.BaseOnDuty.detachmentoneNumber }}</span
                >人
              </div>
            </div>
          </div>
          <div style="width: 100%; display: flex">
            <div class="wrap-top-left-top-item">
              <div class="wrap-top-left-top-item-left">值 班 分 队 二</div>
              <div
                class="wrap-top-left-top-item-right wrap-top-left-top-item-name box1"
                :title="BaseOnDuty.detachmentwoName"
              >
                {{ this.BaseOnDuty.detachmentwoName }}
              </div>
            </div>
            <div class="wrap-top-left-top-item">
              <div class="wrap-top-left-top-item-left">二分队指挥员</div>
              <div
                class="wrap-top-left-top-item-right wrap-top-left-top-item-name"
                :title="BaseOnDuty.detachmentwoPeopleName"
              >
                {{ this.BaseOnDuty.detachmentwoPeopleName }}
              </div>
            </div>

            <div class="wrap-top-left-top-item">
              <div class="wrap-top-left-top-item-left zuozhan">
                二 分 队 人 数
              </div>
              <div class="wrap-top-left-top-item-right2">
                <span>{{ this.BaseOnDuty.detachmentwoNumber }}</span
                >人
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="wrap-top-left-top" style="margin-left: 10px">
        <div class="wrap-top-left-top-head"><b>大队值班</b></div>
        <div style="width: 100%; display: flex">
          <div class="wrap-top-left-top-item">
            <div class="wrap-top-left-top-item-left">值 班 首 长</div>
            <div
              class="wrap-top-left-top-item-right wrap-top-left-top-item-name"
              :title="GreatDuty1.chiefDutyName"
            >
              {{ this.GreatDuty1.chiefDutyName }}
            </div>
          </div>

          <div class="wrap-top-left-top-item">
            <div class="wrap-top-left-top-item-left zuozhan">
              作 战 值 班 员
            </div>
            <div
              class="wrap-top-left-top-item-right wrap-top-left-top-item-name"
              :title="GreatDuty1.combatDutyName"
            >
              {{ this.GreatDuty1.combatDutyName }}
            </div>
          </div>

          <div class="wrap-top-left-top-item">
            <div class="wrap-top-left-top-item-left">值 班 通 信 员</div>
            <div
              class="wrap-top-left-top-item-right wrap-top-left-top-item-name"
              :title="GreatDuty1.messengerName"
            >
              {{ this.GreatDuty1.messengerName }}
            </div>
          </div>
        </div>
        <div style="width: 100%; display: flex">
          <div class="wrap-top-left-top-item">
            <div class="wrap-top-left-top-item-left">机要值班员</div>
            <div
              class="wrap-top-left-top-item-right wrap-top-left-top-item-name"
              :title="GreatDuty1.confidentialName"
            >
              {{ this.GreatDuty1.confidentialName }}
            </div>
          </div>

          <div class="wrap-top-left-top-item">
            <div class="wrap-top-left-top-item-left">通 信 值 班 员</div>
            <div
              class="wrap-top-left-top-item-right wrap-top-left-top-item-name"
              :title="GreatDuty1.dutyOffierName"
              style="margin-left: 10px"
            >
              {{ this.GreatDuty1.dutyOffierName }}
            </div>
          </div>

          <div class="wrap-top-left-top-item">
            <div class="wrap-top-left-top-item-left zuozhan">
              <!-- 值&nbsp;&nbsp;&nbsp;&nbsp; 班&nbsp;&nbsp;&nbsp;&nbsp; 分&nbsp;&nbsp;&nbsp;&nbsp; 队 -->
              <span style="margin-right: 10px">分</span
              ><span style="margin-right: 10px">队</span
              ><span style="margin-right: 10px">数</span><span>量</span>
            </div>
            <div class="wrap-top-left-top-item-right3">
              {{ fenShu }}
              队
              <!-- 发射一营 -->
            </div>
          </div>
        </div>
        <div style="width: 100%; display: flex">
          <div class="wrap-top-left-top-item">
            <div class="wrap-top-left-top-item-left" style="width: 120px">
              政治工作部值班员
            </div>
            <div
              class="wrap-top-left-top-item-right wrap-top-left-top-item-name"
              :title="GreatDuty1.politicalName"
            >
              {{ this.GreatDuty1.politicalName }}
            </div>
          </div>
          <div class="wrap-top-left-top-item">
            <div class="wrap-top-left-top-item-left" style="width: 110px">
              保 障 部 值 班 员
            </div>
            <div
              class="wrap-top-left-top-item-right wrap-top-left-top-item-name"
              :title="GreatDuty1.guaranteeName"
            >
              {{ this.GreatDuty1.guaranteeName }}
            </div>
          </div>
          <div class="wrap-top-left-top-item">
            <div class="wrap-top-left-top-item-left1">
              <span>分</span>
              <span>队</span>
              <span>总</span>
              <span>人</span>
              <span>数</span>
            </div>
            <div class="wrap-top-left-top-item-right" style="margin-left: 15px">
              {{ fenZong }}人
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="wrap-footer">
      <div class="wrap-footer-center">
        <div class="wrap-top-left-top-head"><b>执行任务人员情况</b></div>
        <table width="95%" style="table-layout: fixed">
          <tr height="30">
            <!-- <th style="width:100px;font-weight:500" height="40">单位</th> -->
            <th style="width: 100px; font-weight: 500">指挥员</th>
            <th style="width: 120px; font-weight: 500">事由</th>
            <th style="width: 100px; font-weight: 500">所在位置</th>
            <th style="width: 100px; font-weight: 500">开始时间</th>
            <th style="width: 110px; font-weight: 500">结束时间</th>
          </tr>

          <tr height="30" v-for="item in performanceTask" :key="item.id">
            <!-- <td style="color:#ccc"  height="28">{{item.unitName}}</td> -->
            <td style="color: #ccc">{{ item.commanderName }}</td>
            <td style="color: #ccc" class="shiyou" :title="item.question">
              {{ item.question }}
            </td>
            <td style="color: #ccc" class="shiyou">{{ item.adress }}</td>
            <td style="color: #ccc">{{ item.startTime }}</td>
            <td style="color: #ccc">{{ item.endTime }}</td>
          </tr>
        </table>
      </div>
      <div class="wrap-footer-right zhuangbei">
        <div class="wrap-top-center">
          <div class="renyuan"><b>人员情况</b></div>
          <div class="wrap-top-center-item">
            <div class="wrap-top-center-item-left" id="a1">
              <img src="../../../images/Quantitative/编纳总人数.png" alt="" />
            </div>
            <div class="wrap-top-center-item-right">
              <div class="wrap-top-center-item-right-top">编纳总人数</div>
              <div class="wrap-top-center-item-right-bottom">
                {{ this.peopleQK.zaibianzongshu }}
              </div>
            </div>
          </div>
          <div class="wrap-top-center-item">
            <div class="wrap-top-center-item-left" id="a3">
              <img src="../../../images/Quantitative/编纳在位.png" alt="" />
            </div>
            <div class="wrap-top-center-item-right">
              <div class="wrap-top-center-item-right-top">编纳在位</div>
              <div class="wrap-top-center-item-right-bottom">
                {{ this.peopleQK.zaibianzaiwei }}
              </div>
            </div>
          </div>
          <div class="wrap-top-center-item">
            <div class="wrap-top-center-item-left1">
              <img
                style="width: 44px; height: 22px"
                src="../../../images/Quantitative/休假.png"
                alt=""
              />
            </div>
            <div class="wrap-top-center-item-right">
              <div
                class="wrap-top-center-item-right-top"
                style="margin-top: -40px; margin-left: 10px"
              >
                编纳休假
              </div>
              <div
                class="wrap-top-center-item-right-bottom"
                style="margin-left: 30px"
              >
                {{ this.peopleQK.zaibianxiujia }}
              </div>
            </div>
          </div>
          <div class="wrap-top-center-item">
            <div class="wrap-top-center-item-left" id="a5">
              <img src="../../../images/Quantitative/出差.png" alt="" />
            </div>
            <div class="wrap-top-center-item-right">
              <div class="wrap-top-center-item-right-top">编纳出差</div>
              <div class="wrap-top-center-item-right-bottom">
                {{ this.peopleQK.zaibianchuchai }}
              </div>
            </div>
          </div>
          <div class="wrap-top-center-item">
            <div class="wrap-top-center-item-left" id="a7">
              <img src="../../../images/Quantitative/学习.png" alt="" />
            </div>
            <div class="wrap-top-center-item-right">
              <div class="wrap-top-center-item-right-top">编纳学习</div>
              <div class="wrap-top-center-item-right-bottom">
                {{ this.peopleQK.zaibianxuexi }}
              </div>
            </div>
          </div>
          <div class="wrap-top-center-item">
            <div class="wrap-top-center-item-left">
              <img src="../../../images/Quantitative/请假.png" alt="" />
            </div>
            <div class="wrap-top-center-item-right">
              <div class="wrap-top-center-item-right-top">编纳外诊</div>
              <div class="wrap-top-center-item-right-bottom">
                {{ this.peopleQK.zaibianwaizhen }}
              </div>
            </div>
          </div>
          <div class="wrap-top-center-item">
            <div class="wrap-top-center-item-left" id="a2">
              <img src="../../../images/Quantitative/未编纳总人数.png" alt="" />
            </div>
            <div class="wrap-top-center-item-right">
              <div class="wrap-top-center-item-right-top">未编纳总人数</div>
              <div class="wrap-top-center-item-right-bottom">
                {{ this.peopleQK.weizaibianzongrenshu }}
              </div>
            </div>
          </div>
          <div class="wrap-top-center-item">
            <div class="wrap-top-center-item-left" id="a4">
              <img src="../../../images/Quantitative/编纳在位.png" alt="" />
            </div>
            <div class="wrap-top-center-item-right">
              <div class="wrap-top-center-item-right-top">未编纳在位</div>
              <div class="wrap-top-center-item-right-bottom">
                {{ this.peopleQK.weizaibianzaiwei }}
              </div>
            </div>
          </div>
          <div class="wrap-top-center-item">
            <div class="wrap-top-center-item-left1">
              <img
                style="width: 44px; height: 22px"
                src="../../../images/Quantitative/休假.png"
                alt=""
              />
            </div>
            <div class="wrap-top-center-item-right">
              <div
                class="wrap-top-center-item-right-top"
                style="margin-top: -40px; margin-left: 15px"
              >
                未编纳休假
              </div>
              <div
                class="wrap-top-center-item-right-bottom"
                style="margin-left: 30px"
              >
                {{ this.peopleQK.weizaibianxiujia }}
              </div>
            </div>
          </div>
          <div class="wrap-top-center-item">
            <div class="wrap-top-center-item-left" id="a6">
              <img src="../../../images/Quantitative/出差.png" alt="" />
            </div>
            <div class="wrap-top-center-item-right">
              <div class="wrap-top-center-item-right-top">未编纳出差</div>
              <div class="wrap-top-center-item-right-bottom">
                {{ this.peopleQK.weizaibianchuchai }}
              </div>
            </div>
          </div>
          <div class="wrap-top-center-item">
            <div class="wrap-top-center-item-left" id="a8">
              <img src="../../../images/Quantitative/学习.png" alt="" />
            </div>
            <div class="wrap-top-center-item-right">
              <div class="wrap-top-center-item-right-top">未编纳学习</div>
              <div class="wrap-top-center-item-right-bottom">
                {{ this.peopleQK.weizaibianxuexi }}
              </div>
            </div>
          </div>
          <div class="wrap-top-center-item">
            <div class="wrap-top-center-item-left">
              <img src="../../../images/Quantitative/请假.png" alt="" />
            </div>
            <div class="wrap-top-center-item-right">
              <div class="wrap-top-center-item-right-top">未编纳外诊</div>
              <div class="wrap-top-center-item-right-bottom">
                {{ this.peopleQK.weizaibianwaizhen }}
              </div>
            </div>
          </div>
        </div>
        <div class="junchezhuangbei">
          <!-- 军车值班 -->
          <div class="wrap-top-right junche">
            <div class="wrap-top-left-top-head"><b>军车值班</b></div>
            <table width="95%" style="table-layout: fixed">
              <tr>
                <th style="font-weight: 500" height="40">序号</th>
                <th style="font-weight: 500">车牌号码</th>
                <!-- <th style="font-weight: 500">厂牌型号</th> -->
                <th style="font-weight: 500">司机</th>
                <th style="font-weight: 500">司机位置</th>
              </tr>

              <tr
                style="color: #ccc"
                v-for="(item, index) in MilitaryDuty"
                :key="item.id"
              >
                <td style="color: #ccc" height="28">{{ index + 1 }}</td>
                <td
                  style="
                    color: #ccc;
                    display: -webkit-box;
                    -webkit-box-orient: vertical;
                    -webkit-line-clamp: 2;
                    overflow: hidden;
                  "
                  :title="item.numberPlateName"
                >
                  {{ item.numberPlateName }}
                </td>
                <!-- <td style="color: #ccc">{{ item.modelInfo }}</td>   -->
                <td style="color: #ccc">
                  {{ item.driverName }}
                </td>
                <td style="color: #ccc" class="shiyou">
                  {{ item.adress }}
                </td>
              </tr>
            </table>
          </div>
          <!-- 装备值班 -->
          <div class="zhuangbeizhiban">
            <div class="wrap-top-left-top-head"><b>装备值班</b></div>
            <table width="95%" style="table-layout: fixed">
              <tr>
                <th style="width: 80px; font-weight: 500" height="40">序号</th>
                <th style="width: 80px; font-weight: 500">装备名称</th>
                <!-- <th style="width: 80px; font-weight: 500">所属单位</th> -->
                <th style="width: 80px; font-weight: 500">装备类型</th>
                <th style="width: 80px; font-weight: 500">数量</th>
                <th style="width: 100px; font-weight: 500">位置</th>
              </tr>
              <tr v-for="(item, index) in Equipment" :key="item.id">
                <td style="color: #ccc" height="28">{{ index + 1 }}</td>
                <td style="color: #ccc">{{ item.equipmentName }}</td>
                <td style="color: #ccc">
                  <template>
                    <dict-tag
                      :options="dict.type.type_type"
                      :value="item.type"
                    />
                  </template>
                </td>
                <td style="color: #ccc">{{ item.equipmentNumber }}</td>
                <td style="color: #ccc" class="shiyou">
                  {{ item.standbyPosition }}
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
    <div class="bottomsss"></div>
  </div>
</template>
<script>
import {
  // 值班基地接口
  getTodayzhiban,
  // 大队值班接口
  greatDuty,
  greatDutys,
  // 军车值班接口
  militaryDuty,
  // 装备值班接口
  equipment,
  // 执行任务人员情况
  zhixingrenwu,
  // 人员情况
  listshow,
} from "@/api/combatduty/Quantitative";
import { getDicts } from "@/api/system/dict/data";
export default {
  dicts: ["type_type"],
  data() {
    return {
      // 基地值班
      BaseOnDuty: [],
      // 大队值班
      GreatDuty1: [],
      // 军车值班
      MilitaryDuty: [],
      // 装备值班
      Equipment: [],
      // 执行任务
      performanceTask: [],
      // 人员情况
      peopleQK: [],
      // 查询字典
      dictType: {
        dictType: "brand",
      },
      ziDian: [],
      // 分队数量
      fenShu: "",
      // 分队总人数
      fenZong: "",
    };
  },
  created() {
    // 人员情况
    listshow().then((res) => {
      this.peopleQK = res.data;
    });
    // 基地值班
    getTodayzhiban().then((res) => {
      if (res.rows[0] != undefined) {
        this.BaseOnDuty = res.rows[0];
      }
    });
    // 大队值班接口
    greatDuty().then((res) => {
      if (res.rows[0] != undefined) {
        this.GreatDuty1 = res.rows[0];
      }
    });
    greatDutys().then((res) => {
      this.fenShu = res.data[0];
      this.fenZong = res.data[1];
    });
    // 查询字典
    this.getdict();

    // // 装备值班接口
    equipment().then((res) => {
      if (res.rows != undefined) {
        this.Equipment = res.rows;
      }
    });
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
      month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
      strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    // 执行任务人员情况
    zhixingrenwu({ startTime: currentdate }).then((res) => {
      if (res.rows != undefined) {
        this.performanceTask = res.rows;
      }
    });
  },
  mounted() {
    // // 军车值班接口
    militaryDuty().then((res) => {
      this.MilitaryDuty = res.rows;
      console.log(res.rows);
      this.MilitaryDuty.map((item) => {
        this.ziDian.map((items) => {
          if (item.modelInfo == items.dictValue) {
            return (item.modelInfo = items.dictLabel);
          }
          this.MilitaryDuty = this.MilitaryDuty;
        });
      });
    });
  },
  methods: {
    // 查询字典
    getdict() {
      getDicts(this.dictType.dictType).then((res) => {
        this.ziDian = res.data;
      });
    },
  },
};
</script>
<style scoped>
.wrap-top-left-top-item-right3 {
  width: 100%;
  text-align: center;
  color: #30fdff;
  line-height: 35px;
  letter-spacing: 0.2rem;
}
#app {
  width: 100%;
  height: 100%;
}
html,
body {
  height: 100%;
  width: 100%;
}
.content {
  background-color: #103360;
}
.bottomsss {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 10px;
  /* background-color: pink; */
}
#wrap {
  position: relative;
}
/* .wrap {
  min-height: calc(100vh - 85px);
  width: 100%;
  position: relative;
  overflow: hidden;
  background-color: #103360;
  margin: 0;
  padding:0;
} */
/* 军车装备样式 */
.junchezhuangbei {
  display: flex;
  width: 98.5%;
  height: 65.5%;
  /* margin-left: 10px; */
  margin-top: 10px;
  /* margin: 0 1%; */
  /* background-color: purple; */
  /* margin-top: -170px; */
}
/* 装备值班样式 */
.zhuangbeizhiban {
  /* flex: 1; */
  width: 98%;
  height: 92.5%;
  background-image: url("../../../images/Quantitative/底 拷贝 8.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  margin-left: 10px;
  /* margin-top: 10px; */
  /* margin: 10px 1%; */
  /* margin-left: 17px;
  margin-right: 10px; */
}
.wrap-top {
  position: absolute;
  width: 98.9%;
  height: 23vh;
  /* background-color: pink; */
  display: flex;
  /* flex: 1 */
  flex-direction: row;
  margin: 10px;
  /* padding-top: 5px;
  margin-bottom: 5px; */
}

.wrap-top-left {
  width: 100%;
  flex: 1;
  height: 100%;
  /* margin-left: 9px; */
}

.wrap-top-center {
  /* flex: 1; */
  width: 98.5%;
  height: 38%;
  /* max-width: 98.5%;
   max-height: 35%; */
  /* background-image: url("../../../images/Quantitative/底 拷贝 6 (1).png"); */
  background-image: url("../../../images/Quantitative/底 拷贝 6.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  /* margin-right: 10px; */
  /* margin-top: 10px; */
  /* margin: 0 1%; */
  display: flex;
  flex-wrap: wrap;
  /* margin-right: 8px; */
}

.wrap-top-right {
  /* flex: 1; */
  width: 98%;
  height: 92.5%;
  /* max-width: 98%;
  max-height: 96%; */
  background-image: url("../../../images/Quantitative/底 拷贝 8.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  /* margin-left: 10px; */
  /* margin-top: 10px;
  margin-left: 17px;
  margin-right: 10px; */
}

.wrap-top-left-top {
  width: 100%;
  height: 100%;
  flex: 1;
  background-image: url("../../../images/Quantitative/底.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  max-height: 100%;
  max-width: 100%;
  /* margin: 0 1%; */
  /* margin-left: 10px; */
  /* margin-left: 10px; */
  display: flex;
  flex-wrap: wrap;
  /* margin-top: 10px; */
}

.wrap-top-left-top-item {
  width: 30%;
  height: 40px;
  max-width: 30%;
  max-height: 40px;
  display: inline-block;
  border: #0f4d75 1px solid;
  margin-left: 2.5%;
  margin-top: 3px;
  display: flex;
}

.wrap-top-left-top-item-left {
  width: 86px;
  margin-left: 8px;

  height: 100%;
  font-size: 14px;
  text-align: start;
  color: #fff;
  display: flex;
  align-items: center;
}
.wrap-top-left-top-item-left1 {
  width: 84px;
  margin-left: 8px;
  height: 100%;
  font-size: 14px;
  text-align: start;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.wrap-top-left-top-item-right {
  width: 64%;
  height: 100%;
  font-size: 16px;
  line-height: 35px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #30fdff;
  letter-spacing: 1em; /*如果需要y个字两端对齐，则为(x-y)/(y-1),这里是（4-2）/(2-1)=2em */
  margin-right: -1em;
}
.wrap-top-left-top-item-right:hover {
  cursor: pointer;
}
.wrap-top-left-top-item-right1 {
  width: 60%;
  height: 100%;
  font-size: 16px;
  line-height: 35px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #30fdff;
  letter-spacing: 0.2em; /*如果需要y个字两端对齐，则为(x-y)/(y-1),这里是（4-2）/(2-1)=2em */
  margin-right: -0.2em;
}
.wrap-top-left-top-item-right2 {
  width: 60%;
  height: 100%;
  font-size: 16px;
  line-height: 35px;
  /* display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 10px; */
  text-align: center;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #30fdff;
  letter-spacing: 0.2rem; /*如果需要y个字两端对齐，则为(x-y)/(y-1),这里是（4-2）/(2-1)=2em*/
  /* margin-right: -3.5em; 同上 */
}
.wrap-top-left-top-item-right2 span {
  margin-right: 10px;
}
.wrap-top-left-top-head {
  width: 100%;
  height: 30px;
  font-size: 18px;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #eff7ff;
  /* padding-left: 10px; */
  background-image: url("../../../images/组 21.png");
  background-repeat: no-repeat;
  /* background-size: 60% 100%; */
  margin-top: 17px;
  margin-left: 21px;
}
.junche .wrap-top-left-top-head {
  background-image: url("../../../images/组 21.png");
  background-repeat: no-repeat;
  /* background-size: 79% 100%; */
}
.zhuangbei .wrap-top-left-top-head {
  background-image: url("../../../images/组 21.png");
  background-repeat: no-repeat;
  /* background-size: 95% 100%; */
}
.renyuan {
  width: 100%;
  height: 30px;
  font-size: 18px;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #eff7ff;
  margin-top: 17px;
  margin-left: 21px;
  padding-left: 40px;
  background-image: url("../../../images/组 21.png");
  background-repeat: no-repeat;
  /* background-size: 50% 100%; */
}
.wrap-footer-center .wrap-top-left-top-head {
  background-image: url("../../../images/组 21.png");
  background-repeat: no-repeat;
  /* background-size: 85% 100%; */
}
.wrap-top-left-top-head b {
  font-size: 18px;
  color: #fff;
  font-weight: 400;
  font-size: 18px;
  font-family: Source Han Sans CN;
  /* font-weight: 400; */
  color: #eff7ff;
  margin-left: 40px;
  line-height: 30px;
}
.renyuan b {
  line-height: 30px;
  font-weight: 400;
  font-size: 18px;
  font-family: Source Han Sans CN;
}
.wrap-top-center .wrap-top-left-top-head {
  margin-top: 10px;
}

.wrap-top-right .wrap-top-left-top-head {
  margin-top: 17px;
}
/*九宫格*/
.wrap-top-center-item {
  width: 14%;
  height: 30%;
  background-color: #0c2744;
  margin-left: 2.5%;
  margin-top: 5px;
  display: flex;
  flex-wrap: wrap;
}

.wrap-top-center-item-left {
  flex: 1;
  height: 80%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.wrap-top-center-item-left1 img {
  margin-left: 5px;
  margin-top: 18px;
}
.wrap-top-center-item-left img {
  margin-top: 7px;
  width: 35px;
  height: 35px;
}

.wrap-top-center-item-right {
  flex: 1;
  height: 100%;
}

.wrap-top-center-item-right-top {
  width: 100%;
  height: 20px;
  font-size: 13px;
  color: #fff;
  line-height: 20px;
  text-align: center;
  margin-top: 5px;
}

.wrap-top-center-item-right-bottom {
  width: 100px;
  height: 30px;
  font-size: 24px;
  color: #fff;
  line-height: 30px;
  text-align: center;
  margin-top: 5px;
}

table tr th {
  color: #fff;
  text-align: center;
}

table tr td {
  text-align: center;
  color: #fff;
}

.wrap-footer {
  position: absolute;
  bottom: 0;
  top: 24vh;
  width: 99.8%;
  /* height: calc(100vh-159.84px); */
  /* height: 90%; */
  /* height: 66vh; */
  /* height:calc(100% - 100px); */
  /* height: calc(77vh-94px); */
  /* overflow:scroll; */
  display: flex;
  /* flex: 1; */
  margin: 10px;
  flex-direction: row;
}

.wrap-footer-left {
  flex: 1.1;
  height: 41vh;
  margin-left: 10px;
}

.wrap-footer-center {
  flex: 1;
  height: 100%;
  width: 100%;
  max-width: 100%;
  max-height: 100%;
  /* width: 1100px; */
  background-image: url("../../../images/Quantitative/底 拷贝 7.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  /* margin-top: 10px; */
  /* margin-left: 20px; */
  /* padding-right: 40px; */
  /* margin-bottom: 10px; */
}

.wrap-footer-right {
  width: 99%;
  height: 100%;
  display: flex;
  flex-wrap: wrap;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  margin-left: 10px;
}
.wrap-footer-left-top {
  width: 96%;
  height: 195px;
  background-image: url("/taishi/img/r1.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  margin: 0 2%;
  margin-top: 10px;
}
.wrap-footer-left-top-text {
  width: 96%;
  /*display: flex;*/
  /*align-items: center;*/
  /*justify-content: left;*/
  color: #fff;
  margin: 10px 2%;
  font-size: 14px;
  line-height: 20px;
  text-align: left;
}
.wrap-top-center-item-right-bottom {
  color: #ff9000;
  font-weight: bold;
  font-size: 34px;
}
.zuozhan {
  width: 100px;
}
.shiyou {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
#a1 img {
  height: 30px;
}
#a2 img {
  height: 30px;
}
#a3 img {
  width: 30px;
}
#a4 img {
  width: 30px;
}
#a5 img {
  width: 30px;
}
#a6 img {
  width: 30px;
}
#a7 img {
  width: 30px;
  height: 30px;
}
#a8 img {
  width: 30px;
  height: 30px;
}
.wrap-top-left-top-item-name {

  font-size: 16px;
}
.box1{
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  overflow: hidden;
  margin-left: 8px;
}
</style>