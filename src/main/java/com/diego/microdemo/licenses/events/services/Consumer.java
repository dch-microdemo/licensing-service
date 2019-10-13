package com.diego.microdemo.licenses.events.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.diego.microdemo.licenses.repository.OrganizationRedisRepository;
import com.diego.microdemo.organization.events.model.OrganizationChangeModel;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.cloud.stream.messaging.Sink;

@Service
public class Consumer {
	private final Logger logger = LoggerFactory.getLogger(Consumer.class);
	@Autowired
	private OrganizationRedisRepository organizationRedisRepository;

	//@KafkaListener(topics = "orgChangeTopic", groupId = "licensingGroup")
	@StreamListener(Sink.INPUT)
	public void receiveOrgChange(@Payload OrganizationChangeModel orgChange) {
		logger.info(String.format("$$ -> Consumed Message with Action -> %s", orgChange.getAction()));
		logger.debug("Received a message of type " + orgChange.getType());
		switch (orgChange.getAction()) {
		case "GET":
			logger.debug("Received a GET event from the organization service for organization id {}",
					orgChange.getOrganizationId());
			break;
		case "SAVE":
			logger.debug("Received a SAVE event from the organization service for organization id {}",
					orgChange.getOrganizationId());
			break;
		case "UPDATE":
			logger.debug("Received a UPDATE event from the organization service for organization id {}",
					orgChange.getOrganizationId());
			organizationRedisRepository.deleteById(orgChange.getOrganizationId());
			break;
		case "DELETE":
			logger.debug("Received a DELETE event from the organization service for organization id {}",
					orgChange.getOrganizationId());
			organizationRedisRepository.deleteById(orgChange.getOrganizationId());
			break;
		default:
			logger.error("Received an UNKNOWN event from the organization service of type {}", orgChange.getType());
			break;
		}
	}
}
