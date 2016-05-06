package com.abbink.simplewebstack.api.http.sandwich;

import static com.abbink.simplewebstack.api.utils.Constants.BASE_PATH;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.extern.java.Log;

import com.abbink.simplewebstack.common.aop.Auth;
import com.abbink.simplewebstack.common.auth.mechanisms.AnonymousAuthenticationMechanism;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path(BASE_PATH + "sandwich/stats")
@Auth(AnonymousAuthenticationMechanism.class)
@Log
public class SandwichStatsResource {
	private final SandwichStats sandwichStats;
	
	@Inject
	SandwichStatsResource(SandwichStats sandwichStats) {
		this.sandwichStats = sandwichStats;
	}
	
	@GET
	public SandwichStats.StatsSnapshot getStats() {
		doWhatever();
		return sandwichStats.getStats();
	}
	
	//@Auth
	protected void doWhatever() {
		
	}
}
