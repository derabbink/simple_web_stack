package com.abbink.simplewebstack.api.http.sandwich;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sandwich {
	private int gramsOfPeanutButter;
	private int gramsOfJam;
	
	void addPeanutButter(int grams) {
		gramsOfPeanutButter += grams;
	}
	
	void addJam(int grams) {
		gramsOfJam += grams;
	}
	
	@JsonProperty
	public int getGramsOfPeanutButter() {
		return gramsOfPeanutButter;
	}
	
	@JsonProperty
	public int getGramsOfJam() {
		return gramsOfJam;
	}
}
