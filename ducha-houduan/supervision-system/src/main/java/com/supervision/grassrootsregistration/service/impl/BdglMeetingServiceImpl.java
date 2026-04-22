package com.supervision.grassrootsregistration.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.utils.DateUtils;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration .mapper.BdglMeetingMapper;
import com.supervision.grassrootsregistration .domain.BdglMeeting;
import com.supervision.grassrootsregistration .service.IBdglMeetingService;
import org.springframework.util.Assert;

/**
 * 团支部会议登记本Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-26
 */
@Service
public class BdglMeetingServiceImpl implements IBdglMeetingService 
{
    @Autowired
    private BdglMeetingMapper bdglMeetingMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    /**
     * 查询团支部会议登记本
     * 
     * @param id 团支部会议登记本主键
     * @return 团支部会议登记本
     */
    @Override
    public BdglMeeting selectBdglMeetingById(Integer id)
    {
        return bdglMeetingMapper.selectBdglMeetingById(id);
    }

    /**
     * 查询团支部会议登记本列表
     * 
     * @param bdglMeeting 团支部会议登记本
     * @return 团支部会议登记本
     */
    @Override
    public List<BdglMeeting> selectBdglMeetingList(BdglMeeting bdglMeeting)
    {
        return bdglMeetingMapper.selectBdglMeetingList(bdglMeeting);
    }

    /**
     * 新增团支部会议登记本
     * 
     * @param bdglMeeting 团支部会议登记本
     * @return 结果
     */
    @Override
    public int insertBdglMeeting(BdglMeeting bdglMeeting)
    {
        if(bdglMeeting.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglMeeting.getUnitId()));
            bdglMeeting.setDeptName(sysDept.getDeptName());
        }
        bdglMeeting.setCreatetime(DateUtils.getNowDate());
        return bdglMeetingMapper.insertBdglMeeting(bdglMeeting);
    }

    /**
     * 修改团支部会议登记本
     * 
     * @param bdglMeeting 团支部会议登记本
     * @return 结果
     */
    @Override
    public int updateBdglMeeting(BdglMeeting bdglMeeting)
    {
        if(bdglMeeting.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglMeeting.getUnitId()));
            bdglMeeting.setDeptName(sysDept.getDeptName());
        }
        bdglMeeting.setUpdatetime(DateUtils.getNowDate());
        return bdglMeetingMapper.updateBdglMeeting(bdglMeeting);
    }

    /**
     * 批量删除团支部会议登记本
     * 
     * @param ids 需要删除的团支部会议登记本主键
     * @return 结果
     */
    @Override
    public int deleteBdglMeetingByIds(Integer[] ids)
    {
        return bdglMeetingMapper.deleteBdglMeetingByIds(ids);
    }

    /**
     * 删除团支部会议登记本信息
     * 
     * @param id 团支部会议登记本主键
     * @return 结果
     */
    @Override
    public int deleteBdglMeetingById(Integer id)
    {
        return bdglMeetingMapper.deleteBdglMeetingById(id);
    }
    
    @Override
    public List<SysDeptDto> getMeetingList(String year) {
        //所有营级单位
        List<Map<String, SysDept>> mapList = deptMapper.selectAllCamp();
        //根据营级单位ID查询当前单位下是否有数据
        List<SysDeptDto> dtoList = new ArrayList<>();
        mapList.forEach(item -> {
            SysDeptDto deptDto = BeanUtil.mapToBean(item, SysDeptDto.class, false);
            int deptId = deptDto.getDeptId().intValue();
            int countByunitId = bdglMeetingMapper.selectMeetingByunitId(deptId,year);
            if (countByunitId > 0) {
                dtoList.add(deptDto);
            }
        });
        return dtoList;
    }
    
    @Override
    public List<BdglMeeting> getMeetingOnUnitList(Integer unitId,String year) {
        return bdglMeetingMapper.selectMeetingOnUnitList(unitId,year);
    }
}
