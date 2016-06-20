package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.StuffRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Role;
import com.terrasystems.emedics.model.Stuff;
import com.terrasystems.emedics.model.dto.StuffDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StuffServiceImpl implements StuffService, CurrentUserService {

    @Autowired
    StuffRepository stuffRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailService mailService;


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
    @Transactional
    public Stuff createStuff(StuffDto dto) {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        if (userRepository.existsByEmail(dto.getEmail())) {
            return null;
        }
        Stuff stuff = new Stuff();
        stuff.setUsers(new HashSet<>());
        stuff.setUserRef(new HashSet<>());
        Set<Role> roles = new HashSet<>();
        Role role1 = new Role("ROLE_STUFF");
        role1.setUser(stuff);
        roles.add(role1);

        stuff.setRoles(roles);
        stuff.setDoctor(current);
        if (dto.getPassword() == null) {
            stuff.setPassword(RandomStringUtils.random(10, 'a','b','c','A','B','C','1','2','3','4','5'));
        } else {
            stuff.setPassword(dto.getPassword());
        }
        stuff.setFirstName(dto.getFirstName());
        stuff.setLastName(dto.getLastName());
        stuff.setEmail(dto.getEmail());
        stuff.setPhone(dto.getPhone());
        stuff.setBirth(dto.getBirth());
        stuff.setEnabled(true);
        stuff.setRegistrationDate(new Date());
        if (stuffRepository.save(stuff) == null) {
            return null;
        }

        if (!mailService.sendStuffMail(stuff.getEmail(), stuff.getPassword()).isValue()) {
            return null;
        }
        return stuff;
    }

    @Override
    public void deleteStuff(String id) {
        stuffRepository.delete(id);
    }

    @Override
    @Transactional
    public Stuff updateStuff(StuffDto dto) {
        Stuff stuff = stuffRepository.findOne(dto.getId());
        stuff.setPhone(dto.getPhone());
        stuff.setEmail(dto.getEmail());
        stuff.setFirstName(dto.getFirstName());
        stuff.setLastName(dto.getLastName());
        stuff.setBirth(dto.getBirth());
        stuff.setPassword(dto.getPassword());


        return stuffRepository.save(stuff);
    }
}
