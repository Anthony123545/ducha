<template>
  <div class="material-dashboard app-container">
    <!-- 标题和刷新按钮 -->
    <div class="header-section">
      <h2>物资数量概览</h2>
      <el-button type="primary" @click="fetchMaterialData" icon="el-icon-refresh" circle>
        <i class="el-icon-refresh"></i>
      </el-button>
    </div>

    <!-- 总体统计信息 -->
    <div class="summary-stats">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-card class="summary-card blue-theme">
            <div class="summary-item">
              <span class="summary-label">总库房数</span>
              <span class="summary-value">{{ totalWarehouses }}</span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="summary-card blue-theme">
            <div class="summary-item">
              <span class="summary-label">总物资种类</span>
              <span class="summary-value">{{ totalMaterialTypes }}</span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="summary-card blue-theme">
            <div class="summary-item">
              <span class="summary-label">总物资数量</span>
              <span class="summary-value">{{ totalMaterialsCount }}</span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="summary-card" :class="{ 'low-stock': lowStockItems > 0 }">
            <div class="summary-item">
              <span class="summary-label">低库存物资</span>
              <span class="summary-value">{{ lowStockItems }}</span>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 低库存警告 -->
    <!-- 低库存警告 -->
    <div v-if="lowStockItems > 0" class="warning-section">
      <el-alert
        :title="`警告：有 ${lowStockItems} 种物资库存不足，请及时补充！`"
        type="warning"
        show-icon
        :closable="false"
        effect="dark"
        class="dark-alert"
      />
    </div>

    <!-- 全部物资库存表格 -->
    <div class="inventory-section" style="margin-top: 20px;">
      <h3 style="color: #fff; margin-bottom: 15px;">全部物资库存情况</h3>
      <el-table :data="allMaterialList" style="width: 100%;" class="dark-table">
        <el-table-column prop="name" label="物资名称" width="180" />
        <el-table-column prop="warehouse" label="库房" width="120" />
        <el-table-column prop="material_inventory" label="当前库存" width="100" 
          :cell-style="{color: '#ff4d4f'}">
        </el-table-column>
        <el-table-column prop="material_total" label="总数量" width="100" />
        <el-table-column prop="person_liable" label="责任人" width="120" />
        <el-table-column prop="storage_location" label="存放位置" />
        <el-table-column prop="stockStatus" label="库存状态" width="100">
          <template slot-scope="scope">
            <span v-if="scope.row.isLowStock" style="color: #ff4d4f; font-weight: bold;">库存不足</span>
            <span v-else style="color: #52c41a;">正常</span>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 低库存物资详情（可选展开） -->
    <div v-if="lowStockItems > 0" style="margin-top: 20px;">
      <el-collapse v-model="activeNames" class="dark-collapse" style="background: rgba(30, 41, 59, 0.8); border-radius: 8px; overflow: hidden; border: none;">
        <el-collapse-item title="查看库存不足物资详情" name="1" style="background: transparent;" class="dark-collapse-item">
            <el-table :data="lowStockList" style="width: 100%;" class="dark-table">
              <el-table-column prop="name" label="物资名称" width="180" />
              <el-table-column prop="warehouse" label="库房" width="120" />
              <el-table-column prop="material_inventory" label="当前库存" width="100" />
              <el-table-column prop="material_total" label="总数量" width="100" />
              <el-table-column prop="person_liable" label="责任人" width="120" />
              <el-table-column prop="storage_location" label="存放位置" />
            </el-table>
        </el-collapse-item>
      </el-collapse>
    </div>

    <!-- 主要图表区域 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <!-- 物资数量分布柱状图 -->
      <el-col :span="12">
        <el-card shadow="always" class="chart-card">
          <div slot="header" class="card-header">
            <div class="card-icon" :style="getCardIconStyle('物资数量分布')"></div>
            <span>物资数量分布</span>
          </div>
          <div id="materialChart" style="height: 350px;"></div>
        </el-card>
      </el-col>
      
      <!-- 库存状态饼图 -->
      <el-col :span="12">
        <el-card shadow="always" class="chart-card">
          <div slot="header" class="card-header">
            <div class="card-icon" :style="getCardIconStyle('库存状态分布')"></div>
            <span>库存状态分布</span>
          </div>
          <div id="stockStatusChart" style="height: 350px;"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 物资价值分布和库房利用率 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <!-- 物资价值分布饼图 -->
      <el-col :span="12">
        <el-card shadow="always" class="chart-card">
          <div slot="header" class="card-header">
            <div class="card-icon" :style="getCardIconStyle('物资价值分布')"></div>
            <span>物资价值分布</span>
          </div>
          <div id="valueDistributionChart" style="height: 350px;"></div>
        </el-card>
      </el-col>
      
      <!-- 库房利用率仪表盘 -->
      <el-col :span="12">
        <el-card shadow="always" class="chart-card">
          <div slot="header" class="card-header">
            <div class="card-icon" :style="getCardIconStyle('库房物资种类分布')"></div>
            <span>库房物资种类分布</span>
          </div>
          <div id="warehouseUtilizationChart" style="height: 350px;"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 库房物资概览卡片 -->
    <div class="warehouse-section" style="margin-top: 20px;">
      <h3>库房物资概览</h3>
      <div class="warehouse-cards">
        <el-card 
          v-for="warehouse in warehouseData" 
          :key="warehouse.warehouse" 
          class="warehouse-card blue-theme"
          :class="{ 'has-low-stock': hasLowStockInWarehouse(warehouse) }"
          shadow="hover"
        >
          <div class="warehouse-header">
            <h4>{{ warehouse.warehouse }}</h4>
            <el-tag :type="warehouse.data.length > 0 ? 'primary' : 'info'" size="small">
              {{ warehouse.data.length }} 种物资
            </el-tag>
          </div>
          <div class="warehouse-content">
            <!-- 库房信息装饰区域 -->
            <!-- 库房信息装饰区域 - 无论是否有物资都显示 -->
            <div class="warehouse-decoration" 
                 :style="getWarehouseDecorationStyle(warehouse)">
              <div class="decoration-content">
                <div class="warehouse-type-icon" :style="getCardIconStyle(warehouse.warehouse, true)">{{ getWarehouseIcon(warehouse.warehouse) }}</div>
                <div class="warehouse-info">
                  <div class="warehouse-type">{{ getWarehouseTypeText(warehouse.warehouse) }}</div>
                  <div class="warehouse-materials-count">
                    {{ warehouse.data.length }} 种物资
                  </div>
                </div>
              </div>
            </div>
            
            <div class="material-list" v-if="warehouse.data && warehouse.data.length > 0">
              <div 
                v-for="(item, index) in warehouse.data" 
                :key="item.id || index" 
                class="material-item"
                :class="{ 'low-item': isLowStock(item) }"
              >
                <div class="item-name">{{ item.name }}</div>
                <div class="item-stats">
                  <el-progress
                    :percentage="calculateStockPercentage(item.material_inventory, item.material_total)"
                    :status="isLowStock(item) ? 'exception' : ''"
                    :stroke-width="8"
                    :show-text="false"
                  />
                  <span class="stock-text">
                    {{ item.material_inventory }} / {{ item.material_total }}
                    <span class="unit-price">单价: ¥{{ item.price.toLocaleString() }}</span>
                  </span>
                </div>
              </div>
            </div>
            <div class="no-materials" v-else>
              <el-empty description="暂无物资"></el-empty>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { getMaterialOverview } from '@/api/materialManagement/WarehouseStatistics';

export default {
  name: "MaterialDashboard",
  data() {
    return {
      warehouseData: [], // 库房数据
      totalWarehouses: 0,
      totalMaterialTypes: 0,
      totalMaterialsCount: 0,
      totalMaterialsValue: 0,
      lowStockItems: 0,
      lowStockList: [],
      allMaterialList: [], // 存储所有物资
      activeNames: ['1'], // 默认展开低库存警告详情
      // 图表实例
      materialChart: null,
      stockStatusChart: null,
      valueDistributionChart: null,
      warehouseUtilizationChart: null,
      miniCharts: {}
    };
  },
  mounted() {
    // 初始化数据
    this.fetchMaterialData();
    
    // 监听窗口大小变化，重新调整图表大小
    window.addEventListener('resize', this.handleResize);
  },
  beforeDestroy() {
    // 清理图表实例和事件监听器
    if (this.materialChart) {
      this.materialChart.dispose();
    }
    if (this.stockStatusChart) {
      this.stockStatusChart.dispose();
    }
    if (this.valueDistributionChart) {
      this.valueDistributionChart.dispose();
    }
    if (this.warehouseUtilizationChart) {
      this.warehouseUtilizationChart.dispose();
    }
    // 清理迷你图表
    Object.values(this.miniCharts).forEach(chart => {
      if (chart) {
        chart.dispose();
      }
    });
    window.removeEventListener('resize', this.handleResize);
  },
  methods: {
    // 获取物资数据
    async fetchMaterialData() {
      try {
        // 尝试从后端获取数据
        const response = await getMaterialOverview();
        
        console.log('Backend Response:', response);
        
        if (response.code === 200) {
          // 确保数据格式正确
          if (response.data && response.data.data) {
            this.warehouseData = response.data.data;
            this.totalWarehouses = response.data.number || this.warehouseData.length;
            
            console.log('Warehouse Data from API:', this.warehouseData);
            console.log('Total Warehouses:', this.totalWarehouses);
          } else {
            console.error('Invalid data structure:', response.data);
            // 数据结构无效时保持空数据
            this.warehouseData = [];
            this.totalWarehouses = 0;
          }
        } else {
          console.error('API error:', response.msg);
          // API返回错误时保持空数据
          this.warehouseData = [];
          this.totalWarehouses = 0;
        }
      } catch (error) {
        console.error('Error fetching material data:', error);
        // 请求失败时保持空数据
        this.warehouseData = [];
        this.totalWarehouses = 0;
      }
      
      // 计算统计信息
      this.calculateStatistics();
      
      // 更新图表
      this.initMaterialChart();
      this.initStockStatusChart();
      this.initValueDistributionChart();
      this.initWarehouseUtilizationChart();
      this.initMiniCharts();
    },
    

    
    // 计算统计信息
    calculateStatistics() {
      let totalTypes = 0;
      let totalCount = 0;
      let totalValue = 0;
      let lowStockCount = 0;
      const lowStockItems = [];
      this.allMaterialList = []; // 存储所有物资
      
      this.warehouseData.forEach(warehouse => {
        totalTypes += warehouse.data.length;
        
        warehouse.data.forEach(item => {
          totalCount += item.material_inventory;
          totalValue += item.material_inventory * item.price;
          
          // 添加库房信息和库存状态标识
          const materialWithWarehouse = {
            ...item,
            warehouse: warehouse.warehouse || '', // 添加库房名称
            isLowStock: this.isLowStock(item) // 添加库存状态标识
          };
          
          // 添加到全部物资列表
          this.allMaterialList.push(materialWithWarehouse);
          
          // 判断是否低库存
          if (this.isLowStock(item)) {
            lowStockCount++;
            lowStockItems.push(materialWithWarehouse);
          }
        });
      });
      
      this.totalMaterialTypes = totalTypes;
      this.totalMaterialsCount = totalCount;
      this.totalMaterialsValue = totalValue;
      this.lowStockItems = lowStockCount;
      this.lowStockList = lowStockItems;
    },
    
    // 判断是否低库存
    isLowStock(item) {
      return item.material_inventory <= 0 || (item.material_total > 0 && item.material_inventory / item.material_total < 0.3);
    },
    
    // 判断库房是否有低库存物资
    hasLowStockInWarehouse(warehouse) {
      return warehouse.data.some(item => this.isLowStock(item));
    },
    
    // 获取库房装饰区域样式
    getWarehouseDecorationStyle(warehouse) {
      // 根据库房名称生成不同的背景色
      const colors = {
        '武器库房': 'linear-gradient(135deg, #1890ff 0%, #1890ff80 100%)',
        '弹药库房': 'linear-gradient(135deg, #ff4d4f 0%, #ff4d4f80 100%)',
        '装备库房': 'linear-gradient(135deg, #52c41a 0%, #52c41a80 100%)',
        '工具库房': 'linear-gradient(135deg, #faad14 0%, #faad1480 100%)',
        '其他库房': 'linear-gradient(135deg, #722ed1 0%, #722ed180 100%)'
      };
      
      // 如果没有匹配的颜色，则使用默认颜色
      const defaultColor = 'linear-gradient(135deg, #1890ff 0%, #1890ff80 100%)';
      
      return {
        background: colors[warehouse.warehouse] || defaultColor,
        borderRadius: '8px',
        padding: '15px',
        marginBottom: '15px'
      };
    },
    
    // 获取库房类型文本
    getWarehouseTypeText(warehouseName) {
      const typeMap = {
        '武器库房': '武器仓库',
        '弹药库房': '弹药存储',
        '装备库房': '装备存放',
        '工具库房': '工具管理',
        '其他库房': '综合存储'
      };
      
      return typeMap[warehouseName] || warehouseName;
    },
    
    // 获取库房图标
    getWarehouseIcon(warehouseName) {
      // 根据库房名称返回相应的图标
      const iconMap = {
        '武器': '🏢',
        '弹药': '💣',
        '装备': '🛡️',
        '工具': '🔧',
        '其他': '📦'
      };
      
      // 检查库房名称是否包含关键字
      for (const [key, icon] of Object.entries(iconMap)) {
        if (warehouseName.includes(key)) {
          return icon;
        }
      }
      
      // 默认图标
      return '🏢';
    },

    // 计算库存百分比
    calculateStockPercentage(inventory, total) {
      if (total === 0) return 0;
      return Math.round((inventory / total) * 100);
    },
    
    // 获取蓝黑色调主题颜色
    getThemeColors() {
      return {
        primary: '#1890ff',
        success: '#52c41a',
        warning: '#faad14',
        danger: '#f5222d',
        info: '#13c2c2',
        blue: {
          50: '#e6f7ff',
          100: '#bae7ff',
          200: '#91d5ff',
          300: '#69c0ff',
          400: '#40a9ff',
          500: '#1890ff',
          600: '#096dd9',
          700: '#0050b3',
          800: '#003a8c',
          900: '#002766'
        }
      };
    },
    
    // 初始化物资数量分布柱状图
    initMaterialChart() {
      if (!this.materialChart) {
        this.materialChart = echarts.init(document.getElementById('materialChart'));
      }
      
      // 准备图表数据
      const warehouseNames = [];
      const totalMaterials = [];
      const currentInventory = [];
      const colors = this.getThemeColors();
      
      this.warehouseData.forEach(warehouse => {
        warehouseNames.push(warehouse.warehouse);
        
        // 计算每个库房的总物资和当前库存
        let warehouseTotal = 0;
        let warehouseInventory = 0;
        
        warehouse.data.forEach(item => {
          warehouseTotal += item.material_total;
          warehouseInventory += item.material_inventory;
        });
        
        totalMaterials.push(warehouseTotal);
        currentInventory.push(warehouseInventory);
      });
      
      this.materialChart.setOption({
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
            shadowStyle: {
              color: 'rgba(0, 0, 0, 0.1)'
            }
          },
          backgroundColor: 'rgba(0, 0, 0, 0.7)',
          borderColor: 'transparent',
          textStyle: {
            color: '#fff'
          }
        },
        legend: {
          data: ['总数量', '当前库存'],
          textStyle: {
            color: '#e2e8f0'
          },
          top: '0%'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          top: '15%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: warehouseNames,
            axisTick: {
              alignWithLabel: true,
              lineStyle: {
                color: '#ddd'
              }
            },
            axisLine: {
              lineStyle: {
                color: '#ddd'
              }
            },
            axisLabel: {
              color: '#cbd5e1',
              rotate: 30
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              color: '#cbd5e1'
            },
            splitLine: {
              lineStyle: {
                color: '#f0f0f0',
                type: 'dashed'
              }
            }
          }
        ],
        series: [
          {
            name: '总数量',
            type: 'bar',
            stack: '总量',
            itemStyle: {
              color: colors.blue[600],
              borderRadius: [4, 4, 0, 0]
            },
            emphasis: {
              focus: 'series',
              itemStyle: {
                color: colors.blue[700]
              }
            },
            data: totalMaterials,
            animationDelay: function (idx) {
              return idx * 100;
            }
          },
          {
            name: '当前库存',
            type: 'bar',
            stack: '总量',
            itemStyle: {
              color: function(params) {
                // 如果当前库存小于总数量的30%，显示红色警告
                if (totalMaterials[params.dataIndex] > 0 && params.value / totalMaterials[params.dataIndex] < 0.3) {
                  return colors.danger;
                }
                return colors.success;
              },
              borderRadius: [4, 4, 0, 0]
            },
            emphasis: {
              focus: 'series',
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.3)'
              }
            },
            data: currentInventory,
            animationDelay: function (idx) {
              return idx * 100 + 100;
            }
          }
        ],
        animationEasing: 'elasticOut',
        animationDelayUpdate: function (idx) {
          return idx * 5;
        }
      });
    },
    
    // 初始化库存状态分布饼图
    initStockStatusChart() {
      if (!this.stockStatusChart) {
        this.stockStatusChart = echarts.init(document.getElementById('stockStatusChart'));
      }
      
      // 统计库存状态
      let normalCount = 0;
      let warningCount = 0;
      let criticalCount = 0;
      const colors = this.getThemeColors();
      
      this.warehouseData.forEach(warehouse => {
        warehouse.data.forEach(item => {
          if (item.material_inventory === 0) {
            criticalCount++;
          } else if (item.material_total > 0 && item.material_inventory / item.material_total < 0.3) {
            warningCount++;
          } else {
            normalCount++;
          }
        });
      });
      
      this.stockStatusChart.setOption({
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'item',
          formatter: '{b}: {c} ({d}%)',
          backgroundColor: 'rgba(0, 0, 0, 0.7)',
          borderColor: 'transparent',
          textStyle: {
            color: '#fff'
          }
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          textStyle: {
            color: '#e2e8f0'
          }
        },
        series: [
          {
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            name: '库存状态',
            type: 'pie',
            radius: ['40%', '70%'],
            center: ['60%', '50%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center',
              color: '#e2e8f0',
              textStyle: {
                color: '#e2e8f0'
              }
            },
            emphasis: {
                label: {
                  show: true,
                  fontSize: 16,
                  fontWeight: 'bold',
                  formatter: '{b}\n{d}%',
                  textStyle: {
                    color: '#ffffff',
                    fontWeight: 'bold'
                  }
                },
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            labelLine: {
              show: false,
              lineStyle: {
                color: '#e2e8f0'
              }
            },
            data: [
              { value: normalCount, name: '正常库存', itemStyle: { color: colors.success } },
              { value: warningCount, name: '库存偏低', itemStyle: { color: colors.warning } },
              { value: criticalCount, name: '库存告急', itemStyle: { color: colors.danger } }
            ],
            animationType: 'scale',
            animationEasing: 'elasticOut'
          }
        ]
      });
    },
    
    // 初始化物资价值分布饼图
    initValueDistributionChart() {
      if (!this.valueDistributionChart) {
        this.valueDistributionChart = echarts.init(document.getElementById('valueDistributionChart'));
      }
      
      // 统计每个库房的物资价值
      const warehouseValues = {};
      const colors = this.getThemeColors();
      const colorList = [colors.blue[500], colors.blue[700], colors.info, colors.blue[300], colors.blue[900]];
      
      this.warehouseData.forEach(warehouse => {
        let warehouseValue = 0;
        warehouse.data.forEach(item => {
          warehouseValue += item.material_inventory * item.price;
        });
        if (warehouseValue > 0) {
          warehouseValues[warehouse.warehouse] = warehouseValue;
        }
      });
      
      // 转换为图表数据格式
      const pieData = Object.keys(warehouseValues).map((key, index) => ({
        name: key,
        value: warehouseValues[key],
        itemStyle: {
          color: colorList[index % colorList.length]
        }
      }));
      
      this.valueDistributionChart.setOption({
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'item',
          formatter: function(params) {
            const value = params.value;
            const formattedValue = (value / 10000).toFixed(2) + ' 万元';
            return params.name + ': ' + formattedValue + ' (' + params.percent + '%)';
          },
          backgroundColor: 'rgba(0, 0, 0, 0.7)',
          borderColor: 'transparent',
          textStyle: {
            color: '#fff'
          }
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          textStyle: {
            color: '#e2e8f0'
          }
        },
        series: [
          {
            name: '物资价值',
            type: 'pie',
            radius: ['40%', '70%'],
            center: ['60%', '50%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center',
              color: '#e2e8f0',
              textStyle: {
                color: '#e2e8f0'
              }
            },
            emphasis: {
                label: {
                  show: true,
                  fontSize: 20,
                  fontWeight: 'bold',
                  formatter: function(params) {
                    const value = params.value;
                    const formattedValue = (value / 10000).toFixed(2) + ' 万元';
                    return params.name + '\n' + formattedValue + '\n(' + params.percent + '%)';
                  },
                  textStyle: {
                    color: '#ffffff',
                    fontWeight: 'bold'
                  }
                },
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            labelLine: {
              show: false,
              lineStyle: {
                color: '#e2e8f0'
              }
            },
            data: pieData,
            animationType: 'scale',
            animationEasing: 'elasticOut'
          }
        ]
      });
    },
    
    // 初始化库房物资种类分布仪表盘
    initWarehouseUtilizationChart() {
      if (!this.warehouseUtilizationChart) {
        this.warehouseUtilizationChart = echarts.init(document.getElementById('warehouseUtilizationChart'));
      }
      
      // 准备仪表盘数据
      const gaugeData = [];
      const maxMaterials = Math.max(...this.warehouseData.map(warehouse => warehouse.data.length));
      const colors = this.getThemeColors();
      
      this.warehouseData.forEach(warehouse => {
        if (warehouse.data.length > 0) {
          gaugeData.push({
            name: warehouse.warehouse,
            value: warehouse.data.length
          });
        }
      });
      
      this.warehouseUtilizationChart.setOption({
        backgroundColor: 'transparent',
        tooltip: {
          formatter: '{a} <br/>{b}: {c} 种物资',
          backgroundColor: 'rgba(0, 0, 0, 0.7)',
          borderColor: 'transparent',
          textStyle: {
            color: '#fff'
          }
        },
        series: [
          {
            name: '库房物资种类',
            type: 'gauge',
            detail: {
              formatter: '{value}',
              fontSize: 20,
              color: colors.blue[600]
            },
            data: gaugeData,
            axisLine: {
              lineStyle: {
                width: 30,
                color: [
                  [0.3, colors.danger],
                  [0.7, colors.warning],
                  [1, colors.success]
                ]
              }
            },
            pointer: {
              itemStyle: {
                color: colors.blue[700]
              }
            },
            axisTick: {
              distance: -30,
              length: 8,
              lineStyle: {
                color: '#fff',
                width: 2
              }
            },
            splitLine: {
              distance: -30,
              length: 30,
              lineStyle: {
                color: '#fff',
                width: 4
              }
            },
            axisLabel: {
              color: '#e2e8f0',
              distance: -40
            },
            max: maxMaterials > 0 ? maxMaterials : 10,
            animationEasing: 'elasticOut',
            animationDelayUpdate: function (idx) {
              return idx * 100;
            }
          }
        ]
      });
    },
    
    // 获取卡片图标样式
    getCardIconStyle(name, isWarehouse = false) {
      // 基于名称生成哈希值
      let hash = 0;
      for (let i = 0; i < name.length; i++) {
        hash = name.charCodeAt(i) + ((hash << 5) - hash);
      }
      
      // 使用哈希值生成随机颜色
      const hue = Math.abs(hash) % 360;
      
      // 为库房卡片返回不同大小的样式
      if (isWarehouse) {
        return {
          backgroundColor: `hsl(${hue}, 70%, 50%)`,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          width: '60px',
          height: '60px',
          borderRadius: '8px',
          fontSize: '30px',
          color: 'white'
        };
      }
      
      return {
        backgroundColor: `hsl(${hue}, 70%, 50%)`,
        display: 'inline-flex',
        alignItems: 'center',
        justifyContent: 'center',
        marginRight: '8px',
        width: '24px',
        height: '24px',
        borderRadius: '4px',
        fontSize: '14px',
        color: 'white'
      };
    },

    // 初始化库房迷你图表
    initMiniCharts() {
      const colors = this.getThemeColors();
      
      this.warehouseData.forEach(warehouse => {
        if (warehouse.data && warehouse.data.length > 0) {
          const chartDom = document.getElementById('miniChart-' + warehouse.warehouse);
          if (chartDom) {
            // 销毁已存在的图表
            if (this.miniCharts[warehouse.warehouse]) {
              this.miniCharts[warehouse.warehouse].dispose();
            }
            
            const chart = echarts.init(chartDom);
            this.miniCharts[warehouse.warehouse] = chart;
            
            // 准备迷你图表数据
            const materialNames = warehouse.data.map(item => item.name.length > 6 ? item.name.substring(0, 6) + '...' : item.name);
            const inventoryData = warehouse.data.map(item => item.material_inventory);
            const totalData = warehouse.data.map(item => item.material_total);
            
            chart.setOption({
              backgroundColor: 'transparent',
              tooltip: {
                trigger: 'axis',
                backgroundColor: 'rgba(0, 0, 0, 0.7)',
                borderColor: 'transparent',
                textStyle: {
                  color: '#fff'
                }
              },
              grid: {
                left: '0%',
                right: '0%',
                bottom: '0%',
                top: '0%',
                containLabel: true
              },
              xAxis: {
                type: 'category',
                data: materialNames,
                axisLine: {
                  show: false
                },
                axisTick: {
                  show: false
                },
                axisLabel: {
                  show: false
                }
              },
              yAxis: {
                type: 'value',
                show: false
              },
              series: [
                {
                  name: '当前库存',
                  type: 'bar',
                  data: inventoryData,
                  itemStyle: {
                    color: function(params) {
                      const item = warehouse.data[params.dataIndex];
                      if (this.isLowStock(item)) {
                        return colors.danger;
                      }
                      return colors.blue[500];
                    }.bind(this),
                    borderRadius: [2, 2, 0, 0]
                  },
                  barWidth: '60%'
                }
              ]
            });
          }
        }
      });
    },
    
    // 处理窗口大小变化
    handleResize() {
      if (this.materialChart) {
        this.materialChart.resize();
      }
      if (this.stockStatusChart) {
        this.stockStatusChart.resize();
      }
      if (this.valueDistributionChart) {
        this.valueDistributionChart.resize();
      }
      if (this.warehouseUtilizationChart) {
        this.warehouseUtilizationChart.resize();
      }
      // 调整迷你图表大小
      Object.values(this.miniCharts).forEach(chart => {
        if (chart) {
          chart.resize();
        }
      });
    }
  }
};
</script>

<style scoped>
.material-dashboard {
  padding: 20px;
  background: linear-gradient(135deg, #0f172a 0%, #1e293b 100%);
  min-height: 100vh;
  color: #e2e8f0;
}

/* 强制覆盖Element UI的表格样式 */
:deep(.el-table__header-wrapper) {
  background-color: rgba(30, 41, 59, 0.9) !important;
}

:deep(.el-table__body-wrapper) {
  background-color: rgba(15, 23, 42, 0.9) !important;
}

:deep(.el-table__row) {
  background-color: rgba(15, 23, 42, 0.9) !important;
}

:deep(.el-table) {
  background-color: rgba(15, 23, 42, 0.9) !important;
  --el-table-header-bg-color: rgba(30, 41, 59, 0.9) !important;
  --el-table-row-hover-bg-color: rgba(64, 158, 255, 0.1) !important;
  --el-table-border-color: rgba(64, 158, 255, 0.2) !important;
  --el-table-text-color: #e2e8f0 !important;
}

.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 10px 0;
}

.header-section h2 {
  margin: 0;
  color: #e2e8f0;
  font-size: 24px;
  font-weight: 600;
}

.summary-stats {
  margin-bottom: 20px;
}

.summary-card {
  background: rgba(30, 41, 59, 0.8);
  border: 1px solid rgba(148, 163, 184, 0.1);
  border-radius: 8px;
  padding: 20px;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
}

.summary-card.blue-theme {
  background: linear-gradient(135deg, rgba(24, 144, 255, 0.05) 0%, rgba(7, 149, 255, 0.1) 100%);
  border: 1px solid rgba(24, 144, 255, 0.2);
}

.summary-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
}

.summary-card.low-stock {
  background: linear-gradient(135deg, rgba(245, 34, 45, 0.05) 0%, rgba(245, 34, 45, 0.1) 100%);
  border: 1px solid rgba(245, 34, 45, 0.2);
}

.summary-item {
  text-align: center;
}

.summary-label {
  display: block;
  font-size: 14px;
  color: #94a3b8;
  margin-bottom: 8px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.summary-value {
  display: block;
  font-size: 32px;
  font-weight: bold;
  color: #38bdf8;
  transition: color 0.3s ease;
}

.summary-card:hover .summary-value {
  color: #60a5fa;
}

.summary-card.low-stock .summary-value {
  color: #f87171;
}

.warning-section {
    margin-bottom: 20px;
  }

  .dark-alert {
    background-color: rgba(22, 33, 62, 0.9) !important;
    border: 1px solid rgba(64, 158, 255, 0.3) !important;
    color: #e2e8f0 !important;
  }

  .dark-alert .el-alert__title {
    color: #e2e8f0 !important;
  }

  .dark-collapse-item .el-collapse-item__header {
    background-color: rgba(22, 33, 62, 0.8) !important;
    color: #e2e8f0 !important;
    border-bottom: 1px solid rgba(64, 158, 255, 0.2) !important;
  }

  .dark-collapse-item .el-collapse-item__content {
    background-color: rgba(22, 33, 62, 0.9) !important;
    color: #e2e8f0 !important;
  }

  /* 确保折叠面板没有白色背景 */
  .el-collapse {
    background: transparent !important;
    
    border: none !important;
  }

  /* 为dark-collapse类添加样式 */
  .dark-collapse {
    background: rgba(30, 41, 59, 0.8) !important;
    border: none !important;
  }

  /* 使用:deep伪类强制覆盖折叠面板的所有内部元素 */
  .dark-collapse :deep(*) {
    background: transparent !important;
  }

  .dark-collapse :deep(.el-collapse-item__wrap) {
    background: rgba(22, 33, 62, 0.9) !important;
    border: none !important;
  }

  .dark-collapse :deep(.el-collapse-item__content) {
    background: rgba(22, 33, 62, 0.9) !important;
    border: none !important;
  }

  .dark-collapse :deep(.el-collapse-item) {
    background: transparent !important;
    border: none !important;
  }

  .el-collapse-item__wrap {
    background-color: rgba(22, 33, 62, 0.9) !important;
    border: none !important;
  }

  .el-collapse-item__content {
    background-color: rgba(22, 33, 62, 0.9) !important;
    border-top: none !important;
    padding: 10px 20px !important;
  }

  .dark-table {
    background-color: rgba(15, 23, 42, 0.9) !important;
    color: #e2e8f0 !important;
    border: 1px solid rgba(64, 158, 255, 0.2) !important;
    --el-table-header-bg-color: rgba(30, 41, 59, 0.9) !important;
    --el-table-row-hover-bg-color: rgba(64, 158, 255, 0.1) !important;
    --el-table-border-color: rgba(64, 158, 255, 0.2) !important;
    --el-table-text-color: #e2e8f0 !important;
  }

  .dark-table thead {
    background-color: rgba(30, 41, 59, 0.9) !important;
  }

  .dark-table th {
    color: #e2e8f0 !important;
    font-weight: bold !important;
    background-color: rgba(30, 41, 59, 0.9) !important;
    border-bottom-color: rgba(64, 158, 255, 0.2) !important;
  }

  .dark-table td {
    color: #e2e8f0 !important;
    border-color: rgba(64, 158, 255, 0.2) !important;
  }

  .dark-table .el-table__row {
    background-color: rgba(15, 23, 42, 0.9) !important;
    transition: background-color 0.3s ease;
  }

  .dark-table .el-table__row:hover {
    background-color: rgba(64, 158, 255, 0.1) !important;
  }

  /* 覆盖Element UI默认的表格样式 */
  .dark-table .el-table__header-wrapper {
    background-color: rgba(30, 41, 59, 0.9) !important;
  }

  .dark-table .el-table__body-wrapper {
    background-color: rgba(15, 23, 42, 0.9) !important;
  }

  .dark-table .el-table__cell {
    border-color: rgba(64, 158, 255, 0.2) !important;
  }

.warehouse-section {
  margin-bottom: 30px;
}

.warehouse-section h3 {
  margin-bottom: 15px;
  color: #e2e8f0;
  font-size: 20px;
  font-weight: 600;
}

.warehouse-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 20px;
}

.warehouse-card {
  background: rgba(30, 41, 59, 0.8);
  border: 1px solid rgba(148, 163, 184, 0.1);
  border-radius: 8px;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
}

.warehouse-card.blue-theme {
  background: linear-gradient(135deg, rgba(24, 144, 255, 0.05) 0%, rgba(7, 149, 255, 0.1) 100%);
  border: 1px solid rgba(24, 144, 255, 0.2);
}

.warehouse-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
}

.warehouse-card.has-low-stock {
  border: 1px solid rgba(245, 34, 45, 0.5);
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(245, 34, 45, 0.3);
  }
  70% {
    box-shadow: 0 0 0 10px rgba(245, 34, 45, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(245, 34, 45, 0);
  }
}

.warehouse-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid rgba(148, 163, 184, 0.1);
}

.warehouse-header h4 {
  margin: 0;
  color: #e2e8f0;
  font-size: 16px;
  font-weight: 600;
}

.mini-chart-container {
  margin-bottom: 15px;
  background: rgba(15, 23, 42, 0.5);
  border-radius: 6px;
  padding: 10px;
}

.mini-chart {
  height: 100px;
  width: 100%;
}

.material-list {
  max-height: 300px;
  overflow-y: auto;
  scrollbar-width: thin;
  scrollbar-color: rgba(148, 163, 184, 0.3) transparent;
}

.material-list::-webkit-scrollbar {
  width: 6px;
}

.material-list::-webkit-scrollbar-track {
  background: transparent;
}

.material-list::-webkit-scrollbar-thumb {
  background-color: rgba(148, 163, 184, 0.3);
  border-radius: 3px;
}

.material-item {
  padding: 10px 0;
  border-bottom: 1px solid rgba(148, 163, 184, 0.1);
  transition: background-color 0.3s ease;
}

.material-item:last-child {
  border-bottom: none;
}

.material-item:hover {
  background-color: rgba(148, 163, 184, 0.05);
}

.material-item.low-item {
  background-color: rgba(245, 34, 45, 0.05);
  padding: 10px;
  margin: -10px 0;
  border-left: 3px solid #f56c6c;
  border-radius: 0 4px 4px 0;
}

.item-name {
  font-size: 14px;
  font-weight: bold;
  color: #e2e8f0;
  margin-bottom: 8px;
}

.item-stats {
  margin-top: 5px;
}

.stock-text {
  display: block;
  font-size: 12px;
  color: #94a3b8;
  margin-top: 5px;
}

.unit-price {
  float: right;
  color: #cbd5e1;
  font-weight: 500;
}

.no-materials {
  padding: 40px 0;
  color: #64748b;
}

.card-header {
  font-weight: bold;
  color: #e2e8f0;
  font-size: 16px;
  display: flex;
  align-items: center;
}

.card-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 8px;
  width: 24px;
  height: 24px;
  border-radius: 4px;
  font-size: 14px;
  color: white;
}

.chart-card {
    background: rgba(30, 41, 59, 0.9);
    border: 1px solid rgba(148, 163, 184, 0.1);
    border-radius: 8px;
    backdrop-filter: blur(10px);
    transition: all 0.3s ease;
  }

/* 库房装饰区域样式 */
.warehouse-decoration {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  transition: transform 0.2s ease;
}

.warehouse-decoration:hover {
  transform: scale(1.02);
}

.decoration-content {
  display: flex;
  align-items: center;
  width: 100%;
}

.warehouse-type-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 30px;
  margin-right: 15px;
  opacity: 0.9;
  width: 60px;
  height: 60px;
  border-radius: 8px;
  color: white;
}
.warehouse-info {
  flex: 1;
  color: white;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.warehouse-type {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
}

.warehouse-materials-count {
  font-size: 14px;
  opacity: 0.9;
}

.chart-card:hover {
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .header-section {
    flex-direction: column;
    gap: 10px;
    align-items: flex-start;
  }
  
  .summary-item {
    min-width: auto;
  }
  
  .warehouse-cards {
    grid-template-columns: 1fr;
  }
  
  .warehouse-card {
    margin-bottom: 20px;
  }
  
  .summary-value {
    font-size: 24px;
  }
}

/* 确保所有元素都没有白色背景 */
* {
  box-sizing: border-box;
}

/* 折叠面板样式 */
/deep/.el-collapse {
  background-color: #303f4b !important;
}
/deep/.el-collapse,.el-collapse-item__wrap {
  border: none;
  background-color: #303f4b !important;
}
/deep/ .el-collapse-item__header {
  color: #fff;
  background-color: #303f4b !important;
}
/deep/ .el-collapse-item__content {
  color: #fff;
  background-color: #303f4b !important;
}

/* 使用:deep伪类覆盖Element UI默认样式 */
:deep(.el-table) {
  background-color: rgba(15, 23, 42, 0.9) !important;
  --el-table-header-bg-color: rgba(30, 41, 59, 0.9) !important;
  --el-table-row-hover-bg-color: rgba(64, 158, 255, 0.1) !important;
  --el-table-border-color: rgba(64, 158, 255, 0.2) !important;
  --el-table-text-color: #e2e8f0 !important;
  background-image: none !important;
}

:deep(.el-table__header) {
  background-color: rgba(30, 41, 59, 0.9) !important;
  background-image: none !important;
}

:deep(.el-table__body) {
  background-color: rgba(15, 23, 42, 0.9) !important;
  background-image: none !important;
}

:deep(.el-table__row) {
  background-color: rgba(15, 23, 42, 0.9) !important;
  background-image: none !important;
}

:deep(.el-table__row:hover) {
  background-color: rgba(64, 158, 255, 0.1) !important;
}

:deep(.el-table__cell) {
  color: #e2e8f0 !important;
  border-bottom-color: rgba(64, 158, 255, 0.2) !important;
  background-image: none !important;
}

:deep(.el-table__header th) {
  background-color: rgba(30, 41, 59, 0.9) !important;
  color: #e2e8f0 !important;
  font-weight: bold !important;
  border-bottom-color: rgba(64, 158, 255, 0.2) !important;
  background-image: none !important;
}

/* 强制覆盖表格的所有背景 */
:deep(.el-table__inner-wrapper) {
  background-color: rgba(15, 23, 42, 0.9) !important;
  background-image: none !important;
}

:deep(.el-table__header-wrapper > table) {
  background-color: rgba(30, 41, 59, 0.9) !important;
  background-image: none !important;
}

:deep(.el-table__body-wrapper > table) {
  background-color: rgba(15, 23, 42, 0.9) !important;
  background-image: none !important;
}

/* 覆盖所有表格相关的div元素 */
:deep(.el-table *) {
  background-color: transparent !important;
  background-image: none !important;
}

:deep(.el-table__header-wrapper) {
  background-color: rgba(30, 41, 59, 0.9) !important;
  background-image: none !important;
}

:deep(.el-table__body-wrapper) {
  background-color: rgba(15, 23, 42, 0.9) !important;
  background-image: none !important;
}

:deep(.el-table__wrapper) {
  background-color: rgba(15, 23, 42, 0.9) !important;
  background-image: none !important;
}

/* 确保表格行内的div元素也使用深色背景 */
:deep(.el-table__row *) {
  background-color: transparent !important;
  background-image: none !important;
}

:deep(.el-table__cell > *) {
  background-color: transparent !important;
  background-image: none !important;
}
/* 额外加强表格内部结构样式覆盖 */
:deep(.el-table__fixed) {
  background-color: rgba(15, 23, 42, 0.9) !important;
  background-image: none !important;
}

:deep(.el-table__fixed-right) {
  background-color: rgba(15, 23, 42, 0.9) !important;
  background-image: none !important;
}

:deep(.el-table__row) > td {
  background-color: rgba(15, 23, 42, 0.9) !important;
  background-image: none !important;
}
:deep(.el-table .el-table__header-wrapper th) {
  background-color: rgba(15, 23, 42, 0.9) !important;
  background-image: none !important;
}
/* 确保表格在任何状态下都使用深色主题 */

:deep(.el-table__body tr.hover-row > td) {
  background-color: rgba(64, 158, 255, 0.1) !important;
  background-image: none !important;
}

:deep(.el-collapse-item__header) {
  background-color: rgba(22, 33, 62, 0.8) !important;
  color: #e2e8f0 !important;
  border-bottom: 1px solid rgba(64, 158, 255, 0.2) !important;
}

:deep(.el-collapse-item__content) {
  background-color: rgba(22, 33, 62, 0.9) !important;
  color: #e2e8f0 !important;
  border-top: none !important;
}

:deep(.el-collapse) {
  background: transparent !important;
  background-color: transparent !important;
  border: none !important;
}
</style>
