import request from '@/utils/request'

// 查询截止日期设置列表
export function listTime(query) {
  return request({
    url: '/system/time/list',
    method: 'get',
    params: query
  })
}

// 查询截止日期设置详细
export function getTime(id) {
  return request({
    url: '/system/time/' + id,
    method: 'get'
  })
}

// 新增截止日期设置

export function addTime(data) {
  return request({
    url: '/system/time',
    method: 'post',
    data: data
  })
}

// 修改截止日期设置

export function updateTime(data) {
  return request({
    url: '/system/time',
    method: 'put',
    data: data
  })
}

// 删除截止日期设置

export function delTime(id) {
  return request({
    url: '/system/time/' + id,
    method: 'delete'
  })
}
