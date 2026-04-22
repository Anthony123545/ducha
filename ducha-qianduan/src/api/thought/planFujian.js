import request from '@/utils/request'

// 查询月教育计划附件列表
export function listPlanFujian(query) {
  return request({
    url: '/thought/planFujian/list',
    method: 'get',
    params: query
  })
}

// 查询月教育计划附件详细
export function getPlanFujian(id) {
  return request({
    url: '/thought/planFujian/' + id,
    method: 'get'
  })
}

// 新增月教育计划附件
export function addPlanFujian(data) {
  return request({
    url: '/thought/planFujian',
    method: 'post',
    data: data
  })
}

// 修改月教育计划附件
export function updatePlanFujian(data) {
  return request({
    url: '/thought/planFujian',
    method: 'put',
    data: data
  })
}

// 删除月教育计划附件
export function delPlanFujian(id) {
  return request({
    url: '/thought/planFujian/' + id,
    method: 'delete'
  })
}

// 导出月教育计划附件
export function exportPlanFujian(query) {
  return request({
    url: '/thought/planFujian/export',
    method: 'get',
    params: query
  })
}