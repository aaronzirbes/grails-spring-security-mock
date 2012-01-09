import edu.umn.auth.*
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class SpringSecurityMockGrailsPlugin {
    // the plugin version
    def version = "0.9"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.7 > *"
    // the other plugins this plugin depends on
    def dependsOn = [springSecurityCore: '1.1.1 > *']
    // resources that are excluded from plugin packaging
	def pluginExcludes = [
		'grails-app/domain/**',
		'docs/**',
		'src/docs/**',
		'test/**'
	]

    def author = "Aaron J. Zirbes"
    def authorEmail = "aaron.zirbes@gmail.com"
    def title = "Mock authentication support for Spring Security"
    def description = "Mock authentication support for Spring Security"

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/spring-security-mock"

    def doWithSpring = {
		// plug in mock artifacts in to spring security
		def conf = SpringSecurityUtils.securityConfig
		if (!conf || !conf.active) { return }

		SpringSecurityUtils.loadSecondaryConfig 'DefaultMockSecurityConfig'
		conf = SpringSecurityUtils.securityConfig
		if (!conf.mock.active) { return }

		// mock authentication entry point
		authenticationEntryPoint(ShibbolethAuthenticationEntryPoint)

		// mock user details service
		mockUserDetailsService(ShibbolethUserDetailsService) {
			fullName conf.mock.fullName
			email = conf.mock.email
			username = conf.mock.username
			mockRoles = conf.mock.roles
		}

		// mock authentication provider
		mockAuthenticationProvider(ShibbolethAuthenticationProvider) {
			userDetailsService = ref('mockUserDetailsService')
		}
		// mock authentication filter 
		mockAuthenticationFilter(ShibbolethAuthenticationFilter) {
			authenticationDetailsSource = ref('authenticationDetailsSource')
			authenticationFailureHandler = ref('authenticationFailureHandler')
			authenticationManager = ref('authenticationManager')
			authenticationSuccessHandler = ref('authenticationSuccessHandler')
			rememberMeServices = ref('rememberMeServices')
			sessionAuthenticationStrategy = ref('sessionAuthenticationStrategy')

			mockUsername = conf.mock.username
		}

		println 'Configuring Spring Security Mock ...'
		SpringSecurityUtils.registerProvider 'mockAuthenticationProvider'
		SpringSecurityUtils.registerFilter 'mockAuthenticationFilter', SecurityFilterPosition.CAS_FILTER.getOrder() + 13

    }
}
