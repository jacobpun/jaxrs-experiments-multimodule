package org.punnoose.jersey.service;

import org.dozer.DozerBeanMapper;
import org.punnoose.jersey.dto.ActivityDto;
import org.punnoose.jersey.dto.UserDto;
import org.punnoose.jersey.model.Activity;
import org.punnoose.jersey.model.User;
import org.punnoose.jersey.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository repo;

	@Autowired
	private DozerBeanMapper mapper;

	@Transactional
	public UserDto create(UserDto userDto) {
		User user = mapper.map(userDto, User.class);
		repo.save(user);
		return mapper.map(user, UserDto.class);
	}

	@Transactional
	public ActivityDto addActivity(Long userId, ActivityDto activityDto) {
		Activity activity = mapper.map(activityDto, Activity.class);
		repo.addActivity(userId, activity);
		return mapper.map(activity, ActivityDto.class);
	}

	@Transactional(readOnly=true)
	public UserDto findUser(Long userId) {
		User user = repo.findOne(userId);
		
		if (user == null) {
			logger.debug("User ID {} not found in the database", userId);
			return null;
		}
		
		logger.debug("Rertrieverd user entity from repository for the user ID {}", userId);
		return mapper.map(user, UserDto.class);
	}
}