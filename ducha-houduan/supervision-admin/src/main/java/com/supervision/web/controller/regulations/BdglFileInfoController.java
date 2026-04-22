package com.supervision.web.controller.regulations;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.DateUtils;
import com.supervision.regulations.domain.BdglFileResd;
import com.supervision.regulations.domain.BdglRuleDirectory;
import com.supervision.regulations.mapper.BdglFileResdMapper;
import com.supervision.regulations.service.IBdglRuleDirectoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.regulations.domain.BdglFileInfo;
import com.supervision.regulations.service.IBdglFileInfoService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 规章制度文件记录Controller
 *
 * @author supervision
 * @date 2022-03-15
 */
@Transactional
@RestController
@RequestMapping("/regulations/fileinfo")
public class BdglFileInfoController extends BaseController
{
    @Autowired
    private IBdglRuleDirectoryService bdglRuleDirectoryService;
    @Autowired
    private IBdglFileInfoService bdglFileInfoService;
    @Autowired
    private BdglFileResdMapper resdMapper;
    @Value("${supervision.profile}")
    private String profile;
    /**
     * 查询规章制度文件记录列表
     */
    @PreAuthorize("@ss.hasPermi('regulations:fileinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglFileInfo bdglFileInfo)
    {

        startPage();
        List<BdglFileInfo> list  = bdglFileInfoService.selectBdglFileInfoList(bdglFileInfo);
        return getDataTable(list);
    }

    /**
     * 根据父ID查询下面所有文件
     * @param parentid
     * @return
     */
    @GetMapping("/listParent")
    public TableDataInfo listParent(BdglFileInfo bdglFileInfo,Long parentid)
    {
        startPage();
        bdglFileInfo.setParentid(parentid);
        List<BdglFileInfo> list  = bdglFileInfoService.selectParentIdfile(bdglFileInfo);
        return getDataTable(list);
    }


    /**
     * 导出规章制度文件记录列表
     */
    @PreAuthorize("@ss.hasPermi('regulations:fileinfo:export')")
    @Log(title = "规章制度文件记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglFileInfo bdglFileInfo)
    {
        List<BdglFileInfo> list = bdglFileInfoService.selectBdglFileInfoList(bdglFileInfo);
        ExcelUtil<BdglFileInfo> util = new ExcelUtil<BdglFileInfo>(BdglFileInfo.class);
        return util.exportExcel(list, "规章制度文件记录数据");
    }

    /**
     * 获取规章制度文件记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('regulations:fileinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglFileInfoService.selectBdglFileInfoById(id));
    }

    /**
     * 新增规章制度文件记录
     */
    @PreAuthorize("@ss.hasPermi('regulations:fileinfo:add')")
    @Log(title = "规章制度文件记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglFileInfo bdglFileInfoss) throws IOException {
        LoginUser loginUser = getLoginUser();

        return toAjax(bdglFileInfoService.insertBdglFileInfo(loginUser,bdglFileInfoss));
    }

    /**
     * 修改规章制度文件记录
     */
    @PreAuthorize("@ss.hasPermi('regulations:fileinfo:edit')")
    @Log(title = "规章制度文件记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglFileInfo bdglFileInfo)
    {
        LoginUser loginUser = getLoginUser();
        if(bdglFileInfo.getCommon()!=null){
            bdglFileInfo.setReadVolume(bdglFileInfo.getReadVolume()+1);
            //判断当前问价夹是否存在阅读记录  存在阅读数=+1
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            String format = simpleDateFormat.format(date);
//            String[] split = format.split("-");
//            String s = split[split.length - 1];
            BdglFileResd bdglFileResd = new BdglFileResd();
            bdglFileResd.setMonth(format);
            bdglFileResd.setDirectoryId(bdglFileInfo.getDirectoryId());
            List<BdglFileResd> bdglFileResds = resdMapper.selectBdglFileResdList(bdglFileResd);
            if(bdglFileResds.size()!=0){
                for (BdglFileResd fileResd : bdglFileResds) {
                    fileResd.setReadNumber(fileResd.getReadNumber()+1);
                    resdMapper.updateBdglFileResd(fileResd);
                    //return toAjax(resdMapper.updateBdglFileResd(fileResd));
                }
            //不存在添加记录
            }else{
                //bdglFileResd.setReadNumber(1L);
                bdglFileResd.setDocumentCategory(bdglFileInfo.getDirectoryName());
                resdMapper.insertBdglFileResd(bdglFileResd);
                //return toAjax(resdMapper.insertBdglFileResd(bdglFileResd));
            }
        }
        bdglFileInfo.setCreateUser(loginUser.getUsername());
        bdglFileInfo.setCreateUserid(loginUser.getUserId());
        int num= bdglFileInfoService.updateBdglFileInfo(bdglFileInfo);
        return toAjax(num);
    }

    /**
     * 删除规章制度文件记录
     */
    @PreAuthorize("@ss.hasPermi('regulations:fileinfo:remove')")
    @Log(title = "规章制度文件记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglFileInfoService.deleteBdglFileInfoByIds(ids));
    }
    /**
    * 获取本月阅读量  上月阅读量
    * */
    @GetMapping("/getReadNumber")
    public AjaxResult getReadNumber(){
        List<Map<String,Object>>list=new ArrayList<>();
        List<Map<String,Object>>bdglFileInfos=bdglFileInfoService.selectResdNumber();
        int a=0;
        for (Map<String, Object> bdglFileInfo : bdglFileInfos) {
            a++;
            list.add(bdglFileInfo);
            if(a==7){
                break;
            }
        }
        return AjaxResult.success(list);

    }
}
