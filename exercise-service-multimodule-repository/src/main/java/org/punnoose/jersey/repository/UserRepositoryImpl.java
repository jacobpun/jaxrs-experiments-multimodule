package org.punnoose.jersey.repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.punnoose.jersey.model.Activity;
import org.punnoose.jersey.model.User;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

public class UserRepositoryImpl implements UserRepositoryCustom {
	@PersistenceContext
	private EntityManager em;
	private JpaEntityInformation<Activity, ?> activityEntityInfo;

	@PostConstruct
	public void postConstruct() {
		this.activityEntityInfo = JpaEntityInformationSupport.getMetadata(
				Activity.class, em);
	}

	public void addActivity(Long userId, Activity activity) {
		activity.setUser(em.getReference(User.class, userId));
		save(activity);
	}

	private void save(Activity activity) {
		if (activityEntityInfo.isNew(activity)) {
			em.persist(activity);
		} else {
			em.merge(activity);
		}
	}
}