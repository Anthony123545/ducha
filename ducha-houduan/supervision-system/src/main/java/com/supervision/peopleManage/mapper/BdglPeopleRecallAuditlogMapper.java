package com.supervision.peopleManage.mapper;

import java.util.List;
import com.supervision.peopleManage.domain.BdglPeopleRecallAuditlog;

/**
 * 人员调出审批Mapper接口
 * 
 * @author ruoyi
 * @date 2023-01-10
 */
public interface BdglPeopleRecallAuditlogMapper 
{
    /**
     * 查询人员调出审批
     * 
     * @param id 人员调出审批主键
     * @return 人员调出审批
     */
    public BdglPeopleRecallAuditlog selectBdglPeopleRecallAuditlogById(Integer id);

    /**
     * 查询人员调出审批列表
     * 
     * @param bdglPeopleRecallAuditlog 人员调出审批
     * @return 人员调出审批集合
     */
    public List<BdglPeopleRecallAuditlog> selectBdglPeopleRecallAuditlogList(BdglPeopleRecallAuditlog bdglPeopleRecallAuditlog);

    /**
     * 新增人员调出审批
     * 
     * @param bdglPeopleRecallAuditlog 人员调出审批
     * @return 结果
     */
    public int insertBdglPeopleRecallAuditlog(BdglPeopleRecallAuditlog bdglPeopleRecallAuditlog);

    /**
     * 修改人员调出审批
     * 
     * @param bdglPeopleRecallAuditlog 人员调出审批
     * @return 结果
     */
    public int updateBdglPeopleRecallAuditlog(BdglPeopleRecallAuditlog bdglPeopleRecallAuditlog);

    /**
     * 删除人员调出审批
     * 
     * @param id 人员调出审批主键
     * @return 结果
     */
    public int deleteBdglPeopleRecallAuditlogById(Integer id);

    /**
     * 批量删除人员调出审批
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglPeopleRecallAuditlogByIds(Integer[] ids);
}
