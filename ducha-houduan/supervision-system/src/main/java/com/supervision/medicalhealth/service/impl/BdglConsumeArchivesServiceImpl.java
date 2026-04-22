package com.supervision.medicalhealth.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglConsumeArchivesMapper;
import com.supervision.medicalhealth.domain.BdglConsumeArchives;
import com.supervision.medicalhealth.service.IBdglConsumeArchivesService;

/**
 * 耗材库存档案Service业务层处理
 * 
 * @author hr
 * @date 2022-02-12
 */
@Service
public class BdglConsumeArchivesServiceImpl implements IBdglConsumeArchivesService 
{
    @Autowired
    private BdglConsumeArchivesMapper bdglConsumeArchivesMapper;

    /**
     * 查询耗材库存档案
     * 
     * @param id 耗材库存档案主键
     * @return 耗材库存档案
     */
    @Override
    public BdglConsumeArchives selectBdglConsumeArchivesById(Long id)
    {
        return bdglConsumeArchivesMapper.selectBdglConsumeArchivesById(id);
    }

    /**
     * 查询耗材库存档案列表
     * 
     * @param bdglConsumeArchives 耗材库存档案
     * @return 耗材库存档案
     */
    @Override
    public List<BdglConsumeArchives> selectBdglConsumeArchivesList(BdglConsumeArchives bdglConsumeArchives)
    {
        return bdglConsumeArchivesMapper.selectBdglConsumeArchivesList(bdglConsumeArchives);
    }

    /**
     * 新增耗材库存档案
     * 
     * @param bdglConsumeArchives 耗材库存档案
     * @return 结果
     */
    @Override
    public int insertBdglConsumeArchives(BdglConsumeArchives bdglConsumeArchives)
    {
        bdglConsumeArchives.setCreateTime(DateUtils.getNowDate());
        return bdglConsumeArchivesMapper.insertBdglConsumeArchives(bdglConsumeArchives);
    }

    /**
     * 修改耗材库存档案
     * 
     * @param bdglConsumeArchives 耗材库存档案
     * @return 结果
     */
    @Override
    public int updateBdglConsumeArchives(BdglConsumeArchives bdglConsumeArchives)
    {
        bdglConsumeArchives.setUpdateTime(DateUtils.getNowDate());
          return bdglConsumeArchivesMapper.updateBdglConsumeArchives(bdglConsumeArchives);
    }

    /**
     * 批量删除耗材库存档案
     * 
     * @param ids 需要删除的耗材库存档案主键
     * @return 结果
     */
    @Override
    public int deleteBdglConsumeArchivesByIds(Long[] ids)
    {
        return bdglConsumeArchivesMapper.deleteBdglConsumeArchivesByIds(ids);
    }

    /**
     * 删除耗材库存档案信息
     * 
     * @param id 耗材库存档案主键
     * @return 结果
     */
    @Override
    public int deleteBdglConsumeArchivesById(Long id)
    {
        return bdglConsumeArchivesMapper.deleteBdglConsumeArchivesById(id);
    }
}
