package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.TaskSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EventSpecification implements Specification<Event> {
    private TaskSearchCriteria criteria;
    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return builder.like(root.<User>get("fromUser").<String>get("name"), criteria.getFromName());
    }
}
