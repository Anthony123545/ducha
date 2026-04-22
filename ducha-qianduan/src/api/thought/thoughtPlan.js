import request from '@/utils/request'

// 查询月教育计划列表
export function listThoughtPlan(query) {
  return request({
    url: '/thought/thoughtPlan/list',
    method: 'get',
    params: query
  })
}

// 查询月教育计划详细
export function getThoughtPlan(id) {
  return request({
    url: '/thought/thoughtPlan/' + id,
    method: 'get'
  })
}

// 新增月教育计划
export function addThoughtPlan(data) {
  return request({
    url: '/thought/thoughtPlan',
    method: 'post',
    data: data
  })
}

// 修改月教育计划
export function updateThoughtPlan(data) {
  return request({
    url: '/thought/thoughtPlan',
    method: 'put',
    data: data
  })
}

// 删除月教育计划
export function delThoughtPlan(id) {
  return request({
    url: '/thought/thoughtPlan/' + id,
    method: 'delete'
  })
}

// 导出月教育计划
export function exportThoughtPlan(query) {
  return request({
    url: '/thought/thoughtPlan/export',
    method: 'get',
    params: query
  })
}

// 响应月教育计划
export function xiangYing(data) {
  return request({
    url: '/thought/thoughtPlan/addJinDu',
    method: 'post',
    data: data
  })
}

// 继续响应月教育计划列表
export function JXxiangYing(unitId, planId) {
  return request({
    url: '/thought/thoughtPlan/get',
    method: 'get',
    params: { unitId, planId }
  })
}
// 继续响应响应提交月教育计划列表
export function JXxiangYingXiangYing(data) {
  return request({
    url: '/thought/thoughtPlan/updateJinDu',
    method: 'post',
    data: data
  })
}
// 查询月教育计划详细
export function getXiuGaiButton(id) {
  return request({
    url: '/thought/thoughtPlan/isResponse',
    method: 'post',
    data: id
  })
}

// 响应按钮月教育计划
export function getResponse(id) {
  return request({
    url: 'thought/thoughtPlan/getDetails' + id,
    method: 'get'
  })
}
// 修改按钮月教育计划
export function getModify(id) {
  return request({
    url: 'thought/thoughtPlan/updatePlan' + id,
    method: 'get'
  })
}
