import request from '@/utils/request'

// 查询疫情通知公告列表
export function listAnnouncement(query) {
  return request({
    url: '/medicalhealth/announcement/list',
    method: 'get',
    params: query
  })
}

// 查询疫情通知公告详细
export function getAnnouncement(id) {
  return request({
    url: '/medicalhealth/announcement/' + id,
    method: 'get'
  })
}

// 新增疫情通知公告
export function addAnnouncement(data) {
  return request({
    url: '/medicalhealth/announcement',
    method: 'post',
    data: data
  })
}

// 修改疫情通知公告
export function updateAnnouncement(data) {
  return request({
    url: '/medicalhealth/announcement',
    method: 'put',
    data: data
  })
}

// 删除疫情通知公告
export function delAnnouncement(id) {
  return request({
    url: '/medicalhealth/announcement/' + id,
    method: 'delete'
  })
}

// 导出疫情通知公告
export function exportAnnouncement(query) {
  return request({
    url: '/medicalhealth/announcement/export',
    method: 'get',
    params: query
  })
}