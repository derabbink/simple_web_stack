package com.abbink.simplewebstack.ui.freemarker;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.spi.template.ViewProcessor;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Provider
public class FreemarkerViewProcessor implements ViewProcessor<String> {
	
	@Inject
	private Configuration configuration;
	
	public String resolve(String path) {
		if (!path.endsWith(".ftl")) {
			path = path + ".ftl";
		}
		
		return path;
	}

	public void writeTo(String resolvedPath, Viewable viewable, OutputStream out) throws IOException {
		// Commit the status and headers to the HttpServletResponse
		out.flush();
		
		final Template template = configuration.getTemplate(resolvedPath);
		
		try {
			template.process(viewable.getModel(), new OutputStreamWriter(out));
		} catch(TemplateException te) {
			throw new ContainerException(te);
		}
	}
	
}
