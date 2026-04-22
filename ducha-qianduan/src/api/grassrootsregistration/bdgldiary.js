import request from '@/utils/request'

// 查询要事日记列表
export function listBdgldiary(query) {
  return request({
    url: '/grassrootsregistration/bdgldiary/list',
    method: 'get',
    params: query
  })
}

// 查询要事日记详细
export function getBdgldiary(id) {
  return request({
    url: '/grassrootsregistration/bdgldiary/' + id,
    method: 'get'
  })
}

// 新增要事日记
export function addBdgldiary(data) {
  return request({
    url: '/grassrootsregistration/bdgldiary',
    method: 'post',
    data: data
  })
}

// 修改要事日记
export function updateBdgldiary(data) {
  return request({
    url: '/grassrootsregistration/bdgldiary',
    method: 'put',
    data: data
  })
}

// 审批
export function updateBdglShenPi(data) {
  return request({
    url: '/grassrootsregistration/bdgldiary/update',
    method: 'put',
    data: data
  })
}

// 删除要事日记
export function delBdgldiary(id) {
  return request({
    url: '/grassrootsregistration/bdgldiary/' + id,
    method: 'delete'
  })
}

// 导出要事日记
export function exportBdgldiary(query) {
  return request({
    url: '/grassrootsregistration/bdgldiary/export',
    method: 'get',
    params: query
  })
}

// 选择页面时间查询当前数据
export function exportBdgShuju(query) {
  return request({
    url: '/grassrootsregistration/bdgldiary/getNumber',
    method: 'get',
    params: { date: query }
  })
}
// 查询所在单位首长(用户，包含自己)
export function getUserzs(params) {
  return request({
    url: 'system/user/list',
    method: 'get',
    params
  })
}

// 要是日记导出
export function exportDaily(query) {
  return request({
    url: '/grassrootsregistration/inspect/exportDaily',
    method: 'get',
    params:query
  })
}


// 查询基层截止日期
export function jieZhiList(query) {
  return request({
    url: 'system/time/list',
    method: 'get',
    params:query
  })
}
