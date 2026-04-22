package com.supervision.web.Hikvision.peopleDoor.web.service.impl;

import com.supervision.web.Hikvision.peopleDoor.web.entity.PeopleFace;
import com.supervision.web.Hikvision.peopleDoor.web.mapper.PeopleFaceMapper;
import com.supervision.web.Hikvision.peopleDoor.web.service.PeopleFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// PeopleFaceServiceImpl.java
@Service
public class PeopleFaceServiceImpl implements PeopleFaceService {
    @Autowired
    private PeopleFaceMapper peopleFaceMapper;
//    @Value("${faceImgPath}")  // 你存图片的路径，例如 D:/faceImg/
//    private String faceImgPath = "E:/sourceCode/faceImg/";




    @Override
    public PeopleFace getById(Long id) {
        return peopleFaceMapper.selectById(id);
    }

    @Override
    public List<PeopleFace> getAll() {
        return peopleFaceMapper.selectAll();
    }

    @Override
    public void add(PeopleFace peopleFace) {
        peopleFaceMapper.insert(peopleFace);
    }

    @Override
    public void update(PeopleFace peopleFace) {
        peopleFaceMapper.update(peopleFace);
    }

    @Override
    public void delete(Long id) {
        peopleFaceMapper.delete(id);
    }
}

