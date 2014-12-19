package org.punnoose.jersey;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.EncodingFilter;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;

public class ExerciseService extends ResourceConfig {
	public ExerciseService() {
		packages("org.punnoose.jersey.resource", "org.punnoose.jersey.filter");
		// register(EntityFilteringFeature.class);
		JacksonJsonProvider json = new JacksonJsonProvider().configure(
				WRITE_DATES_AS_TIMESTAMPS, false)
				.configure(INDENT_OUTPUT, true);
		JacksonXMLProvider xml = new JacksonXMLProvider().configure(
				INDENT_OUTPUT, true);
		register(json);
		register(xml);
		EncodingFilter.enableFor(this, GZipEncoder.class);
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
	}
}