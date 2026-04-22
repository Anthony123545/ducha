package com.supervision.medicalhealth.mapper;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglDiagnose;

/**
 * 外诊管理Mapper接口
 * 
 * @author hr
 * @date 2022-02-14
 */
public interface BdglDiagnoseMapper 
{
    /**
     * 查询外诊管理
     * 
     * @param id 外诊管理主键
     * @return 外诊管理
     */
    public BdglDiagnose selectBdglDiagnoseById(Integer id);

    /**
     * 查询外诊管理列表
     * 
     * @param bdglDiagnose 外诊管理
     * @return 外诊管理集合
     */
    public List<BdglDiagnose> selectBdglDiagnoseList(BdglDiagnose bdglDiagnose);

    /**
     * 新增外诊管理
     * 
     * @param bdglDiagnose 外诊管理
     * @return 结果
     */
    public int insertBdglDiagnose(BdglDiagnose bdglDiagnose);

    /**
     * 修改外诊管理
     * 
     * @param bdglDiagnose 外诊管理
     * @return 结果
     */
    public int updateBdglDiagnose(BdglDiagnose bdglDiagnose);

    /**
     * 删除外诊管理
     * 
     * @param id 外诊管理主键
     * @return 结果
     */
    public int deleteBdglDiagnoseById(Integer id);

    /**
     * 批量删除外诊管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglDiagnoseByIds(Integer[] ids);
}
