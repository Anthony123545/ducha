package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglDiaryKinsfolkMapper;
import com.supervision.grassrootsregistration.domain.BdglDiaryKinsfolk;
import com.supervision.grassrootsregistration.service.IBdglDiaryKinsfolkService;

/**
 * 临时来队亲属Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-28
 */
@Service
public class BdglDiaryKinsfolkServiceImpl implements IBdglDiaryKinsfolkService 
{
    @Autowired
    private BdglDiaryKinsfolkMapper bdglDiaryKinsfolkMapper;

    /**
     * 查询临时来队亲属
     * 
     * @param id 临时来队亲属主键
     * @return 临时来队亲属
     */
    @Override
    public BdglDiaryKinsfolk selectBdglDiaryKinsfolkById(Integer id)
    {
        return bdglDiaryKinsfolkMapper.selectBdglDiaryKinsfolkById(id);
    }

    /**
     * 查询临时来队亲属列表
     * 
     * @param bdglDiaryKinsfolk 临时来队亲属
     * @return 临时来队亲属
     */
    @Override
    public List<BdglDiaryKinsfolk> selectBdglDiaryKinsfolkList(BdglDiaryKinsfolk bdglDiaryKinsfolk)
    {
        return bdglDiaryKinsfolkMapper.selectBdglDiaryKinsfolkList(bdglDiaryKinsfolk);
    }

    /**
     * 新增临时来队亲属
     * 
     * @param bdglDiaryKinsfolk 临时来队亲属
     * @return 结果
     */
    @Override
    public int insertBdglDiaryKinsfolk(BdglDiaryKinsfolk bdglDiaryKinsfolk)
    {
        return bdglDiaryKinsfolkMapper.insertBdglDiaryKinsfolk(bdglDiaryKinsfolk);
    }

    /**
     * 修改临时来队亲属
     * 
     * @param bdglDiaryKinsfolk 临时来队亲属
     * @return 结果
     */
    @Override
    public int updateBdglDiaryKinsfolk(BdglDiaryKinsfolk bdglDiaryKinsfolk)
    {
        return bdglDiaryKinsfolkMapper.updateBdglDiaryKinsfolk(bdglDiaryKinsfolk);
    }

    /**
     * 批量删除临时来队亲属
     * 
     * @param ids 需要删除的临时来队亲属主键
     * @return 结果
     */
    @Override
    public int deleteBdglDiaryKinsfolkByIds(Integer[] ids)
    {
        return bdglDiaryKinsfolkMapper.deleteBdglDiaryKinsfolkByIds(ids);
    }

    /**
     * 删除临时来队亲属信息
     * 
     * @param id 临时来队亲属主键
     * @return 结果
     */
    @Override
    public int deleteBdglDiaryKinsfolkById(Integer id)
    {
        return bdglDiaryKinsfolkMapper.deleteBdglDiaryKinsfolkById(id);
    }
}
