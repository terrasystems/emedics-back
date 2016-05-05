package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.ReferenceRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Reference;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.ReferenceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Service
@Transactional(propagation = Propagation.NEVER)
public class ReferenceServiceImpl implements ReferenceService {

    @Autowired
    ReferenceRepository referenceRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public List<Reference> getAllReferences() {
        User user = userRepository.findByEmail(getPrincipals());
        List<Reference> references = referenceRepository.findByUser(user);
        return references;
    }

    @Override
    @Transactional
    public Reference editReference(ReferenceDto referenceDto) {
        User user = userRepository.findByEmail(getPrincipals());
        if (referenceDto.getId()==null) {
            Reference reference = new Reference();
            reference.setType(referenceDto.getType());
            reference.setName(referenceDto.getName());
            reference.setPhone(referenceDto.getPhone());
            reference.setUser(user);
            referenceRepository.save(reference);
            return reference;
        } else {


            Reference reference = referenceRepository.findOne(referenceDto.getId());
            reference.setName(referenceDto.getName());
            reference.setPhone(referenceDto.getPhone());
            reference = referenceRepository.save(reference);
            return reference;
        }

    }

    @Override
    @Transactional
    public Reference getReferenceById(String id) {
        Reference reference = referenceRepository.findOne(id);
        return reference;
    }

    @Override
    @Transactional
    public void removeReference(String id) {
        referenceRepository.delete(id);
    }

}
