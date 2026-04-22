import request from '@/utils/request'
// 获取基地值班的信息
export function getTodayzhiban() {
  return request({
    url: '/combatduty/quantistatis/todayzhiban',
    method: 'get',
  })
}

// 获取大队值班接口
export function greatDuty() {
  return request({
    url: "/combatduty/quantistatis/daduizhiban",
    method: "get"
  })
}
// 获取大队值班分队数量
export function greatDutys() {
  return request({
    url: "/combatduty/quantistatis/getdaDuiZhiBanNumber",
    method: "get"
  })
}

// 获取军车值班接口
export function militaryDuty() {
  return request({
    url: "/combatduty/quantistatis/junchezhiban",
    method: "get"
  })
}

// 获取装备值班接口
export function equipment() {
  return request({
    url: "/combatduty/quantistatis/zhuangbeizhiban",
    method: "get"
  })
}

// 获取执行任务人员情况接口
export function zhixingrenwu(data) {
  return request({
    url: "/combatduty/taskdutyinfo/list",
    method: "get",
    params:data
  })
}


// 获取执行任务人员情况接口
export function listshow() {
  return request({
    url: "/combatduty/quantistatis/listshow",
    method: "get"
  })
}




