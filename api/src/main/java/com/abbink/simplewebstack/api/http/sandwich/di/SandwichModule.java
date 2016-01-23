package com.abbink.simplewebstack.api.http.sandwich.di;

import com.abbink.simplewebstack.api.http.sandwich.OrganicCrunchyValenciaPeanutButter;
import com.abbink.simplewebstack.api.http.sandwich.PeanutButter;
import com.abbink.simplewebstack.api.http.sandwich.SandwichMaker;
import com.abbink.simplewebstack.api.http.sandwich.SandwichMakerResource;
import com.abbink.simplewebstack.api.http.sandwich.SandwichStats;
import com.abbink.simplewebstack.api.http.sandwich.SandwichStatsResource;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class SandwichModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(PeanutButter.class).to(OrganicCrunchyValenciaPeanutButter.class).in(Scopes.SINGLETON);
		
		bind(SandwichMaker.class);
		bind(SandwichStats.class);
		
		bind(SandwichStatsResource.class);
		bind(SandwichMakerResource.class);
	}
	
}
