package com.supervision.web.Hikvision.peopleDoor.web.mapper;

import com.supervision.web.Hikvision.peopleDoor.web.entity.PeopleFace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PeopleFaceMapper {
    PeopleFace selectById(Long id);

    List<PeopleFace> selectAll();

    int insert(PeopleFace peopleFace);

    int update(PeopleFace peopleFace);

    int delete(Long id);
}
