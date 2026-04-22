import request from '@/utils/request'

// 查询专项检查列表
export function listSpecialinfo(query) {
  return request({
    url: '/bdglregular/specialinfo/list',
    method: 'get',
    params: query
  })
}

// 查询专项检查详细
export function getSpecialinfo(id) {
  return request({
    url: '/bdglregular/specialinfo/' + id,
    method: 'get'
  })
}

// 新增专项检查
export function addSpecialinfo(data) {
  return request({
    url: '/bdglregular/specialinfo',
    method: 'post',
    data: data
  })
}

// 修改专项检查
export function updateSpecialinfo(data) {
  return request({
    url: '/bdglregular/specialinfo',
    method: 'put',
    data: data
  })
}

// 删除专项检查
export function delSpecialinfo(id) {
  return request({
    url: '/bdglregular/specialinfo/' + id,
    method: 'delete'
  })
}

// 导出专项检查
export function exportSpecialinfo(query) {
  return request({
    url: '/bdglregular/specialinfo/export',
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
    url: '/bdglregular/specialinfo/editquestion',
    method: 'post',
    data: data
  })
}

//通过
export function editissuccess(data) {
  return request({
    url: '/bdglregular/specialinfo/editissuccess',
    method: 'post',
    data: data
  })
}

//未通过
export function editisfalse(data) {
  return request({
    url: '/bdglregular/specialinfo/editisfalse',
    method: 'post',
    data: data
  })
}

// 带验收数据
export function bumenlist(id) {
  return request({
    url: `/bdglregular/specialinfo/bumenlist?inspectedUnitId=${id}`,
    method: 'get',
  })
}

// 查询专项检查列表
export function listSpecialinfos(query) {
  return request({
    url: '/bdglregular/specialinfo/lists',
    method: 'get',
    params: query
  })
}
