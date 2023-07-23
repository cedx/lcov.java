import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Builds the project.
 */
@SuppressWarnings({"PMD.NoPackage", "PMD.UseUtilityClass"})
class Build {

	/**
	 * The base package of this library.
	 */
	static final String pack = "io.belin.lcov";

	/**
	 * Script entry point.
	 * @param args The command line arguments.
	 */
	public static void main(String... args) throws InterruptedException, IOException {
		var options = Arrays.asList(args).contains("--debug") ? "-g -Xlint:all,-path,-processing" : "";
		shellExec("javac -d bin %s src/%s/*.java".formatted(options, pack.replace('.', '/')), Map.of("CLASSPATH", getClassPath()));
	}

	/**
	 * Returns the class path.
	 * @return The class path.
	 */
	private static String getClassPath() throws IOException {
		return Files.readString(Path.of(".classpath")).stripTrailing();
	}

	/**
	 * Executes the specified command in a shell.
	 * @param command The command to execute.
	 * @param environment The optional environment variables to add to the spawned process.
	 * @return The exit code of the executed command.
	 */
	private static int shellExec(String command, Map<String, String> environment) throws InterruptedException, IOException {
		var variables = new HashMap<>(System.getenv());
		if (environment != null) variables.putAll(environment);

		var envp = variables.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).toArray(String[]::new);
		var shell = System.getProperty("os.name").startsWith("Windows") ? "cmd.exe /c" : "/bin/sh -c";
		var process = Runtime.getRuntime().exec(shell + " " + Objects.requireNonNull(command), envp);
		Stream.concat(process.errorReader().lines(), process.inputReader().lines()).parallel().forEach(System.out::println);
		return process.waitFor();
	}
}
