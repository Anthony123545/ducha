package com.supervision.web.controller.medicalhealth;

import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.medicalhealth.domain.BdglConsumePutin;
import com.supervision.medicalhealth.domain.BdglDurgPutin;
import com.supervision.medicalhealth.service.IBdglConsumePutinService;
import com.supervision.medicalhealth.service.IBdglDurgPutinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 药品过期情况Controller
 *
 * @author hr
 * @date 2022-02-12
 */
@RestController
@RequestMapping("/medicalhealth/guoqi")
public class ConsumablesExpiredController extends BaseController {
    @Autowired
    private IBdglConsumePutinService bdglConsumePutinService;
    //查询过期耗材数据
    @GetMapping("/getGuoQi")
    public TableDataInfo getGuoQi(BdglConsumePutin bdglConsumePutin){
        startPage();
        List<BdglConsumePutin> list = bdglConsumePutinService.selectGuoQiList(bdglConsumePutin);
        return getDataTable(list);
    }
    /**
     * 过期耗材销毁
     * */
    @PostMapping("/haoCaiXiaoHui")
    public AjaxResult haoCaiXiaoHui(@RequestBody BdglConsumePutin bdglConsumePutin){
         return toAjax(bdglConsumePutinService.haoCaiXiaoHui(bdglConsumePutin));
     }
}
