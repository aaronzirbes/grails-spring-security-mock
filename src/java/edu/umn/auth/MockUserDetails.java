package edu.umn.auth;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
 * User details for use by the Mock security plugin
 * This adds support for fullname, email to the {@link User} class.
 * 
 * @author <a href="mailto:ajz@umn.edu">Aaron J. Zirbes</a>
 */
class MockUserDetails extends User {

	private final String fullName;
	private final String email;

	MockUserDetails(String username, String password, boolean enabled, 
			boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, 
			Collection<GrantedAuthority> authorities, String fullName, String email) { 

		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		this.fullName = fullName;
		this.email = email;
	}

	public String getFullName() {
		return this.fullName;
	}

	public String getEmail() {
		return this.email;
	}
}
