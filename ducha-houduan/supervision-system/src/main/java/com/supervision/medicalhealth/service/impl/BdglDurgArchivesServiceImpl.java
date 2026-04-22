package com.supervision.medicalhealth.service.impl;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.utils.DateUtils;
import com.supervision.medicalhealth.domain.BdglDurgArchives;
import com.supervision.medicalhealth.mapper.BdglDurgArchivesMapper;
import com.supervision.medicalhealth.service.IBdglDurgArchivesService;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 药品库存档案Service业务层处理
 *
 * @author hr
 * @date 2022-02-12
 */
@Service
public class BdglDurgArchivesServiceImpl implements IBdglDurgArchivesService {
    @Autowired
    private BdglDurgArchivesMapper bdglDurgArchivesMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询药品库存档案
     *
     * @param id 药品库存档案主键
     * @return 药品库存档案
     */
    @Override
    public BdglDurgArchives selectBdglDurgArchivesById(Long id) {
        return bdglDurgArchivesMapper.selectBdglDurgArchivesById(id);
    }

    /**
     * 查询药品库存档案列表
     *
     * @param bdglDurgArchives 药品库存档案
     * @return 药品库存档案
     */
    @Override
    public List<BdglDurgArchives> selectBdglDurgArchivesList(BdglDurgArchives bdglDurgArchives) {
        return bdglDurgArchivesMapper.selectBdglDurgArchivesList(bdglDurgArchives);
    }

    /**
     * 新增药品库存档案
     *
     * @param bdglDurgArchives 药品库存档案
     * @return 结果
     */
    @Override
    public int insertBdglDurgArchives(BdglDurgArchives bdglDurgArchives) {
        bdglDurgArchives.setCreateTime(DateUtils.getNowDate());
        if (bdglDurgArchives.getUnitId() != null) {
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglDurgArchives.getUnitId()));
            bdglDurgArchives.setUnitName(sysDept.getDeptName());
        }
        return bdglDurgArchivesMapper.insertBdglDurgArchives(bdglDurgArchives);
    }

    /**
     * 修改药品库存档案
     *
     * @param bdglDurgArchives 药品库存档案
     * @return 结果
     */
    @Override
    public int updateBdglDurgArchives(BdglDurgArchives bdglDurgArchives) {
        bdglDurgArchives.setUpdateTime(DateUtils.getNowDate());
        return bdglDurgArchivesMapper.updateBdglDurgArchives(bdglDurgArchives);
    }

    /**
     * 批量删除药品库存档案
     *
     * @param ids 需要删除的药品库存档案主键
     * @return 结果
     */
    @Override
    public int deleteBdglDurgArchivesByIds(Long[] ids) {
        return bdglDurgArchivesMapper.deleteBdglDurgArchivesByIds(ids);
    }

    /**
     * 删除药品库存档案信息
     *
     * @param id 药品库存档案主键
     * @return 结果
     */
    @Override
    public int deleteBdglDurgArchivesById(Long id) {
        return bdglDurgArchivesMapper.deleteBdglDurgArchivesById(id);
    }

    /**
     * 获取所有药品名称
     */
    @Override
    public BdglDurgArchives getYaoPingList(BdglDurgArchives bdglDurgArchive) {
        BdglDurgArchives list = bdglDurgArchivesMapper.selectBdglDurgArchivesByDurgNo(bdglDurgArchive);
        Map<String, Object> map = null;

        map = new HashMap<>();
        //品牌  厂家 规格 库房 单位
        if(list!=null){

            String yaoPingName = list.getDurgName() + "-" + list.getManufacturer() + "-" + list.getSpecifications() + "-" + list.getStoreHouse();
            list.setYaoPinName(yaoPingName);
        }

        return list;
    }

}
