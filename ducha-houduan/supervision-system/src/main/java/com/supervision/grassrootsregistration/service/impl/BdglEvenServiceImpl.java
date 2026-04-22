package com.supervision.grassrootsregistration.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration .mapper.BdglEvenMapper;
import com.supervision.grassrootsregistration .domain.BdglEven;
import com.supervision.grassrootsregistration .service.IBdglEvenService;

/**
 * 连务会记录本Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-26
 */
@Service
public class BdglEvenServiceImpl implements IBdglEvenService 
{
    @Autowired
    private BdglEvenMapper bdglEvenMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询连务会记录本
     * 
     * @param id 连务会记录本主键
     * @return 连务会记录本
     */
    @Override
    public BdglEven selectBdglEvenById(Integer id)
    {
        return bdglEvenMapper.selectBdglEvenById(id);
    }

    /**
     * 查询连务会记录本列表
     * 
     * @param bdglEven 连务会记录本
     * @return 连务会记录本
     */
    @Override
    public List<BdglEven> selectBdglEvenList(BdglEven bdglEven)
    {
        return bdglEvenMapper.selectBdglEvenList(bdglEven);
    }

    /**
     * 新增连务会记录本
     * 
     * @param bdglEven 连务会记录本
     * @return 结果
     */
    @Override
    public int insertBdglEven(BdglEven bdglEven)
    {
        if(bdglEven.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglEven.getUnitId()));
            bdglEven.setUnitName(sysDept.getDeptName());
        }
        bdglEven.setCreatetime(DateUtils.getNowDate());
        return bdglEvenMapper.insertBdglEven(bdglEven);
    }

    /**
     * 修改连务会记录本
     * 
     * @param bdglEven 连务会记录本
     * @return 结果
     */
    @Override
    public int updateBdglEven(BdglEven bdglEven)
    {
        if(bdglEven.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglEven.getUnitId()));
            bdglEven.setUnitName(sysDept.getDeptName());
        }
        bdglEven.setUpdatetime(DateUtils.getNowDate());
        return bdglEvenMapper.updateBdglEven(bdglEven);
    }

    /**
     * 批量删除连务会记录本
     * 
     * @param ids 需要删除的连务会记录本主键
     * @return 结果
     */
    @Override
    public int deleteBdglEvenByIds(Integer[] ids)
    {
        return bdglEvenMapper.deleteBdglEvenByIds(ids);
    }

    /**
     * 删除连务会记录本信息
     * 
     * @param id 连务会记录本主键
     * @return 结果
     */
    @Override
    public int deleteBdglEvenById(Integer id)
    {
        return bdglEvenMapper.deleteBdglEvenById(id);
    }
    /**
     * 获取连务会记录本列表
     * */
    @Override
    public List<BdglEven> selectBdglEvenLists(BdglEven bdglEven) {
        return bdglEvenMapper.selectBdglEvenLists(bdglEven);
    }
    
    @Override
    public List<SysDeptDto> getMeetRecordList(String year) {
        //所有营级单位
        List<Map<String, SysDept>> mapList = deptMapper.selectAllCamp();
        //根据营级单位ID查询当前单位下是否有数据
        List<SysDeptDto> dtoList = new ArrayList<>();
        mapList.forEach(item -> {
            SysDeptDto deptDto = BeanUtil.mapToBean(item, SysDeptDto.class, false);
            int deptId = deptDto.getDeptId().intValue();
            int countByunitId = bdglEvenMapper.selectlMeetRecordByunitId(deptId,year);
            if (countByunitId > 0) {
                dtoList.add(deptDto);
            }
        });
        return dtoList;
    }
    
    /**
     * 领导查询连务会记录本列表s
     */
    @Override
    public List<BdglEven> getMeetRecordOnUnitList(Integer unitId,String year) {
        return bdglEvenMapper.selectMeetRecordOnUnitList(unitId,year);
    }
}
