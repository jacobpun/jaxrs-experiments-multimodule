package org.punnoose.jersey.resource;

import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.punnoose.jersey.dto.ActivityDto;
import org.punnoose.jersey.dto.UserDto;
import org.punnoose.jersey.search.SearchCriteria;
import org.punnoose.jersey.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

@Path("/activities")
public class ActivityResource {

	private Logger logger = LoggerFactory.getLogger(ActivityResource.class);

	@Autowired
	private ActivityService service;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getActivities(@QueryParam(value = "description") List<String> descriptions) {

		List<ActivityDto> activityDtos;
		
		if (descriptions == null || descriptions.size() == 0) {			
			activityDtos = getService().findAll();
		} else {
			activityDtos = getService().findByDescriptionIn(descriptions);
		}
		
		return Response.ok()
				.entity(new GenericEntity<List<ActivityDto>>(activityDtos){})
				.build();
	}

	
	@POST
	@Path("/search")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getActivities(SearchCriteria searchCriteria) {
		
		List<ActivityDto> activityDtos = getService().searchActivity(searchCriteria);

		return Response.ok()
				.entity(new GenericEntity<List<ActivityDto>>(activityDtos){})
				.build();
	}
	
	/**
	 * This API allow customers to fetch an activity for a given Activity ID
	 * @param activityId Id of the activity to be fetched
	 * @return Response entity
	 */
	@GET
	@Path("{activityId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getActivity(@PathParam("activityId") Long activityId) {

		// Check if the activity ID is valid. If not, return HTTP 400 error
		validateActivityId(activityId);

		// Fetch the activity from the service
		ActivityDto activity = getService().findActivity(activityId);

		// Check if the fetched activity is null. If so, return HTTP 404 error
		if (activity == null) {
			logger.info(
					"No activity found for the ID {}. Returning HTTP error 404.",
					activityId);
			throw new NotFoundException("Activity Id " + activityId
					+ " not found.");
		}

		// Return the activity
		logger.debug(
				"Returning activity for the activity ID {}. Activity: {}.",
				activityId, activity.toString());
		return Response.ok().entity(activity).build();
	}

	@GET
	@Path("{activityId}/user")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUser(@PathParam("activityId") Long activityId) {

		// Check if the activity ID is valid. If not, return HTTP 400 error
		validateActivityId(activityId);

		// Fetch the user from the service
		UserDto user = getService().findUser(activityId);

		// Check if the fetched user is null. If so, return HTTP 404 error
		if (user == null) {
			logger.info(
					"No activity found for the ID {}. Returning HTTP error 404.",
					activityId);
			throw new NotFoundException("Activity Id " + activityId
					+ " not found.");
		}

		// Return the user
		logger.debug("Returning user for the activity ID {}. User: {}.",
				activityId, user.toString());
		return Response.ok().entity(user).build();
	}

	@DELETE
	@Path("{activityId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteActivity(@PathParam("activityId") Long activityId) {

		// Check if the activity ID is valid. If not, return HTTP 400 error
		validateActivityId(activityId);

		// Delete the activity
		try {
			getService().deleteActivity(activityId);
		} catch (EmptyResultDataAccessException e) {
			logger.info(
					"No activity found for the ID {}. Returning HTTP error 404.",
					activityId);
			throw new NotFoundException("Activity Id " + activityId
					+ " not found.");
		}

		// Return HTTP 200 response
		logger.debug("Deteled activity having activity ID {}.", activityId);
		return Response.ok().build();
	}

	private void validateActivityId(Long activityId) {
		if (isInvalidActivityId(activityId)) {
			logger.info(
					"Invalid activity Id {} received in the request. Returning HTTP error 400.",
					activityId);
			throw new BadRequestException("Activity Id " + activityId
					+ " is invalid.");
		}
	}

	private boolean isInvalidActivityId(Long activityId) {
		return activityId == null || activityId <= 0;
	}


	public ActivityService getService() {
		return service;
	}


	public void setService(ActivityService service) {
		this.service = service;
	}
}