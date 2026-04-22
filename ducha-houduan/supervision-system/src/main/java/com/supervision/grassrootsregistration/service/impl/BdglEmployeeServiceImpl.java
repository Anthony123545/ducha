package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglEmployeeMapper;
import com.supervision.grassrootsregistration.domain.BdglEmployee;
import com.supervision.grassrootsregistration.service.IBdglEmployeeService;

/**
 * 门禁人员信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-13
 */
@Service
public class BdglEmployeeServiceImpl implements IBdglEmployeeService 
{
    @Autowired
    private BdglEmployeeMapper bdglEmployeeMapper;

    /**
     * 查询门禁人员信息
     * 
     * @param pin 门禁人员信息主键
     * @return 门禁人员信息
     */
    @Override
    public BdglEmployee selectBdglEmployeeByPin(Long pin)
    {
        return bdglEmployeeMapper.selectBdglEmployeeByPin(pin);
    }

    /**
     * 查询门禁人员信息列表
     * 
     * @param bdglEmployee 门禁人员信息
     * @return 门禁人员信息
     */
    @Override
    public List<BdglEmployee> selectBdglEmployeeList(BdglEmployee bdglEmployee)
    {
        return bdglEmployeeMapper.selectBdglEmployeeList(bdglEmployee);
    }

    /**
     * 新增门禁人员信息
     * 
     * @param bdglEmployee 门禁人员信息
     * @return 结果
     */
    @Override
    public int insertBdglEmployee(BdglEmployee bdglEmployee)
    {
        return bdglEmployeeMapper.insertBdglEmployee(bdglEmployee);
    }

    /**
     * 修改门禁人员信息
     * 
     * @param bdglEmployee 门禁人员信息
     * @return 结果
     */
    @Override
    public int updateBdglEmployee(BdglEmployee bdglEmployee)
    {
        return bdglEmployeeMapper.updateBdglEmployee(bdglEmployee);
    }

    /**
     * 批量删除门禁人员信息
     * 
     * @param pins 需要删除的门禁人员信息主键
     * @return 结果
     */
    @Override
    public int deleteBdglEmployeeByPins(Long[] pins)
    {
        return bdglEmployeeMapper.deleteBdglEmployeeByPins(pins);
    }

    /**
     * 删除门禁人员信息信息
     * 
     * @param pin 门禁人员信息主键
     * @return 结果
     */
    @Override
    public int deleteBdglEmployeeByPin(Long pin)
    {
        return bdglEmployeeMapper.deleteBdglEmployeeByPin(pin);
    }
}
