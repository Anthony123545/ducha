package com.supervision.militaryvehicleManagement.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.supervision.combatduty.domain.BdglMilitaryduty;
import com.supervision.combatduty.mapper.BdglMilitarydutyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.militaryvehicleManagement.mapper.BdglThebusMapper;
import com.supervision.militaryvehicleManagement.domain.BdglThebus;
import com.supervision.militaryvehicleManagement.service.IBdglThebusService;

/**
 * 军车管理Service业务层处理
 *
 * @author supervision
 * @date 2022-03-09
 */
@Service
public class BdglThebusServiceImpl implements IBdglThebusService
{
    @Autowired
    private BdglThebusMapper bdglThebusMapper;

    @Autowired
    private BdglMilitarydutyMapper bdglMilitarydutyMapper;

    /**
     * 查询军车管理
     *
     * @param id 军车管理主键
     * @return 军车管理
     */
    @Override
    public BdglThebus selectBdglThebusById(Long id)
    {
        return bdglThebusMapper.selectBdglThebusById(id);
    }

    /**
     * 查询军车管理列表
     *
     * @param bdglThebus 军车管理
     * @return 军车管理
     */
    @Override
    public List<BdglThebus> selectBdglThebusList(BdglThebus bdglThebus)
    {
        return bdglThebusMapper.selectBdglThebusList(bdglThebus);
    }

    /**
     * 新增军车管理
     *
     * @param bdglThebus 军车管理
     * @return 结果
     */
    @Override
    public int insertBdglThebus(BdglThebus bdglThebus)
    {
        return bdglThebusMapper.insertBdglThebus(bdglThebus);
    }

    /**
     * 修改军车管理
     *
     * @param bdglThebus 军车管理
     * @return 结果
     */
    @Override
    public int updateBdglThebus(BdglThebus bdglThebus)
    {
        return bdglThebusMapper.updateBdglThebus(bdglThebus);
    }

    /**
     * 批量删除军车管理
     *
     * @param ids 需要删除的军车管理主键
     * @return 结果
     */
    @Override
    public int deleteBdglThebusByIds(Long[] ids)
    {
        return bdglThebusMapper.deleteBdglThebusByIds(ids);
    }

    /**
     * 删除军车管理信息
     *
     * @param id 军车管理主键
     * @return 结果
     */
    @Override
    public int deleteBdglThebusById(Long id)
    {
        return bdglThebusMapper.deleteBdglThebusById(id);
    }

    /**
     * 值班时间小于当前日期的军车，并且以后没有军车值班的，修改值班状态为在位
     * @return
     */
    @Override
    public int updateBdglThebusStatus() {
        //先查大于等于当前时间的值班数据a1，然后查值班状态的车辆数据a2，a2-a1
        List<BdglMilitaryduty> bdglMilitarydutyList = bdglMilitarydutyMapper.selectDutyListByAttendedTime();

        Set<Long> existsDutyTheBusIdList = new HashSet<>();
        for (BdglMilitaryduty bdglMilitaryduty : bdglMilitarydutyList) {
            String numberPlateId = bdglMilitaryduty.getNumberPlateId();
            for (String s : numberPlateId.split(",")) {
                existsDutyTheBusIdList.add(Long.parseLong(s));
            }
        }
        //查车辆表是值班状态的车辆
        BdglThebus bdglThebus = new BdglThebus();
        bdglThebus.setStatus("2");
        List<BdglThebus> bdglThebusList = bdglThebusMapper.selectBdglThebusList(bdglThebus);
        if (bdglThebusList.size()==0){
            return 1;
        }
        List<Long> onDutyIdList = bdglThebusList.stream().map(BdglThebus::getId).collect(Collectors.toList());
        Iterator<Long> iterator = onDutyIdList.iterator();
        while (iterator.hasNext()){
            Long next = iterator.next();
            if (existsDutyTheBusIdList.contains(next)){
                iterator.remove();
            }
        }

        if (onDutyIdList.size()>0){
            bdglThebusMapper.updateBatchForStatusByIdList(onDutyIdList);
        }

        return 1;
    }

    @Override
    public List<Map<String, Object>> selectVehicleModelList() {
        return bdglThebusMapper.selectVehicleModelList();
    }

    @Override
    public BdglThebus selectBdglThebusByVehicleNumber(String license) {
        return bdglThebusMapper.selectBdglThebusByVehicleNumber(license);
    }
}
