package com.abbink.simplewebstack.common.auth.shiro;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;

public class BearerTokenSecurityManager implements SecurityManager {
	
	private BearerTokenRealm realm;
	private DefaultWebSubjectFactory subjectFactory;
	
	@Inject
	public BearerTokenSecurityManager(BearerTokenRealm realm, DefaultWebSubjectFactory subjectFactory) {
		this.realm = realm;
		this.subjectFactory = subjectFactory;
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
	@Override
	public Session start(SessionContext context) {
		throw new AuthorizationException("Sessions cannot be created for BearerToken-authorized requests.");
	}
	
	@Override
	public Session getSession(SessionKey key) throws SessionException {
		// always return null, since with BearerTokens there are no sessions
		return null;
	}
	// endregion SessionManager_methods
	
	// region SecurityManager_methods
	@Override
	public Subject login(Subject subject, AuthenticationToken authenticationToken) throws AuthenticationException {
		AuthenticationInfo info = authenticate(authenticationToken);
		Subject loggedIn = createSubject(authenticationToken, info, subject);
		
		return loggedIn;
	}
	
	@Override
	public void logout(Subject subject) {
		// for BearerTokens, this method invalidates a BearerToken
		realm.onLogout(subject.getPrincipals());
		// TODO actually invalidate token!
	}
	
	@Override
	public Subject createSubject(SubjectContext context) {
		if (context.getSecurityManager() == null) {
			context.setSecurityManager(this);
		}
		
		return subjectFactory.createSubject(context);
	}
	// region SecurityManager_methods
	
	// inspired by DefaultSecurityManager
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
}
