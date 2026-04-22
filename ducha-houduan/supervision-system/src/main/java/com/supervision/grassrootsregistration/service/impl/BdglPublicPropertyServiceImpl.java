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
import com.supervision.grassrootsregistration.mapper.BdglPublicPropertyMapper;
import com.supervision.grassrootsregistration.domain.BdglPublicProperty;
import com.supervision.grassrootsregistration.service.IBdglPublicPropertyService;

/**
 * 营产，公物管理登记簿Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-26
 */
@Service
public class BdglPublicPropertyServiceImpl implements IBdglPublicPropertyService 
{
    @Autowired
    private BdglPublicPropertyMapper bdglPublicPropertyMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询营产，公物管理登记簿
     * 
     * @param id 营产，公物管理登记簿主键
     * @return 营产，公物管理登记簿
     */
    @Override
    public BdglPublicProperty selectBdglPublicPropertyById(Integer id)
    {
        return bdglPublicPropertyMapper.selectBdglPublicPropertyById(id);
    }

    @Override
    public List<BdglPublicProperty> selectPublicPropertyunitid(Integer unitid,String year) {
        return bdglPublicPropertyMapper.selectPublicPropertyunitid(unitid,year);
    }

    /**
     * 查询营产，公物管理登记簿列表
     * 
     * @param bdglPublicProperty 营产，公物管理登记簿
     * @return 营产，公物管理登记簿
     */
    @Override
    public List<BdglPublicProperty> selectBdglPublicPropertyList(BdglPublicProperty bdglPublicProperty)
    {
        return bdglPublicPropertyMapper.selectBdglPublicPropertyList(bdglPublicProperty);
    }

    /**
     * 新增营产，公物管理登记簿
     * 
     * @param bdglPublicProperty 营产，公物管理登记簿
     * @return 结果
     */
    @Override
    public int insertBdglPublicProperty(BdglPublicProperty bdglPublicProperty)
    {
        if (bdglPublicProperty.getUnitId()!=null) {
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglPublicProperty.getUnitId()));
            bdglPublicProperty.setUnitName(sysDept.getDeptName());
        }
        bdglPublicProperty.setCreatetime(new Date());
            return bdglPublicPropertyMapper.insertBdglPublicProperty(bdglPublicProperty);

    }

    /**
     * 修改营产，公物管理登记簿
     * 
     * @param bdglPublicProperty 营产，公物管理登记簿
     * @return 结果
     */
    @Override
    public int updateBdglPublicProperty(BdglPublicProperty bdglPublicProperty)
    {
        if (bdglPublicProperty.getUnitId()!=null) {
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglPublicProperty.getUnitId()));
            bdglPublicProperty.setUnitName(sysDept.getDeptName());
        }
        return bdglPublicPropertyMapper.updateBdglPublicProperty(bdglPublicProperty);
    }

    /**
     * 批量删除营产，公物管理登记簿
     * 
     * @param ids 需要删除的营产，公物管理登记簿主键
     * @return 结果
     */
    @Override
    public int deleteBdglPublicPropertyByIds(Integer[] ids)
    {
        return bdglPublicPropertyMapper.deleteBdglPublicPropertyByIds(ids);
    }

    /**
     * 删除营产，公物管理登记簿信息
     * 
     * @param id 营产，公物管理登记簿主键
     * @return 结果
     */
    @Override
    public int deleteBdglPublicPropertyById(Integer id)
    {
        return bdglPublicPropertyMapper.deleteBdglPublicPropertyById(id);
    }
    
    /**
     * 领导查看删除营产，公物管理登记簿信息
     */
    @Override
    public List<SysDeptDto> selectPropertyList(String year) {
        //所有营级单位
        List<Map<String, SysDept>> mapList = deptMapper.selectAllCamp();
        //根据营级单位ID查询当前单位下是否有数据
        List<SysDeptDto> dtoList = new ArrayList<>();
        mapList.forEach(item -> {
            SysDeptDto deptDto = BeanUtil.mapToBean(item, SysDeptDto.class, false);
            int deptId = deptDto.getDeptId().intValue();
            int countByUnitId = bdglPublicPropertyMapper.selectPropertyCountByunitId(deptId,year);
            if (countByUnitId > 0) {
                dtoList.add(deptDto);
            }
        });
        return dtoList;
    }
}
