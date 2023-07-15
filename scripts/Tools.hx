import sys.FileSystem;
import sys.io.File;
using StringTools;
using haxe.io.Path;

/** Recursively deletes all files in the specified `directory`. **/
function cleanDirectory(directory: String) for (entry in FileSystem.readDirectory(directory).filter(entry -> entry != ".gitkeep")) {
	final path = Path.join([directory, entry]);
	FileSystem.isDirectory(path) ? removeDirectory(path) : FileSystem.deleteFile(path);
}

/** Recursively deletes the specified `directory`. **/
function removeDirectory(directory: String) {
	cleanDirectory(directory);
	FileSystem.deleteDirectory(directory);
}

/** Sets the Java class path. **/
function setClassPath()
	Sys.putEnv("CLASSPATH", ["bin", File.getContent(".classpath").rtrim()].join(Sys.systemName() == "Windows" ? ";" : ":"));
