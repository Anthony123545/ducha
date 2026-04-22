package com.supervision.regulations.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.regulations.mapper.BdglFileResdMapper;
import com.supervision.regulations.domain.BdglFileResd;
import com.supervision.regulations.service.IBdglFileResdService;

/**
 * 规章制度阅读量统计Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-18
 */
@Service
public class BdglFileResdServiceImpl implements IBdglFileResdService 
{
    @Autowired
    private BdglFileResdMapper bdglFileResdMapper;

    /**
     * 查询规章制度阅读量统计
     * 
     * @param id 规章制度阅读量统计主键
     * @return 规章制度阅读量统计
     */
    @Override
    public BdglFileResd selectBdglFileResdById(Long id)
    {
        return bdglFileResdMapper.selectBdglFileResdById(id);
    }

    /**
     * 查询规章制度阅读量统计列表
     * 
     * @param bdglFileResd 规章制度阅读量统计
     * @return 规章制度阅读量统计
     */
    @Override
    public List<BdglFileResd> selectBdglFileResdList(BdglFileResd bdglFileResd)
    {
        return bdglFileResdMapper.selectBdglFileResdList(bdglFileResd);
    }

    /**
     * 新增规章制度阅读量统计
     * 
     * @param bdglFileResd 规章制度阅读量统计
     * @return 结果
     */
    @Override
    public int insertBdglFileResd(BdglFileResd bdglFileResd)
    {
        return bdglFileResdMapper.insertBdglFileResd(bdglFileResd);
    }

    /**
     * 修改规章制度阅读量统计
     * 
     * @param bdglFileResd 规章制度阅读量统计
     * @return 结果
     */
    @Override
    public int updateBdglFileResd(BdglFileResd bdglFileResd)
    {
        return bdglFileResdMapper.updateBdglFileResd(bdglFileResd);
    }

    /**
     * 批量删除规章制度阅读量统计
     * 
     * @param ids 需要删除的规章制度阅读量统计主键
     * @return 结果
     */
    @Override
    public int deleteBdglFileResdByIds(Long[] ids)
    {
        return bdglFileResdMapper.deleteBdglFileResdByIds(ids);
    }

    /**
     * 删除规章制度阅读量统计信息
     * 
     * @param id 规章制度阅读量统计主键
     * @return 结果
     */
    @Override
    public int deleteBdglFileResdById(Long id)
    {
        return bdglFileResdMapper.deleteBdglFileResdById(id);
    }
}
