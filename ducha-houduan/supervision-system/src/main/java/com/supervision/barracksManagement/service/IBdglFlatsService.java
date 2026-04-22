package com.supervision.barracksManagement.service;

import java.util.List;
import com.supervision.barracksManagement.domain.BdglFlats;
import com.supervision.barracksManagement.domain.dto.ApiFlatsDto;

/**
 * 公寓住房申请人员资格审查登记Service接口
 * 
 * @author supervision
 * @date 2022-03-02
 */
public interface IBdglFlatsService 
{
    /**
     * 查询公寓住房申请人员资格审查登记
     * 
     * @param id 公寓住房申请人员资格审查登记主键
     * @return 公寓住房申请人员资格审查登记
     */
    public BdglFlats selectBdglFlatsById(Integer id);

    /**
     * 查询公寓住房申请人员资格审查登记列表
     * 
     * @param bdglFlats 公寓住房申请人员资格审查登记
     * @return 公寓住房申请人员资格审查登记集合
     */
    public List<BdglFlats> selectBdglFlatsList(BdglFlats bdglFlats);

    /**
     * 新增公寓住房申请人员资格审查登记
     * 
     * @param bdglFlats 公寓住房申请人员资格审查登记
     * @return 结果
     */
    public int insertBdglFlats(BdglFlats bdglFlats);

    /**
     * 修改公寓住房申请人员资格审查登记
     * 
     * @param bdglFlats 公寓住房申请人员资格审查登记
     * @return 结果
     */
    public int updateBdglFlats(BdglFlats bdglFlats);

    /**
     * 批量删除公寓住房申请人员资格审查登记
     * 
     * @param ids 需要删除的公寓住房申请人员资格审查登记主键集合
     * @return 结果
     */
    public int deleteBdglFlatsByIds(Integer[] ids);

    /**
     * 删除公寓住房申请人员资格审查登记信息
     * 
     * @param id 公寓住房申请人员资格审查登记主键
     * @return 结果
     */
    public int deleteBdglFlatsById(Integer id);
    
}
