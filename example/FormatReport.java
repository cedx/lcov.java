import java.nio.file.Path;
import java.util.List;
import io.belin.lcov.FunctionCoverage;
import io.belin.lcov.LineCoverage;
import io.belin.lcov.LineData;
import io.belin.lcov.Report;
import io.belin.lcov.SourceFile;

/**
 * Formats coverage data as LCOV report.
 */
@SuppressWarnings({"PMD.NoPackage", "PMD.UseUtilityClass"})
class FormatReport {

	/**
	 * Application entry point.
	 * @param args The command line arguments.
	 */
	@SuppressWarnings("PMD.SystemPrintln")
	public static void main(String... args) {
		var sourceFile = new SourceFile(Path.of("/home/cedx/lcov.java/fixture.java"));
		sourceFile.functions = new FunctionCoverage(1, 1);
		sourceFile.lines = new LineCoverage(2, 2, List.of(
			new LineData(6, 2, "PF4Rz2r7RTliO9u6bZ7h6g"),
			new LineData(7, 2, "yGMB6FhEEAd8OyASe3Ni1w")
		));

		var report = new Report("Example", List.of(sourceFile));
		System.out.println(report);
	}
}
