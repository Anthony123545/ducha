package com.supervision.grassrootsregistration.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.supervision.grassrootsregistration .domain.BdglDiary;
import com.supervision.grassrootsregistration.domain.BdglWeekwork;
import com.supervision.grassrootsregistration.domain.BdglWeekworkRegister;
import com.supervision.grassrootsregistration.domain.vo.BdglDiaryVo;
import org.apache.ibatis.annotations.Param;

/**
 * 要事日记Service接口
 *
 * @author supervision
 * @date 2022-02-26
 */
public interface IBdglDiaryService
{
    /**
     * 查询要事日记
     *
     * @param id 要事日记主键
     * @return 要事日记
     */
    public BdglDiary selectBdglDiaryById(Integer id);

    /**
     * 查询要事日记列表
     *
     * @param bdglDiary 要事日记
     * @return 要事日记集合
     */
    public List<BdglDiary> selectBdglDiaryList(BdglDiary bdglDiary);

    /**
     * 新增要事日记
     *
     * @param bdglDiary 要事日记
     * @return 结果
     */
    public int insertBdglDiary(BdglDiary bdglDiary);

    /**
     * 修改要事日记
     *
     * @param bdglDiary 要事日记
     * @return 结果
     */
    public int updateBdglDiary(BdglDiary bdglDiary);

    /**
     * 批量删除要事日记
     *
     * @param ids 需要删除的要事日记主键集合
     * @return 结果
     */
    public int deleteBdglDiaryByIds(Integer[] ids);

    /**
     * 删除要事日记信息
     *
     * @param id 要事日记主键
     * @return 结果
     */
    public int deleteBdglDiaryById(Integer id);
    /**获取要事日记详细信息*/
    BdglDiary selectBdglDiaryxiangqing(Integer id);
    /**获取当前部门当日工作安排*/
    List<BdglWeekworkRegister> getWeekWork(BdglDiary bdglDiary);
    /**获取当前部门  干部  战士  文职人数*/
    Map<String, Object> getRenShu(Long deptId);
    //查询以审批部门
    BdglDiary selectBdglDiaryLists(BdglDiary bdglDiary);
    /**获取当日已审批*/
    List<BdglDiary> selectBdglDiarys(BdglDiary bdglDiary);

    /**
     * 查询当前时间、当前部门下是否有数据
     * @param starttime
     * @param endtime
     * @param unitid
     * @return
     */
    public List<BdglDiary> selectBdglDiaryListByTime(String starttime, String endtime, Long unitid);

    int updateBdglDiarya(BdglDiary bdglDiary);

    List<BdglDiary> selectBdglDiaryListss(BdglDiary bdglDiary);

    public int selectBdglDailyInspectCountByTime(Date starttime, Date endtime, Long unitid);
    
    List<BdglDiaryVo> selectDirayStatList(BdglDiaryVo diaryVo);
}
