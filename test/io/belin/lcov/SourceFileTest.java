package io.belin.lcov;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the features of the {@link SourceFile} class.
 */
@DisplayName("SourceFile")
final class SourceFileTest {

	@Test
	@DisplayName("toString()")
	void testToString() {
		var eol = System.lineSeparator();
		assertEquals("SF:{eol}end_of_record".replace("{eol}", eol), new SourceFile().toString());

		var sourceFile = new SourceFile(Path.of("/home/cedx/lcov.java"), new FunctionCoverage(), new BranchCoverage(), new LineCoverage());
		var format = String.join(eol,
			"SF:/home/cedx/lcov.java".replace('/', File.pathSeparatorChar),
			sourceFile.functions.toString(),
			sourceFile.branches.toString(),
			sourceFile.lines.toString(),
			"end_of_record"
		);

		assertEquals(format, sourceFile.toString());
	}
}
