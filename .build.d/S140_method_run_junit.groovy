runJUnit = {dir ->
	ant.junit fork:"true", forkmode:"once", {
		classpath {
			fileset dir: webinfLib, {
				include name: "*.jar"
			}
			fileset dir: libDir, {
				include name: "**/*.jar"
			}
			pathelement path: webinfClasses
			pathelement path: dir
		}
		batchtest todir: "target/test-results",{
			fileset dir: dir,{
				custom classname:"org.spockframework.buildsupport.ant.SpecClassFileSelector", classpathref:'lib.classpath'
			}
			fileset dir: dir,{
				include name: "**/*Test.class"
			}
		}
		formatter type:"brief", usefile:"false"
		formatter type:"xml"
		formatter type:"plain"
	}
}