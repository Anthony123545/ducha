import request from '@/utils/request'

// 查询规章制度目录列表
export function listDirectoryinfo(query) {
  return request({
    url: '/regulations/directoryinfo/list',
    method: 'get',
    params: query
  })
}

// // 查询规章制度子分类列表
// export function listDirectoryinfo1(id) {
//   return request({
//     url: `/regulations/directoryinfo/list?parentId=${id}`,
//     method: 'get'
//   })
// }

// 查询规章制度目录详细
export function getDirectoryinfo(id) {
  return request({
    url: '/regulations/directoryinfo/' + id,
    method: 'get'
  })
}

// 新增规章制度目录
export function addDirectoryinfo(data) {
  return request({
    url: '/regulations/directoryinfo',
    method: 'post',
    data: data
  })
}

// 修改规章制度目录
export function updateDirectoryinfo(data) {
  return request({
    url: '/regulations/directoryinfo',
    method: 'put',
    data: data
  })
}

// 删除规章制度目录
export function delDirectoryinfo(id) {
  return request({
    url: "/regulations/directoryinfo/" + id,
    method: 'delete'
  })
}

// 导出规章制度目录
export function exportDirectoryinfo(query) {
  return request({
    url: '/regulations/directoryinfo/export',
    method: 'get',
    params: query
  })
}