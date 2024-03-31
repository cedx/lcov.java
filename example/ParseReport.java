import io.belin.lcov.Report;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Parses a LCOV report to coverage data.
 */
@SuppressWarnings({"PMD.NoPackage", "PMD.UseUtilityClass"})
class ParseReport {

	/**
	 * Application entry point.
	 * @param args The command line arguments.
	 */
	@SuppressWarnings("PMD.SystemPrintln")
	public static void main(String... args) throws Exception {
		var result = Report.parse(Files.readString(Path.of("res/lcov.info")));
		if (result.isEmpty()) System.err.println("The coverage data is empty or invalid.");
		else {
			var report = result.get();
			System.out.printf("The coverage report contains %d source files:%n", report.sourceFiles.size());
			try (var builder = JsonbBuilder.create(new JsonbConfig().withFormatting(true))) {
				System.out.println(builder.toJson(report));
			}
		}
	}
}
