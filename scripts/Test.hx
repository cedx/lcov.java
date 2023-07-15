/** Runs the test suite. **/
function main() {
	Tools.setClassPath();
	Sys.command("lix Build --debug");
	Sys.command("java org.junit.platform.console.ConsoleLauncher --scan-class-path=bin");
}
