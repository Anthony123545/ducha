package com.supervision.grassrootsregistration.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglDailyInspectMapper;
import com.supervision.grassrootsregistration.domain.BdglDailyInspect;
import com.supervision.grassrootsregistration.service.IBdglDailyInspectService;

/**
 * 查铺查哨Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-28
 */
@Service
public class BdglDailyInspectServiceImpl implements IBdglDailyInspectService 
{
    @Autowired
    private BdglDailyInspectMapper bdglDailyInspectMapper;

    /**
     * 查询查铺查哨
     * 
     * @param id 查铺查哨主键
     * @return 查铺查哨
     */
    @Override
    public BdglDailyInspect selectBdglDailyInspectById(Integer id)
    {
        return bdglDailyInspectMapper.selectBdglDailyInspectById(id);
    }

    /**
     * 查询查铺查哨列表
     * 
     * @param bdglDailyInspect 查铺查哨
     * @return 查铺查哨
     */
    @Override
    public List<BdglDailyInspect> selectBdglDailyInspectList(BdglDailyInspect bdglDailyInspect)
    {
        return bdglDailyInspectMapper.selectBdglDailyInspectList(bdglDailyInspect);
    }

    /**
     * 新增查铺查哨
     * 
     * @param bdglDailyInspect 查铺查哨
     * @return 结果
     */
    @Override
    public int insertBdglDailyInspect(BdglDailyInspect bdglDailyInspect)
    {
        return bdglDailyInspectMapper.insertBdglDailyInspect(bdglDailyInspect);
    }

    /**
     * 修改查铺查哨
     * 
     * @param bdglDailyInspect 查铺查哨
     * @return 结果
     */
    @Override
    public int updateBdglDailyInspect(BdglDailyInspect bdglDailyInspect)
    {
        return bdglDailyInspectMapper.updateBdglDailyInspect(bdglDailyInspect);
    }

    /**
     * 批量删除查铺查哨
     * 
     * @param ids 需要删除的查铺查哨主键
     * @return 结果
     */
    @Override
    public int deleteBdglDailyInspectByIds(Integer[] ids)
    {
        return bdglDailyInspectMapper.deleteBdglDailyInspectByIds(ids);
    }

    /**
     * 删除查铺查哨信息
     * 
     * @param id 查铺查哨主键
     * @return 结果
     */
    @Override
    public int deleteBdglDailyInspectById(Integer id)
    {
        return bdglDailyInspectMapper.deleteBdglDailyInspectById(id);
    }

    /**
     * 查询当前时间、当前部门下是否有数据
     * @param starttime
     * @param endtime
     * @param unitid
     * @return
     */
    @Override
    public List<BdglDailyInspect> selectBdglDailyInspectListByTime(String starttime, String endtime, Long unitid) {
        return bdglDailyInspectMapper.selectBdglDailyInspectListByTime(starttime, endtime ,unitid);
    }

    @Override
    public List<BdglDailyInspect> selectBdglDailyInspects(Long deptId, String timeParameter) {
        return bdglDailyInspectMapper.selectBdglDailyInspects(deptId,timeParameter);
    }
    
}
