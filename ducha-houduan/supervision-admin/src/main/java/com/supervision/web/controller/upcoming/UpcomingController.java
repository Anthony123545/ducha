package com.supervision.web.controller.upcoming;

import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.workingArrangements.domain.BdglWorkProgress;
import com.supervision.workingArrangements.service.IBdglWorkProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/upcoming/upcomingController")
public class UpcomingController  extends BaseController {
    @Autowired
    private IBdglWorkProgressService workProgressService;

    /**
     * 工作任务代办事项
     * */
    @GetMapping("/getWorkTask")
    public AjaxResult getWorkTask(){
        LoginUser loginUser = getLoginUser();
        BdglWorkProgress bdglWorkProgress = new BdglWorkProgress();
        bdglWorkProgress.setPeopleId(loginUser.getUserId());
        bdglWorkProgress.setStauts(0);
        List<BdglWorkProgress> bdglWorkProgresses = workProgressService.selectBdglWorkProgressList(bdglWorkProgress);
        int a=0;
        ArrayList<BdglWorkProgress> bdglWorkProgresses1 = new ArrayList<>();
        for (BdglWorkProgress workProgress : bdglWorkProgresses) {
            a++;
            bdglWorkProgresses1.add(workProgress);
            if(a==7){
                break;
            }
        }
        return AjaxResult.success(bdglWorkProgresses1);
    }
}
