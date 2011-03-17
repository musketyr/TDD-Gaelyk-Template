@GrabResolver(name='jgit-repository', root='http://download.eclipse.org/jgit/maven')
@Grab(group='org.eclipse.jgit', module='org.eclipse.jgit.pgm', version='0.10.1')

import groovy.xml.StreamingMarkupBuilder

import org.eclipse.jgit.pgm.Main

class InstallFromGit {
	static void main(final String[] argv) {
		assert argv.size() == 1
		def pluginUrl = argv[0]
		
		File tmp = new File(System.getProperty('java.io.tmpdir'))
		def random = new Random().nextInt(10000)
		File where = new File(tmp, "gaelyk-plugins-tmp-$random")
		assert where.mkdir()
		
		File stage = new File(tmp, "gaelyk-plugins-tmp-$random-stage")
		assert stage.mkdir()
		
		Main.main(["clone", pluginUrl, where.path] as String[]);
		
		new AntBuilder().sequential{
			copy(todir: stage.path){
				fileset(dir: where.path){
					exclude(name: "*build.groovy")
					exclude(name: "*install.groovy")
					exclude(name: "*remove.groovy")
					exclude(name: ".**")
					exclude(name: "**/.*/**")
				}
				fileset(dir: where.path){
					include(name: "**/.build.d/**")
					include(name: "**/.plugins.d/**")
					exclude(name: "**/.plugins.d/history.xml")
				}
			}
			
			installPlugins(where)
		}
		
		addHistory(stage, pluginUrl)
		
		new AntBuilder().sequential{
			
			mkdir(dir: "./.plugins.d")
			
			addHistory(stage, pluginUrl)
			
			copy(todir: "."){
				fileset(dir: stage.path)
			}
			
			delete(dir: where.path)
			delete(dir: stage.path)
		}
	}
	
	private static installPlugins(where){
		def pluginsDir = new File(where, "/war/WEB-INF/plugins")
		if (pluginsDir.exists()) {
			def plugins = new File("./war/WEB-INF/plugins.groovy")
			if(!plugins.exists()){
				assert plugins.createNewFile()
			}
			pluginsDir.list().each {
				def pluginName = it - ".groovy"
				if(!plugins.readLines().grep(~/\s*install\s+$pluginName\s*/))
				plugins.append("\ninstall $pluginName\n")
			}
		}
	}
	
	private static addHistory(stage, pluginUrl){
		def historyFile = new File(new File("./.plugins.d"), "history.xml")
		if(!historyFile.exists()){
			historyFile.createNewFile()
			historyFile.append("<history></history>")
		}
		def history = new XmlSlurper().parse(historyFile)
		def pluginHistory = history.plugin.find{it.@origin == pluginUrl}
		if(pluginHistory){
			pluginHistory.replaceNode{
				plugin(origin: pluginUrl){
					stage.eachFileRecurse{ theFile ->
						file(name: theFile.path[stage.path.size()..-1])
					}
				}
			}
		} else {
			history.appendNode{
				plugin(origin: pluginUrl){
					stage.eachFileRecurse{ theFile ->
						file(name: theFile.path[stage.path.size()..-1])
					}
				}
			}
		}
		def outputBuilder = new StreamingMarkupBuilder()
		historyFile.text = outputBuilder.bind{ mkp.yield history }
		
	}
}
