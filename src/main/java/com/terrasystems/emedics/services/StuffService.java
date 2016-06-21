package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.Stuff;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.StuffDto;
import com.terrasystems.emedics.model.dto.TemplateEventDto;

import java.util.List;

public interface StuffService {
    List<Stuff> getAllStuff();
    Stuff getById(String id);
    Stuff createStuff(StuffDto dto);
    void deleteStuff(String id);
    Stuff updateStuff(StuffDto dto);
    List<TemplateEventDto> getStuffEvents(String stuffId);

}
