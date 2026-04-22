package com.supervision.zbqk.service.impl;

import java.util.List;

import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.DateUtils;
import com.supervision.zbqk.domain.BdglZbqkFileInfo;
import com.supervision.zbqk.mapper.BdglZbqkFileInfoMapper;
import com.supervision.zbqk.service.IBdglZbqkFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 战备情况Service业务层处理
 * 
 * @author supervision
 * @date 2022-11-16
 */
@Service
public class BdglZbqkFileInfoServiceImpl implements IBdglZbqkFileInfoService
{
    @Autowired(required = false)
    private BdglZbqkFileInfoMapper bdglZbqkFileInfoMapper;

    /**
     * 查询战备情况
     * 
     * @param id 战备情况主键
     * @return 战备情况
     */
    @Override
    public BdglZbqkFileInfo selectBdglZbqkFileInfoById(Long id)
    {
        return bdglZbqkFileInfoMapper.selectBdglZbqkFileInfoById(id);
    }

    /**
     * 查询战备情况列表
     * 
     * @param bdglZbqkFileInfo 战备情况
     * @return 战备情况
     */
    @Override
    public List<BdglZbqkFileInfo> selectBdglZbqkFileInfoList(BdglZbqkFileInfo bdglZbqkFileInfo)
    {
        return bdglZbqkFileInfoMapper.selectBdglZbqkFileInfoList(bdglZbqkFileInfo);
    }

    /**
     * 新增战备情况
     *
     * @return 结果
     */
    @Override
    public int insertBdglZbqkFileInfo(LoginUser loginUser, BdglZbqkFileInfo info)
    {

            info.setCreateUser(loginUser.getUsername());
            info.setCreateUserid(loginUser.getUserId());

            //判断当前问价夹是否存在阅读记录  存在阅读数=+1
//            Bdglzbqkfile bdglzbqkfile = new Bdglzbqkfile();
//            bdglzbqkfile.setDocumentCategory(info.getFileName());
//            List<Bdglzbqkfile> bdglzbqkfiles = resdMapper.selectBdglzbqkfileList(bdglzbqkfile);
            //判断当前文件是否存在阅读记录  不存在  添加
//            if (bdglzbqkfiles.size() == 0) {
//                resdMapper.insertBdglzbqkfile(bdglzbqkfile);
//            }else{
//                bdglzbqkfiles.get(0).setReadNumber(bdglzbqkfiles.get(0).getReadNumber()+1);
//                resdMapper.updateBdglzbqkfile(bdglzbqkfiles.get(0));
//            }
            //bdglFileInfo.aet
            info.setCreateTime(DateUtils.getNowDate());
            bdglZbqkFileInfoMapper.insertBdglZbqkFileInfo(info);

        return 1;
    }

    /**
     * 修改战备情况
     * 
     * @param bdglZbqkFileInfo 战备情况
     * @return 结果
     */
    @Override
    public int updateBdglZbqkFileInfo(BdglZbqkFileInfo bdglZbqkFileInfo)
    {
        return bdglZbqkFileInfoMapper.updateBdglZbqkFileInfo(bdglZbqkFileInfo);
    }

    /**
     * 批量删除战备情况
     * 
     * @param ids 需要删除的战备情况主键
     * @return 结果
     */
    @Override
    public int deleteBdglZbqkFileInfoByIds(Long[] ids)
    {
        return bdglZbqkFileInfoMapper.deleteBdglZbqkFileInfoByIds(ids);
    }

    /**
     * 删除战备情况信息
     * 
     * @param id 战备情况主键
     * @return 结果
     */
    @Override
    public int deleteBdglZbqkFileInfoById(Long id)
    {
        return bdglZbqkFileInfoMapper.deleteBdglZbqkFileInfoById(id);
    }
}
