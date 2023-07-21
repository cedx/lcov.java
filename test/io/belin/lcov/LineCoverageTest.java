package io.belin.lcov;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the features of the {@link LineCoverage} class.
 */
@DisplayName("LineCoverage")
final class LineCoverageTest {

	@Test
	@DisplayName("toString()")
	void testToString() {
		var eol = System.lineSeparator();
		assertEquals("LF:0{eol}LH:0".replace("{eol}", eol), new LineCoverage().toString());

		var data = new LineData(127, 3);
		var coverage = new LineCoverage(23, 11, List.of(data));
		assertEquals((data.toString() + "{eol}LF:23{eol}LH:11").replace("{eol}", eol), coverage.toString());
	}
}
