package com.supervision.web.api.controller;

import com.supervision.barracksManagement.domain.dto.ApiFlatsDto;
import com.supervision.combatduty.domain.dto.ApiDaduiDutyDto;
import com.supervision.combatduty.domain.dto.ApiStatDutyDto;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.exception.ServiceException;
import com.supervision.peopleManage.domain.dto.StatOutPeopleDto;
import com.supervision.peopleManage.domain.dto.StatPeopleDto;
import com.supervision.peopleManage.domain.dto.StatPersonAgeDto;
import com.supervision.peopleManage.domain.dto.StatPersonZzmmDto;
import com.supervision.web.api.vo.ErrorCode;
import com.supervision.web.api.vo.SituationStatistics;
import com.supervision.web.api.service.ITaiShiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/situation/")
public class TaiShiController {

    @Value("${taishi.apiToken}")
    private String apiToken;

    @Resource
    private ITaiShiService taiShiService;

    /**
     * 人员在位统计
     * @param token 校验参数
     */
    @PostMapping("/statPersonReign")
    public AjaxResult statReignPerson(@RequestParam("token")String token){
        if (!apiToken.equals(token)){
            return AjaxResult.error(ErrorCode.TOKEN_VERIFICATION_FAILED,"token校验失败");
        }
        Map<String,List<StatPeopleDto>> dtoMap = taiShiService.statReignPerson();
        return AjaxResult.success(dtoMap);
    }
    
    /**
     * 离位人员信息统计
     */
    @PostMapping("/statPersonOutReign")
    public AjaxResult statOutReignPerson(@RequestParam("token")String token){
        if (!apiToken.equals(token)){
            return AjaxResult.error(ErrorCode.TOKEN_VERIFICATION_FAILED,"token校验失败");
        }
        Map<String,List<StatOutPeopleDto>> dtoMap = taiShiService.statOutReignPerson();
        return AjaxResult.success(dtoMap);
    }
    
    /**
     * 年龄分布统计
     */
    @PostMapping("/statPersonAge")
    public AjaxResult statAgePerson(@RequestParam("token")String token){
        if (!apiToken.equals(token)){
            return AjaxResult.error(ErrorCode.TOKEN_VERIFICATION_FAILED,"token校验失败");
        }
        Map<String,List<StatPersonAgeDto>> dtoMap = taiShiService.statAgePerson();
        return AjaxResult.success(dtoMap);
    }
    
    /**
     * 政治面貌统计
     */
    @PostMapping("/statZzmmPerson")
    public AjaxResult statZzmmPerson(@RequestParam("token")String token){
        if (!apiToken.equals(token)){
            return AjaxResult.error(ErrorCode.TOKEN_VERIFICATION_FAILED,"token校验失败");
        }
        Map<String,List<StatPersonZzmmDto>> dtoList = taiShiService.statZzmmPerson();
        return AjaxResult.success(dtoList);
    }
    
    /**
     * 营房态势统计
     * @param token token
     */
    @PostMapping("/barracks")
    public AjaxResult statisticsBarracks(@RequestParam("token")String token){
        if (!apiToken.equals(token)){
            return AjaxResult.error(ErrorCode.TOKEN_VERIFICATION_FAILED,"token校验失败");
        }
        Map<String,List<ApiFlatsDto>> dtoList = taiShiService.statisticsBarracks();
        return AjaxResult.success(dtoList);
    }
    
    /**
     * 大队值班统计
     * @param token token
     */
    @PostMapping("/statDaduiDuty")
    public AjaxResult statDaduiDuty(@RequestParam("token")String token){
        if (!apiToken.equals(token)){
            return AjaxResult.error(ErrorCode.TOKEN_VERIFICATION_FAILED,"token校验失败");
        }
        Map<String,List<ApiDaduiDutyDto>> dtoList = taiShiService.statDaduiDuty();
        return AjaxResult.success(dtoList);
    }
    
    /**
     * 基层值班统计
     * @param token token
     */
    @PostMapping("/statDuty")
    public AjaxResult statDuty(@RequestParam("token")String token){
        if (!apiToken.equals(token)){
            return AjaxResult.error(ErrorCode.TOKEN_VERIFICATION_FAILED,"token校验失败");
        }
        Map<String,List<ApiStatDutyDto>> dtoList = taiShiService.statDuty();
        return AjaxResult.success(dtoList);
    }

    @PostMapping("/duty")
    public AjaxResult statisticsBarracks1(@RequestParam("token")String token,@RequestBody SituationStatistics situationStatistics){
        if (!apiToken.equals(token)){
            return AjaxResult.error(ErrorCode.TOKEN_VERIFICATION_FAILED,"token校验失败");
        }
        return AjaxResult.success(taiShiService.statisticsOnDuty(situationStatistics));
    }


}
