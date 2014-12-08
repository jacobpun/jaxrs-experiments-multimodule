package org.punnoose.jersey.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.mockito.Mockito;
import org.punnoose.jersey.ExerciseService;
import org.punnoose.jersey.config.ServiceTestConfiguration;
import org.punnoose.jersey.service.ActivityService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

public class ActivityResourceTest extends JerseyTest {

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
	public void should_return_http_error_404_when_trying_to_get_an_activity_that_doesnt_exist() {

		final Long NON_EXISTING_ACTIVITY_ID = 1L;

		// Configure the mocked service to return null from the findActivity
		// method
		ActivityService mockService = context.getBean(ActivityService.class);
		Mockito.doReturn(null).when(mockService)
				.findActivity(NON_EXISTING_ACTIVITY_ID);

		// Send GET request to the resource
		Response actity = target("activities/" + NON_EXISTING_ACTIVITY_ID)
				.request(APPLICATION_JSON).get();

		// Assert that the response code id 404
		assertThat(actity.getStatus(),
				is(Response.Status.NOT_FOUND.getStatusCode()));
	}

	@Test
	public void should_return_http_error_400_when_trying_to_get_an_invalid_activity_id() {
		final Long INVALID_ACTIVITY_ID = 0L;

		// Send GET request with an invalid activity ID
		Response actity = target("activities/" + INVALID_ACTIVITY_ID).request(
				APPLICATION_JSON).get();
		
		// Assert that the response code id 400
		assertThat(actity.getStatus(),
				is(Response.Status.BAD_REQUEST.getStatusCode()));
	}

	@Test
	public void should_return_http_error_404_when_trying_to_delete_an_activity_that_doesnt_exist() {

		final Long NON_EXISTING_ACTIVITY_ID = 1L;

		// Configure the mocked service to throw exception when a non-existing id is passed to the delete method
		ActivityService mockService = context.getBean(ActivityService.class);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(mockService)
				.deleteActivity(NON_EXISTING_ACTIVITY_ID);

		// Send GET request to the resource
		Response actity = target("activities/" + NON_EXISTING_ACTIVITY_ID)
				.request(APPLICATION_JSON).delete();

		// Assert that the response code id 404
		assertThat(actity.getStatus(),
				is(Response.Status.NOT_FOUND.getStatusCode()));
	
	}
	

	@Test
	public void should_return_http_error_400_when_trying_to_delete_an_invalid_activity_id() {
		final Long INVALID_ACTIVITY_ID = 0L;

		// Send GET request with an invalid activity ID
		Response actity = target("activities/" + INVALID_ACTIVITY_ID).request(
				APPLICATION_JSON).delete();
		
		// Assert that the response code id 400
		assertThat(actity.getStatus(),
				is(Response.Status.BAD_REQUEST.getStatusCode()));
	}

}