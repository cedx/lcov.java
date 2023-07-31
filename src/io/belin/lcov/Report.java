package io.belin.lcov;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a trace file, that is a coverage report.
 */
public class Report {

	/**
	 * The source file list.
	 */
	public List<SourceFile> sourceFiles;

	/**
	 * The test name.
	 */
	public String testName;

	/**
	 * Creates a new report.
	 */
	public Report() {
		this("", null);
	}

	/**
	 * Creates a new report.
	 * @param testName The test name.
	 */
	public Report(String testName) {
		this(testName, null);
	}

	/**
	 * Creates a new report.
	 * @param testName The test name.
	 * @param sourceFiles The source file list.
	 */
	public Report(String testName, List<SourceFile> sourceFiles) {
		this.sourceFiles = new ArrayList<>(Objects.requireNonNullElse(sourceFiles, Collections.emptyList()));
		this.testName = Objects.requireNonNull(testName);
	}

	/**
	 * Parses the specified coverage data in LCOV format.
	 * @param coverage The coverage data.
	 * @return The resulting coverage report.
	 * @throws IllegalArgumentException A parsing error occurred.
	 */
	@SuppressWarnings({"PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
	public static Report parse(String coverage) {
		int offset = 0;
		var report = new Report();
		var sourceFile = new SourceFile();

		for (var line: coverage.split("\\r?\\n")) {
			offset++;
			if (line.isBlank()) continue;

			var parts = line.trim().split(":");
			if (parts.length < 2 && !parts[0].equals(Token.EndOfRecord.toString()))
				throw new IllegalArgumentException("Invalid token format at line #%d.".formatted(offset));

			var data = String.join(":", Arrays.copyOfRange(parts, 1, parts.length)).split(",");
			var token = Token.from(parts[0]);
			if (token.isEmpty()) throw new IllegalArgumentException("Unknown token at line #%d.".formatted(offset));

			switch (token.get()) {
				case TestName -> { if (report.testName.isEmpty()) report.testName = data[0]; }
				case EndOfRecord -> report.sourceFiles.add(sourceFile);

				case BranchData -> {
					if (data.length < 4) throw new IllegalArgumentException("Invalid branch data at line #%d.".formatted(offset));
					if (sourceFile.branches != null) sourceFile.branches.data.add(new BranchData(
						Integer.parseInt(data[0]),
						Integer.parseInt(data[1]),
						Integer.parseInt(data[2]),
						data[3].equals("-") ? 0 : Integer.parseInt(data[3])
					));
				}

				case FunctionData -> {
					if (data.length < 2) throw new IllegalArgumentException("Invalid function data at line #%d.".formatted(offset));
					if (sourceFile.functions != null) for (var item: sourceFile.functions.data) if (item.functionName.equals(data[1])) {
						item.executionCount = Integer.parseInt(data[0]);
						break;
					}
				}

				case FunctionName -> {
					if (data.length < 2) throw new IllegalArgumentException("Invalid function name at line #%d.".formatted(offset));
					if (sourceFile.functions != null) sourceFile.functions.data.add(new FunctionData(data[1], Integer.parseInt(data[0]), 0));
				}

				case LineData -> {
					if (data.length < 2) throw new IllegalArgumentException("Invalid line data at line #%d.".formatted(offset));
					if (sourceFile.lines != null) sourceFile.lines.data.add(new LineData(
						Integer.parseInt(data[0]),
						Integer.parseInt(data[1]),
						data.length >= 3 ? data[2] : ""
					));
				}

				case SourceFile -> sourceFile = new SourceFile(Path.of(data[0]), new FunctionCoverage(), new BranchCoverage(), new LineCoverage());
				case BranchesFound -> { if (sourceFile.branches != null) sourceFile.branches.found = Integer.parseInt(data[0]); }
				case BranchesHit -> { if (sourceFile.branches != null) sourceFile.branches.hit = Integer.parseInt(data[0]); }
				case FunctionsFound -> { if (sourceFile.functions != null) sourceFile.functions.found = Integer.parseInt(data[0]); }
				case FunctionsHit -> { if (sourceFile.functions != null) sourceFile.functions.hit = Integer.parseInt(data[0]); }
				case LinesFound -> { if (sourceFile.lines != null) sourceFile.lines.found = Integer.parseInt(data[0]); }
				case LinesHit -> { if (sourceFile.lines != null) sourceFile.lines.hit = Integer.parseInt(data[0]); }
			}
		}

		if (report.sourceFiles.isEmpty()) throw new IllegalArgumentException("The coverage data is empty or invalid.");
		return report;
	}

	/**
	 * Returns a string representation of this object.
	 * @return The string representation of this object.
	 */
	@Override public String toString() {
		var stream = testName.isEmpty() ? Stream.<String>empty() : Stream.of(Token.TestName + ":" + testName);
		return Stream.concat(stream, sourceFiles.stream().map(String::valueOf)).collect(Collectors.joining(System.lineSeparator()));
	}
}
