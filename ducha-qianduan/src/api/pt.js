import request from "@/utils/request";
// 轮训每人
export function workEachTrigger(data) {
  return request({
    url: '/workingArrangements/workProgress/workEachTrigger',
    method: 'post',
    data: data
  })
}

// 任务
export function workTrigger(data) {
  return request({
    url: '/workingArrangements/workProgress/workTrigger',
    method: 'post',
    data: data
  })
}

// 新增公告
export function addNotice(data) {
  return request({
    url: '/system/notice',
    method: 'post',
    data: data
  })
}

// 查询公告列表
export function listNotice(query) {
  return request({
    url: '/system/notice/lists',
    method: 'get',
    params: query
  })
}

// 修改公告
export function updateNotice(data) {
  return request({
    url: '/system/notice/updatePT',
    method: 'put',
    data: data
  })
}


// 代办事项
export function getWorkTask(query) {
  return request({
    url: '/upcoming/upcomingController/getWorkTask',
    method: 'get',
    params: query
  })
}


// 首页的显示隐藏
export function getNone(query) {
  return request({
    url: '/system/menu/getmeun',
    method: 'get',
    params: query
  })
}
// 获取预警功能
export function noReminder(query) {
  return request({
    url: '/system/menu/noReminder',
    method: 'get',
    params: query
  })
}

// 获取提醒功能
export function remindPrint(query) {
  return request({
    url: '/PrintsLog/printlog/remindPrint',
    method: 'get',
    params: query
  })
}


// 代办事项
export function syTaskView(query) {
  return request({
    url: 'jobTaskRecord/jobTask/syTaskView',
    method: 'get',
    params: query
  })
}


export function getInfoD(query) {
  return request({
    url: '/barracksManagement/repair/getInfoD',
    method: 'get',
    params: query
  })
}
