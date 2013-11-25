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
        grailsHome()
        grailsCentral()
        mavenCentral()
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
    }
	plugins {
        // Grails Core Plugins
        compile(":hibernate:${grailsVersion}") {
            export = false
        }
        runtime(":tomcat:${grailsVersion}") {
            export = false 
        }

        // Spring Security
        compile ':spring-security-core:1.2.7.3'
        compile(':spring-security-ldap:1.0.6') {
            export = false
        }

        // Plugin Release
        build(':release:2.2.1', ':rest-client-builder:1.0.3') {
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
