import request from '@/utils/request'

// 查询人员变动列表
export function listPeopleStatistics(query) {
  return request({
    url: '/combatduty/PeopleStatistics/list',
    method: 'get',
    params: query
  })
}
export function listPeopleStatistics1(query){
  return request({
    url: 'combatduty/contrast/get',
    method: 'get',
    params: query
  })
}

// 查询人员变动详细
export function getPeopleStatistics(id) {
  return request({
    url: '/combatduty/PeopleStatistics/' + id,
    method: 'get'
  })
}

// 新增人员变动
export function addPeopleStatistics(data) {
  return request({
    url: '/combatduty/PeopleStatistics',
    method: 'post',
    data: data
  })
}

// 修改人员变动
export function updatePeopleStatistics(data) {
  return request({
    url: '/combatduty/PeopleStatistics',
    method: 'put',
    data: data
  })
}

export function updatePeopleStatistics1(data){
  return request({
    url: 'combatduty/contrast',
    method: 'put',
    data: data
  })
}

// 删除人员变动
export function delPeopleStatistics(id) {
  return request({
    url: '/combatduty/PeopleStatistics/' + id,
    method: 'delete'
  })
}
