package com.supervision.medicalhealth.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglAntiepidemicArchivesMapper;
import com.supervision.medicalhealth.domain.BdglAntiepidemicArchives;
import com.supervision.medicalhealth.service.IBdglAntiepidemicArchivesService;

/**
 * 防疫用品库存档案Service业务层处理
 * 
 * @author hr
 * @date 2022-02-14
 */
@Service
public class BdglAntiepidemicArchivesServiceImpl implements IBdglAntiepidemicArchivesService 
{
    @Autowired
    private BdglAntiepidemicArchivesMapper bdglAntiepidemicArchivesMapper;

    /**
     * 查询防疫用品库存档案
     * 
     * @param id 防疫用品库存档案主键
     * @return 防疫用品库存档案
     */
    @Override
    public BdglAntiepidemicArchives selectBdglAntiepidemicArchivesById(Long id)
    {
        return bdglAntiepidemicArchivesMapper.selectBdglAntiepidemicArchivesById(id);
    }

    /**
     * 查询防疫用品库存档案列表
     * 
     * @param bdglAntiepidemicArchives 防疫用品库存档案
     * @return 防疫用品库存档案
     */
    @Override
    public List<BdglAntiepidemicArchives> selectBdglAntiepidemicArchivesList(BdglAntiepidemicArchives bdglAntiepidemicArchives)
    {
        return bdglAntiepidemicArchivesMapper.selectBdglAntiepidemicArchivesList(bdglAntiepidemicArchives);
    }

    /**
     * 新增防疫用品库存档案
     * 
     * @param bdglAntiepidemicArchives 防疫用品库存档案
     * @return 结果
     */
    @Override
    public int insertBdglAntiepidemicArchives(BdglAntiepidemicArchives bdglAntiepidemicArchives)
    {
        return bdglAntiepidemicArchivesMapper.insertBdglAntiepidemicArchives(bdglAntiepidemicArchives);
    }

    /**
     * 修改防疫用品库存档案
     * 
     * @param bdglAntiepidemicArchives 防疫用品库存档案
     * @return 结果
     */
    @Override
    public int updateBdglAntiepidemicArchives(BdglAntiepidemicArchives bdglAntiepidemicArchives)
    {
        return bdglAntiepidemicArchivesMapper.updateBdglAntiepidemicArchives(bdglAntiepidemicArchives);
    }

    /**
     * 批量删除防疫用品库存档案
     * 
     * @param ids 需要删除的防疫用品库存档案主键
     * @return 结果
     */
    @Override
    public int deleteBdglAntiepidemicArchivesByIds(Long[] ids)
    {
        return bdglAntiepidemicArchivesMapper.deleteBdglAntiepidemicArchivesByIds(ids);
    }

    /**
     * 删除防疫用品库存档案信息
     * 
     * @param id 防疫用品库存档案主键
     * @return 结果
     */
    @Override
    public int deleteBdglAntiepidemicArchivesById(Long id)
    {
        return bdglAntiepidemicArchivesMapper.deleteBdglAntiepidemicArchivesById(id);
    }
}
