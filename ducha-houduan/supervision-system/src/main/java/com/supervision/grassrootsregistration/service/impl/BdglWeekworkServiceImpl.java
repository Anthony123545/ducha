package com.supervision.grassrootsregistration.service.impl;

import java.util.Date;
import java.util.List;

import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.supervision.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.supervision.grassrootsregistration .domain.BdglWeekworkRegister;
import com.supervision.grassrootsregistration .mapper.BdglWeekworkMapper;
import com.supervision.grassrootsregistration .domain.BdglWeekwork;
import com.supervision.grassrootsregistration .service.IBdglWeekworkService;

/**
 * 一周工作安排Service业务层处理
 *
 * @author supervision
 * @date 2022-02-28
 */
@Service
public class BdglWeekworkServiceImpl implements IBdglWeekworkService
{
    @Autowired
    private BdglWeekworkMapper bdglWeekworkMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    /**
     * 查询一周工作安排
     *
     * @param id 一周工作安排主键
     * @return 一周工作安排
     */
    @Override
    public BdglWeekwork selectBdglWeekworkById(Integer id)
    {
        BdglWeekwork bdglWeekwork = bdglWeekworkMapper.selectBdglWeekworkById(id);
        return bdglWeekwork;
    }

    /**
     * 查询一周工作安排列表
     *
     * @param bdglWeekwork 一周工作安排
     * @return 一周工作安排
     */
    @Override
    public List<BdglWeekwork> selectBdglWeekworkList(BdglWeekwork bdglWeekwork)
    {
        return bdglWeekworkMapper.selectBdglWeekworkList(bdglWeekwork);
    }

    /**
     * 新增一周工作安排
     *
     * @param bdglWeekwork 一周工作安排
     * @return 结果
     */
    @Transactional
    @Override
    public int insertBdglWeekwork(BdglWeekwork bdglWeekwork)
    {
        if(bdglWeekwork.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglWeekwork.getUnitId()));
            bdglWeekwork.setUnitName(sysDept.getDeptName());
        }
        Date endTime = bdglWeekwork.getEndTime();

        endTime.setHours(23);
        endTime.setMinutes(59);
        bdglWeekwork.setEndTime(endTime);
        int rows = bdglWeekworkMapper.insertBdglWeekwork(bdglWeekwork);
        insertBdglWeekworkRegister(bdglWeekwork);
        return rows;
    }

    /**
     * 修改一周工作安排
     *
     * @param bdglWeekwork 一周工作安排
     * @return 结果
     */
    @Transactional
    @Override
    public int updateBdglWeekwork(BdglWeekwork bdglWeekwork)
    {
        if(bdglWeekwork.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglWeekwork.getUnitId()));
            bdglWeekwork.setUnitName(sysDept.getDeptName());
        }
        bdglWeekworkMapper.deleteBdglWeekworkRegisterByWeekworkId(bdglWeekwork.getId());
        insertBdglWeekworkRegister(bdglWeekwork);
        return bdglWeekworkMapper.updateBdglWeekwork(bdglWeekwork);
    }

    /**
     * 批量删除一周工作安排
     *
     * @param ids 需要删除的一周工作安排主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteBdglWeekworkByIds(Integer[] ids)
    {
        bdglWeekworkMapper.deleteBdglWeekworkRegisterByWeekworkIds(ids);
        return bdglWeekworkMapper.deleteBdglWeekworkByIds(ids);
    }

    /**
     * 删除一周工作安排信息
     *
     * @param id 一周工作安排主键
     * @return 结果
     */
    @Override
    public int deleteBdglWeekworkById(Integer id)
    {
        bdglWeekworkMapper.deleteBdglWeekworkRegisterByWeekworkId(id);
        return bdglWeekworkMapper.deleteBdglWeekworkById(id);
    }

    @Override
    public List<BdglWeekwork> selectBdglWeekworkLists(BdglWeekwork bdglWeekwork1) {
        return bdglWeekworkMapper.selectBdglWeekworkLists(bdglWeekwork1);
    }

    @Override
    public BdglWeekwork selectBdglWeekwork(BdglWeekwork bdglWeekwork) {
        return bdglWeekworkMapper.selectBdglWeekwork(bdglWeekwork);
    }

    /**
     * 新增一周安排工作分信息
     *
     * @param bdglWeekwork 一周工作安排对象
     */
    public void insertBdglWeekworkRegister(BdglWeekwork bdglWeekwork)
    {
        List<BdglWeekworkRegister> bdglWeekworkRegisterList = bdglWeekwork.getBdglWeekworkRegisterList();
        Integer id = bdglWeekwork.getId();
        if (StringUtils.isNotNull(bdglWeekworkRegisterList))
        {
            List<BdglWeekworkRegister> list = new ArrayList<BdglWeekworkRegister>();
            for (BdglWeekworkRegister bdglWeekworkRegister : bdglWeekworkRegisterList)
            {
                bdglWeekworkRegister.setUnitId(bdglWeekwork.getUnitId());
                addSort(bdglWeekworkRegister);
                bdglWeekworkRegister.setWeekworkId(id);
                list.add(bdglWeekworkRegister);
            }
            if (list.size() > 0)
            {
                bdglWeekworkMapper.batchBdglWeekworkRegister(list);
            }
        }
    }
    
    /**
     * 根据时段添加排序字段
     * @param bdglWeekworkRegister
     */
    private void addSort(BdglWeekworkRegister bdglWeekworkRegister) {
        //根据时段添加排序字段
        if ("早晨".equals(bdglWeekworkRegister.getDateline())){
            bdglWeekworkRegister.setSort(1);
        }
        if ("上午".equals(bdglWeekworkRegister.getDateline())){
            bdglWeekworkRegister.setSort(2);
        }
        if ("下午".equals(bdglWeekworkRegister.getDateline())){
            bdglWeekworkRegister.setSort(3);
        }
        if ("晚上".equals(bdglWeekworkRegister.getDateline())){
            bdglWeekworkRegister.setSort(4);
        }
    }
}