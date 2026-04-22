import request from '@/utils/request'

// 查询防疫用品入库登记列表
export function listAntiepidemicputin(query) {
  return request({
    url: '/medicalhealth/antiepidemicputin/list',
    method: 'get',
    params: query
  })
}

// 查询防疫用品入库登记详细
export function getAntiepidemicputin(id) {
  return request({
    url: '/medicalhealth/antiepidemicputin/' + id,
    method: 'get'
  })
}

// 新增防疫用品入库登记
export function addAntiepidemicputin(data) {
  return request({
    url: '/medicalhealth/antiepidemicputin',
    method: 'post',
    data: data
  })
}

// 修改防疫用品入库登记
export function updateAntiepidemicputin(data) {
  return request({
    // url: '/medicalhealth/antiepidemicputin',
    url: '/medicalhealth/antiepidemicputin/update',
    method: 'put',
    data: data
  })
}

// 销毁防疫用品入库登记
export function updateAntiepidemicputin2(data) {
  return request({
    url: '/medicalhealth/antiepidemicputin',
    // url: '/medicalhealth/antiepidemicputin/update',
    method: 'put',
    data: data
  })
}

// 删除防疫用品入库登记
export function delAntiepidemicputin(id) {
  return request({
    url: '/medicalhealth/antiepidemicputin/' + id,
    method: 'delete'
  })
}

// 导出防疫用品入库登记
export function exportAntiepidemicputin(query) {
  return request({
    url: '/medicalhealth/antiepidemicputin/export',
    method: 'get',
    params: query
  })
}

// 查询防疫品名称
export function exportAntiepidemiFangYi(query) {
  return request({
    url: '/medicalhealth/antiepidemicputin/getlist',
    method: 'get',
    params: query
  })
}

// 查询过期列表
export function exportAntiepidemiGuoQi(query) {
  return request({
    url: '/medicalhealth/fangyiguoqi/getGuoQi',
    method: 'get',
    params: query
  })
}

// 查询防疫品名称出库
export function exportAntiepidemiFangYiChuKu(query) {
  return request({
    url: '/medicalhealth/antiepidemicputout/getlist',
    method: 'get',
    params: query
  })
}

// 新增防疫用品过期入库登记
export function addAntiepidemicputins(data) {
  return request({
    url: '/medicalhealth/antiepidemicputin/insert',
    method: 'post',
    data: data
  })
}
