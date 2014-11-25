package com.franglen.oracle;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates the values that are used to salt the starting time for the Random
 * object.
 * 
 * @author matthew
 */
public class SeedUniquifierGenerator implements Iterable<Long>, Iterator<Long> {

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
	public Iterator<Long> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Long next() {
		for (;;) {
			long current = value.get();
			long next = current * SEED_UNIQUIFIER_FACTOR;
			if (value.compareAndSet(current, next))
				return next;
		}
	}
}
