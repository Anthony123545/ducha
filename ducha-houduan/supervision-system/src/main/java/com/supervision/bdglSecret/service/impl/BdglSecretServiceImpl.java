package com.supervision.bdglSecret.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.bdglSecret.mapper.BdglSecretMapper;
import com.supervision.bdglSecret.domain.BdglSecret;
import com.supervision.bdglSecret.service.IBdglSecretService;

/**
 * 保密设备外借记录Service业务层处理
 *
 * @author supervision
 * @date 2022-03-25
 */
@Service
public class BdglSecretServiceImpl implements IBdglSecretService
{
    @Autowired
    private BdglSecretMapper bdglSecretMapper;

    /**
     * 查询保密设备外借记录
     *
     * @param id 保密设备外借记录主键
     * @return 保密设备外借记录
     */
    @Override
    public BdglSecret selectBdglSecretById(Integer id)
    {
        return bdglSecretMapper.selectBdglSecretById(id);
    }

    /**
     * 查询保密设备外借记录列表
     *
     * @param bdglSecret 保密设备外借记录
     * @return 保密设备外借记录
     */
    @Override
    public List<BdglSecret> selectBdglSecretList(BdglSecret bdglSecret)
    {
        return bdglSecretMapper.selectBdglSecretList(bdglSecret);
    }

    /**
     * 新增保密设备外借记录
     *
     * @param bdglSecret 保密设备外借记录
     * @return 结果
     */
    @Override
    public int insertBdglSecret(BdglSecret bdglSecret)
    {
        return bdglSecretMapper.insertBdglSecret(bdglSecret);
    }

    /**
     * 修改保密设备外借记录
     *
     * @param bdglSecret 保密设备外借记录
     * @return 结果
     */
    @Override
    public int updateBdglSecret(BdglSecret bdglSecret)
    {
        return bdglSecretMapper.updateBdglSecret(bdglSecret);
    }

    @Override
    public int updateStatus(Integer id, String status)
    {
        return bdglSecretMapper.updateStatus(id, status);
    }

    /**
     * 批量删除保密设备外借记录
     *
     * @param ids 需要删除的保密设备外借记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglSecretByIds(Integer[] ids)
    {
        return bdglSecretMapper.deleteBdglSecretByIds(ids);
    }

    /**
     * 删除保密设备外借记录信息
     *
     * @param id 保密设备外借记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglSecretById(Integer id)
    {
        return bdglSecretMapper.deleteBdglSecretById(id);
    }

    @Override
    public List<BdglSecret> selectBdglSecretNumber() {
        return bdglSecretMapper.selectBdglSecretNumber();
    }
}
