package com.supervision.combatduty.service;

import java.util.List;
import com.supervision.combatduty.domain.BdglTeamduty;
import org.apache.ibatis.annotations.Param;

/**
 * 大队值班Service接口
 * 
 * @author supervision
 * @date 2022-03-10
 */
public interface IBdglTeamdutyService 
{
    /**
     * 查询大队值班
     * 
     * @param id 大队值班主键
     * @return 大队值班
     */
    public BdglTeamduty selectBdglTeamdutyById(Long id);

    /**
     * 查询大队值班列表
     * 
     * @param bdglTeamduty 大队值班
     * @return 大队值班集合
     */
    public List<BdglTeamduty> selectBdglTeamdutyList(BdglTeamduty bdglTeamduty);

    /**
     * 新增大队值班
     * 
     * @param bdglTeamduty 大队值班
     * @return 结果
     */
    public int insertBdglTeamduty(BdglTeamduty bdglTeamduty);

    /**
     * 修改大队值班
     * 
     * @param bdglTeamduty 大队值班
     * @return 结果
     */
    public int updateBdglTeamduty(BdglTeamduty bdglTeamduty);

    /**
     * 批量删除大队值班
     * 
     * @param ids 需要删除的大队值班主键集合
     * @return 结果
     */
    public int deleteBdglTeamdutyByIds(Long[] ids);

    /**
     * 删除大队值班信息
     * 
     * @param id 大队值班主键
     * @return 结果
     */
    public int deleteBdglTeamdutyById(Long id);

    /**
     * 查询当前时间、当前部门下是否有数据
     * @param starttime
     * @param endtime
     * @param unitid
     * @return
     */
    public List<BdglTeamduty> selectBdglTeamdutyListByTime(String starttime, String endtime, Long unitid);
}
