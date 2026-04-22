
import request from '@/utils/request'

// 过期耗材列表
export function consumeGuoQi(query) {
    return request({
        url: '/medicalhealth/consumeGuoQi/list',
        method: 'get',
        params: query
    })
}

// 是否销毁
export function consumeXiaoHui(data) {
    return request({
        url: '/medicalhealth/consumeputin',
        method: 'put',
        data: data
    })
}

// 过期防疫用品列表
export function antiepidemicGuoQi(query) {
    return request({
        url: '/medicalhealth/antiepidemicGuoQi/list',
        method: 'get',
        params: query
    })
}

