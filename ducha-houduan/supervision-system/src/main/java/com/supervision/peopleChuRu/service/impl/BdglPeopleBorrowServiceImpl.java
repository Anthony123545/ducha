package com.supervision.peopleChuRu.service.impl;

import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.utils.DateUtils;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleChuRu.mapper.BdglPeopleBorrowMapper;
import com.supervision.peopleChuRu.domain.BdglPeopleBorrow;
import com.supervision.peopleChuRu.service.IBdglPeopleBorrowService;

/**
 * 人员借调Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-05
 */
@Service
public class BdglPeopleBorrowServiceImpl implements IBdglPeopleBorrowService 
{
    @Autowired
    private BdglPeopleBorrowMapper bdglPeopleBorrowMapper;
    @Autowired
    private BdglPeopleMapper bdglPeopleMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询人员借调
     * 
     * @param id 人员借调主键
     * @return 人员借调
     */
    @Override
    public BdglPeopleBorrow selectBdglPeopleBorrowById(Long id)
    {
        return bdglPeopleBorrowMapper.selectBdglPeopleBorrowById(id);
    }

    /**
     * 查询人员借调列表
     * 
     * @param bdglPeopleBorrow 人员借调
     * @return 人员借调
     */
    @Override
    public List<BdglPeopleBorrow> selectBdglPeopleBorrowList(BdglPeopleBorrow bdglPeopleBorrow)
    {
        return bdglPeopleBorrowMapper.selectBdglPeopleBorrowList(bdglPeopleBorrow);
    }

    /**
     * 新增人员借调
     * 
     * @param bdglPeopleBorrow 人员借调
     * @return 结果
     */
    @Override
    public int insertBdglPeopleBorrow(BdglPeopleBorrow bdglPeopleBorrow)
    {
        //添加部门名称
        SysDept sysDept = deptMapper.selectDeptById(bdglPeopleBorrow.getUnitId());
        bdglPeopleBorrow.setUnitName(sysDept.getDeptName());
        bdglPeopleBorrow.setCreateTime(DateUtils.getNowDate());
        int i = bdglPeopleBorrowMapper.insertBdglPeopleBorrow(bdglPeopleBorrow);
        //同步人员在位状态
        BdglPeople bdglPeople = new BdglPeople();
        bdglPeople.setReign(3);
        bdglPeople.setId(bdglPeopleBorrow.getTransferPeopleId());
        bdglPeopleMapper.updateBdglPeople(bdglPeople);

        return i ;
    }

    /**
     * 修改人员借调
     * 
     * @param bdglPeopleBorrow 人员借调
     * @return 结果
     */
    @Override
    public int updateBdglPeopleBorrow(BdglPeopleBorrow bdglPeopleBorrow)
    {   //同步人员在位状态
        if(bdglPeopleBorrow.getState().equals("1")){
            BdglPeopleBorrow bdglPeopleBorrow1 = bdglPeopleBorrowMapper.selectBdglPeopleBorrowById(bdglPeopleBorrow.getId());
            BdglPeople bdglPeople = bdglPeopleMapper.selectBdglPeopleById(bdglPeopleBorrow1.getTransferPeopleId());
            bdglPeople.setReign(0);
            bdglPeople.setId(bdglPeople.getId());
            bdglPeopleMapper.updateBdglPeople(bdglPeople);
        }
        return bdglPeopleBorrowMapper.updateBdglPeopleBorrow(bdglPeopleBorrow);
    }

    /**
     * 批量删除人员借调
     * 
     * @param ids 需要删除的人员借调主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleBorrowByIds(Long[] ids)
    {
        return bdglPeopleBorrowMapper.deleteBdglPeopleBorrowByIds(ids);
    }

    /**
     * 删除人员借调信息
     * 
     * @param id 人员借调主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleBorrowById(Long id)
    {
        return bdglPeopleBorrowMapper.deleteBdglPeopleBorrowById(id);
    }
}
