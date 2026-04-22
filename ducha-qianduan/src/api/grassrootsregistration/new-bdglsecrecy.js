// api/deviceRegister.js
import request from "@/utils/request"; // 假设你已经封装了 request

// 列表
export function listDeviceRegister() {
  return request({
    url: "/grassrootsregistration/device/register/list",
    method: "get",
  });
}

// 新增
export function addDeviceRegister(data) {
  return request({
    url: "/grassrootsregistration/device/register/add",
    method: "post",
    data: data,
  });
}

// 详情
export function getDeviceRegisterDetail(id) {
  return request({
    url: `/grassrootsregistration/device/register/detail/${id}`,
    method: "get",
  });
}
