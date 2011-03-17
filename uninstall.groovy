import groovy.xml.StreamingMarkupBuilder

assert args.size() == 1

def historyFile = new File(new File("./.plugins.d"), "history.xml")
assert historyFile.exists()

println "Parsing history file..."

def history = new XmlSlurper().parse(historyFile)
def pluginHistory = history.plugin.find{it.@origin == args[0]}
assert pluginHistory

println "History file parsed."

println "Deleting files..."

pluginHistory.file.each{
	def theFile = new File(new File('.'), it.@name.text())
	if(it.@name.text() =~ "/war/WEB-INF/plugins/\\w+.groovy") {
		removeFromPlugins(theFile.name - ".groovy")
	}
	assert theFile.exists()
	theFile.deleteOnExit()
	println "Deleted $theFile.path"
}
println "All files deleted"


println "Updating history..."

pluginHistory.replaceNode{}

historyFile.text = new StreamingMarkupBuilder().bind{ mkp.yield history }

println "History updated."

println "Plugin ${args[0]} successfully uninstalled."

private static removeFromPlugins(name){
	println "Removing $name from plugins.groovy..."
	
	def plugins = new File("./war/WEB-INF/plugins.groovy")
	if(!plugins.exists()){
		return
	}
	
	if(plugins.readLines().grep(~/\s*install\s+$name\s*/)){
		plugins.text = plugins.text.replaceAll(/\s*install\s+$name\s*/, "")
	}
	
	if(!plugins.text.trim()){
		plugins.deleteOnExit()
	}
	
	println "Removed $name from plugins.groovy."
}