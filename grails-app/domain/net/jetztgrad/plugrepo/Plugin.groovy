package net.jetztgrad.plugrepo

class Plugin {
	String name
	String description
	String author
	String defaultVersion
	
	static hasMany = [ versions : PluginVersion ]
	
    static constraints = {
		description(nullable: true)
		author(nullable: true)
    }
}
