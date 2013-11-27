package org.zirbes.security.auth

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class MockAuthenticationProviderTests {

	def detailsServiceSettings
	def mockUserDetailsService
	def mockAuthenticationProvider 

    void setUp() {
        // Setup logic here
		detailsServiceSettings = [
			fullName: 'John Doe',
			email: 'johndoe@example.org' ]

		mockUserDetailsService = new MockUserDetailsService(detailsServiceSettings)
    }

    void testProvider() {
		def token = new MockAuthenticationToken('ajz')
		def mockAuthenticationProvider = new MockAuthenticationProvider(userDetailsService: mockUserDetailsService)

		def newToken = mockAuthenticationProvider.authenticate(token)

		assert token.username == newToken.username
		assert false == token.isAuthenticated()
		assert true == newToken.isAuthenticated()

    }

	// try and authenticate against a non-mock authentication token
	
	// Try loadUserDetails only service

	// try weird user details service that responds to neither

	// try afterPropertiesSet where userDetailsService is not set.
	void testUserDetailsNotSet() {
		def mockAuthenticationProvider = new MockAuthenticationProvider()

		shouldFail{
			mockAuthenticationProvider.afterPropertiesSet()
		}
	}
}

