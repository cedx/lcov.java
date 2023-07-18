package io.belin.lcov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests the features of the {@link FunctionData} class.
 */
@DisplayName("FunctionData")
final class FunctionDataTest {

	/**
	 * Tests the {@link FunctionData#toString} method.
	 */
	@ParameterizedTest
	@DisplayName("toString()")
	@CsvSource({
		"'', 0, 0, 'FNDA:0,', 'FN:0,'",
		"main, 127, 3, 'FNDA:3,main', 'FN:127,main'"
	})
	void testToString(String functionName, int lineNumber, int executionCount, String asData, String asDefinition) {
		var data = new FunctionData(functionName, lineNumber, executionCount);
		assertEquals(asData, data.toString(false));
		assertEquals(asDefinition, data.toString(true));
	}
}
