package com.supervision.medicalhealth.service.impl;

import java.util.Date;
import java.util.List;

import com.supervision.common.utils.DateUtils;
import com.supervision.medicalhealth.domain.BdglConsumeArchives;
import com.supervision.medicalhealth.domain.BdglConsumePutin;
import com.supervision.medicalhealth.domain.BdglDurgPutin;
import com.supervision.medicalhealth.mapper.BdglConsumeArchivesMapper;
import com.supervision.medicalhealth.mapper.BdglConsumePutinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglConsumePutoutMapper;
import com.supervision.medicalhealth.domain.BdglConsumePutout;
import com.supervision.medicalhealth.service.IBdglConsumePutoutService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 耗材出库登记Service业务层处理
 * 
 * @author hr
 * @date 2022-02-12
 */
@Transactional
@Service
public class BdglConsumePutoutServiceImpl implements IBdglConsumePutoutService 
{
    @Autowired
    private BdglConsumePutoutMapper bdglConsumePutoutMapper;
    @Autowired
    private BdglConsumeArchivesMapper bdglConsumeArchivesMapper;
    @Autowired
    private BdglConsumePutinMapper consumePutinMapper;

    /**
     * 查询耗材出库登记
     * 
     * @param id 耗材出库登记主键
     * @return 耗材出库登记
     */
    @Override
    public BdglConsumePutout selectBdglConsumePutoutById(Long id)
    {
        return bdglConsumePutoutMapper.selectBdglConsumePutoutById(id);
    }

    /**
     * 查询耗材出库登记列表
     * 
     * @param bdglConsumePutout 耗材出库登记
     * @return 耗材出库登记
     */
    @Override
    public List<BdglConsumePutout> selectBdglConsumePutoutList(BdglConsumePutout bdglConsumePutout)
    {
        return bdglConsumePutoutMapper.selectBdglConsumePutoutList(bdglConsumePutout);
    }

    /**
     * 新增耗材出库登记
     * 
     * @param bdglConsumePutout 耗材出库登记
     * @return 结果
     */
    @Override
    public int insertBdglConsumePutout(BdglConsumePutout bdglConsumePutout)
    {
        /*------------------------更新入库数据------------------------------*/
        BdglConsumePutin bdglDurgPutin1 = new BdglConsumePutin();
        bdglDurgPutin1.setNumber(bdglConsumePutout.getNumber());
        bdglDurgPutin1.setStatus("0");
        bdglDurgPutin1.setOverduedate(new Date());
        List<BdglConsumePutin> bdglDurgPutins = consumePutinMapper.selectweiGuoQiList(bdglDurgPutin1);
        Integer putOut2 = bdglConsumePutout.getPutOut();
        for (BdglConsumePutin bdglDurgPutin : bdglDurgPutins) {
            if(putOut2<=0){
                break;
            }
            Integer surplus = bdglDurgPutin.getSurplus();
            if(bdglDurgPutin.getSurplus()<=putOut2){
                Integer surplus1 = bdglDurgPutin.getSurplus();
                bdglDurgPutin.setSurplus(bdglDurgPutin.getSurplus()-bdglDurgPutin.getSurplus());
                putOut2=(putOut2-surplus1);
                System.out.println(putOut2);
            }else{
                bdglDurgPutin.setSurplus(bdglDurgPutin.getSurplus()-putOut2);
                putOut2=0;
            }
            consumePutinMapper.updateBdglConsumePutin(bdglDurgPutin);
        }
        /*------------------------更新入库数据------------------------------*/

        /*-------------------------修改库存数据-------------------------*/
        BdglConsumeArchives bdglConsumeArchives = bdglConsumeArchivesMapper.selectBdglConsumeArchivesById(Long.valueOf(bdglConsumePutout.getDurgArchivesId()));
        bdglConsumeArchives.setAmount(bdglConsumeArchives.getAmount()-bdglConsumePutout.getPutOut());
        bdglConsumeArchives.setPutOut(bdglConsumeArchives.getPutOut()+bdglConsumePutout.getPutOut());
        bdglConsumeArchivesMapper.updateBdglConsumeArchives(bdglConsumeArchives);
        /*-------------------------修改库存数据-------------------------*/
        bdglConsumePutout.setCreateTime(DateUtils.getNowDate());
        return bdglConsumePutoutMapper.insertBdglConsumePutout(bdglConsumePutout);
    }

    /**
     * 修改耗材出库登记
     * 
     * @param bdglConsumePutout 耗材出库登记
     * @return 结果
     */
    @Override
    public int updateBdglConsumePutout(BdglConsumePutout bdglConsumePutout)
    {
        bdglConsumePutout.setUpdateTime(DateUtils.getNowDate());
        return bdglConsumePutoutMapper.updateBdglConsumePutout(bdglConsumePutout);
    }

    /**
     * 批量删除耗材出库登记
     * 
     * @param ids 需要删除的耗材出库登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglConsumePutoutByIds(Long[] ids)
    {
        return bdglConsumePutoutMapper.deleteBdglConsumePutoutByIds(ids);
    }

    /**
     * 删除耗材出库登记信息
     * 
     * @param id 耗材出库登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglConsumePutoutById(Long id)
    {
        return bdglConsumePutoutMapper.deleteBdglConsumePutoutById(id);
    }
}
