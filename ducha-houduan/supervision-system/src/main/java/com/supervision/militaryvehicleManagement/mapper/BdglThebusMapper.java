package com.supervision.militaryvehicleManagement.mapper;

import java.util.List;
import java.util.Map;

import com.supervision.militaryvehicleManagement.domain.BdglThebus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 军车管理Mapper接口
 *
 * @author supervision
 * @date 2022-03-09
 */
public interface BdglThebusMapper
{
    /**
     * 查询军车管理
     *
     * @param id 军车管理主键
     * @return 军车管理
     */
    public BdglThebus selectBdglThebusById(Long id);

    /**
     * 查询军车管理列表
     *
     * @param bdglThebus 军车管理
     * @return 军车管理集合
     */
    public List<BdglThebus> selectBdglThebusList(BdglThebus bdglThebus);

    /**
     * 新增军车管理
     *
     * @param bdglThebus 军车管理
     * @return 结果
     */
    public int insertBdglThebus(BdglThebus bdglThebus);

    /**
     * 修改军车管理
     *
     * @param bdglThebus 军车管理
     * @return 结果
     */
    public int updateBdglThebus(BdglThebus bdglThebus);

    /**
     * 删除军车管理
     *
     * @param id 军车管理主键
     * @return 结果
     */
    public int deleteBdglThebusById(Long id);

    /**
     * 批量删除军车管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglThebusByIds(Long[] ids);

    /**
     * 批量更新车辆状态
     * @return
     */
    public int updateBatchForStatusByIdList(@Param("idList") List<Long> idList);

    List<Map<String, Object>> selectVehicleModelList();

    @Select("select * from bdgl_thebus where license=#{license}")
    BdglThebus selectBdglThebusByVehicleNumber(String license);
}
