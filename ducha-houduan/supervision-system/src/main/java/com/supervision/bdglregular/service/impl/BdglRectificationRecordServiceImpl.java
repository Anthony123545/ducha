package com.supervision.bdglregular.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.bdglregular.mapper.BdglRectificationRecordMapper;
import com.supervision.bdglregular.domain.BdglRectificationRecord;
import com.supervision.bdglregular.service.IBdglRectificationRecordService;

/**
 * 检查审批不通过记录Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-08
 */
@Service
public class BdglRectificationRecordServiceImpl implements IBdglRectificationRecordService 
{
    @Autowired
    private BdglRectificationRecordMapper bdglRectificationRecordMapper;

    /**
     * 查询检查审批不通过记录
     * 
     * @param id 检查审批不通过记录主键
     * @return 检查审批不通过记录
     */
    @Override
    public BdglRectificationRecord selectBdglRectificationRecordById(Long id)
    {
        return bdglRectificationRecordMapper.selectBdglRectificationRecordById(id);
    }

    /**
     * 查询检查审批不通过记录列表
     * 
     * @param bdglRectificationRecord 检查审批不通过记录
     * @return 检查审批不通过记录
     */
    @Override
    public List<BdglRectificationRecord> selectBdglRectificationRecordList(BdglRectificationRecord bdglRectificationRecord)
    {
        return bdglRectificationRecordMapper.selectBdglRectificationRecordList(bdglRectificationRecord);
    }

    /**
     * 新增检查审批不通过记录
     * 
     * @param bdglRectificationRecord 检查审批不通过记录
     * @return 结果
     */
    @Override
    public int insertBdglRectificationRecord(BdglRectificationRecord bdglRectificationRecord)
    {
        bdglRectificationRecord.setCreateTime(DateUtils.getNowDate());
        return bdglRectificationRecordMapper.insertBdglRectificationRecord(bdglRectificationRecord);
    }

    /**
     * 修改检查审批不通过记录
     * 
     * @param bdglRectificationRecord 检查审批不通过记录
     * @return 结果
     */
    @Override
    public int updateBdglRectificationRecord(BdglRectificationRecord bdglRectificationRecord)
    {
        return bdglRectificationRecordMapper.updateBdglRectificationRecord(bdglRectificationRecord);
    }

    /**
     * 批量删除检查审批不通过记录
     * 
     * @param ids 需要删除的检查审批不通过记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglRectificationRecordByIds(Long[] ids)
    {
        return bdglRectificationRecordMapper.deleteBdglRectificationRecordByIds(ids);
    }

    /**
     * 删除检查审批不通过记录信息
     * 
     * @param id 检查审批不通过记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglRectificationRecordById(Long id)
    {
        return bdglRectificationRecordMapper.deleteBdglRectificationRecordById(id);
    }
}
