<template>
  <div class="risk-dashboard app-container">
    <!-- 风险统计卡片 -->
    <div class="stats">
      <el-card class="stat-card high" shadow="always">
        <div class="title">特大风险</div>
        <div class="count">{{ stats.high }}</div>
      </el-card>
      <el-card class="stat-card medium" shadow="always">
        <div class="title">重大风险</div>
        <div class="count">{{ stats.medium }}</div>
      </el-card>
      <el-card class="stat-card low" shadow="always">
        <div class="title">较大风险</div>
        <div class="count">{{ stats.low }}</div>
      </el-card>
      <el-card class="stat-card low" shadow="always">
        <div class="title">一般风险</div>
        <div class="count">{{ stats.low }}</div>
      </el-card>
    </div>

    <!-- 风险趋势图 -->
    <el-card shadow="always" style="margin-top: 20px;">
      <div slot="header">
        <span>最近一周风险问题趋势</span>
      </div>
      <div id="riskChart" style="height: 300px;"></div>
    </el-card>

    <!-- 特大风险 Top5 -->
    <el-card shadow="always" style="margin-top: 20px;">
      <div slot="header">
        <span>特大风险隐患 Top5</span>
      </div>
      <el-table :data="topList" border style="width: 100%;" fit>
        <el-table-column prop="id" label="编号" width="80" />
        <el-table-column prop="description" label="问题描述" />
        <el-table-column prop="foundAt" label="发现时间" width="180">
          <template slot-scope="scope">
            {{ formatDateTime(scope.row.foundAt) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === 'PENDING' ? 'danger' : 'success'">
              {{ scope.row.status === 'PENDING' ? '未整改' : '已整改' }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { fetchDashboard } from '@/api/safety/safety'

export default {
  name: 'RiskDashboard',
  data() {
    return {
      stats: { high: 0, medium: 0, low: 0 },
      topList: [],
      trendXAxis: [],
      trendData: [],
      _timer: null,
      _chart: null
    }
  },
  mounted() {
    this.loadDashboard()
    // 简单轮询：8 秒刷新一次
    this._timer = setInterval(this.loadDashboard, 8000)
    this.$once('hook:beforeDestroy', () => {
      if (this._timer) clearInterval(this._timer)
      if (this._chart) this._chart.dispose()
    })
  },
  methods: {
    async loadDashboard() {
      try {
        const res = await fetchDashboard(7)
        const c = res.counters || {}
        this.stats.high = c.HIGH || 0
        this.stats.medium = c.MEDIUM || 0
        this.stats.low = c.LOW || 0

        // trend: [{date:'2025-09-01', count:2}, ...]
        const trend = res.weeklyTrend || []
        this.trendXAxis = trend.map(x => this.toWeekLabel(x.date))
        this.trendData = trend.map(x => x.count || 0)

        // top5
        this.topList = res.top5High || []

        this.drawChart()
      } catch (e) {
        // 可以静默或提示
        // this.$message.error('仪表盘加载失败')
        console.error(e)
      }
    },
    drawChart() {
      if (!this._chart) {
        this._chart = echarts.init(document.getElementById('riskChart'))
      }
      this._chart.setOption({
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'category', data: this.trendXAxis },
        yAxis: { type: 'value' },
        series: [
          { name: '新增隐患数', type: 'bar', data: this.trendData, itemStyle: { color: '#ff4d4f' } }
        ]
      })
    },
    toWeekLabel(ymd) {
      // ymd -> '周一/周二' 标签（也可直接返回 ymd）
      const d = new Date(ymd)
      const w = d.getDay() // 0..6
      const names = ['周日', '周一', '周二', '周三', '周四', '周五', '周六']
      return names[w]
    },
    formatDateTime(dt) {
      if (!dt) return ''
      const d = new Date(dt)
      const pad = n => (n < 10 ? '0' + n : n)
      return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())} ${pad(d.getHours())}:${pad(d.getMinutes())}`
    }
  }
}
</script>

<style scoped>
.risk-dashboard { padding: 20px; }
.stats { display: flex; gap: 20px; }
.stat-card { flex: 1; text-align: center; padding: 20px; }
.stat-card .title { font-size: 16px; margin-bottom: 10px; }
.stat-card .count { font-size: 32px; font-weight: bold; }
.high   { border-left: 6px solid #ff4d4f; }
.medium { border-left: 6px solid #faad14; }
.low    { border-left: 6px solid #1890ff; }
</style>
