package com.supervision.regulations.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.supervision.regulations.domain.BdglFileInfo;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 规章制度文件记录Mapper接口
 * 
 * @author supervision
 * @date 2022-03-15
 */
public interface BdglFileInfoMapper 
{
    /**
     * 查询规章制度文件记录
     * 
     * @param id 规章制度文件记录主键
     * @return 规章制度文件记录
     */
    public BdglFileInfo selectBdglFileInfoById(Long id);

    /**
     * 查询规章制度文件记录列表
     * 
     * @param bdglFileInfo 规章制度文件记录
     * @return 规章制度文件记录集合
     */
    public List<BdglFileInfo> selectBdglFileInfoList(BdglFileInfo bdglFileInfo);

    public List<BdglFileInfo> selectParentIdfile(BdglFileInfo bdglFileInfo);
    /**
     * 新增规章制度文件记录
     * 
     * @param bdglFileInfo 规章制度文件记录
     * @return 结果
     */
    public int insertBdglFileInfo(BdglFileInfo bdglFileInfo);

    /**
     * 修改规章制度文件记录
     * 
     * @param bdglFileInfo 规章制度文件记录
     * @return 结果
     */
    public int updateBdglFileInfo(BdglFileInfo bdglFileInfo);

    /**
     * 删除规章制度文件记录
     * 
     * @param id 规章制度文件记录主键
     * @return 结果
     */
    public int deleteBdglFileInfoById(Long id);

    /**
     * 批量删除规章制度文件记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglFileInfoByIds(Long[] ids);

    List<Map<String, Object>> selectResdNumber(@Param("s") String s);
}
