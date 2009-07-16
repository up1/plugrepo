package net.jetztgrad.plugrepo

class Plugin {
	String name
	String description
	String author
	String pluginVersion
	String grailsVersion
	String fileToken
	Repository repository
	Boolean defaultVersion
	
    static constraints = {
		description(nullable: true)
		author(nullable: true)
		repository(nullable: true)
    }
}
