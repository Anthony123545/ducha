import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes = [
  // {
  //   path: '/redirect',
  //   component: Layout,
  //   children: [
  //     {
  //       path: '/redirect/:path(.*)',
  //       component: (resolve) => require(['@/views/redirect.vue'], resolve)
  //     }
  //   ]
  // },


  // 首页区域
  {
    path: '/pt',
    component: (resolve) => require(['@/views/pt'], resolve),
    name: 'pt',
    hidden: true,
    children: [
      {
        path: 'index',
        component: (resolve) => require(['@/views/index'], resolve),
        name: 'Index',
        meta: { title: '主控台', icon: 'dashboard', affix: true }
      }
    ]
  },

  // 登录页
  {
    path: '/login',
    component: (resolve) => require(['@/views/login'], resolve),
    hidden: true
  },

  // 注册页
  {
    path: '/register',
    component: (resolve) => require(['@/views/register'], resolve),
    hidden: true
  },
  //
  // // 基层登记
  // {
  //   path: 'grassrootsregistration',
  //   // component: (resolve) => require(['@/views/PrintsLog/gerenlog/index'], resolve),
  //   // component: (resolve) => require(['@/views/PrintsLog/printlog/index'], resolve),
  //   // component: (resolve) => require(['@/views/PrintsManage/manage/index'], resolve),
  //   hidden: true
  // },
  //
  // // 集中文印
  // {
  //   path: '/print',
  //   // component: (resolve) => require(['@/views/PrintsLog/gerenlog/index'], resolve),
  //   // component: (resolve) => require(['@/views/PrintsLog/printlog/index'], resolve),
  //   component: (resolve) => require(['@/views/PrintsManage/manage/index'], resolve),
  //   hidden: true
  // },
  //
  // // 工作安排
  // {
  //   path: '/workingArrangements',
  //   // component: (resolve) => require(['@/views/workingArrangements/planTask/index'], resolve),
  //   // component: (resolve) => require(['@/views/workingArrangements/workCalendar/index'], resolve),
  //   // component: (resolve) => require(['@/views/workingArrangements/workTask/index'], resolve),
  //   component: (resolve) => require(['@/views/workingArrangements/worktaskplan/index'], resolve),
  //   hidden: true
  // },
  //
  // // 规章制度
  // {
  //   path: '/regulations',
  //   component: (resolve) => require(['@/views/regulations/fileinfo/index'], resolve),
  //   // component: (resolve) => require(['@/views/regulations/directoryinfo/index'], resolve),
  //   hidden: true
  // },
  //
  // 态势分析
  {
    path: '/taishi',
    component: (resolve) => require(['@/views/taishi/index'], resolve),
    hidden: true
  },
  {
    path: '/taishi1',
    component: (resolve) => require(['@/views/taishi/tanshi11'], resolve),
    hidden: true
  },
  {
    path: '/taishi11',
    component: (resolve) => require(['@/views/taishi/taishi1'], resolve),
    hidden: true
  },
  {
    path: '/taishi2',
    component: (resolve) => require(['@/views/taishi/taishi2'], resolve),
    hidden: true
  },
  {
    path: '/taishi3',
    component: (resolve) => require(['@/views/taishi/taishi3'], resolve),
    hidden: true
  },
  {
    path: '/taishi4',
    component: (resolve) => require(['@/views/taishi/taishi4'], resolve),
    hidden: true
  },
  //
  // // 战备值班
  // {
  //   path: '/combatduty',
  //   // component: (resolve) => require(['@/views/combatduty/basedutyinfo/index'], resolve),
  //   // component: (resolve) => require(['@/views/combatduty/grassdutyinfo/index'], resolve),
  //   // component: (resolve) => require(['@/views/combatduty/info/index'], resolve),
  //   // component: (resolve) => require(['@/views/combatduty/militarydutyinfo/index'], resolve),
  //   // component: (resolve) => require(['@/views/combatduty/PeopleStatistics/index'], resolve),
  //   // component: (resolve) => require(['@/views/combatduty/Quantitative/index'], resolve),
  //   // component: (resolve) => require(['@/views/combatduty/RateOfTen/index'], resolve),
  //   // component: (resolve) => require(['@/views/combatduty/regisdutyinfo/index'], resolve),
  //   // component: (resolve) => require(['@/views/combatduty/taskdutyinfo/index'], resolve),
  //   component: (resolve) => require(['@/views/combatduty/teamdutyinfo/index'], resolve),
  //   hidden: true
  // },
  //
  // // 检查督导
  // {
  //   path: '/bdglregular',
  //   // component: (resolve) => require(['@/views/bdglregular/regularcountinfo/index'], resolve),
  //   // component: (resolve) => require(['@/views/bdglregular/regularinfo/index'], resolve),
  //   // component: (resolve) => require(['@/views/bdglregular/specialcountinfo/index'], resolve),
  //   component: (resolve) => require(['@/views/bdglregular/specialinfo/index'], resolve),
  //   hidden: true
  // },
  //
  // // 人员出入
  // {
  //   path: '/peopleChuRu',
  //   // component: (resolve) => require(['@/views/peopleChuRu/bdglLeave/index'], resolve),
  //   // component: (resolve) => require(['@/views/peopleChuRu/leaveOne/index'], resolve),
  //   component: (resolve) => require(['@/views/peopleChuRu/peopleBorrow/index'], resolve),
  //   hidden: true
  // },
  //
  // // 安防监控
  // {
  //   path: '/monitoring',
  //   component: (resolve) => require(['@/views/monitoring/brigadecamera/index'], resolve), // 添加监控信息
  //   // component: (resolve) => require(['@/views/monitoring/camera/index'], resolve), // 监控大屏预览
  //   // component: (resolve) => require(['@/views/monitoring/cameracp/index'], resolve), // 添加监控信息
  //   hidden: true
  // },
  //
  // // 门岗管理
  // {
  //   path: '/doormanManage',
  //   component: (resolve) => require(['@/views/doormanManage/peopleInfoManage/index'], resolve),
  //   hidden: true
  // },
  //
  // // 门禁管理
  // {
  //   path: '/access',
  //   // component: (resolve) => require(['@/views/access/accessControl/index'], resolve),
  //   // component: (resolve) => require(['@/views/access/Renaccess/index'], resolve),
  //   component: (resolve) => require(['@/views/access/renInformation/index'], resolve),
  //   hidden: true
  // },
  //
  // // 手机管理
  // {
  //   path: '/phone',
  //   component: (resolve) => require(['@/views/phone/grid/index'], resolve),
  //   // component: (resolve) => require(['@/views/regulations/directoryinfo/index'], resolve),
  //   hidden: true
  // },
  //
  // // 思想政治教育
  // {
  //   path: '/thought',
  //   // component: (resolve) => require(['@/views/thought/getready/index'], resolve),
  //   // component: (resolve) => require(['@/views/thought/parliament/index'], resolve),
  //   // component: (resolve) => require(['@/views/thought/planFujian/index'], resolve),
  //   // component: (resolve) => require(['@/views/thought/responsefu/index'], resolve),
  //   // component: (resolve) => require(['@/views/thought/responses/index'], resolve),
  //   // component: (resolve) => require(['@/views/thought/talk/index'], resolve),
  //   // component: (resolve) => require(['@/views/thought/teacher/index'], resolve), // 创建目录
  //   // component: (resolve) => require(['@/views/thought/teacherFile/index'], resolve),
  //   component: (resolve) => require(['@/views/thought/thoughtPlan/index'], resolve),
  //   // component: (resolve) => require(['@/views/thought/weekjob/index'], resolve),
  //   hidden: true
  // },
  //
  // // 人力资源
  // {
  //   path: '/deviceManage',
  //   // component: (resolve) => require(['@/views/deviceManage/examination/index'], resolve),
  //   // component: (resolve) => require(['@/views/deviceManage/people/index'], resolve), // 添加用户基本信息
  //   // component: (resolve) => require(['@/views/deviceManage/PersonnelStatistics/index'], resolve),
  //   // component: (resolve) => require(['@/views/deviceManage/seconded/index'], resolve),
  //   // component: (resolve) => require(['@/views/deviceManage/transfer/index'], resolve),
  //   component: (resolve) => require(['@/views/deviceManage/typestatistics/index'], resolve),
  //   hidden: true
  // },
  //
  // // 人力资源
  // {
  //   path: '/tool',
  //   // component: (resolve) => require(['@/views/tool/build/index'], resolve),
  //   component: (resolve) => require(['@/views/tool/gen/index'], resolve),
  //   // component: (resolve) => require(['@/views/tool/swagger/index'], resolve),
  //   hidden: true
  // },
  //
  // // 车辆管理
  // {
  //   path: '/militaryvehicleManagement',
  //   component: (resolve) => require(['@/views/militaryvehicleManagement/driver/index'], resolve),
  //   // component: (resolve) => require(['@/views/militaryvehicleManagement/privateAccessLog/index'], resolve),
  //   // component: (resolve) => require(['@/views/militaryvehicleManagement/privateCar/index'], resolve),
  //   // component: (resolve) => require(['@/views/militaryvehicleManagement/thebusAccessLog/index'], resolve),
  //   // component: (resolve) => require(['@/views/militaryvehicleManagement/thebus/index'], resolve),
  //   // component: (resolve) => require(['@/views/militaryvehicleManagement/thebusApply/index'], resolve),
  //   // component: (resolve) => require(['@/views/militaryvehicleManagement/thebusConfig/index'], resolve),
  //   // component: (resolve) => require(['@/views/militaryvehicleManagement/thebusList/index'], resolve),
  //   // component: (resolve) => require(['@/views/militaryvehicleManagement/thebusSi/index'], resolve),
  //   // component: (resolve) => require(['@/views/militaryvehicleManagement/thebusThemplate/index'], resolve),
  //   hidden: true
  // },
  //
  // // 营房管理
  // {
  //   path: '/barracksManagement',
  //   component: (resolve) => require(['@/views/barracksManagement/barracks/index'], resolve),
  //   // component: (resolve) => require(['@/views/barracksManagement/checkin/index'], resolve),
  //   // component: (resolve) => require(['@/views/barracksManagement/flat/index'], resolve),
  //   // component: (resolve) => require(['@/views/barracksManagement/flats/index'], resolve),
  //   // component: (resolve) => require(['@/views/barracksManagement/maintenanceRecord/index'], resolve),
  //   // component: (resolve) => require(['@/views/barracksManagement/repair/index'], resolve),
  //   // component: (resolve) => require(['@/views/barracksManagement/scadred/index'], resolve),
  //   hidden: true
  // },
  //
  // // 库房管理
  // {
  //   path: '/materialManagement',
  //   component: (resolve) => require(['@/views/materialManagement/archives/index'], resolve),
  //   // component: (resolve) => require(['@/views/materialManagement/distribution/index'], resolve),
  //   // component: (resolve) => require(['@/views/materialManagement/equipmentReceiving/index'], resolve),
  //   // component: (resolve) => require(['@/views/materialManagement/materialRegistration/index'], resolve),
  //   // component: (resolve) => require(['@/views/materialManagement/propertyChange/index'], resolve),
  //   // component: (resolve) => require(['@/views/materialManagement/WarehouseStatistics/index'], resolve),
  //   hidden: true
  // },
  //
  // // 医疗卫生管理
  // {
  //   path: '/medicalhealth',
  //   component: (resolve) => require(['@/views/medicalhealth/announcement/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/antiepidemicamount/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/antiepidemicarchives/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/antiepidemicguoqi/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/antiepidemiclingyong/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/antiepidemicputin/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/antiepidemicputout/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/antiepidemicruin/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/archives/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/bdgldurgarchives/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/bdgldurgguoqi/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/bdgldurglingyong/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/bdgldurgputin/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/bdgldurgputout/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/bdgldurgruin/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/consumeguoqi/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/consumelingyong/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/antiepidemicputin/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/antiepidemicputout/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/antiepidemicruin/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/diagnose/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/examine/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/examine2/index'], resolve),
  //   // component: (resolve) => require(['@/views/medicalhealth/recuperate/index'], resolve),
  //   hidden: true
  // },
  //
  // // 涉密载体外借
  // {
  //   path: '/bdglSecret',
  //   component: (resolve) => require(['@/views/bdglSecret/secret/index'], resolve),
  //   // component: (resolve) => require(['@/views/bdglSecret/secretaccount/index'], resolve),
  //   hidden: true
  // },
  //
  // // 系统管理
  // {
  //   path: '/system',
  //   component: (resolve) => require(['@/views/system/config/index'], resolve),
  //   // component: (resolve) => require(['@/views/system/cookcategory/index'], resolve),
  //   // component: (resolve) => require(['@/views/system/dept/index'], resolve),
  //   // component: (resolve) => require(['@/views/system/diary/index'], resolve),
  //   // component: (resolve) => require(['@/views/system/dict/index'], resolve),
  //   // component: (resolve) => require(['@/views/system/menu/index'], resolve),
  //   // component: (resolve) => require(['@/views/system/notice/index'], resolve),
  //   // component: (resolve) => require(['@/views/system/post/index'], resolve),
  //   // component: (resolve) => require(['@/views/system/role/index'], resolve),
  //   // component: (resolve) => require(['@/views/system/time/index'], resolve),
  //   // component: (resolve) => require(['@/views/system/training/index'], resolve),
  //   // component: (resolve) => require(['@/views/system/user/index'], resolve),
  //   hidden: true
  // },

  {
    path: '/peopleInfoManage',
    name: 'bdglVisit',
    component: () => import('@/views/doormanManage/peopleInfoManage/index.vue'),
    hidden: true,
    meta: { title: '设备详情' }
  },

  // 一周食谱（新版）
  {
    path: '/grassrootsregistration/weekRecipe',
    component: Layout,
    children: [
      {
        path: '',
        component: (resolve) => require(['@/views/grassrootsregistration/weekRecipe/index'], resolve),
        name: 'WeekRecipe',
        meta: { title: '一周食谱（新版）', icon: 'form' }
      }
    ]
  },


  // 报错区域
  {
    path: '/404',
    component: (resolve) => require(['@/views/error/404'], resolve),
    hidden: true
  },
  {
    path: '/401',
    component: (resolve) => require(['@/views/error/401'], resolve),
    hidden: true
  },
  // 主要的
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: (resolve) => require(['@/views/index'], resolve),
        name: 'Index',
      }
    ]
  },

  // 个人中心
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: (resolve) => require(['@/views/system/user/profile/index'], resolve),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  },
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: (resolve) => require(['@/views/system/user/authRole'], resolve),
        name: 'AuthRole',
        meta: { title: '分配角色', activeMenu: '/system/user' }
      }
    ]
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: (resolve) => require(['@/views/system/role/authUser'], resolve),
        name: 'AuthUser',
        meta: { title: '分配用户', activeMenu: '/system/role' }
      }
    ]
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: (resolve) => require(['@/views/system/dict/data'], resolve),
        name: 'Data',
        meta: { title: '字典数据', activeMenu: '/system/dict' }
      }
    ]
  },
  {
    path: '/monitor/job-log',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        component: (resolve) => require(['@/views/monitor/job/log'], resolve),
        name: 'JobLog',
        meta: { title: '调度日志', activeMenu: '/monitor/job' }
      }
    ]
  },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        component: (resolve) => require(['@/views/tool/gen/editTable'], resolve),
        name: 'GenEdit',
        meta: { title: '修改生成配置', activeMenu: '/tool/gen' }
      }
    ]
  },
]

export default new Router({
  // mode: 'history', // 去掉url中的#
  mode: 'hash',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
