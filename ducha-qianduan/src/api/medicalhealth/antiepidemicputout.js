import request from '@/utils/request'

// 查询防疫用品发放登记列表
export function listAntiepidemicputout(query) {
  return request({
    url: '/medicalhealth/antiepidemicputout/list',
    method: 'get',
    params: query
  })
}

// 查询防疫用品发放登记详细
export function getAntiepidemicputout(id) {
  return request({
    url: '/medicalhealth/antiepidemicputout/' + id,
    method: 'get'
  })
}

// 新增防疫用品发放登记
export function addAntiepidemicputout(data) {
  return request({
    url: '/medicalhealth/antiepidemicputout',
    method: 'post',
    data: data
  })
}

// 修改防疫用品发放登记
export function updateAntiepidemicputout(data) {
  return request({
    url: '/medicalhealth/antiepidemicputout',
    method: 'put',
    data: data
  })
}

// 删除防疫用品发放登记
export function delAntiepidemicputout(id) {
  return request({
    url: '/medicalhealth/antiepidemicputout/' + id,
    method: 'delete'
  })
}

// 导出防疫用品发放登记
export function exportAntiepidemicputout(query) {
  return request({
    url: '/medicalhealth/antiepidemicputout/export',
    method: 'get',
    params: query
  })
}