package net.jetztgrad.plugrepo

enum RepositoryType {
	INTERNAL,
	PLUGREPO,
	SUBVERSION
}

class Repository {
	static final String GRAILSORG = "Grails.org"
	static final String LOCAL = "Local"
	static final int DEFAULT_PRIORITY = 10
	
	String name
	RepositoryType type
	String description
	String repositoryURL
	String userName
	String password
	Integer priority = DEFAULT_PRIORITY
	Boolean enabled = true
	
	static hasMany = [plugins:Plugin]

    static constraints = {
		name(blank:false, unique:true, size:3..50)
		repositoryURL(validator: { url, repo
			if (repo.type != RepositoryType.INTERNAL) {
				// must be valid url
				try {
					def u = new URL(url)
				}
				catch (ex) {
					return false
				}
			}
		})
		description(blank:true, maxSize:500)
		userName(nullable:true)
		password(nullable:true)
		enabled(nullable:false)
		priority(nullable:false, min:0, max:20)
		type(nullable:false)
    }
}
