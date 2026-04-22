package com.supervision.peopleManage.service.impl;

import java.util.List;

import com.supervision.common.exception.ServiceException;
import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.SecurityUtils;
import com.supervision.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleManage.mapper.BdglPeopleTypeStatisticsMapper;
import com.supervision.peopleManage.domain.BdglPeopleTypeStatistics;
import com.supervision.peopleManage.service.IBdglPeopleTypeStatisticsService;

/**
 * 人员类别数量Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-29
 */
@Service
public class BdglPeopleTypeStatisticsServiceImpl implements IBdglPeopleTypeStatisticsService
{
    @Autowired
    private BdglPeopleTypeStatisticsMapper bdglPeopleTypeStatisticsMapper;

    /**
     * 查询人员类别数量
     *
     * @param id 人员类别数量主键
     * @return 人员类别数量
     */
    @Override
    public BdglPeopleTypeStatistics selectBdglPeopleTypeStatisticsById(Long id)
    {
        return bdglPeopleTypeStatisticsMapper.selectBdglPeopleTypeStatisticsById(id);
    }

    /**
     * 查询人员类别数量列表
     *
     * @param bdglPeopleTypeStatistics 人员类别数量
     * @return 人员类别数量
     */
    @Override
    public List<BdglPeopleTypeStatistics> selectBdglPeopleTypeStatisticsList(BdglPeopleTypeStatistics bdglPeopleTypeStatistics)
    {
        return bdglPeopleTypeStatisticsMapper.selectBdglPeopleTypeStatisticsList(bdglPeopleTypeStatistics);
    }

    /**
     * 新增人员类别数量
     *
     * @param bdglPeopleTypeStatistics 人员类别数量
     * @return 结果
     */
    @Override
    public int insertBdglPeopleTypeStatistics(BdglPeopleTypeStatistics bdglPeopleTypeStatistics)
    {
        if (bdglPeopleTypeStatistics.getUnitId()==null){
            throw new ServiceException("请选择单位");
        }
        bdglPeopleTypeStatistics.setCreateTime(DateUtils.getNowDate());
        bdglPeopleTypeStatistics.setCreateBy(SecurityUtils.getUsername());

        BdglPeopleTypeStatistics selectBdglPeopleTypeStatistics = new BdglPeopleTypeStatistics();
        selectBdglPeopleTypeStatistics.setUnitId(bdglPeopleTypeStatistics.getUnitId());
        List<BdglPeopleTypeStatistics> selectBdglPeopleTypeStatisticsList = bdglPeopleTypeStatisticsMapper.selectBdglPeopleTypeStatisticsList(selectBdglPeopleTypeStatistics);
        if (selectBdglPeopleTypeStatisticsList.size()>0){
            throw new ServiceException("单位设置重复");
        }

        return bdglPeopleTypeStatisticsMapper.insertBdglPeopleTypeStatistics(bdglPeopleTypeStatistics);
    }

    /**
     * 修改人员类别数量
     *
     * @param bdglPeopleTypeStatistics 人员类别数量
     * @return 结果
     */
    @Override
    public int updateBdglPeopleTypeStatistics(BdglPeopleTypeStatistics bdglPeopleTypeStatistics)
    {
        return bdglPeopleTypeStatisticsMapper.updateBdglPeopleTypeStatistics(bdglPeopleTypeStatistics);
    }

    /**
     * 批量删除人员类别数量
     *
     * @param ids 需要删除的人员类别数量主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleTypeStatisticsByIds(Long[] ids)
    {
        return bdglPeopleTypeStatisticsMapper.deleteBdglPeopleTypeStatisticsByIds(ids);
    }

    /**
     * 删除人员类别数量信息
     *
     * @param id 人员类别数量主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleTypeStatisticsById(Long id)
    {
        return bdglPeopleTypeStatisticsMapper.deleteBdglPeopleTypeStatisticsById(id);
    }
}
