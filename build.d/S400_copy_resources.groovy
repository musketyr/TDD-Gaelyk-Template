ant.copy todir: webinfClasses, {
	fileset dir: source, {
		exclude name: "**/*.groovy"
	}
}