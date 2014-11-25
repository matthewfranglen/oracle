package com.franglen.oracle.generator;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

/**
 * Generates the values that are used to salt the starting time for the Random
 * object.
 * 
 * @author matthew
 */
public class SeedUniquifierGenerator implements Generator {

	/**
	 * Copied from seedUniquifier initialization in java.util.Random
	 */
	private static final long SEED_UNIQUIFIER_INITIAL_VALUE = 8682522807148012L;
	/**
	 * Copied from seedUniquifier() method in java.util.Random
	 */
	private static final long SEED_UNIQUIFIER_FACTOR = 181783497276652981L;

	private final AtomicLong value;

	public SeedUniquifierGenerator() {
		value = new AtomicLong(SEED_UNIQUIFIER_INITIAL_VALUE);
	}

	@Override
	public LongStream stream(long values) {
		return LongStream.generate(this::next).limit(values);
	}

	private Long next() {
		for (;;) {
			long current = value.get();
			long next = current * SEED_UNIQUIFIER_FACTOR;
			if (value.compareAndSet(current, next))
				return next;
		}
	}
}
