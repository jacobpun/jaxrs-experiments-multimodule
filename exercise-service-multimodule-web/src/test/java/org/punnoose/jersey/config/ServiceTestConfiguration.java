package org.punnoose.jersey.config;

import static org.mockito.Mockito.mock;

import org.dozer.DozerBeanMapper;
import org.punnoose.jersey.repository.ActivityRepository;
import org.punnoose.jersey.repository.UserRepository;
import org.punnoose.jersey.service.ActivityService;
import org.punnoose.jersey.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceTestConfiguration {
	@Bean
	public ActivityService activityService() {
		return mock(ActivityService.class);
	}
	
	@Bean
	public ActivityRepository activityRepository() {
		return mock(ActivityRepository.class);
	}

	@Bean
	public UserService userService() {
		return mock(UserService.class);
	}

	@Bean
	public UserRepository userRepository() {
		return mock(UserRepository.class);
	}

	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		return mock(DozerBeanMapper.class);
	}
}