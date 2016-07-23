package com.abbink.simplewebstack.common.auth.shiro.authtokens;

import org.apache.shiro.authc.AuthenticationToken;

import com.abbink.simplewebstack.common.auth.shiro.WebAuthenticationRealm;

/**
 * A base class that is understood by the {@link WebAuthenticationRealm}
 */
public abstract class WebAuthenticationToken implements AuthenticationToken {

}
