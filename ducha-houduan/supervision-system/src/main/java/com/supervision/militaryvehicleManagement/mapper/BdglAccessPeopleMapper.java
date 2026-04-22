package com.supervision.militaryvehicleManagement.mapper;

import com.supervision.militaryvehicleManagement.domain.AccessPeople;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BdglAccessPeopleMapper {
    void add(AccessPeople accessPeople);

    List<AccessPeople> queryList(AccessPeople accessPeople);

    int deleteByIds(@Param("ids")List<Integer> ids);

    AccessPeople selectById(@Param("id")int id);

    boolean updateById(AccessPeople accessPeople);

    @Select("SELECT * FROM access_people")
    List<AccessPeople> listAll();

    @Select("SELECT name FROM access_people WHERE id = #{id}")
    String selectPersonNameById(@Param("id") Integer id);
    /**
     * 校验是否存在相同的人员和职别配置
     * @param userId 用户ID
     * @param level 职别
     * @param id 排除的记录ID（用于修改时的校验）
     * @return 存在的数量
     */
    int checkExist(@Param("userId") Integer userId, @Param("level") Integer level, @Param("id") Integer id);
}
