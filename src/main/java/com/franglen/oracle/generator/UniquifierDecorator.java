package com.franglen.oracle.generator;

import java.util.List;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


/**
 * This wraps another generator and applies a set of uniquifiers to every value produced by it.
 * 
 * @author matthew
 */
public class UniquifierDecorator implements Generator {

	private final List<Long> uniquifiers;
	private final Generator original;

	public UniquifierDecorator(Generator original, int count) {
		this.original = original;
		uniquifiers = new SeedUniquifierGenerator().stream(count).mapToObj(Long::new).collect(Collectors.toList());
	}

	@Override
	public LongStream stream(long values) {
		long originalValues = values / uniquifiers.size();
		return original.stream(originalValues).flatMap(this::mapByUniquifier);
	}

	private LongStream mapByUniquifier(long value) {
		return uniquifiers.stream().mapToLong(Long::longValue).map(applyUniquifier(value));
	}

	private LongUnaryOperator applyUniquifier(long value) {
		return (long uniquifier) -> value ^ uniquifier;
	}
}
