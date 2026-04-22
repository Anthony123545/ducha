package com.supervision.militaryvehicleManagement.mapper;

import java.util.List;
import com.supervision.militaryvehicleManagement.domain.TcTodayInouttime;

/**
 * 车辆外接数据Mapper接口
 * 
 * @author ruoyi
 * @date 2022-05-22
 */
public interface TcTodayInouttimeMapper 
{


    /**
     * 查询车辆外接数据列表
     * 
     * @param tcTodayInouttime 车辆外接数据
     * @return 车辆外接数据集合
     */
    public List<TcTodayInouttime> selectTcTodayInouttimeList(TcTodayInouttime tcTodayInouttime);


}