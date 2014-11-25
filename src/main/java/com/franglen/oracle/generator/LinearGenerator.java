package com.franglen.oracle.generator;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;


/**
 * Generates values starting from an initial value.
 * 
 * @author matthew
 */
public class LinearGenerator implements Generator {

	private final AtomicLong start;

	public LinearGenerator(long start) {
		this.start = new AtomicLong(start);
	}

	@Override
	public LongStream stream(long values) {
		long start = this.start.getAndAdd(values);

		return LongStream.range(start, start + values);
	}
}
