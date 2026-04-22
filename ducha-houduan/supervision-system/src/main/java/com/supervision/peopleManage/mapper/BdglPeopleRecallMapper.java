package com.supervision.peopleManage.mapper;

import java.util.List;
import com.supervision.peopleManage.domain.BdglPeopleRecall;

/**
 * 人员调出Mapper接口
 *
 * @author ruoyi
 * @date 2023-01-10
 */
public interface BdglPeopleRecallMapper
{
    /**
     * 查询人员调出
     *
     * @param id 人员调出主键
     * @return 人员调出
     */
    public BdglPeopleRecall selectBdglPeopleRecallById(Integer id);

    /**
     * 查询人员调出列表
     *
     * @param bdglPeopleRecall 人员调出
     * @return 人员调出集合
     */
    public List<BdglPeopleRecall> selectBdglPeopleRecallList(BdglPeopleRecall bdglPeopleRecall);

    /**
     * 新增人员调出
     *
     * @param bdglPeopleRecall 人员调出
     * @return 结果
     */
    public int insertBdglPeopleRecall(BdglPeopleRecall bdglPeopleRecall);

    /**
     * 修改人员调出
     *
     * @param bdglPeopleRecall 人员调出
     * @return 结果
     */
    public int updateBdglPeopleRecall(BdglPeopleRecall bdglPeopleRecall);

    public List<BdglPeopleRecall> selectBdglPeopleID(BdglPeopleRecall bdglPeopleRecall);

    /**
     * 删除人员调出
     *
     * @param id 人员调出主键
     * @return 结果
     */
    public int deleteBdglPeopleRecallById(Integer id);

    /**
     * 批量删除人员调出
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglPeopleRecallByIds(Integer[] ids);
}
