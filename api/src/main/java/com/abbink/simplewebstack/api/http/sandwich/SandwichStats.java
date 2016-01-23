package com.abbink.simplewebstack.api.http.sandwich;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.annotation.JsonProperty;

@Singleton
public class SandwichStats {
	private int sandwichesMade;
	private int gramsOfJam;
	private int gramsOfPeanutButter;
	private final Counter jamCounter;
	private final Counter pbCounter;
	
	@Inject
	SandwichStats(MetricRegistry metricRegistry) {
		metricRegistry.register(
			MetricRegistry.name(SandwichStats.class, "sandwich", "count"),
			new Gauge<Integer>() {
				public Integer getValue() {
					return sandwichesMade;
				}
			}
		);
		jamCounter = metricRegistry.counter(MetricRegistry.name(SandwichStats.class, "grams-of-jam"));
		pbCounter = metricRegistry.counter(MetricRegistry.name(SandwichStats.class, "grams-of-pb"));
	}
	
	synchronized void recordSandwich(Sandwich sandwich) {
		sandwichesMade++;
		gramsOfJam += sandwich.getGramsOfJam();
		gramsOfPeanutButter += sandwich.getGramsOfPeanutButter();
		
		jamCounter.inc(sandwich.getGramsOfJam());
		pbCounter.inc(sandwich.getGramsOfPeanutButter());
	}
	
	synchronized StatsSnapshot getStats() {
		return new StatsSnapshot(sandwichesMade, gramsOfJam, gramsOfPeanutButter);
	}
	
	public static class StatsSnapshot {
		private final int sandwichesMade;
		private final int gramsOfJam;
		private final int gramsOfPeanutButter;
		
		private StatsSnapshot(int sandwichesMade, int gramsOfJam, int gramsOfPeanutButter) {
			this.sandwichesMade = sandwichesMade;
			this.gramsOfJam = gramsOfJam;
			this.gramsOfPeanutButter = gramsOfPeanutButter;
		}
		
		@JsonProperty
		public int getSandwichesMade() {
			return sandwichesMade;
		}
		
		@JsonProperty
		public int getGramsOfJam() {
			return gramsOfJam;
		}
		
		@JsonProperty
		public int getGramsOfPeanutButter() {
			return gramsOfPeanutButter;
		}
	}
}
