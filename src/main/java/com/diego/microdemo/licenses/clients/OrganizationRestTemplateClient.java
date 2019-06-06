package com.diego.microdemo.licenses.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.diego.microdemo.licenses.model.Organization;

/**
 * @author Diego Chavez
 *
 */
@Component
public class OrganizationRestTemplateClient {
	
	final private RestTemplate restTemplate;

	public OrganizationRestTemplateClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

	public Organization getOrganization(String organizationId) {
		logger.debug("In Licensing Service.getOrganization");
		ResponseEntity<Organization> restExchange = restTemplate.exchange(
				"http://localhost:8081/v1/organizations/{organizationId}", HttpMethod.GET, null,
				Organization.class, organizationId);

		return restExchange.getBody();
	}
}
