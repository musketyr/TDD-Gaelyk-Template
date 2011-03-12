JAVA_VERSION = "1.5"

source = "src"

webinfDir = "war/WEB-INF"
webinfClasses = "${webinfDir}/classes"
webinfLib = "${webinfDir}/lib"

gaeHome = System.getenv("APPENGINE_HOME")
if(!gaeHome) {
	println "To build your file you have to set 'APPENGINE_HOME' env variable pointing to your GAE SDK."
	System.exit(1)
}