package com.supervision.peopleManage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleManage.mapper.BdglPeopleRecallMapper;
import com.supervision.peopleManage.domain.BdglPeopleRecall;
import com.supervision.peopleManage.service.IBdglPeopleRecallService;

/**
 * 人员调出Service业务层处理
 *
 * @author ruoyi
 * @date 2023-01-10
 */
@Service
public class BdglPeopleRecallServiceImpl implements IBdglPeopleRecallService
{
    @Autowired
    private BdglPeopleRecallMapper bdglPeopleRecallMapper;

    /**
     * 查询人员调出
     *
     * @param id 人员调出主键
     * @return 人员调出
     */
    @Override
    public BdglPeopleRecall selectBdglPeopleRecallById(Integer id)
    {
        return bdglPeopleRecallMapper.selectBdglPeopleRecallById(id);
    }

    @Override
    public List<BdglPeopleRecall> selectBdglPeopleID(BdglPeopleRecall bdglPeopleRecall) {
        return bdglPeopleRecallMapper.selectBdglPeopleID(bdglPeopleRecall);
    }


    /**
     * 查询人员调出列表
     *
     * @param bdglPeopleRecall 人员调出
     * @return 人员调出
     */
    @Override
    public List<BdglPeopleRecall> selectBdglPeopleRecallList(BdglPeopleRecall bdglPeopleRecall)
    {
        return bdglPeopleRecallMapper.selectBdglPeopleRecallList(bdglPeopleRecall);
    }

    /**
     * 新增人员调出
     *
     * @param bdglPeopleRecall 人员调出
     * @return 结果
     */
    @Override
    public int insertBdglPeopleRecall(BdglPeopleRecall bdglPeopleRecall)
    {
        return bdglPeopleRecallMapper.insertBdglPeopleRecall(bdglPeopleRecall);
    }

    /**
     * 修改人员调出
     *
     * @param bdglPeopleRecall 人员调出
     * @return 结果
     */
    @Override
    public int updateBdglPeopleRecall(BdglPeopleRecall bdglPeopleRecall)
    {
        return bdglPeopleRecallMapper.updateBdglPeopleRecall(bdglPeopleRecall);
    }

    /**
     * 批量删除人员调出
     *
     * @param ids 需要删除的人员调出主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleRecallByIds(Integer[] ids)
    {
        return bdglPeopleRecallMapper.deleteBdglPeopleRecallByIds(ids);
    }

    /**
     * 删除人员调出信息
     *
     * @param id 人员调出主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleRecallById(Integer id)
    {
        return bdglPeopleRecallMapper.deleteBdglPeopleRecallById(id);
    }
}
