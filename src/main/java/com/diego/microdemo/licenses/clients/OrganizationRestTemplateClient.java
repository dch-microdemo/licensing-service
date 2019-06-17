package com.diego.microdemo.licenses.clients;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.diego.microdemo.licenses.model.Organization;
import com.diego.microdemo.licenses.repository.OrganizationRedisRepository;

/**
 * @author Diego Chavez
 *
 */
@Component
public class OrganizationRestTemplateClient {

	private final RestTemplate restTemplate;

	private final OrganizationRedisRepository orgRedisRepo;

	public OrganizationRestTemplateClient(RestTemplate restTemplate, OrganizationRedisRepository orgRedisRepo) {
		this.restTemplate = restTemplate;
		this.orgRedisRepo = orgRedisRepo;
	}

	private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

	private Organization checkRedisCache(String organizationId) {
		try {
			Optional<Organization> org = orgRedisRepo.findById(organizationId);
			if (org.isPresent()) {
				return org.get();
			} else {
				return null;
			}
		} catch (Exception ex) {
			logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.  Exception {}",
					organizationId, ex);
			return null;
		}
	}

	private void cacheOrganizationObject(Organization org) {
		try {
			orgRedisRepo.save(org);
		} catch (Exception ex) {
			logger.error("Unable to cache organization {} in Redis. Exception {}", org.getId(), ex);
		}
	}

	public Organization getOrganization(String organizationId) {
		logger.debug("In Licensing Service.getOrganization");
		Organization org = checkRedisCache(organizationId);

		if (org != null) {
			logger.debug("I have successfully retrieved an organization {} from the redis cache: {}", organizationId,
					org);
			return org;
		}

		logger.debug("Unable to locate organization from the redis cache: {}.", organizationId);

		ResponseEntity<Organization> restExchange = restTemplate.exchange(
				"http://localhost:8081/v1/organizations/{organizationId}", HttpMethod.GET, null, Organization.class,
				organizationId);

		org = restExchange.getBody();
		if (org != null) {
			cacheOrganizationObject(org);
		}

		return org;
	}
}
