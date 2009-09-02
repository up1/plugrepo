package net.jetztgrad.plugrepo

class Plugin {
	String name
	String description
	String author
	String documentation
	
	PluginRelease defaultRelease
	
	static searchable = true
	static hasMany = [releases:PluginRelease]
	
    static constraints = {
		name(blank: false)
		description(nullable: true)
		author(nullable: true)
		documentation(nullable: true)
		defaultRelease(nullable: true)
    }

	String toString() {
		"${name}"
	}
}
