import request from '@/utils/request'

// 查询列表
export function recallList(query) {
    return request({
        url: 'peopleManage/recall/list',
        method: 'get',
        params: query
    })
}

// 新增
export function recall(data) {
    return request({
        url: 'peopleManage/recall',
        method: 'post',
        data: data
    })
}



// 政审
export function getAuthorization(data) {
    return request({
        url: 'peopleManage/recall/edit',
        method: 'post',
        data: data
    })
}

// 审核按钮
export function getAuthorizations(query) {
    return request({
        url: 'peopleManage/recall/getAuthorization/',
        method: 'get',
        params: query
    })
}

// 删除
export function recallDell(ids) {
    return request({
        url: 'peopleManage/recall/' + ids,
        method: 'delete'
    })
}




