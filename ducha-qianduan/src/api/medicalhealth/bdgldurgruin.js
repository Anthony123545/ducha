import request from '@/utils/request'

// 查询药品销毁记录列表
export function listBdgldurgruin(query) {
  return request({
    url: '/medicalhealth/bdgldurgruin/list',
    method: 'get',
    params: query
  })
}

// 查询药品销毁记录详细
export function getBdgldurgruin(id) {
  return request({
    url: '/medicalhealth/bdgldurgruin/' + id,
    method: 'get'
  })
}

// 新增药品销毁记录
export function addBdgldurgruin(data) {
  return request({
    url: '/medicalhealth/bdgldurgruin',
    method: 'post',
    data: data
  })
}

// 修改药品销毁记录
export function updateBdgldurgruin(data) {
  return request({
    url: '/medicalhealth/bdgldurgruin',
    method: 'put',
    data: data
  })
}

// 删除药品销毁记录
export function delBdgldurgruin(id) {
  return request({
    url: '/medicalhealth/bdgldurgruin/' + id,
    method: 'delete'
  })
}

// 导出药品销毁记录
export function exportBdgldurgruin(query) {
  return request({
    url: '/medicalhealth/bdgldurgruin/export',
    method: 'get',
    params: query
  })
}