import request from '@/utils/request'

// 查询经常性检查列表
export function listRegularinfo(query) {
  return request({
    url: '/bdglregular/regularinfo/list',
    method: 'get',
    params: query
  })
}

// 查询经常性检查详细
export function getRegularinfo(id) {
  return request({
    url: '/bdglregular/regularinfo/' + id,
    method: 'get'
  })
}

// 新增经常性检查
export function addRegularinfo(data) {
  return request({
    url: '/bdglregular/regularinfo',
    method: 'post',
    data: data
  })
}

// 修改经常性检查
export function updateRegularinfo(data) {
  return request({
    url: '/bdglregular/regularinfo',
    method: 'put',
    data: data
  })
}

// 删除经常性检查
export function delRegularinfo(id) {
  return request({
    url: '/bdglregular/regularinfo/' + id,
    method: 'delete'
  })
}

// 导出经常性检查
export function exportRegularinfo(query) {
  return request({
    url: '/bdglregular/regularinfo/export',
    method: 'get',
    params: query
  })
}

// 查询部门 
export function getDept(query) {
  return request({
    url: '/system/dept/list',
    method: 'get',
    params: query
  })
}

// 查询政审对象管理列表
export function listPeople(query) {
  return request({
    url: '/peopleManage/people/list',
    method: 'get',
    params: query
  })
}

//整改
export function editquestion(data) {
  return request({
    url: '/bdglregular/regularinfo/editquestion',
    method: 'post',
    data: data
  })
}

//通过
export function editissuccess(data) {
  return request({
    url: '/bdglregular/regularinfo/editissuccess',
    method: 'post',
    data: data
  })
}

//未通过
export function editisfalse(data) {
  return request({
    url: '/bdglregular/regularinfo/editisfalse',
    method: 'post',
    data: data
  })
}

// 带验收数据
export function bumenlist(id) {
  return request({
    url: `/bdglregular/regularinfo/bumenlist?inspectedUnitId=${id}`,
    method: 'get',
  })
}

// 查询经常性检查列表
export function listRegularinfos(query) {
  return request({
    url: '/bdglregular/regularinfo/lists',
    method: 'get',
    params: query
  })
}

// 获取单位列表
export function getDeptss(query) {
  return request({
    url: '/system/dept/treeselect',
    method: 'get',
    params: query
  })
}
