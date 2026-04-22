package com.supervision.web.noticeManage.mapper;


import com.supervision.web.noticeManage.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface NoticeMapper {
    int insertNotice(com.supervision.web.noticeManage.entity.Notice notice);

    List<Notice> selectAll();

    /**
     * 模糊按标题或按时间范围查询（全部参数可选）
     * 如果 title 为空则忽略标题条件；start/end 为空则忽略时间条件
     */
    List<Notice> selectByTitleAndTimeRange(@Param("title") String title,
                                           @Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);

    int deleteById(@Param("id") Integer id);
}
