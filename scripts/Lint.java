import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Performs the static analysis of source code.
 */
@SuppressWarnings({"PMD.NoPackage", "PMD.ShortClassName", "PMD.UseUtilityClass"})
class Lint {

	/**
	 * The list of directories to analyze.
	 */
	static final List<String> directories = List.of("example", "scripts", "src", "test");

	/**
	 * Script entry point.
	 * @param args The command line arguments.
	 */
	public static void main(String... args) throws InterruptedException, IOException {
		var environment = Map.of("CLASSPATH", getClassPath(Path.of("bin")));
		var mainClass = "net.sourceforge.pmd.cli.PmdCli";
		var sources = String.join(",", directories.toArray(String[]::new));
		exec("java %s cpd --dir=%s --exclude=scripts --minimum-tokens=100".formatted(mainClass, sources), environment);
		exec("java %s check --cache=var/pmd.cache --dir=%s --rulesets=etc/pmd.xml".formatted(mainClass, sources), environment);
	}

	/**
	 * Executes the specified command.
	 * @param command The command to execute.
	 * @param environment The optional environment variables to add to the spawned process.
	 * @return The exit code of the executed command.
	 */
	private static int exec(String command, Map<String, String> environment) throws InterruptedException, IOException {
		var variables = new HashMap<>(System.getenv());
		if (environment != null) variables.putAll(environment);

		var envp = variables.entrySet().stream().map(entry -> "%s=%s".formatted(entry.getKey(), entry.getValue())).toArray(String[]::new);
		var process = Runtime.getRuntime().exec(Objects.requireNonNull(command), envp);
		process.inputReader().lines().forEach(System.out::println);
		return process.waitFor();
	}

	/**
	 * Returns the class path.
	 * @param path An optional path to prepend to the returned class path.
	 * @return The class path.
	 */
	private static String getClassPath(Path path) throws IOException {
		var prefix = path != null && Files.exists(path) ? path.toString() + File.pathSeparator : "";
		return prefix + Files.readString(Path.of(".classpath")).stripTrailing();
	}
}
