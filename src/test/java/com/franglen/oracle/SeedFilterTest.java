package com.franglen.oracle;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import com.franglen.oracle.filter.DoubleFilter;
import com.franglen.oracle.filter.IntFilter;

/**
 * @author matthew
 *
 */
public class SeedFilterTest {

	/**
	 * Copied from multiplier constant in java.util.Random
	 */
	private static final long SEED_MULTIPLIER = 0x5DEECE66DL;
	/**
	 * Generates a seed with zero bits set after initial tampering.
	 */
	private static final long GOOD_STARTING_SEED = SEED_MULTIPLIER;
	/**
	 * Generates a seed with every significant bit set after initial tampering.
	 */
	private static final long BAD_STARTING_SEED = ((1L << 48) - 1) ^ SEED_MULTIPLIER;

	@Test
	public void testEmptyFilter() {
		SeedFilter filter = new SeedFilter();

		assertTrue(filter.accepts(GOOD_STARTING_SEED));
		assertTrue(filter.accepts(BAD_STARTING_SEED));
	}

	@Test
	public void testSingleFilter() {
		SeedFilter filter = new SeedFilter();
		int initialValue = new Random(GOOD_STARTING_SEED).nextInt();

		filter.addFilter(new IntFilter(initialValue));

		assertTrue(filter.accepts(GOOD_STARTING_SEED));
		assertFalse(filter.accepts(BAD_STARTING_SEED));
	}

	@Test
	public void testMultipleFilters() {
		SeedFilter filter = new SeedFilter();
		Random generator = new Random(GOOD_STARTING_SEED);

		for (int i = 0; i < 10; i++) {
			filter.addFilter(new IntFilter(generator.nextInt()));
		}

		assertTrue(filter.accepts(GOOD_STARTING_SEED));
		assertFalse(filter.accepts(BAD_STARTING_SEED));
	}

	public void testMultipleDifferentFilters() {
		SeedFilter filter = new SeedFilter();
		Random generator = new Random(GOOD_STARTING_SEED);

		for (int i = 0; i < 10; i++) {
			filter.addFilter(new IntFilter(generator.nextInt()));
			filter.addFilter(new DoubleFilter(generator.nextDouble()));
		}

		assertTrue(filter.accepts(GOOD_STARTING_SEED));
		assertFalse(filter.accepts(BAD_STARTING_SEED));
	}
}
