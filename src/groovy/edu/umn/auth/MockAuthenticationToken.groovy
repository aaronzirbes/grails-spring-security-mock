package edu.umn.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.authentication.AbstractAuthenticationToken

/**
	An {@link Authentication} object used to load with the Mock

	This <code>MockAuthenticationToken</code> is capable of building an {@link Authentication}
	for use in faking spring security authentication.

	@author <a href="mailto:ajz@umn.edu">Aaron J. Zirbes</a>
*/
class MockAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

	private String name
	private Object credentials
	private Object details
	private Object principal
	private boolean authenticated

	public MockAuthenticationToken(String name) {

		super(new ArrayList<GrantedAuthority>(0));

		this.name = name 
		this.credentials = null
		this.details = null
		this.principal = name
		this.authenticated = false
	}

	public MockAuthenticationToken(String name, Collection<GrantedAuthority> authorities, 
			Object credentials, Object details, Object principal, boolean authenticated) {

		super(authorities)

		this.name = name 
		this.credentials = credentials
		this.details = details
		this.principal = principal
		this.authenticated = authenticated
	}

	/** Getter for name */
	String getName() {
		name
	}

	/** Getter for credentials */
	Object getCredentials() {
		credentials
	}

	/** Getter for details */
	Object getDetails() {
		details
	}

	/** Getter for principal */
	Object getPrincipal() {
		principal
	}

	/** Getter for authenticated */
	boolean getAuthenticated() {
		authenticated
	}
}
