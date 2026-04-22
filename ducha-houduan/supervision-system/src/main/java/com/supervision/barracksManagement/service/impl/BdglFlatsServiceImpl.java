package com.supervision.barracksManagement.service.impl;

import java.util.List;

import com.supervision.barracksManagement.domain.dto.ApiFlatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.barracksManagement.mapper.BdglFlatsMapper;
import com.supervision.barracksManagement.domain.BdglFlats;
import com.supervision.barracksManagement.service.IBdglFlatsService;

/**
 * 公寓住房申请人员资格审查登记Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-02
 */
@Service
public class BdglFlatsServiceImpl implements IBdglFlatsService 
{
    @Autowired
    private BdglFlatsMapper bdglFlatsMapper;

    /**
     * 查询公寓住房申请人员资格审查登记
     * 
     * @param id 公寓住房申请人员资格审查登记主键
     * @return 公寓住房申请人员资格审查登记
     */
    @Override
    public BdglFlats selectBdglFlatsById(Integer id)
    {
        return bdglFlatsMapper.selectBdglFlatsById(id);
    }

    /**
     * 查询公寓住房申请人员资格审查登记列表
     * 
     * @param bdglFlats 公寓住房申请人员资格审查登记
     * @return 公寓住房申请人员资格审查登记
     */
    @Override
    public List<BdglFlats> selectBdglFlatsList(BdglFlats bdglFlats)
    {
        return bdglFlatsMapper.selectBdglFlatsList(bdglFlats);
    }

    /**
     * 新增公寓住房申请人员资格审查登记
     * 
     * @param bdglFlats 公寓住房申请人员资格审查登记
     * @return 结果
     */
    @Override
    public int insertBdglFlats(BdglFlats bdglFlats)
    {
        return bdglFlatsMapper.insertBdglFlats(bdglFlats);
    }

    /**
     * 修改公寓住房申请人员资格审查登记
     * 
     * @param bdglFlats 公寓住房申请人员资格审查登记
     * @return 结果
     */
    @Override
    public int updateBdglFlats(BdglFlats bdglFlats)
    {
        return bdglFlatsMapper.updateBdglFlats(bdglFlats);
    }

    /**
     * 批量删除公寓住房申请人员资格审查登记
     * 
     * @param ids 需要删除的公寓住房申请人员资格审查登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglFlatsByIds(Integer[] ids)
    {
        return bdglFlatsMapper.deleteBdglFlatsByIds(ids);
    }

    /**
     * 删除公寓住房申请人员资格审查登记信息
     * 
     * @param id 公寓住房申请人员资格审查登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglFlatsById(Integer id)
    {
        return bdglFlatsMapper.deleteBdglFlatsById(id);
    }
    
}
