import request from '@/utils/request'

// 查询人员类别数量列表
export function listTypestatistics(query) {
  return request({
    url: '/peopleManage/typestatistics/list',
    method: 'get',
    params: query
  })
}

// 查询人员类别数量详细
export function getTypestatistics(id) {
  return request({
    url: '/peopleManage/typestatistics/' + id,
    method: 'get'
  })
}

// 新增人员类别数量
export function addTypestatistics(data) {
  return request({
    url: '/peopleManage/typestatistics',
    method: 'post',
    data: data
  })
}

// 修改人员类别数量
export function updateTypestatistics(data) {
  return request({
    url: '/peopleManage/typestatistics',
    method: 'put',
    data: data
  })
}

// 删除人员类别数量
export function delTypestatistics(id) {
  return request({
    url: '/peopleManage/typestatistics/' + id,
    method: 'delete'
  })
}
