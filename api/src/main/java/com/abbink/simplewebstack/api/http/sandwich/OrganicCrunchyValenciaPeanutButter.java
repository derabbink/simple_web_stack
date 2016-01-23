package com.abbink.simplewebstack.api.http.sandwich;

public class OrganicCrunchyValenciaPeanutButter implements PeanutButter {
	public void applyToSandwich(Sandwich sandwich, int grams) {
		sandwich.addPeanutButter(grams);
	}
}
