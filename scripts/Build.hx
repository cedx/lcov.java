/** Builds the project. **/
function main() {
	final debug = Sys.args().contains("--debug");
	Sys.command('javac -d bin ${debug ? "-g" : "-g:none"} src/io/belin/lcov/*.java');
}
