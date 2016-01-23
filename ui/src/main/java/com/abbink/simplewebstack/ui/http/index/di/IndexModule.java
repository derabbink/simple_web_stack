package com.abbink.simplewebstack.ui.http.index.di;

import com.abbink.simplewebstack.ui.http.index.IndexResource;
import com.google.inject.AbstractModule;

public class IndexModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(IndexResource.class);
	}
}
