package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglDiaryKinsfolk;

/**
 * 临时来队亲属Mapper接口
 * 
 * @author supervision
 * @date 2022-02-28
 */
public interface BdglDiaryKinsfolkMapper 
{
    /**
     * 查询临时来队亲属
     * 
     * @param id 临时来队亲属主键
     * @return 临时来队亲属
     */
    public BdglDiaryKinsfolk selectBdglDiaryKinsfolkById(Integer id);

    /**
     * 查询临时来队亲属列表
     * 
     * @param bdglDiaryKinsfolk 临时来队亲属
     * @return 临时来队亲属集合
     */
    public List<BdglDiaryKinsfolk> selectBdglDiaryKinsfolkList(BdglDiaryKinsfolk bdglDiaryKinsfolk);

    /**
     * 新增临时来队亲属
     * 
     * @param bdglDiaryKinsfolk 临时来队亲属
     * @return 结果
     */
    public int insertBdglDiaryKinsfolk(BdglDiaryKinsfolk bdglDiaryKinsfolk);

    /**
     * 修改临时来队亲属
     * 
     * @param bdglDiaryKinsfolk 临时来队亲属
     * @return 结果
     */
    public int updateBdglDiaryKinsfolk(BdglDiaryKinsfolk bdglDiaryKinsfolk);

    /**
     * 删除临时来队亲属
     * 
     * @param id 临时来队亲属主键
     * @return 结果
     */
    public int deleteBdglDiaryKinsfolkById(Integer id);

    /**
     * 批量删除临时来队亲属
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglDiaryKinsfolkByIds(Integer[] ids);

    int deleteBdglDiaryKinsfolk(Integer id);
}
