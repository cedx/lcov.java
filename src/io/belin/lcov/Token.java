package io.belin.lcov;

import java.util.Arrays;
import java.util.Optional;

/**
 * Provides the list of tokens supported by the parser.
 */
public enum Token {

	/**
	 * The coverage data of a branch.
	 */
	BranchData("BRDA"),

	/**
	 * The number of branches found.
	 */
	BranchesFound("BRF"),

	/**
	 * The number of branches hit.
	 */
	BranchesHit("BRH"),

	/**
	 * The end of a section.
	 */
	EndOfRecord("end_of_record"),

	/**
	 * The coverage data of a function.
	 */
	FunctionData("FNDA"),

	/**
	 * A function name.
	 */
	FunctionName("FN"),

	/**
	 * The number of functions found.
	 */
	FunctionsFound("FNF"),

	/**
	 * The number of functions hit.
	 */
	FunctionsHit("FNH"),

	/**
	 * The coverage data of a line.
	 */
	LineData("DA"),

	/**
	 * The number of lines found.
	 */
	LinesFound("LF"),

	/**
	 * The number of lines hit.
	 */
	LinesHit("LH"),

	/**
	 * The path to a source file.
	 */
	SourceFile("SF"),

	/**
	 * A test name.
	 */
	TestName("TN");

	/**
	 * The token identifier.
	 */
	private final String id;

	/**
	 * Creates a new token.
	 * @param id The token identifier.
	 */
	private Token(String id) {
		this.id = id;
	}

	/**
	 * Creates a new token from the specified identifier.
	 * @param id A string specifying a token identifier.
	 * @return The optional token corresponding to the specified identifier.
	 */
	public static Optional<Token> from(String id) {
		return Arrays.stream(values()).filter(value -> value.id.equals(id)).findFirst();
	}

	/**
	 * Returns a string representation of this object.
	 * @return The string representation of this object.
	 */
	@Override public String toString() {
		return id;
	}
}
