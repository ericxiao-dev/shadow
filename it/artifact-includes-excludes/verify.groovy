{ project ->
    def wanted = ['a.properties']
    def unwanted = ['b.properties', 'junit/framework/TestCase.class']

    def jar = file("${buildDir}/libs/${project.name}-shadow-${currentVersion}.jar")

    assert jar.exists()

    def jarFile = zipTree(jar)

    wanted.each { item ->
        assert !jarFile.matching {
            include { fileTreeElement ->
                return fileTreeElement.relativePath.toString() == item
            }
        }.isEmpty(), "Did not find $item in jar"
    }

    unwanted.each { item ->
        assert jarFile.matching {
            include { fileTreeElement ->
                fileTreeElement.relativePath.toString() == item
            }
        }.isEmpty(), "Found $item in jar"
    }
}
