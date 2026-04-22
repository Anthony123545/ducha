package com.supervision.web.controller.peopleChuRu;

import com.supervision.common.utils.StringUtils;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateCarService;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.service.IBdglLeaveService;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class PeopleStatusSyncTask {

    @Autowired
    private IBdglLeaveService bdglLeaveService;
    @Autowired
    private IBdglPeopleService bdglPeopleService;
    @Autowired
    private IBdglPrivateCarService privateCarService;

    // 每 1 分钟执行一次
    @Scheduled(cron = "0 * * * * ?")
// 在 PeopleStatusTask.java 中
    public void syncPeopleReignStatus() {
        // 1. 获取所有未归队的假单
        BdglLeave query = new BdglLeave();
        query.setIsReturn("0");
        List<BdglLeave> activeLeaves = bdglLeaveService.selectBdglLeaveList(query);

        Date now = new Date();

        for (BdglLeave leave : activeLeaves) {
            boolean changed = false;
            // 只有 status1, status2, status3 任意一个为 "1" 才继续执行逻辑
            // 或者更保险的写法（统一转成 String 比较）：
            boolean isApproved = "1".equals(String.valueOf(leave.getStatus1())) ||
                    "1".equals(String.valueOf(leave.getStatus2())) ||
                    "1".equals(String.valueOf(leave.getStatus3()));

            if (!isApproved) {
                // 如果还没审批通过，绝对不能改变人员状态，直接跳过此单据
                continue;
            }

            // 2. 自动判定是否过期归队
            if (now.after(leave.getEndTime())) {
                leave.setIsReturn("1");           // 标记为已归队
                leave.setActualreturntime(now);    // 记录时间
                changed = true;
            }

            // 3. 调用你的核心更新逻辑 (处理人员的 reign 状态)
            // 注意：建议将 updateBdglLeave 里的附属表插入逻辑加一个判断，
            // 或者只在手动归队(Controller)时处理附属表，定时任务里不处理附属表。
            this.updateBdglLeaveLogic(leave);

            // ---【新增：车辆联动逻辑】---
            if (StringUtils.isNotEmpty(leave.getPlateNumber())) {
                // 如果已归队，车辆设为 1（在位）；否则，如果时间已开始且未归队，设为 3（出车）
                String carState = "1";
                if ("0".equals(leave.getIsReturn()) && now.after(leave.getStartTime())) {
                    carState = "3";
                }
                updateCarStateByPlate(leave.getPlateNumber(), carState);
            }
            // ---【车辆逻辑结束】---

            // 4. 保存假单状态
            bdglLeaveService.updateBdglLeave(leave);
        }
    }
    /**
     * 根据车牌号更新车辆状态的辅助方法
     */
    private void updateCarStateByPlate(String plate, String state) {
        if (com.supervision.common.utils.StringUtils.isEmpty(plate)) return;

        com.supervision.militaryvehicleManagement.domain.BdglPrivateCar query = new com.supervision.militaryvehicleManagement.domain.BdglPrivateCar();
        query.setVehiclenumber(plate);
        // 注意：这里的 privateCarService 必须已经在类上方通过 @Autowired 注入了
        List<com.supervision.militaryvehicleManagement.domain.BdglPrivateCar> cars = privateCarService.selectBdglPrivateCarList(query);

        if (cars != null && !cars.isEmpty()) {
            for (com.supervision.militaryvehicleManagement.domain.BdglPrivateCar car : cars) {
                if (!state.equals(car.getState())) {
                    car.setState(state);
                    privateCarService.updateBdglPrivateCar(car);
                }
            }
        }
    }

    /**
     * 将你之前的逻辑提取出来，去掉附属表插入部分，专门给定时任务用
     */
    private void updateBdglLeaveLogic(BdglLeave bdglLeave) {
        String[] split = bdglLeave.getPeopleId().split(",");
        Date now = new Date();
        int targetReign = Integer.parseInt(bdglLeave.getLeaveType()) + 1;

        for (String s : split) {
            BdglPeople p = bdglPeopleService.selectBdglPeopleById(Long.valueOf(s));
            if (p != null) {
                // 如果已归队，或者时间不在范围内，设为 0；否则设为 targetReign
                if ("1".equals(bdglLeave.getIsReturn()) || now.before(bdglLeave.getStartTime()) || now.after(bdglLeave.getEndTime())) {
                    if (p.getReign() != 0) {
                        p.setReign(0);
                        bdglPeopleService.updateBdglPeople(p);
                    }
                } else {
                    if (p.getReign() != targetReign) {
                        p.setReign(targetReign);
                        bdglPeopleService.updateBdglPeople(p);
                    }
                }
            }
        }
    }

}
