grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsCentral()
        mavenLocal()
        mavenCentral()
        mavenRepo 'http://repo.spring.io/milestone' // TODO remove
    }
    dependencies {

        String springSecurityVersion = '3.2.0.RC1'

        compile "org.springframework.security:spring-security-core:$springSecurityVersion", {
            excludes 'aopalliance', 'aspectjrt', 'cglib-nodep', 'commons-collections', 'commons-logging',
                         'ehcache', 'fest-assert', 'hsqldb', 'jcl-over-slf4j', 'jsr250-api', 'junit',
                         'logback-classic', 'mockito-core', 'powermock-api-mockito', 'powermock-api-support',
                         'powermock-core', 'powermock-module-junit4', 'powermock-module-junit4-common',
                         'powermock-reflect', 'spring-aop', 'spring-beans', 'spring-context', 'spring-core',
                         'spring-expression', 'spring-jdbc', 'spring-test', 'spring-tx'
                }

        compile "org.springframework.security:spring-security-web:$springSecurityVersion", {
            excludes 'aopalliance', 'commons-codec', 'commons-logging', 'fest-assert', 'groovy', 'hsqldb',
                         'jcl-over-slf4j', 'junit', 'logback-classic', 'mockito-core', 'powermock-api-mockito',
                         'powermock-api-support', 'powermock-core', 'powermock-module-junit4',
                         'powermock-module-junit4-common', 'powermock-reflect', 'spock-core', 'spring-aop',
                         'spring-beans', 'spring-context', 'spring-core', 'spring-expression', 'spring-jdbc',
                         'spring-security-core', 'spring-test', 'spring-tx', 'spring-web', 'spring-webmvc',
                         'tomcat-servlet-api'
        }
    }

    plugins {

        String hibernateVersion = '3.6.10.2'
        String tomcatVersion = '7.0.42'
        String securityPluginVersion = '2.0-RC2'

        // Grails Core Plugins
        compile(":hibernate:${hibernateVersion}") {
            export = false
        }
        runtime(":tomcat:${tomcatVersion}") {
            export = false 
        }

        // Spring Security
        compile ":spring-security-core:${securityPluginVersion}"
        compile(":spring-security-ldap:${securityPluginVersion}") {
            export = false
        }

        // Plugin Release
        build(':release:2.2.1') {
            export = false
        }

        // Testing / Code Coverage
        test(':code-coverage:1.2.5') {
            export = false
        }
        test(':codenarc:0.17') {
            export = false
        }
        test(':gmetrics:0.3.1') {
            export = false
        }
	}
}

codenarc.reports = {
	JenkinsXmlReport('xml') {
		outputFile = 'target/test-reports/CodeNarcReport.xml' 
		title = 'CodeNarc Report for Grails Spring Security Mock plugin'
	}
	JenkinsHtmlReport('html') {
		outputFile = 'CodeNarcReport.html' 
		title = 'CodeNarc Report for Grails Spring Security Mock plugin'
	}
}
codenarc.propertiesFile = 'grails-app/conf/codenarc.properties'
