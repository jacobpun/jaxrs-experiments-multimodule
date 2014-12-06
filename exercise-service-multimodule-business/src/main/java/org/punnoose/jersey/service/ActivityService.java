package org.punnoose.jersey.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.punnoose.jersey.dto.ActivityDto;
import org.punnoose.jersey.dto.UserDto;
import org.punnoose.jersey.model.Activity;
import org.punnoose.jersey.model.QActivity;
import org.punnoose.jersey.model.User;
import org.punnoose.jersey.repository.ActivityRepository;
import org.punnoose.jersey.search.SearchCriteria;
import org.punnoose.jersey.util.DozerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository repo;

	@Autowired
	private DozerBeanMapper mapper;

	@Transactional(readOnly = true)
	public List<ActivityDto> findAll() {
		return DozerHelper.map(mapper, repo.findAll(), ActivityDto.class);
	}

	@Transactional(readOnly = true)
	public ActivityDto findActivity(Long activityId) {
		Activity activity = repo.findOne(activityId);

		if (activity == null) {
			return null;
		}

		return mapper.map(activity, ActivityDto.class);
	}

	@Transactional(readOnly = true)
	public UserDto findUser(Long activityId) {
		User user = repo.findUser(activityId);

		if (user == null) {
			return null;
		}

		return mapper.map(user, UserDto.class);
	}

	@Transactional
	public void deleteActivity(Long activityId) {
		repo.delete(activityId);
	}

	@Transactional(readOnly = true)
	public List<ActivityDto> findByDescriptionIn(List<String> descriptions) {
		return DozerHelper.map(mapper,
				repo.findByDescriptionInIgnoreCase(descriptions),
				ActivityDto.class);
	}

	@Transactional(readOnly = true)
	public List<ActivityDto> searchActivity(SearchCriteria searchCriteria) {
		QActivity activity = QActivity.activity;

		BooleanBuilder where = new BooleanBuilder();

		if (searchByDescription(searchCriteria)) {
			where.and(activity.description.in(searchCriteria.getDescriptions()));
		}

		if (searchByUserName(searchCriteria)) {
			where.and(activity.user.name.equalsIgnoreCase(searchCriteria
					.getUserName()));
		}

		if (searchByUserId(searchCriteria)) {
			where.and(activity.user.id.eq(searchCriteria.getUserId()));
		}

		Iterator<Activity> searchResultsIterator = repo.findAll(where)
				.iterator();
		List<Activity> searchResultsList = getAsList(searchResultsIterator);
		return DozerHelper.map(mapper, searchResultsList, ActivityDto.class);
	}

	private boolean searchByUserName(SearchCriteria searchCriteria) {
		return searchCriteria.getUserName() != null
				&& searchCriteria.getUserName().length() > 0;
	}

	private boolean searchByUserId(SearchCriteria searchCriteria) {
		return searchCriteria.getUserId() != null;
	}

	private boolean searchByDescription(SearchCriteria searchCriteria) {
		return searchCriteria.getDescriptions() != null
				&& searchCriteria.getDescriptions().size() > 0;
	}

	private <T> List<T> getAsList(Iterator<T> iterator) {
		List<T> searchResultsList = new ArrayList<T>();
		CollectionUtils.addAll(searchResultsList, iterator);
		return searchResultsList;
	}
}