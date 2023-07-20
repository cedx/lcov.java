import io.belin.lcov.Report;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Parses a LCOV report to coverage data.
 */
class ParseReport {

	/**
	 * Application entry point.
	 * @param args The command line arguments.
	 */
	public static void main(String... args) throws IOException {
		try {
			var report = Report.parse(Files.readString(Path.of("share/lcov.info")));
			System.out.printf("The coverage report contains %d source files:%n", report.sourceFiles.size());
			System.out.println(JsonbBuilder.create(new JsonbConfig().withFormatting(true)).toJson(report));
		}
		catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}
