package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglFoodBoardWagesMapper;
import com.supervision.grassrootsregistration.domain.BdglFoodBoardWages;
import com.supervision.grassrootsregistration.service.IBdglFoodBoardWagesService;

/**
 * 交/退伙食费Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-28
 */
@Service
public class BdglFoodBoardWagesServiceImpl implements IBdglFoodBoardWagesService 
{
    @Autowired
    private BdglFoodBoardWagesMapper bdglFoodBoardWagesMapper;

    /**
     * 查询交/退伙食费
     * 
     * @param id 交/退伙食费主键
     * @return 交/退伙食费
     */
    @Override
    public BdglFoodBoardWages selectBdglFoodBoardWagesById(Integer id)
    {
        return bdglFoodBoardWagesMapper.selectBdglFoodBoardWagesById(id);
    }

    /**
     * 查询交/退伙食费列表
     * 
     * @param bdglFoodBoardWages 交/退伙食费
     * @return 交/退伙食费
     */
    @Override
    public List<BdglFoodBoardWages> selectBdglFoodBoardWagesList(BdglFoodBoardWages bdglFoodBoardWages)
    {
        return bdglFoodBoardWagesMapper.selectBdglFoodBoardWagesList(bdglFoodBoardWages);
    }

    /**
     * 新增交/退伙食费
     * 
     * @param bdglFoodBoardWages 交/退伙食费
     * @return 结果
     */
    @Override
    public int insertBdglFoodBoardWages(BdglFoodBoardWages bdglFoodBoardWages)
    {
        return bdglFoodBoardWagesMapper.insertBdglFoodBoardWages(bdglFoodBoardWages);
    }

    /**
     * 修改交/退伙食费
     * 
     * @param bdglFoodBoardWages 交/退伙食费
     * @return 结果
     */
    @Override
    public int updateBdglFoodBoardWages(BdglFoodBoardWages bdglFoodBoardWages)
    {
        return bdglFoodBoardWagesMapper.updateBdglFoodBoardWages(bdglFoodBoardWages);
    }

    /**
     * 批量删除交/退伙食费
     * 
     * @param ids 需要删除的交/退伙食费主键
     * @return 结果
     */
    @Override
    public int deleteBdglFoodBoardWagesByIds(Integer[] ids)
    {
        return bdglFoodBoardWagesMapper.deleteBdglFoodBoardWagesByIds(ids);
    }

    /**
     * 删除交/退伙食费信息
     * 
     * @param id 交/退伙食费主键
     * @return 结果
     */
    @Override
    public int deleteBdglFoodBoardWagesById(Integer id)
    {
        return bdglFoodBoardWagesMapper.deleteBdglFoodBoardWagesById(id);
    }
}
