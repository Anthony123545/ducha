package com.supervision.militaryvehicleManagement.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.supervision.militaryvehicleManagement.domain.BdglThebus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.militaryvehicleManagement.mapper.BdglPrivateCarMapper;
import com.supervision.militaryvehicleManagement.domain.BdglPrivateCar;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateCarService;

/**
 * 私家车管理Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-04
 */
@Service
public class BdglPrivateCarServiceImpl implements IBdglPrivateCarService 
{
    @Autowired
    private BdglPrivateCarMapper bdglPrivateCarMapper;

    /**
     * 查询私家车管理
     * 
     * @param id 私家车管理主键
     * @return 私家车管理
     */
    @Override
    public BdglPrivateCar selectBdglPrivateCarById(Long id)
    {
        return bdglPrivateCarMapper.selectBdglPrivateCarById(id);
    }

    /**
     * 查询私家车管理列表
     * 
     * @param bdglPrivateCar 私家车管理
     * @return 私家车管理
     */
    @Override
    public List<BdglPrivateCar> selectBdglPrivateCarList(BdglPrivateCar bdglPrivateCar)
    {
        return bdglPrivateCarMapper.selectBdglPrivateCarList(bdglPrivateCar);
    }

    /**
     * 新增私家车管理
     * 
     * @param bdglPrivateCar 私家车管理
     * @return 结果
     */
    @Override
    public int insertBdglPrivateCar(BdglPrivateCar bdglPrivateCar)
    {
        return bdglPrivateCarMapper.insertBdglPrivateCar(bdglPrivateCar);
    }

    /**
     * 修改私家车管理
     * 
     * @param bdglPrivateCar 私家车管理
     * @return 结果
     */
    @Override
    public int updateBdglPrivateCar(BdglPrivateCar bdglPrivateCar)
    {
        return bdglPrivateCarMapper.updateBdglPrivateCar(bdglPrivateCar);
    }

    /**
     * 批量删除私家车管理
     * 
     * @param ids 需要删除的私家车管理主键
     * @return 结果
     */
    @Override
    public int deleteBdglPrivateCarByIds(Long[] ids)
    {
        return bdglPrivateCarMapper.deleteBdglPrivateCarByIds(ids);
    }

    /**
     * 删除私家车管理信息
     * 
     * @param id 私家车管理主键
     * @return 结果
     */
    @Override
    public int deleteBdglPrivateCarById(Long id)
    {
        return bdglPrivateCarMapper.deleteBdglPrivateCarById(id);
    }

    @Override
    public void updateBdglPrivateCarBYvehiclenumber(BdglPrivateCar bdglPrivateCar) {
        bdglPrivateCarMapper.updateBdglPrivateCarBYvehiclenumber(bdglPrivateCar);
    }

    @Override
    public List<BdglPrivateCar> selectBdglPrivateCarLists(BdglPrivateCar bdglPrivateCar) {
        return bdglPrivateCarMapper.selectBdglPrivateCarLists(bdglPrivateCar);
    }

    @Override
    public List<Map<String, Object>> selectVehicleModelList() {
        return bdglPrivateCarMapper.selectVehicleModelList();
    }

    @Override
    public BdglPrivateCar selectBdglPrivateCarByVehicleNumber(String license) {
        return bdglPrivateCarMapper.selectBdglPrivateCarByVehicleNumber(license);
    }
}
