package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Stuff;
import com.terrasystems.emedics.model.dto.StuffDto;

import java.util.List;

public interface StuffService {
    List<Stuff> getAllStuff();
    Stuff getById(String id);
    Stuff createStuff(StuffDto dto);
    void deleteStuff(String id);

}
