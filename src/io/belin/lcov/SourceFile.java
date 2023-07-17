package io.belin.lcov;

import java.util.StringJoiner;

/**
 * Provides the coverage data of a source file.
 */
public class SourceFile {

	/**
	 * The branch coverage.
	 */
	public BranchCoverage branches;

	/**
	 * The branch coverage.
	 */
	public FunctionCoverage functions;

	/**
	 * The line coverage.
	 */
	public LineCoverage lines;

	/**
	 * The path to the source file.
	 */
	public String path;

	/**
	 * Creates a new source file.
	 */
	public SourceFile() {
		this("");
	}

	/**
	 * Creates a new source file.
	 * @param path The path to the source file.
	 */
	public SourceFile(String path) {
		this(path, null, null, null);
	}

	/**
	 * Creates a new source file.
	 * @param path The path to the source file.
	 * @param functions The function coverage.
	 * @param branches The branch coverage.
	 * @param lines The line coverage.
	 */
	public SourceFile(String path, FunctionCoverage functions, BranchCoverage branches, LineCoverage lines) {
		this.branches = branches;
		this.functions = functions;
		this.lines = lines;
		this.path = path;
	}

	/**
	 * Returns a string representation of this object.
	 * @return The string representation of this object.
	 */
	@Override public String toString() {
		var joiner = new StringJoiner(System.lineSeparator()).add(Token.SourceFile + ":" + path);
		if (functions != null) joiner.add(functions.toString());
		if (branches != null) joiner.add(branches.toString());
		if (lines != null) joiner.add(lines.toString());
		return joiner.add(Token.EndOfRecord.toString()).toString();
	}
}
