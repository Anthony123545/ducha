// src/api/safety.js
import request from '@/utils/request' // 若你的项目不是这个封装，请把这行改成实际 axios 实例

// 列表/台账
export function fetchIssues(params) {
  // params: { pageNum, pageSize, keyword, level, status, from, to }
  return request({
    url: '/api/safety/issues',
    method: 'get',
    params
  })
}

// 新增问题
export function createIssue(data) {
  // data: { description, riskLevel, foundAt }
  return request({
    url: '/api/safety/issues',
    method: 'post',
    data
  })
}

// 更新状态（标记已整改/未整改）
export function updateIssueStatus(id, status) {
  return request({
    url: `/api/safety/issues/${id}/status`,
    method: 'put',
    data: { status }
  })
}

// 删除（逻辑删除）
export function deleteIssue(id) {
  return request({
    url: `/api/safety/issues/${id}`,
    method: 'delete'
  })
}

// 仪表盘
export function fetchDashboard(days = 7) {
  return request({
    url: '/api/safety/dashboard',
    method: 'get',
    params: { days }
  })
}
