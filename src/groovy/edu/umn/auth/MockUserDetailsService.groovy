package edu.umn.auth

import org.apache.log4j.Logger
import grails.plugin.springsecurity.userdetails.GrailsUserDetailsService
import org.springframework.dao.DataAccessException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
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
class MockUserDetailsService implements GrailsUserDetailsService, AuthenticationUserDetailsService {

	static final Logger logger = Logger.getLogger(this)
	String fullName
	String email
	ArrayList<String> mockRoles = new ArrayList<String>()

	String userDnBase
	def ldapAuthoritiesPopulator

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
	 * This is to support the {@code RememberMeService}, not that you'd need to use it with this plugin...
	 */
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return loadUserByUsername(username, true)
	}

	/**
	 * This is to support loading by Authentication
	 */
	UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
		if (authentication == null) {
			return null
		} else {
			return loadUserByUsername(authentication.getPrincipal().toString(), true)
		}
	}

	/**
	 * Loads the mock user based on the username.
	 *
	 * @param username the username identifying the user whose data is required.
	 * @param loadRoles whether to load roles at the same time as loading the user
	 *
	 * @return a fully populated user record (never <code>null</code>)
	 *
	 * @throws UsernameNotFoundException if the user could not be found
	 * @throws DataAccessException if user could not be found for a repository-specific reason
	 */
	UserDetails loadUserByUsername(String username, boolean loadRoles) 
			throws UsernameNotFoundException, DataAccessException {

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
		} else if (loadRoles && mockRoles) {
			logger.debug "loading roles from configuration"
			// Load Development roles if enabled
			authorities = mockRoles.collect{ new GrantedAuthorityImpl(it) }
		} else if (loadRoles) {
			// Falling back for default
			logger.debug "falling back to the default role"
			authorities = DEFAULT_AUTHORITIES
		}

		return new MockUserDetails(username, password, enabled, 
			accountNonExpired, credentialsNonExpired, accountNonLocked, 
			authorities, fullName, email)
	}
}
