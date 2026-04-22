import request from '@/utils/request'

// 查询人员借调列表
export function listPeopleBorrow(query) {
  return request({
    url: '/peopleChuRu/peopleBorrow/list',
    method: 'get',
    params: query
  })
}

// 查询人员借调详细
export function getPeopleBorrow(id) {
  return request({
    url: '/peopleChuRu/peopleBorrow/' + id,
    method: 'get'
  })
}

// 新增人员借调
export function addPeopleBorrow(data) {
  return request({
    url: '/peopleChuRu/peopleBorrow',
    method: 'post',
    data: data
  })
}

// 修改人员借调
export function updatePeopleBorrow(data) {
  return request({
    url: '/peopleChuRu/peopleBorrow',
    method: 'put',
    data: data
  })
}

// 删除人员借调
export function delPeopleBorrow(id) {
  return request({
    url: '/peopleChuRu/peopleBorrow/' + id,
    method: 'delete'
  })
}

// 导出人员借调
export function exportPeopleBorrow(query) {
  return request({
    url: '/peopleChuRu/peopleBorrow/export',
    method: 'get',
    params: query
  })
}