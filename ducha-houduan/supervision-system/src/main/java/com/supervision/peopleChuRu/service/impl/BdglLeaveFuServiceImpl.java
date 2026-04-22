package com.supervision.peopleChuRu.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleChuRu.mapper.BdglLeaveFuMapper;
import com.supervision.peopleChuRu.domain.BdglLeaveFu;
import com.supervision.peopleChuRu.service.IBdglLeaveFuService;

/**
 * 特殊审批记录Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-18
 */
@Service
public class BdglLeaveFuServiceImpl implements IBdglLeaveFuService 
{
    @Autowired
    private BdglLeaveFuMapper bdglLeaveFuMapper;

    /**
     * 查询特殊审批记录
     * 
     * @param id 特殊审批记录主键
     * @return 特殊审批记录
     */
    @Override
    public BdglLeaveFu selectBdglLeaveFuById(Long id)
    {
        return bdglLeaveFuMapper.selectBdglLeaveFuById(id);
    }

    /**
     * 查询特殊审批记录列表
     * 
     * @param bdglLeaveFu 特殊审批记录
     * @return 特殊审批记录
     */
    @Override
    public List<BdglLeaveFu> selectBdglLeaveFuList(BdglLeaveFu bdglLeaveFu)
    {
        return bdglLeaveFuMapper.selectBdglLeaveFuList(bdglLeaveFu);
    }

    /**
     * 新增特殊审批记录
     * 
     * @param bdglLeaveFu 特殊审批记录
     * @return 结果
     */
    @Override
    public int insertBdglLeaveFu(BdglLeaveFu bdglLeaveFu)
    {
        return bdglLeaveFuMapper.insertBdglLeaveFu(bdglLeaveFu);
    }

    /**
     * 修改特殊审批记录
     * 
     * @param bdglLeaveFu 特殊审批记录
     * @return 结果
     */
    @Override
    public int updateBdglLeaveFu(BdglLeaveFu bdglLeaveFu)
    {
        return bdglLeaveFuMapper.updateBdglLeaveFu(bdglLeaveFu);
    }

    /**
     * 批量删除特殊审批记录
     * 
     * @param ids 需要删除的特殊审批记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglLeaveFuByIds(Long[] ids)
    {
        return bdglLeaveFuMapper.deleteBdglLeaveFuByIds(ids);
    }

    /**
     * 删除特殊审批记录信息
     * 
     * @param id 特殊审批记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglLeaveFuById(Long id)
    {
        return bdglLeaveFuMapper.deleteBdglLeaveFuById(id);
    }
}
