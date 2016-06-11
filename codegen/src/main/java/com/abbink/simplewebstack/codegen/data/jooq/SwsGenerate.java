package com.abbink.simplewebstack.codegen.data.jooq;

import org.jooq.util.jaxb.Generate;

public class SwsGenerate extends Generate {
	
	private static final long serialVersionUID = 1519729060837777724L;
	
	public SwsGenerate() {
//		setValidationAnnotations(true);
		setPojos(true);
		setPojosEqualsAndHashCode(true);
		setDaos(true);
	}
	
}
