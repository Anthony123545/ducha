package com.supervision.medicalhealth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.utils.DateUtils;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglRecuperateMapper;
import com.supervision.medicalhealth.domain.BdglRecuperate;
import com.supervision.medicalhealth.service.IBdglRecuperateService;

/**
 * 疗养管理Service业务层处理
 *
 * @author hr
 * @date 2022-02-14
 */
@Service
public class BdglRecuperateServiceImpl implements IBdglRecuperateService {
    @Autowired
    private BdglRecuperateMapper bdglRecuperateMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询疗养管理
     *
     * @param id 疗养管理主键
     * @return 疗养管理
     */
    @Override
    public BdglRecuperate selectBdglRecuperateById(Integer id) {
        return bdglRecuperateMapper.selectBdglRecuperateById(id);
    }

    /**
     * 查询疗养管理列表
     *
     * @param bdglRecuperate 疗养管理
     * @return 疗养管理
     */
    @Override
    public List<BdglRecuperate> selectBdglRecuperateList(BdglRecuperate bdglRecuperate) {
        return bdglRecuperateMapper.selectBdglRecuperateList(bdglRecuperate);
    }

    /**
     * 新增疗养管理
     *
     * @param bdglRecuperate 疗养管理
     * @return 结果
     */
    @Override
    public int insertBdglRecuperate(BdglRecuperate bdglRecuperate) {
        if (bdglRecuperate.getUnitId() != null) {
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglRecuperate.getUnitId()));
            bdglRecuperate.setUnitName(sysDept.getDeptName());//疗养人所在部门名称
            SysDept sysDept1 = deptMapper.selectDeptById(Long.valueOf(bdglRecuperate.getUnitId1()));
            bdglRecuperate.setUnitName1(sysDept1.getDeptName());//一级审批人部门名称
            SysDept sysDept2 = deptMapper.selectDeptById(Long.valueOf(bdglRecuperate.getUnitId2()));
            bdglRecuperate.setUnitName2(sysDept2.getDeptName());//二级审批人部门名称
            SysDept sysDept3 = deptMapper.selectDeptById(Long.valueOf(bdglRecuperate.getUnitId3()));
            bdglRecuperate.setUnitName3(sysDept3.getDeptName());//三级审批人部门名称
        }
        bdglRecuperate.setCreatetime(new Date());//创建时间
        return bdglRecuperateMapper.insertBdglRecuperate(bdglRecuperate);
    }

    /**
     * 修改疗养管理
     *
     * @param bdglRecuperate 疗养管理
     * @return 结果
     */
    @Override
    public int updateBdglRecuperate(BdglRecuperate bdglRecuperate) {
        bdglRecuperate.setUpdatetime(new Date());
        return bdglRecuperateMapper.updateBdglRecuperate(bdglRecuperate);
    }

    /**
     * 批量删除疗养管理
     *
     * @param ids 需要删除的疗养管理主键
     * @return 结果
     */
    @Override
    public int deleteBdglRecuperateByIds(Integer[] ids) {
        return bdglRecuperateMapper.deleteBdglRecuperateByIds(ids);
    }

    /**
     * 删除疗养管理信息
     *
     * @param id 疗养管理主键
     * @return 结果
     */
    @Override
    public int deleteBdglRecuperateById(Integer id) {
        return bdglRecuperateMapper.deleteBdglRecuperateById(id);
    }
}
