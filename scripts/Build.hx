using StringTools;

/** Builds the project. **/
function main() {
	final debug = Sys.args().contains("--debug");
	final pkg = "io.belin.lcov";
	Tools.setClassPath();
	Sys.command('javac -d bin ${debug ? "-g -Xlint:all,-path,-processing" : ""} src/${pkg.replace(".", "/")}/*.java');
}
