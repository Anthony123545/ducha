package com.supervision.combatduty.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.combatduty.mapper.BdglTeamdutyMapper;
import com.supervision.combatduty.domain.BdglTeamduty;
import com.supervision.combatduty.service.IBdglTeamdutyService;

/**
 * 大队值班Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-10
 */
@Service
public class BdglTeamdutyServiceImpl implements IBdglTeamdutyService 
{
    @Autowired
    private BdglTeamdutyMapper bdglTeamdutyMapper;

    /**
     * 查询大队值班
     * 
     * @param id 大队值班主键
     * @return 大队值班
     */
    @Override
    public BdglTeamduty selectBdglTeamdutyById(Long id)
    {
        return bdglTeamdutyMapper.selectBdglTeamdutyById(id);
    }

    /**
     * 查询大队值班列表
     * 
     * @param bdglTeamduty 大队值班
     * @return 大队值班
     */
    @Override
    public List<BdglTeamduty> selectBdglTeamdutyList(BdglTeamduty bdglTeamduty)
    {
        return bdglTeamdutyMapper.selectBdglTeamdutyList(bdglTeamduty);
    }

    /**
     * 新增大队值班
     * 
     * @param bdglTeamduty 大队值班
     * @return 结果
     */
    @Override
    public int insertBdglTeamduty(BdglTeamduty bdglTeamduty)
    {
        return bdglTeamdutyMapper.insertBdglTeamduty(bdglTeamduty);
    }

    /**
     * 修改大队值班
     * 
     * @param bdglTeamduty 大队值班
     * @return 结果
     */
    @Override
    public int updateBdglTeamduty(BdglTeamduty bdglTeamduty)
    {
        return bdglTeamdutyMapper.updateBdglTeamduty(bdglTeamduty);
    }

    /**
     * 批量删除大队值班
     * 
     * @param ids 需要删除的大队值班主键
     * @return 结果
     */
    @Override
    public int deleteBdglTeamdutyByIds(Long[] ids)
    {
        return bdglTeamdutyMapper.deleteBdglTeamdutyByIds(ids);
    }

    /**
     * 删除大队值班信息
     * 
     * @param id 大队值班主键
     * @return 结果
     */
    @Override
    public int deleteBdglTeamdutyById(Long id)
    {
        return bdglTeamdutyMapper.deleteBdglTeamdutyById(id);
    }

    /**
     * 查询当前时间、当前部门下是否有数据
     * @param starttime
     * @param endtime
     * @param unitid
     * @return
     */
    @Override
    public List<BdglTeamduty> selectBdglTeamdutyListByTime(String starttime, String endtime, Long unitid) {
        return bdglTeamdutyMapper.selectBdglTeamdutyListByTime(starttime,endtime,unitid);
    }

    public List<BdglTeamduty> selectBdglTeamdutyLists(BdglTeamduty bdglTeamduty) {

        return bdglTeamdutyMapper.selectBdglTeamdutyLists(bdglTeamduty);
    }
}
