package io.belin.lcov;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the features of the {@link FunctionCoverage} class.
 */
@DisplayName("FunctionCoverage")
final class FunctionCoverageTest {

	/**
	 * Tests the {@link FunctionCoverage#toString} method.
	 */
	@Test
	@DisplayName("toString()")
	void testToString() {
		var eol = System.lineSeparator();
		assertEquals("FNF:0{eol}FNH:0".replace("{eol}", eol), new FunctionCoverage().toString());

		var data = new FunctionData("main", 127, 3);
		var coverage = new FunctionCoverage(23, 11, List.of(data));
		assertEquals("FN:127,main{eol}FNDA:3,main{eol}FNF:23{eol}FNH:11".replace("{eol}", eol), coverage.toString());
	}
}
