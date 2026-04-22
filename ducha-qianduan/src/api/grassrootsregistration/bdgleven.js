import request from '@/utils/request'

// 查询连务会记录本列表
export function listBdgleven(query) {
  return request({
    url: '/grassrootsregistration/bdgleven/list',
    method: 'get',
    params: query
  })
}
// 查询连务会记录本详细
export function getBdgleven(id) {
  return request({
    url: '/grassrootsregistration/bdgleven/' + id,
    method: 'get'
  })
}

// 新增连务会记录本
export function addBdgleven(data) {
  return request({
    url: '/grassrootsregistration/bdgleven',
    method: 'post',
    data: data
  })
}

// 修改连务会记录本
export function updateBdgleven(data) {
  return request({
    url: '/grassrootsregistration/bdgleven',
    method: 'put',
    data: data
  })
}

// 删除连务会记录本
export function delBdgleven(id) {
  return request({
    url: '/grassrootsregistration/bdgleven/' + id,
    method: 'delete'
  })
}

// 导出连务会记录本
export function exportBdgleven(query) {
  return request({
    url: '/grassrootsregistration/bdgleven/export',
    method: 'get',
    params: query
  })
}



// 查询部门 
export function getDept(query) {
  return request({
   url: '/grassrootsregistration/common/getDept',
    method: 'get',
   params: query
   })
  }
  
  // 查找主持人
  export function getZhuChiRen(data) {
   return request({
    url: '/grassrootsregistration/common/getZhuChiRen',
    method: 'get',
   params: { deptId:data}
   })
  }

    // 领导本
    export function getMeetRecordList(year) {
      return request({
       url: `/grassrootsregistration/bdgleven/getMeetRecordList`,
       method: 'get',
       params:{year:year}
      })
     }

    //  查询领导本列表
    export function getMeetRecordOnUnitList(query) {
      return request({
       url: '/grassrootsregistration/bdgleven/getMeetRecordOnUnitList',
       method: 'get',
       params: query
      })
     }