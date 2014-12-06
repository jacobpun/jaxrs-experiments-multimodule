package org.punnoose.jersey.repository;

import org.punnoose.jersey.model.Activity;

public interface UserRepositoryCustom {

	void addActivity(Long userId, Activity activity);
	
}
