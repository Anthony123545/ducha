import request from "@/utils/request";

// 查询集中文印主列表
export function listManage(query) {
  return request({
    url: "/PrintsManage/manage/list",
    method: "get",
    params: query
  });
}

// 查询集中文印主详细
export function getManage(id) {
  return request({
    url: "/PrintsManage/manage/" + id,
    method: "get"
  });
}

// 新增集中文印主
export function addManage(data) {
  return request({
    url: "/PrintsManage/manage",
    method: "post",
    data: data
  });
}

// 修改集中文印主
export function updateManage(data) {
  return request({
    url: "/PrintsManage/manage",
    method: "put",
    data: data
  });
}

// 删除集中文印主
export function delManage(id) {
  return request({
    url: "/PrintsManage/manage/" + id,
    method: "delete"
  });
}

// 导出集中文印主
export function exportManage(query) {
  return request({
    url: "/PrintsManage/manage/export",
    method: "get",
    params: query
  });
}

// 轮巡提醒
// export function printTrigger(data) {
//   return request({
//     url: "/PrintsManage/manage/printTrigger",
//     method: "POST",
//     data: data
//   });
// }

// 轮巡提醒 每人
export function printEachTrigger(data) {
  return request({
    url: "/PrintsManage/manage/printEachTrigger",
    method: "POST",
    data: data
  });
}

// 关闭轮巡提醒
export function printTriggerClear(data) {
  return request({
    url: "/PrintsManage/manage/printTriggerClear",
    method: "POST",
    data: data
  });
}

// 查询人员管理列表
export function listPeople(query) {
  return request({
    url: "/peopleManage/people/list",
    method: "get",
    params: query
  });
}

//打印获取详情
export function printCode(data) {
  return request({
    url: "/PrintsLog/printlog/printCode",
    method: "POST",
    data: data,
  });
}

//获取用户id
export function printPeople(data) {
  return request({
    url: "/PrintsManage/manage/printPeople",
    method: "POST",
    data: data
  });
}

// 确定打印弹框
export function tkbox(data) {
  return request({
    url: "/PrintsLog/printlog/printinsertredis",
    method: "POST",
    data: data
  });
}

// 集中文印统计
export function printCount(query) {
  return request({
    url: "/PrintsManage/manage/printCount",
    method: "get",
    params: query
  });
}
