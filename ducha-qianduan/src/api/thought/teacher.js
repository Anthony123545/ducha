import request from '@/utils/request'

// 查询教案库设置列表
export function listTeacher(query) {
  return request({
    url: '/thought/teacher/list',
    method: 'get',
    params: query
  })
}

// 查询教案库设置详细
export function getTeacher(id) {
  return request({
    url: '/thought/teacher/' + id,
    method: 'get'
  })
}

// 新增教案库设置
export function addTeacher(data) {
  return request({
    url: '/thought/teacher',
    method: 'post',
    data: data
  })
}

// 修改教案库设置
export function updateTeacher(data) {
  return request({
    url: '/thought/teacher',
    method: 'put',
    data: data
  })
}

// 删除教案库设置
export function delTeacher(id) {
  return request({
    url: '/thought/teacher/' + id,
    method: 'delete'
  })
}

// 导出教案库设置
export function exportTeacher(query) {
  return request({
    url: '/thought/teacher/export',
    method: 'get',
    params: query
  })
}

// 查询教案库设置列表
export function listtree(parentId) {
  return request({
    url: `/thought/teacher/treeselect?parentId=${parentId}`,
    method: 'get'
  })
}
// 返回上一级接口
export function listTeachers(query) {
  return request({
    url: '/thought/teacher/lists',
    method: 'get',
    params: query
  })
}