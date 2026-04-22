import request from '@/utils/request'

// 导出考勤
export function listBarracks(query) {
  return request({
    url: '/grassrootsregistration/attendancerecord/export',
    method: 'get',
    params: query
  })
}

// 获取列表
export function attendancerecordList(query) {
  return request({
    url: 'grassrootsregistration/attendancerecord/list',
    method: 'get',
    params: query
  })
}

// 查询获取考勤记录的IP地址配置列表
export function listIpaddressconfig(query) {
  return request({
    url: '/grassrootsregistration/ipaddressconfig/list',
    method: 'get',
    params: query
  })
}


// 人员信息列表查询
export function employee(query) {
  return request({
    url: '/grassrootsregistration/employee/list',
    method: 'get',
    params: query
  })
}
