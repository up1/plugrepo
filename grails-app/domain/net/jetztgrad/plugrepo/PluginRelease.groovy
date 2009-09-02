package net.jetztgrad.plugrepo

class PluginRelease {
	String name
	String pluginVersion
	String grailsVersion
	String fileToken
	Plugin plugin
	Boolean defaultVersion = false

	static searchable = true
	static belongsTo = [repository:Repository]
	
    static constraints = {
		name(blank:false)
		repository(nullable: true)
		plugin(nullable: false)
		pluginVersion(nullable: false)
		grailsVersion(nullable: true)
		fileToken(nullable: true)
    }

	String toString() {
		"${name}/${pluginVersion}"
	}
}
