import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Runs a main class from the "example" folder.
 */
@SuppressWarnings({"PMD.NoPackage", "PMD.UseUtilityClass"})
class Example {

	/**
	 * The base package of this library.
	 */
	static final String pack = "io.belin.lcov";

	/**
	 * Script entry point.
	 * @param args The command line arguments.
	 */
	public static void main(String... args) throws InterruptedException, IOException {
		var jar = Path.of("bin/%s.jar".formatted(pack));
		if (!Files.exists(jar)) exec(List.of("java", "scripts/Dist.java"));
		exec(Stream.concat(List.of("java").stream(), Arrays.stream(args)).toList(), Map.of("CLASSPATH", getClassPath(jar)));
	}

	/**
	 * Executes the specified command.
	 * @param command The command to execute.
	 * @return The exit code of the executed command.
	 */
	private static int exec(List<String> command) throws InterruptedException, IOException {
		return exec(command, null);
	}

	/**
	 * Executes the specified command.
	 * @param command The command to execute.
	 * @param environment The optional environment variables to add to the spawned process.
	 * @return The exit code of the executed command.
	 */
	private static int exec(List<String> command, Map<String, String> environment) throws InterruptedException, IOException {
		var variables = new HashMap<>(System.getenv());
		if (environment != null) variables.putAll(environment);

		var envp = variables.entrySet().stream().map(entry -> "%s=%s".formatted(entry.getKey(), entry.getValue())).toArray(String[]::new);
		var process = Runtime.getRuntime().exec(Objects.requireNonNull(command).toArray(String[]::new), envp);
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
