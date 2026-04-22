package com.supervision.militaryvehicleManagement.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.militaryvehicleManagement.mapper.BdglThebusApplyMapper;
import com.supervision.militaryvehicleManagement.domain.BdglThebusApply;
import com.supervision.militaryvehicleManagement.service.IBdglThebusApplyService;

/**
 * 用车申请Service业务层处理
 *
 * @author supervision
 * @date 2022-03-04
 */
@Service
public class BdglThebusApplyServiceImpl implements IBdglThebusApplyService
{
    @Autowired
    private BdglThebusApplyMapper bdglThebusApplyMapper;


    //删除可出车列表
    @Override
    public int deleteThebusApplyById(Long id) {
        return bdglThebusApplyMapper.deleteBdglThebusApplyById(id);
    }

    public int updateKeChuChe(BdglThebusApply thebusApply) {
        return bdglThebusApplyMapper.updateKeChuChe(thebusApply);
    }
    /**
     * 查询用车申请
     *
     * @param id 用车申请主键
     * @return 用车申请
     */
    @Override
    public BdglThebusApply selectBdglThebusApplyById(Long id)
    {
        return bdglThebusApplyMapper.selectBdglThebusApplyById(id);
    }

    /**
     * 查询用车申请列表
     *
     * @param bdglThebusApply 用车申请
     * @return 用车申请
     */
    @Override
    public List<BdglThebusApply> selectBdglThebusApplyList(BdglThebusApply bdglThebusApply)
    {
        return bdglThebusApplyMapper.selectBdglThebusApplyList(bdglThebusApply);
    }

    /**
     * 新增用车申请
     *
     * @param bdglThebusApply 用车申请
     * @return 结果
     */
    @Override
    public int insertBdglThebusApply(BdglThebusApply bdglThebusApply)
    {
        return bdglThebusApplyMapper.insertBdglThebusApply(bdglThebusApply);
    }

    /**
     * 修改用车申请
     *
     * @param bdglThebusApply 用车申请
     * @return 结果
     */
    @Override
    public int updateBdglThebusApply(BdglThebusApply bdglThebusApply)
    {
        return bdglThebusApplyMapper.updateBdglThebusApply(bdglThebusApply);
    }

    /**
     * 批量删除用车申请
     *
     * @param ids 需要删除的用车申请主键
     * @return 结果
     */
    @Override
    public int deleteBdglThebusApplyByIds(Long[] ids)
    {
        return bdglThebusApplyMapper.deleteBdglThebusApplyByIds(ids);
    }

    /**
     * 删除用车申请信息
     *
     * @param id 用车申请主键
     * @return 结果
     */
    @Override
    public int deleteBdglThebusApplyById(Long id)
    {
        return bdglThebusApplyMapper.deleteBdglThebusApplyById(id);
    }

    @Override
    public List<BdglThebusApply> selectBdglThebusApplyLists(BdglThebusApply bdglThebusApply) {
        return bdglThebusApplyMapper.selectBdglThebusApplyLists(bdglThebusApply);
    }

    @Override
    public BdglThebusApply selectBdglThebusApplyByCarIds(String license) {
        return bdglThebusApplyMapper.selectBdglThebusApplyByCarIds(license);
    }
}
