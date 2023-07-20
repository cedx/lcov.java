package io.belin.lcov;

/**
 * Provides details for line coverage.
 */
public class LineData {

	/**
	 * The data checksum.
	 */
	public String checksum;

	/**
	 * The execution count.
	 */
	public int executionCount;

	/**
	 * The line number.
	 */
	public int lineNumber;

	/**
	 * Creates new line data.
	 */
	public LineData() {
		this(0, 0);
	}

	/**
	 * Creates new line data.
	 * @param lineNumber The line number.
	 * @param executionCount The execution count.
	 */
	public LineData(int lineNumber, int executionCount) {
		this(lineNumber, executionCount, "");
	}

	/**
	 * Creates new line data.
	 * @param lineNumber The line number.
	 * @param executionCount The execution count.
	 * @param checksum The data checksum.
	 */
	public LineData(int lineNumber, int executionCount, String checksum) {
		this.checksum = checksum;
		this.executionCount = executionCount;
		this.lineNumber = lineNumber;
	}

	/**
	 * Returns a string representation of this object.
	 * @return The string representation of this object.
	 */
	@Override public String toString() {
		var value = Token.LineData + ":" + lineNumber + "," + executionCount;
		return checksum.isEmpty() ? value : value + "," + checksum;
	}
}
