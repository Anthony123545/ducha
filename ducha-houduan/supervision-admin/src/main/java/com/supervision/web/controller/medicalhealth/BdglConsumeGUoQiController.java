package com.supervision.web.controller.medicalhealth;

import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.medicalhealth.domain.BdglConsumePutin;
import com.supervision.medicalhealth.service.IBdglConsumePutinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medicalhealth/consumeGuoQi")
public class BdglConsumeGUoQiController extends BaseController {
    @Autowired
    private IBdglConsumePutinService consumePutinService;
    @GetMapping("/list")
    public TableDataInfo list(BdglConsumePutin consumePutin){
        List<BdglConsumePutin> bdglConsumePutins = consumePutinService.selectGuoQiList(consumePutin);
        return getDataTable(bdglConsumePutins);
    }
}
