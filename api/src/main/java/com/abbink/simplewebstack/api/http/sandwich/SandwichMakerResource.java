package com.abbink.simplewebstack.api.http.sandwich;

import static com.abbink.simplewebstack.api.utils.Constants.BASE_PATH;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path(BASE_PATH + "sandwich/create")
@Produces(MediaType.APPLICATION_JSON)
public class SandwichMakerResource {
	private final SandwichMaker sandwichMaker;
	private final SandwichStats sandwichStats;
	
	@Inject
	SandwichMakerResource(SandwichMaker sandwichMaker, SandwichStats sandwichStats) {
		this.sandwichMaker = sandwichMaker;
		this.sandwichStats = sandwichStats;
	}
	
	@GET
	public Sandwich makeSandwich(
		@QueryParam("jam") @DefaultValue("100") int gramsOfJam,
		@QueryParam("peanutButter") @DefaultValue("200") int gramsOfPeanutButter
	) {
		Sandwich sandwich = sandwichMaker.makeSandwich(gramsOfPeanutButter, gramsOfJam);
		sandwichStats.recordSandwich(sandwich);
		
		return sandwich;
	}
}
