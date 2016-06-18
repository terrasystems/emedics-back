package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.StuffRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Role;
import com.terrasystems.emedics.model.Stuff;
import com.terrasystems.emedics.model.dto.StuffDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StuffServiceImpl implements StuffService, CurrentUserService {

    @Autowired
    StuffRepository stuffRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public List<Stuff> getAllStuff() {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        return current.getStuff();
    }

    @Override
    public Stuff getById(String id) {
        return stuffRepository.findOne(id);
    }

    @Override
    public Stuff addNewStuff(StuffDto dto) {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        Stuff stuff = new Stuff();
        stuff.setUsers(new HashSet<>());
        stuff.setUserRef(new HashSet<>());
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_STUFF"));
        stuff.setRoles(roles);
        stuff.setDoctor(current);
        stuff.setFirstName(dto.getFirstName());
        stuff.setLastName(dto.getLastName());
        stuff.setEmail(dto.getEmail());
        stuff.setPhone(dto.getPhone());
        stuff.setBirth(dto.getBirth());
        stuffRepository.save(stuff);
        return stuff;
    }

    @Override
    public void deleteStuff(String id) {
        stuffRepository.delete(id);
    }
}
