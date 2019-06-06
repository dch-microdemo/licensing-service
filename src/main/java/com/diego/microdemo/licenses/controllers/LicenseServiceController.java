package com.diego.microdemo.licenses.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diego.microdemo.licenses.model.License;
import com.diego.microdemo.licenses.services.LicenseService;

/**
 * @author Diego Chavez
 *
 */
@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

	private final LicenseService licenseService;

	private final HttpServletRequest request;

	/**
	 * @param licenseService
	 * @param request
	 */
	public LicenseServiceController(LicenseService licenseService, HttpServletRequest request) {
		this.licenseService = licenseService;
		this.request = request;
	}

	private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {

		return licenseService.getLicensesByOrg(organizationId);
	}

	@RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
	public License getLicenses(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		logger.debug("Entering the license-service-controller");
		logger.debug("Found tmx-correlation-id in license-service-controller: {} ",
				request.getHeader("tmx-correlation-id"));
		return licenseService.getLicense(organizationId, licenseId);
	}

	@RequestMapping(value = "{licenseId}", method = RequestMethod.PUT)
	public void updateLicenses(@PathVariable("licenseId") String licenseId, @RequestBody License license) {
		licenseService.updateLicense(license);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public void saveLicenses(@RequestBody License license) {
		licenseService.saveLicense(license);
	}

	@RequestMapping(value = "{licenseId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLicenses(@PathVariable("licenseId") String licenseId, @RequestBody License license) {
		licenseService.deleteLicense(license);
	}
}
