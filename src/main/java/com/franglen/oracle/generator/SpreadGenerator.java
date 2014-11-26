package com.franglen.oracle.generator;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.LongStream;

/**
 * Generates values by flipping between the range before and the range after the
 * start.
 * 
 * @author matthew
 */
public class SpreadGenerator implements Generator {

	private static enum DIRECTION {
		UP {

			@Override
			DIRECTION inverse() {
				return DOWN;
			}

			@Override
			AtomicLong getStartHolder(SpreadGenerator generator) {
				return generator.above;
			}

			@Override
			long getAndMoveStart(AtomicLong startHolder, long values) {
				return startHolder.getAndAdd(values);
			}

			@Override
			LongStream stream(long start, long values) {
				return LongStream.range(start, start + values);
			}
		},
		DOWN {

			@Override
			DIRECTION inverse() {
				return UP;
			}

			@Override
			AtomicLong getStartHolder(SpreadGenerator generator) {
				return generator.below;
			}

			@Override
			long getAndMoveStart(AtomicLong startHolder, long values) {
				return startHolder.getAndAdd(-values);
			}

			@Override
			LongStream stream(long start, long values) {
				return LongStream.range(start - values, start);
			}
		};

		abstract DIRECTION inverse();

		abstract AtomicLong getStartHolder(SpreadGenerator generator);

		abstract long getAndMoveStart(AtomicLong startHolder, long values);

		abstract LongStream stream(long start, long values);
	}

	private final AtomicLong above, below;
	private final AtomicReference<DIRECTION> direction;

	public SpreadGenerator(long start) {
		above = new AtomicLong(start);
		below = new AtomicLong(start);
		direction = new AtomicReference<DIRECTION>(DIRECTION.UP);
	}

	@Override
	public LongStream stream(long values) {
		DIRECTION direction = getAndToggleDirection();
		AtomicLong startHolder = direction.getStartHolder(this);
		long start = direction.getAndMoveStart(startHolder, values);
		return direction.stream(start, values);
	}

	private DIRECTION getAndToggleDirection() {
		DIRECTION result;

		do {
			result = direction.get();
		}
		while (!direction.compareAndSet(result, result.inverse()));

		return result;
	}
}
