package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration .domain.BdglSecrecy;
import org.apache.ibatis.annotations.Param;

/**
 * 网络/手机/涉密载体登记本Mapper接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface BdglSecrecyMapper 
{
    /**
     * 查询网络/手机/涉密载体登记本
     * 
     * @param id 网络/手机/涉密载体登记本主键
     * @return 网络/手机/涉密载体登记本
     */
    public BdglSecrecy selectBdglSecrecyById(Integer id);

    public List<BdglSecrecy> selectBdglSecrecyBunitId(@Param("unitId") Integer unitId, @Param("year") String year);
    /**
     * 查询网络/手机/涉密载体登记本列表
     * 
     * @param bdglSecrecy 网络/手机/涉密载体登记本
     * @return 网络/手机/涉密载体登记本集合
     */
    public List<BdglSecrecy> selectBdglSecrecyList(BdglSecrecy bdglSecrecy);

    /**
     * 新增网络/手机/涉密载体登记本
     * 
     * @param bdglSecrecy 网络/手机/涉密载体登记本
     * @return 结果
     */
    public int insertBdglSecrecy(BdglSecrecy bdglSecrecy);

    /**
     * 修改网络/手机/涉密载体登记本
     * 
     * @param bdglSecrecy 网络/手机/涉密载体登记本
     * @return 结果
     */
    public int updateBdglSecrecy(BdglSecrecy bdglSecrecy);

    /**
     * 删除网络/手机/涉密载体登记本
     * 
     * @param id 网络/手机/涉密载体登记本主键
     * @return 结果
     */
    public int deleteBdglSecrecyById(Integer id);

    /**
     * 批量删除网络/手机/涉密载体登记本
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglSecrecyByIds(Integer[] ids);
    
    int selectlSecrecyCountByunitId(@Param("unitId") int unitId, @Param("year") String year);
    
    List<BdglSecrecy> selectlSecrecyStartTimeByunitId(@Param("unitId") int unitId, @Param("year") String year);
}
