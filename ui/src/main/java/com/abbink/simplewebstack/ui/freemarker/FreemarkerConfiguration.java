package com.abbink.simplewebstack.ui.freemarker;

import com.abbink.simplewebstack.ui.templates.TemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public class FreemarkerConfiguration extends Configuration {
	
	public FreemarkerConfiguration() {
		setObjectWrapper(new DefaultObjectWrapper());
		// TODO find a way to inject this class
		setClassForTemplateLoading(TemplateLoader.class, "");
	}
	
}
