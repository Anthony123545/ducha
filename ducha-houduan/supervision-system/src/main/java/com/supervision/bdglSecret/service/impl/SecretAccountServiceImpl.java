package com.supervision.bdglSecret.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.bdglSecret.mapper.SecretAccountMapper;
import com.supervision.bdglSecret.domain.SecretAccount;
import com.supervision.bdglSecret.service.ISecretAccountService;

/**
 * 涉密计算机台账Service业务层处理
 * 
 * @author lanse
 * @date 2022-10-30
 */
@Service
public class SecretAccountServiceImpl implements ISecretAccountService 
{
    @Autowired
    private SecretAccountMapper secretAccountMapper;

    /**
     * 查询涉密计算机台账
     * 
     * @param id 涉密计算机台账主键
     * @return 涉密计算机台账
     */
    @Override
    public SecretAccount selectSecretAccountById(Long id)
    {
        return secretAccountMapper.selectSecretAccountById(id);
    }

    /**
     * 查询涉密计算机台账列表
     * 
     * @param secretAccount 涉密计算机台账
     * @return 涉密计算机台账
     */
    @Override
    public List<SecretAccount> selectSecretAccountList(SecretAccount secretAccount)
    {
        return secretAccountMapper.selectSecretAccountList(secretAccount);
    }

    /**
     * 新增涉密计算机台账
     * 
     * @param secretAccount 涉密计算机台账
     * @return 结果
     */
    @Override
    public int insertSecretAccount(SecretAccount secretAccount)
    {
        return secretAccountMapper.insertSecretAccount(secretAccount);
    }

    /**
     * 修改涉密计算机台账
     * 
     * @param secretAccount 涉密计算机台账
     * @return 结果
     */
    @Override
    public int updateSecretAccount(SecretAccount secretAccount)
    {
        return secretAccountMapper.updateSecretAccount(secretAccount);
    }

    /**
     * 批量删除涉密计算机台账
     * 
     * @param ids 需要删除的涉密计算机台账主键
     * @return 结果
     */
    @Override
    public int deleteSecretAccountByIds(Long[] ids)
    {
        return secretAccountMapper.deleteSecretAccountByIds(ids);
    }

    /**
     * 删除涉密计算机台账信息
     * 
     * @param id 涉密计算机台账主键
     * @return 结果
     */
    @Override
    public int deleteSecretAccountById(Long id)
    {
        return secretAccountMapper.deleteSecretAccountById(id);
    }
}
