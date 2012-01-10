package edu.umn.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
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
	An {@link Authentication} object used to load with the Mock

	This <code>MockAuthenticationToken</code> is capable of building an {@link Authentication}
	for use in faking spring security authentication.

	@author <a href="mailto:ajz@umn.edu">Aaron J. Zirbes</a>
*/
class MockAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

	private final String username;
	private final Object principal;

	public MockAuthenticationToken(String username) {

		super(new ArrayList<GrantedAuthority>(0));

		this.username = username;
		this.principal = username;
		this.setAuthenticated(false);
	}

	public String toString() {
		return username;
	}

	public MockAuthenticationToken(final String username, 
			final Collection<? extends GrantedAuthority> authorities, 
			final Object principal) {

		super(authorities);

		this.username = username;
		this.principal = principal;
		this.setAuthenticated(true);
	}

	/** Getter for details */
	public Object getDetails() {
		return null;
	}

	/** Getter for username */
	public String getName() {
		return this.username;
	}

	/** Getter for credentials */
	public Object getCredentials() {
		return null;
	}

	/** Getter for principal */
	public Object getPrincipal() {
		return this.principal;
	}
}
