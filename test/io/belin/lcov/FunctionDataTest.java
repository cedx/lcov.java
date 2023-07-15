package io.belin.lcov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests the features of the {@link FunctionData} class.
 */
public class FunctionDataTest {

	/**
	 * Tests the {@link FunctionData#toString} method.
	 */
	@Test void testToString() {
		// It should return a format like "FN:<lineNumber>,<functionName>" when used as definition.
		assertEquals("FN:0,", new FunctionData().toString(true));
		assertEquals("FN:127,main", new FunctionData("main", 127, 3).toString(true));

		// It should return a format like "FNDA:<executionCount>,<functionName>" when used as data.
		assertEquals("FNDA:0,", new FunctionData().toString(false));
		assertEquals("FNDA:3,main", new FunctionData("main", 127, 3).toString(false));
	}
}
