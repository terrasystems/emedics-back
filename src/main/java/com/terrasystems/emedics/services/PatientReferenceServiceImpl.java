package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.DoctorRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.dto.ReferenceDto;
import com.terrasystems.emedics.model.mapping.ReferenceConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service(value = "patientReferenceService")
public class PatientReferenceServiceImpl implements CurrentUserService, ReferenceService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;


    public PatientReferenceServiceImpl(){}

   /* public List<ReferenceDto> finAllRefsByCriteria(String search) {
        ReferenceConverter converter = new ReferenceConverter();
        List<Doctor> refs =  doctorRepository.findByNameContainingOrTypeContainingOrEmailContaining(search,search,search);
        refs.forEach(ref -> System.out.println(ref.toString()));

        return converter.convertFromDoctors(refs);
    }*/


    @Override
    public List<ReferenceDto> findAllReferencesByCriteria(String search) {
        ReferenceConverter converter = new ReferenceConverter();
        List<Doctor> refs =  doctorRepository.findByNameContainingOrTypeContainingOrEmailContaining(search,search,search);
        refs.forEach(ref -> System.out.println(ref.toString()));

        return converter.convertFromDoctors(refs);
    }
}
