package net.jetztgrad.plugrepo

class PluginVersion {
	String versionString
	String fileToken
	Repository repository
	
	static belongsTo = [plugin:Plugin]
	

    static constraints = {
		repository(nullable: true)
    }
}
