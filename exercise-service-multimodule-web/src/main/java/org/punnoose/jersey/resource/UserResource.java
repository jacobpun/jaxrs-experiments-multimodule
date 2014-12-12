package org.punnoose.jersey.resource;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.codec.digest.DigestUtils;
import org.punnoose.jersey.dto.ActivityDto;
import org.punnoose.jersey.dto.UserDto;
import org.punnoose.jersey.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@Path("/users")
public class UserResource {

	private Logger logger = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private UserService service;

	@Context
	private Request request;
	
	@Context
	private UriInfo uriInfo;

	@POST
	@Produces({  MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response create(@Valid @NotNull UserDto userDto) {
		UserDto createdUser = service.create(userDto);
		URI userUri = getUriForUser(createdUser);
		logger.info("Created user: {}", createdUser);
		return Response.created(userUri).entity(createdUser).build();
	}

	@GET
	@Path("{userId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response get(@PathParam("userId")Long userId) {

		// Check if the user ID is valid. If not, return HTTP 400 error
		validateUserId(userId);

		// Fetch the user from the service
		UserDto user = service.findUser(userId);

		// Check if the fetched user is null. If so, return HTTP 404 error
		if (user == null) {
			logger.info(
					"No user found for the ID {}. Returning HTTP error 404.",
					userId);
			throw new NotFoundException("User Id " + userId
					+ " not found.");
		}

		EntityTag eTag = generateEntityTag(user);
		Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);
		
		if(rb!= null) {
			logger.debug("Precondition failed while GETting the userId ID {}. ", userId);
			return rb.build();
		}
		
		// Return the user
		logger.debug(
				"Returning userId for the userId ID {}. userId: {}.",
				userId, user.toString());
		return Response.ok().tag(eTag).entity(user).build();
	}

	@POST
	@Path("{userId}/activities")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addActivity(@PathParam("userId") Long userId,
			@Valid @NotNull ActivityDto activityDto) {
		try {
			ActivityDto createdActivity = service.addActivity(userId,
					activityDto);
			URI activityUri = getUriForActivity(createdActivity);
			logger.info("Added activity {} for the user Id {}",
					createdActivity, userId);
			return Response.created(activityUri).entity(createdActivity)
					.build();

		} catch (DataIntegrityViolationException ex) {
			logger.error(
					"Caught DataIntegrityViolationException for user ID {}. Returning HTTP 404 error.",
					userId);
			throw new NotFoundException("User Id " + userId + " not found.");
		}
	}

	private URI getUriForActivity(ActivityDto activity) {
		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI activityUri = ub.path(String.valueOf(activity.getId())).build();
		return activityUri;
	}

	private URI getUriForUser(UserDto user) {
		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI userUri = ub.path(String.valueOf(user.getId())).build();
		return userUri;
	}
	
	private void validateUserId(Long userId) {
		if (isInvalidUserId(userId)) {
			logger.info(
					"Invalid user Id {} received in the request. Returning HTTP error 400.",
					userId);
			throw new BadRequestException("User Id " + userId
					+ " is invalid.");
		}
	}

	private boolean isInvalidUserId(Long userId) {
		return userId == null || userId <= 0;
	}
	
	private EntityTag generateEntityTag(UserDto user) {
		return new EntityTag(DigestUtils.md5Hex(user.getName()));
	}
}