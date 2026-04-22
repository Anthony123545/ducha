package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglCookbookRegistrationMapper;
import com.supervision.grassrootsregistration.domain.BdglCookbookRegistration;
import com.supervision.grassrootsregistration.service.IBdglCookbookRegistrationService;

/**
 * 食谱登记Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-02
 */
@Service
public class BdglCookbookRegistrationServiceImpl implements IBdglCookbookRegistrationService 
{
    @Autowired
    private BdglCookbookRegistrationMapper bdglCookbookRegistrationMapper;

    /**
     * 查询食谱登记
     * 
     * @param id 食谱登记主键
     * @return 食谱登记
     */
    @Override
    public BdglCookbookRegistration selectBdglCookbookRegistrationById(Integer id)
    {
        return bdglCookbookRegistrationMapper.selectBdglCookbookRegistrationById(id);
    }

    /**
     * 查询食谱登记列表
     * 
     * @param bdglCookbookRegistration 食谱登记
     * @return 食谱登记
     */
    @Override
    public List<BdglCookbookRegistration> selectBdglCookbookRegistrationList(BdglCookbookRegistration bdglCookbookRegistration)
    {
        return bdglCookbookRegistrationMapper.selectBdglCookbookRegistrationList(bdglCookbookRegistration);
    }

    /**
     * 新增食谱登记
     * 
     * @param bdglCookbookRegistration 食谱登记
     * @return 结果
     */
    @Override
    public int insertBdglCookbookRegistration(BdglCookbookRegistration bdglCookbookRegistration)
    {
        return bdglCookbookRegistrationMapper.insertBdglCookbookRegistration(bdglCookbookRegistration);
    }

    /**
     * 修改食谱登记
     * 
     * @param bdglCookbookRegistration 食谱登记
     * @return 结果
     */
    @Override
    public int updateBdglCookbookRegistration(BdglCookbookRegistration bdglCookbookRegistration)
    {
        return bdglCookbookRegistrationMapper.updateBdglCookbookRegistration(bdglCookbookRegistration);
    }

    /**
     * 批量删除食谱登记
     * 
     * @param ids 需要删除的食谱登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglCookbookRegistrationByIds(Integer[] ids)
    {
        return bdglCookbookRegistrationMapper.deleteBdglCookbookRegistrationByIds(ids);
    }

    /**
     * 删除食谱登记信息
     * 
     * @param id 食谱登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglCookbookRegistrationById(Integer id)
    {
        return bdglCookbookRegistrationMapper.deleteBdglCookbookRegistrationById(id);
    }
}
