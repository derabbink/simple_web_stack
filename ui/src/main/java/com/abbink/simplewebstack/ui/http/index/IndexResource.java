package com.abbink.simplewebstack.ui.http.index;

import static com.abbink.simplewebstack.ui.utils.Constants.BASE_PATH;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import com.sun.jersey.api.view.Viewable;

@Slf4j
@Path(BASE_PATH)
@Produces(MediaType.TEXT_HTML)
public class IndexResource {
	
	@GET
	public Viewable getIndex() {
		log.debug("index response");
		return new Viewable("/index");
	}
	
}
