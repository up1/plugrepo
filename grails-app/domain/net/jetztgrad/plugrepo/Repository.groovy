package net.jetztgrad.plugrepo

enum RepositoryType {
	INTERNAL,
	PLUGREPO,
	SUBVERSION,
	URL
}

class Repository {
	static final String GRAILSORG = "Grails.org"
	static final String LOCAL = "Local"
	static final String TEST = "Test"
	static final int DEFAULT_PRIORITY = 10
	
	String name
	RepositoryType type
	String description
	String repositoryURL
	String userName
	String password
	Integer priority = DEFAULT_PRIORITY
	Boolean enabled = true
	
	static searchable = true
	static hasMany = [releases:PluginRelease]

    static constraints = {
		name(blank:false, unique:true, size:3..50)
		repositoryURL(nullable:true, validator: { url, repo ->
			if (repo.type != RepositoryType.INTERNAL) {
				// must be valid url
				try {
					def u = new URL(url)
					return true
				}
				catch (ex) {
					return false
				}
			}
			return true
		})
		description(blank:true, maxSize:500)
		userName(nullable:true)
		password(nullable:true)
		enabled(nullable:false)
		priority(nullable:false, min:0, max:20)
		type(nullable:false)
    }

	String toString() {
		"${name}"
	}
}
