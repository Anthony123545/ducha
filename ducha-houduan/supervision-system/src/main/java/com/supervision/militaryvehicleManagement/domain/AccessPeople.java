package com.supervision.militaryvehicleManagement.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessPeople {
    /**
     * 审批人员id
     */
    private int id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 审批人员姓名
     */
    private String name;

    /**
     * 审批人员职别
     */
    private Integer level;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
