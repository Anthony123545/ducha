import request from '@/utils/request'

// 查询态势军事训练月统计情况列表
export function listTraining(query) {
  return request({
    url: '/system/training/list',
    method: 'get',
    params: query
  })
}

// 查询态势军事训练月统计情况详细
export function getTraining(id) {
  return request({
    url: '/system/training/' + id,
    method: 'get'
  })
}

// 新增态势军事训练月统计情况
export function addTraining(data) {
  return request({
    url: '/system/training',
    method: 'post',
    data: data
  })
}

// 修改态势军事训练月统计情况
export function updateTraining(data) {
  return request({
    url: '/system/training',
    method: 'put',
    data: data
  })
}

// 删除态势军事训练月统计情况
export function delTraining(id) {
  return request({
    url: '/system/training/' + id,
    method: 'delete'
  })
}
