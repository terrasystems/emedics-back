package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.ReferenceRepository;
import com.terrasystems.emedics.model.Reference;
import com.terrasystems.emedics.model.dto.ReferenceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.NEVER)
public class ReferenceServiceImpl implements ReferenceService {

    @Autowired
    ReferenceRepository referenceRepository;

    @Override
    @Transactional
    public List<Reference> getAllReferences() {
        List<Reference> references = (List<Reference>) referenceRepository.findAll();
        return references;
    }

    @Override
    @Transactional
    public Reference editReference(ReferenceDto referenceDto) {
        Reference reference = referenceRepository.findOne(referenceDto.getId());
        reference.setName(referenceDto.getName());
        reference.setPhone(referenceDto.getPhone());
        reference = referenceRepository.save(reference);
        return reference;
    }

}
