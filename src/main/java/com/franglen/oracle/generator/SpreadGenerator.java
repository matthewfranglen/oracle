package com.franglen.oracle.generator;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;


/**
 * Generates values by flipping between the range before and the range after the start.
 * 
 * @author matthew
 */
public class SpreadGenerator implements Generator {

	private final AtomicLong above, below;
	private final AtomicBoolean isAboveNext;

	public SpreadGenerator(long start) {
		above = new AtomicLong(start);
		below = new AtomicLong(start);
		isAboveNext = new AtomicBoolean(true);
	}

	@Override
	public LongStream stream(long values) {
		return getAndToggleIsAboveNext() ? streamAbove(values) : streamBelow(values);
	}

	private boolean getAndToggleIsAboveNext() {
		boolean result;

		do {
			result = isAboveNext.get();
		} while (!isAboveNext.compareAndSet(result, !result));

		return result;
	}

	private LongStream streamAbove(long values) {
		long start = this.above.getAndAdd(values);

		return LongStream.range(start, start + values);
	}

	private LongStream streamBelow(long values) {
		long start = this.below.getAndAdd(-values);

		return LongStream.range(start - values, start);
	}
}
