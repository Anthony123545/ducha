import request from '@/utils/request'

// 查询防疫用品库存档案列表
export function listAntiepidemicarchives(query) {
  return request({
    url: '/medicalhealth/antiepidemicarchives/list',
    method: 'get',
    params: query
  })
}

// 查询防疫用品库存档案详细
export function getAntiepidemicarchives(id) {
  return request({
    url: '/medicalhealth/antiepidemicarchives/' + id,
    method: 'get'
  })
}

// 新增防疫用品库存档案
export function addAntiepidemicarchives(data) {
  return request({
    url: '/medicalhealth/antiepidemicarchives',
    method: 'post',
    data: data
  })
}

// 修改防疫用品库存档案
export function updateAntiepidemicarchives(data) {
  return request({
    url: '/medicalhealth/antiepidemicarchives',
    method: 'put',
    data: data
  })
}

// 删除防疫用品库存档案
export function delAntiepidemicarchives(id) {
  return request({
    url: '/medicalhealth/antiepidemicarchives/' + id,
    method: 'delete'
  })
}

// 导出防疫用品库存档案
export function exportAntiepidemicarchives(query) {
  return request({
    url: '/medicalhealth/antiepidemicarchives/export',
    method: 'get',
    params: query
  })
}