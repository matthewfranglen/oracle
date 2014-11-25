package com.franglen.oracle.filter;

import java.util.Random;


/**
 * @author matthew
 *
 */
public class DoubleFilter implements Filter {

	private final double result;

	public DoubleFilter(double result) {
		this.result = result;
	}

	@Override
	public boolean accepts(Random value) {
		return value.nextDouble() == result;
	}
}
