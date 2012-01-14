package edu.umn.auth

import static org.junit.Assert.*

import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.ProviderManager

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class MockAuthenticationFilterTests {

	def detailsServiceSettings
	def filterSettings
	def mockUserDetailsService
	def mockAuthenticationProvider 
	def authenticationManager 

    void setUp() {
        // Setup logic here

		detailsServiceSettings = [
			fullName: 'John Doe',
			email: 'johndoe@example.org' ]


		mockUserDetailsService = new MockUserDetailsService(detailsServiceSettings)
		mockAuthenticationProvider = new MockAuthenticationProvider(userDetailsService: mockUserDetailsService)
		def providers = [ mockAuthenticationProvider ]
		authenticationManager = new ProviderManager(providers: providers)

		filterSettings = [ 
			mockUsername: 'test', 
			authenticationManager: authenticationManager ]
    }

    void testFilter() {

		// Setup the Filter
		def mockAuthenticationFilter = new MockAuthenticationFilter(filterSettings)

		// Mock up Request/Response
		def request = new MockHttpServletRequest('GET', '/')
		def response = new MockHttpServletResponse()

		// call authenticate
		def authentication = mockAuthenticationFilter.attemptAuthentication(request, response)

		// make sure it worked
		assert 'test' == authentication.getUsername()
		assert 'test' == authentication.getName()
		assertTrue authentication.isAuthenticated()

    }
}
