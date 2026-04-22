import request from '@/utils/request'

// 获取人员状态（员工）
export function getPeopleStatus(id) {
    return request({
        url: '/peopleChuRu/access/getPeopleStatus',
        method: 'get',
        params: { id }
    })
}

// 获取访客状态
export function getVisitorStatus(id) {
    return request({
        url: '/peopleChuRu/access/getVisitorStatus',
        method: 'get',
        params: { id }
    })
}

// 通过工号/访客号获取状态
export function getStatusByEmployeeNumber(employeeNumber) {
    return request({
        url: '/peopleChuRu/access/getStatusByEmployeeNumber',
        method: 'get',
        params: { employeeNumber }
    })
}

// 设置人员状态 - 使用FormData格式
export function setPeopleStatus(id, status) {
    const formData = new FormData();
    formData.append('id', id);
    formData.append('status', status);
    return request({
        url: '/peopleChuRu/access/setPeopleStatus',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

// 设置访客状态
export function setVisitorStatus(id, status) {
    const formData = new FormData();
    formData.append('id', id);
    formData.append('status', status);
    return request({
        url: '/peopleChuRu/access/setVisitorStatus',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

// 通过工号/访客号设置状态
export function setStatusByEmployeeNumber(employeeNumber, status) {
    const formData = new FormData();
    formData.append('employeeNumber', employeeNumber);
    formData.append('status', status);
    return request({
        url: '/peopleChuRu/access/setStatusByEmployeeNumber',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

// 获取人员出入记录列表
export function getPeopleAccessRecords(params) {
    return request({
        url: '/peopleChuRu/access/record/list',
        method: 'get',
        params: params || {}
    })
}