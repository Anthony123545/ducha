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
import com.supervision.grassrootsregistration.mapper.BdglArmamentMapper;
import com.supervision.grassrootsregistration.domain.BdglArmament;
import com.supervision.grassrootsregistration.service.IBdglArmamentService;

/**
 * 军械装备登记簿Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-26
 */
@Service
public class BdglArmamentServiceImpl implements IBdglArmamentService 
{
    @Autowired
    private BdglArmamentMapper bdglArmamentMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询军械装备登记簿
     * 
     * @param id 军械装备登记簿主键
     * @return 军械装备登记簿
     */
    @Override
    public BdglArmament selectBdglArmamentById(Integer id)
    {
        return bdglArmamentMapper.selectBdglArmamentById(id);
    }

    @Override
    public List<BdglArmament> selectArmamentunitid(Integer unitid,String year) {
        return bdglArmamentMapper.selectArmamentunitid(unitid,year);
    }

    /**
     * 查询军械装备登记簿列表
     * 
     * @param bdglArmament 军械装备登记簿
     * @return 军械装备登记簿
     */
    @Override
    public List<BdglArmament> selectBdglArmamentList(BdglArmament bdglArmament)
    {
        return bdglArmamentMapper.selectBdglArmamentList(bdglArmament);
    }

    /**
     * 新增军械装备登记簿
     * 
     * @param bdglArmament 军械装备登记簿
     * @return 结果
     */
    @Override
    public int insertBdglArmament(BdglArmament bdglArmament)
    {
        if(bdglArmament.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglArmament.getUnitId()));
            bdglArmament.setUnitName(sysDept.getDeptName());
        }
        bdglArmament.setCreatetime(new Date());
        return bdglArmamentMapper.insertBdglArmament(bdglArmament);
    }

    /**
     * 修改军械装备登记簿
     * 
     * @param bdglArmament 军械装备登记簿
     * @return 结果
     */
    @Override
    public int updateBdglArmament(BdglArmament bdglArmament)
    {
        if(bdglArmament.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglArmament.getUnitId()));
            bdglArmament.setUnitName(sysDept.getDeptName());
        }
        return bdglArmamentMapper.updateBdglArmament(bdglArmament);
    }

    /**
     * 批量删除军械装备登记簿
     * 
     * @param ids 需要删除的军械装备登记簿主键
     * @return 结果
     */
    @Override
    public int deleteBdglArmamentByIds(Integer[] ids)
    {
        return bdglArmamentMapper.deleteBdglArmamentByIds(ids);
    }

    /**
     * 删除军械装备登记簿信息
     * 
     * @param id 军械装备登记簿主键
     * @return 结果
     */
    @Override
    public int deleteBdglArmamentById(Integer id)
    {
        return bdglArmamentMapper.deleteBdglArmamentById(id);
    }
    
    @Override
    public List<SysDeptDto> selectArmamentList(String year) {
        //所有营级单位
        List<Map<String, SysDept>> mapList = deptMapper.selectAllCamp();
        //根据营级单位ID查询当前单位下是否有数据
        List<SysDeptDto> dtoList = new ArrayList<>();
        mapList.forEach(item -> {
            SysDeptDto deptDto = BeanUtil.mapToBean(item, SysDeptDto.class, false);
            int deptId = deptDto.getDeptId().intValue();
            int countByUnitId = bdglArmamentMapper.selectlArmamentCountByunitId(deptId,year);
            if (countByUnitId > 0) {
                dtoList.add(deptDto);
            }
        });
        return dtoList;
    }
}
