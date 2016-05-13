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
        Patient currentUser = (Patient) userRepository.findByEmail(getPrincipals());
        Set<User> currentRefs = currentUser.getUsers();
        ReferenceConverter converter = new ReferenceConverter();
        List<Doctor> refs =  doctorRepository.findByNameContainingOrTypeContainingOrEmailContaining(search,search,search).stream()
                .filter(doctor -> !currentRefs.contains(doctor))
                .collect(Collectors.toList());


        return converter.convertFromDoctors(refs);
    }

    @Override
    public StateDto addReferences(Set<String> references) {
        Patient current = (Patient) userRepository.findByEmail(getPrincipals());
        Set<User> refs = (Set<User>) userRepository.findAll(references);
        Set<User> currentRefs = current.getUsers();
        currentRefs.addAll(refs);
        current.setUsers(currentRefs);
        userRepository.save(current);
        return new StateDto(true, "Saved to Your refs");
    }

    @Override
    public Iterable<ReferenceDto> getAllReferences() {
        ReferenceConverter converter = new ReferenceConverter();
        Patient current = (Patient) userRepository.findByEmail(getPrincipals());
        Set<User> userRefs = current.getUsers();
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
        Set<User> removed =  current.getUsers().stream()
                .filter(user -> !refs.contains(user.getId()))
                .collect(Collectors.toSet());
        current.setUsers(removed);
        userRepository.save(current);
    }
}
