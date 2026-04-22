<template>
  <div class="tabs">
    <div class="tab-titles">
      <div
        v-for="(tab, index) in tabs"
        :key="index"
        :class="['tab-title', { active: activeTab === index }]"
        @click="setActiveTab(index)"
      >
        {{ tab.name }}
      </div>
    </div>
    <div class="tab-content">
      <component :is="tabs[activeTab].component" />
    </div>
  </div>
</template>

<script>
import TheBus from "../thebus/index.vue";
import PrivateCar from "../privateCar/index.vue";

export default {
  data() {
    return {
      activeTab: 0, // 默认选中的 tab
      tabs: [
        { name: "军车管理", component: TheBus },
        { name: "私家车管理", component: PrivateCar },
      ],
    };
  },
  methods: {
    setActiveTab(index) {
      this.activeTab = index;
    },
  },
};
</script>

<style scoped>
.tabs {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.tab-titles {
  display: flex;
  cursor: pointer;
  margin-bottom: 20px;
  border-bottom: 2px solid #ddd; /* Tab 底部增加一条边框 */
}

.tab-title {
  padding: 12px 20px;
  margin-right: 10px;
  background-color: #f0f0f0;
  border-radius: 8px 8px 0 0;
  transition:
    background-color 0.3s,
    transform 0.3s;
  font-weight: 500;
  font-size: 16px;
}

.tab-title:hover {
  background-color: #e0e0e0; /* hover时稍微变色 */
  transform: scale(1.05); /* 提高一点点击感 */
}

.tab-title.active {
  background-color: #007bff;
  color: white;
  box-shadow: 0 2px 8px rgba(0, 123, 255, 0.3);
}

.tab-content {
  width: 100%;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 添加内容区阴影 */
  transition:
    opacity 0.3s ease,
    transform 0.3s ease;
  margin-top: 20px;
  min-height: 200px; /* 保证内容区有最小高度 */
}

/* 响应式样式 */
@media (max-width: 768px) {
  .tab-titles {
    flex-direction: column; /* 小屏幕时让 Tab 垂直排列 */
    align-items: center;
  }

  .tab-title {
    margin-bottom: 10px;
    width: 100%;
    text-align: center;
  }

  .tab-content {
    padding: 15px;
    min-height: 150px;
  }
}
</style>
