package edu.umn.auth

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class MockAuthenticationTokenTests {

    void testFilterTokenInstantiation() {
		def token = new MockAuthenticationToken('ajz')

		assert token.username == 'ajz'
		assertFalse token.authenticated
    }

    void testProviderTokenInstantiation() {

		def authorities = [] as Set
		def principal = "ajz"
		def testRole = new GrantedAuthorityImpl('ROLE_TEST')

		authorities.add(testRole)

		def token = new MockAuthenticationToken('ajz', authorities, principal)

		assert token.username == 'ajz'
		assert token.authorities.contains(testRole)
		assertTrue token.authenticated
    }
}
