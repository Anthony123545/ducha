package com.supervision.bdglregular.service.impl;

import java.util.List;

import com.supervision.bdglregular.domain.BdglRegular;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.bdglregular.mapper.BdglSpecialMapper;
import com.supervision.bdglregular.domain.BdglSpecial;
import com.supervision.bdglregular.service.IBdglSpecialService;

/**
 * 专项检查Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-07
 */
@Service
public class BdglSpecialServiceImpl implements IBdglSpecialService 
{
    @Autowired
    private BdglSpecialMapper bdglSpecialMapper;

    /**
     * 查询专项检查
     * 
     * @param id 专项检查主键
     * @return 专项检查
     */
    @Override
    public BdglSpecial selectBdglSpecialById(Long id)
    {
        return bdglSpecialMapper.selectBdglSpecialById(id);
    }

    /**
     * 查询专项检查列表
     * 
     * @param bdglSpecial 专项检查
     * @return 专项检查
     */
    @Override
    public List<BdglSpecial> selectBdglSpecialList(BdglSpecial bdglSpecial)
    {
        return bdglSpecialMapper.selectBdglSpecialList(bdglSpecial);
    }

    /**
     * 新增专项检查
     * 
     * @param bdglSpecial 专项检查
     * @return 结果
     */
    @Override
    public int insertBdglSpecial(BdglSpecial bdglSpecial)
    {
        bdglSpecial.setCreateTime(DateUtils.getNowDate());
        return bdglSpecialMapper.insertBdglSpecial(bdglSpecial);
    }

    /**
     * 修改专项检查
     * 
     * @param bdglSpecial 专项检查
     * @return 结果
     */
    @Override
    public int updateBdglSpecial(BdglSpecial bdglSpecial)
    {
        bdglSpecial.setUpdateTime(DateUtils.getNowDate());
        return bdglSpecialMapper.updateBdglSpecial(bdglSpecial);
    }

    /**
     * 批量删除专项检查
     * 
     * @param ids 需要删除的专项检查主键
     * @return 结果
     */
    @Override
    public int deleteBdglSpecialByIds(Long[] ids)
    {
        return bdglSpecialMapper.deleteBdglSpecialByIds(ids);
    }

    /**
     * 根据部门ID 查出所有数据
     * @param deptId
     * @return
     */
    public List<BdglSpecial> selectDeptById(long deptId) {
        return bdglSpecialMapper.selectDeptById(deptId);
    }

    /**
     * 查询当前部门以及部门下减分状态的数据
     * @param deptId
     * @return
     */
    public List<BdglSpecial> selectDeptByIdAndStates( long deptId){
        return bdglSpecialMapper.selectDeptByIdAndStates(deptId);
    }

    /**
     * 删除专项检查信息
     * 
     * @param id 专项检查主键
     * @return 结果
     */
    @Override
    public int deleteBdglSpecialById(Long id)
    {
        return bdglSpecialMapper.deleteBdglSpecialById(id);
    }

    @Override
    public List<BdglSpecial> selectBdglSpecialLists(BdglSpecial bdglRegulars) {
        return bdglSpecialMapper.selectBdglSpecialLists(bdglRegulars);
    }


}
