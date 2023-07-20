using StringTools;

/** Runs the test suite. **/
function main() {
	final pkg = "io.belin.lcov";
	Sys.command("lix Build --debug");
	Tools.setClassPath();
	Sys.command('javac -d bin -g -Xlint:all,-path,-processing test/${pkg.replace(".", "/")}/*.java');

	final exitCode = Sys.command('java org.junit.platform.console.ConsoleLauncher --select-package=$pkg');
	if (exitCode != 0) Sys.exit(exitCode);
}
