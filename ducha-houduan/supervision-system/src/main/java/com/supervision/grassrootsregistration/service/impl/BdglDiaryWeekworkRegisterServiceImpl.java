package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglDiaryWeekworkRegisterMapper;
import com.supervision.grassrootsregistration.domain.BdglDiaryWeekworkRegister;
import com.supervision.grassrootsregistration.service.IBdglDiaryWeekworkRegisterService;

/**
 * 要事日记一周工作内容Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-31
 */
@Service
public class BdglDiaryWeekworkRegisterServiceImpl implements IBdglDiaryWeekworkRegisterService 
{
    @Autowired
    private BdglDiaryWeekworkRegisterMapper bdglDiaryWeekworkRegisterMapper;

    /**
     * 查询要事日记一周工作内容
     * 
     * @param id 要事日记一周工作内容主键
     * @return 要事日记一周工作内容
     */
    @Override
    public BdglDiaryWeekworkRegister selectBdglDiaryWeekworkRegisterById(Long id)
    {
        return bdglDiaryWeekworkRegisterMapper.selectBdglDiaryWeekworkRegisterById(id);
    }

    /**
     * 查询要事日记一周工作内容列表
     * 
     * @param bdglDiaryWeekworkRegister 要事日记一周工作内容
     * @return 要事日记一周工作内容
     */
    @Override
    public List<BdglDiaryWeekworkRegister> selectBdglDiaryWeekworkRegisterList(BdglDiaryWeekworkRegister bdglDiaryWeekworkRegister)
    {
        return bdglDiaryWeekworkRegisterMapper.selectBdglDiaryWeekworkRegisterList(bdglDiaryWeekworkRegister);
    }

    /**
     * 新增要事日记一周工作内容
     * 
     * @param bdglDiaryWeekworkRegister 要事日记一周工作内容
     * @return 结果
     */
    @Override
    public int insertBdglDiaryWeekworkRegister(BdglDiaryWeekworkRegister bdglDiaryWeekworkRegister)
    {
        bdglDiaryWeekworkRegister.setCreateTime(DateUtils.getNowDate());
        return bdglDiaryWeekworkRegisterMapper.insertBdglDiaryWeekworkRegister(bdglDiaryWeekworkRegister);
    }

    /**
     * 修改要事日记一周工作内容
     * 
     * @param bdglDiaryWeekworkRegister 要事日记一周工作内容
     * @return 结果
     */
    @Override
    public int updateBdglDiaryWeekworkRegister(BdglDiaryWeekworkRegister bdglDiaryWeekworkRegister)
    {
        return bdglDiaryWeekworkRegisterMapper.updateBdglDiaryWeekworkRegister(bdglDiaryWeekworkRegister);
    }

    /**
     * 批量删除要事日记一周工作内容
     * 
     * @param ids 需要删除的要事日记一周工作内容主键
     * @return 结果
     */
    @Override
    public int deleteBdglDiaryWeekworkRegisterByIds(Long[] ids)
    {
        return bdglDiaryWeekworkRegisterMapper.deleteBdglDiaryWeekworkRegisterByIds(ids);
    }

    /**
     * 删除要事日记一周工作内容信息
     * 
     * @param id 要事日记一周工作内容主键
     * @return 结果
     */
    @Override
    public int deleteBdglDiaryWeekworkRegisterById(Long id)
    {
        return bdglDiaryWeekworkRegisterMapper.deleteBdglDiaryWeekworkRegisterById(id);
    }
}
