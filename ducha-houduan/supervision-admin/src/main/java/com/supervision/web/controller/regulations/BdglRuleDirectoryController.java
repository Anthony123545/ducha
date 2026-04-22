package com.supervision.web.controller.regulations;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.regulations.domain.BdglFileInfo;
import com.supervision.regulations.domain.BdglFileResd;
import com.supervision.regulations.mapper.BdglFileResdMapper;
import com.supervision.regulations.service.impl.BdglFileInfoServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.supervision.regulations.domain.BdglRuleDirectory;
import com.supervision.regulations.service.IBdglRuleDirectoryService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 规章制度目录Controller
 * 
 * @author supervision
 * @date 2022-03-15
 */
@RestController
@RequestMapping("/regulations/directoryinfo")
public class BdglRuleDirectoryController extends BaseController
{
    @Autowired
    private IBdglRuleDirectoryService bdglRuleDirectoryService;
    @Autowired
    private BdglFileInfoServiceImpl fileInfoService;
    @Autowired
    private BdglFileResdMapper fileResdMapper;


    /**
     * 查询规章制度目录列表
     */
    @PreAuthorize("@ss.hasPermi('regulations:directoryinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglRuleDirectory bdglRuleDirectory)
    {
        startPage();
        List<BdglRuleDirectory> list = bdglRuleDirectoryService.selectBdglRuleDirectoryList(bdglRuleDirectory);
        return getDataTable(list);
    }
    @GetMapping("/lists")
    public TableDataInfo lists(BdglRuleDirectory bdglRuleDirectory)
    {
        startPage();
        List<BdglRuleDirectory> list = bdglRuleDirectoryService.selectBdglRuleDirectoryList(bdglRuleDirectory);
        return getDataTable(list);
    }


    /**
     * 导出规章制度目录列表
     */
    @PreAuthorize("@ss.hasPermi('regulations:directoryinfo:export')")
    @Log(title = "规章制度目录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglRuleDirectory bdglRuleDirectory)
    {
        List<BdglRuleDirectory> list = bdglRuleDirectoryService.selectBdglRuleDirectoryList(bdglRuleDirectory);
        ExcelUtil<BdglRuleDirectory> util = new ExcelUtil<BdglRuleDirectory>(BdglRuleDirectory.class);
        return util.exportExcel(list, "规章制度目录数据");
    }

    /**
     * 获取规章制度目录详细信息
     */
    @PreAuthorize("@ss.hasPermi('regulations:directoryinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglRuleDirectoryService.selectBdglRuleDirectoryById(id));
    }

    /**
     * 新增规章制度目录
     */
    @PreAuthorize("@ss.hasPermi('regulations:directoryinfo:add')")
    @Log(title = "规章制度目录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglRuleDirectory bdglRuleDirectory) throws IOException {
        LoginUser loginUser = getLoginUser();
        return toAjax(bdglRuleDirectoryService.insertBdglRuleDirectory(bdglRuleDirectory,loginUser));
    }

    /**
     * 修改规章制度目录
     */
    @PreAuthorize("@ss.hasPermi('regulations:directoryinfo:edit')")
    @Log(title = "规章制度目录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglRuleDirectory bdglRuleDirectory)
    {
        return toAjax(bdglRuleDirectoryService.updateBdglRuleDirectory(bdglRuleDirectory));
    }

    /**
     * 删除规章制度目录
     */
    @PreAuthorize("@ss.hasPermi('regulations:directoryinfo:remove')")
    @Log(title = "规章制度目录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        //判断是否有子文件夹
        BdglRuleDirectory bdglRuleDirectory1 = new BdglRuleDirectory();
        bdglRuleDirectory1.setParentId(id);
        List<BdglRuleDirectory> bdglRuleDirectories1 = bdglRuleDirectoryService.selectBdglRuleDirectoryList(bdglRuleDirectory1);
        int size = bdglRuleDirectories1.size();

        boolean b = size != 0;
        if(size!=0){
            return AjaxResult.error("请先删除子文件夹");
        }
        BdglFileInfo bdglFileInfo = new BdglFileInfo();
        bdglFileInfo.setDirectoryId(id);
        List<BdglFileInfo> bdglFileInfos = fileInfoService.selectBdglFileInfoList(bdglFileInfo);
        if(bdglFileInfos.size()!=0){
            return AjaxResult.error("请先删除改文件夹内数据");
        }
        BdglFileResd bdglFileResd = new BdglFileResd();
        bdglFileResd.setDirectoryId(id);
        List<BdglFileResd> bdglFileResds = fileResdMapper.selectBdglFileResdList(bdglFileResd);
        if(bdglFileResds.size()>0){
            for (BdglFileResd fileResd : bdglFileResds) {
                fileResdMapper.deleteBdglFileResdById(fileResd.getId());
            }
        }
        return toAjax(bdglRuleDirectoryService.deleteBdglRuleDirectoryById(id));
    }
}
