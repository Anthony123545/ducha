package com.supervision.militaryvehicleManagement.service;

import java.util.List;
import com.supervision.militaryvehicleManagement.domain.BdglDepartureInfo;

/**
 * 出车信息Service接口
 * 
 * @author supervision
 * @date 2022-03-19
 */
public interface IBdglDepartureInfoService 
{
    /**
     * 查询出车信息
     * 
     * @param id 出车信息主键
     * @return 出车信息
     */
    public BdglDepartureInfo selectBdglDepartureInfoById(Long id);

    /**
     * 查询出车信息列表
     * 
     * @param bdglDepartureInfo 出车信息
     * @return 出车信息集合
     */
    public List<BdglDepartureInfo> selectBdglDepartureInfoList(BdglDepartureInfo bdglDepartureInfo);

    /**
     * 新增出车信息
     * 
     * @param bdglDepartureInfo 出车信息
     * @return 结果
     */
    public int insertBdglDepartureInfo(BdglDepartureInfo bdglDepartureInfo);

    /**
     * 修改出车信息
     * 
     * @param bdglDepartureInfo 出车信息
     * @return 结果
     */
    public int updateBdglDepartureInfo(BdglDepartureInfo bdglDepartureInfo);

    /**
     * 批量删除出车信息
     * 
     * @param ids 需要删除的出车信息主键集合
     * @return 结果
     */
    public int deleteBdglDepartureInfoByIds(Long[] ids);

    /**
     * 删除出车信息信息
     * 
     * @param id 出车信息主键
     * @return 结果
     */
    public int deleteBdglDepartureInfoById(Long id);
}
