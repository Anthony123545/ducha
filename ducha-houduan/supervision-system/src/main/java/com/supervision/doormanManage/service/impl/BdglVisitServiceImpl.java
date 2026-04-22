package com.supervision.doormanManage.service.impl;

import java.util.Date;
import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.doormanManage .mapper.BdglVisitMapper;
import com.supervision.doormanManage .domain.BdglVisit;
import com.supervision.doormanManage .service.IBdglVisitService;

/**
 * 人员来访登记Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-19
 */
@Service
public class BdglVisitServiceImpl implements IBdglVisitService 
{
    @Autowired
    private BdglVisitMapper bdglVisitMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询人员来访登记
     * 
     * @param id 人员来访登记主键
     * @return 人员来访登记
     */
    @Override
    public BdglVisit selectBdglVisitById(Integer id)
    {
        return bdglVisitMapper.selectBdglVisitById(id);
    }

    /**
     * 查询人员来访登记列表
     * 
     * @param bdglVisit 人员来访登记
     * @return 人员来访登记
     */
    @Override
    public List<BdglVisit> selectBdglVisitList(BdglVisit bdglVisit)
    {
        return bdglVisitMapper.selectBdglVisitList(bdglVisit);
    }

    /**
     * 新增人员来访登记
     * 
     * @param bdglVisit 人员来访登记
     * @return 结果
     */
    @Override
    public int insertBdglVisit(BdglVisit bdglVisit,LoginUser loginUser )
    {
        if(bdglVisit.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglVisit.getUnitId()));
            bdglVisit.setUnitName(sysDept.getDeptName());
        }
        bdglVisit.setCreateTime(new Date());
        bdglVisit.setAdminId(Integer.parseInt(loginUser.getUserId().toString()));
        return bdglVisitMapper.insertBdglVisit(bdglVisit);
    }

    /**
     * 修改人员来访登记
     * 
     * @param bdglVisit 人员来访登记
     * @return 结果
     */
    @Override
    public int updateBdglVisit(BdglVisit bdglVisit)
    {
        return bdglVisitMapper.updateBdglVisit(bdglVisit);
    }

    /**
     * 批量删除人员来访登记
     * 
     * @param ids 需要删除的人员来访登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglVisitByIds(Integer[] ids)
    {
        return bdglVisitMapper.deleteBdglVisitByIds(ids);
    }

    /**
     * 删除人员来访登记信息
     * 
     * @param id 人员来访登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglVisitById(Integer id)
    {
        return bdglVisitMapper.deleteBdglVisitById(id);
    }
}
