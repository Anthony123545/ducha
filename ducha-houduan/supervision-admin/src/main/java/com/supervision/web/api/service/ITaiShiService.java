package com.supervision.web.api.service;

import com.supervision.barracksManagement.domain.dto.ApiFlatsDto;
import com.supervision.combatduty.domain.dto.ApiDaduiDutyDto;
import com.supervision.combatduty.domain.dto.ApiStatDutyDto;
import com.supervision.peopleManage.domain.dto.StatOutPeopleDto;
import com.supervision.peopleManage.domain.dto.StatPeopleDto;
import com.supervision.peopleManage.domain.dto.StatPersonAgeDto;
import com.supervision.peopleManage.domain.dto.StatPersonZzmmDto;
import com.supervision.web.api.vo.SituationStatistics;

import java.util.List;
import java.util.Map;

public interface ITaiShiService {
    /**
     * 营房态势信息
     */
    Map<String,List<ApiFlatsDto>> statisticsBarracks();

    /**
     * 军车值班统计
     */
    public List<Map<String, Object>> statisticsOnDuty(SituationStatistics situationStatistics);
    
    Map<String,List<StatPeopleDto>> statReignPerson();
    
    Map<String,List<StatOutPeopleDto>> statOutReignPerson();
    
    Map<String,List<StatPersonAgeDto>> statAgePerson();
    
    Map<String,List<StatPersonZzmmDto>> statZzmmPerson();
    
    Map<String, List<ApiStatDutyDto>> statDuty();
    
    Map<String, List<ApiDaduiDutyDto>> statDaduiDuty();
}
