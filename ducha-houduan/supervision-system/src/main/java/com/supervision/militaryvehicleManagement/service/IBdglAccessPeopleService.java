package com.supervision.militaryvehicleManagement.service;

import com.github.pagehelper.PageInfo;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.militaryvehicleManagement.domain.AccessPeople;

import java.util.List;

public interface IBdglAccessPeopleService {
    void add(AccessPeople accessPeople);

    PageInfo<AccessPeople> queryPage(String name, Integer level,  Integer pageNum, Integer pageSize);

    int deleteByIds(List<Integer> ids);

    AccessPeople getById(int id);

    boolean updateById(AccessPeople accessPeople);

    List<AccessPeople> findAll();
}
