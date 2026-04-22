import request from '@/utils/request'

// 查询单身干部宿舍列表
export function listDormitory(query) {
  return request({
    url: '/single/dormitory/list',
    method: 'get',
    params: query
  })
}

// 查询单身干部宿舍详细
export function getDormitory(id) {
  return request({
    url: '/single/dormitory/' + id,
    method: 'get'
  })
}

// 新增单身干部宿舍
export function addDormitory(data) {
  return request({
    url: '/single/dormitory',
    method: 'post',
    data: data
  })
}

// 修改单身干部宿舍
export function updateDormitory(data) {
  return request({
    url: '/single/dormitory',
    method: 'put',
    data: data
  })
}

// 删除单身干部宿舍
export function delDormitory(id) {
  return request({
    url: '/single/dormitory/' + id,
    method: 'delete'
  })
}

// 导出临时来队
export function exportCheckin(query) {
  return request({
    url: '/single/dormitory/export',
    method: 'get',
    params: query
  })
}
