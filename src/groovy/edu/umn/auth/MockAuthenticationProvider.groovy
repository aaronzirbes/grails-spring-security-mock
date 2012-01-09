package edu.umn.auth

import org.apache.log4j.Logger
import org.springframework.beans.factory.InitializingBean
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.util.IpAddressMatcher
import org.springframework.util.Assert

/**
 * An {@link AuthenticationProvider} implementation that mocks
 * up a fake spring security authentication to allow for mock
 * authentication in a development environment.
 */
class MockAuthenticationProvider implements AuthenticationProvider, InitializingBean {
	    
	private static final log = Logger.getLogger(this)

	def userDetailsService

	public MockAuthenticationProvider() {
		super()
	}

	/** 
	This attempts to authenticate an {@link Authentication} a {@link MockAuthenticationToken}
	*/
	Authentication authenticate(Authentication authentication) throws AuthenticationException {

		log.debug "authenticate() called."

		// exit if unsupported token is passed
		if (!supports(authentication.getClass())) {
			return null
		}

		// This MockAuthenticationProvider always returns a valid authentication token.
		def mockUserDetails = null
		def authorities = null

		// Support for either load by username, or load by authentication
		if (userDetailsService.metaClass.respondsTo(userDetailsService, 'loadUserByUsername', String) ) {
			log.debug "loadUserByUsername"

			mockUserDetails = userDetailsService.loadUserByUsername(authentication.username)

			authorities = mockUserDetails?.authorities
		} else if (userDetailsService.metaClass.respondsTo(userDetailsService, 'loadUserDetails', Authentication) ) {
			log.debug "loadUserDetails"

			mockUserDetails = userDetailsService.loadUserDetails(authentication)

			authorities = mockUserDetails?.authorities
		} else {
			log.debug "fallback to using username"
			// fallback to 
			mockUserDetails = authentication.principal
		}

		// MockAuthenticationToken(String, Collections$UnmodifiableSet, null, MockUserDetails, Boolean

		return new MockAuthenticationToken(authentication.username, authorities, null, mockUserDetails, true)
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(userDetailsService, "userDetailsService must be set")
	}

	/** Returns true if the Authentication implementation passed is supported
	 * by the {@code MockAuthenticationProvider#authenticate} method.
	 */
	boolean supports(Class authentication) {
		return MockAuthenticationToken.class.isAssignableFrom(authentication)
	}
}
