package com.supervision.grassrootsregistration.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration .mapper.BdglSoldierMapper;
import com.supervision.grassrootsregistration .domain.BdglSoldier;
import com.supervision.grassrootsregistration .service.IBdglSoldierService;

/**
 * 军人委员会会议登记本Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-26
 */
@Service
public class BdglSoldierServiceImpl implements IBdglSoldierService 
{
    @Autowired
    private BdglSoldierMapper bdglSoldierMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询军人委员会会议登记本
     * 
     * @param id 军人委员会会议登记本主键
     * @return 军人委员会会议登记本
     */
    @Override
    public BdglSoldier selectBdglSoldierById(Integer id)
    {
        return bdglSoldierMapper.selectBdglSoldierById(id);
    }

    /**
     * 查询军人委员会会议登记本列表
     * 
     * @param bdglSoldier 军人委员会会议登记本
     * @return 军人委员会会议登记本
     */
    @Override
    public List<BdglSoldier> selectBdglSoldierList(BdglSoldier bdglSoldier)
    {
        return bdglSoldierMapper.selectBdglSoldierList(bdglSoldier);
    }

    /**
     * 新增军人委员会会议登记本
     * 
     * @param bdglSoldier 军人委员会会议登记本
     * @return 结果
     */
    @Override
    public int insertBdglSoldier(BdglSoldier bdglSoldier)
    {
        if(bdglSoldier.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglSoldier.getUnitId()));
            bdglSoldier.setDeptName(sysDept.getDeptName());
        }
        bdglSoldier.setCreatetime(new Date());
        return bdglSoldierMapper.insertBdglSoldier(bdglSoldier);
    }

    /**
     * 修改军人委员会会议登记本
     * 
     * @param bdglSoldier 军人委员会会议登记本
     * @return 结果
     */
    @Override
    public int updateBdglSoldier(BdglSoldier bdglSoldier)
    {
        if(bdglSoldier.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglSoldier.getUnitId()));
            bdglSoldier.setDeptName(sysDept.getDeptName());
        }
        return bdglSoldierMapper.updateBdglSoldier(bdglSoldier);
    }

    /**
     * 批量删除军人委员会会议登记本
     * 
     * @param ids 需要删除的军人委员会会议登记本主键
     * @return 结果
     */
    @Override
    public int deleteBdglSoldierByIds(Integer[] ids)
    {
        return bdglSoldierMapper.deleteBdglSoldierByIds(ids);
    }

    /**
     * 删除军人委员会会议登记本信息
     * 
     * @param id 军人委员会会议登记本主键
     * @return 结果
     */
    @Override
    public int deleteBdglSoldierById(Integer id)
    {
        return bdglSoldierMapper.deleteBdglSoldierById(id);
    }
    
    @Override
    public List<SysDeptDto> querySoldierList(String year) {
        //所有营级单位
        List<Map<String, SysDept>> mapList = deptMapper.selectAllCamp();
        //根据营级单位ID查询当前单位下是否有数据
        List<SysDeptDto> dtoList = new ArrayList<>();
        mapList.forEach(item -> {
            SysDeptDto deptDto = BeanUtil.mapToBean(item, SysDeptDto.class, false);
            int deptId = deptDto.getDeptId().intValue();
            int countByunitId = bdglSoldierMapper.selectSoldierByunitId(deptId,year);
            if (countByunitId > 0) {
                dtoList.add(deptDto);
            }
        });
        return dtoList;
    }
    
    @Override
    public List<BdglSoldier> getSoldierOnUnitList(Integer unitId,String year) {
        return bdglSoldierMapper.selectSoldierOnUnitList(unitId,year);
    }
}
