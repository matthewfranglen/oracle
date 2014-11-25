package com.franglen.oracle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.franglen.oracle.filter.Filter;


/**
 * Holds the filters that the potential seeds must pass.
 * 
 * @author matthew
 */
public class SeedFilter {

	private final List<Filter> filters;

	public SeedFilter() {
		filters = new ArrayList<>();
	}

	public boolean accepts(Long seed) {
		Random state = new Random(seed);
		return filters.stream().allMatch((Filter filter) -> filter.accepts(state));
	}

	public void addFilter(Filter filter) {
		filters.add(filter);
	}
}
