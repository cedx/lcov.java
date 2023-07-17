package io.belin.lcov;

import java.util.Arrays;
import java.util.List;
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
		this(0, 0, new FunctionData[0]);
	}

	/**
	 * Creates a new function coverage.
	 * @param found The number of functions found.
	 * @param hit The number of functions hit.
	 */
	public FunctionCoverage(int found, int hit) {
		this(found, hit, new FunctionData[0]);
	}

	/**
	 * Creates a new function coverage.
	 * @param found The number of functions found.
	 * @param hit The number of functions hit.
	 * @param data The coverage data.
	 */
	public FunctionCoverage(int found, int hit, FunctionData[] data) {
		this.data = Arrays.asList(data);
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
			.concat(functions, Stream.of(Token.functionsFound + ":" + found, Token.functionsHit + ":" + hit))
			.collect(Collectors.joining(System.lineSeparator()));
	}
}
