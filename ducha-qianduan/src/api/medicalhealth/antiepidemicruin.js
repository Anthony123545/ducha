import request from '@/utils/request'

// 查询防疫用品销毁记录列表
export function listRuin(query) {
    return request({
        url: '/medicalhealth/ruin/list',
        method: 'get',
        params: query
    })
}

// 查询防疫用品销毁记录详细
export function getRuin(id) {
    return request({
        url: '/medicalhealth/ruin/' + id,
        method: 'get'
    })
}

// 新增防疫用品销毁记录
export function addRuin(data) {
    return request({
        url: '/medicalhealth/ruin',
        method: 'post',
        data: data
    })
}

// 修改防疫用品销毁记录
export function updateRuin(data) {
    return request({
        url: '/medicalhealth/ruin',
        method: 'put',
        data: data
    })
}

// 删除防疫用品销毁记录
export function delRuin(id) {
    return request({
        url: '/medicalhealth/ruin/' + id,
        method: 'delete'
    })
}

// 导出防疫用品销毁记录
export function exportRuin(query) {
    return request({
        url: '/medicalhealth/ruin/export',
        method: 'get',
        params: query
    })
}