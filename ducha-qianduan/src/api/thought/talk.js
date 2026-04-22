import request from '@/utils/request'

// 查询谈心记录卡列表
export function listTalk(query) {
  return request({
    url: '/thought/talk/list',
    method: 'get',
    params: query
  })
}

// 查询谈心记录卡详细
export function getTalk(id) {
  return request({
    url: '/thought/talk/' + id,
    method: 'get'
  })
}

// 新增谈心记录卡
export function addTalk(data) {
  return request({
    url: '/thought/talk',
    method: 'post',
    data: data
  })
}

// 修改谈心记录卡
export function updateTalk(data) {
  return request({
    url: '/thought/talk',
    method: 'put',
    data: data
  })
}

// 删除谈心记录卡
export function delTalk(id) {
  return request({
    url: '/thought/talk/' + id,
    method: 'delete'
  })
}

// 导出谈心记录卡
export function exportTalk(query) {
  return request({
    url: '/thought/talk/export',
    method: 'get',
    params: query
  })
}