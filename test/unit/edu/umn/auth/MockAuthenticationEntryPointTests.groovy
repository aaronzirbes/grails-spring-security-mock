package edu.umn.auth

import static org.junit.Assert.*

import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.InsufficientAuthenticationException
import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class MockAuthenticationEntryPointTests {

    void testRedirect() {
        def entryPoint = new MockAuthenticationEntryPoint()

		def request = new MockHttpServletRequest('GET', '/')
		def response = new MockHttpServletResponse()
		def authenticationException = new InsufficientAuthenticationException('TEST')

		entryPoint.commence(request, response, authenticationException)

		assertEquals "/j_spring_mock_security_check", response.redirectedUrl

    }
}
