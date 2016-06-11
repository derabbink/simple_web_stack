package com.abbink.simplewebstack.codegen.data.jooq.di;

import javax.inject.Named;

import org.jooq.util.h2.H2Database;
import org.jooq.util.jaxb.Configuration;
import org.jooq.util.jaxb.Database;
import org.jooq.util.jaxb.Generate;
import org.jooq.util.jaxb.Generator;
import org.jooq.util.jaxb.Target;

import com.abbink.simplewebstack.codegen.data.jooq.SwsGenerate;
import com.abbink.simplewebstack.codegen.data.jooq.SwsGenerator;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class JooqModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(Generate.class).to(SwsGenerate.class);
		bindConstant().annotatedWith(Names.named("GeneratorName")).to(SwsGenerator.class.getCanonicalName());
	}
	
	@Provides
	public Configuration provideConfiguration(Generator generator) {
		return new Configuration().withGenerator(generator);
	}
	
	@Provides
	public Generator provideGenerator(
		Generate generate,
		@Named("GeneratorName") String generatorName
	) {
		return new Generator()
			.withGenerate(generate)
			.withName(generatorName)
			.withDatabase(new Database()
				.withName(H2Database.class.getCanonicalName())
				.withIncludes(".*")
				.withExcludes("")
				.withInputSchema("PUBLIC")
			)
			.withTarget(new Target()
				.withPackageName("com.abbink.simplewebstack.common.data.generated")
				.withDirectory("../common/src/main/java")
			);
	}
}
