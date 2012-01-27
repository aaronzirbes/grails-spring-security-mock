security {
	mock {
		// Disabled by default
		active = false

		fullName = 'Fake User Account'
		email = 'fake@example.org'
		username = 'fakeuser'
		roles = [ 'ROLE_USER', 'ROLE_ADMIN' ]
	}
	// http://jira.grails.org/browse/GPSPRINGSECURITYCORE-160
	// Over-ride Spring Security Core setting
	userLookup.enabled = false
}

// Suggested defaults
// grails.springsecurity.ipRestrictions = [ '/**': ['127.0.0.0/8', '::1/128'] ]

