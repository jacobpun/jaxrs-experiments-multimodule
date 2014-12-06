package org.punnoose.jersey;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class ExerciseService extends ResourceConfig {
	public ExerciseService() {
		packages("org.punnoose.jersey.resource");
		// register(EntityFilteringFeature.class);
		JacksonJsonProvider json = new JacksonJsonProvider().configure(WRITE_DATES_AS_TIMESTAMPS, false).configure(INDENT_OUTPUT, true);
		register(json);
		EncodingFilter.enableFor(this, GZipEncoder.class);
	}
}