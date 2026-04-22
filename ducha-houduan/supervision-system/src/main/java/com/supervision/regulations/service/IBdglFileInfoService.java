package com.supervision.regulations.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.regulations.domain.BdglFileInfo;

/**
 * 规章制度文件记录Service接口
 * 
 * @author supervision
 * @date 2022-03-15
 */
public interface IBdglFileInfoService 
{
    /**
     * 查询规章制度文件记录
     * 
     * @param id 规章制度文件记录主键
     * @return 规章制度文件记录
     */
    public BdglFileInfo selectBdglFileInfoById(Long id);

    /**
     * 查询规章制度文件记录列表
     * 
     * @param bdglFileInfo 规章制度文件记录
     * @return 规章制度文件记录集合
     */
    public List<BdglFileInfo> selectBdglFileInfoList(BdglFileInfo bdglFileInfo);

    public List<BdglFileInfo> selectParentIdfile(BdglFileInfo bdglFileInfo);

    /**
     * 新增规章制度文件记录
     * 
     * @param bdglFileInfo 规章制度文件记录
     * @return 结果
     */
    public int insertBdglFileInfo(LoginUser loginUse, BdglFileInfo bdglFileInfo) throws IOException;

    /**
     * 修改规章制度文件记录
     * 
     * @param bdglFileInfo 规章制度文件记录
     * @return 结果
     */
    public int updateBdglFileInfo(BdglFileInfo bdglFileInfo);

    /**
     * 批量删除规章制度文件记录
     * 
     * @param ids 需要删除的规章制度文件记录主键集合
     * @return 结果
     */
    public int deleteBdglFileInfoByIds(Long[] ids);

    /**
     * 删除规章制度文件记录信息
     * 
     * @param id 规章制度文件记录主键
     * @return 结果
     */
    public int deleteBdglFileInfoById(Long id);
    /**获取当前月阅读量  上月阅读了*/
    List<Map<String, Object>> selectResdNumber();
}
