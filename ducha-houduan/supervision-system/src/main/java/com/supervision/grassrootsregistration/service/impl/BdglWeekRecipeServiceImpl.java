package com.supervision.grassrootsregistration.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglWeekRecipeMapper;
import com.supervision.grassrootsregistration.domain.BdglWeekRecipe;
import com.supervision.grassrootsregistration.service.IBdglWeekRecipeService;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.system.mapper.SysDeptMapper;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;

/**
 * 一周食谱 Service 业务层处理
 *
 * @author supervision
 * @date 2026-03-27
 */
@Service
public class BdglWeekRecipeServiceImpl implements IBdglWeekRecipeService
{
    @Autowired
    private BdglWeekRecipeMapper bdglWeekRecipeMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private BdglPeopleMapper peopleMapper;

    /**
     * 查询一周食谱
     *
     * @param id 一周食谱主键
     * @return 一周食谱
     */
    @Override
    public BdglWeekRecipe selectBdglWeekRecipeById(Integer id)
    {
        BdglWeekRecipe bdglWeekRecipe = bdglWeekRecipeMapper.selectBdglWeekRecipeById(id);
        if (bdglWeekRecipe != null) {
            // 获取司务长姓名
            if (bdglWeekRecipe.getMessOfficer() != null) {
                BdglPeople people = peopleMapper.selectBdglPeopleById(bdglWeekRecipe.getMessOfficer());
                if (people != null) {
                    bdglWeekRecipe.setMessOfficerName(people.getName());
                }
            }
            // 获取班长姓名
            if (bdglWeekRecipe.getPeopleId() != null) {
                BdglPeople people = peopleMapper.selectBdglPeopleById(bdglWeekRecipe.getPeopleId());
                if (people != null) {
                    bdglWeekRecipe.setPeopleName(people.getName());
                }
            }
        }
        return bdglWeekRecipe;
    }

    /**
     * 查询一周食谱列表
     *
     * @param bdglWeekRecipe 一周食谱
     * @return 一周食谱
     */
    @Override
    public List<BdglWeekRecipe> selectBdglWeekRecipeList(BdglWeekRecipe bdglWeekRecipe)
    {
        return bdglWeekRecipeMapper.selectBdglWeekRecipeList(bdglWeekRecipe);
    }

    /**
     * 新增一周食谱
     *
     * @param bdglWeekRecipe 一周食谱
     * @return 结果
     */
    @Override
    public int insertBdglWeekRecipe(BdglWeekRecipe bdglWeekRecipe)
    {
        bdglWeekRecipe.setCreatetime(new Date());
        // 获取单位名称
        if (bdglWeekRecipe.getUnitId() != null) {
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglWeekRecipe.getUnitId()));
            if (sysDept != null) {
                bdglWeekRecipe.setUnitName(sysDept.getDeptName());
            }
        }
        // 获取司务长姓名
        if (bdglWeekRecipe.getMessOfficer() != null) {
            BdglPeople people = peopleMapper.selectBdglPeopleById(bdglWeekRecipe.getMessOfficer());
            if (people != null) {
                bdglWeekRecipe.setMessOfficerName(people.getName());
            }
        }
        // 获取班长姓名
        if (bdglWeekRecipe.getPeopleId() != null) {
            BdglPeople people = peopleMapper.selectBdglPeopleById(bdglWeekRecipe.getPeopleId());
            if (people != null) {
                bdglWeekRecipe.setPeopleName(people.getName());
            }
        }
        return bdglWeekRecipeMapper.insertBdglWeekRecipe(bdglWeekRecipe);
    }

    /**
     * 修改一周食谱
     *
     * @param bdglWeekRecipe 一周食谱
     * @return 结果
     */
    @Override
    public int updateBdglWeekRecipe(BdglWeekRecipe bdglWeekRecipe)
    {
        bdglWeekRecipe.setUpdatetime(new Date());
        // 获取单位名称
        if (bdglWeekRecipe.getUnitId() != null) {
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglWeekRecipe.getUnitId()));
            if (sysDept != null) {
                bdglWeekRecipe.setUnitName(sysDept.getDeptName());
            }
        }
        // 获取司务长姓名
        if (bdglWeekRecipe.getMessOfficer() != null) {
            BdglPeople people = peopleMapper.selectBdglPeopleById(bdglWeekRecipe.getMessOfficer());
            if (people != null) {
                bdglWeekRecipe.setMessOfficerName(people.getName());
            }
        }
        // 获取班长姓名
        if (bdglWeekRecipe.getPeopleId() != null) {
            BdglPeople people = peopleMapper.selectBdglPeopleById(bdglWeekRecipe.getPeopleId());
            if (people != null) {
                bdglWeekRecipe.setPeopleName(people.getName());
            }
        }
        return bdglWeekRecipeMapper.updateBdglWeekRecipe(bdglWeekRecipe);
    }

    /**
     * 批量删除一周食谱
     *
     * @param ids 需要删除的一周食谱主键集合
     * @return 结果
     */
    @Override
    public int deleteBdglWeekRecipeByIds(Integer[] ids)
    {
        return bdglWeekRecipeMapper.deleteBdglWeekRecipeByIds(ids);
    }

    /**
     * 删除一周食谱信息
     *
     * @param id 一周食谱主键
     * @return 结果
     */
    @Override
    public int deleteBdglWeekRecipeById(Integer id)
    {
        return bdglWeekRecipeMapper.deleteBdglWeekRecipeById(id);
    }
}
