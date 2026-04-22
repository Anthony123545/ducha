package com.supervision.web.controller.thought;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.system.service.ISysUserService;
import com.supervision.thought.domain.ThoughtTeacherFile;
import com.supervision.thought.service.IThoughtTeacherFileService;
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
import com.supervision.thought.domain.ThoughtTeacher;
import com.supervision.thought.service.IThoughtTeacherService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 教案库设置Controller
 *
 * @author supervision
 * @date 2022-04-11
 */
@RestController
@RequestMapping("/thought/teacher")
public class ThoughtTeacherController extends BaseController {
    @Autowired
    private IThoughtTeacherService thoughtTeacherService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IThoughtTeacherFileService thoughtTeacherFileService;

    /**
     * 查询教案库设置列表
     */
    @PreAuthorize("@ss.hasPermi('thought:teacher:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtTeacher thoughtTeacher) {
        startPage();
        List<ThoughtTeacher> list = thoughtTeacherService.selectThoughtTeacherList(thoughtTeacher);
        return getDataTable(list);
    }
    @GetMapping("/lists")
    public TableDataInfo lists(ThoughtTeacher thoughtTeacher) {
        ThoughtTeacher thoughtTeacher1 = thoughtTeacherService.selectThoughtTeacherById(thoughtTeacher.getParentId());
        startPage();
        if(thoughtTeacher1!=null&&thoughtTeacher.getParentDirectory()!=null){
            thoughtTeacher.setParentId(thoughtTeacher1.getParentId());
        }
        List<ThoughtTeacher> list = thoughtTeacherService.selectThoughtTeacherList(thoughtTeacher);
        return getDataTable(list);
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    public TableDataInfo treeselect(ThoughtTeacher thoughtTeacher) {
        ArrayList<ThoughtTeacher> thoughtTeachers1 = new ArrayList<>();
        List<ThoughtTeacher> thoughtTeachers = buildDeptTreeSelect(thoughtTeacher);

        return getDataTable(thoughtTeachers);
    }

    private List<ThoughtTeacher> buildDeptTreeSelect(ThoughtTeacher thoughtTeacher) {
        ArrayList<ThoughtTeacher> chilMenus = new ArrayList<>();
        List<ThoughtTeacher> thoughtTeachers = thoughtTeacherService.selectThoughtTeacherList(thoughtTeacher);
        if (thoughtTeachers.size() > 0) {
            ThoughtTeacher thoughtTeacher1 = new ThoughtTeacher();
            for (ThoughtTeacher teacher : thoughtTeachers) {
                thoughtTeacher1.setParentId(teacher.getId());
                List<ThoughtTeacher> thoughtTeachers1 = buildDeptTreeSelect(thoughtTeacher1);
                teacher.setChildren(thoughtTeachers1);
            }
        }
        thoughtTeacher.setChildren(chilMenus);
        return thoughtTeachers;
    }


    /**
     * 导出教案库设置列表
     */
    @PreAuthorize("@ss.hasPermi('thought:teacher:export')")
    @Log(title = "教案库设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtTeacher thoughtTeacher) {
        List<ThoughtTeacher> list = thoughtTeacherService.selectThoughtTeacherList(thoughtTeacher);
        ExcelUtil<ThoughtTeacher> util = new ExcelUtil<ThoughtTeacher>(ThoughtTeacher.class);
        return util.exportExcel(list, "教案库设置数据");
    }

    /**
     * 获取教案库设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:teacher:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(thoughtTeacherService.selectThoughtTeacherById(id));
    }

    /**
     * 新增教案库设置
     */
    @PreAuthorize("@ss.hasPermi('thought:teacher:add')")
    @Log(title = "教案库设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtTeacher thoughtTeacher) {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        thoughtTeacher.setCreateBy(sysUser.getNickName());
        thoughtTeacher.setCreateUserid(sysUser.getUserId());

        return toAjax(thoughtTeacherService.insertThoughtTeacher(thoughtTeacher));
    }

    /**
     * 修改教案库设置
     */
    @PreAuthorize("@ss.hasPermi('thought:teacher:edit')")
    @Log(title = "教案库设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtTeacher thoughtTeacher) {
        return toAjax(thoughtTeacherService.updateThoughtTeacher(thoughtTeacher));
    }

    /**
     * 删除教案库设置
     */
    @PreAuthorize("@ss.hasPermi('thought:teacher:remove')")
    @Log(title = "教案库设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {

            ThoughtTeacher thoughtTeacher2 = new ThoughtTeacher();
            thoughtTeacher2.setParentId(id);
            List<ThoughtTeacher> thoughtTeachers1 = thoughtTeacherService.selectThoughtTeacherList(thoughtTeacher2);
            if(thoughtTeachers1.size()>0){
                return AjaxResult.error("请先删除子文件夹");
            }
            ThoughtTeacherFile thoughtTeacherFile = new ThoughtTeacherFile();
            thoughtTeacherFile.setDirectoryId(id);
            List<ThoughtTeacherFile> thoughtTeacherFiles = thoughtTeacherFileService.selectThoughtTeacherFileList(thoughtTeacherFile);
            if(thoughtTeacherFiles.size()>0){
                return AjaxResult.error("请删除文件夹内文件");
            }

        }
        return toAjax(thoughtTeacherService.deleteThoughtTeacherByIds(ids));
    }
}
