package org.zirbes.security.auth

import org.apache.log4j.Logger
import org.springframework.beans.factory.InitializingBean
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.util.Assert
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

		return new MockAuthenticationToken(authentication.username, authorities, mockUserDetails)
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
