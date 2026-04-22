import request from '@/utils/request'


export function listGrid() {
  return request({
    url: '/phone/grid/list',
    method: 'get'
  })
}

// 查询手机柜格子详细
export function getGrid(id) {
  return request({
    url: '/phone/grid/' + id,
    method: 'get'
  })
}

// 新增手机柜格子
export function addGrid(data) {
  return request({
    url: '/phone/grid',
    method: 'post',
    data: data
  })
}

// 修改手机柜格子
export function updateGrid(data) {
  return request({
    url: '/phone/grid',
    method: 'put',
    data: data
  })
}

// 删除手机柜格子
export function delGrid(id) {
  return request({
    url: '/phone/grid/' + id,
    method: 'delete'
  })
}

// 导出手机柜格子
export function exportGrid(query) {
  return request({
    url: '/phone/grid/export',
    method: 'get',
    params: query
  })
}

// 导出手机柜格子
export function aaa(query) {
  return request({
    url: '/yyy/aaa/list',
    method: 'get',
    params: query
  })
}

