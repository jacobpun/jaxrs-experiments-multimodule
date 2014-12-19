package org.punnoose.jersey.filter;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.MDC;

@Provider
/**
 * This filter class creates a unique request-id for each incoming request and add it to Mapped Diagnostic Context.
 * @author "Punnoose Pullolickal"
 *
 */
public class RequestIDGeneratorFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		MDC.put("request-id", "ES-" + UUID.randomUUID());
	}
}