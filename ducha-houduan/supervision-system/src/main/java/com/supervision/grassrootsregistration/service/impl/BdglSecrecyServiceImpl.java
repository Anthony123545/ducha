package com.supervision.grassrootsregistration.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.utils.DateUtils;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration .mapper.BdglSecrecyMapper;
import com.supervision.grassrootsregistration .domain.BdglSecrecy;
import com.supervision.grassrootsregistration .service.IBdglSecrecyService;

/**
 * 网络/手机/涉密载体登记本Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-26
 */
@Service
public class BdglSecrecyServiceImpl implements IBdglSecrecyService 
{
    @Autowired
    private BdglSecrecyMapper bdglSecrecyMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询网络/手机/涉密载体登记本
     * 
     * @param id 网络/手机/涉密载体登记本主键
     * @return 网络/手机/涉密载体登记本
     */
    @Override
    public BdglSecrecy selectBdglSecrecyById(Integer id)
    {
        return bdglSecrecyMapper.selectBdglSecrecyById(id);
    }

    @Override
    public List<BdglSecrecy> selectBdglSecrecyBunitId(Integer unitid,String year) {
        return bdglSecrecyMapper.selectBdglSecrecyBunitId(unitid,year);
    }

    /**
     * 查询网络/手机/涉密载体登记本列表
     * 
     * @param bdglSecrecy 网络/手机/涉密载体登记本
     * @return 网络/手机/涉密载体登记本
     */
    @Override
    public List<BdglSecrecy> selectBdglSecrecyList(BdglSecrecy bdglSecrecy)
    {
        return bdglSecrecyMapper.selectBdglSecrecyList(bdglSecrecy);
    }

    /**
     * 新增网络/手机/涉密载体登记本
     * 
     * @param bdglSecrecy 网络/手机/涉密载体登记本
     * @return 结果
     */
    @Override
    public int insertBdglSecrecy(BdglSecrecy bdglSecrecy)
    {
        if(bdglSecrecy.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglSecrecy.getUnitId()));
            bdglSecrecy.setDeptName(sysDept.getDeptName());
        }
        bdglSecrecy.setCreatetime(DateUtils.getNowDate());
        return bdglSecrecyMapper.insertBdglSecrecy(bdglSecrecy);
    }

    /**
     * 修改网络/手机/涉密载体登记本
     * 
     * @param bdglSecrecy 网络/手机/涉密载体登记本
     * @return 结果
     */
    @Override
    public int updateBdglSecrecy(BdglSecrecy bdglSecrecy)
    {
        if(bdglSecrecy.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglSecrecy.getUnitId()));
            bdglSecrecy.setDeptName(sysDept.getDeptName());
        }
        return bdglSecrecyMapper.updateBdglSecrecy(bdglSecrecy);
    }

    /**
     * 批量删除网络/手机/涉密载体登记本
     * 
     * @param ids 需要删除的网络/手机/涉密载体登记本主键
     * @return 结果
     */
    @Override
    public int deleteBdglSecrecyByIds(Integer[] ids)
    {
        return bdglSecrecyMapper.deleteBdglSecrecyByIds(ids);
    }

    /**
     * 删除网络/手机/涉密载体登记本信息
     * 
     * @param id 网络/手机/涉密载体登记本主键
     * @return 结果
     */
    @Override
    public int deleteBdglSecrecyById(Integer id)
    {
        return bdglSecrecyMapper.deleteBdglSecrecyById(id);
    }
    
    /**
     * 领导查看网络/手机/涉密载体登记本
     * @return
     */
    @Override
    public List<SysDeptDto> selectSecrecyStatsList(String year) {
        //所有营级单位
        List<Map<String, SysDept>> mapList = deptMapper.selectAllCamp();
        //根据营级单位ID查询当前单位下是否有数据
        List<SysDeptDto> dtoList = new ArrayList<>();
        mapList.forEach(item -> {
            SysDeptDto deptDto = BeanUtil.mapToBean(item, SysDeptDto.class, false);
            int deptId = deptDto.getDeptId().intValue();
            //查看是否有数据存在
            int countByUnitId = bdglSecrecyMapper.selectlSecrecyCountByunitId(deptId,year);
            if (countByUnitId > 0) {
                //查询启用时间
                List<BdglSecrecy> timeList = bdglSecrecyMapper.selectlSecrecyStartTimeByunitId(deptId,year);
                if (timeList != null && timeList.size() >0){
                    timeList.forEach(time->{
                        String format = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, time.getStartTime());
                        deptDto.setStartTime(format);
                    });
                }
                dtoList.add(deptDto);
            }
        });
        return dtoList;
    }
    
}
