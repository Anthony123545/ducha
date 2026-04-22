package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglEquip;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;

/**
 * 训练器材/教材登记薄Service接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface IBdglEquipService 
{
    /**
     * 查询训练器材/教材登记薄
     * 
     * @param id 训练器材/教材登记薄主键
     * @return 训练器材/教材登记薄
     */
    public BdglEquip selectBdglEquipById(Long id);

    public List<BdglEquip> selectBdglByunitid(Integer unitid);
    /**
     * 查询训练器材/教材登记薄列表
     * 
     * @param bdglEquip 训练器材/教材登记薄
     * @return 训练器材/教材登记薄集合
     */
    public List<BdglEquip> selectBdglEquipList(BdglEquip bdglEquip);

    /**
     * 新增训练器材/教材登记薄
     * 
     * @param bdglEquip 训练器材/教材登记薄
     * @return 结果
     */
    public int insertBdglEquip(BdglEquip bdglEquip);

    /**
     * 修改训练器材/教材登记薄
     * 
     * @param bdglEquip 训练器材/教材登记薄
     * @return 结果
     */
    public int updateBdglEquip(BdglEquip bdglEquip);

    /**
     * 批量删除训练器材/教材登记薄
     * 
     * @param ids 需要删除的训练器材/教材登记薄主键集合
     * @return 结果
     */
    public int deleteBdglEquipByIds(Long[] ids);

    /**
     * 删除训练器材/教材登记薄信息
     * 
     * @param id 训练器材/教材登记薄主键
     * @return 结果
     */
    public int deleteBdglEquipById(Long id);
    
    List<SysDeptDto> selectEquipList(String year);
    
    List<BdglEquip> selectEquIpByunitId(Integer unitid, String year);
}
