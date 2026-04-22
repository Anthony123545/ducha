package com.supervision.web.controller.medicalhealth;

import com.supervision.common.utils.DateUtils;
import com.supervision.grassrootsregistration.service.IBdglAttendanceRecordService;
import com.supervision.medicalhealth.domain.*;
import com.supervision.medicalhealth.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class DingShiController {
    @Autowired
    private IBdglDurgArchivesService bdglDurgArchivesService;
    @Autowired
    private IBdglDurgPutinService bdglDurgPutinService;

    @Autowired
    private IBdglConsumePutinService consumePutinService;

    @Autowired
    private IBdglConsumeArchivesService consumeArchivesService;
    @Autowired
    private IBdglAntiepidemicPutinService antiepidemicPutinService;

    @Autowired
    private IBdglAntiepidemicArchivesService antiepidemicArchivesService;

    @Autowired
    private IBdglAttendanceRecordService bdglAttendanceRecordService;

    /**
     * 统计药品过期数量
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void yaoPinGuoQiTJ() {
        Date nowDate = DateUtils.getNowDate();
        List<BdglDurgArchives> bdglDurgArchives = bdglDurgArchivesService.selectBdglDurgArchivesList(new BdglDurgArchives());
        for (BdglDurgArchives bdglDurgArchive : bdglDurgArchives) {
            BdglDurgPutin bdglDurgPutin = new BdglDurgPutin();
            bdglDurgPutin.setDurgArchivesId(Integer.parseInt(bdglDurgArchive.getId().toString()));
            bdglDurgPutin.setOverduedate(nowDate);
            List<BdglDurgPutin> bdglDurgPutins = bdglDurgPutinService.selectGuoQiList(bdglDurgPutin);
            Integer sum = bdglDurgPutins.stream().mapToInt(BdglDurgPutin::getSurplus).sum();
            bdglDurgArchive.setOverdue(sum);
            bdglDurgArchivesService.updateBdglDurgArchives(bdglDurgArchive);

        }
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void haoCaiGuoQiTJ() {
        List<BdglConsumeArchives> bdglDurgArchives = consumeArchivesService.selectBdglConsumeArchivesList(new BdglConsumeArchives());
        Date nowDate = DateUtils.getNowDate();
        for (BdglConsumeArchives bdglDurgArchive : bdglDurgArchives) {
            BdglConsumePutin bdglDurgPutin = new BdglConsumePutin();
            bdglDurgPutin.setDurgArchivesId(Integer.parseInt(bdglDurgArchive.getId().toString()));
            bdglDurgPutin.setOverduedate(nowDate);
            List<BdglConsumePutin> bdglDurgPutins = consumePutinService.selectGuoQiList(bdglDurgPutin);
            Integer sum = bdglDurgPutins.stream().mapToInt(BdglConsumePutin::getSurplus).sum();
            bdglDurgArchive.setOverdue(sum);
            consumeArchivesService.updateBdglConsumeArchives(bdglDurgArchive);

        }
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void fangYiGuoQiTJ() {
        List<BdglAntiepidemicArchives> bdglAntiepidemicArchives1 = antiepidemicArchivesService.selectBdglAntiepidemicArchivesList(new BdglAntiepidemicArchives());
        for (BdglAntiepidemicArchives bdglAntiepidemicArchives : bdglAntiepidemicArchives1) {

            BdglAntiepidemicPutin bdglAntiepidemicPutin = new BdglAntiepidemicPutin();
            bdglAntiepidemicPutin.setExpirationTime(new Date());
            bdglAntiepidemicPutin.setDurgArchivesId(Integer.parseInt(bdglAntiepidemicArchives.getId().toString()));
            List<BdglAntiepidemicPutin> antiepidemicPutins1 = antiepidemicPutinService.selectGuoQiList(bdglAntiepidemicPutin);
            Integer sum = antiepidemicPutins1.stream().mapToInt(BdglAntiepidemicPutin::getSurplus).sum();
            bdglAntiepidemicArchives.setOverdueQuantity(sum);
            antiepidemicArchivesService.updateBdglAntiepidemicArchives(bdglAntiepidemicArchives);
        }
    }

    @Scheduled(cron = "0 0/2 * * * ?")
  //  @Scheduled(cron = "0 50 23 * * ?")
    public void getAttendanceRecords(){
        bdglAttendanceRecordService.getAttendanceRecordsListByMultipleIp(null,1);
    }


}
