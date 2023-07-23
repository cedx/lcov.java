import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
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
		var environment = Map.of("CLASSPATH", getClassPath(Path.of("bin")));
		var pkgPath = pack.replace('.', '/');

		exec("java scripts/Build.java --debug");
		shellExec("javac -d bin -g -Xlint:all,-path,-processing test/%s/*.java".formatted(pkgPath), environment);
		System.exit(exec("java org.junit.platform.console.ConsoleLauncher --select-package=" + pack, environment));
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
		var map = new HashMap<>(System.getenv());
		if (environment != null) map.putAll(environment);

		var variables = map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue());
		var process = Runtime.getRuntime().exec(Objects.requireNonNull(command), variables.toArray(String[]::new));
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

	/**
	 * Executes the specified command in a shell.
	 * @param command The command to execute.
	 * @param environment The optional environment variables to add to the spawned process.
	 * @return The exit code of the executed command.
	 */
	private static int shellExec(String command, Map<String, String> environment) throws InterruptedException, IOException {
		var map = new HashMap<>(System.getenv());
		if (environment != null) map.putAll(environment);

		var shell = System.getProperty("os.name").startsWith("Windows") ? List.of("cmd.exe", "/c") : List.of("/bin/sh", "-c");
		var cmdList = Stream.concat(shell.stream(), Stream.of(Objects.requireNonNull(command)));
		var variables = map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue());

		var process = Runtime.getRuntime().exec(cmdList.toArray(String[]::new), variables.toArray(String[]::new));
		Stream.concat(process.errorReader().lines(), process.inputReader().lines()).parallel().forEach(System.out::println);
		return process.waitFor();
	}
}
