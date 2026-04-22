package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration .domain.BdglEven;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;

/**
 * 连务会记录本Service接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface IBdglEvenService 
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
     * 批量删除连务会记录本
     * 
     * @param ids 需要删除的连务会记录本主键集合
     * @return 结果
     */
    public int deleteBdglEvenByIds(Integer[] ids);

    /**
     * 删除连务会记录本信息
     * 
     * @param id 连务会记录本主键
     * @return 结果
     */
    public int deleteBdglEvenById(Integer id);
    /**
     * 获取连务会记录本列表
     * */
    List<BdglEven> selectBdglEvenLists(BdglEven bdglEven);
    
    List<SysDeptDto> getMeetRecordList(String year);
    
    List<BdglEven> getMeetRecordOnUnitList(Integer unitId,String year);
}
