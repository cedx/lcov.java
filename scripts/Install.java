import java.io.IOException;
import java.util.Objects;

/**
 * Installs the project dependencies.
 */
@SuppressWarnings({"PMD.NoPackage", "PMD.UseUtilityClass"})
class Install {

	/**
	 * Script entry point.
	 * @param args The command line arguments.
	 */
	public static void main(String... args) throws InterruptedException, IOException {
		shellExec("ivy -cache lib -cachepath .classpath");
	}

	/**
	 * Executes the specified command in a shell.
	 * @param command The command to execute.
	 * @return The exit code of the executed command.
	 */
	private static int shellExec(String command) throws InterruptedException, IOException {
		var shell = System.getProperty("os.name").startsWith("Windows") ? "cmd.exe /c" : "/bin/sh -c";
		var process = Runtime.getRuntime().exec("%s %s".formatted(shell, Objects.requireNonNull(command)));
		process.inputReader().lines().forEach(System.out::println);
		return process.waitFor();
	}
}
