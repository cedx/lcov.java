package io.belin.lcov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

/**
 * Tests the features of the {@link BranchCoverage} class.
 */
public class BranchCoverageTest {

	/**
	 * Tests the {@link BranchCoverage#toString} method.
	 */
	@Test public void testToString() {
		var eol = System.lineSeparator();
		assertEquals("BRF:0{eol}BRH:0".replace("{eol}", eol), new BranchCoverage().toString());

		var data = new BranchData(127, 3, 2, 1);
		var coverage = new BranchCoverage(23, 11, new BranchData[] {data});
		assertEquals((data.toString() + "{eol}BRF:23{eol}BRH:11").replace("{eol}", eol), coverage.toString());
	}
}
