package com.diego.microdemo.licenses.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.diego.microdemo.licenses.model.Organization;

@Repository
public interface OrganizationRedisRepository extends CrudRepository<Organization, String>{

}
