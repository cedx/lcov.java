import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Runs the test suite.
 */
@SuppressWarnings({"PMD.NoPackage", "PMD.ShortClassName", "PMD.UseUtilityClass", "PMD.TestClassWithoutTestCases"})
class Test {

	/**
	 * The base package of this library.
	 */
	static final String pack = "io.belin.lcov";

	/**
	 * Script entry point.
	 * @param args The command line arguments.
	 */
	public static void main(String... args) throws InterruptedException, IOException {
		if (exec("java scripts/Build.java --debug") != 0) System.exit(1);

		var environment = Map.of("CLASSPATH", getClassPath(Path.of("bin")));
		var pkgPath = pack.replace('.', '/');
		if (exec("javac -d bin -g -Xlint:all,-path,-processing test/%s/*.java".formatted(pkgPath), environment) != 0) System.exit(2);

		// TODO
		System.exit(exec("java org.junit.platform.console.ConsoleLauncher --select-package=%s".formatted(pack), environment));
	}

	/**
	 * Executes the specified command.
	 * @param command The command to execute.
	 * @return The exit code of the executed command.
	 */
	private static int exec(String command) throws InterruptedException, IOException {
		return exec(command, null);
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
		Stream.concat(process.errorReader().lines(), process.inputReader().lines()).parallel().forEach(System.out::println);
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
