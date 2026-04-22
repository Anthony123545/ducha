import request from '@/utils/request'

// 查询人员请假列表
export function listLeaveOne(query) {
    return request({
        url: '/peopleChuRu/leaveOne/list',
        method: 'get',
        params: query
    })
}

// 查询人员请假详细
export function getLeaveOne(id) {
    return request({
        url: '/peopleChuRu/leaveOne/' + id,
        method: 'get'
    })
}

// 新增人员请假
export function addLeaveOne(data) {
    return request({
        url: '/peopleChuRu/leaveOne',
        method: 'post',
        data: data
    })
}

// 修改人员请假
export function updateLeaveOne(data) {
    return request({
        url: '/peopleChuRu/leaveOne',
        method: 'put',
        data: data
    })
}

// 人员归队
export function returnTeam(id) {
    return request({
        url: '/peopleChuRu/leaveOne/return/' + id,
        method: 'get'
    })
}

// 删除人员请假
export function delLeaveOne(id) {
    return request({
        url: '/peopleChuRu/leaveOne/' + id,
        method: 'delete'
    })
}

// 导出人员请假
export function exportLeaveOne(query) {
    return request({
        url: '/peopleChuRu/leaveOne/export',
        method: 'get',
        params: query
    })
}

// 查询部门 
export function getDept2(query) {
    return request({
        url: '/system/dept/list',
        method: 'get',
        params: query
    })
}