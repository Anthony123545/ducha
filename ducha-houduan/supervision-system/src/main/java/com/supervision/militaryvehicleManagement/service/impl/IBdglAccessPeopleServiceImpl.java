package com.supervision.militaryvehicleManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supervision.militaryvehicleManagement.domain.AccessPeople;
import com.supervision.militaryvehicleManagement.mapper.BdglAccessPeopleMapper;
import com.supervision.militaryvehicleManagement.service.IBdglAccessPeopleService;
import com.supervision.militaryvehicleManagement.service.IBdglDepartureInfoService;
import com.supervision.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class IBdglAccessPeopleServiceImpl implements IBdglAccessPeopleService {
    @Autowired
    private BdglAccessPeopleMapper bdglAccessPeopleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public void add(AccessPeople accessPeople) {
        // 1. 根据人员名称查询 sys_user 的 user_id
        String name = accessPeople.getName();
        Integer userId = sysUserMapper.selectUserIdByName(name);
        if (userId == null) {
            throw new RuntimeException("未找到名称为【" + name + "】的系统用户，请检查人员名称是否正确！");
        }
        // 2. 调用专门的校验接口
        if (bdglAccessPeopleMapper.checkExist(userId, accessPeople.getLevel(), null) > 0) {
            throw new RuntimeException("该人员该职别已存在，请勿重复添加！");
        }
        // 2. 设置 user_id 到 accessPeople
        accessPeople.setUserId(userId);
        // 3. 插入 access_people 表
        bdglAccessPeopleMapper.add(accessPeople);
    }

    @Override
    public PageInfo<AccessPeople> queryPage(String name, Integer level, Integer pageNum, Integer pageSize) {
        // 如果pageNum和pageSize任一为null则查询全部
        if (pageNum == null || pageSize == null) {
            AccessPeople accessPeople = new AccessPeople();
            accessPeople.setName(name);
            accessPeople.setLevel(level);
            // 执行查询
            List<AccessPeople> list = bdglAccessPeopleMapper.queryList(accessPeople);
            // 使用PageInfo的完整构造方式确保所有属性都被正确初始化
            return new PageInfo<>(list);
        }

        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        AccessPeople accessPeople = new AccessPeople();
        accessPeople.setName(name);
        accessPeople.setLevel(level);
        // 执行查询
        List<AccessPeople> list = bdglAccessPeopleMapper.queryList(accessPeople);

        // 封装结果
        return new PageInfo<>(list);
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        return bdglAccessPeopleMapper.deleteByIds(ids);
    }

    @Override
    public AccessPeople getById(int id) {
        return bdglAccessPeopleMapper.selectById(id);
    }

    @Override
    public boolean updateById(AccessPeople accessPeople) {
        // 1. 查询数据库中旧数据（判断 name 是否被修改）
        AccessPeople oldData = bdglAccessPeopleMapper.selectById(accessPeople.getId());
        if (oldData == null) {
            throw new RuntimeException("更新失败：未找到对应的人员记录（ID=" + accessPeople.getId() + "）");
        }

        // 2. 如果 name 改变了，则根据新 name 查 sys_user 表获取对应 userId
        if (!Objects.equals(oldData.getName(), accessPeople.getName())) {
            String newName = accessPeople.getName();
            Integer newUserId = sysUserMapper.selectUserIdByName(newName);

            if (newUserId == null) {
                throw new RuntimeException("未找到名称为【" + newName + "】的系统用户，请检查人员名称是否正确！");
            }

            accessPeople.setUserId(newUserId);
        }
        // 2. 调用专门的校验接口 (传入当前 id 进行排除)
        if (bdglAccessPeopleMapper.checkExist(accessPeople.getUserId(), accessPeople.getLevel(), accessPeople.getId()) > 0) {
            throw new RuntimeException("该人员已存在相同的职别配置！");
        }
        return bdglAccessPeopleMapper.updateById(accessPeople);
    }

    @Override
    public List<AccessPeople> findAll() {
        return bdglAccessPeopleMapper.listAll();
    }


}
