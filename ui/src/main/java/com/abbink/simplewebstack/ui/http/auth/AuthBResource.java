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
@Path(BASE_PATH + "auth/b")
@Produces(MediaType.TEXT_HTML)
public class AuthBResource {
	
	@GET
	public Viewable getAuthA() {
		log.debug("auth B response");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("foo", "b");
		return new Viewable("/auth", model);
	}
	
}
