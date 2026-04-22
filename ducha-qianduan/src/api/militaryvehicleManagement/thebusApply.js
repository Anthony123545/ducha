import request from '@/utils/request'


// 查询用车申请列表
export function listThebusApply(query) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/list',
    method: 'get',
    params: query
  })
}

// 查询用车申请详细
export function getThebusApply(id) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/' + id,
    method: 'get'
  })
}

// 新增用车申请
export function addThebusApply(data) {
  return request({
    url: '/militaryvehicleManagement/thebusApply',
    method: 'post',
    data: data
  })
}

// 修改用车申请
export function updateThebusApply(data) {
  return request({
    url: '/militaryvehicleManagement/thebusApply',
    method: 'put',
    data: data
  })
}

// 删除用车申请
export function delThebusApply(id) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/' + id,
    method: 'delete'
  })
}

// 导出用车申请
export function exportThebusApply(query) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/export',
    method: 'get',
    params: query
  })
}

// 导出可出车列表
export function exportThebusAvailableList(query) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/exportThebusAvailableList',
    method: 'get',
    params: query
  })
}

// 营级审批
export function exportThebusYin(query) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/yingjishenpi',
    method: 'get',
    params: query
  })
}
// 初审审批
export function exportThebusChu(query) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/chushenshenpi',
    method: 'get',
    params: query
  })
}
// 一级审批
export function exportThebusOne(query) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/yijishenpi',
    method: 'get',
    params: query
  })
}
// 二级审批
export function exportThebusTwo(query) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/erjishenpi',
    method: 'get',
    params: query
  })
}
// 三级审批
export function exportThebusThree(query) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/sanjishenpi',
    method: 'get',
    params: query
  })
}


// 添加出车列表
export function exportThebusCar(data) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/chuchequren',
    method: 'post',
    data: data
  })
}
// 查看出车列表
export function exportThebusCha(id) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/lookchuchelist/' + id,
    method: 'get',
  })
}

// 查询可出车列表
export function chuCarList(query) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/chuchelist',
    method: 'get',
    params: query
  })
}

// 查询可出军车在位列表
export function zaiweiCar(query) {
  return request({
    url: '/militaryvehicleManagement/thebus/zaiweilistlist',
    method: 'get',
    params: query
  })
}

// 新增可出车列表添加
export function addKeChuChe(data) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/addchuchequren',
    method: 'put',
    data: data
  })
}

export function updateKeChuChe(data) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/updatechuchequren',
    method: 'post',
    data: data
  })
}

// 新增可出车列表添加
export function sb(query) {
  return request({
    url: '/system/dept/get',
    method: 'get',
    params: query
  })
}
// 批量审批
export function examine(data) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/examine',
    method: 'post',
    data
  })
}

// 出车归队
export function guiDui(data) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/guiDui',
    method: 'put',
    data
  })
}

//删除可出车记录
export function delThebus(id) {
  return request({
    url: '/militaryvehicleManagement/thebusApply/single/' + id,
    method: 'delete'
    // data: data
  })
}
