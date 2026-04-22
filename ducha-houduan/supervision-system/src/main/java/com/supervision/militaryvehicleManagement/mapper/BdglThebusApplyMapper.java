package com.supervision.militaryvehicleManagement.mapper;

import java.util.List;
import com.supervision.militaryvehicleManagement.domain.BdglThebusApply;
import org.apache.ibatis.annotations.Select;

/**
 * 用车申请Mapper接口
 *
 * @author supervision
 * @date 2022-03-04
 */
public interface BdglThebusApplyMapper
{
    /**
     * 查询用车申请
     *
     * @param id 用车申请主键
     * @return 用车申请
     */
    public BdglThebusApply selectBdglThebusApplyById(Long id);

    /**
     * 查询用车申请列表
     *
     * @param bdglThebusApply 用车申请
     * @return 用车申请集合
     */
    public List<BdglThebusApply> selectBdglThebusApplyList(BdglThebusApply bdglThebusApply);

    /**
     * 新增用车申请
     *
     * @param bdglThebusApply 用车申请
     * @return 结果
     */
    public int insertBdglThebusApply(BdglThebusApply bdglThebusApply);

    /**
     * 修改用车申请
     *
     * @param bdglThebusApply 用车申请
     * @return 结果
     */
    public int updateBdglThebusApply(BdglThebusApply bdglThebusApply);

    /**
     * 删除用车申请
     *
     * @param id 用车申请主键
     * @return 结果
     */
    public int deleteBdglThebusApplyById(Long id);

    /**
     * 批量删除用车申请
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteBdglThebusApplyByIds(Long[] ids);

    List<BdglThebusApply> selectBdglThebusApplyLists(BdglThebusApply bdglThebusApply);


    int updateKeChuChe(BdglThebusApply thebusApply);

    @Select("select * from bdgl_thebus_apply where car_ids=#{license} limit 1")
    BdglThebusApply selectBdglThebusApplyByCarIds(String license);
}
