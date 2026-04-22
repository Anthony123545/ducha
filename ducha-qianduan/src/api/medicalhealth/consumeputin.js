import request from '@/utils/request'

// 查询耗材入库登记列表
export function listPutin(query) {
  return request({
    url: '/medicalhealth/consumeputin/list',
    method: 'get',
    params: query
  })
}

// 查询耗材入库登记详细
export function getPutin(id) {
  return request({
    url: '/medicalhealth/consumeputin/' + id,
    method: 'get'
  })
}

// 新增耗材入库登记
export function addPutin(data) {
  return request({
    url: '/medicalhealth/consumeputin',
    method: 'post',
    data: data
  })
}

// 修改耗材入库登记
export function updatePutin(data) {
  return request({
    // url: '/medicalhealth/consumeputin',
    url: '/medicalhealth/consumeputin/update',
    method: 'put',
    data: data
  })
}

// 删除耗材入库登记
export function delPutin(id) {
  return request({
    url: '/medicalhealth/consumeputin/' + id,
    method: 'delete'
  })
}

// 导出耗材入库登记
export function exportPutin(query) {
  return request({
    url: '/medicalhealth/consumeputin/export',
    method: 'get',
    params: query
  })
}

// 药品过期列表
export function exportYaoPing(query) {
  return request({
    url: '/medicalhealth/yaopinguoqi/getGuoQi',
    method: 'get',
    params: query
  })
}
// 药品销毁
export function exportYaoPingXiaoHui(query) {
  return request({
    url: '/medicalhealth/yaopinguoqi',
    method: 'get',
    params: {
      id: query
    }
  })
}

// 获取耗材名称列表
export function exportHaoCai(query) {
  return request({
    url: '/medicalhealth/consumeputin/getlist',
    method: 'get',
    params: query
  })
}
//  获取耗材出库名称列表
export function exportHaoCaiChu(query) {
  return request({
    url: '/medicalhealth/consumeputout/getlist',
    method: 'get',
    params: query
  })
}
// 新增耗材过期入库登记
export function addPutins(data) {
  return request({
    url: '/medicalhealth/consumeputin/insert',
    method: 'post',
    data: data
  })
}
