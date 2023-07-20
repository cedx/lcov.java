import haxe.xml.Access;
import haxe.zip.Reader;
import sys.FileSystem;
import sys.io.File;
using StringTools;
using haxe.io.Path;
using haxe.zip.Tools;

/** Runs the test suite. **/
function main() {
	Sys.command("lix Build --debug");
	Tools.setClassPath();
	Sys.command('javac -d lib -g -Xlint:all,-path,-processing test/${Tools.javaPackage.replace(".", "/")}/*.java');

	final jacocoJar = "jacocoagent.jar";
	if (!FileSystem.exists('lib/$jacocoJar')) {
		final dependencies = new Access(Xml.parse(File.getContent("ivy.xml")).firstElement()).node.dependencies.nodes.dependency;
		final version = dependencies.filter(dependency -> dependency.att.name == "org.jacoco.agent").pop().att.rev;
		final input = File.read(Path.join([
			Sys.getEnv(Sys.systemName() == "Windows" ? "USERPROFILE" : "HOME"),
			'.ivy2/cache/org.jacoco/org.jacoco.agent/jars/org.jacoco.agent-$version.jar'
		]));

		final entry = Reader.readZip(input).filter(entry -> entry.fileName == jacocoJar).pop();
		if (entry.compressed) entry.uncompress();
		File.saveBytes('lib/$jacocoJar', entry.data);
		input.close();
	}

	final jacocoExec = "var/jacoco.exec";
	final exitCode = Sys.command("java", [
		'-javaagent:lib/$jacocoJar=append=false,destfile=$jacocoExec,includes=${Tools.javaPackage}.*',
		"org.junit.platform.console.ConsoleLauncher",
		'--select-package=${Tools.javaPackage}'
	]);

	if (exitCode != 0) Sys.exit(exitCode);
	Sys.command("java", ["org.jacoco.cli.internal.Main", "report",
		jacocoExec,
		'--classfiles=lib/${Tools.javaPackage.replace(".", "/")}',
		"--html=var/coverage",
		"--sourcefiles=src",
		"--xml=var/coverage.xml"
	]);
}
