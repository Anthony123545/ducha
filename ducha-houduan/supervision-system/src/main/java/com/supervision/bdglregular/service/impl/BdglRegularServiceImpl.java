package com.supervision.bdglregular.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.bdglregular.mapper.BdglRegularMapper;
import com.supervision.bdglregular.domain.BdglRegular;
import com.supervision.bdglregular.service.IBdglRegularService;

/**
 * 经常性检查Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-07
 */
@Service
public class BdglRegularServiceImpl implements IBdglRegularService 
{
    @Autowired
    private BdglRegularMapper bdglRegularMapper;

    /**
     * 查询经常性检查
     * 
     * @param id 经常性检查主键
     * @return 经常性检查
     */
    @Override
    public BdglRegular selectBdglRegularById(Long id)
    {
        return bdglRegularMapper.selectBdglRegularById(id);
    }

    /**
     * 查询经常性检查列表
     * 
     * @param bdglRegular 经常性检查
     * @return 经常性检查
     */
    @Override
    public List<BdglRegular> selectBdglRegularList(BdglRegular bdglRegular)
    {
        return bdglRegularMapper.selectBdglRegularList(bdglRegular);
    }

    /**
     * 根据部门ID 进行查询
     * @param bdglRegular
     * @return
     */
    public List<BdglRegular> selectBdglRegularListByDeptId(BdglRegular bdglRegular) {
        return bdglRegularMapper.selectBdglRegularListByDeptId(bdglRegular);
    }

    /**
     * 新增经常性检查
     * 
     * @param bdglRegular 经常性检查
     * @return 结果
     */
    @Override
    public int insertBdglRegular(BdglRegular bdglRegular)
    {
        bdglRegular.setCreateTime(DateUtils.getNowDate());
        return bdglRegularMapper.insertBdglRegular(bdglRegular);
    }

    /**
     * 修改经常性检查
     * 
     * @param bdglRegular 经常性检查
     * @return 结果
     */
    @Override
    public int updateBdglRegular(BdglRegular bdglRegular)
    {
        bdglRegular.setUpdateTime(DateUtils.getNowDate());
        return bdglRegularMapper.updateBdglRegular(bdglRegular);
    }

    /**
     * 批量删除经常性检查
     * 
     * @param ids 需要删除的经常性检查主键
     * @return 结果
     */
    @Override
    public int deleteBdglRegularByIds(Long[] ids)
    {
        return bdglRegularMapper.deleteBdglRegularByIds(ids);
    }

    /**
     * 根据部门ID 查出所有数据
     * @param deptId
     * @return
     */
    public List<BdglRegular> selectDeptById(long deptId) {
        return bdglRegularMapper.selectDeptById(deptId);
    }

    /**
     * 查询当前部门以及部门下减分状态的数据
     * @param deptId
     * @return
     */
    @Override
    public List<BdglRegular> selectDeptByIdAndStates( long deptId) {
        return bdglRegularMapper.selectDeptByIdAndStates(deptId);
    }


    /**
     * 删除经常性检查信息
     * 
     * @param id 经常性检查主键
     * @return 结果
     */
    @Override
    public int deleteBdglRegularById(Long id)
    {
        return bdglRegularMapper.deleteBdglRegularById(id);
    }

    @Override
    public List<BdglRegular> selectBdglRegularLists(BdglRegular bdglRegulars) {
        return bdglRegularMapper.selectBdglRegularLists(bdglRegulars);
    }
}
