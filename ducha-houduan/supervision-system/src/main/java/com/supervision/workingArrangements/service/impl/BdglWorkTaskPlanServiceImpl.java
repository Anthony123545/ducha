package com.supervision.workingArrangements.service.impl;

import java.util.Comparator;
import java.util.List;

import cn.hutool.core.util.NumberUtil;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.SecurityUtils;
import com.supervision.workingArrangements.domain.BdglWorkTaskPlanDetail;
import com.supervision.workingArrangements.mapper.BdglWorkTaskPlanDetailMapper;
import com.supervision.workingArrangements.resultVo.WorkTaskPlanVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.supervision.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.supervision.workingArrangements.domain.BdglWorkTaskPlanAnner;
import com.supervision.workingArrangements.mapper.BdglWorkTaskPlanMapper;
import com.supervision.workingArrangements.domain.BdglWorkTaskPlan;
import com.supervision.workingArrangements.service.IBdglWorkTaskPlanService;

import javax.annotation.Resource;

/**
 * 任务计划基本信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-08-15
 */
@Service
public class BdglWorkTaskPlanServiceImpl implements IBdglWorkTaskPlanService
{
    @Resource
    private BdglWorkTaskPlanMapper bdglWorkTaskPlanMapper;

    @Resource
    private BdglWorkTaskPlanDetailMapper detailMapper;

    /**
     * 查询任务计划基本信息
     *
     * @param id 任务计划基本信息主键
     * @return 任务计划基本信息
     */
    @Override
    public WorkTaskPlanVo selectBdglWorkTaskPlanById(Long id) {
        BdglWorkTaskPlan taskPlan = bdglWorkTaskPlanMapper.selectBdglWorkTaskPlanById(id);
        return getWorkTaskPlanVo(taskPlan);
    }

    /**
     * 查询任务计划基本信息列表
     *
     * @param bdglWorkTaskPlan 任务计划基本信息
     * @return 任务计划基本信息
     */
    @Override
    public List<BdglWorkTaskPlan> selectBdglWorkTaskPlanList(BdglWorkTaskPlan bdglWorkTaskPlan)
    {
        return bdglWorkTaskPlanMapper.selectBdglWorkTaskPlanList(bdglWorkTaskPlan);
    }

    /**
     * 查询任务计划基本信息列表
     *
     * @param bdglWorkTaskPlan 任务计划基本信息
     * @return 任务计划基本信息
     */
    @Override
    public List<WorkTaskPlanVo> selectBdglWorkTaskPlanListVo(BdglWorkTaskPlan bdglWorkTaskPlan) {
        //查询任务计划基本信息
        //获取当前登录用户ID
        String userName = SecurityUtils.getLoginUser().getUser().getUserName();
        //如果当前用户为admin则不过滤数据
        if ("admin".equals(userName)){
            userName = null;
        }
        bdglWorkTaskPlan.setCreateBy(userName);
        List<BdglWorkTaskPlan> workTaskPlans = bdglWorkTaskPlanMapper.selectBdglWorkTaskPlanList(bdglWorkTaskPlan);
        if (workTaskPlans != null && workTaskPlans.size()>0){
            //遍历任务计划基本信息，封装可选参数
            return workTaskPlans.stream().map(this::getWorkTaskPlanVo).collect(Collectors.toList());
        }else {
            return new ArrayList<>();
        }
    }
    /**
     * 执行任务查询任务信息
     */
    @Override
    public List<WorkTaskPlanVo> selectWorkTaskPlanListVo(BdglWorkTaskPlan bdglWorkTaskPlan) {
        //获取当前登录用户ID
        SysUser user = SecurityUtils.getLoginUser().getUser();
        //根据UserID查询是否有任务存在
        List<BdglWorkTaskPlanDetail> userWorkTaskPlanDetail = detailMapper.selectWorkTaskPlanDetailListByUserId(user.getUserId());
        //判断是否有任务存在，如果任务存在，则返回完成任务信息，如果不存在，则返回null
        List<WorkTaskPlanVo> voList = new ArrayList<>();
        if (userWorkTaskPlanDetail != null && userWorkTaskPlanDetail.size() >0){
            for (BdglWorkTaskPlanDetail detail : userWorkTaskPlanDetail){
                //获取主任务ID
                Long taskPlanId = detail.getTaskPlanId();
                //查询任务基本信息
                BdglWorkTaskPlan workTaskPlan = bdglWorkTaskPlanMapper.selectBdglWorkTaskPlanById(taskPlanId);
                WorkTaskPlanVo planVo = new WorkTaskPlanVo();
                if (workTaskPlan != null) {
                    BeanUtils.copyProperties(workTaskPlan, planVo);
                }
                planVo.setTaskStatus(detail.getTaskStatus());
                planVo.setPlanDetail(detail);
                voList.add(planVo);
            }
        }
        return voList;
    }

    /**
     * 封装任务计划页面展示信息
     * @param item
     * @return
     */
    private WorkTaskPlanVo getWorkTaskPlanVo(BdglWorkTaskPlan item) {
        WorkTaskPlanVo workTaskPlanVo = new WorkTaskPlanVo();
        //复制BdglWorkTaskPlan对象到workTaskPlanVo视图对象
        BeanUtils.copyProperties(item, workTaskPlanVo);
        //查询任务详情
        BdglWorkTaskPlanDetail bdglWorkTaskPlanDetail = new BdglWorkTaskPlanDetail();
        bdglWorkTaskPlanDetail.setTaskPlanId(item.getId());
        List<BdglWorkTaskPlanDetail> detailList = detailMapper.selectBdglWorkTaskPlanDetailList(bdglWorkTaskPlanDetail);
        //获取任务附件信息
        List<BdglWorkTaskPlanAnner> annexList = bdglWorkTaskPlanMapper.selectBdglWorkTaskPlanAnnerByTaskPlanId(item.getId());
        workTaskPlanVo.setAnnexList(annexList);
        getPlanDetailData(workTaskPlanVo, detailList);
        //封装任务详情
        workTaskPlanVo.setBdglWorkTaskPlanDetailList(detailList);
        return workTaskPlanVo;
    }

    /**
     * 封装任务详情数据
     * @param workTaskPlanVo
     * @param detailList
     */
    private void getPlanDetailData(WorkTaskPlanVo workTaskPlanVo, List<BdglWorkTaskPlanDetail> detailList) {
        //如果任务详情为空，则表示任务总数，执行进度，执行单位都不存在，则用0表示
        if (StringUtils.isEmpty(detailList) && detailList.size() == 0) {
            workTaskPlanVo.setTaskCount(0);
            workTaskPlanVo.setTaskProgress("0");
            workTaskPlanVo.setUnitCount(0);
        }
        if (StringUtils.isNotEmpty(detailList) && detailList.size() > 0) {
            //配置任务总数
            workTaskPlanVo.setTaskCount(detailList.size());
            //计算当前进度
            //过滤已完成的任务数，
            List<BdglWorkTaskPlanDetail> status = detailList.stream().filter(detailTemp -> "2".equals(detailTemp.getTaskStatus().toString())).collect(Collectors.toList());
            //任务总数/已完成的任务数,即当前进度
            //如果任务完成数为0，则进度为0
            if (StringUtils.isEmpty(status) && status.size() == 0){
                workTaskPlanVo.setTaskProgress("0");
            }else {
                double i = (double) status.size() / (double) detailList.size();
                String taskProgress = NumberUtil.decimalFormat("#",i*100);
                workTaskPlanVo.setTaskProgress(taskProgress);
            }
            //执行单位封装
            ArrayList<BdglWorkTaskPlanDetail> collect = detailList.stream().collect(Collectors.collectingAndThen(
                    Collectors.toCollection(() -> new TreeSet<>(
                            Comparator.comparing(
                                    BdglWorkTaskPlanDetail::getUnitId))), ArrayList::new));
            //执行单位个数
            workTaskPlanVo.setUnitCount(collect.size());
        }
    }

    /**
     * 新增任务计划基本信息
     *
     * @param bdglWorkTaskPlan 任务计划基本信息
     * @return 结果
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int insertBdglWorkTaskPlan(BdglWorkTaskPlan bdglWorkTaskPlan)
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        bdglWorkTaskPlan.setCreateBy(user.getUserName());
        bdglWorkTaskPlan.setCreateTime(DateUtils.getNowDate());
        int rows = bdglWorkTaskPlanMapper.insertBdglWorkTaskPlan(bdglWorkTaskPlan);
        //如果没有附件，则不添加附件
        if (StringUtils.isNotNull(bdglWorkTaskPlan.getAnnexList())){
            insertBdglWorkTaskPlanAnner(bdglWorkTaskPlan);
        }
        return rows;
    }

    /**
     * 修改任务计划基本信息
     *
     * @param bdglWorkTaskPlan 任务计划基本信息
     * @return 结果
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int updateBdglWorkTaskPlan(BdglWorkTaskPlan bdglWorkTaskPlan)
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        bdglWorkTaskPlan.setUpdateBy(user.getUserName());
        bdglWorkTaskPlan.setUpdateTime(DateUtils.getNowDate());
        if (StringUtils.isNotNull(bdglWorkTaskPlan.getAnnexList()) && bdglWorkTaskPlan.getAnnexList().size() >0){
            bdglWorkTaskPlanMapper.deleteBdglWorkTaskPlanAnnerByTaskPlanId(bdglWorkTaskPlan.getId());
            insertBdglWorkTaskPlanAnner(bdglWorkTaskPlan);
        }
        return bdglWorkTaskPlanMapper.updateBdglWorkTaskPlan(bdglWorkTaskPlan);
    }

    /**
     * 批量删除任务计划基本信息
     *
     * @param ids 需要删除的任务计划基本信息主键
     * @return 结果
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int deleteBdglWorkTaskPlanByIds(Long[] ids)
    {
        //删除附件
        bdglWorkTaskPlanMapper.deleteBdglWorkTaskPlanAnnerByTaskPlanIds(ids);
        //删除详情
        for (Long taskPlanId: ids){
            detailMapper.deletePlanDetailByTaskPlanId(taskPlanId);
        }
        //删除任务
        return bdglWorkTaskPlanMapper.deleteBdglWorkTaskPlanByIds(ids);
    }

    /**
     * 删除任务计划基本信息信息
     *
     * @param id 任务计划基本信息主键
     * @return 结果
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int deleteBdglWorkTaskPlanById(Long id)
    {
        bdglWorkTaskPlanMapper.deleteBdglWorkTaskPlanAnnerByTaskPlanId(id);
        detailMapper.deletePlanDetailByTaskPlanId(id);
        return bdglWorkTaskPlanMapper.deleteBdglWorkTaskPlanById(id);
    }

    /**
     * 新增任务附件信息
     *
     * @param bdglWorkTaskPlan 任务计划基本信息对象
     */
    public void insertBdglWorkTaskPlanAnner(BdglWorkTaskPlan bdglWorkTaskPlan)
    {
        List<BdglWorkTaskPlanAnner> bdglWorkTaskPlanAnnerList = bdglWorkTaskPlan.getAnnexList();
        Long id = bdglWorkTaskPlan.getId();
        if (StringUtils.isNotNull(bdglWorkTaskPlanAnnerList))
        {
            bdglWorkTaskPlanAnnerList.forEach(item -> {
                //保存任务ID
                item.setTaskPlanId(id);
            });
        }
        bdglWorkTaskPlanMapper.batchBdglWorkTaskPlanAnner(bdglWorkTaskPlanAnnerList);
    }
}
