package io.belin.lcov;

/**
 * Provides details for function coverage.
 */
public class FunctionData {

	/**
	 * The execution count.
	 */
	public int executionCount;

	/**
	 * The function name.
	 */
	public String functionName;

	/**
	 * The line number of the function start.
	 */
	public int lineNumber;

	/**
	 * Creates a new function data.
	 */
	public FunctionData() {
		this("", 0, 0);
	}

	/**
	 * Creates a new function data.
	 * @param functionName The function name.
	 * @param lineNumber The line number of the function start.
	 * @param executionCount The execution count.
	 */
	public FunctionData(String functionName, int lineNumber, int executionCount) {
		this.executionCount = executionCount;
		this.functionName = functionName;
		this.executionCount = executionCount;
	}

	/**
	 * Returns a string representation of this object.
	 * @return The string representation of this object.
	 */
	@Override public String toString() {
		return toString(false);
	}

	/**
	 * Returns a string representation of this object.
	 * @param asDefinition Value indicating whether to return the function definition (i.e. name and line number) instead of its data (i.e. name and execution count).
	 * @return The string representation of this object.
	 */
	public String toString(boolean asDefinition) {
		var token = asDefinition ? Token.functionName : Token.functionData;
		var number = asDefinition ? lineNumber : executionCount;
		return token + ":" + number + "," + functionName;
	}
}
