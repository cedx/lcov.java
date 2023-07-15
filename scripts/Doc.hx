/** Builds the documentation. **/
function main() {
	// TODO --source-path
	Sys.command("javadoc -d bin -g src/io/belin/lcov/*.java test/io/belin/lcov/*.java");
}
