package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration .domain.BdglParty;
import org.apache.ibatis.annotations.Param;

/**
 * 党支部会议登记本Mapper接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface BdglPartyMapper 
{
    /**
     * 查询党支部会议登记本
     * 
     * @param id 党支部会议登记本主键
     * @return 党支部会议登记本
     */
    public BdglParty selectBdglPartyById(Integer id);

    /**
     * 查询党支部会议登记本列表
     * 
     * @param bdglParty 党支部会议登记本
     * @return 党支部会议登记本集合
     */
    public List<BdglParty> selectBdglPartyList(BdglParty bdglParty);

    /**
     * 新增党支部会议登记本
     * 
     * @param bdglParty 党支部会议登记本
     * @return 结果
     */
    public int insertBdglParty(BdglParty bdglParty);

    /**
     * 修改党支部会议登记本
     * 
     * @param bdglParty 党支部会议登记本
     * @return 结果
     */
    public int updateBdglParty(BdglParty bdglParty);

    /**
     * 删除党支部会议登记本
     * 
     * @param id 党支部会议登记本主键
     * @return 结果
     */
    public int deleteBdglPartyById(Integer id);

    /**
     * 批量删除党支部会议登记本
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglPartyByIds(Integer[] ids);
    
    int selectlPartyByunitId(@Param("unitId") Integer unitId, @Param("year") String year);
    
    List<BdglParty> selectPartyOnUnitList(@Param("unitId") Integer unitId, @Param("year") String year);
}
