<template>
  <div class="app-container">
    <!-- 顶部统计卡片 -->
    <el-row :gutter="20" class="mb-4">
      <el-col :span="12">
        <el-card shadow="hover">
<!--            <div>-->
<!--              <h3>军车统计</h3>-->
<!--              <p>总数：{{ stats.military.total }}</p>-->
<!--              <p>在位：{{ stats.military.inCamp }}</p>-->
<!--              <p>值班：{{ stats.military.inCamp }}</p>-->
<!--              <p>外出：{{ stats.military.outCamp }}</p>-->
<!--              <p>待修：{{ stats.military.inCamp }}</p>-->
<!--            </div>-->
          <!-- 军车信息总览总览 -->
          <div class="stats-module">
            <div shadow="hover" class="people-overview-card">
<!--              <div slot="header">-->
<!--                <span class="card-title">军车统计</span>-->
<!--              </div>-->
              <h3>军车统计</h3>
              <div class="overview-content">
                <!-- 左侧数据 -->
                <div class="overview-stats">
                  <div class="stat-item">
                    <img src="/resources/car.png" class="stat-icon" />
                    <div class="stat-text">
                      <p class="stat-label">总数</p>
                      <p class="stat-value">{{ stats.military.total  }}</p>
                    </div>
                  </div>
                  <div class="stat-item">
                    <img src="/resources/car.png" class="stat-icon" />
                    <div class="stat-text">
                      <p class="stat-label">在位</p>
                      <p class="stat-value">
                        {{ stats.military.inCamp }}
                      </p>
                    </div>
                  </div>
                  <div class="stat-item">
                    <img src="/resources/car.png" class="stat-icon" />
                    <div class="stat-text">
                      <p class="stat-label">值班</p>
                      <p class="stat-value">
                        {{ stats.military.duty }}
                      </p>
                    </div>
                  </div>
                  <div class="stat-item">
                    <img src="/resources/car.png" class="stat-icon" />
                    <div class="stat-text">
                      <p class="stat-label">外出</p>
                      <p class="stat-value">
                        {{ stats.military.outCamp }}
                      </p>
                    </div>
                  </div>
                  <div class="stat-item">
                    <img src="/resources/car.png" class="stat-icon" />
                    <div class="stat-text">
                      <p class="stat-label">待修</p>
                      <p class="stat-value">
                        {{ stats.military.repair }}
                      </p>
                    </div>
                  </div>
                </div>
                <!-- 右侧图表 -->
                <div class="overview-chart">
                  <div ref="militaryChart" class="chart-box"></div>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
<!--          <div>-->
<!--            <h3>私家车统计</h3>-->
<!--            <p>总数：{{ stats.private.total }}</p>-->
<!--            <p>在位：{{ stats.private.inCamp }}</p>-->
<!--            <p>值班：{{ stats.private.outCamp }}</p>-->
<!--            <p>外出：{{ stats.private.outCamp }}</p>-->
<!--            <p>待修：{{ stats.private.outCamp }}</p>-->
<!--          </div>-->
          <div class="stats-module">
            <div shadow="hover" class="people-overview-card">
              <!--              <div slot="header">-->
              <!--                <span class="card-title">军车统计</span>-->
              <!--              </div>-->
              <h3>私家车统计</h3>
              <div class="overview-content">
                <!-- 左侧数据 -->
                <div class="overview-stats">
                  <div class="stat-item">
                    <img src="/resources/car.png" class="stat-icon" />
                    <div class="stat-text">
                      <p class="stat-label">总数</p>
                      <p class="stat-value">{{ stats.private.total  }}</p>
                    </div>
                  </div>
                  <div class="stat-item">
                    <img src="/resources/car.png" class="stat-icon" />
                    <div class="stat-text">
                      <p class="stat-label">在位</p>
                      <p class="stat-value">
                        {{ stats.private.inCamp }}
                      </p>
                    </div>
                  </div>
                  <div class="stat-item">
                    <img src="/resources/car.png" class="stat-icon" />
                    <div class="stat-text">
                      <p class="stat-label">值班</p>
                      <p class="stat-value">
                        {{ stats.private.duty }}
                      </p>
                    </div>
                  </div>
                  <div class="stat-item">
                    <img src="/resources/car.png" class="stat-icon" />
                    <div class="stat-text">
                      <p class="stat-label">外出</p>
                      <p class="stat-value">
                        {{ stats.private.outCamp }}
                      </p>
                    </div>
                  </div>
                  <div class="stat-item">
                    <img src="/resources/car.png" class="stat-icon" />
                    <div class="stat-text">
                      <p class="stat-label">待修</p>
                      <p class="stat-value">
                        {{ stats.private.repair }}
                      </p>
                    </div>
                  </div>
                </div>
                <!-- 右侧图表 -->
                <div class="overview-chart">
                  <div ref="privateChart" class="chart-box"></div>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 表格展示 -->
    <el-card shadow="always">
      <div class="table-header">
        <span>车辆实时在位情况</span>
        <el-button type="primary" size="small" @click="refreshData">
          刷新数据
        </el-button>
      </div>

      <el-table :data="vehicleList" border style="width: 100%; margin-top: 10px">
        <el-table-column prop="chelaingleibie" label="车辆类别" width="100">
          <template slot-scope="scope">
            {{ scope.row.chelaingleibie === 'MILITARY' ? '军车'
            : scope.row.chelaingleibie === 'PRIVATE' ? '私家车'
              : '' }}
          </template>
        </el-table-column>
        <el-table-column prop="vehiclenumber" label="车牌号" width="120"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="getStatus(scope.row).code === '3' ? 'danger' : 'success'">
              {{ getStatus(scope.row).label }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="outCause" label="外出事由"></el-table-column>
        <el-table-column prop="outTime" label="外出时间" width="300">
          <template slot-scope="scope">
            <span>{{
              parseTime(scope.row.outTime, "{y}-{m}-{d} {h}:{i}:{s}")
            }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import {listThebus} from "@/api/militaryvehicleManagement/thebus";
import {getDicts} from "@/api/system/dict/data";
import {listThebusApply} from "@/api/militaryvehicleManagement/thebusApply";
import * as echarts from "echarts";
import {listPrivateCar} from "@/api/militaryvehicleManagement/privateCar";
import {parseTime} from "../../../utils/supervision";

export default {
  name: "VehicleStatus",
  created() {
    this.getList();
  },
  data() {
    return {
      isFirstLoad: true, // ✅ 标志是否首次加载
      loading: false,
      militaryChart: null,
      privateChart: null,
      // 统计数据（写死）
      stats: {
        military: { total: 0, inCamp: 0, outCamp: 0, repair: 0, duty: 0 },
        private: { total: 0, inCamp: 0, outCamp: 0, repair: 0, duty: 0 },
      },
      // 车辆列表（写死）
      vehicleList: [
        // { type: "军车", plate: "军A-12345", status: "在位", reason: "", time: "" },
        // { type: "军车", plate: "军A-67890", status: "外出", reason: "执行任务", time: "2025-09-04 08:30" },
        // { type: "军车", plate: "军A-11223", status: "外出", reason: "送物资", time: "2025-09-04 09:00" },
        // { type: "军车", plate: "军A-44556", status: "在位", reason: "", time: "" },
        // { type: "军车", plate: "军A-77889", status: "在位", reason: "", time: "" },
        // { type: "私家车", plate: "浙B-88888", status: "在位", reason: "", time: "" },
        // { type: "私家车", plate: "浙B-99999", status: "外出", reason: "回家探亲", time: "2025-09-03 19:20" },
        // { type: "私家车", plate: "浙B-66666", status: "外出", reason: "采购生活用品", time: "2025-09-04 10:15" },
        // { type: "私家车", plate: "浙B-12345", status: "在位", reason: "", time: "" },
        // { type: "私家车", plate: "浙B-54321", status: "在位", reason: "", time: "" },
        // { type: "私家车", plate: "浙B-11111", status: "在位", reason: "", time: "" },
      ],
      militaryList: [],
      privateList: [],
    };
  },
  methods: {
    parseTime,
    getStatus(row) {
      const code = row.chelaingleibie === 'PRIVATE'
        ? row.state
        : row.status;

      const map = {
        '1': '在位',
        '2': '值班',
        '3': '出车',
        '4': '待修'
      };

      return {
        code,
        label: map[code] || '未知'
      };
    },
    refreshData() {
      this.getList();
    },
    // getList() {
    //   this.loading = true;
    //   listThebus(this.queryParams).then((response) => {
    //     console.log("response", response);
    //     this.resetChart();
    //     this.vehicleList = response.rows;
    //     this.stats.military.total = response.total;
    //     this.loading = false;
    //     this.vehicleList.forEach(item => {
    //       switch (item.status) {
    //         case "1": // 在位
    //           this.stats.military.inCamp++;
    //           break;
    //         case "2": // 值班
    //           this.stats.military.duty++;
    //           break;
    //         case "3": // 外出
    //           this.stats.military.outCamp++;
    //           break;
    //         case "4": // 待修
    //           this.stats.military.repair++;
    //           break;
    //       }
    //     });
    //     // 必须 nextTick
    //     this.initChart(this.stats);
    //     // this.getStatuse();
    //     listThebusApply(this.queryParams).then((response) => {
    //       const applyList = response.rows;
    //       // 遍历 vehicleList，直接给每条数据加字段
    //       this.vehicleList.forEach(vehicle => {
    //         // 找到该车辆的申请记录
    //         const applyInfo = applyList.find(a => a.carIds === vehicle.vehiclenumber);
    //         console.log("applyInfo", applyInfo);
    //         // 直接追加字段
    //         this.$set(vehicle, 'outTime', applyInfo ? applyInfo.outtime : null);
    //         this.$set(vehicle, 'outCause', applyInfo ? applyInfo.cause : null);
    //       });
    //       console.log(this.vehicleList);
    //       // this.total = response.total;
    //       // this.loading = false;
    //       // ✅ 只有非首次加载时才提示
    //       if (!this.isFirstLoad) {
    //         this.$message.success("数据已刷新");
    //       }
    //       // 首次加载后关闭标志
    //       this.isFirstLoad = false;
    //     });
    //   }).catch(() => {
    //     this.loading = false;
    //     // ✅ 捕获网络异常或真正的错误
    //     console.log("请求失败：", error);
    //     this.$message.error("获取数据失败");
    //   });
    // },
    getList() {
      this.loading = true;

      Promise.all([
        this.getMilitaryList(),
        this.getPrivateList()
      ]).then(() => {

        // 合并军车 + 私家车
        this.vehicleList = [...this.militaryList, ...this.privateList];

        // 统计逻辑
        this.calculateStats();

        // 图表
        this.$nextTick(() => {
          this.initChart(this.stats);
        });

        // 注入申请信息
        this.injectApplyInfo();

        if (!this.isFirstLoad) {
          this.$message.success("数据已刷新");
        }
        this.isFirstLoad = false;

        this.loading = false;
      })
        .catch(err => {
          console.error(err);
          this.$message.error("获取数据失败");
          this.loading = false;
        });
    },
    getMilitaryList() {
      return new Promise((resolve, reject) => {
        listThebus(this.queryParams).then(res => {
          // 把军车数据直接存入 militaryList
          this.militaryList = res.rows.map(item => ({
            ...item,
            chelaingleibie: 'MILITARY'
          }));
          resolve();
        }).catch(reject);
      });
    },
    getPrivateList() {
      return new Promise((resolve, reject) => {
        listPrivateCar(this.queryParams).then(res => {
          this.privateList = res.rows.map(item => ({
            ...item,
            chelaingleibie: 'PRIVATE'
          }));
          resolve();
        }).catch(reject);
      });
    },
    calculateStats() {
      this.stats.military = { total: 0, inCamp: 0, outCamp: 0, duty: 0, repair: 0 };
      this.stats.private  = { total: 0, inCamp: 0, outCamp: 0, duty: 0, repair: 0 };

      const all = [...this.militaryList, ...this.privateList];

      all.forEach(item => {

        // 判断是私家车还是军车
        const isPrivate = item.chelaingleibie === 'PRIVATE';

        let target = isPrivate ? this.stats.private : this.stats.military;

        target.total++;

        // 军车用 item.status，私家车用 item.state
        const code = isPrivate ? item.state : item.status;

        switch (code) {
          case "1": target.inCamp++; break;
          case "2": target.duty++; break;
          case "3": target.outCamp++; break;
          case "4": target.repair++; break;
        }
      });
    },
    injectApplyInfo() {
      listThebusApply(this.queryParams).then(res => {

        const applyList = res.rows;

        this.vehicleList.forEach(vehicle => {
          const applyInfo = applyList.find(a => a.carIds === vehicle.vehiclenumber);
          this.$set(vehicle, 'outTime',  applyInfo ? applyInfo.outtime : null);
          this.$set(vehicle, 'outCause', applyInfo ? applyInfo.cause  : null);
        });

      });
    },
    resetChart(){
      this.stats.military = { total: 0, inCamp: 0, outCamp: 0, duty: 0, repair: 0 };
      this.stats.private  = { total: 0, inCamp: 0, outCamp: 0, duty: 0, repair: 0 };
    },
    initChart(stats) {
      this.initUserChart(
        stats.military,
        "militaryChart",
        "军车统计情况",
      ); // 军车
      this.initUserChart(
        stats.private,
        "privateChart",
        "私家车统计情况",
      ); // 私家车
    },
    initUserChart(stats, ref, title) {
      const rawData = [
        { value: (stats.inCamp) || 0, name: "在位数量" },
        { value: (stats.outCamp) || 0, name: "外出数量" },
        { value: (stats.repair) || 0, name: "待修数量" },
        { value: (stats.duty) || 0, name: "值班数量" }
      ];
      const seriesData = rawData.map(item => {
        if (item.value === 0) {
          return {
            ...item,
            itemStyle: { opacity: 0 },   // 不显示
            label: { show: false }
          };
        }
        return item;
      });
      const chartRefArr = this.$refs[ref];
      const chartRef = Array.isArray(chartRefArr)
        ? chartRefArr[0]
        : chartRefArr;

      if (!chartRef) {
        console.log("chartRef", this.$refs[ref]);
        console.warn("chart容器未渲染");
        return;
      }

      // ✅ 检查已有实例，存在就销毁
      const oldInstance = echarts.getInstanceByDom(chartRef);
      if (oldInstance) {
        oldInstance.dispose();
      }

      const chart = echarts.init(chartRef);
      if (stats.total === 0) {
        // 显示“暂无数据”
        chart.setOption({
          title: {
            text: "暂无数据",
            left: "center",
            top: "center",
            textStyle: {
              fontSize: 16,
              color: "#999",
            },
          },
          legend: {
            bottom: 0,
            data: rawData.map(i => i.name)
          },
          series: [],
        });

        return chart;
      }
      chart.setOption({
        tooltip: { trigger: "item" },
        legend: {
          bottom: 0,
          data: rawData.map(i => i.name)
        },
        series: [
          {
            name: title,
            type: "pie",
            center: ['50%', '44%'],   // 饼图上移一点
            radius: ["45%", "70%"],
            avoidLabelOverlap: false,
            label: { show: true, formatter: "{b}\n{d}%" },
            data: seriesData
          },
        ],
      });
      return chart;
    },
  },
};
</script>

<style scoped>
.mb-4 {
  margin-bottom: 20px;
}
.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
/* 添加模块之间的间距 */
.stats-module {
  margin-bottom: 15px; /* 根据需要调整 */
}
/* 人员统计 */
.people-overview-card {
  width: 100%;
}
.card-title {
  font-weight: 600;
  font-size: 16px;
  margin-bottom: 8px;
}

.overview-content {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;   /*  👈 顶部对齐 */
  gap: 10px;
  flex-wrap: wrap;
}


.overview-stats {
  display: flex;
  flex-direction: column;   /* 👈 垂直排列 */
  gap: 15px;
}
.stat-item {
  display: flex;
  align-items: center;
  gap: 10px; /* 图标与文本之间的间距 */
}
.stat-icon {
  width: 24px;
  height: 24px;
}
.stat-text {
  display: flex;
  gap: 10px;
}

.stat-label {
  color: #888;
  font-size: 14px;
  margin: 0;
}

.stat-value {
  font-size: 18px;
  font-weight: bold;
  margin: 0;
  color: #333;
}
.overview-chart {
  flex: 1;
  min-width: 300px;
}

.chart-box {
  width: 100%;
  height: 240px;
}
</style>
