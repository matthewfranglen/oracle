package com.franglen.oracle;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.LongStream;

import org.junit.Test;

/**
 * @author matthew
 *
 */
public class SeedCollectorTest {

	@Test
	public void testEmptyCollection() {
		LongStream emptyStream = LongStream.of();
		Collection<Long> result = emptyStream.collect(SeedCollector::supplier, SeedCollector::accumulator, SeedCollector::combiner);
		assertEquals(Collections.EMPTY_LIST, result);
	}

	@Test
	public void testSingleCollection() {
		long value = 10;
		LongStream singleStream = LongStream.of(value);
		Collection<Long> result = singleStream.collect(SeedCollector::supplier, SeedCollector::accumulator, SeedCollector::combiner);
		assertEquals(Arrays.asList(value), result);
	}

	@Test
	public void testSmallCollection() {
		long min = 0, max = 100, count = max - min, sum = expectedSumForRange(min, max);
		LongStream smallStream = LongStream.range(min, max);
		Collection<Long> result = smallStream.collect(SeedCollector::supplier, SeedCollector::accumulator, SeedCollector::combiner);
		assertEquals(count, result.size());
		assertEquals(sum, sumOf(result));
	}

	@Test
	public void testLargeCollection() {
		long min = 0, max = 100_001, count = max - min, sum = expectedSumForRange(min, max);
		LongStream largeStream = LongStream.range(min, max).parallel();
		Collection<Long> result = largeStream.collect(SeedCollector::supplier, SeedCollector::accumulator, SeedCollector::combiner);
		assertEquals(count, result.size());
		assertEquals(sum, sumOf(result));
	}

	private long expectedSumForRange(long minInclusive, long maxExclusive) {
		long maxInclusive = maxExclusive - 1;
		long count = maxExclusive - minInclusive;
		return ((maxInclusive + minInclusive) * count) / 2;
	}

	private long sumOf(Collection<Long> values) {
		return values.stream().mapToLong(Long::longValue).sum();
	}
}
