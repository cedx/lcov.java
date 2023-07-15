/** Installs the project dependencies. **/
function main()
	Sys.command("C:\\Program Files\\Apache\\ivy", ["-cache", "lib", "-cachepath", ".classpath", "-ivy", "etc/ivy.xml"]);
