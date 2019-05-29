package com.diego.microdemo.licenses.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diego.microdemo.licenses.clients.OrganizationRestTemplateClient;
import com.diego.microdemo.licenses.config.ServiceConfig;
import com.diego.microdemo.licenses.model.License;
import com.diego.microdemo.licenses.model.Organization;
import com.diego.microdemo.licenses.repository.LicenseRepository;

@Service
public class LicenseService {
	
	@Autowired
	ServiceConfig config;
	
	@Autowired
	private LicenseRepository licenseRepository;

	@Autowired
	OrganizationRestTemplateClient organizationRestClient;

	public License getLicense(String organizationId, String licenseId) {
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

		Organization org = getOrganization(organizationId);
		return license.withOrganizationName(org.getName()).withContactName(org.getContactName())
				.withContactEmail(org.getContactEmail()).withContactPhone(org.getContactPhone())
				.withComment(config.getExampleProperty());
	}

	private Organization getOrganization(String organizationId) {
		return organizationRestClient.getOrganization(organizationId);
	}

	public List<License> getLicensesByOrg(String organizationId) {
		return licenseRepository.findByOrganizationId(organizationId);
	}

	public void saveLicense(License license) {
		license.withId(UUID.randomUUID().toString());

		licenseRepository.save(license);
	}

	public void updateLicense(License license) {
		licenseRepository.save(license);
	}

	public void deleteLicense(License license) {
		licenseRepository.deleteById(license.getLicenseId());
	}

}
