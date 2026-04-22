<template>
    <div class="out">
      <div class="title">
        <div class="title-left" @click="titlefun">
          <img src="../../assets/images/返回icon.png" alt />
          <span>主控台4</span>
        </div>
        <div class="title-right">
          <span>{{ times }}</span>
        </div>
      </div>
      <div class="main">
        <div class="left">
          <div class="left1">
            <img src="../../assets/images/右上角.png" alt class="left1-jrt" />
            <img src="../../assets/images/右下角.png" alt class="left1-jrb" />
            <img src="../../assets/images/左上角.png" alt class="left1-jlt" />
            <img src="../../assets/images/左下角.png" alt class="left1-jlb" />
            <div class="left1-head">
              <span>今日值班</span>
            </div>
            <div class="left1-wrap">
              <div
                class="left1-wrap-item"
                v-for="(item, index) in jrzblist"
                :key="index"
              >
                <span>{{ item.name }}：</span>
                <b v-if="item.value ? true : false">{{ item.value }}</b>
                <b v-if="!item.value ? true : false">无</b>
              </div>
            </div>
          </div>
          <div class="left2">
            <img src="../../assets/images/右上角.png" alt class="left1-jrt" />
            <img src="../../assets/images/右下角.png" alt class="left1-jrb" />
            <img src="../../assets/images/左上角.png" alt class="left1-jlt" />
            <img src="../../assets/images/左下角.png" alt class="left1-jlb" />
            <div class="left2-1">
              <div class="left1-head">
                <span>装备值班情况</span>
              </div>
              <div class="left2-1-main">
                <div class="left2_d">
                  总计
                  <span>8</span>
                </div>
                <div class="left2_d">
                  试验
                  <span>0</span>
                </div>
                <div class="left2_d">
                  通用
                  <span>8</span>
                </div>
              </div>
            </div>
            <div class="left2-2">
              <div class="left1-head">
                <span>军事训练月统计情况</span>
              </div>
              <div class="left2-2-main">
                <div class="left2-2-main-title">
                  <div class="center4_l-1" style="margin-left: 3%">
                    <div
                      style="background-color: #1d79b5; width: 5px; height: 9px"
                    ></div>
                    <span style="line-height: 15px">参训时</span>间
                  </div>
                  <!-- <div class="center4_l-1">
                    <div
                      style="background-color: #1d79b5; width: 5px; height: 9px"
                    ></div>
                    兵力
                  </div>-->
                  <div class="center4_l-1" style="margin-right: 0%">
                    <div
                      style="background-color: #1d79b5; width: 5px; height: 9px"
                    ></div>
                    成绩
                  </div>
                </div>
                <div class="left2-2-main-main">
                  <table class="time">
                    <tr
                      style="font-size: 16px; color: #30fdff; font-weight: bold"
                    >
                      <td>训练类别</td>
                      <td>人均参训时间</td>
                    </tr>
                    <tr>
                      <td>合成训练</td>
                      <td>6小时</td>
                    </tr>
                    <tr>
                      <td>专业训练</td>
                      <td>75小时</td>
                    </tr>
                    <tr>
                      <td>共同训练</td>
                      <td>42小时</td>
                    </tr>
                  </table>
  
                  <!-- <table class="people">
                    <tr>
                      <td>实有人数</td>
                      <td
                        style="
                          font-family: 'IMPACT';
                          color: #30fdff;
                          font-size: 20px;
                        "
                      >
                        547
                      </td>
                    </tr>
                    <tr>
                      <td>参训人数</td>
                      <td
                        style="
                          font-family: 'IMPACT';
                          color: #30fdff;
                          font-size: 20px;
                        "
                      >
                        492
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <p
                          style="
                            font-size: 9px;
                            color: white;
                            text-align: center;
                            transform: scale(0.9);
                          "
                        >
                          未参训人数
                        </p>
                      </td>
                      <td
                        style="
                          font-family: 'IMPACT';
                          color: #30fdff;
                          font-size: 20px;
                        "
                      >
                        115
                      </td>
                    </tr>
                    <tr>
                      <td>参训率</td>
                      <td
                        style="
                          font-family: 'IMPACT';
                          color: #30fdff;
                          font-size: 20px;
                        "
                      >
                        90%
                      </td>
                    </tr>
                  </table>-->
  
                  <table class="grade">
                    <tr>
                      <td>考核人次</td>
                      <td
                        style="
                          font-family: 'IMPACT';
                          color: #30fdff;
                          font-size: 20px;
                        "
                      >
                        902
                      </td>
                    </tr>
                    <tr>
                      <td>优良</td>
                      <td
                        style="
                          font-family: 'IMPACT';
                          color: #30fdff;
                          font-size: 20px;
                        "
                      >
                        573
                      </td>
                    </tr>
                    <tr>
                      <td>及格</td>
                      <td
                        style="
                          font-family: 'IMPACT';
                          color: #30fdff;
                          font-size: 20px;
                        "
                      >
                        295
                      </td>
                    </tr>
                    <tr>
                      <td>未及格</td>
                      <td
                        style="
                          font-family: 'IMPACT';
                          color: #30fdff;
                          font-size: 20px;
                        "
                      >
                        34
                      </td>
                    </tr>
                  </table>
                </div>
              </div>
            </div>
          </div>
          <div class="left3">
            <img src="../../assets/images/右上角.png" alt class="left1-jrt" />
            <img src="../../assets/images/右下角.png" alt class="left1-jrb" />
            <img src="../../assets/images/左上角.png" alt class="left1-jlt" />
            <img src="../../assets/images/左下角.png" alt class="left1-jlb" />
            <div class="left1-head">
              <span>车辆在位情况</span>
            </div>
            <div class="left3-main">
              <div class="center4_l" style="margin-left: 2%">
                <div
                  style="background-color: #1d79b5; width: 5px; height: 9px"
                ></div>
                私家车管理
              </div>
              <div class="center4_l" style="margin-left: 16%">
                <div
                  style="background-color: #1d79b5; width: 5px; height: 9px"
                ></div>
                军车管理
              </div>
  
              <table class="center4_s">
                <tr>
                  <td rowspan="2">
                    <img src="../../assets/images/cheliangz.png" alt />
                  </td>
                  <td style="width: 88px; color: white; font-size: 1rem">
                    车辆统计
                  </td>
                  <td
                    style="
                      color: #30fdff;
                      font-family: 'IMPACT';
                      font-size: 1.6rem;
                    "
                  >
                    121
                  </td>
                </tr>
              </table>
              <table class="center4_s" style="margin-top: 1.3%">
                <tr>
                  <td rowspan="2">
                    <img src="../../assets/images/cheliangz.png" alt />
                  </td>
                  <td style="width: 88px; color: white; font-size: 1rem">
                    今日进出
                  </td>
                  <td
                    style="
                      color: #30fdff;
                      font-family: 'IMPACT';
                      font-size: 1.6rem;
                    "
                  >
                    0
                  </td>
                </tr>
              </table>
  
              <table class="center4_s" style="margin-top: 1.28%">
                <tr>
                  <td rowspan="2">
                    <img src="../../assets/images/cheliangq.png" alt />
                  </td>
                  <td style="width: 88px; color: white; font-size: 1rem">
                    夜间进入
                  </td>
                  <td
                    style="
                      color: #b4372a;
                      font-family: 'IMPACT';
                      font-size: 1.6rem;
                    "
                  >
                    0
                  </td>
                </tr>
              </table>
              <div class="center4_b">
                总计
                <span style="color: #30fdff; float: right; margin-right: 20px"
                  >79</span
                >
              </div>
              <div class="center4_b">
                在位
                <span style="color: #fde17b; float: right; margin-right: 20px"
                  >73</span
                >
              </div>
              <div class="center4_b">
                外出
                <span style="color: #f0422a; float: right; margin-right: 20px"
                  >2</span
                >
              </div>
              <div class="center4_b">
                出车统计
                <span style="color: #f0422a; float: right; margin-right: 20px"
                  >0</span
                >
              </div>
            </div>
            <!-- <div class="left3-main">
              <div class="center4_l" style="margin-left: 2%">
                <div style="background-color: #1d79b5; width: 5px; height: 9px"></div>军车管理
              </div>
              <div class="center4_l" style="margin-left: 9.5%">
                <div style="background-color: #1d79b5; width: 5px; height: 9px"></div>私家车管理
              </div>
              <div class="center4_b">
                总计
                <span style="color: #30fdff;float: right;  margin-right: 20px;">71</span>
              </div>
              <div class="center4_b">
                在位
                <span style="color: #fde17b;float: right;  margin-right: 20px;">52</span>
              </div>
              <div class="center4_b">
                外出
                <span style="color: #f0422a;float: right;  margin-right: 20px;">19</span>
              </div>
              <div class="center4_b">
                出车统计
                <span style="color: #f0422a;float: right;  margin-right: 20px;">3</span>
              </div>
              <table class="center4_s">
                <tr>
                  <td rowspan="2">
                    <img src="../../assets/images/cheliangz.png" alt />
                  </td>
                  <td style="color: #30fdff; font-family: 'IMPACT'; font-size: 22px">119</td>
                </tr>
                <tr>
                  <td style="font-size: 13px; color: white">车辆统计</td>
                </tr>
              </table>
  
              <table class="center4_s" style="margin-top: 1.3%">
                <tr>
                  <td rowspan="2">
                    <img src="../../assets/images/cheliangz.png" alt />
                  </td>
                  <td style="color: #30fdff; font-family: 'IMPACT'; font-size: 22px">107</td>
                </tr>
                <tr>
                  <td style="font-size: 13px; color: white">车辆在位</td>
                </tr>
              </table>
  
              <table class="center4_s" style="margin-top: 1.28%">
                <tr>
                  <td rowspan="2">
                    <img src="../../assets/images/cheliangz.png" alt />
                  </td>
                  <td style="color: #30fdff; font-family: 'IMPACT'; font-size: 22px">12</td>
                </tr>
                <tr>
                  <td style="font-size: 13px; color: white">车辆出场</td>
                </tr>
              </table>
  
              <table class="center4_y" style="margin-top: 1.5%">
                <tr>
                  <td>
                    <img src="../../assets/images/cheliangq.png" alt />
                  </td>
                  <td style="font-family: 'IMPACT'; font-size: 22px; color: #b4372a">0</td>
                </tr>
                <tr>
                  <td colspan="2" style="letter-spacing: 7px; font-size: 13px">夜间进入</td>
                </tr>
              </table>
  
              <table class="center4_y center4_y2" style="margin-top: 1.5%">
                <tr>
                  <td>
                    <img src="../../assets/images/cheliangq.png" alt />
                  </td>
                  <td style="font-family: 'IMPACT'; font-size: 22px; color: #b4372a">0</td>
                </tr>
                <tr>
                  <td colspan="2" style="letter-spacing: 7px; font-size: 13px">外来车辆</td>
                </tr>
              </table>
            </div>-->
          </div>
        </div>
        <div class="center">
          <div class="center1">
            <img src="../../assets/images/右上角.png" alt class="left1-jrt" />
            <img src="../../assets/images/右下角.png" alt class="left1-jrb" />
            <img src="../../assets/images/左上角.png" alt class="left1-jlt" />
            <img src="../../assets/images/左下角.png" alt class="left1-jlb" />
            <div class="left1-head">
              <span>大队人员在位情况</span>
            </div>
            <div class="center1-t">
              <div
                style="height: 0px; display: flex; width: 100%; margin: 0 auto"
                id="center1"
              >
                <table class="center1_d" style="margin-left: 10px">
                  <tr>
                    <td></td>
                    <td style="padding-left: 7px; padding-bottom: 5px">
                      在位总数
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <img src="../../assets/images/zong.png" alt />
                    </td>
                    <td>
                      <div>5</div>
                      <div>2</div>
                      <div>2</div>
                    </td>
                  </tr>
                </table>
  
                <table class="center1_d">
                  <tr>
                    <td></td>
                    <td style="padding-left: 7px; padding-bottom: 5px">
                      纳编在位
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <img src="../../assets/images/zaiwei.png" alt />
                    </td>
                    <td>
                      <div>4</div>
                      <div>9</div>
                      <div>6</div>
                    </td>
                  </tr>
                </table>
  
                <table class="center1_d">
                  <tr>
                    <td></td>
                    <td style="padding-left: 7px; padding-bottom: 5px">
                      纳编外出
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <img src="../../assets/images/waichu.png" alt />
                    </td>
                    <td>
                      <div>0</div>
                      <div>7</div>
                      <div>0</div>
                    </td>
                  </tr>
                </table>
  
                <table class="center1_d">
                  <tr>
                    <td></td>
                    <td style="padding-left: 7px; padding-bottom: 5px">
                      未纳编在位
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <img src="../../assets/images/zaiwei.png" alt />
                    </td>
                    <td>
                      <div>0</div>
                      <div>2</div>
                      <div>6</div>
                    </td>
                  </tr>
                </table>
  
                <table class="center1_d">
                  <tr>
                    <td></td>
                    <td style="padding-left: 7px; padding-bottom: 5px">
                      未纳编外出
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <img src="../../assets/images/waichu.png" alt />
                    </td>
                    <td>
                      <div>0</div>
                      <div>0</div>
                      <div>5</div>
                    </td>
                  </tr>
                </table>
              </div>
            </div>
            <div class="center1-c">
              <div class="center2">
                <div class="left1-head">
                  <span>各单位人员在位情况</span>
                </div>
                <div id="center2_d"></div>
              </div>
              <div class="center3">
                <div class="left1-head">
                  <span>外出人员对比</span>
                </div>
                <div id="center3_d"></div>
              </div>
            </div>
            <!-- <div class="center4">
              <div class="left1-head">
                <span style="margin-left: 55px">人员请销假</span>
              </div>
              <div id="center4_d">
                <div class="right2_d" style="margin-left: 3%">
                  今日迟到
                  <span>0</span>
                </div>
                <div class="right2_d">
                  本月迟到
                  <span>0</span>
                </div>
                <div class="right2_d">
                  今日应归队
                  <span>0</span>
                </div>
                <div class="right2_b">
                  超时归队
                  <span>0</span>
                </div>
                <div class="right2_b">
                  未归队
                  <span>50</span>
                </div>
              </div>
            </div>-->
          </div>
          <div class="center-a">
            <img src="../../assets/images/右上角.png" alt class="left1-jrt" />
            <img src="../../assets/images/右下角.png" alt class="left1-jrb" />
            <img src="../../assets/images/左上角.png" alt class="left1-jlt" />
            <img src="../../assets/images/左下角.png" alt class="left1-jlb" />
            <div class="left1-head">
              <span>检查督导统计</span>
            </div>
            <div id="center-a-main"></div>
          </div>
          <div class="center-b">
            <img src="../../assets/images/右上角.png" alt class="left1-jrt" />
            <img src="../../assets/images/右下角.png" alt class="left1-jrb" />
            <img src="../../assets/images/左上角.png" alt class="left1-jlt" />
            <img src="../../assets/images/左下角.png" alt class="left1-jlb" />
            <div class="center-b-l">
              <div class="left1-head">
                <span>营房统计</span>
              </div>
              <div id="center-b-l-main">
                <table class="center4_d-1" style="margin-top: 6%">
                  <tr>
                    <td rowspan="2">
                      <img src="../../assets/images/yinfangz.png" alt />
                    </td>
                    <td
                      style="
                        color: #30fdff;
                        font-family: 'IMPACT';
                        font-size: 28px;
                      "
                    ></td>
                  </tr>
                  <tr>
                    <td
                      style="
                        font-family: 'IMPACT';
                        font-size: 2rem;
                        color: #30fdff;
                        display: block;
                      "
                    >
                      15
                    </td>
                    <td style="font-size: 20px; color: white; display: block">
                      营房统计
                    </td>
                  </tr>
                </table>
  
                <table class="center4_d-1" style="margin-top: 6%">
                  <tr>
                    <td rowspan="2">
                      <img src="../../assets/images/yinfangs.png" alt />
                    </td>
                    <td
                      style="
                        color: #30fdff;
                        font-family: 'IMPACT';
                        font-size: 28px;
                      "
                    ></td>
                  </tr>
                  <tr>
                    <td
                      style="
                        font-family: 'IMPACT';
                        font-size: 2rem;
                        color: #e6923e;
                        display: block;
                      "
                    >
                      0
                    </td>
                    <td style="font-size: 20px; color: white; display: block">
                      营房使用
                    </td>
                  </tr>
                </table>
              </div>
            </div>
            <div class="center-b-r">
              <div class="left1-head">
                <span>营具统计</span>
              </div>
              <div id="center-b-r-main">
                <table class="center4_d-1" style="margin-top: 6%">
                  <tr>
                    <td rowspan="2">
                      <img src="../../assets/images/yinjuz.png" alt />
                    </td>
                    <td
                      style="
                        color: #e6923e;
                        font-family: 'IMPACT';
                        font-size: 22px;
                      "
                    ></td>
                  </tr>
                  <tr>
                    <td
                      style="
                        font-family: 'IMPACT';
                        font-size: 2rem;
                        color: #30fdff;
                        display: block;
                      "
                    >
                      481
                    </td>
                    <td style="font-size: 20px; color: white; display: block">
                      营具统计
                    </td>
                  </tr>
                </table>
  
                <table class="center4_d-1" style="margin-top: 6%">
                  <tr>
                    <td rowspan="2">
                      <img src="../../assets/images/yinjus.png" alt />
                    </td>
                    <td
                      style="
                        color: #e6923e;
                        font-family: 'IMPACT';
                        font-size: 28px;
                      "
                    ></td>
                  </tr>
                  <tr>
                    <td
                      style="
                        font-family: 'IMPACT';
                        font-size: 2rem;
                        color: #e6923e;
                        display: block;
                      "
                    >
                      4
                    </td>
                    <td style="font-size: 20px; color: white; display: block">
                      营具使用
                    </td>
                  </tr>
                </table>
              </div>
            </div>
          </div>
        </div>
        <div class="right">
          <div class="right1">
            <img src="../../assets/images/右上角.png" alt class="left1-jrt" />
            <img src="../../assets/images/右下角.png" alt class="left1-jrb" />
            <img src="../../assets/images/左上角.png" alt class="left1-jlt" />
            <img src="../../assets/images/左下角.png" alt class="left1-jlb" />
            <div class="left1-head">
              <span>登记落实</span>
            </div>
            <div
              style="
                color: #fff;
                font-size: 12px;
                display: flex;
                position: absolute;
                right: 10px;
                top: 10px;
              "
            >
              <span
                style="
                  width: 5px;
                  height: 10px;
                  background-color: #f56c6c;
                  display: inline-block;
                  margin-right: 5px;
                  margin-top: 3.5px;
                "
              ></span
              >未登记
              <span
                style="
                  width: 5px;
                  height: 10px;
                  background-color: #67c23a;
                  display: inline-block;
                  margin-right: 5px;
                  margin-left: 10px;
                  margin-top: 3.5px;
                "
              ></span
              >已登记
            </div>
            <div class="right1-main">
              <div class="right1-main-title">
                <p>
                  <span style="width: 32%">落实单位</span>
                  <span>要事日记</span>
                  <span>值班登记</span>
                  <span>工作安排</span>
                  <span>查铺查岗</span>
                </p>
              </div>
              <vueSeamlessScroll
                :data="djlslist"
                class="seamless-warp"
                :class-option="defaultOption"
              >
                <div
                  class="right1-main-box"
                  v-for="(item, index) in djlslist"
                  :key="index"
                >
                  <span style="color: #fff; font-size: 12px; width: 32%">{{
                    item.unitname
                  }}</span>
                  <span>
                    <!-- <svg
                      t="1638612550724"
                      class="icon2"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="3323"
                      width="20"
                      height="10"
                    >
                      <path
                        d="M920 789H104l-40-40V275l40-40h816l40 40v474l-40 40z"
                        p-id="3324"
                        fill="#d03c2a"
                      />
                    </svg>-->
                    <svg
                      t="1638612550724"
                      class="icon3"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="3323"
                      width="20"
                      height="10"
                    >
                      <path
                        d="M920 789H104l-40-40V275l40-40h816l40 40v474l-40 40z"
                        p-id="3324"
                        fill="#67c23a"
                      />
                    </svg>
                  </span>
                  <span>
                    <!-- <svg
                      t="1638612550724"
                      class="icon2"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="3323"
                      width="20"
                      height="10"
                    >
                      <path
                        d="M920 789H104l-40-40V275l40-40h816l40 40v474l-40 40z"
                        p-id="3324"
                        fill="#d03c2a"
                      />
                    </svg>-->
                    <svg
                      t="1638612550724"
                      class="icon3"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="3323"
                      width="20"
                      height="10"
                    >
                      <path
                        d="M920 789H104l-40-40V275l40-40h816l40 40v474l-40 40z"
                        p-id="3324"
                        fill="#67c23a"
                      />
                    </svg>
                  </span>
                  <span>
                    <!-- <svg
                      t="1638612550724"
                      class="icon2"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="3323"
                      width="20"
                      height="10"
                    >
                      <path
                        d="M920 789H104l-40-40V275l40-40h816l40 40v474l-40 40z"
                        p-id="3324"
                        fill="#d03c2a"
                      />
                    </svg>-->
                    <svg
                      t="1638612550724"
                      class="icon3"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="3323"
                      width="20"
                      height="10"
                    >
                      <path
                        d="M920 789H104l-40-40V275l40-40h816l40 40v474l-40 40z"
                        p-id="3324"
                        fill="#67c23a"
                      />
                    </svg>
                  </span>
                  <span>
                    <!-- <svg
                      t="1638612550724"
                      class="icon2"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="3323"
                      width="20"
                      height="10"
                    >
                      <path
                        d="M920 789H104l-40-40V275l40-40h816l40 40v474l-40 40z"
                        p-id="3324"
                        fill="#d03c2a"
                      />
                    </svg>-->
                    <svg
                      t="1638612550724"
                      class="icon3"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="3323"
                      width="20"
                      height="10"
                    >
                      <path
                        d="M920 789H104l-40-40V275l40-40h816l40 40v474l-40 40z"
                        p-id="3324"
                        fill="#67c23a"
                      />
                    </svg>
                  </span>
                </div>
              </vueSeamlessScroll>
            </div>
          </div>
          <div class="right2">
            <img src="../../assets/images/右上角.png" alt class="left1-jrt" />
            <img src="../../assets/images/右下角.png" alt class="left1-jrb" />
            <img src="../../assets/images/左上角.png" alt class="left1-jlt" />
            <img src="../../assets/images/左下角.png" alt class="left1-jlb" />
            <div class="left1-head">
              <span>物资储备情况</span>
            </div>
            <!-- <div class="right2-main">
              <dv-scroll-board
                style="width: 99%; margin-left: 0.5%; font-size: 12px"
                :config="config"
                ref="scrollBoard"
              />
            </div>-->
            <div class="right1-main">
              <div class="right2-main-title">
                <p>
                  <span>序号</span>
                  <span>名称</span>
                  <span>数量</span>
                </p>
              </div>
              <vueSeamlessScroll
                :data="fylist"
                class="seamless-warp"
                :class-option="defaultOption2"
              >
                <div
                  class="right2-main-box"
                  v-for="(item, index) in fylist"
                  :key="index"
                >
                  <span style="color: #fff; font-size: 12px">{{
                    index + 1
                  }}</span>
                  <span>{{ item.durgName }}</span>
                  <span>{{ item.amount }}</span>
                </div>
              </vueSeamlessScroll>
            </div>
          </div>
          <div class="right3">
            <img src="../../assets/images/右上角.png" alt class="left1-jrt" />
            <img src="../../assets/images/右下角.png" alt class="left1-jrb" />
            <img src="../../assets/images/左上角.png" alt class="left1-jlt" />
            <img src="../../assets/images/左下角.png" alt class="left1-jlb" />
            <div class="right3-l">
              <div class="left1-head2">
                <span>集中文印(每月)</span>
              </div>
              <div class="right3-l-main">
                <div class="right3-r-main-item">
                  <div class="right3-r-main-item-l">
                    <img src=".././../assets/images/打印icon.png" alt />
                  </div>
                  <div class="right3-r-main-item-r">
                    <div class="s1">总打印数量</div>
                    <div class="s2"><b style="color: #30fdff">1316</b>份</div>
                  </div>
                </div>
                <div class="right3-r-main-item">
                  <div class="right3-r-main-item-l">
                    <img src=".././../assets/images/归还2.png" alt />
                  </div>
                  <div class="right3-r-main-item-r">
                    <div class="s1">归还数量</div>
                    <div class="s2"><b style="color: #2bf1b2">22</b>份</div>
                  </div>
                </div>
                <div class="right3-r-main-item">
                  <div class="right3-r-main-item-l">
                    <img src=".././../assets/images/归还3.png" alt />
                  </div>
                  <div class="right3-r-main-item-r">
                    <div class="s1">未归还数量</div>
                    <div class="s2"><b style="color: #ff5a5a">719</b>份</div>
                  </div>
                </div>
                <div class="right3-r-main-item">
                  <div class="right3-r-main-item-l">
                    <img src=".././../assets/images/归还4.png" alt />
                  </div>
                  <div class="right3-r-main-item-r">
                    <div class="s1">无需归还数量</div>
                    <div class="s2"><b style="color: #738c97">575</b>份</div>
                  </div>
                </div>
              </div>
            </div>
            <div class="right3-r">
              <div class="left1-head2">
                <span>涉密载体外借统计</span>
              </div>
              <div id="right3-r-main">
                <div class="right3-r-main-item">
                  <div class="right3-r-main-item-l">
                    <img src=".././../assets/images/移动载体icon.png" alt />
                  </div>
                  <div class="right3-r-main-item-r">
                    <div class="s1">移动载体</div>
                    <div class="s2"><b style="color: #30fdff">0</b>台</div>
                  </div>
                </div>
                <div class="right3-r-main-item">
                  <div class="right3-r-main-item-l">
                    <img src=".././../assets/images/手机icon.png" alt />
                  </div>
                  <div class="right3-r-main-item-r">
                    <div class="s1">军用手机</div>
                    <div class="s2"><b style="color: #2bf1b2">0</b>台</div>
                  </div>
                </div>
                <div class="right3-r-main-item">
                  <div class="right3-r-main-item-l">
                    <img src=".././../assets/images/电脑icon.png" alt />
                  </div>
                  <div class="right3-r-main-item-r">
                    <div class="s1">电脑</div>
                    <div class="s2"><b style="color: #30aaff">16</b>台</div>
                  </div>
                </div>
                <div class="right3-r-main-item">
                  <div class="right3-r-main-item-l">
                    <img src=".././../assets/images/相机icon.png" alt />
                  </div>
                  <div class="right3-r-main-item-r">
                    <div class="s1">相机</div>
                    <div class="s2"><b style="color: #9d83ff">0</b>台</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import {
    zaiweipeople,
    gedanweizaiweiqingkaung,
    waichurenyuanzhanbi,
    jinrizhiban,
    tongjiluoshi,
    rizongwenyin,
    getNumber,
    juncheguanli,
    getJieCha,
    getantiepidemic,
    antiepidemicArchivesList,
    getLeavlNumber,
    zongjinrizhiban,
  } from "@/api/taishi/taishi";
  import * as echarts from "echarts";
  import vueSeamlessScroll from "vue-seamless-scroll";
  import Cookies from "js-cookie";
  export default {
    name: "TaiShi",
    data() {
      return {
        n: 8,
        //大队人员在位数据
        zwlist: [],
        //各单位在位情况
        zwqklist: [],
        //今日值班
        jrzblist: [],
        //登记落实
        djlslist: [
          { name: "发射一营", a: 0, b: 1, c: 1, d: 0 },
          { name: "发射二营", a: 0, b: 1, c: 1, d: 0 },
          { name: "发射三营", a: 0, b: 1, c: 1, d: 0 },
          { name: "发射四营", a: 0, b: 1, c: 1, d: 0 },
        ],
        jclist: [],
        //营具
        yingjulist: [],
        //请假
        qjlist: [],
        //防疫
        fylist: [
          { durgName: "医疗垃圾桶", amount: 6 },
          { durgName: "紫外线消毒灯", amount: 9 },
          { durgName: "医用外科口罩", amount: 18000 },
          { durgName: "汇艺缘一次性防护服(大)", amount: 100 },
          { durgName: "汇艺缘一次性防护服(小)", amount: 200 },
          { durgName: "BJSY橡胶手套", amount: 600 },
          { durgName: "BJSY一次性鞋套", amount: 300 },
          { durgName: "沈虎 一次性隔离眼罩", amount: 50 },
          { durgName: "BJSY 75%酒精2.5L", amount: 70 },
          { durgName: "BJSY 病毒采样管", amount: 6 },
          { durgName: "电动喷雾器", amount: 0 },
          { durgName: "洗涤剂*泡腾片", amount: 0 },
          { durgName: "医疗外科口罩", amount: 54540 },
        ],
        //定时器
        timer: null,
        //外出人员占比
        wclist: [],
        wylist: [],
        smztlist: [],
        config: {
          header: ["防疫名称", "现有数量"],
          data: [],
          index: true,
          columnWidth: [],
          align: ["center", "center", "center", "center"],
          headerBGC: "",
          indexHeader: "序号",
          oddRowBGC: "",
          evenRowBGC: "",
          columnWidth: [100, 200, 200, 100],
        },
        config2: {
          header: ["人员", "份数", "日期"],
          data: [
            ["行1列1", "行1列2", "行1列3"],
            ["行2列1", "行2列2", "行2列3"],
            ["行3列1", "行3列2", "行3列3"],
            ["行4列1", "行4列2", "行4列3"],
            ["行5列1", "行5列2", "行5列3"],
            ["行6列1", "行6列2", "行6列3"],
            ["行7列1", "行7列2", "行7列3"],
            ["行8列1", "行8列2", "行8列3"],
            ["行9列1", "行9列2", "行9列3"],
            ["行10列1", "行10列2", "行10列3"],
          ],
          index: false,
          columnWidth: [],
          align: ["center", "center", "center"],
          headerBGC: "",
          indexHeader: "序号",
          oddRowBGC: "",
          evenRowBGC: "",
        },
        listdata: [
          { name: "发射一营", a: 0, b: 1, c: 1, d: 0 },
          { name: "发射二营", a: 0, b: 1, c: 1, d: 0 },
          { name: "发射三营", a: 0, b: 1, c: 1, d: 0 },
          { name: "发射四营", a: 0, b: 1, c: 1, d: 0 },
        ],
        timer2: null,
        times: null,
        jrzblist: [
          { name: "值班首长", value: "许国志" },
          { name: "作战值班员", value: "程远方" },
          { name: "通信值班员", value: "张家政" },
          { name: "机要值班员", value: "程远方" },
          { name: "政治工作部值班员", value: "林杰" },
          { name: "保障部值班员", value: "郭宣辉" },
          { name: "值班通信员", value: "李璐明" },
          { name: "值班总人数", value: "60" },
        ],
        // 检查督导
        jianCanName: [],
        jianCanScore: [],
      };
    },
    created() {
      // if (Cookies.get("shuaxin") != "true") {
      //   window.location.reload();
      // }
      // Cookies.set("shuaxin", "true");
      //大队人员在位数据
      // zaiweipeople().then(res => {
      //   if (res.code == 200) {
      //     // this.zwlist = res.data[0];
      //     let arr = res.data[0];
      //     for (var i in arr) {
      //       this.zsz(arr, i);
      //     }
      //   }
      // });
      //今日值班
      // jinrizhiban().then(res => {
      //   if (res.data != null) {
      //     this.jrzblist = [];
      //     for (var i in res.data) {
      //       if (i == "chiefDutyName") {
      //         this.jrzblist.push({ name: "值班首长", value: res.data[i] });
      //       } else if (i == "combatDutyName") {
      //         this.jrzblist.push({ name: "作战值班员", value: res.data[i] });
      //       } else if (i == "dutyOffierName") {
      //         this.jrzblist.push({ name: "通信值班员", value: res.data[i] });
      //       } else if (i == "confidentialName") {
      //         this.jrzblist.push({ name: "机要值班员", value: res.data[i] });
      //       } else if (i == "politicalName") {
      //         this.jrzblist.push({
      //           name: "政治工作部值班员",
      //           value: res.data[i]
      //         });
      //       } else if (i == "guaranteeName") {
      //         this.jrzblist.push({
      //           name: "保障部值班员",
      //           value: res.data[i]
      //         });
      //       } else if (i == "messengerName") {
      //         this.jrzblist.push({
      //           name: "值班通信员",
      //           value: res.data[i]
      //         });
      //       } else if (i == "detachmentName") {
      //         zongjinrizhiban().then(res => {
      //           this.jrzblist.push({ name: "值班总人数", value: res.data[1] });
      //         });
      //       }
      //     }
      //   }
      // });
      //登记落实
      tongjiluoshi().then((res) => {
        this.djlslist = res.data;
      });
      //集中文印
      // rizongwenyin().then(res => {
      //   // console.log(res.data[0])
      //   this.wylist = res.data;
      //   if (this.wylist[1]) {
  
      //   } else {
      //     this.wylist[1] = 0;
      //   }
      //   if (this.wylist[0]) {
  
      //   } else {
      //     this.wylist[0] = 0;
      //   }
      //   // console.log(this.wylist)
      // });
      //军车
      // juncheguanli().then(res => {
      //   // console.log(res)
      //   for (var i in res.data) {
      //     if (res.data[i] >= 0) {
      //       res.data[i];
      //     } else {
      //       res.data[i] = 0;
      //     }
      //   }
      //   this.jclist = res.data;
      // });
      //涉密移动载体
      // getNumber().then(res => {
      //   this.smztlist = res.data;
      // });
      //营具
      // getantiepidemic().then(res => {
      //   this.yingjulist = res.data;
      // });
      //防疫
      // antiepidemicArchivesList().then(res => {
      //   this.fylist = res.data;
      // });
      //人员请销假
      // getLeavlNumber().then(res => {
      //   // console.log(res.data)
      //   for (var i in res.data) {
      //     if (res.data[i] >= 0) {
      //       res.data[i];
      //     } else {
      //       res.data[i] = 0;
      //     }
      //   }
      //   this.qjlist = res.data;
      // });
      this.startTime();
    },
    mounted() {
      this.$forceUpdate();
      //各单位在位情况
      this.center2();
      //外出人员占比
      waichurenyuanzhanbi().then((res) => {
        this.wclist = res.data;
        this.center3();
      });
      this.center4();
    },
    methods: {
      titlefun() {
        this.$router.push({ path: "/taishi", query: { name: "taishi" } });
      },
      zsz(obj, n) {
        var s = (obj[n] + "").split("");
        if (s.length == 1) {
          s.unshift(0, 0);
        } else if (s.length == 2) {
          s.unshift(0);
        }
        this.zwlist[n] = s;
      },
      center2() {
        let myChartv = echarts.init(
          document.getElementById("center2_d"),
          "vintage"
        );
        var s = 4;
        var z = -1;
  
        var className = [
          "参谋部",
          "政治工作部",
          "保障部",
          "技术室",
          "发射一营",
          "发射二营",
          "测试营",
          "测控营",
          "信息系统营",
          "阵地管理营",
          "支援保障营",
        ];
        var datav = [11, 11, 10, 19, 64, 69, 61, 50, 57, 50, 90];
        // this.zwqklist.map(item => {
        //   for (var i in item) {
        //     className.push(i);
        //     datav.push(item[i]);
        //   }
        // });
  
        function c() {
          if (s == datav.length - 1) {
            s = 4;
            z = 0;
          } else {
            s += 1;
            z += 1;
          }
  
          var data2 = datav.slice(z, s + 1);
          var data = className.slice(z, s + 1);
          // console.log(data2)
          const colorList9 = [
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
            "#39B3FF",
          ];
          const option4 = {
            // dataZoom: [
            //   //给x轴设置滚动条
            //   {
            //     type: "slider",
            //     width: 10,
            //     // handleHeight:'30%',
            //     show: true,
            //     yAxisIndex: [0],
            //     showDetail: false,
            //     left: "95%",
            //     handleColor: "#39B3FF",
            //     startValue: z, //数据窗口范围的起始百分比
            //     endValue: s
            //   },
            //   {
            //     type: "inside",
            //     yAxisIndex: [0],
            //     startValue: z, //数据窗口范围的起始百分比
            //     endValue: s
            //   }
            // ],
            grid: {
              left: 10,
              right: 20,
              bottom: 5,
              top: -15,
              containLabel: true,
            },
            tooltip: {
              trigger: "axis",
              axisPointer: {
                type: "none",
              },
              formatter: function (params) {
                return (
                  params[0].name +
                  "<br/>" +
                  "<span style='display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:'></span>" +
                  // params[0].seriesName + ' : ' + Number((params[0].value.toFixed(4) / 10000).toFixed(2)).toLocaleString() + ' <br/>'
                  params[0].seriesName +
                  " : " +
                  params[0].value
                );
              },
            },
            xAxis: {
              position: "top",
              show: false,
              type: "value",
              splitLine: {
                show: false,
              },
              axisLabel: {
                show: true,
                textStyle: {
                  color: "#fff", //更改坐标轴文字颜色
                },
              },
            },
            yAxis: [
              {
                type: "category",
                inverse: true,
                axisLabel: {
                  show: true,
                  textStyle: {
                    color: "#fff",
                  },
                },
                splitLine: {
                  show: false,
                },
                axisTick: {
                  show: false,
                },
                axisLine: {
                  show: false,
                },
                data: data,
              },
              {
                type: "category",
                inverse: true,
                axisTick: "none",
                axisLine: "none",
                show: true,
                axisLabel: {
                  textStyle: {
                    color: "#ffffff",
                    fontSize: "12",
                  },
                  // formatter: function(datav) {
                  //   return datav;
                  // }
                },
                data: data2,
              },
            ],
            series: [
              {
                name: "在位率",
                type: "bar",
                zlevel: 1,
                showBackground: true,
                itemStyle: {
                  normal: {
                    barBorderRadius: 0,
                    color: (params) => {
                      return colorList9[params.dataIndex];
                    },
                  },
                },
                barWidth: 15,
                data: data2,
              },
            ],
          };
          myChartv.setOption(option4);
          window.addEventListener("resize", function () {
            myChartv.resize();
          });
        }
        c();
        this.timer = setInterval(() => {
          c();
        }, 5000);
      },
      center3() {
        const myChartss = echarts.init(
          document.getElementById("center3_d"),
          "vintage"
        );
        var data1 = [0, 7, 0, 22, 41];
        var data = ["请假", "学习", "特殊类型", "出差", "休假"];
        var data2 = [];
        // console.log(this.wclist);
        // for (var i in this.wclist) {
        //   data.push(i);
        //   for (var e in this.wclist[i][0]) {
        //     if (e == "renshu") {
        //       data1.push(this.wclist[i][0][e]);
        //     } else if (e == "zhanbi") {
        //       data2.push(this.wclist[i][0][e]);
        //     }
        //   }
        // }
        var n = 0;
        data1.map((item) => {
          n += item;
        });
        // data1=[50,10,60,90,10];
        // console.log(n)
        const path = "path://M1390,595h79l-39,22Z";
        const myColor = ["#fff", "#fff", "#fff", "#fff", "#fff"];
        const option12 = {
          grid: {
            left: 40,
            bottom: "20%",
            top: 20,
            right: 30,
          },
          xAxis: {
            axisLabel: {
              padding: 15,
              interval: 0,
              textStyle: {
                color: function (param, index) {
                  return myColor[index];
                },
                fontSize: 12,
                fontWeight: 100,
              },
              margin: 10,
            },
            splitLine: {
              show: false,
            },
            axisLine: {
              show: false,
            },
            splitArea: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            data: data,
          },
          yAxis: {
            max: n,
            min: 0,
  
            axisLine: {
              lineStyle: {
                color: "#6e6e6e",
                width: 1,
              },
            },
            axisLabel: {
              interval: 0,
              formatter: "{value}",
              textStyle: {
                fontSize: 12,
                color: "#fff",
              },
              margin: 20,
            },
            splitLine: {
              show: true,
            },
            axisTick: {
              show: true,
              lineStyle: {
                color: "#fff",
                width: 1,
                height: 5,
              },
            },
          },
          series: [
            {
              type: "bar",
              barWidth: 30,
              barGap: "-100%",
              stack: "广告",
              fontSize: 12,
              itemStyle: {
                fontSize: 12,
                color: function (params) {
                  const colorList = [
                    new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                      {
                        offset: 0,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 1,
                        color: "#39b3ff",
                      },
                    ]),
                    new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                      {
                        offset: 0,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 1,
                        color: "#39b3ff",
                      },
                    ]),
                    new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                      {
                        offset: 0,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 1,
                        color: "#39b3ff",
                      },
                    ]),
                    new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                      {
                        offset: 0,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 1,
                        color: "#39b3ff",
                      },
                    ]),
                    new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                      {
                        offset: 0,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 1,
                        color: "#39b3ff",
                      },
                    ]),
                  ];
                  return colorList[params.dataIndex];
                },
              },
              data: data1,
            },
            {
              name: "",
              type: "pictorialBar",
              symbol: path,
              symbolSize: [60, 20],
              symbolOffset: [0, 0],
              z: 12,
              symbolPosition: "end",
              itemStyle: {
                color: "#e2e2e2",
                opacity: 0,
              },
              data: data1,
            },
            {
              name: "",
              type: "pictorialBar",
              symbol: path,
              symbolSize: [60, 20],
              symbolOffset: [0, 20],
              z: 12,
              itemStyle: {
                opacity: 1,
                color: function (params) {
                  const colorList = [
                    new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                      {
                        offset: 0,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 1,
                        color: "#39b3ff",
                      },
                    ]),
                    new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                      {
                        offset: 0,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 1,
                        color: "#39b3ff",
                      },
                    ]),
                    new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                      {
                        offset: 0,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 1,
                        color: "#39b3ff",
                      },
                    ]),
                    new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                      {
                        offset: 0,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 1,
                        color: "#39b3ff",
                      },
                    ]),
                    new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                      {
                        offset: 0,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 0.5,
                        color: "#39b3ff",
                      },
                      {
                        offset: 1,
                        color: "#39b3ff",
                      },
                    ]),
                  ];
                  return colorList[params.dataIndex];
                },
              },
              data: data1,
            },
            {
              name: "",
              type: "pictorialBar",
              symbol: path,
              symbolSize: [60, 20],
              symbolOffset: [0, 0],
              z: 12,
              label: {
                show: true,
                formatter: "{c}人",
                position: "top",
                distance: 5,
                color: "#fff",
                fontWeight: 100,
                textShadowColor: "rgba(255, 255, 255, .6)",
                fontSize: 16,
                textShadowBlur: "0",
                textShadowOffsetX: 1,
                textShadowOffsetY: 1,
              },
              itemStyle: {
                opacity: 1,
                color: function (params) {
                  const colorList = [
                    "#39b3ff",
                    "#39b3ff",
                    "#39b3ff",
                    "#39b3ff",
                    "#39b3ff",
                  ];
                  return colorList[params.dataIndex];
                },
              },
              symbolPosition: "end",
              data: data1,
            },
            {
              name: "",
              type: "effectScatter",
              rippleEffect: {
                period: 1,
                scale: 5,
                brushType: "fill",
              },
              z: 20,
              symbolPosition: "end",
              symbol: path,
              symbolSize: [15, 5],
              symbolOffset: [0, 9],
              itemStyle: {
                normal: {
                  color: "#fff",
                },
              },
              data: data1,
            },
          ],
        };
        const backImg = "/asset/get/s/data-1622796959507-HTopachtJ.png";
        myChartss._dom.style.backgroundImage = "url('" + backImg + "')";
        myChartss.setOption(option12);
        window.addEventListener("resize", function () {
          myChartss.resize();
        });
      },
  
      async center4() {
        var myChart = echarts.init(document.getElementById("center-a-main"));
        var jc = null;
        // 检查督导
        await getJieCha().then((res) => {
          for (var index = 0; index < res.data.score.length; index++) {
            res.data.score[index] = res.data.score[index].slice(0, -3);
          }
          jc = res.data;
          // console.log(res.data);
        });
        var option = {
          color: ["#00CCFF"],
          tooltip: {
            color: "#00CCFF",
          },
          grid: {
            containLabel: true,
            left: "2%",
            top: 30,
            right: "3%",
            bottom: "3%",
          },
          xAxis: [
            {
              type: "category",
              data: [
                "技术室",
                "发射一营",
                "发射二营",
                "测试营",
                "测控营",
                "信息系统营",
                "阵地管理营",
                "支援保障营",
              ],
              axisTick: {
                alignWithLabel: true,
              },
              nameTextStyle: {
                color: "#fff",
              },
              axisLine: {
                lineStyle: {
                  color: "#fff",
                },
              },
              axisLabel: {
                textStyle: {
                  color: "#fff",
                },
              },
            },
          ],
          yAxis: [
            {
              type: "value",
              axisLabel: {
                textStyle: {
                  color: "#fff",
                },
                formatter: "{value}",
                color: "#00e4ff",
              },
              splitLine: {
                lineStyle: {
                  color: "#fff",
                  opacity: 0.3,
                },
              },
              axisLine: {
                show: false,
              },
            },
          ],
          series: [
            {
              name: "",
              type: "pictorialBar",
              symbolSize: [30, 10],
              symbolOffset: [0, -5],
              symbolPosition: "end",
              color: "#00e4ff",
              z: 12,
              label: {
                normal: {
                  show: true,
                  position: "top",
                  formatter: "{c}",
                  color: "#ff9000",
                },
              },
              data: [1000, 925, 930, 930, 980, 985, 920, 965],
            },
            {
              name: "",
              type: "pictorialBar",
              symbolSize: [30, 10],
              symbolOffset: [0, 5],
              z: 12,
              data: [1000, 925, 930, 930, 980, 985, 920, 965],
            },
            {
              type: "bar",
              itemStyle: {
                normal: {
                  color: "#00e4ff",
                  opacity: 0.7,
                },
              },
              barWidth: "30",
              data: [1000, 925, 930, 930, 980, 985, 920, 965],
              markLine: {
                silent: true,
                symbol: "none",
                label: {
                  position: "middle",
                  formatter: "{b}",
                },
              },
            },
            {
              type: "effectScatter",
              silent: true,
              tooltip: {
                show: false,
              },
              zlevel: 3,
              symbolSize: 10,
              showEffectOn: "render",
              rippleEffect: {
                brushType: "stroke",
                color: "#00e4ff",
                scale: 5,
              },
              itemStyle: {
                color: "#00e4ff",
              },
              hoverAnimation: true,
              data: [0, 0, 0, 0, 0, 0, 0],
            },
          ],
        };
        myChart.setOption(option);
        window.addEventListener("resize", function () {
          myChart.resize();
        });
      },
      startTime() {
        this.timer2 = setInterval(() => {
          var today = new Date();
          var y = today.getFullYear();
          var M = today.getMonth() + 1;
          var d = today.getDate();
          var h = today.getHours();
          var m = today.getMinutes();
          var s = today.getSeconds();
          var w = today.getDay();
          if (M < 10) {
            M = "0" + M;
          }
          if (d < 10) {
            d = "0" + d;
          }
          if (h < 10) {
            h = "0" + h;
          }
          if (m < 10) {
            m = "0" + m;
          }
          if (s < 10) {
            s = "0" + s;
          }
          this.times = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
        }, 500); /* 每500毫秒执行一次，实现动态显示时间效果 */
      },
    },
    computed: {
      defaultOption() {
        return {
          step: 0.2, // 数值越大速度滚动越快
          limitMoveNum: 6, // 开始无缝滚动的数据量 this.dataList.length
          hoverStop: true, // 是否开启鼠标悬停stop
          direction: 1, // 0向下 1向上 2向左 3向右
          openWatch: true, // 开启数据实时监控刷新dom
          singleHeight: 0, // 单步运动停止的高度(默认值0是无缝不停止的滚动) direction => 0/1
          singleWidth: 0, // 单步运动停止的宽度(默认值0是无缝不停止的滚动) direction => 2/3
          waitTime: 1000, // 单步运动停止的时间(默认值1000ms)
        };
      },
      defaultOption2() {
        return {
          step: 0.2, // 数值越大速度滚动越快
          limitMoveNum: 6, // 开始无缝滚动的数据量 this.dataList.length
          hoverStop: true, // 是否开启鼠标悬停stop
          direction: 1, // 0向下 1向上 2向左 3向右
          openWatch: true, // 开启数据实时监控刷新dom
          singleHeight: 0, // 单步运动停止的高度(默认值0是无缝不停止的滚动) direction => 0/1
          singleWidth: 0, // 单步运动停止的宽度(默认值0是无缝不停止的滚动) direction => 2/3
          waitTime: 1000, // 单步运动停止的时间(默认值1000ms)
        };
      },
    },
    destroyed() {
      clearInterval(this.timer); // 清除定时器
      this.timer = null;
      clearInterval(this.timer2); // 清除定时器
      this.timer2 = null;
    },
    components: {
      vueSeamlessScroll,
    },
  };
  </script>
  
  <style scoped>
  .out {
    background-image: url("../../assets/images/tsbj1.png");
    background-size: 100% 100%;
    height: 100%;
    width: 100%;
    /* padding-top: -4vh; */
    background-repeat: no-repeat;
    overflow: auto;
    /* min-width: 1200px; */
    overflow-x: hidden;
    overflow-y: hidden;
    z-index: -1;
    display: flex;
    flex-direction: column;
  }
  .title {
    width: 100%;
    height: 87px;
    background-image: url("../../assets/images/态势分析顶部.gif");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    position: relative;
  }
  .title-left {
    position: absolute;
    left: 115px;
    top: 12px;
    width: 179px;
    height: 51px;
    background-image: url("../../assets/images/主控台按钮底.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    text-align: center;
    line-height: 51px;
    color: #fff;
    font-size: 1.2vw;
    font-weight: bold;
    cursor: pointer;
    opacity: 0.8;
  }
  .title-left img {
    margin-right: 10px;
  }
  .title-right {
    position: absolute;
    color: #00f6ff;
    font-size: 1.2vw;
    right: 100px;
    top: 36px;
  }
  .main {
    flex: 1;
    width: 100%;
    margin: 0;
    padding: 0;
    display: flex;
  }
  .left {
    width: 25%;
    height: 100%;
    margin: 0;
    padding: 0;
    margin-left: 15px;
  }
  .center {
    width: 48%;
    height: 100%;
    margin: 0 10px;
  }
  .right {
    width: 25%;
    height: 100%;
    margin-right: 15px;
  }
  .left1 {
    width: 100%;
    height: 28vh;
    background-image: url("../../assets/images/底.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
    position: relative;
  }
  .left1-jrt {
    position: absolute;
    right: 0;
    top: 0;
  }
  .left1-jlt {
    position: absolute;
    left: 0;
    top: 0;
  }
  .left1-jrb {
    position: absolute;
    right: 0;
    bottom: 0;
  }
  .left1-jlb {
    position: absolute;
    left: 0;
    bottom: 0;
  }
  .left1-head {
    width: 20vw;
    height: 25px;
    background-image: url("../../assets/images/dtitle.png");
    background-repeat: no-repeat;
    background-size: 100%;
    margin-top: 5px;
    margin-left: 2%;
  }
  .left1-head span {
    margin-left: 8%;
    line-height: 23px;
    color: #fff;
    font-size: 0.8rem;
  }
  .left1-head2 {
    height: 25px;
    background-image: url("../../assets/images/集中文印标题底.png");
    background-repeat: no-repeat;
    background-size: 100%;
    margin-top: 5px;
    margin-left: 2%;
  }
  .left1-head2 span {
    margin-left: 15%;
    line-height: 25px;
    color: #fff;
    font-size: 0.8rem;
  }
  .left1-wrap {
    width: 96%;
    height: 80%;
    margin-left: 2%;
  }
  .left1-wrap-item {
    width: 46%;
    height: 4.5vh;
    border: 1px solid #033a5d;
    display: inline-block;
    margin-right: 2%;
    margin-left: 2%;
    margin-top: 2%;
  }
  .left1-wrap-item span {
    font-size: 1rem;
    line-height: 4.5vh;
    color: #fff;
    margin-left: 3%;
  }
  .left1-wrap-item b {
    font-size: 0.9rem;
    color: #30fdff;
  }
  .center1 {
    width: 100%;
    height: 40vh;
    max-width: 100%;
    max-height: 40vh;
    background-image: url("../../assets/images/底 拷贝 3.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    margin-top: 10px;
    padding-top: 5px;
    position: relative;
  }
  .left2 {
    width: 100%;
    height: 29vh;
    background-image: url("../../assets/images/底.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
    position: relative;
  }
  .left2-1 {
    width: 100%;
    height: 20%;
  }
  .left2-2 {
    width: 100%;
    height: 80%;
  }
  .left2-1-main {
    width: 100%;
    height: 85%;
  }
  .left2_d {
    margin-top: 1vh;
    font-size: 14px;
    color: white;
    width: 33.3%;
    text-align: center;
    display: inline-block;
    margin-bottom: 1vh;
  }
  
  .left2_d span {
    margin-left: 0.5vw;
    font-family: "IMPACT";
    font-size: 1.4rem;
    color: #30fdff;
  }
  .center4_b {
    /* margin-top: 0.8vh;
    margin-left: 2%;
    color: white;
    font-size: 1rem;
    background-color: rgba(3, 58, 93, 0.3);
    border: 0.1px solid #052c41;
    height: 4vh;
    width: 35%;
    line-height: 4vh;
    text-align: center; */
    margin-top: 0.8vh;
    margin-left: 2%;
    color: white;
    font-size: 1rem;
    background-color: rgba(3, 58, 93, 0.3);
    border: 0.1px solid #052c41;
    height: 4vh;
    width: 46%;
    line-height: 4vh;
    /* text-align: center; */
    position: relative;
    top: -85%;
    right: -50%;
    padding-left: 10%;
  }
  
  .center4_b span {
    font-family: "IMPACT";
    font-size: 1.6rem;
    /* margin-left: 20px; */
  }
  .center4_s {
    /* height: 6.3vh;
    line-height: 2vh;
    position: relative;
    margin-top: -0.5vh;
    margin-left: 4px;
    margin-bottom: 5px; */
    height: 6.3vh;
    line-height: 2vh;
    /* position: relative; */
    margin-top: -0.5vh;
    margin-left: 30px;
    /* line-height: 50px; */
    margin-bottom: 5px;
    margin-top: 10px;
  }
  .center4_s img {
    width: 45px;
    height: 45px;
    margin-right: 10px;
  }
  .center4_y {
    color: white;
    font-size: 12px;
    position: absolute;
    top: 12%;
    left: 75%;
    height: 8.2vh;
    margin-top: -1vh;
    line-height: 2.2vh;
    margin-bottom: 20px;
  }
  .center4_y2 {
    position: absolute;
    top: 57%;
    left: 75%;
    height: 9vh;
    line-height: 1.9vh;
    margin-bottom: 20px;
  }
  .center4_y img {
    width: 50px;
  }
  .left2-2-main {
    width: 100%;
    height: 85%;
  }
  .left2-2-main-title {
    width: 100%;
    height: 15%;
    display: flex;
    flex-direction: row;
  }
  .left2-2-main-title .center4_l-1 {
    flex: 1;
    height: 100%;
    display: inline-block;
    color: white;
    font-size: 12px;
    margin-top: 0.9vh;
  }
  .left2-2-main-title .center4_l-1 div {
    display: inline-block;
    margin-right: 8px;
    box-sizing: content-box;
  }
  .center4_l {
    color: white;
    font-size: 12px;
    margin-top: 0.9vh;
    margin-left: 0.3vw;
    display: inline-block;
    margin-right: 80px;
  }
  
  .center4_l div {
    display: inline-block;
    margin-right: 8px;
    box-sizing: content-box;
  }
  .left2-2-main-main {
    width: 100%;
    height: 85%;
    display: flex;
    flex-direction: row;
    margin: 0;
    padding: 0;
  }
  .time {
    /* margin-top: 3px; */
    flex: 1;
    font-size: 16px;
    color: white;
    text-align: center;
    transform: scale(0.9);
    overflow: hidden;
  }
  
  .time td {
    height: 3.7vh;
    width: 50%;
    background-color: #0c3361;
  }
  .people {
    /* margin-top: 3px; */
    flex: 1;
    font-size: 14px;
    color: white;
    text-align: center;
    transform: scale(0.9);
    margin: 0;
    padding: 0;
    margin-left: -5%;
  }
  
  .people td {
    height: 3.7vh;
    width: 50%;
    background-color: #0c3361;
  }
  
  .grade {
    /* margin-top: 3px; */
    flex: 1;
    font-size: 16px;
    color: white;
    text-align: center;
    transform: scale(0.9);
    margin-left: -3%;
  }
  
  .grade td {
    height: 3.7vh;
    width: 50%;
    background-color: #0c3361;
  }
  .left2-2 {
    width: 100%;
    height: 70%;
  }
  .left3 {
    width: 100%;
    height: 28vh;
    background-image: url("../../assets/images/底.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
    position: relative;
  }
  .left3-main {
    width: 100%;
    height: 85%;
    position: relative;
  }
  
  .center1_d {
    font-size: 12px;
    color: white;
    display: inline-block;
    margin-bottom: 10px;
    width: 20%;
  }
  
  .center1_d img {
    width: 40px;
    height: 40px;
  }
  
  .center1_d div {
    display: inline-block;
    font-size: 35px;
    font-family: "IMPACT";
    background-color: #0c3361;
    margin-left: 5px;
    width: 25px;
    text-align: center;
    color: #ff9000;
  }
  
  .center2 {
    width: 50%;
    height: 100%;
    display: inline-block;
  }
  
  #center2_d {
    width: 100%;
    height: 87%;
  }
  
  #center3_d {
    width: 100%;
    height: 87%;
  }
  
  .center3 {
    width: 50%;
    height: 100%;
    display: inline-block;
  }
  
  .center4 {
    width: 100%;
    height: 20%;
    display: flex;
    flex-direction: column;
  }
  
  #center4_d {
    width: 100%;
    flex: 1;
  }
  
  .center4_d-1 {
    display: inline-block;
    transform: scale(0.7);
    margin-left: 0.3vw;
    margin-top: -26px;
  }
  
  .center4_d-1 img {
    width: 70px;
    height: 70px;
    margin-right: 20px;
  }
  
  .center-a {
    width: 100%;
    height: 30vh;
    background-image: url("../../assets/images/底 拷贝 4.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    margin-top: 10px;
    padding-top: 5px;
    position: relative;
  }
  #center-a-main {
    width: 100%;
    height: 90%;
  }
  
  .center-b {
    width: 100%;
    height: 15vh;
    background-image: url("../../assets/images/底 拷贝 5.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    margin-top: 10px;
    display: flex;
    flex-direction: row;
    padding-top: 5px;
    position: relative;
  }
  .center-b-l {
    width: 48%;
    height: 100%;
    display: inline-block;
  }
  #center-b-l-main {
    width: 100%;
    height: 85%;
  }
  .center-b-r {
    width: 48%;
    height: 100%;
    display: inline-block;
  }
  #center-b-r-main {
    width: 100%;
    height: 85%;
  }
  
  .right2_d {
    color: white;
    font-size: 1rem;
    text-align: center;
    background-color: #014264;
    width: 18%;
    margin-left: 1%;
    height: 80%;
    /* display: flex;
    justify-content: center;
    align-items: center; */
    display: inline-block;
    line-height: 40px;
    margin-top: 0.5%;
  }
  
  .right2_d span {
    color: #30fdff;
    font-size: 1.5rem;
    margin-left: 10px;
    font-family: "IMPACT";
    line-height: 48px;
  }
  
  .right2_b {
    height: 80%;
    width: 18%;
    color: white;
    font-size: 1rem;
    text-align: center;
    background-color: #2c283a;
    /* margin-top: 0.7rem; */
    margin-left: 1%;
    display: inline-block;
    line-height: 40px;
    margin-top: 0.5%;
  }
  
  .right2_b span {
    color: #f0422a;
    font-size: 1.5rem;
    margin-left: 10px;
    font-family: "IMPACT";
    line-height: 48px;
  }
  
  .right1 {
    width: 100%;
    height: 28vh;
    background-image: url("../../assets/images/底.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
    position: relative;
  }
  .right1-main {
    width: 100%;
    height: 90%;
    margin: 0;
    padding: 0;
  }
  .right1-main-title {
    width: 100%;
    height: 30px;
    margin: 0;
    padding: 0;
    margin-top: -15px;
  }
  .right1-main-title p {
    width: 100%;
    height: 30px;
    list-style: none;
  }
  .right1-main-title p span {
    width: 17%;
    display: inline-block;
    text-align: center;
    line-height: 30px;
    font-size: 14px;
    color: #fff;
  }
  
  .right2-main-title {
    width: 100%;
    height: 30px;
    margin-top: -15px;
  }
  .right2-main-title p {
    width: 100%;
    height: 30px;
    list-style: none;
  }
  .right2-main-title p span {
    width: 33.3%;
    display: inline-block;
    text-align: center;
    line-height: 30px;
    font-size: 14px;
    color: #fff;
  }
  .right2 {
    width: 100%;
    height: 29vh;
    background-image: url("../../assets/images/底.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
    position: relative;
  }
  .right2-main {
    width: 100%;
    height: 85%;
  }
  .right3 {
    width: 100%;
    height: 28vh;
    background-image: url("../../assets/images/底.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
    position: relative;
  }
  .right3-l {
    width: 48%;
    height: 100%;
    display: inline-block;
  }
  .right3-l-main {
    width: 100%;
    height: 85%;
  }
  .right3-r {
    width: 48%;
    height: 100%;
    display: inline-block;
  }
  #right3-r-main {
    width: 100%;
    height: 85%;
  }
  .right3-r-main-item {
    width: 100%;
    height: 5vh;
    /* height: 20%; */
    display: flex;
    margin-top: 1vh;
  }
  .right3-r-main-item-l {
    margin-top: 1px;
    width: 30%;
    height: 100%;
    display: inline-block;
    text-align: center;
    align-items: center;
  }
  .right3-r-main-item-r {
    width: 68%;
    height: 100%;
    margin-left: 1%;
    background: rgba(48, 253, 255, 0.1);
    border: 2px solid rgba(48, 253, 255, 0.1);
    display: inline-block;
    color: #fff;
    display: flex;
  }
  .right3-r-main-item-r .s1 {
    width: 70%;
    color: #fff;
    font-size: 14px;
    display: flex;
    align-items: center;
    justify-content: left;
    margin-left: 3%;
  }
  .right3-r-main-item-r .s2 {
    width: 30%;
    font-size: 14px;
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: right;
    margin-right: 10px;
  }
  .right3-r-main-item-r .s2 b {
    font-size: 18px;
    color: #30fdff;
    margin-right: 6px;
  }
  @keyframes breathe {
    0% {
      opacity: 0.6;
      box-shadow: 0 1px 2px rgb(240 66 42 / 60%);
      transform: scale(1, 1);
      -moz-transform: scale(1, 1);
      -webkit-transform: scale(1, 1);
      -o-transform: scale(1, 1);
      -ms-transform: scale(1, 1);
    }
    50% {
      opacity: 0.8;
      box-shadow: 0 1px 2px rgb(240 66 42 / 80%);
      transform: scale(1.2, 1.2);
      -moz-transform: scale(1.2, 1.2);
      -webkit-transform: scale(1.2, 1.2);
      -o-transform: scale(1.2, 1.2);
      -ms-transform: scale(1.2, 1.2);
    }
    100% {
      opacity: 1;
      box-shadow: 0 2px 30px #f0422a;
      transform: scale(1, 1);
      -moz-transform: scale(1, 1);
      -webkit-transform: scale(1, 1);
      -o-transform: scale(1, 1);
      -ms-transform: scale(1, 1);
    }
  }
  
  .icon2 {
    animation: breathe 2s infinite;
    transform: scale(1.2);
    margin: 0 auto;
    width: 8px;
    height: 5px;
    background-color: #f0422a;
  }
  @keyframes breathe2 {
    0% {
      opacity: 0.6;
      box-shadow: 0 1px 2px rgb(103 194 58 / 60%);
      transform: scale(1, 1);
      -moz-transform: scale(1, 1);
      -webkit-transform: scale(1, 1);
      -o-transform: scale(1, 1);
      -ms-transform: scale(1, 1);
    }
    50% {
      opacity: 0.8;
      box-shadow: 0 1px 2px rgb(103 194 58 / 80%);
      transform: scale(1.2, 1.2);
      -moz-transform: scale(1.2, 1.2);
      -webkit-transform: scale(1.2, 1.2);
      -o-transform: scale(1.2, 1.2);
      -ms-transform: scale(1.2, 1.2);
    }
    100% {
      opacity: 1;
      box-shadow: 0 2px 30px #67c23a;
      transform: scale(1, 1);
      -moz-transform: scale(1, 1);
      -webkit-transform: scale(1, 1);
      -o-transform: scale(1, 1);
      -ms-transform: scale(1, 1);
    }
  }
  
  .icon3 {
    animation: breathe2 2s infinite;
    transform: scale(1.2);
    margin: 0 auto;
    width: 8px;
    height: 5px;
    background-color: #67c23a;
  }
  .seamless-warp {
    width: 100%;
    height: 75%;
    overflow: hidden;
  }
  .seamless-warp .right1-main-box {
    width: 100%;
    height: 30px;
  }
  .seamless-warp .right1-main-box span {
    width: 17%;
    display: inline-block;
    text-align: center;
    line-height: 30px;
  }
  .seamless-warp .right2-main-box {
    width: 100%;
    height: 30px;
  }
  .seamless-warp .right2-main-box span {
    width: 33.3%;
    display: inline-block;
    text-align: center;
    line-height: 30px;
    color: #fff;
    font-size: 12px;
  }
  .center1-t {
    width: 100%;
    height: 25%;
  }
  .center1-c {
    width: 100%;
    height: 65%;
  }
  </style>
  