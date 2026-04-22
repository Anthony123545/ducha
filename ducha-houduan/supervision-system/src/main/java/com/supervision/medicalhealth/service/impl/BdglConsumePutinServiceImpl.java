package com.supervision.medicalhealth.service.impl;

import java.util.*;

import com.supervision.common.utils.DateUtils;
import com.supervision.medicalhealth.domain.*;
import com.supervision.medicalhealth.mapper.BdglConsumeArchivesMapper;
import com.supervision.medicalhealth.service.IBdglConsumeRuinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglConsumePutinMapper;
import com.supervision.medicalhealth.service.IBdglConsumePutinService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 耗材入库登记Service业务层处理
 * 
 * @author hr
 * @date 2022-02-12
 */
@Transactional
@Service
public class BdglConsumePutinServiceImpl implements IBdglConsumePutinService 
{
    @Autowired
    private BdglConsumePutinMapper bdglConsumePutinMapper;
    @Autowired
    private BdglConsumeArchivesMapper bdglConsumeArchivesMapper;
    @Autowired
    private IBdglConsumeRuinService bdglConsumeRuinService;



    /**
     * 查询耗材入库登记
     * 
     * @param id 耗材入库登记主键
     * @return 耗材入库登记
     */
    @Override
    public BdglConsumePutin selectBdglConsumePutinById(Long id)
    {
        return bdglConsumePutinMapper.selectBdglConsumePutinById(id);
    }

    /**
     * 查询耗材入库登记列表
     * 
     * @param bdglConsumePutin 耗材入库登记
     * @return 耗材入库登记
     */
    @Override
    public List<BdglConsumePutin> selectBdglConsumePutinList(BdglConsumePutin bdglConsumePutin)
    {
        return bdglConsumePutinMapper.selectBdglConsumePutinList(bdglConsumePutin);
    }

    /**
     * 新增耗材入库登记
     * 
     * @param bdglConsumePutin 耗材入库登记
     * @return 结果
     */
    @Override
    public int insertBdglConsumePutin(BdglConsumePutin bdglConsumePutin)
    {
        /*-------------------------修改库存数据-------------------------*/
        BdglConsumeArchives bdglConsumeArchives = bdglConsumeArchivesMapper.selectBdglConsumeArchivesById(Long.valueOf(bdglConsumePutin.getDurgArchivesId()));
        bdglConsumeArchives.setAmount(bdglConsumeArchives.getAmount()+bdglConsumePutin.getPutIn());
        bdglConsumeArchives.setPutIn(bdglConsumeArchives.getPutIn()+bdglConsumePutin.getPutIn());
        if(bdglConsumePutin.getOverduedate().getTime()<new Date().getTime()){
            if(bdglConsumeArchives.getOverdue()==null){
                bdglConsumeArchives.setOverdue(0);
            }
            bdglConsumeArchives.setOverdue(bdglConsumeArchives.getOverdue()+bdglConsumePutin.getPutIn());
        }
        bdglConsumeArchivesMapper.updateBdglConsumeArchives(bdglConsumeArchives);
        /*-------------------------修改库存数据-------------------------*/
        bdglConsumePutin.setCreateTime(DateUtils.getNowDate());
        return bdglConsumePutinMapper.insertBdglConsumePutin(bdglConsumePutin);
    }

    /**
     * 修改耗材入库登记
     * 
     * @param bdglConsumePutin 耗材入库登记
     * @return 结果
     */
    @Override
    public int updateBdglConsumePutin(BdglConsumePutin bdglConsumePutin)
    {
        /*-------------------添加销毁记录-------------------------*/
        BdglConsumeRuin bdglDurgRuin = new BdglConsumeRuin();
        bdglDurgRuin.setDurgArchivesId(bdglConsumePutin.getDurgArchivesId());
        bdglDurgRuin.setPutIn(bdglConsumePutin.getSurplus().toString());
        bdglDurgRuin.setManufacturer(bdglConsumePutin.getManufacturer());
        bdglDurgRuin.setSpecifications(bdglConsumePutin.getSpecifications());
        bdglDurgRuin.setDurgBrand(bdglConsumePutin.getDurgBrand());
        bdglDurgRuin.setCreatetime(DateUtils.getNowDate());
        bdglDurgRuin.setPutinId(Integer.parseInt(bdglConsumePutin.getId().toString()));
        bdglConsumeRuinService.insertBdglConsumeRuin(bdglDurgRuin);
        /*-------------------添加销毁记录-------------------------*/
        if(bdglConsumePutin.getDurgArchivesId()!=null){
            /*-------------------------修改库存数据-------------------------*/
            BdglConsumeArchives bdglConsumeArchives = bdglConsumeArchivesMapper.selectBdglConsumeArchivesById(Long.valueOf(bdglConsumePutin.getDurgArchivesId()));
            bdglConsumeArchives.setAmount(bdglConsumeArchives.getAmount()-bdglConsumePutin.getSurplus());
            bdglConsumeArchives.setPutOut(bdglConsumeArchives.getPutOut()+bdglConsumePutin.getSurplus());
            bdglConsumeArchivesMapper.updateBdglConsumeArchives(bdglConsumeArchives);
            /*-------------------------修改库存数据-------------------------*/
            /*------------------------更新入库数据------------------------------*/
            BdglConsumePutin bdglConsumePutins = bdglConsumePutinMapper.selectBdglConsumePutinById(bdglConsumePutin.getId());
            bdglConsumePutin.setSurplus(bdglConsumePutin.getSurplus()-bdglConsumePutins.getSurplus());
            /*------------------------更新入库数据------------------------------*/
            bdglConsumePutin.setSurplus(0);
            bdglConsumePutin.setUpdateTime(DateUtils.getNowDate());
            return bdglConsumePutinMapper.updateBdglConsumePutin(bdglConsumePutin);
        }
        return 1;
    }

    /**
     * 批量删除耗材入库登记
     * 
     * @param ids 需要删除的耗材入库登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglConsumePutinByIds(Long[] ids)
    {
        return bdglConsumePutinMapper.deleteBdglConsumePutinByIds(ids);
    }

    /**
     * 删除耗材入库登记信息
     * 
     * @param id 耗材入库登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglConsumePutinById(Long id)
    {
        return bdglConsumePutinMapper.deleteBdglConsumePutinById(id);
    }
    /**
     * 查询耗材过期情况
     * */
    @Override
    public List<BdglConsumePutin> selectGuoQiList(BdglConsumePutin bdglConsumePutin) {
        bdglConsumePutin.setStatus("0");
        List<BdglConsumePutin> bdglConsumePutins = bdglConsumePutinMapper.selectGuoQiList(bdglConsumePutin);

        return bdglConsumePutins;
    }
    /**
     * 耗材销毁
     * */
    @Override
    public int haoCaiXiaoHui(BdglConsumePutin bdglConsumePutin) {
        /*-------------------添加销毁记录-------------------------*/
        BdglConsumeRuin bdglConsumeRuin = new BdglConsumeRuin();
        bdglConsumeRuin.setDurgArchivesId(bdglConsumePutin.getDurgArchivesId());
        bdglConsumeRuin.setPutIn(bdglConsumePutin.getPutIn().toString());
        bdglConsumeRuin.setManufacturer(bdglConsumePutin.getManufacturer());
        bdglConsumeRuin.setSpecifications(bdglConsumePutin.getSpecifications());
        bdglConsumeRuin.setDurgBrand(bdglConsumePutin.getDurgBrand());
        bdglConsumeRuin.setCreatetime(DateUtils.getNowDate());
        bdglConsumeRuinService.insertBdglConsumeRuin(bdglConsumeRuin);
        /*-------------------添加销毁记录-------------------------*/
        return  bdglConsumePutinMapper.updateBdglConsumePutin(bdglConsumePutin);
    }

    @Override
    public BdglConsumeArchives selectlist(BdglConsumeArchives bdglConsumeArchives) {
        BdglConsumeArchives list = bdglConsumeArchivesMapper.selectBdglConsumeArchivesbynumber(bdglConsumeArchives);
        Map<String,Object> map=null;
       if(list!=null){
            map=new HashMap<>();
            //品牌   规格 库房 单位
            String yaoPingName=list.getDurgName()+"-"+list.getSpecifications()+"-"+list.getStoreHouse()+"-"+list.getUnitName()+"-"+list.getDurgBrand();
           list.setName(yaoPingName);
        }
        return list;
    }

    @Override
    public int add(BdglConsumePutin bdglConsumePutin) {
        return bdglConsumePutinMapper.insertBdglConsumePutin(bdglConsumePutin);
    }

    @Override
    public int update(BdglConsumePutin bdglConsumePutin) {
        BdglConsumePutin bdglDurgPutin1 = bdglConsumePutinMapper.selectBdglConsumePutinById(bdglConsumePutin.getId());
        Integer putIn = bdglDurgPutin1.getPutIn();
        Integer putIn1 = bdglConsumePutin.getPutIn();
        int i =  putIn1-putIn ;
        BdglConsumeArchives  bdglDurgArchives = bdglConsumeArchivesMapper.selectBdglConsumeArchivesById(Long.valueOf(bdglConsumePutin.getDurgArchivesId()));

        if(putIn>putIn1){
            bdglConsumePutin.setSurplus(bdglConsumePutin.getSurplus()+i);
            //入库总数
            bdglDurgArchives.setPutIn(bdglDurgArchives.getPutIn()+i);
            //库存总数
            bdglDurgArchives.setAmount(bdglDurgArchives.getAmount() + i);
            bdglConsumeArchivesMapper.updateBdglConsumeArchives(bdglDurgArchives);
        }else if(putIn<putIn1){
            bdglDurgArchives.setPutIn(bdglDurgArchives.getPutIn()+i);
            //库存总数
            bdglDurgArchives.setAmount(bdglDurgArchives.getAmount() +i);
            bdglConsumeArchivesMapper.updateBdglConsumeArchives(bdglDurgArchives);
            bdglConsumePutin.setSurplus(bdglConsumePutin.getSurplus()+i);

        }

        return bdglConsumePutinMapper.updateBdglConsumePutin(bdglConsumePutin);
    }
}
