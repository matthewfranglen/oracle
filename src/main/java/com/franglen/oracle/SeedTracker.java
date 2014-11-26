package com.franglen.oracle;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.franglen.oracle.filter.Filter;
import com.franglen.oracle.generator.Generator;

/**
 * Generates, filters, and collects the seeds which pass.
 * 
 * @author matthew
 */
public class SeedTracker {

	private static final long DEFAULT_ITERATION_SIZE = 1_000_000;

	private final Generator generator;
	private final SeedFilter filter;
	private long iterationSize;
	private Collection<Long> validSeeds;

	public SeedTracker(Generator generator) {
		this.generator = generator;
		filter = new SeedFilter();
		iterationSize = DEFAULT_ITERATION_SIZE;
		validSeeds = Collections.emptyList();
	}

	public void addFilter(Filter filter) {
		this.filter.addFilter(filter);
	}

	public void setIterationSize(long iterationSize) {
		this.iterationSize = iterationSize;
	}

	public long size() {
		return validSeeds.size();
	}

	public void iterate() {
		Stream<Long> combinedSeedStream = Stream.concat(validateExistingSeeds(), validateNewSeeds());
		validSeeds = combinedSeedStream.collect(Collectors.toList());
	}

	private Stream<Long> validateExistingSeeds() {
		return validSeeds.stream().parallel().filter(filter::accepts);
	}

	private Stream<Long> validateNewSeeds() {
		return generator.stream(iterationSize).parallel().mapToObj(Long::new).filter(filter::accepts);
	}
}
