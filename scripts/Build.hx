/** Builds the project. **/
function main() {
	final debug = Sys.args().contains("--debug");
	final pattern = "io/belin/lcov/*.java";
	Tools.setClassPath();
	Sys.command('javac -d bin ${debug ? "-g" : ""} src/$pattern test/$pattern');
}
