
package com.supervision.zbqk.mapper;
import com.supervision.zbqk.domain.BdglZbqkFileInfo;

import java.util.List;

/**
 * 战备情况Mapper接口
 * 
 * @author supervision
 * @date 2022-11-16
 */
public interface BdglZbqkFileInfoMapper
{
    /**
     * 查询战备情况
     * 
     * @param id 战备情况主键
     * @return 战备情况
     */
    public BdglZbqkFileInfo selectBdglZbqkFileInfoById(Long id);

    /**
     * 查询战备情况列表
     * 
     * @param bdglZbqkFileInfo 战备情况
     * @return 战备情况集合
     */
    public List<BdglZbqkFileInfo> selectBdglZbqkFileInfoList(BdglZbqkFileInfo bdglZbqkFileInfo);

    /**
     * 新增战备情况
     * 
     * @param bdglZbqkFileInfo 战备情况
     * @return 结果
     */
    public int insertBdglZbqkFileInfo(BdglZbqkFileInfo bdglZbqkFileInfo);

    /**
     * 修改战备情况
     * 
     * @param bdglZbqkFileInfo 战备情况
     * @return 结果
     */
    public int updateBdglZbqkFileInfo(BdglZbqkFileInfo bdglZbqkFileInfo);

    /**
     * 删除战备情况
     * 
     * @param id 战备情况主键
     * @return 结果
     */
    public int deleteBdglZbqkFileInfoById(Long id);

    /**
     * 批量删除战备情况
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglZbqkFileInfoByIds(Long[] ids);
}
