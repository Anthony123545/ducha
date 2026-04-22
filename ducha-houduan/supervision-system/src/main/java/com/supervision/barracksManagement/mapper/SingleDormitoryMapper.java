package com.supervision.barracksManagement.mapper;

import java.util.List;
import com.supervision.barracksManagement.domain.SingleDormitory;

/**
 * 单身干部宿舍Mapper接口
 * 
 * @author gaoqiang
 * @date 2022-11-19
 */
public interface SingleDormitoryMapper 
{
    /**
     * 查询单身干部宿舍
     * 
     * @param id 单身干部宿舍主键
     * @return 单身干部宿舍
     */
    public SingleDormitory selectSingleDormitoryById(Long id);

    /**
     * 查询单身干部宿舍列表
     * 
     * @param singleDormitory 单身干部宿舍
     * @return 单身干部宿舍集合
     */
    public List<SingleDormitory> selectSingleDormitoryList(SingleDormitory singleDormitory);

    /**
     * 新增单身干部宿舍
     * 
     * @param singleDormitory 单身干部宿舍
     * @return 结果
     */
    public int insertSingleDormitory(SingleDormitory singleDormitory);

    /**
     * 修改单身干部宿舍
     * 
     * @param singleDormitory 单身干部宿舍
     * @return 结果
     */
    public int updateSingleDormitory(SingleDormitory singleDormitory);

    /**
     * 删除单身干部宿舍
     * 
     * @param id 单身干部宿舍主键
     * @return 结果
     */
    public int deleteSingleDormitoryById(Long id);

    /**
     * 批量删除单身干部宿舍
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSingleDormitoryByIds(Long[] ids);
}
