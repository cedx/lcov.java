package io.belin.lcov;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the features of the {@link Report} class.
 */
@DisplayName("Report")
final class ReportTest {

	@Test
	@DisplayName("parse()")
	void parse() throws IOException {
		var report = Report.parse(Files.readString(Path.of("res/lcov.info"))).get();

		// It should have a test name.
		assertEquals("Example", report.testName);

		// It should contain three source files.
		assertEquals(3, report.sourceFiles.size());
		assertEquals(Path.of("/home/cedx/lcov.java/fixture.java"), report.sourceFiles.get(0).path);
		assertEquals(Path.of("/home/cedx/lcov.java/func1.java"), report.sourceFiles.get(1).path);
		assertEquals(Path.of("/home/cedx/lcov.java/func2.java"), report.sourceFiles.get(2).path);

		// It should have detailed branch coverage.
		var branches = report.sourceFiles.get(1).branches;
		assertEquals(4, branches.data.size());
		assertEquals(4, branches.found);
		assertEquals(4, branches.hit);
		assertEquals(8, branches.data.getFirst().lineNumber);

		// It should have detailed function coverage.
		var functions = report.sourceFiles.get(1).functions;
		assertEquals(1, functions.data.size());
		assertEquals(1, functions.found);
		assertEquals(1, functions.hit);
		assertEquals("func1", functions.data.getFirst().functionName);

		// It should have detailed line coverage.
		var lines = report.sourceFiles.get(1).lines;
		assertEquals(9, lines.data.size());
		assertEquals(9, lines.found);
		assertEquals(9, lines.hit);
		assertEquals("5kX7OTfHFcjnS98fjeVqNA", lines.data.getFirst().checksum);

		// It should an empty result if the report is invalid or empty.
		assertEquals(Optional.empty(), Report.parse("TN:Example"));
	}

	@Test
	@DisplayName("toString()")
	void testToString() {
		var eol = System.lineSeparator();
		assertTrue(new Report().toString().isEmpty());

		var sourceFile = new SourceFile();
		var report = new Report("LcovTest", List.of(sourceFile));
		assertEquals("TN:LcovTest{eol}".replace("{eol}", eol) + sourceFile, report.toString());
	}
}
