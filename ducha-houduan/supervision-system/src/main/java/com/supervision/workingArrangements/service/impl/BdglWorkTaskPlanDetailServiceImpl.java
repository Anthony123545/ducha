package com.supervision.workingArrangements.service.impl;

import java.util.Date;
import java.util.List;

import com.supervision.workingArrangements.domain.BdglWorkTaskPlanAnner;
import com.supervision.workingArrangements.mapper.BdglWorkTaskPlanMapper;
import org.springframework.stereotype.Service;
import com.supervision.workingArrangements.mapper.BdglWorkTaskPlanDetailMapper;
import com.supervision.workingArrangements.domain.BdglWorkTaskPlanDetail;
import com.supervision.workingArrangements.service.IBdglWorkTaskPlanDetailService;

import javax.annotation.Resource;

/**
 * 任务计划执行详情Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-15
 */
@Service
public class BdglWorkTaskPlanDetailServiceImpl implements IBdglWorkTaskPlanDetailService 
{
    @Resource
    private BdglWorkTaskPlanDetailMapper bdglWorkTaskPlanDetailMapper;
    
    @Resource
    private BdglWorkTaskPlanMapper workTaskPlanMapper;

    /**
     * 查询任务计划执行详情
     * 
     * @param id 任务计划执行详情主键
     * @return 任务计划执行详情
     */
    @Override
    public BdglWorkTaskPlanDetail selectBdglWorkTaskPlanDetailById(Long id) {
        BdglWorkTaskPlanDetail planDetail = bdglWorkTaskPlanDetailMapper.selectBdglWorkTaskPlanDetailById(id);
        //查询任务附件
        List<BdglWorkTaskPlanAnner> annerList = workTaskPlanMapper.selectWorkTaskPlanAnnerByTaskPlanDetailId(id);
        planDetail.setAnnexList(annerList);
        return planDetail;
    }

    /**
     * 查询任务计划执行详情列表
     * 
     * @param bdglWorkTaskPlanDetail 任务计划执行详情
     * @return 任务计划执行详情
     */
    @Override
    public List<BdglWorkTaskPlanDetail> selectBdglWorkTaskPlanDetailList(BdglWorkTaskPlanDetail bdglWorkTaskPlanDetail)
    {
        return bdglWorkTaskPlanDetailMapper.selectBdglWorkTaskPlanDetailList(bdglWorkTaskPlanDetail);
    }

    /**
     * 新增任务计划执行详情
     * 
     * @param bdglWorkTaskPlanDetail 任务计划执行详情
     * @return 结果
     */
    @Override
    public int insertBdglWorkTaskPlanDetail(BdglWorkTaskPlanDetail bdglWorkTaskPlanDetail)
    {
        bdglWorkTaskPlanDetail.setCreateTime(new Date());
        return bdglWorkTaskPlanDetailMapper.insertBdglWorkTaskPlanDetail(bdglWorkTaskPlanDetail);
    }

    /**
     * 修改任务计划执行详情
     * 
     * @param bdglWorkTaskPlanDetail 任务计划执行详情
     * @return 结果
     */
    @Override
    public int updateBdglWorkTaskPlanDetail(BdglWorkTaskPlanDetail bdglWorkTaskPlanDetail) {
        
        return bdglWorkTaskPlanDetailMapper.updateBdglWorkTaskPlanDetail(bdglWorkTaskPlanDetail);
    }

    /**
     * 批量删除任务计划执行详情
     * 
     * @param ids 需要删除的任务计划执行详情主键
     * @return 结果
     */
    @Override
    public int deleteBdglWorkTaskPlanDetailByIds(Long[] ids)
    {
        return bdglWorkTaskPlanDetailMapper.deleteBdglWorkTaskPlanDetailByIds(ids);
    }

    /**
     * 删除任务计划执行详情信息
     * 
     * @param id 任务计划执行详情主键
     * @return 结果
     */
    @Override
    public int deleteBdglWorkTaskPlanDetailById(Long id)
    {
        return bdglWorkTaskPlanDetailMapper.deleteBdglWorkTaskPlanDetailById(id);
    }
    
    @Override
    public List<BdglWorkTaskPlanDetail> selectWorkTaskPlanDetailListByTaskPlanId(Long taskPlanId) {
        return bdglWorkTaskPlanDetailMapper.selectWorkTaskPlanDetailListByTaskPlanId(taskPlanId);
    }
    
    /**
     * 新增任务详情附件
     * @param workTaskPlanDetail
     * @return
     */
    @Override
    public int insertBdglWorkTaskPlanDetailAnnex(BdglWorkTaskPlanDetail workTaskPlanDetail) {
        //生成反馈时间
        workTaskPlanDetail.setFeedbackTime(new Date());
        //反馈完成表示任务执行完成，状态改为已完成
        workTaskPlanDetail.setTaskStatus(2L);
        List<BdglWorkTaskPlanAnner> annexList = workTaskPlanDetail.getAnnexList();
        if (annexList != null && annexList.size() >0){
            annexList.forEach(item -> {
                //保存任务ID
                item.setTaskPlanDetailId(workTaskPlanDetail.getId());
            });
            workTaskPlanMapper.batchBdglWorkTaskPlanAnnerWithDetail(workTaskPlanDetail.getAnnexList());
        }
        return bdglWorkTaskPlanDetailMapper.updateBdglWorkTaskPlanDetail(workTaskPlanDetail);
    }
}
