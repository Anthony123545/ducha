package com.supervision.bdglSecret.mapper;

import java.util.List;
import com.supervision.bdglSecret.domain.SecretAccount;

/**
 * 涉密计算机台账Mapper接口
 * 
 * @author lanse
 * @date 2022-10-30
 */
public interface SecretAccountMapper 
{
    /**
     * 查询涉密计算机台账
     * 
     * @param id 涉密计算机台账主键
     * @return 涉密计算机台账
     */
    public SecretAccount selectSecretAccountById(Long id);

    /**
     * 查询涉密计算机台账列表
     * 
     * @param secretAccount 涉密计算机台账
     * @return 涉密计算机台账集合
     */
    public List<SecretAccount> selectSecretAccountList(SecretAccount secretAccount);

    /**
     * 新增涉密计算机台账
     * 
     * @param secretAccount 涉密计算机台账
     * @return 结果
     */
    public int insertSecretAccount(SecretAccount secretAccount);

    /**
     * 修改涉密计算机台账
     * 
     * @param secretAccount 涉密计算机台账
     * @return 结果
     */
    public int updateSecretAccount(SecretAccount secretAccount);

    /**
     * 删除涉密计算机台账
     * 
     * @param id 涉密计算机台账主键
     * @return 结果
     */
    public int deleteSecretAccountById(Long id);

    /**
     * 批量删除涉密计算机台账
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecretAccountByIds(Long[] ids);
}
