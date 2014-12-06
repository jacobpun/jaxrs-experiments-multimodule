package org.punnoose.jersey.repository;

import org.punnoose.jersey.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
	
}