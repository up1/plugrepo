package net.jetztgrad.plugrepo

class PluginRelease {
	String name
	String pluginVersion
	String grailsVersion
	String fileToken
	String documentationUrl
	String downloadUrl
	Plugin plugin

	static searchable = true
	static belongsTo = [repository:Repository]
	
    static constraints = {
		name(blank:false)
		repository(nullable: true)
		plugin(nullable: false)
		pluginVersion(nullable: false)
		grailsVersion(nullable: true)
		fileToken(nullable: true)
		documentationUrl(nullable: true) //, url: true)
		downloadUrl(nullable: true) //, url: true)
    }

	String toString() {
		"${name}/${pluginVersion}"
	}
}
