package com.franglen.oracle.generator;

import java.util.stream.LongStream;


/**
 * A generator generates potential seeds.
 * 
 * @author matthew
 */
public interface Generator {

	/**
	 * Returns a stream that will generate no more than the next N values.
	 * 
	 * An infinite stream must not be returned.
	 */
	public LongStream stream(long values);
}
