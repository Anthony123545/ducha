import request from "@/utils/request";

// 查询军车管理设置列表
export function listThebusConfig(query) {
  return request({
    url: "/militaryvehicleManagement/thebusConfig/list",
    method: "get",
    params: query,
  });
}

// 查询军车管理设置详细
export function getThebusConfig(id) {
  return request({
    url: "/militaryvehicleManagement/thebusConfig/" + id,
    method: "get",
  });
}

// 新增军车管理设置
export function addThebusConfig(data) {
  return request({
    url: "/militaryvehicleManagement/thebusConfig",
    method: "post",
    data: data,
  });
}

// 修改军车管理设置
export function updateThebusConfig(data) {
  return request({
    url: "/militaryvehicleManagement/thebusConfig",
    method: "put",
    data: data,
  });
}

// 删除军车管理设置
export function delThebusConfig(id) {
  return request({
    url: "/militaryvehicleManagement/thebusConfig/" + id,
    method: "delete",
  });
}

// 导出军车管理设置
export function exportThebusConfig(query) {
  return request({
    url: "/militaryvehicleManagement/thebusConfig/export",
    method: "get",
    params: query,
  });
}

// 查询军车管理设置详细
export function getThebusUser(id) {
  return request({
    url: "system/user/list",
    method: "get",
  });
}
export function getThebusUserByRoleId(roleId) {
  return request({
    url: "system/ /" + roleId,
    method: "get",
  });
}

// 新增审批人员
export function addAccessPeople(data) {
  return request({
    url: "/militaryvehicleManagement/thebusConfig/addAccessPeople",
    method: "post",
    data: data,
  });
}
//查询审批人员
export function listAccessPeople(query) {
  return request({
    url: "/militaryvehicleManagement/thebusConfig/listAccessPeople",
    method: "get",
    params: query,
  });
}

// 删除审批人员
export function delAccessPeople(ids) {
  return request({
    url: "/militaryvehicleManagement/thebusConfig/del/",
    method: "delete",
    data: ids,
  });
}

// 查询审批人员
export function getAccessPeople(id) {
  return request({
    url: "/militaryvehicleManagement/thebusConfig/getAccessPeople/" + id,
    method: "get",
  });
}

// 修改审批人员信息
export function updateAccessPeople(data) {
  return request({
    url: "/militaryvehicleManagement/thebusConfig/updateAccessPeople",
    method: "put",
    data: data,
  });
}

//查询所有审批人员
export function listAllAccessPeople() {
  return request({
    url: "/militaryvehicleManagement/thebusConfig/listAllAccessPeople",
    method: "get",
  });
}
