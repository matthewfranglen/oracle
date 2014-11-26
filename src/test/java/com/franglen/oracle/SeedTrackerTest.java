package com.franglen.oracle;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.stream.LongStream;

import org.junit.Test;

import com.franglen.oracle.filter.Filter;
import com.franglen.oracle.generator.Generator;
import com.franglen.oracle.generator.LinearGenerator;


/**
 * @author matthew
 *
 */
public class SeedTrackerTest {

	private static final Generator EMPTY_GENERATOR = new Generator() {
		public LongStream stream(long values) {
			return LongStream.of();
		}
	};

	private static final Generator NON_EMPTY_GENERATOR = new LinearGenerator(0);

	private static final Filter PERMIT_ALL_FILTER = new Filter() {
		public boolean accepts(Random value) {
			return true;
		}
	};

	private static final Filter PERMIT_NOTHING_FILTER = new Filter() {
		public boolean accepts(Random value) {
			return false;
		}
	};

	private static final long ITERATION_SIZE = 1000;

	@Test
	public void testInitialState() {
		SeedTracker tracker = new SeedTracker(NON_EMPTY_GENERATOR);

		assertEquals(0, tracker.size());
	}

	@Test
	public void testEmptyGenerator() {
		SeedTracker tracker = new SeedTracker(EMPTY_GENERATOR);

		tracker.addFilter(PERMIT_ALL_FILTER);
		tracker.setIterationSize(ITERATION_SIZE);
		tracker.iterate();

		assertEquals(0, tracker.size());
	}

	@Test
	public void testSingleMatchingValue() {
		SeedTracker tracker = new SeedTracker(NON_EMPTY_GENERATOR);

		tracker.addFilter(PERMIT_ALL_FILTER);
		tracker.setIterationSize(1);
		tracker.iterate();

		assertEquals(1, tracker.size());
	}

	@Test
	public void testSingleFailingValue() {
		SeedTracker tracker = new SeedTracker(NON_EMPTY_GENERATOR);

		tracker.addFilter(PERMIT_NOTHING_FILTER);
		tracker.setIterationSize(1);
		tracker.iterate();

		assertEquals(0, tracker.size());
	}

	@Test
	public void testManyMatchingValues() {
		SeedTracker tracker = new SeedTracker(NON_EMPTY_GENERATOR);

		tracker.addFilter(PERMIT_ALL_FILTER);
		tracker.setIterationSize(ITERATION_SIZE);
		tracker.iterate();

		assertEquals(ITERATION_SIZE, tracker.size());
	}

	@Test
	public void testManyFailingValues() {
		SeedTracker tracker = new SeedTracker(EMPTY_GENERATOR);

		tracker.addFilter(PERMIT_NOTHING_FILTER);
		tracker.setIterationSize(ITERATION_SIZE);
		tracker.iterate();

		assertEquals(0, tracker.size());
	}

	@Test
	public void testManyIterations() {
		long count = 10, expectedSize = 10 * ITERATION_SIZE;
		SeedTracker tracker = new SeedTracker(NON_EMPTY_GENERATOR);

		tracker.addFilter(PERMIT_ALL_FILTER);
		tracker.setIterationSize(ITERATION_SIZE);

		for (int i = 0;i < count;i++) {
			tracker.iterate();
		}

		assertEquals(expectedSize, tracker.size());
	}
}
