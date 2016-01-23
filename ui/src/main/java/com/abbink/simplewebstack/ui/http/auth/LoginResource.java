package com.abbink.simplewebstack.ui.http.auth;

import static com.abbink.simplewebstack.ui.utils.Constants.BASE_PATH;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.abbink.simplewebstack.common.jersey.shiro.Auth;
import com.sun.jersey.api.view.Viewable;

// TODO change to custom path, once FormAuthenticationFilter setters are injected
@Path(BASE_PATH + "login")
@Produces(MediaType.TEXT_HTML)
public class LoginResource {
	
	@GET
	public Viewable getLogin(
		@Auth Subject subject
	) {
		if (subject.isAuthenticated()) {
//		if (false) {
			// TODO redirect to account settings instead
			return new Viewable("/auth/logged_in");
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("showFailure", false);
		model.put("username", "");
		return new Viewable("/auth/login_form", model);
	}
	
	@POST
	public Viewable postLogin(
		@Context HttpServletRequest request,
		// TODO use Guice-bound param name
		@FormParam("username") String username,
		// TODO use Guice-bound param name
		@FormParam("password") String password
	) {
		// TODO change attribute key
		Object attrib = request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if (attrib != null && attrib instanceof String) {
			forceLogout();
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("showFailure", true);
			model.put("username", username);
			return new Viewable("/auth/login_form", model);
		}
		
		// TODO implement redirect to index for logged in users
		// this should never happen, as the FormAuthenticationFilter redirects before getting here
		return new Viewable("/auth/logged_in");
	}
	
	/**
	 * Silently force-terminates the current session, should it exist.
	 */
	private void forceLogout() {
		// TODO
	}
	
}
