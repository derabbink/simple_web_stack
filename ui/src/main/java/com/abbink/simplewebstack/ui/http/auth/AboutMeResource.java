package com.abbink.simplewebstack.ui.http.auth;

import static com.abbink.simplewebstack.ui.utils.Constants.BASE_PATH;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.abbink.simplewebstack.common.auth.aop.Auth;
import com.abbink.simplewebstack.common.auth.mechanisms.OptionalWebAuthenticationMechanism;
import com.sun.jersey.api.view.Viewable;

@Slf4j
@Singleton
@Path(BASE_PATH + "aboutme")
@Auth(OptionalWebAuthenticationMechanism.class)
@Produces(MediaType.TEXT_HTML)
public class AboutMeResource {
	
	@GET
	public Viewable get(
		@Context Subject subject,
		@Context UriInfo uriInfo
	) {
		log.trace("get");
		Map<String, Object> model = new HashMap<String, Object>();
		if (subject != null) {
			model.put("hasSubject", true);
			Object principal = subject.getPrincipal();
			model.put("principal", principal);
			if (principal != null) {
				model.put("principalType", principal.getClass());
			} else {
				model.put("principalType", null);
			}
			model.put("isAuthenticated", subject.isAuthenticated());
			model.put("isRemembered", subject.isRemembered());
			Session session = subject.getSession(false);
			if (session != null) {
				model.put("hasSession", true);
				model.put("sessionId", session.getId());
				model.put("sessionStartTime", session.getStartTimestamp());
				model.put("sessionLastAccessTime", session.getLastAccessTime());
				model.put("sessionTimeout", session.getTimeout());
			} else {
				model.put("hasSession", false);
			}
		}
		model.put("loginUri", uriInfo.getBaseUriBuilder().path(LoginResource.class).build());
		model.put("logoutUri", uriInfo.getBaseUriBuilder().path(LogoutResource.class).build());
		return new Viewable("/auth/about_me", model);
	}
	
}
