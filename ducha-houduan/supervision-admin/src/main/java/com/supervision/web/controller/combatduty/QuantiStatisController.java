package com.supervision.web.controller.combatduty;

import com.supervision.combatduty.domain.*;
import com.supervision.combatduty.service.*;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * 量化统计接口
 *
 * @author supervision
 * @date 2022-03-10
 */
@RestController
@RequestMapping("/combatduty/quantistatis")
public class QuantiStatisController extends BaseController {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private IBdglPeopleService bdglPeopleService;

    @Autowired
    private IBdglBasedutyService bdglBasedutyService;

    @Autowired
    private IBdglTeamdutyService bdglTeamdutyService;

    @Autowired
    private IBdglMilitarydutyService bdglMilitarydutyService;

    @Autowired
    private IBdglRegisDutyService bdglRegisDutyService;

    @Autowired
    private IBdglTeamdutyFuService bdglTeamdutyFuService;

    /**
     * 查询人员情况信息
     */
    @GetMapping("/listshow")
    public AjaxResult listShow()
    {
        Map<String ,Integer> map = new HashMap<>();

        /**
         * 在编总人数
         */
        BdglPeople bp1 = new BdglPeople();
        bp1.setAuthorizedStrength("Y");
        List<BdglPeople> zaibianzongrenshu = bdglPeopleService.selectBdglPeopleList(bp1);
        if(zaibianzongrenshu.size() > 0){
            map.put("zaibianzongshu", zaibianzongrenshu.size());
        }else {
            map.put("zaibianzongshu", 0);
        }
        /**
         * 在编在位
         */
        bp1.setReign(0);
        List<BdglPeople> zaibianzaiwei = bdglPeopleService.selectBdglPeopleList(bp1);
        if(zaibianzaiwei.size() > 0){
            map.put("zaibianzaiwei", zaibianzaiwei.size());
        }else {
            map.put("zaibianzaiwei", 0);
        }
        /**
         * 在编休假
         */
        bp1.setReign(1);
        List<BdglPeople> zaibianxiujia = bdglPeopleService.selectBdglPeopleList(bp1);
        if(zaibianxiujia.size() > 0){
            map.put("zaibianxiujia", zaibianxiujia.size());
        }else {
            map.put("zaibianxiujia", 0);
        }
        /**
         * 在编出差
         */
        bp1.setReign(2);
        List<BdglPeople> zaibianchuchai = bdglPeopleService.selectBdglPeopleList(bp1);
        if(zaibianchuchai.size() > 0){
            map.put("zaibianchuchai", zaibianchuchai.size());
        }else {
            map.put("zaibianchuchai", 0);
        }
        /**
         * 在编学习
         */
        bp1.setReign(4);
        List<BdglPeople> zaibianxuexi = bdglPeopleService.selectBdglPeopleList(bp1);
        if(zaibianxuexi.size() > 0){
            map.put("zaibianxuexi", zaibianxuexi.size());
        }else {
            map.put("zaibianxuexi", 0);
        }
        /**
         * 在编外诊
         */
        bp1.setReign(6);
        List<BdglPeople> zaibianwaizhen = bdglPeopleService.selectBdglPeopleList(bp1);
        if(zaibianwaizhen.size() > 0){
            map.put("zaibianwaizhen", zaibianxuexi.size());
        }else {
            map.put("zaibianwaizhen", 0);
        }


        /**
         * 未在编总人数
         */
        BdglPeople bp2 = new BdglPeople();
        bp2.setAuthorizedStrength("N");
        List<BdglPeople> weizaibianzongrenshu = bdglPeopleService.selectBdglPeopleList(bp2);
        if(weizaibianzongrenshu.size() > 0){
            map.put("weizaibianzongrenshu", weizaibianzongrenshu.size());
        }else {
            map.put("weizaibianzongrenshu", 0);
        }
        /**
         * 未在编在位
         */
        bp2.setReign(0);
        List<BdglPeople> weizaibianzaiwei = bdglPeopleService.selectBdglPeopleList(bp2);
        if(weizaibianzaiwei.size() > 0){
            map.put("weizaibianzaiwei", weizaibianzaiwei.size());
        }else {
            map.put("weizaibianzaiwei", 0);
        }
        /**
         * 在编休假
         */
        bp2.setReign(1);
        List<BdglPeople> weizaibianxiujia = bdglPeopleService.selectBdglPeopleList(bp2);
        if(weizaibianxiujia.size() > 0){
            map.put("weizaibianxiujia", weizaibianxiujia.size());
        }else {
            map.put("weizaibianxiujia", 0);
        }
        /**
         * 未在编出差
         */
        bp2.setReign(2);
        List<BdglPeople> weizaibianchuchai = bdglPeopleService.selectBdglPeopleList(bp2);
        if(weizaibianchuchai.size() > 0){
            map.put("weizaibianchuchai", weizaibianchuchai.size());
        }else {
            map.put("weizaibianchuchai", 0);
        }
        /**
         * 未在编学习
         */
        bp2.setReign(4);
        List<BdglPeople> weizaibianxuexi = bdglPeopleService.selectBdglPeopleList(bp2);
        if(weizaibianxuexi.size() > 0) {
            map.put("weizaibianxuexi", weizaibianxuexi.size());
        }else {
            map.put("weizaibianxuexi", 0);
        }
        /**
         * 未在编外诊
         */
        bp2.setReign(6);
        List<BdglPeople> weizaibianwaizhen = bdglPeopleService.selectBdglPeopleList(bp1);
        if(weizaibianwaizhen.size() > 0){
            map.put("weizaibianwaizhen", weizaibianwaizhen.size());
        }else {
            map.put("weizaibianwaizhen", 0);
        }

        return AjaxResult.success(map);

    }

    /**
     * 获取人员五率数据
     * @return
     */
    @GetMapping("/listwulvinfo")
    public AjaxResult listWuLvInfo() {

        Map<String, Integer> map = new HashMap<>();

        // 获取实际人数
        BdglPeople bp1 = new BdglPeople();
        List<BdglPeople> bdglPeople1 = bdglPeopleService.selectBdglPeopleList(bp1);
        map.put("sjrs",bdglPeople1.size());

        //获取在位人数
        BdglPeople bp2 = new BdglPeople();
        bp2.setReign(0);
        List<BdglPeople> bdglPeople2 = bdglPeopleService.selectBdglPeopleList(bp2);
        map.put("zwrs",bdglPeople2.size());

        //获取对口人数
        BdglPeople bp3 = new BdglPeople();
        bp3.setIsBianzhi("Y");
        List<BdglPeople> bdglPeople3 = bdglPeopleService.selectBdglPeopleList(bp3);
        map.put("dkrs",bdglPeople3.size());

        //出动人数
        BdglPeople bp4 = new BdglPeople();
        bp4.setReign(5);
        List<BdglPeople> bdglPeople4 = bdglPeopleService.selectBdglPeopleList(bp4);
        map.put("cdrs", bdglPeople4.size());

        return AjaxResult.success(map);
    }

    /**
     * 基地今日值班
     * @param bdglBaseduty
     * @return
     */
    @GetMapping("/todayzhiban")
    public TableDataInfo list(BdglBaseduty bdglBaseduty)
    {
        List<BdglBaseduty> lists = new ArrayList<>();
        List<BdglBaseduty> list = bdglBasedutyService.selectBdglBasedutyList(bdglBaseduty);
        for (BdglBaseduty baseduty : list) {
            String today = sdf.format(new Date());
            if(today.equals(baseduty.getAttendedTime())) {
                lists.add(baseduty);
                break;
            }
        }
        return getDataTable(lists);
    }

    /**
     * 大队今日值班
     * @param bdglTeamduty
     * @return
     */
    @GetMapping("/daduizhiban")
    public TableDataInfo listdadui(BdglTeamduty bdglTeamduty) throws ParseException {
        List<BdglTeamduty> lists = new ArrayList<>();
        List<BdglTeamduty >  bdglTeamdutys =  bdglTeamdutyService.selectBdglTeamdutyList( bdglTeamduty);
        for (BdglTeamduty teamduty : bdglTeamdutys) {
            String today = sdf.format(new Date());
            if(sdf.parse(today).equals(sdf.parse(teamduty.getAttendedTime()))) {
                lists.add(teamduty);
                break;
            }
        }
        return getDataTable(lists);
    }

    /**
     * 大队值班分队数量分队总人数
     * */
    @GetMapping("/getdaDuiZhiBanNumber")
    public AjaxResult getdaDuiZhiBanNumber(BdglTeamduty bdglTeamduty){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        bdglTeamduty.setAttendedTime(format);
        List<BdglTeamduty >  bdglTeamdutys =  bdglTeamdutyService.selectBdglTeamdutyList( bdglTeamduty);
        int fenDuiNumber=0;
        int fenDuiRenShu=0;
        if(bdglTeamdutys.size()>0){
            for (BdglTeamduty teamduty : bdglTeamdutys) {
                BdglTeamdutyFu teamdutyfu = new BdglTeamdutyFu();
                teamdutyfu.setBdglTeamdutyId(teamduty.getId());
                List<BdglTeamdutyFu> bdglTeamdutyFus = bdglTeamdutyFuService.selectBdglTeamdutyFuList(teamdutyfu);
                if(bdglTeamdutyFus.size()>0){

                    for (BdglTeamdutyFu teamdutyFus : bdglTeamdutyFus) {
                        fenDuiNumber++;
                        if( teamdutyFus.getDetachmentNumber()!=null){
                            fenDuiRenShu += teamdutyFus.getDetachmentNumber();
                        }
                    }
                }
            }
        }
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(fenDuiNumber);
        integers.add(fenDuiRenShu);
        return AjaxResult.success(integers);
    }

    /**
     * 军车今日值班
     * @param bdglMilitaryduty
     * @return
     */
    @GetMapping("/junchezhiban")
    public TableDataInfo listjunche(BdglMilitaryduty bdglMilitaryduty) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        bdglMilitaryduty.setAttendedTime(format);
        List<BdglMilitaryduty> bdglMilitaryduties = bdglMilitarydutyService.selectBdglMilitarydutyList(bdglMilitaryduty);
        return getDataTable(bdglMilitaryduties);
    }

    /**
     * 装备今日值班
     * @param bdglRegisDuty
     * @return
     */
    @GetMapping("/zhuangbeizhiban")
    public TableDataInfo listzhaungbei(BdglRegisDuty bdglRegisDuty) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        bdglRegisDuty.setAttendedTime(format);
        List<BdglRegisDuty> bdglRegisDuties = bdglRegisDutyService.selectBdglRegisDutyList(bdglRegisDuty);
        return getDataTable(bdglRegisDuties);
    }

    /**
     * 装备可用数
     * @param bdglRegisDuty
     * @return
     */
    @GetMapping("/zhuanbeikeyongshu")
    public AjaxResult zhuanbeikeyongshu(BdglRegisDuty bdglRegisDuty) {
        bdglRegisDuty.setEquipmentStates("0");
        List<BdglRegisDuty> bdglRegisDuties = bdglRegisDutyService.selectBdglRegisDutyList(bdglRegisDuty);
        if(bdglRegisDuties.size() > 0){
            return AjaxResult.success(bdglRegisDuties.size());
        }else {
            return AjaxResult.success("0");
        }
    }
}
