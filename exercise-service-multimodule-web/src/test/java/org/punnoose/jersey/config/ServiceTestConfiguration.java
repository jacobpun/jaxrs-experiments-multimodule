package org.punnoose.jersey.config;

import static org.mockito.Mockito.mock;

import org.dozer.DozerBeanMapper;
import org.punnoose.jersey.repository.ActivityRepository;
import org.punnoose.jersey.service.ActivityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceTestConfiguration {
	@Bean
	public ActivityService activityService() {
		ActivityService mockService = mock(ActivityService.class);
		return mockService;
	}

	@Bean
	public ActivityRepository activityRepository() {
		return mock(ActivityRepository.class);
	}

	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		return mock(DozerBeanMapper.class);
	}
}