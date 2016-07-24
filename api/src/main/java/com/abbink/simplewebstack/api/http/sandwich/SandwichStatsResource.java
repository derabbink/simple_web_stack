package com.abbink.simplewebstack.api.http.sandwich;

import static com.abbink.simplewebstack.api.utils.Constants.BASE_PATH;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.subject.Subject;

import com.abbink.simplewebstack.common.auth.aop.Auth;
import com.abbink.simplewebstack.common.auth.mechanisms.BearerTokenAuthenticationMechanism;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path(BASE_PATH + "sandwich/stats")
@Auth(BearerTokenAuthenticationMechanism.class)
public class SandwichStatsResource {
	private final SandwichStats sandwichStats;
	
	@Inject
	SandwichStatsResource(SandwichStats sandwichStats) {
		this.sandwichStats = sandwichStats;
	}
	
	@GET
	public SandwichStats.StatsSnapshot getStats(@Context Subject sub) {
		return sandwichStats.getStats();
	}
}
