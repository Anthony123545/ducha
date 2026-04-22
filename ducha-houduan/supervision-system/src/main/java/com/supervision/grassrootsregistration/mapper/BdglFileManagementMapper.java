package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglFileManagement;
import org.apache.ibatis.annotations.Param;

/**
 * 文件管理登计本Mapper接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface BdglFileManagementMapper 
{
    /**
     * 查询文件管理登计本
     * 
     * @param id 文件管理登计本主键
     * @return 文件管理登计本
     */
    public BdglFileManagement selectBdglFileManagementById(Integer id);

    public  List<BdglFileManagement> selectBdglSecrecyBunitunitid(@Param("unitId") Integer unitId, @Param("year") String year);

    /**
     * 查询文件管理登计本列表
     * 
     * @param bdglFileManagement 文件管理登计本
     * @return 文件管理登计本集合
     */
    public List<BdglFileManagement> selectBdglFileManagementList(BdglFileManagement bdglFileManagement);

    /**
     * 新增文件管理登计本
     * 
     * @param bdglFileManagement 文件管理登计本
     * @return 结果
     */
    public int insertBdglFileManagement(BdglFileManagement bdglFileManagement);

    /**
     * 修改文件管理登计本
     * 
     * @param bdglFileManagement 文件管理登计本
     * @return 结果
     */
    public int updateBdglFileManagement(BdglFileManagement bdglFileManagement);

    /**
     * 删除文件管理登计本
     * 
     * @param id 文件管理登计本主键
     * @return 结果
     */
    public int deleteBdglFileManagementById(Integer id);

    /**
     * 批量删除文件管理登计本
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglFileManagementByIds(Integer[] ids);
    
    int selectlFileDocCountByunitId(@Param("unitId") int unitId, @Param("year") String year);
}
