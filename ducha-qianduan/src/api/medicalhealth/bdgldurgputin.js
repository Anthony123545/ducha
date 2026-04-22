import request from '@/utils/request'

// 查询药品入库登记列表
export function listBdgldurgputin(query) {
  return request({
    url: '/medicalhealth/bdgldurgputin/list',
    method: 'get',
    params: query
  })
}

// 查询药品入库登记详细
export function getBdgldurgputin(id) {
  return request({
    url: '/medicalhealth/bdgldurgputin/' + id,
    method: 'get'
  })
}

// 新增药品入库登记
export function addBdgldurgputin(data) {
  return request({
    url: '/medicalhealth/bdgldurgputin',
    method: 'post',
    data: data
  })
}

// 修改药品入库登记
export function updateBdgldurgputin(data) {
  return request({
    url: '/medicalhealth/bdgldurgputin',
    method: 'put',
    data: data
  })
}

// 删除药品入库登记
export function delBdgldurgputin(id) {
  return request({
    url: '/medicalhealth/bdgldurgputin/' + id,
    method: 'delete'
  })
}

// 导出药品入库登记
export function exportBdgldurgputin(query) {
  return request({
    url: '/medicalhealth/bdgldurgputin/export',
    method: 'get',
    params: query
  })
}
//  获取药品列表
export function exportYao(query) {
  return request({
    url: '/medicalhealth/bdgldurgputin/getYaoPingList',
    method: 'get',
    params: query
  })
}
//  获取出库药品列表
export function exportChuYao(query) {
  return request({
    url: '/medicalhealth/bdgldurgputout/getList',
    method: 'get',
    params: query
  })
}
// 新增药品过期入库登记
export function addBdgldurgputins(data) {
  return request({
    url: '/medicalhealth/bdgldurgputin/insert',
    method: 'post',
    data: data
  })
}