import request from '@/utils/request'

// 查询营具物资变更列表
export function listPropertyChange(query) {
  return request({
    url: '/materialManagement/propertyChange/list',
    method: 'get',
    params: query
  })
}

// 查询营具物资变更详细
export function getPropertyChange(id) {
  return request({
    url: '/materialManagement/propertyChange/' + id,
    method: 'get'
  })
}

// 新增营具物资变更
export function addPropertyChange(data) {
  return request({
    url: '/materialManagement/propertyChange',
    method: 'post',
    data: data
  })
}

// 修改营具物资变更
export function updatePropertyChange(data) {
  return request({
    url: '/materialManagement/propertyChange',
    method: 'put',
    data: data
  })
}

// 删除营具物资变更
export function delPropertyChange(id) {
  return request({
    url: '/materialManagement/propertyChange/' + id,
    method: 'delete'
  })
}

// 导出营具物资变更
export function exportPropertyChange(query) {
  return request({
    url: '/materialManagement/propertyChange/export',
    method: 'get',
    params: query
  })
}