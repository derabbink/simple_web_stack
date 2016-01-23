package com.abbink.simplewebstack.api.http.sandwich;

import javax.inject.Inject;

public class SandwichMaker {
	private final PeanutButter peanutButter;
	
	@Inject
	public SandwichMaker(PeanutButter peanutButter) {
		this.peanutButter = peanutButter;
	}
	
	Sandwich makeSandwich(int gramsOfPeanutButter, int gramsOfJam) {
		Sandwich sandwich = new Sandwich();
		peanutButter.applyToSandwich(sandwich, gramsOfPeanutButter);
		
		// let's not overcomplicate things... jam is easy
		sandwich.addJam(gramsOfJam);
		return sandwich;
	}
}
