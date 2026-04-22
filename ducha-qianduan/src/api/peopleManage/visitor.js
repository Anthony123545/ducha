import request from '@/utils/request';

// 查询访客管理列表
export function listVisitor(query) {
  return request({
    url: '/peopleManage/visitor/list',
    method: 'get',
    params: query
  });
}

// 查询访客管理详细
export function getVisitor(id) {
  return request({
    url: '/peopleManage/visitor/' + id,
    method: 'get'
  });
}

// 新增访客管理
export function addVisitor(data) {
  return request({
    url: '/peopleManage/visitor',
    method: 'post',
    data: data
  });
}

// 修改访客管理
export function updateVisitor(data) {
  return request({
    url: '/peopleManage/visitor',
    method: 'put',
    data: data
  });
}

// 删除访客管理
export function delVisitor(ids) {
  return request({
    url: '/peopleManage/visitor/' + ids,
    method: 'delete'
  });
}

// 导出访客管理
export function exportVisitor(query) {
  return request({
    url: '/peopleManage/visitor/export',
    method: 'get',
    params: query
  });
}