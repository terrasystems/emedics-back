package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.Stuff;
import com.terrasystems.emedics.model.dto.*;

import java.util.List;

public interface StuffService {
    List<Stuff> getAllStuff();
    Stuff getById(String id);
    Stuff createStuff(StuffDto dto);
    void deleteStuff(String id);
    Stuff updateStuff(StuffDto dto);
    List<TemplateEventDto> getStuffEvents(String stuffId);
    List<ReferenceDto> getAllReferences();
    StateDto addReferences(String reference);
    List<ReferenceDto> findOrgReferencesByCriteria(String search);
    StateDto inactiveStuff(String id);
    Event assignTask(String stuffId, String eventId);
    ObjectResponse editTask(EventDto eventDto);
    StateDto closeTask(String id);
    List<PatientDto> getAllPatients();
}
