package io.belin.lcov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests the features of the {@link LineData} class.
 */
@DisplayName("LineData")
final class LineDataTest {

	@ParameterizedTest
	@DisplayName("toString()")
	@CsvSource({
		"0, 0, '', 'DA:0,0'",
		"127, 3, ed076287532e86365e841e92bfc50d8c, 'DA:127,3,ed076287532e86365e841e92bfc50d8c'"
	})
	void testToString(int lineNumber, int executionCount, String checksum, String output) {
		var data = new LineData(lineNumber, executionCount, checksum);
		assertEquals(output, data.toString());
	}
}
