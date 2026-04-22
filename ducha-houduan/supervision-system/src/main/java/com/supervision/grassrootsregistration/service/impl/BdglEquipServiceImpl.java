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
import com.supervision.grassrootsregistration.mapper.BdglEquipMapper;
import com.supervision.grassrootsregistration.domain.BdglEquip;
import com.supervision.grassrootsregistration.service.IBdglEquipService;

/**
 * 训练器材/教材登记薄Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-26
 */
@Service
public class BdglEquipServiceImpl implements IBdglEquipService 
{
    @Autowired
    private BdglEquipMapper bdglEquipMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询训练器材/教材登记薄
     * 
     * @param id 训练器材/教材登记薄主键
     * @return 训练器材/教材登记薄
     */
    @Override
    public BdglEquip selectBdglEquipById(Long id)
    {
        return bdglEquipMapper.selectBdglEquipById(id);
    }

    @Override
    public List<BdglEquip> selectBdglByunitid(Integer unitid) {
        return bdglEquipMapper.selectBdglByunitid(unitid);
    }


    /**
     * 查询训练器材/教材登记薄列表
     * 
     * @param bdglEquip 训练器材/教材登记薄
     * @return 训练器材/教材登记薄
     */
    @Override
    public List<BdglEquip> selectBdglEquipList(BdglEquip bdglEquip)
    {
        return bdglEquipMapper.selectBdglEquipList(bdglEquip);
    }

    /**
     * 新增训练器材/教材登记薄
     * 
     * @param bdglEquip 训练器材/教材登记薄
     * @return 结果
     */
    @Override
    public int insertBdglEquip(BdglEquip bdglEquip)
    {
        if(bdglEquip.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglEquip.getUnitId()));
            bdglEquip.setUnitName(sysDept.getDeptName());
        }
        if(bdglEquip.getUnitId1()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglEquip.getUnitId1()));
            bdglEquip.setUnitName1(sysDept.getDeptName());
        }
        bdglEquip.setCreatetime(new Date());
        return bdglEquipMapper.insertBdglEquip(bdglEquip);
    }

    /**
     * 修改训练器材/教材登记薄
     * 
     * @param bdglEquip 训练器材/教材登记薄
     * @return 结果
     */
    @Override
    public int updateBdglEquip(BdglEquip bdglEquip)
    {
        if(bdglEquip.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglEquip.getUnitId()));
            bdglEquip.setUnitName(sysDept.getDeptName());
        }
        if(bdglEquip.getUnitId1()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglEquip.getUnitId1()));
            bdglEquip.setUnitName1(sysDept.getDeptName());
        }
        return bdglEquipMapper.updateBdglEquip(bdglEquip);
    }

    /**
     * 批量删除训练器材/教材登记薄
     * 
     * @param ids 需要删除的训练器材/教材登记薄主键
     * @return 结果
     */
    @Override
    public int deleteBdglEquipByIds(Long[] ids)
    {
        return bdglEquipMapper.deleteBdglEquipByIds(ids);
    }

    /**
     * 删除训练器材/教材登记薄信息
     * 
     * @param id 训练器材/教材登记薄主键
     * @return 结果
     */
    @Override
    public int deleteBdglEquipById(Long id)
    {
        return bdglEquipMapper.deleteBdglEquipById(id);
    }
    
    @Override
    public List<SysDeptDto> selectEquipList(String year) {
        //所有营级单位
        List<Map<String, SysDept>> mapList = deptMapper.selectAllCamp();
        //根据营级单位ID查询当前单位下是否有数据
        List<SysDeptDto> dtoList = new ArrayList<>();
        mapList.forEach(item -> {
            SysDeptDto deptDto = BeanUtil.mapToBean(item, SysDeptDto.class, false);
            int deptId = deptDto.getDeptId().intValue();
            int countByUnitId = bdglEquipMapper.selectlEquipCountByunitId(deptId,year);
            if (countByUnitId > 0) {
                dtoList.add(deptDto);
            }
        });
        return dtoList;
    }
    
    @Override
    public List<BdglEquip> selectEquIpByunitId(Integer unitid, String year) {
        return bdglEquipMapper.selectEquIpByunitId(unitid,year);
    }
}
