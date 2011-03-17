ant.groovyc srcdir: source, destdir: webinfClasses, {
	exclude name:'pleasefollowme/GroovletUnderSpec.groovy'
	exclude name:'pleasefollowme/GroovletMockLogger.groovy'
	exclude name:'pleasefollowme/GaelykUnitSpec.groovy'
	classpath {
		fileset dir: webinfLib, {
	    	include name: "*.jar"
		}
		fileset dir: libDir, {
			include name: "*.jar"
		}
        fileset dir: "${gaeHome}/lib/", {
            include name: "**/*.jar"
        }
		pathelement path: source
	}
	javac source: JAVA_VERSION, target: JAVA_VERSION, debug: "on"
}