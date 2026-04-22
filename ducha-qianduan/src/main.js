import Vue from 'vue'

import Cookies from 'js-cookie'

import Element from 'element-ui'
import './assets/styles/element-variables.scss'


import '@/assets/styles/index.scss' // global css
import '@/assets/styles/supervision.scss' // supervision css
import App from './App'
import store from './store'
import router from './router'
import directive from './directive' //directive
import plugins from './plugins' // plugins
import VScaleScreen from 'v-scale-screen'
import "@/plugins/video.js"; // 引入刚刚定义的video.js文件
// 引入jquery
import $ from "jquery";
window.$ = $;
window.jQuery = $;
export default $;
// 引入字体图标
import '@/inconfont/iconfont.css';

import './assets/icons' // icon
import './permission' // permission control
import {
  getDicts
} from "@/api/system/dict/data";
import {
  getConfigKey
} from "@/api/system/config";
import {
  parseTime,
  resetForm,
  addDateRange,
  selectDictLabel,
  selectDictLabels,
  handleTree,
  getNowWeek
} from "@/utils/supervision";
// 分页组件
import Pagination from "@/components/Pagination";
// 自定义表格工具组件
import RightToolbar from "@/components/RightToolbar"
// 富文本组件
import flv from "@/components/flv"
import Editor from "@/components/Editor"
// 文件上传组件
import FileUpload from "@/components/FileUpload"
// 图片上传组件
import ImageUpload from "@/components/ImageUpload"
// 图片上传组件
import ImageUpload2 from "@/components/ImageUpload2"
// 字典标签组件
import DictTag from '@/components/DictTag'
// 头部标签组件
import VueMeta from 'vue-meta'
// 字典数据组件
import DictData from '@/components/DictData'
// 公共样式
import '@/assets/styles/global.css'
// 全局挂载 VueQuillEditor
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import '@/utils/lodJs.js'

// 引入echarts
import echarts from 'echarts'


import axios from 'axios'
// 全局方法挂载
Vue.prototype.$axios = axios
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.handleTree = handleTree
Vue.prototype.getNowWeek = getNowWeek
Vue.prototype.$bus = new Vue();
// originVal 后台返回的中国标准时间
Vue.filter('dataFormat', originVal => {
  const dt = new Date(originVal)
  if (originVal) {
    const y = dt.getFullYear()
    const m = (dt.getMonth() + 1 + '').padStart(2, '0')
    const d = (dt.getDate() + '').padStart(2, '0')
    return `${y}年-${m}月-${d}日`
  }

})

Vue.filter('dataFormatshiF', originVal => {
  const dt = new Date(originVal)
  if (originVal) {
    const h = (dt.getHours() + '').padStart(2, '0')
    const mm = (dt.getMinutes() + '').padStart(2, '0')
    const s = (dt.getSeconds() + '').padStart(2, '0')
    return `${h}时${mm}分`
  }

})
// originVal 后台返回的中国标准时间
Vue.filter('dataFormats', originVal => {
  const dt = new Date(originVal)
  if (originVal) {
    const m = (dt.getMonth() + 1 + '').padStart(2, '0')
    const d = (dt.getDate() + '').padStart(2, '0')
    return `${m}月${d}日`
  }

})
// originVal 后台返回的中国标准时间
Vue.filter('dataFormaty', originVal => {
  const dt = new Date(originVal)
  if (originVal) {
    const y = dt.getFullYear()
    const m = (dt.getMonth() + 1 + '').padStart(2, '0')
    const d = (dt.getDate() + '').padStart(2, '0')
    return `${y}年${m}月${d}日`
  }

})
// originVal 后台返回的中国标准时间
Vue.filter('dataFormatys', originVal => {
  const dt = new Date(originVal)
  if (originVal) {
    const y = dt.getFullYear()
    const m = (dt.getMonth() + 1 + '').padStart(2, '0')
    const d = (dt.getDate() + '').padStart(2, '0')
    return `${y}.${m}.${d}`
  }

})
// originVal 后台返回的中国标准时间
Vue.filter('dataFormatyss', originVal => {
  const dt = new Date(originVal)
  if (originVal) {
    const y = dt.getFullYear()
    const m = (dt.getMonth() + 1 + '').padStart(2, '0')
    const d = (dt.getDate() + '').padStart(2, '0')
    return `${y}.${m}`
  }

})
import dataV from '@jiaminghi/data-view'

Vue.use(dataV)

// 全局组件挂载
Vue.component('DictTag', DictTag)
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.component('Editor', Editor)
Vue.component('FileUpload', FileUpload)
Vue.component('ImageUpload', ImageUpload)
Vue.component('ImageUpload2', ImageUpload2)
Vue.component('flv', flv)

Vue.use(directive)
Vue.use(plugins)
Vue.use(VueMeta)
Vue.use(VueQuillEditor)
Vue.use(VScaleScreen)
DictData.install()

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})


const on = Vue.prototype.$on
Vue.prototype.$on = function (event, func) {
  let timer;
  let flag = true;
  let newFunc = func
  if (event == 'click') {
    newFunc = function () {
      if (flag) {
        func.apply(this, arguments)
        flag = false
      }
      clearTimeout(timer)
      timer = setTimeout(function () {
        flag = true
      }, 3000)
    }
  }
  on.call(this, event, newFunc)
}
