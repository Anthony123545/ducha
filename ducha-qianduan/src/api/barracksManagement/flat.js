import request from '@/utils/request'

// 查询公寓房档案列表
export function listFlat(query) {
  return request({
    url: '/barracksManagement/flat/list',
    method: 'get',
    params: query
  })
}

// 查询公寓房档案详细
export function getFlat(id) {
  return request({
    url: '/barracksManagement/flat/' + id,
    method: 'get'
  })
}

// 新增公寓房档案
export function addFlat(data) {
  return request({
    url: '/barracksManagement/flat',
    method: 'post',
    data: data
  })
}

// 修改公寓房档案
export function updateFlat(data) {
  return request({
    url: '/barracksManagement/flat',
    method: 'put',
    data: data
  })
}

// 删除公寓房档案
export function delFlat(id) {
  return request({
    url: '/barracksManagement/flat/' + id,
    method: 'delete'
  })
}

// 导出公寓房档案
export function exportFlat(query) {
  return request({
    url: '/barracksManagement/flat/export',
    method: 'get',
    params: query
  })
}