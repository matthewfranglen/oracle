package com.franglen.oracle;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Provides the methods required to collect a LongStream into a
 * Collection<Long>.
 * 
 * @author matthew
 */
public class SeedCollector {

	private SeedCollector() {}

	/**
	 * A function that creates and returns a new mutable result container.
	 *
	 * @return a function which returns a new, mutable result container
	 * @see java.util.stream.Collector#supplier()
	 */
	public static Collection<Long> supplier() {
		return new ArrayList<Long>();
	}

	/**
	 * A function that folds a value into a mutable result container.
	 *
	 * @return a function which folds a value into a mutable result container
	 * @see java.util.stream.Collector#accumulator()
	 */
	public static void accumulator(Collection<Long> collection, long value) {
		collection.add(value);
	}

	/**
	 * A function that accepts two partial results and merges them. The combiner
	 * function may fold state from one argument into the other and return that,
	 * or may return a new result container.
	 *
	 * @return a function which combines two partial results into a combined
	 *         result
	 * @see java.util.stream.Collector#combiner()
	 */
	public static Collection<Long> combiner(Collection<Long> one, Collection<Long> two) {
		one.addAll(two);
		return one;
	}
}
