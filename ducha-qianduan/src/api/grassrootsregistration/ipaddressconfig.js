import request from '@/utils/request'

// 查询获取考勤记录的IP地址配置列表
export function listIpaddressconfig(query) {
  return request({
    url: '/grassrootsregistration/ipaddressconfig/list',
    method: 'get',
    params: query
  })
}

// 查询获取考勤记录的IP地址配置详细
export function getIpaddressconfig(id) {
  return request({
    url: '/grassrootsregistration/ipaddressconfig/' + id,
    method: 'get'
  })
}

// 新增获取考勤记录的IP地址配置
export function addIpaddressconfig(data) {
  return request({
    url: '/grassrootsregistration/ipaddressconfig',
    method: 'post',
    data: data
  })
}

// 修改获取考勤记录的IP地址配置
export function updateIpaddressconfig(data) {
  return request({
    url: '/grassrootsregistration/ipaddressconfig',
    method: 'put',
    data: data
  })
}

// 删除获取考勤记录的IP地址配置
export function delIpaddressconfig(id) {
  return request({
    url: '/grassrootsregistration/ipaddressconfig/' + id,
    method: 'delete'
  })
}
