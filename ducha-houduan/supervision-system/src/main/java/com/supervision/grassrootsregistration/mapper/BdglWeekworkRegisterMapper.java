package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglWeekworkRegister;
import com.supervision.grassrootsregistration.resultVo.BdglWeekworkRegisterVo;

/**
 * 一周安排工作分Mapper接口
 * 
 * @author supervision
 * @date 2022-02-28
 */
public interface BdglWeekworkRegisterMapper 
{
    /**
     * 查询一周安排工作分
     * 
     * @param id 一周安排工作分主键
     * @return 一周安排工作分
     */
    public BdglWeekworkRegister selectBdglWeekworkRegisterById(Long id);
    
    /**
     * 查询一周工作安排分组数据
     */
    List<BdglWeekworkRegisterVo> getWeekworkRegisterList(BdglWeekworkRegister bdglWeekworkRegister);

    /**
     * 查询一周安排工作分列表
     * 
     * @param bdglWeekworkRegister 一周安排工作分
     * @return 一周安排工作分集合
     */
    public List<BdglWeekworkRegister> selectBdglWeekworkRegisterList(BdglWeekworkRegister bdglWeekworkRegister);

    /**
     * 新增一周安排工作分
     * 
     * @param bdglWeekworkRegister 一周安排工作分
     * @return 结果
     */
    public int insertBdglWeekworkRegister(BdglWeekworkRegister bdglWeekworkRegister);

    /**
     * 修改一周安排工作分
     * 
     * @param bdglWeekworkRegister 一周安排工作分
     * @return 结果
     */
    public int updateBdglWeekworkRegister(BdglWeekworkRegister bdglWeekworkRegister);

    /**
     * 删除一周安排工作分
     * 
     * @param id 一周安排工作分主键
     * @return 结果
     */
    public int deleteBdglWeekworkRegisterById(Long id);

    /**
     * 批量删除一周安排工作分
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglWeekworkRegisterByIds(Long[] ids);

    List<BdglWeekworkRegisterVo> getWeekworkRegisterList2(BdglWeekworkRegister bdglWeekworkRegister);

    List<BdglWeekworkRegister> selectBdglWeekworkRegisterLists(BdglWeekworkRegister bdglWeekworkRegister);
}
