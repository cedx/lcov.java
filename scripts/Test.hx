/** Runs the test suite. **/
function main() {
	Sys.command("lix Build --debug");
	Tools.setClassPath();
	final exitCode = Sys.command("java org.junit.platform.console.ConsoleLauncher --select-package=io.belin.lcov");
	if (exitCode != 0) Sys.exit(exitCode);
}
