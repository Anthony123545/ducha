import request from '@/utils/request'

// 查询私家车列表
export function personalCarStat(query) {
  return request({
    url: '/militaryvehicleManagement/privateAccessLog/personalCarStat',
    method: 'get',
    params: query
  })
}

// 导出私家车模板
export function exportCarStat(query) {
    return request({
      url: '/militaryvehicleManagement/privateAccessLog/exportCarStat',
      method: 'get',
      params: query
    })
  }