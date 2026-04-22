import request from '@/utils/request'

// 查询一周食谱列表
export function listWeekRecipe(query) {
  return request({
    url: '/grassrootsregistration/weekRecipe/list',
    method: 'get',
    params: query
  })
}

// 查询一周食谱详细
export function getWeekRecipe(id) {
  return request({
    url: '/grassrootsregistration/weekRecipe/' + id,
    method: 'get'
  })
}

// 新增一周食谱
export function addWeekRecipe(data) {
  return request({
    url: '/grassrootsregistration/weekRecipe',
    method: 'post',
    data: data
  })
}

// 修改一周食谱
export function updateWeekRecipe(data) {
  return request({
    url: '/grassrootsregistration/weekRecipe',
    method: 'put',
    data: data
  })
}

// 删除一周食谱
export function delWeekRecipe(id) {
  return request({
    url: '/grassrootsregistration/weekRecipe/' + id,
    method: 'delete'
  })
}

// 导出一周食谱 Word
export function exportWeekRecipeWord(id) {
  return request({
    url: '/grassrootsregistration/weekRecipe/exportWord/' + id,
    method: 'get',
    responseType: 'blob'
  })
}

// 预览一周食谱
export function previewWeekRecipe(id) {
  return request({
    url: '/grassrootsregistration/weekRecipe/preview/' + id,
    method: 'get'
  })
}
