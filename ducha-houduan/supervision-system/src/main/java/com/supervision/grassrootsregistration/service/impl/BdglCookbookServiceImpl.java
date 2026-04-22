package com.supervision.grassrootsregistration.service.impl;

import java.util.*;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.grassrootsregistration.domain.BdglCookbookRegistration;
import com.supervision.grassrootsregistration.mapper.BdglCookbookRegistrationMapper;
import com.supervision.grassrootsregistration.service.IBdglCookbookRegistrationService;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.system.domain.BdglCookcategory;
import com.supervision.system.mapper.BdglCookcategoryMapper;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglCookbookMapper;
import com.supervision.grassrootsregistration.domain.BdglCookbook;
import com.supervision.grassrootsregistration.service.IBdglCookbookService;

/**
 * 一周食谱Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-26
 */
@Service
public class BdglCookbookServiceImpl implements IBdglCookbookService
{
    @Autowired
    private BdglCookbookMapper bdglCookbookMapper;
    @Autowired
    private BdglCookbookRegistrationMapper cookbookRegistrationMapper;
    @Autowired
    private BdglCookcategoryMapper cookcategoryMapper;
    @Autowired
    private BdglPeopleMapper peopleMapper;
    @Autowired
    private SysDeptMapper deptMapper;


    /**
     * 查询一周食谱
     * 
     * @param id 一周食谱主键
     * @return 一周食谱
     */
    @Override
    public BdglCookbook selectBdglCookbookById(Integer id)
    {
        BdglCookbook bdglCookbook = bdglCookbookMapper.selectBdglCookbookById(id);
        BdglCookbookRegistration bdglCookbookRegistration = new BdglCookbookRegistration();
        bdglCookbookRegistration.setCookbookId(id);
        //查找一周食谱详细信息
        List<BdglCookbookRegistration> bdglCookbookRegistrations = cookbookRegistrationMapper.selectBdglCookbookRegistrationList(bdglCookbookRegistration);
        for (BdglCookbookRegistration cookbookRegistration : bdglCookbookRegistrations) {

        }
        bdglCookbook.setCookbookRegistrations(bdglCookbookRegistrations);
        return bdglCookbook;
    }

    /**
     * 查询一周食谱列表
     * 
     * @param bdglCookbook 一周食谱
     * @return 一周食谱
     */
    @Override
    public List<BdglCookbook> selectBdglCookbookList(BdglCookbook bdglCookbook)
    {
        return bdglCookbookMapper.selectBdglCookbookList(bdglCookbook);
    }

    /**
     * 新增一周食谱
     * 
     * @param bdglCookbook 一周食谱
     * @return 结果
     */
    @Override
    public int insertBdglCookbook(BdglCookbook bdglCookbook)
    {
        bdglCookbook.setCreatetime(new Date());
        SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglCookbook.getUnitId()));
        bdglCookbook.setUnitName(sysDept.getDeptName());
        int i = bdglCookbookMapper.insertBdglCookbook(bdglCookbook);
        List<BdglCookbookRegistration> cookbookRegistrations = bdglCookbook.getCookbookRegistrations();
        //添加每周食谱
        for (BdglCookbookRegistration cookbookRegistration : cookbookRegistrations) {
            BdglCookbookRegistration cookbookRegistration1 = cookbookRegistration.getCookbookRegistration();
            cookbookRegistration.setCookbookId(bdglCookbook.getId());
            cookbookRegistration.setWeeklist(cookbookRegistration1.getWeeklist());
            cookbookRegistration.setTimelist(cookbookRegistration1.getTimelist());
            cookbookRegistration.setTypelist(cookbookRegistration1.getTypelist());
            /*-----------------------获取厨师姓名------------------------------------*/
            String cookcategoryIds = cookbookRegistration.getPeopleIds();
            if(cookcategoryIds!=null&&cookcategoryIds!=""){
                String[] split = cookcategoryIds.split(",");
                List<BdglPeople>peoples=peopleMapper.select(split);
                String names="";
                for (BdglPeople people : peoples) {
                    names+=people.getName()+",";
                }
                cookbookRegistration.setPeopleNames(names.substring(0,names.length()-1));
            }

            /*-----------------------获取厨师姓名------------------------------------*/
            /*-----------------------获取食物名称------------------------------------*/
            String cookcategoryIds1 = cookbookRegistration.getCookcategoryIds();
            if(cookcategoryIds1!=null&&cookcategoryIds1!=""){
                String[] split2 = cookcategoryIds1.split(",");
                List<BdglCookcategory> cookcategories=cookcategoryMapper.select(split2);
                String foodName="";
                for (BdglCookcategory cookcategory : cookcategories) {
                    foodName+=cookcategory.getName()+",";
                }
                cookbookRegistration.setCookcategoryNames(foodName.substring(0,foodName.length()-1));
            }

            /*-----------------------获取食物名称------------------------------------*/
            cookbookRegistration.setCreateTime(new Date());
            cookbookRegistrationMapper.insertBdglCookbookRegistration(cookbookRegistration);
        }

        return i;
    }

    /**
     * 修改一周食谱
     * 
     * @param bdglCookbook 一周食谱
     * @return 结果
     */
    @Override
    public int updateBdglCookbook(BdglCookbook bdglCookbook)
    {
        List<BdglCookbookRegistration> cookbookRegistrations = bdglCookbook.getCookbookRegistrations();

         BdglCookbookRegistration bdglCookbookRegistration = new BdglCookbookRegistration();
        bdglCookbookRegistration.setCookbookId(bdglCookbook.getId());
        List<BdglCookbookRegistration> bdglCookbookRegistrations = cookbookRegistrationMapper.selectBdglCookbookRegistrationList(bdglCookbookRegistration);
        c:for (int i = 0; i < cookbookRegistrations.size(); i++) {
            int a=i;
            for (int i1 = a; i1 < bdglCookbookRegistrations.size(); i1++) {
                String cookcategoryIds = cookbookRegistrations.get(i).getCookcategoryIds();
                String peopleIds = cookbookRegistrations.get(i).getPeopleIds();
                String cookcategoryIds1 = cookbookRegistrations.get(i).getCookcategoryIds();
                String peopleNames = cookbookRegistrations.get(i).getPeopleIds();
                String names="";
                if(cookcategoryIds!=null&&cookcategoryIds!=""){
                    String[] split = cookcategoryIds.split(",");
                    List<BdglCookcategory>peoples=cookcategoryMapper.select(split);
                    for (BdglCookcategory people : peoples) {
                        names+=people.getName()+",";
                    }
                }
                String cooknames="";
                if(peopleNames!=null&&peopleNames!=""){
                    String[] split = peopleNames.split(",");
                    List<BdglPeople>peoples=peopleMapper.select(split);
                    for (BdglPeople people : peoples) {
                        cooknames+=people.getName()+",";
                    }
                }
                if(cooknames!=null&&cooknames!=""){
                    bdglCookbookRegistration.setPeopleNames(cooknames.substring(0,cooknames.length()-1));

                }else{
                    bdglCookbookRegistration.setPeopleNames("");

                }
                if(cookcategoryIds!=null){
                    bdglCookbookRegistration.setCookcategoryIds(cookcategoryIds1);

                }else{
                    bdglCookbookRegistration.setCookcategoryIds(",");
                }
                if(peopleIds!=null){
                    bdglCookbookRegistration.setPeopleIds(peopleIds);

                }else{
                    bdglCookbookRegistration.setPeopleIds(",");
                }
                if(names!=null){
                    bdglCookbookRegistration.setCookcategoryNames(names);
                }else{
                    bdglCookbookRegistration.setCookcategoryNames(",");
                }
                bdglCookbookRegistration.setId(bdglCookbookRegistrations.get(i).getId());
                bdglCookbookRegistration.setUpdatetime(new Date());
                cookbookRegistrationMapper.updateBdglCookbookRegistration(bdglCookbookRegistration);
                continue c;
            }
        }
        return bdglCookbookMapper.updateBdglCookbook(bdglCookbook);
    }

    /**
     * 批量删除一周食谱
     * 
     * @param ids 需要删除的一周食谱主键
     * @return 结果
     */
    @Override
    public int deleteBdglCookbookByIds(Integer[] ids)
    {
        for (Integer id : ids) {
            BdglCookbookRegistration bdglCookbookRegistration = new BdglCookbookRegistration();
            bdglCookbookRegistration.setCookbookId(id);
            List<BdglCookbookRegistration> bdglCookbookRegistrations = cookbookRegistrationMapper.selectBdglCookbookRegistrationList(bdglCookbookRegistration);
            for (BdglCookbookRegistration cookbookRegistration : bdglCookbookRegistrations) {
                cookbookRegistrationMapper.deleteBdglCookbookRegistrationById(cookbookRegistration.getId());
            }
        }
        return bdglCookbookMapper.deleteBdglCookbookByIds(ids);
    }

    /**
     * 删除一周食谱信息
     * 
     * @param id 一周食谱主键
     * @return 结果
     */
    @Override
    public int deleteBdglCookbookById(Integer id)
    {
        return bdglCookbookMapper.deleteBdglCookbookById(id);
    }

    @Override
    public  HashMap<String, Object> getFood() {
        HashMap<String, Object> map = new HashMap<>();
        //早餐
        BdglCookcategory cookcategory = new BdglCookcategory();
        cookcategory.setTimelist(0);
        cookcategory.setTypelist(0);
        List<BdglCookcategory> bdglCookbookRegistrations = cookcategoryMapper.selectBdglCookcategoryList(cookcategory);
        BdglCookcategory cookcategory3 = new BdglCookcategory();
        cookcategory3.setTimelist(0);
        cookcategory3.setTypelist(1);
        List<BdglCookcategory> bdglCookbookRegistrations3 = cookcategoryMapper.selectBdglCookcategoryList(cookcategory3);
        //午餐
        BdglCookcategory cookcategory1 = new BdglCookcategory();
        cookcategory1.setTimelist(1);
        cookcategory1.setTypelist(0);
        List<BdglCookcategory> bdglCookbookRegistrations1 = cookcategoryMapper.selectBdglCookcategoryList(cookcategory1);
        BdglCookcategory cookcategory4 = new BdglCookcategory();
        cookcategory4.setTimelist(1);
        cookcategory4.setTypelist(1);
        List<BdglCookcategory> bdglCookbookRegistrations4 = cookcategoryMapper.selectBdglCookcategoryList(cookcategory4);
        //晚餐
        BdglCookcategory cookcategory2 = new BdglCookcategory();
        cookcategory2.setTimelist(2);
        cookcategory2.setTypelist(0);
        List<BdglCookcategory> bdglCookbookRegistrations2 = cookcategoryMapper.selectBdglCookcategoryList(cookcategory2);
        BdglCookcategory cookcategory5 = new BdglCookcategory();
        cookcategory5.setTimelist(2);
        cookcategory5.setTypelist(1);
        List<BdglCookcategory> bdglCookbookRegistrations5 = cookcategoryMapper.selectBdglCookcategoryList(cookcategory5);
        map.put("zaoZhu",bdglCookbookRegistrations);
        map.put("zhongZhu",bdglCookbookRegistrations1);
        map.put("wanZhu",bdglCookbookRegistrations2);
        map.put("zaoFu",bdglCookbookRegistrations3);
        map.put("zhongFU",bdglCookbookRegistrations4);
        map.put("wanFu",bdglCookbookRegistrations5);
        return map;
    }


}
