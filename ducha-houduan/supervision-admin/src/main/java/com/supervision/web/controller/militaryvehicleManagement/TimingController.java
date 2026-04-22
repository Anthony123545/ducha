package com.supervision.web.controller.militaryvehicleManagement;

import com.supervision.common.utils.DateUtils;
import com.supervision.militaryvehicleManagement.domain.BdglPrivateAccessLog;
import com.supervision.militaryvehicleManagement.domain.BdglPrivateCar;
import com.supervision.militaryvehicleManagement.domain.BdglThebusAccessLog;
import com.supervision.militaryvehicleManagement.domain.TcTodayInouttime;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateAccessLogService;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateCarService;
import com.supervision.militaryvehicleManagement.service.IBdglThebusAccessLogService;
import com.supervision.militaryvehicleManagement.service.ITcTodayInouttimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class TimingController {

    @Autowired
    private ITcTodayInouttimeService tcTodayInouttimeService;

    @Autowired
    private IBdglPrivateAccessLogService privateCarService;

    @Autowired
    private IBdglThebusAccessLogService accessLogService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 定时更新私家车在位状态
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void updatePrivateCarState() {
        TcTodayInouttime tcTodayInouttime = new TcTodayInouttime();
        String carId = redisTemplate.opsForValue().get("carId");
        List<TcTodayInouttime> tcTodayInouttimes;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //如果时间是null查询所有  并记录数据库最新插入时间
        if (carId != null) {

            tcTodayInouttime.setpKey(Long.valueOf(carId));
            tcTodayInouttimes = tcTodayInouttimeService.selectTcTodayInouttimeList(tcTodayInouttime);

            //如果时间不是null  以车牌号分组查询大于记录时间的数据   并更新私家车状态
        } else {
            carId = "39000";
            tcTodayInouttime.setpKey(Long.valueOf(carId));
            tcTodayInouttimes = tcTodayInouttimeService.selectTcTodayInouttimeList(tcTodayInouttime);
        }
        if (tcTodayInouttimes.size() > 0) {
            int i = 0;
            for (TcTodayInouttime tcTodayInouttime1 : tcTodayInouttimes) {
                i++;
                if (tcTodayInouttime1.getCarno().contains("YZ")) {
                    BdglThebusAccessLog bdglThebusAccessLog = new BdglThebusAccessLog();
                    //车牌号*
                    bdglThebusAccessLog.setVehiclenumber(tcTodayInouttime1.getCarno());
                    //备注
                    bdglThebusAccessLog.setRemark(tcTodayInouttime1.getRemark());
                    //进入时间*
                    bdglThebusAccessLog.setWaichushijian(tcTodayInouttime1.getInTime());
                    //出去时间*
                    bdglThebusAccessLog.setFanhuishijian(tcTodayInouttime1.getOutTime());


                    accessLogService.insertBdglThebusAccessLog(bdglThebusAccessLog);
                } else {
                    BdglPrivateAccessLog bdglPrivateCar = new BdglPrivateAccessLog();
                    //车牌号
                    bdglPrivateCar.setVehiclenumber(tcTodayInouttime1.getCarno());

                    //进入时间
                    bdglPrivateCar.setJinrushiajin(tcTodayInouttime1.getInTime());
                    //出去时间
                    bdglPrivateCar.setLikaishitian(tcTodayInouttime1.getOutTime());

                    bdglPrivateCar.setRemark(tcTodayInouttime1.getRemark());
                    privateCarService.insertBdglPrivateAccessLog(bdglPrivateCar);
                }
                if (String.valueOf(i).equals(String.valueOf(tcTodayInouttimes.size())) ) {
                    redisTemplate.opsForValue().set("carId",tcTodayInouttime1.getpKey().toString());
                }
            }
        }

    }

}