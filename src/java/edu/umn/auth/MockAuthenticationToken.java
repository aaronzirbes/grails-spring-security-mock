package edu.umn.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
	An {@link Authentication} object used to load with the Mock

	This <code>MockAuthenticationToken</code> is capable of building an {@link Authentication}
	for use in faking spring security authentication.

	@author <a href="mailto:ajz@umn.edu">Aaron J. Zirbes</a>
*/
class MockAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

	private final String username;
	private final Object details;
	private final Object principal;

	public MockAuthenticationToken(String username) {

		super(new ArrayList<GrantedAuthority>(0));

		this.username = username;
		this.details = null;
		this.principal = username;
		this.setAuthenticated(false);
	}

	public String toString() {
		return username;
	}

	public MockAuthenticationToken(final String username, 
			final Collection<? extends GrantedAuthority> authorities, 
			final Object details, 
			final Object principal, 
			final boolean authenticated) {

		super(authorities);

		this.username = username;
		this.details = details;
		this.principal = principal;
		this.setAuthenticated(false);
	}

	/** Getter for username */
	public String getName() {
		return this.username;
	}

	/** Getter for credentials */
	public Object getCredentials() {
		return null;
	}

	/** Getter for details */
	public Object getDetails() {
		return this.details;
	}

	/** Getter for principal */
	public Object getPrincipal() {
		return this.principal;
	}
}
