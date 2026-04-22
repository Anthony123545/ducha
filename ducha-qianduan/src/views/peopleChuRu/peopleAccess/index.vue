<template>
  <div class="app-container">

    <!-- 主内容区域 -->
    <div class="content-wrapper">
      <!-- 左侧人员信息卡片 -->
      <div class="people-section">
        <div class="section-header">
          <div class="section-title">人员列表</div>
          <div class="section-stats">共 {{ peopleList.length }} 人</div>
        </div>
        
        <!-- 搜索框 -->
        <div class="search-wrapper">
          <el-select v-model="searchType" placeholder="搜索类型" class="search-type-select" style="width: 120px; margin-right: 10px;">
            <el-option label="姓名" value="name"></el-option>
            <el-option label="工号" value="employeeNumber"></el-option>
            <el-option label="访客号" value="visitorNumber"></el-option>
            <el-option label="单位/部门" value="dept"></el-option>
            <el-option label="手机号" value="phone"></el-option>
          </el-select>
          <el-input
            v-model="searchKeyword"
            :placeholder="`搜索${getPlaceholderText()}`"
            prefix-icon="el-icon-search"
            clearable
            @clear="handleSearchClear"
            @input="handleSearchInput"
            class="search-input"
          />
          <div class="type-filter">
            <el-radio-group v-model="peopleType" @change="changePeopleType">
              <el-radio-button label="all">全部</el-radio-button>
              <el-radio-button label="employee">员工</el-radio-button>
              <el-radio-button label="visitor">访客</el-radio-button>
            </el-radio-group>
          </div>
        </div>
        
        <!-- 人员卡片列表 -->
        <div class="people-cards">
          <div 
            v-for="person in filteredPeopleList" 
            :key="person.employeeNumber" 
            class="person-card"
            :class="{
              'person-in': person.status === '在营',
              'person-out': person.status === '外出',
              'person-unknown': person.status === '未识别',
              'person-selected': selectedEmployeeNumber === person.employeeNumber
            }"
            @click="selectPerson(person)"
          >
            <div class="person-avatar" :class="person.peopleType === '访客' ? 'visitor-avatar' : 'employee-avatar'">
            {{ person.name.charAt(0) }}
            <span class="person-type-tag" :class="person.peopleType === '访客' ? 'visitor-tag' : 'employee-tag'">
              {{ person.peopleType }}
            </span>
          </div>
          <div class="person-info">
            <div class="person-name">
              {{ person.name }}
            </div>
            <div class="person-id">
              工号: {{ person.employeeNumber }}
            </div>
            <div class="person-dept">
              {{ person.peopleType === '访客' ? '单位' : '部门' }}: {{ person.deptName }}
            </div>
            <div class="person-idcard">身份证: {{ person.employeeNumberCard || '未填写' }}</div>
            <div class="person-phone">手机: {{ person.phone || '未填写' }}</div>
            <div class="person-status">
                <el-button 
                  size="mini" 
                  :type="person.status === '在营' ? 'success' : 'danger'"
                  disabled
                  class="status-button"
                >
                  {{ person.status }}
                </el-button>
              </div>
            </div>
            <div class="person-actions">
              <el-button 
              size="small" 
              :type="person.peopleType === '访客' ? 'warning' : 'success'" 
              plain
              icon="el-icon-check" 
              @click.stop="mockRecognition(person, '入')"
              :disabled="person.status === '在营'"
            >
              入营
            </el-button>
            <el-button 
              size="small" 
              :type="person.peopleType === '访客' ? 'warning' : 'danger'" 
              plain
              icon="el-icon-close" 
              @click.stop="mockRecognition(person, '出')"
              :disabled="person.status === '外出'"
            >
              出营
            </el-button>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧出入记录 -->
      <div class="log-section">
        <el-card class="log-card" shadow="hover">
          <div slot="header" class="log-header">
            <div class="log-title">
              <i class="el-icon-timer"></i> 
              人脸识别出入记录
              <el-tag v-if="selectedEmployeeNumber" type="info" size="small" style="margin-left: 10px;">
                {{ getSelectedPersonName() }}
              </el-tag>
            </div>
            <div class="log-header-actions">
              <el-button 
                v-if="selectedEmployeeNumber" 
                size="small" 
                type="text" 
                @click="clearSelectedPerson"
                style="color: #64b5f6; margin-right: 10px;"
              >
                <i class="el-icon-close"></i> 清除筛选
              </el-button>
              <!-- 分页组件 -->
              <div class="pagination-wrapper" v-if="pagination.total > 0">
                <el-pagination
                  background
                  layout="prev,next, jumper, sizes, total"
                  :total="pagination.total"
                  :page-size="pagination.pageSize"
                  :page-sizes="[5, 10,15]"
                  :current-page="pagination.currentPage"
                  @current-change="handlePageChange"
                  @size-change="handleSizeChange"
                />
              </div>
            </div>
          </div>
        
          
          <!-- 日志表格 -->
          <div class="log-table-wrapper">
            <el-table 
              v-loading="false" 
              :data="filteredLogList" 
              style="width: 100%;"
              class="log-table"
              :row-class-name="tableRowClassName"
            >
              <el-table-column prop="time" label="时间"  />
          <el-table-column prop="employeeNumber" label="工号" />
          <el-table-column prop="peopleType" label="人员类型" />
          <el-table-column prop="name" label="姓名">
            <template slot-scope="scope">
              <div class="log-name">
                {{ scope.row.name }}
                <el-tag v-if="scope.row.peopleType === '访客'" size="mini" type="warning" class="ml-1">访客</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="dept" label="单位/部门" >
            <template slot-scope="scope">
              <div class="log-dept">{{ scope.row.deptName }}</div>
            </template>
          </el-table-column>

              <el-table-column prop="action" label="出入类型" >
                <template slot-scope="scope">
                  <el-tag 
                    :type="scope.row.action === '入营' ? 'success' : 'danger'" 
                    class="action-tag"
                    effect="dark"
                  >
                    {{ scope.row.action }}
                  </el-tag>
                </template>
              </el-table-column>
            </el-table>
            
            
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import { getPeopleStatus, getVisitorStatus, setPeopleStatus, setVisitorStatus, getPeopleAccessRecords, setStatusByEmployeeNumber, getStatusByEmployeeNumber } from '@/api/peopleChuRu/peopleAccess'

export default {
  name: "PeopleAccess",
  data() {
      return {
        // 人员信息（包含员工和访客）
        peopleList: [],
        // 出入日志
        logList: [],
        // 分页参数
        pagination: {
          currentPage: 1,
          pageSize: 15,
          total: 0
        },
        // 搜索关键词
        searchKeyword: '',
        // 搜索类型
        searchType: 'name',
        // 选中的人员ID
        selectedEmployeeNumber: null,
        // 人员类型筛选
        peopleType: 'all' // 'all', 'employee', 'visitor'
      };
    },
  created() {
    // 页面加载时获取人员状态和出入记录
    this.fetchPeopleStatus();
    this.fetchAccessRecords();
  },
  computed: {
    // 过滤后的人员列表
    filteredPeopleList() {
        console.log('搜索关键词变化:', this.searchKeyword);
        
        // 确保peopleList是数组
        const list = Array.isArray(this.peopleList) ? this.peopleList : [];
        
        // 先根据人员类型过滤
        let filteredByType = this.peopleType === 'all' 
          ? list 
          : list.filter(person => {
              if (!person) return false;
              // 映射类型值：employee -> 员工, visitor -> 访客
              const targetType = this.peopleType === 'employee' ? '员工' : '访客';
              return person.peopleType === targetType;
            });
        
        // 确保searchKeyword是字符串类型
        const searchTerm = String(this.searchKeyword || '').trim();
        console.log('处理后的搜索词:', searchTerm);
        
        // 再根据搜索关键词过滤
        if (searchTerm === '') {
          console.log('搜索词为空，返回类型过滤后列表');
          return filteredByType;
        }
        
        const keyword = searchTerm.toLowerCase();
        const result = filteredByType.filter(person => {
          if (!person) return false;
          
          // 根据搜索类型进行过滤
          switch(this.searchType) {
            case 'name':
              return String(person.name || '').toLowerCase().includes(keyword);
            case 'employeeNumber':
              return String(person.employeeNumber || person.employeeNumber || '').toLowerCase().includes(keyword);
            case 'visitorNumber':
              return String(person.visitorNumber || person.employeeNumber || '').toLowerCase().includes(keyword);
            case 'dept':
              return String(person.dept || '').toLowerCase().includes(keyword);
            case 'phone':
              return String(person.phone || '').includes(keyword);
            default:
              // 安全地获取并处理所有可能的搜索字段
              const searchFields = [
                String(person.name || ''),
                String(person.dept || ''),
                String(person.employeeNumber || ''),
                String(person.employeeNumber || ''),
                String(person.visitorNumber || ''),
                String(person.employeeNumberCard || ''),
                String(person.phone || ''),
                String(person.company || '')
              ];
              
              // 检查是否有任何字段包含搜索关键词
              return searchFields.some(field => field.toLowerCase().includes(keyword));
          }
        });
        
        console.log('搜索结果数量:', result.length);
        return result;
      },
    // 过滤后的出入记录（根据选中人员）
    filteredLogList() {
      if (!this.selectedEmployeeNumber) {
        return this.logList;
      }
      return this.logList.filter(record => record.ID === this.selectedEmployeeNumber);
    }
  },
  methods: {
    // 获取人员状态（包含员工和访客）
  async fetchPeopleStatus() {
    try {
      // 并行获取员工和访客数据
      const [employeeRes, visitorRes] = await Promise.all([getPeopleStatus(), getVisitorStatus()]);
      let allData = [];
      
      // 处理员工数据
      if (employeeRes.code === 200 && employeeRes.data) {
        allData = allData.concat(employeeRes.data.map(person => ({
          ...person,
          // 后端返回的peopleStatus: 0表示不在营，1表示在营
          status: person.status === 1 ? '在营' : '外出',
          peopleType: '员工'
        })));
      }
      
      // 处理访客数据
      if (visitorRes.code === 200 && visitorRes.data) {
        allData = allData.concat(visitorRes.data.map(person => ({
          ...person,
          id: person.employeeNumber,
          // 后端返回的peopleStatus: 0表示不在营，1表示在营
          status: person.status === 1 ? '在营' : '外出',
          peopleType: '访客',
          originalId: person.employeeNumber // 保留原始id，用于API调用
        })));
      }
      
      // 保存所有数据，过滤逻辑完全交给filteredPeopleList计算属性处理
      this.peopleList = allData;
      
      // 按状态和类型排序：在营 > 外出，员工优先于访客
      this.peopleList.sort((a, b) => {
        // 首先按状态排序
        const statusOrder = { '在营': 2, '外出': 1 };
        if (statusOrder[a.status] !== statusOrder[b.status]) {
          return statusOrder[b.status] - statusOrder[a.status];
        }
        // 然后按人员类型排序
        if (a.peopleType === '员工' && b.peopleType === '访客') return -1;
        if (a.peopleType === '访客' && b.peopleType === '员工') return 1;
        return 0;
      });
    } catch (error) {
      console.error('获取人员状态失败:', error);
      this.$message.error('获取人员状态失败，请稍后重试');
    }
  },
    
    // 更新单个人员的最新状态
async updateSinglePersonStatus(personId) {
  try {
    // 查找对应人员获取工号
    const person = this.peopleList.find(p => p.employeeNumber === personId);
    if (!person || !person.employeeNumber) {
      throw new Error('未找到指定人员或工号信息');
    }
    
    // 使用工号查询接口获取状态
    const result = await getStatusByEmployeeNumber(person.employeeNumber);
    
    if (result.code === 200 && result.data) {
      // 在peopleList中找到对应人员并更新状态
      const personIndex = this.peopleList.findIndex(person => person.employeeNumber === personId);
      if (personIndex !== -1) {
        // 确保保留peopleType字段
        const currentPerson = this.peopleList[personIndex];
        const updatedPerson = {
          ...currentPerson,
          ...result.data,
          status: result.data.status === 1 ? '在营' : '外出',
          // 明确保留人员类型
          peopleType: currentPerson.peopleType
        };
        
        // 使用 $set 方法确保响应式更新
        this.$set(this.peopleList, personIndex, updatedPerson);
      }
    }
  } catch (error) {
    console.error('获取单个人员状态失败:', error);
    // 失败时回退到刷新整个列表
    this.fetchPeopleStatus();
  }
},

    // 设置人员状态
    async setPersonStatus(person, status, peopleType = '员工') {
      try {

        // 使用setStatusByEmployeeNumber接口，工号作为参数
        const statusValue = status === '入' ? 1 : 0;
        console.log('设置状态前，人员当前状态:', person.status);
        console.log('准备设置状态值:', statusValue);
        
        const setResult = await setStatusByEmployeeNumber(person.employeeNumber, statusValue);
        console.log('设置状态接口返回:', setResult);
        
        // 立即重新获取最新状态并更新
        const getResult = await getStatusByEmployeeNumber(person.employeeNumber);
        console.log('获取最新状态结果:', getResult);
        
        // 直接在当前方法中更新状态，确保响应式更新
        if (getResult.code === 200 && getResult.data && getResult.data.length > 0) {
          // 修正：data是数组，需要访问第一个元素
          const statusData = getResult.data[0];
          const personIndex = this.peopleList.findIndex(p => p.employeeNumber === person.employeeNumber);
          if (personIndex !== -1) {
            const updatedPerson = {
              ...this.peopleList[personIndex],
              ...statusData,
              status: statusData.status === 1 ? '在营' : '外出',
              peopleType: this.peopleList[personIndex].peopleType
            };
            this.$set(this.peopleList, personIndex, updatedPerson);
            console.log('状态更新成功，新状态:', updatedPerson.status);
          }
        }
        
        return true;
      } catch (error) {
        console.error('设置人员状态失败:', error);
        this.$message.error('设置人员状态失败，请稍后重试');
        return false;
      }
    },
    
    // 根据状态获取标签类型
    getTagType(status) {
      switch(status) {
        case '在营': return 'success';
        case '外出': return 'danger';
        default: return 'info';
      }
    },
    
    // 表格行样式
    tableRowClassName({ row, rowIndex }) {
      return 'log-row';
    },
    
    // 获取人员出入记录列表（支持分页和人员筛选）
    async fetchAccessRecords(page = this.pagination.currentPage, size = this.pagination.pageSize, person = null) {
      try {
        const params = {
          pageNum: page,
          pageSize: size
        };
        
        // 如果传入了人员对象，添加人员信息到查询参数
        if (person) {
          params.userId = person.userId;
          params.employeeNumber = person.employeeNumber;
          params.userName = person.name;
          params.deptId = person.deptId;
          params.deptName = person.dept;
        }
        
        const result = await getPeopleAccessRecords(params);
        if (result.code === 200) {
          // 更新分页总数
          this.pagination.total = result.total || 0;
          // 转换后端数据为前端需要的格式
        this.logList = (result.rows || []).map(record => {
          // 根据后端返回的userId或其他信息判断人员类型
          // 这里假设后端返回的ID格式或其他字段可以区分员工和访客
          // 如果后端有明确标识，应该使用后端提供的字段
          let peopleType = '员工';
          // 简单判断：如果ID长度或格式特殊可能是访客，或者根据部门名称判断
          if (record.deptName && record.deptName.includes('访客') || 
              record.userId && typeof record.userId === 'string' && record.userId.startsWith('V')) {
            peopleType = '访客';
          }
          
          return {
            name: record.userName,
            ID: record.userId,
            employeeNumber: record.employeeNumber, 
            deptName: record.deptName,
            action: record.action,
            time: record.time,
            peopleType: peopleType
          };
        });
          console.log('获取出入记录成功:', this.logList);
        }
      } catch (error) {
        console.error('获取出入记录失败:', error);
        this.$message.error('获取出入记录失败，请稍后重试');
      }
    },
    
    // 分页切换
    handlePageChange(currentPage) {
      this.pagination.currentPage = currentPage;
      // 如果有选中人员，传入选中人员信息
      const selectedPerson = this.peopleList.find(p => p.employeeNumber === this.selectedEmployeeNumber);
      this.fetchAccessRecords(currentPage, this.pagination.pageSize, selectedPerson);
    },
    
    // 每页大小切换
    handleSizeChange(pageSize) {
      this.pagination.pageSize = pageSize;
      this.pagination.currentPage = 1;
      // 如果有选中人员，传入选中人员信息
      const selectedPerson = this.peopleList.find(p => p.employeeNumber === this.selectedEmployeeNumber);
      this.fetchAccessRecords(1, pageSize, selectedPerson);
    },
    
    // 人脸识别（调用后端API）
    async mockRecognition(person, type) {
      // 显示加载提示
      const loading = this.$loading({
        lock: true,
        text: '正在处理...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      
      try {
        // 调用后端API设置状态
        const success = await this.setPersonStatus(person, type, person.peopleType);
        
        if (success) {
          // 显示操作成功提示
          this.$message({
            message: `${person.name} ${type === '入' ? '入营' : '出营'}成功`,
            type: 'success',
            duration: 2000
          });
          
          // 重新获取后端的出入记录列表，确保数据与后端同步
          // 重置到第一页，显示最新记录
          this.pagination.currentPage = 1;
          // 如果有选中人员，传入选中人员信息
          const selectedPerson = this.peopleList.find(p => p.employeeNumber === this.selectedEmployeeNumber);
          await this.fetchAccessRecords(1, this.pagination.pageSize, selectedPerson);
        }
      } catch (error) {
        console.error('操作失败:', error);
      } finally {
        // 关闭加载提示
        loading.close();
      }
    },
    
    // 根据工号/访客号模拟识别
    async mockRecognitionByNumber(employeeNumber) {
      // 查找对应的人员
      const person = this.peopleList.find(p => p.employeeNumber === employeeNumber);
      if (person) {
        // 选择该人员
        this.selectedEmployeeNumber = person.employeeNumber;
        // 切换状态
        const newStatus = person.status === '在营' ? '出' : '入';
        await this.mockRecognition(person, newStatus);
      } else {
        // 如果本地未找到，尝试通过接口查询并直接切换状态
        this.$confirm(`未在本地找到编号为 ${employeeNumber} 的记录，是否直接切换状态？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          const loading = this.$loading({
            lock: true,
            text: '正在处理...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });
          
          try {
            // 默认设为入营状态
            const newStatus = 1;
            await setStatusByEmployeeNumber(employeeNumber, newStatus);
            this.$message.success(`设置入营状态成功`);
            
            // 重新获取所有数据
            await this.fetchPeopleStatus();
            await this.fetchAccessRecords();
          } catch (error) {
            console.error('设置状态失败:', error);
            this.$message.error('设置状态失败');
          } finally {
            loading.close();
          }
        }).catch(() => {
          // 取消操作
        });
      }
    },
    // 处理搜索输入
    handleSearchInput(value) {
      console.log('输入事件触发:', value);
      // 直接赋值确保响应式更新
      this.searchKeyword = value;
      // 强制刷新视图（如果需要）
      this.$forceUpdate();
    },
    
    // 获取占位文本
    getPlaceholderText() {
      switch(this.searchType) {
        case 'name': return '姓名';
        case 'employeeNumber': return '工号';
        case 'visitorNumber': return '访客号';
        case 'dept': return '单位/部门';
        case 'phone': return '手机号';
        default: return '姓名';
      }
    },
    
    // 清除搜索
    handleSearchClear() {
      this.searchKeyword = '';
      this.$forceUpdate();
    },
    
    // 选中人员
    selectPerson(person) {
      if (this.selectedEmployeeNumber === person.employeeNumber) {
        // 如果点击已选中的人员，则取消选中
        this.selectedEmployeeNumber = null;
        this.$message.info('已取消选中人员');
        // 重置分页到第一页
        this.pagination.currentPage = 1;
        // 重新获取所有记录，不传人员信息
        this.fetchAccessRecords(1, this.pagination.pageSize, null);
      } else {
        // 选中新的人员
        this.selectedEmployeeNumber = person.employeeNumber;
        this.$message.success(`已选中 ${person.name}，查看其出入记录`);
        // 重置分页到第一页
        this.pagination.currentPage = 1;
        // 重新获取记录，传入选中人员的信息
        this.fetchAccessRecords(1, this.pagination.pageSize, person);
      }
    },
    
    // 获取选中人员姓名
    getSelectedPersonName() {
      if (!this.selectedEmployeeNumber) return '';
      const person = this.peopleList.find(p => p.employeeNumber === this.selectedEmployeeNumber);
      return person ? person.name : '';
    },
    
    // 清除选中人员
    clearSelectedPerson() {
      this.selectedEmployeeNumber = null;
      this.$message.info('已清除人员筛选');
      this.pagination.currentPage = 1;
      // 重新获取所有记录，不传人员信息
      this.fetchAccessRecords(1, this.pagination.pageSize, null);
    },
    
    // 切换人员类型筛选
    changePeopleType(type) {
      this.peopleType = type;
      this.fetchPeopleStatus(); // 重新获取数据
    }
  }
};
</script>

<style scoped>
/* 全局样式 */
.app-container {
  height: 100vh;
  background: linear-gradient(135deg, #0c1624 0%, #1a2a40 100%);
  padding: 20px;
  color: #e3f2fd;
  margin: 0;
  overflow-x: hidden;
}

/* 页面标题已移除 */

/* 主内容区域 */
.content-wrapper {
  display: flex;
  gap: 20px;
  height: calc(100vh - 80px);
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  box-sizing: border-box;
  overflow-x: hidden;
  position: relative;
}

/* 人员区域 */
.people-section {
  flex: 1;
  background: rgba(26, 42, 64, 0.8);
  border-radius: 12px;
  padding: 20px;
  border: 1px solid rgba(56, 142, 255, 0.3);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.3);
  display: flex;
  flex-direction: column;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid rgba(56, 142, 255, 0.3);
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #ffffff;
}

.section-stats {
  font-size: 14px;
  color: #64b5f6;
}

/* 搜索框样式 */
.search-wrapper {
  margin-bottom: 15px;
}

.search-wrapper > .search-type-select {
  margin-right: 10px;
}

.search-wrapper > .search-input {
  width: calc(100% - 130px); /* 确保输入框和选择器一起占满整行 */
}

.type-filter {
  margin-top: 10px;
  display: flex;
  justify-content: flex-start;
}

.type-filter .el-radio-group {
  background: rgba(26, 42, 64, 0.6);
  border-radius: 6px;
  padding: 2px;
}

.type-filter .el-radio-button {
  margin: 0;
}

.type-filter .el-radio-button__inner {
  border: none;
  background: transparent;
  color: #90caf9;
  min-width: 80px;
  border-radius: 4px;
}

.type-filter .el-radio-button__inner:hover {
  color: #e3f2fd;
  background: rgba(56, 142, 255, 0.2);
}

.type-filter .is-active .el-radio-button__inner {
  background: #388eff;
  color: white;
}

.type-filter .el-radio-button:first-child .el-radio-button__inner {
  border-radius: 4px;
}

.type-filter .el-radio-button:last-child .el-radio-button__inner {
  border-radius: 4px;
}

.search-input {
  width: 100%;
}

.search-input ::v-deep .el-input__inner {
  background: rgba(26, 42, 64, 0.8);
  border: 1px solid rgba(56, 142, 255, 0.3);
  color: #e3f2fd;
  border-radius: 8px;
  padding: 0 15px 0 35px;
  height: 40px;
  transition: all 0.3s ease;
}

.search-input ::v-deep .el-input__inner:focus {
  border-color: #388eff;
  box-shadow: 0 0 0 2px rgba(56, 142, 255, 0.2);
}

.search-input ::v-deep .el-input__inner::placeholder {
  color: #64b5f6;
}

.search-input ::v-deep .el-input__prefix {
  left: 12px;
  color: #64b5f6;
}

.search-input ::v-deep .el-input__suffix {
  right: 12px;
  color: #64b5f6;
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #ffffff;
}

.section-stats {
  font-size: 14px;
  color: #64b5f6;
}

/* 人员卡片列表 */
.people-cards {
  display: flex;
  flex-direction: column;
  gap: 12px;
  overflow-y: auto;
  flex: 1;
}

.people-cards::-webkit-scrollbar {
  width: 6px;
}

.people-cards::-webkit-scrollbar-track {
  background: rgba(26, 42, 64, 0.5);
  border-radius: 3px;
}

.people-cards::-webkit-scrollbar-thumb {
  background: rgba(56, 142, 255, 0.6);
  border-radius: 3px;
}

/* 人员卡片 */
.person-card {
  background: rgba(26, 42, 64, 0.6);
  border: 1px solid rgba(56, 142, 255, 0.3);
  border-radius: 8px;
  padding: 12px;
  margin-bottom: 8px;
  display: flex;
  align-items: center;
  gap: 12px;
  transition: all 0.3s ease;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  min-height: 120px;
}

.person-card:hover {
  background: rgba(26, 42, 64, 0.8);
  border-color: rgba(56, 142, 255, 0.5);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(56, 142, 255, 0.3);
}

/* 选中状态样式 */
.person-card.person-selected {
  background: rgba(56, 142, 255, 0.2);
  border-color: #388eff;
  box-shadow: 0 0 0 2px rgba(56, 142, 255, 0.3), 0 4px 12px rgba(56, 142, 255, 0.4);
}

.person-card.person-selected::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 4px;
  height: 100%;
  background: #388eff;
}

.person-card.person-in {
  border-color: rgba(76, 175, 80, 0.4);
  background: rgba(76, 175, 80, 0.05);
}

.person-card.visitor-in {
  border-color: rgba(255, 152, 0, 0.4);
  background: rgba(255, 152, 0, 0.05);
}

.person-card.person-out {
  border-color: rgba(244, 67, 54, 0.4);
  background: rgba(244, 67, 54, 0.05);
}

.person-card.person-unknown {
  border-color: rgba(56, 142, 255, 0.4);
  background: rgba(56, 142, 255, 0.05);
}

/* 人员头像 */
.person-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  font-weight: 600;
  color: white;
  box-shadow: 0 2px 10px rgba(25, 118, 210, 0.4);
  flex-shrink: 0;
  position: relative;
}

.employee-avatar {
  background: linear-gradient(45deg, #1976d2, #42a5f5);
}

.visitor-avatar {
  background: linear-gradient(45deg, #ff9800, #ffc107);
  box-shadow: 0 2px 10px rgba(255, 152, 0, 0.4);
}

.person-type-tag {
  position: absolute;
  bottom: -5px;
  right: -5px;
  font-size: 8px;
  padding: 2px 4px;
  border-radius: 8px;
  font-weight: 500;
}

.employee-tag {
  background: #1976d2;
  color: white;
}

.visitor-tag {
  background: #ff9800;
  color: white;
}

/* 人员信息 */
.person-info {
  flex: 1;
}

.person-name {
  font-size: 14px;
  font-weight: 600;
  color: #ffffff;
  margin-bottom: 2px;
}

.person-id,
.person-dept,
.person-idcard,
.person-phone {
  font-size: 12px;
  color: #90caf9;
  margin-bottom: 1px;
}

.person-status {
  margin-top: 3px;
}

/* 状态按钮样式 */
.status-button {
  min-width: 60px !important;
  height: 24px !important;
  line-height: 24px !important;
  padding: 0 8px !important;
  font-size: 11px !important;
  border-radius: 12px !important;
  font-weight: 500 !important;
  border: none !important;
  cursor: default !important;
  transform: none !important;
  box-shadow: none !important;
}

.status-button.is-disabled {
  opacity: 1 !important;
}

.status-button.el-button--success.is-disabled {
  background-color: rgba(76, 175, 80, 0.8) !important;
  border-color: rgba(76, 175, 80, 0.8) !important;
  color: #ffffff !important;
}

.status-button.el-button--danger.is-disabled {
  background-color: rgba(244, 67, 54, 0.8) !important;
  border-color: rgba(244, 67, 54, 0.8) !important;
  color: #ffffff !important;
}

/* 人员操作按钮 */
.person-actions {
  display: flex;
  flex-direction: column;
  gap: 6px;
  align-items: flex-end;
  flex-shrink: 0;
}

.el-button {
  transition: all 0.3s ease;
  min-width: 80px;
}

.el-button:hover:not(:disabled) {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(56, 142, 255, 0.3);
}

.el-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* 日志区域 */
.log-section {
  flex: 2;
  width: 100%;
  box-sizing: border-box;
  min-width: 0; /* 防止flex子元素溢出 */
}

.log-card {
  height: 100%;
  background: rgba(26, 42, 64, 0.8);
  border: 1px solid rgba(56, 142, 255, 0.3);
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.3);
  margin: 0 !important;
  padding: 0 !important;
}

.log-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: rgba(26, 42, 64, 0.95);
  padding: 15px 20px;
  border-bottom: 1px solid rgba(56, 142, 255, 0.3);
}

.log-header-actions {
  display: flex;
  align-items: center;
  gap: 10px;
}

.log-title {
  font-size: 16px;
  font-weight: 600;
  color: #ffffff;
  display: flex;
  align-items: center;
  gap: 8px;
}

.log-stats {
  font-size: 14px;
  color: #64b5f6;
}

/* 日志表格 */
.log-table-wrapper {
  height: calc(100% - 60px);
  display: flex;
  flex-direction: column;
  width: 100%;
  box-sizing: border-box;
  position: relative;
}

.log-table {
  background: transparent;
  width: 100% !important;
  box-sizing: border-box;
  height: 100%;
}

.log-table .el-table__header {
  background: rgba(26, 42, 64, 0.95);
}

.log-table .el-table__header th {
  background: transparent !important;
  color: #e3f2fd !important;
  border-bottom: 1px solid rgba(56, 142, 255, 0.3) !important;
  font-weight: 600 !important;
  padding: 12px 10px !important;
}

.log-table .el-table__body-wrapper {
  overflow-y: auto;
  height: calc(100% - 40px);
  width: 100% !important;
  overflow-x: hidden;
}

.log-table .el-table__body-wrapper::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

.log-table .el-table__body-wrapper::-webkit-scrollbar-track {
  background: rgba(26, 42, 64, 0.5);
  border-radius: 3px;
}

.log-table .el-table__body-wrapper::-webkit-scrollbar-thumb {
  background: rgba(56, 142, 255, 0.6);
  border-radius: 3px;
}

.log-row {
  transition: all 0.2s ease;
  background: rgba(26, 42, 64, 0.7) !important;
}

.log-row:hover {
  background: rgba(26, 42, 64, 0.9) !important;
}

.log-table .el-table__row td {
  border-bottom: 1px solid rgba(56, 142, 255, 0.2) !important;
  color: #e3f2fd !important;
  padding: 12px 10px !important;
}

.log-name {
  font-weight: 600;
  color: #ffffff;
}

.ml-1 {
  margin-left: 4px;
}

.ml-2 {
  margin-left: 8px;
}

.action-tag {
  font-weight: 600;
}

/* 空状态 */
.empty-log {
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.el-empty {
  color: #64b5f6;
}

/* 确保Element UI组件正确适配 */
.el-table,
.log-table {
  --el-table-bg-color: transparent !important;
  --el-table-header-bg-color: transparent !important;
  --el-table-row-hover-bg-color: rgba(26, 42, 64, 0.9) !important;
  --el-table-border-color: rgba(56, 142, 255, 0.2) !important;
  background-color: transparent !important;
  width: 100% !important;
}

.pagination-wrapper{
  padding: 0 !important;
  margin: 0 !important;
}

/* 表格容器宽度确保 - 加强版 */
.log-table-wrapper .el-table__body-wrapper {
  width: 100% !important;
  min-width: 0 !important;
  max-width: 100% !important;
  overflow-x: hidden !important;
  overflow-y: auto !important;
  box-sizing: border-box !important;
  position: relative;
}

/* 强制表格列自适应 */
.el-table th.gutter,
.el-table td.gutter {
  width: 0 !important;
  padding: 0 !important;
}

/* 防止表格内容溢出 */
.el-table th,
.el-table td {
  white-space: nowrap;
  text-overflow: ellipsis;
  box-sizing: border-box !important;
  padding: 12px 8px !important; /* 减少内边距 */
  max-width: 150px; /* 限制单元格最大宽度 */
}

/* 确保没有额外的内边距影响宽度 */
/deep/ .log-card .el-card__body {
  padding: 0 !important;
  margin: 0 !important;
}

/* 确保卡片无边距 - 修复版本 */
.el-card__body {
  padding: 0 !important;
  margin: 0 !important;
  height: 100% !important; /* 使用全部高度 */
  display: flex;
  flex-direction: column;
  overflow: hidden; /* 防止内容溢出 */
}

/* 确保表格行宽度 */
.el-table__row {
  width: 100% !important;
  box-sizing: border-box !important;
}

/* 表头样式强制覆盖 */
.el-table th,
.el-table thead th,
.log-table th,
.log-table thead th {
  background-color: rgba(26, 42, 64, 0.95) !important;
  color: #e3f2fd !important;
  border-bottom: 1px solid rgba(56, 142, 255, 0.3) !important;
  font-weight: 600 !important;
  padding: 12px 10px !important;
}

/* 表格行样式强制覆盖 */
.el-table td,
.el-table tr,
.log-table td,
.log-table tr {
  background-color: transparent !important;
  border-bottom: 1px solid rgba(56, 142, 255, 0.2) !important;
  color: #e3f2fd !important;
}

/* 确保行背景色 */
.el-table__row {
  background-color: rgba(26, 42, 64, 0.7) !important;
}

.el-table__row:hover {
  background-color: rgba(26, 42, 64, 0.9) !important;
}

/* 确保标签样式 */
.el-tag {
  border-radius: 4px;
  padding: 0 8px;
  height: 24px;
  line-height: 22px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .app-container {
    padding: 10px;
  }
  
  .content-wrapper {
    flex-direction: column;
    height: auto;
    min-height: calc(100vh - 20px);
  }
  
  .people-section,
  .log-section {
    height: auto;
    min-height: 300px;
  }
  
  .log-table-wrapper {
    height: 400px;
  }
}

/* 针对不同屏幕宽度的优化 */
@media (max-width: 1200px) {
  .content-wrapper {
    gap: 15px;
  }
  
  .el-table th,
  .el-table td {
    padding: 10px 6px !important;
    font-size: 13px;
  }
  
  .people-section {
    flex: 0.8;
  }
}

/* 动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>

/* 状态标签 */
.status-tag {
  display: inline-block;
  padding: 2px 6px;
  border-radius: 10px;
  font-size: 10px;
  font-weight: 500;
  text-align: center;
  min-width: 45px;
}

.status-tag.in {
  background: rgba(76, 175, 80, 0.2);
  color: #4caf50;
  border: 1px solid rgba(76, 175, 80, 0.4);
}

.status-tag.out {
  background: rgba(255, 152, 0, 0.2);
  color: #ff9800;
  border: 1px solid rgba(255, 152, 0, 0.4);
}

/* 分页组件样式 - 修复版本 */
.log-header-actions .pagination-wrapper {
  margin: 0 !important;
  padding: 0 !important;
  flex-shrink: 0;
  display: block !important; /* 覆盖父容器的flex布局 */
}

/* Element UI 分页组件样式覆盖 */
.el-pagination {
  color: #e3f2fd !important;
  font-size: 12px !important;
}

.el-pagination .el-pager li {
  background: rgba(26, 42, 64, 0.8) !important;
  color: #e3f2fd !important;
  border: 1px solid rgba(56, 142, 255, 0.3) !important;
  margin: 0 2px !important;
  min-width: 28px !important;
  height: 28px !important;
  line-height: 26px !important;
}

.el-pagination .el-pager li:hover {
  background: rgba(56, 142, 255, 0.3) !important;
}

.el-pagination .el-pager li.active {
  background: #388eff !important;
  color: #ffffff !important;
  border-color: #388eff !important;
}

.el-pagination .btn-prev,
.el-pagination .btn-next {
  background: rgba(26, 42, 64, 0.8) !important;
  color: #e3f2fd !important;
  border: 1px solid rgba(56, 142, 255, 0.3) !important;
  min-width: 28px !important;
  height: 28px !important;
}

.el-pagination .btn-prev:hover,
.el-pagination .btn-next:hover {
  background: rgba(56, 142, 255, 0.3) !important;
}

.el-pagination .el-pagination__sizes {
  margin: 0 10px !important;
}

.el-pagination .el-select .el-input .el-input__inner {
  background: rgba(26, 42, 64, 0.8) !important;
  color: #e3f2fd !important;
  border: 1px solid rgba(56, 142, 255, 0.3) !important;
}

.el-pagination .el-pagination__jump {
  color: #e3f2fd !important;
  margin-left: 10px !important;
}

.el-pagination .el-pagination__total {
  color: #e3f2fd !important;
  margin-right: 10px !important;
}
