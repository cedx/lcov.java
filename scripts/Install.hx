/** Installs the project dependencies. **/
function main() Sys.command("ivy", ["-cache", "lib", "-cachepath", ".classpath"]);
