import request from '@/utils/request'

// 查询防疫用品库存数据列表
export function listAntiepidemicamount(query) {
  return request({
    url: '/medicalhealth/antiepidemicamount/list',
    method: 'get',
    params: query
  })
}
// 查询防疫用品库存数据详细
export function getAntiepidemicamount(id) {
  return request({
    url: '/medicalhealth/antiepidemicamount/' + id,
    method: 'get'
  })
}

// 新增防疫用品库存数据
export function addAntiepidemicamount(data) {
  return request({
    url: '/medicalhealth/antiepidemicamount',
    method: 'post',
    data: data
  })
}

// 修改防疫用品库存数据
export function updateAntiepidemicamount(data) {
  return request({
    url: '/medicalhealth/antiepidemicamount',
    method: 'put',
    data: data
  })
}

// 删除防疫用品库存数据
export function delAntiepidemicamount(id) {
  return request({
    url: '/medicalhealth/antiepidemicamount/' + id,
    method: 'delete'
  })
}

// 导出防疫用品库存数据
export function exportAntiepidemicamount(query) {
  return request({
    url: '/medicalhealth/antiepidemicamount/export',
    method: 'get',
    params: query
  })
}