package io.belin.lcov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides the coverage data of functions.
 */
public class FunctionCoverage {

	/**
	 * The coverage data.
	 */
	public List<FunctionData> data;

	/**
	 * The number of functions found.
	 */
	public int found;

	/**
	 * The number of functions hit.
	 */
	public int hit;

	/**
	 * Creates a new function coverage.
	 */
	public FunctionCoverage() {
		this(0, 0, null);
	}

	/**
	 * Creates a new function coverage.
	 * @param found The number of functions found.
	 * @param hit The number of functions hit.
	 */
	public FunctionCoverage(int found, int hit) {
		this(found, hit, null);
	}

	/**
	 * Creates a new function coverage.
	 * @param data The coverage data.
	 */
	public FunctionCoverage(List<FunctionData> data) {
		this(0, 0, data);
	}

	/**
	 * Creates a new function coverage.
	 * @param found The number of functions found.
	 * @param hit The number of functions hit.
	 * @param data The coverage data.
	 */
	public FunctionCoverage(int found, int hit, List<FunctionData> data) {
		this.data = new ArrayList<>(Objects.requireNonNullElse(data, Collections.emptyList()));
		this.found = found;
		this.hit = hit;
	}

	/**
	 * Returns a string representation of this object.
	 * @return The string representation of this object.
	 */
	@Override public String toString() {
		var functions = Stream.concat(
			data.stream().map(function -> function.toString(true)),
			data.stream().map(function -> function.toString(false))
		);

		return Stream
			.concat(functions, Stream.of(Token.FunctionsFound + ":" + found, Token.FunctionsHit + ":" + hit))
			.collect(Collectors.joining(System.lineSeparator()));
	}
}
