package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.DoctorRepository;
import com.terrasystems.emedics.dao.StuffRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.ReferenceDto;
import com.terrasystems.emedics.model.dto.RegisterDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.UserDto;
import com.terrasystems.emedics.model.mapping.ReferenceConverter;
import com.terrasystems.emedics.security.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service(value = "patientReferenceService")
public class PatientReferenceServiceImpl implements CurrentUserService, ReferenceService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    StuffRepository stuffRepository;
    @Autowired
    RegistrationService registrationService;



    @Override
    public List<ReferenceDto> findAllReferencesByCriteria(String search) {
        Patient currentUser = (Patient) userRepository.findByEmail(getPrincipals());
        Set<User> currentRefs = currentUser.getUserRef();
        ReferenceConverter converter = new ReferenceConverter();
        List<ReferenceDto> result = new ArrayList<>();
        List<Doctor> doctorRefs =  doctorRepository.findByNameContainingOrTypeContainingOrEmailContaining(search,search,search).stream()
                .filter(doctor -> !currentRefs.contains(doctor))
                .collect(Collectors.toList());
        List<Stuff> stuffRef =  stuffRepository.findByNameContainingAndAdminIsTrueOrEmailContainingAndAdminIsTrue(search,search).stream()
                .filter((stuff -> !currentRefs.contains(stuff)))
                .collect(Collectors.toList());
        result.addAll(converter.convertFromDoctors(doctorRefs));
        result.addAll(converter.convertFromStuff(stuffRef));

        return result;
    }


    @Override
    public StateDto addReferences(Set<String> references) {
        User current =  userRepository.findByEmail(getPrincipals());
        Set<User> refs = (Set<User>) userRepository.findAll(references);
        Set<User> currentRefs = current.getUserRef();
        currentRefs.addAll(refs);
        current.setUserRef(currentRefs);
        userRepository.save(current);
        return new StateDto(true, "Saved to Your refs");
    }

    @Override
    public Iterable<ReferenceDto> getAllReferences() {
        ReferenceConverter converter = new ReferenceConverter();
        User current = (Patient) userRepository.findByEmail(getPrincipals());
        Set<User> userRefs = current.getUserRef();
        List<String> doctors = userRefs.stream()
                .map(user -> {
                    return user.getId();
                }).collect(Collectors.toList());
        List<Doctor> doctorList = (List<Doctor>) doctorRepository.findAll(doctors);

        return converter.convertFromDoctors(doctorList);
    }

    @Override
    public StateDto removeReferences(Set<String> refs) throws Exception{
        User current = (Patient) userRepository.findByEmail(getPrincipals());
        StateDto state = new StateDto();
        Set<User> removed =  current.getUserRef().stream()
                .filter(user -> !refs.contains(user.getId()))
                .collect(Collectors.toSet());
        current.setUserRef(removed);
        userRepository.save(current);
        state.setValue(true);
        state.setMessage("References removed");
        return state;
    }

    @Override
    public StateDto createReference(String email) {
        User currrent = userRepository.findByEmail(getPrincipals());
        RegisterDto registerDto = new RegisterDto();
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setUsername(email);
        registerDto.setUser(userDto);
        StateDto status = registrationService.registerUser(registerDto,"doc");
        if (status.isValue()){
            User doctor =  userRepository.findByEmail(email);
            Set<User> doctors = new HashSet<>();
            doctors.add(doctor);
            currrent.getUserRef().addAll(doctors);
            userRepository.save(currrent);
        }
        return status;
    }
}
