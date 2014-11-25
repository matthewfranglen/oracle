package com.franglen.oracle.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

/**
 * @author matthew
 *
 */
public class IntFilterTest {

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
	public void testAccept() {
		int expected = new Random(GOOD_STARTING_SEED).nextInt();
		IntFilter filter = new IntFilter(expected);

		assertTrue(filter.accepts(new Random(GOOD_STARTING_SEED)));
	}

	@Test
	public void testReject() {
		int expected = new Random(GOOD_STARTING_SEED).nextInt();
		IntFilter filter = new IntFilter(expected);

		assertFalse(filter.accepts(new Random(BAD_STARTING_SEED)));
	}
}
