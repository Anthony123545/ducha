import request from '@/utils/request'
// 获取人员五率的信息
export function getReteOfTen() {
    return request({
      url: '/peoplefiverates/probinfo/list',
      method: 'get',
    })
  }
// 获取六量列表接口
export function getliuliang(){
  return request({
    url:"/peoplefiverates/measureinfo/list",
    method:"get"
  })
}
// 获取修改接口
export function updateliuliang(data){
  return request({
    url:"/peoplefiverates/measureinfo",
    method:"post",
    data:data
  })
}

// 人员五率添加接口
export function updatewulv(data){
  return request({
    url:"/peoplefiverates/probinfo",
    method:"post",
    data:data
  })
}

// 装备五率添加接口
export function updatezbwl(data){
  return request({
    url:"/peoplefiverates/outfitinfo",
    method:"post",
    data:data
  })
}

// 装备五率查询接口
export function getzbwl(data){
  return request({
    url:"/peoplefiverates/outfitinfo/list",
    method:"get",
    data:data
  })
}

// 获取装备可用数接口
export function getzbky(){
  return request({
    url:"/combatduty/quantistatis/zhuanbeikeyongshu",
    method:"get",
  })
}

