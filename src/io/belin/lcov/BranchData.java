package io.belin.lcov;

/**
 * Provides details for branch coverage.
 */
public class BranchData {

	/**
	 * The block number.
	 */
	public int blockNumber;

	/**
	 * The branch number.
	 */
	public int branchNumber;

	/**
	 * The line number.
	 */
	public int lineNumber;

	/**
	 * A number indicating how often this branch was taken.
	 */
	public int taken;

	/**
	 * Creates new branch data.
	 */
	public BranchData() {
		this(0, 0, 0, 0);
	}

	/**
	 * Creates new branch data.
	 * @param lineNumber The line number.
	 * @param blockNumber The block number.
	 * @param branchNumber The branch number.
	 */
	public BranchData(int lineNumber, int blockNumber, int branchNumber) {
		this(lineNumber, blockNumber, branchNumber, 0);
	}

	/**
	 * Creates new branch data.
	 * @param lineNumber The line number.
	 * @param blockNumber The block number.
	 * @param branchNumber The branch number.
	 * @param taken A number indicating how often this branch was taken.
	 */
	public BranchData(int lineNumber, int blockNumber, int branchNumber, int taken) {
		this.blockNumber = blockNumber;
		this.branchNumber = branchNumber;
		this.lineNumber = lineNumber;
		this.taken = taken;
	}

	/**
	 * Returns a string representation of this object.
	 * @return The string representation of this object.
	 */
	@Override public String toString() {
		return Token.BranchData + ":" + lineNumber + "," + blockNumber + "," + branchNumber + "," + (taken > 0 ? taken : "-");
	}
}
