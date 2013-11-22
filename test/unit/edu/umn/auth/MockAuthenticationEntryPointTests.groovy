package edu.umn.auth

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import javax.servlet.http.HttpServletResponse
import org.junit.*
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.InsufficientAuthenticationException

@TestMixin(GrailsUnitTestMixin)
class MockAuthenticationEntryPointTests {

    void testRedirect() {
        def entryPoint = new MockAuthenticationEntryPoint()

		def request = new MockHttpServletRequest('GET', '/')
		def response = new MockHttpServletResponse()
		def authenticationException = new InsufficientAuthenticationException('TEST')

		entryPoint.commence(request, response, authenticationException)

		assert "/j_spring_mock_security_check" == response.redirectedUrl
    }

    void testRejectIfNoRule() {
        def entryPoint = new MockAuthenticationEntryPoint()
        entryPoint.rejectIfNoRule = true

		def request = new MockHttpServletRequest('GET', '/')

		def responseMocker = mockFor(MockHttpServletResponse)
        responseMocker.demand.sendError(1) { HttpServletResponse hsr, msg ->
            assert hsr == HttpServletResponse.SC_UNAUTHORIZED
        }
        responseMocker.demand.sendRedirect(0) { String s -> }
		def response = responseMocker.createMock()

		def authenticationException = new InsufficientAuthenticationException('TEST')

		entryPoint.commence(request, response, authenticationException)

        responseMocker.verify()
    }

}
