package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.DoctorRepository;
import com.terrasystems.emedics.dao.PatientRepository;
import com.terrasystems.emedics.dao.StuffRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.model.mapping.ReferenceConverter;
import com.terrasystems.emedics.security.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ReferenceCreateService referenceCreateService;
    @Autowired
    StuffService stuffService;



    @Override
    public List<ReferenceDto> findMyReferencesByCriteria(String search) {
        User currentUser = userRepository.findByEmail(getPrincipals());
        if (currentUser.getDiscriminatorValue().equals("patient")) {
            return findPatientReferencesByCriteria(search);
        } else if (currentUser.getDiscriminatorValue().equals("doctor")) {
            return findDoctorsReferencesByCriteria(search);
        } else return stuffService.findOrgReferencesByCriteria(search);
    }

    public List<ReferenceDto> findMyReferencesByCriteria(ReferenceCriteria criteria) {
        User current = userRepository.findByEmail(getPrincipals());


        return null;
    }

    @Override
    public List<ReferenceDto> findAllReferencesByCriteria(String search, String type) {
        ReferenceConverter converter = new ReferenceConverter();
        User current = userRepository.findByEmail(getPrincipals());
        List<ReferenceDto> refs = new ArrayList<>();
        if (current.getDiscriminatorValue().equals("patient")) {
            refs.addAll(converter.convertFromDoctors(doctorRepository.findByIdIsNotAndNameContainingIgnoreCaseOrType_NameContainingIgnoreCaseOrEmailContainingIgnoreCase(current.getId(),search,search,search)));
            refs.addAll(converter.convertFromStuff(stuffRepository.findByIdIsNotAndDoctor_NameContainingIgnoreCaseAndDoctor_AdminIsTrueOrDoctor_Type_NameContainingIgnoreCaseAndDoctor_AdminIsTrue(current.getId(), search,search)));

            return refs;
        } else {
            if (type.equals("pat")) {
                refs.addAll(converter.convertFromPatients(patientRepository.findByIdIsNotAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(current.getId(),search.toLowerCase(),search.toLowerCase())));
                return refs;
            }
            refs.addAll(converter.convertFromDoctors(doctorRepository.findByIdIsNot("%"+search.toLowerCase()+"%",current.getId())));
            refs.addAll(converter.convertFromPatients(patientRepository.findByIdIsNotAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(current.getId(),search.toLowerCase(),search.toLowerCase())));
            return refs;
        }
    }


    @Override
    @Transactional
    public StateDto addReferences(String reference) {
        User current = userRepository.findByEmail(getPrincipals());
        if (current.getDiscriminatorValue().equals("patient")) {
            Doctor refToAdd = (Doctor) userRepository.findOne(reference);
            current.getUserRef().add(refToAdd);
            refToAdd.getPatients().add((Patient) current);
            refToAdd.getUserRef().add(current);
            userRepository.save(current);
            userRepository.save(refToAdd);
            return new StateDto(true, MessageEnums.MSG_SAVE_REFS.toString());
        } else if (current.getDiscriminatorValue().equals("doctor")) {
            User refToAdd = userRepository.findOne(reference);
            if (refToAdd.getDiscriminatorValue().equals("patient")) {
                current.getUserRef().add(refToAdd);
                refToAdd.getUserRef().add(current);
                ((Doctor) current).getPatients().add((Patient) refToAdd);
                ((Patient) refToAdd).getDoctors().add((Doctor) current);
                userRepository.save(current);
                userRepository.save(refToAdd);
                return new StateDto(true, MessageEnums.MSG_SAVE_REFS.toString());
            } else {
                current.getUserRef().add(refToAdd);
                refToAdd.getUserRef().add(current);
                userRepository.save(current);
                userRepository.save(refToAdd);
                return new StateDto(true, MessageEnums.MSG_SAVE_REFS.toString());
            }

        } else {
            return stuffService.addReferences(reference);
        }

    }

    @Override
    public Iterable<ReferenceDto> getAllReferences() {
        ReferenceConverter converter = new ReferenceConverter();
        User current = userRepository.findByEmail(getPrincipals());
        if(current.getDiscriminatorValue().equals("stuff")) {
            return stuffService.getAllReferences();
        } else {
            return converter.convertFromUsers(current.getUserRef());
        }
        /*Set<User> userRefs = current.getUserRef();
        List<String> refs = userRefs.stream()
                .map(user -> {
                    return user.getId();
                }).collect(Collectors.toList());
        List<User> refsList = (List<User>) userRepository.findAll(refs);*/

    }

    @Override
    @Transactional
    public StateDto removeReferences(String reference) throws Exception {
        User current = userRepository.findByEmail(getPrincipals());
        User refToRemove = userRepository.findOne(reference);
        if(current.getDiscriminatorValue().equals("patient")){
            current.getUserRef().remove(refToRemove);
            refToRemove.getUserRef().remove(current);
            ((Doctor) refToRemove).getPatients().remove(current);

            return new StateDto(true, MessageEnums.MSG_SAVE_REFS.toString());
        } else if(current.getDiscriminatorValue().equals("doctor")){
            if (refToRemove.getDiscriminatorValue().equals("patient")) {
                ((Doctor) current).getPatients().remove(refToRemove);
                current.getUserRef().remove(refToRemove);
                refToRemove.getUserRef().remove(current);
                ((Patient) refToRemove).getDoctors().remove(current);
                return new StateDto(true, MessageEnums.MSG_SAVE_REFS.toString());

            } else {
                current.getUserRef().remove(refToRemove);
                refToRemove.getUserRef().remove(current);
                return new StateDto(true, MessageEnums.MSG_SAVE_REFS.toString());
            }
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public String createReference(ReferenceCreateRequest request) {
        User current = userRepository.findByEmail(getPrincipals());
        if (userRepository.existsByEmail(request.getEmail())) {
            return null;
        }
        if (current.getDiscriminatorValue().equals("patient")) {
            Doctor doctor = referenceCreateService.createDoctor(request);
            if (doctor != null) {
                current.getUserRef().add(doctor);
                doctor.getPatients().add((Patient) current);
                doctor.getUserRef().add(current);
                return doctor.getId();
            } else return null;
        } else if (current.getDiscriminatorValue().equals("doctor")) {
            if (request.getType().equals("pat")) {
                Patient patient = referenceCreateService.createPatient(request);
                if (patient != null) {
                    Doctor currentDoctor = (Doctor) userRepository.findByEmail(getPrincipals());
                    currentDoctor.getPatients().add(patient);
                    currentDoctor.getUserRef().add(patient);
                    patient.getUserRef().add(currentDoctor);
                    patient.getDoctors().add(currentDoctor);
                    userRepository.save(currentDoctor);
                    userRepository.save(patient);
                    return patient.getId();
                } else return null;
            } else {
                Doctor doctor = referenceCreateService.createDoctor(request);
                if (doctor != null) {
                    Doctor currentDoctor = (Doctor) userRepository.findByEmail(getPrincipals());
                    currentDoctor.getUserRef().add(doctor);
                    doctor.getUserRef().add(currentDoctor);
                    userRepository.save(doctor);
                    userRepository.save(currentDoctor);
                    return currentDoctor.getId();
                } else return null;
            }
        } else return null;
    }

    @Override
    public List<ReferenceDto> findMyRefs(String search, String type) {
        ReferenceConverter converter = new ReferenceConverter();
        User current = userRepository.findByEmail(getPrincipals());
        if (current.getDiscriminatorValue().equals("doctor")) {
            Doctor doctor = (Doctor) current;
            List<ReferenceDto> myRefs = new ArrayList<>();
            if (!type.equals("pat")) {
                myRefs.addAll(converter.convertFromPatients(doctor.getPatients()));
            } else {
                myRefs.addAll(converter.convertFromPatients(doctor.getPatients()));
                myRefs.addAll((Collection<? extends ReferenceDto>) getAllReferences());
            }
            return myRefs.stream().filter(referenceDto -> {
                return referenceDto.getName().contains(search);
            }).collect(Collectors.toList());
        } else if (current.getDiscriminatorValue().equals("patient")){
            Patient patient = (Patient) current;
            List<ReferenceDto> myRefs = new ArrayList<>();
            myRefs.addAll((Collection<? extends ReferenceDto>) getAllReferences());
            return myRefs;
        } else {
            return stuffService.findOrgReferencesByCriteria(search);
        }
    }

    private List<ReferenceDto> findDoctorRefs(String search, String type) {
        ReferenceConverter converter = new ReferenceConverter();
        Doctor doctor = (Doctor) userRepository.findByEmail(getPrincipals());
        List<ReferenceDto> myRefs = new ArrayList<>();
        if (!type.equals("pat")) {
            myRefs.addAll(converter.convertFromPatients(doctor.getPatients()));
        } else {
            myRefs.addAll(converter.convertFromPatients(doctor.getPatients()));
            myRefs.addAll((Collection<? extends ReferenceDto>) getAllReferences());
        }
        return myRefs.stream().filter(referenceDto -> {
            return referenceDto.getName().contains(search);
        }).collect(Collectors.toList());
    }

    private List<ReferenceDto> findDoctorsReferencesByCriteria(String search) {
        ReferenceConverter converter = new ReferenceConverter();
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        Set<User> currentRefs = current.getUserRef();
        List<ReferenceDto> refs = new ArrayList<>();
        List<Stuff> stuffRef = stuffRepository.findByIdIsNotAndDoctor_NameContainingIgnoreCaseAndDoctor_AdminIsTrueOrDoctor_Type_NameContainingIgnoreCaseAndDoctor_AdminIsTrue(current.getId(),search, search).stream()
                .filter((stuff -> !currentRefs.contains(stuff)))
                .collect(Collectors.toList());
        /*List<Doctor> doctorsRefs = doctorRepository.findByIdIsNotAndNameContainingOrIdIsNotAndType_NameContainingOrIdIsNotAndEmailContaining(current.getId(),search, current.getId(), search, current.getId(),search).stream()
                .filter(doctor -> !currentRefs.contains(doctor))
                .collect(Collectors.toList());*/
        List<Doctor> doctorsRefs = doctorRepository.findByIdIsNot(search.toLowerCase(), current.getId());
        List<Patient> patientsRefs = patientRepository.findByIdIsNotAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(current.getId(),search,search).stream()
                .filter(patient -> !currentRefs.contains(patient))
                .collect(Collectors.toList());
        refs.addAll(converter.convertFromStuff(stuffRef));
        refs.addAll(converter.convertFromDoctors(doctorsRefs));
        refs.addAll(converter.convertFromPatients(patientsRefs));
        return refs;
    }

    private List<ReferenceDto> findStuffReferencesByCriteria(String search) {
        ReferenceConverter converter = new ReferenceConverter();
        Stuff current = (Stuff) userRepository.findByEmail(getPrincipals());
        Set<User> currentRefs = current.getUserRef();
        List<ReferenceDto> refs = new ArrayList<>();
        List<Patient> patientsRefs = patientRepository.findByIdIsNotAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(current.getId(),search,search).stream()
                .filter(patient -> !currentRefs.contains(patient))
                .collect(Collectors.toList());
        List<Doctor> doctorsRefs = doctorRepository.findByIdIsNotAndNameContainingIgnoreCaseOrType_NameContainingIgnoreCaseOrEmailContainingIgnoreCase(current.getId(),search,search,search).stream()
                .filter(doctor -> !currentRefs.contains(doctor))
                .collect(Collectors.toList());
        List<Stuff> stuffRef = stuffRepository.findByIdIsNotAndDoctor_NameContainingIgnoreCaseAndDoctor_AdminIsTrueOrDoctor_Type_NameContainingIgnoreCaseAndDoctor_AdminIsTrue(current.getId(),search, search).stream()
                .filter((stuff -> !currentRefs.contains(stuff)))
                .collect(Collectors.toList());

        refs.addAll(converter.convertFromStuff(stuffRef));
        refs.addAll(converter.convertFromDoctors(doctorsRefs));
        refs.addAll(converter.convertFromPatients(patientsRefs));
        return refs;
    }

    private List<ReferenceDto> findPatientReferencesByCriteria(String search) {
        ReferenceConverter converter = new ReferenceConverter();
        Patient current = (Patient) userRepository.findByEmail(getPrincipals());
        Set<User> currentRefs = current.getUserRef();
        List<ReferenceDto> refs = new ArrayList<>();
        List<Doctor> doctorsRefs = doctorRepository.findByIdIsNotAndNameContainingIgnoreCaseOrType_NameContainingIgnoreCaseOrEmailContainingIgnoreCase(current.getId(),search,search,search).stream()
                .filter(doctor -> !currentRefs.contains(doctor))
                .collect(Collectors.toList());
        List<Stuff> stuffRef = stuffRepository.findByIdIsNotAndDoctor_NameContainingIgnoreCaseAndDoctor_AdminIsTrueOrDoctor_Type_NameContainingIgnoreCaseAndDoctor_AdminIsTrue(current.getId(),search, search).stream()
                .filter((stuff -> !currentRefs.contains(stuff)))
                .collect(Collectors.toList());
        refs.addAll(converter.convertFromDoctors(doctorsRefs));
        refs.addAll(converter.convertFromStuff(stuffRef));
        return refs;
    }


}
