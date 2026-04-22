import request from '@/utils/request'

// 查询党委议教会列表
export function listParliament(query) {
  return request({
    url: '/thought/parliament/list',
    method: 'get',
    params: query
  })
}

// 查询党委议教会详细
export function getParliament(id) {
  return request({
    url: '/thought/parliament/' + id,
    method: 'get'
  })
}

// 新增党委议教会
export function addParliament(data) {
  return request({
    url: '/thought/parliament',
    method: 'post',
    data: data
  })
}

// 修改党委议教会
export function updateParliament(data) {
  return request({
    url: '/thought/parliament',
    method: 'put',
    data: data
  })
}

// 删除党委议教会
export function delParliament(id) {
  return request({
    url: '/thought/parliament/' + id,
    method: 'delete'
  })
}

// 导出党委议教会
export function exportParliament(query) {
  return request({
    url: '/thought/parliament/export',
    method: 'get',
    params: query
  })
}
// 获取所有人员列表
export function getAll(query) {
  return request({
    url: '/grassrootsregistration/common/list',
    method: 'get',
  })
}