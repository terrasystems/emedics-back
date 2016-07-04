package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.TaskSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EventSpecification implements Specification<Event> {
    private TaskSearchCriteria criteria;

    public EventSpecification(TaskSearchCriteria criteria) {
        this.criteria = criteria;
    }
    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate templateNamePredicate = null;
        Predicate patientNamePredicate = null;
        Predicate fromNamePredicate = null;
        if (!criteria.getFromName().isEmpty()) {
            fromNamePredicate = builder.like(root.<User>get("fromUser").<String>get("name"), "%" + criteria.getFromName() + "%");
        }
        if (!criteria.getTemplateName().isEmpty()) {
            templateNamePredicate = builder.like(root.<Template>get("template").<String>get("name"), "%" + criteria.getTemplateName() + "%");
        }
        if (!criteria.getPatientName().isEmpty()) {
            patientNamePredicate = builder.like(root.<User>get("patient").<String>get("name"), "%" + criteria.getPatientName() + "%");
        }

        return builder.and(fromNamePredicate, templateNamePredicate, patientNamePredicate);
    }
}
