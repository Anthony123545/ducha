package com.supervision.medicalhealth.service.impl;

import java.lang.reflect.Array;
import java.util.*;

import com.supervision.common.utils.DateUtils;
import com.supervision.medicalhealth.domain.*;
import com.supervision.medicalhealth.mapper.BdglAntiepidemicArchivesMapper;
import com.supervision.medicalhealth.mapper.BdglAntiepidemicRuinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglAntiepidemicPutinMapper;
import com.supervision.medicalhealth.service.IBdglAntiepidemicPutinService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 防疫用品入库登记Service业务层处理
 * 
 * @author hr
 * @date 2022-02-14
 */
@Transactional
@Service
public class BdglAntiepidemicPutinServiceImpl implements IBdglAntiepidemicPutinService 
{
    @Autowired
    private BdglAntiepidemicPutinMapper bdglAntiepidemicPutinMapper;
    @Autowired
    private BdglAntiepidemicArchivesMapper bdglAntiepidemicArchivesMapper;

    @Autowired
    private BdglAntiepidemicRuinMapper antiepidemicRuinMapper;

    /**
     * 查询防疫用品入库登记
     * 
     * @param id 防疫用品入库登记主键
     * @return 防疫用品入库登记
     */
    @Override
    public BdglAntiepidemicPutin selectBdglAntiepidemicPutinById(Long id)
    {
        return bdglAntiepidemicPutinMapper.selectBdglAntiepidemicPutinById(id);
    }

    /**
     * 查询防疫用品入库登记列表
     * 
     * @param bdglAntiepidemicPutin 防疫用品入库登记
     * @return 防疫用品入库登记
     */
    @Override
    public List<BdglAntiepidemicPutin> selectBdglAntiepidemicPutinList(BdglAntiepidemicPutin bdglAntiepidemicPutin)
    {
        return bdglAntiepidemicPutinMapper.selectBdglAntiepidemicPutinList(bdglAntiepidemicPutin);
    }

    /**
     * 新增防疫用品入库登记
     * 
     * @param bdglAntiepidemicPutin 防疫用品入库登记
     * @return 结果
     */
    @Override
    public int insertBdglAntiepidemicPutin(BdglAntiepidemicPutin bdglAntiepidemicPutin)
    {
        /*----------------------------更新防疫用品库存档案----------------------------------------*/
        BdglAntiepidemicArchives bdglAntiepidemicArchives1 = bdglAntiepidemicArchivesMapper.selectBdglAntiepidemicArchivesById(Long.valueOf(bdglAntiepidemicPutin.getDurgArchivesId()));
        if(bdglAntiepidemicPutin.getExpirationTime().getTime()<new Date().getTime()){
            if(bdglAntiepidemicArchives1.getOverdueQuantity()==null){
                bdglAntiepidemicArchives1.setOverdueQuantity(0);
            }
            bdglAntiepidemicArchives1.setOverdueQuantity(bdglAntiepidemicArchives1.getOverdueQuantity()+bdglAntiepidemicPutin.getPutIn());
        }
        //入库数量
        bdglAntiepidemicArchives1.setAmount(bdglAntiepidemicArchives1.getAmount()+bdglAntiepidemicPutin.getPutIn());
        //库存数量
        bdglAntiepidemicArchives1.setPutIn(bdglAntiepidemicArchives1.getPutIn()+bdglAntiepidemicPutin.getPutIn());
        bdglAntiepidemicArchivesMapper.updateBdglAntiepidemicArchives(bdglAntiepidemicArchives1);
        /*----------------------------更新防疫用品库存档案----------------------------------------*/
        return bdglAntiepidemicPutinMapper.insertBdglAntiepidemicPutin(bdglAntiepidemicPutin);
    }

    /**
     * 修改防疫用品入库登记
     * 
     * @param bdglAntiepidemicPutin 防疫用品入库登记
     * @return 结果
     */
    @Override
    public int updateBdglAntiepidemicPutin(BdglAntiepidemicPutin bdglAntiepidemicPutin)
    {
        /*-------------------添加销毁记录-------------------------*/
        BdglAntiepidemicRuin bdglDurgRuin = new BdglAntiepidemicRuin();
        bdglDurgRuin.setDurgArchivesId(bdglAntiepidemicPutin.getDurgArchivesId());
        bdglDurgRuin.setPutIn(bdglAntiepidemicPutin.getSurplus()+"");
        bdglDurgRuin.setSpecifications(bdglAntiepidemicPutin.getSpecifications());
        bdglDurgRuin.setDurgBrand(bdglAntiepidemicPutin.getDurgBrand());
        bdglDurgRuin.setCreatetime(DateUtils.getNowDate());
        if(bdglAntiepidemicPutin.getStoreHouse()!=null){

            bdglDurgRuin.setManufacturer(bdglAntiepidemicPutin.getStoreHouse().toString());
        }
        bdglDurgRuin.setPutinId(Integer.parseInt(bdglAntiepidemicPutin.getId().toString()));
        antiepidemicRuinMapper.insertBdglAntiepidemicRuin(bdglDurgRuin);
        /*-------------------添加销毁记录-------------------------*/
        if(bdglAntiepidemicPutin.getDurgArchivesId()!=null){

            /*-------------------------更新库存数量-------------------------------*/
            BdglAntiepidemicArchives bdglAntiepidemicArchives1 = bdglAntiepidemicArchivesMapper.selectBdglAntiepidemicArchivesById(Long.valueOf(bdglAntiepidemicPutin.getDurgArchivesId()));
            //库存数量
            bdglAntiepidemicArchives1.setAmount(bdglAntiepidemicArchives1.getAmount()-bdglAntiepidemicPutin.getSurplus());
            //出库数量
            bdglAntiepidemicArchives1.setPutOut(bdglAntiepidemicArchives1.getPutOut()+bdglAntiepidemicPutin.getSurplus());
            bdglAntiepidemicArchivesMapper.updateBdglAntiepidemicArchives(bdglAntiepidemicArchives1);
            /*--------------------------更新库存数量------------------------------*/
            bdglAntiepidemicPutin.setSurplus(0);
            return bdglAntiepidemicPutinMapper.updateBdglAntiepidemicPutin(bdglAntiepidemicPutin);
        }
        return 1;
    }

    /**
     * 批量删除防疫用品入库登记
     * 
     * @param ids 需要删除的防疫用品入库登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglAntiepidemicPutinByIds(Long[] ids)
    {
        return bdglAntiepidemicPutinMapper.deleteBdglAntiepidemicPutinByIds(ids);
    }

    /**
     * 删除防疫用品入库登记信息
     * 
     * @param id 防疫用品入库登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglAntiepidemicPutinById(Long id)
    {
        return bdglAntiepidemicPutinMapper.deleteBdglAntiepidemicPutinById(id);
    }

    @Override
    public BdglAntiepidemicArchives selectlist(BdglAntiepidemicArchives bdglAntiepidemicArchives) {
        BdglAntiepidemicArchives list = bdglAntiepidemicArchivesMapper.selectBdglAntiepidemicArchivesByBarCode(bdglAntiepidemicArchives);
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
    public List<BdglAntiepidemicPutin> selectGuoQiList(BdglAntiepidemicPutin bdglDurgPutin) {
        bdglDurgPutin.setExpirationTime(DateUtils.getNowDate());
        List<BdglAntiepidemicPutin>antiepidemicPutins=bdglAntiepidemicPutinMapper.sselectGuoQi(bdglDurgPutin);
        return antiepidemicPutins;
    }

    @Override
    public int add(BdglAntiepidemicPutin bdglAntiepidemicPutin) {
        return bdglAntiepidemicPutinMapper.insertBdglAntiepidemicPutin(bdglAntiepidemicPutin);
    }

    @Override
    public int update(BdglAntiepidemicPutin bdglAntiepidemicPutin) {
        BdglAntiepidemicPutin bdglDurgPutin1 = bdglAntiepidemicPutinMapper.selectBdglAntiepidemicPutinById(bdglAntiepidemicPutin.getId());
        Integer putIn = bdglDurgPutin1.getPutIn();
        Integer putIn1 = bdglAntiepidemicPutin.getPutIn();
        int i =  putIn1-putIn ;
        BdglAntiepidemicArchives bdglAntiepidemicArchives1 = bdglAntiepidemicArchivesMapper.selectBdglAntiepidemicArchivesById(Long.valueOf(bdglAntiepidemicPutin.getDurgArchivesId()));
        if(putIn>putIn1){
            bdglAntiepidemicPutin.setSurplus(bdglAntiepidemicPutin.getSurplus()+i);
            //入库总数
            bdglAntiepidemicArchives1.setPutIn(bdglAntiepidemicArchives1.getPutIn()+i);
            //库存总数
            bdglAntiepidemicArchives1.setAmount(bdglAntiepidemicArchives1.getAmount() + i);
            bdglAntiepidemicArchivesMapper.updateBdglAntiepidemicArchives(bdglAntiepidemicArchives1);
        }else if(putIn<putIn1){
            bdglAntiepidemicArchives1.setPutIn(bdglAntiepidemicArchives1.getPutIn()+i);
            //库存总数
            bdglAntiepidemicArchives1.setAmount(bdglAntiepidemicArchives1.getAmount() +i);
            bdglAntiepidemicArchivesMapper.updateBdglAntiepidemicArchives(bdglAntiepidemicArchives1);
            bdglAntiepidemicPutin.setSurplus(bdglAntiepidemicPutin.getSurplus()+i);

        }

        return bdglAntiepidemicPutinMapper.updateBdglAntiepidemicPutin(bdglAntiepidemicPutin);
    }
}
