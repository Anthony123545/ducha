import request from '@/utils/request'

// 查询教案库列表
export function listTeacherFile(query) {
  return request({
    url: '/thought/teacherFile/list',
    method: 'get',
    params: query
  })
}

// 查询教案库详细
export function getTeacherFile(id) {
  return request({
    url: '/thought/teacherFile/' + id,
    method: 'get'
  })
}

// 新增教案库
export function addTeacherFile(data) {
  return request({
    url: '/thought/teacherFile',
    method: 'post',
    data: data
  })
}

// 修改教案库
export function updateTeacherFile(data) {
  return request({
    url: '/thought/teacherFile',
    method: 'put',
    data: data
  })
}

// 删除教案库
export function delTeacherFile(id) {
  return request({
    url: '/thought/teacherFile/' + id,
    method: 'delete'
  })
}

// 导出教案库
export function exportTeacherFile(query) {
  return request({
    url: '/thought/teacherFile/export',
    method: 'get',
    params: query
  })
}
// 查询上传文件目录列表
export function listmulu(query) {
  return request({
    url: '/thought/teacher/treeselect',
    method: 'get',
    params: query
  })
}

// 查询上传文件目录列表
export function listread(data) {
  return request({
    url: '/thought/teacherFile/update',
    method: 'put',
    data: data
  })
}
// 获取排行列表接口
export function rankList() {  
  return request({
    url:"/thought/teacherFile/getReadRanking",
    method:"get"
  })
}
