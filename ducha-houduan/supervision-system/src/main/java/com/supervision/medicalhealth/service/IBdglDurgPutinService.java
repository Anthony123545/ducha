package com.supervision.medicalhealth.service;

import com.supervision.medicalhealth.domain.BdglDurgArchives;
import com.supervision.medicalhealth.domain.BdglDurgPutin;

import java.util.List;

/**
 * 药品入库登记Service接口
 * 
 * @author hr
 * @date 2022-02-12
 */
public interface IBdglDurgPutinService 
{
    /**
     * 查询药品入库登记
     * 
     * @param id 药品入库登记主键
     * @return 药品入库登记
     */
    public BdglDurgPutin selectBdglDurgPutinById(Long id);

    /**
     * 查询药品入库登记列表
     * 
     * @param bdglDurgPutin 药品入库登记
     * @return 药品入库登记集合
     */
    public List<BdglDurgPutin> selectBdglDurgPutinList(BdglDurgPutin bdglDurgPutin);

    /**
     * 新增药品入库登记
     * 
     * @param bdglDurgPutin 药品入库登记
     * @return 结果
     */
    public int insertBdglDurgPutin(BdglDurgPutin bdglDurgPutin);

    /**
     * 修改药品入库登记
     * 
     * @param bdglDurgPutin 药品入库登记
     * @return 结果
     */
    public int updateBdglDurgPutin(BdglDurgPutin bdglDurgPutin);

    /**
     * 批量删除药品入库登记
     * 
     * @param ids 需要删除的药品入库登记主键集合
     * @return 结果
     */
    public int deleteBdglDurgPutinByIds(Long[] ids);

    /**
     * 删除药品入库登记信息
     * 
     * @param id 药品入库登记主键
     * @return 结果
     */
    public int deleteBdglDurgPutinById(Long id);
    /**
     *查询药品过期情况
     **/
    List<BdglDurgPutin> selectGuoQiList(BdglDurgPutin bdglDurgPutin);
    /**
     * 过期药品销毁
     * */
    int yaoPinXiaoHui(Long id);

    int add(BdglDurgPutin bdglDurgPutin);

}
