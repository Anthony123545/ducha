import request from '@/utils/request'

// 查询单身干部宿舍列表
export function listDorm(query) {
    return request({
        url: '/barracksManagement/scadred/list',
        method: 'get',
        params: query
    })
}

// 查询单身干部宿舍详细
export function getDorm(id) {
    return request({
        url: '/barracksManagement/scadred/' + id,
        method: 'get'
    })
}

// 新增单身干部宿舍
export function addDorm(data) {
    return request({
        url: '/barracksManagement/scadred',
        method: 'post',
        data: data
    })
}

// 修改单身干部宿舍
export function updateDorm(data) {
    return request({
        url: '/barracksManagement/scadred',
        method: 'put',
        data: data
    })
}

// 删除单身干部宿舍
export function delDorm(id) {
    return request({
        url: '/barracksManagement/scadred/' + id,
        method: 'delete'
    })
}

// 导出公寓房档案
export function exportScadred(query) {
    return request({
        url: '/barracksManagement/scadred/export',
        method: 'get',
        params: query
    })
}