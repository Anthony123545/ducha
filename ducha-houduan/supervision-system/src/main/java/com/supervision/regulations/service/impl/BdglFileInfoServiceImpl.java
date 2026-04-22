package com.supervision.regulations.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.DateUtils;
import com.supervision.regulations.domain.BdglFileResd;
import com.supervision.regulations.domain.BdglRuleDirectory;
import com.supervision.regulations.mapper.BdglFileResdMapper;
import com.supervision.regulations.mapper.BdglRuleDirectoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.supervision.regulations.mapper.BdglFileInfoMapper;
import com.supervision.regulations.domain.BdglFileInfo;
import com.supervision.regulations.service.IBdglFileInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 规章制度文件记录Service业务层处理
 *
 * @author supervision
 * @date 2022-03-15
 */
@Transactional
@Service
public class BdglFileInfoServiceImpl implements IBdglFileInfoService {
    @Autowired
    private BdglFileInfoMapper bdglFileInfoMapper;
    @Autowired
    private BdglRuleDirectoryMapper directoryMapper;
    @Autowired
    private BdglFileResdMapper resdMapper;
    @Value("${supervision.profile}")
    private String profile;

    /**
     * 查询规章制度文件记录
     *
     * @param id 规章制度文件记录主键
     * @return 规章制度文件记录
     */
    @Override
    public BdglFileInfo selectBdglFileInfoById(Long id) {
        return bdglFileInfoMapper.selectBdglFileInfoById(id);
    }

    /**
     * 查询规章制度文件记录列表
     *
     * @param bdglFileInfo 规章制度文件记录
     * @return 规章制度文件记录
     */
    @Override
    public List<BdglFileInfo> selectBdglFileInfoList(BdglFileInfo bdglFileInfo) {
        return bdglFileInfoMapper.selectBdglFileInfoList(bdglFileInfo);
    }

    public List<BdglFileInfo> selectParentIdfile(BdglFileInfo bdglFileInfo) {
        return bdglFileInfoMapper.selectParentIdfile(bdglFileInfo);
    }

    /**
     * 新增规章制度文件记录
     *
     * @param bdglFileInfoo 规章制度文件记录
     * @return 结果
     */
    @Override
    public int insertBdglFileInfo( LoginUser loginUser,BdglFileInfo bdglFileInfoo) throws IOException {
        List<BdglFileInfo> bdglFileInfos = bdglFileInfoo.getBdglFileInfos();
        for (BdglFileInfo bdglFileInfo : bdglFileInfos) {
            bdglFileInfo.setDirectoryId(bdglFileInfoo.getDirectoryId());
            bdglFileInfo.setDirectoryName(bdglFileInfoo.getDirectoryName());
            bdglFileInfo.setCreateUser(loginUser.getUsername());
            bdglFileInfo.setCreateUserid(loginUser.getUserId());
            /*-----------------------------------修改文件数----------------------------------------*/
            BdglRuleDirectory bdglRuleDirectory = directoryMapper.selectBdglRuleDirectoryById(bdglFileInfo.getDirectoryId());
            bdglRuleDirectory.setDocumentsNumber(bdglRuleDirectory.getDocumentsNumber() + 1);
            directoryMapper.updateBdglRuleDirectory(bdglRuleDirectory);
            //修改父文件夹问件数
            BdglRuleDirectory bdglRuleDirectory1 = directoryMapper.selectBdglRuleDirectoryById(bdglRuleDirectory.getParentId());
            bdglRuleDirectory1.setDocumentsNumber(bdglRuleDirectory1.getDocumentsNumber() + 1);
            directoryMapper.updateBdglRuleDirectory(bdglRuleDirectory1);
            /*-----------------------------------修改文件数----------------------------------------*/
            //判断当前问价夹是否存在阅读记录  存在阅读数=+1
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            String format = simpleDateFormat.format(date);
//        String[] split = format.split("-");
//        String s = split[split.length - 1];
            BdglFileResd bdglFileResd = new BdglFileResd();
            bdglFileResd.setMonth(format);
            bdglFileResd.setDirectoryId(bdglFileInfo.getDirectoryId());
            List<BdglFileResd> bdglFileResds = resdMapper.selectBdglFileResdList(bdglFileResd);
            //判断当前文件是否存在阅读记录  不存在  添加
            if (bdglFileResds.size() == 0) {
//                fileResd.setReadNumber(fileResd.getReadNumber()+1);
//                resdMapper.updateBdglFileResd(fileResd);
                bdglFileResd.setDocumentCategory(bdglFileInfo.getDirectoryName());
                resdMapper.insertBdglFileResd(bdglFileResd);
            }
            //bdglFileInfo.aet
            bdglFileInfo.setCreateTime(DateUtils.getNowDate());
            bdglFileInfoMapper.insertBdglFileInfo(bdglFileInfo);
        }
        return 1;
    }

    /**
     * 修改规章制度文件记录
     *
     * @param bdglFileInfo 规章制度文件记录
     * @return 结果
     */
    @Override
    public int updateBdglFileInfo(BdglFileInfo bdglFileInfo) {
        return bdglFileInfoMapper.updateBdglFileInfo(bdglFileInfo);
    }

    /**
     * 批量删除规章制度文件记录
     *
     * @param ids 需要删除的规章制度文件记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglFileInfoByIds(Long[] ids) {
        for (Long id : ids) {
            //修改子文件夹个数
            BdglFileInfo bdglFileInfo = bdglFileInfoMapper.selectBdglFileInfoById(id);
            BdglRuleDirectory bdglRuleDirectory = directoryMapper.selectBdglRuleDirectoryById(bdglFileInfo.getDirectoryId());
            bdglRuleDirectory.setDocumentsNumber(bdglRuleDirectory.getDocumentsNumber() - 1L);
            directoryMapper.updateBdglRuleDirectory(bdglRuleDirectory);
            //修改父文件夹问件数
            BdglRuleDirectory bdglRuleDirectory1 = directoryMapper.selectBdglRuleDirectoryById(bdglRuleDirectory.getParentId());
            bdglRuleDirectory1.setDocumentsNumber(bdglRuleDirectory1.getDocumentsNumber() - 1);
            directoryMapper.updateBdglRuleDirectory(bdglRuleDirectory1);
            //删除本地文件
            String parentDirName = bdglFileInfo.getParentDirName();
            String replace = parentDirName.replace("/profile", "");
            File file = new File(profile + replace.trim());
            file.delete();
            BdglFileResd bdglFileResd = new BdglFileResd();
            bdglFileResd.setDirectoryId(bdglFileInfo.getDirectoryId());
            //删除文件后减去阅读数量
            List<BdglFileResd> bdglFileResds = resdMapper.selectBdglFileResdList(bdglFileResd);
            if (bdglFileResds.size() != 0) {
                for (BdglFileResd fileResd : bdglFileResds) {
                    fileResd.setReadNumber(fileResd.getReadNumber() - bdglFileInfo.getReadVolume());
                    resdMapper.updateBdglFileResd(fileResd);
                }
            }
        }

        return bdglFileInfoMapper.deleteBdglFileInfoByIds(ids);
    }

    /**
     * 删除规章制度文件记录信息
     *
     * @param id 规章制度文件记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglFileInfoById(Long id) {
        return bdglFileInfoMapper.deleteBdglFileInfoById(id);
    }


    /**
     * 获取当前月阅读量  上月阅读了
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> selectResdNumber() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String format = simpleDateFormat.format(date);
        String[] split = format.split("-");
        String s = split[split.length - 1];
        List<Map<String, Object>> readNumbers = bdglFileInfoMapper.selectResdNumber(format);
        for (Map<String, Object> readNumber : readNumbers) {
            BdglFileResd fileResd = new BdglFileResd();
            int i = Integer.parseInt(s.substring(s.length() - 1));
            if (i == 1) {

                fileResd.setMonth((Integer.parseInt(split[0])-1)+"-"+"12");
            } else {
                String month = i - 1 + "";
                fileResd.setMonth(Integer.parseInt(split[0])+"-"+"0"+month.trim());
            }
            Object id = readNumber.get("directory_id");
            String s1 = id.toString();
            fileResd.setDirectoryId(Long.valueOf(s1));
            //获取当前文件夹上月阅读量
            BdglFileResd bdglFileResd = resdMapper.selectResdNumber(fileResd);
            if (bdglFileResd != null) {
                readNumber.put("readnumber2", bdglFileResd.getReadNumber());
                System.out.println();
            } else {
                readNumber.put("readnumber2", 0);

            }
        }
        return readNumbers;
    }

}
