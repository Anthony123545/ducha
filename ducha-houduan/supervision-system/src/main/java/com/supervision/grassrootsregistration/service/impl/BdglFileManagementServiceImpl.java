package com.supervision.grassrootsregistration.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.grassrootsregistration.domain.BdglFileBorrowRecord;
import com.supervision.grassrootsregistration.mapper.BdglFileBorrowRecordMapper;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglFileManagementMapper;
import com.supervision.grassrootsregistration.domain.BdglFileManagement;
import com.supervision.grassrootsregistration.service.IBdglFileManagementService;

/**
 * 文件管理登计本Service业务层处理
 *
 * @author supervision
 * @date 2022-02-26
 */
@Service
public class BdglFileManagementServiceImpl implements IBdglFileManagementService
{
    @Autowired
    private BdglFileManagementMapper bdglFileManagementMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private BdglFileBorrowRecordMapper borrowRecordMapper;
    @Override
    public BdglFileManagement selectBdglFileManagementDetailById(Integer id) {

        // 1️⃣ 查主表
        BdglFileManagement file =
                bdglFileManagementMapper.selectBdglFileManagementById(id);

        if (file == null) {
            return null;
        }

        // 2️⃣ 根据 number 查借阅记录
        List<BdglFileBorrowRecord> records =
                borrowRecordMapper.selectByFileNumber(file.getNumber());

        // 3️⃣ 组装
        file.setBorrowRecords(records);

        return file;
    }

    /**
     * 查询文件管理登计本
     *
     * @param id 文件管理登计本主键
     * @return 文件管理登计本
     */
    @Override
    public BdglFileManagement selectBdglFileManagementById(Integer id)
    {
        return bdglFileManagementMapper.selectBdglFileManagementById(id);
    }

    @Override
    public List<BdglFileManagement> selectBdglSecrecyBunitunitid(Integer unitid,String year) {
        return bdglFileManagementMapper.selectBdglSecrecyBunitunitid(unitid,year);
    }

    /**
     * 查询文件管理登计本列表
     *
     * @param bdglFileManagement 文件管理登计本
     * @return 文件管理登计本
     */
    @Override
    public List<BdglFileManagement> selectBdglFileManagementList(BdglFileManagement bdglFileManagement)
    {
        return bdglFileManagementMapper.selectBdglFileManagementList(bdglFileManagement);
    }

    /**
     * 新增文件管理登计本
     *
     * @param bdglFileManagement 文件管理登计本
     * @return 结果
     */
    @Override
    public int insertBdglFileManagement(BdglFileManagement bdglFileManagement)
    {
        if(bdglFileManagement.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglFileManagement.getUnitId()));
            bdglFileManagement.setDeptName(sysDept.getDeptName());
        }
        bdglFileManagement.setCreatetime(new Date());
        return bdglFileManagementMapper.insertBdglFileManagement(bdglFileManagement);
    }

    /**
     * 修改文件管理登计本
     *
     * @param bdglFileManagement 文件管理登计本
     * @return 结果
     */
    @Override
    public int updateBdglFileManagement(BdglFileManagement bdglFileManagement)
    {
        bdglFileManagement.setCreatetime(new Date());
        if(bdglFileManagement.getUnitId()!=null){
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglFileManagement.getUnitId()));
            bdglFileManagement.setDeptName(sysDept.getDeptName());
        }
        return bdglFileManagementMapper.updateBdglFileManagement(bdglFileManagement);
    }

    /**
     * 批量删除文件管理登计本
     *
     * @param ids 需要删除的文件管理登计本主键
     * @return 结果
     */
    @Override
    public int deleteBdglFileManagementByIds(Integer[] ids)
    {
        return bdglFileManagementMapper.deleteBdglFileManagementByIds(ids);
    }

    /**
     * 删除文件管理登计本信息
     *
     * @param id 文件管理登计本主键
     * @return 结果
     */
    @Override
    public int deleteBdglFileManagementById(Integer id)
    {
        return bdglFileManagementMapper.deleteBdglFileManagementById(id);
    }

    /**
     * 领导查看文件登记本
     * @return
     */
    @Override
    public List<SysDeptDto> selectFileDocList(String year) {
        //所有营级单位
        List<Map<String, SysDept>> mapList = deptMapper.selectAllCamp();
        //根据营级单位ID查询当前单位下是否有数据
        List<SysDeptDto> dtoList = new ArrayList<>();
        mapList.forEach(item -> {
            SysDeptDto deptDto = BeanUtil.mapToBean(item, SysDeptDto.class, false);
            int deptId = deptDto.getDeptId().intValue();
            int countByunitId = bdglFileManagementMapper.selectlFileDocCountByunitId(deptId,year);
            if (countByunitId > 0) {
                dtoList.add(deptDto);
            }
        });
        return dtoList;
    }
}
