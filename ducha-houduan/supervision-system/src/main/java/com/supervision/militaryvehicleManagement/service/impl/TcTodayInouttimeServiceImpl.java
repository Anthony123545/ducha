package com.supervision.militaryvehicleManagement.service.impl;

import java.util.List;

import com.supervision.common.annotation.DataSource;
import com.supervision.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.militaryvehicleManagement.mapper.TcTodayInouttimeMapper;
import com.supervision.militaryvehicleManagement.domain.TcTodayInouttime;
import com.supervision.militaryvehicleManagement.service.ITcTodayInouttimeService;

/**
 * 车辆外接数据Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-05-22
 */

@DataSource(value = DataSourceType.SLAVE)
@Service
public class TcTodayInouttimeServiceImpl implements ITcTodayInouttimeService 
{
    @Autowired
    private TcTodayInouttimeMapper tcTodayInouttimeMapper;




    /**
     * 查询车辆外接数据列表
     * 
     * @param tcTodayInouttime 车辆外接数据
     * @return 车辆外接数据
     */
    @Override
    public List<TcTodayInouttime> selectTcTodayInouttimeList(TcTodayInouttime tcTodayInouttime)
    {
        return tcTodayInouttimeMapper.selectTcTodayInouttimeList(tcTodayInouttime);
    }


}