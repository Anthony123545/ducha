import request from "@/utils/request";

// 在位情况
export function zaiweipeople() {
  return request({
    url: "/situation/situationinfo/zaiweipeople",
    method: "get"
  });
}

// 个单位人员样式
export function gedanweizaiweiqingkaung() {
  return request({
    url: "/situation/situationinfo/gedanweizaiweiqingkaung",
    method: "get"
  });
}

// 外出人员对比
export function waichurenyuanzhanbi() {
  return request({
    url: "/situation/situationinfo/waichurenyuanzhanbi",
    method: "get"
  });
}
// 外出人员对比2
export function waichurenyuanzhanbi2() {
  return request({
    url: "/combatduty/contrast/get",
    method: "get"
  });
}

// 今日值班
export function jinrizhiban() {
  return request({
    url: "/situation/situationinfo/jinrizhiban",
    method: "get"
  });
}
// 今日值班总人数
export function zongjinrizhiban() {
  return request({
    url: "/combatduty/quantistatis/getdaDuiZhiBanNumber",
    method: "get"
  });
}
// 登记落实
export function tongjiluoshi() {
  return request({
    url: "/situation/situationinfo/tongjiluoshi",
    method: "get"
  });
}

// 集中文印
export function rizongwenyin() {
  return request({
    url: "/situation/situationinfo/rizongwenyin",
    method: "get"
  });
}

// 军车管理
export function juncheguanli() {
  return request({
    url: "/situation/situationinfo/juncheguanli",
    method: "get"
  });
}

// 统计物品外借数量
export function getNumber() {
  return request({
    url: "/bdglSecret/secret/getNumber",
    method: "get"
  });
}

// 统计检查督导数量
export function getJieCha() {
  return request({
    url: "/situation/situationinfo/unitrank",
    method: "get"
  });
}

// 营具
export function getantiepidemic() {
  return request({
    url: "/situation/situationinfo/getantiepidemic",
    method: "get"
  });
}

// 防疫
export function antiepidemicArchivesList() {
  return request({
    url: "/situation/situationinfo/antiepidemicArchivesList",
    method: "get"
  });
}

// 人员请销假
export function getLeavlNumber() {
  return request({
    url: "/situation/situationinfo/getLeavlNumber",
    method: "get"
  });
}
// 营房统计
export function zaiweipeopleGet() {
  return request({
    url: "/situation/situationinfo/getBarracks",
    method: "get"
  });
}
// 态势军事训练月统计情况列表
export function listTraining(query) {
  return request({
    url: '/system/training/list',
    method: 'get',
    params: query
  })
}

// 态势装备值班情况
export function selectCount(query) {
  return request({
    url: '/combatduty/regisdutyinfo/selectCount',
    method: 'get',
    params: query
  })
}

// 态势车辆在位情况
export function selectCounts(query) {
  return request({
    url: '/militaryvehicleManagement/thebus/selectCount',
    method: 'get',
    params: query
  })
}
