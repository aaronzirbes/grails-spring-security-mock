
security {
	mock {
		// Disabled by default
		active = false

		fullName = 'Fake User Account'
		email = 'fake@example.org'
		username = 'fake user'
		mockRoles = [ 'ROLE_USER', 'ROLE_ADMIN' ]
	}
}
