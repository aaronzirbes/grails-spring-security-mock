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

/**
 * Class to load user details from the Config.groovy configuration file
 */
class MockUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService {

	private static final log = Logger.getLogger(this)
	def fullName
	def email
	def username
	ArrayList<String> mockRoles = new ArrayList<String>()

	/**
	 * This is to support the {@code RememberMeService}
	 */
	private final Map<String, MockUserDetails> registeredUsers = new HashMap<String, MockUserDetails>()
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
		return loadMockUserDetails()
	}

	/**
	 * This is to support loading by Authentication
	 */
	UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
		return loadMockUserDetails()
	}

	/**
	 * This loads the user details from configuration settings
	 */
	UserDetails loadMockUserDetails() throws UsernameNotFoundException {

		log.debug("loadUserDetails():: invocation")

		// set default values
		def fullName = fullName
		def email = email
		def username = authentication.name
		def password = ''
		def enabled = true
		def accountNonExpired = true
		def credentialsNonExpired = true
		def accountNonLocked = true

		Collection<GrantedAuthorityImpl> authorities

		// Allow roles to be manually set in development mode
		if (mockRoles) {
			// Load Development roles if enabled
			authorities.addAll(mockRoles.collect{ new GrantedAuthorityImpl(it) })
		} else {
			authorities = DEFAULT_AUTHORITIES
		}

		return new MockUserDetails(username, password, enabled, 
			accountNonExpired, credentialsNonExpired, accountNonLocked, 
			authorities, fullName, email)
	}
}
