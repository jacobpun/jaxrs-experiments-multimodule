package org.punnoose.jersey.service;

import org.dozer.DozerBeanMapper;
import org.punnoose.jersey.dto.ActivityDto;
import org.punnoose.jersey.dto.UserDto;
import org.punnoose.jersey.model.Activity;
import org.punnoose.jersey.model.User;
import org.punnoose.jersey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
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
			return null;
		}
		
		return mapper.map(user, UserDto.class);
	}
}