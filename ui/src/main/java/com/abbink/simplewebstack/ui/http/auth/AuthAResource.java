package com.abbink.simplewebstack.ui.http.auth;

import static com.abbink.simplewebstack.ui.utils.Constants.BASE_PATH;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import com.sun.jersey.api.view.Viewable;

@Slf4j
@Path(BASE_PATH + "auth/a")
@Produces(MediaType.TEXT_HTML)
public class AuthAResource {
	
	@GET
	public Viewable getAuthA() {
		log.debug("auth A response");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("foo", "a");
		return new Viewable("/auth", model);
	}
	
}
