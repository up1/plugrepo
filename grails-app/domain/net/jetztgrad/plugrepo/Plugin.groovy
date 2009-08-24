package net.jetztgrad.plugrepo

class Plugin {
	String name
	String description
	String author
	String pluginVersion
	String grailsVersion
	String fileToken
	String documentation
	Boolean defaultVersion = false
	
	static searchable = true
	static belongsTo = [repository:Repository]
	
    static constraints = {
		name(nullable: true)
		repository(nullable: true)
		description(nullable: true)
		author(nullable: true)
		repository(nullable: true)
		documentation(nullable: true)
		pluginVersion(nullable: false)
		grailsVersion(nullable: true)
		fileToken(nullable: true)
    }

	String toString() {
		"${name}/${pluginVersion}"
	}
}
