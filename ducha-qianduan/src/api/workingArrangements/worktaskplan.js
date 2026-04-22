import request from '@/utils/request'

// 查询任务计划基本信息列表
export function listWorktaskplan(query) {
  return request({
    url: '/workingArrangements/worktaskplan/list',
    method: 'get',
    params: query
  })
}

// 查询任务计划基本信息详细
export function getWorktaskplan(id) {
  return request({
    url: '/workingArrangements/worktaskplan/' + id,
    method: 'get'
  })
}

// 新增任务计划基本信息
export function addWorktaskplan(data) {
  return request({
    url: '/workingArrangements/worktaskplan',
    method: 'post',
    data: data
  })
}

// 修改任务计划基本信息
export function updateWorktaskplan(data) {
  return request({
    url: '/workingArrangements/worktaskplan',
    method: 'put',
    data: data
  })
}

// 删除任务计划基本信息
export function delWorktaskplan(id) {
  return request({
    url: '/workingArrangements/worktaskplan/' + id,
    method: 'delete'
  })
}

// 查询任务计划信息详细
export function getBytTskPlanId(id) {
  return request({
    url: '/workingArrangements/taskplandetail/getBytTskPlanId/' + id,
    method: 'get'
  })
}
// 查看任务计划信息详细
export function taskplandetailIds(id) {
  return request({
    url: '/workingArrangements/taskplandetail/' + id,
    method: 'get'
  })
}

// 查询任务计划执行详情列表
export function listTaskplandetail(query) {
  return request({
    url: '/workingArrangements/taskplandetail/list',
    method: 'get',
    params: query
  })
}

// 新增添加任务详情
export function taskplandetail(data) {
  return request({
    url: '/workingArrangements/taskplandetail',
    method: 'post',
    data: data
  })
}

// 编辑
export function addAnnex(data) {
  return request({
    url: '/workingArrangements/taskplandetail/addAnnex',
    method: 'post',
    data: data
  })
}

// 查询任务计划执行详情详细列表
export function executeList(query) {
  return request({
    url: '/workingArrangements/worktaskplan/executeList',
    method: 'get',
    params: query
  })
}

// 修改任务计划执行详情
export function updateTaskplandetail(data) {
  return request({
    url: '/workingArrangements/taskplandetail',
    method: 'put',
    data: data
  })
}

// 删除任务计划执行详情
export function delTaskplandetail(id) {
  return request({
    url: '/workingArrangements/taskplandetail/' + id,
    method: 'delete'
  })
}

// 查询部门 
export function getDepts(query) {
  return request({
    url: '/system/dept/list',
    method: 'get',
    params: query
  })
}

// 查询用户列表
export function listUsers(query) {
  return request({
    url: '/system/user/lists',
    method: 'get',
    params: query
  })
}

// 查询人员(用户，包含自己)
export function getUserzs(data) {
  return request({
    url: 'system/user/lists',
    method: 'get',
    params: { deptId: data }
  })
}


// 审批接口
export function audit(data) {
  return request({
    url: '/workingArrangements/taskplandetail/audit',
    method: 'put',
    data: data
  })
}