package org.punnoose.jersey.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.mockito.Mockito;
import org.punnoose.jersey.ExerciseService;
import org.punnoose.jersey.config.ServiceTestConfiguration;
import org.punnoose.jersey.dto.UserDto;
import org.punnoose.jersey.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserResourceTest extends JerseyTest {

	private static final String USERS_URI_PATH = "users";
	private ApplicationContext context;

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		ExerciseService app = new ExerciseService();
		context = new AnnotationConfigApplicationContext(
				ServiceTestConfiguration.class);
		app.property("contextConfig", context);
		return app;
	}

	@Test
	public void should_return_http_error_400_when_trying_to_create_a_user_whose_name_is_null() {

		// Create a User entity having no "name" property
		Map<String, String> userWithNoName = new HashMap<>();
		userWithNoName.put("ExtraKey1", "ExtraValue1");
		Entity<Map<String, String>> userEntity = Entity.entity(userWithNoName,
				APPLICATION_JSON);

		// POST the user entity to "users" resource
		Response user = target(USERS_URI_PATH).request(APPLICATION_JSON).post(
				userEntity);

		// Assert that the response code is 400
		assertThat(user.getStatus(),
				is(Response.Status.BAD_REQUEST.getStatusCode()));

		// Assert that the valid error message is returned
		assertThat(user.readEntity(String.class),
				containsString("Name should be valid"));
	}

	@Test
	public void should_return_http_error_400_when_null_is_posetd_to_users_collection() {
		// POST NULL to "users" resource
		Response actity = target(USERS_URI_PATH).request(APPLICATION_JSON)
				.post(null);

		// Assert that the response code is 400
		assertThat(actity.getStatus(),
				is(Response.Status.BAD_REQUEST.getStatusCode()));

		// Assert that the valid error message is returned
		assertThat(actity.readEntity(String.class),
				containsString("may not be null"));
	}

	@Test
	public void should_retun_http_error_304_when_trying_to_get_an_unmodified_user_object() {
		// configure mock objects
		final long FAKE_USER_ID = 1L;
		final String FAKE_USER_NAME = "John";
		UserDto fakeUser = new UserDto();
		fakeUser.setId(FAKE_USER_ID);
		fakeUser.setName(FAKE_USER_NAME);

		UserService mockService = context.getBean(UserService.class);
		Mockito.doReturn(fakeUser).when(mockService).findUser(FAKE_USER_ID);

		// get a user & retrieve the eTag
		EntityTag eTag = target(USERS_URI_PATH + "/" + FAKE_USER_ID)
				.request(APPLICATION_JSON).get().getEntityTag();

		// get the same user again by passing the if-None-Match header
		Response resp = target(USERS_URI_PATH + "/" + FAKE_USER_ID)
				.request(APPLICATION_JSON).header("If-None-Match", eTag).get();

		// assert that the response code is 304
		assertThat(resp.getStatus(),
				is(Response.Status.NOT_MODIFIED.getStatusCode()));
	}
}