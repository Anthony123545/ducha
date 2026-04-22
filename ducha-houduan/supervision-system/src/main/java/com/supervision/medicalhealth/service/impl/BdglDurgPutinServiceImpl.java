package com.supervision.medicalhealth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supervision.common.utils.DateUtils;
import com.supervision.medicalhealth.domain.BdglConsumeRuin;
import com.supervision.medicalhealth.domain.BdglDurgArchives;
import com.supervision.medicalhealth.domain.BdglDurgPutin;
import com.supervision.medicalhealth.domain.BdglDurgRuin;
import com.supervision.medicalhealth.mapper.BdglDurgArchivesMapper;
import com.supervision.medicalhealth.mapper.BdglDurgPutinMapper;
import com.supervision.medicalhealth.mapper.BdglDurgRuinMapper;
import com.supervision.medicalhealth.service.IBdglDurgPutinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 药品入库登记Service业务层处理
 *
 * @author hr
 * @date 2022-02-12
 */
@Transactional
@Service
public class BdglDurgPutinServiceImpl implements IBdglDurgPutinService {
    @Autowired
    private BdglDurgPutinMapper bdglDurgPutinMapper;
    @Autowired
    private BdglDurgArchivesMapper bdglDurgArchivesMapper;
    @Autowired
    private BdglDurgRuinMapper bdglDurgRuinMapper;

    /**
     * 查询药品入库登记
     *
     * @param id 药品入库登记主键
     * @return 药品入库登记
     */
    @Override
    public BdglDurgPutin selectBdglDurgPutinById(Long id) {
        return bdglDurgPutinMapper.selectBdglDurgPutinById(id);
    }

    /**
     * 查询药品入库登记列表
     *
     * @param bdglDurgPutin 药品入库登记
     * @return 药品入库登记
     */
    @Override
    public List<BdglDurgPutin> selectBdglDurgPutinList(BdglDurgPutin bdglDurgPutin) {
        return bdglDurgPutinMapper.selectBdglDurgPutinList(bdglDurgPutin);
    }

    /**
     * 新增药品入库登记
     *
     * @param bdglDurgPutin 药品入库登记
     * @return 结果
     */
    @Override
    public int insertBdglDurgPutin(BdglDurgPutin bdglDurgPutin) {
        bdglDurgPutin.setCreateTime(DateUtils.getNowDate());

        BdglDurgArchives bdglDurgArchives = bdglDurgArchivesMapper.selectBdglDurgArchivesById(Long.valueOf(bdglDurgPutin.getDurgArchivesId()));
        if(bdglDurgPutin.getOverduedate().getTime()<new Date().getTime()){
            if(bdglDurgArchives.getOverdue()==null){
                bdglDurgArchives.setOverdue(0);
            }
            bdglDurgArchives.setOverdue(bdglDurgArchives.getOverdue()+bdglDurgPutin.getPutIn());
        }

        int i = bdglDurgArchives.getPutIn() + bdglDurgPutin.getPutIn();
        //入库总数
        bdglDurgArchives.setPutIn(i);
        //库存总数
        bdglDurgArchives.setAmount(bdglDurgPutin.getPutIn() + bdglDurgArchives.getAmount());
        bdglDurgArchivesMapper.updateBdglDurgArchives(bdglDurgArchives);
        return bdglDurgPutinMapper.insertBdglDurgPutin(bdglDurgPutin);
    }

    /**
     * 修改药品入库登记
     *
     * @param bdglDurgPutin 药品入库登记
     * @return 结果
     */
    @Override
    public int updateBdglDurgPutin(BdglDurgPutin bdglDurgPutin) {
        BdglDurgPutin bdglDurgPutin1 = bdglDurgPutinMapper.selectBdglDurgPutinById(bdglDurgPutin.getId());
        Integer putIn = bdglDurgPutin1.getPutIn();
        Integer putIn1 = bdglDurgPutin.getPutIn();
        int i =  putIn1-putIn ;
        BdglDurgArchives bdglDurgArchives = bdglDurgArchivesMapper.selectBdglDurgArchivesById(Long.valueOf(bdglDurgPutin.getDurgArchivesId()));

        if(putIn>putIn1){
            bdglDurgPutin.setSurplus(bdglDurgPutin.getSurplus()+i);
            //入库总数
            bdglDurgArchives.setPutIn(bdglDurgArchives.getPutIn()+i);
            //库存总数
            bdglDurgArchives.setAmount(bdglDurgArchives.getAmount() + i);
            bdglDurgArchivesMapper.updateBdglDurgArchives(bdglDurgArchives);
        }else if(putIn<putIn1){
            bdglDurgArchives.setPutIn(bdglDurgArchives.getPutIn()+i);
            //库存总数
            bdglDurgArchives.setAmount(bdglDurgArchives.getAmount() +i);
            bdglDurgArchivesMapper.updateBdglDurgArchives(bdglDurgArchives);
            bdglDurgPutin.setSurplus(bdglDurgPutin.getSurplus()+i);

        }

        return bdglDurgPutinMapper.updateBdglDurgPutin(bdglDurgPutin);
    }

    /**
     * 批量删除药品入库登记
     *
     * @param ids 需要删除的药品入库登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglDurgPutinByIds(Long[] ids) {
        return bdglDurgPutinMapper.deleteBdglDurgPutinByIds(ids);
    }

    /**
     * 删除药品入库登记信息
     *
     * @param id 药品入库登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglDurgPutinById(Long id) {
        return bdglDurgPutinMapper.deleteBdglDurgPutinById(id);
    }

    /**
     * 查询药品过期情况
     **/
    @Override
    public List<BdglDurgPutin> selectGuoQiList(BdglDurgPutin bdglDurgPutin) {
        List<BdglDurgPutin> bdglDurgPutins = bdglDurgPutinMapper.selectGuoQiList(bdglDurgPutin);

        return bdglDurgPutins;
    }

    @Override
    public int yaoPinXiaoHui(Long id
    ) {
        BdglDurgPutin bdglDurgPutin = bdglDurgPutinMapper.selectBdglDurgPutinById(id);
        bdglDurgPutin.setStatus("1");
        /*-------------------添加销毁记录-------------------------*/
        BdglDurgRuin bdglDurgRuin = new BdglDurgRuin();
        bdglDurgRuin.setDurgArchivesId(bdglDurgPutin.getDurgArchivesId());
        bdglDurgRuin.setPutIn(bdglDurgPutin.getSurplus().toString());
        bdglDurgRuin.setManufacturer(bdglDurgPutin.getManufacturer());
        bdglDurgRuin.setSpecifications(bdglDurgPutin.getSpecifications());
        bdglDurgRuin.setDurgBrand(bdglDurgPutin.getDurgBrand());
        bdglDurgRuin.setCreatetime(DateUtils.getNowDate());
        bdglDurgRuin.setPutinId(Integer.parseInt(bdglDurgPutin.getId().toString()));

        bdglDurgRuinMapper.insertBdglDurgRuin(bdglDurgRuin);
        /*-------------------添加销毁记录-------------------------*/

        if (bdglDurgPutin.getDurgArchivesId()!=null) {

            /*-----------------------修改库存数量------------------------------*/
            BdglDurgArchives bdglDurgArchives = bdglDurgArchivesMapper.selectBdglDurgArchivesById(Long.valueOf(bdglDurgPutin.getDurgArchivesId()));
            int i = bdglDurgArchives.getPutOut() + bdglDurgPutin.getPutIn();
            //出库总数
            bdglDurgArchives.setPutOut(bdglDurgArchives.getPutOut() + bdglDurgPutin.getSurplus());
            //库存总数
            bdglDurgArchives.setAmount(bdglDurgArchives.getAmount() - bdglDurgPutin.getSurplus());
            bdglDurgArchivesMapper.updateBdglDurgArchives(bdglDurgArchives);
            /*-----------------------修改库存数量------------------------------*/
            bdglDurgPutin.setSurplus(0);
            return bdglDurgPutinMapper.updateBdglDurgPutin(bdglDurgPutin);
        }
        return 1;
    }

    @Override
    public int add(BdglDurgPutin bdglDurgPutin) {
        return bdglDurgPutinMapper.insertBdglDurgPutin(bdglDurgPutin);
    }
}
