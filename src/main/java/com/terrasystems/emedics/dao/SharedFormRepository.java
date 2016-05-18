package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.SharedForm;
import org.springframework.data.repository.CrudRepository;

public interface SharedFormRepository extends CrudRepository<SharedForm, String> {
    SharedForm findByBlank_IdAndPatient_Id(String blankId, String patientId);
}
