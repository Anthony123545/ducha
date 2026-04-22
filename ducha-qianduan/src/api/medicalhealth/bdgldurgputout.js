import request from '@/utils/request'

// 查询药品出库登记列表
export function listBdgldurgputout(query) {
  return request({
    url: '/medicalhealth/bdgldurgputout/list',
    method: 'get',
    params: query
  })
}

// 查询药品出库登记详细
export function getBdgldurgputout(id) {
  return request({
    url: '/medicalhealth/bdgldurgputout/' + id,
    method: 'get'
  })
}

// 新增药品出库登记
export function addBdgldurgputout(data) {
  return request({
    url: '/medicalhealth/bdgldurgputout',
    method: 'post',
    data: data
  })
}

// 修改药品出库登记
export function updateBdgldurgputout(data) {
  return request({
    url: '/medicalhealth/bdgldurgputout',
    method: 'put',
    data: data
  })
}

// 删除药品出库登记
export function delBdgldurgputout(id) {
  return request({
    url: '/medicalhealth/bdgldurgputout/' + id,
    method: 'delete'
  })
}

// 导出药品出库登记
export function exportBdgldurgputout(query) {
  return request({
    url: '/medicalhealth/bdgldurgputout/export',
    method: 'get',
    params: query
  })
}