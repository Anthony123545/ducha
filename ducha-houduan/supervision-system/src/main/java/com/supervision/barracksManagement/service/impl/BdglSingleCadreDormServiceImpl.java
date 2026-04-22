package com.supervision.barracksManagement.service.impl;

import java.util.List;

import com.supervision.barracksManagement.domain.BdglSingleCadreDorm;
import com.supervision.barracksManagement.mapper.BdglSingleCadreDormMapper;
import com.supervision.barracksManagement.service.IBdglSingleCadreDormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 单身干部宿舍Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-08
 */
@Service
public class BdglSingleCadreDormServiceImpl implements IBdglSingleCadreDormService
{
    @Autowired
    private BdglSingleCadreDormMapper bdglSingleCadreDormMapper;

    /**
     * 查询单身干部宿舍
     * 
     * @param id 单身干部宿舍主键
     * @return 单身干部宿舍
     */
    @Override
    public BdglSingleCadreDorm selectBdglSingleCadreDormById(Integer id)
    {
        return bdglSingleCadreDormMapper.selectBdglSingleCadreDormById(id);
    }

    /**
     * 查询单身干部宿舍列表
     * 
     * @param bdglSingleCadreDorm 单身干部宿舍
     * @return 单身干部宿舍
     */
    @Override
    public List<BdglSingleCadreDorm> selectBdglSingleCadreDormList(BdglSingleCadreDorm bdglSingleCadreDorm)
    {
        return bdglSingleCadreDormMapper.selectBdglSingleCadreDormList(bdglSingleCadreDorm);
    }

    /**
     * 新增单身干部宿舍
     * 
     * @param bdglSingleCadreDorm 单身干部宿舍
     * @return 结果
     */
    @Override
    public int insertBdglSingleCadreDorm(BdglSingleCadreDorm bdglSingleCadreDorm)
    {
        return bdglSingleCadreDormMapper.insertBdglSingleCadreDorm(bdglSingleCadreDorm);
    }

    /**
     * 修改单身干部宿舍
     * 
     * @param bdglSingleCadreDorm 单身干部宿舍
     * @return 结果
     */
    @Override
    public int updateBdglSingleCadreDorm(BdglSingleCadreDorm bdglSingleCadreDorm)
    {
        return bdglSingleCadreDormMapper.updateBdglSingleCadreDorm(bdglSingleCadreDorm);
    }

    /**
     * 批量删除单身干部宿舍
     * 
     * @param ids 需要删除的单身干部宿舍主键
     * @return 结果
     */
    @Override
    public int deleteBdglSingleCadreDormByIds(Integer[] ids)
    {
        return bdglSingleCadreDormMapper.deleteBdglSingleCadreDormByIds(ids);
    }

    /**
     * 删除单身干部宿舍信息
     * 
     * @param id 单身干部宿舍主键
     * @return 结果
     */
    @Override
    public int deleteBdglSingleCadreDormById(Integer id)
    {
        return bdglSingleCadreDormMapper.deleteBdglSingleCadreDormById(id);
    }
}
