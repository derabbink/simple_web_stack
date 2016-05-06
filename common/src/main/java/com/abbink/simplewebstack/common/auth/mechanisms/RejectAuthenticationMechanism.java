package com.abbink.simplewebstack.common.auth.mechanisms;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import lombok.extern.java.Log;

import com.sun.jersey.spi.container.ContainerRequest;

@Log
public class RejectAuthenticationMechanism extends AuthenticationMechanism {
			
		@Override
		public ContainerRequest filter(ContainerRequest request) {
			log.info("Rejecting authentication for " + request.getMethod());
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
		
}
