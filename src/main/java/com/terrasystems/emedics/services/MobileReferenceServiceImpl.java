package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.DoctorRepository;
import com.terrasystems.emedics.dao.PatientRepository;
import com.terrasystems.emedics.dao.StuffRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.Stuff;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.ReferenceDto;
import com.terrasystems.emedics.model.mapping.ReferenceConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MobileReferenceServiceImpl implements MobileReferenceService, CurrentUserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    StuffRepository stuffRepository;
    @Autowired
    PatientRepository patientRepository;

    @Override
    public List<ReferenceDto> findReferencesForMobile(String search, int start, int count) {
        User current = userRepository.findByEmail(getPrincipals());
        if(current.getDiscriminatorValue().equals("patient")) {
            return findPatientReferencesForMobileByCriteria(search, start, count);
        } else if(current.getDiscriminatorValue().equals("doctor")) {
            return findDoctorReferencesForMobileByCriteria(search, start, count);
        } else {
            return findStuffReferencesForMobileByCriteria(search, start, count);
        }
    }

    public List<ReferenceDto> findPatientReferencesForMobileByCriteria(String search, int start, int count) {
        Pageable pageable = new PageRequest(start, count);
        ReferenceConverter converter = new ReferenceConverter();
        Patient current = (Patient) userRepository.findByEmail(getPrincipals());
        Set<User> currentRefs = current.getUserRef();
        List<ReferenceDto> refs = new ArrayList<>();
        Iterable<Doctor> doctorsRefs = doctorRepository.findByIdIsNotAndNameContainingIgnoreCaseOrType_NameContainingIgnoreCaseOrEmailContainingIgnoreCase(current.getId(), search, search, search, pageable).stream()
                .filter(doctor -> !currentRefs.contains(doctor))
                .collect(Collectors.toList());
        Iterable<Stuff> stuffRef = stuffRepository.findByIdIsNotAndDoctor_NameContainingIgnoreCaseAndDoctor_AdminIsTrueOrDoctor_Type_NameContainingIgnoreCaseAndDoctor_AdminIsTrue(current.getId(),search, search, pageable).stream()
                .filter((stuff -> !currentRefs.contains(stuff)))
                .collect(Collectors.toList());
        refs.addAll((Collection<? extends ReferenceDto>) converter.convertFromDoctorsIterator(doctorsRefs));
        refs.addAll((Collection<? extends ReferenceDto>) converter.convertFromStuffsIterator(stuffRef));
        return refs;
    }

    public List<ReferenceDto> findDoctorReferencesForMobileByCriteria(String search, int start, int count) {
        Pageable pageable = new PageRequest(start, count);
        ReferenceConverter converter = new ReferenceConverter();
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        Set<User> currentRefs = current.getUserRef();
        List<ReferenceDto> refs = new ArrayList<>();
        Iterable<Stuff> stuffRef = stuffRepository.findByIdIsNotAndDoctor_NameContainingIgnoreCaseAndDoctor_AdminIsTrueOrDoctor_Type_NameContainingIgnoreCaseAndDoctor_AdminIsTrue(current.getId(),search, search, pageable).stream()
                .filter((stuff -> !currentRefs.contains(stuff)))
                .collect(Collectors.toList());
        Iterable<Doctor> doctorsRefs = doctorRepository.findByIdIsNotAndNameContainingIgnoreCaseOrIdIsNotAndEmailContainingIgnoreCaseOrIdIsNotAndType_NameContainingIgnoreCase(current.getId(), search, current.getId(), search, current.getId(), search, pageable);
        Iterable<Patient> patientsRefs = patientRepository.findByIdIsNotAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(current.getId(),search,search, pageable).stream()
                .filter(patient -> !currentRefs.contains(patient))
                .collect(Collectors.toList());
        refs.addAll((Collection<? extends ReferenceDto>) converter.convertFromStuffsIterator(stuffRef));
        refs.addAll((Collection<? extends ReferenceDto>) converter.convertFromDoctorsIterator(doctorsRefs));
        refs.addAll((Collection<? extends ReferenceDto>) converter.convertFromPatientsIterator(patientsRefs));
        return refs;
    }

    public List<ReferenceDto> findStuffReferencesForMobileByCriteria(String search, int start, int count) {
        Pageable pageable = new PageRequest(start, count);
        ReferenceConverter converter = new ReferenceConverter();
        Stuff stuff = (Stuff) userRepository.findByEmail(getPrincipals());
        Doctor doctor = stuff.getDoctor();
        String docId = doctor.getId();
        Set<User> currentRefs = doctor.getUserRef();
        List<ReferenceDto> refs = new ArrayList<>();
        Iterable<Doctor> doctorsRefs = doctorRepository.findByIdIsNotAndNameContainingIgnoreCaseOrIdIsNotAndEmailContainingIgnoreCaseOrIdIsNotAndType_NameContainingIgnoreCase(docId, search, docId, search, docId, search, pageable);
        Iterable<Patient> patientsRefs = patientRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search,search, pageable).stream()
                .filter(patient -> !currentRefs.contains(patient))
                .collect(Collectors.toList());
        refs.addAll((Collection<? extends ReferenceDto>) converter.convertFromDoctorsIterator(doctorsRefs));
        refs.addAll((Collection<? extends ReferenceDto>) converter.convertFromPatientsIterator(patientsRefs));
        return refs;
    }
}
