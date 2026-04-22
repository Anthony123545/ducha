import request from '@/utils/request'

// 查询教育准备会列表
export function listGetready(query) {
  return request({
    url: '/thought/getready/list',
    method: 'get',
    params: query
  })
}

// 查询教育准备会详细
export function getGetready(id) {
  return request({
    url: '/thought/getready/' + id,
    method: 'get'
  })
}

// 新增教育准备会
export function addGetready(data) {
  return request({
    url: '/thought/getready',
    method: 'post',
    data: data
  })
}

// 修改教育准备会
export function updateGetready(data) {
  return request({
    url: '/thought/getready',
    method: 'put',
    data: data
  })
}

// 删除教育准备会
export function delGetready(id) {
  return request({
    url: '/thought/getready/' + id,
    method: 'delete'
  })
}

// 导出教育准备会
export function exportGetready(query) {
  return request({
    url: '/thought/getready/export',
    method: 'get',
    params: query
  })
}