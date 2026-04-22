package com.supervision.grassrootsregistration.mapper;

import java.util.Date;
import java.util.List;
import com.supervision.grassrootsregistration .domain.BdglDiary;
import com.supervision.grassrootsregistration.domain.vo.BdglDiaryVo;
import org.apache.ibatis.annotations.Param;

/**
 * 要事日记Mapper接口
 *
 * @author supervision
 * @date 2022-02-26
 */
public interface BdglDiaryMapper
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
     * 删除要事日记
     *
     * @param id 要事日记主键
     * @return 结果
     */
    public int deleteBdglDiaryById(Integer id);

    /**
     * 批量删除要事日记
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglDiaryByIds(Integer[] ids);
    /**查询已审批部门*/
    BdglDiary selectBdglDiaryLists(BdglDiary bdglDiary);
    /**获取当日已审批部门数量*/
    List<BdglDiary> selectBdglDiarys(BdglDiary bdglDiary);

    /**
     * 查询当前时间、当前部门下是否有数据
     * @param starttime
     * @param endtime
     * @param unitid
     * @return
     */
    public List<BdglDiary> selectBdglDiaryListByTime(@Param("starttime") String starttime, @Param("endtime") String endtime, @Param("unitid") Long unitid);

    List<BdglDiary> selectBdglDiaryListss(BdglDiary bdglDiary);

    public int selectBdglDailyInspectCountByTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("unitId") Long unitId);
    
    List<BdglDiaryVo> selectDirayStatList(BdglDiaryVo diaryVo);
}
