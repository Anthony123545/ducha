import request from '@/utils/request'

// 查询一周食谱列表
export function listBdglcookbook(query) {
    return request({
        url: '/grassrootsregistration/bdglcookbook/list',
        method: 'get',
        params: query
    })
}

// 查询一周食谱详细
export function getBdglcookbook(id) {
    return request({
        url: '/grassrootsregistration/bdglcookbook/' + id,
        method: 'get'
    })
}

// 新增一周食谱
export function addBdglcookbook(data) {
    return request({
        url: '/grassrootsregistration/bdglcookbook',
        method: 'post',
        data: data
    })
}

// 修改一周食谱
export function updateBdglcookbook(data) {
    return request({
        url: '/grassrootsregistration/bdglcookbook',
        method: 'put',
        data: data
    })
}

// 删除一周食谱
export function delBdglcookbook(id) {
    return request({
        url: '/grassrootsregistration/bdglcookbook/' + id,
        method: 'delete'
    })
}

// 导出一周食谱
export function exportBdglcookbook(query) {
    return request({
        url: '/grassrootsregistration/bdglcookbook/export',
        method: 'get',
        params: query
    })
}
//导出一周食谱Excel
export function exportBdglcookbookExcel(id) {
    return request({
        url: '/grassrootsregistration/bdglcookbook/exportExcel/' + id,
        method: 'get',
        responseType: 'blob'
    })
}
//  获取所有食品
export function getThings(query) {
    return request({
        url: '/grassrootsregistration/bdglcookbook/getfood',
        method: 'get',
        params: query
    })
}