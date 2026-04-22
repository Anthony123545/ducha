package com.supervision.militaryvehicleManagement.mapper;

import java.util.List;
import java.util.Map;

import com.supervision.militaryvehicleManagement.domain.BdglPrivateCar;
import com.supervision.militaryvehicleManagement.domain.BdglThebus;
import org.apache.ibatis.annotations.Select;

/**
 * 私家车管理Mapper接口
 * 
 * @author supervision
 * @date 2022-03-04
 */
public interface BdglPrivateCarMapper 
{
    /**
     * 查询私家车管理
     * 
     * @param id 私家车管理主键
     * @return 私家车管理
     */
    public BdglPrivateCar selectBdglPrivateCarById(Long id);

    /**
     * 查询私家车管理列表
     * 
     * @param bdglPrivateCar 私家车管理
     * @return 私家车管理集合
     */
    public List<BdglPrivateCar> selectBdglPrivateCarList(BdglPrivateCar bdglPrivateCar);

    /**
     * 新增私家车管理
     * 
     * @param bdglPrivateCar 私家车管理
     * @return 结果
     */
    public int insertBdglPrivateCar(BdglPrivateCar bdglPrivateCar);

    /**
     * 修改私家车管理
     * 
     * @param bdglPrivateCar 私家车管理
     * @return 结果
     */
    public int updateBdglPrivateCar(BdglPrivateCar bdglPrivateCar);

    /**
     * 删除私家车管理
     * 
     * @param id 私家车管理主键
     * @return 结果
     */
    public int deleteBdglPrivateCarById(Long id);

    /**
     * 批量删除私家车管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglPrivateCarByIds(Long[] ids);

    void updateBdglPrivateCarBYvehiclenumber(BdglPrivateCar bdglPrivateCar);

    List<BdglPrivateCar> selectBdglPrivateCarLists(BdglPrivateCar bdglPrivateCar);

    List<Map<String, Object>> selectVehicleModelList();

    @Select("select * from bdgl_private_car where license=#{license}")
    BdglPrivateCar selectBdglPrivateCarByVehicleNumber(String license);
}
