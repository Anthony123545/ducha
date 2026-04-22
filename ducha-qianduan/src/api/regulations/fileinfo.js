import request from '@/utils/request'

// 查询规章制度文件记录列表
export function listFileinfo(query) {
  return request({
    url: '/regulations/fileinfo/list',
    method: 'get',
    params: query
  })
}

// 查询规章制度文件记录详细
export function getFileinfo(id) {
  return request({
    url: '/regulations/fileinfo/' + id,
    method: 'get'
  })
}

// 新增规章制度文件记录
export function addFileinfo(data, file) {
  return request({
    url: `/regulations/fileinfo`,
    method: 'post',
    data: data,
    file,
    // headers: {
    //   'Content-Type':'multipart/form-data'
    // }
  })
}

// 修改规章制度文件记录
export function updateFileinfo(data) {
  return request({
    url: '/regulations/fileinfo',
    method: 'put',
    data: data
  })
}

// 删除规章制度文件记录
export function delFileinfo(id) {
  return request({
    url: '/regulations/fileinfo/' + id,
    method: 'delete'
  })
}

// 导出规章制度文件记录
export function exportFileinfo(query) {
  return request({
    url: '/regulations/fileinfo/export',
    method: 'get',
    params: query
  })
}

// 获取排行列表接口
export function rankList() {
  return request({
    url: "/regulations/fileinfo/getReadNumber",
    method: "get"
  })
}

// 获取一级数据接口
export function listParents(query) {
  return request({
    url: "/regulations/fileinfo/listParent",
    method: "get",
    params: query
  })
}
