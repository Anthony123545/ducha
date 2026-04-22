package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglCookbookPrcMapper;
import com.supervision.grassrootsregistration.domain.BdglCookbookPrc;
import com.supervision.grassrootsregistration.service.IBdglCookbookPrcService;

/**
 * 一周工作食普图Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-08
 */
@Service
public class BdglCookbookPrcServiceImpl implements IBdglCookbookPrcService 
{
    @Autowired
    private BdglCookbookPrcMapper bdglCookbookPrcMapper;

    /**
     * 查询一周工作食普图
     * 
     * @param id 一周工作食普图主键
     * @return 一周工作食普图
     */
    @Override
    public BdglCookbookPrc selectBdglCookbookPrcById(Long id)
    {
        return bdglCookbookPrcMapper.selectBdglCookbookPrcById(id);
    }

    /**
     * 查询一周工作食普图列表
     * 
     * @param bdglCookbookPrc 一周工作食普图
     * @return 一周工作食普图
     */
    @Override
    public List<BdglCookbookPrc> selectBdglCookbookPrcList(BdglCookbookPrc bdglCookbookPrc)
    {
        return bdglCookbookPrcMapper.selectBdglCookbookPrcList(bdglCookbookPrc);
    }

    /**
     * 新增一周工作食普图
     * 
     * @param bdglCookbookPrc 一周工作食普图
     * @return 结果
     */
    @Override
    public int insertBdglCookbookPrc(BdglCookbookPrc bdglCookbookPrc)
    {
        return bdglCookbookPrcMapper.insertBdglCookbookPrc(bdglCookbookPrc);
    }

    /**
     * 修改一周工作食普图
     * 
     * @param bdglCookbookPrc 一周工作食普图
     * @return 结果
     */
    @Override
    public int updateBdglCookbookPrc(BdglCookbookPrc bdglCookbookPrc)
    {
        return bdglCookbookPrcMapper.updateBdglCookbookPrc(bdglCookbookPrc);
    }

    /**
     * 批量删除一周工作食普图
     * 
     * @param ids 需要删除的一周工作食普图主键
     * @return 结果
     */
    @Override
    public int deleteBdglCookbookPrcByIds(Long[] ids)
    {
        return bdglCookbookPrcMapper.deleteBdglCookbookPrcByIds(ids);
    }

    /**
     * 删除一周工作食普图信息
     * 
     * @param id 一周工作食普图主键
     * @return 结果
     */
    @Override
    public int deleteBdglCookbookPrcById(Long id)
    {
        return bdglCookbookPrcMapper.deleteBdglCookbookPrcById(id);
    }
}
