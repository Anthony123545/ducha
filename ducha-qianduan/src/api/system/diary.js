import request from '@/utils/request'

// 查询要事日记设置 列表
export function listDiary(query) {
  return request({
    url: '/system/diary/list',
    method: 'get',
    params: query
  })
}

// 查询要事日记设置 详细
export function getDiary(id) {
  return request({
    url: '/system/diary/' + id,
    method: 'get'
  })
}

// 新增要事日记设置 
export function addDiary(data) {
  return request({
    url: '/system/diary',
    method: 'post',
    data: data
  })
}

// 修改要事日记设置 
export function updateDiary(data) {
  return request({
    url: '/system/diary',
    method: 'put',
    data: data
  })
}

// 删除要事日记设置 
export function delDiary(id) {
  return request({
    url: '/system/diary/' + id,
    method: 'delete'
  })
}
