# 智能督查管理平台

<div align="center">

![Version](https://img.shields.io/badge/version-3.7.0-blue.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)
![Vue](https://img.shields.io/badge/Vue-2.6.12-brightgreen.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5.13-brightgreen.svg)

一套功能完善的部队智能督查管理系统，涵盖人员管理、物资管理、装备管理、医疗健康、安防监控、视频监控等全方位管理功能。

</div>

---

## 📋 目录

- [项目简介](#项目简介)
- [功能特性](#功能特性)
- [技术栈](#技术栈)
- [系统架构](#系统架构)
- [快速开始](#快速开始)
- [项目结构](#项目结构)
- [核心亮点](#核心亮点)
- [贡献指南](#贡献指南)
- [许可证](#许可证)

---

## 🎯 项目简介

智能督查管理平台是一款专为部队管理设计的综合信息化管理系统，通过前后端分离架构，实现对部队人员、物资、装备、医疗、安防等全方位的数字化管理。系统集成了海康威视设备和ZLMediaKit流媒体服务，支持实时视频监控、人员出入管控、门禁管理等功能。

---

## ✨ 功能特性

### 🏢 系统管理
- 用户管理 - 用户信息维护与权限分配
- 角色管理 - 角色权限配置
- 菜单管理 - 动态菜单配置
- 部门管理 - 组织架构管理
- 岗位管理 - 岗位信息维护
- 字典管理 - 系统字典配置
- 参数配置 - 系统参数维护
- 通知公告 - 公告发布与管理
- 日志管理 - 操作日志与登录日志
- 定时任务 - Quartz定时任务调度
- 系统监控 - 服务监控与缓存监控

### 👥 人员管理
- 人员基本信息管理
- 借调管理与转岗管理
- 考核管理与类型统计
- 访客管理

### 🚪 人员出入
- 请假管理
- 外借管理
- 人员通行记录
- 访客管理
- 离队管理

### 🚗 车辆管理
- 军车管理
- 公车管理与申请
- 司机管理
- 公车通行日志
- 私车管理与通行日志

### 🏠 营房管理
- 营房信息管理
- 入住管理
- 房间与楼栋管理
- 维修记录与缺陷管理

### 📦 库房管理
- 物资登记
- 入库/出库管理
- 配送管理
- 物品变动记录
- 库房统计与档案管理

### 🏥 医疗卫生
- 医疗公告与防疫管理
- 药品档案与领用管理
- 消耗品管理
- 诊断与检查管理
- 疗养管理

### 📊 态势分析
- 态势分析可视化大屏
- 数据统计与图表展示
- 实时数据监控

### 📺 安防监控
- 监控管理与预览
- 摄像头管理
- 支持FLV、HLS流媒体播放
- 集成海康威视视频控制

### 🔐 门禁管理
- 门禁控制
- 门禁信息管理
- 人员信息管理
- 集成海康威视门禁系统

### 🧱 基层登记
- 人员花名册
- 战士管理
- 周工作计划
- 食谱管理
- 会议记录
- 装备与武器管理
- 统计分析

### ⚔️ 战备值班
- 基本值班信息
- 基层值班信息
- 军事值班信息
- 团队值班信息
- 值班记录与统计

### 📱 思想政治教育
- 教员管理
- 会议记录
- 谈话记录
- 思想计划与周工作

### 📄 规章制度
- 文件信息管理
- 目录信息管理

### 📋 工作安排
- 工作任务管理
- 工作计划与日历
- 工作计划任务

### 🖨️ 集中文印
- 文印管理
- 文印日志
- 个人日志

---

## 🛠️ 技术栈

### 前端技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 2.6.12 | 前端框架 |
| Vue Router | 3.4.9 | 路由管理 |
| Vuex | 3.6.0 | 状态管理 |
| Element UI | 2.15.6 | UI组件库 |
| ECharts | 5.6.0 | 数据可视化 |
| @jiaminghi/data-view | 2.10.0 | 大屏展示 |
| Axios | 0.21.0 | HTTP请求 |
| flv.js | 1.6.2 | FLV视频播放 |
| hls.js | 1.6.14 | HLS流媒体播放 |
| video.js | 8.23.4 | 视频播放器 |
| Less / Sass | 4.4.1 / 1.32.13 | CSS预处理器 |
| jsencrypt | 3.0.0-rc.1 | 加密工具 |

### 后端技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 1.8 | 开发语言 |
| Spring Boot | 2.5.13 | 核心框架 |
| MyBatis | 2.2.2 | ORM框架 |
| PageHelper | 1.4.1 | 分页插件 |
| MySQL | 8.0.22 | 主数据库 |
| Redis | - | 缓存数据库 |
| Druid | 1.2.8 | 数据库连接池 |
| Swagger | 3.0.0 | API文档 |
| JWT | 0.9.1 | Token认证 |
| Quartz | - | 定时任务 |
| EasyExcel | 3.1.1 | Excel处理 |
| Apache POI | 4.1.2 | Office文档处理 |
| Hutool | 5.8.5 | 工具包 |
| FastJSON | 2.0.51 | JSON处理 |
| ZLMediaKit | - | 流媒体服务 |

---

## 🏗️ 系统架构

```
智能督查管理平台
├── 前端层 (ducha-qianduan)
│   ├── Vue 2.6 + Vue Router + Vuex
│   ├── Element UI 组件库
│   ├── ECharts 数据可视化
│   ├── flv.js/hls.js 视频播放
│   └── WebSocket 实时通信
│
├── 后端层 (ducha-houduan)
│   ├── supervision-admin      # Web服务入口
│   ├── supervision-system     # 系统业务模块
│   ├── supervision-common     # 通用工具模块
│   ├── supervision-framework  # 框架核心模块
│   ├── supervision-quartz     # 定时任务模块
│   └── supervision-generator  # 代码生成模块
│
├── 数据层
│   ├── MySQL 主数据库
│   ├── Redis 缓存
│   └── SQL Server 从数据库（支持多数据源）
│
├── 外部服务
│   ├── 海康威视 SDK（门禁、监控）
│   ├── ZLMediaKit（流媒体服务）
│   └── Swagger API文档
│
└── 安全层
    ├── JWT Token 认证
    ├── RBAC 权限控制
    ├── SQL 注入防护
    └── XSS 攻击防护
```

---

## 🚀 快速开始

### 前端部署

```bash
# 克隆项目
git clone https://github.com/your-username/ducha-qianduan.git
cd ducha-qianduan

# 安装依赖
npm install

# 开发环境运行
npm run dev

# 生产环境构建
npm run build:prod

# 预发布环境构建
npm run build:stage
```

### 后端部署

```bash
# 克隆项目
git clone https://github.com/your-username/ducha-houduan.git
cd ducha-houduan

# 使用Maven编译
mvn clean install

# 运行主模块
cd supervision-admin
mvn spring-boot:run
```

### 环境配置

**前端环境变量**（`.env.development`）：
```env
VUE_APP_TITLE=智能督查管理平台
VUE_APP_BASE_API=/dev-api
VUE_APP_WS_URL=ws://localhost:8090/websocket
```

**后端数据库配置**（`application-druid.yml`）：
```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/newducha?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull
    username: root
    password: your-password
```

**Redis配置**（`application.yml`）：
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password:
    database: 0
```

---

## 📁 项目结构

### 前端项目结构

```
ducha-qianduan/
├── bin/                      # 可执行文件
├── build/                    # 构建脚本
├── public/                   # 静态资源
│   ├── assets/               # 资源文件
│   ├── codebase/             # 代码库（视频控制等）
│   └── index.html            # 入口HTML
├── src/
│   ├── api/                  # API接口（140+接口文件）
│   ├── assets/               # 静态资源
│   ├── components/           # 公共组件
│   ├── directive/            # 自定义指令
│   ├── layout/               # 布局组件
│   ├── plugins/              # 插件
│   ├── router/               # 路由配置
│   ├── store/                # Vuex状态管理
│   ├── utils/                # 工具函数
│   ├── views/                # 页面视图（20+业务模块）
│   ├── App.vue               # 根组件
│   └── main.js               # 入口文件
├── .env.development          # 开发环境配置
├── .env.production           # 生产环境配置
├── package.json              # 项目配置
└── vue.config.js             # Vue CLI配置
```

### 后端项目结构

```
ducha-houduan/
├── supervision-admin/         # Web服务入口模块
├── supervision-system/        # 系统业务模块
│   └── src/main/java/com/supervision/system/
│       ├── controller/        # 控制器层（201个控制器）
│       ├── service/           # 服务层（180个服务）
│       ├── mapper/            # 数据访问层（182个Mapper）
│       └── domain/            # 实体类
├── supervision-common/        # 通用工具模块
├── supervision-framework/     # 框架核心模块
├── supervision-quartz/        # 定时任务模块
├── supervision-generator/     # 代码生成模块
├── pom.xml                    # 主Maven配置
└── application.yml            # 应用配置
```

---

## 🌟 核心亮点

### 1. 完善的权限系统
- 基于RBAC的角色权限控制
- 动态路由与菜单配置
- 细粒度的按钮级权限控制

### 2. 实时视频监控
- 支持FLV、HLS等多种流媒体格式
- 集成海康威视视频控制系统
- 多路视频同时预览

### 3. 数据可视化大屏
- 专业的态势分析大屏展示
- 实时数据监控与图表分析
- 响应式大屏适配

### 4. 智能安防集成
- 门禁控制与人员出入管理
- 车辆识别与抓拍图片处理
- 人脸识别集成

### 5. 高效的文档处理
- PDF、Word文档在线预览
- Excel导入导出
- 自定义Word模板导出

### 6. 实时通信
- WebSocket实时消息推送
- 在线用户状态监控
- 实时数据更新

### 7. 多数据源支持
- MySQL主数据库
- Redis缓存
- 支持SQL Server多数据源配置

### 8. 代码规范与优化
- ESLint代码检查
- 前后端代码分离
- 代码分割与懒加载
- 完善的API文档（Swagger）

### 9. 丰富的业务功能
- 20+个业务模块
- 140+个前端API接口
- 201个后端控制器
- 覆盖部队管理全方位需求

### 10. 灵活的配置管理
- 支持开发、预发布、生产多环境
- 动态配置字典与参数
- 定时任务可视化管理

---

## 📊 项目统计

### 前端项目
- **组件数量**: 30+ 公共组件
- **页面数量**: 100+ 业务页面
- **API接口**: 140+ 接口文件
- **业务模块**: 20+ 功能模块

### 后端项目
- **控制器**: 201个
- **服务层**: 180个
- **Mapper接口**: 182个
- **业务模块**: 30个
- **代码行数**: 100,000+

---

## 👥 贡献指南

欢迎对该项目进行贡献！请遵循以下步骤：

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

---

## 📝 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

---

## 📞 联系方式

如有问题或建议，欢迎通过以下方式联系：

- 提交 Issue
- 发送邮件
- 讨论区

---

<div align="center">

**感谢您使用智能督查管理平台！**

</div>