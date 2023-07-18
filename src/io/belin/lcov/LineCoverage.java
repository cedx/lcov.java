package io.belin.lcov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides the coverage data of lines.
 */
public class LineCoverage {

	/**
	 * The coverage data.
	 */
	public ArrayList<LineData> data;

	/**
	 * The number of lines found.
	 */
	public int found;

	/**
	 * The number of lines hit.
	 */
	public int hit;

	/**
	 * Creates a new line coverage.
	 */
	public LineCoverage() {
		this(0, 0);
	}

	/**
	 * Creates a new line coverage.
	 * @param found The number of lines found.
	 * @param hit The number of lines hit.
	 */
	public LineCoverage(int found, int hit) {
		this(found, hit, Collections.emptyList());
	}

	/**
	 * Creates a new line coverage.
	 * @param found The number of lines found.
	 * @param hit The number of lines hit.
	 * @param data The coverage data.
	 */
	public LineCoverage(int found, int hit, List<LineData> data) {
		this.data = new ArrayList<>(data);
		this.found = found;
		this.hit = hit;
	}

	/**
	 * Returns a string representation of this object.
	 * @return The string representation of this object.
	 */
	@Override public String toString() {
		var lines = data.stream().map(String::valueOf);
		return Stream
			.concat(lines, Stream.of(Token.LinesFound + ":" + found, Token.LinesHit + ":" + hit))
			.collect(Collectors.joining(System.lineSeparator()));
	}
}
