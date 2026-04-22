import request from '@/utils/request'

// 查询维修申请列表
export function listRepair(query) {
  return request({
    url: '/barracksManagement/repair/list',
    method: 'get',
    params: query
  })
}

// 查询维修申请详细
export function getRepair(id) {
  return request({
    url: '/barracksManagement/repair/' + id,
    method: 'get'
  })
}

// 新增维修申请
export function addRepair(data) {
  return request({
    url: '/barracksManagement/repair',
    method: 'post',
    data: data
  })
}

// 修改维修申请
export function updateRepair(data) {
  return request({
    url: '/barracksManagement/repair',
    method: 'put',
    data: data
  })
}

// 删除维修申请
export function delRepair(id) {
  return request({
    url: '/barracksManagement/repair/' + id,
    method: 'delete'
  })
}

// 完成
export function repairSuccess(data) {
  return request({
    url: '/barracksManagement/repair/repairSuccess',
    method: 'put',
    data: data
  })
}
