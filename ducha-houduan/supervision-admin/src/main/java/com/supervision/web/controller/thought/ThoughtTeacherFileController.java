package com.supervision.web.controller.thought;

import java.io.File;
import java.util.List;

import com.supervision.thought.domain.ThoughtTeacher;
import com.supervision.thought.service.IThoughtTeacherService;
import org.springframework.beans.factory.annotation.Value;
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
import com.supervision.thought.domain.ThoughtTeacherFile;
import com.supervision.thought.service.IThoughtTeacherFileService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 教案库Controller
 * 
 * @author supervision
 * @date 2022-04-11
 */
@RestController
@RequestMapping("/thought/teacherFile")
public class ThoughtTeacherFileController extends BaseController
{
    @Autowired
    private IThoughtTeacherFileService thoughtTeacherFileService;
    @Autowired
    private IThoughtTeacherService thoughtTeacherService;

    @Value("${supervision.profile}")
    private String profile;

    /**
     * 查询教案库列表
     */
    @PreAuthorize("@ss.hasPermi('thought:teacherFile:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtTeacherFile thoughtTeacherFile)
    {
        startPage();
        List<ThoughtTeacherFile> list = thoughtTeacherFileService.selectThoughtTeacherFileList(thoughtTeacherFile);
        return getDataTable(list);
    }

    /**
     * 导出教案库列表
     */
    @PreAuthorize("@ss.hasPermi('thought:teacherFile:export')")
    @Log(title = "教案库", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtTeacherFile thoughtTeacherFile)
    {
        List<ThoughtTeacherFile> list = thoughtTeacherFileService.selectThoughtTeacherFileList(thoughtTeacherFile);
        ExcelUtil<ThoughtTeacherFile> util = new ExcelUtil<ThoughtTeacherFile>(ThoughtTeacherFile.class);
        return util.exportExcel(list, "教案库数据");
    }

    /**
     * 获取教案库详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:teacherFile:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(thoughtTeacherFileService.selectThoughtTeacherFileById(id));
    }

    /**
     * 新增教案库
     */
    @PreAuthorize("@ss.hasPermi('thought:teacherFile:add')")
    @Log(title = "教案库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtTeacherFile thoughtTeacherFile)
    {
        List<ThoughtTeacherFile> thoughtTeacherFiles = thoughtTeacherFile.getThoughtTeacherFiles();
         for (ThoughtTeacherFile teacherFile : thoughtTeacherFiles) {
            teacherFile.setDirectoryId(thoughtTeacherFile.getDirectoryId());
            teacherFile.setDirectoryName(thoughtTeacherFile.getDirectoryName());
            ThoughtTeacher thoughtTeacher = thoughtTeacherService.selectThoughtTeacherById(teacherFile.getDirectoryId());
            thoughtTeacher.setDocumentsNumber(thoughtTeacher.getDocumentsNumber()+1);
            thoughtTeacherService.updateThoughtTeacher(thoughtTeacher);
            thoughtTeacherFileService.insertThoughtTeacherFile(teacherFile);
        }
        return AjaxResult.success(1);
    }

    /**
     * 修改教案库
     */
    @PreAuthorize("@ss.hasPermi('thought:teacherFile:edit')")
    @Log(title = "教案库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtTeacherFile thoughtTeacherFile)
    {
        return toAjax(thoughtTeacherFileService.updateThoughtTeacherFile(thoughtTeacherFile));
    }
    @PutMapping("/update")
    public AjaxResult update(@RequestBody ThoughtTeacherFile thoughtTeacherFile)
    {
        ThoughtTeacherFile thoughtTeacherFile1 = thoughtTeacherFileService.selectThoughtTeacherFileById(thoughtTeacherFile.getId());
        thoughtTeacherFile.setReadVolume(thoughtTeacherFile1.getReadVolume()+1);
        return toAjax(thoughtTeacherFileService.updateThoughtTeacherFile(thoughtTeacherFile));
    }

    /**
     * 删除教案库
     */
    @PreAuthorize("@ss.hasPermi('thought:teacherFile:remove')")
    @Log(title = "教案库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        for (Long id : ids) {
            ThoughtTeacherFile thoughtTeacherFile = thoughtTeacherFileService.selectThoughtTeacherFileById(id);
            ThoughtTeacher thoughtTeacher = thoughtTeacherService.selectThoughtTeacherById(thoughtTeacherFile.getDirectoryId());
            thoughtTeacher.setDocumentsNumber(thoughtTeacher.getDocumentsNumber()-1);
            thoughtTeacherService.updateThoughtTeacher(thoughtTeacher);
            String parentDirName = thoughtTeacherFile.getParentDirName();
            String replace = parentDirName.replace("/profile", "");
            File file = new File(profile+replace.trim());
        }
        return toAjax(thoughtTeacherFileService.deleteThoughtTeacherFileByIds(ids));
    }
    /**阅读排行*/
    @GetMapping("/getReadRanking")
    public AjaxResult getReadRanking(){
        List<ThoughtTeacherFile> thoughtTeacherFiles=thoughtTeacherFileService.selectReadRanking();
        return AjaxResult.success(thoughtTeacherFiles);
    }
}
