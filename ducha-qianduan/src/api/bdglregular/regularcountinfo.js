import request from "@/utils/request";

// 查询营级单位排名列表
export function unitrank() {
  return request({
    url: "/bdglregular/regularcountinfo/unitrank",
    method: "get"
  });
}

// 查询待处理及待验收问题列表
export function pendingacceptance() {
  return request({
    url: "/bdglregular/regularcountinfo/pendingacceptance",
    method: "get"
  });
}

// 查询整体状态完成比例列表
export function completeproportion() {
  return request({
    url: "/bdglregular/regularcountinfo/completeproportion",
    method: "get"
  });
}

// 查询各营级及机关单位列表
export function unitproportion() {
  return request({
    url: "/bdglregular/regularcountinfo/unitproportion",
    method: "get"
  });
}

// 查询经常性检查详细
export function getRegularinfo(id) {
  return request({
    url: '/bdglregular/regularinfo/' + id,
    method: 'get'
  })
}

