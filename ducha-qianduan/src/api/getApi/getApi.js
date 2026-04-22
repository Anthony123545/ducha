

import request from '@/utils/request'

// 获取主持人id
export function getDeptId(url, path) {
    // console.log(path);
    return request({
        url,
        method: 'get',
        params: { 'deptId': path }
    })
}



