package com.supervision.grassrootsregistration.domain;

import lombok.Data;

@Data
public class DeviceRegister {
    private Long id;

    private String name;
    private String dept;

    private String phone1;
    private String phone1Time;

    private String phone2;
    private String phone2Time;

    private String smartPhone1;
    private String smartPhone2;
    private String smartPhone3;
    private String nonSmartPhone;

    private String otherDevice;

    private String wechat;
    private String qq;
    private String weibo;
    private String douyin;
    private String otherSocial;

    private String userName;
    private String useDate;

}
