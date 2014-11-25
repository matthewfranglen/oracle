package com.franglen.oracle.generator;

import static org.junit.Assert.assertTrue;

import java.util.function.LongPredicate;

import org.junit.Test;

/**
 * @author matthew
 *
 */
public class LinearGeneratorTest {

	@Test
	public void testStream() {
		long start = 0, range = 1000, end = start + range;
		LinearGenerator generator = new LinearGenerator(start);

		assertTrue(generator.stream(range).allMatch(isInRange(start, end)));
	}

	@Test
	public void testRepeatedStreams() {
		long initial = 0, range = 1000, iterations = 10, start = initial + (range * iterations), end = start + range;
		LinearGenerator generator = new LinearGenerator(initial);

		for (int i = 0; i < iterations; i++) {
			generator.stream(range);
		}

		assertTrue(generator.stream(range).allMatch(isInRange(start, end)));
	}

	private LongPredicate isInRange(long startInclusive, long endExclusive) {
		return (long value) -> value >= startInclusive && value < endExclusive;
	}
}
