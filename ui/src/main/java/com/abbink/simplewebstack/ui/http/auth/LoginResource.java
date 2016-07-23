package com.abbink.simplewebstack.ui.http.auth;

import static com.abbink.simplewebstack.ui.utils.Constants.BASE_PATH;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import lombok.extern.java.Log;

import org.apache.shiro.subject.Subject;

import com.abbink.simplewebstack.common.auth.aop.Auth;
import com.abbink.simplewebstack.common.auth.mechanisms.AnonymousAuthenticationMechanism2;
import com.abbink.simplewebstack.common.auth.service.WebLoginService2;
import com.abbink.simplewebstack.common.http.Redirect;
import com.sun.jersey.api.view.Viewable;

@Log
@Path(BASE_PATH + "login")
@Auth(AnonymousAuthenticationMechanism2.class)
@Produces(MediaType.TEXT_HTML)
public class LoginResource {
	private WebLoginService2 loginService;
	
	@Inject
	public LoginResource(WebLoginService2 loginService) {
		this.loginService = loginService;
	}
	
	@GET
	public Viewable get(
		@Context Subject subject,
		@Context UriInfo uriInfo
	) {
		if (subject.isAuthenticated() || subject.isRemembered()) {
			throw new Redirect(uriInfo.getBaseUriBuilder().path(AboutMeResource.class).build());
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("showFailure", false);
		model.put("username", "");
		return new Viewable("/auth/login_form", model);
	}
	
	@POST
	public Viewable post(
		@Context UriInfo uriInfo,
		@Context HttpServletRequest request,
		@Context HttpServletResponse response,
		@FormParam("username") String username,
		@FormParam("password") String password
	) {
		if (!loginService.executeLogin(username, password, request, response)) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("showFailure", true);
			model.put("username", username);
			return new Viewable("/auth/login_form", model);
		}
		
		throw new Redirect(uriInfo.getBaseUriBuilder().path(AboutMeResource.class).build());
	}
}
