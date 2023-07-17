package io.belin.lcov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

/**
 * Tests the features of the {@link FunctionCoverage} class.
 */
public class FunctionCoverageTest {

	/**
	 * Tests the {@link FunctionCoverage#toString} method.
	 */
	@Test public void testToString() {
		var eol = System.lineSeparator();
		assertEquals("FNF:0{eol}FNH:0".replace("{eol}", eol), new FunctionCoverage().toString());

		var data = new FunctionData("main", 127, 3);
		var coverage = new FunctionCoverage(23, 11, new FunctionData[] {data});
		assertEquals((data.toString() + "{eol}FNF:23{eol}FNH:11").replace("{eol}", eol), coverage.toString());
	}
}
