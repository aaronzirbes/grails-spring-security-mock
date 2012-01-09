package edu.umn.auth;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.Assert;

/**
 * Processes a {@link MockAuthenticationToken}, and authenticates via Mock authenticator
 * 
 * @author <a href="mailto:ajz@umn.edu">Aaron J. Zirbes</a>
 */
class MockAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private String mockUsername;

	/** The default constructor */
	public MockAuthenticationFilter() {
		super("/j_spring_mock_security_check");
	}

	/** Try to login the user using a mock authenticator */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException {

		logger.debug("attemptAuthentication():: loading mock security environment");

		MockAuthenticationToken mockAuthenticationToken = new MockAuthenticationToken(mockUsername);

		logger.debug("attemptAuthentication():: calling authenticate");

		return this.getAuthenticationManager().authenticate(mockAuthenticationToken);
	}

	public void setMockUsername(String mockUsername) {
		this.mockUsername = mockUsername;
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		Assert.notNull(mockUsername, "mockUsername cannot be null");
	}
}
