package com.supervision.militaryvehicleManagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.militaryvehicleManagement.mapper.BdglDepartureInfoMapper;
import com.supervision.militaryvehicleManagement.domain.BdglDepartureInfo;
import com.supervision.militaryvehicleManagement.service.IBdglDepartureInfoService;

/**
 * 出车信息Service业务层处理
 *
 * @author supervision
 * @date 2022-03-19
 */
@Service
public class BdglDepartureInfoServiceImpl implements IBdglDepartureInfoService {
    @Autowired
    private BdglDepartureInfoMapper bdglDepartureInfoMapper;
    
    /**
     * 查询出车信息
     *
     * @param id 出车信息主键
     * @return 出车信息
     */
    @Override
    public BdglDepartureInfo selectBdglDepartureInfoById(Long id) {
        return bdglDepartureInfoMapper.selectBdglDepartureInfoById(id);
    }
    
    /**
     * 查询出车信息列表
     *
     * @param bdglDepartureInfo 出车信息
     * @return 出车信息
     */
    @Override
    public List<BdglDepartureInfo> selectBdglDepartureInfoList(BdglDepartureInfo bdglDepartureInfo) {
        return bdglDepartureInfoMapper.selectBdglDepartureInfoList(bdglDepartureInfo);
    }
    
    /**
     * 新增出车信息
     *
     * @param bdglDepartureInfo 出车信息
     * @return 结果
     */
    @Override
    public int insertBdglDepartureInfo(BdglDepartureInfo bdglDepartureInfo) {
        return bdglDepartureInfoMapper.insertBdglDepartureInfo(bdglDepartureInfo);
    }
    
    /**
     * 修改出车信息
     *
     * @param bdglDepartureInfo 出车信息
     * @return 结果
     */
    @Override
    public int updateBdglDepartureInfo(BdglDepartureInfo bdglDepartureInfo) {
        return bdglDepartureInfoMapper.updateBdglDepartureInfo(bdglDepartureInfo);
    }
    
    /**
     * 批量删除出车信息
     *
     * @param ids 需要删除的出车信息主键
     * @return 结果
     */
    @Override
    public int deleteBdglDepartureInfoByIds(Long[] ids) {
        return bdglDepartureInfoMapper.deleteBdglDepartureInfoByIds(ids);
    }
    
    /**
     * 删除出车信息信息
     *
     * @param id 出车信息主键
     * @return 结果
     */
    @Override
    public int deleteBdglDepartureInfoById(Long id) {
        return bdglDepartureInfoMapper.deleteBdglDepartureInfoById(id);
    }
}
