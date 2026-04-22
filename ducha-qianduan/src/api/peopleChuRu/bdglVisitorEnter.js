import request from '@/utils/request';

// 查询访客入所申请列表
export function listBdglVisitorEnter(query) {
    return request({
        url: '/peopleChuRu/bdglVisitorEnter/list',
        method: 'get',
        params: query
    });
}

// 获取访客入所申请详细
export function getBdglVisitorEnter(id) {
    return request({
        url: `/peopleChuRu/bdglVisitorEnter/${id}`,
        method: 'get'
    });
}

// 审批
export function updateBdglVisitorEnterShen(data) {
    return request({
        url: '/peopleChuRu/bdglVisitorEnter/shenpi',
        method: 'put',
        data: data
    });
}

// 特殊审批
export function updateBdglVisitorEnterTe(data) {
    return request({
        url: '/peopleChuRu/bdglVisitorEnter/teshu',
        method: 'put',
        data: data
    });
}

// 新增访客入所申请
export function addBdglVisitorEnter(data) {
    return request({
        url: '/peopleChuRu/bdglVisitorEnter',
        method: 'post',
        data: data
    });
}

// 修改访客入所申请
export function updateBdglVisitorEnter(data) {
    return request({
        url: '/peopleChuRu/bdglVisitorEnter',
        method: 'put',
        data: data
    });
}

// 删除访客入所申请
export function delBdglVisitorEnter(id) {
    return request({
        url: `/peopleChuRu/bdglVisitorEnter/${id}`,
        method: 'delete'
    });
}

// 导出访客入所申请
export function exportBdglVisitorEnter(query) {
    return request({
        url: '/peopleChuRu/bdglVisitorEnter/export',
        method: 'get',
        params: query
    });
}

// 营级审批人
export function YingJIUSer(query) {
    return request({
        url: '/peopleChuRu/bdglLeave/getYingJIUSer',
        method: 'get',
        params: query
    });
}

// 连级审批人
export function LIanJIUSer(query) {
    return request({
        url: '/peopleChuRu/bdglLeave/getLIanJIUSer',
        method: 'get',
        params: query
    });
}

// 政治审批人
export function ZhengZhiUSer(query) {
    return request({
        url: '/peopleChuRu/bdglLeave/getZhengZhiUSer',
        method: 'get',
        params: query
    });
}

// 查询审批人全部
export function getBdglList(id) {
    return request({
        url: '/system/user/list',
        method: 'get'
    });
}

// 获取所有部门
export function getAllDept() {
    return request({
        url: 'grassrootsregistration/common/getAllDept',
        method: 'get'
    });
}

// 获取所有人员列表
export function getAll(query) {
    return request({
        url: '/system/user/lists',
        method: 'get'
    });
}

// 获取访客列表（调用VisitorController的list接口）
export function getVisitorList(query) {
    return request({
        url: '/peopleManage/visitor/list',
        method: 'get',
        params: query
    });
}

// 获取审核通过的车牌号列表
export function getPlateNumbers(params) {
    return request({
        url: '/peopleChuRu/bdglVisitorEnter/getPlateNumbers',
        method: 'get',
        params: params
    });
}

// 获取门闸设备信息
export function getGateDevices(gateType) {
    return request({
        url: '/peopleChuRu/bdglVisitorEnter/getGateDevices',
        method: 'get',
        params: { gateType: gateType }
    });
}