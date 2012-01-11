import edu.umn.auth.*
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

	/* 
	 * Grails Spring Security Mock Plugin - Fake Authentication for Spring Security
     * Copyright (C) 2012 Aaron J. Zirbes
	 *
     * This program is free software: you can redistribute it and/or modify
     * it under the terms of the GNU General Public License as published by
     * the Free Software Foundation, either version 3 of the License, or
     * (at your option) any later version.
	 *
     * This program is distributed in the hope that it will be useful,
     * but WITHOUT ANY WARRANTY; without even the implied warranty of
     * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     * GNU General Public License for more details.
	 *
     * You should have received a copy of the GNU General Public License
     * along with this program.  If not, see <http://www.gnu.org/licenses/>.
	 */
class SpringSecurityMockGrailsPlugin {
    // the plugin version
    def version = "0.9.1"
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
		authenticationEntryPoint(MockAuthenticationEntryPoint)

		// mock user details service
		mockUserDetailsService(MockUserDetailsService) {
			fullName conf.mock.fullName
			email = conf.mock.email
			username = conf.mock.username
			mockRoles = conf.mock.roles
		}

		// mock authentication provider
		mockAuthenticationProvider(MockAuthenticationProvider) {
			userDetailsService = ref('mockUserDetailsService')
		}
		// mock authentication filter 
		mockAuthenticationFilter(MockAuthenticationFilter) {
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
