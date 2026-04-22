import request from '@/utils/request'

// 查询物资档案列表
export function listArchives(query) {
    return request({
        url: '/materialManagement/materialRegistration/storageStatistics',
        method: 'get',
        params: query
    })
}

// 查询物资概览数据
export function getMaterialOverview() {
    return request({
        url: '/materialManagement/materialRegistration/echarts/storage/list',
        method: 'get'
    })
}