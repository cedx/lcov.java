package io.belin.lcov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides the coverage data of branches.
 */
public class BranchCoverage {

	/**
	 * The coverage data.
	 */
	public List<BranchData> data;

	/**
	 * The number of branches found.
	 */
	public int found;

	/**
	 * The number of branches hit.
	 */
	public int hit;

	/**
	 * Creates a new branch coverage.
	 */
	public BranchCoverage() {
		this(0, 0, null);
	}

	/**
	 * Creates a new branch coverage.
	 * @param found The number of branches found.
	 * @param hit The number of branches hit.
	 */
	public BranchCoverage(int found, int hit) {
		this(found, hit, null);
	}

	/**
	 * Creates a new branch coverage.
	 * @param data The coverage data.
	 */
	public BranchCoverage(List<BranchData> data) {
		this(0, 0, data);
	}

	/**
	 * Creates a new branch coverage.
	 * @param found The number of branches found.
	 * @param hit The number of branches hit.
	 * @param data The coverage data.
	 */
	public BranchCoverage(int found, int hit, List<BranchData> data) {
		this.data = new ArrayList<>(Objects.requireNonNullElse(data, Collections.emptyList()));
		this.found = found;
		this.hit = hit;
	}

	/**
	 * Returns a string representation of this object.
	 * @return The string representation of this object.
	 */
	@Override public String toString() {
		var branches = data.stream().map(String::valueOf);
		return Stream
			.concat(branches, Stream.of(Token.BranchesFound + ":" + found, Token.BranchesHit + ":" + hit))
			.collect(Collectors.joining(System.lineSeparator()));
	}
}
