package com.supervision.web.controller.bdglregular;

import com.supervision.bdglregular.domain.BdglRegular;
import com.supervision.bdglregular.domain.BdglSpecial;
import com.supervision.bdglregular.service.IBdglRegularService;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.utils.DateUtils;
import com.supervision.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 经常性检查统计Controller
 *
 * @author supervision
 * @date 2022-03-09
 */
@RestController
@RequestMapping("/bdglregular/regularcountinfo")
public class RegularCountController extends BaseController {

    @Autowired
    private IBdglRegularService bdglRegularService;

    @Autowired
    private ISysDeptService deptService;

    private DecimalFormat df = new DecimalFormat("#0.00"); //定义截取两位小数

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 营级单位排名
     */
    @GetMapping("/unitrank")
    public AjaxResult unitRank()
    {
        //获取二级部门
        List<SysDept> sysDepts = twoJiDept();

        //返回值定义
        List<Map<String,String>> returnList = new ArrayList<>();
        for (SysDept sysDept : sysDepts) {
            Map<String,String> map = new HashMap<>();

            Long deptId = sysDept.getDeptId();
            List<BdglRegular> bdglRegulars = bdglRegularService.selectDeptById(deptId);
            BigDecimal addscore = BigDecimal.valueOf(0); //定义加分
            BigDecimal subscore = BigDecimal.valueOf(0); //定义减分
            for (BdglRegular regular : bdglRegulars) {
                //计算减分数据
                if("0".equals(regular.getCaoZuoType())) {
                    subscore = subscore.add(regular.getScore());
                }
                //计算加分数据
                if("1".equals(regular.getCaoZuoType())) {
                    addscore = addscore.add(regular.getScore());
                }
            }
            map.put("id", deptId +"");
            map.put("name", sysDept.getDeptName());
            map.put("addscore", addscore + "");
            map.put("subscore", subscore + "");
            map.put("zongfen", sysDept.getScore() + "");
            returnList.add(map);
        }

        //排序
        Collections.sort(returnList, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> a, Map<String, String> b) {
                if(Double.parseDouble(b.get("zongfen")) > Double.parseDouble(a.get("zongfen"))){
                    return 1;
                }
                if(Double.parseDouble(b.get("zongfen")) == Double.parseDouble(a.get("zongfen"))){
                    return 0;
                }
                return -1;
            }
        });
        return AjaxResult.success(returnList);
    }

    /**
     * 整体比例完成情况
     * @return
     */
    @GetMapping("/completeproportion")
    public AjaxResult completeProportion() {
        BdglRegular bd = new BdglRegular();
        bd.setCaoZuoType("0");
        List<BdglRegular> bdglRegulars = bdglRegularService.selectBdglRegularList(bd);

        bd.setCurrentState("待处理");
        List<BdglRegular> daichulizhi = bdglRegularService.selectBdglRegularList(bd); //待处理值

        bd.setCurrentState("待验收");
        List<BdglRegular> daiyanshouzhi = bdglRegularService.selectBdglRegularList(bd); //待验收值

        bd.setCurrentState("未通过");
        List<BdglRegular> weitongguozhi = bdglRegularService.selectBdglRegularList(bd); //未通过值

        bd.setCurrentState("已通过");
        List<BdglRegular> yitongguozhi = bdglRegularService.selectBdglRegularList(bd); //已通过值

        bd.setCurrentState("已逾期");
        List<BdglRegular> yiYuQi = bdglRegularService.selectBdglRegularList(bd); //已通过值

        List<Map<String,Integer>> returnlist = new ArrayList<>();
        Map<String, Integer> map = new HashMap();

        map.put("daichuli", daichulizhi.size());
        map.put("daiyanshou", daiyanshouzhi.size());
        map.put("weitongguo", weitongguozhi.size());
        map.put("yitongguo", yitongguozhi.size());
        map.put("yiYuQi", yiYuQi.size());
        returnlist.add(map);
        return  AjaxResult.success(returnlist);
    }

    /**
     * 待处理机待验收问题
     * @return
     */
    @GetMapping("/pendingacceptance")
    public AjaxResult pendingAcceptance() throws ParseException {
        String dangqianshijian = sdf.format(new Date());
        BdglRegular bd = new BdglRegular();
        bd.setCaoZuoType("0");
        List<BdglRegular> bdglRegularn = bdglRegularService.selectBdglRegularList(bd);
        List<BdglRegular> bdglRegulars = new ArrayList<>();
        List<SysDept> sysDepts = twoJiDept();
        for (BdglRegular bdglRegular : bdglRegularn) {
            for (SysDept sysDept : sysDepts) {
                if(bdglRegular.getInspectedUnitId().equals(sysDept.getDeptId())) {
                    bdglRegulars.add(bdglRegular);
                }
            }
        }

        List<BdglRegular> returnList = new ArrayList<>();
        for (BdglRegular bdglRegular : bdglRegularn) {
            String rectificationTime = bdglRegular.getRectificationTime(); //整改时间
            if(sdf.parse(dangqianshijian).getTime() <= sdf.parse(rectificationTime).getTime()) { //判断整改时间是否已过
                if("待处理".equals(bdglRegular.getCurrentState())) {
                    returnList.add(bdglRegular);
                }
                if("待验收".equals(bdglRegular.getCurrentState())) {
                    returnList.add(bdglRegular);
                }
            }
        }
        return AjaxResult.success(returnList);
    }

    /**
     * 各营级及机关单位
     * @return
     */
    @GetMapping("/unitproportion")
    public AjaxResult unitProportion() {
        //获取二级部门
        List<SysDept> sysDepts = twoJiDept();
        //返回值定义
        List<Map<String,String>> returnList= new ArrayList<>();
        for (SysDept sysDept : sysDepts) {
            Map<String,String> map = new HashMap<>();

            Long deptId = sysDept.getDeptId();
            List<BdglRegular> list = new ArrayList<>();
            List<BdglRegular> bdglRegulars = bdglRegularService.selectDeptById(deptId);
            for (BdglRegular bdglRegular : bdglRegulars) {
                if("0".equals(bdglRegular.getCaoZuoType())) {
                    list.add(bdglRegular);
                }
            }


            Integer daichulizhi = 0;
            Integer daiyanshouzhi = 0;
            Integer weitongguozhi = 0;
            Integer yitongguozhi = 0;
            Integer yiYuQi = 0;
            List<BdglRegular> lists = bdglRegularService.selectDeptByIdAndStates(deptId);
            for (BdglRegular bdglSpecial : lists) {
                if("未通过".equals(bdglSpecial.getCurrentState())) {
                    weitongguozhi += 1;
                }

                if("已通过".equals(bdglSpecial.getCurrentState())) {
                    yitongguozhi += 1;
                }

                if("待验收".equals(bdglSpecial.getCurrentState())) {
                    daiyanshouzhi += 1;
                }
                
                if("已逾期".equals(bdglSpecial.getCurrentState())) {
                    yiYuQi += 1;
                }

                if("待处理".equals(bdglSpecial.getCurrentState())) {
                    Date date = DateUtils.parseDate(bdglSpecial.getRectificationTime());
                    if (new Date().getTime() > date.getTime()) {
                        //判断整改时间是否已过
                        bdglSpecial.setCurrentState("已逾期");
                        bdglSpecial.setPaixuValue(6);
                        bdglRegularService.updateBdglRegular(bdglSpecial);
                        yiYuQi += 1;
                    }else{
                        daichulizhi += 1;
                    }
                }
            }


            Map<String,String> maps = new HashMap();
            maps.put("name", sysDept.getDeptName());
            String format1 = "";
            if(daichulizhi > 0) {
                format1 = daichulizhi + "";
            }else{
                format1 = "0";
            }
            maps.put("daichuli",format1);
            String format2 = "";
            if(daiyanshouzhi > 0) {
                format2 = daiyanshouzhi + "";
            }else{
                format2 = "0";
            }
            maps.put("daiyanshou",format2);
            String format3 = "";
            if(weitongguozhi > 0) {
                format3 = weitongguozhi + "";
            }else{
                format3 = "0";
            }
            maps.put("weitongguo",format3);
            String format4 = "";
            if(yitongguozhi > 0) {
                format4 = yitongguozhi + "";
            }else{
                format4 = "0";
            }
            maps.put("yitongguo", format4);
            String format5 = "";
            if(yiYuQi > 0) {
                format5 = yiYuQi + "";
            }else{
                format5 = "0";
            }
            maps.put("yiYuQi", format5);
            maps.put("deptId",sysDept.getDeptId().toString());
            returnList.add(maps);
        }
        return AjaxResult.success(returnList);
    }


    /**
     * 获取到二级部门
     * @return
     */
    public List<SysDept> twoJiDept() {
        List<SysDept> returnDept = new ArrayList<>();
        //获取二级部门
        SysDept sd = new SysDept();
        sd.setParentId(Long.valueOf(100));
        List<SysDept> sysDepts = deptService.selectDeptList(sd);
        for (SysDept sysDept : sysDepts) {
            if("Y".equals(sysDept.getIsShow())){
                returnDept.add(sysDept);
            }
        }
        return returnDept;
    }

}
