package org.punnoose.jersey.repository;

import java.util.List;

import org.punnoose.jersey.model.Activity;
import org.punnoose.jersey.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ActivityRepository extends JpaRepository<Activity, Long>, QueryDslPredicateExecutor<Activity> {
	@Query("select u from Activity a inner join a.user u where a.id=:activityId")
	User findUser(@Param("activityId") Long activityId);
	
	List<Activity> findByDescriptionInIgnoreCase(List<String> descriptions);
}