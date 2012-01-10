Spring Security Mock
===================

This is a grails plugin to let you fake spring security authentication
for development purposes.  This is helpful when the spring security
implementation is tied to localized infrastructure such as LDAP, CAS,
Shibboleth, or something of the like.

Background
----------

The username and roles mocked up by the plugin are configurable
within your Config.groovy file.

It is suggested that you restrict spring security to only allow
access from localhost when using this plugin to prevent unwanted
access to your application when authentication is being bypassed.

It is recommended that you wrap the configuration settings for this
pluggin to only load when in development, or possibly test mode.

Documentation
-------------

Documentation is build using the 'grails doc' command.  You can
view the [documentation online on github](http://aaronzirbes.github.com/grails-spring-security-mock/docs/guide/).
