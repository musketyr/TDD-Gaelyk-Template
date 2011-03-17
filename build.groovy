AntBuilder ant = []
GroovyScriptEngine gse = [[".build.d"] as String[]]
Binding binding = []
binding.ant = ant
binding.args = args


ant.sequential {
	new File(".build.d").list().findAll{it ==~ /S\d{3}.*\.groovy/}.sort().each{
		echo "Running part $it"
		gse.run(it, binding)
	}
}