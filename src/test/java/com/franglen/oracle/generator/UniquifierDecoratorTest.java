package com.franglen.oracle.generator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;


/**
 * @author matthew
 *
 */
public class UniquifierDecoratorTest {

	@Test
	public void testValues() {
		long start = 0, range = 1000, last = start;
		UniquifierDecorator generator = new UniquifierDecorator(new LinearGenerator(start), 10);
		List<Long> values = generator.stream(range).mapToObj(Long::new).collect(Collectors.toList());

		for (Long current : values) {
			assertFalse(Math.abs(current - last) < 1000);
		}
	}

	@Test
	public void testRangeRespected() {
		long start = 0, range = 1000;
		UniquifierDecorator generator = new UniquifierDecorator(new LinearGenerator(start), 10);
		long count = generator.stream(range).count();

		assertTrue(count <= range);
	}
}
