package com.franglen.oracle;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;

/**
 * @author matthew
 *
 */
public class SeedUniquifierGeneratorTest {

	private static final Long[] EXPECTED_VALUES = { 8006678197202707420L, -3282039941672302964L, 3620162808252824828L, 199880078823418412L,
			-358888042979226340L, -3027244073376649012L, 2753936029964524604L, -9114341766410567060L, -4556895898465471908L, 7145509263664170764L };

	@Test
	public void testValues() {
		SeedUniquifierGenerator generator = new SeedUniquifierGenerator();

		for (int i = 0; i < 10; i++) {
			assertEquals("test seed generator value " + i, EXPECTED_VALUES[i], generator.next());
		}
	}

	@Test
	public void testSeparation() {
		SeedUniquifierGenerator first, second;

		first = new SeedUniquifierGenerator();
		second = new SeedUniquifierGenerator();

		assertEquals("test seed generator separation", EXPECTED_VALUES[0], first.next());
		assertEquals("test seed generator separation", EXPECTED_VALUES[0], second.next());

		for (int i = 0; i < 10; i++) {
			assertEquals("test seed generator separation", first.next(), second.next());
		}
	}

	@Test
	public void testIterator() {
		Iterator<Long> generator = new SeedUniquifierGenerator().iterator();

		for (int i = 0; i < 10; i++) {
			assertEquals("test seed generator value " + i, EXPECTED_VALUES[i], generator.next());
		}
	}
}
