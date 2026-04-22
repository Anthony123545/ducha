package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration .domain.BdglEven;
import org.apache.ibatis.annotations.Param;

/**
 * 连务会记录本Mapper接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface BdglEvenMapper 
{
    /**
     * 查询连务会记录本
     * 
     * @param id 连务会记录本主键
     * @return 连务会记录本
     */
    public BdglEven selectBdglEvenById(Integer id);

    /**
     * 查询连务会记录本列表
     * 
     * @param bdglEven 连务会记录本
     * @return 连务会记录本集合
     */
    public List<BdglEven> selectBdglEvenList(BdglEven bdglEven);

    /**
     * 新增连务会记录本
     * 
     * @param bdglEven 连务会记录本
     * @return 结果
     */
    public int insertBdglEven(BdglEven bdglEven);

    /**
     * 修改连务会记录本
     * 
     * @param bdglEven 连务会记录本
     * @return 结果
     */
    public int updateBdglEven(BdglEven bdglEven);

    /**
     * 删除连务会记录本
     * 
     * @param id 连务会记录本主键
     * @return 结果
     */
    public int deleteBdglEvenById(Integer id);

    /**
     * 批量删除连务会记录本
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglEvenByIds(Integer[] ids);
    /**
     * 获取连务会记录本列表
     * */
    List<BdglEven> selectBdglEvenLists(BdglEven bdglEven);
    
    int selectlMeetRecordByunitId(@Param("unitId") Integer unitId, @Param("year") String year);
    
    List<BdglEven> selectMeetRecordOnUnitList(@Param("unitId") Integer unitId, @Param("year") String year);
}
