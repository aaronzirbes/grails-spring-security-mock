package edu.umn.auth

import static org.junit.Assert.*

import org.springframework.security.core.authority.GrantedAuthorityImpl
import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class MockUserDetailsTests {

    void testInstantiation() {
		String username = 'marvelca'
		String fullName = 'Steve Rogers'
		String email = 'capnamerica@avengers.us'
		String password = 'buckybarnes'
		boolean enabled = true
		boolean accountNonExpired = true
		boolean credentialsNonExpired = true
		boolean accountNonLocked = true

		Collection<GrantedAuthorityImpl> authorities = new ArrayList<GrantedAuthorityImpl>()

		authorities.add(new GrantedAuthorityImpl('ROLE_SUPER_HERO') )

		def userDetails = new MockUserDetails(username, password, enabled, 
			accountNonExpired, credentialsNonExpired, accountNonLocked, 
			authorities, fullName, email)

		assert username == userDetails.username
		assert fullName == userDetails.fullName
		assert email == userDetails.email
		assert password == userDetails.password
		assert enabled == userDetails.enabled
		assert accountNonExpired == userDetails.accountNonExpired
		assert credentialsNonExpired == userDetails.credentialsNonExpired
		assert accountNonLocked == userDetails.accountNonLocked
		assert username == userDetails.toString()
    }
}
