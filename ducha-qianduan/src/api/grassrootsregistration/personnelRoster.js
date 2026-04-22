import request from '@/utils/request'

// 查询领导本查看
export function getPersonnelRosterList(year) {
    return request({
        url: `/grassrootsregistration/getPersonnelRosterList`,
        method: 'get',
        params: { year: year }
    })
}
//  查询领导本列表
export function getPersonnelRosterOnUnitList(query) {
    return request({
        url: '/grassrootsregistration/personnelRoster/getPersonnelRosterOnUnitList',
        method: 'get',
        params: query
    })
}