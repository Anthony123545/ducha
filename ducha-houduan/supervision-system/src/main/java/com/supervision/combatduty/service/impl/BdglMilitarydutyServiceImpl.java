package com.supervision.combatduty.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import cn.hutool.core.util.StrUtil;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.text.Convert;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.system.mapper.SysDeptMapper;
import com.supervision.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.supervision.combatduty.mapper.BdglMilitarydutyMapper;
import com.supervision.combatduty.domain.BdglMilitaryduty;
import com.supervision.combatduty.service.IBdglMilitarydutyService;

import javax.annotation.Resource;

/**
 * 军车值班Service业务层处理
 *
 * @author supervision
 * @date 2022-03-11
 */
@Service
public class BdglMilitarydutyServiceImpl implements IBdglMilitarydutyService
{
    @Resource
    private BdglMilitarydutyMapper bdglMilitarydutyMapper;

    @Resource
    private SysDeptMapper deptMapper;

    @Resource
    private SysUserMapper userMapper;

    @Resource
    private BdglPeopleMapper bdglPeopleMapper;
    /**
     * 查询军车值班
     *
     * @param id 军车值班主键
     * @return 军车值班
     */
    @Override
    public BdglMilitaryduty selectBdglMilitarydutyById(Long id)
    {
        return bdglMilitarydutyMapper.selectBdglMilitarydutyById(id);
    }

    /**
     * 查询军车值班列表
     *
     * @param bdglMilitaryduty 军车值班
     * @return 军车值班
     */
    @Override
    public List<BdglMilitaryduty> selectBdglMilitarydutyList(BdglMilitaryduty bdglMilitaryduty) {
        return bdglMilitarydutyMapper.selectBdglMilitarydutyList(bdglMilitaryduty);
    }

    /**
     * 新增军车值班
     *
     * @param bdglMilitaryduty 军车值班
     * @return 结果
     */
    @Override
    public int insertBdglMilitaryduty(BdglMilitaryduty bdglMilitaryduty)
    {
        createMilitaryduty(bdglMilitaryduty);
        return bdglMilitarydutyMapper.insertBdglMilitaryduty(bdglMilitaryduty);
    }

    private void createMilitaryduty(BdglMilitaryduty bdglMilitaryduty){
        //车属单位ID
//        List<String> uid = bdglMilitaryduty.getUnitId();
//        //查询对应名称
//        StringJoiner unitNameBuild = new StringJoiner(",");
//        uid.forEach(item->{
//            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(item));
//            unitNameBuild.add(sysDept.getDeptName());
//        });
//        bdglMilitaryduty.setCarUnitId(String.join(",", uid));
//        //车属单位名称
//        bdglMilitaryduty.setCarUnitName(String.valueOf(unitNameBuild));

        String carUnitId = bdglMilitaryduty.getCarUnitId();
        SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(carUnitId));
        bdglMilitaryduty.setCarUnitName(sysDept.getDeptName());
        //司机ID
        List<String> sjIds = bdglMilitaryduty.getSjId();
        StringJoiner sjBuilder = new StringJoiner(",");
        sjIds.forEach(id-> {
//            SysUser sysUser = userMapper.selectUserById(Long.valueOf(id));
            BdglPeople bdglPeople = bdglPeopleMapper.selectBdglPeopleById(Long.valueOf(id));
            sjBuilder.add(bdglPeople.getName());
        });
        bdglMilitaryduty.setDriverId(String.join(",", sjIds));
        //司机名称
        bdglMilitaryduty.setDriverName(String.valueOf(sjBuilder));
        //手机号
        bdglMilitaryduty.setTelephone(String.join(",", bdglMilitaryduty.getPhone()));
    }
    /**
     * 修改军车值班
     *
     * @param bdglMilitaryduty 军车值班
     * @return 结果
     */
    @Override
    public int updateBdglMilitaryduty(BdglMilitaryduty bdglMilitaryduty) {
        createMilitaryduty(bdglMilitaryduty);
        return bdglMilitarydutyMapper.updateBdglMilitaryduty(bdglMilitaryduty);
    }

    /**
     * 批量删除军车值班
     *
     * @param ids 需要删除的军车值班主键
     * @return 结果
     */
    @Override
    public int deleteBdglMilitarydutyByIds(Long[] ids)
    {
        return bdglMilitarydutyMapper.deleteBdglMilitarydutyByIds(ids);
    }

    /**
     * 删除军车值班信息
     *
     * @param id 军车值班主键
     * @return 结果
     */
    @Override
    public int deleteBdglMilitarydutyById(Long id)
    {
        return bdglMilitarydutyMapper.deleteBdglMilitarydutyById(id);
    }
}
