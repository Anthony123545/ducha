package com.supervision.medicalhealth.service.impl;

import java.util.Date;
import java.util.List;

import com.supervision.common.utils.DateUtils;
import com.supervision.medicalhealth.domain.BdglDurgArchives;
import com.supervision.medicalhealth.domain.BdglDurgPutin;
import com.supervision.medicalhealth.domain.BdglDurgPutout;
import com.supervision.medicalhealth.mapper.BdglDurgArchivesMapper;
import com.supervision.medicalhealth.mapper.BdglDurgPutinMapper;
import com.supervision.medicalhealth.mapper.BdglDurgPutoutMapper;
import com.supervision.medicalhealth.service.IBdglDurgPutoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 药品出库登记Service业务层处理
 * 
 * @author hr
 * @date 2022-02-12
 */
@Transactional
@Service
public class BdglDurgPutoutServiceImpl implements IBdglDurgPutoutService
{
    @Autowired
    private BdglDurgPutoutMapper bdglDurgPutoutMapper;
    @Autowired
    private BdglDurgArchivesMapper bdglDurgArchivesMapper;
    @Autowired
    private BdglDurgPutinMapper durgPutinMapper;

    /**
     * 查询药品出库登记
     * 
     * @param id 药品出库登记主键
     * @return 药品出库登记
     */
    @Override
    public BdglDurgPutout selectBdglDurgPutoutById(Long id)
    {
        return bdglDurgPutoutMapper.selectBdglDurgPutoutById(id);
    }

    /**
     * 查询药品出库登记列表
     * 
     * @param bdglDurgPutout 药品出库登记
     * @return 药品出库登记
     */
    @Override
    public List<BdglDurgPutout> selectBdglDurgPutoutList(BdglDurgPutout bdglDurgPutout)
    {
        return bdglDurgPutoutMapper.selectBdglDurgPutoutList(bdglDurgPutout);
    }

    /**
     * 新增药品出库登记
     * 
     * @param bdglDurgPutout 药品出库登记
     * @return 结果
     */
    @Override
    public int  insertBdglDurgPutout(BdglDurgPutout bdglDurgPutout)
    {

        /*-----------------------------更新入库数量---------------------------------*/
        BdglDurgPutin bdglDurgPutin1 = new BdglDurgPutin();
        bdglDurgPutin1.setNumber(bdglDurgPutout.getNumber());
        bdglDurgPutin1.setStatus("0");
        bdglDurgPutin1.setOverduedate(new Date());
        List<BdglDurgPutin> bdglDurgPutins = durgPutinMapper.selectBdglDurgPutinListweiGuoQi(bdglDurgPutin1);
        Integer putOut2 = bdglDurgPutout.getPutOut();
        for (BdglDurgPutin bdglDurgPutin : bdglDurgPutins) {
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
            durgPutinMapper.updateBdglDurgPutin(bdglDurgPutin);
        }
        /*-----------------------------更新入库数量---------------------------------*/
        //更新库存数量
        BdglDurgArchives bdglDurgArchives = bdglDurgArchivesMapper.selectBdglDurgArchivesById(Long.valueOf(bdglDurgPutout.getDurgArchivesId()));
        //出库数量 put_out
        Integer putOut = bdglDurgPutout.getPutOut();
        Integer putOut1 = bdglDurgArchives.getPutOut();
        bdglDurgArchives.setPutOut(bdglDurgPutout.getPutOut()+bdglDurgArchives.getPutOut());
        //库存总数 amount
        bdglDurgArchives.setAmount(bdglDurgArchives.getAmount()-bdglDurgPutout.getPutOut());
        bdglDurgArchivesMapper.updateBdglDurgArchives(bdglDurgArchives);
        bdglDurgPutout.setCreateTime(DateUtils.getNowDate());
        bdglDurgPutout.setUnitId(bdglDurgArchives.getUnitId());
        bdglDurgPutout.setUnitName(bdglDurgArchives.getUnitName());
        return bdglDurgPutoutMapper.insertBdglDurgPutout(bdglDurgPutout);
    }

    /**
     * 修改药品出库登记
     * 
     * @param bdglDurgPutout 药品出库登记
     * @return 结果
     */
    @Override
    public int updateBdglDurgPutout(BdglDurgPutout bdglDurgPutout)
    {
        bdglDurgPutout.setUpdateTime(DateUtils.getNowDate());
        return bdglDurgPutoutMapper.updateBdglDurgPutout(bdglDurgPutout);
    }

    /**
     * 批量删除药品出库登记
     * 
     * @param ids 需要删除的药品出库登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglDurgPutoutByIds(Long[] ids)
    {
        return bdglDurgPutoutMapper.deleteBdglDurgPutoutByIds(ids);
    }

    /**
     * 删除药品出库登记信息
     * 
     * @param id 药品出库登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglDurgPutoutById(Long id)
    {
        return bdglDurgPutoutMapper.deleteBdglDurgPutoutById(id);
    }
}
