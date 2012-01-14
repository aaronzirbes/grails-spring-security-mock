package edu.umn.auth

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class MockUserDetailsServiceTests {

	def detailsServiceSettings

    void setUp() {
        // Setup logic here

		def mockRoles = [ 'ROLE_AWESOME' ]

		detailsServiceSettings = [
			fullName: 'John Doe',
			email: 'johndoe@example.org',
		   	mockRoles: mockRoles ]
    }

    void testLoadByUsername() {

		def mockUserDetailsService = new MockUserDetailsService(detailsServiceSettings)
		String username = 'ajz'
		def userDetails = mockUserDetailsService.loadUserByUsername(username)

		assert 'John Doe' == userDetails.fullName
		assert 'johndoe@example.org' == userDetails.email
		assert '' == userDetails.password
		assertTrue userDetails.accountNonExpired
		assertTrue userDetails.credentialsNonExpired
		assertTrue userDetails.accountNonLocked
		assertTrue userDetails.authorities.collect{ it.toString() }.contains('ROLE_AWESOME')
		assertFalse userDetails.authorities.collect{ it.toString() }.contains('ROLE_LAME_SAUCE')

    }

    void testLoadByToken() {
		def token = new MockAuthenticationToken('ajz')
		def mockUserDetailsService = new MockUserDetailsService(detailsServiceSettings)

		def userDetails = mockUserDetailsService.loadUserDetails(token)

		assert 'John Doe' == userDetails.fullName
		assert 'johndoe@example.org' == userDetails.email
		assert '' == userDetails.password
		assertTrue userDetails.accountNonExpired
		assertTrue userDetails.credentialsNonExpired
		assertTrue userDetails.accountNonLocked
		assertTrue userDetails.authorities.collect{ it.toString() }.contains('ROLE_AWESOME')
		assertFalse userDetails.authorities.collect{ it.toString() }.contains('ROLE_LAME_SAUCE')
    }

	void testNullTokenReturnsNull() {
		def mockUserDetailsService = new MockUserDetailsService(detailsServiceSettings)
		def userDetails = mockUserDetailsService.loadUserDetails(null)

		assertNull userDetails
	}
}
