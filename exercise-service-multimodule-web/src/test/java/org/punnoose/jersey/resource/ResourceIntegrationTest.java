package org.punnoose.jersey.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.punnoose.jersey.resource.testdata.TestDataFixture.USER_1;
import static org.punnoose.jersey.resource.testdata.TestDataFixture.USER_1_ACTIVITY_1;
import static org.punnoose.jersey.resource.testdata.TestDataFixture.USER_1_ACTIVITY_2;
import static org.punnoose.jersey.resource.testdata.TestDataFixture.USER_2;
import static org.punnoose.jersey.resource.testdata.TestDataFixture.USER_2_ACTIVITY_1;
import static org.punnoose.jersey.resource.testdata.TestDataFixture.USER_3;

import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.punnoose.jersey.ExerciseService;
import org.punnoose.jersey.config.ServiceIntegrationTestConfiguration;
import org.punnoose.jersey.dto.ActivityDto;
import org.punnoose.jersey.dto.UserDto;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ResourceIntegrationTest extends JerseyTest {

	private Long userid1, userid2;
	private Long userid1_activityid1, userid1_activityid2, userid2_activityid1;

	@Override
	protected Application configure() {

		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				ServiceIntegrationTestConfiguration.class);
		ExerciseService app = new ExerciseService();
		app.property("contextConfig", ctx);
		return app;

	}

	public void setupUsersAndActivities() {
		userid1 = createUser(USER_1);
		userid2 = createUser(USER_2);
		
		userid1_activityid1 = createActivity(userid1, USER_1_ACTIVITY_1);
		userid1_activityid2 = createActivity(userid1, USER_1_ACTIVITY_2);
		userid1_activityid2 = createActivity(userid2, USER_2_ACTIVITY_1);
	}

	@Test
	public void should_save_and_retrieve_extras_in_user_objects() {
		Long userid = createUser(USER_3);
		Response user = target("users/" + userid).request(APPLICATION_JSON).get();
		assertThat(user.getStatus(), is(200));
		UserDto userDto = user.readEntity(UserDto.class);
		assertThat(userDto.getExtras().size(),is(2) );
	}
	
	@Test
	public void should_return_all_activities() {
		setupUsersAndActivities();
		Response activities = target("activities").request(APPLICATION_JSON).get();
		assertThat(activities.getStatus(), is(200));
	}
	
	private Long createUser(UserDto user) {
		Entity<UserDto> user1Entity = Entity.entity(user, APPLICATION_JSON);
		return target("users")
				.request(APPLICATION_JSON)
				.post(user1Entity)
				.readEntity(UserDto.class)
				.getId();
	}

	private Long createUser(Map<String, String> user) {
		Entity<Map<String, String>> user1Entity = Entity.entity(user, APPLICATION_JSON);
		return target("users")
				.request(APPLICATION_JSON)
				.post(user1Entity)
				.readEntity(UserDto.class)
				.getId();
	}
	
	private Long createActivity(Long userId, ActivityDto activity) {
		Entity<ActivityDto> user1Activity1 = Entity.entity(activity, APPLICATION_JSON);
		return target("users/" + userId + "/activities")
				.request(APPLICATION_JSON)
				.post(user1Activity1)
				.readEntity(ActivityDto.class)
				.getId();
	}
}