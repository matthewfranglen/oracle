package com.franglen.oracle.generator;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * @author matthew
 *
 */
public class SeedUniquifierGeneratorTest {

	private static final long[] EXPECTED_VALUES = { 8006678197202707420L, -3282039941672302964L, 3620162808252824828L, 199880078823418412L,
			-358888042979226340L, -3027244073376649012L, 2753936029964524604L, -9114341766410567060L, -4556895898465471908L, 7145509263664170764L };

	@Test
	public void testValues() {
		SeedUniquifierGenerator generator = new SeedUniquifierGenerator();
		long[] values = generator.stream(EXPECTED_VALUES.length).toArray();

		assertArrayEquals(EXPECTED_VALUES, values);
	}

	@Test
	public void testSeparation() {
		long range = 100;
		SeedUniquifierGenerator first, second;
		long[] firstValues, secondValues;

		first = new SeedUniquifierGenerator();
		firstValues = first.stream(range).toArray();

		second = new SeedUniquifierGenerator();
		secondValues = second.stream(range).toArray();

		assertArrayEquals("test seed generator separation", firstValues, secondValues);
	}
}
