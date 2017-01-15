#Reactor Core Scala

[![Travis CI](https://travis-ci.org/sinwe/reactor-core-scala.svg?branch=master)](https://travis-ci.org/sinwe/reactor-core-scala)
[![codecov](https://codecov.io/gh/sinwe/reactor-core-scala/branch/master/graph/badge.svg)](https://codecov.io/gh/sinwe/reactor-core-scala)
[![Dependencies](https://app.updateimpact.com/badge/816040452200468480/reactor-core-scala.svg?config=compile)](https://app.updateimpact.com/latest/816040452200468480/reactor-core-scala)
                            
This project is a Scala wrapper for reactor-core.

This project was created after I can't find any Scala code for [reactor-core](https://github.com/reactor/reactor-core) project.
Using reactor-core project as it is in scala code will look ugly because
a lot of methods use Java 8 lambda which is not compatible with Scala lambda.
This will force Scala code to use anonymous class which turns ugly.

So instead of

    val mono = Mono.just(1)
                   .map(new java.util.function.Function[Int, String] {
                       def apply(t: Int): String = t.toString
                   })
                   
it becomes

    val mono = Mono.just(1).map(_.toString)

##Getting it
It is still in preliminary stage and requires a lot of refinement. No release has been made so far.
Those who wanted to try, can get the SNAPSHOT version from snapshot repository as below:

With Gradle from Sonatype:
    
    repositories {
        maven { url 'https://oss.sonatype.org/content/repositories/snapshots/' }
    }
    
    dependencies {
        compile "com.github.sinwe:reactor-core-scala:0.1.0-SNAPSHOT
    }

With Maven from Sonatype:

    <repositories>
        <repository>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <id>ossSonatypeSnapshot</id>
            <name>OSS Sonatype Snapshots</name>
            <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
            <layout>default</layout>
        </repository>
     </repositories>

    <dependency>
        <groupId>com.github.sinwe</groupId>
        <artifactId>reactor-core-scala</artifactId>
        <version>0.1.0-SNAPSHOT</version>
    </dependency>


##Contributing
Contributions are welcome. Simply fork this project, make some modification, push and 
create a pull request.
