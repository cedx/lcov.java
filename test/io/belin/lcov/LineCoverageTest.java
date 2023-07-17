package io.belin.lcov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

/**
 * Tests the features of the {@link LineCoverage} class.
 */
public class LineCoverageTest {

	/**
	 * Tests the {@link LineCoverage#toString} method.
	 */
	@Test public void testToString() {
		var eol = System.lineSeparator();
		assertEquals("LF:0{eol}LH:0".replace("{eol}", eol), new LineCoverage().toString());

		var data = new LineData(127, 3);
		var coverage = new LineCoverage(23, 11, new LineData[] {data});
		assertEquals((data.toString() + "{eol}LF:23{eol}LH:11").replace("{eol}", eol), coverage.toString());
	}
}
