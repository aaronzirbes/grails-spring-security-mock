package edu.umn.auth

import org.apache.log4j.Logger
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator
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
 * Class to load user details from the Config.groovy configuration file
 */
class MockUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService {

	static final Logger logger = Logger.getLogger(this)
	String fullName
	String email
	ArrayList<String> mockRoles = new ArrayList<String>()

	String userDnBase
	DefaultLdapAuthoritiesPopulator ldapAuthoritiesPopulator

	/**
	 * This is to support the {@code RememberMeService}
	 */
	Map<String, MockUserDetails> registeredUsers = new HashMap<String, MockUserDetails>()
	/**
	 * Some Spring Security classes (e.g. RoleHierarchyVoter) expect at least one role, so
	 * we give a user with no granted roles this one which gets past that restriction but
	 * doesn't grant anything.
	 */
	private static final List<GrantedAuthority> DEFAULT_AUTHORITIES = AuthorityUtils.createAuthorityList("ROLE_USER")

	/**
	 * This is to support the {@code RememberMeService}
	 */
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = loadMockUserDetails(username)
		return userDetails
	}

	/**
	 * This is to support loading by Authentication
	 */
	UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
		if (authentication == null) {
			return null
		} else {
			return loadMockUserDetails(authentication.getPrincipal().toString())
		}
	}

	/**
	 * This loads the user details from configuration settings
	 */
	UserDetails loadMockUserDetails(String username) throws UsernameNotFoundException {

		// set default values
		String fullName = fullName
		String email = email
		String password = ''
		boolean enabled = true
		boolean accountNonExpired = true
		boolean credentialsNonExpired = true
		boolean accountNonLocked = true

		Collection<GrantedAuthority> authorities

		// Allow roles to be manually set in development mode
		if (userDnBase && ldapAuthoritiesPopulator) {
			logger.debug "loading roles/authorities from LDAP"
			String userDn = 'cn=' + username + ',' + userDnBase
			// Load roles from LDAP
			authorities = ldapAuthoritiesPopulator.getGroupMembershipRoles(userDn, username)
		} else if (mockRoles) {
			logger.debug "loading roles from configuration"
			// Load Development roles if enabled
			authorities = mockRoles.collect{ new GrantedAuthorityImpl(it) }
		} else {
			// Falling back for default
			logger.debug "falling back to the default role"
			authorities = DEFAULT_AUTHORITIES
		}

		return new MockUserDetails(username, password, enabled, 
			accountNonExpired, credentialsNonExpired, accountNonLocked, 
			authorities, fullName, email)
	}
}
