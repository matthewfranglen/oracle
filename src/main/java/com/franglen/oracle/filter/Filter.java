package com.franglen.oracle.filter;

import java.util.Random;

/**
 * A filter represents a constraint on a seed.
 * 
 * @author matthew
 *
 */
public interface Filter {

	/**
	 * The filter accepts the random object if it generates the correct value on
	 * the next call.
	 */
	public boolean accepts(Random value);
}
