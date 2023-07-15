package io.belin.lcov;

/**
 * Provides the list of tokens supported by the parser.
 */
public enum Token {

	/**
	 * The coverage data of a branch.
	 */
	branchData("BRDA"),

	/**
	 * The number of branches found.
	 */
	branchesFound("BRF"),

	/**
	 * The number of branches hit.
	 */
	branchesHit("BRH"),

	/**
	 * The end of a section.
	 */
	endOfRecord("end_of_record"),

	/**
	 * The coverage data of a function.
	 */
	functionData("FNDA"),

	/**
	 * A function name.
	 */
	functionName("FN"),

	/**
	 * The number of functions found.
	 */
	functionsFound("FNF"),

	/**
	 * The number of functions hit.
	 */
	functionsHit("FNH"),

	/**
	 * The coverage data of a line.
	 */
	lineData("DA"),

	/**
	 * The number of lines found.
	 */
	linesFound("LF"),

	/**
	 * The number of lines hit.
	 */
	linesHit("LH"),

	/**
	 * The path to a source file.
	 */
	sourceFile("SF"),

	/**
	 * A test name.
	 */
	testName("TN");

	/**
	 * The token value.
	 */
	private final String value;

	/**
	 * Creates a new token.
	 * @param value The token value.
	 */
	private Token(String value) {
		this.value = value;
	}

	/**
	 * Returns a string representation of this object.
	 * @return The string representation of this object.
	 */
	@Override public String toString() {
		return value;
	}
}
