package com.supervision.peopleManage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleManage.mapper.BdglPeopleRecallAuditlogMapper;
import com.supervision.peopleManage.domain.BdglPeopleRecallAuditlog;
import com.supervision.peopleManage.service.IBdglPeopleRecallAuditlogService;

/**
 * 人员调出审批Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-10
 */
@Service
public class BdglPeopleRecallAuditlogServiceImpl implements IBdglPeopleRecallAuditlogService 
{
    @Autowired
    private BdglPeopleRecallAuditlogMapper bdglPeopleRecallAuditlogMapper;

    /**
     * 查询人员调出审批
     * 
     * @param id 人员调出审批主键
     * @return 人员调出审批
     */
    @Override
    public BdglPeopleRecallAuditlog selectBdglPeopleRecallAuditlogById(Integer id)
    {
        return bdglPeopleRecallAuditlogMapper.selectBdglPeopleRecallAuditlogById(id);
    }

    /**
     * 查询人员调出审批列表
     * 
     * @param bdglPeopleRecallAuditlog 人员调出审批
     * @return 人员调出审批
     */
    @Override
    public List<BdglPeopleRecallAuditlog> selectBdglPeopleRecallAuditlogList(BdglPeopleRecallAuditlog bdglPeopleRecallAuditlog)
    {
        return bdglPeopleRecallAuditlogMapper.selectBdglPeopleRecallAuditlogList(bdglPeopleRecallAuditlog);
    }

    /**
     * 新增人员调出审批
     * 
     * @param bdglPeopleRecallAuditlog 人员调出审批
     * @return 结果
     */
    @Override
    public int insertBdglPeopleRecallAuditlog(BdglPeopleRecallAuditlog bdglPeopleRecallAuditlog)
    {
        return bdglPeopleRecallAuditlogMapper.insertBdglPeopleRecallAuditlog(bdglPeopleRecallAuditlog);
    }

    /**
     * 修改人员调出审批
     * 
     * @param bdglPeopleRecallAuditlog 人员调出审批
     * @return 结果
     */
    @Override
    public int updateBdglPeopleRecallAuditlog(BdglPeopleRecallAuditlog bdglPeopleRecallAuditlog)
    {
        return bdglPeopleRecallAuditlogMapper.updateBdglPeopleRecallAuditlog(bdglPeopleRecallAuditlog);
    }

    /**
     * 批量删除人员调出审批
     * 
     * @param ids 需要删除的人员调出审批主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleRecallAuditlogByIds(Integer[] ids)
    {
        return bdglPeopleRecallAuditlogMapper.deleteBdglPeopleRecallAuditlogByIds(ids);
    }

    /**
     * 删除人员调出审批信息
     * 
     * @param id 人员调出审批主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleRecallAuditlogById(Integer id)
    {
        return bdglPeopleRecallAuditlogMapper.deleteBdglPeopleRecallAuditlogById(id);
    }
}
