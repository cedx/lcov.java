package io.belin.lcov;

import java.util.Objects;

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
	 * Creates new function data.
	 */
	public FunctionData() {
		this("", 0, 0);
	}

	/**
	 * Creates new function data.
	 * @param functionName The function name.
	 */
	public FunctionData(String functionName) {
		this(functionName, 0, 0);
	}

	/**
	 * Creates new function data.
	 * @param functionName The function name.
	 * @param lineNumber The line number of the function start.
	 */
	public FunctionData(String functionName, int lineNumber) {
		this(functionName, lineNumber, 0);
	}

	/**
	 * Creates new function data.
	 * @param functionName The function name.
	 * @param lineNumber The line number of the function start.
	 * @param executionCount The execution count.
	 */
	public FunctionData(String functionName, int lineNumber, int executionCount) {
		this.executionCount = executionCount;
		this.functionName = Objects.requireNonNull(functionName);
		this.lineNumber = lineNumber;
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
	 * @param asDefinition Whether to return the function definition instead of its data.
	 * @return The string representation of this object.
	 */
	public String toString(boolean asDefinition) {
		var token = asDefinition ? Token.FunctionName : Token.FunctionData;
		var number = asDefinition ? lineNumber : executionCount;
		return token + ":" + number + "," + functionName;
	}
}
