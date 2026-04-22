import request from '@/utils/request'

// 查询人员管理列表
export function listPeople(query) {
    return request({
        url: '/peopleManage/people/list',
        method: 'get',
        params: query
    })
}

// 查询人员管理详细
export function getPeople(id) {
    return request({
        url: '/peopleManage/people/' + id,
        method: 'get'
    })
}

// 新增人员管理
export function addPeople(data) {
    return request({
        url: '/peopleManage/people',
        method: 'post',
        data: data
    })
}

// 修改人员管理
export function updatePeople(data) {
    return request({
        url: '/peopleManage/people',
        method: 'put',
        data: data
    })
}

// 删除人员管理
export function delPeople(id) {
    return request({
        url: '/peopleManage/people/' + id,
        method: 'delete'
    })
}

// 导出人员管理
export function exportPeople(query) {
    return request({
        url: '/peopleManage/people/export',
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
// 查询部门下拉树结构
export function treeselect() {
    return request({
        url: '/system/dept/treeselect',
        method: 'get'
    })
}

// 统计人员人数
export function countNumber() {
    return request({
        url: '/peopleManage/people/countNumber',
        method: 'get',
        timeout: 50000,
    })
}

// 下载Excel模板
export function downloadTemplate() {
    return request({
        url: '/peopleManage/people/excel/template',
        method: 'get',
        responseType: 'blob'
    })
}

// 导入人员数据
export function importPeople(file) {
    const formData = new FormData();
    formData.append('file', file);
    return request({
        url: '/peopleManage/people/excel/import',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}