import io.belin.lcov.Report;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Parses a LCOV report to coverage data.
 */
@SuppressWarnings({"PMD.NoPackage", "PMD.SystemPrintln", "PMD.UseUtilityClass"})
class ParseReport {

	/**
	 * Application entry point.
	 * @param args The command line arguments.
	 */
	public static void main(String... args) throws IOException {
		try {
			var report = Report.parse(Files.readString(Path.of("share/lcov.info")));
			System.out.printf("The coverage report contains %d source files:%n", report.sourceFiles.size());
			var builder = JsonbBuilder.create(new JsonbConfig().withFormatting(System.console() != null));
			System.out.println(builder.toJson(report));
		}
		catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}
}
