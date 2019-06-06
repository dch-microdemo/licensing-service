package com.diego.microdemo.licenses.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author Diego Chavez
 *
 */
@Component
public class ServiceConfig {

	@Value("${example.property}")
	private String exampleProperty = "";

	@Value("${signing.key}")
	private String jwtSigningKey = "";

	public String getJwtSigningKey() {
		return jwtSigningKey;
	}

	public String getExampleProperty() {
		return exampleProperty;
	}
	/**
	 * public String getRedisServer(){ return redisServer; }
	 * 
	 * public Integer getRedisPort(){ return new Integer( redisPort ).intValue(); }
	 */

}
