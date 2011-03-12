final GROOVY_FILES = '**/*.groovy'
final RULESET_FILES = [
		'rulesets/basic.xml',
		'rulesets/braces.xml',
		'rulesets/concurrency.xml',
		'rulesets/design.xml',
		'rulesets/dry.xml',
		'rulesets/exceptions.xml',
		'rulesets/generic.xml',
		'rulesets/junit.xml',
		'rulesets/logging.xml',
		'rulesets/naming.xml',
		'rulesets/size.xml',
		'rulesets/unnecessary.xml',
		'rulesets/unused.xml',
		'rulesets/imports.xml'
].join(',')

ant.taskdef name:'codenarc', classname:'org.codenarc.ant.CodeNarcTask', classpathref:LIB_CLASSPATH

ant.codenarc(ruleSetFiles:RULESET_FILES, maxPriority1Violations:10, maxPriority2Violations:100, maxPriority3Violations:1000) {
	
	fileset(dir:'src/') {
		include(name:GROOVY_FILES)
		exclude(name:"groovyx/**/*.groovy")
	}
	fileset(dir:'tests/groovy') {
		include(name:GROOVY_FILES)
		exclude(name:"pleasefollowme/*.groovy")
	}
	
	fileset(dir:'war/WEB-INF/groovy') {
	   include(name:GROOVY_FILES)
	}
	
	report(type:'xml') {
		option(name:"outputFile", value:"target/CodeNarcXmlReport.xml")
		option(name:"title", value:"Psaní")
	}
	report(type:'html') {
		option(name:"outputFile", value:"target/CodeNarcXmlReport.html")
		option(name:"title", value:"Psaní")
	}
	report(type:'text') {
		option(name:'writeToStandardOut', value:true)
	}
}