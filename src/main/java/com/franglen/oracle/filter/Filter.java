package com.franglen.oracle.filter;

import java.util.Random;


/**
 * @author matthew
 *
 */
public interface Filter {

	public boolean accepts(Random value);
}
