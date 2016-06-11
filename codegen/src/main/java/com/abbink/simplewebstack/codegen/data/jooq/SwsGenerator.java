package com.abbink.simplewebstack.codegen.data.jooq;

import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.JavaGenerator;

public class SwsGenerator extends JavaGenerator {
	
	public SwsGenerator() {
		setStrategy(new DefaultGeneratorStrategy());
	}

}
