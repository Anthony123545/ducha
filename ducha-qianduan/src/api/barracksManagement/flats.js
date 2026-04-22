import request from '@/utils/request'

// 查询公寓住房申请人员资格审查登记列表
export function listFlats(query) {
  return request({
    url: '/barracksManagement/flats/list',
    method: 'get',
    params: query
  })
}

// 查询公寓住房申请人员资格审查登记详细
export function getFlats(id) {
  return request({
    url: '/barracksManagement/flats/' + id,
    method: 'get'
  })
}

// 新增公寓住房申请人员资格审查登记
export function addFlats(data) {
  return request({
    url: '/barracksManagement/flats',
    method: 'post',
    data: data
  })
}

// 修改公寓住房申请人员资格审查登记
export function updateFlats(data) {
  return request({
    url: '/barracksManagement/flats',
    method: 'put',
    data: data
  })
}

// 删除公寓住房申请人员资格审查登记
export function delFlats(id) {
  return request({
    url: '/barracksManagement/flats/' + id,
    method: 'delete'
  })
}

// 导出公寓住房申请人员资格审查登记
export function exportFlats(query) {
  return request({
    url: '/barracksManagement/flats/export',
    method: 'get',
    params: query
  })
}