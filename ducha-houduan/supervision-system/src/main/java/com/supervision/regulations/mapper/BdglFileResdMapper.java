package com.supervision.regulations.mapper;

import java.util.List;
import com.supervision.regulations.domain.BdglFileResd;

/**
 * 规章制度阅读量统计Mapper接口
 * 
 * @author supervision
 * @date 2022-03-18
 */
public interface BdglFileResdMapper 
{
    /**
     * 查询规章制度阅读量统计
     * 
     * @param id 规章制度阅读量统计主键
     * @return 规章制度阅读量统计
     */
    public BdglFileResd selectBdglFileResdById(Long id);

    /**
     * 查询规章制度阅读量统计列表
     * 
     * @param bdglFileResd 规章制度阅读量统计
     * @return 规章制度阅读量统计集合
     */
    public List<BdglFileResd> selectBdglFileResdList(BdglFileResd bdglFileResd);

    /**
     * 新增规章制度阅读量统计
     * 
     * @param bdglFileResd 规章制度阅读量统计
     * @return 结果
     */
    public int insertBdglFileResd(BdglFileResd bdglFileResd);

    /**
     * 修改规章制度阅读量统计
     * 
     * @param bdglFileResd 规章制度阅读量统计
     * @return 结果
     */
    public int updateBdglFileResd(BdglFileResd bdglFileResd);

    /**
     * 删除规章制度阅读量统计
     * 
     * @param id 规章制度阅读量统计主键
     * @return 结果
     */
    public int deleteBdglFileResdById(Long id);

    /**
     * 批量删除规章制度阅读量统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglFileResdByIds(Long[] ids);

    BdglFileResd selectResdNumber(BdglFileResd fileResd);
}
