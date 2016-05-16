package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization,String> {
}
