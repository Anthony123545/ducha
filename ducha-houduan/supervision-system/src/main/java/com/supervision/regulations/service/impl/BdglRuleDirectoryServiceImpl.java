package com.supervision.regulations.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.regulations.mapper.BdglRuleDirectoryMapper;
import com.supervision.regulations.domain.BdglRuleDirectory;
import com.supervision.regulations.service.IBdglRuleDirectoryService;

/**
 * 规章制度目录Service业务层处理
 *
 * @author supervision
 * @date 2022-03-15
 */
@Service
public class BdglRuleDirectoryServiceImpl implements IBdglRuleDirectoryService
{
    @Autowired
    private BdglRuleDirectoryMapper bdglRuleDirectoryMapper;

    /**
     * 查询规章制度目录
     *
     * @param id 规章制度目录主键
     * @return 规章制度目录
     */
    @Override
    public BdglRuleDirectory selectBdglRuleDirectoryById(Long id)
    {
        return bdglRuleDirectoryMapper.selectBdglRuleDirectoryById(id);
    }

    /**
     * 查询规章制度目录列表
     *
     * @param bdglRuleDirectory 规章制度目录
     * @return 规章制度目录
     */
    @Override
    public List<BdglRuleDirectory> selectBdglRuleDirectoryList(BdglRuleDirectory bdglRuleDirectory)
    {
        if(bdglRuleDirectory.getCommon()!=null){
            bdglRuleDirectory.setParentId(0L);
        }
        List<BdglRuleDirectory> bdglRuleDirectories = bdglRuleDirectoryMapper.selectBdglRuleDirectoryLists(bdglRuleDirectory);
        return bdglRuleDirectories;
    }

    /**
     * 新增规章制度目录
     *
     * @param bdglRuleDirectory 规章制度目录
     * @return 结果
     */
    @Override
    public int insertBdglRuleDirectory(BdglRuleDirectory bdglRuleDirectory, LoginUser loginUser) throws IOException {

        bdglRuleDirectory.setCreateTime(DateUtils.getNowDate());

        BdglRuleDirectory bdglRuleDirectory1 = bdglRuleDirectoryMapper.selectBdglRuleDirectoryById(bdglRuleDirectory.getParentId());
        /*-------------------------------子级文件夹个数------------------------------------------*/
        if(bdglRuleDirectory1!=null) {
            Long subclassification = bdglRuleDirectory1.getSubclassification();
            bdglRuleDirectory1.setSubclassification(bdglRuleDirectory1.getSubclassification() + 1L);
            bdglRuleDirectoryMapper.updateBdglRuleDirectory(bdglRuleDirectory1);
        }
        /*-------------------------------子级文件夹个数------------------------------------------*/
        /*---------------------------------祖父列表赋值---------------------------------------------*/
        if(bdglRuleDirectory1!=null){
            bdglRuleDirectory.setAncestors(bdglRuleDirectory1.getAncestors() +","+bdglRuleDirectory.getParentId());
            bdglRuleDirectory.setParentDirectory(bdglRuleDirectory1.getParentDirectory()+"\\"+bdglRuleDirectory.getDirectory());
            //如果有父级文件夹 拼接父级文件夹名称
//            File file=new File("D:\\aaaaaaaaaaaa\\"+bdglRuleDirectory1.getParentDirectory()+"\\"+bdglRuleDirectory.getDirectory());
//            if(!file.exists()){//如果文件夹不存在
//                file.mkdir();//创建文件夹
//            }
        }else{
            String s = "D:\\aaaaaaaaaaaa\\"+bdglRuleDirectory.getDirectory();
            File file=new File(s);
            boolean exists = file.getParentFile().exists();
//            if(!file.exists()){//如果文件夹不存在
//                file.mkdirs();//创建文件夹
//                String parentDirectory = bdglRuleDirectory.getDirectory();
//                bdglRuleDirectory.setParentDirectory(parentDirectory);
//            }

        }
        /*---------------------------------祖父列表赋值---------------------------------------------*/
        bdglRuleDirectory.setCreateTime(new Date());
        bdglRuleDirectory.setCreateUserid(loginUser.getUserId());
        bdglRuleDirectory.setCreateUser(loginUser.getUsername());
        return bdglRuleDirectoryMapper.insertBdglRuleDirectory(bdglRuleDirectory);
    }

    /**
     * 修改规章制度目录
     *
     * @param bdglRuleDirectory 规章制度目录
     * @return 结果
     */
    @Override
    public int updateBdglRuleDirectory(BdglRuleDirectory bdglRuleDirectory)
    {
        String fileName ="";
        if(bdglRuleDirectory.getParentDirectory()!=null){
            fileName= "D:\\aaaaaaaaaaaa\\"+bdglRuleDirectory.getParentDirectory()+"\\"+bdglRuleDirectory.getParentDirectory(); //指定文件名及路径
        }else{
             fileName = "D:\\aaaaaaaaaaaa\\"+bdglRuleDirectory.getParentDirectory();
        }
        File file=new File(fileName);
        String filename=file.getAbsolutePath();
        if(filename.indexOf("/")>=0)
        {
            filename = filename.substring(0,filename.lastIndexOf("\\"));
        }
        //file.renameTo(new File(bdglRuleDirectory.getDirectory())); //改名
        bdglRuleDirectory.setUpdateTime(DateUtils.getNowDate());
        return bdglRuleDirectoryMapper.updateBdglRuleDirectory(bdglRuleDirectory);
    }

    /**
     * 批量删除规章制度目录
     *
     * @param ids 需要删除的规章制度目录主键
     * @return 结果
     */
    @Override
    public int deleteBdglRuleDirectoryByIds(Long[] ids)
    {
        return bdglRuleDirectoryMapper.deleteBdglRuleDirectoryByIds(ids);
    }

    /**
     * 删除规章制度目录信息
     *
     * @param id 规章制度目录主键
     * @return 结果
     */
    @Override
    public int deleteBdglRuleDirectoryById(Long id)
    {
        BdglRuleDirectory bdglRuleDirectory = bdglRuleDirectoryMapper.selectBdglRuleDirectoryById(id);
        if(bdglRuleDirectory.getParentId()!=0){
            BdglRuleDirectory bdglRuleDirectory1 = new BdglRuleDirectory();
            bdglRuleDirectory1.setId(bdglRuleDirectory.getParentId());
            BdglRuleDirectory bdglRuleDirectorie = bdglRuleDirectoryMapper.selectBdglRuleDirectoryById(bdglRuleDirectory.getParentId());
            bdglRuleDirectorie.setSubclassification(bdglRuleDirectorie.getSubclassification()-1L);
            bdglRuleDirectoryMapper.updateBdglRuleDirectory(bdglRuleDirectorie);
        }
        //删除文件夹
        //new File("D:\\aaaaaaaaaaaa\\"+bdglRuleDirectory.getParentDirectory()).delete();
        return bdglRuleDirectoryMapper.deleteBdglRuleDirectoryById(id);
    }
}
