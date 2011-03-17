runEasyB = {folder, dest ->
	ant.easyb {
		classpath {
			fileset dir: webinfLib, {
				include name: "*.jar"
			}
			fileset dir: libDir, {
				include name: "**/*.jar"
			}
			pathelement path: webinfClasses
			pathelement path: buildDir
		}
		report location: "target/story-results/${dest}.txt", format:"txtstory"
		report location: "target/story-results/${dest}.html", format:"html"
		report location: "target/story-results/${dest}.xml", format:"xml"
		behaviors(dir: folder){
			include name: "**/*.story"
		}
	}
}