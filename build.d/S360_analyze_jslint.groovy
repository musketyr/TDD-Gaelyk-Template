ant.jslint{
	formatter type:"plain"
	formatter type:"report", destfile:"target/jslint.html"
	formatter type:"xml",    destfile:"target/jslint.xml"
	fileset dir:"war/js", includes:"**/*.js",  excludes:"**/*.pack.js"
}