package org.punnoose.jersey.config;

import java.util.Arrays;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "org.punnoose.jersey.service" })
public class ServiceConfiguration {
	@Bean
	public DozerBeanMapper getMapper() {
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.setMappingFiles(Arrays
				.asList(new String[] { "dozer-bean-mappings.xml" }));
		return mapper;
	}
}