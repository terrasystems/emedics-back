package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.DoctorRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.Reference;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.ReferenceDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.mapping.ReferenceConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service(value = "patientReferenceService")
public class PatientReferenceServiceImpl implements CurrentUserService, ReferenceService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;



    @Override
    public List<ReferenceDto> findAllReferencesByCriteria(String search) {
        ReferenceConverter converter = new ReferenceConverter();
        List<Doctor> refs =  doctorRepository.findByNameContainingOrTypeContainingOrEmailContaining(search,search,search);
        refs.forEach(ref -> System.out.println(ref.toString()));

        return converter.convertFromDoctors(refs);
    }

    @Override
    public StateDto addReferences(Iterable<String> references) {
        Patient current = (Patient) userRepository.findByEmail(getPrincipals());
        List<User> refs = (List<User>) userRepository.findAll(references);
        current.setUsers(refs);
        userRepository.save(current);
        return new StateDto(true, "Saved to Your refs");
    }

    @Override
    public Iterable<ReferenceDto> getAllReferences() {
        ReferenceConverter converter = new ReferenceConverter();
        Patient current = (Patient) userRepository.findByEmail(getPrincipals());
        List<User> userRefs = current.getUsers();
        List<String> doctors = userRefs.stream()
                .map(user -> {
                    return user.getId();
                }).collect(Collectors.toList());
        List<Doctor> doctorList = (List<Doctor>) doctorRepository.findAll(doctors);

        return converter.convertFromDoctors(doctorList);
    }

    @Override
    public void removeReferences(Set<String> refs) throws Exception{
        Patient current = (Patient) userRepository.findByEmail(getPrincipals());
        List<User> removed =  current.getUsers().stream()
                .filter(user -> !refs.contains(user.getId()))
                .collect(Collectors.toList());
        current.setUsers(removed);
        userRepository.save(current);
    }
}
