import request from "@/utils/request";

// 查询文件管理登计本列表
export function listBdglfilemanagement(query) {
  return request({
    url: "/grassrootsregistration/bdglfilemanagement/list",
    method: "get",
    params: query,
  });
}

// 查询文件管理登计本详细
export function getBdglfilemanagement(id) {
  return request({
    url: "/grassrootsregistration/bdglfilemanagement/" + id,
    method: "get",
  });
}

// 查看文件管理登计本详细
export function fileunits(query) {
  return request({
    url: "/grassrootsregistration/bdglfilemanagement/fileunits",
    method: "get",
    params: query,
  });
}

// 新增文件管理登计本
export function addBdglfilemanagement(data) {
  return request({
    url: "/grassrootsregistration/bdglfilemanagement",
    method: "post",
    data: data,
  });
}

// 修改文件管理登计本
export function updateBdglfilemanagement(data) {
  return request({
    url: "/grassrootsregistration/bdglfilemanagement",
    method: "put",
    data: data,
  });
}

// 删除文件管理登计本
export function delBdglfilemanagement(id) {
  return request({
    url: "/grassrootsregistration/bdglfilemanagement/" + id,
    method: "delete",
  });
}

// 导出文件管理登计本
export function exportBdglfilemanagement(query) {
  return request({
    url: "/grassrootsregistration/bdglfilemanagement/export",
    method: "get",
    params: query,
  });
}

// 列表
export function docList(year) {
  return request({
    url: `/grassrootsregistration/bdglfilemanagement/docList`,
    method: "get",
    params: { year: year },
  });
}

// 详情
export function getBdglfilemanagementDetail(id) {
  return request({
    url: "/grassrootsregistration/bdglfilemanagement/detail/" + id,
    method: "get",
  });
}
