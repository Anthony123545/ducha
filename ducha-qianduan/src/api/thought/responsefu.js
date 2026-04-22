import request from '@/utils/request'

// 查询月教育响应附件列表
export function listResponsefu(query) {
  return request({
    url: '/thought/responsefu/list',
    method: 'get',
    params: query
  })
}

// 查询月教育响应附件详细
export function getResponsefu(id) {
  return request({
    url: '/thought/responsefu/' + id,
    method: 'get'
  })
}

// 新增月教育响应附件
export function addResponsefu(data) {
  return request({
    url: '/thought/responsefu',
    method: 'post',
    data: data
  })
}

// 修改月教育响应附件
export function updateResponsefu(data) {
  return request({
    url: '/thought/responsefu',
    method: 'put',
    data: data
  })
}

// 删除月教育响应附件
export function delResponsefu(id) {
  return request({
    url: '/thought/responsefu/' + id,
    method: 'delete'
  })
}

// 导出月教育响应附件
export function exportResponsefu(query) {
  return request({
    url: '/thought/responsefu/export',
    method: 'get',
    params: query
  })
}