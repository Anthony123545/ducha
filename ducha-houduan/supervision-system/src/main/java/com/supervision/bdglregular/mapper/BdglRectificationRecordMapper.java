package com.supervision.bdglregular.mapper;

import java.util.List;
import com.supervision.bdglregular.domain.BdglRectificationRecord;

/**
 * 检查审批不通过记录Mapper接口
 * 
 * @author supervision
 * @date 2022-03-08
 */
public interface BdglRectificationRecordMapper 
{
    /**
     * 查询检查审批不通过记录
     * 
     * @param id 检查审批不通过记录主键
     * @return 检查审批不通过记录
     */
    public BdglRectificationRecord selectBdglRectificationRecordById(Long id);

    /**
     * 查询检查审批不通过记录列表
     * 
     * @param bdglRectificationRecord 检查审批不通过记录
     * @return 检查审批不通过记录集合
     */
    public List<BdglRectificationRecord> selectBdglRectificationRecordList(BdglRectificationRecord bdglRectificationRecord);

    /**
     * 新增检查审批不通过记录
     * 
     * @param bdglRectificationRecord 检查审批不通过记录
     * @return 结果
     */
    public int insertBdglRectificationRecord(BdglRectificationRecord bdglRectificationRecord);

    /**
     * 修改检查审批不通过记录
     * 
     * @param bdglRectificationRecord 检查审批不通过记录
     * @return 结果
     */
    public int updateBdglRectificationRecord(BdglRectificationRecord bdglRectificationRecord);

    /**
     * 删除检查审批不通过记录
     * 
     * @param id 检查审批不通过记录主键
     * @return 结果
     */
    public int deleteBdglRectificationRecordById(Long id);

    /**
     * 批量删除检查审批不通过记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglRectificationRecordByIds(Long[] ids);
}
