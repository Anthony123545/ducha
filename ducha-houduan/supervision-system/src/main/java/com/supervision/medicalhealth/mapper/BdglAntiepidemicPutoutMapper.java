package com.supervision.medicalhealth.mapper;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglAntiepidemicPutout;

/**
 * 防疫用品发放登记Mapper接口
 * 
 * @author hr
 * @date 2022-02-14
 */
public interface BdglAntiepidemicPutoutMapper 
{
    /**
     * 查询防疫用品发放登记
     * 
     * @param id 防疫用品发放登记主键
     * @return 防疫用品发放登记
     */
    public BdglAntiepidemicPutout selectBdglAntiepidemicPutoutById(Long id);

    /**
     * 查询防疫用品发放登记列表
     * 
     * @param bdglAntiepidemicPutout 防疫用品发放登记
     * @return 防疫用品发放登记集合
     */
    public List<BdglAntiepidemicPutout> selectBdglAntiepidemicPutoutList(BdglAntiepidemicPutout bdglAntiepidemicPutout);

    /**
     * 新增防疫用品发放登记
     * 
     * @param bdglAntiepidemicPutout 防疫用品发放登记
     * @return 结果
     */
    public int insertBdglAntiepidemicPutout(BdglAntiepidemicPutout bdglAntiepidemicPutout);

    /**
     * 修改防疫用品发放登记
     * 
     * @param bdglAntiepidemicPutout 防疫用品发放登记
     * @return 结果
     */
    public int updateBdglAntiepidemicPutout(BdglAntiepidemicPutout bdglAntiepidemicPutout);

    /**
     * 删除防疫用品发放登记
     * 
     * @param id 防疫用品发放登记主键
     * @return 结果
     */
    public int deleteBdglAntiepidemicPutoutById(Long id);

    /**
     * 批量删除防疫用品发放登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglAntiepidemicPutoutByIds(Long[] ids);
}
