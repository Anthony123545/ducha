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
import com.supervision.grassrootsregistration.mapper.BdglDrillMapper;
import com.supervision.grassrootsregistration.domain.BdglDrill;
import com.supervision.grassrootsregistration.service.IBdglDrillService;

/**
 * 军事训练登记簿Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-26
 */
@Service
public class BdglDrillServiceImpl implements IBdglDrillService 
{
    @Autowired
    private BdglDrillMapper bdglDrillMapper;
    
    @Autowired
    private SysDeptMapper deptMapper;
    /**
     * 查询军事训练登记簿
     * 
     * @param id 军事训练登记簿主键
     * @return 军事训练登记簿
     */
    @Override
    public BdglDrill selectBdglDrillById(Integer id)
    {
        return bdglDrillMapper.selectBdglDrillById(id);
    }

    /**
     * 查询军事训练登记簿列表
     * 
     * @param bdglDrill 军事训练登记簿
     * @return 军事训练登记簿
     */
    @Override
    public List<BdglDrill> selectBdglDrillList(BdglDrill bdglDrill)
    {
        return bdglDrillMapper.selectBdglDrillList(bdglDrill);
    }

    /**
     * 新增军事训练登记簿
     * 
     * @param bdglDrill 军事训练登记簿
     * @return 结果
     */
    @Override
    public int insertBdglDrill(BdglDrill bdglDrill)
    {
        bdglDrill.setCreatetime(new Date());
        return bdglDrillMapper.insertBdglDrill(bdglDrill);
    }

    /**
     * 修改军事训练登记簿
     * 
     * @param bdglDrill 军事训练登记簿
     * @return 结果
     */
    @Override
    public int updateBdglDrill(BdglDrill bdglDrill)
    {

        return bdglDrillMapper.updateBdglDrill(bdglDrill);
    }

    /**
     * 批量删除军事训练登记簿
     * 
     * @param ids 需要删除的军事训练登记簿主键
     * @return 结果
     */
    @Override
    public int deleteBdglDrillByIds(Integer[] ids)
    {
        return bdglDrillMapper.deleteBdglDrillByIds(ids);
    }

    /**
     * 删除军事训练登记簿信息
     * 
     * @param id 军事训练登记簿主键
     * @return 结果
     */
    @Override
    public int deleteBdglDrillById(Integer id)
    {
        return bdglDrillMapper.deleteBdglDrillById(id);
    }
    /**
     * 领导查看军事训练登记簿列表
     */
    @Override
    public List<SysDeptDto> selectDrillList(String year) {
        //所有营级单位
        List<Map<String, SysDept>> mapList = deptMapper.selectAllCamp();
        //根据营级单位ID查询当前单位下是否有数据
        List<SysDeptDto> dtoList = new ArrayList<>();
        mapList.forEach(item -> {
            SysDeptDto deptDto = BeanUtil.mapToBean(item, SysDeptDto.class, false);
            int deptId = deptDto.getDeptId().intValue();
            int countByUnitId = bdglDrillMapper.selectlDrillCountByunitId(deptId,year);
            if (countByUnitId > 0) {
                dtoList.add(deptDto);
            }
        });
        return dtoList;
    }
    /**
     * 获取军事训练登记簿详细信息单位下的所有信息
     */
    @Override
    public List<BdglDrill> selectDrillByUnitId(Integer unitId,String year) {
        return bdglDrillMapper.selectDrillByUnitId(unitId,year);
    }
}
