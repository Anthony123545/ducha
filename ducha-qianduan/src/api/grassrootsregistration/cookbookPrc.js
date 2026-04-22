import request from '@/utils/request'

// 查询一周工作食普图列表
export function listCookbookPrc(query) {
  return request({
    url: '/grassrootsregistration/cookbookPrc/list',
    method: 'get',
    params: query
  })
}

// 查询一周工作食普图详细
export function getCookbookPrc(id) {
  return request({
    url: '/grassrootsregistration/cookbookPrc/' + id,
    method: 'get'
  })
}

// 新增一周工作食普图
export function addCookbookPrc(data) {
  return request({
    url: '/grassrootsregistration/cookbookPrc',
    method: 'post',
    data: data
  })
}

// 修改一周工作食普图
export function updateCookbookPrc(data) {
  return request({
    url: '/grassrootsregistration/cookbookPrc',
    method: 'put',
    data: data
  })
}

// 删除一周工作食普图
export function delCookbookPrc(id) {
  return request({
    url: '/grassrootsregistration/cookbookPrc/' + id,
    method: 'delete'
  })
}
