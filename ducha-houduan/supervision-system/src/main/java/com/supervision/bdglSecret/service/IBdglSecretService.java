package com.supervision.bdglSecret.service;

import java.util.List;
import com.supervision.bdglSecret.domain.BdglSecret;

/**
 * 保密设备外借记录Service接口
 *
 * @author supervision
 * @date 2022-03-25
 */
public interface IBdglSecretService
{
    /**
     * 查询保密设备外借记录
     *
     * @param id 保密设备外借记录主键
     * @return 保密设备外借记录
     */
    public BdglSecret selectBdglSecretById(Integer id);

    /**
     * 查询保密设备外借记录列表
     *
     * @param bdglSecret 保密设备外借记录
     * @return 保密设备外借记录集合
     */
    public List<BdglSecret> selectBdglSecretList(BdglSecret bdglSecret);

    /**
     * 新增保密设备外借记录
     *
     * @param bdglSecret 保密设备外借记录
     * @return 结果
     */
    public int insertBdglSecret(BdglSecret bdglSecret);

    /**
     * 修改保密设备外借记录
     *
     * @param bdglSecret 保密设备外借记录
     * @return 结果
     */
    public int updateBdglSecret(BdglSecret bdglSecret);
    public int updateStatus(Integer id, String status);
    /**
     * 批量删除保密设备外借记录
     *
     * @param ids 需要删除的保密设备外借记录主键集合
     * @return 结果
     */
    public int deleteBdglSecretByIds(Integer[] ids);

    /**
     * 删除保密设备外借记录信息
     *
     * @param id 保密设备外借记录主键
     * @return 结果
     */
    public int deleteBdglSecretById(Integer id);

    List<BdglSecret> selectBdglSecretNumber();

}
