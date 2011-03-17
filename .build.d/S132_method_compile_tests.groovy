compileTests = {srcdir, destdir, testsResources ->
	ant.groovyc srcdir: srcdir, destdir: destdir, {
		classpath {
			fileset dir: libDir, {
				include name: "**/*.jar"
			}
			fileset dir: webinfLib, {
				include name: "*.jar"
			}
			pathelement path: webinfClasses
			pathelement path: source
		}
		javac source: JAVA_VERSION, target: JAVA_VERSION, debug: "on"
	}
	
	ant.copy todir: destdir, {
		fileset dir: testsResources
	}
}