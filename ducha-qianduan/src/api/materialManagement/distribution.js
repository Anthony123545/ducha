import request from '@/utils/request'

// 查询物资发放列表
export function listDistribution(query) {
  return request({
    url: '/materialManagement/distribution/list',
    method: 'get',
    params: query
  })
}

// 查询物资发放详细
export function getDistribution(id) {
  return request({
    url: '/materialManagement/distribution/' + id,
    method: 'get'
  })
}

// 新增物资发放
export function addDistribution(data) {
  return request({
    url: '/materialManagement/distribution',
    method: 'post',
    data: data
  })
}

// 修改物资发放
export function updateDistribution(data) {
  return request({
    url: '/materialManagement/distribution',
    method: 'put',
    data: data
  })
}

// 删除物资发放
export function delDistribution(id) {
  return request({
    url: '/materialManagement/distribution/' + id,
    method: 'delete'
  })
}

// 导出物资发放
export function exportDistribution(query) {
  return request({
    url: '/materialManagement/distribution/export',
    method: 'get',
    params: query
  })
}
// 获取营具入库物品名称列表
export function exportMaterialRegWuPingList(query) {
  return request({
    url: '/materialManagement/distribution/getlist',
    method: 'get',
    params: query
  })
}

// 获取所有树形单位
export function treeselect(query) {
  return request({
    url: '/system/dept/treeselect',
    method: 'get',
    params: query
  })
}

// 物资接收
export function jieshou(data) {
  return request({
    url: '/materialManagement/distribution/jieshou',
    method: 'put',
    data
  })
}