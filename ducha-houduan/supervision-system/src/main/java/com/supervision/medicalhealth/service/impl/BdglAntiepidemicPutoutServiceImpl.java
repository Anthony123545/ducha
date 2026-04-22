package com.supervision.medicalhealth.service.impl;

import java.util.Date;
import java.util.List;

import com.supervision.medicalhealth.domain.BdglAntiepidemicArchives;
import com.supervision.medicalhealth.domain.BdglAntiepidemicPutin;
import com.supervision.medicalhealth.domain.BdglDurgPutin;
import com.supervision.medicalhealth.mapper.BdglAntiepidemicArchivesMapper;
import com.supervision.medicalhealth.mapper.BdglAntiepidemicPutinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglAntiepidemicPutoutMapper;
import com.supervision.medicalhealth.domain.BdglAntiepidemicPutout;
import com.supervision.medicalhealth.service.IBdglAntiepidemicPutoutService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 防疫用品发放登记Service业务层处理
 * 
 * @author hr
 * @date 2022-02-14
 */
@Transactional
@Service
public class BdglAntiepidemicPutoutServiceImpl implements IBdglAntiepidemicPutoutService 
{
    @Autowired
    private BdglAntiepidemicPutoutMapper bdglAntiepidemicPutoutMapper;
    @Autowired
    private BdglAntiepidemicArchivesMapper bdglAntiepidemicArchivesMapper;
    @Autowired
    private BdglAntiepidemicPutinMapper antiepidemicPutinMapper;

    /**
     * 查询防疫用品发放登记
     * 
     * @param id 防疫用品发放登记主键
     * @return 防疫用品发放登记
     */
    @Override
    public BdglAntiepidemicPutout selectBdglAntiepidemicPutoutById(Long id)
    {
        return bdglAntiepidemicPutoutMapper.selectBdglAntiepidemicPutoutById(id);
    }

    /**
     * 查询防疫用品发放登记列表
     * 
     * @param bdglAntiepidemicPutout 防疫用品发放登记
     * @return 防疫用品发放登记
     */
    @Override
    public List<BdglAntiepidemicPutout> selectBdglAntiepidemicPutoutList(BdglAntiepidemicPutout bdglAntiepidemicPutout)
    {
        return bdglAntiepidemicPutoutMapper.selectBdglAntiepidemicPutoutList(bdglAntiepidemicPutout);
    }

    /**
     * 新增防疫用品发放登记
     * 
     * @param bdglAntiepidemicPutout 防疫用品发放登记
     * @return 结果
     */
    @Override
    public int insertBdglAntiepidemicPutout(BdglAntiepidemicPutout bdglAntiepidemicPutout)
    {
        /*----------------------更新入库数据----------------------------*/
        BdglAntiepidemicPutin bdglDurgPutin1 = new BdglAntiepidemicPutin();
        bdglDurgPutin1.setPutNumber(bdglAntiepidemicPutout.getNumber());
        bdglDurgPutin1.setState("0");
        bdglDurgPutin1.setExpirationTime(new Date());
        List<BdglAntiepidemicPutin> bdglDurgPutins = antiepidemicPutinMapper.selectBdglDurgPutinListweiGuoQi(bdglDurgPutin1);
        Integer putOut2 = bdglAntiepidemicPutout.getPutOut();
        for (BdglAntiepidemicPutin bdglDurgPutin : bdglDurgPutins) {
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
            antiepidemicPutinMapper.updateBdglAntiepidemicPutin(bdglDurgPutin);
        }
        /*----------------------更新入库数据----------------------------*/
        /*----------------------------更新防疫用品库存档案----------------------------------------*/
        BdglAntiepidemicArchives bdglAntiepidemicArchives1 = bdglAntiepidemicArchivesMapper.selectBdglAntiepidemicArchivesById(Long.valueOf(bdglAntiepidemicPutout.getDurgArchivesId()));
        //入库数量
        bdglAntiepidemicArchives1.setAmount(bdglAntiepidemicArchives1.getAmount()-bdglAntiepidemicPutout.getPutOut());
        //库存数量
        bdglAntiepidemicArchives1.setPutOut(bdglAntiepidemicArchives1.getPutOut()+bdglAntiepidemicPutout.getPutOut());
        bdglAntiepidemicArchivesMapper.updateBdglAntiepidemicArchives(bdglAntiepidemicArchives1);
        /*----------------------------更新防疫用品库存档案----------------------------------------*/
        bdglAntiepidemicPutout.setUnitId1(bdglAntiepidemicArchives1.getUnitId());
        bdglAntiepidemicPutout.setUnitName1(bdglAntiepidemicArchives1.getUnitName());

        return bdglAntiepidemicPutoutMapper.insertBdglAntiepidemicPutout(bdglAntiepidemicPutout);
    }

    /**
     * 修改防疫用品发放登记
     * 
     * @param bdglAntiepidemicPutout 防疫用品发放登记
     * @return 结果
     */
    @Override
    public int updateBdglAntiepidemicPutout(BdglAntiepidemicPutout bdglAntiepidemicPutout)
    {
        return bdglAntiepidemicPutoutMapper.updateBdglAntiepidemicPutout(bdglAntiepidemicPutout);
    }

    /**
     * 批量删除防疫用品发放登记
     * 
     * @param ids 需要删除的防疫用品发放登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglAntiepidemicPutoutByIds(Long[] ids)
    {
        return bdglAntiepidemicPutoutMapper.deleteBdglAntiepidemicPutoutByIds(ids);
    }

    /**
     * 删除防疫用品发放登记信息
     * 
     * @param id 防疫用品发放登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglAntiepidemicPutoutById(Long id)
    {
        return bdglAntiepidemicPutoutMapper.deleteBdglAntiepidemicPutoutById(id);
    }
}
