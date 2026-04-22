
import requests from '@/utils/requests'

// 线上查询手机柜列表
export function xianPhone(query) {
  return requests({
    url: '/wl/api/v1/cabinets',
    method: 'get',
    params: query
  })
}
// 线上获取指定手机柜的全部格子
export function xianPhoneGeZi(query) {
  return requests({
    url: '/wl/api/v1/grids',
    method: 'get',
    params: { cabinetNum: query }
  })
}
// 线上开关门记录
export function xianPhoneOppe(query) {
  return requests({
    url: '/wl/api/v1/log/grid',
    method: 'get',
    params: query
  })
}
// 获取accessToken
export function getToken(data) {
  return requests({
    url: '/wl/api/getAccessToken',
    method: 'post',
    data
  })
}

// 获取accessToken
export function aaa(data) {
  return requests({
    url: '/wl/api/v1/log/store',
    method: 'GET',
    data
  })
}

