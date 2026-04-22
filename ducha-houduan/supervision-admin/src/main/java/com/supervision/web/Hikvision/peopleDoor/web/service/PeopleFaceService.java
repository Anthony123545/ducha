package com.supervision.web.Hikvision.peopleDoor.web.service;

import com.supervision.web.Hikvision.peopleDoor.web.entity.PeopleFace;

import java.util.List;

public interface PeopleFaceService {
    PeopleFace getById(Long id);

    List<PeopleFace> getAll();

    void add(PeopleFace peopleFace);

    void update(PeopleFace peopleFace);

    void delete(Long id);
}

