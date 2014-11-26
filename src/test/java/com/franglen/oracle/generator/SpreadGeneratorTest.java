package com.franglen.oracle.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.function.LongPredicate;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.Stream;

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

	@Test
	public void testContendedCallsCount() {
		long count = 10_000, length = 1;
		LongStream contendedStream = createContendedSpreadGeneratorStream(count, length);

		assertEquals(count * length, contendedStream.count());
	}

	@Test
	public void testContendedCallsMinimum() {
		long count = 10_000, length = 1, minimum = -count / 2;
		LongStream contendedStream = createContendedSpreadGeneratorStream(count, length);

		assertEquals(count * length, contendedStream.filter(isEqualOrAbove(minimum)).count());
	}

	@Test
	public void testContendedCallsMaximum() {
		long count = 10_000, length = 1, maximum = count / 2;
		LongStream contendedStream = createContendedSpreadGeneratorStream(count, length);

		assertEquals(count * length, contendedStream.filter(isEqualOrBelow(maximum)).count());
	}

	@Test
	public void testContendedCallsTotal() {
		long count = 10_000, length = 1;
		LongStream contendedStream = createContendedSpreadGeneratorStream(count, length);
		assertEquals(0, contendedStream.sum());
	}

	private LongPredicate isEqualOrAbove(long start) {
		return (long value) -> value >= start;
	}

	private LongPredicate isEqualOrBelow(long start) {
		return (long value) -> value <= start;
	}

	private LongStream createContendedSpreadGeneratorStream(long streamCount, long streamLength) {
		SpreadGenerator generator = new SpreadGenerator(0);
		Stream<LongStream> stream = Stream.generate(createStreamGenerator(generator, streamLength));
		return stream.limit(streamCount).parallel().flatMapToLong((LongStream v) -> v);
	}

	private Supplier<LongStream> createStreamGenerator(SpreadGenerator generator, long length) {
		return () -> {
			return generator.stream(length);
		};
	}
}
