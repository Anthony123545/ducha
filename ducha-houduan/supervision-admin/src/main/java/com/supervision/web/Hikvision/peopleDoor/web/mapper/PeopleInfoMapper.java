package com.supervision.web.Hikvision.peopleDoor.web.mapper;

import com.supervision.web.Hikvision.peopleDoor.web.entity.PeopleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PeopleInfoMapper {
    PeopleInfo selectById(Long id);

    List<PeopleInfo> selectAll();

    int selectTotal();

    int insert(PeopleInfo peopleInfo);

    int update(PeopleInfo peopleInfo);

    int delete(Long id);

    List<PeopleInfo> selectByIds(@Param("personIds") List<String> personIds);

//    void updatePersonId(@Param("id") Long id, @Param("personId") String personId);
}
