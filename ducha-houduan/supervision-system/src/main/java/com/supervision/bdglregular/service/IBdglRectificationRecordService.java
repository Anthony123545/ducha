package com.supervision.bdglregular.service;

import java.util.List;
import com.supervision.bdglregular.domain.BdglRectificationRecord;
import com.supervision.bdglregular.domain.BdglRegular;

/**
 * 检查审批不通过记录Service接口
 * 
 * @author supervision
 * @date 2022-03-08
 */
public interface IBdglRectificationRecordService 
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
     * 批量删除检查审批不通过记录
     * 
     * @param ids 需要删除的检查审批不通过记录主键集合
     * @return 结果
     */
    public int deleteBdglRectificationRecordByIds(Long[] ids);


    /**
     * 删除检查审批不通过记录信息
     * 
     * @param id 检查审批不通过记录主键
     * @return 结果
     */
    public int deleteBdglRectificationRecordById(Long id);
}
