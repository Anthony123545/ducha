package com.supervision.grassrootsregistration.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.utils.DateUtils;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration .mapper.BdglPartyMapper;
import com.supervision.grassrootsregistration .domain.BdglParty;
import com.supervision.grassrootsregistration .service.IBdglPartyService;

import javax.annotation.Resource;

/**
 * 党支部会议登记本Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-26
 */
@Service
public class BdglPartyServiceImpl implements IBdglPartyService 
{
    @Autowired
    private BdglPartyMapper bdglPartyMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;

    /**
     * 查询党支部会议登记本
     * 
     * @param id 党支部会议登记本主键
     * @return 党支部会议登记本
     */
    @Override
    public BdglParty selectBdglPartyById(Integer id)
    {
        return bdglPartyMapper.selectBdglPartyById(id);
    }

    /**
     * 查询党支部会议登记本列表
     * 
     * @param bdglParty 党支部会议登记本
     * @return 党支部会议登记本
     */
    @Override
    public List<BdglParty> selectBdglPartyList(BdglParty bdglParty)
    {
        return bdglPartyMapper.selectBdglPartyList(bdglParty);
    }

    /**
     * 新增党支部会议登记本
     * 
     * @param bdglParty 党支部会议登记本
     * @return 结果
     */
    @Override
    public int insertBdglParty(BdglParty bdglParty)
    {
        if(bdglParty.getUnitId()!=null){
            SysDept sysDept = sysDeptMapper.selectDeptById(Long.valueOf(bdglParty.getUnitId()));
            bdglParty.setUnitName(sysDept.getDeptName());
        }
        bdglParty.setCreatetime(DateUtils.getNowDate());
        return bdglPartyMapper.insertBdglParty(bdglParty);
    }

    /**
     * 修改党支部会议登记本
     * 
     * @param bdglParty 党支部会议登记本
     * @return 结果
     */
    @Override
    public int updateBdglParty(BdglParty bdglParty)
    {
        if(bdglParty.getUnitId()!=null){
            SysDept sysDept = sysDeptMapper.selectDeptById(Long.valueOf(bdglParty.getUnitId()));
            bdglParty.setUnitName(sysDept.getDeptName());
        }
        bdglParty.setUpdatetime(DateUtils.getNowDate());
        return bdglPartyMapper.updateBdglParty(bdglParty);
    }

    /**
     * 批量删除党支部会议登记本
     * 
     * @param ids 需要删除的党支部会议登记本主键
     * @return 结果
     */
    @Override
    public int deleteBdglPartyByIds(Integer[] ids)
    {
        return bdglPartyMapper.deleteBdglPartyByIds(ids);
    }

    /**
     * 删除党支部会议登记本信息
     * 
     * @param id 党支部会议登记本主键
     * @return 结果
     */
    @Override
    public int deleteBdglPartyById(Integer id)
    {
        return bdglPartyMapper.deleteBdglPartyById(id);
    }
    
    @Override
    public List<SysDeptDto> getPartyList(String year) {
        //所有营级单位
        List<Map<String, SysDept>> mapList = sysDeptMapper.selectAllCamp();
        //根据营级单位ID查询当前单位下是否有数据
        List<SysDeptDto> dtoList = new ArrayList<>();
        mapList.forEach(item -> {
            SysDeptDto deptDto = BeanUtil.mapToBean(item, SysDeptDto.class, false);
            int deptId = deptDto.getDeptId().intValue();
            int countByunitId = bdglPartyMapper.selectlPartyByunitId(deptId,year);
            if (countByunitId > 0) {
                dtoList.add(deptDto);
            }
        });
        return dtoList;
    }
    
    @Override
    public List<BdglParty> getPartyOnUnitList(Integer unitId,String year) {
        return bdglPartyMapper.selectPartyOnUnitList(unitId,year);
    }
}
