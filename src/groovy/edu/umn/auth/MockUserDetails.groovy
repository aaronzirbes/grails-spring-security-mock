package edu.umn.auth

import java.util.Collection
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

/**
 * User details for use by the Mock security plugin
 * This adds support for fullname, email to the {@link User} class.
 * 
 * @author <a href="mailto:ajz@umn.edu">Aaron J. Zirbes</a>
 */
class MockUserDetails extends User {

	String fullName
	String email

	MockUserDetails(String username, String password, boolean enabled, 
			boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, 
			Collection<GrantedAuthority> authorities, String fullName, String email) { 

		super(username, password, enabled, accountNonExpired, credentialsNonExpired, 
		accountNonLocked, authorities) 

		this.fullName = fullName
		this.email = email
	} 
}
