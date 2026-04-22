import request from '@/utils/request'

// 查询物资入库登记列表
export function listMaterialRegistration(query) {
  return request({
    url: '/materialManagement/materialRegistration/list',
    method: 'get',
    params: query
  })
}

// 查询物资入库登记详细
export function getMaterialRegistration(id) {
  return request({
    url: '/materialManagement/materialRegistration/' + id,
    method: 'get'
  })
}

// 新增物资入库登记
export function addMaterialRegistration(data) {
  return request({
    url: '/materialManagement/materialRegistration',
    method: 'post',
    data: data
  })
}

// 修改物资入库登记
export function updateMaterialRegistration(data) {
  return request({
    url: '/materialManagement/materialRegistration',
    method: 'put',
    data: data
  })
}

// 删除物资入库登记
export function delMaterialRegistration(id) {
  return request({
    url: '/materialManagement/materialRegistration/' + id,
    method: 'delete'
  })
}

// 导出物资入库登记
export function exportMaterialRegistration(query) {
  return request({
    url: '/materialManagement/materialRegistration/export',
    method: 'get',
    params: query
  })
}

// 获取物品名称列表
export function exportMaterialRegWuPingList(query) {
  return request({
    url: '/materialManagement/materialRegistration/getlist',
    method: 'get',
    params: query
  })
}

export function getBarCode(query) {
  return request({
    url: `/materialManagement/archives/getBarCode`,
    method: 'get',
    params: query
  })
}
