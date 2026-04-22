<template>
  <div class="wrap">
    <div class="head">
      <div class="head-left">
        <div class="head-left-title">
          <span>营级单位排名</span>
        </div>
        <el-table
          :data="peopleList"
          :header-cell-style="{ background: '#0c2c50', color: 'white' }"
          style="width: 98%; background: #0c2c50; margin-left: 1%"
          height="88%"
        >
          <!-- <el-table-column type="selection" width="55" align="center" /> -->
          <el-table-column label="名次" align="center" type="index" />
          <!-- <el-table-column label="备用手机号" align="center" prop="id" /> -->
          <el-table-column label="单位" align="center" prop="name" />
          <el-table-column label="加分" align="center" prop="addscore">
            <template slot-scope="scope">
              <div style="color: rgb(2 255 2)">{{ scope.row.addscore }}</div>
            </template>
          </el-table-column>
          <el-table-column label="减分" align="center" prop="subscore">
            <template slot-scope="scope">
              <div style="color: rgb(255 106 0)">{{ scope.row.subscore }}</div>
            </template>
          </el-table-column>
          <el-table-column label="总分" align="center" prop="zongfen" />
        </el-table>
      </div>
      <div class="head-right">
        <div class="head-left-title">
          <span>待处理及待验收问题</span>
        </div>
        <el-table
          :data="peopleList2"
          :header-cell-style="{ background: '#0c2c50', color: 'white' }"
          style="width: 98%; background: #0c2c50; margin-left: 1%"
          height="88%"
          @row-click="rowclick"
        >
          <!-- <el-table-column type="selection" width="55" align="center" /> -->
          <el-table-column label="序号" align="center" type="index" />
          <el-table-column label="受检单位" align="center" prop="inspectedUnit" />
          <el-table-column label="检查单位" align="center" prop="unitCheck"></el-table-column>
          <el-table-column label="状态" align="center" prop="currentState"></el-table-column>
          <el-table-column label="减分" align="center" prop="score">
            <template slot-scope="scope">
              <div style="color: rgb(255 106 0)">{{ scope.row.score }}</div>
            </template>
          </el-table-column>
          <el-table-column label="事由" show-overflow-tooltip align="center" prop="question" />
          <el-table-column label="整改时间" align="center" prop="rectificationTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.rectificationTime, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div class="foot">
      <div class="foot-left">
        <div class="head-left-title">
          <span>整体状态完成比例</span>
        </div>
        <div id="foot-left-content"></div>
      </div>
      <div class="foot-right">
        <div class="head-left-title">
          <span>各单位</span>
        </div>
        <div class="foot-right-main" id="foot-right-main" ref="foot">
          <div class="block"></div>
          <div class="block"></div>
          <div class="block"></div>
          <div class="block"></div>
          <div class="block"></div>
          <div class="block"></div>
          <div class="block"></div>
          <div class="block"></div>
          <div class="block"></div>
          <div class="block"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  unitrank,
  pendingacceptance,
  completeproportion,
  unitproportion,
  getRegularinfo
} from "@/api/bdglregular/regularcountinfo";
import * as echarts from "echarts";
import Cookies from "js-cookie";

export default {
  data() {
    return {
      peopleList: [],
      peopleList2: [],
      peopleList3: []
    };
  },
  created() {
    // if (Cookies.get("shuaxin") != "true") {
    //   window.location.reload();
    // }
    // Cookies.set("shuaxin", "true");
    this.getlist();
    this.getlist2();
  },
  mounted() {
    this.getlist3();
    this.echarfun();
  },
  methods: {
    getlist() {
      unitrank().then(res => {
        if (res.code == 200) {
          this.peopleList = res.data;

          this.peopleList.forEach(item => {
            return (item.zongfen = item.zongfen.split(".")[0]);
          });
          this.peopleList.forEach(item => {
            return (item.subscore = item.subscore.split(".")[0]);
          });
          this.peopleList.forEach(item => {
            return (item.addscore = item.addscore.split(".")[0]);
          });
        }
      });
    },
    getlist2() {
      pendingacceptance().then(res => {
        if (res.code == 200) {
          this.peopleList2 = res.data;
        }
      });
    },
    getlist3() {
      completeproportion().then(res => {
        if (res.code == 200) {
          var myChart = echarts.init(
            document.getElementById("foot-left-content")
          );
          var option = {
            title: {
              text: "",
              subtext: "",
              left: ""
            },
            tooltip: {
              trigger: "item",
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            color: ["#5470c6", "#fac858", "#ee6666", "#91cc75", "#409EFF"],
            legend: {
              orient: "vertical",
              right: "0",
              top: "10%",
              itemGap: 20,
              textStyle: {
                color: "#fff"
              },
              data: ["待处理", "待验收", "未通过", "已通过", "已逾期"]
            },
            series: [
              {
                name: "",
                type: "pie",
                radius: "80%",
                label: {
                  normal: {
                    show: true,
                    position: "inner",
                    formatter: "{c}", //\n({d}%)
                    textStyle: {
                      fontWeight: 300,
                      fontSize: 16,
                      color: "#fff"
                    }
                  }
                },
                data: [
                  {
                    value:
                      res.data[0].daiyanshou == 0
                        ? null
                        : res.data[0].daiyanshou,
                    name: "待验收"
                  },
                  {
                    value:
                      res.data[0].daichuli == 0 ? null : res.data[0].daichuli,
                    name: "待处理"
                  },
                  {
                    value:
                      res.data[0].weitongguo == 0
                        ? null
                        : res.data[0].weitongguo,
                    name: "未通过"
                  },
                  {
                    value:
                      res.data[0].yitongguo == 0 ? null : res.data[0].yitongguo,
                    name: "已通过"
                  },
                  {
                    value: res.data[0].yiYuQi == 0 ? null : res.data[0].yiYuQi,
                    name: "已逾期"
                  }
                ],
                emphasis: {
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: "rgba(0, 0, 0, 0.5)"
                  }
                },
                itemStyle: {
                  normal: {
                    //饼状图阴影，值越大阴影亮度越高
                    shadowBlur: 5,
                    shadowColor: "#93DDFF"
                  }
                }
              }
            ]
          };
          if (
            res.data[0].daiyanshou == 0 &&
            res.data[0].daichuli == 0 &&
            res.data[0].weitongguo == 0 &&
            res.data[0].yitongguo == 0
          ) {
            option.color = ["#255F8A"];
            option.tooltip = {
              show: false
            };
            option.series[0].data = [{ value: 0, name: "无" }];
          }
          myChart.setOption(option);
          myChart.on("click", this.eConsole1);
        }
      });
    },
    getlist4() {},
    echarfun() {
      unitproportion().then(res => {
        if (res.code == 200) {
          this.peopleList3 = res.data;
          var roseCharts = document.getElementsByClassName("block");
          for (var i = 0; i < this.peopleList3.length; i++) {
            var myChart = echarts.init(roseCharts[i]);
            var option = {
              title: {
                text: this.peopleList3[i].name,
                subtext: "",
                // left: "",
                y: "bottom",
                x: "center",
                padding: 0,
                textStyle: {
                  color: "#ffff",
                  // fontSize: 12,
                  fontStyle: "normal",
                  fontWeight: "normal"
                }
              },
              color: ["#5470c6", "#fac858", "#ee6666", "#91cc75"],
              tooltip: {
                trigger: "item",
                formatter: "{a} <br/>{b} : {c} ({d}%)"
              },
              // legend: {
              //   orient: "vertical",
              //   right: "0",
              //   top: "3%",
              //   itemGap: 5,
              //   textStyle: {
              //     color: "#fff"
              //   },
              //   data: ["待处理", "待验收", "未通过", "已通过"]
              // },
              series: [
                {
                  name: "",
                  type: "pie",
                  radius: "60%",
                  center: ["50%", "50%"],
                  label: {
                    normal: {
                      show: true,
                      position: "inner",
                      formatter: "{c}", //\n({d}%)
                      textStyle: {
                        fontWeight: 300,
                        fontSize: 16,
                        color: "#fff"
                      }
                    }
                  },
                  data: [
                    {
                      value:
                        this.peopleList3[i].daiyanshou == 0
                          ? null
                          : this.peopleList3[i].daiyanshou,
                      name: "待验收",
                      id: this.peopleList3[i].deptId
                    },
                    {
                      value:
                        this.peopleList3[i].daichuli == 0
                          ? null
                          : this.peopleList3[i].daichuli,
                      name: "待处理",
                      id: this.peopleList3[i].deptId
                    },
                    {
                      value:
                        this.peopleList3[i].weitongguo == 0
                          ? null
                          : this.peopleList3[i].weitongguo,
                      name: "未通过",
                      id: this.peopleList3[i].deptId
                    },
                    {
                      value:
                        this.peopleList3[i].yitongguo == 0
                          ? null
                          : this.peopleList3[i].yitongguo,
                      name: "已通过",
                      id: this.peopleList3[i].deptId
                    }
                  ],
                  emphasis: {
                    itemStyle: {
                      shadowBlur: 10,
                      shadowOffsetX: 0,
                      shadowColor: "rgba(0, 0, 0, 0.5)"
                    }
                  },
                  itemStyle: {
                    normal: {
                      //饼状图阴影，值越大阴影亮度越高
                      shadowBlur: 5,
                      shadowColor: "#93DDFF"
                    }
                  }
                }
              ]
            };
            if (
              this.peopleList3[i].daiyanshou == 0 &&
              this.peopleList3[i].daichuli == 0 &&
              this.peopleList3[i].weitongguo == 0 &&
              this.peopleList3[i].yitongguo == 0
            ) {
              option.color = ["#255F8A"];
              option.tooltip = {
                show: false
              };
              option.series[0].data = [{ value: 0, name: "无" }];
            }
            myChart.setOption(option);

            myChart.on("click", this.eConsole);
          }
        }
      });
    },
    rowclick(row) {
      this.$router
        .push({
          path: "/bdglregular/regularinfo",
          // query: { id: row.inspectedUnitId,inspectedUnitId:row.inspectedUnitId,currentState:currentState },
          query: { obj: row }
        })
        .catch(() => {});
    },
    //饼状图点击事件
    eConsole(param) {
      // console.log(param);
      if (param.data.id) {
        this.$router
          .push({
            path: "/bdglregular/regularinfo",
            query: { obj: param.data }
          })
          .catch(() => {});
      }
    },
    // 整体情况跳转
    eConsole1(param) {
      if (param.data.name != "无") {
        this.$router
          .push({
            path: "/bdglregular/regularinfo",
            query: { obj: param.data }
          })
          .catch(() => {});
      }
    }
  }
};
</script>

<style scoped>
.wrap {
  min-height: calc(100vh - 85px);
  width: 100%;
  position: relative;
  overflow: hidden;
  background-color: #103360;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
}
.head {
  width: 100%;
  /* height: 43.5vh; */
  flex: 1;
  display: flex;
  flex-direction: row;
  margin-top: 10px;
  margin-bottom: 10px;
}
.head-left {
  flex: 4;
  height: 44vh;
  background-image: url("../../../images/量化/底1.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  margin-left: 10px;
  margin-right: 10px;
}
.head-right {
  flex: 6;
  height: 44vh;
  background-image: url("../../../images/量化/底2.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  margin-right: 10px;
}

.head-left-title {
  width: 96%;
  height: 30px;
  font-size: 18px;
  color: #fff;
  background-image: url("../../../assets/images/组 21.png");
  background-repeat: no-repeat;
  margin-top: 9px;
  line-height: 30px;
  position: relative;
  margin-left: 1%;
}
.head-left-title span {
  position: absolute;
  left: 36px;
}

::v-deep .el-table__empty-block {
  background-color: #0c2c50;
}
.foot {
  width: 100%;
  /* height: 45vh; */
  flex: 1;
  display: flex;
  flex-direction: row;
  margin-bottom: 10px;
}
.foot-left {
  flex: 4;
  height: 43.5vh;
  background-image: url("../../../images/量化/底1.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  margin-left: 10px;
  margin-right: 10px;
}
.foot-right {
  flex: 6;
  height: 43.5vh;
  background-image: url("../../../images/量化/底2.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  margin-right: 10px;
}
.foot-right-main {
  width: 100%;
  height: 100%;
}
.foot-right-main .block {
  width: 20%;
  height: 42%;
  margin: 0;
  display: inline-block;
}
#foot-left-content {
  width: 90%;
  height: 380px;
}
::v-deep .el-table .el-table__cell.gutter {
  display: none;
}
</style>
