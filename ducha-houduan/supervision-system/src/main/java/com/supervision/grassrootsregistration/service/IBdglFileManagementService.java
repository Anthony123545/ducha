package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglFileManagement;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;

/**
 * 文件管理登计本Service接口
 *
 * @author supervision
 * @date 2022-02-26
 */
public interface IBdglFileManagementService
{
    /**
     * 查询文件管理登计本
     *
     * @param id 文件管理登计本主键
     * @return 文件管理登计本
     */
    public BdglFileManagement selectBdglFileManagementById(Integer id);

    public BdglFileManagement selectBdglFileManagementDetailById(Integer id);

    public  List<BdglFileManagement> selectBdglSecrecyBunitunitid(Integer unitid,String year);

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
     * 批量删除文件管理登计本
     *
     * @param ids 需要删除的文件管理登计本主键集合
     * @return 结果
     */
    public int deleteBdglFileManagementByIds(Integer[] ids);

    /**
     * 删除文件管理登计本信息
     *
     * @param id 文件管理登计本主键
     * @return 结果
     */
    public int deleteBdglFileManagementById(Integer id);

    List<SysDeptDto> selectFileDocList(String year);
}
