package com.franglen.oracle.generator;

import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.function.LongPredicate;

import org.junit.Test;


/**
 * @author matthew
 *
 */
public class SpreadGeneratorTest {

	@Test
	public void testAbove() {
		int start = 0, range = 1000;
		SpreadGenerator generator = new SpreadGenerator(start);

		assertTrue(generator.stream(range).allMatch(isEqualOrAbove(start)));
	}

	@Test
	public void testBelow() {
		int start = 0, range = 1000;
		SpreadGenerator generator = new SpreadGenerator(start);

		generator.stream(range); // above
		assertTrue(generator.stream(range).allMatch(isEqualOrBelow(start)));
	}

	@Test
	public void testMultipleCalls() {
		int start = 0, above = start, below = start, range = 1000;
		SpreadGenerator generator = new SpreadGenerator(start);

		for (int i = 0;i < 10;i++) {
			assertTrue(generator.stream(range).allMatch(isEqualOrAbove(above)));
			assertTrue(generator.stream(range).allMatch(isEqualOrBelow(below)));

			above += range;
			below -= range;
		}
	}

	@Test
	public void testMultipleRandomRanges() {
		int start = 0, above = start, below = start, range = 1000;
		SpreadGenerator generator = new SpreadGenerator(start);
		Random random = new Random();

		for (int i = 0;i < 10;i++) {
			int aboveRange = random.nextInt(range), belowRange = random.nextInt(range);

			assertTrue(generator.stream(aboveRange).allMatch(isEqualOrAbove(above)));
			assertTrue(generator.stream(belowRange).allMatch(isEqualOrBelow(below)));

			above += aboveRange;
			below -= belowRange;
		}
	}

	public LongPredicate isEqualOrAbove(int start) {
		return (long value) -> value >= start;
	}

	public LongPredicate isEqualOrBelow(int start) {
		return (long value) -> value <= start;
	}
}
