package com.supervision.web.controller.medicalhealth;

import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.medicalhealth.domain.BdglAntiepidemicPutin;
import com.supervision.medicalhealth.domain.BdglDurgPutin;
import com.supervision.medicalhealth.service.IBdglAntiepidemicPutinService;
import com.supervision.medicalhealth.service.IBdglDurgPutinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/medicalhealth/fangyiguoqi")
public class BdglAntiepidemicGuoQiController extends BaseController {
    @Autowired
    private IBdglAntiepidemicPutinService bdglAntiepidemicPutinService;
    //查询过期药品数据 bdgl_durg_putin
    @GetMapping("/getGuoQi")
    public TableDataInfo getGuoQi(BdglAntiepidemicPutin bdglDurgPutin){
        startPage();
        List<BdglAntiepidemicPutin> list = bdglAntiepidemicPutinService.selectGuoQiList(bdglDurgPutin);
        return getDataTable(list);
    }

}
