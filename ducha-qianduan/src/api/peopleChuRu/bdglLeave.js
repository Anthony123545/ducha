import request from '@/utils/request'

// 查询人员请假列表
export function listBdglLeave(query) {
    return request({
        url: '/peopleChuRu/bdglLeave/list',
        method: 'get',
        params: query
    })
}

// 查询人员请假详细
export function getBdglLeave(id) {
    return request({
        url: '/peopleChuRu/bdglLeave/' + id,
        method: 'get'
    })
}

// 审批
export function updateBdglLeaveShen(data) {
    return request({
        url: '/peopleChuRu/bdglLeave/shenpi',
        method: 'put',
        data: data
    })
}

// 特殊审批
export function updateBdglLeaveTe(data) {
    return request({
        url: '/peopleChuRu/bdglLeave/teshu',
        method: 'put',
        data: data
    })
}
// 新增人员请假
export function addBdglLeave(data) {
    return request({
        url: '/peopleChuRu/bdglLeave',
        method: 'post',
        data: data
    })
}

// 修改人员请假
export function updateBdglLeave(data) {
    return request({
        url: '/peopleChuRu/bdglLeave',
        method: 'put',
        data: data
    })
}

// 删除人员请假
export function delBdglLeave(id) {
    return request({
        url: '/peopleChuRu/bdglLeave/' + id,
        method: 'delete'
    })
}

// 导出人员请假
export function exportBdglLeave(query) {
    return request({
        url: '/peopleChuRu/bdglLeave/export',
        method: 'get',
        params: query
    })
}

// 营级审批人
export function YingJIUSer(query) {
    return request({
        url: '/peopleChuRu/bdglLeave/getYingJIUSer',
        method: 'get',
        params: query
    })
}
// 连级审批人
export function LIanJIUSer(query) {
    return request({
        url: '/peopleChuRu/bdglLeave/getLIanJIUSer',
        method: 'get',
        params: query
    })
}
// 政治审批人
export function ZhengZhiUSer(query) {
    return request({
        url: '/peopleChuRu/bdglLeave/getZhengZhiUSer',
        method: 'get',
        params: query
    })
}

// 查询审批人全部
export function getBdglList(id) {
    return request({
        url: '/system/user/list',
        method: 'get'
    })
}

// 审批获取所有单位
export function getAllDept() {
    return request({
        url: 'grassrootsregistration/common/getAllDept',
        method: 'get'
    })
}

// 获取所有人员列表
export function getAll(query) {
    return request({
        url: '/system/user/lists',
        method: 'get',
    })
}
// 归队权限
export function guidui(data) {
    return request({
        url: '/peopleChuRu/bdglLeave/guidui',
        method: 'put',
        data: data
    })
}

// 获取车牌号信息
export function getPlateNumbers(params) {
    return request({
        url: '/peopleChuRu/bdglLeave/getPlateNumbers',
        method: 'get',
        params: params
    })
}

// 获取门闸设备信息
export function getGateDevices(gateType) {
    return request({
        url: '/peopleChuRu/bdglLeave/getGateDevices',
        method: 'get',
        params: { gateType: gateType }
    })
}