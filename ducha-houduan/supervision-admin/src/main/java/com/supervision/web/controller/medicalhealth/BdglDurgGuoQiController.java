package com.supervision.web.controller.medicalhealth;

import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.medicalhealth.domain.BdglDurgPutin;
import com.supervision.medicalhealth.service.IBdglDurgPutinService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 药品过期情况Controller
 *
 * @author hr
 * @date 2022-02-12
 */
@RestController
@RequestMapping("/medicalhealth/yaopinguoqi")
public class BdglDurgGuoQiController extends BaseController {
    @Autowired
    private IBdglDurgPutinService bdglDurgPutinService;
    //查询过期药品数据 bdgl_durg_putin
    @GetMapping("/getGuoQi")
    public TableDataInfo getGuoQi(BdglDurgPutin bdglDurgPutin){
        startPage();
        bdglDurgPutin.setStatus("0");
        bdglDurgPutin.setOverduedate(new Date());
        List<BdglDurgPutin> list = bdglDurgPutinService.selectGuoQiList(bdglDurgPutin);
        return getDataTable(list);
    }
    /**
     * 过期药品销毁
     * */
    @GetMapping
    public AjaxResult edit ( long id){
        return toAjax(bdglDurgPutinService.yaoPinXiaoHui(id));
    }

}
