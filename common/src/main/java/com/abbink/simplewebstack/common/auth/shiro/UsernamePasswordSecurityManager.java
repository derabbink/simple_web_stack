package com.abbink.simplewebstack.common.auth.shiro;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.session.HttpServletSession;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;

/**
 * SecurityManager in charge of handling authentication for username & password authentication
 */
public class UsernamePasswordSecurityManager implements SecurityManager {
	
	private UsernamePasswordRealm realm;
	private SubjectFactory subjectFactory;
	//private RememberMeManager rememberMeManager;
	
	@Inject
	public UsernamePasswordSecurityManager(
		UsernamePasswordRealm realm,
		DefaultWebSubjectFactory subjectFactory//,
		//WebRememberMeManager rememberMeManager
	) {
		this.realm = realm;
		this.subjectFactory = subjectFactory;
		//this.rememberMeManager = rememberMeManager;
	}
	
	// region Authenticator_methods
	@Override
	public AuthenticationInfo authenticate(AuthenticationToken token) throws AuthenticationException {
		if (!realm.supports(token)) {
			throw new AuthenticationException("AuthenticationToken of type " + token.getClass() +
				"could not be authenticated by " + realm.getClass());
		}
		
		return realm.getAuthenticationInfo(token);
	}
	// endregion Authenticator_methods
	
	// region Authorizer_methods
	@Override
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		// only implemented in ModularRealmAuthorizer for realms that implement Authorizer
		return false;
	}
	
	@Override
	public boolean isPermitted(PrincipalCollection subjectPrincipal, Permission permission) {
		// only implemented in ModularRealmAuthorizer for realms that implement Authorizer
		return false;
	}
	
	@Override
	public boolean[] isPermitted(PrincipalCollection subjectPrincipal, String... permissions) {
		// only implemented in ModularRealmAuthorizer for realms that implement Authorizer
		if (permissions == null) {
			return new boolean[0];
		}
		return new boolean[permissions.length];
	}
	
	@Override
	public boolean[] isPermitted(PrincipalCollection subjectPrincipal, List<Permission> permissions) {
		// only implemented in ModularRealmAuthorizer for realms that implement Authorizer
		if (permissions == null) {
			return new boolean[0];
		}
		return new boolean[permissions.size()];
	}
	
	@Override
	public boolean isPermittedAll(PrincipalCollection subjectPrincipal, String... permissions) {
		// only implemented in ModularRealmAuthorizer for realms that implement Authorizer
		return false;
	}
	
	@Override
	public boolean isPermittedAll(PrincipalCollection subjectPrincipal, Collection<Permission> permissions) {
		// only implemented in ModularRealmAuthorizer for realms that implement Authorizer
		return false;
	}
	
	@Override
	public void checkPermission(PrincipalCollection subjectPrincipal, String permission) throws AuthorizationException {
		if (!isPermitted(subjectPrincipal, permission)) {
			throw new UnauthorizedException("Subject does not have permission [" + permission + "]");
		}
	}
	
	@Override
	public void checkPermission(PrincipalCollection subjectPrincipal, Permission permission) throws AuthorizationException {
		if (!isPermitted(subjectPrincipal, permission)) {
			throw new UnauthorizedException("Subject does not have permission [" + permission + "]");
		}
	}
	
	@Override
	public void checkPermissions(PrincipalCollection subjectPrincipal, String... permissions) throws AuthorizationException {
		if (permissions != null && permissions.length > 0) {
			for (String perm : permissions) {
				checkPermission(subjectPrincipal, perm);
			}
		}
	}
	
	@Override
	public void checkPermissions(PrincipalCollection subjectPrincipal, Collection<Permission> permissions) throws AuthorizationException {
		if (permissions != null) {
			for (Permission permission : permissions) {
				checkPermission(subjectPrincipal, permission);
			}
		}
	}
	
	@Override
	public boolean hasRole(PrincipalCollection subjectPrincipal, String roleIdentifier) {
		// only implemented in ModularRealmAuthorizer for realms that implement Authorizer
		return false;
	}
	
	@Override
	public boolean[] hasRoles(PrincipalCollection subjectPrincipal, List<String> roleIdentifiers) {
		// only implemented in ModularRealmAuthorizer for realms that implement Authorizer
		if (roleIdentifiers == null) {
			return new boolean[0];
		}
		return new boolean[roleIdentifiers.size()];
	}
	
	@Override
	public boolean hasAllRoles(PrincipalCollection subjectPrincipal, Collection<String> roleIdentifiers) {
		for (String roleIdentifier : roleIdentifiers) {
			if (!hasRole(subjectPrincipal, roleIdentifier)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void checkRole(PrincipalCollection subjectPrincipal, String roleIdentifier) throws AuthorizationException {
		if (!hasRole(subjectPrincipal, roleIdentifier)) {
			throw new UnauthorizedException("Subject does not have role [" + roleIdentifier + "]");
		}
	}
	
	@Override
	public void checkRoles(PrincipalCollection subjectPrincipal, Collection<String> roleIdentifiers) throws AuthorizationException {
		if (roleIdentifiers != null) {
			for (String role : roleIdentifiers) {
				checkRole(subjectPrincipal, role);
			}
		}
	}
	
	@Override
	public void checkRoles(PrincipalCollection subjectPrincipal, String... roleIdentifiers) throws AuthorizationException {
		if (roleIdentifiers != null) {
			for (String role : roleIdentifiers) {
				checkRole(subjectPrincipal, role);
			}
		}
	}
	// endregion Authorizer_methods
	
	// region SessionManager_methods
	/**
	 * Inspired by shiro's ServletContainerSessionManager.start()
	 * @param context must be a WebSessionContext instance.
	 *   Its getHost() must return the request's host, and its getServletRequest() must return a HttpServletRequest.
	 */
	@Override
	public Session start(SessionContext context) {
		if (!(context instanceof WebSessionContext)) {
			throw new IllegalArgumentException("context must be a WebSessionContext");
		}
		WebSessionContext webContext = (WebSessionContext)context;
		HttpServletRequest request = (HttpServletRequest)webContext.getServletRequest();
		
		return new HttpServletSession(request.getSession(), webContext.getHost());
	}
	
	/**
	 * Inspired by shiro's ServletContainerSessionManager.getSession()
	 * This is extremely hacky, but this is on shiro: SessionContext has a getSessionId(),
	 * but doesn't implement SessionKey
	 * @param context must be a WebSessionContext instance.
	 *   Its getServletRequest() must return a HttpServletRequest.
	 */
	@Override
	public Session getSession(SessionKey key) throws SessionException {
		if (!(key instanceof WebSessionContext)) {
			throw new IllegalArgumentException("key must be a WebSessionContext");
		}
		WebSessionContext webContext = (WebSessionContext)key;
		webContext.getSessionId();
		HttpServletRequest request = (HttpServletRequest)webContext.getServletRequest();
		
		Session session = null;
		HttpSession httpSession = request.getSession(false);
		
		if (httpSession != null) {
			session = new HttpServletSession(httpSession, request.getRemoteHost());
		}
		return session;
	}
	// endregion SessionManager_methods
	
	// region SecurityManager_methods
	@Override
	public Subject login(Subject subject, AuthenticationToken authenticationToken) throws AuthenticationException {
		try {
			AuthenticationInfo info = authenticate(authenticationToken);
			Subject loggedIn = createSubject(authenticationToken, info, subject);
			onSuccessfulLogin(authenticationToken, info, loggedIn);
			return loggedIn;
		} catch (AuthenticationException ae) {
			try {
				onFailedLogin(authenticationToken, ae, subject);
			} finally {
				throw ae;
			}
		}
	}
	
	@Override
	public void logout(Subject subject) {
		beforeLogout(subject);
		
		PrincipalCollection principals = subject.getPrincipals();
		try {
			delete(subject);
		} finally {
			stopSession(subject);
		}
	}
	
	@Override
	public Subject createSubject(SubjectContext context) {
		context = new DefaultSubjectContext(context);
		if (context.getSecurityManager() == null) {
			context.setSecurityManager(this);
		}
		if (context.resolveSession() == null) {
			SessionKey sessionKey = new DefaultSessionKey(context.getSessionId());
			context.setSession(getSession(sessionKey));
		}
		// resolveprincipals
		return subjectFactory.createSubject(context);
	}
	// endregion SecurityManager_methods
	
	// region inspired_by_DefaultSecurityManager
	protected Subject createSubject(AuthenticationToken token, AuthenticationInfo info, Subject existing) {
		WebSubjectContext context = new DefaultWebSubjectContext();
		context.setAuthenticated(true);
		context.setAuthenticationToken(token);
		context.setAuthenticationInfo(info);
		if (existing != null) {
			context.setSubject(existing);
		}
		return createSubject(context);
	}
	
	protected void delete(Subject subject) {
		// realm.delete(subject);
	}
	
	protected void stopSession(Subject subject) {
		Session s = subject.getSession(false);
		if (s != null) {
			s.stop();
		}
	}
	
	protected void onSuccessfulLogin(AuthenticationToken token, AuthenticationInfo info, Subject subject) {
//		rememberMeManager.onSuccessfulLogin(subject, token, info);
	}
	
	protected void onFailedLogin(AuthenticationToken token, AuthenticationException ae, Subject subject) {
//		rememberMeManager.onFailedLogin(subject, token, ae);
	}
	
	protected void beforeLogout(Subject subject) {
//		rememberMeManager.onLogout(subject);
	}
	// endregion inspired_by_DefaultSecurityManager
	
	

}
