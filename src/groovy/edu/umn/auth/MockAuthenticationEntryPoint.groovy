package edu.umn.auth

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.log4j.Logger
import org.springframework.beans.factory.InitializingBean
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
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
 * Processes a mock login request for spring security
 *
 * @author <a href="mailto:ajz@umn.edu">Aaron J. Zirbes</a>
 */
class MockAuthenticationEntryPoint implements AuthenticationEntryPoint, InitializingBean {

	void afterPropertiesSet() { }

	static final Logger logger = Logger.getLogger(this)

	/** Commences the login re-direct */
	public final void commence(final HttpServletRequest request, final HttpServletResponse response,
	            final AuthenticationException authenticationException) throws IOException, ServletException {

		logger.debug('commencing from exception' + authenticationException.toString())

		// get the context
		def contextPath = request.getContextPath()

		// This matches the MockAuthenticationFilter URL
		final String redirectUrl = contextPath + "/j_spring_mock_security_check"

		response.sendRedirect(redirectUrl)
	}
}
