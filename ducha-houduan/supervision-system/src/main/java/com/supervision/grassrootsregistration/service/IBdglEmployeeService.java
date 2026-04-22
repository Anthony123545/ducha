package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglEmployee;

/**
 * 门禁人员信息Service接口
 * 
 * @author ruoyi
 * @date 2023-03-13
 */
public interface IBdglEmployeeService 
{
    /**
     * 查询门禁人员信息
     * 
     * @param pin 门禁人员信息主键
     * @return 门禁人员信息
     */
    public BdglEmployee selectBdglEmployeeByPin(Long pin);

    /**
     * 查询门禁人员信息列表
     * 
     * @param bdglEmployee 门禁人员信息
     * @return 门禁人员信息集合
     */
    public List<BdglEmployee> selectBdglEmployeeList(BdglEmployee bdglEmployee);

    /**
     * 新增门禁人员信息
     * 
     * @param bdglEmployee 门禁人员信息
     * @return 结果
     */
    public int insertBdglEmployee(BdglEmployee bdglEmployee);

    /**
     * 修改门禁人员信息
     * 
     * @param bdglEmployee 门禁人员信息
     * @return 结果
     */
    public int updateBdglEmployee(BdglEmployee bdglEmployee);

    /**
     * 批量删除门禁人员信息
     * 
     * @param pins 需要删除的门禁人员信息主键集合
     * @return 结果
     */
    public int deleteBdglEmployeeByPins(Long[] pins);

    /**
     * 删除门禁人员信息信息
     * 
     * @param pin 门禁人员信息主键
     * @return 结果
     */
    public int deleteBdglEmployeeByPin(Long pin);
}
