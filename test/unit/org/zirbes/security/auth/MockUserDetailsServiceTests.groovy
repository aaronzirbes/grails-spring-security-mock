package org.zirbes.security.auth

import static org.junit.Assert.*
import org.junit.*

class MockUserDetailsServiceTests {

    MockUserDetailsService mockUserDetailsService

    @Before
    void setUp() {
		List<String> mockRoles = [ 'ROLE_AWESOME' ]

		mockUserDetailsService = new MockUserDetailsService()
        mockUserDetailsService.fullName = 'John Doe'
        mockUserDetailsService.email = 'johndoe@example.org'
        mockUserDetailsService.mockRoles = mockRoles
    }

    @After
    void tearDown() {
        mockUserDetailsService = null
    }

    @Test
    void testLoadByUsername() {

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

    @Test
    void testLoadByToken() {
		def token = new MockAuthenticationToken('ajz')

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

    @Test
	void testNullTokenReturnsNull() {
		def userDetails = mockUserDetailsService.loadUserDetails(null)

		assertNull userDetails
	}
}
