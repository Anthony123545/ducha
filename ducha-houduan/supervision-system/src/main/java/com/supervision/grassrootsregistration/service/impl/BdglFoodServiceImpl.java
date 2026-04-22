package com.supervision.grassrootsregistration.service.impl;

import java.util.*;

import cn.hutool.core.util.ObjectUtil;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.domain.*;
import com.supervision.grassrootsregistration.mapper.*;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.service.IBdglFoodService;

/**
 * 伙食管理登记薄Service业务层处理
 *
 * @author supervision
 * @date 2022-02-26
 */
@Service
public class BdglFoodServiceImpl implements IBdglFoodService {
    @Autowired
    private BdglFoodMapper bdglFoodMapper;
    @Autowired
    private BdglFoodMealMapper bdglFoodMealMapper;
    @Autowired
    private BdglFoodKindMapper bdglFoodKindMapper;
    @Autowired
    private BdglFoodProcurementMapper bdglFoodProcurementMapper;
    @Autowired
    private BdglFoodBoardWagesMapper bdglFoodBoardWagesMapper;
    @Autowired
    private BdglFoodShoppingMapper bdglFoodShoppingMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private BdglPeopleMapper peopleMapper;


    /**
     * 查询伙食管理登记薄
     *
     * @param id 伙食管理登记薄主键
     * @return 伙食管理登记薄
     */
    @Override
    public BdglFood selectBdglFoodById(Integer id) {
        BdglFood bdglFood = bdglFoodMapper.selectBdglFoodById(id);
//        String peopleIds = bdglFood.getPeopleIds();
//        String[] split = peopleIds.split(",");
//        bdglFood.setCommons(split);
        BdglFoodMeal bdglFoodMeal = new BdglFoodMeal();
        bdglFoodMeal.setFoodId(id);
        List<BdglFoodMeal> bdglFoodMeals = bdglFoodMealMapper.selectBdglFoodMealList(bdglFoodMeal);
        bdglFood.setBdglFoodMeal(bdglFoodMeals);
    
        /*
          实物检查验收
         */
        BdglFoodKind bdglFoodKind = new BdglFoodKind();
        bdglFoodKind.setFoodId(id);
        List<BdglFoodKind> bdglFoodKinds = bdglFoodKindMapper.selectBdglFoodKindLists(bdglFoodKind);
        Map<String, List<BdglFoodKind>> map = getStringListMap(bdglFoodKinds);
        //查看详细信息使用此字段
        bdglFood.setBdglFoodKindData(map);
        //修改实物接口使用此字段
        bdglFood.setBdglFoodKinds(bdglFoodKinds);
        
        BdglFoodProcurement bdglFoodProcurement = new BdglFoodProcurement();
        bdglFoodProcurement.setFoodId(id);
        List<BdglFoodProcurement> bdglFoodProcurements = bdglFoodProcurementMapper.selectBdglFoodProcurementList(bdglFoodProcurement);
        bdglFood.setBdglFoodProcurements(bdglFoodProcurements);
        BdglFoodBoardWages bdglFoodBoardWages = new BdglFoodBoardWages();
        bdglFoodBoardWages.setFoodId(id);
        List<BdglFoodBoardWages> bdglFoodBoardWages1 = bdglFoodBoardWagesMapper.selectBdglFoodBoardWagesList(bdglFoodBoardWages);
        bdglFood.setBdglFoodBoardWagess(bdglFoodBoardWages1);
        BdglFoodShopping bdglFoodShopping = new BdglFoodShopping();
        bdglFoodShopping.setFoodId(id);
        List<BdglFoodShopping> bdglFoodShoppings = bdglFoodShoppingMapper.selectBdglFoodShoppingList(bdglFoodShopping);
        bdglFood.setBdglFoodShoppings(bdglFoodShoppings);
        return bdglFood;
    }
    
    private Map<String, List<BdglFoodKind>> getStringListMap(List<BdglFoodKind> bdglFoodKinds) {
        Map<String, List<BdglFoodKind>> map =new HashMap<>();
        //主食List
        List<BdglFoodKind> foodList1 = new ArrayList<>();
        //副食List
        List<BdglFoodKind> foodList2 = new ArrayList<>();
        //其他List
        List<BdglFoodKind> foodList3 = new ArrayList<>();
        bdglFoodKinds.forEach(item->{
            //物品类型
            String distinctions = item.getDistinctions();
            if (StringUtils.isNotEmpty(distinctions) && "主食".equals(distinctions)){
                foodList1.add(item);
            }
            if (StringUtils.isNotEmpty(distinctions) && "副食".equals(distinctions)){
                foodList2.add(item);
            }
            if (StringUtils.isNotEmpty(distinctions) && "其他".equals(distinctions)){
                foodList3.add(item);
            }
            //根据同类型物品进行数据封装
            //主食
            map.put("主食",foodList1);
            //副食
            map.put("副食",foodList2);
            //其他
            map.put("其他",foodList3);
        });
        return map;
    }
    
    /**
     * 查询伙食管理登记薄列表
     *
     * @param bdglFood 伙食管理登记薄
     * @return 伙食管理登记薄
     */
    @Override
    public List<BdglFood> selectBdglFoodList(BdglFood bdglFood) {
        return bdglFoodMapper.selectBdglFoodList(bdglFood);
    }

    /**
     * 新增伙食管理登记薄
     *
     * @param bdglFood 伙食管理登记薄
     * @return 结果
     */
    @Override
    public int insertBdglFood(BdglFood bdglFood) {
        bdglFood.setCreatetime(new Date());
        if (bdglFood.getUnitId() != null) {
            SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglFood.getUnitId()));
            bdglFood.setUnitName(sysDept.getDeptName());
        }
        //添加帮厨人姓名
        String peopleNames = "";
        if (bdglFood.getPeopleIds() != null && bdglFood.getPeopleIds() != "") {
            String[] split = bdglFood.getPeopleIds().split(",");
            List<BdglPeople> selectname = peopleMapper.selectname(split);
            for (BdglPeople bdglPeople : selectname) {
                peopleNames += bdglPeople.getName();
            }
        }
        bdglFood.setPeopleNames(peopleNames);
        List<BdglFoodMeal> bdglFoodMeal = bdglFood.getBdglFoodMeal();//一日三餐
        int i = bdglFoodMapper.insertBdglFood(bdglFood);
        List<BdglFoodBoardWages> bdglFoodBoardWagess = bdglFood.getBdglFoodBoardWagess();//交退伙食费
        if (bdglFoodBoardWagess.size() > 0 && bdglFoodBoardWagess != null) {
            for (BdglFoodBoardWages foodBoardWagess : bdglFoodBoardWagess) {
                String name = "";
                if (foodBoardWagess.getPeopleId() != null) {
                    BdglPeople bdglPeople = peopleMapper.selectBdglPeopleById(Long.valueOf(foodBoardWagess.getPeopleId()));
                    name = bdglPeople.getName();
                }
                foodBoardWagess.setPeopleName(name);
                foodBoardWagess.setFoodId(bdglFood.getId());
                bdglFoodBoardWagesMapper.insertBdglFoodBoardWages(foodBoardWagess);
            }
        }
        List<BdglFoodKind> bdglFoodKinds = bdglFood.getBdglFoodKinds();//实物检查验收
        if (bdglFoodKinds.size() > 0 && bdglFoodKinds != null) {

            for (BdglFoodKind bdglFoodKind : bdglFoodKinds) {

                bdglFoodKind.setFoodId(bdglFood.getId());
                bdglFoodKindMapper.insertBdglFoodKind(bdglFoodKind);
            }
        }
        List<BdglFoodProcurement> bdglFoodProcurements = bdglFood.getBdglFoodProcurements();//采购食材
        if (bdglFoodProcurements.size() > 0 && bdglFoodProcurements != null) {

            for (BdglFoodProcurement bdglFoodProcurement : bdglFoodProcurements) {
                bdglFoodProcurement.setFoodId(bdglFood.getId());
                bdglFoodProcurementMapper.insertBdglFoodProcurement(bdglFoodProcurement);

            }
        }
        //在食堂购物
        List<BdglFoodShopping> bdglFoodShoppings = bdglFood.getBdglFoodShoppings();//在食堂购物
        if (bdglFoodShoppings.size() > 0 && bdglFoodShoppings != null) {
            for (BdglFoodShopping bdglFoodShopping : bdglFoodShoppings) {
                bdglFoodShopping.setFoodId(bdglFood.getId());
                String name = "";
                if (bdglFoodShopping.getPeopleId() != null) {
                    BdglPeople bdglPeople = peopleMapper.selectBdglPeopleById(Long.valueOf(bdglFoodShopping.getPeopleId()));
                    name = bdglPeople.getName();
                }
                bdglFoodShopping.setPeoplename(name);
                bdglFoodShoppingMapper.insertBdglFoodShopping(bdglFoodShopping);
            }
        }
        //
        if (bdglFoodMeal.size() > 0 && bdglFoodMeal != null) {
            for (BdglFoodMeal foodMeal : bdglFoodMeal) {
                foodMeal.setFoodId(bdglFood.getId());
                bdglFoodMealMapper.insertBdglFoodMeal(foodMeal);
            }
        }

        return i;
    }

    /**
     * 修改伙食管理登记薄
     *
     * @param bdglFood 伙食管理登记薄
     * @return 结果
     */
    @Override
    public int updateBdglFood(BdglFood bdglFood) {
        List<BdglFoodMeal> bdglFoodMeal = bdglFood.getBdglFoodMeal();//一日三餐
        //添加帮厨人姓名
        String peopleNames = "";
        if (bdglFood.getPeopleIds() != null && bdglFood.getPeopleIds() != "") {
            String[] split = bdglFood.getPeopleIds().split(",");
            List<BdglPeople> selectname = peopleMapper.selectname(split);
            for (BdglPeople bdglPeople : selectname) {
                peopleNames += bdglPeople.getName();
            }
        }
        bdglFood.setPeopleNames(peopleNames);
        int i = bdglFoodMapper.updateBdglFood(bdglFood);
        List<BdglFoodBoardWages> bdglFoodBoardWagess = bdglFood.getBdglFoodBoardWagess();//交退伙食费
        if (bdglFoodBoardWagess.size() > 0 && bdglFoodBoardWagess != null) {
            int s = bdglFoodBoardWagesMapper.deleteBdglFoodBoardWages(bdglFood.getId());
            for (BdglFoodBoardWages foodBoardWagess : bdglFoodBoardWagess) {
                foodBoardWagess.setFoodId(bdglFood.getId());
                String name = "";
                if (ObjectUtil.isNotNull(foodBoardWagess.getPeopleId())){
                    BdglPeople bdglPeople = peopleMapper.selectBdglPeopleById(Long.valueOf(foodBoardWagess.getPeopleId()));
                    name = bdglPeople.getName();
                    foodBoardWagess.setPeopleName(name);
                }
                //如果id是空  插入新数据
                foodBoardWagess.setFoodId(bdglFood.getId());
                foodBoardWagess.setCreatetime(new Date());
                bdglFoodBoardWagesMapper.insertBdglFoodBoardWages(foodBoardWagess);
            }
        }
        List<BdglFoodKind> bdglFoodKinds = bdglFood.getBdglFoodKinds();//实物检查验收
        if (bdglFoodKinds.size() > 0 && bdglFoodKinds != null) {
            int s = bdglFoodKindMapper.deleteBdglFoodKind(bdglFood.getId());
            for (BdglFoodKind bdglFoodKind : bdglFoodKinds) {
                bdglFoodKind.setFoodId(bdglFood.getId());
                bdglFoodKind.setCreatetime(new Date());
                bdglFoodKindMapper.insertBdglFoodKind(bdglFoodKind);
            }
        }
        List<BdglFoodProcurement> bdglFoodProcurements = bdglFood.getBdglFoodProcurements();//采购食材
        if (bdglFoodProcurements.size() > 0 && bdglFoodProcurements != null) {
            int s = bdglFoodProcurementMapper.deleteBdglFoodProcurement(bdglFood.getId());
            for (BdglFoodProcurement bdglFoodProcurement : bdglFoodProcurements) {
                bdglFoodProcurement.setFoodId(bdglFood.getId());
                bdglFoodProcurement.setCreattime(new Date());
                bdglFoodProcurementMapper.insertBdglFoodProcurement(bdglFoodProcurement);

            }
        }
        //在食堂购物
        List<BdglFoodShopping> bdglFoodShoppings = bdglFood.getBdglFoodShoppings();//在食堂购物
        if (bdglFoodShoppings.size() > 0 && bdglFoodShoppings != null) {
            int s = bdglFoodShoppingMapper.deleteBdglFoodShopping(bdglFood.getId());
            for (BdglFoodShopping bdglFoodShopping : bdglFoodShoppings) {
                bdglFoodShopping.setFoodId(bdglFood.getId());
                String name = "";
                if (bdglFoodShopping.getPeopleId() != null) {
                    BdglPeople bdglPeople = peopleMapper.selectBdglPeopleById(Long.valueOf(bdglFoodShopping.getPeopleId()));
                    name = bdglPeople.getName();
                }
                bdglFoodShopping.setPeoplename(name);
                bdglFoodShopping.setFoodId(bdglFood.getId());
                bdglFoodShopping.setUpdatetime(new Date());
                bdglFoodShoppingMapper.insertBdglFoodShopping(bdglFoodShopping);
            }
        }
        //
        if (bdglFoodMeal.size() > 0 && bdglFoodMeal != null) {
            for (BdglFoodMeal foodMeal : bdglFoodMeal) {
                foodMeal.setFoodId(bdglFood.getId());
                bdglFoodMealMapper.updateBdglFoodMeal(foodMeal);
            }
        }
        return bdglFoodMapper.updateBdglFood(bdglFood);
    }

    /**
     * 批量删除伙食管理登记薄
     *
     * @param ids 需要删除的伙食管理登记薄主键
     * @return 结果
     */
    @Override
    public int deleteBdglFoodByIds(Integer[] ids) {
        for (Integer id : ids) {
            BdglFoodMeal bdglFoodMeal = new BdglFoodMeal();
            bdglFoodMeal.setFoodId(id);
            List<BdglFoodMeal> bdglFoodMeals = bdglFoodMealMapper.selectBdglFoodMealList(bdglFoodMeal);
            if (bdglFoodMeals != null) {
                for (BdglFoodMeal foodMeal : bdglFoodMeals) {
                    bdglFoodMealMapper.deleteBdglFoodMealById(foodMeal.getId());
                }
            }
            BdglFoodShopping bdglFoodShopping = new BdglFoodShopping();
            bdglFoodShopping.setFoodId(id);
            List<BdglFoodShopping> bdglFoodShoppings = bdglFoodShoppingMapper.selectBdglFoodShoppingList(bdglFoodShopping);
            if (bdglFoodShoppings != null) {
                for (BdglFoodShopping foodShopping : bdglFoodShoppings) {
                    bdglFoodShoppingMapper.deleteBdglFoodShoppingById(foodShopping.getId());
                }
            }
            BdglFoodProcurement bdglFoodProcurement = new BdglFoodProcurement();
            bdglFoodProcurement.setFoodId(id);
            List<BdglFoodProcurement> bdglFoodProcurements = bdglFoodProcurementMapper.selectBdglFoodProcurementList(bdglFoodProcurement);
            if (bdglFoodProcurements != null) {
                for (BdglFoodProcurement foodProcurement : bdglFoodProcurements) {
                    bdglFoodProcurementMapper.deleteBdglFoodProcurementById(foodProcurement.getId());
                }
            }

            BdglFoodKind bdglFoodKind = new BdglFoodKind();
            bdglFoodKind.setFoodId(id);
            List<BdglFoodKind> bdglFoodKinds = bdglFoodKindMapper.selectBdglFoodKindList(bdglFoodKind);
            if (bdglFoodKinds != null) {
                for (BdglFoodKind foodKind : bdglFoodKinds) {
                    bdglFoodKindMapper.deleteBdglFoodKindById(foodKind.getId());
                }
            }

            BdglFoodBoardWages bdglFoodBoardWages = new BdglFoodBoardWages();
            bdglFoodBoardWages.setFoodId(id);
            List<BdglFoodBoardWages> bdglFoodBoardWages1 = bdglFoodBoardWagesMapper.selectBdglFoodBoardWagesList(bdglFoodBoardWages);
            if (bdglFoodBoardWages1 != null) {
                for (BdglFoodBoardWages foodBoardWages : bdglFoodBoardWages1) {
                    bdglFoodBoardWagesMapper.deleteBdglFoodBoardWagesById(foodBoardWages.getId());
                }
            }

        }
        return bdglFoodMapper.deleteBdglFoodByIds(ids);
    }

    /**
     * 删除伙食管理登记薄信息
     *
     * @param id 伙食管理登记薄主键
     * @return 结果
     */
    @Override
    public int deleteBdglFoodById(Integer id) {
        return bdglFoodMapper.deleteBdglFoodById(id);
    }
}
