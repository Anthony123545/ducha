package com.supervision.thought.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import com.supervision.thought.domain.ThoughtWeekjobFu;
import com.supervision.thought.service.IThoughtWeekjobFuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtWeekjobMapper;
import com.supervision.thought.domain.ThoughtWeekjob;
import com.supervision.thought.service.IThoughtWeekjobService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 周工作Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-06
 */
@Transactional
@Service
public class ThoughtWeekjobServiceImpl implements IThoughtWeekjobService 
{
    @Autowired
    private ThoughtWeekjobMapper thoughtWeekjobMapper;
    @Autowired
    private IThoughtWeekjobFuService weekjobFuService;

    /**
     * 查询周工作
     * 
     * @param id 周工作主键
     * @return 周工作
     */
    @Override
    public ThoughtWeekjob selectThoughtWeekjobById(Long id)
    {
        ThoughtWeekjob thoughtWeekjob = thoughtWeekjobMapper.selectThoughtWeekjobById(id);
        ThoughtWeekjobFu thoughtWeekjobFu = new ThoughtWeekjobFu();
        thoughtWeekjobFu.setWeekjobId(Integer.parseInt(id.toString()));
        List<ThoughtWeekjobFu> thoughtWeekjobFus = weekjobFuService.selectThoughtWeekjobFuList(thoughtWeekjobFu);
        thoughtWeekjob.setThoughtWeekjobFuList(thoughtWeekjobFus);
        return thoughtWeekjob;
    }

    /**
     * 查询周工作列表
     * 
     * @param thoughtWeekjob 周工作
     * @return 周工作
     */
    @Override
    public List<ThoughtWeekjob> selectThoughtWeekjobList(ThoughtWeekjob thoughtWeekjob)
    {
        return thoughtWeekjobMapper.selectThoughtWeekjobList(thoughtWeekjob);
    }

    /**
     * 新增周工作
     * 
     * @param thoughtWeekjob 周工作
     * @return 结果
     */
    @Override
    public int insertThoughtWeekjob(ThoughtWeekjob thoughtWeekjob)
    {
        int i = thoughtWeekjobMapper.insertThoughtWeekjob(thoughtWeekjob);
        List<ThoughtWeekjobFu> thoughtWeekjobFuList = thoughtWeekjob.getThoughtWeekjobFuList();
        if(thoughtWeekjobFuList.size()>0){
            for (ThoughtWeekjobFu thoughtWeekjobFu : thoughtWeekjobFuList) {
                if(thoughtWeekjobFu.getWeek()!=null){
                    thoughtWeekjobFu.setWeekjobId(Integer.parseInt(thoughtWeekjob.getId().toString()));
                    weekjobFuService.insertThoughtWeekjobFu(thoughtWeekjobFu);
                }
            }
        }
        return i;
    }

    /**
     * 修改周工作
     * 
     * @param thoughtWeekjob 周工作
     * @return 结果
     */
    @Override
    public int updateThoughtWeekjob(ThoughtWeekjob thoughtWeekjob)
    {
        thoughtWeekjob.setUpdateTime(DateUtils.getNowDate());
        int i = thoughtWeekjobMapper.updateThoughtWeekjob(thoughtWeekjob);
        List<ThoughtWeekjobFu> thoughtWeekjobFuList = thoughtWeekjob.getThoughtWeekjobFuList();
        if(thoughtWeekjobFuList.size()>0){
            for (ThoughtWeekjobFu thoughtWeekjobFu : thoughtWeekjobFuList) {
                if(thoughtWeekjobFu.getId()!=null){
                    weekjobFuService.updateThoughtWeekjobFu(thoughtWeekjobFu);
                }else{
                    thoughtWeekjobFu.setWeekjobId(Integer.parseInt(thoughtWeekjob.getId().toString()));
                    weekjobFuService.insertThoughtWeekjobFu(thoughtWeekjobFu);
                }
            }
        }
        return i;
    }

    /**
     * 批量删除周工作
     * 
     * @param ids 需要删除的周工作主键
     * @return 结果
     */
    @Override
    public int deleteThoughtWeekjobByIds(Long[] ids)
    {
        return thoughtWeekjobMapper.deleteThoughtWeekjobByIds(ids);
    }

    /**
     * 删除周工作信息
     * 
     * @param id 周工作主键
     * @return 结果
     */
    @Override
    public int deleteThoughtWeekjobById(Long id)
    {
        return thoughtWeekjobMapper.deleteThoughtWeekjobById(id);
    }
}
