import request from '@/utils/request'

// 查询装备登记列表
export function listRegisdutyinfo(query) {
  return request({
    url: '/combatduty/regisdutyinfo/list',
    method: 'get',
    params: query
  })
}

// 查询装备登记详细
export function getRegisdutyinfo(id) {
  return request({
    url: '/combatduty/regisdutyinfo/' + id,
    method: 'get'
  })
}

// 新增装备登记
export function addRegisdutyinfo(data) {
  return request({
    url: '/combatduty/regisdutyinfo',
    method: 'post',
    data: data
  })
}

// 修改装备登记
export function updateRegisdutyinfo(data) {
  return request({
    url: '/combatduty/regisdutyinfo',
    method: 'put',
    data: data
  })
}

// 删除装备登记
export function delRegisdutyinfo(id) {
  return request({
    url: '/combatduty/regisdutyinfo/' + id,
    method: 'delete'
  })
}

// 导出装备登记
export function exportRegisdutyinfo(query) {
  return request({
    url: '/combatduty/regisdutyinfo/export',
    method: 'get',
    params: query
  })
}

// 查询部门 
export function getDept(query) {
  return request({
    url: '/system/dept/list',
    method: 'get',
    params: query
  })
}

// 查询类型统计数量
export function getNumber(query) {
  return request({
    url: '/combatduty/regisdutyinfo/getNumber',
    method: 'get',
    params: query
  })
}

