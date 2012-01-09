package edu.umn.auth

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.log4j.Logger
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter

/**
 * Processes a {@link MockAuthenticationToken}, and authenticates via Mock authenticator
 * 
 * @author <a href="mailto:ajz@umn.edu">Aaron J. Zirbes</a>
 */
class MockAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final log = Logger.getLogger(this)

	def mockAuthType
	def mockUsername

	/** The default constructor */
	public MockAuthenticationFilter() {
		super("/j_spring_mock_security_check")
	}

	/** Try to login the user using a mock authenticator */
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

		Authentication token = null

		log.debug "attemptAuthentication():: loading mock security environment"

		remoteUser = mockUsername

		MockAuthenticationToken mockAuthenticationToken = new MockAuthenticationToken(remoteUser)

		log.debug "attemptAuthentication():: calling authenticate"
		token = authenticationManager.authenticate(shibbolethAuthenticationToken)

		return token
	}
}
