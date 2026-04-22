package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglWeekworkRegisterMapper;
import com.supervision.grassrootsregistration.domain.BdglWeekworkRegister;
import com.supervision.grassrootsregistration.service.IBdglWeekworkRegisterService;

/**
 * 一周安排工作分Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-28
 */
@Service
public class BdglWeekworkRegisterServiceImpl implements IBdglWeekworkRegisterService 
{
    @Autowired
    private BdglWeekworkRegisterMapper bdglWeekworkRegisterMapper;

    /**
     * 查询一周安排工作分
     * 
     * @param id 一周安排工作分主键
     * @return 一周安排工作分
     */
    @Override
    public BdglWeekworkRegister selectBdglWeekworkRegisterById(Long id)
    {
        return bdglWeekworkRegisterMapper.selectBdglWeekworkRegisterById(id);
    }

    /**
     * 查询一周安排工作分列表
     * 
     * @param bdglWeekworkRegister 一周安排工作分
     * @return 一周安排工作分
     */
    @Override
    public List<BdglWeekworkRegister> selectBdglWeekworkRegisterList(BdglWeekworkRegister bdglWeekworkRegister)
    {
        return bdglWeekworkRegisterMapper.selectBdglWeekworkRegisterList(bdglWeekworkRegister);
    }

    /**
     * 新增一周安排工作分
     * 
     * @param bdglWeekworkRegister 一周安排工作分
     * @return 结果
     */
    @Override
    public int insertBdglWeekworkRegister(BdglWeekworkRegister bdglWeekworkRegister)
    {
        return bdglWeekworkRegisterMapper.insertBdglWeekworkRegister(bdglWeekworkRegister);
    }

    /**
     * 修改一周安排工作分
     * 
     * @param bdglWeekworkRegister 一周安排工作分
     * @return 结果
     */
    @Override
    public int updateBdglWeekworkRegister(BdglWeekworkRegister bdglWeekworkRegister)
    {
        return bdglWeekworkRegisterMapper.updateBdglWeekworkRegister(bdglWeekworkRegister);
    }

    /**
     * 批量删除一周安排工作分
     * 
     * @param ids 需要删除的一周安排工作分主键
     * @return 结果
     */
    @Override
    public int deleteBdglWeekworkRegisterByIds(Long[] ids)
    {
        return bdglWeekworkRegisterMapper.deleteBdglWeekworkRegisterByIds(ids);
    }

    /**
     * 删除一周安排工作分信息
     * 
     * @param id 一周安排工作分主键
     * @return 结果
     */
    @Override
    public int deleteBdglWeekworkRegisterById(Long id)
    {
        return bdglWeekworkRegisterMapper.deleteBdglWeekworkRegisterById(id);
    }

    @Override
    public List<BdglWeekworkRegister> selectBdglWeekworkRegisterLists(BdglWeekworkRegister bdglWeekworkRegister) {
        return bdglWeekworkRegisterMapper.selectBdglWeekworkRegisterLists(bdglWeekworkRegister);

    }
}
