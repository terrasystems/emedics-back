package com.terrasystems.emedics.services;

import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.ReferenceCreateRequest;

public interface ReferenceCreateService {
    Patient createPatient(ReferenceCreateRequest request);
    Doctor createDoctor(ReferenceCreateRequest request);
    boolean inviteUser(String id);
}
