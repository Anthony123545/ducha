package com.supervision.medicalhealth.mapper;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglAntiepidemicPutin;

/**
 * 防疫用品入库登记Mapper接口
 * 
 * @author hr
 * @date 2022-02-14
 */
public interface BdglAntiepidemicPutinMapper 
{
    /**
     * 查询防疫用品入库登记
     * 
     * @param id 防疫用品入库登记主键
     * @return 防疫用品入库登记
     */
    public BdglAntiepidemicPutin selectBdglAntiepidemicPutinById(Long id);

    /**
     * 查询防疫用品入库登记列表
     * 
     * @param bdglAntiepidemicPutin 防疫用品入库登记
     * @return 防疫用品入库登记集合
     */
    public List<BdglAntiepidemicPutin> selectBdglAntiepidemicPutinList(BdglAntiepidemicPutin bdglAntiepidemicPutin);

    /**
     * 新增防疫用品入库登记
     * 
     * @param bdglAntiepidemicPutin 防疫用品入库登记
     * @return 结果
     */
    public int insertBdglAntiepidemicPutin(BdglAntiepidemicPutin bdglAntiepidemicPutin);

    /**
     * 修改防疫用品入库登记
     * 
     * @param bdglAntiepidemicPutin 防疫用品入库登记
     * @return 结果
     */
    public int updateBdglAntiepidemicPutin(BdglAntiepidemicPutin bdglAntiepidemicPutin);

    /**
     * 删除防疫用品入库登记
     * 
     * @param id 防疫用品入库登记主键
     * @return 结果
     */
    public int deleteBdglAntiepidemicPutinById(Long id);

    /**
     * 批量删除防疫用品入库登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglAntiepidemicPutinByIds(Long[] ids);

    List<BdglAntiepidemicPutin> sselectGuoQi(BdglAntiepidemicPutin bdglDurgPutin);

    List<BdglAntiepidemicPutin> selectBdglDurgPutinListweiGuoQi(BdglAntiepidemicPutin bdglDurgPutin1);
}
