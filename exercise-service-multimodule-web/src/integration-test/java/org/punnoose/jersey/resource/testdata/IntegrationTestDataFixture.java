package org.punnoose.jersey.resource.testdata;

import java.util.HashMap;
import java.util.Map;

import org.punnoose.jersey.dto.ActivityDto;
import org.punnoose.jersey.dto.UserDto;

public class IntegrationTestDataFixture {

	public static final UserDto USER_1;
	public static final UserDto USER_2;
	public static final Map<String, String> USER_3 = new HashMap<String, String>();
	
	public static final ActivityDto USER_1_ACTIVITY_1;
	public static final ActivityDto USER_1_ACTIVITY_2;
	public static final ActivityDto USER_2_ACTIVITY_1;
	
	static{
		
		USER_1 = new UserDto();
		USER_1.setName("USER_1");
		
		USER_2 = new UserDto();
		USER_2.setName("USER_2");
		
		USER_3.put("name", "USER_3");
		USER_3.put("extra1", "EXTRA_1_VAL");
		USER_3.put("extra2", "EXTRA_2_VAL");
		
		USER_1_ACTIVITY_1 = new ActivityDto();
		USER_1_ACTIVITY_1.setDescription("USER_1_ACTIVITY_1");
		USER_1_ACTIVITY_1.setDuration(11);

		USER_1_ACTIVITY_2 = new ActivityDto();
		USER_1_ACTIVITY_2.setDescription("USER_1_ACTIVITY_2");
		USER_1_ACTIVITY_2.setDuration(11);
		
		USER_2_ACTIVITY_1 = new ActivityDto();
		USER_2_ACTIVITY_1.setDescription("USER_2_ACTIVITY_1");
		USER_2_ACTIVITY_1.setDuration(21);
	}

}