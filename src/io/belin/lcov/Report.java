package io.belin.lcov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a trace file, that is a coverage report.
 */
public class Report {

	/**
	 * The source file list.
	 */
	public ArrayList<SourceFile> sourceFiles;

	/**
	 * The test name.
	 */
	public String testName;

	/**
	 * Creates a new report.
	 */
	public Report() {
		this("");
	}

	/**
	 * Creates a new report.
	 * @param testName The test name.
	 */
	public Report(String testName) {
		this(testName, new SourceFile[0]);
	}

	/**
	 * Creates a new report.
	 * @param testName The test name.
	 * @param sourceFiles The source file list.
	 */
	public Report(String testName, SourceFile[] sourceFiles) {
		this.sourceFiles = new ArrayList<>(Arrays.asList(sourceFiles));
		this.testName = testName;
	}

	/**
	 * Parses the specified coverage data in LCOV format.
	 * @param coverage The coverage data.
	 * @return The resulting coverage report.
	 * @throws IllegalArgumentException A parsing error occurred.
	 */
	public static Report parse(String coverage) {
		int offset = 0;
		var report = new Report();
		var sourceFile = new SourceFile();

		for (var line: coverage.split("\r?\n")) {
			offset++;
			line = line.trim();
			if (line.length() == 0) continue;

			var parts = line.split(":");
			if (parts.length < 2 && !parts[0].equals(Token.endOfRecord.toString()))
				throw new IllegalArgumentException("Invalid token format at line #%d.".formatted(offset));

			var data = String.join(":", Arrays.copyOfRange(parts, 1, parts.length)).split(",");
			var token = Token.from(parts[0]);
			if (token.isEmpty()) throw new IllegalArgumentException("Unknown token at line #%d.".formatted(offset));

			switch (token.get()) {
				case testName -> { if (report.testName.length() == 0) report.testName = data[0]; }
				case endOfRecord -> report.sourceFiles.add(sourceFile);

				case branchData -> {
					if (data.length < 4) throw new IllegalArgumentException("Invalid branch data at line #%d.".formatted(offset));
					if (sourceFile.branches != null) sourceFile.branches.data.add(new BranchData(
						Integer.parseInt(data[0]),
						Integer.parseInt(data[1]),
						Integer.parseInt(data[2]),
						data[3].equals("-") ? 0 : Integer.parseInt(data[3])
					));
				}

				case functionData -> {
					if (data.length < 2) throw new IllegalArgumentException("Invalid function data at line #%d.".formatted(offset));
					if (sourceFile.functions != null) for (var item: sourceFile.functions.data) if (item.functionName.equals(data[1])) {
						item.executionCount = Integer.parseInt(data[0]);
						break;
					}
				}

				case functionName -> {
					if (data.length < 2) throw new IllegalArgumentException("Invalid function name at line #%d.".formatted(offset));
					if (sourceFile.functions != null) sourceFile.functions.data.add(new FunctionData(data[1], Integer.parseInt(data[0]), 0));
				}

				case lineData -> {
					if (data.length < 2) throw new IllegalArgumentException("Invalid line data at line #%d.".formatted(offset));
					if (sourceFile.lines != null) sourceFile.lines.data.add(new LineData(
						Integer.parseInt(data[0]),
						Integer.parseInt(data[1]),
						data.length >= 3 ? data[2] : ""
					));
				}

				case sourceFile -> sourceFile = new SourceFile(data[0], new FunctionCoverage(), new BranchCoverage(), new LineCoverage());
				case branchesFound -> { if (sourceFile.branches != null) sourceFile.branches.found = Integer.parseInt(data[0]); }
				case branchesHit -> { if (sourceFile.branches != null) sourceFile.branches.hit = Integer.parseInt(data[0]); }
				case functionsFound -> { if (sourceFile.functions != null) sourceFile.functions.found = Integer.parseInt(data[0]); }
				case functionsHit -> { if (sourceFile.functions != null) sourceFile.functions.hit = Integer.parseInt(data[0]); }
				case linesFound -> { if (sourceFile.lines != null) sourceFile.lines.found = Integer.parseInt(data[0]); }
				case linesHit -> { if (sourceFile.lines != null) sourceFile.lines.hit = Integer.parseInt(data[0]); }
			}
		}

		if (report.sourceFiles.size() == 0) throw new IllegalArgumentException("The coverage data is empty or invalid.");
		return report;
	}

	/**
	 * Returns a string representation of this object.
	 * @return The string representation of this object.
	 */
	@Override public String toString() {
		var stream = testName.length() > 0 ? Stream.of(Token.testName + ":" + testName) : Stream.<String>empty();
		return Stream.concat(stream, sourceFiles.stream().map(String::valueOf)).collect(Collectors.joining(System.lineSeparator()));
	}
}
