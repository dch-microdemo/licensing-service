package com.diego.microdemo.licenses.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.diego.microdemo.licenses.model.Organization;

@Component
public class OrganizationRestTemplateClient {
	@Autowired
	RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

	public Organization getOrganization(String organizationId) {
		logger.debug("In Licensing Service.getOrganization");
		ResponseEntity<Organization> restExchange = restTemplate.exchange(
				"http://localhost:8081/v1/organizations/{organizationId}", HttpMethod.GET, null,
				Organization.class, organizationId);

		return restExchange.getBody();
	}
}
