import request from '@/utils/request'

// 查询战备情况列表
export function listInfo(query) {
  return request({
    url: '/zbqk/fileinfo/list',
    method: 'get',
    params: query
  })
}

// 查询战备情况详细
export function getInfo(id) {
  return request({
    url: '/zbqk/fileinfo/' + id,
    method: 'get'
  })
}

// 新增战备情况
export function addInfo(data) {
  return request({
    url: '/zbqk/fileinfo',
    method: 'post',
    data: data
  })
}

// 修改战备情况
export function updateInfo(data) {
  return request({
    url: '/zbqk/fileinfo',
    method: 'put',
    data: data
  })
}

// 删除战备情况
export function delInfo(id) {
  return request({
    url: '/zbqk/fileinfo/' + id,
    method: 'delete'
  })
}

// 导出战备情况
export function exportInfo(query) {
  return request({
    url: '/zbqk/fileinfo/export',
    method: 'get',
    params: query
  })
}

// 上传
export function addFileinfo(data, file) {
  return request({
    url: `/zbqk/fileinfo`,
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
    url: '/zbqk/fileinfo',
    method: 'put',
    data: data
  })
}
