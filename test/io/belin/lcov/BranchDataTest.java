package io.belin.lcov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests the features of the {@link BranchData} class.
 */
class BranchDataTest {

	/**
	 * Tests the {@link BranchData#toString} method.
	 */
	@ParameterizedTest
	@CsvSource({
		"0, 0, 0, 0, 'BRDA:0,0,0,-'",
		"127, 3, 2, 1, 'BRDA:127,3,2,1'"
	})
	void testToString(int lineNumber, int blockNumber, int branchNumber, int taken, String output) {
		var data = new BranchData(lineNumber, blockNumber, branchNumber, taken);
		assertEquals(output, data.toString());
	}
}
