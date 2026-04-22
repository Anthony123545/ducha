import request from '@/utils/request'

// 查询药品库存档案列表
export function listBdgldurgarchives(query) {
  return request({
    url: `/medicalhealth/bdgldurgarchives/list`,
    method: 'get',
    params: query
  })
}

// 查询药品库存档案详细
export function getBdgldurgarchives(id) {
  return request({
    url: '/medicalhealth/bdgldurgarchives/' + id,
    method: 'get'
  })
}

// 新增药品库存档案
export function addBdgldurgarchives(data) {
  return request({
    url: '/medicalhealth/bdgldurgarchives',
    method: 'post',
    data: data
  })
}

// 修改药品库存档案
export function updateBdgldurgarchives(data) {
  return request({
    url: '/medicalhealth/bdgldurgarchives',
    method: 'put',
    data: data
  })
}

// 删除药品库存档案
export function delBdgldurgarchives(id) {
  return request({
    url: '/medicalhealth/bdgldurgarchives/' + id,
    method: 'delete'
  })
}

// 导出药品库存档案
export function exportBdgldurgarchives(query) {
  return request({
    url: '/medicalhealth/bdgldurgarchives/export',
    method: 'get',
    params: query
  })
}

// 查询部门 
export function getDept(query) {
  return request({
    url: '/grassrootsregistration/common/getDept',
    method: 'get',
    params: query
  })
}
// 查询部门2 
export function getDept2() {
  return request({
    url: '/grassrootsregistration/common/getDept',
    method: 'get',
    params: { type: '1' }
  })
}