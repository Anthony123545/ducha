import request from '@/utils/request'

// 查询工作计划日历
export function peopleList(query) {
  return request({
    url: '/jobTaskRecord/jobTask/peopleList',
    method: 'get',
    params: query
  })
}
// 进度录入
export function progressInput(data) {
  return request({
    url: '/jobTaskRecord/jobTask/progressInput',
    method: 'put',
    data
  })
}
// 查看进度录入
export function queryTask(workId) {
  return request({
    url: `/jobTaskRecord/jobTask/queryTask/${workId}`,
    method: 'get',
  })
}

// 日历导出
export function exportCurrentUserTask(query) {
  return request({
    url: `/jobTaskRecord/jobTask/exportCurrentUserTask`,
    method: 'get',
    params: query
  })
}