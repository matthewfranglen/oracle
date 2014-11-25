package com.franglen.oracle.filter;

import java.util.Random;


/**
 * @author matthew
 *
 */
public class IntFilter implements Filter {

	private final int result;

	public IntFilter(int result) {
		this.result = result;
	}

	@Override
	public boolean accepts(Random value) {
		return value.nextInt() == result;
	}
}
