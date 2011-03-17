LIB_CLASSPATH = 'lib.classpath'

ant.path(id:LIB_CLASSPATH){
    fileset(dir:libDir){
        include(name:"**/*.jar")
    }
}